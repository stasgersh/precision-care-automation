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
package com.ge.onchron.verif.howsteps.detailedPatientData;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.jbehave.core.model.ExamplesTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import com.ge.onchron.verif.model.Table;
import com.ge.onchron.verif.model.TableHeaderItem;
import com.ge.onchron.verif.model.detailedDataItems.BadgeIndicatorItem;
import com.ge.onchron.verif.model.detailedDataItems.CLPItem;
import com.ge.onchron.verif.model.detailedDataItems.DetailedDataItem;
import com.ge.onchron.verif.model.detailedDataItems.TableItem;
import com.ge.onchron.verif.model.enums.ArrowDirection;
import com.ge.onchron.verif.pages.elements.EmptyStateInfo;
import com.ge.onchron.verif.pages.sections.DetailedPatientData;

import static com.ge.onchron.verif.TestSystemParameters.ANY_VALUE_PLACEHOLDER;
import static com.ge.onchron.verif.TestSystemParameters.WINDOW_HANDLES_NUMBER;
import static net.serenitybdd.core.Serenity.setSessionVariable;

public abstract class DetailedPatientDataSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger( DetailedPatientDataSteps.class );

    protected abstract DetailedPatientData getDetailedPatientData();

    protected abstract void waitUntilLoadingSkeletonDisappears();

    public void checkDataTable( Table expectedTable, boolean isSorted ) {
        waitUntilLoadingSkeletonDisappears();
        Table observedTable = collectTable( expectedTable.getTitle() );

        assertTrue( STR."Observed table does not match to the expected one (sorted: \{isSorted
                        })\nExpected table: \{expectedTable}\nObserved table: \{observedTable}\n",
                observedTable.hasSameTitleAndRows( expectedTable, isSorted ) );
    }

    public void checkSingleDataTable( Table expectedTable, boolean isSorted ) {
        // Check single table which does not have table title (e.g. Comorbidities, Allergies and intolerances)
        waitUntilLoadingSkeletonDisappears();
        List<Table> foundTables = getDetailedPatientData().getTables();
        LOGGER.info( STR."Found \{foundTables.size()} table(s)" );
        assertThat( "Only one table is expected in this step", foundTables.size(), is( equalTo( 1 ) ) );
        assertTrue(
                STR."Observed Completed medications table does not match to the expected one\n Expected table: \{expectedTable}\n Observed table: \{foundTables.getFirst()}\n",
                foundTables.getFirst().hasSameRows( expectedTable, isSorted ) );
    }

    public void checkSortParamInTable( String expectedTableTitle, String expHeaderItemName, String arrowDirection ) {
        waitUntilLoadingSkeletonDisappears();
        Table requiredTable = collectTable( expectedTableTitle );
        checkSortParamOnTable( requiredTable, expHeaderItemName, arrowDirection );
    }

    public void checkSortParamOnTable( Table observedTable, String expHeaderItemName, String arrowDirection ) {
        ArrowDirection expArrowDirection = ArrowDirection.valueOf( arrowDirection.toUpperCase() );

        List<TableHeaderItem> headerItems = observedTable.getHeaderItems();

        List<TableHeaderItem> sortedHeaderItems = headerItems.stream()
                                                             .filter( TableHeaderItem::usedForSorting )
                                                             .collect( Collectors.toList() );
        LOGGER.info( STR."Sorted headers: \{sortedHeaderItems}" );
        assertEquals( STR."More than one table header item is used for sorting: \{sortedHeaderItems}", 1, sortedHeaderItems.size() );
        TableHeaderItem observedHeaderItem = sortedHeaderItems.getFirst();
        LOGGER.info( STR."Observed header item: \{observedHeaderItem.getName()}" );
        assertEquals( String.format( STR."Table is sorted by '\{observedHeaderItem.getName()}' header item instead of '\{expHeaderItemName}'" ),
                expHeaderItemName, observedHeaderItem.getName() );
        LOGGER.info( STR."Observed arrow order: \{observedHeaderItem.getArrowDirection()}" );
        assertEquals( STR."Table is sorted in '\{observedHeaderItem.getArrowDirection()}' order instead of '\{expArrowDirection}'",
                expArrowDirection, observedHeaderItem.getArrowDirection() );
    }

    private Table collectTable( String tableTitle ) {
        List<Table> foundTables = getDetailedPatientData().getTables();
        return foundTables.stream()
                          .filter( table -> Objects.equals( table.getTitle(), tableTitle ) )
                          .findFirst()
                          .orElseThrow( () -> new RuntimeException( STR."Following table was not found: \{tableTitle}" ) );
    }

    public void clickOnHeaderItemInTable( String tableTitle, String columnName ) {
        getDetailedPatientData().clickOnHeaderItemInTable( tableTitle, columnName );
    }

    public void verifyTableNotDisplayed( String tableTitle ) {
        waitUntilLoadingSkeletonDisappears();
        List<Table> foundTables = getDetailedPatientData().getTables();
        Optional<Table> observedTable = foundTables.stream()
                                                   .filter( table -> Objects.equals( table.getTitle(), tableTitle ) )
                                                   .findFirst();
        assertTrue( STR."The following table should not be displayed: \{tableTitle}", observedTable.isEmpty() );
    }

    public void checkEmptyStateInfo( ExamplesTable params ) {
        String expectedHeader = params.getRows().get( 0 ).get( "header" );
        String expectedBody = params.getRows().get( 0 ).get( "body" );
        EmptyStateInfo emptyStateInfo = getDetailedPatientData().getEmptyStateInfo();
        LOGGER.info( STR."Observed empty state information:\\nHeader:\{emptyStateInfo.getHeader()}\\nBody: \{emptyStateInfo.getBody()}" );
        assertEquals( "Empty state information header is not ok.", expectedHeader, emptyStateInfo.getHeader() );
        assertEquals( "Empty state information body is not ok.", expectedBody, emptyStateInfo.getBody() );
    }

    public void verifyAllDetailedData( List<DetailedDataItem> expectedItems ) {
        waitUntilLoadingSkeletonDisappears();
        List<DetailedDataItem> observedItems = getDetailedPatientDataItems();
        LOGGER.info( "Found data items:" );
        observedItems.forEach( i -> LOGGER.info( i.toString() ) );
        assertEquals( STR."Number of the items do not match. \nExpected items: \{expectedItems}\nObserved items: \{observedItems}"
                , expectedItems.size(), observedItems.size() );
        verifyPartialDetailedData( expectedItems, observedItems );
    }

    public void verifyPartialDetailedData( List<DetailedDataItem> expectedItems ) {
        waitUntilLoadingSkeletonDisappears();
        List<DetailedDataItem> observedItems = getDetailedPatientDataItems();
        LOGGER.info( STR."Observed data items: \{observedItems.stream().map( DetailedDataItem::toString )
                                                              .collect( Collectors.joining( "\n" ) )}" );
        verifyPartialDetailedData( expectedItems, observedItems );
    }

    private void verifyPartialDetailedData( List<DetailedDataItem> expectedItems, List<DetailedDataItem> observedItems ) {
        LOGGER.info( STR."Observed sidebar items: \{observedItems}" );
        expectedItems.forEach( expectedItem -> {
            // TABLE ON SIDE PANEL
            if ( expectedItem instanceof TableItem ) {
                DetailedDataItem observedItem = findDetailedDataItemByClassAndName( expectedItem, observedItems );
                // In this step, we check only that it is a table item. Exact table values can be checked in a separated step.
                assertTrue( STR."This is not a data table: \{expectedItem.getName()}", observedItem instanceof TableItem );
            }
            // BADGE INDICATOR ON SIDE PANEL
            else if ( expectedItem instanceof BadgeIndicatorItem ) {
                List<DetailedDataItem> filteredObservedItems = findDetailedDataItemsByClassName( expectedItem, observedItems );
                assertTrue( STR."Badge indicator was not found: \{expectedItem}", filteredObservedItems.contains( expectedItem ) );
            }
            // CLP DATA ON SIDE PANEL
            else if ( expectedItem instanceof CLPItem ) {
                List<DetailedDataItem> clpItems = observedItems.stream()
                                                               .filter( item -> item.getClass().equals( CLPItem.class ) )
                                                               .collect( Collectors.toList() );
                assertTrue( "CLP item was not found: " + expectedItem, clpItems.contains( expectedItem ) );
            }
            // All OTHER DATA ON SIDE PANEL
            else {
                boolean requiredItemFound = observedItems.contains( expectedItem );
                if ( !requiredItemFound ) {
                    DetailedDataItem observedItem = findDetailedDataItemByClassAndName( expectedItem, observedItems );
                    if ( ANY_VALUE_PLACEHOLDER.equals( expectedItem.getValue() ) ) {
                        assertEquals( "The name of the data item is not ok.", expectedItem.getName(), observedItem.getName() );
                    } else {
                        assertEquals( "Data item is not ok.", expectedItem, observedItem );
                    }
                }
            }
        } );
    }

    private DetailedDataItem findDetailedDataItemByClassAndName( DetailedDataItem expectedItem, List<DetailedDataItem> observedItems ) {
        return observedItems.stream()
                            .filter( item -> item.getClass().equals( expectedItem.getClass() ) && item.getName().equals( expectedItem.getName() ) )
                            .findFirst()
                            .orElseThrow( () -> new RuntimeException(
                                    STR."Following item was not found: \{expectedItem.getName()} (\{expectedItem.getClass().getSimpleName()})" ) );
    }

    private List<DetailedDataItem> findDetailedDataItemsByClassName( DetailedDataItem expectedItem, List<DetailedDataItem> observedItems ) {
        return observedItems.stream()
                            .filter( item -> item.getClass().equals( expectedItem.getClass() ) )
                            .collect( Collectors.toList() );
    }

    public void checkDetailedPatientDataNotAvailable() {
        List<DetailedDataItem> observedDataItems = getDetailedPatientDataItems();
        LOGGER.info( STR."Observed data items: \{observedDataItems.stream().map( DetailedDataItem::getName ).collect( Collectors.joining( "\\n" ) )}" );
        assertTrue( "Detailed patient data should not be available.", observedDataItems.isEmpty() );
    }

    private List<DetailedDataItem> getDetailedPatientDataItems() {
        waitUntilLoadingSkeletonDisappears();
        return getDetailedPatientData().getItems();
    }

    public void clickOnDataItem( DetailedDataItem expectedItem ) {
        waitUntilLoadingSkeletonDisappears();
        getDetailedPatientData().clickOnItem( expectedItem );
    }

    public void clickOnDataItemValue( DetailedDataItem expectedItem ) {
        if ( expectedItem.getValue().toString().contains( "Download" )) {
            Set<String> originalWindow = getDetailedPatientData().getDriver().getWindowHandles();
            setSessionVariable( WINDOW_HANDLES_NUMBER ).to( originalWindow.size() );
        }
        getDetailedPatientData().clickOnItemValue( expectedItem );
    }

    public void clickButtonOnItem( DetailedDataItem expectedItem, String buttonText ) {
        getDetailedPatientData().clickButtonOnItem( expectedItem, buttonText );
    }

    public void checkTableRowsAreHighlighted( boolean isHighlighted, String color, Table expectedTable ) {
        waitUntilLoadingSkeletonDisappears();

        String tableTitle = expectedTable.getTitle();
        for ( int i = 0; i < expectedTable.getRows().size(); i++ ) {
            String backgroundColorOfFirstCellOfXRow = getDetailedPatientData().getBackgroundColorOfFirstCellOfXRow( expectedTable.getRows().get( i ), tableTitle );
            LOGGER.info( STR."Observed background color is :\{backgroundColorOfFirstCellOfXRow}" );
            if ( isHighlighted ) {
                assertEquals( "Row should be highlighted\n", color, backgroundColorOfFirstCellOfXRow );
            } else {
                assertNotEquals( "Row shouldn't be highlighted\n", color, backgroundColorOfFirstCellOfXRow );
            }
        }
    }

    public void checkTableTextIsBold( boolean isBold, String tableTitle, ExamplesTable expectedTable ) {
        List<String> expectedList = expectedTable.getRows().stream().map( param -> param.get( "Strings" ) ).collect( Collectors.toList() );
        List<String> boldTextsFromTable = getDetailedPatientData().getBoldTextsFromTable( tableTitle );
        LOGGER.info( STR."Observed bold texts: \{boldTextsFromTable}" );
        if ( isBold ) {
            assertEquals( "Texts should be bold\n", expectedList, boldTextsFromTable );
        } else {
            assertNotEquals( "Texts shouldn't be bold\n", expectedList, boldTextsFromTable );
        }
    }

}
