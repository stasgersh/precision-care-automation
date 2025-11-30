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
package com.ge.onchron.verif.whatsteps.tabs.medicalHistory;

import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.howsteps.detailedPatientData.medicalHistory.MedicationsSteps;
import com.ge.onchron.verif.model.Table;
import com.ge.onchron.verif.model.detailedDataItems.DetailedDataItem;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

import java.util.List;
import java.util.stream.Collectors;

public class Medications {

    @Steps
    private       MedicationsSteps    medicationsSteps;
    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Given( "the Completed medications section is opened" )
    @When( "the user opens the Completed medications section" )
    public void openCompletedMedications() {
        testDefinitionSteps.addStep("Open completed medications");
        medicationsSteps.openCompletedMedications();
        testDefinitionSteps.logEvidence(STR."The Completed medications section is opened", "Completed medications section is opened", true);
    }

    @When( "the user closes the Completed medications section" )
    public void closeCompletedMedications() {
        testDefinitionSteps.addStep("Close completed medications");
        medicationsSteps.closeCompletedMedications();
        testDefinitionSteps.logEvidence("The Completed medications section closed", "Completed medications section closed", true);
    }

    @Given("the active medications table $is displayed")
    @Then("the active medications table $is displayed")
    public void checkDefaultMedicationsTableVisibility(Boolean isDisplayed) {
        testDefinitionSteps.addStep("Check default medications table visibility");
        medicationsSteps.checkDefaultMedicationsTableVisibility(isDisplayed);
        testDefinitionSteps.logEvidence(STR."The active medications table \{isDisplayed ? "" : "not"} displayed",
            "Default active medications table visibility is ok", true);
    }

    @Given( "the Completed medications header $is displayed in the Medications section" )
    @Then( "the Completed medications header $is displayed in the Medications section" )
    public void checkCompletedMedicationsHeaderVisibility( Boolean isDisplayed ) {
        testDefinitionSteps.addStep("Check completed medications header visibility");
        medicationsSteps.checkCompletedMedicationsHeaderVisibility( isDisplayed );
        testDefinitionSteps.logEvidence(STR."The Completed medications header \{isDisplayed ? "" : "not"} displayed in the Medications section",
            "Completed medications visibility is ok", true);
    }

    @Given( "the Completed medications table $is displayed in the Medications section" )
    @Then( "the Completed medications table $is displayed in the Medications section" )
    public void checkCompletedMedicationsTableVisibility( Boolean isDisplayed ) {
        testDefinitionSteps.addStep("Check completed medications table visibility");
        medicationsSteps.checkCompletedMedicationsTableVisibility( isDisplayed );
        testDefinitionSteps.logEvidence(STR."The Completed medications table \{isDisplayed ? "" : "not"} displayed in the Medications section",
            "Completed medications visibility is ok", true);
    }

    @Then( "the active medications table contains the following sorted items: $table" )
    public void checkSortedDefaultMedicationsTable( ExamplesTable table ) {
        testDefinitionSteps.addStep("Check Sorted default medications table");
        medicationsSteps.checkDefaultMedicationsTable( new Table( table.getRows() ), true );
        testDefinitionSteps.logEvidence(STR."The active medications table contains the following sorted items: \{table.getRows().stream().map(Object::toString)
                .collect(Collectors.joining("\n"))}",
            "Medications table (default) match to the expected", true);
    }

    @Then( "the active medications table contains the following items: $table" )
    public void checkDefaultMedicationsTable( ExamplesTable table ) {
        testDefinitionSteps.addStep("Check default medications table");
        medicationsSteps.checkDefaultMedicationsTable( new Table( table.getRows() ), false );
        testDefinitionSteps.logEvidence(STR."The active medications table contains the following items: \{table.getRows().stream().map(Object::toString)
                .collect(Collectors.joining("\n"))}",
            "Medications table (default) match to the expected", true);
    }

    @Given( "the Completed medications table contains the following sorted items: $table" )
    @Then( "the Completed medications table contains the following sorted items: $table" )
    public void checkSortedCompletedMedicationsTable( ExamplesTable table ) {
        testDefinitionSteps.addStep("Check sorted completed medications table");
        medicationsSteps.checkCompletedMedicationsTable( new Table( table.getRows() ), true );
        testDefinitionSteps.logEvidence(STR."The Completed medications table contains the following sorted items: \{table.getRows().stream().map(Object::toString)
                .collect(Collectors.joining("\n"))}",
            "Completed medications table match to the expected", true);
    }

    @Then("the Completed medications table contains the following items: $table")
    public void checkCompletedMedicationsTable(ExamplesTable table) {
        testDefinitionSteps.addStep("Check completed medications table");
        medicationsSteps.checkCompletedMedicationsTable(new Table(table.getRows()), false);
        testDefinitionSteps.logEvidence(
            STR."The Completed medications table contains the following items: \{table.getRows().stream().map(Object::toString)
                .collect(Collectors.joining("\n"))}",
            "Completed medications table match to the expected", true);
    }

    @Given( "the active medications table content is sorted by \"$columnName\" column which has {a|an} \"$arrowDirection\" pointing arrow" )
    @Then( "the active medications table content is sorted by \"$columnName\" column which has {a|an} \"$arrowDirection\" pointing arrow" )
    public void checkSortParamInDefaultMedicationsTable( String columnName, String arrowDirection ) {
        testDefinitionSteps.addStep("Check sort param in default medications table");
        medicationsSteps.checkSortParamInDefaultMedicationsTable( columnName, arrowDirection );
        medicationsSteps.checkDefaultMedicationsTableContentSorted( columnName, arrowDirection );
        testDefinitionSteps.logEvidence(STR."The active medications table content is sorted by '\{columnName}' column which has '\{arrowDirection}' pointing arrow",
            STR."Medications table content is sorted by '\{columnName}' column which has '\{arrowDirection}' pointing arrow successfully", true);
    }

    @Given( "the Completed medications table is sorted by \"$columnName\" column which has {a|an} \"$arrowDirection\" pointing arrow" )
    @Then( "the Completed medications table is sorted by \"$columnName\" column which has {a|an} \"$arrowDirection\" pointing arrow" )
    public void checkSortParamInCompletedMedicationsTable( String columnName, String arrowDirection ) {
        testDefinitionSteps.addStep("Check sort param in completed medications table");
        medicationsSteps.checkSortParamInCompletedMedicationsTable( columnName, arrowDirection );
        testDefinitionSteps.logEvidence(STR."The active medications table is sorted by '\{columnName}' column which has '\{arrowDirection}' pointing arrow",
            STR."Medications table is sorted by '\{columnName}' column which has '\{arrowDirection}' pointing arrow successfully", true);
    }

    @Then( "the Completed medications section has a button to expand the data" )
    public void checkExpandButtonVisibility() {
        testDefinitionSteps.addStep("Check expand button visibility");
        medicationsSteps.checkExpandButtonVisibility();
        testDefinitionSteps.logEvidence("The Completed medications section has a button to expand the data",
            "Expand button visibility in Completed medications section is ok", true);
    }

    @Given( "the Completed medications section has a button to collapse the data" )
    public void checkCollapseButtonVisibility() {
        testDefinitionSteps.addStep("Check collapse button visibility");
        medicationsSteps.checkCollapseButtonVisibility();
        testDefinitionSteps.logEvidence("The Completed medications section has a button to collapse the data",
            "Collapse button visibility in Completed medications section is ok", true);
    }

    @Then( "the Completed medications header contains a badge with the following number: $numberOnBadge" )
    public void checkBadgeOnCompletedMedHeader( int numberOnBadge ) {
        testDefinitionSteps.addStep("Check badge on completed med header");
        medicationsSteps.checkBadgeOnCompletedMedHeader( numberOnBadge );
        testDefinitionSteps.logEvidence(STR."The Completed medications header contains a badge with the following number: \{numberOnBadge}",
            "The number on the Completed medications badge is ok", true);
    }

    @When( "the user clicks on the \"$columnName\" column header in the Medications table" )
    public void clickOnColumnHeaderInDefaultMedTable( String columnName ) {
        testDefinitionSteps.addStep("Click on column header in default med table");
        medicationsSteps.clickOnColumnHeaderInDefaultMedTable( columnName );
        testDefinitionSteps.logEvidence(STR."The '\{columnName}' column header clicked in the Medications table",
            STR."The \{columnName}' column header clicked successfully", true);
    }

    @When( "the user clicks on the \"$columnName\" column header in the Completed medications table" )
    public void clickOnColumnHeaderInCompletedMedTable( String columnName ) {
        testDefinitionSteps.addStep("Click on column header in Completed med table");
        medicationsSteps.clickOnColumnHeaderInCompletedMedTable( columnName );
        testDefinitionSteps.logEvidence(STR."The '\{columnName}' column header clicked in the Medications table",
            STR."The \{columnName}' column header clicked successfully", true);
    }

    @Given("the \"Medications\" section includes the following data in Medical history: $table")
    @Then("the \"Medications\" section includes the following data in Medical history: $table")
    public void verifyPartialDetailedData(List<DetailedDataItem> dataItems) {
        testDefinitionSteps.addStep("Check Medical History data in 'Medications' section");
        medicationsSteps.verifyPartialDetailedData(dataItems);
        testDefinitionSteps.logEvidence(
            STR."The \"Medications\" section includes the following data in Medical history: \{dataItems.stream().map(DetailedDataItem::toString)
                .collect(
                    Collectors.joining("\n"))}",
            STR."Data item is ok (See previous logs)", true);
    }

}
