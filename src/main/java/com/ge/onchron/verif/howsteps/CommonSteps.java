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
package com.ge.onchron.verif.howsteps;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.UserCredentials;
import com.ge.onchron.verif.pages.ErrorPage;
import com.ge.onchron.verif.pages.PatientPage;
import com.ge.onchron.verif.pages.SimplePage;
import com.ge.onchron.verif.pages.sections.UserSideNavMenu;
import com.jayway.jsonpath.JsonPath;
import com.nimbusds.jwt.SignedJWT;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;

import static com.ge.onchron.verif.TestSystemParameters.ACCESS_TOKEN_FROM_COOKIE;
import static com.ge.onchron.verif.TestSystemParameters.TEST_DATA_CLASSPATH;
import static com.ge.onchron.verif.pages.utils.PageUtils.getBrowserTabCount;
import static com.ge.onchron.verif.pages.utils.PageUtils.getNewTabUrl;
import static com.ge.onchron.verif.utils.FileUtils.readFromFile;
import static com.ge.onchron.verif.utils.UserCredentialsUtils.getTestUsersJson;
import static com.ge.onchron.verif.utils.Utils.insertSystemParameters;
import static com.ge.onchron.verif.utils.Utils.waitMillis;
import static net.serenitybdd.core.Serenity.getDriver;
import static net.serenitybdd.core.Serenity.setSessionVariable;

public class CommonSteps {

    private SimplePage simplePage;
    private ErrorPage errorPage;
    private UserSideNavMenu userMenu;

    @Steps
    PatientPage patientPage;

    private static final Logger LOGGER = LoggerFactory.getLogger( CommonSteps.class );

    private final String NAME_OF_ACCESS_TOKENS_IN_COOKIE = "access_token";
    private final String CURRENT_URL_PLACEHOLDER = "CURRENT_URL";

    public void goToUrl( String url ) {
        url = url.contains( CURRENT_URL_PLACEHOLDER ) ? url.replace( CURRENT_URL_PLACEHOLDER, getDriver().getCurrentUrl() ) : insertSystemParameters( url );
        Serenity.getWebdriverManager().getWebdriver().navigate().to( url );
    }

    public void storeAccessTokenFromCookie() {
        WebDriver webdriver = Serenity.getWebdriverManager().getWebdriver();
        Cookie cookie = getDriver().manage().getCookieNamed( NAME_OF_ACCESS_TOKENS_IN_COOKIE );
        String accessToken = cookie.getValue();
        LOGGER.info( "access token parameter from browser's cookie: " + accessToken );
        setSessionVariable( ACCESS_TOKEN_FROM_COOKIE ).to( accessToken );
    }

    public void checkNewBrowserTab( String expectedUrl ) {
        String newTabUrl = getNewTabUrl();
        LOGGER.info( STR."New Tab Url:\{newTabUrl}" );
        assertEquals( "URL of the new browser tab is not ok.", expectedUrl, newTabUrl );
    }

    public void checkNewBrowserTabOpened( boolean isOpened ) {
        int browserTabCount = getBrowserTabCount();
        LOGGER.info( STR."number of browser tabs is: \{browserTabCount}" );
        if ( isOpened ) {
            assertThat( "New browser tab is not opened", browserTabCount, greaterThan( 1 ) );
        } else {
            assertThat( "New browser tab is opened", browserTabCount, is( 1 ) );
        }
    }

    public void checkPageText( String pageOrModal, String filename ) {
        String actualText = pageOrModal.equals( "page" ) ? simplePage.getPageText() : simplePage.getModalText();
        String expectedText = readFromFile( TEST_DATA_CLASSPATH + filename ).replace( "\r", "" ).replace( "[USER_NAME]", userMenu.getUsername() );
        LOGGER.info( STR."pageText: \{actualText}\n, expectedText: \{expectedText}" );
        assertEquals( STR."Page text does not match the reference file \{filename}\n", expectedText, actualText );
    }

    public void refreshPage() {
        getDriver().navigate().refresh();
    }

    public void checkFullScreenError( String expectedErrorMessage ) {
        String errorMessage = errorPage.getErrorMessage();
        LOGGER.info( STR."Full Screen error message: \{errorMessage}" );
        assertEquals( "Error message mismatch on error screen.", expectedErrorMessage, errorMessage );
    }

    public void checkPopUpError( String expectedErrorMessage ) {
        String errorMessage = errorPage.getAdminConsoleErrorMessage();
        LOGGER.info( STR."Screen error message: \{errorMessage}" );
        assertTrue( "Error message mismatch on error screen.", errorMessage.contains( expectedErrorMessage ) );
        errorPage.closePopUP();
    }

    public void waitInMillis( int waitInMillis ) {
        waitMillis( waitInMillis );
    }

    public void openNewBrowserTab() {
        Serenity.getDriver().switchTo().newWindow( WindowType.TAB );
    }

    public void storeUserParameter( UserCredentials user, String parameterName, String storedParameterName ) {
        String testUsersJson = getTestUsersJson();
        String parameterPath = String.format( "$.[?(@.alias=='%s')].%s", user.getAlias(), parameterName );
        String parameterValue = new ArrayList<>( JsonPath.read( testUsersJson, parameterPath ) ).getFirst().toString();
        setSessionVariable( storedParameterName ).to( parameterValue );
    }
    public void reopenBrowserWindow() throws InterruptedException {
        String originalWindow  = getDriver().getCurrentUrl();
        saveCookies(getDriver(), "cookies.ser");
        getDriver().close();
        goToUrl( originalWindow );
        loadCookies(getDriver(), "cookies.ser");
        Thread.sleep(2000);
    }

    // Method to save cookies to a file
    public void saveCookies(WebDriver driver, String filePath) {
        Set<Cookie> cookies = driver.manage().getCookies();
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(cookies);
        } catch (IOException e) {
            LOGGER.info(STR."Failed to save cookies \{e.getMessage()}");
            fail("Failed to save cookies");
        }
    }

    // Method to load cookies from a file and add them to a new session
    public void loadCookies(WebDriver driver, String filePath) {
        File cookieFile = new File(filePath);
        if (cookieFile.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(cookieFile))) {
                Set<Cookie> cookies = (Set<Cookie>) in.readObject();
                for (Cookie cookie : cookies) {
                    driver.manage().addCookie(cookie);
                }
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.info(STR."Failed to load cookies \{e.getMessage()}");
                fail("Failed to load cookies");
            }
        }
    }
    public void shrinkWindow() {
        getDriver().manage().window().setSize(new Dimension(1000,1000));
    }

    public void validateTokenExpirationTime( int maxMinutes ) {
        String accessToken = Serenity.sessionVariableCalled( ACCESS_TOKEN_FROM_COOKIE );
        try {
            SignedJWT jwt = SignedJWT.parse( accessToken );
            Date expiry = jwt.getJWTClaimsSet().getExpirationTime();
            Date now = new Date();

            long secondsRemaining = ( expiry.getTime() - now.getTime() ) / 1000;
            long minutesRemaining = secondsRemaining / 60;
            boolean isExpired = expiry.before(now);

            if ( isExpired ) {
                fail( "Token is already expired!" );
            }
            if (secondsRemaining > ( maxMinutes * 60 )) {
                fail( STR."Token expires in \{minutesRemaining}min (>\{maxMinutes}min). Expected ≤\{maxMinutes}min." );
            }
            LOGGER.info( "Token expires in {}s ({}min) - within {}min limit",
                    secondsRemaining, minutesRemaining, maxMinutes );
        } catch ( ParseException e ) {
            fail( STR."Failed to parse access token\{e.getMessage()}" );
        }
    }
}
