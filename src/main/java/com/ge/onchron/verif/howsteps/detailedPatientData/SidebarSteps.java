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
package com.ge.onchron.verif.howsteps.detailedPatientData;

import org.jbehave.core.model.ExamplesTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.Badge;
import com.ge.onchron.verif.model.BannerNotification;
import com.ge.onchron.verif.model.DetailedImportantSection;
import com.ge.onchron.verif.model.DetailedSidebarHeader;
import com.ge.onchron.verif.model.detailedDataItems.DetailedDataItem;
import com.ge.onchron.verif.model.enums.BadgeType;
import com.ge.onchron.verif.pages.sections.DetailedPatientData;
import com.ge.onchron.verif.pages.sections.Sidebar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.ge.onchron.verif.TestSystemParameters.LABEL_COLORS_FROM_LABEL_PANEL;
import static com.ge.onchron.verif.utils.ReplaceUtils.replaceDatePlaceholders;
import static com.ge.onchron.verif.utils.ReplaceUtils.replaceStoredPlaceholderText;
import static com.ge.onchron.verif.utils.Utils.waitMillis;
import static net.serenitybdd.core.Serenity.hasASessionVariableCalled;
import static net.serenitybdd.core.Serenity.sessionVariableCalled;

public class SidebarSteps extends DetailedPatientDataSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger( SidebarSteps.class );

    private Sidebar sidebar;

    @Override
    protected DetailedPatientData getDetailedPatientData() {
        return sidebar.getDetailedPatientData();
    }

    @Override
    protected void waitUntilLoadingSkeletonDisappears() {
        sidebar.waitUntilSidebarContentLoadingSkeletonDisappears();
    }

    public void checkSidebarVisibility( boolean isVisible ) {
        if ( isVisible ) {
            sidebar.waitUntilSidebarContentLoadingSkeletonDisappears();
        }
        boolean sidebarVisible = sidebar.isSidebarCurrentlyVisible();
        LOGGER.info( STR."Sidebar is visible: \{sidebarVisible}" );
        assertThat( "Sidebar visibility is not ok.", sidebarVisible, is( equalTo( isVisible ) ) );
    }

    public void checkSidebarHeaderTitle( String headerTitle ) {
        String title = sidebar.getHeader().getTitle();
        LOGGER.info( STR."Observed header title: \{title}" );
        assertEquals( "Sidebar header is not ok.", headerTitle, title );
    }

    public void clickOnCloseButton() {
        sidebar.clickOnCloseButton();
    }

    public void checkSidebarHeaderData( DetailedSidebarHeader expectedHeaderData ) {
        // Labels, badges, tags are checked in separated steps
        DetailedSidebarHeader header = sidebar.getHeader();
        LOGGER.info( STR."Actual header is: \{header}" );
        assertEquals( "Sidebar header is not ok.", expectedHeaderData, header );
    }

    public void checkBadgesOnSidebarHeader( List<Badge> expectedBadges ) {
        for ( int i = 0; i < expectedBadges.size(); i++ ) {
            String expectedLabelText = replaceStoredPlaceholderText( expectedBadges.get( i ).getText() );
            expectedBadges.get( i ).setText( expectedLabelText );
        }
        List<Badge> observedBadges = sidebar.getHeader().getBadges();
        LOGGER.info( STR."Observed badges:\\n\{observedBadges.stream().map( Badge::toString ).collect( Collectors.joining( "\\n" ) )}" );
        // Label sort order is taken into account
        assertEquals( "Badges on sidebar header are not ok.", expectedBadges, observedBadges );
    }

    public void checkLabelTextsAndColorsOnSidebarHeader( List<String> expectedLabelTexts ) {
        if ( !hasASessionVariableCalled( LABEL_COLORS_FROM_LABEL_PANEL ) ) {
            fail( "Label colors were not saved previously, so label colors cannot be checked." );
        }
        Map<String, String> storedLabelColorsFromLabelPanel = sessionVariableCalled( LABEL_COLORS_FROM_LABEL_PANEL );

        List<Badge> observedBadges = sidebar.getHeader().getBadges();
        List<Badge> observedLabelBadges = observedBadges.stream()
                                                        .filter( badge -> badge.getBadgeType().equals( BadgeType.LABEL ) )
                                                        .collect( Collectors.toList() );

        LOGGER.info( STR."Observed label badges on the sidebar: \{observedBadges}" );
        for ( int i = 0; i < expectedLabelTexts.size(); i++ ) {
            String expectedLabelText = replaceStoredPlaceholderText( expectedLabelTexts.get( i ) );
            expectedLabelTexts.set( i, expectedLabelText );
        }

        expectedLabelTexts.forEach( expectedLabelText -> {
            Badge observedLabelBadge = observedLabelBadges.stream()
                                                          .filter( olb -> olb.getText().equals( expectedLabelText ) )
                                                          .findFirst()
                                                          .orElseThrow( () -> new RuntimeException(
                                                                  String.format( "Following label was not found on the sidebar: %s.\nObserved labels: %s", expectedLabelText, observedLabelBadges ) ) );

            String expectedColor = storedLabelColorsFromLabelPanel.get( expectedLabelText );
            assertNotNull( STR."Label color was not saved previously for label: \{expectedLabelText}", expectedColor );
            assertEquals( STR."Label color is not ok: \{expectedLabelText}", expectedColor, observedLabelBadge.getColor() );
        } );
    }

    public void checkNoBadgesOnSidebarHeader() {
        List<Badge> observedBadges = sidebar.getHeader().getBadges();
        LOGGER.info( STR."Observed badges:\\n\{observedBadges.stream().map( Badge::toString ).collect( Collectors.joining( "\\n" ) )}" );
        assertTrue( STR."Badges should not be available on the sidebar header. Badges: \{observedBadges}", observedBadges.isEmpty() );
    }

    public void checkEventIsMarkedAsImportantOnSideBar( DetailedImportantSection expectedImportantSection ) {
        DetailedImportantSection markAsImportantDetails = sidebar.getMarkAsImportantDetails();
        LOGGER.info( STR."Observed mark as important details: \{markAsImportantDetails}" );
        assertEquals( "Mark as Important section is not in the expected state\n", expectedImportantSection, markAsImportantDetails );
    }

    /**
     * @param markOrMarked: 'Mark as important' is displayed to the user when the event is not marked as important
     *                      'Marked as important' is displayed to the user when the event is marked as important
     */
    public void clickMarkAsImportant( String markOrMarked ) {
        sidebar.clickOnMarkAsImportantButton( markOrMarked );
        waitMillis( 500 );
    }

    public void clickOnLabelButton() {
        sidebar.clickOnLabelButton();
    }

    public void isNotificationBannerAvailableInSidebarHeader( boolean isAvailable ) {
        List<BannerNotification> observedBannerNotifications = sidebar.getSidebarBanners();
        LOGGER.info( STR."Notification banners on sidepanel: \{observedBannerNotifications}" );
        assertThat( String.format( "Notification banner %s available in the sidepanel header", (isAvailable ? "is not" : "is") ),
                !observedBannerNotifications.isEmpty(), is( equalTo( isAvailable ) ) );
    }

    public void checkNotificationBannersInSidebarHeader( List<BannerNotification> expectedBannerNotifications ) {
        List<BannerNotification> observedBannerNotifications = sidebar.getSidebarBanners();
        LOGGER.info( STR."Notification banners on sidepanel: \{observedBannerNotifications}" );
        assertEquals( "Expected and observed notification banners do not match.", expectedBannerNotifications, observedBannerNotifications );
    }

    public void clickButtonOnNotificationBannerInSidebarHeader( BannerNotification bannerNotification ) {
        sidebar.clickButtonOnNotificationBanner( bannerNotification );
    }

    public void moveMouseOnTheSidebarBadge( Badge badge ) {
        sidebar.hoverOnCLPLabelOnTimeline( badge.getText() );
    }

    public void moveMouseOnTheSidebarData( DetailedDataItem sidebarItem ) {
        sidebar.moveMouseOnTheSidebarData( sidebarItem );
    }

    public void checkAdherenceDatesOnSidebar( ExamplesTable adherenceDateTable ) {
        Map<String, String> expectedAdherenceTable = new HashMap<>();
        List<Map<String, String>> rows = adherenceDateTable.getRows();
        Pattern pattern = Pattern.compile( "<(\\d+)_DAYS_(AGO|FROM_NOW)>" );
        for ( Map<String, String> row : rows ) {
            String value = row.get( "value" );
            if ( pattern.matcher( value ).matches() ) {
                value = replaceDatePlaceholders( value, "MMM dd, yyyy" );
            }
            expectedAdherenceTable.put( row.get( "date_type" ), value );
        }

        Map<String, String> actualAdherenceTable = sidebar.getAdherenceDatesInMap();
        for ( Map.Entry<String, String> entry : expectedAdherenceTable.entrySet() ) {
            String key = entry.getKey();
            String expectedValue = entry.getValue();
            String actualValue = actualAdherenceTable.get( key );

            assertEquals( STR."Adherence date table value for key \{key} does not match.", expectedValue, actualValue );
        }
    }

    public void checkButtonIsDisabledOnTheSidebarHeader( String button ) {
        List<String> disabledButtonList = sidebar.getDisabledButtonsList();
        LOGGER.info( STR."The following buttons are disabled on the Sidebar header: \{disabledButtonList.toString()}" );
        assertFalse( disabledButtonList.isEmpty() );
        assertTrue( STR."\{button} button was not disabled", disabledButtonList.contains( button ) );
    }

    public void clickOnAIShowMoreOrLess( String buttonMoreOrLess ) {
        sidebar.clickOnAIShowMoreOrLess( buttonMoreOrLess );
    }
}
