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
package com.ge.onchron.verif.pages.sections;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.treatmentAndResponse.ImagingStudySelector;
import com.ge.onchron.verif.model.treatmentAndResponse.StudySelectorListElement;
import com.ge.onchron.verif.pages.SimplePage;
import com.ge.onchron.verif.pages.elements.tooltip.TooltipElement;
import com.ge.onchron.verif.pages.utils.PageElementUtils;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.pages.utils.PageUtils.currentlyContainsWebElements;


public class StudySelector extends SimplePage {

    private TooltipElement tooltip;

    private final String STUDY_LIST_ROW_NAME_SELECTOR = "[class*='ImagingStudySelector-module_study-item_nvnO4']";
    private final String STUDY_LIST_ROW_DATE_SELECTOR = "[class*='ImagingStudySelector-module_study-date_']";

    private final String STUDY_SELECTOR_MODULE_SELECTOR = "[class*='ImagingStudySelector-module_selector-']";
    private final String STUDY_SELECTOR_MODULE_CONTAINER = "[class*='ImagingStudySelector-module_container_']";
    private final String STUDY_SELECTOR_DROPDOWN_SELECTOR = "[class*='select__dropdown-panel']";
    private final String STUDY_SELECTOR_DROPDOWN_ITEMS_SELECTOR = "[class*='select__dropdown-panel-items']";
    private final String STUDY_SEARCH_SELECTOR = "div[class*='select__panel-header-input']";
    private final String IMAGING_STUDY_SELECTOR_MODULE_NO_PRINT_SELECTOR = "[class*='select ImagingStudySelector-module_no-print_']";
    private final String RESET_ICON_SELECTOR = "[class*='ImagingStudySelector-module_reset_']";
    private final String SWAP_ICON_SELECTOR = "[class*='ImagingStudySelector-module_swap_']";
    private final String NO_MATCH_SELECTOR = "[class*='ImagingStudySelector-module_no-match_']";
    private final String REVERSE_ORDER_ALERT_SELECTOR = "[class*='ImagingStudySelector-module_reversed-alert_']";


    @FindBy( css = STUDY_SELECTOR_MODULE_SELECTOR )
    private List<WebElementFacade> studySelectors;

    @FindBy( css = STUDY_SELECTOR_MODULE_CONTAINER )
    private WebElementFacade studySelectorContainer;

    @FindBy( css = RESET_ICON_SELECTOR )
    private WebElementFacade resetIcon;

    @FindBy( css = SWAP_ICON_SELECTOR )
    private WebElementFacade swapIcon;

    @FindBy( css = STUDY_SEARCH_SELECTOR )
    private WebElementFacade searchField;

    @FindBy( css = NO_MATCH_SELECTOR )
    private WebElementFacade noMatchElement;

    public List<StudySelectorListElement> getStudySelectorListElements() {
        List<StudySelectorListElement> studyList = new ArrayList<>();
        List<WebElementFacade> selectorListElements = find( By.cssSelector( STUDY_SELECTOR_DROPDOWN_ITEMS_SELECTOR ) ).thenFindAll( By.cssSelector( "div[role='option']" ) );

        selectorListElements.forEach( option -> {
            StudySelectorListElement listElement = new StudySelectorListElement();
            listElement.setDate( option.find( By.cssSelector( STUDY_LIST_ROW_DATE_SELECTOR ) ).getText() );
            WebElementFacade nameElement = option.find( By.cssSelector( STUDY_LIST_ROW_NAME_SELECTOR ) );

            String optionClass = option.getAttribute( "class" );
            if ( optionClass.contains( "--selected" ) ) {
                listElement.setSelected( true );
            }
            if ( optionClass.contains( "--disabled" ) ) {
                listElement.setDisabled( true );
            }
            listElement.setName( nameElement.getText() );
            listElement.setWebElement( option );
            studyList.add( listElement );
        } );
        return studyList;
    }

    public void selectListElement( StudySelectorListElement study ) {
        try {
            elementClicker( study.getWebElement() );
        } catch ( NoSuchElementException e ) {
            fail( String.format( "Could not select the %s\n%s", study, e ) );
        }
    }

    public List<ImagingStudySelector> getStudySelectors() {
        List<ImagingStudySelector> studySelectorModuleList = new ArrayList<>();
        studySelectors.forEach( selector -> {
            ImagingStudySelector studySelectorModule = new ImagingStudySelector();
            WebElementFacade badgeElement = selector.then( By.cssSelector( "[class*='StudyTypeBadge-module_badge_']" ) );
            studySelectorModule.setBadge( badgeElement.getText() );
            studySelectorModule.setBadgeFaded( badgeElement.getAttribute( "class" ).contains( "-module_faded" ) );

            WebElementFacade studyRow = selector.find( By.cssSelector( "[class*='ImagingStudySelector-module_study-row-print-only']" ) ); // When studyRow element has no child elements then there is no study was selected
            if ( studyRow.findElements( By.xpath( ".//*" ) ).isEmpty() ) {
                studySelectorModule.setSelectedStudyName( "" );
                studySelectorModule.setSelectedStudyDate( "" );
                studySelectorModule.setEmpty( true );
            } else {
                studySelectorModule.setSelectedStudyName( selector.then( By.cssSelector( "[class*='ImagingStudySelector-module_study-text_']" ) ).getText() );
                studySelectorModule.setSelectedStudyDate( selector.then( By.cssSelector( "[class*='ImagingStudySelector-module_study-date_']" ) ).getText() );
            }
            studySelectorModule.setModuleElement( selector.then( By.cssSelector( "[class*='ImagingStudySelector-module_select-container_']" ) ) );
            studySelectorModule.setOpen( currentlyContainsWebElements( selector, By.cssSelector( "[class*='select__dropdown-panel--open']" ) ) );
            studySelectorModule.setDisabled( selector.then( By.cssSelector( IMAGING_STUDY_SELECTOR_MODULE_NO_PRINT_SELECTOR ) ).getAttribute( "class" ).contains( "--disabled" ) );
            studySelectorModuleList.add( studySelectorModule );
        } );
        return studySelectorModuleList;
    }

    public ImagingStudySelector getStudySelectorByBadge( String expectedBadge ) {
        return getStudySelectors().stream()
                                  .filter( studySelectorModul -> studySelectorModul.getBadge().equals( expectedBadge ) )
                                  .findFirst().orElseThrow( () -> new RuntimeException( STR."There is no imaging study selector for: \{expectedBadge}" ) );
    }

    public void waitForStudySelectorsToBeEnabled() {
        Wait<WebDriver> wait = new FluentWait<>( getDriver() )
                .withTimeout( Duration.ofSeconds( 5 ) )
                .pollingEvery( Duration.ofMillis( 100 ) )
                .ignoring( java.util.NoSuchElementException.class );
        try {
            wait.until( ExpectedConditions.not( ExpectedConditions.attributeContains( By.cssSelector( IMAGING_STUDY_SELECTOR_MODULE_NO_PRINT_SELECTOR ), "class", "select--disabled" ) ) );
        } catch ( Exception e ) {
            fail( "Study selectors remained disabled after waiting 5 seconds" );
        }
    }

    public boolean isVerticalScrollbarDisplayedForDropdown( WebElementFacade openStudySelectorElement ) {
        WebElementFacade studySelectorDropdownElement = openStudySelectorElement.then( By.xpath( ".." ) ).then( By.cssSelector( STUDY_SELECTOR_DROPDOWN_ITEMS_SELECTOR ) );
        return PageElementUtils.hasVerticalScrollbar( studySelectorDropdownElement );
    }

    public boolean swapIsDisabled() {
        return swapIcon.getAttribute( "class" ).contains( "module_disabled_" );
    }

    public boolean resetIsDisabled() {
        return resetIcon.getAttribute( "class" ).contains( "module_disabled_" );
    }

    public void typeIntoSearchField( String searchText ) {
        WebElementFacade searchInputElement = find( By.cssSelector( STUDY_SEARCH_SELECTOR ) ).then( By.cssSelector( "input" ) );
        searchInputElement.waitUntilEnabled().type( searchText );
    }

    public void clickXOnSearchField() {
        try {
            elementClicker( searchField.then( By.cssSelector( "[class*='input-icon--clear']" ) ) );
        } catch ( NoSuchElementException e ) {
            fail( "Could not found 'X' icon on the search field" );
        }
    }

    public String getTooltipText() {
        return tooltip.getTooltipFullText();
    }

    public void clickSwap() {
        elementClicker( swapIcon );
    }

    public void clickReset() {
        elementClicker( resetIcon );
    }

    public String getNoMatchText() {
        return noMatchElement.getText();
    }

    public int getNumberOfItemsInDropdown() {
        return find( By.cssSelector( STUDY_SELECTOR_DROPDOWN_ITEMS_SELECTOR ) ).thenFindAll( By.xpath( "*" ) ).size();
    }

    public String getReverseOrderText() {
        try {
            return studySelectorContainer.then( By.cssSelector( REVERSE_ORDER_ALERT_SELECTOR ) ).getTextContent();
        } catch ( NoSuchElementException e ) {
            return "";
        }
    }
}
