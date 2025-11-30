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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import com.ge.onchron.verif.model.enums.MedicalHistorySection;
import com.ge.onchron.verif.pages.tabs.MedicalHistory;


public class CommonMedicalHistorySteps {

    private MedicalHistory medicalHistoryTab;
    private static final Logger         LOGGER = LoggerFactory.getLogger( CommonMedicalHistorySteps.class );

    public void checkMedicalHistoryViewVisibility( boolean isDisplayed ) {
        boolean historyTabVisible = medicalHistoryTab.isVisible();
        LOGGER.info(STR."Medical history view visibility is: \{historyTabVisible }");
        assertThat( "Visibility of the Medical history view is not ok.", historyTabVisible, is( equalTo( isDisplayed ) ) );
    }

    public void checkSectionAvailability( MedicalHistorySection medicalHistorySection, Boolean isDisplayed ) {
        medicalHistoryTab.waitUntilLoadingSkeletonDisappears();
        assertThat( STR."Visibility of the Medical history section is not ok: \{medicalHistorySection}" ,
                medicalHistoryTab.isSectionVisible( medicalHistorySection ), is( equalTo( isDisplayed ) ) );
    }

    public void checkSectionVisibilityInViewport( MedicalHistorySection medicalHistorySection, boolean isDisplayed ) {
        medicalHistoryTab.waitUntilLoadingSkeletonDisappears();
        assertThat( String.format( "'%s' medical history section %s visible in the viewport", medicalHistorySection.name(), (isDisplayed ? "is not" : "is") ),
                medicalHistoryTab.isSectionVisibleInViewport( medicalHistorySection ), is( equalTo( isDisplayed ) ) );
    }

    public void isNavigationButtonHighlighted( String navigationButtonName, boolean isHighlighted ) {
        medicalHistoryTab.waitUntilLoadingSkeletonDisappears();
        assertThat( String.format( "'%s' medical history sidenav button %s highlighted: ", navigationButtonName, (isHighlighted ? "is not" : "is") ),
                medicalHistoryTab.isNavigationButtonHighlighted( navigationButtonName ), is( equalTo( isHighlighted ) ) );
    }

    public void checkSectionOrder( List<String> expectedMedicalHistorySections ) {
        medicalHistoryTab.waitUntilLoadingSkeletonDisappears();
        List<String> actualMedicalHistorySections = medicalHistoryTab.getMedicalHistorySectionTitles();
        assertEquals( "Medical Section order is not expected", expectedMedicalHistorySections, actualMedicalHistorySections );
    }
}
