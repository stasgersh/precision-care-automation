/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2021, 2021, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.utils.oauth2;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.RestRequest;
import com.ge.onchron.verif.model.Token;
import com.ge.onchron.verif.model.UserCredentials;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static com.ge.onchron.verif.TestSystemParameters.IDAM_URL;
import static com.ge.onchron.verif.TestSystemParameters.STORED_USERS;
import static com.ge.onchron.verif.TestSystemParameters.UI_BASE_URL_PROPERTY;
import static com.ge.onchron.verif.TestSystemParameters.USER_ORGANIZATION;
import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;
import static com.ge.onchron.verif.pages.utils.CookieUtils.getAccessToken;
import static com.ge.onchron.verif.pages.utils.CookieUtils.getLoggedInUsername;
import static com.ge.onchron.verif.utils.RestUtils.sendRequest;
import static com.ge.onchron.verif.utils.Utils.storeMapSessionVariable;
import static com.ge.onchron.verif.utils.oauth2.TokenUtils.isActiveToken;
import static com.ge.onchron.verif.utils.oauth2.TokenUtils.parseToken;
import static io.restassured.config.RedirectConfig.redirectConfig;
import static net.serenitybdd.core.Serenity.getDriver;

public class AuthorizationCodeFlowUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger( AuthorizationCodeFlowUtils.class );

    public static final String LOCATION_HEADER = "Location";
    public static final String SESSION_DATA_KEY_PARAM = "sessionDataKey";

    public static final String LOGIN_VIA_TOKEN_MEDIATING_PATH = "auth/login";
    public static final String COMMON_AUTH_PATH = "/commonauth";
    public static final String SAML_AUTH_PATH = "/samlsso";
    public static final String INTROSPECT_PATH = "oauth2/introspect";
    public static final String AUTHORIZE_PATH = "oauth2/authorize";
    public static final String TOKEN_PATH = "oauth2/token";

    public static Token login( UserCredentials user ) {
        // Option #1: Get user token from local storage. If not available, then get via API
        if ( isValidURL( getDriver().getCurrentUrl() ) ) { // it can be empty before browser is opened or "data:," before url is entered in browser
            String loggedInUsername = getLoggedInUsername();
            if ( user.getUsername().equals( loggedInUsername ) ) {
                String accessToken = getAccessToken();
                if ( accessToken != null ) {
                    Token token = new Token();
                    token.setAccessToken( accessToken );
                    return token;
                }
            }
        }

        // Option #2: Get token via IDAM API
        if ( user.hasValidTokenWithOffset() && isActiveToken( user.getToken() ) ) {
            return user.getToken();
        }

        // Generate new token with retries
        final int maxRetries = 5;
        final int retryDelayMs = 1000;
        for ( int attempt = 1; attempt <= maxRetries; attempt++ ) {
            try {
                LOGGER.info( "Generating new token for user: {} (attempt {}/{})", user.getAlias(), attempt, maxRetries );
                String locationHeader = initAuthorizationCodeFlow( user );
                Token token = generateToken( locationHeader );
                if ( token != null ) {
                    user.setToken( token );
                    storeMapSessionVariable( STORED_USERS, user.getAlias(), user );
                    return token;
                }
                if ( attempt < maxRetries ) {
                    LOGGER.warn( "Token generation attempt {}/{} failed. Retrying...", attempt, maxRetries );
                    Thread.sleep( retryDelayMs * attempt );
                }
            } catch ( InterruptedException e ) {
                Thread.currentThread().interrupt();
                LOGGER.error( "Token generation interrupted for user: {}", user.getAlias(), e );
                throw new IllegalStateException( STR."Token generation interrupted for user: \{user.getAlias()}", e );
            }
        }
        LOGGER.error( "Failed to generate token for user: {} after {} attempts", user.getAlias(), maxRetries );
        throw new IllegalStateException( STR."Failed to generate token for user: \{user.getAlias()} after \{maxRetries} attempts" );
    }

    private static boolean isValidURL( String url ) {
        try {
            new URL( url ).toURI();
            return true;
        } catch ( MalformedURLException | URISyntaxException e ) {
            return false;
        }
    }

    private static String initAuthorizationCodeFlow( UserCredentials user ) {
        String authSessionDataKeyValue = authorizeWithApp();
        String samlRequest = commonAuthLoginRequest( authSessionDataKeyValue, user );
        String samlSessionDataKeyValue = initSamlAuthentication( authSessionDataKeyValue, samlRequest );
        String samlResponse = samlAuthentication( samlSessionDataKeyValue, user );
        String sessionDataKeyValue = validateSamlResponse( authSessionDataKeyValue, samlResponse );
        return getAuthorizationCode( sessionDataKeyValue );
    }

    private static String authorizeWithApp() {
        // Call Token Mediating backend and get authorize URL from Location header of the response
        String baseUiUrl = getSystemParameter( UI_BASE_URL_PROPERTY );
        RequestSpecification req = new RequestSpecBuilder()
                .setConfig( RestAssured.config().redirect( redirectConfig().followRedirects( false ) ) )
                .setBaseUri( baseUiUrl )
                .setBasePath( LOGIN_VIA_TOKEN_MEDIATING_PATH )
                .addQueryParam( "redirect_uri", baseUiUrl.endsWith( "/" ) ? baseUiUrl : STR."\{baseUiUrl}/" )
                .build();
        Response res = sendRequest( new RestRequest( Method.GET, req ) );
        assertEquals( "The received status code is not ok.", HttpStatus.SC_MOVED_TEMPORARILY, res.getStatusCode() );
        String authUrlRequest = res.getHeaders().get( LOCATION_HEADER ).getValue();
        try {
            URI requestUri = new URI( authUrlRequest );
            List<NameValuePair> params = URLEncodedUtils.parse( requestUri, StandardCharsets.UTF_8 );
            Map<String, String> queryParams = new HashMap<>();
            params.forEach( param -> queryParams.put( param.getName(), param.getValue() ) );
            RequestSpecification rs = new RequestSpecBuilder()
                    .setConfig( RestAssured.config().redirect( redirectConfig().followRedirects( false ) ) )
                    .setBaseUri( getSystemParameter( IDAM_URL ) )
                    .setBasePath( requestUri.toURL().getPath() )
                    .addQueryParams( queryParams )
                    .build();
            Response response = sendRequest( new RestRequest( Method.GET, rs ) );
            assertEquals( "The received status code is not ok.", HttpStatus.SC_MOVED_TEMPORARILY, response.getStatusCode() );

            String location = response.getHeaders().get( LOCATION_HEADER ).getValue();
            return getUrlQueryParam( location, SESSION_DATA_KEY_PARAM );

        } catch ( URISyntaxException | MalformedURLException e ) {
            throw new RuntimeException( String.format( "Failed to authorize with the app: %s", e ) );
        }
    }

    private static String commonAuthLoginRequest( String reqSessionDataKeyValue, UserCredentials user ) {
        RequestSpecification rs = new RequestSpecBuilder()
                .setConfig( RestAssured.config().redirect( redirectConfig().followRedirects( false ) ) )
                .setBaseUri( getSystemParameter( IDAM_URL ) )
                .setBasePath( COMMON_AUTH_PATH )
                .addFormParam( "usernameUserInput", user.getUsername() + "@" + getSystemParameter( USER_ORGANIZATION ) )
                .addFormParam( "username", user.getUsername() + "@" + getSystemParameter( USER_ORGANIZATION ) )
                .addFormParam( "authType", "idf" )
                .addFormParam( SESSION_DATA_KEY_PARAM, reqSessionDataKeyValue )
                .setContentType( ContentType.URLENC )
                .build();
        Response response = sendRequest( new RestRequest( Method.POST, rs ) );
        assertEquals( "The received status code is not ok.", HttpStatus.SC_OK, response.getStatusCode() );

        String regex = "'SAMLRequest' value='(.*)'><input type";
        Pattern pattern = Pattern.compile( regex );
        Matcher matcher = pattern.matcher( response.asString() );
        String samlRequest = null;
        while ( matcher.find() ) {
            samlRequest = matcher.group( 1 );
        }
        assertNotNull( "SAMLRequest was not found in commonAuth response: " + response.asString(), samlRequest );
        return samlRequest;
    }

    private static String initSamlAuthentication( String relayState, String samlRequest ) {
        RequestSpecification rs = new RequestSpecBuilder()
                .setConfig( RestAssured.config().redirect( redirectConfig().followRedirects( false ) ) )
                .setBaseUri( getSystemParameter( IDAM_URL ) )
                .setBasePath( SAML_AUTH_PATH )
                .addFormParam( "RelayState", relayState )
                .addFormParam( "SAMLRequest", samlRequest )
                .addQueryParam( "tenantDomain", getSystemParameter( USER_ORGANIZATION ) )
                .setContentType( ContentType.URLENC )
                .build();
        Response response = sendRequest( new RestRequest( Method.POST, rs ) );
        assertEquals( "The received status code is not ok.", HttpStatus.SC_MOVED_TEMPORARILY, response.getStatusCode() );

        String location = response.getHeaders().get( LOCATION_HEADER ).getValue();
        return getUrlQueryParam( location, SESSION_DATA_KEY_PARAM );
    }

    private static String samlAuthentication( String sessionDataKey, UserCredentials user ) {
        RequestSpecification rs = new RequestSpecBuilder()
                .setConfig( RestAssured.config().redirect( redirectConfig().followRedirects( false ) ) )
                .setBaseUri( getSystemParameter( IDAM_URL ) )
                .setBasePath( SAML_AUTH_PATH )
                .addFormParam( "tocommonauth", true )
                .addFormParam( "username", user.getUsername() + "@" + getSystemParameter( USER_ORGANIZATION ) )
                .addFormParam( "password", user.getPassword() )
                .addFormParam( "sessionDataKey", sessionDataKey )
                .addQueryParam( "tenantDomain", getSystemParameter( USER_ORGANIZATION ) )
                .setContentType( ContentType.URLENC )
                .build();
        Response response = sendRequest( new RestRequest( Method.POST, rs ) );
        assertEquals( "The received status code is not ok.", HttpStatus.SC_OK, response.getStatusCode() );

        String regex = "'SAMLResponse' value='(.*)'/>\n";
        Pattern pattern = Pattern.compile( regex );
        Matcher matcher = pattern.matcher( response.asString() );
        String samlResponse = null;
        while ( matcher.find() ) {
            samlResponse = matcher.group( 1 );
        }
        assertNotNull( "SAMLRequest was not found in commonAuth response: " + response.asString(), samlResponse );
        return samlResponse;
    }

    private static String validateSamlResponse( String reqSessionDataKeyValue, String samlResponse ) {
        RequestSpecification rs = new RequestSpecBuilder()
                .setConfig( RestAssured.config().redirect( redirectConfig().followRedirects( false ) ) )
                .setBaseUri( getSystemParameter( IDAM_URL ) )
                .setBasePath( COMMON_AUTH_PATH )
                .addFormParam( "RelayState", reqSessionDataKeyValue )
                .addFormParam( "SAMLResponse", samlResponse )
                .setContentType( ContentType.URLENC )
                .build();
        Response response = sendRequest( new RestRequest( Method.POST, rs ) );
        assertEquals( "The received status code is not ok.", HttpStatus.SC_MOVED_TEMPORARILY, response.getStatusCode() );

        String location = response.getHeaders().get( LOCATION_HEADER ).getValue();
        return getUrlQueryParam( location, SESSION_DATA_KEY_PARAM );
    }

    private static String getAuthorizationCode( String sessionDataKey ) {
        RequestSpecification rs = new RequestSpecBuilder()
                .setConfig( RestAssured.config().redirect( redirectConfig().followRedirects( false ) ) )
                .setBaseUri( getSystemParameter( IDAM_URL ) )
                .setBasePath( AUTHORIZE_PATH )
                .addFormParam( SESSION_DATA_KEY_PARAM, sessionDataKey )
                .build();
        Response response = sendRequest( new RestRequest( Method.POST, rs ) );
        assertEquals( "The received status code is not ok.", HttpStatus.SC_MOVED_TEMPORARILY, response.getStatusCode() );
        return response.getHeaders().get( LOCATION_HEADER ).getValue();
    }

    private static Token generateToken( String locationHeader ) {
        try {
            URI requestUri = new URI( locationHeader );
            List<NameValuePair> params = URLEncodedUtils.parse( requestUri, StandardCharsets.UTF_8 );
            Map<String, String> queryParams = new HashMap<>();
            params.forEach( param -> queryParams.put( param.getName(), param.getValue() ) );
            RequestSpecification rs = new RequestSpecBuilder()
                    .setConfig( RestAssured.config().redirect( redirectConfig().followRedirects( false ) ) )
                    .setBaseUri( getSystemParameter( UI_BASE_URL_PROPERTY ) )
                    .setBasePath( requestUri.toURL().getPath() )
                    .addQueryParams( queryParams )
                    .build();
            Response response = sendRequest( new RestRequest( Method.GET, rs ) );
            if ( response.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY ) {
                return parseToken( response.cookies() );
            }
            LOGGER.warn( "generateToken failed with status {}", response.getStatusCode() );
            return null;
        } catch ( URISyntaxException | MalformedURLException e ) {
            LOGGER.error( "Invalid URL/URI in generateToken: {}", e.getMessage() );
            return null;
        }
    }

    private static String getUrlQueryParam( String url, String paramName ) {
        String paramValue = null;
        try {
            List<NameValuePair> params = URLEncodedUtils.parse( new URI( url ), StandardCharsets.UTF_8 );
            paramValue = params.stream()
                               .filter( param -> param.getName().equals( paramName ) )
                               .map( NameValuePair::getValue )
                               .findFirst()
                               .orElseThrow( () -> new URISyntaxException( url, "The " + paramName + " query param was not found in received URL" ) );
        } catch ( URISyntaxException e ) {
            fail( "Error while getting url query param: " + e.getMessage() );
        }
        return paramValue;
    }

}
