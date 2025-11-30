/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2024, 2024, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE HealthCare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 */
package com.ge.onchron.verif.whatsteps.popups;

import java.util.List;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.ge.onchron.verif.howsteps.SummaryLayoutModalSteps;
import com.ge.onchron.verif.howsteps.SummaryToolbarSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.model.StringList;
import com.ge.onchron.verif.model.summaryCard.SummaryGroup;
import net.thucydides.core.annotations.Steps;

public class SummaryLayoutModal {

    @Steps
    private SummaryLayoutModalSteps summaryLayoutModalSteps;
    @Steps
    private SummaryToolbarSteps summaryToolbarSteps;

    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Given( "the user navigated to the \"$optionName\" from the \"More\" menu" )
    @When( "the user navigates to the \"$optionName\" from the \"More\" menu" )
    public void navigateToMenu( String optionName ) {
        testDefinitionSteps.addStep( STR."the user navigates to the \"\{optionName}\" from the \"More\" menu" );
        summaryToolbarSteps.clickMoreButtonOnNavigationToolbar();
        summaryToolbarSteps.clickItemInViewOptions( optionName );
        summaryLayoutModalSteps.checkSummaryLayoutModalIsDisplayed( true );
        testDefinitionSteps.logEvidence(
                STR."the user navigated to the \"\{optionName}\" from the \"More\" menu",
                STR."the user navigated to the \"\{optionName}\" from the \"More\" menu",
                true );
    }

    @Then( "the \"Summary order and visibility\" modal $isDisplayed displayed" )
    public void checkSummaryLayoutModalIsDisplayed( Boolean isDisplayed ) {
        testDefinitionSteps.addStep( STR."Check that the \"Summary order and visibility\" modal \{isDisplayed ? "is" : "is not"} displayed" );
        summaryLayoutModalSteps.checkSummaryLayoutModalIsDisplayed( isDisplayed );
        testDefinitionSteps.logEvidence(
                STR."the \"Summary order and visibility\" modal \{isDisplayed ? "is" : "is not"} displayed",
                STR."the \"Summary order and visibility\" modal \{isDisplayed ? "is" : "is not"} displayed",
                true );
    }

    @Then( "the \"Summary order and visibility\" modal contains the following groups: $groups" )
    public void checkGroupNames( List<SummaryGroup> groups ) {
        testDefinitionSteps.addStep( STR."Check that the \"Summary order and visibility\" modal contains the following groups: \{groups}" );
        summaryLayoutModalSteps.checkGroups( groups );
        testDefinitionSteps.logEvidence(
                STR."the \"Summary order and visibility\" modal contains the following groups: \{groups}",
                STR."the \"Summary order and visibility\" modal contains the following groups: \{groups}",
                true );
    }

    @Given( "the user set the following group settings on the \"Summary order and visibility\" modal: $groups" )
    @When( "the user sets the following group settings on the \"Summary order and visibility\" modal: $groups" )
    public void setGroupSettings( List<SummaryGroup> groups ) {
        testDefinitionSteps.addStep( STR."the user set the following group settings on the \"Summary order and visibility\" modal: \{groups}" );
        summaryLayoutModalSteps.setGroupSettings( groups );
        testDefinitionSteps.logEvidence(
                STR."the user has set the following group settings on the \"Summary order and visibility\" modal: \{groups}",
                STR."the user has set the following group settings on the \"Summary order and visibility\" modal: \{groups}",
                true );
    }

    @When( "the user clicks on the \"$buttonText\" button on the \"Summary order and visibility\" modal" )
    public void clickOnButton( String buttonText ) {
        testDefinitionSteps.addStep( STR."the user clicks on the \"\{buttonText}\" button on the \"Summary order and visibility\" modal" );
        summaryLayoutModalSteps.clickOnButton( buttonText );
        testDefinitionSteps.logEvidence(
                STR."the user clicked on the \"\{buttonText}\" button on the \"Summary order and visibility\" modal",
                STR."the user clicked on the \"\{buttonText}\" button on the \"Summary order and visibility\" modal",
                true );
    }

    @Then( "the \"Summary order and visibility\" modal disappears" )
    public void waitForDisappearModal() {
        testDefinitionSteps.addStep( STR."Check that the \"Summary order and visibility\" modal disappears" );
        summaryLayoutModalSteps.waitForDisappearModal();
        testDefinitionSteps.logEvidence(
                "the \"Summary order and visibility\" modal disappeared",
                "the \"Summary order and visibility\" modal disappeared",
                true );
    }

    @Given( "the user saved the settings on the \"Summary order and visibility\" modal" )
    public void saveSettings() {
        testDefinitionSteps.addStep( "the user saves the settings on the \"Summary order and visibility\" modal" );
        summaryLayoutModalSteps.clickOnButton( "Save" );
        summaryLayoutModalSteps.waitForDisappearModal();
        testDefinitionSteps.logEvidence(
                "the user saved the settings on the \"Summary order and visibility\" modal",
                "the user saved the settings on the \"Summary order and visibility\" modal",
                true );
    }

    @Then( "the following columns are available on the \"Summary order and visibility\" modal: $columnNames" )
    public void checkColumnNames( StringList columnNames ) {
        testDefinitionSteps.addStep( STR."Check that the following columns are available on the \"Summary order and visibility\" modal: \{columnNames}" );
        summaryLayoutModalSteps.checkColumnNames( columnNames.getList() );
        testDefinitionSteps.logEvidence(
                STR."the following columns are available on the \"Summary order and visibility\" modal: \{columnNames}",
                STR."the following columns are available on the \"Summary order and visibility\" modal: \{columnNames}",
                true );
    }

}
