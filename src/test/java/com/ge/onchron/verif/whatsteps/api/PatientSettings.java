/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2024, 2024, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 */
package com.ge.onchron.verif.whatsteps.api;

import com.ge.onchron.verif.howsteps.PatientScreenSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.When;

import com.ge.onchron.verif.howsteps.PatientSettingsSteps;
import com.ge.onchron.verif.model.UserCredentials;
import net.thucydides.core.annotations.Steps;

public class PatientSettings {
    private TestDefinitionSteps  testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Steps
    private PatientSettingsSteps patientSettingsSteps;
    @Steps
    private PatientScreenSteps patientScreenSteps;

    @Given( "[API] the [$userCredentials] user reset the $settings settings for \"$patientName\"" )
    @When( "[API] the [$userCredentials] user resets the $settings settings for \"$patientName\"" )
    public void resetPatientSettings( UserCredentials userCredentials, String settings, String patientName ) {
        testDefinitionSteps.addStep(STR."Reset the patient settings");
        patientSettingsSteps.resetPatientSettings( userCredentials, settings, patientName );
        testDefinitionSteps.logEvidence(STR.
                "The '\{userCredentials.getAlias()}' user reset the patient's \{settings} for '\{patientName}'",
            STR."The patient's \{settings} reset successfully",
            true);
    }

    @Given( "[API] the following summary layout was set for \"$patientName\" by [$userCredentials]: $settingFilePath" )
    @When( "[API] the following summary layout is set for \"$patientName\" by [$userCredentials]: $settingFilePath" )
    public void setSummaryLayoutForPatient( String patientName, UserCredentials userCredentials, String settingFilePath ) {
        testDefinitionSteps.addStep( STR."[API] the following summary layout is set for '\{patientName}' by \{userCredentials.getAlias()}: \{settingFilePath}" );
        patientSettingsSteps.setSummaryLayout( patientName, userCredentials, settingFilePath );
        patientScreenSteps.refreshIfPageIsVisible();
        testDefinitionSteps.logEvidence(
                STR."[API] the following summary layout was set by \{userCredentials.getAlias()}: \{settingFilePath}",
                STR."[API] the following summary layout was set by \{userCredentials.getAlias()}: \{settingFilePath}", true );
    }

    @Given( "[API] the empty cards $hide hidden on Summary view for \"$patientName\" by the [$userCredentials] user" )
    @When( "[API] the empty cards $hide hidden on Summary view for \"$patientName\" by the [$userCredentials] user" )
    public void showEmtpyCards( String patientName, Boolean hide, UserCredentials userCredentials ) {
        testDefinitionSteps.addStep( STR."Empty cards are \{hide ? "" : " not"} hidden on Summary view by the \{userCredentials.getAlias()} user" );
        patientSettingsSteps.hideEmptyCards( patientName, userCredentials, hide );
        patientScreenSteps.refreshIfPageIsVisible();
        testDefinitionSteps.logEvidence(
                STR."Empty cards are \{hide ? "" : "not"} hidden",
                STR."Empty cards are \{hide ? "" : "not"} hidden", true );
    }

    @Given( "[API] the comment settings are reset for \"$patientName\" by the [$userCredentials] user" )
    public void resetCommentSettings( String patientName, UserCredentials userCredentials ) {
        testDefinitionSteps.addStep( STR."Comment settings are reset by the \{userCredentials.getAlias()} user" );
        patientSettingsSteps.resetCommentSettings( patientName, userCredentials );
        testDefinitionSteps.logEvidence(STR.
                        "The '\{userCredentials.getAlias()}' user reset the patient's comment settings for '\{patientName}'",
                STR."The patient's comment settings were reset successfully",
                true);
        }

    @Given( "[API] the trials settings are reset for \"$patientName\" by the [$userCredentials] user" )
    public void resetTrialsSettings( String patientName, UserCredentials userCredentials ) {
        testDefinitionSteps.addStep( STR."Trials settings are reset by the \{userCredentials.getAlias()} user" );
        patientSettingsSteps.resetTrialsSettings( patientName, userCredentials );
        testDefinitionSteps.logEvidence(STR.
                        "The '\{userCredentials.getAlias()}' user reset the patient's trial settings for '\{patientName}'",
                STR."The patient's trial settings were reset successfully",
                true);
    }

}
