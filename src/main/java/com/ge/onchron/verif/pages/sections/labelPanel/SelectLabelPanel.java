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
package com.ge.onchron.verif.pages.sections.labelPanel;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.support.Color;

import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.Label;
import com.ge.onchron.verif.pages.SimplePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.ListOfWebElementFacades;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.pages.utils.PageElementUtils.waitForLoadingIndicatorDisappears;
import static com.ge.onchron.verif.pages.utils.PageUtils.currentlyContainsWebElements;

public class SelectLabelPanel extends SimplePage {

    private final String SELECT_LABEL_PANEL_CSS_SELECTOR = "[class*='LabelPanel-module_container']";
    private final String SEARCH_BOX_CSS_SELECTOR = "[class*='search']";
    private final String CREATE_NEW_BTN_SELECTOR = "//*[contains(@class, 'button') and contains(normalize-space(text()), 'Create new')]";
    private final String LABEL_LIST_CONTAINER_CSS_SELECTOR = "[class*='LabelPanel-module_label-list']";
    private final String LABEL_CSS_SELECTOR = "[class*='Label-module_label']";
    private final String NOT_FOUND_MSG_CSS_SELECTOR = "[class*='LabelPanel-module_notfound']";

    @FindBy( css = SELECT_LABEL_PANEL_CSS_SELECTOR )
    private WebElementFacade defaultLabelPanel;

    @FindBy( css = SEARCH_BOX_CSS_SELECTOR )
    private WebElementFacade searchBox;

    @FindBy( css = LABEL_LIST_CONTAINER_CSS_SELECTOR )
    private WebElementFacade labelListContainer;

    @FindBy( xpath = CREATE_NEW_BTN_SELECTOR )
    private WebElementFacade createNewBtn;

    public boolean isVisible() {
        waitForLoadingIndicatorDisappears();
        return defaultLabelPanel.isVisible();
    }

    public String getLabelColor( String labelName ) {
        WebElementFacade labelElement = getLabelElement( labelName );
        Color color = Color.fromString( labelElement.getCssValue( "background-color" ) );
        return color.asRgb();
    }

    public void clickOnLabel( String labelName ) {
        WebElementFacade labelElement = getLabelElement( labelName );
        elementClicker( labelElement );
    }

    public void clickOnCreateNew() {
        elementClicker( createNewBtn );
    }

    public Label getLabel( String labelName ) {
        WebElementFacade labelElement = getLabelElement( labelName );
        Color color = Color.fromString( labelElement.getCssValue( "background-color" ) );
        Label label = new Label( labelElement.getText() );
        label.setColor( color.asRgb() );
        return label;
    }

    public List<String> getAllLabelNames() {
        ListOfWebElementFacades webElementFacades = labelListContainer.thenFindAll( By.cssSelector( LABEL_CSS_SELECTOR ) );
        return webElementFacades.textContents();
    }

    private WebElementFacade getLabelElement( String labelName ) {
        WebElementFacade labelElement = null;
        try {
            labelElement = labelListContainer.then( By.xpath( ".//*[text()='" + labelName + "']" ) ).then( By.xpath( "./.." ) );
        } catch ( NoSuchElementException e ) {
            fail( "Label element was not found: " + labelName );
        }
        return labelElement;
    }

    public void typeInSearchField( String searchText ) {
        searchBox.then( By.tagName( "input" ) ).type( searchText );
    }

    public String getNotFoundText() {
        By notFoundSelector = By.cssSelector( NOT_FOUND_MSG_CSS_SELECTOR );
        if ( !currentlyContainsWebElements( defaultLabelPanel, notFoundSelector ) ) {
            return null;
        }
        WebElementFacade notFoundElement = defaultLabelPanel.then( notFoundSelector );
        return notFoundElement.getText();
    }

}
