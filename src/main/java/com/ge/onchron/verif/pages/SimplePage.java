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

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.fail;

import com.ge.onchron.verif.TestSystemParameters;
import com.ge.onchron.verif.utils.Utils;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.TestSystemParameters.UI_PERFORMANCE_TEST;
import static com.ge.onchron.verif.pages.utils.PageUtils.currentlyContainsWebElements;
import static com.ge.onchron.verif.pages.utils.PageUtils.waitUntilElementDisappears;
import static com.ge.onchron.verif.utils.Utils.waitMillis;

public class SimplePage extends PageObject {

    private static final Logger LOGGER = LoggerFactory.getLogger( SimplePage.class );
    private static final int MAX_LOADING_SKELETON_WAITING_TIME_IN_MILLIS = 80000;

    public void elementClicker( WebElementFacade element ) {
        if ( TestSystemParameters.getSystemParameter( UI_PERFORMANCE_TEST ).equals( "Yes" ) ) {
            elementClickerWithPerformanceMeasurement( element );
        } else {
            elementClickerWithWait( element );
        }
    }

    private void elementClickerWithPerformanceMeasurement( WebElementFacade element ) {
        long startTime = System.nanoTime();
        elementClickerWithWait( element );
        long stopTime = System.nanoTime();
        int performance = (int) ((stopTime - startTime) / 1000000);
        Utils.savePerformance( performance );
    }

    public void elementClickerWithWait( WebElementFacade element ) {
        try {
            element.waitUntilVisible().click();
            waitForAngularRequestsToFinish();
        } catch ( Exception e ) {
            fail( STR."Could not click on \{element.getText()} element\{e.toString()}" );
        }
    }

    public static void waitUntilLoadingSkeletonDisappears( WebElementFacade parentElement, By skeletonLocator ) {
        try {
            LOGGER.info( "Waiting for loading skeleton to disappear" );
            waitUntilElementDisappears( parentElement.getWrappedElement(), skeletonLocator, MAX_LOADING_SKELETON_WAITING_TIME_IN_MILLIS, "Loading skeleton did not disappear during the waiting period." );
        } catch ( StaleElementReferenceException e ) {
            LOGGER.warn( "Element disappeared or reloaded while waiting for element disappearing, check it once again" );
            boolean elementIsVisible = currentlyContainsWebElements( parentElement, skeletonLocator );
            if ( elementIsVisible ) {
                fail( STR."Element did not disappear after a StaleElementReferenceException: \{skeletonLocator.toString()}" );
            }
        }
    }

    public String getPageText() {
        waitMillis( 500 );
        return find( By.id( "appRoot" ) ).getText();
    }

    public String getModalText() {
        waitMillis( 500 );
        return find( By.className( "modal" ) ).getText();
    }

    public String getCurrentURL() {
        return getDriver().getCurrentUrl();
    }
}
