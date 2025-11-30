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
package com.ge.onchron.verif.pages.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.jbehave.core.model.ExamplesTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.Table;
import com.ge.onchron.verif.model.TableHeaderItem;
import com.ge.onchron.verif.model.detailedDataItems.TableItem;
import com.ge.onchron.verif.model.enums.ArrowDirection;

import static com.ge.onchron.verif.pages.elements.HtmlTable.cellWebElementFrom;
import static com.ge.onchron.verif.pages.elements.HtmlTable.rowsFrom;
import static com.ge.onchron.verif.pages.utils.PageUtils.currentlyContainsWebElements;

public class TableUtils {

    private final static String HIGHLIGHTED_TABLE_CELL_CLASS = "//td[contains(@class,'SidebarTable-module_highlight-cell')]";
    private final static String UPWARD_ARROW_XPATH = ".//*[contains(@id,'Arrow_Up')]";
    private final static String DOWNWARD_ARROW_XPATH = ".//*[contains(@id,'Arrow_Down')]";

    public static Table createTable( WebElement element ) {
        List<TableHeaderItem> headerItems = getTableHeaderItems( element );
        List<String> headers = headerItems.stream().map( TableHeaderItem::getName ).collect( Collectors.toList() );

        List<Map<Object, String>> tableRows = rowsFrom( element );

        boolean tableContainsHighlightedRow = currentlyContainsWebElements( element, By.xpath( HIGHLIGHTED_TABLE_CELL_CLASS ) );
        List<Map<String, String>> reducedTableRows = reduceTableRowElements( tableRows, headers, tableContainsHighlightedRow );

        Table table = new Table( reducedTableRows );
        table.setHeaderItems( headerItems );
        return table;
    }

    public static TableItem createTableItem( String tableTitle, WebElement element ) {
        Table table = createTable( element );
        table.setTitle( tableTitle );
        return new TableItem( tableTitle, table );
    }

    public static void clickOnHeaderItemInTable( WebElement tableElement, String columnName ) {
        List<WebElement> headerElements = getTableHeaderElements( tableElement );
        headerElements.stream()
                      .filter( headerElement -> headerElement.getText().equals( columnName ) )
                      .findFirst()
                      .orElseThrow( () -> new RuntimeException( "The following column was not found: \n" + columnName ) )
                      .click();
    }

    public static WebElement getCellWebElementFromTable( WebElement tableElement, int rowNb, String columnName ) {
        List<TableHeaderItem> headers = createTable( tableElement ).getHeaderItems();
        TableHeaderItem requiredHeader = headers.stream()
                                                .filter( h -> h.getName().equals( columnName ) )
                                                .findFirst()
                                                .orElseThrow( () -> new RuntimeException( String.format( "The following column was not found in the table: %s", columnName ) ) );
        return cellWebElementFrom( tableElement, rowNb, headers.indexOf( requiredHeader ) );
    }

    private static List<TableHeaderItem> getTableHeaderItems( WebElement nextElement ) {
        List<TableHeaderItem> headerItems = new ArrayList<>();
        List<WebElement> headerElements = getTableHeaderElements( nextElement );
        for ( WebElement headerElement : headerElements ) {
            // Ignore colored highlight cell on the side
            if ( headerElement.getAttribute( "class" ).contains( "TableElement-module_highlight-cell" ) ) {
                continue;
            }
            String headerName = headerElement.getText();
            TableHeaderItem tableHeaderItem;
            if ( currentlyContainsWebElements( headerElement, By.xpath( UPWARD_ARROW_XPATH ) ) ) {
                tableHeaderItem = new TableHeaderItem( headerName, true, ArrowDirection.UPWARD );
            } else if ( currentlyContainsWebElements( headerElement, By.xpath( DOWNWARD_ARROW_XPATH ) ) ) {
                tableHeaderItem = new TableHeaderItem( headerName, true, ArrowDirection.DOWNWARD );
            } else {
                tableHeaderItem = new TableHeaderItem( headerName, false );
            }
            headerItems.add( tableHeaderItem );
        }
        return headerItems;
    }

    private static List<WebElement> getTableHeaderElements( WebElement table ) {
        return table.findElement( By.tagName( "thead" ) ).findElements( By.tagName( "th" ) );
    }

    private static List<Map<String, String>> reduceTableRowElements( List<Map<Object, String>> tableRows, List<String> headers, boolean cutHighlightedCells ) {
        // all table values are duplicated in tableRows: e.g. {"Route": "Oral use"} and {0: "Oral use"}
        // in the first one, the key is the table header; in the second one, the key is the nb of the column
        // now, we need only the first one, so the values - where the key is an integer - are removed
        List<Map<String, String>> reducedTableRows = new ArrayList<>();
        tableRows.forEach( row -> {
            HashMap<String, String> reducedRow = new LinkedHashMap<>();
            int i;
            for ( i = cutHighlightedCells ? 1 : 0; i < headers.size(); i++ ) {
                reducedRow.put( headers.get( i ), row.get( headers.get( i ) ) );
            }
            reducedTableRows.add( reducedRow );
        } );
        return reducedTableRows;
    }

    public static List<String> getExampleTableAsList( ExamplesTable table ) {
        List<String> tableAsList = new ArrayList<>();
        for ( Map<String, String> param : table.getRows() ) {
            String rowText = param.keySet()
                                  .stream()
                                  .filter( key -> !param.get( key ).isEmpty() && !"<N/A>".equals( param.get( key ) ) )
                                  .map( param::get )
                                  .collect( Collectors.joining( "; " ) );
            tableAsList.add( rowText.trim() );
        }
        return tableAsList;
    }

    public static Table sortTableByColumnName( Table table, String columnName, String arrowDirection ) {
        List<Map<String, String>> rowsToSort = new ArrayList<>( table.getRows() );

        if( "upward".equals( arrowDirection )) {
            rowsToSort.sort( Comparator.comparing( row -> getComparableValue(columnName, row.get( columnName )) ) );
        } else if ("downward".equals( arrowDirection )) {
            rowsToSort.sort( Comparator.comparing( ( Map<String, String> row ) -> getComparableValue(columnName, row.get( columnName )) ).reversed() );
        } else {
            fail("Arrow direction is not valid: should be 'upward' or 'downward");
        }

        return new Table( table.getTitle(), rowsToSort );
    }

    private static <T extends Comparable<?>> T getComparableValue( String columnName, String value ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH);
        return (T) switch ( columnName ) {
            case "Prescribed", "Date", "Observation date" -> value.isBlank() ? LocalDate.MIN : LocalDate.parse( value, formatter );
            default -> value.toLowerCase();
        };
    }

}
