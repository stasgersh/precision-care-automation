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
package com.ge.onchron.verif.howsteps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

import com.ge.onchron.verif.pages.PatientPage;
import net.serenitybdd.core.Serenity;

import static com.ge.onchron.verif.TestSystemParameters.PATIENT_PAGE_URL;
import static com.ge.onchron.verif.pages.utils.PageElementUtils.waitForCompleteDocument;
import static net.serenitybdd.core.Serenity.getDriver;

public class PatientScreenSteps {

    private PatientPage patientPage;
    private static final Logger LOGGER = LoggerFactory.getLogger( PatientScreenSteps.class );

    public void verifyPatientPageIsLoaded() {
        waitForCompleteDocument();
        boolean visible = patientPage.isVisible();
        LOGGER.info( STR."Patient page visibility: \{visible}" );
        assertTrue( "Patient page is not displayed", visible );
    }

    public boolean patientPageIsCurrentlyVisible() {
        return patientPage.isCurrentlyVisible();
    }

    public void navigateToInitialPatientPage() {
        if ( !Serenity.getDriver().getCurrentUrl().endsWith( PATIENT_PAGE_URL ) ) {
            LOGGER.info( STR."Navigating to \{PATIENT_PAGE_URL} and open it" );
            patientPage.open();
        }
    }

    public void refreshIfPageIsVisible() {
        if ( patientPage.isCurrentlyVisible() ) {
            getDriver().navigate().refresh();
            verifyPatientPageIsLoaded();
        }
    }

}
