/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2021, 2021, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.whatsteps;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.ge.onchron.verif.howsteps.ReportTabSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import net.thucydides.core.annotations.Steps;

public class ReportTab {

    @Steps
    private ReportTabSteps reportTabSteps;
    @Steps
    private TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @When( "the user clicks on the Download button on the PDF report tab" )
    public void clickOnDownloadButtonOnPDFReportTab() {
        testDefinitionSteps.addStep( "User clicks on the Download button on the PDF report tab" );
        reportTabSteps.clickOnDownloadButtonOnPDFReportTab();
        testDefinitionSteps.logEvidence( "User clicked on the Download button on the PDF report tab",
                "User clicked on the Download button on the PDF report tab", true );
    }

    @When( "the user closes the PDF report tab" )
    public void closePDFReportTab() {
        testDefinitionSteps.addStep( "User closes the PDF report tab" );
        reportTabSteps.closePDFReportTab();
        testDefinitionSteps.logEvidence( "User closed the PDF report tab",
                "User closed the PDF report tab", true );
    }

    @When( "the user navigates to the $tabNumber{st|nd|rd|th} PDF report tab" )
    public void switchToPDFReportTab( int tabNumber ) {
        testDefinitionSteps.addStep( STR."User navigates to the \{tabNumber}{st|nd|rd|th} PDF report tab" );
        reportTabSteps.navigateToReportTabX( tabNumber);
        testDefinitionSteps.logEvidence( STR."User navigated to the \{tabNumber}{st|nd|rd|th} PDF report tab",
                STR."User navigated to the \{tabNumber}{st|nd|rd|th} PDF report tab", true );
    }

    @Then( "a new browser tab titled \"$tabTitle\" is opened for the user" )
    public void newBrowserTabWasOpenedWithTitle( String tabTitle ) {
        testDefinitionSteps.addStep( "User was navigated to new browser tab" );
        reportTabSteps.checkUserWasNavigatedToReportTabWithTitle( tabTitle );
        testDefinitionSteps.logEvidence( "Splash page loaded without error",
                "Page loaded with no error", true );
    }

    @Then( "$int new browser tabs is opened for the user" )
    public void newBrowserTabsWereOpened( int tabNumber ) {
        testDefinitionSteps.addStep( STR."Check \{tabNumber} new browser tabs are opened for the user" );
        reportTabSteps.checkNumberOfNewPDFReportTabOpened( tabNumber );
        testDefinitionSteps.logEvidence( STR."\{tabNumber} new browser tabs are opened for the user",
                STR."\{tabNumber} new browser tabs are opened for the user", true );
    }

    @Then( "the PDF report content is loaded in the new tab" )
    public void checkPDFTabContentIsLoaded() {
        testDefinitionSteps.addStep( "Check PDF report tab content is displayed" );
        reportTabSteps.checkPDFContentIsLoaded();
        testDefinitionSteps.logEvidence( "PDF report tab content is loaded",
                "PDF report tab content is loaded", true );
    }

    @Then( "the PDF report tab header contains the report title: \"$reportTitle\"" )
    public void checkPDFReportTabHeaderContainsReportTitle( String reportTitle ) {
        testDefinitionSteps.addStep( STR."Check PDF report tab header contains the report title: \{reportTitle}" );
        reportTabSteps.checkPDFReportTabHeaderContainsReportTitle( reportTitle );
        testDefinitionSteps.logEvidence( "PDF report tab header contains the report title",
                STR."PDF report tab header contains the report title: \{reportTitle}", true );
    }

}
