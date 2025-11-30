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
package com.ge.onchron.verif.pages.sections;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.FindBy;

import static org.junit.Assert.fail;

import com.ge.onchron.verif.pages.SimplePage;
import net.serenitybdd.core.pages.WebElementFacade;

public class TabSelector extends SimplePage {

    private final String TAB_CANCEL_X_ICON_SELECTOR = "[class*='Tabs-module_cancel-icon_']";

    @FindBy( css = "[class*='Tabs-module_container']" )
    private WebElementFacade tabsContainer;

    private List<WebElementFacade> getTabs() {
        return tabsContainer.thenFindAll( By.tagName( "button" ) );
    }

    public void clickOnTab( String tabName ) {
        WebElementFacade tab = getTabWebElement( tabName );
        elementClicker( tab );
    }

    public int getCounterFromBadgeOnTab( String tabName ) {
        WebElementFacade tab = getTabWebElement( tabName );
        WebElementFacade badge = tab.then( By.className( "badge" ) );
        return Integer.parseInt( badge.getText() );
    }

    private WebElementFacade getTabWebElement( String tabName ) {
        List<WebElementFacade> tabs = getTabs();
        return tabs.stream()
                   .filter( tab -> {
                       String[] tabTextElements = tab.getText().split( "\n" );  // [0]: tab name, [1] badge text
                       String actualTabName = tabTextElements[0];
                       return actualTabName.equals( tabName );
                   } )
                   .findFirst()
                   .orElseThrow( () -> new RuntimeException( "Following tab was not found: " + tabName ) );
    }

    public void clickOnResponseX() {
        WebElementFacade responseTabElement = getTabWebElement( "Response" );
        try {
            WebElementFacade xIconElement = responseTabElement.then( By.cssSelector( "[class*='Tabs-module_cancel-icon_']" ) );
            elementClicker( xIconElement );
        } catch ( NoSuchElementException e ) {
            fail( "Could not click on X icon on Response tab" );
        }
    }

    public boolean isTabOpen(String tabName) {
        try {
            return getTabWebElement( tabName ).isCurrentlyVisible();
        } catch ( Exception e ) {
            return false;
        }
    }
}
