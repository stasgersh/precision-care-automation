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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

import com.ge.onchron.verif.model.UserCredentials;
import com.ge.onchron.verif.pages.LoginPageCloud;
import net.serenitybdd.core.Serenity;

import static com.ge.onchron.verif.TestSystemParameters.ORIGINAL_TOKEN_VALIDATION_MODE;
import static com.ge.onchron.verif.pages.utils.CookieUtils.getLoggedInUsername;
import static com.ge.onchron.verif.utils.Utils.waitMillis;

public class LoginSteps {

    LoginPageCloud loginPage;
    private static final Logger LOGGER = LoggerFactory.getLogger( LoginSteps.class );

    public UserCredentials createUserCredentials( String username, String password, String organization ) {
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUsername( username );
        userCredentials.setPassword( password );
        userCredentials.setOrganizationName( organization );
        return userCredentials;
    }

    public void loginPageIsLoaded() {
        final boolean loginPageDisplayed = loginPage.isLoginPageDisplayed();
        LOGGER.info( STR."Login page is displayed: \{loginPageDisplayed}" );
        assertTrue( "Login page is not loaded", loginPageDisplayed );
    }

    public void loginPageIsLoaded( String productName ) {
        final boolean loginPageDisplayed = loginPage.isAdminLoginPageDisplayed( productName );
        LOGGER.info( STR."Login page is displayed: \{loginPageDisplayed}" );
        assertTrue( "Login page is not loaded", loginPageDisplayed );
    }

    public void verifyDashboardIsLoaded() {
        loginPage.isAdminDashboardVisible();
    }

    public void loginWithUser( UserCredentials user ) {
        loginPage.login( user );
    }

    public void goToOrganizationLoginScreenIfNeeded() {
        loginPage.goToOrganizationLoginScreen();
    }

    public void fieldMaskedOrDisplayed( String field, boolean masked ) {
        String fieldType = loginPage.getInputFieldByAlias( field ).getAttribute( "type" );
        if ( masked ) {
            assertThat( "Field type is not 'password'", fieldType, is( "password" ) );
        } else {
            assertThat( "Field type is not 'text'", fieldType, is( "text" ) );
        }
    }

    public void checkMessageExist( String message ) {
        waitMillis( 2000 );
        String loginMessage = loginPage.getMessageFromLoginPage();
        LOGGER.info( STR."Screen message: \{loginMessage}" );
        assertThat( STR."Message mismatch on login screen", loginMessage.contains( message ) );
    }

    public void checkFieldVisibility( String field, boolean visible ) {
        boolean isVisible = loginPage.getInputFieldByAlias( field ).isVisible();
        LOGGER.info( STR."Field \{field} visibility is: \{isVisible}" );
        assertThat( STR."'\{field}' field visibility is not ok.", isVisible, is( visible ) );
    }

    public void checkMoreFieldsVisibility( List<String> fields, boolean visible ) {
        fields.forEach( field -> checkFieldVisibility( field, visible ) );
    }

    public boolean isRequiredUserLoggedIn( UserCredentials expectedUser ) {
        String loggedInUsername = getLoggedInUsername();
        return expectedUser.getUsername().equals( loggedInUsername );
    }

    public void setTokenValidationMode( String mode ) {
        String originalMode = System.getProperty("token.validation.mode");
        Serenity.setSessionVariable( ORIGINAL_TOKEN_VALIDATION_MODE).to( originalMode );

        LOGGER.info("Token validation mode changing: {} → {}",
                originalMode != null ? originalMode : "unset (default)",
                mode);

        System.setProperty("token.validation.mode", mode.toUpperCase());
    }
}
