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
package com.ge.onchron.verif.converters;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.steps.ParameterConverters;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.RestRequest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;

import static com.ge.onchron.verif.TestSystemParameters.ADHERENCE_SERVICE_URL;
import static com.ge.onchron.verif.TestSystemParameters.ADHERENCE_TREATMENT_PATH;
import static com.ge.onchron.verif.TestSystemParameters.API_BASE_URL_PROPERTY;
import static com.ge.onchron.verif.TestSystemParameters.CONFIG_API_BASE_PATH_PROPERTY;
import static com.ge.onchron.verif.TestSystemParameters.CORE_API_BASE_PATH_PROPERTY;
import static com.ge.onchron.verif.TestSystemParameters.PATIENT_SUMMARY_BASE_URL;
import static com.ge.onchron.verif.TestSystemParameters.TEST_DATA_CLASSPATH;
import static com.ge.onchron.verif.TestSystemParameters.VALUESET_API_BASE_PATH_PROPERTY;
import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;
import static com.ge.onchron.verif.utils.FileUtils.readFromFile;
import static com.ge.onchron.verif.utils.ReplaceUtils.replaceCustomParameters;

public class RestRequestConverter implements ParameterConverters.ParameterConverter {

    private static final String METHOD_KEY = "method";
    private static final String SERVICE_KEY = "service";
    private static final String ENDPOINT_PATH_KEY = "endpoint_path";
    private static final String PAYLOAD_KEY = "payload";
    private static final String CONTENT_TYPE_KEY = "content_type";
    private static final String QUERY_PARAMETER_STARTING_CHARACTER = "?";
    private static final String QUERY_KEY_VALUE_SEPARATOR = "=";
    private static final String QUERY_PARAMETER_SEPARATOR = "&";
    private static final String EMPTY_STRING = "";
    private static final String NOT_APPLICABLE = "N/A";

    @Override
    public boolean accept( Type type ) {
        return type instanceof Class && RestRequest.class.isAssignableFrom( (Class) type );
    }

    @Override
    public Object convertValue( String string, Type type ) {
        // Convert the string back to table for processing
        ExamplesTable examplesTable = new ExamplesTable( string );
        assertThat( "The provided API request table in the feature file is not ok.", examplesTable.getRows().size(), is( equalTo( 1 ) ) );
        Map<String, String> parameters = examplesTable.getRow( 0 );

        if ( !parameters.containsKey( METHOD_KEY ) || !parameters.containsKey( ENDPOINT_PATH_KEY ) || !parameters.containsKey( SERVICE_KEY ) ) {
            fail( "Missing mandatory parameter in the request specification table in the feature file: " + parameters );
        }

        String baseURI = null;
        String servicePath = null;
        String service = parameters.get( SERVICE_KEY );
        switch ( service ) {
            case "core":
                baseURI = getSystemParameter( API_BASE_URL_PROPERTY );
                servicePath = getSystemParameter( CORE_API_BASE_PATH_PROPERTY );
                break;
            case "config":
                baseURI = getSystemParameter( API_BASE_URL_PROPERTY );
                servicePath = getSystemParameter( CONFIG_API_BASE_PATH_PROPERTY );
                break;
            case "valueset":
                baseURI = getSystemParameter( API_BASE_URL_PROPERTY );
                servicePath = getSystemParameter( VALUESET_API_BASE_PATH_PROPERTY );
                break;
            case "patient-summary":
                baseURI = getSystemParameter( PATIENT_SUMMARY_BASE_URL );
                servicePath = "";
                break;
            case "adherence":
                baseURI = getSystemParameter( ADHERENCE_SERVICE_URL );
                servicePath = getSystemParameter( ADHERENCE_TREATMENT_PATH );
                break;
            default:
                fail( "Not known service: " + service );
                break;
        }
        Map<String, String> originalValues = new HashMap<>();

        String path = replaceCustomParameters( parameters.get( ENDPOINT_PATH_KEY ) );

        RequestSpecBuilder rsb = new RequestSpecBuilder();
        rsb.setBaseUri( baseURI );
        rsb.setBasePath( servicePath + path );
        originalValues.put( "uri", servicePath + parameters.get( ENDPOINT_PATH_KEY ) );

        Method method = Method.valueOf( parameters.get( METHOD_KEY ) );

        if ( path.contains( QUERY_PARAMETER_STARTING_CHARACTER ) ) {
            Arrays.stream( StringUtils.substringAfter( path, QUERY_PARAMETER_STARTING_CHARACTER )
                                      .split( QUERY_PARAMETER_SEPARATOR ) )
                  .forEach( param -> addParam( rsb, param ) );
        }

        RestRequest restRequest = new RestRequest();
        restRequest.setMethod( method );

        originalValues.put( "body", null );
        // Optional parameters
        if ( parameters.get( PAYLOAD_KEY ) != null && !parameters.get( PAYLOAD_KEY ).equals( NOT_APPLICABLE ) ) {
            String payload = parameters.get( PAYLOAD_KEY );
            if ( payload.endsWith( ".json" ) ) {
                restRequest.setPayloadFileName( payload );
                payload = readFromFile( TEST_DATA_CLASSPATH + payload );
            }
            originalValues.put( "body", payload );
            payload = replaceCustomParameters( payload );
            rsb.setBody( payload );
        }
        if ( parameters.get( CONTENT_TYPE_KEY ) != null && !parameters.get( CONTENT_TYPE_KEY ).equals( NOT_APPLICABLE ) ) {
            String contentType = parameters.get( CONTENT_TYPE_KEY );
            rsb.setContentType( ContentType.fromContentType( contentType ) );
        }
        restRequest.setRequestSpecification( rsb.build() );
        restRequest.setOriginalValues( originalValues );
        return restRequest;
    }

    private void addParam( RequestSpecBuilder rsb, String param ) {
        param = replaceCustomParameters( param );
        if ( param.contains( QUERY_KEY_VALUE_SEPARATOR ) ) {
            String[] keyValue = param.split( QUERY_KEY_VALUE_SEPARATOR );
            if ( keyValue.length > 1 ) {
                rsb.addQueryParam( keyValue[0], keyValue[1] );
            } else {
                rsb.addQueryParam( keyValue[0], EMPTY_STRING );
            }
        } else {
            rsb.addQueryParam( param, (Object) null );
        }
    }

}
