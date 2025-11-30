/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2023, 2023, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.whatsteps.tabs.medicalHistory;

import java.util.List;
import java.util.stream.Collectors;

import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

import com.ge.onchron.verif.howsteps.detailedPatientData.medicalHistory.AllergiesAndIntolerancesSteps;
import com.ge.onchron.verif.model.Table;
import com.ge.onchron.verif.model.detailedDataItems.DetailedDataItem;
import net.thucydides.core.annotations.Steps;

public class AllergiesAndIntolerances {

    @Steps
    private   AllergiesAndIntolerancesSteps allergiesAndIntolerancesSteps;
    private final TestDefinitionSteps           testDefinitionSteps = TestDefinitionSteps.getInstance();
    @Given( "the \"Allergies and intolerances\" section contains the following data in Medical history: $table" )
    @Then( "the \"Allergies and intolerances\" section contains the following data in Medical history: $table" )
    public void verifyAllDetailedData(List<DetailedDataItem> dataItems) {
        testDefinitionSteps.addStep(
            "Check Allergies and intolerances contains medical history");
        allergiesAndIntolerancesSteps.verifyAllDetailedData(dataItems);
        testDefinitionSteps.logEvidence(
            STR."the \"Allergies and intolerances\" section contains the following data in Medical history: \{dataItems.stream()
                .map(DetailedDataItem::toString).collect(Collectors.joining("\n"))}", "the \"Allergies and intolerances\" section contains expected data (see previous logs)", true);
    }

    @Given("the \"Allergies and intolerances\" section includes the following data in Medical history: $table")
    @Then("the \"Allergies and intolerances\" section includes the following data in Medical history: $table")
    public void verifyPartialDetailedData(List<DetailedDataItem> dataItems) {
        testDefinitionSteps.addStep("Check Allergies and intolerances includes medical history");
        allergiesAndIntolerancesSteps.verifyPartialDetailedData(dataItems);
        String table = dataItems.stream()
            .map(DetailedDataItem::toString).collect(Collectors.joining("\n"));
        testDefinitionSteps.logEvidence(
            STR."the \"Allergies and intolerances\" section includes the following data in Medical history: \{table }",
            STR."Allergies and intolerances section includes the following data in Medical history: \{table }", true);
    }

    @Given( "the \"Allergies and intolerances\" section contains the following \"$tableTitle\" table: $table" )
    @Then( "the \"Allergies and intolerances\" section contains the following \"$tableTitle\" table: $table" )
    public void checkDataTable( String tableTitle, ExamplesTable table ) {
        testDefinitionSteps.addStep(
            STR."Check tables in Allergies and intolerances section");
        allergiesAndIntolerancesSteps.checkDataTable( new Table( tableTitle, table.getRows() ), false );
        testDefinitionSteps.logEvidence(
            STR."the \"Allergies and intolerances\" section contains the following \{tableTitle} table: \{table}",
            "Observed table match to the expected one", true);
    }

    @Given( "the \"Allergies and intolerances\" section contains the following single table: $table" )
    @Then( "the \"Allergies and intolerances\" section contains the following single table: $table" )
    public void checkSingleTable( ExamplesTable table ) {
        testDefinitionSteps.addStep(
            STR."Check Allergies and intolerances section contains single table");
        allergiesAndIntolerancesSteps.checkSingleDataTable( new Table( table.getRows() ), false );
        testDefinitionSteps.logEvidence(
            STR."the \"Allergies and intolerances\" section contains the following single table: \{table.asString()}",
            "Observed Completed medications table match as expected", true);
    }

    @When( "the user clicks on the \"$columnName\" column header in the Allergies and intolerances table" )
    public void clickOnColumnHeaderInAllergiesTable( String columnName) {
        testDefinitionSteps.addStep("Click on column header in Allergies and intolerances table");
        allergiesAndIntolerancesSteps.clickOnColumnHeaderInAllergiesTable( columnName );
        testDefinitionSteps.logEvidence(STR."The '\{columnName}' column header clicked in the Allergies and intolerances table",
                STR."The \{columnName}' column header clicked successfully in Allergies and intolerances table", true);
    }

    @Then( "the Allergies and intolerances table content is sorted by \"$columnName\" column which has {a|an} \"$arrowDirection\" pointing arrow" )
    public void checkSortParamInAllergiesTable( String columnName, String arrowDirection ) {
        testDefinitionSteps.addStep("Check sort param in Allergies and intolerances table");
        allergiesAndIntolerancesSteps.checkSortParamInAllergiesTable( columnName, arrowDirection );
        allergiesAndIntolerancesSteps.checkAllergiesTableContentSorted( columnName, arrowDirection );
        testDefinitionSteps.logEvidence(STR."The Allergies and intolerances table content is sorted by '\{columnName}' column which has '\{arrowDirection}' pointing arrow",
                STR."Allergies and intolerances table content is sorted by '\{columnName}' column which has '\{arrowDirection}' pointing arrow", true);
    }

    @Then( "the Allergies and intolerances table contains the following sorted items: $table" )
    public void checkSortedAllergiesTable( ExamplesTable table ) {
        testDefinitionSteps.addStep("Check Sorted Allergies and intolerances table");
        allergiesAndIntolerancesSteps.checkAllergiesTable( new Table( table.getRows() ), true );
        testDefinitionSteps.logEvidence(STR."The Allergies and intolerances table contains the following sorted items: \{table.getRows().stream().map(Object::toString)
                                                                                                                      .collect(Collectors.joining("\n"))}",
                "Allergies and intolerances table match to the expected", true);
    }

}
