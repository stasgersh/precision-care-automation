/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2024, 2024, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 */
package com.ge.onchron.verif.pages.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;

import java.util.List;

public class ClpUtils {

    public static final String CLP_HIGHLIGHT_CLASS = "Nlp-module_container";
    private static final String CLP_CONTENT_CONTAINER_SELECTOR = "[class*='Nlp-module_no-print']";
    private static final String CLP_CONTENT_SELECTOR = "[class*='Nlp-module_content']";

    public static String getClpItemText( WebElement element ) {
        List<WebElement> clpDataElements = element.findElements( By.cssSelector( CLP_CONTENT_CONTAINER_SELECTOR ) );
        // Note: If 2 elements found, then the 2nd one is the 'See more' / 'See less' button. Here, we need only the clp text
        return clpDataElements.get( 0 ).findElement( By.cssSelector( CLP_CONTENT_SELECTOR ) ).getText();
    }

    public static String getClpItemBackgroundColorAsHex( WebElement element ) {
        List<WebElement> clpDataElements = element.findElements( By.cssSelector( CLP_CONTENT_SELECTOR ) );
        String backgroundColor = clpDataElements.get( 0 ).getCssValue( "background-color" );
        return Color.fromString( backgroundColor ).asHex();
    }
}
