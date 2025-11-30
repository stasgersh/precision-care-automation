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
 * forbidden except by express written permission of GE HealthCare.
 */
package com.ge.onchron.verif.howsteps;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.RestRequest;
import com.ge.onchron.verif.model.UserCredentials;
import com.ge.onchron.verif.utils.PatientUtils;
import com.ge.onchron.verif.utils.PatientUtils.PATIENT_SETTINGS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import net.thucydides.core.annotations.Steps;

import static com.ge.onchron.verif.TestSystemParameters.API_BASE_URL_PROPERTY;
import static com.ge.onchron.verif.TestSystemParameters.CORE_API_BASE_PATH_PROPERTY;
import static com.ge.onchron.verif.TestSystemParameters.TEST_DATA_CLASSPATH;
import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;
import static com.ge.onchron.verif.utils.FileUtils.readFromFile;
import static com.ge.onchron.verif.utils.PatientUtils.getPatientId;


public class PatientSettingsSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger( PatientSettingsSteps.class );
    public static final String PATIENT_ENDPOINT_PATH = "/patients";
    public static final String SETTINGS_ENDPOINT_PATH = "/settings";


    @Steps
    private RestApiSteps restApiSteps;

    public void resetPatientSettings( UserCredentials userCredentials, String settings, String patientName ) {
        String patientId = getPatientId( patientName );
        switch ( settings.toLowerCase() ) {
            case "patient banner":
                PatientUtils.resetPatientSettings( userCredentials, patientId, PATIENT_SETTINGS.PATIENT_BANNER );
                break;
            case "timeline":
                PatientUtils.resetPatientSettings( userCredentials, patientId, PATIENT_SETTINGS.TIMELINE );
                break;
            case "treatment response":
                PatientUtils.resetPatientSettings( userCredentials, patientId, PATIENT_SETTINGS.TREATMENT_RESPONSE );
                break;
            case "trends":
                PatientUtils.resetPatientSettings( userCredentials, patientId, PATIENT_SETTINGS.TRENDS );
                break;
            case "summary":
                PatientUtils.resetPatientSettings( userCredentials, patientId, PATIENT_SETTINGS.SUMMARY );
                break;
            default:
                fail( "No enum constant with value: " + settings );
        }
    }

    public void setSummaryLayout( String patientName, UserCredentials user, String settingFilePath ) {
        String patientSetting = readFromFile( TEST_DATA_CLASSPATH + settingFilePath );
        LOGGER.info( "Applying patient setting to {}", patientSetting );

        setPatientSetting( patientName, user, new Gson().fromJson( patientSetting, JsonObject.class ) );
    }

    public void hideEmptyCards(  String patientName, UserCredentials user, Boolean hide ) {
        LOGGER.info( "Hide empty cards: {}", hide );

        JsonObject settings = new JsonObject();
        JsonObject summary = new JsonObject();
        summary.addProperty( "hideEmptySummaryCards", hide );
        settings.add( "summary", summary );

        setPatientSetting( patientName, user, settings );
    }

    public void resetCommentSettings( String patientName, UserCredentials user ) {
        LOGGER.info( "Resetting comment settings for patient {}", patientName );

        JsonObject settings = new JsonObject();
        JsonObject comments = new JsonObject();
        comments.addProperty( "hideTimelineComments", false );
        settings.add( "comments", comments );

        setPatientSetting( patientName, user, settings );
    }

    public void resetTrialsSettings( String patientName, UserCredentials user ) {
        LOGGER.info( "Resetting trials settings for patient {}", patientName );

        JsonObject trialsEligibility = new JsonObject();
        trialsEligibility.add("treatmentEligibilityId", JsonNull.INSTANCE);
        trialsEligibility.add("filters", JsonNull.INSTANCE);
        trialsEligibility.add("sortOptions", JsonNull.INSTANCE);

        JsonObject trials = new JsonObject();
        trials.add("trialsEligibility", trialsEligibility);

        setPatientSetting( patientName, user, trials );
    }

    private void setPatientSetting( String patientName, UserCredentials user, JsonObject content ) {
        RequestSpecBuilder rsb = new RequestSpecBuilder();
        rsb.setBaseUri( getSystemParameter( API_BASE_URL_PROPERTY ) );
        rsb.setBasePath( getSystemParameter( CORE_API_BASE_PATH_PROPERTY ) + PATIENT_ENDPOINT_PATH + "/" + getPatientId( patientName) + SETTINGS_ENDPOINT_PATH );
        rsb.setContentType( ContentType.JSON );
        rsb.setBody( new GsonBuilder().serializeNulls().create().toJson( content ) );
        restApiSteps.sendAuthenticatedRequest( user, new RestRequest( Method.PATCH, rsb.build() ) );
        restApiSteps.checkResponseStatusCode( HttpStatus.SC_NO_CONTENT );

        LOGGER.info( "Successfully updated settings for patient {}", patientName );
    }
}
