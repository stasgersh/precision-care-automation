/*
 * -GEHC CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2023, 2023, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.pages.sections;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import com.ge.onchron.verif.model.Badge;
import com.ge.onchron.verif.model.ClusterPillEvent;
import com.ge.onchron.verif.pages.SimplePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.pages.utils.BadgeUtils.getEventBadgesFromWebElement;
import static com.ge.onchron.verif.pages.utils.PageElementUtils.moveToElement;
import static com.ge.onchron.verif.pages.utils.PageElementUtils.moveToElementJS;
import static com.ge.onchron.verif.pages.utils.PageElementUtils.unhoverElementJS;

public class ClusterPill extends SimplePage {

    private final String CLUSTER_PILL_SELECTOR = "[class*='ClusterPill-module_pill']";
    private final String EVENT_LIST_IN_CLUSTER_PILL_SELECTOR = "[class*='ClusterPill-module_cluster-list']";
    private final String EVENT_IN_CLUSTER_PILL_SELECTOR = "[class*='Tooltip-module_container']";

    private final String EVENT_NAME_IN_CLUSTER_PILL_SELECTOR = "[class*='Tooltip-module_title']";
    private final String EVENT_DATE_IN_CLUSTER_PILL_SELECTOR = "[class*='Tooltip-module_date']";


    @FindBy( css = CLUSTER_PILL_SELECTOR )
    private WebElementFacade clusterPill;

    @FindBy( css = EVENT_LIST_IN_CLUSTER_PILL_SELECTOR )
    private WebElementFacade eventList;

    public boolean isVisible() {
        return clusterPill.isCurrentlyVisible();
    }

    public String getClusterPillText() {
        List<WebElementFacade> clusterPillElements = clusterPill.thenFindAll( By.xpath( "child::*" ) );
        // Note: clusterPillElements[0]: cluster pill, clusterPillElements[1]: cluster list which contains the events
        return clusterPillElements.get( 0 ).getText();
    }

    public List<ClusterPillEvent> getEvents( boolean withDate, boolean withBadges ) {
        List<WebElementFacade> eventElements = eventList.thenFindAll( By.cssSelector( EVENT_IN_CLUSTER_PILL_SELECTOR ) );
        List<ClusterPillEvent> events = new ArrayList<>();
        for ( WebElementFacade actualEventElement : eventElements ) {
            String eventName = getEventName( actualEventElement );
            ClusterPillEvent event = new ClusterPillEvent( eventName );
            if ( withDate ) {
                String eventDate = getEventDate( actualEventElement );
                event.setDateOnTooltip( eventDate );
            }
            if ( withBadges ) {
                List<Badge> badges = getEventBadgesFromWebElement( actualEventElement );
                event.setBadges( badges );
            }
            events.add( event );
        }
        return events;
    }

    public void clickOnEventOnClusterPill( ClusterPillEvent requiredEvent ) {
        WebElementFacade foundEvent = getEventElementFromClusterPill( requiredEvent );
        elementClicker( foundEvent );
    }

    public void hoverClusterPill() {
        moveToElement( clusterPill );
    }

    public void clickOnClusterPill() {
        elementClicker( clusterPill );
    }

    private String getEventName( WebElementFacade eventElement ) {
        return eventElement.then( By.cssSelector( EVENT_NAME_IN_CLUSTER_PILL_SELECTOR ) ).getText();
    }

    private String getEventDate( WebElementFacade eventElement ) {
        moveToElement( eventElement );
        WebElementFacade dateElement = eventElement.then( By.cssSelector( EVENT_DATE_IN_CLUSTER_PILL_SELECTOR ) );
        return dateElement.getText();
    }

    public void hoverEventInClusterPill( ClusterPillEvent requiredEvent ) {
        WebElementFacade foundEvent = getEventElementFromClusterPill( requiredEvent );
        moveToElementJS( foundEvent );
    }

    public void unhoverEventInClusterPill( ClusterPillEvent requiredEvent ) {
        WebElementFacade foundEvent = getEventElementFromClusterPill( requiredEvent );
        unhoverElementJS( foundEvent );
    }

    private WebElementFacade getEventElementFromClusterPill( ClusterPillEvent requiredEvent ) {
        List<WebElementFacade> eventElements = eventList.thenFindAll( By.cssSelector( EVENT_IN_CLUSTER_PILL_SELECTOR ) );
        return eventElements.stream().filter( eventElement -> getEventName( eventElement ).equals( requiredEvent.getName() ) )
                            .findFirst()
                            .orElseThrow( () -> new RuntimeException( "Following event was not found on cluster pill: " + requiredEvent ) );
    }

}
