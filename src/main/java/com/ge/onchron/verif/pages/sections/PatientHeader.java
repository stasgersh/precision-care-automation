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
package com.ge.onchron.verif.pages.sections;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;

import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.Patient;
import com.ge.onchron.verif.model.detailedDataItems.CLPItem;
import com.ge.onchron.verif.model.detailedDataItems.DetailedDataItem;
import com.ge.onchron.verif.model.detailedDataItems.LinkItem;
import com.ge.onchron.verif.model.detailedDataItems.SimpleItem;
import com.ge.onchron.verif.pages.SimplePage;
import com.ge.onchron.verif.pages.elements.tooltip.TooltipElement;
import com.ge.onchron.verif.pages.utils.PageElementUtils;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.pages.utils.PageUtils.currentlyContainsElements;
import static com.ge.onchron.verif.pages.utils.PageUtils.currentlyContainsWebElements;

public class PatientHeader extends SimplePage {

    private TooltipElement tooltip;

    @FindBy( xpath = "//*[contains(@class,'PatientDetails-module_patient-header')]" )
    private WebElementFacade patientHeader;

    @FindBy( css = "[class*='PatientHeader-module_props']" )
    private WebElementFacade patientBanner;

    @FindBy( xpath = "//*[contains(@class,'PatientSelector-module_select-container')]" )
    private WebElementFacade patientSelector;

    @FindBy( xpath = "//*[contains(@class, 'PatientHeader-module_no-print')]" )
    private WebElementFacade patientBannerElements;

    @FindBy( xpath = "//*[contains(@class,'PatientHeader-module_button')]//.." ) // parent is the <button> element
    private WebElementFacade patientBannerButton;

    @FindBy( xpath = "//*[contains(@class, 'PatientHeader-module_content') and contains(@class, 'PatientHeader-module_no-print')]" )
    private WebElementFacade patientBannerContent;

    @FindBy( xpath = "//*[contains(@placeholder,'Search')]" )
    private WebElementFacade searchField;

    @FindBy( xpath = XPATH_OF_CLEAR_BUTTON_IN_SEARCH_FIELD )
    private WebElementFacade clearBtnInSearchField;

    @FindBy( className = "select__dropdown-panel-items" )
    private WebElementFacade patientDropdownPanelElement;

    private final String XPATH_FOR_PATIENT_BAR_HIGHLIGHTED_TEXT = "child::*[contains(@class,'no-print')]/span[contains(text(),'%s')]/child::*[contains(@class,'module_no-print')]";
    private final String SELECTOR_OF_CLOSED_PATIENT_BANNER = "[class*='PatientHeader-module_minified']";
    private final String XPATH_OF_OPENED_PATIENT_SELECT_LIST = "//*[contains(@class,'select__dropdown-panel--open')]";
    private final String XPATH_OF_CLEAR_BUTTON_IN_SEARCH_FIELD = "//*[contains(@class,'select__panel-header-input-icon--clear')]";
    private final String PATIENT_LIST_ITEM_SELECTOR = "[class*='PatientSelector-module_select-option--no-height']";
    private final String PATIENT_NAME_SELECTOR = "[class*='name']";
    private final String PATIENT_ID_SELECTOR = "[class*='PatientSelector-module_select-option--id']";

    private final String XPATH_OF_PATIENT_BANNER_LOADING_SKELETON = "//*[contains(@class,'PatientHeader-module_extended-skeleton')]";

    private final String XPATH_OF_PATIENT_BANNER_VALUE = "*[contains(@class,'TextElement-module') or contains(@class,'ClpElement-module')]";
    private final String XPATH_OF_PATIENT_BANNER_TRUNCATED_VALUE = "*[contains(@class,'TextElement-module_clickable') or contains(@class,'ClpElement-module_clickable')]";
    private final String XPATH_OF_PATIENT_BANNER_TRUNCATED_MORE_LINK = "*[contains(@class,'TextElement-module_more-link') or contains(@class,'ClpElement-module_more-link')]";
    private final String XPATH_OF_PATIENT_BANNER_FULL_VALUE = "*[contains(@class,'TextElement-module_print') or contains(@class,'ClpElement-module_print')]";

    private final String SELECTOR_OF_CLP_PATIENT_BANNER_ITEM = "[class*='ClpElement-module_clp']";
    private final String DISPLAYED_PATIENT_ID_TYPE = "MRN";

    public boolean isVisible() {
        return patientHeader.isVisible();
    }

    public boolean isPatientBannerVisible() {
        return patientBanner.isCurrentlyVisible();
    }

    public void waitUntilPatientBannerLoadingSkeletonDisappears() {
        patientBannerContent.waitUntilVisible();
        waitUntilLoadingSkeletonDisappears( patientHeader, By.xpath( XPATH_OF_PATIENT_BANNER_LOADING_SKELETON ) );
    }

    public void clickOnPatientSelect() {
        elementClicker( patientSelector );
    }

    public void openPatientSelectMenu() {
        if ( !isPatientSelectMenuOpened() ) {
            clickOnPatientSelect();
        }
    }

    private boolean isPatientSelectMenuOpened() {
        return currentlyContainsElements( patientHeader, By.xpath( XPATH_OF_OPENED_PATIENT_SELECT_LIST ) );
    }

    public void typeIntoPatientSearchField( String searchParam ) {
        searchField.waitUntilEnabled().type( searchParam );
    }

    public List<WebElement> getPatientListElements() {
        return patientHeader.then()
                            .findElement( By.className( "select__dropdown-panel-items" ) )
                            .findElements( By.className( "select__option" ) );
    }

    public int getNumberOfPatients() {
        return getPatientListElements().size();
    }

    public List<Patient> getPatients() {
        List<WebElement> patientElements = getPatientListElements();
        List<Patient> patients = new ArrayList<>();

        if ( isEmptyPatientList( patientElements ) ) {
            return patients;
        }

        patientElements.forEach( actualPatient -> {
            String patientName = actualPatient.findElement( By.className( "name" ) ).getText();
            Patient patient = new Patient( patientName );

            String patientPidWithPrefix = actualPatient.findElement( By.className( "pid" ) ).getText();
            String patientPid = patientPidWithPrefix.split( ":" )[1];
            patient.setPID( patientPid.replaceAll( "\\p{Z}", "" ) ); //non-breaking space .trim() doesn't work

            patients.add( patient );
        } );

        return patients;
    }

    public boolean isEmptyPatientList( List<WebElement> patientElements ) {
        return patientElements.size() == 1 && patientElements.get( 0 ).getAttribute( "id" ).equals( "option-not-found" );
    }

    public String getTextOfEmptyPatientList() {
        List<WebElement> patientElements = getPatientListElements();
        if ( !isEmptyPatientList( patientElements ) ) {
            fail( "The patient list is not empty (or does not contain the empty list placeholder)." );
        }
        return patientElements.get( 0 ).getText();
    }

    public void clickOnXButtonInSearch() {
        elementClicker( clearBtnInSearchField );
    }

    public void clearSearchParams() {
        if ( currentlyContainsElements( searchField, By.xpath( XPATH_OF_CLEAR_BUTTON_IN_SEARCH_FIELD ) ) ) {
            clickOnXButtonInSearch();
        }
    }

    public void selectPatientByName( String patientName ) {
        typeIntoPatientSearchField( patientName );
        if ( !currentlyContainsElements( patientDropdownPanelElement, By.cssSelector( PATIENT_NAME_SELECTOR ) ) ) {
            fail( STR."Patient list is empty, patient cannot be selected: \{patientName}" );
        }
        List<WebElementFacade> patientOptions = patientDropdownPanelElement.thenFindAll( By.cssSelector( PATIENT_LIST_ITEM_SELECTOR ) );
        WebElementFacade requiredPatient = patientOptions.stream()
                                                         .filter( patientOption -> patientOption.then( By.cssSelector( PATIENT_NAME_SELECTOR ) ).getText().equals( patientName )
                                                                 || patientOption.then( By.cssSelector( PATIENT_ID_SELECTOR ) ).getText().equals( STR."\{DISPLAYED_PATIENT_ID_TYPE}: \{patientName}" ) )
                                                         .findFirst()
                                                         .orElseThrow( () -> new RuntimeException( patientName + " patient was not found in the patient list" ) );
        elementClicker( requiredPatient );
    }

    public void openPatientBannerCaret() {
        if ( isPatientBannerClosed() ) {
            elementClicker( patientBannerButton );
        }
    }

    public void closePatientBannerCaret() {
        if ( !isPatientBannerClosed() ) {
            elementClicker( patientBannerButton );
        }
    }

    public boolean isPatientBannerClosed() {
        return currentlyContainsElements( patientHeader, By.cssSelector( SELECTOR_OF_CLOSED_PATIENT_BANNER ) );
    }

    public List<DetailedDataItem> getPatientBannerParams() {
        LinkedHashMap<DetailedDataItem, WebElementFacade> patientBannerItems = getPatientBannerParamsWithWebElements();
        return new ArrayList<>( patientBannerItems.keySet() );
    }

    public List<DetailedDataItem> getCurrentlyVisiblePatientBannerParams() {
        LinkedHashMap<DetailedDataItem, WebElementFacade> patientBannerItems = getPatientBannerParamsWithWebElements();
        Set<DetailedDataItem> keys = patientBannerItems.keySet();
        List<DetailedDataItem> visibleParams = new ArrayList<>();
        for ( DetailedDataItem key : keys ) {
            WebElementFacade actualParam = patientBannerItems.get( key );
            if ( isPatientBannerElementVisible( actualParam ) ) {
                visibleParams.add( key );
            }
        }
        return visibleParams;
    }

    /**
     * The size of the patient banner container element is different when the banner is open or closed.
     * If the patient banner element is outside of this rect, then it is not visible.
     * <p>
     * Notes:
     * getRect().getY() -> Y axis value from top left corner of an element
     * getRect().getWidth() & getRect().getHeight() – size of an element
     *
     * @param element The element to be checked if it is visible in the patient banner or not
     * @return visibility of the element
     */
    private boolean isPatientBannerElementVisible( WebElementFacade element ) {
        Rectangle rectOfPatientBanner = patientBannerContent.getRect();
        int yPosOfPatientBannerBottomLine = rectOfPatientBanner.getY() + rectOfPatientBanner.getHeight();
        int yPosOfElement = element.getRect().getY();
        return yPosOfElement < yPosOfPatientBannerBottomLine;
    }

    public void clickOnLink( String linkName ) {
        Map<DetailedDataItem, WebElementFacade> patientBannerItems = getPatientBannerParamsWithWebElements();
        WebElementFacade link = patientBannerItems.entrySet().stream()
                                                  .filter( param -> param.getKey() instanceof LinkItem && param.getKey().getName().equals( linkName ) )
                                                  .map( Map.Entry::getValue )
                                                  .findFirst()
                                                  .orElseThrow( () -> new RuntimeException( "Following link was not found in patient banner: " + linkName ) );
        elementClicker( link );
    }

    private LinkedHashMap<DetailedDataItem, WebElementFacade> getPatientBannerParamsWithWebElements() {
        List<WebElementFacade> patientBannerItems = patientBannerContent.thenFindAll( By.xpath( "*" ) );
        LinkedHashMap<DetailedDataItem, WebElementFacade> patientParams = new LinkedHashMap<>();
        for ( WebElementFacade actualItem : patientBannerItems ) {
            String className = actualItem.getAttribute( "class" );
            if ( className.contains( "link" ) ) {
                // LinkItem (LINK)
                String itemName = actualItem.getText();
                patientParams.put( new LinkItem( itemName ), actualItem );
            } else {
                String fullItemText = actualItem.getText();

                String itemName;
                String itemValue;
                String color = null;

                if ( currentlyContainsElements( actualItem, By.tagName( "label" ) ) ) {
                    // Not found value
                    WebElementFacade actualItemElement = actualItem.find( By.tagName( "label" ) );
                    itemValue = actualItemElement.getText();
                    itemName = fullItemText.replace( itemValue, "" );
                    color = Color.fromString( actualItemElement.getCssValue( "color" ) ).asHex();

                } else if ( currentlyContainsElements( actualItem, By.xpath( XPATH_OF_PATIENT_BANNER_TRUNCATED_VALUE ) ) ) {
                    // Truncated value
                    String itemTruncatedValue = actualItem.find( By.xpath( XPATH_OF_PATIENT_BANNER_TRUNCATED_VALUE ) ).getTextContent();
                    String itemMoreLink = actualItem.find( By.xpath( XPATH_OF_PATIENT_BANNER_TRUNCATED_MORE_LINK ) ).getText();
                    itemValue = actualItem.find( By.xpath( XPATH_OF_PATIENT_BANNER_FULL_VALUE ) ).getTextContent();
                    itemName = fullItemText.replace( itemTruncatedValue + itemMoreLink, "" );

                } else {
                    // Not truncated value
                    itemValue = actualItem.find( By.xpath( XPATH_OF_PATIENT_BANNER_FULL_VALUE ) ).getTextContent();
                    itemName = fullItemText.replace( itemValue, "" );
                }

                if ( currentlyContainsWebElements( actualItem, By.cssSelector( SELECTOR_OF_CLP_PATIENT_BANNER_ITEM ) ) ) {
                    // CLPItem (CLP_INFO)
                    String backgroundColor = Color.fromString( actualItem.find( By.cssSelector( SELECTOR_OF_CLP_PATIENT_BANNER_ITEM ) ).getCssValue( "background-color" ) ).asHex();
                    patientParams.put( new CLPItem( itemName, itemValue, backgroundColor ), actualItem );
                } else {
                    // Otherwise, it can be only a SimpleItem (KEY-VALUE)
                    SimpleItem simpleItem = new SimpleItem( itemName, itemValue );
                    if ( color != null ) {
                        simpleItem.setColor( color );
                    }
                    patientParams.put( simpleItem, actualItem );
                }
            }
        }
        return patientParams;
    }

    public String getSelectedPatientName() {
        return patientSelector.getText();
    }

    public String getPatientBannerButtonText() {
        return patientBannerButton.getText();
    }

    public void hoverOnHeaderParameter( String parameterName, By by ) {
        Map<DetailedDataItem, WebElementFacade> patientBannerItems = getPatientBannerParamsWithWebElements();
        WebElementFacade parameter = patientBannerItems.entrySet().stream()
                                                       .filter( param -> param.getKey().getName().equals( parameterName ) )
                                                       .map( Map.Entry::getValue )
                                                       .findFirst()
                                                       .orElseThrow( () -> new RuntimeException( STR."Following parameter was not found in patient banner: \{parameterName}" ) );
        WebElementFacade parameterItem = parameter.thenFindAll( by )
                                                  .stream()
                                                  .filter( param -> !param.getAttribute( "class" ).contains( "print-only" ) )
                                                  .findFirst()
                                                  .orElseThrow( () -> new RuntimeException( STR."Following parameter does not have element \{parameterName} based on \{by} filter" ) );
        PageElementUtils.moveToElement( parameterItem );
    }

    public void hoverOnHeaderParameterValue( String parameterName ) {
        hoverOnHeaderParameter( parameterName, By.xpath( XPATH_OF_PATIENT_BANNER_VALUE ) );
    }

    public void hoverOnHeaderParameterMoreLink( String parameterName ) {
        hoverOnHeaderParameter( parameterName, By.xpath( XPATH_OF_PATIENT_BANNER_TRUNCATED_MORE_LINK ) );
    }

    //TODO: improve this method to use enums for different parameters to provide specific IDs or other smarter way
    public boolean getHighlightedDataForSpecificParameter( DetailedDataItem highlightedParams ) {
        if ( highlightedParams.getName().contains( "Histology" ) ) {
            return patientBannerElements.findElements( By.xpath( "//*[contains(@class,'ClpElement-module_clp')]" ) )
                                        .stream().map( WebElement::getText ).toList().contains( highlightedParams.getName() );
        }
        return false;
    }

    public String getBannerTooltipText() {
        return tooltip.getTooltipFullText();
    }

    public boolean isLoadingSkeletonVisible() {
        Optional<WebElementFacade> loadingSkeleton = findFirst( By.xpath( XPATH_OF_PATIENT_BANNER_LOADING_SKELETON ) );
        return loadingSkeleton.isPresent() && loadingSkeleton.get().isVisible();
    }
}
