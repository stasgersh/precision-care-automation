/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2022, 2022, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.howsteps;

import com.ge.onchron.verif.model.RestRequest;
import com.ge.onchron.verif.model.Token;
import com.ge.onchron.verif.utils.JsonUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Steps;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.jbehave.core.model.ExamplesTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static com.ge.onchron.verif.TestSystemParameters.*;
import static com.ge.onchron.verif.utils.FileUtils.readFromFile;
import static com.ge.onchron.verif.utils.ReplaceUtils.replaceCustomParameters;
import static com.ge.onchron.verif.utils.RestUtils.sendRequest;
import static com.ge.onchron.verif.utils.Utils.waitMillis;
import static com.ge.onchron.verif.utils.Utils.waitingTimeExpired;
import static com.ge.onchron.verif.utils.auditLog.AuditLogUtils.isDateAndTimeInRange;
import static com.ge.onchron.verif.utils.oauth2.ServiceUserAppUtils.getServiceUserAppToken;
import static net.serenitybdd.core.Serenity.*;

import static org.junit.Assert.*;

public class AuditLogSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger( AuditLogSteps.class );
    private static final String STORED_AUDIT_LOG_ITEM = "foundAuditLogItem";
    private static final String FROM_FILE_PREFIX = "[FROM_FILE]: ";


    @Steps
    RestApiSteps restApiSteps;

    public void findAuditLog( ExamplesTable expectedAuditLogParams ) {
        findAuditLog( expectedAuditLogParams, true );
    }

    public void findAuditLog( ExamplesTable expectedAuditLogParams, boolean useRequestCorrelation ) {
        boolean auditLogFound = false;
        boolean isV2 = "v2".equals( getSystemParameter( AUDIT_LOG_VERSION ) );
        String nextUrl = getSystemParameter( EAT_SERVICE_URL ) + getSystemParameter( AUDIT_LOG_MESSAGES_URL_PATH );
        long startTime = System.currentTimeMillis();
        String correlationId = null;
        if ( useRequestCorrelation ) {
            correlationId = restApiSteps.getLastResponse().getHeader( "x-correlation-id" );
        }

        boolean waitingTimeExpired;
        do {
            //search first 5 pages (v1-250/v2-500 logs)
            int pagesSearched = 5;
            for ( int i = 0; i < pagesSearched; i++ ) {
                Response response = isV2 ? getAuditLogV2( nextUrl ) : getAuditLog( i );   // search the required audit log always on the first page
                assertEquals( "Status code of getting audit log is not ok.", HttpStatus.SC_OK, response.getStatusCode() );
                JsonObject responseJson = new Gson().fromJson( response.asString(), JsonObject.class ).getAsJsonObject();
                JsonArray observedAuditLogItems = responseJson.getAsJsonArray( isV2 ? "entry" : "items" );
                if ( isV2 ) {
                    nextUrl = JsonUtils.getFieldValue( response.asString(), "link[0].url" );
                }
                auditLogFound = findAuditLog( expectedAuditLogParams, observedAuditLogItems, startTime, correlationId, isV2 );
                if ( auditLogFound || nextUrl == null || nextUrl.isEmpty() || i == pagesSearched - 1) {
                    nextUrl = getSystemParameter( EAT_SERVICE_URL ) + getSystemParameter( AUDIT_LOG_MESSAGES_URL_PATH );
                    break;
                }
            }

            int waitingTimeInMinutes = Integer.parseInt( getSystemParameter( AUDIT_LOG_WAITING_TIME_IN_MINUTES ) );
            waitingTimeExpired = waitingTimeExpired( startTime, waitingTimeInMinutes * 60000 );
            waitMillis( 3000 ); // wait 3 sec between two audit log requests

        } while ( !auditLogFound && !waitingTimeExpired );
        assertTrue( String.format( "Audit log was not found: %s", expectedAuditLogParams ), auditLogFound );
    }

    public void checkDateTimeOfAuditLog( String dateJsonPath, String dateTimeFormat ) {
        if ( !hasASessionVariableCalled( STORED_AUDIT_LOG_ITEM ) ) {
            fail( "Audit log item was not stored previously." );
        }
        JsonObject observedAuditLogItem = sessionVariableCalled( STORED_AUDIT_LOG_ITEM );
        String eventDate = JsonUtils.getFieldValue( observedAuditLogItem.toString(), dateJsonPath );
        boolean dateTimeIsParsed = checkDateTimeFormat( eventDate, dateTimeFormat );
        assertTrue( String.format( "The eventDate format is not ok. Observed date: %s, Expected format: %s", eventDate, dateTimeFormat ), dateTimeIsParsed );
    }

    private boolean checkDateTimeFormat( String eventDate, String dateTimeFormat ) {
        boolean dateTimeIsParsed;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern( dateTimeFormat );
            LocalDateTime.parse( eventDate, formatter );
            dateTimeIsParsed = true;
        } catch ( DateTimeParseException e ) {
            dateTimeIsParsed = false;
        }
        return dateTimeIsParsed;
    }

    private Response getAuditLog( int pageNum ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd" );
        LocalDate currentDate = LocalDate.now();
        String today = currentDate.format( formatter );

        RequestSpecBuilder rsb = new RequestSpecBuilder()
                .setBaseUri( getSystemParameter( EAT_SERVICE_URL ) + getSystemParameter( AUDIT_LOG_MESSAGES_URL_PATH ) )
                .addQueryParam( "startDate", today )
                .addQueryParam( "endDate", today )
                .addQueryParam( "page", pageNum )
                .setContentType( "application/json" )
                .addHeader( "x-tenant-id", getSystemParameter( EAT_SERVICE_TENANT_ID ) );
        return sendAuditRequest( rsb );
    }

    private Response getAuditLogV2( String url ) {

        RequestSpecBuilder rsb = new RequestSpecBuilder()
                .setContentType( "application/json" )
                .addHeader( "x-tenant-id", getSystemParameter( EAT_SERVICE_TENANT_ID ) );

        if ( url.contains( "?" ) ) {
            // Separate base URL and query string
            String baseUrl = url.substring( 0, url.indexOf( "?" ) );
            String query = url.substring( url.indexOf( "?" ) + 1 );

            // Add base URL
            rsb.setBaseUri( baseUrl );

            // Split and add query parameters
            String[] queryParams = query.split( "&" );
            for ( String param : queryParams ) {
                String[] keyValue = param.split( "=", 2 );
                if ( keyValue.length == 2 ) {
                    rsb.addQueryParam( keyValue[0], URLDecoder.decode( keyValue[1], StandardCharsets.UTF_8 ) );
                }
            }

        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd" );
            LocalDate currentDate = LocalDate.now();
            String today = currentDate.format( formatter );
            rsb.setBaseUri( getSystemParameter( EAT_SERVICE_URL ) + getSystemParameter( AUDIT_LOG_MESSAGES_URL_PATH ) )
               .addQueryParam( "date", today )
               .addQueryParam( "_sort", "-date" )
               .addQueryParam( "_count", 100 );
            if ( hasASessionVariableCalled( "audit.test.username" )
                    && hasASessionVariableCalled( "audit.test.organizationName" ) ) {
                String agent = STR."\{sessionVariableCalled( "audit.test.username" )}@\{sessionVariableCalled( "audit.test.organizationName" )}";
                rsb.addQueryParam( "agent", agent );
            }

        }
        return sendAuditRequest( rsb );
    }

    private static Response sendAuditRequest( RequestSpecBuilder rsb ) {
        if ( getSystemParameter( EAT_SERVICE_PROXY_HOST ) != null && !getSystemParameter( EAT_SERVICE_PROXY_PORT ).isBlank() ) {
            rsb.setProxy( getSystemParameter( EAT_SERVICE_PROXY_HOST ), Integer.parseInt( getSystemParameter( EAT_SERVICE_PROXY_PORT ) ) );
        }
        Token token = getServiceUserAppToken( getSystemParameter( EAT_SERVICE_CLIENT_ID ) );
        RequestSpecification rs = rsb.build();
        rs.auth().preemptive().oauth2( token.getAccessToken() );
        return sendRequest( new RestRequest( Method.GET, rs ) , true, false);
    }

    private boolean findAuditLog( ExamplesTable expectedAuditLogParamsTable, JsonArray observedAuditLogItems,
                                  long startTimeInMillis, String correlationId, boolean isV2 ) {
        if (observedAuditLogItems == null || observedAuditLogItems.isEmpty() ) {
            return false;
        }
        Map<String, String> expectedAuditLogParams = new HashMap<>();
        expectedAuditLogParamsTable.getRows().forEach( row ->
        {
            String propertyPath = row.get( "property_path" );
            String propertyValue = row.get( "property_value" );
            if ( propertyValue.startsWith( FROM_FILE_PREFIX ) ) {
                String fileName = StringUtils.substringAfter( propertyValue, FROM_FILE_PREFIX );
                propertyValue = readFromFile( TEST_DATA_CLASSPATH + fileName );

            }
            expectedAuditLogParams.put( propertyPath, replaceCustomParameters( propertyValue ) );
        } );
        for ( JsonElement observedAuditLogItem : observedAuditLogItems ) {
            String logItemStr;
            if ( isV2 ) {
                logItemStr = JsonUtils.getFieldValue( observedAuditLogItem.toString(), "resource" );
            } else {
                logItemStr = observedAuditLogItem.toString();
            }
            boolean auditLogFound = true;
            for ( Map.Entry<String, String> entry : expectedAuditLogParams.entrySet() ) {
                String parameterPath = entry.getKey();
                String expectedParameterValue = entry.getValue();

                String observedParameterValue = null;
                try {
                    Object value = JsonPath.read( logItemStr, parameterPath );
                    if ( value instanceof net.minidev.json.JSONArray ) {
                        observedParameterValue = new ArrayList<>( JsonPath.read( logItemStr, parameterPath ) ).getFirst().toString();
                    } else if ( value != null ) {
                        observedParameterValue = value.toString();
                    }
                } catch ( PathNotFoundException | NoSuchElementException e ) {
                    auditLogFound = false;
                    break;
                }
                if ( observedParameterValue == null ||
                        !(observedParameterValue.contains( expectedParameterValue ) &&
                                (correlationId == null || observedParameterValue.contains( correlationId )) ||
                                observedParameterValue.matches( expectedParameterValue )) ) {
                    auditLogFound = false;
                    break;
                }
            }
            if ( auditLogFound ) {
                LOGGER.info( String.format( "Required audit log candidate: %s", observedAuditLogItem ) );
                String eventDate = JsonUtils.getFieldValue( logItemStr, isV2 ? "recorded" : "eventDate" );
                String dateFormat = isV2 ? "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" : "yyyy-MM-dd HH:mm:ss.S";
                boolean inTimeRange = isDateAndTimeInRange( eventDate, startTimeInMillis, dateFormat );
                if ( inTimeRange ) {
                    LOGGER.info( "Required audit log found!" );
                    setSessionVariable( STORED_AUDIT_LOG_ITEM ).to( observedAuditLogItem.getAsJsonObject() );
                } else {
                    LOGGER.warn( String.format( "Audit log is found but it is not in the expected time range (in mins): %s. Continue searching", inTimeRange ) );
                }
                return inTimeRange;
            }
        }
        return false;

    }

    public ExamplesTable convertMapToExampleTable( Map<String, String> map ) {
        StringBuilder tableBuilder = new StringBuilder();
        tableBuilder.append( "| property_path | property_value |\n" );

        map.forEach( ( key, value ) ->
                tableBuilder.append( "| " ).append( key ).append( " | " ).append( value ).append( " |\n" )
        );
        LOGGER.info( STR."Converted Map params into ExamplesTables structure", tableBuilder );
        return new ExamplesTable( tableBuilder.toString() );
    }

}
