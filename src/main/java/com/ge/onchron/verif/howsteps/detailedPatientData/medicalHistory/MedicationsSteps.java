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
package com.ge.onchron.verif.howsteps.detailedPatientData.medicalHistory;

import com.ge.onchron.verif.howsteps.PatientHeaderSteps;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.ge.onchron.verif.howsteps.detailedPatientData.DetailedPatientDataSteps;
import com.ge.onchron.verif.model.Table;
import com.ge.onchron.verif.pages.sections.DetailedPatientData;
import com.ge.onchron.verif.pages.sections.medicalHistory.Medications;
import com.ge.onchron.verif.pages.utils.TableUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ge.onchron.verif.model.enums.MedicalHistorySection.MEDICATIONS;
import static com.ge.onchron.verif.pages.utils.PageElementUtils.waitForCompleteDocument;

public class MedicationsSteps extends DetailedPatientDataSteps {

    private           Medications medicationsSection;
    private static final Logger      LOGGER = LoggerFactory.getLogger( MedicationsSteps.class );
    @Override
    public DetailedPatientData getDetailedPatientData() {
        medicationsSection.waitUntilLoadingSkeletonDisappears();
        return PageFactory.initElements( medicationsSection.getMedicalHistorySection( MEDICATIONS ), DetailedPatientData.class );
    }

    @Override
    public void waitUntilLoadingSkeletonDisappears() {
        medicationsSection.waitUntilLoadingSkeletonDisappears();
    }

    public void checkMedicationsVisibility( boolean isDisplayed ) {
        waitForCompleteDocument();
        assertThat( "Visibility of the Medications view is not ok.", medicationsSection.isVisible(), is( equalTo( isDisplayed ) ) );
    }

    public void openCompletedMedications() {
        medicationsSection.waitUntilLoadingSkeletonDisappears();
        medicationsSection.expandCompletedMedications();
    }

    public void closeCompletedMedications() {
        medicationsSection.collapseCompletedMedications();
    }

    public void checkDefaultMedicationsTableVisibility( Boolean isVisible ) {
        medicationsSection.waitUntilLoadingSkeletonDisappears();
        boolean defaultMedTableVisible = medicationsSection.isDefaultMedTableVisible();
        LOGGER.info(STR."Default Medications table visibility is: \{defaultMedTableVisible}");
        assertThat( "Default active medications table visibility is not ok.", defaultMedTableVisible, is( equalTo( isVisible ) ) );
    }

    public void checkCompletedMedicationsHeaderVisibility( Boolean isVisible ) {
        medicationsSection.waitUntilLoadingSkeletonDisappears();
        boolean completedMedHeaderVisible = medicationsSection.isCompletedMedHeaderVisible();
        LOGGER.info(STR."Completed Medication header visability is: \{completedMedHeaderVisible}");
        assertThat( "Completed medications visibility is not ok.", completedMedHeaderVisible, is( equalTo( isVisible ) ) );
    }

    public void checkCompletedMedicationsTableVisibility( Boolean isVisible ) {
        medicationsSection.waitUntilLoadingSkeletonDisappears();
        boolean completedMedTableVisible = medicationsSection.isCompletedMedTableVisible();
        LOGGER.info(STR."Completed Medication table visability is: \{completedMedTableVisible}");
        assertThat( "Completed medications visibility is not ok.", completedMedTableVisible, is( equalTo( isVisible ) ) );
    }

    public void checkExpandButtonVisibility() {
        medicationsSection.waitUntilLoadingSkeletonDisappears();
        final boolean expandButtonVisiblity = medicationsSection.isExpandButtonVisible();
        LOGGER.info(STR."Expand button visibility : \{expandButtonVisiblity }");
        assertTrue( "Expand button visibility in Completed medications section is not ok.", expandButtonVisiblity );
    }

    public void checkCollapseButtonVisibility() {
        LOGGER.info(STR."Collapse button visibility : \{medicationsSection.isCollapseButtonVisible()}");
        assertTrue( "Collapse button visibility in Completed medications section is not ok.", medicationsSection.isCollapseButtonVisible() );
    }

    public void checkSortParamInDefaultMedicationsTable( String columnName, String arrowDirection ) {
        medicationsSection.waitUntilLoadingSkeletonDisappears();
        Table table = medicationsSection.getDefaultMedTable();
        LOGGER.info(STR."Default medication table: \{table.getRows()}");
        checkSortParamOnTable( table, columnName, arrowDirection );
    }

    public void checkSortParamInCompletedMedicationsTable( String columnName, String arrowDirection ) {
        medicationsSection.waitUntilLoadingSkeletonDisappears();
        Table table = medicationsSection.getCompletedMedTable();
        LOGGER.info(STR."Completed medication table: \{table.getRows()}");
        checkSortParamOnTable( table, columnName, arrowDirection );
    }

    public void checkDefaultMedicationsTable(Table expectedMedTable, boolean withSameRawOrder) {
        medicationsSection.waitUntilLoadingSkeletonDisappears();
        Table observedMedTable = medicationsSection.getDefaultMedTable();
        LOGGER.info(STR."Observed Medications table: \{observedMedTable}");
        assertTrue(
            STR."Observed Medications table (default) does not match to the expected one\n Expected table: \{expectedMedTable}\n Observed table: \{observedMedTable}\n",
            observedMedTable.hasSameRows(expectedMedTable, withSameRawOrder));
    }

    public void checkCompletedMedicationsTable(Table expectedMedTable, boolean withSameRawOrder) {
        medicationsSection.waitUntilLoadingSkeletonDisappears();
        Table observedMedTable = medicationsSection.getCompletedMedTable();
        LOGGER.info(STR."Observed Medications table: \{observedMedTable}");
        assertTrue(
            STR."Observed Completed medications table does not match to the expected one\n Expected table: \{expectedMedTable}\n Observed table: \{observedMedTable}\n",
            observedMedTable.hasSameRows(expectedMedTable, withSameRawOrder));
    }

    public void checkBadgeOnCompletedMedHeader( int expNumberOnBadge ) {
        medicationsSection.waitUntilLoadingSkeletonDisappears();
        String completedMedBadgeText = medicationsSection.getCompletedMedBadgeText();
        LOGGER.info(STR."completed med badge \{completedMedBadgeText}");
        assertEquals( "The number on the Completed medications badge is not ok.", expNumberOnBadge, Integer.parseInt( completedMedBadgeText ) );
    }

    public void clickOnColumnHeaderInDefaultMedTable( String columnName ) {
        medicationsSection.clickOnHeaderItemInDefaultMedTable( columnName );
    }

    public void clickOnColumnHeaderInCompletedMedTable( String columnName ) {
        medicationsSection.clickOnHeaderItemInCompletedMedTable( columnName );
    }

    public void checkDefaultMedicationsTableContentSorted( String columnName, String arrowDirection ) {
        medicationsSection.waitUntilLoadingSkeletonDisappears();
        Table observedMedTable = medicationsSection.getDefaultMedTable();
        LOGGER.info(STR."Default medication table: \{observedMedTable.getRows()}");

        Table sortedTable = TableUtils.sortTableByColumnName(observedMedTable, columnName, arrowDirection );

        assertTrue(
                STR."Sorted Medications table (default) does not match to the expected one\n Expected table: \{sortedTable}\n Observed table: \{observedMedTable}\n",
                observedMedTable.hasSameRows( sortedTable, true )
        );
    }
}
