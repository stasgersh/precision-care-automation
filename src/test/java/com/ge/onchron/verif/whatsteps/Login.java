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

import com.ge.onchron.verif.howsteps.*;
import com.ge.onchron.verif.model.UserCredentials;
import com.ge.onchron.verif.pages.LoginPageCloud;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.util.List;
import org.slf4j.*;

import static java.lang.StringTemplate.STR;

public class Login {

    @Steps
    private LoginSteps loginSteps;
    @Steps
    private SplashScreenSteps landingSteps;
    @Steps
    private PatientScreenSteps patientScreenSteps;
    @Steps
    private UserMenuSteps       userMenuSteps;
    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();
    LoginPageCloud loginPageCloud;

    private static final Logger LOGGER = LoggerFactory.getLogger( Login.class );

    @Given( "the user is on the OncoCare Login page" )
    public void navigateToLoginPage() {
        testDefinitionSteps.addStep("Navigate to login page");
        landingSteps.loadSplashPage();
        landingSteps.clickSignIn();
        loginSteps.loginPageIsLoaded();
        testDefinitionSteps.logEvidence( "OncoCare Login page is displayed",
            "Login page is loaded", true);
    }

    @Given( "the [$user] user is logged in to OncoCare application" )
    @When( "the [$user] user logs in to OncoCare" )
    public void loginFlowByUser( UserCredentials user ) {
        testDefinitionSteps.addStep(STR."Login to OncoCare app with user: \{user.getAlias()}");
        if ( patientScreenSteps.patientPageIsCurrentlyVisible() && !loginSteps.isRequiredUserLoggedIn( user ) ) {
            logout();
        }
        if ( !patientScreenSteps.patientPageIsCurrentlyVisible() ) {
            landingSteps.loadSplashPage();
            landingSteps.clickSignIn();
            loginSteps.goToOrganizationLoginScreenIfNeeded();
            loginSteps.loginPageIsLoaded();
            loginSteps.loginWithUser(user);
            patientScreenSteps.verifyPatientPageIsLoaded();
        }
        testDefinitionSteps.logEvidence( "Logged in successfully and Patient page is loaded", "Logged in and Patient page was loaded", true);
    }

    @Given("token validation is set to $mode mode")
    public void setTokenValidationMode(String mode) {
        testDefinitionSteps.addStep(STR."Set token validation to \{mode} mode");
        loginSteps.setTokenValidationMode(mode);
        testDefinitionSteps.logEvidence(
                STR."token validation is set to: \{mode}",
                STR."token validation was set to: \{mode} successfully", true
        );
    }

    @Then("the Login page is loaded")
    public void loginPageIsLoadedStandalone() {
        testDefinitionSteps.addStep("Login page is loaded");
        loginSteps.loginPageIsLoaded();
        testDefinitionSteps.logEvidence("Login page is loaded successfully", "Login page was loaded without errors",
            true);
    }
    @Then("the $productName Login page is loaded")
    public void adminLoginPageIsLoadedStandalone(String productName) {
        testDefinitionSteps.addStep(STR."Check \{productName} login page");
        loginSteps.loginPageIsLoaded(productName);
        testDefinitionSteps.logEvidence("Login page is loaded successfully", "Login page was loaded without errors",
                true);
    }

    @Then( "the Admin Console Dashboard page is loaded" )
    public void verifyAdminConsoleDashboardLoaded() {
        testDefinitionSteps.addStep("Check Admin Console Dashboard page is loaded");
        loginSteps.verifyDashboardIsLoaded();
        testDefinitionSteps.logEvidence("The Admin Console Dashboard page page is loaded",
                "Admin Console Dashboard page is loaded (see previous logs)",
                true);
    }

    @When("the [$user] user logs in to OncoCare from the Login page")
    public void loginToAppByUser(UserCredentials user) {
        testDefinitionSteps.addStep(STR."Login to app by user \{user.getAlias()}");
        loginSteps.loginWithUser(user);
        testDefinitionSteps.logEvidence(STR."the \{user.getAlias()} user logs in to OncoCare from the Login page",
            STR."The \{user.getAlias()} user logs in to OncoCare from the Login page successfully", true);
    }
    @When("the $userName user logs in to CIO from the Login page with password $password and organization $organization")
    public void loginToCIOAppByUser(String userName, String password, String organization) {
        UserCredentials user = loginSteps.createUserCredentials(userName, password, organization);
        testDefinitionSteps.addStep(STR."Login to app by user \{user.getUsername()}");
        loginSteps.loginWithUser(user);
        testDefinitionSteps.logEvidence(STR."the \{user.getUsername()} user logs in to OncoCare from the Login page",
                STR."The \{user.getUsername()} user logs in to OncoCare from the Login page successfully", true);
    }
    @When("the [$user] user logs in to AdminConsole from the Login page")
    public void loginToAdminConsole(UserCredentials user) {
        testDefinitionSteps.addStep(STR."Login to app by user \{user.getAlias()}");
        loginPageCloud.loginToAdminConsole(user, true);
        testDefinitionSteps.logEvidence(STR."The \{user.getAlias()} user logs in to AdminConsole from the Login page",
                STR."The \{user.getAlias()} user logs in to AdminConsole from the Login page successfully", true);
    }

    @Given("the user is logged out from OncoCare application")
    public void logoutFromAppByUser() {
        testDefinitionSteps.addStep("Logout from application");
        logout();
        testDefinitionSteps.logEvidence( "The user logs out from application successfully",
                "The user logged out from application successfully", true);
    }

    private void logout() {
        if (userMenuSteps.isVisible()) {
            userMenuSteps.clickOnSignOut();
            landingSteps.waitForLoginModuleToLoad();
        }
    }

    @Then("the characters in the $field field $masked masked")
    public void checkFieldIsMaskedOrNot(String field, Boolean masked) {
        loginSteps.fieldMaskedOrDisplayed(field, masked);
    }

    @Then( "the following input fields $visible visible: $fields" )
    public void checkFieldsVisibility( Boolean visible, List<String> fields ) {
        testDefinitionSteps.addStep( STR."Check visibility of fields:\\n \{String.join( "\\n", fields )}" );
        loginSteps.checkMoreFieldsVisibility( fields, visible );
        testDefinitionSteps.logEvidence( STR."All fields are \{visible ? "" : "not"} visible",
                "All fields had expected visibility (see previous logs)", true );
    }
}
