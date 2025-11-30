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
package com.ge.onchron.verif.pages.tabs;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.Badge;
import com.ge.onchron.verif.model.TimelineEvent;
import com.ge.onchron.verif.model.enums.EventType;
import com.ge.onchron.verif.pages.PatientPage;
import com.ge.onchron.verif.pages.elements.EventElement;
import com.ge.onchron.verif.pages.elements.Swimlane;
import java.awt.Point;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.ListOfWebElementFacades;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.webdriver.WebDriverFacade;

import static com.ge.onchron.verif.SystemTestConstants.BOLD_TEXT_FONT_WEIGHT;
import static com.ge.onchron.verif.SystemTestConstants.FONT_WEIGHT;
import static com.ge.onchron.verif.TestSystemParameters.RESET_BROWSER_SIZE_REQUIRED;
import static com.ge.onchron.verif.pages.utils.BadgeUtils.getEventBadgesFromWebElement;
import static com.ge.onchron.verif.pages.utils.PageElementUtils.isVisibleInViewport;
import static com.ge.onchron.verif.pages.utils.PageElementUtils.moveToElementJS;
import static com.ge.onchron.verif.pages.utils.PageElementUtils.scrollIntoViewport;
import static com.ge.onchron.verif.pages.utils.PageElementUtils.unhoverElementJS;
import static com.ge.onchron.verif.pages.utils.PageUtils.currentlyContainsElements;
import static com.ge.onchron.verif.pages.utils.PageUtils.currentlyContainsWebElements;
import static com.ge.onchron.verif.utils.Utils.waitMillis;
import static net.serenitybdd.core.Serenity.setSessionVariable;

public class TimeLine extends PatientPage {

    private static final Logger LOGGER = LoggerFactory.getLogger( TimeLine.class );
    public static final String SWIMLANE_SELECTOR = "//*[contains(@class,'p-rel')]";

    private final String TIMELINE_LOADING_SKELETON_XPATH = "//*[contains(@class,'SkeletonLoader-module_timeline-container')]";

    private final String MARKER_CLASS = "SwimLanes-module_marker";
    private final String CLUSTER_CLASS = "SwimLanes-module_cluster";
    private final String EVENT_POINT_CSS_SELECTOR = "[class^='" + MARKER_CLASS + "'],[class^='" + CLUSTER_CLASS + "']";

    private final String EVENT_LABEL_CSS_SELECTOR = "[class*='TimelineTooltip-module_container']";

    private final String VERTICAL_GUIDE_XPATH = "//*[contains(@class,'TimelineAxis-module_tooltip-wrap')]";
    private final String TOOLTIP_LABEL_XPATH = ".//*[contains(@class,'tooltip__content')]";
    private final String VERTICAL_LINE_XPATH = ".//*[contains(@class,'TimelineAxis-module_line')]";

    private final String BADGE_CONTAINER_ON_EVENT_LABEL_CSS_SELECTOR = "[class*='TooltipBadgeList-module_badge-list']";

    private final String SWIMLANES_MODULE_HEADER_LANE_SELECTOR = "[class*='SwimLanes-module_lane_']";
    private final String SWIMLANES_MODULE_TOOLTIP_LANE_SELECTOR = "[class*='SwimLanes-module_tooltip-lane_']";


    @FindBy( xpath = "//*[contains(@class,'TimelineContent-module_container')]" )
    private WebElementFacade timelineContent;

    @FindBy( xpath = "//*[contains(@class,'TimelineDashboard-module_outer-wrap')]" )
    private WebElementFacade timelineDashboardWrapper;

    @FindBy( css = "[id='timeline-content']" )
    private WebElementFacade timelineDashboard;

    @FindBy( xpath = "//*[contains(@class,'TimelineAxis-module_container')]" )
    private WebElementFacade timelineAxis;

    @FindBy( xpath = "//*[contains(@class,'MacroNavAxis-module_container')]" )
    private WebElement timelineDashboardDates;


    public Boolean isTimelineVisible() {
        return timelineDashboard.isVisible();
    }

    private List<WebElementFacade> getVerticalGuides() {
        return timelineAxis.thenFindAll( By.xpath( VERTICAL_GUIDE_XPATH ) );
    }

    private WebElementFacade getHoveredEventVerticalGuide() {
        return getVerticalGuides().stream()
                                  .filter( guide ->
                                          !(guide.getTextContent().equalsIgnoreCase( "Now" ) ||
                                                  guide.getTextContent().equalsIgnoreCase( "Protocol start" )) )
                                  .findFirst()
                                  .orElse( null );
    }

    private WebElementFacade getTooltipLabelOfVerticalGuide( WebElementFacade verticalGuide ) {
        return verticalGuide.then( By.xpath( TOOLTIP_LABEL_XPATH ) );
    }

    private WebElementFacade getLineOfVerticalGuide( WebElementFacade verticalGuide ) {
        return verticalGuide.then( By.xpath( VERTICAL_LINE_XPATH ) );
    }

    public List<String> getVisibleTimelineAxisValues() {
        return timelineAxis.thenFindAll( By.tagName( "g" ) ).stream()
                           .map( WebElementFacade::getText )
                           .collect( Collectors.toList() );
    }

    public HashMap<String, Point> getVisibleTimelineAxisValuesWithPosition() {
        HashMap<String, Point> axisValues = new HashMap<>();
        ListOfWebElementFacades axisValueElements = timelineAxis.thenFindAll( By.tagName( "g" ) );
        for ( WebElementFacade axisValueElement : axisValueElements ) {
            WebElementFacade textElement = axisValueElement.find( By.tagName( "text" ) );
            String x = textElement.getAttribute( "x" );
            String y = textElement.getAttribute( "y" );
            axisValues.put( textElement.getText(), new Point( Double.valueOf( x ).intValue(), Double.valueOf( y ).intValue() ) );
        }
        return axisValues;
    }

    public Dimension getTimelineContentSize() {
        return timelineContent.getSize();
    }

    public void scrollTimelineTo( int x, int y ) {
        String javascriptCmd = "arguments[0].style.scrollBehavior='auto'; arguments[0].scrollTo(arguments[1], arguments[2]);";
        WebDriver webdriver = Serenity.getWebdriverManager().getWebdriver();
        ((WebDriverFacade) webdriver).executeScript( javascriptCmd, timelineDashboard, x, y );
        waitMillis( 600 );
    }

    public void scrollTimelineTo( int x ) {
        String script = "arguments[0].scrollTo(arguments[1], arguments[0].scrollTop);";
        WebDriver webdriver = Serenity.getWebdriverManager().getWebdriver();
        ((WebDriverFacade) webdriver).executeScript( script, timelineDashboard, x );
        waitMillis( 600 );

    }

    public void waitUntilTimelineLoadingSkeletonDisappears() {
        timelineDashboardWrapper.waitUntilPresent();
        waitUntilLoadingSkeletonDisappears( timelineDashboardWrapper, By.xpath( TIMELINE_LOADING_SKELETON_XPATH ) );
        waitForAngularRequestsToFinish();
    }

    public List<Swimlane> getSwimLanes() {
        waitUntilTimelineLoadingSkeletonDisappears();
        timelineContent.setImplicitTimeout( Duration.ofSeconds( (10) ) );
        List<WebElementFacade> swimlaneCandidates = timelineContent.thenFindAll( By.xpath( SWIMLANE_SELECTOR ) );
        List<Swimlane> swimlanes = new ArrayList<>();

        swimlaneCandidates.forEach( swimlane -> {
            WebElementFacade header = swimlane.find( By.cssSelector( SWIMLANES_MODULE_HEADER_LANE_SELECTOR ) );
            WebElementFacade eventPointsContainer = swimlane.find( By.cssSelector( "svg" ) );
            WebElementFacade eventLabelContainer = swimlane.find( By.cssSelector( SWIMLANES_MODULE_TOOLTIP_LANE_SELECTOR ) );
            swimlanes.add( new Swimlane( header, eventPointsContainer, eventLabelContainer ) );
        } );

        LOGGER.info( STR."Observed swimlanes:\\n\{swimlanes.stream().map( Swimlane::getName ).collect( Collectors.joining( "\\n" ) )}" );
        return swimlanes;
    }

    public void clickOnEventPointOnSwimlane( String swimlaneName, TimelineEvent expectedEvent ) {
        int attempts = 0;
        while ( attempts < 5 ) {
            try {
                EventElement timelineEvent = hoverEventPointOnSwimlane( swimlaneName, expectedEvent );
                elementClicker( timelineEvent.getEventPoint() );
                return;
            } catch ( StaleElementReferenceException e ) {
                LOGGER.info( "Event hover or click is stale, retrying" );
                waitMillis( 500 );
                attempts++;
            }
        }
        throw new RuntimeException( "Failed to click or hover on the event after 5 attempts" );
    }

    public EventElement hoverEventPointOnSwimlane( String swimlaneName, TimelineEvent expectedEvent ) {
        EventElement timelineEvent = getEventOnSwimlane( swimlaneName, expectedEvent );
        // moveToElementJS() is also needed if this method is followed by a click action and the target is a cluster event
        moveToElementJS( timelineEvent.getEventPoint() );
        return timelineEvent;
    }

    public void hoverEventLabelOnSwimlane( String swimlaneName, TimelineEvent expectedEvent ) {
        EventElement timelineEvent = getEventOnSwimlane( swimlaneName, expectedEvent );
        moveToElementJS( timelineEvent.getEventLabel() );
    }

    public void unhoverEventLabelOnSwimlane( String swimlaneName, TimelineEvent expectedEvent ) {
        EventElement timelineEvent = getEventOnSwimlane( swimlaneName, expectedEvent );
        unhoverElementJS( timelineEvent.getEventLabel() );
    }

    private EventElement getEventOnSwimlane( String swimlaneName, TimelineEvent expectedEvent ) {
        List<EventElement> eventElements = getEventElementsFromSwimlane( swimlaneName, true );
        boolean withDate = expectedEvent.getDateOnTimelineAxis() != null;
        return eventElements.stream()
                            .filter( event -> createTimelineEvent( event, withDate, false, false ).equals( expectedEvent ) )
                            .findFirst()
                            .orElseThrow( () -> new RuntimeException( STR."The following event was not found on \{swimlaneName} swimlane: \{expectedEvent.toString( true )}" ) );
    }

    public boolean hasBoldLabelText( String swimlaneName, TimelineEvent expectedEvent ) {
        return getEventOnSwimlane( swimlaneName, expectedEvent ).getEventLabel().find( By.tagName( "text" ) ).getCssValue( FONT_WEIGHT ).equals( BOLD_TEXT_FONT_WEIGHT );
    }

    public List<TimelineEvent> getTimelineEventsFromSwimlane( String swimlaneName, boolean withDate, boolean withPosition, boolean withBadges ) {
        List<EventElement> eventElements = getEventElementsFromSwimlane( swimlaneName, true );
        List<TimelineEvent> timelineEvents = new ArrayList<>();
        for ( EventElement eventElement : eventElements ) {
            TimelineEvent event = createTimelineEvent( eventElement, withDate, withPosition, withBadges );
            timelineEvents.add( event );
        }
        return timelineEvents;
    }

    private List<EventElement> getEventElementsFromSwimlane( String swimlaneName, boolean scrollNeeded ) {
        Swimlane swimlane = getSwimlaneByName( swimlaneName );
        if ( scrollNeeded ) {
            scrollIntoViewport( swimlane.getEventLabelsContainer() );
        }
        // Get event points
        List<WebElementFacade> eventPoints = getEventPoints( swimlane );
        // Get event labels
        List<WebElementFacade> eventLabels = getEventLabels( swimlane );

        assertEquals( "The number of the event labels and points do not match on \"" + swimlaneName + "\" swimlane: " +
                        ", labels: " + eventLabels.size() + ", points: " + eventPoints.size(),
                eventLabels.size(), eventPoints.size() );

        List<EventElement> eventElements = new ArrayList<>();
        for ( int i = 0; i < eventPoints.size(); i++ ) {
            EventElement eventElement = new EventElement( eventPoints.get( i ), eventLabels.get( i ) );
            eventElements.add( eventElement );
        }
        return eventElements;
    }

    public List<WebElementFacade> getEventPoints( Swimlane swimlane ) {
        List<WebElementFacade> eventPoints = new ArrayList<>();
        WebElementFacade eventPointsContainer = swimlane.getEventPointsContainer();
        if ( currentlyContainsWebElements( eventPointsContainer, By.cssSelector( EVENT_POINT_CSS_SELECTOR ) ) ) {
            List<WebElementFacade> eventPointCandidates = eventPointsContainer.thenFindAll( By.cssSelector( EVENT_POINT_CSS_SELECTOR ) );
            for ( WebElementFacade eventPointCandidate : eventPointCandidates ) {
                // Workaround to avoid hidden event cluster points (after cluster animation or filter)
                if ( eventPointCandidate.getAttribute( "class" ).contains( CLUSTER_CLASS ) && !currentlyContainsElements( eventPointCandidate, By.tagName( "text" ) ) ) {
                    LOGGER.warn( "Hidden event cluster was found!" );
                } else {
                    eventPoints.add( eventPointCandidate );
                }
            }
        }
        return eventPoints;
    }

    public List<WebElementFacade> getEventLabels( Swimlane swimlane ) {
        List<WebElementFacade> eventLabels = new ArrayList<>();
        WebElementFacade eventLabelsContainer = swimlane.getEventLabelsContainer();
        if ( currentlyContainsWebElements( eventLabelsContainer, By.cssSelector( EVENT_LABEL_CSS_SELECTOR ) ) ) {
            eventLabels = eventLabelsContainer.thenFindAll( By.cssSelector( EVENT_LABEL_CSS_SELECTOR ) );
        }
        return eventLabels;
    }

    private Swimlane getSwimlaneByName( String swimlaneName ) {
        return getSwimLanes().stream()
                             .filter( sl -> sl.getName().equals( swimlaneName ) )
                             .findFirst()
                             .orElseThrow( () -> new RuntimeException( "Swimlane was not found: " + swimlaneName ) );
    }

    private TimelineEvent createTimelineEvent( EventElement eventElement, boolean withDate, boolean withPosition, boolean withBadges ) {
        TimelineEvent timelineEvent = new TimelineEvent();
        timelineEvent.setTextOnEventPoint( eventElement.getEventPoint().getText() );

        // Event point can be a marker or a cluster
        String eventPointClassName = eventElement.getEventPoint().getAttribute( "class" );
        if ( eventPointClassName.contains( MARKER_CLASS ) ) {
            timelineEvent.setEventType( EventType.MARKER );
        } else if ( eventPointClassName.contains( CLUSTER_CLASS ) ) {
            timelineEvent.setEventType( EventType.CLUSTER );
        } else {
            fail( "Not known event type:" + eventPointClassName );
        }

        String eventName = getEventName( eventElement.getEventLabel() );
        timelineEvent.setNameLabel( eventName );

        if ( withDate ) {
            String eventDate = getEventDate( eventElement );
            timelineEvent.setDateOnTimelineAxis( eventDate );
        }
        if ( withPosition ) {
            WebElementFacade rect = eventElement.getEventPoint().then( By.tagName( "rect" ) );
            String x = rect.getAttribute( "x" );
            String y = rect.getAttribute( "y" );
            timelineEvent.setPosition( new Point( Double.valueOf( x ).intValue(), Double.valueOf( y ).intValue() ) );
        }
        if ( withBadges ) {
            List<Badge> badges = getBadgesFromTimelineEvent( eventElement.getEventLabel() );
            timelineEvent.setBadges( badges );
        }

        LOGGER.info( "Observed timeline event: " + timelineEvent );
        return timelineEvent;
    }

    private String getEventDate( EventElement eventElement ) {
        moveToElementJS( eventElement.getEventPoint() );  // by using getEventPoint(), the event tooltip is not expanded but vertical guide is displayed
        WebElementFacade hoveredEventVerticalGuide = getHoveredEventVerticalGuide();

        // Retry to get vertical guide because sometimes the tooltip is not displayed for the first time after performing the above action
        int retry = 0;
        while ( hoveredEventVerticalGuide == null && retry < 3 ) {
            retry++;
            moveToElementJS( eventElement.getEventPoint() );
            hoveredEventVerticalGuide = getHoveredEventVerticalGuide();
        }
        assertNotNull( "Cannot get vertical guide from the hovered timeline event", hoveredEventVerticalGuide );

        WebElementFacade tooltipLabel = getTooltipLabelOfVerticalGuide( hoveredEventVerticalGuide );
        return tooltipLabel.getText();
    }

    private String getEventName( WebElementFacade labelElement ) {
        return labelElement.then( By.cssSelector( "[class*='TimelineTooltip-module_title']" ) ).getText();
    }

    private List<Badge> getBadgesFromTimelineEvent( WebElementFacade labelElement ) {
        List<Badge> badges = new ArrayList<>();
        if ( !currentlyContainsWebElements( labelElement, By.cssSelector( BADGE_CONTAINER_ON_EVENT_LABEL_CSS_SELECTOR ) ) ) {
            return badges; // return empty list when no badges on event label
        }
        WebElementFacade badgeContainer = labelElement.then( By.cssSelector( BADGE_CONTAINER_ON_EVENT_LABEL_CSS_SELECTOR ) );
        return getEventBadgesFromWebElement( badgeContainer );
    }

    public void moveToEvent( String swimlaneName, TimelineEvent expectedEvent ) {
        List<EventElement> eventElements = getEventElementsFromSwimlane( swimlaneName, true );
        boolean withDate = expectedEvent.getDateOnTimelineAxis() != null;
        EventElement requiredEvent = eventElements.stream()
                                                  .filter( event -> createTimelineEvent( event, withDate, false, false ).equals( expectedEvent ) )
                                                  .findFirst()
                                                  .orElseThrow( () -> new RuntimeException( STR."The following event was not found on \{swimlaneName} swimlane: \{expectedEvent.toString( true )}" ) );
        moveToElementJS( requiredEvent.getEventPoint() );
        unhoverElementJS( requiredEvent.getEventPoint() );    // in order to remove the 'hover state' from the event
    }

    public void checkEventsVisibilityInViewport( String swimlaneName, List<TimelineEvent> expectedEvents, Boolean visible ) {
        boolean eventsWithDate = expectedEvents.get( 0 ).getDateOnTimelineAxis() != null;
        List<EventElement> visibleEventElements = getEventElementsFromSwimlane( swimlaneName, false )
                .stream()
                .filter( eventElement -> isVisibleInViewport( eventElement.getEventPoint().getWrappedElement() ) )
                .toList();
        List<TimelineEvent> visibleEvents = visibleEventElements.stream()
                                                                .map( element -> createTimelineEvent( element, eventsWithDate, false, false ) )
                                                                .toList();
        LOGGER.info( STR."Observed events: \{visibleEvents.stream()
                                                          .map( TimelineEvent::toString )
                                                          .collect( Collectors.joining( "\\n" ) )}"
        );
        expectedEvents.forEach( expectedEvent -> {
            assertEquals( STR."The visibility of the following event on \{swimlaneName} is not ok: \{expectedEvent}", visible, visibleEvents.contains( expectedEvent ) );
        } );
    }

    public void setBrowserSize( int x, int y ) {
        Dimension dimension = new Dimension( x, y );
        getDriver().manage().window().setSize( dimension );
        setSessionVariable( RESET_BROWSER_SIZE_REQUIRED ).to( true );
    }

    public Rectangle getTimelineRect() {
        return timelineContent.getRect();
    }

    public Rectangle getTodayMarkerLabelRect() {
        return getVerticalGuides().stream()
                                  .filter( guide -> guide.getTextContent().equalsIgnoreCase( "Now" ) )
                                  .findFirst()
                                  .map( WebElementFacade::getRect )
                                  .orElse( null );
    }

    public List<WebElement> getListOfDatesOnTimelineLocation() {
        return timelineDashboardDates.findElements( By.cssSelector( "[class^='macronav-tick-svg']" ) );
    }

    public String getTimelineBannerNotificationText() {
        return timelineContent.find( By.cssSelector( "[class*='BannerNotification-module_msg_']" ) ).getText();
    }

    public boolean isLoadingSkeletonVisible() {
        Optional<WebElementFacade> loadingSkeleton = findFirst( By.xpath( TIMELINE_LOADING_SKELETON_XPATH ) );
        return loadingSkeleton.isPresent() && loadingSkeleton.get().isVisible();
    }

    public List<String> getLoadingSkeletonElements() {
        return findAll( By.cssSelector( "[id='timeline-skeleton-diff'] > *" ) )
                .stream()
                .map( e -> e.getAttribute( "outerHTML" ) )
                .collect( Collectors.toList() );
    }

    public void scrollTimelineToSwimlaneVertically( String swimlane ) {
        String swimlaneSelector = "//*[contains(@class, 'SwimLanes-module_swimlane-title_') and normalize-space(text())='placeholder']".replace( "placeholder", swimlane );
        List<WebElementFacade> targetSwimlanes = timelineDashboardWrapper.thenFindAll(
                By.xpath( swimlaneSelector ) );
        if ( targetSwimlanes.size() == 1 ) {
            scrollIntoViewport( targetSwimlanes.getFirst() );
        } else {
            fail( STR."Could not scroll vertically to swimlane: \{swimlane}" );
        }
    }
}
