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
package com.ge.onchron.verif.howsteps.detailedPatientData.medicalHistory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

import com.ge.onchron.verif.howsteps.detailedPatientData.DetailedPatientDataSteps;
import com.ge.onchron.verif.model.Table;
import com.ge.onchron.verif.pages.sections.DetailedPatientData;
import com.ge.onchron.verif.pages.sections.medicalHistory.PatientHistory;
import com.ge.onchron.verif.pages.utils.TableUtils;

public class PatientHistorySteps extends DetailedPatientDataSteps {

    private PatientHistory patientHistorySection;
    private static final Logger LOGGER = LoggerFactory.getLogger( PatientHistorySteps.class );


    @Override
    public DetailedPatientData getDetailedPatientData() {
        patientHistorySection.waitUntilLoadingSkeletonDisappears();
        return patientHistorySection.getDetailedPatientData();
    }

    @Override
    public void waitUntilLoadingSkeletonDisappears() {
        patientHistorySection.waitUntilLoadingSkeletonDisappears();
    }

    public void checkSortParamInSurgicalHistoryTable( String columnName, String arrowDirection ) {
        patientHistorySection.waitUntilLoadingSkeletonDisappears();
        Table table = patientHistorySection.getSurgicalHistoryTable();
        LOGGER.info(STR."Surgical history table: \{table.getRows()}");
        checkSortParamOnTable( table, columnName, arrowDirection );
    }

    public void clickOnColumnHeaderInSurgicalHistoryTable (String columnName) {
        patientHistorySection.clickOnHeaderItemInSurgicalHistoryTable( columnName );
    }

    public void checkSurgicalHistoryTable( Table expectedSurgicalHistoryTable, boolean withSameRawOrder) {
        patientHistorySection.waitUntilLoadingSkeletonDisappears();
        Table observedSurgicalHistoryTable = patientHistorySection.getSurgicalHistoryTable();
        LOGGER.info(STR."Observed Surgical history table: \{observedSurgicalHistoryTable}");
        assertTrue(
                STR."Observed Surgical history table does not match to the expected one\n Expected table: \{expectedSurgicalHistoryTable}\n Observed table: \{observedSurgicalHistoryTable}\n",
                observedSurgicalHistoryTable.hasSameRows(expectedSurgicalHistoryTable, withSameRawOrder));
    }

    public void checkSurgicalHistoryTableContentSorted( String columnName, String arrowDirection ) {
        patientHistorySection.waitUntilLoadingSkeletonDisappears();
        Table observedSurgicalHistoryTable = patientHistorySection.getSurgicalHistoryTable();
        LOGGER.info(STR."Surgical history table: \{observedSurgicalHistoryTable.getRows()}");

        Table sortedTable = TableUtils.sortTableByColumnName(observedSurgicalHistoryTable, columnName, arrowDirection );

        assertTrue(
                STR."Sorted Surgical history table does not match to the expected one\n Expected table: \{sortedTable}\n Observed table: \{observedSurgicalHistoryTable}\n",
                observedSurgicalHistoryTable.hasSameRows( sortedTable, true )
        );
    }

}
