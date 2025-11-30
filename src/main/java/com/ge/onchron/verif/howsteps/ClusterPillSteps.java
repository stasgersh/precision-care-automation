/*
 * -GEHC CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2023, 2023, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE HealthCare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 */
package com.ge.onchron.verif.howsteps;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.ge.onchron.verif.model.Badge;
import com.ge.onchron.verif.model.ClusterPillEvent;
import com.ge.onchron.verif.model.enums.BadgeType;
import com.ge.onchron.verif.pages.sections.ClusterPill;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClusterPillSteps {

    private ClusterPill clusterPill;

    private static final Logger LOGGER = LoggerFactory.getLogger( ClusterPillSteps.class );

    public void checkClusterPillVisibility( Boolean isOpened ) {
        boolean clusterPillVisible = clusterPill.isVisible();
        LOGGER.info(STR."The actual cluster pill visibility is: \{clusterPillVisible}");
        assertThat( "Cluster pill visibility is not ok.", clusterPillVisible, is( equalTo( isOpened ) ) );
    }

    public void checkClusterPillText( String expectedText ) {
        String clusterPillText = clusterPill.getClusterPillText();
        LOGGER.info(STR."The actual cluster pill text is: \{clusterPillText}");
        assertEquals( "Cluster pill text is not ok.", expectedText, clusterPillText );
    }

    public void hoverClusterPill() {
        clusterPill.hoverClusterPill();
    }

    public void clickOnClusterPill() {
        clusterPill.clickOnClusterPill();
    }

    public void checkEventsInClusterPill( List<ClusterPillEvent> expectedEvents ) {
        boolean withDate = expectedEvents.get( 0 ).getDateOnTooltip() != null; // in order to know if event dates need to be checked or not
        boolean withBadges = false;
        List<ClusterPillEvent> observedEvents = clusterPill.getEvents( withDate, withBadges );
        if ( !withDate ) {
            LOGGER.info(STR."Actual events: \{observedEvents}");
            assertEquals( "Events in cluster pill are not ok.", expectedEvents, observedEvents );
        } else {
            LOGGER.info(STR."Actual events size: \{observedEvents.size()}");
            assertEquals( String.format( "Number of events in cluster pill does not match. Expected: %s, Observed: %s", expectedEvents.size(), observedEvents.size() ),
                    expectedEvents.size(), observedEvents.size() );
            verifyClusterEventsWithDate( expectedEvents, observedEvents );
        }
    }

    /**
     * Verify events in cluster pill
     * Ignoring the order of those events which have the same date
     *
     * @param expectedEvents all expected events in the cluster pill
     * @param observedEvents all observed events in the cluster pill
     */
    private void verifyClusterEventsWithDate( List<ClusterPillEvent> expectedEvents, List<ClusterPillEvent> observedEvents ) {
        for ( int i = 0; i < expectedEvents.size(); i++ ) {
            ClusterPillEvent actualExpectedEvent = expectedEvents.get( i );
            List<ClusterPillEvent> expectedEventsWithSameDate = new ArrayList<>();
            expectedEventsWithSameDate.add( actualExpectedEvent );
            for ( int j = i + 1; j < expectedEvents.size(); j++ ) {
                ClusterPillEvent nextExpectedEvent = expectedEvents.get( j );
                if ( actualExpectedEvent.getDateOnTooltip().equals( nextExpectedEvent.getDateOnTooltip() ) ) {
                    expectedEventsWithSameDate.add( nextExpectedEvent );
                } else {
                    break;
                }
            }
            List<ClusterPillEvent> observedEventCandidatesWithSameDate = observedEvents.subList( i, i + expectedEventsWithSameDate.size() );
            assertThat( String.format( "Events in cluster does not match. \nAll expected: %s,\nAll observed: %s\n", expectedEvents, observedEvents ),
                    observedEventCandidatesWithSameDate, containsInAnyOrder( expectedEventsWithSameDate.toArray() ) );
            i = i + expectedEventsWithSameDate.size() - 1;
        }
    }

    public void checkBadgesOnEventInClusterPill( ClusterPillEvent eventWithBadges ) {
        ClusterPillEvent observedEvent = getEventFromClusterPill( eventWithBadges );
        // Check badges without their color
        val observedEventBadges = observedEvent.getBadges();
        LOGGER.info(STR."The actual event badges: \{observedEventBadges}");
        assertEquals( "Events in cluster pill are not ok.", eventWithBadges.getBadges(), observedEventBadges );
    }

    public void checkNoBadgesOnEventInClusterPill( ClusterPillEvent eventWithBadges ) {
        ClusterPillEvent observedEvent = getEventFromClusterPill( eventWithBadges );
        // Check badges without their color
        val observedEventBadges = observedEvent.getBadges();
        LOGGER.info(STR."The actual event badges: \{observedEventBadges}");
        assertEquals( "Events in cluster pill are not ok.", eventWithBadges.getBadges(), observedEventBadges );
    }

    public void checkMiniatuirzedBadgesOnEventInClusterPill( ClusterPillEvent eventWithBadges, List<String> badgeTypes ) {
        boolean withDate = eventWithBadges.getDateOnTooltip() != null;
        boolean withBadges = true;
        List<ClusterPillEvent> observedEvents = clusterPill.getEvents( withDate, withBadges );
        ClusterPillEvent observedEvent = getEventFromClusterPill( eventWithBadges );

        // Check miniaturized badges without their color
        int actualBadgesSize = observedEvent.getBadges().size();
        LOGGER.info(STR."The actual event badges number is: \{actualBadgesSize}");
        assertEquals( "Number of expected and observed badges do not match on event label in cluster pill.", badgeTypes.size(), actualBadgesSize );

        for ( int i = 0; i < observedEvent.getBadges().size(); i++ ) {
            Badge actualBadge = observedEvent.getBadges().get( i );
            // In case of miniaturized badges, there are no badge texts
            LOGGER.info(STR."Actual badge text: \{actualBadge.getText()}");
            assertTrue( "Badge should be miniaturized without any texts on it: " + actualBadge, actualBadge.getText().isEmpty() );
            LOGGER.info(STR."Actual badge type: \{actualBadge.getBadgeType()}");
            assertEquals( "Badge type is not ok for badge: " + actualBadge, BadgeType.valueOf( badgeTypes.get( i ) ), actualBadge.getBadgeType() );
        }

    }

    private ClusterPillEvent getEventFromClusterPill( ClusterPillEvent requiredEvent ) {
        boolean withDate = requiredEvent.getDateOnTooltip() != null;
        boolean withBadges = true;
        List<ClusterPillEvent> observedEvents = clusterPill.getEvents( withDate, withBadges );
        return observedEvents.stream()
                             .filter( oe -> {
                                 String expectedDate = requiredEvent.getDateOnTooltip() != null ? requiredEvent.getDateOnTooltip() : "";
                                 String observedDate = oe.getDateOnTooltip() != null ? requiredEvent.getDateOnTooltip() : "";
                                 return oe.getName().equals( requiredEvent.getName() ) && observedDate.equals( expectedDate );
                             } )
                             .findFirst()
                             .orElseThrow( () -> new RuntimeException( "Following event was not found in cluster pill: " + requiredEvent ) );
    }

    public void hoverEventInClusterPill( ClusterPillEvent event ) {
        clusterPill.hoverEventInClusterPill( event );
    }

    public void unhoverEventInClusterPill( ClusterPillEvent event ) {
        clusterPill.unhoverEventInClusterPill( event );
    }

    public void selectEventOnClusterPill( ClusterPillEvent event ) {
        clusterPill.clickOnEventOnClusterPill( event );
    }

}
