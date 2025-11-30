/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2024, 2024, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.pages.sections.toolbar;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.Badge;
import com.ge.onchron.verif.model.enums.BadgeType;
import com.ge.onchron.verif.pages.SimplePage;
import com.ge.onchron.verif.pages.elements.Checkbox;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

public class SummaryToolbar extends SimplePage {

    private static final Logger LOGGER = LoggerFactory.getLogger( SummaryToolbar.class );

    private final String NAVIGATION_TOOLBAR_SELECTOR = "[class*='SummaryToolbar-module_toolbar_']";
    private final String SUMMARY_TOOLBAR_MORE_BUTTON_SELECTOR = "[class*='SummaryToolbar-module_toolbar-btn']";
    private final String MORE_CONTAINER_SELECTOR = "[class*='SummaryToolbar-module_more-container_']";
    private final String MORE_MENU_PANEL_SELECTOR = "[class*='SummaryToolbar-module_menu-panel']";
    private final String SUB_MENU_PANEL_SELECTOR = "[class*='SummaryToolbar-module_sub-menu-container']";
    private final String HIDE_EMPTY_CARDS_CHECKBOX_SELECTOR = "[class*='SummaryToolbar-module_menu-item-checkbox_']";
    private final String SUMMARY_LINKS_GROUP_SELECTOR = "[class*='SummaryToolbar-module_group-links_']";
    private final String SUMMARY_LINKS_SELECTOR = "[class*='SummaryToolbar-module_item_']";
    private final String EMPTY_CARDS_HIDDEN_BADGE_SELECTOR = "[class*='SummaryToolbar-module_empty-cards-badge_']";
    private final String LOADING_INDICATOR_SELECTOR = "[class*='indicator__circular--extra-extra-small']";

    @FindBy( css = NAVIGATION_TOOLBAR_SELECTOR )
    private WebElementFacade navigationToolbar;

    @FindBy( css = SUMMARY_TOOLBAR_MORE_BUTTON_SELECTOR )
    private WebElementFacade moreButton;

    @FindBy( css = SUMMARY_LINKS_SELECTOR )
    private List<WebElementFacade> linkElementList;


    public boolean isVisible() {
        return navigationToolbar.isVisible();
    }

    public boolean isMoreMenuOpen() {
        return navigationToolbar.then( By.cssSelector( MORE_CONTAINER_SELECTOR ) ).isDisplayed();
    }

    public void clickMoreButton() {
        elementClicker( moreButton );
    }

    public boolean getHideEmptyCardsCheckboxState() {
        return new Checkbox( navigationToolbar.then( By.cssSelector( HIDE_EMPTY_CARDS_CHECKBOX_SELECTOR ) ) ).isSelected();
    }

    public List<String> getLinkList() {
        try {
            return linkElementList.stream().map( element -> element.find( By.tagName( "span" ) ).getText() )
                                  .collect( Collectors.toList() );
        } catch ( NoSuchElementException e ) {
            return Collections.emptyList();
        }
    }

    public void clickSectionLink( String section ) {
        linkElementList.stream()
                       .filter( link -> link.getText().equals( section ) )
                       .findFirst()
                       .ifPresentOrElse( this::elementClicker, () -> fail( section + " link was not found on the Navigation toolbar" ) );
    }

    public void setHideEmptyCardsCheckboxState( boolean expectedCheckboxState ) {
        Checkbox checkbox = new Checkbox( navigationToolbar.then( By.cssSelector( HIDE_EMPTY_CARDS_CHECKBOX_SELECTOR ) ) );
        if ( checkbox.isSelected() != expectedCheckboxState ) {
            elementClicker( checkbox.getWebElement() );
        }
        waitForCondition().until( ExpectedConditions.invisibilityOfElementLocated( By.cssSelector( LOADING_INDICATOR_SELECTOR ) )  );
    }

    public Badge getBadge() {
        try {
            WebElementFacade badgeElement = navigationToolbar.find( By.cssSelector( EMPTY_CARDS_HIDDEN_BADGE_SELECTOR ) );
            Badge badge = new Badge();
            badge.setText( badgeElement.getText() );
            if ( !badgeElement.thenFindAll( By.id( "Info-Badge" ) ).isEmpty() ) {
                badge.setBadgeType( BadgeType.INFORMATION );
            }
            badge.setColor( Color.fromString( badgeElement.getCssValue( "background-color" ) ).asHex() );
            return badge;
        } catch ( NoSuchElementException e ) {
            return null;
        }
    }

    public void clickItemInViewOptions( String optionName ) {
        WebElementFacade menuPanel = navigationToolbar.then( By.cssSelector( MORE_MENU_PANEL_SELECTOR ) );
        WebElementFacade element = menuPanel.thenFindAll( By.xpath( "child::*" ) )
                                            .stream()
                                            .filter( webElement -> webElement.getText().equals( optionName ) )
                                            .findFirst()
                                            .orElseThrow( () -> new RuntimeException( String.format( "The following item was not found in View options: \n%s", optionName ) ) );
        elementClicker( element );
    }

    public void clickItemInViewOptionsSubMenu( String optionName ) {
        WebElementFacade element = navigationToolbar.then( By.cssSelector( MORE_MENU_PANEL_SELECTOR ) )
                                                    .then( By.cssSelector( SUB_MENU_PANEL_SELECTOR ) )
                                                    .thenFindAll( By.xpath( "child::*" ) )
                                                    .stream()
                                                    .filter( webElement -> webElement.getText().equals( optionName ) )
                                                    .findFirst()
                                                    .orElseThrow( () -> new RuntimeException( String.format( "The following sub-menu item was not found in View options: \n%s", optionName ) ) );
        elementClicker( element );
    }

}
