/*
 * -GEHC CONFIDENTIAL-
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
package com.ge.onchron.verif.howsteps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.ge.onchron.verif.pages.ReportTabPage;
import com.ge.onchron.verif.pages.utils.PageUtils;

import static com.ge.onchron.verif.TestSystemParameters.WINDOW_HANDLES_NUMBER;
import static net.serenitybdd.core.Serenity.sessionVariableCalled;

public class ReportTabSteps {

    private ReportTabPage reportTabPage;

    private static final Logger LOGGER = LoggerFactory.getLogger( ReportTabSteps.class );

    public void checkUserWasNavigatedToReportTabWithTitle( String tabTitle ) {
        int originalTabCount = sessionVariableCalled( WINDOW_HANDLES_NUMBER );
        reportTabPage.waitForXNewTabToOpen( 1, originalTabCount );
        PageUtils.switchToNewestTab();
        String actualTabTitle = PageUtils.getTabTitle();

        LOGGER.info( STR."The PDF Report tab was loaded with title: \{tabTitle}" );
        assertEquals( "The PDF Report tab was not loaded correctly", tabTitle, actualTabTitle );
    }

    public void checkNumberOfNewPDFReportTabOpened( int tabNumber ) {
        int originalTabCount = sessionVariableCalled( WINDOW_HANDLES_NUMBER );
        reportTabPage.waitForXNewTabToOpen( tabNumber, originalTabCount );
    }

    public void checkPDFContentIsLoaded() {
        boolean isContentLoaded = reportTabPage.isContentLoaded();
        assertTrue( "Report tab content was not loaded", isContentLoaded );
    }

    public void checkPDFReportTabHeaderContainsReportTitle( String expectedReportTitle ) {
        String actualReportTitle = reportTabPage.getHeaderText();
        LOGGER.info( STR."The PDF report tab header is: \{actualReportTitle}" );
        assertEquals( "The PDF report tab header does not contain the expected report title", expectedReportTitle, actualReportTitle );
    }

    public void clickOnDownloadButtonOnPDFReportTab() {
        reportTabPage.clickDownloadOnHeader();
    }

    public void closePDFReportTab() {
        PageUtils.closeCurrentTabAndSwitchToFirstTab();
    }

    public void navigateToReportTabX( int tabNumber ) {
        int originalTabCount = sessionVariableCalled( WINDOW_HANDLES_NUMBER );
        int targetTabIndex = originalTabCount + tabNumber - 1;
        PageUtils.switchToTabByIndex( targetTabIndex );
    }
}

