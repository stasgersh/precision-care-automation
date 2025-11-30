/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2025, 2025, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ge.onchron.verif.howsteps.RestApiSteps;
import com.ge.onchron.verif.model.RestRequest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Method;
import io.restassured.response.Response;
import java.util.Iterator;
import java.util.Map;

import static com.ge.onchron.verif.TestSystemParameters.ORIGINAL_CONFIGS;
import static com.ge.onchron.verif.utils.Utils.waitMillis;
import static net.serenitybdd.core.Serenity.sessionVariableCalled;
import static net.serenitybdd.core.Serenity.setSessionVariable;

public class ConfigUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger( ConfigUtils.class );

    public static void restoreChangedConfiguration() {
        Map<String, String> originalConfigs = sessionVariableCalled( ORIGINAL_CONFIGS );
        if ( originalConfigs != null ) {
            RestApiSteps restApiSteps = new RestApiSteps();
            Iterator<Map.Entry<String, String>> iterator = originalConfigs.entrySet().iterator();
            while ( iterator.hasNext() ) {
                Map.Entry<String, String> entry = iterator.next();
                String uri = entry.getKey();
                String originalConfig = entry.getValue();
                LOGGER.info( "Restoring configuration for: {} to its original value.", uri );

                RequestSpecBuilder rsb = new RequestSpecBuilder()
                        .setBaseUri( uri )
                        .setContentType( "application/json" )
                        .setBody( originalConfig );

                Response response = restApiSteps.sendBackendAuthenticatedRequest( new RestRequest( Method.PUT, rsb.build() ) );
                if ( response != null && response.getStatusCode() >= 200 && response.getStatusCode() < 300 ) {
                    LOGGER.info( "Successfully restored configuration for: {}", uri );
                    iterator.remove(); // Remove restored configuration from the map
                } else {
                    LOGGER.error( "Failed to restore configuration for : {}. Status: {}, Body: {}",
                            uri,
                            response != null ? response.getStatusCode() : "null",
                            response != null ? response.getBody().asString() : "null"
                    );
                }
            }
            setSessionVariable( ORIGINAL_CONFIGS ).to( originalConfigs );
        }
    }

    private static void waitAfterConfigUpdate( int waitTime ) {
        // TODO: Refactor with dynamic wait based on configType
        LOGGER.info( "Waiting {} ms after config update", waitTime );
        waitMillis( waitTime );
    }

}

