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
package com.ge.onchron.verif.pages;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import com.ge.onchron.verif.model.UserCredentials;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.TestSystemParameters.PRODUCT_NAME;
import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;

abstract public class LoginPage extends SimplePage {

    private final String PASSWORD_INPUT_ID = "password";
    private final String LOGIN_CONTAINER = "loginContainer";

    @FindBy( id = "loginForm" )
    private WebElementFacade loginForm;

    @FindBy( id = PASSWORD_INPUT_ID )
    private WebElementFacade passwordInput;

    @FindBy(xpath = "//*[contains(@id, 'username')]")
    private WebElementFacade userNameInput;

    @FindBy( xpath = "//button[@type='submit']" )
    private WebElementFacade signInButton;

    @FindBy(id = LOGIN_CONTAINER)
    private WebElementFacade loginContainer;


    // this map is used to identify elements in feature files without their DOM identifier
    private final Map<String, String> INPUT_MAP = new HashMap<>();

    public LoginPage() {
        INPUT_MAP.put( "Password", PASSWORD_INPUT_ID );
    }

    protected Map<String, String> getInputMap() {
        return INPUT_MAP;
    }

    protected String getProductName() {
        return getSystemParameter( PRODUCT_NAME );
    }

    public void typeUsername( String username ) {
        userNameInput.type( username );
    }

    public void typePassword( String pwd ) {
        passwordInput.type( pwd );
    }

    public void clickSignIn() {
        signInButton.waitUntilClickable();
        elementClicker( signInButton );
    }

    public String getMessageFromLoginPage() {
        return loginContainer.waitUntilVisible().getText();
    }

    public WebElementFacade getInputFieldByAlias( String inputAlias ) {
        return loginForm.find( By.id( INPUT_MAP.get( inputAlias ) ) );
    }

    abstract public boolean isLoginPageDisplayed();

    abstract public boolean isAdminLoginPageDisplayed(String productName);
    abstract public WebElementFacade getUserNameInput();

    abstract public void login( UserCredentials user );
    abstract public void isAdminDashboardVisible();
    abstract public void goToOrganizationLoginScreen();

}
