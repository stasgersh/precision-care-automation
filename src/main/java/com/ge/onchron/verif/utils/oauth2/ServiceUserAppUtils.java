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

import com.ge.onchron.verif.model.Token;

import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;
import static com.ge.onchron.verif.utils.oauth2.ClientCredentialUtils.getMultiServiceToken;
import static net.serenitybdd.core.Serenity.hasASessionVariableCalled;
import static net.serenitybdd.core.Serenity.sessionVariableCalled;
import static net.serenitybdd.core.Serenity.setSessionVariable;

public class ServiceUserAppUtils {

    public static final String STORED_SERVICE_USER_APP_TOKEN = "service_user_app_token";
    public static final String SERVICE_USER_APP_CLIENT_ID_PROPERTY = "service.user.app.client_id";
    public static final String SERVICE_USER_APP_CLIENT_SECRET_PROPERTY = "service.user.app.client_secret";
    public static final String SERVICE_USER_APP_SCOPE_PROPERTY = "service.user.app.scope";


    public static Token getServiceUserAppToken( String audience ) {
        if ( hasASessionVariableCalled( STORED_SERVICE_USER_APP_TOKEN ) ) {
            return sessionVariableCalled( STORED_SERVICE_USER_APP_TOKEN );
        }

        // Unique scope is added to the scope list to avoid invalidating token when tests are running parallely
        String scopes = String.format( "%s uniqueScope%d", getSystemParameter( SERVICE_USER_APP_SCOPE_PROPERTY ), System.currentTimeMillis() );
        Token token = new Token();
        token.setAccessToken( getMultiServiceToken(
                getSystemParameter( SERVICE_USER_APP_CLIENT_ID_PROPERTY ),
                getSystemParameter( SERVICE_USER_APP_CLIENT_SECRET_PROPERTY ),
                scopes,
                audience )
        );
        setSessionVariable( "service_user_app_token" ).to( token );
        return token;
    }

}
