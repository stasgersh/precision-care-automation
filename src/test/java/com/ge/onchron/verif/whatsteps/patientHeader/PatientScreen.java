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
package com.ge.onchron.verif.whatsteps.patientHeader;

import com.ge.onchron.verif.howsteps.*;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import net.thucydides.core.annotations.Steps;

public class PatientScreen {

    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Steps
    private PatientScreenSteps  patientScreenSteps;

    @Given( "the Patient page is loaded" )
    @Then( "the Patient page is loaded" )
    public void verifyPatientPageIsLoaded() {
        testDefinitionSteps.addStep("Check patient page is loaded");
        patientScreenSteps.verifyPatientPageIsLoaded();
        testDefinitionSteps.logEvidence("The Patient page is loaded",
            "Patient page is loaded (see previous logs)",
            true);
    }

    @When( "the user navigates to the initial patient page" )
    @Given( "the patient page is displayed" )
    public void navigateToPatientPage() {
        testDefinitionSteps.addStep("Navigate to initial patient page");
        patientScreenSteps.navigateToInitialPatientPage();
        testDefinitionSteps.logEvidence("Patient page is displayed",
            "the patient page is displayed",
            true);
    }

}
