/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2024, 2024, GE Healthcare
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.ge.onchron.verif.model.Badge;
import com.ge.onchron.verif.pages.sections.toolbar.SummaryToolbar;

import static com.ge.onchron.verif.TestSystemParameters.USER_SETTINGS_CHANGED;
import static com.ge.onchron.verif.utils.Utils.waitMillis;
import static net.serenitybdd.core.Serenity.setSessionVariable;

public class SummaryToolbarSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger( SummaryToolbarSteps.class );

    private SummaryToolbar summaryToolbar;

    public void checkNavigationToolbarIsDisplayed() {
        assertTrue( "Navigation toolbar is not displayed", summaryToolbar.isVisible() );
    }

    public void clickMoreButtonOnNavigationToolbar() {
        summaryToolbar.clickMoreButton();
    }

    public void checkMoreMenuVisibility( boolean isOpened ) {
        boolean moreMenuVisible = summaryToolbar.isMoreMenuOpen();
        LOGGER.info( "More menu on navigation toolbar visibility is: {}", moreMenuVisible );
        assertThat( "More menu visibility is not ok.", moreMenuVisible, is( equalTo( isOpened ) ) );
    }

    public void checkHideEmptyCardsCheckbox( boolean isChecked ) {
        openMoreMenu();
        boolean state = summaryToolbar.getHideEmptyCardsCheckboxState();
        LOGGER.info( "The 'Hide empty cards' checkbox actual state is: {}", state );
        assertEquals( "Hide empty cards checkbox is not in the expected state\n", isChecked, state );
    }

    private void openMoreMenu() {
        if ( !summaryToolbar.isMoreMenuOpen() ) {
            summaryToolbar.clickMoreButton();
        }
    }

    public void checkNavigationToolbarLinks( List<String> navigationLinks ) {
        List<String> observedList = summaryToolbar.getLinkList();
        LOGGER.info( "Observed list of section links: {}", observedList );
        assertEquals( "Navigation links do not match expected", navigationLinks, observedList );
    }

    public void checkNoLinksAreOnTheNavigationToolbar() {
        List<String> observedList = summaryToolbar.getLinkList();
        LOGGER.info( "Observed list of section links {}", observedList.isEmpty() ? "is empty" : ":" + observedList );
        assertThat( "Observed navigation toolbar links are not empty", observedList.isEmpty() );
    }

    public void clickSectionLinkOnToolbar( String section ) {
        summaryToolbar.clickSectionLink( section );
    }

    public void checkURLContainsSection( String urlTag ) {
        String url = summaryToolbar.getCurrentURL();
        LOGGER.info( "Current URL is {}", url );
        assertTrue( "The section tag is not in the URL", url.contains( urlTag ) );
    }

    public void setHideEmptyCardsVisibility( boolean isSelected ) {
        openMoreMenu();
        summaryToolbar.setHideEmptyCardsCheckboxState( isSelected );
        setSessionVariable( USER_SETTINGS_CHANGED ).to( true );
        waitMillis( 300 );
    }

    public void checkBadgeOnNavigationToolbar( Badge expectedBadge ) {
        Badge observedBadge = summaryToolbar.getBadge();
        assertEquals( "Could not found badge on the Navigation toolbar", expectedBadge, observedBadge );
        assertEquals( "Badge color is not expected", expectedBadge.getColor(), observedBadge.getColor() );
    }

    public void checkNoBadgeIsDisplayedOnNavigationToolbar() {
        Badge observedBadge = summaryToolbar.getBadge();
        assertNull( STR."No badge should be displayed on the navigation toolbar but was:\{observedBadge}", observedBadge );
    }

    public void clickItemInViewOptions( String optionName ) {
        summaryToolbar.clickItemInViewOptions( optionName );
    }

    public void clickItemInViewOptionsSubMenu( String optionName ) {
        summaryToolbar.clickItemInViewOptionsSubMenu( optionName );
    }

}
