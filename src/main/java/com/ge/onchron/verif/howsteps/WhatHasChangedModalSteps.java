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
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.howsteps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import com.ge.onchron.verif.model.Table;
import com.ge.onchron.verif.pages.sections.modal.WhatHasChangedModal;

public class WhatHasChangedModalSteps {

    private WhatHasChangedModal whatHasChangedModal;

    private static final Logger LOGGER = LoggerFactory.getLogger( WhatHasChangedModalSteps.class );

    public void checkWhatHasChangedModalIsDisplayed( boolean isVisible ) {
        boolean whatHasChangedModalIsVisible = whatHasChangedModal.isCurrentlyVisible();
        LOGGER.info( STR."The 'What has changed' modal is visible: \{whatHasChangedModalIsVisible}" );
        assertThat( "'What has changed' modal visibility is not ok.", whatHasChangedModalIsVisible, is( equalTo( isVisible ) ) );
    }

    public void checkWhatHasChangedModalHeader( String expectedTitle ) {
        String actualTitle = whatHasChangedModal.getHeader();
        LOGGER.info( STR."The actual 'What has changed' modal header is: \{actualTitle}" );
        assertEquals( "'What has changed' modal header is not ok.", expectedTitle, actualTitle );
    }

    public void closeWhatHasChangedModal() {
        whatHasChangedModal.close();
    }

    public void closeWhatHasChangedModalIfVisible() {
        if ( whatHasChangedModal.isCurrentlyVisible() ) {
            whatHasChangedModal.close();
        }
    }

    public void checkWhatHasChangedTableContent( Table expectedTable ) {
        Table observedTable = whatHasChangedModal.getWhatHasChangedTable();
        assertEquals( "The event list table is not ok on the 'What has changed' modal", expectedTable.getRows(), observedTable.getRows() );
    }

    public void clickFullReportButtonOnWhatHasChangedModal( int rowNb, String columnName ) {
        whatHasChangedModal.clickFullReportButton( rowNb, columnName );
    }

    public void hoverButtonOnWhatHasChangedModal( String buttonName, int rowNb, String columnName ) {
        whatHasChangedModal.hoverButton( buttonName, rowNb, columnName );
    }

    public void clickBackToEventListOnModal() {
        whatHasChangedModal.clickBackToEventList();
    }
}
