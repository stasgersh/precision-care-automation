/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2023, 2023, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.hc.core5.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.RestRequest;
import com.ge.onchron.verif.model.Token;
import com.ge.onchron.verif.model.UserCredentials;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static com.ge.onchron.verif.TestSystemParameters.API_BASE_URL_PROPERTY;
import static com.ge.onchron.verif.TestSystemParameters.CORE_API_BASE_PATH_PROPERTY;
import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;
import static com.ge.onchron.verif.utils.RestUtils.sendRequest;
import static com.ge.onchron.verif.utils.UserCredentialsUtils.getDefaultUser;
import static com.ge.onchron.verif.utils.Utils.storeMapSessionVariable;
import static com.ge.onchron.verif.utils.oauth2.AuthorizationCodeFlowUtils.login;
import static net.serenitybdd.core.Serenity.hasASessionVariableCalled;
import static net.serenitybdd.core.Serenity.sessionVariableCalled;
import static net.serenitybdd.core.Serenity.setSessionVariable;

public class PatientUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger( PatientUtils.class );

    public static final String EVENT_ID_COLUMN = "eventID";
    public static final String ROOT_RESOURCE_ID_COLUMN = "root_resource_of_the_event";
    public static final String PATIENTS_PATH = "%s/patients";
    public static final String TIMELINE_PATH = "%s/patients/%s/timeline";
    public static final String PATIENT_STATUS_PATH = "%s/patients/%s/status";
    public static final String PATIENT_SETTINGS_PATH = "%s/patients/%s/settings";
    public static final String STORED_PATIENT_TIMELINE_ITEMS = "storedPatientTimelineItems";
    public static final String STORED_PATIENT_LIST = "PATIENT_LIST";

    public enum PATIENT_SETTINGS {
        PATIENT_BANNER( "/banner" ),
        TIMELINE( "/timeline" ),
        TREATMENT_RESPONSE( "/treatmentResponse" ),
        TRENDS( "/trends" ),
        SUMMARY( "/summary" ),
        MEDICAL_HISTORY( "/medicalHistory");

        private String setting;

        PATIENT_SETTINGS( String setting ) {
            this.setting = setting;
        }

        public String getValue() {
            return setting;
        }
    }


    public static String getEventIdFromExampleTableRow( String patientId, Map<String, String> eventParams ) {
        String eventId = null;
        if ( eventParams.get( EVENT_ID_COLUMN ) != null && !eventParams.get( EVENT_ID_COLUMN ).equals( "N/A" ) ) {
            eventId = eventParams.get( EVENT_ID_COLUMN );

        } else if ( eventParams.get( ROOT_RESOURCE_ID_COLUMN ) != null && !eventParams.get( ROOT_RESOURCE_ID_COLUMN ).equals( "N/A" ) ) {
            String expectedRootResourceId = eventParams.get( ROOT_RESOURCE_ID_COLUMN );
            eventId = getEventIdBasedOnRootResource( patientId, expectedRootResourceId );

        } else {
            fail( "Neither event id nor root resource id was provided in the feature file" );
        }
        return eventId;
    }

    public static String getEventIdBasedOnRootResource( String patientId, String rootResourceId ) {
        // 1. Get patient's timeline items
        JsonArray timelineItems = null;
        if ( hasASessionVariableCalled( STORED_PATIENT_TIMELINE_ITEMS ) ) {
            Map<String, JsonArray> storedTimelineItems = sessionVariableCalled( STORED_PATIENT_TIMELINE_ITEMS );
            if ( storedTimelineItems.containsKey( patientId ) ) {
                timelineItems = storedTimelineItems.get( patientId );
            }
        }
        if ( timelineItems == null ) {
            RequestSpecBuilder timelineRequestBuilder = new RequestSpecBuilder();
            timelineRequestBuilder.setBaseUri( getSystemParameter( API_BASE_URL_PROPERTY ) );
            String timelinePath = String.format( TIMELINE_PATH, getSystemParameter( CORE_API_BASE_PATH_PROPERTY ), patientId );
            timelineRequestBuilder.setBasePath( timelinePath );
            timelineRequestBuilder.addQueryParam( "version", getLatestVersionTimestamp( patientId ) );
            RequestSpecification timelineRequest = timelineRequestBuilder.build();

            Token token = login( getDefaultUser() );
            timelineRequest.auth().preemptive().oauth2( token.getAccessToken() );

            int maxRetries = 3;
            int attempt = 0;
            boolean success = false;

            Response response = null;
            while ( attempt < maxRetries && !success ) {
                attempt++;
                response = sendRequest( new RestRequest( Method.GET, timelineRequest ) );

                if ( response.getStatusCode() == SC_OK || response.getStatusCode() == SC_NO_CONTENT ) {
                    LOGGER.info( STR."Response code was not expected: \{response.getStatusCode()}" );
                    break;
                } else if ( attempt == maxRetries ) {
                    // Final attempt failed, assert failure
                    assertThat( STR."The response status code is not ok after \{maxRetries} attempts. Last response code: \{response.getStatusCode()}",
                            response.getStatusCode(), is( equalTo( HttpStatus.SC_OK ) ) );
                }
            }

            JsonObject timlineRespJson = new Gson().fromJson( response.asString(), JsonObject.class );
            timelineItems = timlineRespJson.getAsJsonArray( "items" );
            storeMapSessionVariable( STORED_PATIENT_TIMELINE_ITEMS, patientId, timelineItems );
        }

        // 2. Filter for the required items
        List<JsonObject> filteredTimelineItems = new ArrayList<>();
        timelineItems.forEach( timelineItem -> {
            JsonObject timelineItemObject = timelineItem.getAsJsonObject();
            JsonArray resources = timelineItem.getAsJsonObject().getAsJsonObject( "trace" ).get( "resources" ).getAsJsonArray();
            for ( JsonElement resource : resources ) {
                String rootResource = resource.getAsString();
                if ( rootResource.equals( rootResourceId ) ) {
                    filteredTimelineItems.add( timelineItemObject );
                }
            }
        } );

        // 3. Check found items and return the event ID
        assertFalse( "Required event with root resource was not found: " + rootResourceId, filteredTimelineItems.isEmpty() );
        assertEquals( "More than one event was found with same root resource: " + rootResourceId + "\nFiltered events:\n" + filteredTimelineItems,
                1, filteredTimelineItems.size() );

        return filteredTimelineItems.get( 0 ).get( "id" ).getAsString();
    }

    public static String getLatestVersionTimestamp( String patientId ) {
        RequestSpecBuilder statusRequestBuilder = new RequestSpecBuilder();
        statusRequestBuilder.setBaseUri( getSystemParameter( API_BASE_URL_PROPERTY ) );
        String patientsPath = String.format( PATIENT_STATUS_PATH, getSystemParameter( CORE_API_BASE_PATH_PROPERTY ), patientId );
        statusRequestBuilder.setBasePath( patientsPath );
        RequestSpecification getPatientsRequest = statusRequestBuilder.build();

        Token token = login( getDefaultUser() );
        getPatientsRequest.auth().preemptive().oauth2( token.getAccessToken() );

        Response response = sendRequest( new RestRequest( Method.GET, getPatientsRequest ) );
        assertThat( "Response status code is not ok.", response.getStatusCode(), equalTo( HttpStatus.SC_OK ) );
        return response.jsonPath().getString( "latestVersion" );
    }

    public static String getPatientId( String requiredPatientName ) {
        JsonArray patientList;
        if ( hasASessionVariableCalled( STORED_PATIENT_LIST ) ) {
            patientList = sessionVariableCalled( STORED_PATIENT_LIST );

        } else {
            LOGGER.info( "Retrieve and store patient list." );
            patientList = getPatientList();
            setSessionVariable( STORED_PATIENT_LIST ).to( patientList );
        }
        JsonObject patientObject = getPatientFromPatientList( patientList, requiredPatientName );
        if ( patientObject == null ) {
            // Refresh the stored patients list to check if the patient was uploaded after the latest patient list store
            LOGGER.info( "Patient was not found in patient list, refresh patient list once." );
            patientList = getPatientList();
            patientObject = getPatientFromPatientList( patientList, requiredPatientName );
        }
        assertNotNull( "Patient was not found in patient list: " + requiredPatientName, patientObject );
        return patientObject.get( "id" ).getAsString();
    }

    private static JsonArray getPatientList() {
        RequestSpecBuilder timelineRequestBuilder = new RequestSpecBuilder();
        timelineRequestBuilder.setBaseUri( getSystemParameter( API_BASE_URL_PROPERTY ) );
        String patientsPath = String.format( PATIENTS_PATH, getSystemParameter( CORE_API_BASE_PATH_PROPERTY ) );
        timelineRequestBuilder.setBasePath( patientsPath );
        RequestSpecification getPatientsRequest = timelineRequestBuilder.build();

        Token token = login( getDefaultUser() );
        getPatientsRequest.auth().preemptive().oauth2( token.getAccessToken() );

        Response response = sendRequest( new RestRequest( Method.GET, getPatientsRequest ) );
        assertThat( "The response status code is not ok.", response.getStatusCode(), is( equalTo( HttpStatus.SC_OK ) ) );

        JsonObject jsonObject = new Gson().fromJson( response.asString(), JsonObject.class );
        return jsonObject.getAsJsonArray( "patients" );
    }

    private static JsonObject getPatientFromPatientList( JsonArray patientList, String requiredPatientIdentifier ) {
        Iterator<JsonElement> patientIterator = patientList.iterator();
        boolean patientFound = false;
        JsonObject patientObject = null;
        while ( patientIterator.hasNext() && !patientFound ) {
            JsonObject actualPatient = patientIterator.next().getAsJsonObject();
            JsonElement nameObj = actualPatient.get( "name" );
            String actualPatientName = nameObj == null ? "Unknown" : nameObj.getAsString();
            String actualPatientId = actualPatient.get( "id" ).getAsString();
            if ( actualPatientName.equals( requiredPatientIdentifier ) || actualPatientId.equals( requiredPatientIdentifier ) ) {
                patientFound = true;
                patientObject = actualPatient;
            }
        }
        return patientObject;
    }

    public static String getPatientSettings( UserCredentials userCredentials, String patientId ) {
        RequestSpecBuilder patientSettingsRequestBuilder = new RequestSpecBuilder();
        patientSettingsRequestBuilder.setBaseUri( getSystemParameter( API_BASE_URL_PROPERTY ) );
        String patientsPath = String.format( PATIENT_SETTINGS_PATH, getSystemParameter( CORE_API_BASE_PATH_PROPERTY ), patientId );
        patientSettingsRequestBuilder.setBasePath( patientsPath );
        RequestSpecification getPatientSettingsRequest = patientSettingsRequestBuilder.build();

        Token token = login( userCredentials );
        getPatientSettingsRequest.auth().preemptive().oauth2( token.getAccessToken() );

        Response response = sendRequest( new RestRequest( Method.GET, getPatientSettingsRequest ) );
        assertThat( "The response status code is not ok.", response.getStatusCode(), is( equalTo( HttpStatus.SC_OK ) ) );

        return response.asString();
    }

    public static void setPatientSettings( UserCredentials userCredentials, String patientId, JsonObject patientSettingsJson ) {
        RequestSpecBuilder patientSettingsRequestBuilder = new RequestSpecBuilder();
        patientSettingsRequestBuilder.setBaseUri( getSystemParameter( API_BASE_URL_PROPERTY ) );
        String patientsPath = String.format( PATIENT_SETTINGS_PATH, getSystemParameter( CORE_API_BASE_PATH_PROPERTY ), patientId );
        patientSettingsRequestBuilder.setBasePath( patientsPath );
        patientSettingsRequestBuilder.setContentType( ContentType.JSON );
        patientSettingsRequestBuilder.setBody( patientSettingsJson.toString() );
        RequestSpecification patientSettingsRequest = patientSettingsRequestBuilder.build();

        Token token = login( userCredentials );
        patientSettingsRequest.auth().preemptive().oauth2( token.getAccessToken() );

        Response response = sendRequest( new RestRequest( Method.PUT, patientSettingsRequest ) );
        assertThat( "The response status code is not ok.", response.getStatusCode(), is( equalTo( HttpStatus.SC_NO_CONTENT ) ) );
    }

    public static void resetPatientSettings( UserCredentials userCredentials, String patientId, PATIENT_SETTINGS patientSetting ) {
        RequestSpecBuilder patientSettingsRequestBuilder = new RequestSpecBuilder();
        patientSettingsRequestBuilder.setBaseUri( getSystemParameter( API_BASE_URL_PROPERTY ) );
        String patientsPath = String.format( PATIENT_SETTINGS_PATH + patientSetting.getValue(), getSystemParameter( CORE_API_BASE_PATH_PROPERTY ), patientId );
        patientSettingsRequestBuilder.setBasePath( patientsPath );
        RequestSpecification patientSettingsRequest = patientSettingsRequestBuilder.build();

        Token token = login( userCredentials );
        patientSettingsRequest.auth().preemptive().oauth2( token.getAccessToken() );
        LOGGER.info( "Sending {} patient settings request to {}", Method.DELETE, patientsPath );
        Response response = sendRequest( new RestRequest( Method.DELETE, patientSettingsRequest ) );
        LOGGER.info( "Failed to delete patientSettings. The response status code is: {}", response.getStatusCode() );
    }

}
