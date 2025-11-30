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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

import com.ge.onchron.verif.pages.sections.TabSelector;

import static com.ge.onchron.verif.pages.utils.PageElementUtils.waitForCompleteDocument;

public class TabNavigationSteps {

    private TabSelector tabSelector;
    private static final Logger LOGGER = LoggerFactory.getLogger( TabNavigationSteps.class );

    public void navigateTo( String tabName ) {
        tabSelector.clickOnTab( tabName );
        tabSelector.waitForAngularRequestsToFinish();
        waitForCompleteDocument();
    }

    public void checkCounterBadgeOnTab( int expectedCounter, String tabName ) {
        int counter = tabSelector.getCounterFromBadgeOnTab( tabName );
        LOGGER.info( STR."Actual counter: \{counter}" );
        assertEquals( STR."Counter badge displays '\{counter}' instead of '\{expectedCounter}' on '\{tabName}' tab", expectedCounter, counter );
    }

    public void closeResponseTab() {
        tabSelector.clickOnResponseX();
    }

    public void checkTabState( String tabName, boolean expectedState ) {
        boolean tabState = tabSelector.isTabOpen(tabName);
        LOGGER.info( STR."Actual state of \{tabName} tab on the navigation bar: \{tabState ? "visible" : "not visible"}" );
        assertEquals( "Tab state is not expected on the navigation bar", expectedState, tabState );
    }

    public void onlyNavigateTo( String tabName ) {
        tabSelector.clickOnTab( tabName );
    }
}
