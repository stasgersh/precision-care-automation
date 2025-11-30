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
package com.ge.onchron.verif.pages.sections.modal;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.fail;

import com.ge.onchron.verif.pages.utils.PageUtils;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

public class ViewModeModal extends BaseModal {

    private final String MODAL_CLASSNAME = "modal";
    private final String RADIOBUTTON_SELECTOR = "//label[input[@class='radio__input' and @value='VIEW_MODE']]";
    private final String VIEW_MODE_MODAL_SELECTOR = "//div[contains(@class, 'modal modal') and .//*[contains(text(), 'View mode')]]";
    private final String FIELD_SELECTOR_GENERIC = "//div[@class='u-pt--' and .//span[text()='FIELD_NAME']]";
    private final String DATE_PICKER_INPUT_SELECTOR = "//input[contains(@class, 'date-picker')]";
    private final String OPEN_DROPDOWN_SELECTOR = "div[class*='select__dropdown-panel--open']";
    private final String ERROR_MESSAGE_SELECTOR = "div[class*='-module_errorMsg_']";
    private final String SELECTED_RADIO_BUTTON_CSS = "input[type='radio'][name='radio_group']:checked";
    private final String SELECTED_RADIO_BUTTON_SIBLING_LABEL_CSS = "following-sibling::span[@class='radio__label']";

    @FindBy( className = MODAL_CLASSNAME )
    private WebElementFacade modal;

    @FindBy( xpath = "//*[contains(@class,'Modal-module_modal-header')]" )
    private WebElementFacade header;

    @FindBy( xpath = "//*[contains(@class,'modal__content')]" )
    private WebElementFacade content;

    @FindBy( xpath = VIEW_MODE_MODAL_SELECTOR )
    private WebElementFacade viewModeModal;


    @Override
    protected By getLocator() {
        return By.className( MODAL_CLASSNAME );
    }

    public String getHeaderText() {
        return header.getText();
    }

    public String getContent() {
        return content.getText();
    }

    public void clickButtonOnViewMode( String buttonText ) {
        WebElementFacade button = modal.thenFindAll( By.tagName( "button" ) ).stream()
                                       .filter( btn -> btn.getText().equals( buttonText ) )
                                       .findFirst()
                                       .orElseThrow( () -> new RuntimeException( STR."The following button was not found on the View mode modal: \{buttonText}" ) );
        elementClicker( button );
    }

    public void clickOnRadio( String radioText ) {
        String actualRadioSelector = RADIOBUTTON_SELECTOR.replace( "VIEW_MODE", radioText );
        WebElementFacade radioElement = modal.find( By.xpath( actualRadioSelector ) );
        elementClicker( radioElement );
    }

    private WebElementFacade getFieldElementByName( String fieldName ) {
        return modal.find( By.xpath( FIELD_SELECTOR_GENERIC.replace( "FIELD_NAME", fieldName ) ) );
    }

    public void openDropdownOfFieldOnViewModeModal( String field ) {
        WebElementFacade fieldElement = getFieldElementByName( field );
        WebElementFacade dropdownSelector = fieldElement.then( By.cssSelector( ".select" ) );
        elementClicker( dropdownSelector );
    }

    private List<WebElementFacade> getOptionsElementsFromOpenDropDown() {
        try {
            WebElementFacade openDropdownElement = modal.find( By.cssSelector( OPEN_DROPDOWN_SELECTOR ) );
            return openDropdownElement.thenFindAll( By.className( "select__option" ) );
        } catch ( NoSuchElementException e ) {
            return Collections.emptyList();
        }
    }

    public List<String> getOptionsFromOpenDropDownAsList() {
        return getOptionsElementsFromOpenDropDown().stream().map( WebElementFacade::getText ).collect( Collectors.toList() );
    }

    public void selectOptionFromOpenDropdown( String option ) {
        List<WebElementFacade> dropdownOptions = getOptionsElementsFromOpenDropDown();
        dropdownOptions.stream()
                       .filter( op -> op.getText().equals( option ) )
                       .findFirst()
                       .ifPresentOrElse(
                               this::elementClicker,
                               () -> {
                                   throw new NoSuchElementException( STR."Option was not found in the open dropdwn: \{option}" );
                               }
                       );
    }

    public void typeIntoProtocolStartDate( String date ) {
        WebElementFacade datePickerInput = modal.then( By.xpath( DATE_PICKER_INPUT_SELECTOR ) );
        datePickerInput.clear();
        datePickerInput.type( date );
    }

    public void waitForModalLoaderToFinish() {
        WebElement appRoot = getDriver().findElement( By.id( "appRoot" ) );
        PageUtils.waitUntilElementDisappears( appRoot, By.cssSelector( "[class^='ViewModeModal-module_loader_']" ), 30000, "View mode modal was not loaded in time" );
    }

    public void waitForModalClosure() {
        waitForAbsenceOf( By.cssSelector( ".modal" ) );
    }

    public void closeOpenDropDown() {
        try {
            WebElementFacade openDropDownElement = modal.find( By.cssSelector( OPEN_DROPDOWN_SELECTOR ) );
            elementClicker( openDropDownElement );
        } catch ( NoSuchElementException e ) {
            fail( "Could not find the open dropdown element on the View mode modal" );
        }
    }

    public String getErrorMessageOfViewModeModalField( String field ) {
        try {
            WebElementFacade fieldElement = getFieldElementByName( field );
            return fieldElement.then( By.cssSelector( ERROR_MESSAGE_SELECTOR ) ).getTextContent();
        } catch ( NoSuchElementException e ) {
            return STR."No error message was found for \{field} field";
        }
    }

    public String getSelectedViewMode() {
        WebElementFacade selectedRadio = modal.then( By.cssSelector( SELECTED_RADIO_BUTTON_CSS ) );
        return selectedRadio.find( By.xpath( SELECTED_RADIO_BUTTON_SIBLING_LABEL_CSS ) ).getText();
    }

    public String getSelectedConfigValueByTitle( String elementTitle ) {
        WebElementFacade fieldSelector = getFieldElementByName( elementTitle ).then( By.cssSelector( ".select" ) );
        try {
            return fieldSelector.then( By.cssSelector( "div[class*='Dropdown-module_selected-elem_']" ) ).getText();
        } catch ( NoSuchElementException e ) {
            // Checking for placeholder text if no selected value present
            try {
                return fieldSelector.then( By.cssSelector( ".select__placeholder" ) ).getText();
            } catch ( NoSuchElementException ex ) {
                return "Could not find the field by title";
            }
        }
    }

    public String getSelectedDateOrPlaceholderFromDatePicker() {
        WebElementFacade field = getFieldElementByName( "Protocol start date" );
        WebElementFacade dateInputElement = field.then( By.xpath( DATE_PICKER_INPUT_SELECTOR ) );
        String enteredValue = dateInputElement.getAttribute( "value" );
        return enteredValue == null ? dateInputElement.getAttribute( "placeholder" ) : enteredValue;
    }
}
