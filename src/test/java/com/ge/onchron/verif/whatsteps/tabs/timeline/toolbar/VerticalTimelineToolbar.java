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
package com.ge.onchron.verif.whatsteps.tabs.timeline.toolbar;

import java.util.List;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.ge.onchron.verif.converters.types.ColorString;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.howsteps.VerticalTimelineToolbarSteps;
import com.ge.onchron.verif.model.FilterDataCheckbox;
import net.thucydides.core.annotations.Steps;

import static com.ge.onchron.verif.model.enums.ZoomDirection.ZOOM_IN;
import static com.ge.onchron.verif.model.enums.ZoomDirection.ZOOM_OUT;
import static joptsimple.internal.Strings.EMPTY;

public class VerticalTimelineToolbar {

    @Steps
    private VerticalTimelineToolbarSteps verticalTimelineToolbarSteps;

    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Given( "the Vertical toolbar section $isDisplayed displayed" )
    @Then( "the Vertical toolbar section $isDisplayed displayed" )
    public void isVerticalToolbarDisplayed( Boolean isDisplayed ) {
        testDefinitionSteps.addStep("Check vertical toolbar visibility");
        verticalTimelineToolbarSteps.checkVerticalTimelineToolbarVisibility( isDisplayed );
        testDefinitionSteps.logEvidence(STR."The vertical toolbar is: \{isDisplayed ? EMPTY : "not"} visible",
                "The vertical toolbar is visible (check previous logs)", true);
    }

    @Given( "the user has clicked the \"$toolbarItem\" icon on the vertical toolbar" )
    @When( "the user clicks the \"$toolbarItem\" icon on the vertical toolbar" )
    public void userClickVerticalToolbarItem( String toolbarItem ) {
        testDefinitionSteps.addStep(STR."Click on the \{toolbarItem} icon on the vertical toolbar");
        verticalTimelineToolbarSteps.clicksVerToolbarItem( toolbarItem );
        testDefinitionSteps.logEvidence(STR."Successfully clicked on the on the \{toolbarItem} icon",
                "Successfully clicked on the icon (without errors)", true);
    }

    @Given( "the 'Zoom in' button on the vertical toolbar is clicked by the user $count times" )
    @When( "the user clicks the 'Zoom in' button on the vertical toolbar $count times" )
    public void zoomIn( int count ) {
        testDefinitionSteps.addStep(STR."Click on the 'Zoom in' button \{count} times on the vertical toolbar");
        verticalTimelineToolbarSteps.zoomOnVerticalToolbar( ZOOM_IN, count );
        testDefinitionSteps.logEvidence(STR."Successfully zoomed in  \{count} times",
                "Successfully clicked on the button to zoom in (without errors)", true);
    }

    @When( "the user clicks the 'Zoom out' button on the vertical toolbar $count times" )
    public void zoomOut( int count ) {
        testDefinitionSteps.addStep(STR."Click on the 'Zoom out' button \{count} times on the vertical toolbar");
        verticalTimelineToolbarSteps.zoomOnVerticalToolbar( ZOOM_OUT, count );
        testDefinitionSteps.logEvidence(STR."Successfully zoomed out  \{count} times",
                "Successfully clicked on the button to zoom out (without errors)", true);
    }

    @Given( "the full time range is set on the vertical timeline toolbar" )
    @When( "the user clicks on the 'All time' button on the vertical timeline toolbar" )
    public void setFullTimeRange() {
        testDefinitionSteps.addStep("Click on the 'All time' button on the vertical timeline toolbar");
        verticalTimelineToolbarSteps.setFullTimeRange();
        testDefinitionSteps.logEvidence("Clicked successfully on the 'All time' button",
                "Successfully clicked on the 'All time' button (without errors)", true);
    }

    @When( "the user clicks on the 'Last 30 days' button on the vertical timeline toolbar" )
    public void setLast30daysTimeRange() {
        testDefinitionSteps.addStep("Click on the 'Last 30 days' button on the vertical timeline toolbar");
        verticalTimelineToolbarSteps.setLast30daysTimeRange();
        testDefinitionSteps.logEvidence("Clicked successfully on the 'Last 30 days' button",
                "Successfully clicked on the 'Last 30 days' button (without errors)", true);
    }

    @When( "the user clicks on the 'Last 90 days' button on the vertical timeline toolbar" )
    public void setLast90daysTimeRange() {
        testDefinitionSteps.addStep("Click on the 'Last 90 days' button on the vertical timeline toolbar");
        verticalTimelineToolbarSteps.setLast90daysTimeRange();
        testDefinitionSteps.logEvidence("Clicked successfully on the 'Last 90 days' button",
                "Successfully clicked on the 'Last 90 days' button (without errors)", true);
    }

    @Given( "the user has set the following timeline filter options on the vertical toolbar: $listItems" )
    @When( "the user set the following timeline filter options on the vertical toolbar: $listItems" )
    public void setSwimlaneItemsInFilterData( List<FilterDataCheckbox> listItems ) {
        testDefinitionSteps.addStep(STR."Set the following timeline filter options on the vertical toolbar \{listItems}");
        verticalTimelineToolbarSteps.setTimelineFiltersOnFilterModal( listItems );
        testDefinitionSteps.logEvidence(STR."Successfully set the filter options \{listItems} on the vertical toolbar",
                "Successfully set the filter options  (without errors)", true);
    }

    @Then( "the filter counter icon on the Filter button $isDisplayed displayed on the vertical toolbar" )
    public void checkFilterCounterIconVisibility( Boolean isDisplayed ) {
        testDefinitionSteps.addStep("Check filter counter icon visibility on the vertical toolbar");
        verticalTimelineToolbarSteps.checkFilterCounterIconVisibility( isDisplayed );
        testDefinitionSteps.logEvidence(STR."The counter icon on the vertical toolbar is: \{isDisplayed ? EMPTY : "not"} visible",
                "The vertical toolbar counter icon is visible (check previous logs)", true);
    }

    @Then( "the filter counter icon on the Filter button on the vertical toolbar is displayed with number: $number" )
    public void checkFilterCounterIcon( int number ) {
        testDefinitionSteps.addStep(STR."Check filter counter icon is displayed with \{number} number");
        verticalTimelineToolbarSteps.checkFilterCounterIcon( number );
        testDefinitionSteps.logEvidence(STR."The filter counter icon on the vertical toolbar has the \{number} as expected",
                "The filter counter icon has expected number (check previous logs)", true);
    }

    @Then( "the filter counter color icon on the Filter button is displayed with color:$color" )
    public void checkFilterCounterIcon( ColorString color ) {
        testDefinitionSteps.addStep( "Check filter counter icon color" );
        verticalTimelineToolbarSteps.checkFilterCounterIconBadgeColor( color.getHex());
        testDefinitionSteps.logEvidence( STR."The filter counter icon color was \{color.getHex()} as expected",
                "The filter counter icon has expected color (check previous logs)", true );
    }

}
