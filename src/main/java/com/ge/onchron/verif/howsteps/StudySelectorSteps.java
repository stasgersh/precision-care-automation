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
package com.ge.onchron.verif.howsteps;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.treatmentAndResponse.ImagingStudySelector;
import com.ge.onchron.verif.model.treatmentAndResponse.StudySelectorListElement;
import com.ge.onchron.verif.pages.sections.StudySelector;
import com.ge.onchron.verif.pages.utils.PageElementUtils;

import static com.ge.onchron.verif.utils.Utils.waitMillis;

public class StudySelectorSteps {

    private StudySelector studySelector;
    private static final Logger LOGGER = LoggerFactory.getLogger( SummarySteps.class );


    public void selectImagingStudyFor( StudySelectorListElement expectedStudy, String badgeText ) {
        ImagingStudySelector imagingStudySelector = studySelector.getStudySelectorByBadge( badgeText );
        if ( imagingStudySelector.isEmpty()
                || !imagingStudySelector.getSelectedStudyName().equals( expectedStudy.getName() )
                || !imagingStudySelector.getSelectedStudyDate().equals( expectedStudy.getDate() ) ) {
            openOrCloseStudySelectorOfAorB( true, imagingStudySelector );
            StudySelectorListElement targetStudy = getMatchingStudyInList( expectedStudy );
            if ( !targetStudy.isSelected() ) {
                studySelector.selectListElement( targetStudy );
            } else {
                openOrCloseStudySelectorOfAorB( false, imagingStudySelector );
            }
        }
        LOGGER.info( STR."\{expectedStudy.getName()} with date \{expectedStudy.getDate()} was already selected for imaging study \{badgeText}" );
    }

    public void clickImagingStudyInList( StudySelectorListElement expectedStudy ) {
        StudySelectorListElement targetStudy = getMatchingStudyInList( expectedStudy );
        studySelector.selectListElement( targetStudy );
    }

    private StudySelectorListElement getMatchingStudyInList( StudySelectorListElement expectedStudy ) {
        List<StudySelectorListElement> observedListElements = studySelector.getStudySelectorListElements();
        LOGGER.info( STR."Observed list of study elements\n: \{observedListElements.stream().map( StudySelectorListElement::getName ).toList()}" );
        StudySelectorListElement targetStudy = observedListElements.stream()
                                                                   .filter( observedElement -> observedElement.getName().equals( expectedStudy.getName() ) && observedElement.getDate().equals( expectedStudy.getDate() ) )
                                                                   .findFirst()
                                                                   .orElseThrow( () -> new RuntimeException( STR."Expected imaging study \"\{expectedStudy.getName()}\" with date \{expectedStudy.getDate()} was not found in the list" ) );
        LOGGER.info( STR."Target study found: \{targetStudy}" );
        return targetStudy;
    }

    public void openOrCloseStudySelectorOfAorB( boolean expectedStudySelectorState, String badgeText ) {
        studySelector.waitForStudySelectorsToBeEnabled();
        ImagingStudySelector studySelectorModule = studySelector.getStudySelectorByBadge( badgeText );
        openOrCloseStudySelector( expectedStudySelectorState, studySelectorModule );
    }

    public void openOrCloseStudySelectorOfAorB( boolean expectedStudySelectorState, ImagingStudySelector studySelectorModule ) {
        openOrCloseStudySelector( expectedStudySelectorState, studySelectorModule );
    }

    private void openOrCloseStudySelector( boolean expectedStudySelectorState, ImagingStudySelector studySelectorModule ) {
        if ( studySelectorModule.isOpen() != expectedStudySelectorState ) {
            waitMillis( 5000 );
            studySelector.elementClicker( studySelectorModule.getModuleElement() );
            LOGGER.info( STR."\{expectedStudySelectorState ? "opened" : "closed"} study selector of \{studySelectorModule.getBadge()}" );
        } else {
            LOGGER.info( STR."Study selector of \{studySelectorModule.getBadge()} is already \{expectedStudySelectorState ? "open" : "closed"}" );
        }
    }

    public void checkAvailableStudiesIsInOrder( List<StudySelectorListElement> expectedListElements ) {
        List<StudySelectorListElement> observedListElements = studySelector.getStudySelectorListElements();
        LOGGER.info( STR."Observed list of study elements in study\n: \{observedListElements.stream().map( StudySelectorListElement::getName ).toList()}" );
        assertEquals( "Available studies in dropdown list does not match expected", expectedListElements, observedListElements );
    }

    public void checkDropdownIsScrollable( boolean isScrollable ) {
        assertEquals( STR."Dropdown list scrollbar \{isScrollable ? "is not " : "is"} displayed", isScrollable, studySelector.isVerticalScrollbarDisplayedForDropdown( getOpenImagingStudySelector().getModuleElement() ) );
    }

    private ImagingStudySelector getOpenImagingStudySelector() {
        return studySelector.getStudySelectors()
                            .stream()
                            .filter( ImagingStudySelector::isOpen )
                            .findFirst()
                            .orElseThrow( () -> new RuntimeException( "Could not found an open imaging study selector" ) );
    }

    public void checkNumberOfAvailableStudies( int expectedNumberOfStudies ) {
        int actualNumberOfStudies = studySelector.getStudySelectorListElements().size();
        LOGGER.info( STR."Found \{actualNumberOfStudies} available studies in the open dropdown list" );
        assertEquals( "Number of available studies in the open dropdown is not expected", expectedNumberOfStudies, actualNumberOfStudies );
    }

    public void checkStudySelectorIsDisabled( String badgeText ) {
        assertTrue( STR."Study selector of \{badgeText} is not disabled", studySelector.getStudySelectorByBadge( badgeText ).isDisabled() );
    }

    public void checkButtonState( String button, boolean expectedButtonState ) {
        boolean actualButtonState = false;
        switch ( button ) {
            case "Swap":
                actualButtonState = studySelector.swapIsDisabled();
                break;
            case "Reset":
                actualButtonState = studySelector.resetIsDisabled();
                break;
            default:
                fail( STR."Could not found \{button}on Response view" );
        }
        LOGGER.info( STR."Actual state of \{button} button is \{actualButtonState}" );
        assertEquals( STR."Button state of \{button} was not expected", expectedButtonState, actualButtonState );
    }

    public void typeIntoImagingStudySearch( String searchText ) {
        studySelector.typeIntoSearchField( searchText );
    }

    public void checkSelectedStudyFor( String badgeText, StudySelectorListElement expectedStudy ) {
        ImagingStudySelector imagingStudySelector = studySelector.getStudySelectorByBadge( badgeText );
        assertEquals( "The selected study's name does not match the expected", expectedStudy.getName(), imagingStudySelector.getSelectedStudyName() );
        assertEquals( "The selected study's date does not match the expected", expectedStudy.getDate(), imagingStudySelector.getSelectedStudyDate() );
    }

    public void clickXIconOnStudySelectorSearch() {
        studySelector.clickXOnSearchField();
    }

    public void hoverOnStudySelectorElement( StudySelectorListElement studySelectorListElement ) {
        StudySelectorListElement targetStudy = getMatchingStudyInList( studySelectorListElement );
        PageElementUtils.moveToElement( targetStudy.getWebElement() );
    }

    public void checkFullStudyNameInPopover( String expectedFullStudyName ) {
        String actualFullStudyName = studySelector.getTooltipText();
        LOGGER.info( STR."Actual full study name: \{actualFullStudyName}" );
        assertEquals( "Full study name does not match expected", expectedFullStudyName, actualFullStudyName );
    }

    public void checkNoStudyIsSelectedFor( String badgeText ) {
        ImagingStudySelector actualStudySelector = studySelector.getStudySelectorByBadge( badgeText );
        assertTrue( STR."Study selector for \{badgeText} is not empty and has the following study selected: \{actualStudySelector.getSelectedStudyName()} with date \{actualStudySelector.getSelectedStudyDate()}", actualStudySelector.isEmpty() );
    }

    public void clickButtonOnStudySelectorModule( String button ) {
        switch ( button.toLowerCase() ) {
            case "swap":
                studySelector.clickSwap();
                break;
            case "reset":
                studySelector.clickReset();
                break;
            default:
                fail( STR."Could not found \{button} on Response view" );
        }
    }

    public void checkNoResultFoundTextOnImagingStudyDropdown( String expectedNoMatchText ) {
        String actualNoMatchText = studySelector.getNoMatchText();
        int numberOfVisibleItemsInDropdown = studySelector.getNumberOfItemsInDropdown();
        LOGGER.info( STR."Found items in the open imaging study selector dropdown: \{numberOfVisibleItemsInDropdown}" );
        LOGGER.info( STR."Actual no match text displayed: \{actualNoMatchText}" );
        assertEquals( "Found more items in the open imaging study selector dropdown than expected", 1, numberOfVisibleItemsInDropdown );
        assertEquals( "\'No result\' text does not match expected", expectedNoMatchText, actualNoMatchText );
    }

    public void checkReverseStudyOrderAlertText( String alertText ) {
        String actualAlert = studySelector.getReverseOrderText();
        LOGGER.info( STR."Actual reverse order text displayed: \{actualAlert}" );
        assertEquals( "Reverse order text is not correct", alertText, actualAlert );

    }
}
