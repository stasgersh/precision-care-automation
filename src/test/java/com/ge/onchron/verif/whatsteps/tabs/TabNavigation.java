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
package com.ge.onchron.verif.whatsteps.tabs;

import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.ge.onchron.verif.howsteps.TabNavigationSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import net.thucydides.core.annotations.Steps;

public class TabNavigation {

    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Steps
    private TabNavigationSteps tabNavigationSteps;

    @Given( "the counter badge displays \"$counter\" on the \"$tabName\" tab" )
    @Then( "the counter badge displays \"$counter\" on the \"$tabName\" tab" )
    public void checkCounterBadgeOnTab( int counter, String tabName ) {
        testDefinitionSteps.addStep( STR."Check the counter badge on the \{tabName} tab" );
        tabNavigationSteps.checkCounterBadgeOnTab( counter, tabName );
        testDefinitionSteps.logEvidence( STR."The counter badge displayed on the tab has count '\{counter}'",
                "Counter matched expected (see previous logs)", true );
    }

    @Given( "the user navigated to the \"$tabName\" view" )
    @When( "the user navigates to the \"$tabName\" view" )
    @Aliases( values = {"the user clicks on the \"$tabName\" tab",
            "the \"$tabName\" view is selected"} )
    public void navigateToTab( String tabName ) {
        testDefinitionSteps.addStep( STR."Navigate to \{tabName} view." );
        tabNavigationSteps.navigateTo( tabName );
        testDefinitionSteps.logEvidence(
                STR."Tab \{tabName} is selected succesfully",
                STR."\{tabName} is selected without errors.", true );
    }

    @When( "the user clicks the \"X\" icon of the Response tab on the navigation bar" )
    public void closeResponseTab() {
        testDefinitionSteps.addStep( STR."Closing Response tab by clicking X on the navigation bar" );
        tabNavigationSteps.closeResponseTab();
        testDefinitionSteps.logEvidence( "The user clicked the \"X\" icon of the Response tab on the navigation bar", "The user clicked the \"X\" icon of the Response tab on the navigation bar", true );
    }

    @Then( "the \"$tabName\" tab $isOrIsNot visible on the navigation bar" )
    public void checkTabStateOnNavigationBar( String tabName, Boolean isDisplayed ) {
        testDefinitionSteps.addStep( STR."Checking that the \{tabName} tab is \{isDisplayed ? " visible" : " not visible"} on the navigation bar" );
        tabNavigationSteps.checkTabState( tabName, isDisplayed );
        testDefinitionSteps.logEvidence( STR."The \{tabName} tab is \{isDisplayed ? " visible" : " not visible"} on the navigation bar", STR."The \{tabName} tab is \{isDisplayed ? " visible" : " not visible"} on the navigation bar", true );
    }

    @When( "the user clicks on the \"$tabName\" view without waiting for it to load" )
    public void onlyNavigateToTab( String tabName ) {
        testDefinitionSteps.addStep( STR."Navigate to \{tabName} view without waiting for the load." );
        tabNavigationSteps.onlyNavigateTo( tabName );
        testDefinitionSteps.logEvidence(
                STR."Tab \{tabName} is selected succesfully",
                STR."\{tabName} is selected without errors.", true );
    }
}
