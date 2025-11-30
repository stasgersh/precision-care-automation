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
package com.ge.onchron.verif.whatsteps.tabs.timeline.sidebar;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

import com.ge.onchron.verif.converters.types.ColorString;
import com.ge.onchron.verif.howsteps.SelectLabelPanelSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.howsteps.detailedPatientData.SidebarSteps;
import com.ge.onchron.verif.model.Badge;
import com.ge.onchron.verif.model.BannerNotification;
import com.ge.onchron.verif.model.DetailedImportantSection;
import com.ge.onchron.verif.model.DetailedSidebarHeader;
import com.ge.onchron.verif.model.StringList;
import com.ge.onchron.verif.model.Table;
import com.ge.onchron.verif.model.detailedDataItems.DetailedDataItem;
import net.thucydides.core.annotations.Steps;

public class Sidebar {

    @Steps
    private SidebarSteps sidebarSteps;

    @Steps
    private SelectLabelPanelSteps labelPanelSteps;
    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();


    @Given( "the sidebar header contains the following data: $headerData" )
    @Then( "the sidebar header contains the following data: $headerData" )
    public void checkSidebarHeaderData( DetailedSidebarHeader headerData ) {
        testDefinitionSteps.addStep(STR."Check sidebar header data.");
        sidebarSteps.checkSidebarHeaderData(headerData);
        testDefinitionSteps.logEvidence(STR."Sidebar header contains the following data:\n\{headerData}",
                "Sidebar data matches expected (See previous logs)", true);
    }

    @Given( "the sidebar header contains the following badges: $labels" )
    @Then( "the sidebar header contains the following badges: $labels" )
    public void checkBadgesOnSidebarHeader( List<Badge> badges ) {
        testDefinitionSteps.addStep( "Check badges in sidebar header" );
        sidebarSteps.checkBadgesOnSidebarHeader( badges );
        badges.forEach( b -> testDefinitionSteps.logEvidence( STR."The sidebar header contains badge \{b}",
                "Badge was present in the sidebar header (see previous logs)", true, false ) );
        testDefinitionSteps.logEvidence( "All above badges were found in sidebar header", "All badges were found (see previous logs)", true );
    }

    @Given( "the sidebar header does not contain any badges" )
    @Then( "the sidebar header does not contain any badges" )
    public void checkNoBadgesOnSidebarHeader() {
        testDefinitionSteps.addStep( "Check badges in sidebar header" );
        sidebarSteps.checkNoBadgesOnSidebarHeader();
        testDefinitionSteps.logEvidence( "The sidebar header does not contain any badges",
                "the sidebar header did not contain any badges (see previous logs)", true );
    }

    @Given( "the sidebar header contains the following labels with the previously saved color: $labels" )
    @Then( "the sidebar header contains the following labels with the previously saved color: $labels" )
    public void checkLabelTextsAndColorsOnSidebarHeader( StringList labels ) {
        testDefinitionSteps.addStep( "Check label texts and colors on sidebar header" );
        List<String> originalLabels = new ArrayList<>(labels.getList());
        sidebarSteps.checkLabelTextsAndColorsOnSidebarHeader( labels.getList() );
        originalLabels.forEach( l -> testDefinitionSteps.logEvidence( "The sidebar header contains the following label with color " +
                STR."matching the stored one:\{l}",
                "Label was present (see previous logs)", true, false));
        testDefinitionSteps.logEvidence( "All above labels were found in sidebar header and had expected color",
                "All labels were found (see previous logs)", true );
    }

    @Given( "the sidebar contains the following \"$tableTitle\" table: $table" )
    @Then( "the sidebar contains the following \"$tableTitle\" table: $table" )
    public void checkSidebarTable( String tableTitle, ExamplesTable table ) {
        testDefinitionSteps.addStep( STR."Check sidebar table \{tableTitle}" );
        Table expectedTable = new Table( tableTitle, table.getRows() );
        sidebarSteps.checkDataTable( expectedTable, false );
        testDefinitionSteps.logEvidence( STR."the sidebar contains the following table \{tableTitle} with following content: \{expectedTable}",
                "The table was found and content was matching (see previous logs)", true);
    }

    @Then( "the sidebar contains the following sorted \"$tableTitle\" table: $table" )
    public void checkSortedSidebarTable( String tableTitle, ExamplesTable table ) {
        testDefinitionSteps.addStep( STR."Check sidebar table \{tableTitle}" );
        Table expectedTable = new Table( tableTitle, table.getRows() );
        sidebarSteps.checkDataTable( new Table( tableTitle, table.getRows() ), true );
        testDefinitionSteps.logEvidence( STR."the sidebar contains the following sorted table \{tableTitle} with following content: \{expectedTable}",
                "The table was found and sorted content was matching (see previous logs)", true);
    }

    @Given( "the \"$tableTitle\" table is sorted by \"$columnName\" column which has {a|an} \"$arrowDirection\" pointing arrow" )
    @Then( "the \"$tableTitle\" table is sorted by \"$columnName\" column which has {a|an} \"$arrowDirection\" pointing arrow" )
    public void checkSortParamInSidebarTable( String tableTitle, String columnName, String arrowDirection ) {
        testDefinitionSteps.addStep( STR."Check sort parameter in sidebar table \{tableTitle}" );
        sidebarSteps.checkSortParamInTable( tableTitle, columnName, arrowDirection );
        testDefinitionSteps.logEvidence( STR."Table \{tableTitle} is sorted by \{columnName}", "Sort parameter is as expected (see previous logs)", true, false );
        testDefinitionSteps.logEvidence( STR."Arrow on column \{columnName} is pointing \{arrowDirection}", "Sort direction is as expected (see previous logs)", true );
    }

    @When( "the user clicks on the \"$columnName\" column header in \"$tableTitle\" table" )
    public void clickOnColumnHeaderInSidebarTable( String columnName, String tableTitle ) {
        testDefinitionSteps.addStep( STR."Click on column \{columnName} header in table \{tableTitle}" );
        sidebarSteps.clickOnHeaderItemInTable( tableTitle, columnName );
        testDefinitionSteps.logEvidence( "Column header was clicked successfully", "Column header was clicked without errors", true );
    }

    @Then( "the following rows $isHighlighted highlighted with \"$color\" color in the \"$tableTitle\" table: $table" )
    public void checkTableRowsAreHighlighted( Boolean isHighlighted, ColorString color, String tableTitle, ExamplesTable table ) {
        testDefinitionSteps.addStep( STR."Check highlighted rows in \{tableTitle} table" );
        sidebarSteps.checkTableRowsAreHighlighted( isHighlighted, color.getHex(), new Table( tableTitle, table.getRows() ) );
        String rows = table.getRows().stream().map( Object::toString ).collect( Collectors.joining( "\n" ) );
        testDefinitionSteps.logEvidence( STR."Check the following rows are \{isHighlighted ? "" : "not"} highlited with \{Color.decode(color.getHex())} color:\n \{rows}",
                "All rows had expected color (see previous logs)", true);
    }

    @Then( "the following texts $isBold \"bold\" in the \"$tableTitle\" table: $table" )
    public void checkTableTextIsBold( Boolean isBold, String tableTitle, ExamplesTable table ) {
        String texts = table.getRows().stream().map( Object::toString ).collect( Collectors.joining( "\\n" ) );
        testDefinitionSteps.addStep( STR."Check boldness of following texts in the '\{tableTitle}' table:\n\{texts}" );
        sidebarSteps.checkTableTextIsBold( isBold, tableTitle, table );
        testDefinitionSteps.logEvidence( STR."Texts are \{isBold ? "" : "not"} bold", "Text boldness matched expected (see previous logs)", true );
    }

    @Then( "there is no visible table in the \"$tableTitle\" section" )
    public void verifyTableNotDisplayed( String tableTitle ) {
        testDefinitionSteps.addStep( STR."Check visible tables in \{tableTitle} section" );
        sidebarSteps.verifyTableNotDisplayed( tableTitle );
        testDefinitionSteps.logEvidence( STR."There are no visible tables in the \{tableTitle} section", "No tables are visible (see previous logs)", true );
    }

    @Given( "the sidebar $isDisplayed displayed" )
    @Then( "the sidebar $isDisplayed displayed" )
    public void checkSidebarVisibility( Boolean isDisplayed ) {
        testDefinitionSteps.addStep(STR."Check that sidebar is \{isDisplayed ? "" : "not"} displayed");
        sidebarSteps.checkSidebarVisibility( isDisplayed );
        testDefinitionSteps.logEvidence(STR."Sidebar is \{isDisplayed ? "" : "not"} displayed",
                STR."Sidebar visibility is \{isDisplayed}", true);
    }

    @Then( "the empty information placeholder is displayed on the sidebar with the following text: $texts" )
    public void checkEmptyStateInfo( ExamplesTable params ) {
        testDefinitionSteps.addStep( "Check empty state info" );
        sidebarSteps.checkEmptyStateInfo( params );
        testDefinitionSteps.logEvidence( STR."The empty information placeholder is displayed on the sidebar with the following text: \{
                params.getRows().stream().map( Object::toString ).collect( Collectors.joining( "\n" ) )}",
                "The placeholder text matched expected (see previous logs)", true );
    }

    @When( "the user clicks on the Close button on the sidebar" )
    public void clickOnCloseButton() {
        testDefinitionSteps.addStep( "Click on the Close button on the sidebar" );
        sidebarSteps.clickOnCloseButton();
        testDefinitionSteps.logEvidence( "Close button was clicked successfully", "Button clicked without errors (see previous logs)", true );
    }

    @Given( "the sidebar content contains the following data: $table" )
    @Then( "the sidebar content contains the following data: $table" )
    public void checkSidebarData( List<DetailedDataItem> sidebarItems ) {
        testDefinitionSteps.addStep("Check sidebar content.");
        sidebarSteps.verifyAllDetailedData( sidebarItems );
        sidebarItems.forEach(si -> testDefinitionSteps.logEvidence(STR."Item \{si} is present on sidebar",
                "Item is present (See previous logs)", true, false));
        testDefinitionSteps.logEvidence("All items from above are present in sidebar",
                "All items were found (See previous logs)", true);
    }

    @Given( "the following data was displayed on the sidebar: $table" )
    @Then( "the following data was displayed on the sidebar: $table" )
    public void checkSidebarContainsData( List<DetailedDataItem> sidebarItems ) {
        testDefinitionSteps.addStep("Check sidebar content.");
        sidebarSteps.verifyPartialDetailedData( sidebarItems );
        sidebarItems.forEach(si -> testDefinitionSteps.logEvidence(STR."Item \{si} is present on sidebar",
                "Item is present (See previous logs)", true, false));
        testDefinitionSteps.logEvidence("All items from above are present in sidebar",
                "All items were found (See previous logs)", true);
    }

    @Then( "the sidebar contains the following adherence dates: $adherenceDateTable" )
    public void checkAdherenceDates( ExamplesTable adherenceDateTable ) {
        testDefinitionSteps.addStep( "Checking adherence date table on sidebar" );
        sidebarSteps.checkAdherenceDatesOnSidebar( adherenceDateTable );
        testDefinitionSteps.logEvidence( STR."Sidebar contains the following adherence dates\{adherenceDateTable.getRows()}", STR."Sidebar contains the following adherence dates\{adherenceDateTable.getRows()}", true );
    }

    @When( "the user clicks on the \"$buttonName\" button at the following data on the sidebar: $sidebarItem" )
    public void clickButtonOnSidebarItem( String buttonText, DetailedDataItem sidebarItem ) {
        testDefinitionSteps.addStep( STR."Click \{buttonText} button at the following item on the sidebar: \{sidebarItem} " );
        sidebarSteps.clickButtonOnItem( sidebarItem, buttonText );
        testDefinitionSteps.logEvidence(STR."Button \{buttonText} clicked successfully",
                "Button found and clicked without errors", true);
    }

    @When( "the user clicks on the \"$buttonMoreOrLess\" button under the AI generated text on the sidebar" )
    public void clickShowMoreUnderAIText( String buttonMoreOrLess ) {
        testDefinitionSteps.addStep( STR."Click \"\{buttonMoreOrLess}\" button under AI generated text on the sidebar" );
        sidebarSteps.clickOnAIShowMoreOrLess( buttonMoreOrLess );
        testDefinitionSteps.logEvidence(STR."the user clicked on the \{buttonMoreOrLess} button under the AI generated text on the sidebar successfully",
                STR."the user clicked on the \{buttonMoreOrLess} button under the AI generated text on the sidebar", true);
    }

    @Then( "there is no any other patient data on the sidebar" )
    public void checkNoDataOnSidebar() {
        testDefinitionSteps.addStep( "Check patient data on the sidebar" );
        sidebarSteps.checkDetailedPatientDataNotAvailable();
        testDefinitionSteps.logEvidence( "There is no other patient data on the sidebar", "No patient data on the sidebar (see previous logs)", true );
    }

    @Given( "the following sidebar item was clicked: $sidebarItem" )
    @When( "the user clicks on the following sidebar item: $sidebarItem" )
    public void clickOnSidebarItem( DetailedDataItem sidebarItem ) {
        testDefinitionSteps.addStep( STR."Click on the following sidebar item: \{sidebarItem.getName()}" );
        sidebarSteps.clickOnDataItem( sidebarItem );
        testDefinitionSteps.logEvidence( STR."Sidebar item \{sidebarItem.getName()} clicked successfully", "Clicked without errors", true );
    }

    @When( "the user clicks on the value of the following sidebar item: $sidebarItem" )
    public void clickOnSidebarItemValue( DetailedDataItem sidebarItem ) {
        testDefinitionSteps.addStep( STR."Click on the following sidebar item value:\nName:\{sidebarItem.getName()}\nValue:\{sidebarItem.getValue()}" );
        sidebarSteps.clickOnDataItemValue( sidebarItem );
        testDefinitionSteps.logEvidence( STR."Sidebar item value \{sidebarItem.getName()} clicked successfully", "Clicked without errors", true );
    }

    @Given( "the \'Mark as important\' section is displayed as: $importantSection" )
    @Then( "the \'Mark as important\' section is displayed as: $importantSection" )
    public void checkEventIsMarkedAsImportant( DetailedImportantSection importantSection ) {
        testDefinitionSteps.addStep( "Check 'Mark as import' section" );
        sidebarSteps.checkEventIsMarkedAsImportantOnSideBar( importantSection );
        testDefinitionSteps.logEvidence( STR."the 'Mark as important' section is displayed as: \{importantSection}",
                "the 'Mark as important' section matches expected", true);
    }

    @Given( "the \'$markOrMarked as important\' button was clicked on the sidebar" )
    @When( "the user clicks \'$markOrMarked as important\' button on the sidebar" )
    public void clickMarkAsImportantButton( String markOrMarked ) {
        testDefinitionSteps.addStep( STR."Click '\{markOrMarked} as important' button on the sidebar" );
        sidebarSteps.clickMarkAsImportant( markOrMarked );
        testDefinitionSteps.logEvidence( STR."'\{markOrMarked} as important' button clicked successfully", "Clicked without errors", true );
    }

    @When( "the user clicks on the 'Label' button on the sidebar header" )
    public void clickOnLabelButton() {
        testDefinitionSteps.addStep( "Click on the 'Label' button on the sidebar header" );
        sidebarSteps.clickOnLabelButton();
        testDefinitionSteps.logEvidence( STR."Label' button clicked successfully", "Clicked without errors", true );
    }

    @When( "the user adds the following {label|labels} to the opened event: $labels" )
    public void addLabelToTheOpenedEvent( StringList labels ) {
        testDefinitionSteps.addStep( STR."Add following labels to the opened event:\n\{String.join( ",\n", labels.getList())}" );
        sidebarSteps.clickOnLabelButton();
        labelPanelSteps.clickOnLabels( labels.getList() );
        testDefinitionSteps.logEvidence( "All labels added successfully to the opened event", "All labels were added (see previous logs)", true );
    }

    @Given( "notification banner $isAvailable available in the sidebar header" )
    @Then( "notification banner $isAvailable available in the sidebar header" )
    public void checkNotificationBannersInSidebarHeader( Boolean doesContain ) {
        testDefinitionSteps.addStep( "Check notification banners in sidebar header" );
        sidebarSteps.isNotificationBannerAvailableInSidebarHeader( doesContain );
        testDefinitionSteps.logEvidence( STR."Notification banner \{doesContain ? "" : "not"} available in the sidebar header",
                STR."Notification banner was \{doesContain ? "" : "not"} available in the sidebar header (see previous logs)", true);
    }

    @Then( "the sidebar header contains the following notification {banner|banners}: $bannerNotifications" )
    public void checkNotificationBannersInSidebarHeader( List<BannerNotification> bannerNotifications ) {
        testDefinitionSteps.addStep( "Check notifications in the sidebar header" );
        sidebarSteps.checkNotificationBannersInSidebarHeader( bannerNotifications );
        bannerNotifications.forEach( b -> testDefinitionSteps.logEvidence( STR."The sidebar header contains the following notification banner:\n \{b}",
                "Expected banner was found", true, false ) );
        testDefinitionSteps.logEvidence( "All notification banners from above were found", "All banners are present", true );
    }

    @When( "the user clicks on the button on the following notification banner on the sidebar header: $bannerNotification" )
    public void clickButtonOnNotificationBannerInSidebarHeader( BannerNotification bannerNotification ) {
        testDefinitionSteps.addStep( STR."Click on the button on the following notification banner on the sidebar header: \{bannerNotification}" );
        sidebarSteps.clickButtonOnNotificationBannerInSidebarHeader( bannerNotification );
        testDefinitionSteps.logEvidence( "Button was clicked successfully", "Clicked without errors", true );
    }

    @When( "the user moves the mouse over on the following sidebar badge: $labels" )
    public void moveMouseOnTheSidebarBadge( Badge badge ) {
        testDefinitionSteps.addStep( STR."Move the mouse on the Sidebar badge." );
        sidebarSteps.moveMouseOnTheSidebarBadge( badge );
        testDefinitionSteps.logEvidence( "The user moved the mouse over on the sidebar badge", "The user successfully moved the mouse over on the sidebar badge", true );
    }

    @When( "the user moves the mouse on the following data on the sidebar: $table" )
    public void moveMouseOnTheSidebarData( DetailedDataItem sidebarItem ) {
        testDefinitionSteps.addStep( "Move the mouse on the Sidebar data" );
        sidebarSteps.moveMouseOnTheSidebarData( sidebarItem );
        testDefinitionSteps.logEvidence( "The user moved the mouse over on the sidebar data",
                "The user successfully moved the mouse over on the sidebar data", true );
    }

    @Then( "the \"$button\" button is disabled on the Sidebar header" )
    public void checkButtonIsDisabledOnSidebarHeader( String button ) {
        testDefinitionSteps.addStep( STR."Checking \{button} is disabled on the Sidebar header" );
        sidebarSteps.checkButtonIsDisabledOnTheSidebarHeader( button );
        testDefinitionSteps.logEvidence( STR."The \{button} is disabled on the Sidebar header",
                STR."The \{button} was disabled on the Sidebar header", true );
    }

}
