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
package com.ge.onchron.verif.whatsteps.popups;

import java.util.List;
import java.util.stream.Collectors;

import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.model.detailedDataItems.CommentItem;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.ge.onchron.verif.howsteps.ClusterPillSteps;
import com.ge.onchron.verif.model.Badge;
import com.ge.onchron.verif.model.ClusterPillEvent;
import com.ge.onchron.verif.model.StringList;
import net.thucydides.core.annotations.Steps;

import static com.ge.onchron.verif.TestSystemParameters.TIMELINE_AXIS_TOOLTIP_DATE_FORMAT;
import static com.ge.onchron.verif.utils.ReplaceUtils.replaceDatePlaceholders;
import static joptsimple.internal.Strings.EMPTY;

public class ClusterPill {

    @Steps
    private ClusterPillSteps clusterPillSteps;
    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Given( "the cluster pill is $isOpened" )
    @Then( "the cluster pill is $isOpened" )
    public void checkClusterPillVisibility( Boolean isOpened ) {
        testDefinitionSteps.addStep("Check cluster pill visibility");
        clusterPillSteps.checkClusterPillVisibility( isOpened );
        testDefinitionSteps.logEvidence(STR."The cluster pill is visible: \{isOpened ? EMPTY : "not"} visible",
                "The cluster pill is visible (check previous logs)", true);
    }

    @Then( value = "the cluster pill is still $isOpened", priority = 1 )
    public void checkClusterPillStillVisible( Boolean isOpened ) {
        checkClusterPillVisibility( isOpened );
    }

    @When( "the user hovers over the cluster pill" )
    public void hoverClusterPill() {
        testDefinitionSteps.addStep("Hover cluster pill");
        clusterPillSteps.hoverClusterPill();
        testDefinitionSteps.logEvidence("Successfully hovered over cluster pill",
                "Successfully hovered over cluster pill (without errors)", true);
    }

    @When( "the user clicks on the cluster pill" )
    public void clickOnClusterPill() {
        testDefinitionSteps.addStep("Click on the cluster pill");
        clusterPillSteps.clickOnClusterPill();
        testDefinitionSteps.logEvidence("Successfully clicked on the cluster pill",
                "Successfully clicked on cluster pill (button clicked with no errors)", true);
    }

    @When( "the user closes the cluster pill" )
    public void closeClusterPill() {
        testDefinitionSteps.addStep("Close the cluster pill");
        clusterPillSteps.hoverClusterPill();
        clusterPillSteps.clickOnClusterPill();
        testDefinitionSteps.logEvidence("Successfully closed the cluster pill",
                "Successfully closed the cluster pill (button clicked with no errors)", true);
    }

    @Then( "the cluster pill displays the following text: \"$text\"" )
    public void checkClusterPillText( String text ) {
        testDefinitionSteps.addStep("Check cluster pill text");
        clusterPillSteps.checkClusterPillText( replaceDatePlaceholders( text, TIMELINE_AXIS_TOOLTIP_DATE_FORMAT ) );
        testDefinitionSteps.logEvidence(STR."The cluster pill text is: \{text}",
                "The cluster pill is as expected (check previous logs)", true);
    }

    @Then( "the following events are displayed in the cluster pill: $eventList" )
    public void checkEventsInClusterPill( List<ClusterPillEvent> eventList ) {
        testDefinitionSteps.addStep(STR."Check events are displayed in cluster pill");
        clusterPillSteps.checkEventsInClusterPill( eventList );
        testDefinitionSteps.logEvidence(STR."The events are displayed as expected in the cluster pill: \{eventList.stream().map(ClusterPillEvent::toString).collect(Collectors.joining("\n"))}}",
                "The events are displayed as expected (check previous logs)", true);
    }

    @Then( "the \"$event\" event is available in the custer pill with the below badges: $badges" )
    public void checkBadgesOnEventInClusterPill( ClusterPillEvent event, List<Badge> badges ) {
        testDefinitionSteps.addStep(STR."Check \{event} event badges on cluster pill");
        event.setBadges( badges );
        clusterPillSteps.checkBadgesOnEventInClusterPill( event );
        testDefinitionSteps.logEvidence(STR."The \{event} event is available in the cluster pill with the expected badges: \{badges}",
                STR."The \{event} event is available in the cluster pill with the expected badges (check previous logs)", true);
    }
    @Then( "the \"$event\" event is available in the custer pill without any badges" )
    public void checkNoBadgesOnEventInClusterPill( ClusterPillEvent event) {
        testDefinitionSteps.addStep(STR."Check \{event} event badges on cluster pill");
        clusterPillSteps.checkNoBadgesOnEventInClusterPill( event );
        testDefinitionSteps.logEvidence(STR."The \{event} event is available in the cluster pill without any badges",
                STR."The \{event} event is available in the cluster pill without any badges (check previous logs)", true);
    }

    @Then( "the \"$event\" event is available in the custer pill with the below miniaturized badges: $badgeTypes" )
    public void checkMiniaturizedBadgesOnEventInClusterPill( ClusterPillEvent event, StringList badgeTypes ) {
        testDefinitionSteps.addStep(STR."Check \{event} event in the custer pill has miniaturized badges");
        clusterPillSteps.checkMiniatuirzedBadgesOnEventInClusterPill( event, badgeTypes.getList() );
        testDefinitionSteps.logEvidence(STR."The \{event} event is available in the cluster pill with the expected miniaturized badges: \{badgeTypes}",
                STR."The \{event} event is available in the cluster pill with the expected miniaturized badges (check previous logs)", true);
    }

    @When( "the user hovers the \"$event\" event in the cluster pill" )
    public void hoverEventInClusterPill( ClusterPillEvent event ) {
        testDefinitionSteps.addStep("Hover event in the cluster pill");
        clusterPillSteps.hoverEventInClusterPill( event );
        testDefinitionSteps.logEvidence("Successfully hovered event in the cluster pill",
                "Successfully hovered event in the cluster pill (without errors)", true);
    }

    @When( "the user unhovers the \"$event\" event in the cluster pill" )
    public void unhoverEventInClusterPill( ClusterPillEvent event ) {
        testDefinitionSteps.addStep("Un-hover event in the cluster pill");
        clusterPillSteps.unhoverEventInClusterPill( event );
        testDefinitionSteps.logEvidence("Successfully un-hovered event in the cluster pill",
                "Successfully un-hovered event in the cluster pill (without errors)", true);
    }

    @Given( "the following event was selected from the cluster pill: $event" )
    @When( "the user selects the following event from the cluster pill: $event" )
    @Alias( "the user clicks on the following event on the cluster pill: $event" )
    public void selectEventOnClusterPill( ClusterPillEvent event ) {
        testDefinitionSteps.addStep(STR."Select following event on the cluster pill:\n\{event}");
        clusterPillSteps.selectEventOnClusterPill( event );
        testDefinitionSteps.logEvidence("Event selected successfully",
                "Event was selected without errors", true);

    }

}
