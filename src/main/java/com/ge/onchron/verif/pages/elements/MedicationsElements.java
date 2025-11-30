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

import java.util.List;

import org.openqa.selenium.By;

import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.pages.elements.MedicationsElements.MEDICATION_ELEMENTS.COMPLETED_MEDICATIONS_TABLE;
import static com.ge.onchron.verif.pages.elements.MedicationsElements.MEDICATION_ELEMENTS.COMPLETED_MEDICATIONS_TABLE_HEADER;
import static com.ge.onchron.verif.pages.elements.MedicationsElements.MEDICATION_ELEMENTS.DEFAULT_MEDICATIONS_TABLE;
import static junit.framework.TestCase.fail;

public class MedicationsElements {

    public enum MEDICATION_ELEMENTS {
        ANCHOR( 0 ),
        MEDICATIONS_HEADER( 1 ),
        DEFAULT_MEDICATIONS_TABLE( 2 ),
        COMPLETED_MEDICATIONS_TABLE_HEADER( 3 ),
        COMPLETED_MEDICATIONS_TABLE( 4 );

        private int num;

        MEDICATION_ELEMENTS( int num ) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }
    }

    private List<WebElementFacade> medicationElements;

    public MedicationsElements( WebElementFacade medicationsView ) {
        /*
         * elements on Medications section:
         * 0. anchor
         * 1. "Medications" header
         * 2. "Default" medications table
         * 3. Completed medications header
         * 4. (if exists) Completed medications table
         */
        this.medicationElements = medicationsView.thenFindAll( By.xpath( "child::*" ) );
    }

    public WebElementFacade getDefaultMedTable() {
        WebElementFacade table = medicationElements.get( DEFAULT_MEDICATIONS_TABLE.getNum() );
        if ( !table.getTagName().equals( "table" ) ) {
            fail( "Element is not a table but a " + table.getTagName() );
        }
        return table;
    }

    public WebElementFacade getCompletedMedHeader() {
        WebElementFacade header = medicationElements.get( COMPLETED_MEDICATIONS_TABLE_HEADER.getNum() );
        if ( !header.getTagName().equals( "div" ) ) {
            fail( "Element is not a div (header) but a " + header.getTagName() );
        }
        return header;
    }

    public WebElementFacade getCompletedMedTable() {
        if ( medicationElements.size() > COMPLETED_MEDICATIONS_TABLE.getNum() ) {
            WebElementFacade table = medicationElements.get( COMPLETED_MEDICATIONS_TABLE.getNum() ) ;
            if ( !table.getTagName().equals( "table" ) ) {
                fail( "Element is not a table but a " + table.getTagName() );
            }
            return table;
        } else {
            return null;
        }
    }

}
