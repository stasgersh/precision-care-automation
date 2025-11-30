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

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;

import com.ge.onchron.verif.model.RestRequest;
import com.ge.onchron.verif.model.UserCredentials;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;

import static com.ge.onchron.verif.TestSystemParameters.API_BASE_URL_PROPERTY;
import static com.ge.onchron.verif.TestSystemParameters.CORE_API_BASE_PATH_PROPERTY;
import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;
import static com.ge.onchron.verif.utils.ReplaceUtils.replaceCustomParameters;
import static com.ge.onchron.verif.utils.UserCredentialsUtils.getDefaultUser;

public class LabelApiSteps {

    public static final String LABELS_ENDPOINT_PATH = "/labels";
    public static final String CREATE_LABEL_PAYLOAD_SKELETON = "{ \"text\": \"%s\", \"paletteColor\":0}";

    @Steps
    private RestApiSteps restApiSteps;

    /**
     * Create labels via API
     * Labels are created with the first (default) color
     *
     * @param requiredLabelNames list
     */
    public void createLabels( List<String> requiredLabelNames ) {
        UserCredentials defaultUser = getDefaultUser();
        JsonArray availableLabels = getLabelsList( defaultUser );

        List<String> availableLabelNames = new ArrayList<>();
        for ( JsonElement availableLabel : availableLabels ) {
            String labelName = availableLabel.getAsJsonObject().get( "text" ).getAsString();
            availableLabelNames.add( labelName );
        }

        requiredLabelNames.forEach( requiredLabelName -> {
            requiredLabelName = replaceCustomParameters( requiredLabelName );
            if ( !availableLabelNames.contains( requiredLabelName ) ) {
                createLabel( defaultUser, requiredLabelName );
            }
        } );
    }

    private void createLabel( UserCredentials user, String labelName ) {
        RequestSpecBuilder rsb = new RequestSpecBuilder();
        rsb.setBaseUri( getSystemParameter( API_BASE_URL_PROPERTY ) );
        rsb.setBasePath( getSystemParameter( CORE_API_BASE_PATH_PROPERTY ) + LABELS_ENDPOINT_PATH );
        rsb.setContentType( ContentType.JSON );
        rsb.setBody( String.format( CREATE_LABEL_PAYLOAD_SKELETON, labelName ) );
        restApiSteps.sendAuthenticatedRequest( user, new RestRequest( Method.POST, rsb.build() ) );
        restApiSteps.checkResponseStatusCode( HttpStatus.SC_CREATED );
    }

    protected JsonArray getLabelsList( UserCredentials user ) {
        RequestSpecBuilder rsb = new RequestSpecBuilder();
        rsb.setBaseUri( getSystemParameter( API_BASE_URL_PROPERTY ) );
        rsb.setBasePath( getSystemParameter( CORE_API_BASE_PATH_PROPERTY ) + LABELS_ENDPOINT_PATH );
        rsb.setContentType( ContentType.JSON );
        Response response = restApiSteps.sendAuthenticatedRequest( user, new RestRequest( Method.GET, rsb.build() ) );
        restApiSteps.checkResponseStatusCode( HttpStatus.SC_OK );
        return new Gson().fromJson( response.asString(), JsonArray.class );
    }

}
