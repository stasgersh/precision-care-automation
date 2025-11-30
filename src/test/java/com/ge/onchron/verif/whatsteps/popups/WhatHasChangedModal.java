/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2024, 2024, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.whatsteps.popups;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.howsteps.WhatHasChangedModalSteps;
import com.ge.onchron.verif.model.Table;
import net.thucydides.core.annotations.Steps;

public class WhatHasChangedModal {

    @Steps
    private WhatHasChangedModalSteps whatHasChangedModalSteps;

    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @When( "the user clicks on the 'X' button on the 'What has changed' modal" )
    public void closeWhatHasChangedModal() {
        testDefinitionSteps.addStep( "Close 'What has changed' modal by clicking 'X'" );
        whatHasChangedModalSteps.closeWhatHasChangedModal();
        testDefinitionSteps.logEvidence( "the 'What has changed' modal was closed successfully",
                "'What has changed' modal was closed successfully (without errors)", true );
    }

    @When( "the user clicks on the 'Full report' button in row: $rowNb, column: \"$columnName\" in Events list table on 'What has changed' modal" )
    public void clickFullReportButton( int rowNb, String columnName ) {
        testDefinitionSteps.addStep( String.format( "the user clicks on the 'Full report' button in row: %s, column: \"%s\" in Events list table on 'What has changed' modal", rowNb, columnName ) );
        whatHasChangedModalSteps.clickFullReportButtonOnWhatHasChangedModal( rowNb, columnName );
        testDefinitionSteps.logEvidence(
                String.format( "the user clicked on the 'Full report' button in row: %s, column: \"%s\" in Events list table on 'What has changed' modal", rowNb, columnName ),
                String.format( "the user clicked on the 'Full report' button in row: %s, column: \"%s\" in Events list table on 'What has changed' modal", rowNb, columnName ), true );
    }

    @When( "the user hovers the '$buttonName' button in row: $rowNb, column: \"$columnName\" in Events list table on 'What has changed' modal" )
    public void hoverButtonOnWhatHasChangedModal( String buttonName, int rowNb, String columnName ) {
        testDefinitionSteps.addStep( String.format( "the user clicks on the '%s' button in row: %s, column: \"%s\" in Events list table on 'What has changed' modal", buttonName, rowNb, columnName ) );
        whatHasChangedModalSteps.hoverButtonOnWhatHasChangedModal( buttonName, rowNb, columnName );
        testDefinitionSteps.logEvidence(
                String.format( "the user clicked on the '%s' button in row: %s, column: \"%s\" in Events list table on 'What has changed' modal", buttonName, rowNb, columnName ),
                String.format( "the user clicked on the '%s' button in row: %s, column: \"%s\" in Events list table on 'What has changed' modal", buttonName, rowNb, columnName ), true );
    }

    @When( "the user clicks on the 'Back to Event list' button on the full report modal" )
    public void clickBackToEventList() {
        testDefinitionSteps.addStep( "Click 'Back to Event list' on modal" );
        whatHasChangedModalSteps.clickBackToEventListOnModal();
        testDefinitionSteps.logEvidence(
                "'Back to Event list' button was clicked successfully",
                "'Back to Event list' button was clicked successfully", true );
    }

    @Then( "the 'What has changed' modal $isDisplayed displayed on the screen" )
    public void checkWhatHasChangedModalIsDisplayed( Boolean isDisplayed ) {
        testDefinitionSteps.addStep( STR."Checking that 'What has changed' modal is ' \{isDisplayed ? " visible" : " not visible"} on the screen" );
        whatHasChangedModalSteps.checkWhatHasChangedModalIsDisplayed( isDisplayed );
        testDefinitionSteps.logEvidence(
                STR."The 'What has changed modal was \{isDisplayed ? " visible" : " not visible"}",
                STR."The 'What has changed modal was \{isDisplayed ? " visible" : " not visible"}",
                true );
    }

    @Then( "the 'What has changed' modal has the following title: \"$title\"" )
    public void checkWhatHasChangedModalHeader( String title ) {
        testDefinitionSteps.addStep( "Check 'What has changed' modal title" );
        whatHasChangedModalSteps.checkWhatHasChangedModalHeader( title );
        testDefinitionSteps.logEvidence(
                STR."The 'What has changed' modal displayed the following header: \{title}",
                STR."The 'What has changed' modal displayed the following header: \{title}", true );
    }

    @Then( "the 'What has changed' modal has the following event list table: $whatHasChangedTable" )
    public void checkWhatHasChangedTableAvailability( ExamplesTable whatHasChangedTable ) {
        testDefinitionSteps.addStep( String.format( "the 'What has changed' modal has the following event list table: %s", whatHasChangedTable.getRows() ) );
        whatHasChangedModalSteps.checkWhatHasChangedTableContent( new Table( whatHasChangedTable.getRows() ) );
        testDefinitionSteps.logEvidence(
                String.format( "the 'What has changed' modal has the following event list table: %s", whatHasChangedTable.getRows() ),
                String.format( "the 'What has changed' modal has the following event list table: %s", whatHasChangedTable.getRows() ), true );
    }

}
