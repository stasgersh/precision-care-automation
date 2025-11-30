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
package com.ge.onchron.verif.pages.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.ge.onchron.verif.pages.utils.PageUtils.currentlyContainsWebElements;

/**
 * Created based on HtmlTable.java in serenity-core:
 * https://github.com/serenity-bdd/serenity-core/blob/master/serenity-core/src/main/java/net/thucydides/core/pages/components/HtmlTable.java
 */
public class HtmlTable {

    private final WebElement tableElement;
    private List<String> headings;

    public HtmlTable( final WebElement tableElement ) {
        this.tableElement = tableElement;
        this.headings = null;
    }

    public List<Map<Object, String>> getRows() {

        List<Map<Object, String>> results = new ArrayList<>();

        List<String> headings = getHeadings();
        List<WebElement> rows = getRowElementsFor( headings );

        for ( WebElement row : rows ) {
            List<WebElement> cells = cellsIn( row );
            if ( enoughCellsFor( headings ).in( cells ) ) {
                results.add( rowDataFrom( cells, headings ) );
            }
        }
        return results;
    }

    private static class EnoughCellsCheck {
        private final int minimumNumberOfCells;

        private EnoughCellsCheck( List<String> headings ) {
            this.minimumNumberOfCells = headings.size();
        }

        public boolean in( List<WebElement> cells ) {
            return (cells.size() >= minimumNumberOfCells);
        }
    }

    private HtmlTable.EnoughCellsCheck enoughCellsFor( List<String> headings ) {
        return new HtmlTable.EnoughCellsCheck( headings );
    }

    public List<String> getHeadings() {
        if ( headings == null ) {
            List<String> thHeadings = headingElements()
                    .stream()
                    .map( this::cellToText )
                    .filter( element -> !element.isEmpty() )
                    .collect( Collectors.toList() );

            if ( thHeadings.isEmpty() ) {
                headings = firstRowElements()
                        .stream()
                        .map( element -> element.getAttribute( "textContent" ) )
                        .collect( Collectors.toList() );
            } else {
                headings = thHeadings;
            }
        }
        return headings;
    }

    public List<WebElement> headingElements() {
        return tableElement.findElements( By.xpath( ".//th" ) );
    }

    public List<WebElement> firstRowElements() {
        return tableElement.findElement( By.tagName( "tr" ) ).findElements( By.xpath( ".//td" ) );
    }

    public List<WebElement> getRowElementsFor( List<String> headings ) {

        List<WebElement> rowCandidates = tableElement.findElements( By.xpath( ".//tr[td][count(td)>=" + headings.size() + "]" ) );
        rowCandidates = stripHeaderRowIfPresent( rowCandidates, headings );
        return rowCandidates;
    }

    public List<WebElement> getRowElements() {

        return getRowElementsFor( getHeadings() );
    }

    private List<WebElement> stripHeaderRowIfPresent( List<WebElement> rowCandidates, List<String> headings ) {
        if ( !rowCandidates.isEmpty() ) {
            WebElement firstRow = rowCandidates.get( 0 );
            if ( hasMatchingCellValuesIn( firstRow, headings ) ) {
                rowCandidates.remove( 0 );
            }
        }
        return rowCandidates;
    }

    private boolean hasMatchingCellValuesIn( WebElement firstRow, List<String> headings ) {
        List<WebElement> cells = firstRow.findElements( By.xpath( "./td" ) );
        for ( int cellIndex = 0; cellIndex < headings.size(); cellIndex++ ) {
            if ( (cells.size() < cellIndex) || !cellToText( cells.get( cellIndex ) ).equals( headings.get( cellIndex ) ) ) {
                return false;
            }
        }
        return true;
    }

    private Map<Object, String> rowDataFrom( List<WebElement> cells, List<String> headings ) {
        Map<Object, String> rowData = new HashMap<>();

        int column = 0;
        for ( String heading : headings ) {
            String cell = cellValueAt( column++, cells );
            if ( !StringUtils.isEmpty( heading ) ) {
                rowData.put( heading, cell );
            }
            rowData.put( column, cell );
        }
        return rowData;
    }

    private List<WebElement> cellsIn( WebElement row ) {
        // Ignore colored highlight cell on the side
        return row.findElements( By.xpath( "./td[not(contains(@class, 'TableElement-module_highlight-cell'))]" ) );
    }

    private String cellValueAt( final int column, final List<WebElement> cells ) {
        return cellToText( cells.get( column ) );
    }

    /**
     * @param cell
     * @return cell context in String format
     * <p>
     * The method is customized in order to be able to handle badge content in table cells
     */
    private String cellToText( WebElement cell ) {
        List<WebElement> cellElements = cell.findElements( By.xpath( "child::*" ) );
        StringBuilder sb = new StringBuilder();
        for ( int i = 0; i < cellElements.size(); i++ ) {
            WebElement cellElement = cellElements.get( i );

            // Badge content into "[...]" format
            if ( cellElement.getAttribute( "class" ).contains( "IndicatorElement" ) ) {
                String badgeText = cellElement.findElement( By.cssSelector( ".badge" ) ).getText();
                sb.append( "[" ).append( badgeText ).append( "]" );
            } else if ( currentlyContainsWebElements( cellElement, By.cssSelector( "[class*='clp']" ) ) ) {
                // CLP data content into "[CLP_DATA]: ..." format
                sb.append( "[CLP_DATA]: " ).append( cellElement.getText() );
            } else if ( cellElement.getAttribute( "class" ).contains( "TableElement-module_list-row" ) ) {
                // Multiple elements can be in the cell
                String multipleElementsFromCell = getMultipleElementsFromCell( cellElement );
                sb.append( multipleElementsFromCell );
            } else {
                sb.append( cellElement.getText() );
            }
            if ( i < cellElements.size() - 1 ) {
                sb.append( " " );
            }
        }
        return sb.toString();
    }

    private static String getMultipleElementsFromCell( WebElement cellElement ) {
        StringBuilder sb = new StringBuilder();
        List<WebElement> elementsInCell = cellElement.findElements( By.xpath( "child::*" ) );
        for ( int j = 0; j < elementsInCell.size(); j++ ) {
            if ( j != 0 ) {
                sb.append( " " );
            }
            WebElement elementInCell = elementsInCell.get( j );
            if ( currentlyContainsWebElements( elementInCell, By.id( "Notes_-_16" ) ) ) {
                sb.append( "[REPORT_BUTTON]" );
            } else if ( currentlyContainsWebElements( elementInCell, By.id( "b" ) ) ) {
                sb.append( "[IMAGING_BUTTON]" );
            } else {
                sb.append( "[UNKNOWN_ITEM]" );
            }
        }
        return sb.toString();
    }

    public static List<Map<Object, String>> rowsFrom( final WebElement table ) {
        return new HtmlTable( table ).getRows();
    }

    public static WebElement cellWebElementFrom( final WebElement table, int rowNb, int columnNb ) {
        HtmlTable htmlTable = new HtmlTable( table );
        return htmlTable.cellsIn( htmlTable.getRowElements().get( rowNb ) ).get( columnNb );
    }

}
