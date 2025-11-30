/*
 * -GEHC CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2025, 2025, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE HealthCare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 */
package com.ge.onchron.verif.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;


public class ReportTabPage extends SimplePage {

    @FindBy( css = "div.header" )
    private WebElementFacade header;

    @FindBy( css = "div.header span" )
    private WebElementFacade title;

    @FindBy( css = "a[type='button'].download-button" )
    private WebElementFacade downloadButton;

    public void waitForXNewTabToOpen( int expectedNewTabCount, int originalTabCount ) {
        Wait<WebDriver> wait = new FluentWait<>( getDriver() )
                .withTimeout( Duration.ofSeconds( 30 ) )
                .pollingEvery( Duration.ofMillis( 500 ) )
                .ignoring( NoSuchElementException.class );

        int expectedTabCount = originalTabCount + expectedNewTabCount;

        wait.until( driver -> {
            int currentTabCount = driver.getWindowHandles().size();
            return currentTabCount >= expectedTabCount;
        } );
    }

    public boolean isContentLoaded() {
        WebElement iframe = getDriver().findElement( By.tagName( "iframe" ) );
        return iframe.getAttribute( "src" ).startsWith( "blob:" );
    }

    public String getHeaderText() {
        return title.getText();
    }

    public void clickDownloadOnHeader() {
        elementClicker( downloadButton );
    }

}
