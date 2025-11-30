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

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.Color;

import com.ge.onchron.verif.pages.SimplePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.ListOfWebElementFacades;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.utils.Utils.waitMillis;

public class CreateLabelPanel extends SimplePage {

    private final String CREATE_NEW_LABEL_PANEL_CSS_SELECTOR = "[class*='LabelPanel-module_small']";
    private final String CREATE_BUTTON_XPATH = ".//button[text()='Create']";
    private final String COLOR_PALETTE_ITEM_CSS_SELECTOR = "[class*='LabelPanel-module_color-pick']";
    private final String LABEL_PANEL_ERROR_CSS_SELECTOR = "[class*='LabelPanel-module_error']";
    private final String CLASS_OF_SELECTED_COLOR = "LabelPanel-module_active";

    @FindBy( css = CREATE_NEW_LABEL_PANEL_CSS_SELECTOR )
    private WebElementFacade createNewLabelPanel;

    @FindBy( css = LABEL_PANEL_ERROR_CSS_SELECTOR )
    private WebElementFacade labelPanelError;

    public boolean isVisible() {
        return createNewLabelPanel.isVisible();
    }

    public void typeTextIntoLabelNameField( String labelName ) {
        getLabelNameTextBox().type( labelName );
    }

    public void removeLastCharOfLabelName() {
        getLabelNameTextBox().sendKeys( Keys.BACK_SPACE );
    }

    public void continueLabelNameTyping( String text ) {
        getLabelNameTextBox().sendKeys( text );
    }

    private WebElementFacade getLabelNameTextBox() {
        return createNewLabelPanel.then( By.cssSelector( "[class*='textbox']" ) );
    }

    public void selectColor( int ordinalNum ) {
        WebElementFacade requiredColor = getElementFromColorPalette( ordinalNum );
        elementClicker( requiredColor );
    }

    public List<Integer> getOrdinalNumsOfSelectedColorsFromPalette() {
        List<Integer> ordinalNumsOfSelectedColors = new ArrayList<>();
        List<WebElementFacade> colorPaletteItems = createNewLabelPanel.thenFindAll( By.cssSelector( COLOR_PALETTE_ITEM_CSS_SELECTOR ) );
        for ( int i = 0; i < colorPaletteItems.size(); i++ ) {
            if ( colorPaletteItems.get( i ).getAttribute( "class" ).contains( CLASS_OF_SELECTED_COLOR ) ) {
                ordinalNumsOfSelectedColors.add( i + 1 );   // in the feature files, counting is started from 1, not from 0
            }
        }
        return ordinalNumsOfSelectedColors;
    }

    public String getRgbColorFromPalette( int ordinalNum ) {
        WebElementFacade requiredColor = getElementFromColorPalette( ordinalNum );
        Color color = Color.fromString( requiredColor.getCssValue( "background-color" ) );
        return color.asRgb();
    }

    public void clickOnCreateButton() {
        WebElementFacade createButton = createNewLabelPanel.then( By.xpath( CREATE_BUTTON_XPATH ) );
        elementClicker( createButton );
        waitMillis( 300 ); // no loading indicator, so a little wait is needed
    }

    private WebElementFacade getElementFromColorPalette( int ordinalNum ) {
        ListOfWebElementFacades colorPaletteItems = createNewLabelPanel.thenFindAll( By.cssSelector( COLOR_PALETTE_ITEM_CSS_SELECTOR ) );
        return colorPaletteItems.get( ordinalNum - 1 );
    }

    public String getLabelPanelError() {
        return labelPanelError.getText();
    }

    public boolean isCreateButtonEnabled() {
        WebElementFacade createButton = createNewLabelPanel.then( By.xpath( CREATE_BUTTON_XPATH ) );
        return createButton.isCurrentlyEnabled();
    }

}
