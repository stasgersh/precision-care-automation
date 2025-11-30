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
package com.ge.onchron.verif.whatsteps.tabs.timeline;

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.howsteps.TimeLineSteps;
import com.ge.onchron.verif.model.Badge;
import com.ge.onchron.verif.model.StringList;
import com.ge.onchron.verif.model.TimelineEvent;
import com.ge.onchron.verif.model.enums.EventType;
import com.ge.onchron.verif.pages.utils.PageUtils;
import java.util.List;
import java.util.stream.Collectors;
import net.thucydides.core.annotations.Steps;

public class TimeLine {

    @Steps
    private TimeLineSteps timeLineSteps;
    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Given( "the patient timeline is loaded" )
    @When( "the patient timeline is loaded" )
    @Then( "the patient timeline is loaded" )
    @Alias( "the patient timeline is reloaded" )
    public void waitUntilPatientTimelineIsLoaded() {
        testDefinitionSteps.addStep( "Wait until patient timeline is loaded" );
        timeLineSteps.waitUntilPatientTimelineIsLoaded();
        testDefinitionSteps.logEvidence( "Patient timeline is loaded", "Patient timeline loaded without errors.", true );
    }

    @Given( "the patient timeline $isVisible displayed" )
    @Then( "the patient timeline $isVisible displayed" )
    public void checkTimelineVisibility( Boolean isVisible ) {
        testDefinitionSteps.addStep( "Check that patient timeline visibility." );
        timeLineSteps.checkTimelineVisibility( isVisible );
        testDefinitionSteps.logEvidence( STR."patient timeline visibility is: \{isVisible}", isVisible, true );
    }

    @When( "the user scrolls the timeline horizontally to the end" )
    public void scrollTimelineHorizontallyToMaxRight() {
        testDefinitionSteps.addStep( "Scroll timeline horizontally to the end" );
        timeLineSteps.scrollTimelineHorizontallyToMaxRight();
        testDefinitionSteps.logEvidence( "Timeline is scrolled to the end", "Timeline is scrolled to the end without errors", true );
    }

    @When( "the user scrolls the timeline horizontally to the left side" )
    public void scrollTimelineHorizontallyToMaxLeft() {
        testDefinitionSteps.addStep( "Scroll timeline horizontally to the left side" );
        timeLineSteps.scrollTimelineHorizontallyToMaxLeft();
        testDefinitionSteps.logEvidence( "Timeline is scrolled to the left side", "Timeline is scrolled to the left side without errors", true );
    }

    @Given( "the timeline is scrolled vertically to the bottom" )
    @When( "the user scrolls the timeline vertically to the bottom" )
    public void scrollTimelineVerticallyToTheBottom() {
        testDefinitionSteps.addStep( "Scroll timeline vertically to the bottom" );
        timeLineSteps.scrollTimelineVerticallyToTheBottom();
        testDefinitionSteps.logEvidence( "Timeline is scrolled vertically to the bottom", "Timeline is scrolled vertically to the bottom without errors", true );
    }

    @Given( "the timeline is scrolled vertically to the \"$swimlane\" swimlane" )
    @When( "the user scrolls the timeline vertically to the \"$swimlane\" swimlane" )
    public void scrollTimelineVerticallyToSwimlane( String swimlane ) {
        testDefinitionSteps.addStep( STR."Scroll timeline vertically to the \{swimlane}" );
        timeLineSteps.scrollTimelineVerticallyToSwimlane( swimlane );
        testDefinitionSteps.logEvidence( STR."Timeline is scrolled vertically to the \{swimlane}", STR."Timeline is scrolled vertically to the \{swimlane} without errors", true );
    }

    @When( "the user scrolls the timeline vertically to the top" )
    public void scrollTimelineVerticallyToTheTop() {
        testDefinitionSteps.addStep( "Scroll timeline vertically to the top" );
        timeLineSteps.scrollTimelineVerticallyToTheTop();
        testDefinitionSteps.logEvidence( "Timeline is scrolled vertically to the top", "Timeline is scrolled vertically to the top without errors", true );
    }

    @Given( "the browser size is set to: width=$width, height=$height" )
    @When( "the browser size is set to: width=$width, height=$height" )
    public void setBrowserSize( int width, int height ) {
        testDefinitionSteps.addStep( STR."Set browser width to '\{width}' and height to '\{height}'" );
        timeLineSteps.setBrowserSize( width, height );
        testDefinitionSteps.logEvidence( "browser width and height set are succesfully", "browser width and height are set without errors", true );
    }

    @Given( "the following event point is selected on the \"$swimlaneName\" swimlane: $events" )
    @When( "the user clicks on the following event point on the \"$swimlaneName\" swimlane: $events" )
    public void clickOnEventPointOnSwimlane( String swimlaneName, List<TimelineEvent> events ) {
        testDefinitionSteps.addStep( STR."Click on the event point \{events.get( 0 )} on swimlane \{swimlaneName}" );
        assertThat( "Only one item can be added to the table in this step definition", events.size(), is( equalTo( 1 ) ) );
        timeLineSteps.clickOnEventPointOnSwimlane( swimlaneName, events.get( 0 ) );
        testDefinitionSteps.logEvidence( "Event point clicked successfully", "Event point clicked without errors", true );
    }

    @When( "the user hovers the following event point on the \"$swimlaneName\" swimlane: $events" )
    public void hoverEventPointOnSwimlane( String swimlaneName, List<TimelineEvent> events ) {
        testDefinitionSteps.addStep( STR."Hover over the event point \{events.get( 0 )} on swimlane \{swimlaneName}" );
        assertThat( "Only one item can be added to the table in this step definition", events.size(), is( equalTo( 1 ) ) );
        timeLineSteps.hoverEventPointOnSwimlane( swimlaneName, events.get( 0 ) );
        testDefinitionSteps.logEvidence( "Hovered over event point successfully", "Hovered over event point without errors", true );
    }

    @When( "the user opens the following event point on the \"$swimlaneName\" swimlane: $events" )
    public void openEventOnSwimlane( String swimlaneName, List<TimelineEvent> events ) {
        assertThat( "Only one item can be added to the table in this step definition", events.size(), is( equalTo( 1 ) ) );
        timeLineSteps.openEventOnSwimlane( swimlaneName, events.get( 0 ) );
    }

    @When( "the user hovers the following event label on the \"$swimlaneName\" swimlane: $events" )
    public void hoverEventLabelOnSwimlane( String swimlaneName, List<TimelineEvent> events ) {
        testDefinitionSteps.addStep( STR."Hover over the event label \{events.get( 0 )} on swimlane \{swimlaneName}" );
        assertThat( "Only one item can be added to the table in this step definition", events.size(), is( equalTo( 1 ) ) );
        timeLineSteps.hoverEventLabelOnSwimlane( swimlaneName, events.get( 0 ) );
        testDefinitionSteps.logEvidence( "Hovered over event label successfully", "Hovered over event label without errors", true );
    }

    @When( "the user unhovers the following event label on the \"$swimlaneName\" swimlane: $events" )
    public void unhoverEventLabelOnSwimlane( String swimlaneName, List<TimelineEvent> events ) {
        testDefinitionSteps.addStep( STR."Unhover from over the event label \{events.get( 0 )} on swimlane \{swimlaneName}" );
        assertThat( "Only one item can be added to the table in this step definition", events.size(), is( equalTo( 1 ) ) );
        timeLineSteps.unhoverEventLabelOnSwimlane( swimlaneName, events.get( 0 ) );
        testDefinitionSteps.logEvidence( "Unhovered from over event label successfully", "Unhovered over event label without errors", true );
    }

    @Given( "the swimlanes $isAvailable available on the timeline in the following order: $swimLaneHeaders" )
    @Then( "the swimlanes $isAvailable available on the timeline in the following order: $swimLaneHeaders" )
    @Alias( "the following swimlanes $isAvailable available on the timeline: $swimLaneHeaders" )
    public void checkSwimLanesAvailability( Boolean isAvailable, StringList swimLaneHeaders ) {
        testDefinitionSteps.addStep( "Check swimlanes availability and order" );
        String names = String.join( ", ", swimLaneHeaders.getList() );
        timeLineSteps.checkSwimLanesAvailability( isAvailable, swimLaneHeaders.getList() );
        if ( isAvailable ) {
            testDefinitionSteps.logEvidence( STR."Check swimlanes appear in the following order \{names}", "Swimlanes appear in order (See previous logs)", true );
        } else {
            testDefinitionSteps.logEvidence( STR."Check that following swimlanes do not appear \{names}", "Swimlanes do not appear (See previous logs)", true );
        }

    }

    @Given( "the below {swimlane|swimlanes} $isVisible visible in the viewport: $swimLaneHeaders" )
    @Then( "the below {swimlane|swimlanes} $isVisible visible in the viewport: $swimLaneHeaders" )
    public void checkVisibleSwimlanesInTheViewport( Boolean isVisible, StringList swimLaneHeaders ) {
        testDefinitionSteps.addStep( "Check visible swimlanes in the viewport" );
        timeLineSteps.checkVisibleSwimlanesInTheViewport( isVisible, swimLaneHeaders.getList() );
        String names = String.join( ", ", swimLaneHeaders.getList() );
        testDefinitionSteps.logEvidence( STR."Check that following swimlanes are visible \{names}", "Swimlanes are visible (See previous logs)", true );
    }

    @Given( "the \"$swimlaneName\" swimlane contains the following events: $events" )
    @Then( "the \"$swimlaneName\" swimlane contains the following events: $events" )
    @Alias( "the \"$swimlaneName\" swimlane still contains the following events: $events" )
    public void checkEventsOnSwimlane( String swimlaneName, List<TimelineEvent> events ) {
        testDefinitionSteps.addStep( STR."Check events on \{swimlaneName} swimlane" );
        timeLineSteps.checkEventsOnSwimlane( swimlaneName, events );
        events.forEach( e -> testDefinitionSteps.logEvidence( STR."Event \{e} is present on \{swimlaneName} swimlane",
                "Event is present (See previous logs)", true, false ) );
        testDefinitionSteps.logEvidence( STR."All events from above are present on \{swimlaneName} swimlane",
                "All events were present (See previous logs)", true );
    }

    @Given( "the \"$swimlaneName\" swimlane contains the following events from left to the right: $events" )
    @Then( "the \"$swimlaneName\" swimlane contains the following events from left to the right: $events" )
    public void checkEventsOnSwimlaneFromLeftToRight( String swimlaneName, List<TimelineEvent> events ) {
        testDefinitionSteps.addStep( STR."Check events on \{swimlaneName} swimlane" );
        timeLineSteps.checkEventsOnSwimlaneFromLeftToRight( swimlaneName, events );
        testDefinitionSteps.logEvidence(
                STR."Check following events appear on \{swimlaneName} swimlane from left to right \{events.stream()
                                                                                                          .map( TimelineEvent::toString )
                                                                                                          .collect( Collectors.joining( "\\n" ) )}",
                STR."Events on \{swimlaneName} swimlane appear in order (See previous logs)", true );
    }

    @Given( "the following event $isAvailable available on the \"$swimlaneName\" swimlane: $events" )
    @Then( "the following event $isAvailable available on the \"$swimlaneName\" swimlane: $events" )
    @Alias( "the following events $isAvailable available on the \"$swimlaneName\" swimlane: $events" )
    public void checkEventsAvailabilityOnSwimlane( Boolean isAvailable, String swimlaneName, List<TimelineEvent> events ) {
        testDefinitionSteps.addStep( STR."Check available events on \{swimlaneName} swimlane" );
        timeLineSteps.checkEventsAvailabilityOnSwimlane( swimlaneName, events, isAvailable );
        events.forEach( e -> testDefinitionSteps.logEvidence( STR."Event \{e} is \{isAvailable ? "" : "not"} available on \{swimlaneName} swimlane",
                "Event is available (See previous logs)", true, false ) );
        testDefinitionSteps.logEvidence( STR."All events from above are \{isAvailable ? "" : "not"} available on \{swimlaneName} swimlane",
                STR."All events were \{isAvailable ? "" : "not"} available (See previous logs)", true );
    }

    @Then( "the following {event|events} {are|is} ordered from left to the right on the \"$swimlaneName\" swimlane: $events" )
    public void checkEventsAvailabilityOnSwimlaneInOrder( String swimlaneName, List<TimelineEvent> events ) {
        testDefinitionSteps.addStep( STR."Check events on \{swimlaneName} swimlane" );
        timeLineSteps.checkEventsAvailabilityOnSwimlaneInOrder( swimlaneName, events );
        testDefinitionSteps.logEvidence(
                STR."Check following events appear on \{swimlaneName} swimlane in order from left to right \{events.stream()
                                                                                                                   .map( TimelineEvent::toString )
                                                                                                                   .collect( Collectors.joining( "\\n" ) )}",
                STR."Events on \{swimlaneName} swimlane appear in order (See previous logs)", true );
    }

    @Given( "the \"$eventName\" event $eventType is available on the \"$swimlane\" swimlane at \"$eventDate\" with the below badges: $eventBadges" )
    @Then( "the \"$eventName\" event $eventType is available on the \"$swimlane\" swimlane at \"$eventDate\" with the below badges: $eventBadges" )
    @Alias( "the \"$eventName\" event $eventType on the \"$swimlane\" swimlane at \"$eventDate\" has the below badges: $eventBadges" )
    public void checkEventWithBadgesOnSwimlaneWithDate( String eventName, EventType eventType, String swimlaneName, String eventDate, List<Badge> eventBadges ) {
        testDefinitionSteps.addStep( STR."Check events and their badges on \{swimlaneName} swimlane at date \{eventDate}" );
        TimelineEvent event = new TimelineEvent( eventType, eventName, eventDate );
        event.setBadges( eventBadges );
        timeLineSteps.checkEventWithBadgesOnSwimlane( swimlaneName, event );
        testDefinitionSteps.logEvidence( STR."Event \{eventName} of type \{eventType.name()} is available on swimlane \{swimlaneName} and has following badges \{eventBadges.stream()
                                                                                                                                                                            .map( Badge::toString ).collect( Collectors.joining( ", " ) )}",
                "Event is present and has expected badges (See previous logs)", true );
    }

    @Given( "the \"$eventName\" event $eventType is available on the \"$swimlane\" swimlane with the below badges: $eventBadges" )
    @Then( "the \"$eventName\" event $eventType is available on the \"$swimlane\" swimlane with the below badges: $eventBadges" )
    @Alias( "the \"$eventName\" event $eventType on the \"$swimlane\" swimlane has the below badges: $eventBadges" )
    public void checkEventWithBadgesOnSwimlane( String eventName, EventType eventType, String swimlaneName, List<Badge> eventBadges ) {
        testDefinitionSteps.addStep( STR."Check events and their badges on \{swimlaneName} swimlane" );
        TimelineEvent event = new TimelineEvent( eventType, eventName );
        event.setBadges( eventBadges );
        timeLineSteps.checkEventWithBadgesOnSwimlane( swimlaneName, event );
        testDefinitionSteps.logEvidence( STR."Event \{eventName} of type \{eventType.name()} is available on swimlane \{swimlaneName} and has following badges \{eventBadges.stream()
                                                                                                                                                                            .map( Badge::toString ).collect( Collectors.joining( ", " ) )}",
                "Event is present and has expected badges (See previous logs)", true );
    }

    @Given( "the following {event|events} $isAvailable available on the \"$swimlane\" swimlane with the below badges: $events" )
    @Then( "the following {event|events} $isAvailable available on the \"$swimlane\" swimlane with the below badges: $events" )
    public void checkMoreEventsWithBadgesOnSwimlane( Boolean isAvailable, String swimlaneName, List<TimelineEvent> events ) {
        testDefinitionSteps.addStep( STR."Check events and their badges on \{swimlaneName} swimlane" );
        timeLineSteps.checkEventsWithBadgesOnSwimlane( isAvailable, swimlaneName, events );
        events.forEach( e -> testDefinitionSteps.logEvidence( STR."Event \{e} is \{isAvailable ? "" : "not"} available on swimlane \{swimlaneName}",
                STR."Event is \{isAvailable ? "" : "not"} present and has expected badges (See previous logs)", true, false ) );
        testDefinitionSteps.logEvidence( STR."All events from above are \{isAvailable ? "" : "not"} available with expected badges.",
                "All Events were as expected (see previous logs)", true );

    }

    @Given( "the \"$eventName\" event $eventType is available on the \"$swimlane\" swimlane at \"$eventDate\" without any badges" )
    @Then( "the \"$eventName\" event $eventType is available on the \"$swimlane\" swimlane at \"$eventDate\" without any badges" )
    public void checkEventHasNoBadgesOnSwimlaneWithDate( String eventName, EventType eventType, String swimlaneName, String eventDate ) {
        testDefinitionSteps.addStep( STR."Check events and their badges on \{swimlaneName} swimlane at date \{eventDate}" );
        TimelineEvent event = new TimelineEvent( eventType, eventName, eventDate );
        timeLineSteps.checkEventHasNoBadgesOnSwimlane( swimlaneName, event );
        testDefinitionSteps.logEvidence( STR."Event \{eventName} of type \{eventType.name()} is available on swimlane \{swimlaneName} without badges",
                "Event is present without badges (See previous logs)", true );
    }

    @Given( "the \"$eventName\" event $eventType is available on the \"$swimlane\" swimlane without any badges" )
    @Then( "the \"$eventName\" event $eventType is available on the \"$swimlane\" swimlane without any badges" )
    public void checkEventHasNoBadgesOnSwimlane( String eventName, EventType eventType, String swimlaneName ) {
        testDefinitionSteps.addStep( STR."Check events and their badges on \{swimlaneName}" );
        TimelineEvent event = new TimelineEvent( eventType, eventName );
        timeLineSteps.checkEventHasNoBadgesOnSwimlane( swimlaneName, event );
        testDefinitionSteps.logEvidence( STR."Event \{eventName} of type \{eventType.name()} is available on swimlane \{swimlaneName} without badges",
                "Event is present without badges (See previous logs)", true );
    }

    @Given( "the \"$eventName\" event $eventType is available on the \"$swimlane\" swimlane at \"$eventDate\" with $badgeNumber miniaturized badges: $badgeTypes" )
    @Then( "the \"$eventName\" event $eventType is available on the \"$swimlane\" swimlane at \"$eventDate\" with $badgeNumber miniaturized badges: $badgeTypes" )
    public void checkNumOfMiniaturizedBadgesOnEventsWithDate( String eventName, EventType eventType, String swimlaneName, String eventDate, int badgeNumber, StringList badgeTypes ) {
        testDefinitionSteps.addStep( STR."Check events and their miniaturized badges on \{swimlaneName} swimlane at date \{eventDate}" );
        TimelineEvent event = new TimelineEvent( eventType, eventName, eventDate );
        timeLineSteps.checkMiniaturizedBadgesOnEvents( swimlaneName, event, badgeNumber, badgeTypes.getList() );
        testDefinitionSteps.logEvidence( STR."Event \{eventName} of type \{eventType.name()} is available on swimlane \{swimlaneName
                        } and has \{badgeNumber} miniaturized badges of following types \{
                        String.join( ", ", badgeTypes.getList() )}",
                "Event is present and has expected badges (See previous logs)", true );
    }

    @Given( "the \"$eventName\" event $eventType is available on the \"$swimlane\" swimlane with $badgeNumber miniaturized badges: $badgeTypes" )
    @Then( "the \"$eventName\" event $eventType is available on the \"$swimlane\" swimlane with $badgeNumber miniaturized badges: $badgeTypes" )
    public void checkNumOfMiniaturizedBadgesOnEvents( String eventName, EventType eventType, String swimlaneName, int badgeNumber, StringList badgeTypes ) {
        testDefinitionSteps.addStep( STR."Check events and their miniaturized badges on \{swimlaneName} swimlane" );
        assertEquals( "Configuration error in feature file (badge num vs. badge type list size)", badgeNumber, badgeTypes.getList().size() );
        TimelineEvent event = new TimelineEvent( eventType, eventName );
        timeLineSteps.checkMiniaturizedBadgesOnEvents( swimlaneName, event, badgeNumber, badgeTypes.getList() );
        testDefinitionSteps.logEvidence( STR."Event \{eventName} of type \{eventType.name()} is available on swimlane \{swimlaneName
                        } and has \{badgeNumber} miniaturized badges of following types \{
                        String.join( ", ", badgeTypes.getList() )}",
                "Event is present and has expected badges (See previous logs)", true );
    }

    @Given( "the following {event|events} $isAvailable available on the timeline: $eventsWithSwimlane" )
    @Then( "the following {event|events} $isAvailable available on the timeline: $eventsWithSwimlane" )
    public void checkEventsAvailabilityOnTimeline( Boolean isAvailable, List<TimelineEvent> eventsWithSwimlane ) {
        PageUtils.waitForDOMToBeStable( 2 );
        testDefinitionSteps.addStep( "Check events in timeline" );
        timeLineSteps.checkEventsAvailabilityOnTimeline( eventsWithSwimlane, isAvailable );

        eventsWithSwimlane.forEach( e -> testDefinitionSteps.logEvidence( STR."Timeline event \{e} is \{isAvailable ? "" : "not"} available",
                STR."Event is \{isAvailable ? "" : "not"} available (See previous logs)", true, false ) );

        testDefinitionSteps.logEvidence( "All timeline events from above are present",
                STR."All events were \{isAvailable ? "" : "not"} available (see previous logs)", true );
    }

    @Then( "all swimlanes have at least one event" )
    public void verifyAllSwimlanesHaveEvent() {
        testDefinitionSteps.addStep( "Check events on all swimlanes" );
        timeLineSteps.verifyAllSwimlanesHaveEvent();
        testDefinitionSteps.logEvidence( "All swimlanes have at least 1 event",
                "All swimlanes had at least 1 event (see previous logs)", true );
    }

    @Then( "all events in all swimlanes have a label" )
    public void verifyAllEventsHaveLabels() {
        testDefinitionSteps.addStep( "Check all events on all swimlanes" );
        timeLineSteps.verifyAllEventsHaveLabels();
        testDefinitionSteps.logEvidence( "All events in all swimlanes have a label",
                "all events in all swimlanes had a label (see previous logs)", true );
    }

    @Then( "all event labels in all swimlanes contain a name" )
    public void verifyAllEventsLabelsHaveName() {
        testDefinitionSteps.addStep( "Check all events on all swimlanes" );
        timeLineSteps.verifyAllEventsLabelsHaveName();
        testDefinitionSteps.logEvidence( "All event labels in all swimlanes contain a name",
                "All event labels in all swimlanes contained a name (see previous logs)", true );
    }

    @Then( "all event clusters in all swimlanes displays a number" )
    public void verifyAllEventClustersDisplayNumber() {
        testDefinitionSteps.addStep( "Check all event clusters on all swimlanes" );
        timeLineSteps.verifyAllEventClustersDisplayNumber();
        testDefinitionSteps.logEvidence( "All event clusters in all swimlanes displays a number",
                "All event clusters in all swimlanes displayed a number (see previous logs)", true );
    }

    @Then( "the earliest date on the timeline is \"$date\"" )
    public void checkFirstDateOnTimelineAxis( String date ) {
        testDefinitionSteps.addStep( "Check earliest date on the timeline" );
        timeLineSteps.checkFirstDateOnTimelineAxis( date );
        testDefinitionSteps.logEvidence( STR."The earliest date on the timeline is \{date}",
                STR."The earliest date on the timeline was \{date}", true );
    }

    ;

    @Then( "the latest date on the timeline is the date of the current or the next day" )
    public void checkLastDateOnTimelineAxis() {
        testDefinitionSteps.addStep( "Check the latest date on the timeline" );
        timeLineSteps.checkLastDateOnTimelineAxis();
        testDefinitionSteps.logEvidence( "The latest date on the timeline is the date of the current or the next day",
                "The latest date on the timeline was the date of the current or the next day", true );
    }

    @Given( "the following values $isVisible visible on the timeline axis: $timelineAxisValues" )
    @Then( "the following values $isVisible visible on the timeline axis: $timelineAxisValues" )
    public void checkTimelineAxisValues( Boolean isVisible, StringList timelineAxisValues ) {
        testDefinitionSteps.addStep( "Check timeline axis values" );
        timeLineSteps.checkTimelineAxisValues( isVisible, timelineAxisValues.getList() );

        timelineAxisValues.getList().forEach( v -> testDefinitionSteps.logEvidence( STR."The value '\{v}' is \{
                        isVisible ? "" : "not"} visible on the timeline axis",
                "Expected values were present on timeline axis (see previous logs)", true, false ) );

        testDefinitionSteps.logEvidence( STR."All above values are \{isVisible ? "" : "not"} visible on timeline axis",
                STR."All above values were \{isVisible ? "" : "not"} visible on timeline axis (see previous logs)", true );

    }

    @Given( "the timeline axis values are ascending from left to the right" )
    @Then( "the timeline axis values are ascending from left to the right" )
    public void checkTimelineAxisValuesAscendingFromLeftToRight() {
        testDefinitionSteps.addStep( "Check timeline axis values" );
        timeLineSteps.checkTimelineAxisValuesAscendingFromLeftToRight( true );
        testDefinitionSteps.logEvidence( "Check timeline axis values ascending from left to right",
                "Timeline axis values are ascending from left to right (see previous logs)", true );
    }

    @Given( "the timeline axis values are descending from left to the right" )
    @Then( "the timeline axis values are descending from left to the right" )
    public void checkTimelineAxisValuesInDescendingOrder() {
        testDefinitionSteps.addStep( "Check timeline axis values" );
        timeLineSteps.checkTimelineAxisValuesAscendingFromLeftToRight( false );
        testDefinitionSteps.logEvidence( "Check timeline axis values descending from left to right",
                "Timeline axis values are descending from left to right (see previous logs)", true );
    }

    @Given( "the timeline is moved to the following event on \"$swimlaneName\" swimlane: $events" )
    @When( "the user moves to the following event on \"$swimlaneName\" swimlane: $events" )
    public void moveToEvent( @Named( "swimlaneName" ) String swimlaneName,
                             @Named( "events" ) List<TimelineEvent> events ) {
        testDefinitionSteps.addStep( STR."Move timeline to event \{events.get( 0 )}" );
        assertThat( "Only one item can be added to the table in this step definition", events.size(), is( equalTo( 1 ) ) );
        timeLineSteps.moveToEvent( swimlaneName, events.get( 0 ) );
        testDefinitionSteps.logEvidence( "Timeline successfully moved to event", "Timeline moved to event without errors", true );
    }

    @Given( "the below events on \"$swimlaneName\" swimlane $isVisible visible in the viewport: $events" )
    @Then( "the below events on \"$swimlaneName\" swimlane $isVisible visible in the viewport: $events" )
    @Alias( "the below event on \"$swimlaneName\" swimlane $isVisible visible in the viewport: $events" )
    public void checkVisibleEventsInTheViewport( @Named( "swimlaneName" ) String swimlaneName,
                                                 @Named( "events" ) List<TimelineEvent> events,
                                                 @Named( "isVisible" ) Boolean isVisible ) {
        testDefinitionSteps.addStep( STR."Check events on \{swimlaneName} swimlane" );
        timeLineSteps.checkVisibleEventsInTheViewport( swimlaneName, events, isVisible );
        events.forEach( e -> testDefinitionSteps.logEvidence( STR."Event \{e} on \{swimlaneName} swimlane is \{
                        isVisible ? "" : "not"} visible in viewport",
                STR."Event is is \{isVisible ? "" : "not"} visible (See previous logs)", true, false ) );
        testDefinitionSteps.logEvidence( STR."All events from above are \{isVisible ? "" : "not"} visible in viewport",
                STR."All events were \{isVisible ? "" : "not"} visible in viewport (See previous logs)", true );
    }

    @Then( "the following event's label on the \"$swimlaneName\" swimlane $isBold bold: $events" )
    public void checkEventLabelTextIsBold( String swimlaneName, Boolean isBold, List<TimelineEvent> events ) {
        testDefinitionSteps.addStep( STR."Check labels of events on \{swimlaneName} swimlane" );
        timeLineSteps.checkEventLabelTextIsBold( swimlaneName, isBold, events );
        events.forEach( e -> testDefinitionSteps.logEvidence( STR."Event \{
                e} does \{isBold ? "" : "not"} have a bold label", STR."Event \{
                e} did \{isBold ? "" : "not"} have a bold label", true, false ) );
        testDefinitionSteps.logEvidence( "All events from above have expected label boldness",
                "All events from above have expected label boldness (See previous logs)", true );
    }

    @Given( "\"Now\" marker is displayed on the \"$side\" side of the timeline axis" )
    @Then( "\"Now\" marker is displayed on the \"$side\" side of the timeline axis" )
    public void checkTodayTimelineAxisLabelPosition( String side ) {
        testDefinitionSteps.addStep( "Check position of 'Today' marker" );
        timeLineSteps.checkTodayTimelineAxisLabelPosition( side );
        testDefinitionSteps.logEvidence( STR."'Today' marker is displayed on the \{side} side of the timeline axis",
                STR."'Today' marker was displayed on the \{side} side of the timeline axis (see previous logs)", true );
    }

    @Then( "\"$event\" event marker is displayed on the \"$side\" side of the timeline axis" )
    public void checkEventDisplayedPosition( String event, String side ) {
        testDefinitionSteps.addStep( STR."Check position of \{event} marker" );
        timeLineSteps.checkEventPositionInTimeLine( event, side );
        testDefinitionSteps.logEvidence( STR."'\{event}' marker is displayed on the \{side} side of the timeline axis",
                STR."'\{event}' marker was displayed on the \{side} side of the timeline axis (see previous logs)", true );
    }

    @Then( "the timeline banner displays the following notification: \"$notificationText\"" )
    public void checkTimelineBannerNotification( String notificationText ) {
        testDefinitionSteps.addStep( STR."Checkin timeline banner notification" );
        timeLineSteps.waitUntilPatientTimelineIsLoaded();
        timeLineSteps.checkTimelineBannerNotification( notificationText );
        testDefinitionSteps.logEvidence( STR."Timeline banner notification was displayed as: \{notificationText}",
                STR."Timeline banner notification was displayed as: \{notificationText}", true );
    }

    @Then( "the timeline loading skeleton $isVisible visible" )
    public void checkTimelineLoadingSkeletonVisibility( Boolean isVisible ) {
        testDefinitionSteps.addStep( STR."Checking timeline loading skeleton visibility" );
        timeLineSteps.checkTimelineLoadingSkeletonVisibility( isVisible );
        testDefinitionSteps.logEvidence( STR."Timeline loading skeleton is\{isVisible ? " " : " not "}visible",
                STR."Timeline loading skeleton was\{isVisible ? " " : " not "}visible", true );
    }
}
