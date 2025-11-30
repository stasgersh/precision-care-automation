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
package com.ge.onchron.verif.whatsteps;

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.ge.onchron.verif.howsteps.LoginSteps;
import com.ge.onchron.verif.howsteps.SplashScreenSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import net.thucydides.core.annotations.Steps;

public class SplashScreen {

    @Steps
    private SplashScreenSteps landingSteps;
    @Steps
    private LoginSteps loginSteps;
    private TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Given( "the user is on the OncoCare splash screen" )
    public void userOnSplashScreen() {
        testDefinitionSteps.addStep( "User is on splash screen" );
        landingSteps.loadSplashPage();
        testDefinitionSteps.logEvidence( "Splash page loaded without error",
                "Page loaded with no error", true );
    }

    @When( value = "the user clicks the Sign in button" )
    @Alias( value = "the user clicks the Back to Sign in button" )
    public void userClicksSignInButton() {
        testDefinitionSteps.addStep( "Click on sign in button" );
        landingSteps.clickSignIn();
        testDefinitionSteps.logEvidence( "User sign in without error",
                "User sign in with no error", true );
    }

    @Then( "the user navigates to the Login screen" )
    @When( "the user navigates to the Login screen" )
    public void userNavigatesToLoginScreen() {
        testDefinitionSteps.addStep( "User navigates to login screen" );
        landingSteps.clickSignIn();
        loginSteps.goToOrganizationLoginScreenIfNeeded();
        testDefinitionSteps.logEvidence("Login screen loaded without error",
                "Page loaded with no error", true);
    }

    @Given("the user was navigated to the OncoCare splash screen")
    @Then("the user is navigated to the OncoCare splash screen")
    public void verifyUserIsOnTheSplashScreen() {
        testDefinitionSteps.addStep("Navigate to the OncoCare splash screen");
        landingSteps.verifyUserIsOnTheSplashScreen();
        testDefinitionSteps.logEvidence("the user was navigated to the OncoCare splash screen",
            "Splash screen is displayed", true);
    }

}
