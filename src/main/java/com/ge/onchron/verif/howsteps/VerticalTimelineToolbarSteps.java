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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.ge.onchron.verif.model.FilterDataCheckbox;
import com.ge.onchron.verif.model.enums.ZoomDirection;
import com.ge.onchron.verif.pages.sections.toolbar.VerticalTimelineToolbar;

import static com.ge.onchron.verif.pages.utils.PageElementUtils.waitForCompleteDocument;
import static com.ge.onchron.verif.utils.Utils.waitMillis;

public class VerticalTimelineToolbarSteps {

    private VerticalTimelineToolbar verticalTimelineToolbar;
    private static final Logger LOGGER = LoggerFactory.getLogger( VerticalTimelineToolbarSteps.class );

    public void checkVerticalTimelineToolbarVisibility( boolean isDisplayed ) {
        boolean verticalToolbarVisibility = verticalTimelineToolbar.isDisplayed();
        LOGGER.info(STR."The actual vertical toolbar visibility is: \{verticalToolbarVisibility}");
        assertEquals(STR."Vertical timeline toolbar is \{isDisplayed ? "not displayed " : "displayed"}", isDisplayed, verticalToolbarVisibility );
    }

    public void clicksVerToolbarItem( String toolbarItem ) {
        verticalTimelineToolbar.clickMenuItemByName( toolbarItem );
    }

    public void setFullTimeRange() {
        verticalTimelineToolbar.setFullTimeRange();
        waitMillis( 1200 ); // waiting for finishing cluster animation
    }

    public void setLast30daysTimeRange() {
        verticalTimelineToolbar.setLast30daysTimeRange();
        waitMillis( 1200 ); // waiting for finishing cluster animation
    }

    public void setLast90daysTimeRange() {
        verticalTimelineToolbar.setLast90daysTimeRange();
        waitMillis( 1200 ); // waiting for finishing cluster animation
    }

    public void zoomOnVerticalToolbar( ZoomDirection zoomDirection, int count ) {
        for ( int i = 0; i < count; i++ ) {
            clicksVerToolbarItem( zoomDirection.equals( ZoomDirection.ZOOM_IN ) ? "Zoom in" : "Zoom out" );
        }
        waitForCompleteDocument();
        waitMillis( 1000 ); // waiting for finishing cluster animation
    }

    public void setTimelineFiltersOnFilterModal( List<FilterDataCheckbox> checkboxes ) {
        verticalTimelineToolbar.openFilterModal();
        checkboxes.forEach( checkbox -> verticalTimelineToolbar.setCheckboxOnFilterModal( checkbox ) );
        verticalTimelineToolbar.closeFilterModal();
    }

    public void checkFilterCounterIconVisibility( Boolean isDisplayed ) {
        String numberOnFilterCounterIcon = verticalTimelineToolbar.numFromFilterCounterIcon();
        boolean badgeIsVisible = numberOnFilterCounterIcon != null;
        LOGGER.info(STR."The actual filter counter icon is visible: \{badgeIsVisible}");
        assertThat( "Filter counter icon visibility is not ok on vertical toolbar", badgeIsVisible, is( equalTo( isDisplayed ) ) );
    }

    public void checkFilterCounterIcon( Integer expNumber ) {
        String numberOnFilterCounterIcon = verticalTimelineToolbar.numFromFilterCounterIcon();
        LOGGER.info(STR."The actual number on filter counter icon is: \{numberOnFilterCounterIcon}");
        assertNotNull( "Filter counter icon was not found on vertical toolbar", numberOnFilterCounterIcon );
        assertEquals( "Number on filter counter icon is not ok on vertical toolbar", expNumber, Integer.valueOf( numberOnFilterCounterIcon ) );
    }

    public void checkFilterCounterIconBadgeColor( String expectedColor ) {
        String iconBadgeColor = verticalTimelineToolbar.getColorFromFilterCounterIcon();
        LOGGER.info( STR."The actual color on the filter counter icon is: \{iconBadgeColor}" );
        assertEquals( "Actual color on filter counter icon is not ok on timeline toolbar", expectedColor, iconBadgeColor );
    }
}
