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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.CheckboxModel;
import com.ge.onchron.verif.model.FilterDataCheckbox;
import com.ge.onchron.verif.model.enums.ZoomDirection;
import com.ge.onchron.verif.pages.sections.toolbar.TimelineToolbar;
import com.ge.onchron.verif.pages.tabs.TimeLine;

import static com.ge.onchron.verif.model.enums.ZoomDirection.ZOOM_IN;
import static com.ge.onchron.verif.model.enums.ZoomDirection.ZOOM_OUT;
import static com.ge.onchron.verif.pages.elements.CheckboxState.SELECTED;
import static com.ge.onchron.verif.pages.elements.CheckboxState.UNSELECTED;
import static com.ge.onchron.verif.pages.utils.PageElementUtils.waitForCompleteDocument;
import static com.ge.onchron.verif.utils.Utils.waitMillis;
import static com.ge.onchron.verif.utils.Utils.waitingTimeExpired;

public class TimelineToolbarSteps {

    private static final int MAX_ALLOWED_ZOOM_TIME_IN_MILLIS = 30000;
    private static final Logger LOGGER = LoggerFactory.getLogger( TimelineToolbarSteps.class );

    private TimeLine timeLine;
    private TimelineToolbar timelineToolbar;

    public void checkTimelineToolbarVisibility( Boolean isVisible ) {
        boolean actuallyVisible = timelineToolbar.isVisible();
        LOGGER.info( STR."TimeLine Toolbar is visible: \{actuallyVisible}" );
        assertThat( "Patient toolbar visibility is not ok.", actuallyVisible, is( equalTo( isVisible ) ) );
    }

    public void openFilterData() {
        timelineToolbar.openFilterModal();
    }

    public void closeFilterModal() {
        timelineToolbar.closeFilterModal();
    }

    public void setTimelineFiltersOnFilterModal( List<FilterDataCheckbox> checkboxes ) {
        timeLine.waitUntilTimelineLoadingSkeletonDisappears();
        timelineToolbar.openFilterModal();
        checkboxes.forEach( checkbox -> timelineToolbar.setCheckboxOnFilterModal( checkbox ) );
    }

    public void checkFilterOptionsOnFilterModal( boolean isDisplayed, List<FilterDataCheckbox> expectedFilterOptions ) {
        expectedFilterOptions.forEach( expectedFilterOption -> {
            String actualFilterGroup = expectedFilterOption.getFilterGroup();
            List<CheckboxModel> observedFilterOptions = timelineToolbar.getCheckboxesFromFilterModalGroup( actualFilterGroup );
            LOGGER.info( STR."The actual filter options are: \{observedFilterOptions}" );
            assertThat(
                    String.format( "Filter options on Filter modal do not match: Expected but not found: %s, \n Observed options: %s", expectedFilterOption, observedFilterOptions ),
                    observedFilterOptions.contains( expectedFilterOption.getCheckboxModel() ), is( equalTo( isDisplayed ) ) );
        } );
    }

    public void checkFilterOptionsOnFilterModalStrict( String groupName, List<CheckboxModel> expectedGroupItems ) {
        List<CheckboxModel> observedFilterOptions = timelineToolbar.getCheckboxesFromFilterModalGroup( groupName );
        LOGGER.info( STR."The actual filter options are: \{observedFilterOptions}" );
        assertThat( "Filter data options on Filter modal do not match.", observedFilterOptions, is( equalTo( expectedGroupItems ) ) );
    }

    public void typeIntoSearchLabelField( String searchText ) {
        timelineToolbar.typeIntoSearchFilterField( searchText );
    }

    public void checkMessageInFilterGroup( String filterGroupName, String expectedText ) {
        String actualText = timelineToolbar.getTextFromFilterGroup( filterGroupName );
        LOGGER.info( STR."The actual message in group name \{filterGroupName} is: \{actualText}" );
        assertEquals( "Message in Filter group is not ok.", expectedText, actualText );
    }

    public void clickOnResetFiltersOnFilterModal() {
        timelineToolbar.clickResetFilterButton();
    }

    public void zoomInMax() {
        zoomWhileEnabled( ZOOM_IN );
    }

    public void zoomOutMax() {
        zoomWhileEnabled( ZOOM_OUT );
    }

    public void zoom( ZoomDirection zoomDirection, int count ) {
        for ( int i = 0; i < count; i++ ) {
            zoom( zoomDirection );
        }
        waitForCompleteDocument();
        waitMillis( 1000 ); // waiting for finishing cluster animation
    }

    private void zoomWhileEnabled( ZoomDirection zoomDirection ) {
        long startTime = System.currentTimeMillis();
        boolean allowedZoomTimeExpired = false;
        while ( isZoomEnabled( zoomDirection ) ) {
            zoom( zoomDirection );
            allowedZoomTimeExpired = waitingTimeExpired( startTime, MAX_ALLOWED_ZOOM_TIME_IN_MILLIS );
        }
        if ( allowedZoomTimeExpired ) {
            fail( "Zooming out took more than " + MAX_ALLOWED_ZOOM_TIME_IN_MILLIS + " sec" );
        }
        waitForCompleteDocument();
        waitMillis( 1000 ); // waiting for finishing cluster animation
    }

    private boolean isZoomEnabled( ZoomDirection zoom ) {
        if ( zoom.equals( ZOOM_IN ) ) {
            return timelineToolbar.getZoomButtons().isZoomInEnabled();
        } else {
            return timelineToolbar.getZoomButtons().isZoomOutEnabled();
        }
    }

    private void zoom( ZoomDirection zoom ) {
        if ( zoom.equals( ZOOM_IN ) ) {
            timelineToolbar.getZoomButtons().zoomIn();
        } else {
            timelineToolbar.getZoomButtons().zoomOut();
        }
    }

    public void setFullTimeRange() {
        timelineToolbar.setFullTimeRange();
        waitMillis( 1200 ); // waiting for finishing cluster animation
    }

    public void setLast30daysTimeRange() {
        timelineToolbar.setLast30daysTimeRange();
        waitMillis( 1200 ); // waiting for finishing cluster animation
    }

    public void setLast90daysTimeRange() {
        timelineToolbar.setLast90daysTimeRange();
        waitMillis( 1200 ); // waiting for finishing cluster animation
    }

    public void moveZoomSliderByMouseToRightSide() {
        timelineToolbar.moveZoomSliderByMouseToRightSide();
        waitMillis( 1000 ); // waiting for finishing cluster animation
    }

    public void moveZoomSliderByMouseToLeftSide() {
        timelineToolbar.moveZoomSliderByMouseToLeftSide();
        waitMillis( 1000 ); // waiting for finishing cluster animation
    }

    public void isZoomSliderOnLeftSide( boolean isOnLeftSide ) {
        double zoomSiderPos = timelineToolbar.getZoomSliderPosition();
        LOGGER.info( STR."The zoom slider is on the left side: \{zoomSiderPos}" );
        assertThat( "Zoom slider " + (isOnLeftSide ? "is not " : "is ") + "on the left side", zoomSiderPos == 0, is( equalTo( isOnLeftSide ) ) );
    }

    public void isZoomSliderInTheMiddle() {
        double zoomSiderPos = timelineToolbar.getZoomSliderPosition();
        LOGGER.info( STR."The zoom slider position is in the middle: \{zoomSiderPos}" );
        assertEquals( "Zoom slider is not in the middle, actual position: " + zoomSiderPos, 50, zoomSiderPos, 10.0 );
    }

    public void isZoomSliderOnRightSide() {
        double zoomSiderPos = timelineToolbar.getZoomSliderPosition();
        LOGGER.info( STR."The zoom slider position is on the right side: \{zoomSiderPos}" );
        assertEquals( "Zoom slider is not on the right side, actual position: " + zoomSiderPos, 100, zoomSiderPos, 0.0 );
    }

    public void checkFilterCounterIconVisibility( Boolean isDisplayed ) {
        String numFromFilterCounterIcon = timelineToolbar.getNumFromFilterCounterIcon();
        boolean iconIsVisible = numFromFilterCounterIcon != null;
        LOGGER.info( STR."The filter counter icon is visible: \{iconIsVisible}" );
        assertThat( "Filter counter icon visibility is not ok on timeline toolbar", iconIsVisible, is( equalTo( isDisplayed ) ) );
    }

    public void checkFilterCounterIcon( Integer expNumber ) {
        String numFromFilterCounterIcon = timelineToolbar.getNumFromFilterCounterIcon();
        LOGGER.info( STR."The actual number on the filter counter icon is: \{numFromFilterCounterIcon}" );
        assertNotNull( "Filter counter icon was not found on timeline toolbar", numFromFilterCounterIcon );
        assertEquals( "Number on filter counter icon is not ok on timeline toolbar", expNumber, Integer.valueOf( numFromFilterCounterIcon ) );
    }

    public void checkFilterBadgesInFilterList( List<String> expectedFilters ) {
        List<String> observedFilters = timelineToolbar.getFilterNamesFromFilterList();
        LOGGER.info( STR."Actual filters names from filter list: \{observedFilters}" );
        assertEquals( "Filter badges on filter list do not match.", expectedFilters, observedFilters );
    }

    public void checkFilterBadgeVisibilityOnFilterList( Boolean isAvailable ) {
        List<String> observedFilters = timelineToolbar.getFilterNamesFromFilterList();
        LOGGER.info( STR."The actual filters are: \{observedFilters}" );
        assertThat( "Filter badge availability on the timeline toolbar is not ok", !observedFilters.isEmpty(), is( isAvailable ) );
    }

    public void checkMoreFiltersButtonVisibility( Boolean isDisplayed ) {
        boolean isMoreFiltersButtonVisible = timelineToolbar.isMoreFiltersButtonVisible();
        LOGGER.info( STR."The more filters button is visible: \{isMoreFiltersButtonVisible}" );
        assertThat( "More applied filters button visibility is not ok", isMoreFiltersButtonVisible, is( isDisplayed ) );
    }

    public void openMoreFiltersModal() {
        timelineToolbar.openMoreFiltersModal();
    }

    public void removeFilterFromAppliedFilters( List<String> filtersToRemove ) {
        timelineToolbar.removeFilterFromAppliedFiltersList( filtersToRemove );
    }

    public void resetFiltersOnMoreFiltersModal() {
        timelineToolbar.resetFiltersOnMoreFiltersModal();
    }

    public void hideMacroNavigator() {
        timelineToolbar.setHideNavigatorCheckboxState( SELECTED );
    }

    public void showMacroNavigator() {
        timelineToolbar.setHideNavigatorCheckboxState( UNSELECTED );
    }

    public void checkHideNavigatorCheckbox( boolean isChecked ) {
        boolean state = timelineToolbar.getHideNavigatorCheckboxState();
        LOGGER.info( STR."The 'Hide Navigator' checkbox actual state is: \{state}" );
        assertEquals( "Hide navigator checkbox is not in the expected state\n", isChecked, state );
    }

    public void resetTimelineSettings() {
        waitMillis( 500 );
        if ( !timelineToolbar.isMoreMenuOpen() ) {
            timelineToolbar.openMoreMenu();
        }
        timelineToolbar.clickResetToDefaultViewButtonInMoreMenu();
        waitMillis( 1500 );
        timeLine.waitUntilTimelineLoadingSkeletonDisappears();
    }

    public void checkFilterModalGroups( List<String> expectedGroups ) {
        assertEquals( "Filter group names do not match.", expectedGroups, timelineToolbar.getFilterGroupNames() );
    }

    public void checkDisabledFilterNotification( String expectedFilterNotification ) {
        String actualFilterNotification = timelineToolbar.getFilterModalNotification();
        assertEquals( "Filter notification is not expected", expectedFilterNotification.replace( "\\n", "\n" ), actualFilterNotification );
    }

    public void clickLinkOnFilterModal( String linkText ) {
        timelineToolbar.clickLinkText( linkText );
    }

    public void checkBadgeOnViewModeButton( String badgeText, boolean isDisplayed ) {
        boolean actualBadgeState = timelineToolbar.isBadgeDisplayedOnViewModeButton();
        assertEquals( STR."TBadge on View mode button was \{isDisplayed ? "not displayed " : "displayed"}", isDisplayed, actualBadgeState );
        if ( isDisplayed ) {
            String actualBadgeText = timelineToolbar.getViewModeBadgeText();
            assertEquals( STR."Badge text on View mode button was not expected", badgeText, actualBadgeText );
        }
    }

    public void checkZoomOutEnabled( boolean isEnabled ) {
        boolean actualEnabledState = isZoomEnabled(ZOOM_OUT);
        LOGGER.info( STR."The 'Zoom out' button actual state is: \{actualEnabledState}" );
        assertEquals( "Zoom out button is not in the expected state\n", isEnabled, actualEnabledState );
    }

}
