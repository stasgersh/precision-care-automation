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
package com.ge.onchron.verif.howsteps;

import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ge.onchron.verif.model.RequestResponse;
import com.ge.onchron.verif.model.RestRequest;
import com.ge.onchron.verif.model.Token;
import com.ge.onchron.verif.model.UserCredentials;
import com.ge.onchron.verif.utils.JsonUtils;
import com.ge.onchron.verif.utils.TextUtils;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.SneakyThrows;

import static com.ge.onchron.verif.TestSystemParameters.ACCESS_TOKEN_FROM_COOKIE;
import static com.ge.onchron.verif.TestSystemParameters.INVALIDATE_TOKEN;
import static com.ge.onchron.verif.TestSystemParameters.MIXED_ACCESS_TOKEN;
import static com.ge.onchron.verif.TestSystemParameters.ORIGINAL_CONFIGS;
import static com.ge.onchron.verif.TestSystemParameters.REQUEST_RESPONSE;
import static com.ge.onchron.verif.utils.JsonUtils.removeKeys;
import static com.ge.onchron.verif.utils.RestUtils.sendRequest;
import static com.ge.onchron.verif.utils.oauth2.AuthorizationCodeFlowUtils.login;
import static com.ge.onchron.verif.utils.oauth2.ClientCredentialUtils.getMultiServiceAuthorization;
import static net.serenitybdd.core.Serenity.hasASessionVariableCalled;
import static net.serenitybdd.core.Serenity.sessionVariableCalled;
import static net.serenitybdd.core.Serenity.setSessionVariable;

public class RestApiSteps {
    private static final Logger LOGGER =
            LoggerFactory.getLogger( RestApiSteps.class );

    private static final String AMAZON_REQUEST_ID = "x-amzn-requestid";

    public Response sendAuthenticatedRequest( UserCredentials user, RestRequest restRequest ) {
        Token token = login( user );
        restRequest.getRequestSpecification().auth().preemptive().oauth2( token.getAccessToken() );
        Response response = sendRequest( restRequest );
        LOGGER.info( STR."AWS request ID: \{response.getHeader( AMAZON_REQUEST_ID )}" );
        return response;
    }

    private void getAndSaveOriginalConfig( String uri, Token token ) {
        if ( hasOriginalConfig( uri ) ) {
            return;
        }
        RequestSpecification getSpec = new io.restassured.builder.RequestSpecBuilder()
                .setBaseUri( uri )
                .build();
        getSpec.auth().preemptive().oauth2( token.getAccessToken() );
        RestRequest getRequest = new RestRequest( Method.GET, getSpec );

        Response originalResponse = sendRequest( getRequest );
        String originalConfig = originalResponse.asString();

        Map<String, String> originalConfigs = hasASessionVariableCalled( ORIGINAL_CONFIGS )
                ? sessionVariableCalled( ORIGINAL_CONFIGS )
                : new HashMap<>();
        originalConfigs.put( uri, originalConfig );
        setSessionVariable( ORIGINAL_CONFIGS ).to( originalConfigs );
    }

    public Response sendBackendAuthenticatedRequest( RestRequest restRequest ) {
        Token token = getMultiServiceAuthorization();
        restRequest.getRequestSpecification().auth().preemptive().oauth2( token.getAccessToken() );
        boolean isConfigPut = restRequest.getMethod().equals( Method.PUT )
                && ((RequestSpecificationImpl) restRequest.getRequestSpecification()).getBasePath().contains( "representation/config" );

        String uri = ((RequestSpecificationImpl) restRequest.getRequestSpecification()).getURI();
        if ( isConfigPut ) {
            getAndSaveOriginalConfig( uri, token );
        }
        Response response = sendRequest( restRequest );
        LOGGER.info( STR."AWS request ID: \{response.getHeader( AMAZON_REQUEST_ID )}" );
        return response;
    }

    private boolean hasOriginalConfig( String uri ) {
        Map<String, String> originalConfigs = hasASessionVariableCalled( ORIGINAL_CONFIGS )
                ? sessionVariableCalled( ORIGINAL_CONFIGS )
                : new HashMap<>();
        return originalConfigs.containsKey( uri );
    }

    public void invalidateAccessToken() {
        setSessionVariable( MIXED_ACCESS_TOKEN ).to( new Token() );
        setSessionVariable( INVALIDATE_TOKEN ).to( true );
    }

    public void resetAccessTokenValidity() {
        setSessionVariable( MIXED_ACCESS_TOKEN ).to( null );
        setSessionVariable( INVALIDATE_TOKEN ).to( false );
    }


    public void sendNotAuthenticatedRequest( RestRequest restRequest ) {
        sendRequest( restRequest );
    }

    public void sendRequestWithTokenFromCookie( RestRequest restRequest ) {
        if ( !hasASessionVariableCalled( ACCESS_TOKEN_FROM_COOKIE ) ) {
            fail( "Access token was not stored from browser's cookie" );
        }
        String accessToken = sessionVariableCalled( ACCESS_TOKEN_FROM_COOKIE );
        restRequest.getRequestSpecification().auth().preemptive().oauth2( accessToken );
        sendRequest( restRequest );
    }

    public void checkResponseStatusCode( int statusCode ) {
        Response response = getLastResponse();
        LOGGER.info( STR."Response status: \{response.getStatusCode()}" );
        LOGGER.info( STR."Response message: \{response.body().asString()}" );
        assertThat( "The response status code is not ok.",
                response.getStatusCode(), is( equalTo( statusCode ) ) );
    }

    public int getResponseStatusCode() {
        Response response = getLastResponse();
        return response.getStatusCode();
    }

    public void checkResponseBody( String body ) {
        Response response = getLastResponse();
        LOGGER.info( STR."Observed body: \{response.asString()}" );
        assertThat( "The response content is not ok.", response.asString(),
                is( equalTo( body ) ) );
    }

    public void compareJsonFiles( String expectedJsonFile ) {
        Response response = getLastResponse();
        LOGGER.info( STR."Observed body: \{response.getBody()}" );
        JSONAssert.assertEquals( response.getBody().asString(), expectedJsonFile, true );
    }

    public void compareKeyWordsWithResponse( List<String> expectedKeyWord, String property ) {
        Response response = getLastResponse();
        List<ArrayList<String>> propertyValue = response.jsonPath().getList( property );
        int index = 0;
        for ( String keyWord : expectedKeyWord ) {
            TextUtils.textCompareWithKeywords( keyWord.trim(),
                    propertyValue.get( index ).stream().map( Objects::toString ).collect( Collectors.joining( ", " ) ) );
            index++;
        }
    }

    @SneakyThrows
    public void compareJsonByType( String expectedJsonFile ) {
        Response response = getLastResponse();
        LOGGER.info( STR."Observed body: \{response.getBody()}" );

        String responseBody = response.getBody().asString();
        String expectedJson = Files.readString( Paths.get( expectedJsonFile ) );

        Object responseJson = JsonUtils.getJsonByType( responseBody );
        Object expectedJsonObj = JsonUtils.getJsonByType( expectedJson );
        JSONAssert.assertEquals( (JSONObject) expectedJsonObj, (JSONObject) responseJson, true );
    }

    public void checkResponseBodyContains( String expectedText ) {
        Response response = getLastResponse();
        LOGGER.info( STR."Observed body: \{response.asString()}" );
        assertThat( "The response content is not ok.", response.asString(),
                is( containsString( expectedText ) ) );
    }

    public void checkResponseHeaders( Map<String, String> expectedHeaders ) {
        Response response = getLastResponse();
        Headers observedHeaders = response.getHeaders();
        expectedHeaders.forEach( ( headerName, headerValue ) -> {
            Header observedHeader = observedHeaders.get( headerName );
            LOGGER.info( STR."Observed header: \{observedHeader}" );
            assertNotNull( STR."Following header was not received in the response: \{headerName}", observedHeader );
            LOGGER.info( STR."Observed header: \{observedHeader}" );
            assertEquals( STR."\{headerName} header value is not ok.", headerValue, observedHeader.getValue() );
        } );
    }

    public void checkResponseContentType( String contentType ) {
        ContentType expectedContentType = ContentType.fromContentType( contentType );
        Response response = getLastResponse();
        ContentType observedContentType = ContentType.fromContentType( response.getContentType() );
        LOGGER.info( STR."Response content type \{observedContentType}" );
        assertEquals( "Response content type is not ok.", expectedContentType, observedContentType );
    }

    public Response getLastResponse() {
        RequestResponse reqResp = sessionVariableCalled( REQUEST_RESPONSE );
        return reqResp == null ? null : reqResp.getResponse();
    }

    public String getNextPage() {
        Response response = getLastResponse();
        String nextPage = response.jsonPath().get( "nextPageToken" );
        LOGGER.info( STR."Next Page Token: \{nextPage}" );
        return nextPage;
    }

    public String getCorrelationId() {
        Response response = getLastResponse();
        String correlation_id = response.getHeader( "x-correlation-id" );
        LOGGER.info( STR."The correlation-id is: \{correlation_id}" );
        return correlation_id;
    }

    public void checkJsonPropertyAvailabilityInResponse( String propertyName ) {
        Response response = getLastResponse();
        Object property = response.jsonPath().get( propertyName );
        LOGGER.info( STR."Response of Property in json response:\{property}" );
        assertNotNull( STR."Property was not found in response json: \{propertyName}", property );
    }

    public void checkJsonPropertyValueInResponse( String propertyName,
                                                  String expectedPropertyValue, boolean contains ) {
        Response response = getLastResponse();
        String observedPropertyValue = response.jsonPath().getString( propertyName );
        LOGGER.info( STR."Response contains Property \{propertyName} with value: \{observedPropertyValue}" );
        if ( contains ) {
            assertTrue( String.format( "%s property value is not ok in the response. Expected to contain: %s, but was: %s",
                    propertyName, expectedPropertyValue, observedPropertyValue ), observedPropertyValue.contains( expectedPropertyValue ) );
        } else {
            assertEquals( String.format( "%s property value is not ok in the response. Expected: %s, but was: %s",
                    propertyName, expectedPropertyValue, observedPropertyValue ), expectedPropertyValue, observedPropertyValue );
        }
    }

    public void checkJsonPropertyCountInResponse( String propertyName,
                                                  int expectedPropertyCount ) {
        Response response = getLastResponse();
        int observedPropertySize = response.jsonPath().getList( propertyName ).size();
        LOGGER.info( STR."Response contains list at \{propertyName} of size: \{observedPropertySize}" );
        assertThat(
                String.format( "%s property size is not ok in the response.",
                        propertyName ), expectedPropertyCount <= observedPropertySize );
    }

    public void checkPropertyAvailabilityInListResponse( String listPropertyName,
                                                         String subPropertyName ) {
        Response response = getLastResponse();
        List<Object> listProperty = response.jsonPath().get( listPropertyName );
        LOGGER.info( STR."Property was found in response json: \{listPropertyName}" );
        assertNotNull( STR."Property was not found in response json: \{listPropertyName}", listProperty );
        String fullPropertyPath = STR."\{listPropertyName}.\{subPropertyName}";
        List<Object> requiredPropertiesFromListItems = response.jsonPath().getList( fullPropertyPath );
        for ( int i = 0; i < requiredPropertiesFromListItems.size(); i++ ) {
            Object propertyValue = requiredPropertiesFromListItems.get( i );
            LOGGER.info( STR."for list item \{i} required property: '\{subPropertyName}' has value '\{propertyValue}'" );
            assertNotNull(
                    STR."Not all list items have the required property: \{subPropertyName}", propertyValue );
        }
    }

    public void checkJsonListElementValuesInResponse( String listPropertyName, String subPropertyName, List<String> expectedValuesFromList ) {
        String fullPropertyPath = STR."\{listPropertyName}.\{subPropertyName}";
        Response response = getLastResponse();
        List<String> observedValuesFromList = new ArrayList<>( JsonPath.read( response.asString(), fullPropertyPath ) );
        LOGGER.info( STR."Observed values of property \{fullPropertyPath}: \{observedValuesFromList}" );
        assertNotNull( STR."Property was not found in response json: \{fullPropertyPath}", fullPropertyPath );
        assertEquals( "The observed set of values do not match the expected!", new HashSet<>( expectedValuesFromList ), new HashSet<>( observedValuesFromList ) );
    }

    public void checkJsonListElementValueAllSameInResponse( String listPropertyName,
                                                            String subPropertyName, String expectedValueFromList ) {
        String fullPropertyPath = STR."\{listPropertyName}.\{subPropertyName}";
        Response response = getLastResponse();
        List<String> observedValuesFromList =
                response.jsonPath().getList( fullPropertyPath );
        LOGGER.info( STR."Values of property \{fullPropertyPath}: \{observedValuesFromList}" );
        assertNotNull(
                STR."Property was not found in response json: \{fullPropertyPath}",
                fullPropertyPath );
        assertEquals( "List values do not match expected!",
                Collections.nCopies( observedValuesFromList.size(), expectedValueFromList ),
                observedValuesFromList );
    }

    public void checkJsonListContainsExpectedValues( String listPropertyName, String subPropertyName, List<String> expectedValuesFromList ) {
        String fullPropertyPath = STR."\{listPropertyName}.\{subPropertyName}";
        Response response = getLastResponse();
        List<String> observedValuesFromList = new ArrayList<>( JsonPath.read( response.asString(), fullPropertyPath ) );
        LOGGER.info( STR."Values of property \{fullPropertyPath}: \{observedValuesFromList}" );
        assertThat( "List values do not match expected!", observedValuesFromList, hasItems( expectedValuesFromList.toArray( new String[0] ) ) );
    }

    public String getCreatedResponseId() {
        String responseID = getLastResponse().jsonPath().getString( "id" );
        LOGGER.info( STR."Generated last response ID:\{responseID}" );
        return responseID;
    }

    public void saveResponseJsonAs( String storedVariableName ) {
        Response response = getLastResponse();
        setSessionVariable( storedVariableName ).to( response.asString() );
    }

    public void removeElementFromResponseAndSaveAs( String jsonPath, String storedVariableName ) {
        Response response = getLastResponse();
        DocumentContext jsonContext = JsonPath.parse( response.asString() );
        jsonContext.delete( jsonPath );
        setSessionVariable( storedVariableName ).to( jsonContext.jsonString() );
    }

    public void setValueToElementFromResponseAndSaveAs( String jsonPath, String storedVariableName, String text ) {
        Response response = getLastResponse();
        DocumentContext jsonContext = JsonPath.parse( response.asString() );
        if ( jsonPath.contains( "required" ) ) {
            jsonContext.set( jsonPath, Boolean.valueOf( text ) );
        } else {
            jsonContext.set( jsonPath, text );
        }
        setSessionVariable( storedVariableName ).to( jsonContext.jsonString() );
    }

    public void resetUserToken( UserCredentials user ) {
        user.setToken( null );
    }

    public void compareJsonFilesWithIgnoredAttributes( List<String> properties, String expectedJsonFile ) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Response response = getLastResponse();
            JsonNode actualResponseAsJsonNode = removeKeys( mapper.readTree( response.getBody().asString() ), properties );
            JsonNode expectedResponseAsJsonNode = removeKeys( mapper.readTree( expectedJsonFile ), properties );
            LOGGER.info( STR."Observed body with ignored properties:\{properties} \r\n\\was:\{actualResponseAsJsonNode}" );
            JSONAssert.assertEquals( mapper.writeValueAsString( expectedResponseAsJsonNode ), mapper.writeValueAsString( actualResponseAsJsonNode ), true );
        } catch ( JsonProcessingException e ) {
            throw new RuntimeException( e );
        }
    }
}
