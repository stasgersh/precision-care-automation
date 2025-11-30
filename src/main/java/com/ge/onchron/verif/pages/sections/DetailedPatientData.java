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

import com.ge.onchron.verif.pages.utils.ClpUtils;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.Badge;
import com.ge.onchron.verif.model.Table;
import com.ge.onchron.verif.model.detailedDataItems.BadgeIndicatorItem;
import com.ge.onchron.verif.model.detailedDataItems.CLPItem;
import com.ge.onchron.verif.model.detailedDataItems.DetailedDataItem;
import com.ge.onchron.verif.model.detailedDataItems.EmptyInfoItem;
import com.ge.onchron.verif.model.detailedDataItems.LinkItem;
import com.ge.onchron.verif.model.detailedDataItems.SectionTitle;
import com.ge.onchron.verif.model.detailedDataItems.SimpleItem;
import com.ge.onchron.verif.model.detailedDataItems.TableItem;
import com.ge.onchron.verif.model.detailedDataItems.TrendItem;
import com.ge.onchron.verif.model.enums.BadgeType;
import com.ge.onchron.verif.pages.elements.EmptyStateInfo;
import com.ge.onchron.verif.pages.utils.TableUtils;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.pages.components.HtmlTable;

import static com.ge.onchron.verif.SystemTestConstants.BOLD_TEXT_FONT_WEIGHT;
import static com.ge.onchron.verif.pages.utils.BadgeUtils.getBadgeType;
import static com.ge.onchron.verif.pages.utils.ClpUtils.CLP_HIGHLIGHT_CLASS;
import static com.ge.onchron.verif.pages.utils.ClpUtils.getClpItemText;
import static com.ge.onchron.verif.pages.utils.PageElementUtils.scrollIntoViewport;
import static com.ge.onchron.verif.pages.utils.PageUtils.currentlyContainsWebElements;
import static com.ge.onchron.verif.pages.utils.TableUtils.createTableItem;
import static com.ge.onchron.verif.utils.ImageUtils.bytesToBufferedImage;
import static com.ge.onchron.verif.utils.ImageUtils.saveScreenshot;

public class DetailedPatientData extends PageObject {

    private static final Logger LOGGER = LoggerFactory.getLogger( DetailedPatientData.class );

    private final String TABLE_TAG_NAME = "table";
    private final String TREND_TITLE_CLASS = "LineChart-module_title";
    private final String TABLE_TITLE_CLASS = "TableElement-module_title";
    private final String TREND_GRAPH_CSS_SELECTOR = "[class^='LineChart-module_wrap']";
    private final String BUTTON_TAG_NAME = "button";
    private final String COMMENTS_RELATED_CLASS = "Comments";
    private final String SIDEBAR_HEADER_CLASS = "EventSidebar-module_header";
    private final String EMPTY_STATE_INFO_XPATH = ".//*[contains(@class,'EmptyState_info')]";

    @FindBy( xpath = "./*" )
    private List<WebElement> childElements;

    @FindBy( xpath = EMPTY_STATE_INFO_XPATH )
    private WebElement emptyStateInfo;

    public List<DetailedDataItem> getItems() {
        Map<DetailedDataItem, WebElement> itemsWithWebElements = getItemsWithWebElements();
        return new ArrayList<>( itemsWithWebElements.keySet() );
    }

    public void clickOnItem( DetailedDataItem expectedItem ) {
        Map<DetailedDataItem, WebElement> itemsWithWebElements = getItemsWithWebElements();
        Map.Entry<DetailedDataItem, WebElement> requiredItem = itemsWithWebElements.entrySet().stream()
                                                                                   .filter( entry -> entry.getKey().equals( expectedItem ) )
                                                                                   .findFirst()
                                                                                   .orElseThrow( () -> new RuntimeException( "The following item was not found: \n" + expectedItem ) );
        if ( requiredItem.getKey() instanceof LinkItem ) {
            requiredItem.getValue().findElement( By.tagName( "button" ) ).click();
        } else {
            fail( "Clicking on the following item is not supported: " + expectedItem );
        }
    }

    public void clickOnItemValue( DetailedDataItem expectedItem ) {
        Map.Entry<DetailedDataItem, WebElement> requiredItem = getItem( expectedItem );
        WebElement keyValueElement = requiredItem.getValue();
        WebElement requiredValueElement = keyValueElement.findElement( By.linkText( ((SimpleItem) expectedItem).getValue() ) );
        requiredValueElement.click();
    }

    public void clickButtonOnItem( DetailedDataItem expectedItem, String buttonText ) {
        WebElement requiredItem = getItem( expectedItem ).getValue();
        WebElement requiredButton = requiredItem.findElement( By.tagName( "button" ) );
        assertEquals( "Button text is not ok at the sidebar element.", buttonText, requiredButton.getText() );
        requiredButton.click();
    }

    private Map.Entry<DetailedDataItem, WebElement> getItem( DetailedDataItem expectedItem ) {
        Map<DetailedDataItem, WebElement> itemsWithWebElements = getItemsWithWebElements();
        return itemsWithWebElements.entrySet().stream()
                                   .filter( entry -> entry.getKey().equals( expectedItem ) )
                                   .findFirst()
                                   .orElseThrow( () -> new RuntimeException( "The following item was not found: \n" + expectedItem ) );
    }

    public LinkedHashMap<DetailedDataItem, WebElement> getItemsWithWebElements() {
        ListIterator<WebElement> iterator = childElements.listIterator();
        LinkedHashMap<DetailedDataItem, WebElement> items = new LinkedHashMap<>();
        while ( iterator.hasNext() ) {
            WebElement actualElement = iterator.next();
            String classOfActualElement = actualElement.getAttribute( "class" );
            if ( classOfActualElement.contains( "section-title" ) ) {
                items.put( new SectionTitle( actualElement.getText() ), actualElement );                              // section title
            } else if ( classOfActualElement.contains( TABLE_TITLE_CLASS ) ) {                                        // table with a header
                WebElement tableElement = iterator.next();
                if ( !TABLE_TAG_NAME.equalsIgnoreCase( tableElement.getTagName() ) ) {
                    fail( "Table element was expected after a " + TABLE_TITLE_CLASS + " class" );
                }
                items.put( createTableItem( actualElement.getText(), tableElement ), tableElement );
            } else if ( actualElement.getTagName().equals( TABLE_TAG_NAME ) ) {                                       // table without header
                items.put( createTableItem( null, actualElement ), actualElement );
            } else if ( classOfActualElement.contains( TREND_TITLE_CLASS ) ) {                                        // trend graph with a header
                WebElement nextElement = iterator.next();
                By trendGraphSelector = By.cssSelector( TREND_GRAPH_CSS_SELECTOR );
                if ( !currentlyContainsWebElements( nextElement, trendGraphSelector ) ) {
                    fail( "Trend graph (" + TREND_GRAPH_CSS_SELECTOR + ") was expected after a " + TREND_TITLE_CLASS + " class" );
                }
                WebElement trendGraph = nextElement.findElement( trendGraphSelector );
                items.put( createTrendItem( actualElement, trendGraph ), trendGraph );

            } else if ( currentlyContainsWebElements( actualElement, By.xpath( BUTTON_TAG_NAME ) ) ) {                // button
                items.put( createButtonItem( actualElement ), actualElement );
            } else if ( currentlyContainsWebElements(
                    actualElement, By.cssSelector( String.format( "[class*='%s']", CLP_HIGHLIGHT_CLASS ) ) ) ) {      // CLP info
                items.put( createClpItem( actualElement ), actualElement );

            } else if ( classOfActualElement.contains( COMMENTS_RELATED_CLASS ) ) {                                   // comment related item
                LOGGER.info( "Comment element found but skipped here" );

            } else if ( classOfActualElement.contains( SIDEBAR_HEADER_CLASS ) ) {                                     // sidebar header
                LOGGER.info( "Sidebar header found but skipped here" );

            } else if ( classOfActualElement.contains( "noinfo" ) ) {                                                 // noinfo element
                String text = actualElement.getText();
                Color color = Color.fromString( actualElement.getCssValue( "color" ) );
                items.put( new EmptyInfoItem( text, color.asHex() ), actualElement );

            } else if ( classOfActualElement.contains( "IndicatorElement-module_indicators" ) ) {                                                 // badge indicator
                Badge badge = new Badge();
                WebElement actualBadge = actualElement.findElement( By.cssSelector( ".badge" ) );
                badge.setText( actualBadge.getText() );
                BadgeType badgeType = getBadgeType( actualBadge );
                badge.setBadgeType( badgeType );
                Color color = Color.fromString( actualBadge.getCssValue( "background-color" ) );
                badge.setColor( color.asRgb() );
                items.put( new BadgeIndicatorItem( badge ), actualBadge );

            } else if ( classOfActualElement.contains( "anchor" ) ) {                                                 // anchor (not processed)
                LOGGER.info( "Anchor element: no need to process" );

            } else {
                items.put( createSimpleItem( actualElement ), actualElement );                                        // simple item
            }

        }
        return items;
    }

    private SimpleItem createSimpleItem( WebElement element ) {
        List<WebElement> params = element.findElements( By.xpath( "child::*" ) );
        SimpleItem simpleItem = null;
        if ( params.size() == 1 ) {
            simpleItem = new SimpleItem( params.get( 0 ).getText() );
        } else if ( params.size() == 2 ) {
            simpleItem = new SimpleItem( params.get( 0 ).getText(), params.get( 1 ).getText() );
        } else {
            fail( "Not known item: " + element.getText() );
        }
        return simpleItem;
    }

    private LinkItem createButtonItem( WebElement element ) {
        return new LinkItem( element.getText() );
    }

    public void clickOnHeaderItemInTable( String tableTitle, String columnName ) {
        Map<DetailedDataItem, WebElement> itemsWithWebElements = getItemsWithWebElements();
        Map.Entry<DetailedDataItem, WebElement> requiredTable = itemsWithWebElements.entrySet().stream()
                                                                                    .filter( entry -> entry.getKey().getName().equals( tableTitle ) )
                                                                                    .findFirst()
                                                                                    .orElseThrow( () -> new RuntimeException( "The following table item was not found: \n" + tableTitle ) );
        WebElement tableElement = requiredTable.getValue();
        TableUtils.clickOnHeaderItemInTable( tableElement, columnName );
    }

    private DetailedDataItem createClpItem( WebElement element ) {
        String clpText = getClpItemText( element );
        String backgroundColor = ClpUtils.getClpItemBackgroundColorAsHex( element );
        return new CLPItem( "", clpText, backgroundColor );
    }

    private TrendItem createTrendItem( WebElement actualElement, WebElement trendElement ) {
        String name = geNodeMainText( actualElement );
        scrollIntoViewport( trendElement ); // get the trend graph into the viewport

        byte[] screenshotBytes = trendElement.getScreenshotAs( OutputType.BYTES );
        BufferedImage bufferedImage = bytesToBufferedImage( screenshotBytes );
        saveScreenshot( bufferedImage );

        return new TrendItem( name, bufferedImage );
    }

    public List<Table> getTables() {
        List<Table> tables = getItems().stream()
                                       .filter( item -> item instanceof TableItem )
                                       .map( item -> ((TableItem) item).getValue() )
                                       .collect( Collectors.toList() );
        LOGGER.info( STR."List of tables: \{tables.stream().map( Object::toString ).collect( Collectors.joining( "\n" ) )}" );
        return tables;
    }

    public String geNodeMainText( WebElement element ) {
        if ( currentlyContainsWebElements( element, By.xpath( "child::*" ) ) ) {
            // if the node has more child elements with more text, then return only the first one
            // e.g. Completed medications ... Collapse ^
            List<WebElement> childElements = element.findElements( By.xpath( "child::*" ) );
            return childElements.get( 0 ).getText();
        } else {
            return element.getText();
        }
    }

    public EmptyStateInfo getEmptyStateInfo() {
        return new EmptyStateInfo( emptyStateInfo );
    }

    private HtmlTable getTableElementByTitle( String tableTitle ) {
        Map<DetailedDataItem, WebElement> itemsWithWebElements = getItemsWithWebElements();
        Map.Entry<DetailedDataItem, WebElement> requiredItem = itemsWithWebElements.entrySet().stream()
                                                                                   .filter( entry -> entry.getKey().getName().equals( tableTitle ) )
                                                                                   .findFirst()
                                                                                   .orElseThrow( () -> new RuntimeException( "The following table item was not found: \n" + tableTitle ) );
        return new HtmlTable( requiredItem.getValue() );
    }

    public String getBackgroundColorOfFirstCellOfXRow( Map<String, String> expectedRow, String tableTitle ) {
        HtmlTable observedTable = getTableElementByTitle( tableTitle );
        List<WebElement> rowElements = observedTable.getRowElements();
        LOGGER.info( STR."Observed rows:\\n\{rowElements.stream()
                                                        .map( r -> STR."Row text: \{r.findElement( By.xpath( "./td[1]" ) ).getText()
                                                                } \{r.findElement( By.xpath( "./td[2]" ) ).getText()
                                                                }, Color: \{r.findElements( By.tagName( "td" ) ).get( 0 ).getCssValue( "background-color" )}" )
                                                        .collect( Collectors.joining( "\\n" ) )}" );
        WebElement matchingRow = rowElements.stream()
                                            .filter( row -> {
                                                String expectedText = expectedRow.get( expectedRow.keySet().stream().findFirst().get() );
                                                return row.findElement( By.xpath( "./td[1]" ) ).getText().equals( expectedText ) ||
                                                        row.findElement( By.xpath( "./td[2]" ) ).getText().equals( expectedText );
                                            } )
                                            .findFirst()
                                            .orElseThrow( () -> new RuntimeException( "The following row was was not found: \n" + expectedRow ) );

        String backgroundColor = matchingRow.findElements( By.tagName( "td" ) ).get( 0 ).getCssValue( "background-color" );
        return Color.fromString( backgroundColor ).asHex();
    }

    public List<String> getBoldTextsFromTable( String tableTitle ) {
        HtmlTable observedTable = getTableElementByTitle( tableTitle );
        List<WebElement> rowElements = observedTable.getRowElements();
        List<String> result = new ArrayList<>();
        for ( WebElement row : rowElements ) {
            result.add( row.findElements( By.tagName( "td" ) )
                           .stream()
                           .filter( text -> text.getCssValue( "font-weight" ).equals( BOLD_TEXT_FONT_WEIGHT ) )
                           .map( WebElement::getText )
                           .collect( Collectors.joining( ";" ) ) );
        }
        result.removeAll( Collections.singleton( "" ) );
        return result;
    }

}
