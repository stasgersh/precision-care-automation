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
package com.ge.onchron.verif.whatsteps.popups;

import java.util.List;

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.ge.onchron.verif.howsteps.L3ModalSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.model.l3report.L3ModalHeader;
import com.ge.onchron.verif.model.l3report.L3ReportHeader;
import com.ge.onchron.verif.model.l3report.L3ReportHeaderOnEventsList;
import net.thucydides.core.annotations.Steps;

public class L3Modal {

    @Steps
    private L3ModalSteps l3ModalSteps;

    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Given( "the full report modal $isDisplayed displayed on the screen" )
    @Then( "the full report modal $isDisplayed displayed on the screen" )
    @Alias( "the full reports modal $isDisplayed displayed on the screen" )
    public void checkL3ModalIsDisplayed( Boolean isDisplayed ) {
        testDefinitionSteps.addStep( "Check L3 Modal is displayed" );
        l3ModalSteps.checkL3ModalIsDisplayed( isDisplayed );
        testDefinitionSteps.logEvidence( STR."The L3 Modal is \{isDisplayed ? "" : "not"} displayed",
                STR."The L3 Modal is \{isDisplayed ? "" : "not"} displayed (see previous logs)", true );
    }

    @When( "the user clicks on the 'X' button on the full report modal" )
    public void closeL3Modal() {
        testDefinitionSteps.addStep( "Close L3 Modal" );
        l3ModalSteps.closeL3Modal();
        testDefinitionSteps.logEvidence( "The L3Modal was closed successfully",
                "L3Modal was closed successfully (without errors)", true );
    }

    @Then( "the full report modal header contains the following data: $headerData" )
    public void checkL3SingleReportModalHeader( L3ReportHeader headerData ) {
        testDefinitionSteps.addStep( "Check L3 Modal header" );
        l3ModalSteps.checkL3SingleReportModalHeaderData( headerData );
        testDefinitionSteps.logEvidence( STR."The L3Modal header has the expected header data: \{headerData}",
                "L3Modal has expected header data (check previous logs)", true );
    }

    @Then( "the full report modal body contains the following data: $fileName" )
    public void checkL3SingleReportModalBody( String fileName ) {
        testDefinitionSteps.addStep( "Check L3 Modal body" );
        l3ModalSteps.checkL3SingleReportModalBody( fileName );
        testDefinitionSteps.logEvidence( STR."The L3Modal has the expected body from file: \{fileName}",
                "L3Modal has expected body data (check previous logs)", true );
    }

    @Then( "selected report on the multiple report modal contains the following header data: $headerData" )
    public void checkSelectedReportHeaderOnMultipleReportModal( L3ReportHeader headerData ) {
        testDefinitionSteps.addStep( String.format( "Check that the selected report on the multiple report modal contains the following header data: %s", headerData ) );
        l3ModalSteps.checkSelectedReportHeaderOnMultipleReportModal( headerData );
        testDefinitionSteps.logEvidence(
                String.format( "the selected report on the multiple report modal contains the following header data: %s", headerData ),
                String.format( "the selected report on the multiple report modal contains the following header data: %s", headerData ), true );
    }

    @Then( "selected report on the multiple report modal contains the following content: $fileName" )
    public void checkSelectedReportBodyOnMultipleReportModal( String fileName ) {
        testDefinitionSteps.addStep( String.format( "Check that the selected report on the multiple report modal contains the following content: %s", fileName ) );
        l3ModalSteps.checkSelectedReportBodyOnMultipleReportModal( fileName );
        testDefinitionSteps.logEvidence(
                String.format( "the selected report on the multiple report modal contains the following content: %s", fileName ),
                String.format( "the selected report on the multiple report modal contains the following content: %s", fileName ), true );
    }

    @Then( "the event list on the multiple report modal contains the following events: $eventData" )
    public void checkEventsListOnMultipleReportModal( List<L3ReportHeaderOnEventsList> eventData ) {
        testDefinitionSteps.addStep( String.format( "Check that the event list on the multiple report modal contains the following events: %s", eventData ) );
        l3ModalSteps.checkEventsListOnMultipleReportModal( eventData );
        testDefinitionSteps.logEvidence(
                String.format( "the event list on the multiple report modal contains the following events: %s", eventData ),
                String.format( "the event list on the multiple report modal contains the following events: %s", eventData ), true );
    }

    @When( "the user clicks on the following event on the event list on the multiple report modal: $eventData" )
    public void clickEventOnEventsListOnMultipleReportModal( L3ReportHeaderOnEventsList eventData ) {
        testDefinitionSteps.addStep( String.format( "the user clicks on the following event on the event list on the multiple report modal: %s", eventData ) );
        l3ModalSteps.clickEventOnEventsListOnMultipleReportModal( eventData );
        testDefinitionSteps.logEvidence(
                String.format( "the user clicked on the following event on the event list on the multiple report modal successfully: %s", eventData ),
                String.format( "the user clicked on the following event on the event list on the multiple report modal successfully: %s", eventData ), true );
    }

    @Then( "the multiple report modal header contains the following data: $modalHeaderData" )
    public void checkMultipleReportModalHeader( L3ModalHeader l3ModalHeader ) {
        testDefinitionSteps.addStep( String.format( "Check that the L3 modal header contains the following data: %s", l3ModalHeader ) );
        l3ModalSteps.checkMultipleReportModalHeader( l3ModalHeader );
        testDefinitionSteps.logEvidence(
                String.format( "the modal header contains the following data: %s", l3ModalHeader ),
                String.format( "the modal header contains the following data: %s", l3ModalHeader ), true );
    }

}
