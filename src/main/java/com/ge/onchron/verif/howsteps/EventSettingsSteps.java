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

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.ge.onchron.verif.model.RestRequest;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import net.thucydides.core.annotations.Steps;

import static com.ge.onchron.verif.TestSystemParameters.API_BASE_URL_PROPERTY;
import static com.ge.onchron.verif.TestSystemParameters.CORE_API_BASE_PATH_PROPERTY;
import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;
import static com.ge.onchron.verif.utils.PatientUtils.getEventIdFromExampleTableRow;
import static com.ge.onchron.verif.utils.PatientUtils.getLatestVersionTimestamp;
import static com.ge.onchron.verif.utils.Utils.waitMillis;

public class EventSettingsSteps {

    public static final String TIMELINE_ENDPOINT_PATH = "%s/patients/%s/timeline";
    public static final String EVENT_SETTINGS_ENDPOINT_PATH = "%s/patients/%s/events/%s/settings";

    public static final String PATIENT_ID_COLUMN = "patientID";

    public static final String EVENTS_PROPERTY_NAME = "items";
    public static final String ID_PROPERTY_NAME = "id";
    public static final String SETTINGS_PROPERTY_NAME = "settings";
    public static final String LABELS_PROPERTY_NAME = "labels";
    public static final String LABEL_TEXT_PROPERTY_NAME = "text";
    public static final String IMPORTANT_PROPERTY_NAME = "important";

    @Steps
    private RestApiSteps restApiSteps;
    @Steps
    private LabelApiSteps labelApiSteps;

    private static final Logger LOGGER = LoggerFactory.getLogger( RestApiSteps.class );

    public void markOrUnmarkEventImportantForUser( boolean isMarked, UserCredentials user, List<Map<String, String>> expectedEventsList ) {
        expectedEventsList.forEach( event -> {
            String patientId = event.get( PATIENT_ID_COLUMN );
            String eventId = getEventIdFromExampleTableRow( patientId, event );
            markOrUnmarkImportantSingleEvent( user, patientId, eventId, isMarked );
            waitMillis( 200 );
        } );
    }

    private void markOrUnmarkImportantSingleEvent( UserCredentials user, String patientId, String eventIdWithType, boolean isMarked ) {
        JsonObject eventSettings = getEventSettings( user, patientId, eventIdWithType );
        eventSettings.addProperty( IMPORTANT_PROPERTY_NAME, isMarked );
        LOGGER.info( STR."Setting event: \{eventIdWithType} to marked: \{isMarked}" );
        setEventSettings( user, patientId, eventIdWithType, eventSettings );
    }

    public void clearLabelsForMoreEvents( UserCredentials user, List<Map<String, String>> eventsList ) {
        eventsList.forEach( event -> {
            String patientId = event.get( PATIENT_ID_COLUMN );
            String eventId = getEventIdFromExampleTableRow( patientId, event );
            clearLabelsOfSingleEvent( user, patientId, eventId );
        } );
    }

    private void clearLabelsOfSingleEvent( UserCredentials user, String patientId, String eventIdWithType ) {
        JsonObject eventSettings = getEventSettings( user, patientId, eventIdWithType );
        eventSettings.add( LABELS_PROPERTY_NAME, new JsonArray() );
        setEventSettings( user, patientId, eventIdWithType, eventSettings );
    }

    public void addLabelsToEvents( UserCredentials user, List<Map<String, String>> eventsWithLabels ) {
        eventsWithLabels.forEach( event -> {
            String patientId = event.get( PATIENT_ID_COLUMN );
            String eventId = getEventIdFromExampleTableRow( patientId, event );
            List<String> labels = Arrays.asList( event.get( LABELS_PROPERTY_NAME ).split( ", " ) );
            addLabelsToSingleEvent( user, patientId, eventId, labels );
        } );
    }

    private void addLabelsToSingleEvent( UserCredentials user, String patientId, String eventIdWithType, List<String> requiredLabelNames ) {
        JsonObject eventSettings = getEventSettings( user, patientId, eventIdWithType );
        JsonArray availableLabels = labelApiSteps.getLabelsList( user );

        JsonArray requiredLabels = new JsonArray();

        requiredLabelNames.forEach( requiredLabelName -> {
            boolean requiredLabelFound = false;
            for ( JsonElement availableLabel : availableLabels ) {
                if ( availableLabel.getAsJsonObject().get( LABEL_TEXT_PROPERTY_NAME ).getAsString().equals( requiredLabelName ) ) {
                    requiredLabelFound = true;
                    requiredLabels.add( availableLabel.getAsJsonObject() );
                }
            }
            assertTrue( "Following label is not available: " + requiredLabelName, requiredLabelFound );
        } );

        eventSettings.add( LABELS_PROPERTY_NAME, requiredLabels );
        setEventSettings( user, patientId, eventIdWithType, eventSettings );
    }

    private void setEventSettings( UserCredentials user, String patientId, String eventIdWithType, JsonObject eventSettingsJson ) {
        RequestSpecBuilder rsb = new RequestSpecBuilder();
        rsb.setBaseUri( getSystemParameter( API_BASE_URL_PROPERTY ) );
        rsb.setBasePath( String.format( EVENT_SETTINGS_ENDPOINT_PATH, getSystemParameter( CORE_API_BASE_PATH_PROPERTY ), patientId, eventIdWithType ) );
        rsb.setContentType( ContentType.JSON );
        rsb.setBody( eventSettingsJson.toString() );

        RequestSpecification requestSpecification = rsb.build();
        restApiSteps.sendAuthenticatedRequest( user, new RestRequest( Method.PUT, requestSpecification ) );
        restApiSteps.checkResponseStatusCode( HttpStatus.SC_NO_CONTENT );
    }

    private JsonObject getEventSettings( UserCredentials user, String patientId, String eventIdWithType ) {
        // Get timeline response
        RequestSpecBuilder rsb = new RequestSpecBuilder();
        rsb.setBaseUri( getSystemParameter( API_BASE_URL_PROPERTY ) );
        String timelinePath = String.format( TIMELINE_ENDPOINT_PATH, getSystemParameter( CORE_API_BASE_PATH_PROPERTY ), patientId );
        rsb.setBasePath( timelinePath );
        rsb.addQueryParam( "version", getLatestVersionTimestamp( patientId ) );
        Response response = restApiSteps.sendAuthenticatedRequest( user, new RestRequest( Method.GET, rsb.build() ) );
        restApiSteps.checkResponseStatusCode( HttpStatus.SC_OK );

        // Parse timeline response
        JsonObject responseJson = new Gson().fromJson( response.asString(), JsonObject.class );
        JsonArray eventsArray = responseJson.getAsJsonArray( EVENTS_PROPERTY_NAME );

        // Get event from timeline response
        JsonObject requiredEvent = null;
        for ( JsonElement jsonElement : eventsArray ) {
            if ( jsonElement.getAsJsonObject().get( ID_PROPERTY_NAME ).getAsString().equals( eventIdWithType ) ) {
                requiredEvent = jsonElement.getAsJsonObject();
            }
        }

        // Get event settings
        assertNotNull( "Required event was not found: " + eventIdWithType, requiredEvent );
        return requiredEvent.get( SETTINGS_PROPERTY_NAME ).getAsJsonObject();
    }

}
