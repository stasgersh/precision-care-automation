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
package com.ge.onchron.verif.howsteps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.l3report.L3ModalHeader;
import com.ge.onchron.verif.model.l3report.L3ReportHeader;
import com.ge.onchron.verif.model.l3report.L3ReportHeaderOnEventsList;
import com.ge.onchron.verif.pages.sections.modal.L3Report.L3MultipleReportsModal;
import com.ge.onchron.verif.pages.sections.modal.L3Report.L3SingleReportModal;
import java.util.List;

import static com.ge.onchron.verif.TestSystemParameters.TEST_DATA_CLASSPATH;
import static com.ge.onchron.verif.utils.FileUtils.readFromFile;
import static com.ge.onchron.verif.utils.TextUtils.limitString;
import static com.ge.onchron.verif.utils.Utils.normalizeLineEndings;

public class L3ModalSteps {

    private L3SingleReportModal l3SingleReportModal;
    private L3MultipleReportsModal l3MultipleReportsModal;

    private static final Logger LOGGER = LoggerFactory.getLogger( L3ModalSteps.class );

    public void checkL3ModalIsDisplayed( boolean isVisible ) {
        boolean l3ModalIsVisible = l3SingleReportModal.isVisible();
        LOGGER.info( STR."The L3 Modal is visible: \{l3ModalIsVisible}" );
        assertThat( "L3 modal visibility is not ok.", l3ModalIsVisible, is( equalTo( isVisible ) ) );
    }

    public void checkL3SingleReportModalHeaderData( L3ReportHeader expectedHeaderData ) {
        L3ReportHeader actualL3ModalHeader = l3SingleReportModal.getHeader();
        LOGGER.info( STR."The actual L3 Modal header is: \{actualL3ModalHeader}" );
        assertEquals( "L3 modal header is not ok.", expectedHeaderData, actualL3ModalHeader );
    }

    public void checkL3SingleReportModalBody( String expectedFileName ) {
        l3SingleReportModal.waitUntilContentLoadingIndicatorDisappears();
        String expectedBody = normalizeLineEndings( readFromFile( TEST_DATA_CLASSPATH + expectedFileName ) );
        LOGGER.info( String.format( "Comparing the actual modal body with %s", TEST_DATA_CLASSPATH + expectedFileName ) );
        String observedBody = normalizeLineEndings( l3SingleReportModal.getBody() );
        if ( observedBody.contains( "Failed to load content" ) ) {
            fail( "The L3 Single Modal was failed to load content" );
        }
        LOGGER.info( String.format( "The actual L3 Single Modal body is: %s", limitString( observedBody, 1000 ) ) );
        assertEquals( "The L3 Single Modal body content is not ok.", expectedBody, observedBody );
    }

    public void closeL3Modal() {
        l3SingleReportModal.close();
    }

    public void closeL3ModalIfVisible() {
        if ( l3SingleReportModal.isCurrentlyVisible() ) {
            l3SingleReportModal.close();
        }
    }

    public void checkSelectedReportHeaderOnMultipleReportModal( L3ReportHeader expectedHeaderData ) {
        L3ReportHeader selectedReportHeader = l3MultipleReportsModal.getSelectedReportHeader();
        LOGGER.info( String.format( "The header of selected report on L3 Multiple reports modal header is: %s", selectedReportHeader ) );
        assertEquals( "The header of selected report on L3 Multiple reports modal header is not ok.", expectedHeaderData, selectedReportHeader );
    }

    public void checkSelectedReportBodyOnMultipleReportModal( String expectedFileName ) {
        String expectedBody = normalizeLineEndings( readFromFile( TEST_DATA_CLASSPATH + expectedFileName ) );
        String observedBody = normalizeLineEndings( l3MultipleReportsModal.getSelectedReportBody() );
        LOGGER.info( String.format( "The actual L3 report body on Multiple Report Modal is: %s", observedBody ) );
        assertEquals( "The actual L3 report body on Multiple Report Modal is not ok.", expectedBody, observedBody );
    }

    public void checkEventsListOnMultipleReportModal( List<L3ReportHeaderOnEventsList> expectedEventsList ) {
        LOGGER.info( String.format( "Expected events list on L3 Multiple reports modal: %s", expectedEventsList ) );
        List<L3ReportHeaderOnEventsList> observedEventsList = l3MultipleReportsModal.getEventsList();
        LOGGER.info( String.format( "Observed events list on L3 Multiple reports modal: %s", observedEventsList ) );
        assertEquals( "The header of selected report on L3 Multiple reports modal header is not ok.", expectedEventsList, observedEventsList );
    }

    public void clickEventOnEventsListOnMultipleReportModal( L3ReportHeaderOnEventsList event ) {
        l3MultipleReportsModal.clickEventOnEventList( event );
    }

    public void checkMultipleReportModalHeader( L3ModalHeader expectedModalHeader ) {
        L3ModalHeader modalHeader = l3MultipleReportsModal.getModalHeader();
        LOGGER.info( String.format( "The L3 Multiple report modal header: %s", modalHeader ) );
        assertEquals( "The L3 Multiple report modal header is not ok.", expectedModalHeader, modalHeader );
    }

}

