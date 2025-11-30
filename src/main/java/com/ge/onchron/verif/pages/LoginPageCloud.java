/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2024, 2024, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 */
package com.ge.onchron.verif.pages;

import com.ge.onchron.verif.model.UserCredentials;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.TestSystemParameters.*;

public class LoginPageCloud extends LoginPage {

    private final String USER_AND_ORG_INPUT_ID = "usernameUserInput";
    private final String USER_NAME_INPUT_ID = "prop-username";
    private final String DASHBOARD_VIEW_ID = "system-dashboard-aca";

    @FindBy( css = "[data-cy*='Continue']" )
    private WebElementFacade continueBtn;

    @FindBy( id = DASHBOARD_VIEW_ID )
    private WebElementFacade adminDashboard;

    @FindBy( css = "[data-cy*='Welcome']" )
    private WebElementFacade mainTitle;

    @FindBy( id = USER_AND_ORG_INPUT_ID )
    private WebElementFacade usernameAndOrgInput;

    @FindBy(xpath = "//*[contains(@id, 'username')]" )
    private WebElementFacade usernameInput;

    public LoginPageCloud() {
        getInputMap().put( "Username", USER_NAME_INPUT_ID );
    }

    @Override
    public boolean isLoginPageDisplayed() {
        continueBtn.waitUntilNotVisible();
        return mainTitle.waitUntilVisible().getText().contains( getProductName() );
    }

    @Override
    public boolean isAdminLoginPageDisplayed(String productName) {
        return mainTitle.waitUntilVisible().getText().contains( productName );
    }

    @Override
    public WebElementFacade getUserNameInput() {
        return usernameInput;
    }

    @Override
    public void isAdminDashboardVisible() {
        adminDashboard.waitUntilVisible().isVisible();
    }

    @Override
    public void login( UserCredentials user ) {
        goToOrganizationLoginScreen(user.getOrganizationName());
        typeUsername( user.getUsername() );
        typePassword( user.getPassword() );
        clickSignIn();
    }
    public void loginToAdminConsole(UserCredentials user, Boolean isSystemUser)
    {
        if(!isSystemUser) {
            goToOrganizationLoginScreen(user.getOrganizationName());
        }
        else {
            goToOrganizationLoginScreen(user.getOrganizationName());
            typeUsername(user.getUsername());
            typePassword(user.getPassword());
            clickSignIn();
        }
    }

    @Override
    public void goToOrganizationLoginScreen() {
        if ( continueBtn.isCurrentlyVisible() ) {
            usernameAndOrgInput.type(STR."@\{getSystemParameter(USER_ORGANIZATION)}");
            elementClicker( continueBtn );
        }
    }

    public void goToOrganizationLoginScreen(String organizationName) {
        if ( continueBtn.isCurrentlyVisible() ) {
            usernameAndOrgInput.type(STR."@\{organizationName}");
            elementClicker( continueBtn );
            continueBtn.waitUntilNotVisible();
        }
    }

}
