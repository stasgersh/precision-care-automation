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


package com.ge.onchron.verif.pages.sections;

import java.util.List;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

import com.ge.onchron.verif.howsteps.PatientDataSyncBannerSteps;
import com.ge.onchron.verif.model.BannerNotification;
import com.ge.onchron.verif.pages.SimplePage;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.ListOfWebElementFacades;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.pages.utils.PageUtils.currentlyContainsWebElements;
import static com.ge.onchron.verif.utils.Utils.waitingTimeExpired;

public class PatientDataSyncBanner extends SimplePage {

    private final String IMMEDIATE_CHILD_BANNER_NOTIFICATION_SELECTOR = "./*[contains(@class,'flex')]";
    private final String BANNER_NOTIFICATION_MESSAGE_SELECTOR = ".//*[contains(@class,'BannerNotification-module_msg')]";
    private final String BANNER_NOTIFICATION_BUTTON_SELECTOR = "[class*='PatientSyncNotification-module_hyperlink']";
    private final String CHILD_ELEMENT_SELECTOR = "./*";

    @FindBy( css = "[class^='PatientDetails-module_container']" )
    private WebElementFacade patientDetails;
    private static final Logger LOGGER = LoggerFactory.getLogger( PatientDataSyncBannerSteps.class );

    private WebElementFacade getPatientSyncBanner() {
        // Get only the immediate child elements of patientDetails (so, notification banner from the side panel is not collected here)
        ListOfWebElementFacades notificationBannerContainers = patientDetails.thenFindAll( By.xpath( IMMEDIATE_CHILD_BANNER_NOTIFICATION_SELECTOR ) );
        if ( !notificationBannerContainers.isEmpty() ) {
            // If more notification banners are available above the patient sync banner, then the last one should contain the patient status sync banner (and the Trial Eligibility info banner)
            WebElementFacade patientSyncBannerCandidateContainer = notificationBannerContainers.getLast();
            List<WebElementFacade> syncBanners = patientSyncBannerCandidateContainer.thenFindAll( By.xpath( BANNER_NOTIFICATION_MESSAGE_SELECTOR ) );
            WebElementFacade patientSyncBannerCandidate = syncBanners.getFirst();   // 1st: patient sync banner, 2nd: Clinical trial eligibility data banner
            if ( currentlyContainsWebElements( patientSyncBannerCandidate, By.xpath( CHILD_ELEMENT_SELECTOR ) ) ) {
                return patientSyncBannerCandidate;
            }
        }
        return null;
    }

    private WebElementFacade getPatientSyncTrialStatusBanner() {
        // Get only the immediate child elements of patientDetails (so, notification banner from the side panel is not collected here)
        ListOfWebElementFacades notificationBannerContainers = patientDetails.thenFindAll( By.xpath( IMMEDIATE_CHILD_BANNER_NOTIFICATION_SELECTOR ) );
        if ( !notificationBannerContainers.isEmpty() ) {
            // If more notification banners are available above the patient sync banner, then the last one should contain the patient status sync banner (and the Trial Eligibility info banner)
            WebElementFacade patientSyncBannerTrialStatusCandidate = patientDetails
                    .thenFindAll( By.xpath( IMMEDIATE_CHILD_BANNER_NOTIFICATION_SELECTOR ) ).getLast()
                    .thenFindAll( By.xpath( BANNER_NOTIFICATION_MESSAGE_SELECTOR ) ).getLast();;// 2nd: Clinical trial eligibility data banner
            if ( patientSyncBannerTrialStatusCandidate !=null ) {
                return patientSyncBannerTrialStatusCandidate;
            }
        }
        return null;
    }

    public int getPatientSyncBannerLastSync() {
        ListOfWebElementFacades notificationBanners = patientDetails.thenFindAll( By.xpath( IMMEDIATE_CHILD_BANNER_NOTIFICATION_SELECTOR ) );
        WebElement webElement = notificationBanners.getLast().findElement( By.xpath( BANNER_NOTIFICATION_MESSAGE_SELECTOR ) );
        List<String> str = List.of( webElement.findElement( By.cssSelector( "[class*='PatientSyncNotification-module_no-print']" ) ).getText().split( " " ) );
        return Integer.valueOf( str.getFirst() );
    }

    /**
     * Wait for patient sync banner with webdriver max timeout
     *
     * @return A boolean about the patient sync banner visibility
     */
    public boolean isVisible() {
        WebElementFacade patientSyncBanner = waitUntilPresent();
        return patientSyncBanner != null;
    }

    public boolean isCurrentlyVisible() {
        return getPatientSyncBanner() != null;
    }

    public WebElementFacade waitUntilPresent() {
        long startTime = System.currentTimeMillis();
        boolean waitingTimeExpired;
        int timeoutInMillis = Long.valueOf( getWaitForTimeout().toMillis() ).intValue();
        WebElementFacade patientSyncBanner;
        do {
            patientSyncBanner = getPatientSyncBanner();
            waitingTimeExpired = waitingTimeExpired( startTime, timeoutInMillis );
        } while ( patientSyncBanner == null && !waitingTimeExpired );
        return patientSyncBanner;
    }

    public BannerNotification getBannerNotification() {
        WebElementFacade patientSyncBanner = getPatientSyncBanner();
        BannerNotification bannerNotificationDetails = createBannerNotificationDetailsFrom(patientSyncBanner);
        return bannerNotificationDetails;
    }

    public BannerNotification getBannerTrialNotification() {
        WebElementFacade patientSyncTrialStatusBanner = getPatientSyncTrialStatusBanner();
        BannerNotification bannerNotificationDetails = createBannerNotificationDetailsFrom(patientSyncTrialStatusBanner);
        return bannerNotificationDetails;
    }

    private BannerNotification createBannerNotificationDetailsFrom(WebElementFacade patientSyncBanner) {
        String bannerText = patientSyncBanner.getText();
        Dimension height = patientSyncBanner.getSize();
        String colorString = patientSyncBanner.thenFindAll( By.xpath( ".." ) )
                                              .stream()
                                              .findFirst()
                                              .orElseThrow( () -> new RuntimeException( "No parent item found at banner" ) )
                                              .getCssValue( "background-color" );

        String buttonTextInBanner = null;
        WebElementFacade buttonInBanner = getButtonFromPatientSyncBanner( patientSyncBanner );
        if ( buttonInBanner != null ) {
            buttonTextInBanner = buttonInBanner.getText();
            bannerText = bannerText.replace( buttonTextInBanner, "" ).replace( "\n", "" );
        }
        BannerNotification bannerNotification = new BannerNotification( bannerText.trim() );
        bannerNotification.setButtonText( buttonTextInBanner );
        bannerNotification.setBackgroundColor( Color.fromString( colorString ) );
        bannerNotification.setBarSize( height.getHeight() );

        return bannerNotification;
    }

    public void clickOnButtonOnPatientSyncBanner( BannerNotification expectedBannerNotif ) {
        WebElementFacade patientSyncBanner = getPatientSyncBanner();
        WebElementFacade buttonInBanner = getButtonFromPatientSyncBanner( patientSyncBanner );
        if ( buttonInBanner != null ) {
            LOGGER.info( STR."Clicking on required button in patient sync banner \{buttonInBanner}" );
            assertEquals( "Required button was not found in patient sync banner.", expectedBannerNotif.getButtonText(), buttonInBanner.getText() );
            elementClicker( buttonInBanner );
        }
    }

    private WebElementFacade getButtonFromPatientSyncBanner( WebElementFacade patientSyncBanner ) {
        if ( currentlyContainsWebElements( patientSyncBanner, By.cssSelector( BANNER_NOTIFICATION_BUTTON_SELECTOR ) ) ) {
            return patientSyncBanner.then( By.cssSelector( BANNER_NOTIFICATION_BUTTON_SELECTOR ) );
        }
        return null;
    }

}
