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
import org.jbehave.core.model.ExamplesTable;

import com.ge.onchron.verif.howsteps.detailedPatientData.medicalHistory.ComorbiditiesSteps;
import com.ge.onchron.verif.model.Table;
import com.ge.onchron.verif.model.detailedDataItems.DetailedDataItem;
import net.thucydides.core.annotations.Steps;

public class Comorbidities {

    @Steps
    private     ComorbiditiesSteps  comorbiditiesSteps;
    private TestDefinitionSteps testDefinitionSteps =
        TestDefinitionSteps.getInstance();

    @Given( "the \"Comorbidities\" section contains the following data in Medical history: $table" )
    @Then( "the \"Comorbidities\" section contains the following data in Medical history: $table" )
    public void verifyAllDetailedData( List<DetailedDataItem> dataItems ) {
        testDefinitionSteps.addStep(STR.
            "Check Comorbidities section contains data in Medical history");
        comorbiditiesSteps.verifyAllDetailedData( dataItems );
        testDefinitionSteps.logEvidence(STR."the \"Comorbidities\" section contains the following data in Medical history: \{dataItems.stream().map(DetailedDataItem::toString).collect(Collectors.joining("\n"))}",
            "Number of the items match as expected", true);
    }

    @Given( "the \"Comorbidities\" section includes the following data in Medical history: $table" )
    @Then( "the \"Comorbidities\" section includes the following data in Medical history: $table" )
    public void verifyPartialDetailedData( List<DetailedDataItem> dataItems ) {
        testDefinitionSteps.addStep(STR.
            "Check Comorbidities section includes data in Medical history");
        comorbiditiesSteps.verifyPartialDetailedData( dataItems );
        testDefinitionSteps.logEvidence(STR."the \"Comorbidities\" section includes the following data in Medical history: \{dataItems.stream().map(DetailedDataItem::toString).collect(Collectors.joining("\n"))}",
            "\"Comorbidities\" section includes the following data in Medical history", true);
    }

    @Given( "the \"Comorbidities\" section contains the following \"$tableTitle\" table: $table" )
    @Then( "the \"Comorbidities\" section contains the following \"$tableTitle\" table: $table" )
    public void checkDataTable( String tableTitle, ExamplesTable table ) {
        testDefinitionSteps.addStep(STR.
            "Check Comorbidities section contains data table");
        comorbiditiesSteps.checkDataTable( new Table( tableTitle, table.getRows() ), false );
        testDefinitionSteps.logEvidence(STR."the \"Comorbidities\" section contains the following \{tableTitle} table: \{table.getRows()}",
            "Observed table match as expected", true);
    }

    @Given( "the \"Comorbidities\" section contains the following single table: $table" )
    @Then( "the \"Comorbidities\" section contains the following single table: $table" )
    public void checkSingleTable( ExamplesTable table ) {
        testDefinitionSteps.addStep(STR.
            "Check Comorbidities section contains single table");
        comorbiditiesSteps.checkSingleDataTable( new Table( table.getRows() ), false );
        testDefinitionSteps.logEvidence(STR."the \"Comorbidities\" section contains the following single table: \{table.getRows()}",
            "Observed table match as expected", true);
    }

}
