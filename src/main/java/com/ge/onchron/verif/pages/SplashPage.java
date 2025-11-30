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
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.webdriver.WebDriverFacade;

import static com.ge.onchron.verif.pages.utils.PageUtils.currentlyContainsElements;

public class SplashPage extends SimplePage {

    private final String SIGN_IN_SCREEN_CLASS = "Login-module_container";
    private final String SIGN_IN_BUTTON_CLASS = "button";

    @FindBy( css = "[class*='" + SIGN_IN_SCREEN_CLASS + "']" )
    private WebElementFacade signInScreen;

    @FindBy( className = SIGN_IN_BUTTON_CLASS )
    private WebElementFacade signInButton;

    public boolean splashPageIsVisible() {
        return signInScreen.waitUntilVisible().isVisible();
    }

    public void clickSignIn() {
        elementClicker( signInButton.waitUntilClickable() );
    }

    public void ignoreCertIssue() {
        // Needed for Internet Explorer 11
        WebDriver webdriver = Serenity.getWebdriverManager().getWebdriver();
        boolean titleAvailable = currentlyContainsElements( find( By.tagName( "body" ) ), By.className( "title" ) );
        if ( titleAvailable && find( By.className( "title" ) ).getText().equals( "This site is not secure" ) ) {
            ((WebDriverFacade) webdriver).executeScript( "javascript:document.getElementById('overridelink').click()" );
        }
    }

    public void waitForLoginModule() {
        WebDriver driver = getDriver();
        Wait<WebDriver> wait = new FluentWait<>( driver )
                .withTimeout( Duration.ofSeconds( 10 ) )
                .pollingEvery( Duration.ofMillis( 100 ) )
                .ignoring( NoSuchElementException.class );
        wait.until( ExpectedConditions.visibilityOfElementLocated( By.cssSelector( "[class*='Login-module_button']" ) ) );
    }
}
