/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2023, 2023, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE HealthCare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.utils.oauth2;

import java.util.Map;

import com.ge.onchron.verif.model.RestRequest;
import com.ge.onchron.verif.model.Token;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static com.ge.onchron.verif.TestSystemParameters.IDAM_URL;
import static com.ge.onchron.verif.TestSystemParameters.INVALIDATE_TOKEN;
import static com.ge.onchron.verif.TestSystemParameters.TOKEN_VALIDATION_MODE;
import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;
import static com.ge.onchron.verif.utils.RestUtils.sendRequest;
import static net.serenitybdd.core.Serenity.sessionVariableCalled;

public class TokenUtils {

    public static Token parseToken( Map<String, String> cookies ) {
        Token token = new Token();
        token.setAccessToken( cookies.get( "access_token" ) );
        token.setRefreshToken( cookies.get( "refresh_token" ) );
        token.setIdToken( cookies.get( "user_info" ) );
        token.setExpirationTime( Long.parseLong( cookies.get( "access_token_expiration" ) ) );
        token.setTokenIssuedTime( System.currentTimeMillis() );
        return token;
    }

    public static boolean isActiveToken( Token token ) {

        if ( sessionVariableCalled( INVALIDATE_TOKEN ) != null && (boolean) sessionVariableCalled( INVALIDATE_TOKEN ) ) {
            token.setAccessToken( "invalid_token" );
            return true;
        }
        if ( token == null ) {
            return false;
        }

        String validationMode = getSystemParameter( TOKEN_VALIDATION_MODE );
        if ( "offline".equalsIgnoreCase( validationMode ) ) {
            return validateTokenOffline( token );
        } else {
            return validateTokenOnline( token );
        }
    }

    private static boolean validateTokenOffline( Token token ) {
        return JwksTokenValidator.validateTokenOffline( token.getAccessToken() );
    }

    private static boolean validateTokenOnline( Token token ) {
        RequestSpecification rs = new RequestSpecBuilder()
                .setBaseUri( getSystemParameter( IDAM_URL ) )
                .setBasePath( AuthorizationCodeFlowUtils.INTROSPECT_PATH )
                .addQueryParam( "token", token.getAccessToken() )
                .setContentType( ContentType.URLENC )
                .build();
        Response response = sendRequest( new RestRequest( Method.POST, rs ), false );
        return response.jsonPath().getBoolean( "active" );
    }

}
