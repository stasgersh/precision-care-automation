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
package com.ge.onchron.verif.howsteps;

import java.util.List;

import static org.junit.Assert.assertTrue;

import com.ge.onchron.verif.pages.sections.UserSideNavMenu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserMenuSteps {

    private                  UserSideNavMenu userMenu;

    public void clickOnSignOut() {
        userMenu.clickOnSignOut();
    }

    private static final Logger LOGGER = LoggerFactory.getLogger( UserMenuSteps.class );

    public void checkUserMenuItems( List<String> expectedMenuItems ) {
        List<String> observedMenuItems = userMenu.getTextOfSideNavUtilityItems();
        expectedMenuItems.forEach( expectedMenuItem -> {
                boolean found = observedMenuItems.contains( expectedMenuItem );
                LOGGER.info(STR."The menu item: \{expectedMenuItem} was found successfully: \{found}");
                assertTrue(STR."The following user menu item was not found: \{expectedMenuItem}", found ); });
    }

    public void clickOnMenuItem( String menuItem ) {
        userMenu.clickOnSideNavUtilityItem( menuItem );
    }

    public void moveMouseToMenu() {
        userMenu.moveMouseToMenu();
    }

    public boolean isVisible() {
        return userMenu.isVisible();
    }

}
