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
package com.ge.onchron.verif.whatsteps.user;

import java.util.List;

import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.ge.onchron.verif.howsteps.PatientScreenSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.howsteps.UserMenuSteps;
import com.ge.onchron.verif.howsteps.UserSettingsSteps;
import com.ge.onchron.verif.model.StringList;
import com.ge.onchron.verif.model.UserCredentials;
import com.ge.onchron.verif.model.UserSetting;
import com.ge.onchron.verif.model.enums.OnOff;
import net.thucydides.core.annotations.Steps;

public class UserSettings {

    @Steps
    private UserSettingsSteps userSettingsSteps;
    @Steps
    private PatientScreenSteps patientScreenSteps;
    @Steps
    private UserMenuSteps userMenuSteps;

    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Given( "[API] the CLP visualization is switched $clpState for [$userCredentials] user" )
    @When( "[API] the [$userCredentials] user turns $clpState the CLP visualization" )
    @Aliases( values = {
            "[API] the AI data visualization is switched $clpState for [$userCredentials] user",
            "[API] the [$userCredentials] user turns $clpState the AI data visualization"
    } )
    public void switchClpVisualization( OnOff clpState, UserCredentials userCredentials ) {
        testDefinitionSteps.addStep( STR."Switch NLP visualization state to: \{clpState} for user: \{userCredentials.getAlias()}" );
        userSettingsSteps.switchClpVisualization( clpState, userCredentials );
        patientScreenSteps.refreshIfPageIsVisible();
        testDefinitionSteps.logEvidence( STR."The NLP visualization for user: \{userCredentials.getAlias()} state switched to: \{clpState} successfully",
                "User NLP visualization state switched successfully", true );
    }

    @Given( "[API] the following swimlane order was set by the [$userCredentials] user: $swimlaneOrder" )
    public void setSwimlaneOrder( UserCredentials userCredentials, StringList swimlaneOrder ) {
        testDefinitionSteps.addStep( STR."User \{userCredentials.getAlias()} sets swimlane order as following: \{swimlaneOrder}" );
        userSettingsSteps.setSwimlaneOrder( swimlaneOrder.getList(), userCredentials );
        patientScreenSteps.refreshIfPageIsVisible();
        testDefinitionSteps.logEvidence( STR."The user: \{userCredentials.getAlias()} successfully set the swimlane in the expected order",
                "The swimlane order successfully was set", true );
    }

    @Given( "[API] the default swimlane order was set by the [$userCredentials] user" )
    public void setDefaultSwimlaneOrder( UserCredentials userCredentials ) {
        testDefinitionSteps.addStep( STR."User \{userCredentials.getAlias()} sets swimlane order to default" );
        userSettingsSteps.setDefaultSwimlaneOrder( userCredentials );
        patientScreenSteps.refreshIfPageIsVisible();
        testDefinitionSteps.logEvidence( STR."The user: \{userCredentials.getAlias()} successfully set the swimlane in default order",
                "The swimlane order successfully was set", true );
    }

    @Given( "the Settings page $isDisplayed displayed" )
    @Then( "the Settings page $isDisplayed displayed" )
    public void checkSettingsPageVisibility( Boolean isDisplayed ) {
        testDefinitionSteps.addStep( "Check the settings page visibility" );
        userSettingsSteps.checkSettingsPageVisibility( isDisplayed );
        testDefinitionSteps.logEvidence( STR."The settings page is visible: \{isDisplayed}",
                "The settings page is displayed as expected (check previous logs)", isDisplayed );
    }

    @Given( "the user navigated to the Settings page" )
    @When( "the user navigates to the Settings page" )
    public void navigateToSettingsPage() {
        testDefinitionSteps.addStep( "Navigate to settings page" );
        userMenuSteps.clickOnMenuItem( "Settings" );
        userSettingsSteps.checkSettingsPageVisibility( true );
        testDefinitionSteps.logEvidence( "The settings page is displayed",
                "The settings page is displayed as expected (check previous logs)", true );
    }

    @When( "the user clicks on 'Edit' button at the following user setting: $userSetting" )
    public void clickEditOnSetting( UserSetting userSetting ) {
        testDefinitionSteps.addStep( STR."Click on edit button in user settings for \{userSetting.getTitle()}" );
        userSettingsSteps.clickEditOnSetting( userSetting );
        testDefinitionSteps.logEvidence( STR."The edit button was clicked successfully for \{userSetting.getTitle()}",
                "The edit button was clicked successfully (check previous logs)", true );
    }

    @When( "the user selects the \"$radioBtnLabel\" radio button at the following user setting: $userSetting" )
    public void selectRadioButton( String radioBtnLabel, UserSetting userSetting ) {
        testDefinitionSteps.addStep( STR."Select radio button: \{radioBtnLabel} from user setting: \{userSetting.getSettingName()}" );
        userSettingsSteps.selectRadioButton( radioBtnLabel, userSetting );
        testDefinitionSteps.logEvidence( STR."The radio button: \{radioBtnLabel} was clicked successfully",
                "The radio button clicked successfully", true );
    }

    @When( "the user clicks on the \"$btnLabel\" button on the Settings page" )
    public void clickButtonInSettingsContent( String btnLabel ) {
        testDefinitionSteps.addStep( STR."Click on: \{btnLabel} button in settings content" );
        userSettingsSteps.clickButtonInSettingsContent( btnLabel );
        testDefinitionSteps.logEvidence( STR."The button: \{btnLabel} was clicked successfully",
                "The button clicked successfully", true );
    }

    @When( "the user clicks on the \"$btnLabel\" button in the Settings toolbar header" )
    public void clickButtonInSettingsToolbar( String btnLabel ) {
        testDefinitionSteps.addStep( STR."Click on: \{btnLabel} button in settings toolbar" );
        userSettingsSteps.clickButtonInSettingsToolbar( btnLabel );
        testDefinitionSteps.logEvidence( STR."The button: \{btnLabel} was clicked successfully",
                "The button clicked successfully", true );
    }

    @Given( "the following settings are available on the page: $userSettings" )
    @Then( "the following settings are available on the page: $userSettings" )
    public void checkAvailableUserSettings( List<UserSetting> userSettings ) {
        testDefinitionSteps.addStep( "Check available user settings" );
        userSettingsSteps.checkAvailableUserSettings( userSettings );
        testDefinitionSteps.logEvidence( STR."The user settings \{userSettings}  available on the page as expected",
                "The settings available as expected successfully (check previous logs)", true );

    }

    @Given( "the \"$settingName\" setting in the \"$settingsSectionName\" section contains the following ordered list: $swimlanes" )
    @Then( "the \"$settingName\" setting in the \"$settingsSectionName\" section contains the following ordered list: $swimlanes" )
    public void checkListValueOfUserSetting( String settingsSectionName, String settingName, StringList expectedList ) {
        testDefinitionSteps.addStep( STR."Check list value of user settings: \{settingName}, in settings section: \{settingsSectionName}" );
        UserSetting userSetting = new UserSetting();
        userSetting.setTitle( settingsSectionName );
        userSetting.setSettingName( settingName );
        userSettingsSteps.checkListValueOfUserSetting( userSetting, expectedList );
        testDefinitionSteps.logEvidence( STR."The user settings has swimlane in the expected order: \{expectedList} as expected",
                "The user settings has swimlane in the expected order (check previous logs)", true );
    }

    @When( "the user sets the following \"$settingName\" list in \"$settingsSectionName\" section: $swimlanes" )
    public void setListValueOfUserSetting( String settingName, String settingsSectionName, StringList expectedList ) {
        testDefinitionSteps.addStep( STR."Set the list value of user setting: \{settingName} to \{expectedList}" );
        UserSetting userSetting = new UserSetting();
        userSetting.setTitle( settingsSectionName );
        userSetting.setSettingName( settingName );
        userSettingsSteps.setDragAndDropUserSetting( userSetting, expectedList );
        testDefinitionSteps.logEvidence( STR."The user settings: \{settingName} under section: \{settingsSectionName} has swimlane in the expected order: \{expectedList} as expected",
                "The user settings has swimlane in the expected order (check previous logs)", true );
    }

    @Given( "[API] the Oncology focus is turned $oncoFocusState by the user [$userCredentials]" )
    public void showEmtpyCards( OnOff oncoFocusState, UserCredentials userCredentials ) {
        testDefinitionSteps.addStep( STR."Switch Oncology focus state to: \{oncoFocusState} for user: \{userCredentials.getAlias()}" );
        userSettingsSteps.switchOncoFocus( oncoFocusState, userCredentials );
        patientScreenSteps.refreshIfPageIsVisible();
        testDefinitionSteps.logEvidence(
                STR."The Oncology focus state for user: \{userCredentials.getAlias()} switched to: \{oncoFocusState}",
                STR."The Oncology focus state for user: \{userCredentials.getAlias()} switched to: \{oncoFocusState}", true );
    }

}
