/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2025, 2025, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.pages.elements;

import java.util.List;

import org.openqa.selenium.By;

import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.pages.elements.AllergiesAndIntolerancesElements.ALLERGIES_AND_INTOLERANCES_ELEMENTS.ALLERGIES_AND_INTOLERANCES_HEADER;
import static com.ge.onchron.verif.pages.elements.AllergiesAndIntolerancesElements.ALLERGIES_AND_INTOLERANCES_ELEMENTS.ALLERGIES_AND_INTOLERANCES_TABLE;
import static junit.framework.TestCase.fail;

public class AllergiesAndIntolerancesElements {

    public enum ALLERGIES_AND_INTOLERANCES_ELEMENTS {
        ANCHOR( 0 ),
        ALLERGIES_AND_INTOLERANCES_HEADER( 1 ),
        ALLERGIES_AND_INTOLERANCES_TABLE( 2 );

        private int num;

        ALLERGIES_AND_INTOLERANCES_ELEMENTS( int num ) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }
    }

    private List<WebElementFacade> allergiesAndIntolerancesElements;

    public AllergiesAndIntolerancesElements( WebElementFacade allergiesAndIntolerancesView ) {
        /*
         * elements on Allergies and Intolerances section:
         * 0. anchor
         * 1. "Allergies and Intolerances" header
         * 2. "Allergies and Intolerances" table
         */
        this.allergiesAndIntolerancesElements = allergiesAndIntolerancesView.thenFindAll( By.xpath( "child::*" ) );
    }

    public WebElementFacade getAllergiesAndIntolerancesTable() {
        WebElementFacade table = allergiesAndIntolerancesElements.get( ALLERGIES_AND_INTOLERANCES_TABLE.getNum() );
        if ( !table.getTagName().equals( "table" ) ) {
            fail( "Element is not a table but a " + table.getTagName() );
        }
        return table;
    }

    public WebElementFacade getAllergiesAndIntolerancesHeader() {
        WebElementFacade header = allergiesAndIntolerancesElements.get( ALLERGIES_AND_INTOLERANCES_HEADER.getNum() );
        if ( !header.getTagName().equals( "div" ) ) {
            fail( "Element is not a div (header) but a " + header.getTagName() );
        }
        return header;
    }
}