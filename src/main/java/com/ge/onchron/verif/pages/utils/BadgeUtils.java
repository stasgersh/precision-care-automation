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
package com.ge.onchron.verif.pages.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;

import com.ge.onchron.verif.model.Badge;
import com.ge.onchron.verif.model.clinicalConfiguration.OverallMatchingScore;
import com.ge.onchron.verif.model.enums.AdherenceStatusBadgeType;
import com.ge.onchron.verif.model.enums.BadgeType;
import java.util.ArrayList;
import java.util.List;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.pages.utils.PageUtils.currentlyContainsWebElements;

public class BadgeUtils {

    public static final String CLP_ICON_NAME = "ico-ai";
    public static final String IMPORTANT_ICON_NAME = "Star";
    public static final String COMMENT_ICON_NAME = "Message";
    public static final String DOSE_CHANGE_ICON_NAME = "Minus_Plus";
    public static final String SYNC_PROGRESS_ICON = "sync_";

    public static final String INTRAVENOUS_FLUIDS_ICON_NAME = "ico-intravenousfluids";
    public static final String CYCLED_EVENT_COLOR = "rgb(197, 199, 201)";

    public static List<Badge> getEventBadgesFromWebElement( WebElementFacade eventElement ) {
        List<WebElementFacade> badgeElements = eventElement.thenFindAll( By.className( "badge" ) );
        List<Badge> badges = new ArrayList<>();
        for ( WebElementFacade actualBadgeElement : badgeElements ) {
            Badge actualBadge = new Badge();

            // 1. Set badge type
            BadgeType badgeType = getBadgeType( actualBadgeElement );
            actualBadge.setBadgeType( badgeType );

            // 2. Set badge text
            actualBadge.setText( actualBadgeElement.getText() );

            // 3. Set badge color
            String color = getColor( actualBadgeElement );
            actualBadge.setColor( color );

            badges.add( actualBadge );
        }
        return badges;
    }

    private static String getColor( WebElement element ) {
        Color color = Color.fromString( element.getCssValue( "background-color" ) );
        return color.asRgb();
    }

    public static BadgeType getBadgeType( WebElement badgeElement ) {
        BadgeType badgeType;
        AdherenceStatusBadgeType adherenceStatusBadgeType;
        String elementId = getBadgeElementId( badgeElement );
        String badgeText = badgeElement.getText();

        // Note: in case of miniaturized badges, badge color is required to be able to identify cycled event badge type
        if ( elementId != null && elementId.contains( INTRAVENOUS_FLUIDS_ICON_NAME ) && getColor( badgeElement ).equals( CYCLED_EVENT_COLOR ) ) {
            badgeType = BadgeType.CYCLED_EVENT; // Cycled event badge
        } else {
            String badgeId = elementId != null ? elementId : "";
            if ( badgeId.contains( CLP_ICON_NAME ) ) { // CLP badge
                badgeType = BadgeType.CLP;
            } else if ( badgeId.contains( IMPORTANT_ICON_NAME ) ) { // Important event badge
                badgeType = BadgeType.IMPORTANT_EVENT;
            } else if ( badgeId.contains( COMMENT_ICON_NAME ) ) { // Comment badge
                badgeType = BadgeType.COMMENT;
            } else if ( badgeId.contains( DOSE_CHANGE_ICON_NAME ) ) { // Chemo dose changed badge
                badgeType = BadgeType.DOSE_CHANGED;
            } else if ( badgeId.contains( SYNC_PROGRESS_ICON ) ) { // Criteria sync status badge in Trials tab view
                badgeType = BadgeType.SYNC_PROGRESS_BADGE;
            } else if ( badgeTextMatchesAdherenceStatus( badgeText ) ) { // Adherence CriteriaStatus Badge
                badgeType = BadgeType.STATUS_BADGE;
            } else if ( badgeTextMatchesClinicalTrialStatus( badgeText ) ) { // Trial Matching CriteriaStatus Badge
                badgeType = BadgeType.TRIAL_MATCHING_STATUS_BADGE;
            } else if ( elementId == null && badgeElement.getAttribute( "class" ).contains( "label" ) ) {
                badgeType = BadgeType.LABEL; // Label badge
            } else {
                badgeType = BadgeType.CLASSIFICATION; // Some kind of classification badge
            }
        }
        return badgeType;
    }

    private static String getBadgeElementId( WebElement badgeElement ) {
        if ( currentlyContainsWebElements( badgeElement, By.tagName( "svg" ) ) ) {
            WebElement svgElement = badgeElement.findElement( By.tagName( "svg" ) );
            if ( currentlyContainsWebElements( svgElement, By.tagName( "g" ) ) ) {
                return svgElement.findElement( By.tagName( "g" ) ).getAttribute( "id" );
            } else {
                return svgElement.getAttribute( "id" );
            }
        }
        return null;
    }

    private static boolean badgeTextMatchesAdherenceStatus( String badgeText ) {
        try {
            AdherenceStatusBadgeType.valueOf( badgeText.toUpperCase() );
            return true;
        } catch ( IllegalArgumentException e ) {
            return false;
        }
    }

    private static boolean badgeTextMatchesClinicalTrialStatus( String badgeText ) {
        return OverallMatchingScore.fromString( badgeText ) != null;
    }

}
