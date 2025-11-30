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
package com.ge.onchron.verif.howsteps.detailedPatientData.medicalHistory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

import com.ge.onchron.verif.howsteps.detailedPatientData.DetailedPatientDataSteps;
import com.ge.onchron.verif.model.Table;
import com.ge.onchron.verif.pages.sections.DetailedPatientData;
import com.ge.onchron.verif.pages.sections.medicalHistory.AllergiesAndIntolerances;
import com.ge.onchron.verif.pages.utils.TableUtils;

public class AllergiesAndIntolerancesSteps extends DetailedPatientDataSteps {

    private AllergiesAndIntolerances allergiesAndIntolerancesSection;
    private static final Logger LOGGER = LoggerFactory.getLogger( AllergiesAndIntolerancesSteps.class );


    @Override
    public DetailedPatientData getDetailedPatientData() {
        allergiesAndIntolerancesSection.waitUntilLoadingSkeletonDisappears();
        return allergiesAndIntolerancesSection.getDetailedPatientData();
    }

    @Override
    public void waitUntilLoadingSkeletonDisappears() {
        allergiesAndIntolerancesSection.waitUntilLoadingSkeletonDisappears();
    }

    public void checkSortParamInAllergiesTable( String columnName, String arrowDirection ) {
        allergiesAndIntolerancesSection.waitUntilLoadingSkeletonDisappears();
        Table table = allergiesAndIntolerancesSection.getAllergiesAndIntolerancesTable();
        LOGGER.info(STR."Allergies and intolerances table: \{table.getRows()}");
        checkSortParamOnTable( table, columnName, arrowDirection );
    }

    public void clickOnColumnHeaderInAllergiesTable( String columnName ){
        allergiesAndIntolerancesSection.clickOnHeaderItemInAllergiesTable( columnName );
    }

    public void checkAllergiesTable( Table expectedAllergiesTable, boolean withSameRawOrder) {
        allergiesAndIntolerancesSection.waitUntilLoadingSkeletonDisappears();
        Table observedAllergiesTable = allergiesAndIntolerancesSection.getAllergiesAndIntolerancesTable();
        LOGGER.info(STR."Observed Allergies and intolerances table: \{observedAllergiesTable}");
        assertTrue(
                STR."Observed Allergies and intolerances table does not match to the expected one\n Expected table: \{expectedAllergiesTable}\n Observed table: \{observedAllergiesTable}\n",
                observedAllergiesTable.hasSameRows(expectedAllergiesTable, withSameRawOrder));
    }

    public void checkAllergiesTableContentSorted( String columnName, String arrowDirection ) {
        allergiesAndIntolerancesSection.waitUntilLoadingSkeletonDisappears();
        Table observedAllergiesTable = allergiesAndIntolerancesSection.getAllergiesAndIntolerancesTable();
        LOGGER.info(STR."Allergies and intolerances table: \{observedAllergiesTable.getRows()}");

        Table sortedTable = TableUtils.sortTableByColumnName(observedAllergiesTable, columnName, arrowDirection );

        assertTrue(
                STR."Sorted Allergies and intolerances table does not match to the expected one\n Expected table: \{sortedTable}\n Observed table: \{observedAllergiesTable}\n",
                observedAllergiesTable.hasSameRows( sortedTable, true )
        );
    }

}
