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

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.ge.onchron.verif.model.NotificationPopup;
import com.ge.onchron.verif.pages.elements.NotificationPopups;

import static com.ge.onchron.verif.utils.Utils.waitMillis;
import static com.ge.onchron.verif.utils.Utils.waitingTimeExpired;

public class NotificationPopupSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger( NotificationPopupSteps.class );

    NotificationPopups notificationPopups;

    private List<NotificationPopup> retrieveNotifications( int timeoutInMillis ) {
        List<NotificationPopup> initialNotifications = notificationPopups.getNotifications();
        int numOfInitialNotifications = initialNotifications.size();

        long startTime = System.currentTimeMillis();
        boolean waitingTimeExpired;

        LOGGER.info( String.format( "Waiting for notification popup started, timeout: %s ms", timeoutInMillis ) );
        do {
            waitingTimeExpired = waitingTimeExpired( startTime, timeoutInMillis );
        } while ( !numOfNotificationsChanged( numOfInitialNotifications ) && !waitingTimeExpired );
        LOGGER.info( "Waiting for notification popup finished" );

        if ( waitingTimeExpired ) {
            // Do not fail because the test can expect that popup does not appear and the returned notifications list is empty
            LOGGER.info( String.format( "Notification popup did not appear in %s milliseconds", timeoutInMillis ) );
        }

        waitMillis( 2000 ); // wait for finishing notification popup animation (if more notifications are detected at the same time)
        List<NotificationPopup> observedNotifications = notificationPopups.getNotifications();
        LOGGER.info( "Observed notifications: " + observedNotifications );
        return observedNotifications;
    }

    private boolean numOfNotificationsChanged( int numOfInitialNotifications ) {
        return notificationPopups.getNotifications().size() != numOfInitialNotifications;
    }

    public void waitAndCheckVisibleNotifications( int timeoutInMillis, List<NotificationPopup> expectedNotificationPopups ) {
        List<NotificationPopup> observedNotifications = retrieveNotifications( timeoutInMillis );
        List<NotificationPopup> visibleNotifications = observedNotifications.stream()
                                                                            .filter( NotificationPopup::isVisible )
                                                                            .collect( Collectors.toList() );

        LOGGER.info(STR."Number of actual visible notification popups is: \{visibleNotifications.size()}");
        LOGGER.info(STR."The actual notifications are: \{visibleNotifications}");
        assertEquals( "Number of expected and observed visible notification popups do not match",
                expectedNotificationPopups.size(), visibleNotifications.size() );

        for ( NotificationPopup expectedNotificationPopup : expectedNotificationPopups ) {
            boolean found = visibleNotifications.contains( expectedNotificationPopup );
            LOGGER.info(STR."Expected notification: \{expectedNotificationPopup} is found: \{found}");
            assertTrue( "Required notification was not found: " + expectedNotificationPopup, found );
        }
    }

    public void clickOnButtonOnNotification( String buttonText, NotificationPopup notificationPopups ) {
        this.notificationPopups.clickOnButtonOnNotification( buttonText, notificationPopups );
    }

    public void clickOnLinkOnNotification( String linkText, NotificationPopup notificationPopups ) {
        this.notificationPopups.clickOnLinkOnNotification( linkText, notificationPopups );
    }

    public void checkButtonAvailabilityOnAllNotifications( String buttonText ) {
        List<NotificationPopup> observedNotifications = notificationPopups.getNotifications();
        observedNotifications.forEach( notification -> {
            boolean isButtonVisible = notificationPopups.getButtonVisibility( buttonText, notification );
            LOGGER.info(STR."The button: \{buttonText} on notification: \{notification} is visible: \{isButtonVisible}");
            assertTrue( String.format( "The %s button is not visible on %s notification popup", buttonText, notification ), isButtonVisible );
        } );
    }

    public void checkNumOfOverlappedNotifications( int nbOfOverlappedPopups ) {
        List<NotificationPopup> observedNotifications = notificationPopups.getNotifications();
        List<NotificationPopup> stackedNotifications = observedNotifications.stream()
                                                                            .filter( NotificationPopup::inStack )
                                                                            .collect( Collectors.toList() );

        // The first "stacked" notification is visible, but the others are overlapped
        int overlappedNum = stackedNotifications.size() - 1;
        LOGGER.info(STR."The actual number of overlapped notifications is: \{overlappedNum}");
        assertEquals( "Number of expected and observed visible notification popups do not match",
                nbOfOverlappedPopups, overlappedNum );
    }

    public void closeNotificationPopup( NotificationPopup notificationPopup ) {
        notificationPopups.closeNotificationPopup( notificationPopup );
        waitMillis( 500 ); // wait for finishing notification popup animation
    }


    public void checkCurrentVisibleNotifications( List<NotificationPopup> expectedNotificationPopups ) {
        List<NotificationPopup> observedNotifications = notificationPopups.getNotifications();
        List<NotificationPopup> visibleNotifications = observedNotifications.stream()
                                                                            .filter( NotificationPopup::isVisible )
                                                                            .collect( Collectors.toList() );

        LOGGER.info(STR."Actual visible notification popup amount is: \{visibleNotifications.size()}");
        assertEquals( "Number of expected and observed visible notification popups do not match",
                expectedNotificationPopups.size(), visibleNotifications.size() );

        for ( NotificationPopup expectedNotificationPopup : expectedNotificationPopups ) {
            boolean found = observedNotifications.contains( expectedNotificationPopup );
            LOGGER.info(STR."Expected notification: \{expectedNotificationPopup} is found: \{found}");
            assertTrue( "Required notification was not found: " + expectedNotificationPopup, found );
        }
    }

    public void checkNewNotificationNotAppear( int timeoutInMillis ) {
        List<NotificationPopup> initialNotifications = notificationPopups.getNotifications();
        List<NotificationPopup> observedNotifications = retrieveNotifications( timeoutInMillis );
        LOGGER.info(STR."Initial notifications: \{initialNotifications}");
        LOGGER.info(STR."Observed notifications: \{observedNotifications}");
        assertTrue( String.format( "New notification popup should not appear: \nOld popups:\n%s \nNew popups: \n%s", initialNotifications, observedNotifications ),
                initialNotifications.size() == observedNotifications.size() );
    }

    public void checkNotificationsNotDisplayed() {
        List<NotificationPopup> observedNotifications = notificationPopups.getNotifications();
        LOGGER.info(STR."Check actual notifications are empty: \{observedNotifications}");
        assertTrue( "Notification popup should not be displayed: " + observedNotifications, observedNotifications.isEmpty() );
    }

    public void closeAllNotifications() {
        List<NotificationPopup> observedNotifications = notificationPopups.getNotifications();
        while ( !observedNotifications.isEmpty() ) {
            closeNotificationPopup( observedNotifications.get( 0 ) ); // close only the first popup in every loop
            observedNotifications = notificationPopups.getNotifications();
        }
    }

}
