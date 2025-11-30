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
package com.ge.onchron.verif.whatsteps.user;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.ge.onchron.verif.howsteps.CommonSteps;
import com.ge.onchron.verif.howsteps.SplashScreenSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.howsteps.UserMenuSteps;
import com.ge.onchron.verif.model.StringList;
import net.thucydides.core.annotations.Steps;

public class UserMenu {

    @Steps
    private UserMenuSteps userMenuSteps;
    @Steps
    private SplashScreenSteps splashScreenSteps;
    @Steps
    private CommonSteps commonSteps;

    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @When( "the user clicks on the \"Sign out\" button on the user menu panel" )
    public void clickOnSignOut() {
        testDefinitionSteps.addStep("Click on the 'Sign out' button on the user menu panel");
        userMenuSteps.clickOnSignOut();
        testDefinitionSteps.logEvidence("User signed out without errors",
            "User signed out with no errors", true);
    }

    @Given( "the user logged out from OncoCare" )
    @When( "the user logs out from OncoCare" )
    public void signOutFromOncoCare() {
        testDefinitionSteps.addStep("Click on sign out button");
        userMenuSteps.clickOnSignOut();
        commonSteps.refreshPage();
        splashScreenSteps.verifyUserIsOnTheSplashScreen();
        testDefinitionSteps.logEvidence("Sign out button was clicked successfully",
            "Sign out button clicked (check previous logs)", true);

    }

    @Then( "the following items are available in the user menu: $menuItems" )
    public void checkUserMenuItems( StringList menuItems ) {
        testDefinitionSteps.addStep("Check user menu items");
        userMenuSteps.checkUserMenuItems( menuItems.getList() );
        testDefinitionSteps.logEvidence(STR."The user menu items has the expected items: \{menuItems}",
            "The user menu items has the expected items (check previous logs)", true);
    }

    @Given( "the \"$menuItem\" menu item is selected on the user menu panel" )
    @When( "the user clicks on the \"$menuItem\" menu item on the user menu panel" )
    public void clickOnMenuItem( String menuItem ) {
        testDefinitionSteps.addStep(STR."Click on the menu item: \{menuItem}");
        userMenuSteps.clickOnMenuItem( menuItem );
        testDefinitionSteps.logEvidence(STR."Menu item \{menuItem} was clicked successfully",
            "The menu item was clicked successfully (check previous logs)", true);
    }

    @When( "the user opens the main user menu" )
    public void openMainUserMenu() {
        testDefinitionSteps.addStep("Open the main user menu");
        userMenuSteps.moveMouseToMenu();
        testDefinitionSteps.logEvidence("User opened the main user menu successfully",
            "User opened the main user menu successfully (check previous logs)", true);
    }

}
