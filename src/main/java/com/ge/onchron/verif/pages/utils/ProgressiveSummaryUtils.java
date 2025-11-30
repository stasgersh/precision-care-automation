/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2025, 2025, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE HealthCare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 */
package com.ge.onchron.verif.pages.utils;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.fail;

import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.pages.utils.PageElementUtils.hoverElementJS;
import static com.ge.onchron.verif.pages.utils.PageUtils.getWebElementChildNodes;
import static com.ge.onchron.verif.pages.utils.TableUtils.getCellWebElementFromTable;
import static com.ge.onchron.verif.utils.Utils.waitMillis;

public class ProgressiveSummaryUtils {

    private static final String WEBELEMENT_WITH_SPACE_SELECTOR = "u-ml----";
    private static final String OPTIONS_BUTTON_SELECTOR = "[class*='SummaryItem-module_cta']";

    public static String getWhatHasChangedTitleText( WebElementFacade titleContainer ) {
        List<Object> titleParts = getWebElementChildNodes( titleContainer );
        StringBuilder titleBuilder = new StringBuilder();
        for ( Object titlePart : titleParts ) {
            if ( titlePart instanceof WebElement titlePartWebelement ) {
                if ( titlePartWebelement.getAttribute( "class" ).contains( WEBELEMENT_WITH_SPACE_SELECTOR ) ) {
                    titleBuilder.append( " " );
                }
                titleBuilder.append( titlePartWebelement.getText() );
            } else {
                titleBuilder.append( titlePart.toString() );
            }
        }
        return titleBuilder.toString();
    }

    public static void hoverButtonInTableCell( WebElement whatHasChangedTableWebElement, String buttonName, int rowNb, String columnName ) {
        WebElement button = getButtonWebelementFromTableCell( whatHasChangedTableWebElement, buttonName, rowNb, columnName );
        hoverElementJS( button );
        waitMillis( 500 );
    }

    public static WebElement getButtonWebelementFromTableCell( WebElement whatHasChangedTableWebElement, String buttonName, int rowNb, String columnName ) {
        WebElement buttonWebelement = null;
        switch ( buttonName ) {
            case "Full report":
                buttonWebelement = getFullReportButton( whatHasChangedTableWebElement, rowNb, columnName );
                break;
            case "Images":
                buttonWebelement = getImagesButton( whatHasChangedTableWebElement, rowNb, columnName );
                break;
            default:
                fail( String.format( "Not known button type to get from table cell: %s", buttonName ) );
        }
        return buttonWebelement;
    }

    public static WebElement getFullReportButton( WebElement whatHasChangedTableWebElement, int rowNb, String columnName ) {
        return getCellWebelements( whatHasChangedTableWebElement, rowNb, columnName ).getFirst();
    }

    public static WebElement getImagesButton( WebElement whatHasChangedTableWebElement, int rowNb, String columnName ) {
        return getCellWebelements( whatHasChangedTableWebElement, rowNb, columnName ).getLast();
    }

    private static List<WebElement> getCellWebelements( WebElement whatHasChangedTableWebElement, int rowNb, String columnName ) {
        return getCellWebElementFromTable( whatHasChangedTableWebElement, rowNb, columnName )
                .findElements( By.cssSelector( OPTIONS_BUTTON_SELECTOR ) );
    }

}
