/*
 * -GEHC CONFIDENTIAL-
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
package com.ge.onchron.verif.whatsteps.tabs.summary;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ge.onchron.verif.howsteps.SummarySteps;
import com.ge.onchron.verif.howsteps.SummaryToolbarSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.model.Badge;
import com.ge.onchron.verif.model.StringList;
import net.thucydides.core.annotations.Steps;


public class SummaryToolbar {

    @Steps
    private SummaryToolbarSteps summaryToolbarSteps;

    @Steps
    private SummarySteps summarySteps;

    private static final Logger LOGGER = LoggerFactory.getLogger( SummaryToolbar.class );
    private TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();


    @When( "the user clicks the \"More\" button on the Navigation toolbar" )
    public void openMoreMenuOnNavToolbar() {
        testDefinitionSteps.addStep( "Click on More button on the Navigation toolbar" );
        summaryToolbarSteps.clickMoreButtonOnNavigationToolbar();
        testDefinitionSteps.logEvidence( "The user clicks on the \"More\" button on the Navigation toolbar", "Successfully clicked on the \"More\" button on the Navigation toolbar", true );
    }

    @When( "the user clicks on \"$section\" link on the navigation toolbar" )
    public void clickOnSectionLink( String section ) {
        testDefinitionSteps.addStep( STR."Click on \{section} on the Navigation toolbar" );
        summaryToolbarSteps.clickSectionLinkOnToolbar( section );
        testDefinitionSteps.logEvidence( STR."The user clicks on \{section} link on the navigation toolbar", STR."Successfully clicked on the \{section} link on the navigation toolbar", true );
    }

    @When( "the user $isSelected the \"Hide empty cards\" checkbox in View options" )
    public void hideEmptyCards( Boolean isSelected ) {
        testDefinitionSteps.addStep( STR."\{isSelected ? "Selects" : "Unselects"} \"Hide empty cards\" checkbox in View options" );
        summaryToolbarSteps.setHideEmptyCardsVisibility( isSelected );
        testDefinitionSteps.logEvidence( STR."The user \{isSelected ? "selects" : "unselects"}\"Hide empty cards\" checkbox in View options", "Successfully selected \"Hide empty cards\" checkbox in View options", true );
    }

    @Given( "the \"Hide empty cards\" checkbox $wasOrWasNot selected in the View options" )
    @Then( "the \"Hide empty cards\" checkbox $isOrIsNot selected in the View options" )
    public void checkHideEmptyCardsCheckboxState( Boolean isChecked ) {
        testDefinitionSteps.addStep( "Checking 'Hide empty cards' checkbox button state" );
        summaryToolbarSteps.checkHideEmptyCardsCheckbox( isChecked );
        testDefinitionSteps.logEvidence( STR."The 'Hide empty cards' checkbox \{isChecked ? "is" : "is not"} checked as expected",
                STR."The 'Hide empty cards' checkbox \{isChecked ? "is" : " is not"} checked (check previous logs)", true );
    }

    @When( "the user clicks on the \"$optionName\" option in the View options" )
    public void clickItemInViewOptions( String optionName ) {
        testDefinitionSteps.addStep( STR."the user clicks on the \"\{optionName}\" option in the View options" );
        summaryToolbarSteps.clickItemInViewOptions( optionName );
        testDefinitionSteps.logEvidence(
                STR."the user clicked on the \"\{optionName}\" option in the View options",
                STR."the user clicked on the \"\{optionName}\" option in the View options",
                true );
    }

    @When( "the user clicks on the \"$optionName\" option in the sub-menu in the View options" )
    public void clickItemInViewOptionsSubMenu( String optionName ) {
        testDefinitionSteps.addStep( String.format( "the user clicks on the \"%s\" option in the sub-menu in the View options", optionName ) );
        summaryToolbarSteps.clickItemInViewOptionsSubMenu( optionName );
        testDefinitionSteps.logEvidence(
                String.format( "the user clicked on the \"%s\" option in the sub-menu in the View options", optionName ),
                String.format( "the user clicked on the \"%s\" option in the sub-menu in the View options", optionName ),
                true );
    }

    @Given( "the navigation toolbar is displayed" )
    @Then( "the navigation toolbar is displayed" )
    public void checkNavigationToolbarIsDisplayed() {
        testDefinitionSteps.addStep( "Checking navigation toolbar is displayed" );
        summaryToolbarSteps.checkNavigationToolbarIsDisplayed();
        testDefinitionSteps.logEvidence( "the navigation toolbar is displayed", "the navigation toolbar is displayed", true );
    }

    @Then( "the \"More\" menu is $isOpened" )
    public void checkMoreMenuVisibility( Boolean isOpened ) {
        testDefinitionSteps.addStep( "Checking navigation toolbar visibility" );
        summaryToolbarSteps.checkMoreMenuVisibility( isOpened );
        testDefinitionSteps.logEvidence( STR."the More menu \{isOpened ? "" : "not"} opened", STR."the More menu \{isOpened ? "" : "not"} opened", true );
    }

    @Given( "the navigation toolbar contains links to the following sections: $navigationLinks" )
    @Then( "the navigation toolbar contains links to the following sections: $navigationLinks" )
    public void checkNavigationToolbarLinks( StringList navigationLinks ) {
        testDefinitionSteps.addStep( "Checking navigation toolbar contains links to the following sections" );
        summaryToolbarSteps.checkNavigationToolbarLinks( navigationLinks.getList() );
        testDefinitionSteps.logEvidence( STR."The navigation toolbar contains links to the following sections: \{navigationLinks.getList()}",
                "The navigation toolbar contains links to the following sections (check previous logs)", true );
    }

    @Then( "the navigation toolbar does not contain any section link" )
    public void checkNoLinksAreOnTheNavigationToolbar() {
        testDefinitionSteps.addStep( "Checking navigation toolbar does not contain any section link" );
        summaryToolbarSteps.checkNoLinksAreOnTheNavigationToolbar();
        testDefinitionSteps.logEvidence( "The navigation toolbar does not contain any section link", "The navigation toolbar does not contain any section link", true );
    }

    @Then( "\"$section\" section is in the viewport" )
    public void checkSummarySectionIsInTheViewport( String section ) {
        testDefinitionSteps.addStep( STR."Checking that \{section} section is in the viewport" );
        summarySteps.checkSummarySectionIsInTheViewport( section );
        testDefinitionSteps.logEvidence( STR."\{section} section is in the viewport", STR."\{section} section is in the viewport", true );
    }

    @Then( "the URL contains the \"$urlTag\" tag" )
    public void checkURLContainsSection( String urlTag ) {
        testDefinitionSteps.addStep( STR."Checking the URL contains the \{urlTag}" );
        summaryToolbarSteps.checkURLContainsSection( urlTag );
        testDefinitionSteps.logEvidence( STR."The URL contains the \{urlTag}", STR."The \{urlTag} is in the URL", true );
    }

    @Given( "the following badge is displayed on the Navigation toolbar: $toolbarBadge" )
    @Then( "the following badge is displayed on the Navigation toolbar: $toolbarBadge" )
    public void checkBadgeOnNavigationToolbar( Badge badge ) {
        testDefinitionSteps.addStep( STR."Checking that the following badge \{badge.getText()} is displayed on the Navigation toolbar" );
        summaryToolbarSteps.checkBadgeOnNavigationToolbar( badge );
        testDefinitionSteps.logEvidence( STR."The following badge is displayed on the Navigation toolbar: \{badge}", STR."The following badge is displayed on the Navigation toolbar: \{badge}", true );
    }

    @Given( "no badge is displayed on the Navigation toolbar" )
    @Then( "no badge is displayed on the Navigation toolbar" )
    public void checkNoBadgeIsDisplayedOnTheNavigationToolbar() {
        testDefinitionSteps.addStep( STR."Checking that no badge is displayed on the Navigation toolbar" );
        summaryToolbarSteps.checkNoBadgeIsDisplayedOnNavigationToolbar();
        testDefinitionSteps.logEvidence( "no badge is displayed on the Navigation toolbar", "no badge is displayed on the Navigation toolbar", true );

    }


}
