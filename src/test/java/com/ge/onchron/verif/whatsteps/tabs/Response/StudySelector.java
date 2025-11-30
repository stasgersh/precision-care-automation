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
package com.ge.onchron.verif.whatsteps.tabs.Response;

import java.util.List;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.ge.onchron.verif.howsteps.StudySelectorSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.model.treatmentAndResponse.StudySelectorListElement;
import net.thucydides.core.annotations.Steps;


public class StudySelector {

    @Steps
    private StudySelectorSteps studySelectorSteps;

    private TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();


    @Given( "the following imaging study was selected for study $badgeText: $studySelectorListElement" )
    @When( "the user selects the following imaging study for study $badgeText: $studySectorListElement" )
    public void selectImagingStudy( String badgeText, StudySelectorListElement studySelectorListElement ) {
        testDefinitionSteps.addStep( STR."Selecting imaging study for \{badgeText}" );
        studySelectorSteps.selectImagingStudyFor( studySelectorListElement, badgeText );
        testDefinitionSteps.logEvidence( STR."the following imaging study was selected for study \{badgeText}: \{studySelectorListElement}",
                "The imaging study was selected for study successfully", true );
    }

    @When( "the user $isOpen the imaging study selector for $badgeText" )
    public void openImagingStudySelector( Boolean isOpen, String badgeText ) {
        testDefinitionSteps.addStep( STR."Opening imaging study selector for \{badgeText}" );
        studySelectorSteps.openOrCloseStudySelectorOfAorB( isOpen, badgeText );
        testDefinitionSteps.logEvidence( STR."the imaging study selector of \{badgeText} was opened", STR."the imaging study selector of \{badgeText} was opened successfully", true );
    }

    @When( "the user types \"$searchText\" into the Search field of the open imaging study selector" )
    public void typeIntoImagingStudySearch( String searchText ) {
        testDefinitionSteps.addStep( STR."Typing into the search field of the open imaging study selector" );
        studySelectorSteps.typeIntoImagingStudySearch( searchText );
        testDefinitionSteps.logEvidence( STR."the user types \{searchText} into the Search field of the open imaging study selector", STR."the user typed \{searchText} into the Search field of the open imaging study selector", true );
    }

    @When( "the user clicks the following imaging study in the open list: $studySelectorListElement" )
    public void clickImagingStudyInList( StudySelectorListElement studySelectorListElement ) {
        testDefinitionSteps.addStep( STR."Clicking imaging study \{studySelectorListElement.getName()} with date: \{studySelectorListElement.getDate()}" );
        studySelectorSteps.clickImagingStudyInList( studySelectorListElement );
        testDefinitionSteps.logEvidence( STR."the user clicks the following imaging study in the list:\{studySelectorListElement.toString()}",
                "STR.\"the user clicks the following imaging study in the list:\\{studySelectorListElement.toString()}", true );
    }

    @When( "the user cancels the search by clicking 'X' icon on the Search field" )
    public void clickXIconOnStudySelectorSearch() {
        testDefinitionSteps.addStep( STR."Clicking X icon to cancel the Search" );
        studySelectorSteps.clickXIconOnStudySelectorSearch();
        testDefinitionSteps.logEvidence( STR."the user cancels the search by clicking 'X' icon on the Search field", STR."the user canceled the search by clicking 'X' icon on the Search field", true );
    }

    @When( "the user hovers on the following study in the open list: $studySectorListElement" )
    public void hoverOnStudyInStudySelector( StudySelectorListElement studySelectorListElement ) {
        testDefinitionSteps.addStep( STR."Hovering on \{studySelectorListElement.getName()} imaging study with date \{studySelectorListElement.getDate()} in the study selector" );
        studySelectorSteps.hoverOnStudySelectorElement( studySelectorListElement );
        testDefinitionSteps.logEvidence( STR."the user hovers on the following study in the list: \{studySelectorListElement.getName()}, \{studySelectorListElement.getDate()}", STR."the user hovers on the following study in the list: \{studySelectorListElement.getName()}, \{studySelectorListElement.getDate()}", true );
    }

    @When( "the user clicks on the $button button on the Study selector module" )
    public void clickButtonOnStudySelectorModule( String button ) {
        testDefinitionSteps.addStep( STR."Clicking on \{button}" );
        studySelectorSteps.clickButtonOnStudySelectorModule( button );
        testDefinitionSteps.logEvidence(
                STR."The user clicks on the \{button} button",
                STR."The user clicked on the \{button} button",
                true );
    }

    @Then( "the open dropdown list displays the imaging studies in the following order: $studySectorListElement" )
    public void checkAvailableStudiesOfSelectorInOrder( List<StudySelectorListElement> studySelectorList ) {
        testDefinitionSteps.addStep( STR."Checking available studies in open study selector dropdown list" );
        studySelectorSteps.checkAvailableStudiesIsInOrder( studySelectorList );
        testDefinitionSteps.logEvidence( STR."the open dropdown list displays the imaging studies in the following order: \{studySelectorList}", STR."the dropdown list of $badgeText displayed the imaging studies in the following order: \{studySelectorList}", true );
    }

    @Then( "$int studies are available in the open dropdown list" )
    public void checkNumberOfAvailableStudiesInDropdown( int studyNumber ) {
        testDefinitionSteps.addStep( STR."Checking the number of available studies in open study selector dropdown list" );
        studySelectorSteps.checkNumberOfAvailableStudies( studyNumber );
        testDefinitionSteps.logEvidence( STR."\{studyNumber} studies are available in the open dropdown list", STR."\{studyNumber} studies are available in the open dropdown list", true );
    }

    @Then( "the open dropdown list $isScrollable scrollable" )
    public void checkDropdownIsScrollable( Boolean isScrollable ) {
        testDefinitionSteps.addStep( STR."Checking the study selector \{isScrollable ? " is" : " is not"} scrollable" );
        studySelectorSteps.checkDropdownIsScrollable( isScrollable );
        testDefinitionSteps.logEvidence( STR."the dropdown list \{isScrollable ? " is" : " is not"} scrollable", STR."the dropdown list \{isScrollable ? " is" : " is not"} scrollable", true );
    }

    @Then( "the imaging study selector of $badgeText is disabled" )
    public void checkImagingStudyDisabled( String badgeText ) {
        testDefinitionSteps.addStep( STR."Checking that the imaging study selector of \{badgeText} is disabled" );
        studySelectorSteps.checkStudySelectorIsDisabled( badgeText );
        testDefinitionSteps.logEvidence( STR."the imaging study selector of \{badgeText} is disabled", STR."the imaging study selector of \{badgeText} is disabled", true );
    }

    @Then( "the $button button $isOrIsNot disabled on Response view" )
    public void checkButtonState( String button, Boolean isDisabled ) {
        testDefinitionSteps.addStep( STR."Checking \{button} \{isDisabled ? " is" : " is not"} disabled" );
        studySelectorSteps.checkButtonState( button, isDisabled );
        testDefinitionSteps.logEvidence( STR."the \{button} button \{isDisabled ? " is" : " is not"} disabled on Response view", STR."the \{button} button \{isDisabled ? " is" : " is not"} disabled on Response view", true );
    }

    @Then( "the following imaging study is displayed for $badgeText: $studySelectorListElement" )
    public void checkSelectedImagingStudy( String badgeText, StudySelectorListElement studySelectorListElement ) {
        testDefinitionSteps.addStep( STR."Checking that study was selected for \{badgeText}: \{studySelectorListElement.toString()}" );
        studySelectorSteps.checkSelectedStudyFor( badgeText, studySelectorListElement );
        testDefinitionSteps.logEvidence( STR."the following imaging study is displayed for \{badgeText}: \{studySelectorListElement.toString()}", STR."the following imaging study is displayed for \{badgeText}: \{studySelectorListElement.toString()}", true );
    }

    @Given( "no imaging study was selected for study $badgeText" )
    @Then( "no imaging study is selected for study $badgeText" )
    public void checkNoStudyIsSelectedFor( String badgeText ) {
        testDefinitionSteps.addStep( STR."Checking that no study was selected for \{badgeText}" );
        studySelectorSteps.checkNoStudyIsSelectedFor( badgeText );
        testDefinitionSteps.logEvidence( STR."no imaging study is selected for study \{badgeText}:", STR."no imaging study is selected for study \{badgeText}", true );
    }

    @Then( "a tooltip is displayed with the full study name as: \"$studyName\"" )
    public void checkFullStudyNameInPopover( String studyName ) {
        testDefinitionSteps.addStep( STR."Checking that the full study name is displayed in a popover" );
        studySelectorSteps.checkFullStudyNameInPopover( studyName );
        testDefinitionSteps.logEvidence( STR."a popover is displayed with the full study name as: \{studyName}", STR."a popover is displayed with the full study name as: \{studyName}", true );
    }

    @Then( "the open dropdown displays \"$noResultText\"" )
    public void checkNoResultFoundBySearch( String noResultText ) {
        testDefinitionSteps.addStep( STR."Checking \{noResultText} in the open dropdown" );
        studySelectorSteps.checkNoResultFoundTextOnImagingStudyDropdown( noResultText );
        testDefinitionSteps.logEvidence(
                STR."The open dropdown displays \{noResultText}",
                STR."The open dropdown displayed \{noResultText}",
                true );
    }

    @Then( "the following alert is displayed under the imaging study selectors: \"$alertText\"" )
    public void checkReverseOrderAlert( String alertText ) {
        testDefinitionSteps.addStep( STR."Checking \{alertText} is displayed under imaging study selectors" );
        studySelectorSteps.checkReverseStudyOrderAlertText( alertText );
        testDefinitionSteps.logEvidence(
                STR."the following alert is displayed under the imaging study selectors: \{alertText}",
                STR."the following alert is displayed under the imaging study selectors: \{alertText}",
                true );
    }

}
