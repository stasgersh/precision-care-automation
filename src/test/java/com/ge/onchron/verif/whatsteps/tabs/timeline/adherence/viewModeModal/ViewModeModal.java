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
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.whatsteps.tabs.timeline.adherence.viewModeModal;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.howsteps.TimelineToolbarSteps;
import com.ge.onchron.verif.howsteps.ViewModeModalSteps;
import com.ge.onchron.verif.model.ViewModeModalConfiguration;
import net.thucydides.core.annotations.Steps;

public class ViewModeModal {

    @Steps
    private ViewModeModalSteps viewModeModalSteps;

    @Steps
    private TimelineToolbarSteps timelineToolbarSteps;

    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();


    @Given( "the user opened the View mode modal on the Timeline toolbar" )
    @When( "the user clicks on \"View mode\" button on the Timeline toolbar" )
    public void openViewModeModal() {
        testDefinitionSteps.addStep( STR."Opened View mode on Timeline toolbar" );
        viewModeModalSteps.openViewModeModal();
        testDefinitionSteps.logEvidence( "The View mode was opened successfully on Timeline",
                "Successfully opened View mode on Timeline", true );
    }

    @When( "the user clicks \"$button\" on the \"View mode\" modal" )
    public void clickButtonOnViewModeModal( String button ) {
        testDefinitionSteps.addStep( STR."Clicking \{button} on the View mode modal" );
        viewModeModalSteps.clickButtonOnViewMode( button );
        viewModeModalSteps.waitForViewModeModalLoaderToFinish();
        testDefinitionSteps.logEvidence( STR."The user clicked \{button} on the \"View mode\" modal successfully",
                STR."The user clicked \{button} on the \"View mode\" modal successfully", true );
    }

    @Given( "the user has set the following treatment protocol configuration for the patient: $adherenceProtocolDetails" )
    @When( "the user sets the following treatment protocol configuration for the patient: $adherenceProtocolDetails" )
    public void setTreatmentProtocolSettingsWithModalOpenAndClose( ExamplesTable adherenceProtocolDetails ) {
        openViewModeModal();
        checkViewModeModalDisplayed( true );
        setAdherenceProtocolSettingsonViewMode( adherenceProtocolDetails );
        clickButtonOnViewModeModal( "Done" );
        checkViewModeModalDisplayed( false );
    }

    @When( "the user sets the following options on \"View mode\" modal: $adherenceProtocolDetails" )
    public void setAdherenceProtocolSettingsonViewMode( ExamplesTable adherenceProtocolDetails ) {
        testDefinitionSteps.addStep( STR."Set View mode settings as: " + adherenceProtocolDetails.getRows() );
        viewModeModalSteps.setAdherenceProtocolSettings( adherenceProtocolDetails );
        testDefinitionSteps.logEvidence( STR."the user set the following options on \"View mode\" modal: \{adherenceProtocolDetails.getRows()}",
                STR."the user set the following options on \"View mode\" modal: \{adherenceProtocolDetails.getRows()}", true );
    }

    @When( "the user selects \"$viewMode\" checkbox on the View mode modal" )
    public void setViewMode( String viewMode ) {
        testDefinitionSteps.addStep( STR."Settings the following view mode: \{viewMode}" );
        viewModeModalSteps.setViewModeOnViewModeModal( viewMode );
        testDefinitionSteps.logEvidence( STR."User selected \{viewMode} checkbox on the View mode modal successfully",
                STR."User selected \{viewMode} checkbox on the View mode modal successfully", true );
    }

    @When( "the user opens the \"$dropdown\" dropdown on \"View mode\" modal" )
    public void openDropdownOfViewModeModal( String dropdown ) {
        testDefinitionSteps.addStep( STR."Opening the \{dropdown} dropdown on the View mode modal" );
        viewModeModalSteps.openDropDownOnViewModeModal( dropdown );
        testDefinitionSteps.logEvidence( STR."User opens the \{dropdown} dropdown on the View mode modal",
                STR."User opened the \{dropdown} dropdown on the View mode modal successfully", true );
    }

    @When( "the user closes the open dropdown on \"View mode\" modal" )
    public void closeOpenDropdownOfViewModeModal() {
        testDefinitionSteps.addStep( STR."Closing the open dropdown on the View mode modal" );
        viewModeModalSteps.closeOpenDropDownOnViewModeModal();
        testDefinitionSteps.logEvidence( STR."User closing the open dropdown on the View mode modal",
                STR."User closed the open dropdown on the View mode modal successfully", true );
    }

    @Then( "the \"View mode\" modal $isDisplayed displayed" )
    public void checkViewModeModalDisplayed( Boolean isDisplayed ) {
        if ( !isDisplayed ) {
            testDefinitionSteps.addStep( STR."Waiting for View mode modal to close" );
            viewModeModalSteps.waitForViewModeModalLoaderToFinish();
            viewModeModalSteps.waitForViewModeModalToClose();
        } else {
            testDefinitionSteps.addStep( STR."Checking that the View mode modal is \{isDisplayed ? " visible" : " not visible"}" );
            viewModeModalSteps.checkViewModeModalIsOrIsNotDisplayed( isDisplayed );
            viewModeModalSteps.waitForViewModeModalLoaderToFinish();
        }
        testDefinitionSteps.logEvidence(
                STR."The View mode modal \{isDisplayed ? " visible" : " not visible"}",
                STR."The View mode modal \{isDisplayed ? " visible" : " not visible"}",
                true );
    }

    @Then( "the following {protocols|trial arms} are present in the open dropdown on \"View mode\" modal: $optionList" )
    public void checkOpenDropdownOptionsOnViewMode( ExamplesTable optionList ) {
        testDefinitionSteps.addStep( STR."Checking that the following options are present in the open dropdown: \{optionList.getRows()}" );
        viewModeModalSteps.checkExpectedOptionsArePresentInTheOpenDropdown( optionList );
        testDefinitionSteps.logEvidence( STR."The following options were present in the open dropdown: \{optionList.getRows()}", STR."The following options were present in the open dropdown: \{optionList.getRows()}", true );
    }

    @Then( "the following error message is displayed under \"$field\" field on the View mode modal: \"$errorMessage\"" )
    public void checkErrorMessageOfFieldOnViewModal( String field, String errorMessage ) {
        testDefinitionSteps.addStep( STR."Checking that following error message is displayed under \{field} field: \{errorMessage}" );
        viewModeModalSteps.checkErrorMessageOfFieldOnViewModeModal( field, errorMessage );
        testDefinitionSteps.logEvidence( STR."The following error message was displayed under \{field} field: \{errorMessage}", STR."The following error message was displayed under \{field} field: \{errorMessage}", true );
    }

    @Then( "no error message is displayed under \"$field\" field on the View mode modal" )
    public void checkNoErrorMessageIsDisplayedOfViewModeModalField( String field ) {
        testDefinitionSteps.addStep( STR."Checking that no error message is displayed under \{field} field" );
        viewModeModalSteps.checkNoErrorMessageIsDisplayedOfViewModeModalField( field );
        testDefinitionSteps.logEvidence( STR."No error message was displayed under \{field} field:", STR."No error message was displayed under \{field} field", true );
    }

    @Then( "the \"$badgeText\" badge $isDisplayed displayed on the \"View mode\" button" )
    public void checkBadgeOnViewModeButton( String badgeText, Boolean isDisplayed ) {
        testDefinitionSteps.addStep( STR."Checking that the \{badgeText} is \{isDisplayed ? " visible" : " not visible"} on View mode button" );
        timelineToolbarSteps.checkBadgeOnViewModeButton( badgeText, isDisplayed );
        testDefinitionSteps.logEvidence(
                STR."The \{badgeText} is \{isDisplayed ? " visible" : " not visible"} on View mode button",
                STR."The \{badgeText} was \{isDisplayed ? " visible" : " not visible"} on View mode button",
                true );
    }

    @Then( "the following treatment configuration is automatically filled on the View mode modal: $viewModeConfigurationTable" )
    public void checkViewModeModalConfiguration( ViewModeModalConfiguration expectedConfiguration ) {
        testDefinitionSteps.addStep( STR."Checking that the View mode modal configuration was filled automatically for the patient" );
        viewModeModalSteps.checkViewModeModalConfiguration( expectedConfiguration );
        testDefinitionSteps.logEvidence(
                STR."The following treatment confguration is filled on the View mode modal: \{expectedConfiguration.toString()}",
                STR."The following treatment confguration was filled on the View mode modal: \{expectedConfiguration.toString()}",
                true );
    }
}
