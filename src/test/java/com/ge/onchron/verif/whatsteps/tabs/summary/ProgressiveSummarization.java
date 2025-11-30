/*
 * -GEHC CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2024, 2024, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE HealthCare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 */
package com.ge.onchron.verif.whatsteps.tabs.summary;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

import com.ge.onchron.verif.howsteps.ProgressiveSummarizationSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.model.StringList;
import com.ge.onchron.verif.model.Table;
import java.util.ArrayList;
import java.util.List;
import net.thucydides.core.annotations.Steps;

import static com.ge.onchron.verif.pages.utils.TableUtils.getExampleTableAsList;


public class ProgressiveSummarization {

    @Steps
    private ProgressiveSummarizationSteps psSteps;

    private TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();


    @When( "the user collapses the PS Overview" )
    public void checkPsOverviewIsCollapsed() {
        testDefinitionSteps.addStep( "the user collapses the PS Overview" );
        psSteps.collapsePsOverview();
        testDefinitionSteps.logEvidence( "the user collapsed the PS Overview", "the user collapsed the PS Overview", true );
    }

    @Then( "the Progressive Summarization Overview $isCollapsed collapsed" )
    public void checkPsIsCollapsed( Boolean isCollapsed ) {
        testDefinitionSteps.addStep( String.format( "Check that the Progressive Summarization Overview %s collapsed", isCollapsed ? "is" : "is not" ) );
        psSteps.checkPsOverviewIsCollapsed( isCollapsed );
        testDefinitionSteps.logEvidence(
                String.format( "the Progressive Summarization Overview %s collapsed", isCollapsed ? "is" : "is not" ),
                String.format( "the Progressive Summarization Overview %s collapsed", isCollapsed ? "is" : "is not" ), true );
    }

    @Given( "the Progressive Summarization Overview $isVisible displayed" )
    @Then( "the Progressive Summarization Overview $isVisible displayed" )
    public void checkPsOverviewVisibility( Boolean isVisible ) {
        testDefinitionSteps.addStep( String.format( "Check that the Progressive Summarization Overview %s displayed", isVisible ? "is" : "is not" ) );
        psSteps.checkPsOverviewVisibility( isVisible );
        testDefinitionSteps.logEvidence(
                String.format( "the Progressive Summarization Overview %s displayed", isVisible ? "is" : "is not" ),
                String.format( "the Progressive Summarization Overview %s displayed", isVisible ? "is" : "is not" ), true );
    }

    @Then( "the '$sectionName' section $isDisplayed displayed in PS Overview" )
    public void checkPsSectionVisibility( String sectionName, Boolean isDisplayed ) {
        testDefinitionSteps.addStep( String.format( "Check that the '%s' section %s displayed in PS Overview", sectionName, isDisplayed ? "is" : "is not" ) );
        psSteps.checkPsSectionVisibility( sectionName, isDisplayed );
        testDefinitionSteps.logEvidence(
                String.format( "the '%s' section %s displayed in PS Overview", sectionName, isDisplayed ? "is" : "is not" ),
                String.format( "the '%s' section %s displayed in PS Overview", sectionName, isDisplayed ? "is" : "is not" ), true );
    }

    @Given( "the '$sectionName' section has the title: \"$title\"" )
    @Then( "the '$sectionName' section has the title: \"$title\"" )
    public void checkPsSectionTitle( String sectionName, String title ) {
        testDefinitionSteps.addStep( String.format( "Check that the '%s' section has the title: %s", sectionName, title ) );
        psSteps.checkPsSectionTitle( sectionName, title );
        testDefinitionSteps.logEvidence(
                String.format( "the '%s' section has the title: %s", sectionName, title ),
                String.format( "the '%s' section has the title: %s", sectionName, title ), true );
    }

    @Then( "the '$sectionName' section $doesHave 'AI is turned off' information" )
    public void checkAiTurnedOffInfoVisibilityOnPsSection( String sectionName, Boolean doesHave ) {
        testDefinitionSteps.addStep( String.format( "Check that the '%s' section %s 'AI is turned off' information", sectionName, doesHave ? "has" : "does not have" ) );
        psSteps.checkAiTurnedOffInfoVisibilityOnPsSection( sectionName, doesHave );
        testDefinitionSteps.logEvidence(
                String.format( "the '%s' section %s 'AI is turned off' information", sectionName, doesHave ? "has" : "does not have" ),
                String.format( "the '%s' section %s 'AI is turned off' information", sectionName, doesHave ? "has" : "does not have" ), true );
    }

    @Then( "the '$sectionName' section has an AI badge with text: \"$title\"" )
    public void checkAiBadgeTextOnPsSection( String sectionName, String title ) {
        testDefinitionSteps.addStep( String.format( "Check that the '%s' section has an AI badge with text: %s", sectionName, title ) );
        psSteps.checkAiBadgeTextOnPsSection( sectionName, title );
        testDefinitionSteps.logEvidence(
                String.format( "Check that the '%s' section has an AI badge with text: %s", sectionName, title ),
                String.format( "Check that the '%s' section has an AI badge with text: %s", sectionName, title ), true );
    }

    @Then( "the '$sectionName' section contains the following key words: $keyWords" )
    public void checkReportTextOnPsSection( String sectionName, StringList keyWords ) {
        testDefinitionSteps.addStep( String.format( "Check that the '%s' section contains the following key words: %s", sectionName, keyWords.getList() ) );
        psSteps.checkReportTextOnPsSection( sectionName, new ArrayList<>( keyWords.getList() ), true );
        testDefinitionSteps.logEvidence(
                String.format( "the '%s' section contains the following key words: %s", sectionName, keyWords.getList() ),
                String.format( "the '%s' section contains the following key words: %s", sectionName, keyWords.getList() ), true );
    }

    @Then( "the '$sectionName' section does not contain the following key words: $keyWords" )
    public void checkReportNotContainTextOnPsSection( String sectionName, StringList keyWords ) {
        testDefinitionSteps.addStep( String.format( "Check that the '%s' section contains the following key words: %s", sectionName, keyWords.getList() ) );
        psSteps.checkReportTextOnPsSection( sectionName, new ArrayList<>( keyWords.getList() ), false );
        testDefinitionSteps.logEvidence(
                String.format( "the '%s' section contains the following key words: %s", sectionName, keyWords.getList() ),
                String.format( "the '%s' section contains the following key words: %s", sectionName, keyWords.getList() ), true );
    }

    @Then( "the '$sectionName' section contains the following text: \"$text\"" )
    public void checkFullReportTextOnPsSection( String sectionName, String text ) {
        testDefinitionSteps.addStep( String.format( "Check that the '%s' section contains the following text: %s", sectionName, text ) );
        psSteps.checkFullReportTextOnPsSection( sectionName, text );
        testDefinitionSteps.logEvidence(
                String.format( "the '%s' section contains the following text: %s", sectionName, text ),
                String.format( "the '%s' section contains the following text: %s", sectionName, text ), true );
    }

    @When( "the user hovers the 'i' symbol next to the AI badge on '$sectionName' section" )
    public void hoverInfoSymbolOnPsSection( String sectionName ) {
        testDefinitionSteps.addStep( String.format( "the user hovers the 'i' symbol next to the AI badge on '%s' section", sectionName ) );
        psSteps.hoverInfoSymbolOnPsSection( sectionName );
        testDefinitionSteps.logEvidence(
                String.format( "the user hovered the 'i' symbol next to the AI badge on '%s' section", sectionName ),
                String.format( "the user hovered the 'i' symbol next to the AI badge on '%s' section", sectionName ), true );
    }

    @When( "the user clicks on the \"$buttonText\" button on '$sectionName' section" )
    public void clickButtonOnPsSection( String buttonText, String sectionName ) {
        testDefinitionSteps.addStep( String.format( "the user clicks on the \"%s\" button on '%s' section", buttonText, sectionName ) );
        psSteps.clickButtonOnPsSection( sectionName, buttonText );
        testDefinitionSteps.logEvidence(
                String.format( "the user clicked on the \"%s\" button on '%s' section", buttonText, sectionName ),
                String.format( "the user clicked on the \"%s\" button on '%s' section", buttonText, sectionName ), true );
    }

    @Then( "the 'What has changed' section $doesHave the event list table" )
    public void checkWhatHasChangedTableAvailability( Boolean doesHave ) {
        testDefinitionSteps.addStep( String.format( "Check that the 'What has changed' section %s the event list table", doesHave ? "has" : "does not have" ) );
        psSteps.checkWhatHasChangedTableAvailability( doesHave );
        testDefinitionSteps.logEvidence(
                String.format( "the 'What has changed' section %s the event list table", doesHave ? "has" : "does not have" ),
                String.format( "the 'What has changed' section %s the event list table", doesHave ? "has" : "does not have" ), true );
    }

    @Given( "the 'What has changed' section has the following event list table: $whatHasChangedTable" )
    @Then( "the 'What has changed' section has the following event list table: $whatHasChangedTable" )
    public void checkWhatHasChangedTableAvailability( ExamplesTable whatHasChangedTable ) {
        testDefinitionSteps.addStep( String.format( "Check that the 'What has changed' section has the following event list table: %s", whatHasChangedTable.getRows() ) );
        psSteps.checkWhatHasChangedTableContent( new Table( whatHasChangedTable.getRows() ) );
        testDefinitionSteps.logEvidence(
                String.format( "the 'What has changed' section has the following event list table: %s", whatHasChangedTable.getRows() ),
                String.format( "the 'What has changed' section has the following event list table: %s", whatHasChangedTable.getRows() ), true );
    }

    @Then( "the '$buttonName' button is $buttonState in row: $rowNb, column: \"$columnName\" in 'What has changed' table" )
    public void checkButtonStateInTable( String buttonName, Boolean buttonState, int rowNb, String columnName ) {
        testDefinitionSteps.addStep(
                String.format( "Check that the '%s' button is %s in row: %s, column: \"%s\" in 'What has changed' table",
                        buttonState ? "enabled" : "disabled", buttonName, rowNb, columnName ) );
        psSteps.checkButtonStateInTable( buttonName, buttonState, rowNb, columnName );
        testDefinitionSteps.logEvidence(
                String.format( "the '%s' button is %s in row: %s, column: \"%s\" in 'What has changed' table",
                        buttonState ? "enabled" : "disabled", buttonName, rowNb, columnName ),
                String.format( "the 'Full report' button is %s in row: %s, column: \"%s\" in 'What has changed' table",
                        buttonState ? "enabled" : "disabled", buttonName, rowNb, columnName ), true );
    }

    @Then( "the '$sectionName' section displays the following button \"$showMoreOrLessText\"" )
    public void checkWhatHasChangedSectionMoreOrLessButtonText( String sectionName, String showMoreOrLessText ) {
        testDefinitionSteps.addStep(
                String.format( "Checking the '%s' section displays the following button \"%s\"", sectionName, showMoreOrLessText ) );
        psSteps.checkWhatHasChangedSectionMoreOrLessButtonText( sectionName, showMoreOrLessText );
        testDefinitionSteps.logEvidence(
                String.format( "the '%s' section displays the following button %s", sectionName, showMoreOrLessText ),
                String.format( "the '%s' section displays the following button %s", sectionName, showMoreOrLessText ),
                true );
    }

    @When( "the user clicks on the 'Full report' button in row: $rowNb, column: \"$columnName\" in 'What has changed' table" )
    public void clickFullReportButton( int rowNb, String columnName ) {
        testDefinitionSteps.addStep( String.format( "the user clicks on the 'Full report' button in row: %s, column: \"%s\" in 'What has changed' table", rowNb, columnName ) );
        psSteps.clickFullReportButton( rowNb, columnName );
        testDefinitionSteps.logEvidence(
                String.format( "the user clicked on the 'Full report' button in row: %s, column: \"%s\" in 'What has changed' table", rowNb, columnName ),
                String.format( "the user clicked on the 'Full report' button in row: %s, column: \"%s\" in 'What has changed' table", rowNb, columnName ), true );
    }

    @When( "the user hovers the '$buttonName' button in row: $rowNb, column: \"$columnName\" in 'What has changed' table" )
    public void hoverButtonInTable( String buttonName, int rowNb, String columnName ) {
        testDefinitionSteps.addStep( String.format( "the user hovers the '%s' button in row: %s, column: \"%s\" in 'What has changed' table", buttonName, rowNb, columnName ) );
        psSteps.hoverButtonInTable( buttonName, rowNb, columnName );
        testDefinitionSteps.logEvidence(
                String.format( "the user hovered the '%s' button in row: %s, column: \"%s\" in 'What has changed' table", buttonName, rowNb, columnName ),
                String.format( "the user hovered the '%s' button in row: %s, column: \"%s\" in 'What has changed' table", buttonName, rowNb, columnName ), true );
    }

    @When( "the user hovers the $sourceLinkNb{st|nd|rd|th} source link in '$sectionName' section" )
    public void hoverSourceOnPsSection( int sourceLinkNb, String sectionName ) {
        testDefinitionSteps.addStep( String.format( "the user hovers the %s(st|nd|rd|th) source link in '%s' section", sourceLinkNb, sectionName ) );
        int cardIndex = sourceLinkNb - 1;
        psSteps.hoverSourceOnPsSection( sectionName, cardIndex );
        testDefinitionSteps.logEvidence(
                String.format( "the user hovered the %s(st|nd|rd|th) source link in '%s' section", sourceLinkNb, sectionName ),
                String.format( "the user hovered the %s(st|nd|rd|th) source link in '%s' section", sourceLinkNb, sectionName ), true );
    }

    @When( "the user clicks on the $sourceLinkNb{st|nd|rd|th} source link in '$sectionName' section" )
    public void clickSourceOnPsSection( int sourceLinkNb, String sectionName ) {
        testDefinitionSteps.addStep( String.format( "the user clicks the %s(st|nd|rd|th) source link in '%s' section", sourceLinkNb, sectionName ) );
        int cardIndex = sourceLinkNb - 1;
        psSteps.clickSourceOnPsSection( sectionName, cardIndex );
        testDefinitionSteps.logEvidence(
                String.format( "the user clicked the %s(st|nd|rd|th) source link in '%s' section", sourceLinkNb, sectionName ),
                String.format( "the user clicked the %s(st|nd|rd|th) source link in '%s' section", sourceLinkNb, sectionName ), true );
    }

    @When( "the user clicks the last source link in '$sectionName' section" )
    public void clickLastSourceOnPsSection( String sectionName ) {
        testDefinitionSteps.addStep( String.format( "the user clicks the last source link in '%s' section", sectionName ) );
        psSteps.clickLastSourceOnPsSection( sectionName );
        testDefinitionSteps.logEvidence(
                String.format( "the user clicked the last source link in '%s' section", sectionName ),
                String.format( "the user clicked the last source link in '%s' section", sectionName ), true );
    }

    @When( "the user clicks \"$buttonText\" on '$sectionName' section" )
    public void clickButtonOnWhatHasChangedSection( String buttonText, String sectionName ) {
        testDefinitionSteps.addStep( String.format( "User clicks \"%s\" button on 'What has changed' section", buttonText ) );
        psSteps.clickButtonOnPsSection( sectionName, buttonText );
        testDefinitionSteps.logEvidence(
                String.format( "the user clicked on \"%s\" button on 'What has changed' section", buttonText ),
                String.format( "the user clicked on \"%s\" button on 'What has changed' section", buttonText ),
                true );
    }

    @When( "[API] the patient with ID \"$patientId\" is deleted from PS monitoring list" )
    @Given( "[API] the patient with ID \"$patientId\" is deleted from PS monitoring list" )
    public void deletePatientFromMonitoringList( String patientId ) {
        testDefinitionSteps.addStep( String.format( "Delete the patient with ID \"%s\" from PS monitoring list", patientId ) );
        psSteps.deletePatientFromMonitoringList( patientId );
        testDefinitionSteps.logEvidence(
                String.format( "The patient with ID \"%s\" is deleted from PS monitoring list", patientId ),
                String.format( "The patient with ID \"%s\" is deleted from PS monitoring list", patientId ),
                true );
    }

    @Given( "[API] the Progressive Summarization monitoring list was deleted" )
    @When( "[API] the Progressive Summarization monitoring list is deleted" )
    public void deletePatientsFromMonitoringList() {
        testDefinitionSteps.addStep( "Delete the patients from PS monitoring list" );
        psSteps.deletePatientsFromMonitoringList();
        testDefinitionSteps.logEvidence(
                "The patients are deleted from PS monitoring list",
                "The patients are deleted from PS monitoring list",
                true
        );
    }

    @Given( "[API] the Progressive Summarization $eventType event timing is configured $isConfigured trigger next minute" )
    @When( "[API] the Progressive Summarization $eventType event timing is configured $isConfigured trigger next minute" )
    public void patientSummaryTriggeringEventsConfigured( String eventType, Boolean isConfigured ) {
        testDefinitionSteps.addStep( String.format( "Configure the ps %s event timing '%s' trigger soon", eventType, isConfigured ? "to" : "not to" ) );
        psSteps.configureTriggeringEventSchedules( eventType, isConfigured, 1 );
        testDefinitionSteps.logEvidence(
                String.format( "Configured the ps %s event timing '%s' trigger soon", eventType, isConfigured ? "to" : "not to" ),
                String.format( "Configured the ps %s event timing '%s' trigger soon", eventType, isConfigured ? "to" : "not to" ),
                true
        );
    }

    @Given( "[API] the Progressive Summarization $eventType event timing is configured $isConfigured trigger in $min minutes" )
    @When( "[API] the Progressive Summarization $eventType event timing is configured $isConfigured trigger in $min minutes" )
    public void patientSummaryTriggeringEventsConfigured( String eventType, Boolean isConfigured, int min ) {
        testDefinitionSteps.addStep( String.format( "Configure the ps %s event timing '%s' trigger in '%d' minutes", eventType, isConfigured ? "to" : "not to", min ) );
        psSteps.configureTriggeringEventSchedules( eventType, isConfigured, min );
        testDefinitionSteps.logEvidence(
                String.format( "Configured the ps %s event timing '%s' trigger in '%d' minutes", eventType, isConfigured ? "to" : "not to", min ),
                String.format( "Configured the ps %s event timing '%s' trigger in '%d' minutes", eventType, isConfigured ? "to" : "not to", min ),
                true
        );
    }

    @Then( "[API] the Progressive Summarization monitoring list contains the following patient: $patientId" )
    public void patientSummaryMonitoringListContainsPatient( String patientId ) {
        testDefinitionSteps.addStep( String.format( "Check that the patient with id: '%s' is in the ps monitoring list", patientId ) );
        psSteps.monitoringListContainsPatient( patientId );
        testDefinitionSteps.logEvidence(
                String.format( "The ps monitoring list contains the patient with id: '%s'", patientId ),
                String.format( "The ps monitoring list contains the patient with id: '%s'", patientId ),
                true
        );
    }

    @Then( "[API] the Progressive Summarization monitoring list $isEmpty empty" )
    public void patientSummaryMonitoringListIsEmpty( Boolean isEmpty ) {
        testDefinitionSteps.addStep( String.format( "Check that the ps monitoring list '%s' empty", isEmpty ? "is" : "is not" ) );
        psSteps.checkMonitoringListIsEmpty( isEmpty );
        testDefinitionSteps.logEvidence(
                String.format( "The ps monitoring list '%s' empty", isEmpty ? "is" : "is not" ),
                String.format( "The ps monitoring list '%s' empty", isEmpty ? "is" : "is not" ),
                true
        );
    }

    @Then( "the '$sectionName' section has the following empty state message: \"$message\"" )
    public void checkEmptyStateMessage( String sectionName, String message ) {
        testDefinitionSteps.addStep( String.format( "Check that the '%s' section has the following empty state message: \"%s\"", sectionName, message ) );
        psSteps.checkEmptyStateMessage( sectionName, message );
        testDefinitionSteps.logEvidence(
                String.format( "The '%s' section had the following empty state message: \"%s\"", sectionName, message ),
                String.format( "The '%s' section had the following empty state message: \"%s\"", sectionName, message ),
                true );
    }

    @Then( "the progressive summarization loading skeleton $isVisible visible" )
    public void checkLoadingSkeletonVisibility( Boolean isVisible ) {
        testDefinitionSteps.addStep( "Check progressive summarization loading skeleton visibility" );
        psSteps.checkLoadingSkeletonVisibility( isVisible );
        testDefinitionSteps.logEvidence(
                String.format( "The progressive summarization loading skeleton is %s", isVisible ? "visible" : "invisible" ),
                String.format( "The progressive summarization loading skeleton was %s", isVisible ? "visible" : "invisible" ),
                true );
    }


    @Then( "the '$sectionName' section lists the AI findings with bullet points" )
    public void checkBulletPointsAvailabilityInSection( String sectionName ) {
        testDefinitionSteps.addStep( String.format( "Check that the '%s' section section lists the AI findings with bullet points", sectionName ) );
        psSteps.checkBulletPointsAvailabilityInSection( sectionName );
        testDefinitionSteps.logEvidence(
                String.format( "The '%s' section section lists the AI findings with bullet points", sectionName ),
                String.format( "The '%s' section section listed the AI findings with bullet points", sectionName ),
                true );
    }

    @Then( "a [source] link is available at the end of all AI findings in the '$sectionName' section" )
    public void checkSourceLinkAvailabilityInSection( String sectionName ) {
        testDefinitionSteps.addStep( String.format( "Check that a [source] link is available at the end of all AI findings in the '%s' section", sectionName ) );
        psSteps.checkSourceLinkAvailabilityInSection( sectionName );
        testDefinitionSteps.logEvidence(
                String.format( "A [source] link is available at the end of all AI findings in the '%s' section", sectionName ),
                String.format( "A [source] link was available at the end of all AI findings in the '%s' section", sectionName ),
                true );
    }

    @Then( "the value of \"$property\" property in \"$response_2\" is $comparison than \"$response_1\"" )
    public void comparePropertyValues( String property, String response_2, String comparison, String response_1 ) {
        testDefinitionSteps.addStep( String.format( "Compare the value of \"%s\" property in \"%s\" is %s than \"%s\"", property, response_2, comparison, response_1 ) );
        psSteps.compareMonitoringProperties( property, response_2, response_1, comparison );
        testDefinitionSteps.logEvidence(
                String.format( "The value of \"%s\" property in \"%s\" is %s than \"%s\"", property, response_2, comparison, response_1 ),
                String.format( "The value of \"%s\" property in \"%s\" is %s than \"%s\"", property, response_2, comparison, response_1 ),
                true );
    }

    @Then( "the monitoring response does not contain the following properties: $examplesTable" )
    public void checkMonitoringResponseDoesNotContainProperties( ExamplesTable examplesTable ) {
        List<String> propertyList = getExampleTableAsList( examplesTable );
        testDefinitionSteps.addStep( String.format( "Check that the monitoring response does not contain the following properties: %s", propertyList ) );
        psSteps.checkMonitoringResponseDoesNotContainProperties( propertyList );
        testDefinitionSteps.logEvidence(
                String.format( "The monitoring response does not contain the following properties: %s", propertyList ),
                String.format( "The monitoring response does not contain the following properties: %s", propertyList ),
                true );
    }


}
