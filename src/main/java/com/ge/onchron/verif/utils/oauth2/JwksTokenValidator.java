/*
 * -GE CONFIDENTIAL- or -GE HIGHLY CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2025, 2025, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE HealthCare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 * GE is a trademark of General Electric Company used under trademark license.
 */

package com.ge.onchron.verif.utils.oauth2;

import static org.slf4j.LoggerFactory.getLogger;

import com.ge.onchron.verif.model.RestRequest;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Calendar;
import java.util.Date;

import static com.ge.onchron.verif.TestSystemParameters.IDAM_JWKS_URL;
import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;
import static com.ge.onchron.verif.utils.RestUtils.sendRequest;

public class JwksTokenValidator {
    private static final org.slf4j.Logger LOGGER = getLogger( JwksTokenValidator.class );

    private static JWKSet cachedJwks;
    private static Date jwksExpirationTime;

    public static boolean validateTokenOffline( String accessToken ) {
        try {
            SignedJWT signedJWT = SignedJWT.parse( accessToken );
            LOGGER.info( "JWT Header: {}", signedJWT.getHeader().toJSONObject() );
            LOGGER.info( "JWT Claims: {}", signedJWT.getJWTClaimsSet().toJSONObject() );

            // Check expiration
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            LOGGER.info( "Token Expiration: {}", expirationTime );

            if ( expirationTime != null && expirationTime.before( new Date() ) ) {
                LOGGER.warn( "Token validation FAILED: Token expired" );
                return false;
            }
            LOGGER.info( "Token not expired" );

            // Verify signature using JWKS
            JWKSet jwks = getJwks();
            String keyId = signedJWT.getHeader().getKeyID();
            RSAKey rsaKey = (RSAKey) jwks.getKeyByKeyId( keyId );

            if ( rsaKey == null ) {
                LOGGER.error( "Offline token validation FAILED: Key ID not found in JWKS" );
                LOGGER.error( "Available Key IDs: {}", jwks.getKeys().stream()
                                                           .map( JWK::getKeyID )
                                                           .toList() );
                return false;
            }

            JWSVerifier verifier = new RSASSAVerifier( rsaKey );
            boolean signatureValid = signedJWT.verify( verifier );
            if ( signatureValid ) {
                LOGGER.info( "Offline token validation SUCCESSFUL: Signature verified" );
            } else {
                LOGGER.error( "Offline token validation FAILED: Signature verification failed" );
            }
            return signatureValid;

        } catch ( Exception e ) {
            LOGGER.error( "Offline token validation FAILED: Exception occurred", e );
            return false;
        }
    }

    private static JWKSet getJwks() throws Exception {
        Date now = new Date();

        // Refresh JWKS if cache is empty or expired
        if ( cachedJwks == null || jwksExpirationTime == null || jwksExpirationTime.before( now ) ) {
            refreshJwks();
        }
        return cachedJwks;
    }

    private static void refreshJwks() throws Exception {
        LOGGER.info( "Fetching JWKS from IDAM" );
        String jwksUrl = getSystemParameter( IDAM_JWKS_URL );
        RequestSpecification rs = new RequestSpecBuilder()
                .setBaseUri( jwksUrl )
                .build();
        Response response = sendRequest( new RestRequest( Method.GET, rs ), false );

        LOGGER.info( "JWKS Status: " + response.getStatusCode() );
        LOGGER.info( "JWKS Response: " + response.asString() );

        cachedJwks = JWKSet.parse( response.asString() );

        // JWKS updates daily at 00:30 (check authorizer_jwks_update_schedule: 30-0-*-*-*)
        jwksExpirationTime = calculateNextJwksUpdate();
        LOGGER.info( "JWKS cached until {}", jwksExpirationTime );
    }

    private static Date calculateNextJwksUpdate() {
        Calendar cal = Calendar.getInstance();
        cal.set( Calendar.HOUR_OF_DAY, 0 );
        cal.set( Calendar.MINUTE, 30 );
        cal.set( Calendar.SECOND, 0 );
        cal.set( Calendar.MILLISECOND, 0 );

        // If current time is past today's 00:30, set to tomorrow's 00:30
        if ( cal.getTime().before( new Date() ) ) {
            cal.add( Calendar.DAY_OF_MONTH, 1 );
        }

        return cal.getTime();
    }

}