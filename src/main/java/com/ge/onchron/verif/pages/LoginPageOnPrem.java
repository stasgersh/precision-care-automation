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

public class LoginPageOnPrem extends LoginPage {

    private final String USER_NAME_INPUT_ID = "usernameUserInput";
    private final String DASHBOARD_VIEW_ID = "system-dashboard-aca";

    @FindBy( id = "displayNameProduct" )
    private WebElementFacade mainTitle;

    @FindBy( id = DASHBOARD_VIEW_ID )
    private WebElementFacade adminDashboard;

    @FindBy( id = USER_NAME_INPUT_ID )
    private WebElementFacade usernameInput;

    public LoginPageOnPrem() {
        getInputMap().put( "Username", USER_NAME_INPUT_ID );
    }

    @Override
    public void login( UserCredentials user ) {
        typeUsername( user.getUsername() );
        typePassword( user.getPassword() );
        clickSignIn();
    }

    @Override
    public void goToOrganizationLoginScreen() {
        // No additional steps needed in case of on-prem env
    }
    @Override
    public void isAdminDashboardVisible() {
        adminDashboard.waitUntilVisible().isVisible();
    }

    @Override
    public boolean isLoginPageDisplayed() {
        return mainTitle.waitUntilVisible().getText().equals( getProductName() );
    }

    @Override
    public boolean isAdminLoginPageDisplayed(String productName) {
        return mainTitle.waitUntilVisible().getText().equals( productName );
    }

    @Override
    public WebElementFacade getUserNameInput() {
        return usernameInput;
    }

}
