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
package com.ge.onchron.verif.whatsteps.api;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

import com.ge.onchron.verif.howsteps.RestApiSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.model.RestRequest;
import com.ge.onchron.verif.model.StringList;
import com.ge.onchron.verif.model.UserCredentials;
import io.restassured.http.Headers;
import io.restassured.internal.RequestSpecificationImpl;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.thucydides.core.annotations.Steps;

import static com.ge.onchron.verif.TestSystemParameters.*;
import static com.ge.onchron.verif.utils.FileUtils.readFromFile;
import static com.ge.onchron.verif.utils.FileUtils.readFromFileByAbsolutePath;
import static com.ge.onchron.verif.utils.Utils.convertTableToMap;

public class RestApi {

    @Steps
    private RestApiSteps restApiSteps;

    private TestDefinitionSteps testDefinitionSteps =
            TestDefinitionSteps.getInstance();

    @Given( "the following request is sent to OncoCare on behalf of [$user] user: $restRequest" )
    @When( "the following request is sent to OncoCare on behalf of [$user] user: $restRequest" )
    public void sendAuthenticatedRequest( UserCredentials user,
                                          RestRequest restRequest ) {
        testDefinitionSteps.addStep( STR."Send the following Authenticated Request:\n\{restRequest}" );
        restApiSteps.sendAuthenticatedRequest( user, restRequest );
        testDefinitionSteps.logEvidence( "Authentication request sent successfully",
                "request is sent to OncoCare without errors", true );
    }

    @Given( "the following request was sent to Precision Insights by service user app: $restRequest" )
    @When( "the following request is sent to Precision Insights by service user app: $restRequest" )
    public void sendAuthenticatedRequestByServiceUserApp( RestRequest restRequest ) {
        testDefinitionSteps.addStep( STR."Send the following Authenticated Request by service user app:\n\{restRequest}" );
        restApiSteps.sendBackendAuthenticatedRequest( restRequest );
        testDefinitionSteps.logEvidence( "Authentication request sent successfully", "request is sent to PI without errors", true );
    }

    @When( "the following request is sent to OncoCare without authenticated user: $restRequest" )
    public void sendNotAuthenticatedRequest( RestRequest restRequest ) {
        restApiSteps.sendNotAuthenticatedRequest( restRequest );
    }

    @Given( "the following request was sent to OncoCare by using the stored access token from cookie: $restRequest" )
    @When( "the following request is sent to OncoCare by using the stored access token from cookie: $restRequest" )
    public void sendRequestWithTokenFromCookie( RestRequest restRequest ) {
        String basePath = ((RequestSpecificationImpl) restRequest.getRequestSpecification()).getBasePath();
        Headers headers = ((RequestSpecificationImpl) restRequest.getRequestSpecification()).getHeaders();
        Object body = ((RequestSpecificationImpl) restRequest.getRequestSpecification()).getBody();
        testDefinitionSteps.addStep( STR.
                "Send \{restRequest.getMethod()} request to url \{basePath} with headers \{headers} and body \{body}}" );
        restApiSteps.sendRequestWithTokenFromCookie( restRequest );
        testDefinitionSteps.logEvidence(
                "Send request to OncoCare by using the stored access token from cookie",
                "request was sent to OncoCare by using the stored access token from cookie without error",
                true );
    }

    @Given( "the received HTTP status code was $statusCode" )
    @When( "the received HTTP status code is $statusCode" )
    @Then( "the received HTTP status code is $statusCode" )
    public void checkResponseStatusCode( int statusCode ) {
        testDefinitionSteps.addStep( STR."Check HTTP status code" );
        restApiSteps.checkResponseStatusCode( statusCode );
        testDefinitionSteps.logEvidence(
                STR."the received HTTP status code is \{statusCode}",
                STR."The response status code is \{statusCode}",
                true );
    }

    @Then( "the response contains the following body: \"$responseBody\"" )
    public void checkResponseBody( String responseBody ) {
        testDefinitionSteps.addStep( STR."Check the response" );
        restApiSteps.checkResponseBodyContains(responseBody );
        testDefinitionSteps.logEvidence(
                STR."the response contains the following body: \{responseBody}",
                "Response content matches expected (see previous logs)",
                true );
    }

    @Then( "the response contains the following body content: \"$path\"" )
    public void checkResponseBodyContent( String path ) {
        String new_path = String.valueOf( Paths.get( getSystemParameter( BASE_RESOURCES_PATH ), path ) );
        String fileContent = readFromFileByAbsolutePath( new_path );
        testDefinitionSteps.addStep( STR."Check the response" );
        restApiSteps.checkResponseBody( fileContent );
        testDefinitionSteps.logEvidence(
                STR."the response contains the following body: \{fileContent}",
                "Response content matches expected (see previous logs)",
                true );
    }
    @Then("the body property $property of the response contains the following keyWords: $filename")
    public void checkJsonKeyWords(String property, String keyWords ) {
        String filePath = STR."\{Paths.get(getSystemParameter(CTE_AI_BASE_PATH), keyWords).toString()}.txt";
        List<String> fileContent = Arrays.stream(readFromFileByAbsolutePath( filePath ).split("\n")).toList();
        testDefinitionSteps.addStep( STR."Check the response" );
        restApiSteps.compareKeyWordsWithResponse( fileContent, property );
        testDefinitionSteps.logEvidence(STR.
                        "the response contains the following keyWords: \{fileContent}",
                "Response content matches expected (see previous logs)",
                true);
    }
    @Then("the body of the response contains the following: $fileName")
    public void checkJsonResponse(String fileName) {
        String filePath = STR."\{Paths.get(getSystemParameter(CTE_AI_BASE_PATH), fileName).toString()}.json";
        String fileContent = readFromFileByAbsolutePath( filePath );
        testDefinitionSteps.addStep("Check the response");
        restApiSteps.compareJsonFiles(fileContent);
        testDefinitionSteps.logEvidence(STR.
                        "the response contains the following body: \{fileContent}",
                "Response content matches expected (see previous logs)",
                true);
    }
    @Then("the body of the response contains the following content: $fileName")
    public void checkResponse(String fileName) {
        String filePath = STR."\{Paths.get(getSystemParameter(CTE_AI_BASE_PATH), fileName).toString()}.json";
        String fileContent = readFromFileByAbsolutePath( filePath );
        testDefinitionSteps.addStep("Check the response");
        restApiSteps.compareJsonByType(fileContent);
        testDefinitionSteps.logEvidence(STR.
                        "the response contains the following body: \{fileContent}",
                "Response content matches expected (see previous logs)",
                true);
    }

    @Then( "the response contains the following body content: \"$path\" from $basePath" )
    public void checkResponseBodyContent(String basePath, String path) {
        String new_path = String.valueOf( Paths.get( getSystemParameter(basePath ), path ) );
        String fileContent = readFromFileByAbsolutePath( new_path );
        testDefinitionSteps.addStep( STR."Check the response" );
        restApiSteps.checkResponseBody( fileContent );
        testDefinitionSteps.logEvidence(
                STR."the response contains the following body: \{fileContent}",
                "Response content matches expected (see previous logs)",
                true );
    }

    @Then( "the response body contains the following text: '$text'" )
    public void checkResponseBodyContains( String text ) {
        testDefinitionSteps.addStep( STR."Check the response body" );
        restApiSteps.checkResponseBodyContains( text );
        testDefinitionSteps.logEvidence(
                STR."the response body contains the following text: \{text}",
                "Response content matches expected (see previous logs)", true );
    }

    @Then( "the response body contains the following lines: $lines" )
    public void checkResponseBodyContainsAll( StringList lines ) {
        testDefinitionSteps.addStep( STR."Check the response body" );
        for ( String text : lines.getList() ) {
            restApiSteps.checkResponseBodyContains( text );
            testDefinitionSteps.logEvidence( STR."the response body contains the following text: \{text}",
                    "Response content matches expected (see previous logs)",
                    true, false );
        }
        testDefinitionSteps.logEvidence( STR."the response body contains all lines from above",
                "Response content matches expected (see previous logs)",
                true );

    }

    @Then( "the response contains the following headers: $headersTable" )
    public void checkResponseHeaders( ExamplesTable headersTable ) {
        testDefinitionSteps.addStep( "Check the response headers" );
        restApiSteps.checkResponseHeaders( convertTableToMap( headersTable ) );
        testDefinitionSteps.logEvidence( STR.
                        "the response contains the following headers: \{headersTable.getRows()}",
                "Response content type is ok",
                true );
    }

    @Then( "the response contains the following content type: \"$contentType\"" )
    public void checkResponseHeaders( String contentType ) {
        testDefinitionSteps.addStep( STR.
                "Check the response" );
        restApiSteps.checkResponseContentType( contentType );
        testDefinitionSteps.logEvidence( STR.
                        "the response contains the following content type: \{contentType}",
                "Response content type is ok",
                true );
    }

    @Then( "the response contains the \"$propertyName\" property" )
    public void checkPropertyAvailabilityInResponse( String propertyName ) {
        testDefinitionSteps.addStep( STR."Check property '\{propertyName}' is available in response" );
        restApiSteps.checkJsonPropertyAvailabilityInResponse( propertyName );
        testDefinitionSteps.logEvidence( STR.
                        "the response contains the following property: '\{propertyName}'",
                "Response contains expected Property (See previous logs)",
                true );
    }

    @Then("the \"$propertyName\" property in the response $expression the value: $propertyValue" )
    public void checkJsonPropertyValueInResponse( String propertyName, String expression, String propertyValue ) {
        testDefinitionSteps.addStep( STR."Check property's '\{propertyName}' value in response" );
        restApiSteps.checkJsonPropertyValueInResponse( propertyName, propertyValue, expression.equals( "contains" ) );
        testDefinitionSteps.logEvidence( STR.
                        "the '\{propertyName}' property in the response \{expression} the value: '\{propertyValue}'",
                "Response contains expected Property with expected value (See previous logs)",
                true );
    }

    @Then("the \"$propertyName\" property in the response contains $count elements" )
    public void checkJsonPropertyValueInResponse( String propertyName, int count ) {
        testDefinitionSteps.addStep( STR."Check property's '\{propertyName}' value in response" );
        restApiSteps.checkJsonPropertyCountInResponse( propertyName, count );
        testDefinitionSteps.logEvidence( STR.
                        "the '\{propertyName}' property in the response contains \{count} elements",
                "Response contains expected Property with expected value (See previous logs)",
                true );
    }

    @Then("the \"$listPropertyName\" property contains a list where all elements have \"$subPropertyName\" property" )
    public void checkPropertyAvailabilityInListResponse( String listPropertyName, String subPropertyName ) {
        testDefinitionSteps.addStep( STR."Check all elements in '\{listPropertyName}' property" );
        restApiSteps.checkPropertyAvailabilityInListResponse(
                listPropertyName, subPropertyName );
        testDefinitionSteps.logEvidence( STR.
                        "the '\{listPropertyName}' property contains a list where all elements have '\{subPropertyName}' property",
                "All list items have the required property (See previous logs)",
                true );
    }

    @Then( "the \"$listPropertyName\" property contains a list where all elements have following properties: $subPropertiesList" )
    public void checkPropertyAvailabilityInListResponse( String listPropertyName, StringList subPropertiesList ) {
        testDefinitionSteps.addStep( STR."Check all elements in '\{listPropertyName}' property" );
        for ( String subPropertyName : subPropertiesList.getList() ) {
            restApiSteps.checkPropertyAvailabilityInListResponse(
                    listPropertyName, subPropertyName );
            testDefinitionSteps.logEvidence( STR."the '\{listPropertyName}' property contains a list where all elements have '\{subPropertyName}' property",
                    "All list items have the required property (See previous logs)",
                    true, false );
        }
        testDefinitionSteps.logEvidence( STR."All properties from above are present in the response",
                "All list items have the required properties (See previous logs)",
                true );
    }

    @Then( "the \"$listPropertyName\" property contains a list where the elements have \"$subPropertyName\" properties with below values: $propertyValues" )
    public void checkJsonListElementValuesInResponse( String listPropertyName, String subPropertyName, StringList propertyValues ) {
        testDefinitionSteps.addStep( STR."Check all elements in \{listPropertyName} property" );
        restApiSteps.checkJsonListElementValuesInResponse( listPropertyName, subPropertyName, propertyValues.getList() );
        testDefinitionSteps.logEvidence( STR.
                        "property '\{listPropertyName}' contains a list where the elements have '\{subPropertyName}' properties with below values: '\{propertyValues.getList()}'",
                "All list items have the required property with expected values (See previous logs)",
                true );
    }

    @Then( "the \"$listPropertyName\" property contains a list where elements have \"$subPropertyName\" properties includes the following values: $propertyValues" )
    public void checkJsonListContainsExpectedValues ( String listPropertyName, String subPropertyName, StringList propertyValues ) {
        testDefinitionSteps.addStep( STR."Check all elements in \{listPropertyName} property" );
        restApiSteps.checkJsonListContainsExpectedValues( listPropertyName, subPropertyName, propertyValues.getList() );
        testDefinitionSteps.logEvidence( STR.
                        "property '\{listPropertyName}' contains a list where elements have '\{subPropertyName}' properties includes the following values: '\{propertyValues.getList()}'",
                "List items have the expected properties with expected values (See previous logs)",
                true );
    }

    @Then( "the \"$listPropertyName\" property contains a list where all elements have \"$subPropertyName\" properties with value: $propertyValues" )
    public void checkJsonListElementValuesInResponse( String listPropertyName, String subPropertyName, String propertyValue ) {
        testDefinitionSteps.addStep( STR."Check property's '\{listPropertyName}.\{subPropertyName}' value in response" );
        restApiSteps.checkJsonListElementValueAllSameInResponse( listPropertyName, subPropertyName, propertyValue );
        testDefinitionSteps.logEvidence( STR."the '\{listPropertyName}.\{subPropertyName}' property in the response contains the value: '\{propertyValue}'",
                "Response contains expected Property with expected value (See previous logs)",
                true );
    }

    @Given( "the backend authentication token is invalidated" )
    public void invalidateMultiServiceAccessToken() {
        testDefinitionSteps.addStep( "Invalidate the backend authentication token" );
        restApiSteps.invalidateAccessToken();
        testDefinitionSteps.logEvidence( "Token in use is no longer valid", "Token invalidated successfully", true );
    }

    @Then( "the [$user] token is reset" )
    public void resetUserToken( UserCredentials user ) {
        testDefinitionSteps.addStep( STR."Invalidate the authentication token for \{user.getAlias()}" );
        restApiSteps.resetUserToken( user );
        testDefinitionSteps.logEvidence( STR."Token for \{user.getAlias()} is reset", STR."Token for \{user.getAlias()} is reset", true );
    }

    @Given( "the backend authentication token is valid" )
    public void resetAccessTokenValidity() {
        testDefinitionSteps.addStep( "Use valid access token" );
        restApiSteps.resetAccessTokenValidity();
        testDefinitionSteps.logEvidence( "Token in use is valid", "Token set to be valid", true );
    }

    @When( "the user stores response json as \"$storedVariableName\"" )
    public void saveResponseJsonAs( String storedVariableName ) {
        testDefinitionSteps.addStep( String.format( "the user stores the response json as \"%s\"", storedVariableName ) );
        restApiSteps.saveResponseJsonAs( storedVariableName );
        testDefinitionSteps.logEvidence(
                String.format( "the user stored the response json and stored as \"%s\"", storedVariableName ),
                String.format( "the user stored the response json and stored as \"%s\"", storedVariableName ),
                true );
    }

    @When( "the user removes the element \"$jsonPath\" from the response json and stores as \"$storedVariableName\"" )
    public void removeElementFromResponseAndSaveAs( String jsonPath, String storedVariableName ) {
        testDefinitionSteps.addStep( String.format( "the user removes the element \"%s\" from the response json and stores as \"%s\"", jsonPath, storedVariableName ) );
        restApiSteps.removeElementFromResponseAndSaveAs( jsonPath, storedVariableName );
        testDefinitionSteps.logEvidence(
                String.format( "the user removed the element \"%s\" from the response json and stored as \"%s\"", jsonPath, storedVariableName ),
                String.format( "the user removed the element \"%s\" from the response json and stored as \"%s\"", jsonPath, storedVariableName ),
                true );
    }

    @Then( "the user checks the json response ignoring properties [$properties] VS expected json file $filePath" )
    public void checkResponseByIgnoringProperties(ArrayList<String> properties, String filePath) {
        testDefinitionSteps.addStep(STR."the user checks the json response ignoring properties [\{properties}] VS expected json file \{filePath}");
        String expectedResponse = readFromFile(STR."\{TEST_DATA_CLASSPATH}\{filePath}");
        restApiSteps.compareJsonFilesWithIgnoredAttributes(properties, expectedResponse);
        testDefinitionSteps.logEvidence(
                STR."the user checks the json response ignoring properties [\{properties}] VS expected json file \{filePath}",
                STR."the user checks the json response ignoring properties [\{properties}] VS expected json file \{filePath}",
                true );
    }

    @When( "the user set $value value to the element \"$jsonPath\" from the response json and stores as \"$storedVariableName\"" )
    public void setValueToElementFromResponseAndSaveAs( String value, String jsonPath, String storedVariableName ) {
        testDefinitionSteps.addStep( STR."the user set \{value} value the element \{jsonPath} from the response json and stores as \{storedVariableName}" );
        restApiSteps.setValueToElementFromResponseAndSaveAs(jsonPath, storedVariableName, value );
        testDefinitionSteps.logEvidence(
                String.format( STR."the user set \{value} value the element \{jsonPath} from the response json and stored as \{storedVariableName}"),
                String.format( STR."the user set \{value} value the element \{jsonPath} from the response json and stored as \{storedVariableName}"),
                true );
    }

}
