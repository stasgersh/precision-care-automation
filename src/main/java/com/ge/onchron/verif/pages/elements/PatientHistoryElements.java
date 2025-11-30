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

import static com.ge.onchron.verif.pages.elements.PatientHistoryElements.PATIENT_HISTORY_ELEMENTS.SURGICAL_HISTORY_HEADER;
import static com.ge.onchron.verif.pages.elements.PatientHistoryElements.PATIENT_HISTORY_ELEMENTS.SURGICAL_HISTORY_TABLE;

import static junit.framework.TestCase.fail;

public class PatientHistoryElements {

    public enum PATIENT_HISTORY_ELEMENTS {
        ANCHOR( 0 ),
        PATIENT_HISTORY( 1 ),
        SURGICAL_HISTORY_HEADER( 2 ),
        SURGICAL_HISTORY_TABLE( 3 ),
        FAMILY_HISTORY_HEADER( 4 ),
        FAMILY_HISTORY_TABLE( 5 ),
        SOCIAL_HISTORY_HEADER( 6 ),
        SOCIAL_HISTORY_TABLE( 7 ),
        MARITAL_STATUS( 8 ),
        SUBSTANCE_HISTORY_HEADER( 9 ),
        SUBSTANCE_HISTORY_TABLE( 10 );

        private int num;

        PATIENT_HISTORY_ELEMENTS( int num ) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }
    }

    private List<WebElementFacade> patientHistoryElements;

    public PatientHistoryElements( WebElementFacade patientHistoryView ) {
        /*
         * elements on Patient History section:
         * 0. anchor
         * 1. "Patient history"
         * 2. "Surgical history" header
         * 3. "Surgical history" table
         * 4. "Family history" header
         * 5. "Family history" table
         * 6. "Social history" header
         * 7. "Social history" table
         * 8. "Marital status"
         * 9. "Substance history" header
         * 10. "Substance history" table
         *
         */
        this.patientHistoryElements = patientHistoryView.thenFindAll( By.xpath( "child::*" ) );
    }

    public WebElementFacade getSurgicalHistoryTable() {
        WebElementFacade table = patientHistoryElements.get( SURGICAL_HISTORY_TABLE.getNum() );
        if ( !table.getTagName().equals( "table" ) ) {
            fail( "Element is not a table but a " + table.getTagName() );
        }
        return table;
    }

    public WebElementFacade getSurgicalHistoryHeader() {
        WebElementFacade header = patientHistoryElements.get( SURGICAL_HISTORY_HEADER.getNum() );
        if ( !header.getTagName().equals( "div" ) ) {
            fail( "Element is not a div (header) but a " + header.getTagName() );
        }
        return header;
    }
}