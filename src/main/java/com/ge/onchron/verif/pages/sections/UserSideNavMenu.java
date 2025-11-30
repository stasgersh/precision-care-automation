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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import com.ge.onchron.verif.pages.SimplePage;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.pages.utils.PageElementUtils.moveToElement;

public class UserSideNavMenu extends SimplePage {

    @FindBy( css = "[class*='SideNavigation-module_sidenav-container']" )
    private WebElementFacade sideMenuPanel;

    @FindBy( css = "[class*='SideNavigation-module_open']" )
    private WebElementFacade openSideMenuPanel;

    private final String SIDE_MENU_UTILITIES = "[class*='SideNavigation-module_utilities-container']";

    private final List<String> orderedSideNavUtilityItems = Arrays.asList(
            "About",
            "Help",
            "Settings",
            "Profile",
            "Sign out" );

    public boolean isVisible() {
        return sideMenuPanel.isCurrentlyVisible();
    }

    public boolean isOpen() {
        return openSideMenuPanel.isCurrentlyVisible();
    }

    public void clickOnSignOut() {
        clickOnSideNavUtilityItem( "Sign out" );
    }

    public List<String> getTextOfSideNavUtilityItems() {
        return getSideNavUtilityItems().stream()
                                       .map( WebElementFacade::getTextContent )
                                       .collect( Collectors.toList() );
    }

    private List<WebElementFacade> getSideNavUtilityItems() {
        return sideMenuPanel.then( By.cssSelector( SIDE_MENU_UTILITIES ) ).thenFindAll( By.xpath( "child::*" ) );
    }

    public void clickOnSideNavUtilityItem( String menuItem ) {
        List<WebElementFacade> sideNavUtilityItems = getSideNavUtilityItems();
        sideNavUtilityItems.get( orderedSideNavUtilityItems.indexOf( menuItem ) ).click();
    }

    public String getUsername() {
        return getSideNavUtilityItems().get( orderedSideNavUtilityItems.indexOf( "Profile" ) ).getTextContent();
    }

    public void moveMouseToMenu() {
        moveToElement( sideMenuPanel );
    }

}
