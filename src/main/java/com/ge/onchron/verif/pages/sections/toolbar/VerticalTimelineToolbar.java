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

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.Color;

import com.ge.onchron.verif.pages.sections.toolbar.timeRangeButtons.TimeRangeButtonsOnVerticalToolbar;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.pages.utils.PageUtils.currentlyContainsElements;

public class VerticalTimelineToolbar extends BaseTimelineToolbar {

    private final String VERTICAL_PATIENT_TOOLBAR_XPATH = "//*[contains(@class,'toolbar--vertical')]";
    private final String VERTICAL_TOOLBAR_ITEM_CSS_SELECTOR = "[class*='VerticalToolbar-module_toolbar-item']";
    private final String VERTICAL_TOOLBAR_FILTER_BADGE_CLASSNAME = "badge__count";

    private final String XPATH_OF_BUTTON_ON_VERTICAL_TOOLBAR = ".//button[contains(@class, 'toolbar__button') and contains(.,'%s')]"; // with tooltip text

    @FindBy( xpath = VERTICAL_PATIENT_TOOLBAR_XPATH )
    private WebElementFacade toolbar;

    @FindBy( css = VERTICAL_TOOLBAR_ITEM_CSS_SELECTOR )
    private List<WebElementFacade> toolbarItemList;

    private TimeRangeButtonsOnVerticalToolbar timeRangeButtonsOnVerticalToolbar;

    public boolean isDisplayed() {
        return toolbar.isVisible();
    }

    public void clickMenuItemByName( String name ) {
        String buttonXPathSelector = String.format( XPATH_OF_BUTTON_ON_VERTICAL_TOOLBAR, name );
        WebElementFacade button = toolbar.find( By.xpath( buttonXPathSelector ) );
        elementClicker( button );
    }

    @Override
    public void setFullTimeRange() {
        timeRangeButtonsOnVerticalToolbar.setFullTimeRange();
    }

    @Override
    public void setLast30daysTimeRange() {
        timeRangeButtonsOnVerticalToolbar.setLast30days();
    }

    @Override
    public void setLast90daysTimeRange() {
        timeRangeButtonsOnVerticalToolbar.setLast90days();
    }

    public void openFilterModal() {
        if ( !getTimelineFilterModal().isCurrentlyVisible() ) {
            // 0. zoom in, 1. zoom out, 2. last 30 days, 3. last 90 days, 4. All time, 5. View mode, 6. Filter
            elementClicker( toolbarItemList.get( 6 ).then( By.tagName( "button" ) ) );
            getTimelineFilterModal().waitUntilVisible();
        }
    }

    public String numFromFilterCounterIcon() {
        if ( currentlyContainsElements( toolbar, By.className( VERTICAL_TOOLBAR_FILTER_BADGE_CLASSNAME ) ) ) {
            return toolbar.then( By.className( VERTICAL_TOOLBAR_FILTER_BADGE_CLASSNAME ) ).getText();
        } else {
            return null;
        }
    }

    public String getColorFromFilterCounterIcon() {
        if ( currentlyContainsElements( toolbar, By.className( VERTICAL_TOOLBAR_FILTER_BADGE_CLASSNAME ) ) ) {
            return Color.fromString( toolbar.then( By.className( VERTICAL_TOOLBAR_FILTER_BADGE_CLASSNAME ) ).getCssValue( "background-color" )).asHex();
        } else {
            return null;
        }
    }

}
