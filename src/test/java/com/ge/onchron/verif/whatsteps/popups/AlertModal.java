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
package com.ge.onchron.verif.whatsteps.popups;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.ge.onchron.verif.howsteps.AlertModalSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import net.thucydides.core.annotations.Steps;

public class AlertModal {

    private TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Steps
    private AlertModalSteps alertModalSteps;


    @Given( "an alert modal $isDisplayed displayed" )
    @Then( "an alert modal $isDisplayed displayed" )
    public void checkAlertModalVisibility( Boolean isDisplayed ) {
        testDefinitionSteps.addStep(STR."Check an alert modal visibility");
        alertModalSteps.checkAlertModalVisibility( isDisplayed );
        testDefinitionSteps.logEvidence(
                STR."An alert modal visibility is \{isDisplayed? "" : "not"} displayed",
                STR."An alert modal visibility was \{isDisplayed? "" : "not"} displayed as expected",
                true);
    }

    @Then( "the alert modal contains the following title: \"$title\"" )
    public void checkAlertModalTitle( String title ) {
        testDefinitionSteps.addStep(STR."Check the alert modal title");
        alertModalSteps.checkAlertModalTitle( title );
        testDefinitionSteps.logEvidence(
                STR."The alert modal contained the following title: \{title}",
                STR."The alert modal contained the following title: \{title}",
                true);

    }

    @Then( "the alert modal contains the following message: $alertMessage" )
    public void checkAlertModalMessage( String alertMessage ) {
        testDefinitionSteps.addStep(STR."Check the alert modal message");
        alertModalSteps.checkAlertModalMessage( alertMessage );
        testDefinitionSteps.logEvidence(
                STR."The alert modal contained the following message: \{alertMessage}",
                STR."The alert modal contained the following message: \{alertMessage}",
                true);
    }

    @When( "the user clicks on the \"$buttonText\" button on the alert modal" )
    public void clickButtonOnAlertModal( String buttonText ) {
        testDefinitionSteps.addStep(STR."Click on the \{buttonText} button on the alert modal");
        alertModalSteps.clickButtonOnAlertModal( buttonText );
        testDefinitionSteps.logEvidence(
                STR."The \{buttonText} button on the alert modal is clicked successfully",
                STR."The \{buttonText} button on the alert modal was clicked",
                true);
    }

    @When( "the user clicks on the 'X' button on the alert modal" )
    public void clickCloseOnAlertModal() {
        testDefinitionSteps.addStep("Click on the 'X' (close) button on the alert modal");
        alertModalSteps.closeAlertModal();
        testDefinitionSteps.logEvidence(
                STR."Alert modal was closed by clicking on the 'X' button",
                STR."Alert modal was closed by 'X' button",
                true);
    }

    @Then( "the alert modal disappears" )
    public void verifyAlertDisappears() {
        testDefinitionSteps.addStep(STR."Check the alert disappears");
        alertModalSteps.checkAlertModalVisibility( false );
        testDefinitionSteps.logEvidence(
                STR."The alert modal disappeared",
                STR."The alert modal was disappeared",
                true);
    }

}
