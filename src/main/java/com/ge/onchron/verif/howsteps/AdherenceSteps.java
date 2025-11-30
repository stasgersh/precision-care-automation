/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2024, 2024, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.howsteps;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ge.onchron.verif.model.RestRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Steps;

import static com.ge.onchron.verif.TestSystemParameters.ADHERENCE_SERVICE_URL;
import static com.ge.onchron.verif.TestSystemParameters.ADHERENCE_TREATMENT_PATH;
import static com.ge.onchron.verif.TestSystemParameters.TEST_DATA_CLASSPATH;
import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;
import static com.ge.onchron.verif.utils.FileUtils.readFromFile;

public class AdherenceSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger( AdherenceSteps.class );
    private static final String TREATMENTS_PROPERTY_NAME = "treatments";


    @Steps
    RestApiSteps restApiSteps;

    public JsonArray getTreatmentList() {
        RequestSpecBuilder rsb = new RequestSpecBuilder()
                .setBaseUri( getSystemParameter( ADHERENCE_SERVICE_URL ) )
                .setBasePath( getSystemParameter( ADHERENCE_TREATMENT_PATH ) )
                .setContentType( ContentType.JSON );
        // Temporarily bypassing the 502 issue on the first call
        int maxRetries = 3;
        int attempt = 0;
        Response response;
        do {
            response = restApiSteps.sendBackendAuthenticatedRequest( new RestRequest( Method.GET, rsb.build() ) );
            attempt++;
        } while ( response.getStatusCode() != HttpStatus.SC_OK && attempt < maxRetries );
        restApiSteps.checkResponseStatusCode( HttpStatus.SC_OK );

        JsonElement responseJson = new Gson().fromJson( response.asPrettyString(), JsonElement.class );
        JsonArray treatmentArray = new JsonArray();
        if ( responseJson.isJsonArray() ) {
            treatmentArray = responseJson.getAsJsonArray();
        } else {
            treatmentArray.add( responseJson.getAsJsonObject() );
        }
        return treatmentArray;
    }

    public void uploadTreatment( String resourceFilePath ) {
        String treatmentResourceString = readFromFile( TEST_DATA_CLASSPATH + resourceFilePath );
        JsonObject treatmentResourceJson = new Gson().fromJson( treatmentResourceString, JsonObject.class );

        if ( !isTreatmentAlreadyAvailable( treatmentResourceJson.get( "name" ).getAsString() ) ) {
            RequestSpecification rsb = new RequestSpecBuilder()
                    .setBaseUri( getSystemParameter( ADHERENCE_SERVICE_URL ) )
                    .setBasePath( getSystemParameter( ADHERENCE_TREATMENT_PATH ) )
                    .setBody( treatmentResourceJson.toString() )
                    .setContentType( ContentType.JSON )
                    .build();
            restApiSteps.sendBackendAuthenticatedRequest( new RestRequest( Method.POST, rsb ) );
            restApiSteps.checkResponseStatusCode( HttpStatus.SC_CREATED );
        }

    }

    private boolean isTreatmentAlreadyAvailable( String name ) {
        JsonArray treatmentList = getTreatmentList();
        for ( JsonElement treatment : treatmentList ) {
            JsonObject jsonObject = treatment.getAsJsonObject();
            if ( jsonObject.get( "name" ).getAsString().equals( name ) ) {
                LOGGER.info( STR."\{name} treatment protocol was already available" );
                return true;
            }
        }
        return false;
    }

    public void deleteTreatmentById( String treatmentID ) {
        RequestSpecBuilder rsb = new RequestSpecBuilder()
                .setBaseUri( getSystemParameter( ADHERENCE_SERVICE_URL ) )
                .setBaseUri( getSystemParameter( ADHERENCE_TREATMENT_PATH ) + "/" + treatmentID )
                .setContentType( ContentType.JSON );
        restApiSteps.sendBackendAuthenticatedRequest( new RestRequest( Method.DELETE, rsb.build() ) );
        restApiSteps.checkResponseStatusCode( HttpStatus.SC_NO_CONTENT );
    }
}
