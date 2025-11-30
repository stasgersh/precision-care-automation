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
package com.ge.onchron.verif.whatsteps.popups;

import java.util.List;

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.ge.onchron.verif.howsteps.NotificationPopupSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.model.NotificationPopup;
import net.thucydides.core.annotations.Steps;

import static com.ge.onchron.verif.utils.Utils.insertSystemParameters;

public class NotificationPopups {

    @Steps
    private NotificationPopupSteps notificationPopupSteps;

    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Given( "the following notification popup{s|} appeared in $timeoutInMillis milliseconds: $notificationTable" )
    @Then( "the following notification popup{s|} appear{s|} in $timeoutInMillis milliseconds: $notificationTable" )
    @Alias( "the following notification popup{s|} {is|are} visible in $timeoutInMillis milliseconds: $notificationTable" )
    public void waitAndCheckVisibleNotifications( String timeoutInMillis, List<NotificationPopup> notificationPopups ) {
        int timeoutMillis = Integer.parseInt( insertSystemParameters( timeoutInMillis ) );
        testDefinitionSteps.addStep(STR."Wait up to \{timeoutMillis} milliseconds and check for notifications to be visible");
        notificationPopupSteps.waitAndCheckVisibleNotifications( timeoutMillis, notificationPopups );
        testDefinitionSteps.logEvidence(STR."The following notifications are found as expected: \{notificationPopups}",
                "The notifications are found as expected (check previous logs)", true);
    }

    @Then( "the following notification popup{s|} {is|are} visible on the screen: $notificationTable" )
    public void checkCurrentVisibleNotifications( List<NotificationPopup> notificationPopups ) {
        testDefinitionSteps.addStep("Check current visible notifications");
        notificationPopupSteps.checkCurrentVisibleNotifications( notificationPopups );
        testDefinitionSteps.logEvidence(STR."The following notifications are visible on screen as expected: \{notificationPopups}",
                "The notifications are visible as expected (check previous logs)", true);
    }

    @Then( "$numOfOverlappedPopups additional popup{s|} {is|are} overlapped by the last visible popup" )
    @Alias( "$numOfOverlappedPopups additional popups are still overlapped by the last visible popup" )
    public void checkNumOfOverlappedNotifications( int numOfOverlappedPopups ) {
        testDefinitionSteps.addStep("Check number of overlapped notifications");
        notificationPopupSteps.checkNumOfOverlappedNotifications( numOfOverlappedPopups );
        testDefinitionSteps.logEvidence(STR."The number of overlapped notifications on screen as expected: \{numOfOverlappedPopups}",
                "The number of overlapped notifications on screen as expected (check previous logs)", true);
    }

    @Then( "new notification popup does not appear in $timeoutInMillis milliseconds" )
    public void checkNotificationsNotAppear( String timeoutInMillis ) {
        int timeoutMillis = Integer.parseInt( insertSystemParameters( timeoutInMillis ) );
        testDefinitionSteps.addStep(STR."Check notifications do not appear in \{timeoutMillis} milliseconds");
        notificationPopupSteps.checkNewNotificationNotAppear( timeoutMillis );
        testDefinitionSteps.logEvidence(STR."The notification didn't appear in \{timeoutMillis} milliseconds",
                "The notification didn't appear (check previous logs)", true);
    }

    @Then( "all notification popups disappeared" )
    @Alias( "notification popup is not displayed" )
    public void checkNotificationsNotDisplayed() {
        testDefinitionSteps.addStep("Check notifications not displayed");
        notificationPopupSteps.checkNotificationsNotDisplayed();
        testDefinitionSteps.logEvidence("The notification not displayed as expected",
                "The notification not displayed as expected (check previous logs)", true);
    }

    @Then( "the '$buttonText' button is available on all notification popups" )
    public void checkButtonAvailabilityOnAllNotifications( String buttonText ) {
        testDefinitionSteps.addStep(STR."Check button: \{buttonText} availability on all notification popups");
        notificationPopupSteps.checkButtonAvailabilityOnAllNotifications( buttonText );
        testDefinitionSteps.logEvidence(STR."The button \{buttonText} is available on all notifications as expected",
                STR."The button \{buttonText} is available on all notifications (check previous logs)", true);

    }

    @When( "the user clicks on the '$buttonText' button on the following notification popup: $notificationPopup" )
    public void clickOnButtonOnNotification( String buttonText, NotificationPopup notificationPopup ) {
        testDefinitionSteps.addStep(STR."Click on the '\{buttonText}' button on the notification popups: \{notificationPopup}");
        notificationPopupSteps.clickOnButtonOnNotification( buttonText, notificationPopup );
        testDefinitionSteps.logEvidence(STR."The button \{buttonText} was clicked successfully on the notification popups \{notificationPopup}",
                STR."The button \{buttonText} was clicked successfully (without errors)", true);
    }

    @When( "the user clicks on the '$linkText' link on the following notification popup: $notificationPopup" )
    public void clickOnLinkOnNotification( String linkText, NotificationPopup notificationPopup ) {
        testDefinitionSteps.addStep( STR."Click on the '\{linkText}' link on the notification popups: \{notificationPopup}" );
        notificationPopupSteps.clickOnLinkOnNotification( linkText, notificationPopup );
        testDefinitionSteps.logEvidence( STR."The link \{linkText} was clicked on the notification popups: \{notificationPopup}",
                STR."The link \{linkText} was clicked successfully on the notification popups: \{notificationPopup}", true );
    }

    @When( "the user clicks on 'X' on the following notification popup: $notificationPopup" )
    public void closeNotificationPopup( NotificationPopup notificationPopup ) {
        testDefinitionSteps.addStep(STR."Click on 'X' to close the notifications: \{notificationPopup}");
        notificationPopupSteps.closeNotificationPopup( notificationPopup );
        testDefinitionSteps.logEvidence(STR."The notification popups \{notificationPopup} were closed successfully",
                STR."The button notification popups were closed successfully (without errors)", true);
    }

}
