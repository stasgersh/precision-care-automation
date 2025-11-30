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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.ge.onchron.verif.howsteps.PatientHeaderSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.model.StringList;
import com.ge.onchron.verif.model.detailedDataItems.DetailedDataItem;
import net.thucydides.core.annotations.Steps;

public class PatientHeader {

    @Steps
    private PatientHeaderSteps patientHeaderSteps;
    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Given( "the Patient header section $isDisplayed displayed" )
    @Then( "the Patient header section $isDisplayed displayed" )
    public void isPatientHeaderDisplayed( Boolean isDisplayed ) {
        testDefinitionSteps.addStep( STR."Check if patient header section is \{isDisplayed ? "" : "not"} displayed" );
        patientHeaderSteps.checkPatientHeaderVisibility( isDisplayed );
        testDefinitionSteps.logEvidence( STR."the Patient header section \{isDisplayed ? "" : "not"} displayed", "Patient header visibility is ok", true );
    }

    @Given( "the \"$patientName\" patient is selected" )
    @When( "the user selects the \"$patientName\" patient" )
    public void selectPatient( String patientName ) {
        testDefinitionSteps.addStep( STR."Select paitient name:\{patientName}" );
        patientHeaderSteps.selectPatientByName( patientName, true );
        testDefinitionSteps.logEvidence( "Patient is selected successfully", "loaded patient banner without error", true );
    }

    @Given( "the patient with id $patientId  is selected" )
    @When( "the user selects the patient with id $patientId" )
    public void selectPatientById( String patientId ) {
        testDefinitionSteps.addStep( STR."Select paitient with id:\{patientId}" );
        patientHeaderSteps.selectPatientById( patientId, true );
        testDefinitionSteps.logEvidence( "Patient is selected successfully", "loaded patient banner without error", true );
    }

    @When( "the user selects the \"$patientName\" patient (without reseting patient settings)" )
    public void selectPatientWithoutResetPatientSettings( String patientName ) {
        testDefinitionSteps.addStep( STR."Select patient by name \{patientName}" );
        boolean initPatientSettings = false;
        patientHeaderSteps.selectPatientByName( patientName, initPatientSettings );
        testDefinitionSteps.logEvidence( "Patient selected successfully", "Patient sync banner is visible (See previous logs)", true );
    }

    @Then( "the \"$patientName\" patient is selected" )
    public void checkSelectedPatient( String patientName ) {
        testDefinitionSteps.addStep( STR."Check if patient \{patientName} selected" );
        patientHeaderSteps.checkSelectedPatientName( patientName );
        testDefinitionSteps.logEvidence( STR."The \{patientName} patient is selected", "Patient selected by name(See previous logs)", true );
    }

    @When( "the user selects the \"$patientName\" patient from the already opened patient list" )
    public void selectPatientFromOpenedList( String patientName ) {
        testDefinitionSteps.addStep( STR."Select patient by name \{patientName} from opened list" );
        patientHeaderSteps.selectPatientByNameFromOpenedList( patientName );
        testDefinitionSteps.logEvidence( STR."The \{patientName} patient selected from the already opened patient list", "Patient selected by name from opened patient list(See previous logs)", true );
    }

    @Given( "the user opened the patient select menu" )
    @When( "the user opens the patient select menu" )
    public void openPatientSelect() {
        testDefinitionSteps.addStep( "Open the patient select menu" );
        patientHeaderSteps.openPatientSelect();
        testDefinitionSteps.logEvidence( "The patient select menu opened", "Patient select menu is open(See previous logs)", true );
    }

    @Given( "the patient banner was expanded" )
    @When( "the user expands the patient banner" )
    public void expandPatientBannerWithCaret() {
        testDefinitionSteps.addStep( "Expand patient banner with caret" );
        patientHeaderSteps.expandPatientBannerWithCaret();
        testDefinitionSteps.logEvidence( "The patient banner was expanded", "The patient banner expand successfully(See previous logs)", true );
    }

    @Given( "the patient banner is collapsed" )
    @When( "the user collapses the patient banner" )
    public void closePatientBannerWithCaret() {
        testDefinitionSteps.addStep( "Close patient banner with caret" );
        patientHeaderSteps.closePatientBannerWithCaret();
        testDefinitionSteps.logEvidence( "the patient banner was collapsed", "The patient banner close successfully(See previous logs)", true );
    }

    @Then( "the following patient information displayed in the patient banner: $parameters" )
    public void checkAllPatientBannerDetails( List<DetailedDataItem> patientBannerItems ) {
        testDefinitionSteps.addStep( "Check the patient information displayed in the patient banner" );
        patientHeaderSteps.checkAllPatientBannerDetails( patientBannerItems );
        testDefinitionSteps.logEvidence( STR."The patient banner has expected details \{patientBannerItems}",
                "All banners displayed (check previous log)", true );
    }

    @Given( "the following parameters $isVisible visible in the patient banner: $parameterNames" )
    @Then( "the following parameters $isVisible visible in the patient banner: $parameterNames" )
    public void checkPatientBannerParamsVisibility( Boolean isVisible, StringList parameterNames ) {
        List<String> parameterNamesList = new ArrayList<>( parameterNames.getList() );
        testDefinitionSteps.addStep( STR."Check the patient parameters \{parameterNamesList} visibility" );
        patientHeaderSteps.checkPatientBannerParamsVisibility( parameterNames.getList(), isVisible );
        testDefinitionSteps.logEvidence( STR."the following parameters  \{isVisible ? "" : "not"} visible in the patient banner: \{parameterNamesList}",
                "All parameters visible on patient banner (check previous log)", true );
    }

    @Then( "the following patient information displayed in the patient banner and highlighted:$highlightedParams" )
    public void checkForAiIndicationOnGridMainSection( DetailedDataItem highlightedParams ) {
        testDefinitionSteps.addStep( STR."Check in the patient banner for highlighted parameters:\n\{highlightedParams}" );
        patientHeaderSteps.checkHighlightedAttributeAsIndicationForAI( highlightedParams );
        testDefinitionSteps.logEvidence(
                STR."The following parameters are highlighted in patient banner\n\{highlightedParams} successfully",
                STR."The observed paramters were found and highlighted:\n\{highlightedParams}", true );
    }

    @Given( "the patient banner includes the following data: $parameters" )
    @Then( "the patient banner includes the following data: $parameters" )
    public void checkSpecificPatientBannerDetails( List<DetailedDataItem> patientBannerItems ) {
        testDefinitionSteps.addStep( "Check parameters are includes in the patient banner" );
        patientHeaderSteps.checkSpecificPatientBannerDetails( patientBannerItems );
        testDefinitionSteps.logEvidence(
                STR."the patient banner includes the following data: \{patientBannerItems.stream().map( DetailedDataItem::toString )
                                                                                         .collect( Collectors.joining( "\n" ) )}",
                "Patient banner params match as Expected (check previous log)", true );
    }

    @When( "the user clicks on the following link in the patient banner: \"$linkName\"" )
    public void clickOnLinkInPatientBanner( String linkName ) {
        testDefinitionSteps.addStep( STR."Click on the link \{linkName} in the patient banner" );
        patientHeaderSteps.clickOnLinkInPatientBanner( linkName );
        testDefinitionSteps.logEvidence( STR."the user clicks on the link \{linkName} successfully",
                STR."The link on the patient banner was clicked successfully", true );
    }

    @Then( "the patient banner $isExpanded expanded" )
    public void isPatientBannerExpanded( Boolean isExpanded ) {
        testDefinitionSteps.addStep( STR."Check the patient banner is \{isExpanded ? "" : "not"} expanded" );
        patientHeaderSteps.isPatientBannerExpanded( isExpanded );
        testDefinitionSteps.logEvidence( STR."the patient banner \{isExpanded ? "" : "not"} expanded",
                STR."the patient banner is \{isExpanded ? "" : "not"} expanded successfully", true );
    }

    @Then( "the patient banner $isCollapsed collapsed" )
    public void isPatientBannerCollapsed( Boolean isCollapsed ) {
        testDefinitionSteps.addStep( "Check the patient banner collapsed" );
        patientHeaderSteps.isPatientBannerExpanded( !isCollapsed );
        testDefinitionSteps.logEvidence( STR."the patient banner is \{isCollapsed ? "" : "not"} collapsed",
                STR."the patient banner is \{isCollapsed ? "" : "not"} collapsed successfully", true );
    }

    @Then( "the patient banner button label was changed to \"$buttonText\"" )
    public void checkPatientBannerButtonText( String buttonText ) {
        testDefinitionSteps.addStep( STR."Check patient banner button label was changed to \{buttonText}" );
        patientHeaderSteps.checkPatientBannerButtonText( buttonText );
        testDefinitionSteps.logEvidence( STR."the patient banner button label was changed to \{buttonText}",
                STR."Patient banner button text \{buttonText} is ok", true );
    }

    @When( "the user moves the mouse over on the value of the following patient banner item: $parameters" )
    public void moveMouseOnTheValueOfPatientBannerItem( List<DetailedDataItem> patientBannerItems ) {
        testDefinitionSteps.addStep( STR."Move the mouse over the value of the Patient Banner Item" );
        patientHeaderSteps.moveMouseToPatientBannerItem( patientBannerItems.getFirst() );
        testDefinitionSteps.logEvidence(
                STR."The user moved the mouse over of the following patient banner item: \{patientBannerItems.stream().map( DetailedDataItem::toString )
                                                                                                             .collect( Collectors.joining( "\n" ) )}",
                "The user successfully moved the mouse over for the patient banner item)", true );
    }

    @When( "the user moves the mouse over on the \"more\" link of the following patient banner item: $parameters" )
    public void moveMouseOnTheMoreLinkOfPatientBannerItem( List<DetailedDataItem> patientBannerItems ) {
        testDefinitionSteps.addStep( STR."Move the mouse over the value of the Patient Banner \"more\" link" );
        patientHeaderSteps.moveMouseToPatientBannerMoreLink( patientBannerItems.getFirst() );
        testDefinitionSteps.logEvidence(
                STR."The user moved the mouse over of the following patient banner \"more\" link: \{patientBannerItems.stream().map( DetailedDataItem::toString )
                                                                                                                      .collect( Collectors.joining( "\n" ) )}",
                "The user successfully moved the mouse over for the patient banner \"more\" link)", true );
    }

    @Given( "the Patient Banner $isDisplayed displayed" )
    @When( "the Patient Banner $isDisplayed displayed" )
    @Then( "the Patient Banner $isDisplayed displayed" )
    public void checkPatientBannerVisibility( Boolean isDisplayed ) {
        testDefinitionSteps.addStep( STR."Check that the patient banner is \{isDisplayed ? "" : "not"} displayed" );
        patientHeaderSteps.checkPatientBannerVisibility( isDisplayed );
        testDefinitionSteps.logEvidence(
                STR."the patient banner is \{isDisplayed ? "" : "not"} displayed",
                STR."the patient banner is \{isDisplayed ? "" : "not"} displayed", true );
    }

    @Then( "the Patient banner contains both the $patientName and $MRNValue together" )
    public void checkPatientBannerNameAndMRN( String patientName, String MRNValue ) {
        testDefinitionSteps.addStep( STR."Verify the patient banner contains the \"\{patientName}\" name and \"\{MRNValue}\" MRN" );
        patientHeaderSteps.checkSelectedPatientName( patientName );
        patientHeaderSteps.checkSpecificPatientBannerDetail( "MRN", MRNValue );
        testDefinitionSteps.logEvidence( STR."the Patient banner contains both the \{patientName} and \{MRNValue} together",
                STR."the Patient banner successfully contains both the \{patientName} and \{MRNValue} together", true );
    }

    @Then( "the patient name is displayed in the patient header: \"$patientName\"" )
    public void checkPatientNameInPatientHeader( String patientName ) {
        testDefinitionSteps.addStep( STR."Check that the patient name is \{patientName} in the patient header" );
        patientHeaderSteps.checkSelectedPatientName( patientName );
        patientHeaderSteps.checkPatientNameInPatientHeader( patientName );
        testDefinitionSteps.logEvidence(
                STR."the patient name is \{patientName} in the patient header",
                STR."the patient name is \{patientName} in the patient header", true );
    }

    @Then( "the banner tooltip displays the following text: \"$fullText\"" )
    public void checkBannerTooltip( String fullText ) {
        testDefinitionSteps.addStep( STR."Check that the banner tooltip is displayed with the following text: \"\{fullText}\"" );
        patientHeaderSteps.checkBannerTooltip( fullText );
        testDefinitionSteps.logEvidence(
                STR."the banner tooltip displays the following text: \"\{fullText}\"",
                STR."the banner tooltip displayed the following text: \"\{fullText}\"", true );
    }

    @Then( "the banner tooltip displays the full value of the following patient banner item: $patientBannerItem" )
    public void checkBannerTooltip( DetailedDataItem patientBannerItem ) {
        String itemText = patientBannerItem.toString();
        testDefinitionSteps.addStep( STR."the banner tooltip displays the full value of the following patient banner item: \{itemText}" );
        patientHeaderSteps.checkTooltipDisplaysFullValueOfTheBannerItem( patientBannerItem );
        testDefinitionSteps.logEvidence(
                STR."the banner tooltip displays the full value of the following patient banner item: \{itemText}",
                STR."the banner tooltip displayed the full value of the following patient banner item: \{itemText}", true
        );
    }

    @Given( "the patient banner loading skeleton $isVisible visible" )
    @Then( "the patient banner loading skeleton $isVisible visible" )
    public void checkBannerVisibility( Boolean isVisible ) {
        testDefinitionSteps.addStep( STR."Check patient banner visibility" );
        patientHeaderSteps.checkBannerLoadingSkeletonVisibility( isVisible );
        testDefinitionSteps.logEvidence(
                STR."the patient banner loading skeleton is\{isVisible ? " " : " not "}visible",
                STR."the patient banner loading skeleton was\{isVisible ? " " : " not "}visible", true );
    }

}
