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
package com.ge.onchron.verif.whatsteps.tabs.medicalHistory;

import java.util.List;
import java.util.stream.Collectors;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.howsteps.detailedPatientData.medicalHistory.PatientHistorySteps;
import com.ge.onchron.verif.model.Table;
import com.ge.onchron.verif.model.detailedDataItems.DetailedDataItem;
import net.thucydides.core.annotations.Steps;

public class PatientHistory {

    @Steps
    private PatientHistorySteps patientHistorySteps;
    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Given( "the \"Patient history\" section contains the following data in Medical history: $table" )
    @Then( "the \"Patient history\" section contains the following data in Medical history: $table" )
    public void verifyAllDetailedData( List<DetailedDataItem> dataItems ) {
        testDefinitionSteps.addStep( "Check 'Medical history' in 'Patient history' section" );
        patientHistorySteps.verifyAllDetailedData( dataItems );
        testDefinitionSteps.logEvidence( STR."the 'Patient history' section contains the following data in Medical history:\{
                        dataItems.stream().map( Object::toString ).collect( Collectors.joining( "\n" ) )}",
                "Medical history matched expected (see previous logs)", true );
    }

    @Given( "the \"Patient history\" section includes the following data in Medical history: $table" )
    @Then( "the \"Patient history\" section includes the following data in Medical history: $table" )
    public void verifyPartialDetailedData( List<DetailedDataItem> dataItems ) {
        testDefinitionSteps.addStep( "Check 'Medical history' in 'Patient history' section" );
        patientHistorySteps.verifyPartialDetailedData( dataItems );
        testDefinitionSteps.logEvidence( STR."the 'Patient history' section includes the following data in Medical history:\{
                        dataItems.stream().map( Object::toString ).collect( Collectors.joining( "\n" ) )}",
                "All expected items were found (see previous logs)", true );
    }

    @Given( "the \"Patient history\" section contains the following \"$tableTitle\" table: $table" )
    @Then( "the \"Patient history\" section contains the following \"$tableTitle\" table: $table" )
    public void checkDataTable( String tableTitle, ExamplesTable table ) {
        testDefinitionSteps.addStep( STR."Check table \{tableTitle} in 'Patient history' section" );
        String rows = table.getRows().stream().map( Object::toString ).collect( Collectors.joining( "\n" ) );
        patientHistorySteps.checkDataTable( new Table( tableTitle, table.getRows() ), false );
        testDefinitionSteps.logEvidence( STR."the 'Patient history' section contains the following '\{tableTitle}' table with data: \{rows}",
                "Table was found and had matching data (see previous logs)", true);
    }

    @Given( "the \"Patient history\" section contains the following sorted \"$tableTitle\" table: $table" )
    @Then( "the \"Patient history\" section contains the following sorted \"$tableTitle\" table: $table" )
    public void checkSortedDataTable( String tableTitle, ExamplesTable table ) {
        testDefinitionSteps.addStep( STR."Check table \{tableTitle} in 'Patient history' section" );
        String rows = table.getRows().stream().map( Object::toString ).collect( Collectors.joining( "\n" ) );
        patientHistorySteps.checkDataTable( new Table( tableTitle, table.getRows() ), true );
        testDefinitionSteps.logEvidence( STR."the 'Patient history' section contains the following '\{tableTitle
                        }' table with data in following order: \{rows}",
                "Table was found and had matching data in expected order (see previous logs)", true);
    }

    @Given( "the \"Patient history\" section contains the following single table: $table" )
    @Then( "the \"Patient history\" section contains the following single table: $table" )
    public void checkSingleTable( ExamplesTable table ) {
        testDefinitionSteps.addStep( STR."Check tables in 'Patient history' section" );
        String rows = table.getRows().stream().map( Object::toString ).collect( Collectors.joining( "\n" ) );
        patientHistorySteps.checkSingleDataTable( new Table( table.getRows() ), false );
        testDefinitionSteps.logEvidence( STR."the 'Patient history' section contains the single table with following data: \{rows}",
                "Single table was found and it had matching data (see previous logs)", true);
    }

    @Then( "the Surgical history table content in Patient History is sorted by \"$columnName\" column which has {a|an} \"$arrowDirection\" pointing arrow" )
    public void checkSortParamInSurgicalHistoryTable( String columnName, String arrowDirection ) {
        testDefinitionSteps.addStep(STR."Check sort param in Surgical history table in Patient History");
        patientHistorySteps.checkSortParamInSurgicalHistoryTable( columnName, arrowDirection );
        patientHistorySteps.checkSurgicalHistoryTableContentSorted( columnName, arrowDirection );
        testDefinitionSteps.logEvidence(STR."The Surgical history table content is sorted by '\{columnName}' column which has '\{arrowDirection}' pointing arrow",
                STR."The Surgical history table content is sorted by '\{columnName}' column which has '\{arrowDirection}' pointing arrow", true);
    }

    @When( "the user clicks on the \"$columnName\" column header in the Surgical History table" )
    public void clickOnColumnHeaderInSurgicalHistoryTable( String columnName ){
        testDefinitionSteps.addStep("Click on column header in Surgical history table");
        patientHistorySteps.clickOnColumnHeaderInSurgicalHistoryTable( columnName );
        testDefinitionSteps.logEvidence(STR."The '\{columnName}' column header clicked in the Surgical history table",
                STR."The \{columnName}' column header clicked successfully in Surgical history table", true);
    }

    @Then( "the Surgical history table contains the following sorted items: $table" )
    public void checkSortedSurgicalHistoryTable( ExamplesTable table ) {
        testDefinitionSteps.addStep("Check Sorted Surgical history table");
        patientHistorySteps.checkSurgicalHistoryTable( new Table( table.getRows() ), true );
        testDefinitionSteps.logEvidence(STR."The Surgical history table contains the following sorted items: \{table.getRows().stream().map(Object::toString)
                                                                                                                              .collect(Collectors.joining("\n"))}",
                "Surgical history table match to the expected", true);
    }

}
