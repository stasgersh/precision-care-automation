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

import static java.util.stream.Collectors.toMap;

import org.apache.commons.lang3.Range;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import com.ge.onchron.verif.model.Badge;
import com.ge.onchron.verif.model.TimelineEvent;
import com.ge.onchron.verif.model.enums.BadgeType;
import com.ge.onchron.verif.model.enums.EventType;
import com.ge.onchron.verif.model.enums.Side;
import com.ge.onchron.verif.pages.elements.Swimlane;
import com.ge.onchron.verif.pages.sections.Sidebar;
import com.ge.onchron.verif.pages.tabs.TimeLine;
import java.awt.Point;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Steps;

import static com.ge.onchron.verif.pages.utils.PageElementUtils.isVisibleInViewport;
import static com.ge.onchron.verif.pages.utils.PageElementUtils.waitForCompleteDocument;
import static com.ge.onchron.verif.utils.Utils.waitMillis;

public class TimeLineSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger( TimeLineSteps.class );
    private static final String TIMELINE_DATE_WITH_DAY_PATTERN = "yyyy MMM d";

    private TimeLine timeLine;
    private Sidebar sidebar;

    @Steps
    PatientDataSyncBannerSteps patientDataSyncBannerSteps;

    public void waitUntilPatientTimelineIsLoaded() {
        timeLine.waitUntilTimelineLoadingSkeletonDisappears();
        patientDataSyncBannerSteps.waitUntilPatientStatusIsLoaded();
    }

    public void checkTimelineVisibility( boolean isVisible ) {
        timeLine.waitUntilTimelineLoadingSkeletonDisappears();
        assertThat( "Timeline visibility is not ok.", timeLine.isTimelineVisible(), is( equalTo( isVisible ) ) );
    }

    public void checkSwimLanesAvailability( boolean isAvailable, List<String> swimLaneNames ) {
        List<Swimlane> observedSwimLanes = timeLine.getSwimLanes();
        List<String> observedSwimLaneNames = observedSwimLanes.stream()
                                                              .map( Swimlane::getName )
                                                              .collect( Collectors.toList() );
        LOGGER.info( STR."Observed swimlanes:\\n\{String.join( "\\n", observedSwimLaneNames )}" );
        if ( isAvailable ) {
            assertEquals( "Available swimlanes are not ok.", swimLaneNames, observedSwimLaneNames );
        } else {
            swimLaneNames.removeIf( swimLaneHeader -> !observedSwimLaneNames.contains( swimLaneHeader ) );
            assertThat( STR."The following swimlanes should not be available on the timeline: \{swimLaneNames}", swimLaneNames.size(), is( 0 ) );
        }
    }

    public void checkVisibleSwimlanesInTheViewport( Boolean isVisible, List<String> swimLaneNames ) {
        List<Swimlane> observedSwimLanes = timeLine.getSwimLanes();
        LOGGER.info( STR."Observed swimlanes: \{observedSwimLanes.stream().map( Swimlane::getName ).collect( Collectors.joining( ", " ) )}" );
        swimLaneNames.forEach( swimlaneName -> {
            Swimlane swimlane = observedSwimLanes.stream()
                                                 .filter( sl -> sl.getName().equals( swimlaneName ) )
                                                 .findFirst()
                                                 .orElseThrow( () -> new RuntimeException( STR."Following swimlane was not found on the timeline: \{swimlaneName}" ) );
            Boolean visibleInViewport = isVisibleInViewport( swimlane.getEventLabelsContainer().getWrappedElement() );
            LOGGER.info( STR."Swimlane \{swimlane.getName()} visibility is: \{visibleInViewport}" );
            assertThat( STR."The \{swimlaneName} swimlane visibility in the viewport is not ok.",
                    visibleInViewport, is( equalTo( isVisible ) ) );
        } );
    }

    public void checkEventsOnSwimlane( String swimlaneName, List<TimelineEvent> expectedEvents ) {
        boolean eventsWithDate = expectedEvents.get( 0 ).getDateOnTimelineAxis() != null; // in order to know if event dates need to be checked or not
        List<TimelineEvent> observedEvents = timeLine.getTimelineEventsFromSwimlane( swimlaneName, eventsWithDate, false, false );
        LOGGER.info( STR."Observed events: \{observedEvents.stream().map( TimelineEvent::toString ).collect( Collectors.joining( "\\n " ) )}" );
        assertEquals( "Timeline events are not ok.", expectedEvents, observedEvents );
    }

    public void checkEventsOnSwimlaneFromLeftToRight( String swimlaneName, List<TimelineEvent> expectedEvents ) {
        boolean eventsWithDate = expectedEvents.get( 0 ).getDateOnTimelineAxis() != null;
        List<TimelineEvent> observedEvents = timeLine.getTimelineEventsFromSwimlane( swimlaneName, eventsWithDate, true, false );
        // Sort events (the first item on the left side will have the index "0")
        observedEvents.sort( Comparator.comparing( event -> event.getPosition().getX() ) );
        LOGGER.info( STR."Observed events: \{observedEvents.stream().map( TimelineEvent::toString ).collect( Collectors.joining( "\\n " ) )}" );
        assertEquals( "Timeline events (or their sort order) are not ok.", expectedEvents, observedEvents );
    }

    public void checkEventsAvailabilityOnSwimlane( String swimlaneName, List<TimelineEvent> events, Boolean isAvailable ) {
        boolean eventsWithDate = events.get( 0 ).getDateOnTimelineAxis() != null;
        List<TimelineEvent> observedEvents = timeLine.getTimelineEventsFromSwimlane( swimlaneName, eventsWithDate, false, false );
        events.forEach( event -> {
            LOGGER.info( STR."The following event\{isAvailable ? " was" : " was not"} found on the '\{swimlaneName}' swimlane: \{event}" );
            assertThat( STR."The following event\{isAvailable ? " was not" : " was"} found on the '\{swimlaneName}' swimlane: \{event}",
                    observedEvents.contains( event ), is( isAvailable ) );
        } );
    }

    public void checkEventsAvailabilityOnSwimlaneInOrder( String swimlaneName, List<TimelineEvent> expectedEvents ) {
        boolean eventsWithDate = expectedEvents.get( 0 ).getDateOnTimelineAxis() != null;
        List<TimelineEvent> observedEvents = timeLine.getTimelineEventsFromSwimlane( swimlaneName, eventsWithDate, true, false );
        // Sort events (the first item on the left side will have the index "0")
        observedEvents.sort( Comparator.comparing( event -> event.getPosition().getX() ) );
        // Remove the events which no need to check now
        observedEvents.removeIf( observedEvent -> !expectedEvents.contains( observedEvent ) );
        assertEquals( "Expected timeline events (or their sort order) are not ok.", expectedEvents, observedEvents );
    }

    public void checkEventWithBadgesOnSwimlane( String swimlaneName, TimelineEvent expectedEvent ) {
        boolean eventWithDate = expectedEvent.getDateOnTimelineAxis() != null;
        List<TimelineEvent> observedEvents = timeLine.getTimelineEventsFromSwimlane( swimlaneName, eventWithDate, false, true );
        LOGGER.info( STR."Observed events: \{observedEvents.stream().map( TimelineEvent::toString ).collect( Collectors.joining( "\\n " ) )}" );
        verifyEventWithBadge( true, observedEvents, expectedEvent );
    }

    public void checkEventsWithBadgesOnSwimlane( Boolean isAvailable, String swimlaneName, List<TimelineEvent> expectedEvents ) {
        boolean eventWithDate = expectedEvents.get( 0 ).getDateOnTimelineAxis() != null;
        List<TimelineEvent> observedEvents = timeLine.getTimelineEventsFromSwimlane( swimlaneName, eventWithDate, false, true );
        LOGGER.info( STR."Observed events: \{observedEvents.stream().map( TimelineEvent::toString ).collect( Collectors.joining( "\\n " ) )}" );
        expectedEvents.forEach( expectedEvent -> verifyEventWithBadge( isAvailable, observedEvents, expectedEvent ) );
    }

    private void verifyEventWithBadge( Boolean isAvailable, List<TimelineEvent> observedEvents, TimelineEvent expectedEvent ) {
        TimelineEvent observedEvent = observedEvents.stream()
                                                    .filter( oe -> oe.equals( expectedEvent ) )
                                                    .findFirst()
                                                    .orElse( null );

        assertThat( STR."The following event\{isAvailable ? " was not" : " was"} found: \{expectedEvent}", observedEvent != null, is( isAvailable ) );

        if ( observedEvent != null ) {
            LOGGER.info( STR."Event found: \{observedEvent}." );
            assertEquals( "Number of expected and observed badges do not match on event label.", expectedEvent.getBadges().size(), observedEvent.getBadges().size() );
            expectedEvent.getBadges().forEach( expectedEventBadge -> {
                // Check without color
                assertTrue( STR."The following badge was not found on event label: \\n\{expectedEventBadge}", observedEvent.getBadges().contains( expectedEventBadge ) );
                // Check color if needed
                if ( expectedEventBadge.getColor() != null ) {
                    Badge observedBadge = observedEvent.getBadges().get( observedEvent.getBadges().indexOf( expectedEventBadge ) );
                    assertEquals( "Color of expected and observed badges do not match on event label.", expectedEventBadge.getColor(), observedBadge.getColor() );
                }
            } );
        }
    }

    public void checkEventHasNoBadgesOnSwimlane( String swimlaneName, TimelineEvent expectedEvent ) {
        boolean eventWithDate = expectedEvent.getDateOnTimelineAxis() != null;
        List<TimelineEvent> observedEvents = timeLine.getTimelineEventsFromSwimlane( swimlaneName, eventWithDate, false, true );
        TimelineEvent observedEvent = observedEvents.stream()
                                                    .filter( oe -> oe.equals( expectedEvent ) )
                                                    .findFirst()
                                                    .orElseThrow( () -> new RuntimeException( STR."Following timeline event was not found: \{expectedEvent}" ) );
        LOGGER.info( STR."Observed event \{observedEvent}" );
        assertTrue( STR."The following event should not have any badges: \{observedEvent}", observedEvent.getBadges().isEmpty() );
    }

    public void checkMiniaturizedBadgesOnEvents( String swimlaneName, TimelineEvent expectedEvent, int badgeNumber, List<String> badgeTypes ) {
        boolean eventWithDate = expectedEvent.getDateOnTimelineAxis() != null;
        List<TimelineEvent> observedEvents = timeLine.getTimelineEventsFromSwimlane( swimlaneName, eventWithDate, false, true );
        TimelineEvent observedEvent = observedEvents.stream()
                                                    .filter( oe -> oe.equals( expectedEvent ) )
                                                    .findFirst()
                                                    .orElseThrow( () -> new RuntimeException( STR."Following timeline event was not found: \{expectedEvent}" ) );

        LOGGER.info( STR."Observed event \{observedEvent}" );
        assertEquals( "Number of expected and observed badges do not match on event label.", badgeNumber, observedEvent.getBadges().size() );

        for ( int i = 0; i < observedEvent.getBadges().size(); i++ ) {
            Badge actualBadge = observedEvent.getBadges().get( i );
            // In case of miniaturized badges, there are no badge texts
            assertTrue( STR."Badge should be miniaturized without any texts on it: \{actualBadge}", actualBadge.getText().isEmpty() );
            assertEquals( STR."Badge type is not ok for badge: \{actualBadge}", BadgeType.valueOf( badgeTypes.get( i ) ), actualBadge.getBadgeType() );
        }
    }

    public void checkEventsAvailabilityOnTimeline( List<TimelineEvent> eventsWithSwimlane, Boolean isAvailable ) {
        boolean eventsWithDate = eventsWithSwimlane.get( 0 ).getDateOnTimelineAxis() != null;
        Map<String, List<TimelineEvent>> storedEventsFromSwimlane = new HashMap<>();
        eventsWithSwimlane.forEach( expectedEvent -> {
            String actualSwimlane = expectedEvent.getSwimlane();
            List<TimelineEvent> observedEventsOnSwimlane;
            if ( storedEventsFromSwimlane.containsKey( actualSwimlane ) ) {
                observedEventsOnSwimlane = storedEventsFromSwimlane.get( actualSwimlane );
            } else {
                observedEventsOnSwimlane = timeLine.getTimelineEventsFromSwimlane( actualSwimlane, eventsWithDate, false, true );
                storedEventsFromSwimlane.put( actualSwimlane, observedEventsOnSwimlane );
            }

            LOGGER.info( STR."Observed events: \{observedEventsOnSwimlane.stream()
                                                                         .map( TimelineEvent::toString )
                                                                         .collect( Collectors.joining( "\\n" ) )}"
            );
            assertThat( STR."Visibility of event on the '\{actualSwimlane}' swimlane is not ok: \{expectedEvent}",
                    observedEventsOnSwimlane.contains( expectedEvent ), is( isAvailable ) );
        } );
    }

    public void clickOnEventPointOnSwimlane( String swimlaneName, TimelineEvent expectedEvent ) {
        timeLine.clickOnEventPointOnSwimlane( swimlaneName, expectedEvent );
        waitForCompleteDocument();
        waitMillis( 1000 ); // waiting for finishing cluster animation
    }

    public void openEventOnSwimlane( String swimlaneName, TimelineEvent expectedEvent ) {
        // Click on event point only if sidebar is not opened or not the required event is opened
        if ( !(sidebar.isSidebarVisible() && sidebar.getHeader().getTitle().equals( expectedEvent.getNameLabel() )) ) {
            clickOnEventPointOnSwimlane( swimlaneName, expectedEvent );
        }
    }

    public void hoverEventPointOnSwimlane( String swimlaneName, TimelineEvent expectedEvent ) {
        timeLine.hoverEventPointOnSwimlane( swimlaneName, expectedEvent );
    }

    public void hoverEventLabelOnSwimlane( String swimlaneName, TimelineEvent timelineEvent ) {
        timeLine.hoverEventLabelOnSwimlane( swimlaneName, timelineEvent );
    }

    public void unhoverEventLabelOnSwimlane( String swimlaneName, TimelineEvent timelineEvent ) {
        timeLine.unhoverEventLabelOnSwimlane( swimlaneName, timelineEvent );
    }

    public void verifyAllSwimlanesHaveEvent() {
        List<Swimlane> observedSwimLanes = timeLine.getSwimLanes();
        observedSwimLanes.forEach( swimlane -> {
            List<TimelineEvent> events = timeLine.getTimelineEventsFromSwimlane( swimlane.getName(), false, false, false );
            LOGGER.info( STR."Swimlane \{swimlane.getName()} has \{events.size()} events." );
            assertFalse( STR."The following swimlane does not have any events: \{swimlane.getName()}", events.isEmpty() );
        } );
    }

    public void verifyAllEventsHaveLabels() {
        List<Swimlane> observedSwimLanes = timeLine.getSwimLanes();
        observedSwimLanes.forEach( swimlane -> {
            // Get event labels
            List<WebElementFacade> eventLabels = timeLine.getEventPoints( swimlane );
            // Get event points
            List<WebElementFacade> eventPoints = timeLine.getEventPoints( swimlane );
            LOGGER.info( STR."On swimlane \{swimlane} found \{eventLabels.size()} event labels and \{eventPoints.size()} event points." );
            assertEquals( STR."The number of the event labels and points do not match on the swimlane: \{swimlane.getName()}",
                    eventLabels.size(), eventPoints.size() );
        } );
    }

    public void verifyAllEventsLabelsHaveName() {
        List<Swimlane> observedSwimLanes = timeLine.getSwimLanes();
        observedSwimLanes.forEach( swimlane -> {
            LOGGER.info( STR."Checking swimlane \{swimlane.getName()}" );
            List<TimelineEvent> events = timeLine.getTimelineEventsFromSwimlane( swimlane.getName(), false, false, false );
            events.forEach( event -> {
                LOGGER.info( STR."Found event \{event}" );
                assertFalse(
                        STR."An event on \{swimlane.getName()} swimlane does not have text on the label: \{event}",
                        event.getNameLabel().isEmpty() );
            } );
        } );
    }

    public void checkFirstDateOnTimelineAxis( String expectedFirstDate ) {
        List<String> timelineAxisValues = timeLine.getVisibleTimelineAxisValues();
        LOGGER.info( STR."Visible timeline axis values: \{timelineAxisValues}" );
        assertEquals( "First timeline axis date is not ok.", expectedFirstDate, timelineAxisValues.get( 0 ) );
    }

    public void checkLastDateOnTimelineAxis() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( TIMELINE_DATE_WITH_DAY_PATTERN );
        LocalDate currentDate = LocalDate.now();
        String today = currentDate.format( formatter );
        String tomorrow = currentDate.plusDays( 1 ).format( formatter );
        LOGGER.info( STR."Today \{today}" );
        LOGGER.info( STR."Tomorrow \{tomorrow}" );
        List<String> timelineAxisValues = timeLine.getVisibleTimelineAxisValues();
        LOGGER.info( STR."Visible timeline axis values: \{timelineAxisValues}" );

        assertThat( "Last timeline axis date is not ok.",
                timelineAxisValues.get( timelineAxisValues.size() - 1 ), either( is( today ) ).or( is( tomorrow ) ) );
    }

    public void checkTimelineAxisValues( boolean isVisible, List<String> expectedTimelineAxisValues ) {
        List<String> visibleTimelineAxisValues = timeLine.getVisibleTimelineAxisValues();
        LOGGER.info( STR."Visible timeline axis values \{String.join( ", ", visibleTimelineAxisValues )}" );
        expectedTimelineAxisValues.forEach( timelineAxisValue -> {
            assertThat( STR."Following axis value visibility is not ok: \{timelineAxisValue}",
                    visibleTimelineAxisValues.contains( timelineAxisValue ), is( isVisible ) );
        } );
    }

    public void checkTimelineAxisValuesAscendingFromLeftToRight( boolean shouldAscendingFromLeftToRight ) {
        HashMap<String, Point> visibleTimelineAxisParams = timeLine.getVisibleTimelineAxisValuesWithPosition();
        // Sort observed params
        HashMap<String, Point> visibleParamsSortedByLeftToRight = visibleTimelineAxisParams.entrySet().stream()
                                                                                           .sorted( Comparator.comparing( entry -> entry.getValue().getX() ) )
                                                                                           .collect( toMap( Map.Entry::getKey, Map.Entry::getValue, ( e1, e2 ) -> e2, LinkedHashMap::new ) );

        List<String> visibleParamsInAscendingOrder = visibleParamsSortedByLeftToRight.keySet().stream().sorted().toList();
        boolean ascendingFromLeftToRight = visibleParamsInAscendingOrder.equals( new ArrayList<>( visibleParamsSortedByLeftToRight.keySet() ) );
        LOGGER.info( STR."Values ascending from left to right: \{ascendingFromLeftToRight}" );
        assertEquals( STR."Order of timeline axis values is not ok, shouldAscendingFromLeftToRight: \{shouldAscendingFromLeftToRight}",
                shouldAscendingFromLeftToRight, ascendingFromLeftToRight );
    }

    public void scrollTimelineHorizontallyToMaxRight() {
        Dimension timelineContentSize = timeLine.getTimelineContentSize();
        timeLine.scrollTimelineTo( timelineContentSize.getWidth() );
    }

    public void scrollTimelineHorizontallyToMaxLeft() {
        Dimension timelineContentSize = timeLine.getTimelineContentSize();
        timeLine.scrollTimelineTo( timelineContentSize.getWidth() * (-1), 0 );
    }

    public void scrollTimelineVerticallyToTheBottom() {
        Dimension timelineContentSize = timeLine.getTimelineContentSize();
        timeLine.scrollTimelineTo( 0, timelineContentSize.getHeight() );
    }

    public void scrollTimelineVerticallyToSwimlane( String swimlane ) {
        timeLine.scrollTimelineToSwimlaneVertically( swimlane );
    }

    public void scrollTimelineVerticallyToTheTop() {
        Dimension timelineContentSize = timeLine.getTimelineContentSize();
        timeLine.scrollTimelineTo( 0, timelineContentSize.getHeight() * (-1) );
    }

    public void setBrowserSize( int width, int height ) {
        timeLine.setBrowserSize( width, height );
        waitForCompleteDocument();
        waitMillis( 1500 ); // waiting for finishing cluster animation
    }

    public void verifyAllEventClustersDisplayNumber() {
        List<Swimlane> observedSwimLanes = timeLine.getSwimLanes();
        observedSwimLanes.forEach( swimlane -> {
            List<TimelineEvent> events = timeLine.getTimelineEventsFromSwimlane( swimlane.getName(), false, false, false );
            List<TimelineEvent> eventClusters = events.stream()
                                                      .filter( event -> event.getEventType().equals( EventType.CLUSTER ) )
                                                      .toList();

            eventClusters.forEach( event -> {
                LOGGER.info( STR."Checking event cluster \{event}" );
                assertFalse(
                        STR."An event cluster on \{swimlane.getName()} swimlane does not have the number of included events: \{event}",
                        event.getTextOnEventPoint().isEmpty() );
            } );
        } );
    }

    public void moveToEvent( String swimlaneName, TimelineEvent event ) {
        timeLine.moveToEvent( swimlaneName, event );
    }

    public void checkVisibleEventsInTheViewport( String swimlaneName, List<TimelineEvent> events, Boolean isVisible ) {
        timeLine.checkEventsVisibilityInViewport( swimlaneName, events, isVisible );
    }

    public void checkEventLabelTextIsBold( String swimlaneName, boolean isBold, List<TimelineEvent> events ) {
        events.forEach( event -> {
            boolean isActualBold = timeLine.hasBoldLabelText( swimlaneName, event );
            LOGGER.info( STR."Event \{event.getNameLabel()} has bold label: \{isActualBold}" );
            assertThat( STR."The following event's label on the '\{swimlaneName}' swimlane \{isBold ? "is not " : "is "}bold:\n\{event}",
                    isActualBold, is( isBold ) );
        } );
    }

    public void checkTodayTimelineAxisLabelPosition( String expectedSideString ) {
        Side expectedSide = Side.valueOf( expectedSideString.toUpperCase() );
        Rectangle timelineRect = timeLine.getTimelineRect();

        // position of today's marker label
        Rectangle todayLabelRect = timeLine.getTodayMarkerLabelRect();
        int todayLabelCenterPosX = todayLabelRect.getX() + (todayLabelRect.getWidth() / 2);

        int expectedPosition;
        if ( expectedSide.equals( Side.RIGHT ) ) {
            expectedPosition = timelineRect.getX() + timelineRect.getWidth();
        } else {
            expectedPosition = timelineRect.getX();
        }
        Range<Integer> rangeX = Range.between( expectedPosition - 5, expectedPosition + 5 );
        LOGGER.info( STR."PositionX of today's marker label should be in range \{rangeX}, actual is \{todayLabelCenterPosX}" );
        assertTrue( String.format( "PositionX of today's marker label should be in range %s but it is %s", rangeX, todayLabelCenterPosX ), rangeX.contains( todayLabelCenterPosX ) );
    }

    public void checkEventPositionInTimeLine( String event, String expectedSideString ) {
        List<WebElement> list = timeLine.getListOfDatesOnTimelineLocation();
        Side expectedSide = Side.valueOf( expectedSideString.toUpperCase() );
        for ( WebElement element : list ) {
            if ( element.getText().equals( event ) ) {
                if ( expectedSide.equals( Side.RIGHT ) ) {
                    assertTrue( element.getLocation().x > timeLine.getDriver().manage().window().getSize().width / 2 );
                } else {
                    assertTrue( element.getLocation().x < timeLine.getDriver().manage().window().getSize().width / 2 );
                }
            }
        }
    }

    public void checkTimelineBannerNotification( String expectedNotification ) {
        waitMillis( 1000 );
        String actualNotification = timeLine.getTimelineBannerNotificationText();
        LOGGER.info( STR."Actual timeline banner notification was displayed as: \{actualNotification}" );
        assertEquals( "Actual timeline banner notification was not expected", expectedNotification, actualNotification );
    }

    public void checkTimelineLoadingSkeletonVisibility( boolean isVisible ) {
        boolean observedLoadingSkeletonVisible = timeLine.isLoadingSkeletonVisible();
        assertEquals( STR."Loading skeleton is\{isVisible ? " " : " not "}present on the timeline when expected\{isVisible ? " not " : " "}to be present", isVisible, observedLoadingSkeletonVisible );
    }

    public void checkTimelineLoadingSkeletonChanges() {
        List<String> observedSkeleton = List.of();
        List<String> startingSkeleton = timeLine.getLoadingSkeletonElements();
        assertFalse( "Loading skeleton elements not found", startingSkeleton.isEmpty() );

        for ( int i = 0; i < 20; i++ ) {
            waitMillis( 3000 );
            observedSkeleton = timeLine.getLoadingSkeletonElements();
            if ( !startingSkeleton.equals( observedSkeleton ) ) {
                break;
            }
        }
        assertFalse( "Timeline has loaded before the loading skeleton changed", observedSkeleton.isEmpty() );
        assertNotEquals( "Loading skeleton did not change after 60 seconds", startingSkeleton, observedSkeleton );
    }
}