/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2021, 2021, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.whatsteps.tabs.timeline.toolbar;

import java.util.List;

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.howsteps.TimelineToolbarSteps;
import com.ge.onchron.verif.model.CheckboxModel;
import com.ge.onchron.verif.model.FilterDataCheckbox;
import com.ge.onchron.verif.model.StringList;
import net.thucydides.core.annotations.Steps;

import static com.ge.onchron.verif.model.enums.ZoomDirection.ZOOM_IN;
import static com.ge.onchron.verif.model.enums.ZoomDirection.ZOOM_OUT;
import static joptsimple.internal.Strings.EMPTY;

public class TimelineToolbar {

    @Steps
    private TimelineToolbarSteps timelineToolbarSteps;
    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Given( "the timeline toolbar section $isDisplayed displayed" )
    @Then( "the timeline toolbar section $isDisplayed displayed" )
    public void isTimelineToolbarDisplayed( Boolean isDisplayed ) {
        testDefinitionSteps.addStep( "Check timeline toolbar visibility" );
        timelineToolbarSteps.checkTimelineToolbarVisibility( isDisplayed );
        testDefinitionSteps.logEvidence( STR."The timeline toolbar is: \{isDisplayed ? EMPTY : "not"} visible",
                "The timeline toolbar is visible (check previous logs)", true );

    }

    @Given( "the user has opened the timeline filter modal" )
    @When( "the user opens the timeline filter modal" )
    @Alias( "the user opens the timeline filter modal again" )
    public void openFilterData() {
        testDefinitionSteps.addStep( "Open the timeline filter modal" );
        timelineToolbarSteps.openFilterData();
        testDefinitionSteps.logEvidence( "Successfully opened the filter data modal",
                "Successfully opened the filter data modal (without errors)", true );
    }

    @Given( "the user has closed the timeline filter modal" )
    @When( "the user closes the timeline filter modal" )
    public void closeFilterData() {
        testDefinitionSteps.addStep( "Close the timeline filter modal" );
        timelineToolbarSteps.closeFilterModal();
        testDefinitionSteps.logEvidence( "Successfully closed the filter data modal",
                "Successfully closed the filter data modal (without errors)", true );
    }

    @Given( "the user has set the following timeline filter {option|options}: $checkbox" )
    @When( "the user set the following timeline filter {option|options}: $checkbox" )
    public void setTimelineFilter( List<FilterDataCheckbox> checkboxes ) {
        testDefinitionSteps.addStep( STR."Set the following timeline filters: \{checkboxes}" );
        timelineToolbarSteps.setTimelineFiltersOnFilterModal( checkboxes );
        timelineToolbarSteps.closeFilterModal();
        testDefinitionSteps.logEvidence( "The timeline filters were selected successfully",
                "Successfully selected the timeline filters (without errors)", true );
    }

    @When( "the user clicks \"$linkText\" on the Filter modal" )
    public void clickLinkOnFilterModal( String linkText ) {
        testDefinitionSteps.addStep( STR."Clicking \"\{linkText}\" on the Filter modal" );
        timelineToolbarSteps.clickLinkOnFilterModal( linkText );
        testDefinitionSteps.logEvidence( STR."Clicked \"\{linkText}\" on the filter modal",
                STR."Successfully clicked \"\{linkText}\" on the filter modal", true );
    }

    @Then( "the following groups are available on the filter modal: $groups" )
    public void checkFilterModalGroups( StringList groups ) {
        timelineToolbarSteps.checkFilterModalGroups( groups.getList() );
    }

    @Then( "the following notification is displayed on the Timeline filter modal: \"$disabledNotification\"" )
    public void checkFilterDisabledNotification( String disabledNotification ) {
        testDefinitionSteps.addStep( STR."Check disabled filter notification is displayed on Timeline Filter modal" );
        timelineToolbarSteps.checkDisabledFilterNotification( disabledNotification );
        testDefinitionSteps.logEvidence( STR."Disabled filter notification is displayed on the Timeline Filter modal",
                "Disabled filter notification is displayed on the Timeline Filter modal", true );
    }

    @Given( "the following timeline filter {option|options} $isDisplayed displayed on the filter modal: $listItems" )
    @Then( "the following timeline filter {option|options} $isDisplayed displayed on the filter modal: $listItems" )
    public void checkFilterOptionsOnFilterModal( Boolean isDisplayed, List<FilterDataCheckbox> listItems ) {
        //todo move placeholder replace from converter
        testDefinitionSteps.addStep( STR."Check filter options \{listItems.toString().replaceAll( "\\d{5,}", "<<create_label_timestamp>>" )} on filter modal visibility" );
        timelineToolbarSteps.checkFilterOptionsOnFilterModal( isDisplayed, listItems );
        testDefinitionSteps.logEvidence( STR."The filter options on the filter modal are : \{isDisplayed ? EMPTY : "not"} visible",
                "The filter options on the filter modal are visible (check previous logs)", true );
    }

    @Given( "only the following timeline filter options are displayed on the filter modal in \"$groupName\" group: $listItems" )
    @Then( "only the following timeline filter options are displayed on the filter modal in \"$groupName\" group: $listItems" )
    public void checkFilterOptionsOnFilterModalStrict( String groupName, List<CheckboxModel> listItems ) {
        testDefinitionSteps.addStep( STR."Check timeline filters options '\{listItems}' are displayed on the filter modal in '\{groupName}'" );
        timelineToolbarSteps.checkFilterOptionsOnFilterModalStrict( groupName, listItems );
        testDefinitionSteps.logEvidence( STR."The filter options in modal \{groupName} are displayed as expected",
                "The filter options on the filter modal are visible (check previous logs)", true );
    }

    @When( "the user types the following text into the search field on filter modal: \"$searchText\"" )
    public void typeIntoSearchFiltersField( String searchText ) {
        testDefinitionSteps.addStep( STR."Insert the following text: \{searchText} into the search field on filter modal" );
        timelineToolbarSteps.typeIntoSearchLabelField( searchText );
        testDefinitionSteps.logEvidence( STR."The text \{searchText} was typed successfully",
                "Successfully typed the text in the search fields (without errors)", true );
    }

    @Then( "the following message is displayed on the Filter modal in the \"$filterGroupName\" group: \"$text\"" )
    public void checkMessageInFilterGroup( String filterGroupName, String text ) {
        testDefinitionSteps.addStep( STR."Check message in filter group\{filterGroupName} visibility" );
        timelineToolbarSteps.checkMessageInFilterGroup( filterGroupName, text );
        testDefinitionSteps.logEvidence( STR."The message \{text} is displayed as expected in filter group name \{filterGroupName}",
                "The message displayed as expected (check previous logs)", true );
    }

    @Given( "the filter counter icon on the Filter button is displayed with number: $number" )
    @Then( "the filter counter icon on the Filter button is displayed with number: $number" )
    public void checkFilterCounterIcon( int number ) {
        testDefinitionSteps.addStep( "Check filter counter icon number" );
        timelineToolbarSteps.checkFilterCounterIcon( number );
        testDefinitionSteps.logEvidence( STR."The filter counter icon has \{number} number as expected",
                "The filter counter icon has expected number (check previous logs)", true );
    }

    @Given( "the following filter {badge|badges} {is|are} available in the filter list on the timeline toolbar: $filterNameList" )
    @Then( "the following filter {badge|badges} {is|are} available in the filter list on the timeline toolbar: $filterNameList" )
    public void checkFiltersInFilterList( StringList filterNameList ) {
        testDefinitionSteps.addStep( STR."Check filters in filter list \{filterNameList}" );
        timelineToolbarSteps.checkFilterBadgesInFilterList( filterNameList.getList() );
        testDefinitionSteps.logEvidence( STR."The filters in \{filterNameList} available as expected",
                STR."The filters in \{filterNameList} available as expected (check previous logs)", true );
    }

    @Given( "the filter counter icon on the Filter button $isDisplayed displayed" )
    @Then( "the filter counter icon on the Filter button $isDisplayed displayed" )
    public void checkFilterCounterIconVisibility( Boolean isDisplayed ) {
        testDefinitionSteps.addStep( "Check filter counter icon visibility" );
        timelineToolbarSteps.checkFilterCounterIconVisibility( isDisplayed );
        testDefinitionSteps.logEvidence( STR."The filter counter icon is : \{isDisplayed ? EMPTY : "not"} visible as expected",
                "The filter counter icon is visible (check previous logs)", true );
    }

    @Then( "filter badge $isDisplayed available in the filter list on the timeline toolbar" )
    public void checkFilterBadgeVisibilityOnFilterList( Boolean isDisplayed ) {
        testDefinitionSteps.addStep( "Check filter badge icon visibility on filter list" );
        timelineToolbarSteps.checkFilterBadgeVisibilityOnFilterList( isDisplayed );
        testDefinitionSteps.logEvidence( STR."The filter badge is: \{isDisplayed ? EMPTY : "not"} visible as expected",
                "The filter badge icon is visible (check previous logs)", true );
    }

    @Then( "a button $isDisplayed displayed on the timeline toolbar to see all applied filters" )
    public void checkMoreFiltersButtonVisibility( Boolean isDisplayed ) {
        testDefinitionSteps.addStep( "Check more filters button visibility" );
        timelineToolbarSteps.checkMoreFiltersButtonVisibility( isDisplayed );
        testDefinitionSteps.logEvidence( STR."The more filters button is: \{isDisplayed ? EMPTY : "not"} visible as expected",
                "The more filters button is visible (check previous logs)", true );
    }

    @When( "the user opens the more filters modal to see all applied filters" )
    public void openMoreFiltersModal() {
        testDefinitionSteps.addStep( "Open more filters modal" );
        timelineToolbarSteps.openMoreFiltersModal();
        testDefinitionSteps.logEvidence( "The open more filters modal was clicked successfully and shown all applied filters",
                "Successfully clicked the open more filters modal (without errors)", true );
    }

    @When( "the user clicks on the 'X' button on the below filter badges in the filter list on the timeline toolbar: $filterNameList" )
    public void removeFilterFromAppliedFilters( StringList filterNameList ) {
        testDefinitionSteps.addStep( STR."Click 'X' button below filter badges on timeline toolbar \{filterNameList}" );
        timelineToolbarSteps.removeFilterFromAppliedFilters( filterNameList.getList() );
        testDefinitionSteps.logEvidence( "The 'X' button clicked successfully and removed applied filters",
                "Successfully clicked the 'X' button and removed applied filters (without errors)", true );
    }

    @When( "the user clicks on the 'Reset filters' button in the filter list modal on the timeline toolbar" )
    public void resetFiltersOnAppliedFiltersModal() {
        testDefinitionSteps.addStep( "Click on the 'Reset filters' button" );
        timelineToolbarSteps.resetFiltersOnMoreFiltersModal();
        testDefinitionSteps.logEvidence( "The 'Rest filters' button clicked successfully and reset the applied filters",
                "Successfully clicked the 'Rest filters' button and reset applied filters (without errors)", true );
    }

    @When( "the user clicks on the \"Reset filters\" button on the filter modal" )
    public void clickOnResetFilters() {
        testDefinitionSteps.addStep( "Click on the 'Reset filters' button" );
        timelineToolbarSteps.clickOnResetFiltersOnFilterModal();
        testDefinitionSteps.logEvidence( "The 'Rest filters' button clicked successfully and reset the applied filters",
                "Successfully clicked the 'Rest filters' button and reset applied filters (without errors)", true );
    }

    @Given( "the timeline is zoomed in to max resolution" )
    @When( "the user zooms in the timeline to max resolution" )
    public void zoomInMax() {
        testDefinitionSteps.addStep( "Zoom in the timeline to max resolution" );
        timelineToolbarSteps.zoomInMax();
        testDefinitionSteps.logEvidence( "Zoom in was done successfully",
                "Zoom in was done successfully (without errors)", true );
    }

    @Given( "the 'Zoom in' button is clicked by the user $count times" )
    @When( "the user clicks on the 'Zoom in' button $count times" )
    public void zoomIn( int count ) {
        testDefinitionSteps.addStep( STR."Click on the zoom in button \{count} times" );
        timelineToolbarSteps.zoom( ZOOM_IN, count );
        testDefinitionSteps.logEvidence( STR."Zoom in was done \{count} times successfully",
                "Zoom in was done successfully (without errors)", true );
    }

    @When( "the user zooms out the timeline until it is possible" )
    public void zoomOutMax() {
        testDefinitionSteps.addStep( "Zoom out the timeline to max resolution" );
        timelineToolbarSteps.zoomOutMax();
        testDefinitionSteps.logEvidence( "Zoom out was done successfully",
                "Zoom out was done successfully (without errors)", true );
    }

    @When( "the user clicks on the 'Zoom out' button $count times" )
    public void zoomOut( int count ) {
        testDefinitionSteps.addStep( STR."Click on the zoom out button \{count} times" );
        timelineToolbarSteps.zoom( ZOOM_OUT, count );
        testDefinitionSteps.logEvidence( STR."Zoom out was done \{count} times successfully",
                "Zoom out was done successfully (without errors)", true );
    }

    @Given( "the full time range is displayed on the timeline" )
    @When( "the user clicks on the 'All time' button on the timeline toolbar" )
    public void setFullTimeRange() {
        testDefinitionSteps.addStep( "Set full time range. (Click 'All time' button on the timeline toolbar)" );
        timelineToolbarSteps.setFullTimeRange();
        testDefinitionSteps.logEvidence( "Full time range set successfully",
                "Button 'All time' was successfully pressed", true );
    }

    @When( "the user clicks on the 'Last 30 days' button on the timeline toolbar" )
    public void setLast30daysTimeRange() {
        testDefinitionSteps.addStep( "Click on the 'Last 30 days' button on the timeline toolbar" );
        timelineToolbarSteps.setLast30daysTimeRange();
        testDefinitionSteps.logEvidence( "The 'Last 30 days' button was clicked successfully",
                "The 'Last 30 days' button was clicked successfully (without errors)", true );
    }

    @When( "the user clicks on the 'Last 90 days' button on the timeline toolbar" )
    public void setLast90daysTimeRange() {
        testDefinitionSteps.addStep( "Click on the 'Last 90 days' button on the timeline toolbar" );
        timelineToolbarSteps.setLast90daysTimeRange();
        testDefinitionSteps.logEvidence( "The 'Last 90 days' button was clicked successfully",
                "The 'Last 90 days' button was clicked successfully (without errors)", true );
    }

    @When( "the user moves the zoom slider by mouse to the right side" )
    public void moveZoomSliderByMouseToTheRightSide() {
        testDefinitionSteps.addStep( "Move the zoom slider by mouse to the right side" );
        timelineToolbarSteps.moveZoomSliderByMouseToRightSide();
        testDefinitionSteps.logEvidence( "The zoom slider was moved to the right successfully",
                "The zoom slider was moved to the right successfully (without errors)", true );
    }

    @When( "the user moves the zoom slider by mouse to the left side" )
    public void moveZoomSliderByMouseToTheLeftSide() {
        testDefinitionSteps.addStep( "Move the zoom slider by mouse to the left side" );
        timelineToolbarSteps.moveZoomSliderByMouseToLeftSide();
        testDefinitionSteps.logEvidence( "The zoom slider was moved to the left successfully",
                "The zoom slider was moved to the left successfully (without errors)", true );
    }

    @Given( "the zoom slider $isOnLeftSide moved to the left side" )
    @Then( "the zoom slider $isOnLeftSide moved to the left side" )
    @Alias( "the zoom slider $isOnLeftSide on the left side" )
    public void isZoomSliderOnLeftSide( Boolean isOnLeftSide ) {
        testDefinitionSteps.addStep( "Check if the zoom slider is on the left side" );
        timelineToolbarSteps.isZoomSliderOnLeftSide( isOnLeftSide );
        testDefinitionSteps.logEvidence( STR."The zoom slider button is: \{isOnLeftSide ? EMPTY : "not"} on the left side as expected",
                "The zoom slider is on the left side (check previous logs)", true );
    }

    @Then( "the zoom slider is in the middle of the zoom slider axis" )
    public void isZoomSliderInTheMiddle() {
        testDefinitionSteps.addStep( "Check if the zoom slider is on the middle" );
        timelineToolbarSteps.isZoomSliderInTheMiddle();
        testDefinitionSteps.logEvidence( "The zoom slider button is in the middle as expected",
                "The zoom slider is on the middle (check previous logs)", true );
    }

    @Then( "the zoom slider is moved to the right side" )
    public void isZoomSliderOnRightSide() {
        testDefinitionSteps.addStep( "Check if the zoom slider is on the right side" );
        timelineToolbarSteps.isZoomSliderOnRightSide();
        testDefinitionSteps.logEvidence( "The zoom slider button is on the right side as expected",
                "The zoom slider button is on the right side (check previous logs)", true );
    }

    @Given( "the user has selected the \"Hide Navigator\" checkbox on the timeline toolbar" )
    @When( "the user selects the \"Hide Navigator\" checkbox on the timeline toolbar" )
    public void hideMacroNavigator() {
        testDefinitionSteps.addStep( "Select the 'Hide Navigator' checkbox in the timeline toolbar" );
        timelineToolbarSteps.hideMacroNavigator();
        testDefinitionSteps.logEvidence( "The 'Hide Navigator' checkbox button was selected successfully",
                "Successfully selected the 'Hide Navigtor' checkbox (check previous logs)", true );
    }

    @When( "the user unselects the \"Hide Navigator\" checkbox on the timeline toolbar" )
    public void showMacroNavigator() {
        testDefinitionSteps.addStep( "Unselect the 'Hide Navigator' checkbox in the timeline toolbar" );
        timelineToolbarSteps.showMacroNavigator();
        testDefinitionSteps.logEvidence( "The 'Hide Navigator' checkbox button was unselected successfully",
                "Successfully unselected the 'Hide Navigtor' checkbox (check previous logs)", true );
    }

    @When( "the user resets the timeline settings using \"Reset to default view\" in the More menu" )
    public void resetTimelinesettings() {
        testDefinitionSteps.addStep( "Click on the 'Reset to default view' in the more menu" );
        timelineToolbarSteps.resetTimelineSettings();
        testDefinitionSteps.logEvidence( "The 'Reset to default view' button was clicked successfully",
                "Successfully clicked the 'Reset to default view' button (check previous logs)", true );
    }

    @Then( "the \"Hide navigator\" checkbox $isOrisNot selected on the timeline toolbar" )
    public void checkHideNavigatorCheckboxState( Boolean isChecked ) {
        testDefinitionSteps.addStep( "Check 'Hide Navigator' checkbox button state" );
        timelineToolbarSteps.checkHideNavigatorCheckbox( isChecked );
        testDefinitionSteps.logEvidence( STR."The 'Hide Navigator' button is: \{isChecked ? EMPTY : "not"} checked as expected",
                "The 'Hide Navigator' button checked (check previous logs)", true );
    }

    @Then( "the Zoom out button $isOrIsNot enabled" )
    public void checkZoomOutButtonEnabled(Boolean isEnabled) {
        testDefinitionSteps.addStep( "Check 'Zoom out' button state" );
        timelineToolbarSteps.checkZoomOutEnabled(isEnabled);
        testDefinitionSteps.logEvidence(
                STR."the Zoom out button in the Timeline toolbar is \{isEnabled ? "" : " not"} enabled",
                STR."the Zoom out button in the Timeline toolbar is \{isEnabled ? "" : " not"} enabled", true );
    }
}
