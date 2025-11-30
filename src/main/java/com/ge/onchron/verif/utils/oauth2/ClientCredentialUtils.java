/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2025, 2025, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE HealthCare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 */
package com.ge.onchron.verif.utils.oauth2;

import org.apache.http.HttpStatus;
import org.junit.Assert;

import com.ge.onchron.verif.TestSystemParameters;
import com.ge.onchron.verif.model.RestRequest;
import com.ge.onchron.verif.model.Token;
import com.ge.onchron.verif.utils.RestUtils;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static com.ge.onchron.verif.TestSystemParameters.FULFILL_CLIENT_ID;
import static com.ge.onchron.verif.TestSystemParameters.FULFILL_CLIENT_SECRET;
import static com.ge.onchron.verif.TestSystemParameters.FULFILL_SCOPE;
import static com.ge.onchron.verif.TestSystemParameters.MIXED_ACCESS_TOKEN;
import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;
import static com.ge.onchron.verif.utils.oauth2.TokenUtils.isActiveToken;
import static net.serenitybdd.core.Serenity.sessionVariableCalled;
import static net.serenitybdd.core.Serenity.setSessionVariable;

public class ClientCredentialUtils {

    public static Token getMultiServiceAuthorization() {
        Token token = sessionVariableCalled( MIXED_ACCESS_TOKEN );
        if ( isActiveToken( token ) ) {
            return token;
        }
        token = new Token();
        token.setAccessToken( getMultiServiceToken( getSystemParameter( FULFILL_CLIENT_ID ),
                getSystemParameter( FULFILL_CLIENT_SECRET ), getSystemParameter( FULFILL_SCOPE ), null ) );
        setSessionVariable( "service_user_credentials" ).to( STR."\{getSystemParameter( FULFILL_CLIENT_ID )}@precision-insights.app" );
        setSessionVariable( MIXED_ACCESS_TOKEN ).to( token );        
        return token;
    }

    public static String getMultiServiceToken( String clientId, String clientSecret, String scope, String audience ) {
        RequestSpecBuilder rsb = new RequestSpecBuilder()
                .setBaseUri( TestSystemParameters.getSystemParameter( TestSystemParameters.IDAM_URL ) )
                .setBasePath( AuthorizationCodeFlowUtils.TOKEN_PATH )
                .addFormParam( "grant_type", "client_credentials" )
                .addFormParam( "client_id", clientId )
                .addFormParam( "client_secret", clientSecret )
                .addFormParam( "scope", scope );
        if ( audience != null ) {
            rsb.addFormParam( "audience", audience );
        }
        RequestSpecification rs = rsb.build();

        Response response = RestUtils.sendRequest( new RestRequest( Method.POST, rs ) );
        Assert.assertEquals( "The received status code is not ok.", HttpStatus.SC_OK, response.getStatusCode() );
        return response.getBody().jsonPath().getString( "access_token" );
    }

}
