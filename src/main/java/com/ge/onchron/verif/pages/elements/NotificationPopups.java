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
package com.ge.onchron.verif.pages.elements;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ge.onchron.verif.howsteps.NotificationPopupSteps;
import com.ge.onchron.verif.model.NotificationPopup;
import com.ge.onchron.verif.pages.SimplePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.ListOfWebElementFacades;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.pages.utils.PageUtils.currentlyContainsElements;
import static com.ge.onchron.verif.utils.Utils.waitMillis;
import static junit.framework.TestCase.fail;

public class NotificationPopups extends SimplePage {

    private static final Logger LOGGER = LoggerFactory.getLogger( NotificationPopupSteps.class );

    private final String NOTIFICATIONS_WRAPPER_CSS_SELECTOR = "[class*='system-notification__grouping']";
    private final String SINGLE_NOTIFICATION_CSS_SELECTOR = "[class*='system-notification__container']";
    private final String NOTIFICATION_STACK_CLASS = "box--group-stack";

    private final String NOTIF_HEADER_CSS_SELECTOR = "[class*='system-notification__heading ']";
    private final String NOTIF_CONTENT_WITH_BUTTONS_CSS_SELECTOR = "[class*='system-notification__content ']";

    private final String NOTIF_CLOSE_BUTTON_CSS_SELECTOR = "[class='system-notification__close-icon']";

    @FindBy( css = NOTIFICATIONS_WRAPPER_CSS_SELECTOR )
    private WebElementFacade notificationsContainer;

    public List<NotificationPopup> getNotifications() {
        LinkedHashMap<NotificationPopup, WebElementFacade> notifications;
        try {
            notifications = getNotificationsWithWebelements();
        } catch ( StaleElementReferenceException e ) {
            LOGGER.warn( "Notifications were changed while selenium tried to collect elements. Try once again.." );
            waitMillis( 2000 ); // wait for finishing notification popup animation (if more changes detected at the same time)
            notifications = getNotificationsWithWebelements();
        }
        return new ArrayList<>( notifications.keySet() );
    }

    private boolean isNotificationDisplayed() {
        return notificationsContainer.isPresent() && currentlyContainsElements( notificationsContainer, By.cssSelector( SINGLE_NOTIFICATION_CSS_SELECTOR ) );
    }

    private LinkedHashMap<NotificationPopup, WebElementFacade> getNotificationsWithWebelements() {
        LinkedHashMap<NotificationPopup, WebElementFacade> notifications = new LinkedHashMap<>();
        if ( !isNotificationDisplayed() ) {
            return notifications;
        }
        ListOfWebElementFacades notificationElements = notificationsContainer.thenFindAll( By.cssSelector( SINGLE_NOTIFICATION_CSS_SELECTOR ) );
        for ( WebElementFacade notificationElement : notificationElements ) {
            String headerText = notificationElement.then( By.cssSelector( NOTIF_HEADER_CSS_SELECTOR ) ).getText();
            List<WebElementFacade> contextElements = notificationElement.then( By.cssSelector( NOTIF_CONTENT_WITH_BUTTONS_CSS_SELECTOR ) ).thenFindAll( By.cssSelector( "*" ) );
            // Remove empty elements because these elements would break the notification content separation
            contextElements.removeIf( element -> element.getText().isEmpty() );
            String contentMsg = null;
            if ( contextElements.size() > 0 ) {
                contentMsg = contextElements.get( 0 ).getText();
            } else {
                fail( "Content message was not found on notification popup: " + headerText );
            }
            NotificationPopup notificationPopup = new NotificationPopup( headerText, contentMsg );
            if ( contextElements.size() > 1 ) {
                notificationPopup.setContentEventDate( contextElements.get( 1 ).getText() );
            }
            if ( contextElements.size() > 2 ) {
                notificationPopup.setContentEventName( contextElements.get( 2 ).getText() );
            }
            WebElementFacade parentElement = notificationElement.find( By.xpath( "./.." ) );
            if ( parentElement.getAttribute( "class" ).equals( NOTIFICATION_STACK_CLASS ) ) {
                notificationPopup.setInStack( true );
                int indexInStack = parentElement.findElements( By.cssSelector( SINGLE_NOTIFICATION_CSS_SELECTOR ) ).indexOf( notificationElement.getElement() );
                notificationPopup.setIndexInStack( indexInStack );
            }
            notifications.put( notificationPopup, notificationElement );
        }
        return notifications;
    }

    public void clickOnButtonOnNotification( String buttonText, NotificationPopup expectedNotification ) {
        elementClicker( getButton( buttonText, expectedNotification ) );
    }

    public void clickOnLinkOnNotification( String linkText, NotificationPopup expectedNotification ) {
        elementClicker( getLink( linkText, expectedNotification ) );
    }

    public boolean getButtonVisibility( String buttonText, NotificationPopup expectedNotification ) {
        return getButton( buttonText, expectedNotification ).isCurrentlyVisible();
    }

    public void closeNotificationPopup( NotificationPopup expectedNotification ) {
        WebElementFacade notifPopupElement = getNotificationWebelement( expectedNotification );
        elementClicker( notifPopupElement.then( By.cssSelector( NOTIF_CLOSE_BUTTON_CSS_SELECTOR ) ) );
    }

    public WebElementFacade getButton( String buttonText, NotificationPopup expectedNotification ) {
        WebElementFacade notifPopupElement = getNotificationWebelement( expectedNotification );
        ListOfWebElementFacades buttons = notifPopupElement.then( By.cssSelector( NOTIF_CONTENT_WITH_BUTTONS_CSS_SELECTOR ) ).thenFindAll( By.tagName( "button" ) );
        return buttons.stream()
                      .filter( button -> button.getText().equals( buttonText ) )
                      .findFirst()
                      .orElseThrow( () -> new RuntimeException( "Button was not found: " + buttonText ) );
    }

    public WebElementFacade getLink( String linkText, NotificationPopup expectedNotification ) {
        WebElementFacade notifPopupElement = getNotificationWebelement( expectedNotification );
        ListOfWebElementFacades buttons = notifPopupElement.then( By.cssSelector( NOTIF_CONTENT_WITH_BUTTONS_CSS_SELECTOR ) ).thenFindAll( By.tagName( "a" ) );
        return buttons.stream()
                      .filter( button -> button.getText().equals( linkText ) )
                      .findFirst()
                      .orElseThrow( () -> new RuntimeException( "Link was not found: " + linkText ) );
    }

    private WebElementFacade getNotificationWebelement( NotificationPopup expectedNotification ) {
        LinkedHashMap<NotificationPopup, WebElementFacade> notifications = getNotificationsWithWebelements();
        return notifications.entrySet().stream()
                            .filter( notif -> notif.getKey().equals( expectedNotification ) )
                            .map( Map.Entry::getValue )
                            .findFirst()
                            .orElseThrow( () -> new RuntimeException( "Notification popup was not found: " + expectedNotification ) );
    }

}
