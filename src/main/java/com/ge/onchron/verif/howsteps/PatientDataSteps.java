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

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.http.params.CoreConnectionPNames.CONNECTION_TIMEOUT;
import static org.apache.http.params.CoreConnectionPNames.SO_TIMEOUT;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isOneOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.RestRequest;
import com.ge.onchron.verif.model.Token;
import com.ge.onchron.verif.utils.JsonUtils;
import com.ge.onchron.verif.utils.TextUtils;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Steps;
import software.amazon.awssdk.services.sfn.model.ExecutionStatus;

import static com.ge.onchron.verif.TestSystemParameters.DCC_URL_PROPERTY;
import static com.ge.onchron.verif.TestSystemParameters.FHIR_CLIENT_ID;
import static com.ge.onchron.verif.TestSystemParameters.FHIR_CLIENT_SECRET;
import static com.ge.onchron.verif.TestSystemParameters.FHIR_SCOPE;
import static com.ge.onchron.verif.TestSystemParameters.FHIR_SERVER_URL_PROPERTY;
import static com.ge.onchron.verif.TestSystemParameters.PS_CREATED_PATIENT_BUNDLE;
import static com.ge.onchron.verif.TestSystemParameters.TEST_DATA_CLASSPATH;
import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;
import static com.ge.onchron.verif.utils.FileUtils.readFromFile;
import static com.ge.onchron.verif.utils.PatientUtils.getLatestVersionTimestamp;
import static com.ge.onchron.verif.utils.ReplaceUtils.replaceDatePlaceholders;
import static com.ge.onchron.verif.utils.ReplaceUtils.replaceIdPlaceholders;
import static com.ge.onchron.verif.utils.ReplaceUtils.replaceStoredId;
import static com.ge.onchron.verif.utils.RestUtils.sendRequest;
import static com.ge.onchron.verif.utils.UserCredentialsUtils.getDefaultUser;
import static com.ge.onchron.verif.utils.Utils.waitMillis;
import static com.ge.onchron.verif.utils.Utils.waitingTimeExpired;
import static com.ge.onchron.verif.utils.oauth2.AuthorizationCodeFlowUtils.login;
import static com.ge.onchron.verif.utils.oauth2.ClientCredentialUtils.getMultiServiceToken;
import static net.serenitybdd.core.Serenity.setSessionVariable;

public class PatientDataSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger( PatientDataSteps.class );

    public static final String DATE_PATTERN_IN_FHIR_RESOURCE = "yyyy-MM-dd";
    public static final int MAX_NUM_OF_RESOURCES_PER_BUNDLE = 160;
    public static final int MAX_WAITING_TIME_FOR_POST_PROCESSED_DATA_IN_SEC = 600;
    public static final int MAX_WAITING_TIME_FOR_RESOURCE_AVAILABILITY_IN_MSEC = 300 * 1_000;
    public static final String POST_PROCESSED_METADATA_PROPERTY_PATH = "meta.extension.url";
    public static final int MAX_BUNDLE_SIZE_BYTES = 4 * 1024 * 1024;

    private static final int PATIENT_BUNDLE_UPLOAD_TIMEOUT_IN_SEC = 1000;

    @Steps
    private RestApiSteps restApiSteps;

    @Steps
    private SfnCommonSteps sfnCommonSteps;

    @Steps
    private DataQuerySteps dataQuerySteps;


    public void deleteResourcesFromPds( List<String> resourceFilePaths ) {
        resourceFilePaths.forEach( resourceFilePath -> {
            String resourceString = readFromFile( TEST_DATA_CLASSPATH + resourceFilePath );
            JsonObject resourceJson = new Gson().fromJson( resourceString, JsonObject.class );
            String resourceType = resourceJson.get( "resourceType" ).getAsString();
            String resourceId = resourceJson.get( "id" ).getAsString();

            // TODO: revert this!!! This is just for investigation
            getResourceFromSource( resourceType, resourceId );

            // Delete only the selected resource in cloud
            deleteResource( resourceType, resourceId );

            verifyResourceIsNotUploaded( resourceType, resourceId );
        } );
    }

    private void deleteResource( String resourceType, String resourceId ) {
        RequestSpecification rs = new RequestSpecBuilder()
                .setBaseUri( getSystemParameter( FHIR_SERVER_URL_PROPERTY ) )
                .setBasePath( "/" + resourceType + "/" + resourceId )
                .build();
        setTokenToUploadBundleRequest( rs );
        Response response = sendRequest( new RestRequest( Method.DELETE, rs ) );
        assertThat( "Response of deleting resource from PDS is not ok.",
                response.getStatusCode(), isOneOf( HttpStatus.SC_NO_CONTENT, HttpStatus.SC_NOT_FOUND ) );
    }

    public void deleteResourcesWithPatientReferenceId( String resourceType, String patientReferenceId ) {
        // Get relevant resources
        RequestSpecification rs = new RequestSpecBuilder()
                .setConfig( RestAssured.config()
                                       .encoderConfig( RestAssured.config()
                                                                  .getEncoderConfig()
                                                                  .appendDefaultContentCharsetToContentTypeIfUndefined( false ) ) )
                .setBaseUri( getSystemParameter( FHIR_SERVER_URL_PROPERTY ) )
                .setBasePath( resourceType )
                .addQueryParam( "subject", String.format( "Patient/%s", patientReferenceId ) )
                .build();
        setTokenToUploadBundleRequest( rs );
        Response response = sendRequest( new RestRequest( Method.GET, rs ) );

        // Get resource IDs
        List<String> resourceIds = response.jsonPath().getList( "entry.resource.id" );

        // Delete resources
        if ( resourceIds != null && !resourceIds.isEmpty() ) {
            resourceIds.forEach( resourceId -> deleteResource( resourceType, resourceId ) );
        }

    }

    public void verifyResourceIsNotUploaded( String resourceType, String resourceId ) {
        Response response = getResourceFromSource( resourceType, resourceId );
        assertThat( "Checking that resource is not uploaded to PDS is failed.",
                response.getStatusCode(), isOneOf( HttpStatus.SC_GONE, HttpStatus.SC_NOT_FOUND ) );
    }

    public boolean resourceIsUploaded( String resourceType, String resourceId ) {
        return getResourceFromSource( resourceType, resourceId ).getStatusCode() == HttpStatus.SC_OK;
    }

    public Response getResourceFromSource( String resourceType, String resourceId ) {
        RequestSpecification rs = new RequestSpecBuilder()
                .setBaseUri( getSystemParameter( FHIR_SERVER_URL_PROPERTY ) )
                .setBasePath( "/" + resourceType + "/" + resourceId )
                .build();
        setTokenToUploadBundleRequest( rs );
        Response response = sendRequest( new RestRequest( Method.GET, rs ) );
        LOGGER.info( "Response status code: {}", response.getStatusCode() );
        return response;
    }

    public void uploadSingleResource( String patientResourceFilePath, String resourceFilePath, boolean withWait ) {
        String patientResourceString = readFromFile( TEST_DATA_CLASSPATH + patientResourceFilePath );
        JsonObject patientResourceJson = new Gson().fromJson( patientResourceString, JsonObject.class );
        String resourceString = readFromFile( TEST_DATA_CLASSPATH + resourceFilePath );
        JsonObject resourceJson = new Gson().fromJson( resourceString, JsonObject.class );

        String patientResourceId = patientResourceJson.get( "id" ).getAsString();
        String resourcePatientRef = resourceJson.getAsJsonObject( "subject" ).get( "reference" ).getAsString();
        LOGGER.info( "Patient resource ref: {}", resourcePatientRef );
        assertTrue( "Patient ID does not match in patient and event resource", resourcePatientRef.contains( patientResourceId ) );

        uploadSingleResourceJson( patientResourceJson, resourceJson, withWait );
    }

    private void uploadSingleResourceJson( JsonObject patientResourceJson, JsonObject resourceJson, boolean withWait ) {
        JsonObject bundle = new JsonObject();

        // bundle properties
        bundle.addProperty( "resourceType", "Bundle" );
        bundle.addProperty( "type", "batch" );

        // prepare entry based on the patient resource json
        JsonObject patientResource = new JsonObject();
        patientResource.add( "resource", patientResourceJson );
        prepareEntry( patientResource );

        // prepare entry based on the event resource json
        JsonObject resource = new JsonObject();
        resource.add( "resource", resourceJson );
        prepareEntry( resource );

        JsonArray entries = new JsonArray();
        entries.add( new Gson().toJsonTree( patientResource ) );
        entries.add( new Gson().toJsonTree( resource ) );
        bundle.add( "entry", entries );

        uploadBundle( bundle );
        if ( withWait ) {
            waitForPostProcess( bundle.size(), patientResourceJson.get( "id" ).getAsString() );
        }

    }

    public void uploadPatientJacketFromFile( String resourceFilePath ) {
        String resourceBundle = readFromFile( TEST_DATA_CLASSPATH + resourceFilePath );
        uploadPatientJacket( resourceBundle );
        waitForPostProcess( resourceBundle.length(), JsonUtils.getFieldValue( resourceBundle, "entry[0].resource.id" ) );
    }

    public void uploadPatientJacketFromFileIfNotPresent( String resourceFilePath ) {
        String resourceBundle = readFromFile( TEST_DATA_CLASSPATH + resourceFilePath );
        JsonObject patientResourceJson = new Gson().fromJson( resourceBundle, JsonObject.class )
                                                   .get( "entry" ).getAsJsonArray().get( 0 ).getAsJsonObject()
                                                   .get( "resource" ).getAsJsonObject();
        if ( !resourceIsUploaded( patientResourceJson.get( "resourceType" ).getAsString(), patientResourceJson.get( "id" ).getAsString() ) ) {
            uploadPatientJacket( resourceBundle );
            waitForPostProcess( resourceBundle.length(), JsonUtils.getFieldValue( resourceBundle, "entry[0].resource.id" ) );
        }
    }

    public void uploadPatientJacketFromFileIfNotInFulfillment( List<String> dataQuery, String resourceFilePath ) {
        String resourceBundle = readFromFile( TEST_DATA_CLASSPATH + resourceFilePath );
        JsonObject patientResourceJson = new Gson().fromJson( resourceBundle, JsonObject.class )
                                                   .get( "entry" ).getAsJsonArray().get( 0 ).getAsJsonObject()
                                                   .get( "resource" ).getAsJsonObject();

        if ( resourceNotExistsInFulfillment( dataQuery, patientResourceJson.get( "id" ).getAsString() ) ) {
            uploadPatientJacket( resourceBundle );
            waitForPostProcess( resourceBundle.length(), JsonUtils.getFieldValue( resourceBundle, "entry[0].resource.id" ) );
        }
    }

    private boolean resourceNotExistsInFulfillment( List<String> dataQuery, String patientID ) {
        dataQuerySteps.executeDataQuery( dataQuery, patientID );
        Response response = restApiSteps.getLastResponse();
        return response.jsonPath().getList( "results[0].entries" ).isEmpty();
    }

    public void uploadPatientJacketFromFileWithNoFixedWait( String resourceFilePath ) {
        String resourceBundle = readFromFile( TEST_DATA_CLASSPATH + resourceFilePath );
        uploadPatientJacket( resourceBundle );
    }

    public void uploadResourceFromFile( String resourceFilePath ) {
        uploadResourceFromFile( resourceFilePath, true );
    }

    public void uploadResourceFromFile( String resourceFilePath, boolean wait ) {
        String resourceBundle = readFromFile( TEST_DATA_CLASSPATH + resourceFilePath );
        uploadPatientJacket( resourceBundle );
        if ( wait ) {
            waitForPostProcess( resourceBundle.length(), JsonUtils.getFieldValue( resourceBundle, "entry[0].resource.id" ) );
        }
    }

    public void deletePatientJacketFromFile( String resourceFilePath ) {
        String resourceBundle = readFromFile( TEST_DATA_CLASSPATH + resourceFilePath );
        resourceBundle = resourceBundle.replaceAll( "PUT", "DELETE" );
        uploadBundle( resourceBundle );
    }

    public void prepareDateParamsAndUploadPatientJacket( String resourceFilePath ) {
        String bundleString = readFromFile( TEST_DATA_CLASSPATH + resourceFilePath );
        bundleString = replaceDatePlaceholders( bundleString, DATE_PATTERN_IN_FHIR_RESOURCE );
        LOGGER.info( STR."Upload patient jacket bundle \{TextUtils.limitString( bundleString, 1000 )}" );
        uploadPatientJacket( bundleString );
        waitForPostProcess( bundleString.length(), JsonUtils.getFieldValue( bundleString, "entry[0].resource.id" ) );
    }

    public void prepareIdsAndUploadPatientJacket( String resourceFilePath ) {
        String bundleString = readFromFile( TEST_DATA_CLASSPATH + resourceFilePath );
        bundleString = replaceIdPlaceholders( bundleString );
        setSessionVariable( PS_CREATED_PATIENT_BUNDLE ).to( bundleString );
        LOGGER.info( STR."Upload patient jacket bundle \{bundleString}" );
        uploadPatientJacket( bundleString );
        waitForPostProcess( bundleString.length(), JsonUtils.getFieldValue( bundleString, "entry[0].resource.id" ) );
    }

    public void waitForPostProcess( int bundleLength, String patientId ) {
        int baseTime = 120000; // just to be on the safe side. It's max time, if ETL finishes earlier it's not going to reach this.
        float waitMultiplier = 5.5f;
        int maxWaitTime = Math.round( baseTime + bundleLength * waitMultiplier );
        // Sometimes process starts after up to 2 minutes hence the increased start timeout
        sfnCommonSteps.waitForSfnFinish( true, maxWaitTime, 180000, ExecutionStatus.SUCCEEDED,
                Map.of( "parameters.patientId", patientId ), 1, false, "fulfill-etl" );
    }

    private void uploadPatientJacket( String bundleString ) {
        JsonObject fullPatientBundle = new Gson().fromJson( bundleString, JsonObject.class );

        JsonObject bundleToSend = new JsonObject();
        bundleToSend.addProperty( "resourceType", "Bundle" );
        bundleToSend.addProperty( "type", "batch" );
        bundleToSend.add( "entry", new JsonArray() );

        Map<String, String> failedToUploadResources = new HashMap<>();
        Iterator<JsonElement> iterator = fullPatientBundle.getAsJsonArray( "entry" ).iterator();
        while ( iterator.hasNext() ) {
            JsonElement jsonElement = iterator.next();
            prepareEntry( jsonElement.getAsJsonObject() );

            JsonArray entries = bundleToSend.getAsJsonArray( "entry" );
            entries.add( new Gson().toJsonTree( jsonElement ) );
            bundleToSend.add( "entry", entries );
            long size = bundleToSend.toString().getBytes().length;
            if ( bundleToSend.getAsJsonArray( "entry" ).size() == MAX_NUM_OF_RESOURCES_PER_BUNDLE
                    || size >= MAX_BUNDLE_SIZE_BYTES || !iterator.hasNext() ) {
                try {
                    JsonArray resourceResponses = uploadBundle( bundleToSend );
                    Type listType = new TypeToken<List<JsonElement>>() {
                    }.getType();
                    List<JsonElement> receivedResourceResponseList = new Gson().fromJson( resourceResponses, listType );
                    List<JsonElement> sentResourcesList = new Gson().fromJson( bundleToSend.getAsJsonArray( "entry" ), listType );

                    for ( int i = 0; i < receivedResourceResponseList.size(); i++ ) {
                        String responseStatusCode = receivedResourceResponseList.get( i ).getAsJsonObject().get( "response" ).getAsJsonObject().get( "status" ).getAsString();
                        if ( !responseStatusCode.contains( "200" ) && !responseStatusCode.contains( "201" ) && !responseStatusCode.contains( "304" ) ) { // On-prem: "200 OK" or "304 Not Modified", Cloud: "200"
                            JsonElement errorJson = receivedResourceResponseList.get( i ).getAsJsonObject().get( "response" ).getAsJsonObject().get( "outcome" );
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            String prettyJsonString = gson.toJson( errorJson );
                            // Get ID of resource which was failed to upload
                            String resourceId = sentResourcesList.get( i ).getAsJsonObject().get( "resource" ).getAsJsonObject().get( "id" ).getAsString();
                            failedToUploadResources.put( resourceId, prettyJsonString );
                        }
                    }

                } catch ( Exception e ) {
                    LOGGER.error( STR."Failed to upload patient bundle: \{e.getMessage()}" );
                    fail( String.format( "Failed to upload patient bundle.\nError:\n%s", e ) );
                }
                bundleToSend.add( "entry", new JsonArray() );
            }
        }
        assertTrue( "Not all patient resources were uploaded to PDS: " + failedToUploadResources, failedToUploadResources.isEmpty() );
    }

    private void prepareEntry( JsonObject entry ) {
        JsonObject resourceObj = entry.getAsJsonObject().getAsJsonObject( "resource" );
        JsonObject request = new JsonObject();
        request.addProperty( "method", "PUT" );
        String resourceUrl = resourceObj.get( "resourceType" ).getAsString() + "/" + resourceObj.get( "id" ).getAsString();
        request.addProperty( "url", resourceUrl );
        entry.add( "request", request );
        entry.remove( "fullUrl" );
        entry.addProperty( "fullUrl", getSystemParameter( FHIR_SERVER_URL_PROPERTY ) + "/" + resourceUrl );
    }

    public JsonArray uploadBundle( String payload ) {
        SSLConfig sslConfig = new SSLConfig().with().relaxedHTTPSValidation().and().allowAllHostnames();
        RequestSpecification rs = new RequestSpecBuilder()
                .setBaseUri( getSystemParameter( DCC_URL_PROPERTY ) )
                .setBody( payload )
                .setContentType( ContentType.JSON )
                .setConfig( RestAssured.config()
                                       .sslConfig( sslConfig )
                                       .httpClient( HttpClientConfig.httpClientConfig()
                                                                    .setParam( CONNECTION_TIMEOUT, PATIENT_BUNDLE_UPLOAD_TIMEOUT_IN_SEC * 1000 )
                                                                    .setParam( SO_TIMEOUT, PATIENT_BUNDLE_UPLOAD_TIMEOUT_IN_SEC * 1000 ) ) )
                .build();
        setTokenToUploadBundleRequest( rs );
        Response response = sendRequest( new RestRequest( Method.POST, rs ) );
        LOGGER.info( "uploaded bundle status code: {}", response.getStatusCode() );
        assertThat( "Failed to upload patient bundle.", response.getStatusCode(), is( equalTo( HttpStatus.SC_OK ) ) );
        JsonArray entry = null;
        try {
            entry = new Gson().fromJson( response.asString(), JsonObject.class ).getAsJsonArray( "entry" );
        } catch ( Exception e ) {
            fail( String.format( "Failed to convert response to json, response body: %s", response.asString() ) );
        }
        return entry;
    }

    private JsonArray uploadBundle( JsonObject jsonObject ) {
        return uploadBundle( jsonObject.toString() );
    }

    private void setTokenToUploadBundleRequest( RequestSpecification rs ) {
        Token token;
        if ( getSystemParameter( DCC_URL_PROPERTY ).contains( "proxy" ) ) {
            token = login( getDefaultUser() );
        } else {
            token = new Token();
            token.setAccessToken( getMultiServiceToken( getSystemParameter( FHIR_CLIENT_ID ),
                    getSystemParameter( FHIR_CLIENT_SECRET ), getSystemParameter( FHIR_SCOPE ), null ) );
        }
        rs.auth().preemptive().oauth2( token.getAccessToken() );
    }

    public String saveLatestVersionTimestamp( String versionType, String patientId ) {
        String latestVersionTimestamp = getLatestVersionTimestamp( patientId );
        LOGGER.info( String.format( "The \"%s\" timestamp of patient %s is %s", versionType, patientId, latestVersionTimestamp ) );
        setSessionVariable( versionType ).to( latestVersionTimestamp );
        return latestVersionTimestamp;
    }

    public String getPatientResourcesFromDpsa( String patientId ) {
        RequestSpecification rs = new RequestSpecBuilder()
                .setUrlEncodingEnabled( false )
                .setBaseUri( getSystemParameter( FHIR_SERVER_URL_PROPERTY ) )
                .setBasePath( STR."/Patient/\{patientId}/$everything" )
                .build();
        setTokenToUploadBundleRequest( rs );
        Response response = sendRequest( new RestRequest( Method.GET, rs ) );
        LOGGER.info( STR."Response status code: \{response.getStatusCode()}" );
        assertThat( "Failed to get patient resources", response.getStatusCode(), is( 200 ) );
        return response.asString();
    }

    public void compareWithStoredData( String resourceFilePath ) {
        String expected = readFromFile( TEST_DATA_CLASSPATH + resourceFilePath );
        String patientId = JsonUtils.getFieldValue( expected, "entry[0].resource.id" );
        LOGGER.info( STR."Comparing patient's \{patientId} data with DPSA." );
        String dpsaPatient = getPatientResourcesFromDpsa( patientId );
        JSONAssert.assertEquals( removeVariableFields( expected ),
                removeVariableFields( dpsaPatient ), false );
        LOGGER.info( "No differences found." );
    }

    private String removeVariableFields( String bundle ) {
        bundle = JsonUtils.setFieldValue( bundle, "type", null );
        bundle = JsonUtils.setFieldValue( bundle, "total", null );
        int count = StringUtils.countMatches( bundle, "\"resourceType\"" );
        for ( int i = 0; i < count; i++ ) {
            bundle = JsonUtils.setFieldValue( bundle, STR."entry[\{i}].fullUrl", null );
            bundle = JsonUtils.setFieldValue( bundle, STR."entry[\{i}].search", null );
            bundle = JsonUtils.setFieldValue( bundle, STR."entry[\{i}].request", null );
            bundle = JsonUtils.setFieldValue( bundle, STR."entry[\{i}].resource.meta", null );
        }
        return bundle;
    }

    public void waitForPostProcessingData( String requiredPostProcessingType, List<String> resourcePaths ) {
        long startTime = System.currentTimeMillis();
        boolean waitingTimeExpired;
        do {
            ListIterator<String> iterator = resourcePaths.listIterator();
            while ( iterator.hasNext() ) {
                String resourcePath = iterator.next();
                Response response = getResource( resourcePath );
                assertEquals( "Response of getting resource from DPSA is not ok.", HttpStatus.SC_OK, response.getStatusCode() );
                List<String> extensionUrls = response.jsonPath().getList( POST_PROCESSED_METADATA_PROPERTY_PATH );
                LOGGER.info( String.format( "extensionUrls in the resource: %s", extensionUrls ) );
                long resourcePathFound = 0;
                if ( extensionUrls != null ) {
                    resourcePathFound = extensionUrls.stream()
                                                     .filter( extensionUrl -> extensionUrl.contains( requiredPostProcessingType ) )
                                                     .count();
                }
                if ( resourcePathFound > 0 ) {
                    iterator.remove();
                    resourcePaths.remove( resourcePath );
                }
            }
            waitingTimeExpired = waitingTimeExpired( startTime, MAX_WAITING_TIME_FOR_POST_PROCESSED_DATA_IN_SEC * 1000 );
        } while ( !resourcePaths.isEmpty() && !waitingTimeExpired );
        assertTrue( String.format( "The required \"%s\" post-processing data not generated into the following resources: %s",
                requiredPostProcessingType, resourcePaths ), resourcePaths.isEmpty() );
    }

    private Response getResource( String resourcePath ) {
        RequestSpecification rs = new RequestSpecBuilder()
                .setBaseUri( getSystemParameter( FHIR_SERVER_URL_PROPERTY ) )
                .setBasePath( resourcePath )
                .build();
        setTokenToUploadBundleRequest( rs );
        return sendRequest( new RestRequest( Method.GET, rs ) );
    }

    public void checkResourceAvailabilityWithCategory( int numberOfRequiredResources, String docType, String category, String patientId ) {
        patientId = replaceStoredId( patientId );

        long startTime = System.currentTimeMillis();
        boolean waitingTimeExpired;
        int totalResources = -1;

        if ( numberOfRequiredResources == 0 ) {
            // Ensure no resources are generated within the max waiting time.
            do {
                if ( totalResources != -1 ) {
                    waitMillis( MAX_WAITING_TIME_FOR_RESOURCE_AVAILABILITY_IN_MSEC / 30 );
                }
                RequestSpecification rs = new RequestSpecBuilder()
                        .setBaseUri( getSystemParameter( FHIR_SERVER_URL_PROPERTY ) )
                        .setBasePath( docType )
                        .addQueryParam( "subject", String.format( "Patient/%s", patientId ) )
                        .addQueryParam( "category", category )
                        .build();
                setTokenToUploadBundleRequest( rs );
                Response response = sendRequest( new RestRequest( Method.GET, rs ) );

                totalResources = response.jsonPath().getInt( "total" );

                LOGGER.info( String.format( "Total number of resources in the response: %s", totalResources ) );

                if ( totalResources > 0 ) {
                    fail( String.format( "%s resource(s) were generated with category %s for patient with id %s before timeout. Expected 0 but found %d",
                            docType, category, patientId, totalResources ) );
                }

                waitingTimeExpired = waitingTimeExpired( startTime, MAX_WAITING_TIME_FOR_RESOURCE_AVAILABILITY_IN_MSEC );
            } while ( !waitingTimeExpired );

            // Final assertion to ensure still zero after timeout
            assertEquals( String.format( "%s resource was generated with category %s for patient with id %s", docType, category, patientId ),
                    0, totalResources );
        } else {
            // Original behavior: wait until required number appears or timeout
            do {
                if ( totalResources != -1 ) {
                    waitMillis( MAX_WAITING_TIME_FOR_RESOURCE_AVAILABILITY_IN_MSEC / 30 );
                }
                RequestSpecification rs = new RequestSpecBuilder()
                        .setBaseUri( getSystemParameter( FHIR_SERVER_URL_PROPERTY ) )
                        .setBasePath( docType )
                        .addQueryParam( "subject", String.format( "Patient/%s", patientId ) )
                        .addQueryParam( "category", category )
                        .build();
                setTokenToUploadBundleRequest( rs );
                Response response = sendRequest( new RestRequest( Method.GET, rs ) );

                totalResources = response.jsonPath().getInt( "total" );

                LOGGER.info( String.format( "Total number of resources in the response: %s", totalResources ) );
                waitingTimeExpired = waitingTimeExpired( startTime, MAX_WAITING_TIME_FOR_RESOURCE_AVAILABILITY_IN_MSEC );
            } while ( totalResources < numberOfRequiredResources && !waitingTimeExpired );
            assertEquals( String.format( "%s resource was not generated with category %s for patient with id %s", docType, category, patientId ), numberOfRequiredResources, totalResources );
        }
    }
}
