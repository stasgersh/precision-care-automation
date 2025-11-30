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
package com.ge.onchron.verif.pages.sections.toolbar;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import static org.junit.Assert.assertNotNull;

import com.ge.onchron.verif.model.CheckboxModel;
import com.ge.onchron.verif.model.FilterDataCheckbox;
import com.ge.onchron.verif.pages.SimplePage;
import com.ge.onchron.verif.pages.elements.Checkbox;
import com.ge.onchron.verif.pages.elements.CheckboxState;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.ListOfWebElementFacades;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.pages.utils.PageElementUtils.scrollIntoViewport;
import static com.ge.onchron.verif.utils.Utils.waitMillis;

public abstract class BaseTimelineToolbar extends SimplePage {

    private final String CLOSE_FILTER_MODAL_SELECTOR = "[title*='Close modal']";
    private final String FILTER_MODAL_COLUMN_HEADER_SELECTOR = "[class*='FilterModal-module_column-header']";
    private final String FILTER_MODAL_COLUMN_SELECTOR = "[class*='FilterModal-module_column-list']";
    private final String FILTER_MODAL_COLUMN_ITEMS_SELECTOR = "[class*='menu-item']";
    private final String FILTER_MODAL_NOTIFICATION_SELECTOR = "[class*='FilterModal-module_notifwrap_']";

    @FindBy( xpath = "//*[contains(@class,'FilterModal-module_filter-modal')]" )
    private WebElementFacade timelineFilterModal;

    @FindBy( css = "[class*='FilterModal-module_columns']" )
    private WebElementFacade filterModalColumns;

    @FindBy( css = FILTER_MODAL_NOTIFICATION_SELECTOR )
    private WebElementFacade filterModalNotification;

    public abstract void setFullTimeRange();

    public abstract void setLast30daysTimeRange();

    public abstract void setLast90daysTimeRange();

    private WebElementFacade getSearchFilterField() {
        return timelineFilterModal.then( By.cssSelector( "[class*='search']" ) ).then( By.tagName( "input" ) );
    }

    private WebElementFacade getResetFilterButton() {
        return timelineFilterModal.then( By.cssSelector( "[title*='Reset filters']" ) );
    }

    protected WebElementFacade getTimelineFilterModal() {
        return timelineFilterModal;
    }

    public void closeFilterModal() {
        if ( getTimelineFilterModal().isCurrentlyVisible() ) {
            getTimelineFilterModal().then( By.cssSelector( CLOSE_FILTER_MODAL_SELECTOR ) ).click();
            waitMillis( 500 ); // Waiting for the filter state to be saved
        }
    }

    public void setCheckboxOnFilterModal( FilterDataCheckbox filterDataElement ) {
        String requiredFilterGroup = filterDataElement.getFilterGroup();
        List<WebElementFacade> groupItems = getFilterGroupItemWebelements( requiredFilterGroup );

        CheckboxModel expCheckbox = filterDataElement.getCheckboxModel();
        Checkbox checkbox = groupItems.stream()
                                      .filter( cb -> {
                                          scrollIntoViewport( cb );
                                          return cb.getText().equals( expCheckbox.getLabel() );
                                      } )
                                      .findFirst()
                                      .map( Checkbox::new )
                                      .orElseThrow( () -> new RuntimeException( "Filter option on filter modal was not found: " + filterDataElement ) );
        checkbox.setCheckboxState( CheckboxState.valueOf( expCheckbox.isSelected() ) );
    }

    public List<CheckboxModel> getCheckboxesFromFilterModalGroup( String filterDataGroupName ) {
        List<WebElementFacade> groupItems = getFilterGroupItemWebelements( filterDataGroupName );
        List<CheckboxModel> checkboxModels = new ArrayList<>();
        for ( WebElementFacade groupItem : groupItems ) {
            Checkbox checkbox = new Checkbox( groupItem );
            scrollIntoViewport( checkbox.getWebElement() );
            checkboxModels.add( new CheckboxModel( checkbox.getText(), checkbox.isSelected(), checkbox.isEnabled() ) );
        }
        return checkboxModels;
    }

    private List<WebElementFacade> getFilterGroupItemWebelements( String filterDataGroupName ) {
        return getGroupItemsFromFilterModal( filterDataGroupName );
    }

    public String getTextFromFilterGroup( String groupName ) {
        WebElementFacade requiredGroup = getFilterGroupElementsContainerFromFilterModal( groupName );
        assertNotNull( "Filter group was not found: " + groupName, requiredGroup );
        return requiredGroup.getText();
    }

    private List<WebElementFacade> getGroupItemsFromFilterModal( String groupName ) {
        WebElementFacade requiredGroup = getFilterGroupElementsContainerFromFilterModal( groupName );
        assertNotNull( "Filter group was not found: " + groupName, requiredGroup );
        return requiredGroup.thenFindAll( By.cssSelector( FILTER_MODAL_COLUMN_ITEMS_SELECTOR ) );
    }

    private WebElementFacade getFilterGroupElementsContainerFromFilterModal( String groupName ) {
        List<WebElementFacade> columns = filterModalColumns.thenFindAll( By.xpath( "child::*" ) );
        for ( WebElementFacade actualColumn : columns ) {
            List<WebElementFacade> groupHeaders = actualColumn.thenFindAll( By.cssSelector( FILTER_MODAL_COLUMN_HEADER_SELECTOR ) );
            List<WebElementFacade> groupElementsList = actualColumn.thenFindAll( By.cssSelector( FILTER_MODAL_COLUMN_SELECTOR ) );
            for ( int i = 0; i < groupHeaders.size(); i++ ) {
                if ( groupHeaders.get( i ).getText().equals( groupName ) ) {
                    return groupElementsList.get( i );
                }
            }
        }
        return null;
    }

    public List<String> getFilterGroupNames() {
        ListOfWebElementFacades filterGroupHeaders = filterModalColumns.thenFindAll( By.cssSelector( FILTER_MODAL_COLUMN_HEADER_SELECTOR ) );
        return filterGroupHeaders.textContents();
    }

    public void typeIntoSearchFilterField( String searchText ) {
        getSearchFilterField().type( searchText );
    }

    public void clickResetFilterButton() {
        elementClicker( getResetFilterButton() );
    }

    public String getFilterModalNotification() {
        return filterModalNotification.getText();
    }

    public void clickLinkText( String linkText ) {
        WebElementFacade link = getTimelineFilterModal().then( By.xpath( "//a[text()='LINK_TEXT']".replace( "LINK_TEXT", linkText ) ) );
        elementClicker( link );
    }
}
