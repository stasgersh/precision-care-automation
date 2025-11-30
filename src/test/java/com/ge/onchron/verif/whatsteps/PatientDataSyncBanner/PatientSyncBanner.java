/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2023, 2023, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE HealthCare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */


package com.ge.onchron.verif.whatsteps.PatientDataSyncBanner;

import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.model.StringList;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.ge.onchron.verif.howsteps.PatientDataSyncBannerSteps;
import com.ge.onchron.verif.model.BannerNotification;
import net.thucydides.core.annotations.Steps;

import static com.ge.onchron.verif.utils.Utils.insertSystemParameters;

public class PatientSyncBanner {

    @Steps
    private     PatientDataSyncBannerSteps patientDataSyncSteps;
    private TestDefinitionSteps        testDefinitionSteps =
        TestDefinitionSteps.getInstance();

    @Given( "the patient data sync banner $isDisplayed displayed" )
    @Then( "the patient data sync banner $isDisplayed displayed" )
    public void isPatientSyncBannerDisplayed( Boolean isDisplayed )
    {
        testDefinitionSteps.addStep(STR."Check that patient data sync banner is \{isDisplayed ? "" : "not"} displayed");
        patientDataSyncSteps.checkPatientDataSyncBannerVisibility( isDisplayed );
        testDefinitionSteps.logEvidence(STR."the patient data sync banner is \{isDisplayed ? "" : "not"} displayed",
            STR."Patient sync banner is \{isDisplayed} visible", true);
    }

    @Then( "the message displayed in the banner matches to regex: \"$messages\"" )
    @Given( "the message displayed in the banner matches to regex: \"$messages\"" )
    public void checkPatientSyncBannerMsg( String message ) {
        testDefinitionSteps.addStep("Check message content displayed in the banner");
        patientDataSyncSteps.checkPatientSyncBannerMsg( message );
        testDefinitionSteps.logEvidence(STR."the message displayed in the banner matches to regex: \{message}",
            "Patient sync banner message is ok", true);
    }
    @Then("the message displayed in the banner with the following parameters: $bannerNotification")
    public void checkPatientSyncBannerMsgBackgroundColor(BannerNotification bannerNotification) {
        testDefinitionSteps.addStep(STR."Check \{bannerNotification.getMessage()} message displayed in the banner with: semantic color (\{bannerNotification.getBackgroundColor()}) and warning icon shown on status bar size of \{bannerNotification.getBarSize()}");
        patientDataSyncSteps.checkPatientSyncBannerMsgWithIndicationsDetails(bannerNotification);
        testDefinitionSteps.logEvidence(STR."the message displayed in the banner matches semantic color (\{bannerNotification.getBackgroundColor()}) and warning icon shown on status bar size of \{bannerNotification.getBarSize()}", "Patient sync banner message is ok", true);
    }

    @Then( "the message $message, $isDisplayed displayed in the banner" )
    @Given( "the message $message, $isDisplayed displayed in the banner" )
    public void checkPatientSyncBannerMsg( String message, Boolean isDisplayed ) {
        testDefinitionSteps.addStep(STR."Check message:\{message} display status is \{isDisplayed ? "" : "not"} displayed in the banner");
        patientDataSyncSteps.checkPatientSyncBannerMsg( message, isDisplayed );
        testDefinitionSteps.logEvidence(STR."The message:\{message} display status is \{isDisplayed} in the banner",
                STR."Patient sync banner message display status is: is \{isDisplayed ? "" : "not"} displayed", true);
    }

    @Then("the message displayed in the banner with the following parameters for clinical trials: $bannerNotification")
    public void checkPatientSyncBannerMsgForClinicalTrialsBackgroundColor(BannerNotification bannerNotification) {
        testDefinitionSteps.addStep(STR."Check \{bannerNotification.getMessage()} message displayed in the banner with: semantic color (\{bannerNotification.getBackgroundColor()}) and warning icon shown on status bar size of \{bannerNotification.getBarSize()}" );
        patientDataSyncSteps.checkPatientSyncBannerMsgForTrialsWithIndicationsDetails(bannerNotification);
        testDefinitionSteps.logEvidence(STR."the message displayed in the banner matches semantic color (\{bannerNotification.getBackgroundColor()}) and warning icon shown on status bar size of \{bannerNotification.getBarSize()}",
            "Patient sync banner message is ok", true);
    }

    @Then( "the following patient sync banner appears in $timeoutInMillis milliseconds: $bannerNotification" )
    @Alias( "the following patient sync banner appears in $timeoutInMillis milliseconds, regex: \"$bannerNotification\"" )
    public void waitForPatientSyncBanner( String timeoutInMillis, BannerNotification bannerNotification ) {
        int timeoutMillis = Integer.parseInt( insertSystemParameters( timeoutInMillis ) );
        testDefinitionSteps.addStep(STR."Check that patient sync banner appear in less than \{timeoutMillis} milliseconds");
        patientDataSyncSteps.waitForPatientSyncBanner( timeoutMillis, bannerNotification );
        testDefinitionSteps.logEvidence(STR."the following patient sync banner appears in \{timeoutMillis} milliseconds: \{bannerNotification}",
            STR."Patient sync banner appears on less than \{timeoutMillis} successfully", true);
    }

    @When( "the user clicks on the button on the following patient sync banner: $bannerNotification" )
    public void clickOnButtonOnPatientSyncBanner( BannerNotification bannerNotification ) {
        testDefinitionSteps.addStep("Click on the button on the patient banner");
        patientDataSyncSteps.clickOnButtonOnPatientSyncBanner( bannerNotification );
        testDefinitionSteps.logEvidence(STR."the user clicks on the button on the following patient sync banner: \{bannerNotification}",
            STR."The button on the following patient sync banner: \{bannerNotification} was clicked succesfully", true);
    }
    @Then("the last sync time is different for the patients: $patients")
    public void lastSyncTimeIsDifferentForPatients( StringList patients ) {
        testDefinitionSteps.addStep("Check Last sync time for the patients");
        patientDataSyncSteps.comparePatientsLastSyncTime(patients);
        testDefinitionSteps.logEvidence("last sync time is different for the patients",
                "last sync time is different for the patients", true);
    }
    @Then("The patients were last synced in following order: $patients")
    public void checkWhichPatientWasSyncedLater( StringList patients) {
        testDefinitionSteps.addStep("Check Which patient was synced later");
        patientDataSyncSteps.checkPatientsSyncTimeOrder(patients);
        testDefinitionSteps.logEvidence(STR."\{patients.getList().getLast()} was synced later than \{patients.getList().getFirst()}",
                STR."\{patients.getList().getLast()} was synced later than \{patients.getList().getFirst()}", true);
    }

}
