/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2022, 2022, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.whatsteps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.ge.onchron.verif.howsteps.CommonSteps;
import com.ge.onchron.verif.howsteps.LoginSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.model.UserCredentials;
import net.thucydides.core.annotations.Steps;

import static com.ge.onchron.verif.utils.Utils.insertSystemParameters;

public class Common {

    @Steps
    private CommonSteps commonSteps;

    @Steps
    private LoginSteps loginSteps;

    private TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @When( "the user goes to the following URL: $url" )
    public void goToUrl( String url ) {
        testDefinitionSteps.addStep( STR."Go to the following URL: \{url}" );
        commonSteps.goToUrl( url );
        testDefinitionSteps.logEvidence( STR."The following URL: \{url} presented",
                STR."The following URL: \{url} presented with no error", true );
    }

    @Given( "the user access token is stored from the browser's cookie" )
    public void storeAccessTokenFromCookie() {
        testDefinitionSteps.addStep( "Check that the user access token is stored from the browser's cookie" );
        commonSteps.storeAccessTokenFromCookie();
        testDefinitionSteps.logEvidence( "User access token is stored from the browser",
                "User access token is stored from the browser without error", true );
    }

    @Given( "the user access token is valid for no more than $maxMinutes minutes" )
    public void validateTokenExpirationTime( int maxMinutes ) {
        testDefinitionSteps.addStep( STR."Validate access token expires in ≤\{maxMinutes} minutes" );
        commonSteps.validateTokenExpirationTime( maxMinutes );
        testDefinitionSteps.logEvidence(
                STR."the user access token is valid for no more than \{maxMinutes} minutes",
                STR."the user access token is valid for no more than \{maxMinutes} minutes",
                true
        );
    }

    @Then( "new browser tab $isOpened opened" )
    public void checkNewBrowserTabOpened( Boolean isOpened ) {
        testDefinitionSteps.addStep( STR."Check new browser tab visibility" );
        commonSteps.checkNewBrowserTabOpened( isOpened );
        testDefinitionSteps.logEvidence( STR."New browser tab is \{isOpened ? "" : "not"} opened",
                STR."New browser tab is \{isOpened ? "" : "not"} opened", true );
    }

    @Then( "a new browser tab is opened with the following url: $url" )
    public void checkNewBrowserTab( String url ) {
        testDefinitionSteps.addStep( STR."Check new browser Tab is opened" );
        commonSteps.checkNewBrowserTab( url );
        testDefinitionSteps.logEvidence( STR."New browser tab is opened with the following url: \{url}",
                "URL of the new browser tab is ok", true );
    }

    @Then( "the user sees the $pageOrModal in US English as \"$filename\" ref file" )
    public void checkPageText( String pageOrModal, String filename ) {
        testDefinitionSteps.addStep( STR."Check page text" );
        commonSteps.checkPageText( pageOrModal, filename );
        testDefinitionSteps.logEvidence( STR."The \{pageOrModal} in US English as \{filename} ref file",
                STR."Page text match the reference file \{filename}", true );
    }

    @Given( "the page is reloaded" )
    @When( "the user clicks on the browser's refresh button" )
    public void refreshPage() {
        testDefinitionSteps.addStep( "Click on the browser's refresh button" );
        commonSteps.refreshPage();
        testDefinitionSteps.logEvidence( "The page is reloaded",
                "The page is reloaded successfully", true );
    }

    @Then( "the following error is displayed on the screen: \"$errorMessage\"" )
    public void checkFullScreenError( String errorMessage ) {
        testDefinitionSteps.addStep( "Check full screen error" );
        commonSteps.checkFullScreenError( errorMessage );
        testDefinitionSteps.logEvidence( STR."the following error is displayed on the screen: \{errorMessage}",
                "The Error message on error screen displayed as expected", true );
    }

    @Then( "the following error pop-up is displayed on the screen: \"$errorMessage\"" )
    public void checkScreenError( String errorMessage ) {
        testDefinitionSteps.addStep( "Check full screen error" );
        commonSteps.checkPopUpError( errorMessage );
        testDefinitionSteps.logEvidence( STR."the following error is displayed on the screen: \{errorMessage}",
                "The Error message on error screen displayed as expected", true );
    }

    @Then( "the following error message is displayed on the login screen: \"$errorMessage\"" )
    public void checkLoginScreenError( String errorMessage ) {
        testDefinitionSteps.addStep( "Check full screen error" );
        loginSteps.checkMessageExist( errorMessage );
        testDefinitionSteps.logEvidence( STR."the following error is displayed on the screen: \{errorMessage}",
                "The Error message on error screen displayed as expected", true );
    }

    @When( "the user waits $timeoutInMillis milliseconds" )
    public void waitInMillis( String timeoutInMillis ) {
        int timeoutMillis = Integer.parseInt( insertSystemParameters( timeoutInMillis ) );
        testDefinitionSteps.addStep( STR."Wait for \{timeoutMillis} milliseconds" );
        commonSteps.waitInMillis( timeoutMillis );
        testDefinitionSteps.logEvidence( STR."\{timeoutMillis} milliseconds waited",
                STR."\{timeoutMillis} milliseconds waited", true );
    }

    @When( "the user waits $timeoutInMinutes minutes" )
    public void waitInMinutes( String timeoutInMinutes ) {
        int timeoutMins = Integer.parseInt( insertSystemParameters( timeoutInMinutes ) );
        testDefinitionSteps.addStep( STR."Wait for \{timeoutMins} minutes" );
        commonSteps.waitInMillis( timeoutMins * 60000 );
        testDefinitionSteps.logEvidence( STR."\{timeoutMins} minutes waited", STR."\{timeoutMins} minutes waited", true );
    }

    @When( "the user opens a new browser tab" )
    public void openNewBrowserTab() {
        testDefinitionSteps.addStep( "Open new browser tab" );
        commonSteps.openNewBrowserTab();
        testDefinitionSteps.logEvidence( "New browser tab is opened",
                "New browser tab is opened successfully", true );
    }

    @Given( "the \"$parameterName\" of [$user] user is stored as \"$storedParameterName\"" )
    public void storeUserParameter( String parameterName, UserCredentials user, String storedParameterName ) {
        testDefinitionSteps.addStep( String.format( "Store \"%s\" parameter of [%s] as \"%s\"", parameterName, user.getAlias(), storedParameterName ) );
        commonSteps.storeUserParameter( user, parameterName, storedParameterName );
        testDefinitionSteps.logEvidence(
                String.format( "The \"%s\" parameter of [%s] as \"%s\"", parameterName, user.getAlias(), storedParameterName ),
                String.format( "The \"%s\" parameter of [%s] as \"%s\"", parameterName, user.getAlias(), storedParameterName ),
                true );
    }
    @Given("the user reopen browser")
    public void closeBrowser() throws InterruptedException {
        testDefinitionSteps.addStep( "reopen browser" );
        commonSteps.reopenBrowserWindow();
        testDefinitionSteps.logEvidence( "the browser reopened", "the browser reopened", true );

    }
    @When("the user shrinks browser window")
    public void shrinkBrowserWindow() {
        testDefinitionSteps.addStep( "shrink browser window" );
        commonSteps.shrinkWindow();
        testDefinitionSteps.logEvidence("the browser shrunk", "the browser shrunk", true );
    }

}
