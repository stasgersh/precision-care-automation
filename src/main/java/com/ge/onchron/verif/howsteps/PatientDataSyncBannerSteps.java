/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2023, 2023, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE HealthCare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */


package com.ge.onchron.verif.howsteps;

import com.ge.onchron.verif.model.BannerNotification;
import com.ge.onchron.verif.model.StringList;
import com.ge.onchron.verif.pages.sections.PatientDataSyncBanner;
import com.ge.onchron.verif.pages.sections.PatientHeader;

import org.openqa.selenium.support.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.ge.onchron.verif.utils.Utils.waitingTimeExpired;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PatientDataSyncBannerSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger( PatientDataSyncBannerSteps.class );
    private static PatientDataSyncBanner patientDataSyncBanner;
    private static PatientHeader patientHeader;

    public void checkPatientDataSyncBannerVisibility( Boolean isVisible ) {
        LOGGER.info( "Checking Visibility of Patient Data Sync Banner" );
        assertThat( "Patient Data Sync Banner is not visible", patientDataSyncBanner.isVisible(), is( equalTo( isVisible ) ) );
    }

    public void waitUntilPatientStatusIsLoaded() {
        patientDataSyncBanner.waitUntilPresent();
        boolean currentlyVisible = patientDataSyncBanner.isCurrentlyVisible();
        LOGGER.info(STR."Patient sync banner visibility:\{currentlyVisible}");
        assertTrue( "Patient sync banner is not visible.", currentlyVisible);
    }

    public void checkPatientSyncBannerMsg( String expectedMessage ) {
        String observedMessage = patientDataSyncBanner.getBannerNotification().getMessage();
        LOGGER.info("Patient Sync Banner Observed Message:\n{}", observedMessage);
        assertTrue( String.format( "Patient sync banner message is not ok. Expected: %s, Observed: %s", expectedMessage, observedMessage ),
                observedMessage.matches( expectedMessage ) );
    }

    public void checkPatientSyncBannerMsg( String expectedMessage, boolean isDisplayed ) {
        String observedMessage = patientDataSyncBanner.getBannerNotification().getMessage();
        if ( isDisplayed ) {
            LOGGER.info(STR."Patient Sync Banner Message:\n\{expectedMessage} should be displayed");
            assertThat( String.format( STR."Patient sync banner message is not ok.\nExpected message to be displayed was:\{expectedMessage}, but the observed message was: %s", observedMessage ),
                    observedMessage ,is(containsString(expectedMessage )));
        }
        if ( !isDisplayed ) {
            LOGGER.info(STR."Patient Sync Banner Message:\n\{expectedMessage} should not be displayed");
            assertThat( String.format( "Patient sync banner message is not ok.\nExpected: Not to be displayed, but the observed message was: %s", observedMessage ),
                    observedMessage ,not(containsString(expectedMessage )));
        }
    }

    public void checkPatientSyncBannerMsgWithIndicationsDetails(BannerNotification expectedBannerNotificationDetails) {
        assertBannerNotificationMsg( patientDataSyncBanner.getBannerNotification(),expectedBannerNotificationDetails);
    }

    public void checkPatientSyncBannerMsgForTrialsWithIndicationsDetails(BannerNotification expectedBannerNotificationTrialDetails) {
        assertBannerNotificationMsg( patientDataSyncBanner.getBannerTrialNotification(),expectedBannerNotificationTrialDetails);
    }

    public void waitForPatientSyncBanner( int timeoutInMillis, BannerNotification expectedPatientSyncBanner ) {
        long startTime = System.currentTimeMillis();
        boolean waitingTimeExpired;
        LOGGER.info( String.format( "Waiting for patient sync notification banner started, timeout: %s ms", timeoutInMillis ) );
        BannerNotification observedPatientSyncBanner;
        do {
            observedPatientSyncBanner = patientDataSyncBanner.getBannerNotification();
            waitingTimeExpired = waitingTimeExpired( startTime, timeoutInMillis );
            if (expectedPatientSyncBanner.getBackgroundColor() == null) {
                observedPatientSyncBanner.setBackgroundColor(null);
            }
            if (expectedPatientSyncBanner.getBarSize() == 0) {
                observedPatientSyncBanner.setBarSize(0);
            }
        } while ( !expectedPatientSyncBanner.equals( observedPatientSyncBanner ) && !waitingTimeExpired );
        LOGGER.info( "Waiting for patient sync notification banner finished" );
        LOGGER.info(STR."Expected: \{expectedPatientSyncBanner}" );
        LOGGER.info(STR."Observed Banner: \{observedPatientSyncBanner}");
        assertEquals( "Patient sync banner is not ok.", expectedPatientSyncBanner, observedPatientSyncBanner );
    }

    public void clickOnButtonOnPatientSyncBanner( BannerNotification bannerNotification ) {
        patientDataSyncBanner.clickOnButtonOnPatientSyncBanner( bannerNotification );
    }

    public void comparePatientsLastSyncTime(StringList patientsNames)
    {
        List<Integer> lastSyncTime = getPatientsLastSyncTime(patientsNames);
        assertThat("The last sync is the same for both patients", lastSyncTime.getLast(), is(not(lastSyncTime.getFirst())));
    }
    public void checkPatientsSyncTimeOrder(StringList patientsNames){
        List<Integer> lastSyncTime = getPatientsLastSyncTime(patientsNames);
        assertTrue(STR."Last synced patient is \{patientsNames.getList().getFirst()}",
                lastSyncTime.getLast() > lastSyncTime.getFirst());
    }
    public List<Integer> getPatientsLastSyncTime(StringList patientsNames){
        List<Integer> lastSyncTime = new ArrayList<>();
        for (String patientName : patientsNames.getList()) {
            patientHeader.clickOnPatientSelect();
            patientHeader.selectPatientByName(patientName);
            waitUntilPatientStatusIsLoaded();
            lastSyncTime.add(patientDataSyncBanner.getPatientSyncBannerLastSync());
            LOGGER.info(STR."\{patientName} patient last sync time: \{lastSyncTime.getLast()}");
        }
        return lastSyncTime;
    }

    private void assertBannerNotificationMsg(BannerNotification observedBannerNotificationDetails,
                                             BannerNotification expectedBannerNotificationDetails) {
        String observedMessage = observedBannerNotificationDetails.getMessage();
        Color observedSyncMessageBackgroundColor = observedBannerNotificationDetails.getBackgroundColor();
        int observedBarSize = observedBannerNotificationDetails.getBarSize();

        LOGGER.info(STR."Patient Sync Banner Observed Trial Message:\n\{observedMessage} and Sync Message Background Color of \{observedSyncMessageBackgroundColor}");
        assertThat( String.format( "Patient sync banner message is not ok. Expected: %s, Observed: %s",
                        expectedBannerNotificationDetails.getMessage(), observedMessage ), observedMessage.matches(expectedBannerNotificationDetails.getMessage()) );
        assertThat( String.format( "Patient sync banner message background color is not ok. Expected: %s, Observed: %s",
                        expectedBannerNotificationDetails.getBackgroundColor().asHex(), observedSyncMessageBackgroundColor.asHex()), observedSyncMessageBackgroundColor, is(expectedBannerNotificationDetails.getBackgroundColor()) );
        assertThat( String.format( "Patient sync message bar size ok. Expected: %s, Observed: %s",
                        expectedBannerNotificationDetails.getBarSize(), observedBarSize), observedBarSize, is( expectedBannerNotificationDetails.getBarSize()) );
    }
}