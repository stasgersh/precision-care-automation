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
package com.ge.onchron.verif.utils;

import org.apache.http.conn.ConnectTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.http.params.CoreConnectionPNames.CONNECTION_TIMEOUT;
import static org.apache.http.params.CoreConnectionPNames.SO_TIMEOUT;

import com.ge.onchron.verif.model.RequestResponse;
import com.ge.onchron.verif.model.RestRequest;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import java.net.SocketTimeoutException;

import static com.ge.onchron.verif.TestSystemParameters.API_BASE_URL_PROPERTY;
import static com.ge.onchron.verif.TestSystemParameters.CONFIG_API_BASE_PATH_PROPERTY;
import static com.ge.onchron.verif.TestSystemParameters.CORE_API_BASE_PATH_PROPERTY;
import static com.ge.onchron.verif.TestSystemParameters.REQUEST_RESPONSE;
import static com.ge.onchron.verif.TestSystemParameters.REST_CONNECTION_TIMEOUT;
import static com.ge.onchron.verif.TestSystemParameters.REST_SOCKET_TIMEOUT;
import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;
import static io.restassured.RestAssured.given;
import static net.serenitybdd.core.Serenity.setSessionVariable;

public class RestUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger( RestUtils.class );

    private static final int MAX_RETRIES = 5;
    private static final int INITIAL_BACKOFF_MS = 1000;

    static {
        SSLConfig sslConfig = new SSLConfig().with().relaxedHTTPSValidation().and().allowAllHostnames();
        RestAssured.config = RestAssured.config()
                                        .sslConfig( sslConfig )
                                        .httpClient( HttpClientConfig.httpClientConfig()
                                                                     .setParam( CONNECTION_TIMEOUT, Integer.parseInt( getSystemParameter( REST_CONNECTION_TIMEOUT ) ) )
                                                                     .setParam( SO_TIMEOUT, Integer.parseInt( getSystemParameter( REST_SOCKET_TIMEOUT ) ) ) );
    }

    public static Response sendRequest( RestRequest restRequest, boolean logRequest, boolean storeResponse ) {
        if ( logRequest ) {
            LOGGER.info( "Sending {}", restRequest.toString( false, 1000 ) );
        }

        int attempt = 0;
        int backoff = INITIAL_BACKOFF_MS;

        while ( true ) {
            try {
                Response response = given()
                        .spec( restRequest.getRequestSpecification() )
                        .when().request( restRequest.getMethod() );
                if ( storeResponse ) {
                    setSessionVariable( REQUEST_RESPONSE ).to( new RequestResponse( restRequest, response ) );
                }
                return response;
            } catch ( Exception e ) {
                if ( isCausedBySocketTimeout( e ) && attempt < MAX_RETRIES ) {
                    attempt++;
                    LOGGER.warn( "Socket/Connect timeout on attempt {}/{}. Retrying after {} ms", attempt, MAX_RETRIES, backoff );
                    try {
                        Thread.sleep( backoff );
                    } catch ( InterruptedException ie ) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException( ie );
                    }
                    backoff *= 2;
                    continue;
                }
                throw e;
            }
        }
    }

    public static Response sendRequest( RestRequest restRequest, boolean logRequest ) {
        return sendRequest( restRequest, logRequest, true );
    }

    public static Response sendRequest( RestRequest restRequest ) {
        return sendRequest( restRequest, true );
    }

    private static boolean isCausedBySocketTimeout( Throwable t ) {
        while ( t != null ) {
            if ( t instanceof SocketTimeoutException ) {
                return true;
            }
            if ( t instanceof ConnectTimeoutException ) {
                return true;
            }
            t = t.getCause();
        }
        return false;
    }

    public static String getUrlAlias( String url ) {
        return url.replace( getSystemParameter( API_BASE_URL_PROPERTY ), "" )
                  .replace( getSystemParameter( CORE_API_BASE_PATH_PROPERTY ), "<core_url>" )
                  .replace( getSystemParameter( CONFIG_API_BASE_PATH_PROPERTY ), "<config_api_url>" )
                  .replace( getSystemParameter( CORE_API_BASE_PATH_PROPERTY ), "<core_api_url>" );
    }

}