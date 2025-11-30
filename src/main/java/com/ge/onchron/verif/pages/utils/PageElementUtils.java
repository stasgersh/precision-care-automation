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
package com.ge.onchron.verif.pages.utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.fail;

import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.utils.Utils.waitMillis;
import static net.serenitybdd.core.Serenity.getDriver;

public class PageElementUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger( PageElementUtils.class );

    public static Boolean isVisibleInViewport( WebElement element ) {
        WebDriver driver = ((RemoteWebElement) element).getWrappedDriver();
        return (Boolean) ((JavascriptExecutor) driver).executeScript(
                "var elem = arguments[0],                 " +
                        "  box = elem.getBoundingClientRect(),    " +
                        "  cx = box.left + box.width / 2,         " +
                        "  cy = box.top + box.height / 2,         " +
                        "  e = document.elementFromPoint(cx, cy); " +
                        "for (; e; e = e.parentElement) {         " +
                        "  if (e === elem)                        " +
                        "    return true;                         " +
                        "}                                        " +
                        "return false;                            "
                , element );
    }

    public static void waitForCompleteDocument() {
        new WebDriverWait( getDriver(), Duration.ofSeconds( 30 ) ).until( webDriver -> ((JavascriptExecutor) webDriver).executeScript( "return document.readyState" ).equals( "complete" ) );
    }

    public static void moveToElement( WebElementFacade webElement ) {
        Actions actions = new Actions( getDriver() );
        actions.moveToElement( webElement );
        actions.build().perform();
    }

    public static void moveToElementJS( WebElementFacade webElement ) {
        scrollIntoViewport( webElement );
        hoverElementJS( webElement );
    }

    public static void scrollIntoViewport( WebElement webElement ) {
        ((JavascriptExecutor) getDriver()).executeScript( "arguments[0].scrollIntoViewIfNeeded(true);", webElement );
    }

    public static void hoverElementJS( WebElement webElement ) {
        String javaScript = "var evObj = document.createEvent('MouseEvents');" +
                "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
                "arguments[0].dispatchEvent(evObj);";
        ((JavascriptExecutor) getDriver()).executeScript( javaScript, webElement );
    }

    public static void dispatchMouseClickElementJS( WebElement webElement ) {
        String javaScript = "arguments[0].dispatchEvent(new MouseEvent('click', { " +
                "view: window, bubbles: true, cancelable: true, buttons: 1 }));";
        ((JavascriptExecutor) getDriver()).executeScript( javaScript, webElement );
    }

    public static void unhoverElementJS( WebElement webElement ) {
        String javaScript = "var evObj = document.createEvent('MouseEvents');" +
                "evObj.initMouseEvent(\"mouseout\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
                "arguments[0].dispatchEvent(evObj);";
        ((JavascriptExecutor) getDriver()).executeScript( javaScript, webElement );
    }

    public static void waitForLoadingIndicatorDisappears() {
        WebDriver driver = getDriver();
        Wait<WebDriver> waitToAppear = new FluentWait<>( driver )
                .withTimeout( Duration.ofSeconds( 3 ) )
                .pollingEvery( Duration.ofMillis( 100 ) )
                .ignoring( NoSuchElementException.class );
        WebElement indicator = null;
        try {
            indicator = waitToAppear.until( ExpectedConditions.visibilityOfElementLocated( By.cssSelector( "[class*='indicator__circular']" ) ) );
        } catch ( TimeoutException ex ) {
            // ignore timeout in case loaded too fast - indicator never appeared
            return;
        }
        // Only wait for disappearance if indicator was found
        if ( indicator != null ) {
            Wait<WebDriver> waitToDisappear = new FluentWait<>( driver )
                    .withTimeout( Duration.ofSeconds( 20 ) )
                    .pollingEvery( Duration.ofMillis( 100 ) )
                    .ignoring( NoSuchElementException.class, StaleElementReferenceException.class );
            waitToDisappear.until( ExpectedConditions.stalenessOf( indicator ) );
        }
    }

    public static WebElement getNonStaleElement( By by ) {
        int attempts = 0;
        while ( attempts < 3 )
            try {
                WebElement element = getDriver().findElement( by );
                if ( element.isDisplayed() ) {
                    return element;
                }
            } catch ( StaleElementReferenceException e ) {
                waitForCompleteDocument();
                waitMillis( 1000 );
                attempts++;
            }
        return null;
    }

    public static boolean hasVerticalScrollbar( WebElementFacade webElement ) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        return (boolean) js.executeScript(
                "return arguments[0].scrollHeight > arguments[0].clientHeight;", webElement );
    }

    public static void hoverOnWebElement( WebElementFacade webElement ) {
        List<WebElementFacade> childElements = webElement.thenFindAll( By.xpath( "child::*" ) );
        WebElementFacade valueElement = childElements.getLast();

        Actions actions = new Actions( getDriver() );
        actions.moveToElement( webElement );

        final int maxRetries = 5;
        int tries = 0;

        while ( tries < maxRetries ) {
            try {
                actions.moveToElement( valueElement ).perform();
                if ( isTooltipDisplayed( By.cssSelector( "[class*='ClpTooltip-module_tooltipContainer']" ) ) ||
                        isTooltipDisplayed( By.id( "Notes_-_16" ) ) ) {
                    LOGGER.info( "Tooltip successfully displayed." );
                    return; // Exit method if a tooltip is displayed
                }
                waitMillis( 200 );
                tries++;

            } catch ( NoSuchElementException e ) {
                LOGGER.error( String.format( "Tooltip container not found on try %d.", tries + 1 ) );
                tries++;
            } catch ( ElementNotInteractableException e ) {
                LOGGER.error( String.format( "Element not interactable on try %d.", tries + 1 ) );
                tries++;
            } catch ( Exception e ) {
                LOGGER.error( String.format( "Unexpected error on try %d: %s", tries + 1, e.getMessage() ) );
                tries++;
            }
        }
        fail( STR."Could not hover on CLP text after \{maxRetries} tries" );
    }

    /**
     * Utility method to check if a tooltip is displayed.
     *
     * @param by The locator of the tooltip element.
     * @return true if the tooltip is displayed, false otherwise.
     */
    private static boolean isTooltipDisplayed( By by ) {
        try {
            WebElement tooltipContainer = getDriver().findElement( by );
            return tooltipContainer.isDisplayed();
        } catch ( NoSuchElementException e ) {
            return false;
        }
    }
}
