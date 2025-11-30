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
package com.ge.onchron.verif.pages.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.fail;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.webdriver.WebDriverFactory;

import static com.ge.onchron.verif.utils.Utils.waitingTimeExpired;
import static net.serenitybdd.core.Serenity.getDriver;

public class PageUtils {

    /**
     * Check existence of an element by using Serenity WebElementFacade
     *
     * @param element Serenity's WebElementFacade
     * @param by      locator
     * @return boolean
     */
    public static boolean currentlyContainsElements( WebElementFacade element, By by ) {
        element.setImplicitTimeout( Duration.ofSeconds( 0 ) );
        boolean contains = element.containsElements( by );
        element.resetTimeouts();
        return contains;
    }

    /**
     * Check existence of an element by using pure Selenium WebElement
     *
     * @param element Selenium's WebElement
     * @param by      locator
     * @return boolean
     */
    public static boolean currentlyContainsWebElements( WebElement element, By by ) {
        WebDriverFactory webDriverFactory = new WebDriverFactory();
        Duration originalImplicitWaitTimeout = webDriverFactory.currentTimeoutFor( Serenity.getDriver() );
        Serenity.getDriver().manage().timeouts().implicitlyWait( Duration.ofSeconds( 0 ) );
        List<WebElement> elements = element.findElements( by );
        Serenity.getDriver().manage().timeouts().implicitlyWait( originalImplicitWaitTimeout );
        return !elements.isEmpty();
    }

    public static void executeWithZeroImplicitTimeout( Runnable function ) {
        WebDriverFactory webDriverFactory = new WebDriverFactory();
        Duration originalImplicitWaitTimeout = webDriverFactory.currentTimeoutFor( Serenity.getDriver() );
        Serenity.getDriver().manage().timeouts().implicitlyWait( Duration.ofSeconds( 0 ) );
        function.run();
        Serenity.getDriver().manage().timeouts().implicitlyWait( originalImplicitWaitTimeout );
    }

    public static void waitUntilElementDisappears( WebElement parentElement, By locator, int maxWaitingTimeInMillis, String customErrorMsg ) {
        long startTime = System.currentTimeMillis();
        boolean elementIsVisible;
        boolean waitingTimeExpired;
        do {
            elementIsVisible = currentlyContainsWebElements( parentElement, locator );
            waitingTimeExpired = waitingTimeExpired( startTime, maxWaitingTimeInMillis );
        } while ( elementIsVisible && !waitingTimeExpired );

        if ( waitingTimeExpired ) {
            String errorMsg = customErrorMsg != null ? customErrorMsg : "Element did not disappear during the waiting period: " + locator.toString();
            fail( errorMsg );
        }
    }

    public static int getBrowserTabCount() {
        return getBrowserTabs().size();
    }

    public static String getNewTabUrl() {
        List<String> browserTabs = getBrowserTabs();
        assertThat( "New browser tab is not opened", browserTabs.size(), greaterThan( 1 ) );
        getDriver().switchTo().window( browserTabs.get( 1 ) );
        String url = getDriver().getCurrentUrl();
        getDriver().close();
        getDriver().switchTo().window( browserTabs.get( 0 ) );
        return url;
    }

    public static void switchToNewestTab() {
        List<String> browserTabs = getBrowserTabs();
        getDriver().switchTo().window( browserTabs.get( browserTabs.size() - 1 ) );
    }

    public static void closeCurrentTabAndSwitchToFirstTab() {
        getDriver().close();
        getDriver().switchTo().window( getDriver().getWindowHandles().iterator().next() );
    }

    public static String getTabTitle() {
        return getDriver().getTitle();
    }

    private static List<String> getBrowserTabs() {
        return new ArrayList<>( getDriver().getWindowHandles() );
    }

    public static void waitForDOMToBeStable( int stablePeriodInSeconds ) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        long stablePeriodMillis = stablePeriodInSeconds * 1000;
        long stableStartTime = 0;
        long maxWaitTimeMillis = 20000;
        long startTime = System.currentTimeMillis();

        while ( true ) {
            boolean isStable = (Boolean) js.executeScript(
                    "return (window.performance.timing.loadEventEnd > 0 && " +
                            "document.readyState === 'complete' && " +
                            "document.querySelectorAll('*').length > 0);"
            );
            long currentTime = System.currentTimeMillis();
            if ( isStable ) {
                if ( stableStartTime == 0 ) {
                    stableStartTime = currentTime;
                } else if ( currentTime - stableStartTime >= stablePeriodMillis ) {
                    break; // DOM is stable for the specified period
                }
            } else {
                stableStartTime = 0; // Reset the timer if the DOM is not stable
            }
            if ( currentTime - startTime >= maxWaitTimeMillis ) {
                break; // Exit if the maximum wait time is exceeded
            }
            try {
                Thread.sleep( 200 );
            } catch ( InterruptedException e ) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static List<Object> getWebElementChildNodes( WebElementFacade parent ) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        return (List<Object>) js.executeScript(
                "var parent = arguments[0];" +
                        "var childNodes = parent.childNodes;" +
                        "var nodes = [];" +
                        "for (var i = 0; i < childNodes.length; i++) {" +
                        "  if (childNodes[i].nodeType === Node.ELEMENT_NODE) {" +
                        "    nodes.push(childNodes[i]);" +
                        "  } else if (childNodes[i].nodeType === Node.TEXT_NODE && childNodes[i].nodeValue.trim() !== '') {" +
                        "    nodes.push(childNodes[i].nodeValue.trim());" +
                        "  }" +
                        "}" +
                        "return nodes;", parent
        );
    }

    public static String formatXPath( String template, String value ) {
        return String.format( template, escapeXPathValue( value ) );
    }

    private static String escapeXPathValue( String value ) {
        return value.replace( "'", "\\'" ); // crude example, customize based on framework
    }

    public static void switchToTabByIndex( int targetTab ) {
        getDriver().switchTo().window( getBrowserTabs().get( targetTab ) );
    }
}