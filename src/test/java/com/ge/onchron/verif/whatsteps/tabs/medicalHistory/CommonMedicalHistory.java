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

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.model.ExamplesTable;

import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.howsteps.detailedPatientData.medicalHistory.CommonMedicalHistorySteps;
import com.ge.onchron.verif.model.enums.MedicalHistorySection;
import net.thucydides.core.annotations.Steps;

import static com.ge.onchron.verif.pages.utils.TableUtils.getExampleTableAsList;

public class CommonMedicalHistory {

    @Steps
    private CommonMedicalHistorySteps medicalHistorySteps;
    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Given( "the Medical history view $isDisplayed displayed" )
    @Then( "the Medical history view $isDisplayed displayed" )
    public void checkMedicalHistoryViewIsDisplayed( Boolean isDisplayed ) {
        testDefinitionSteps.addStep( STR."Check Medical history view is \{isDisplayed ? "" : "not"} displayed" );
        medicalHistorySteps.checkMedicalHistoryViewVisibility( isDisplayed );
        testDefinitionSteps.logEvidence( STR."the Medical history view \{isDisplayed ? "" : "not"} displayed",
                "Visibility of the Medical history section is ok", true );
    }

    @Given( "the \"$medicalHistorySection\" section $isDisplayed available in Medical history" )
    @Then( "the \"$medicalHistorySection\" section $isDisplayed available in Medical history" )
    public void checkSectionAvailability( MedicalHistorySection medicalHistorySection, Boolean isDisplayed ) {
        testDefinitionSteps.addStep( STR."Check \{medicalHistorySection.name()} section is \{isDisplayed ? "" : "not"} available in Medical history" );
        medicalHistorySteps.checkSectionAvailability( medicalHistorySection, isDisplayed );
        testDefinitionSteps.logEvidence( STR."the \{medicalHistorySection.name()} section \{isDisplayed ? "" : "not"} available in Medical history",
                "Visibility of the Medical history section is ok", true );
    }

    @Then( "the \"$medicalHistorySection\" section $isDisplayed displayed in the viewport on Medical history view" )
    public void checkSectionVisibilityInViewport( MedicalHistorySection medicalHistorySection, Boolean isDisplayed ) {
        testDefinitionSteps.addStep( STR."Check \{medicalHistorySection.name()} section is \{isDisplayed ? "" : "not"} in the viewport on Medical history view" );
        medicalHistorySteps.checkSectionVisibilityInViewport( medicalHistorySection, isDisplayed );
        testDefinitionSteps.logEvidence( STR."the \{medicalHistorySection.name()} section \{isDisplayed ? "" : "not"} displayed in the viewport on Medical history view",
                STR."'\{medicalHistorySection.name()}' medical history section \{isDisplayed ? "" : "not"} visible in the viewport", true );
    }

    @Then( "the \"$medicalHistorySection\" navigation button $isHighlighted highlighted on Medical history view" )
    public void isNavigationButtonHighlighted( String navigationButtonName, Boolean isHighlighted ) {
        testDefinitionSteps.addStep( STR."Check \{navigationButtonName} navigation button  is \{isHighlighted ? "" : "not"} highlighted on Medical history view" );
        medicalHistorySteps.isNavigationButtonHighlighted( navigationButtonName, isHighlighted );
        testDefinitionSteps.logEvidence( STR."the '\{navigationButtonName}' navigation button \{isHighlighted ? "" : "not"} highlighted on Medical history view",
                STR."'\{navigationButtonName}' medical history sidenav button \{isHighlighted ? "" : "not"} highlighted: ", true );
    }

    @Then( "the Medical history sections are displayed in the following order: $medicalHistorySectionList" )
    public void checkSectionOrder( ExamplesTable medicalHistorySectionList ) {
        testDefinitionSteps.addStep( "Check Medical history section order" );
        List<String> expectedMedicalHistorySections = getExampleTableAsList( medicalHistorySectionList );
        medicalHistorySteps.checkSectionOrder( expectedMedicalHistorySections );
        testDefinitionSteps.logEvidence( STR."The Medical history sections are displayed in the expected order",
                STR."The Medical history sections are displayed in the expected order", true );
    }

}
