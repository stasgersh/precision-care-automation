/*
 * -GE CONFIDENTIAL-
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
package com.ge.onchron.verif.pages.sections.modal;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;

import com.ge.onchron.verif.model.detailedDataItems.LinkWithDateItem;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

public class NavigateToTimelineModal extends BaseModal {

    private final String MODAL_CSS_SELECTOR = "[class*='SummaryGoToTimeline-module_modal']";
    private final String GO_TO_TIMELINE_LINK_CSS_SELECTOR = "[class*='SummaryGoToTimeline-module_link']";
    private final String CLOSE_BUTTON_CSS_SELECTOR = "[class*='SummaryGoToTimeline-module_close']";

    @FindBy( css = MODAL_CSS_SELECTOR )
    private WebElementFacade modal;

    @FindBy( css = CLOSE_BUTTON_CSS_SELECTOR )
    private WebElementFacade closeButton;

    @Override
    protected By getLocator() {
        return By.cssSelector( MODAL_CSS_SELECTOR );
    }

    public List<LinkWithDateItem> getLinks() {
        LinkedHashMap<LinkWithDateItem, WebElementFacade> linksWithWebelements = getLinksWithWebelements();
        return new ArrayList<>( linksWithWebelements.keySet() );
    }

    private LinkedHashMap<LinkWithDateItem, WebElementFacade> getLinksWithWebelements() {
        List<WebElementFacade> linksElements = modal.thenFindAll( By.cssSelector( GO_TO_TIMELINE_LINK_CSS_SELECTOR ) );
        LinkedHashMap<LinkWithDateItem, WebElementFacade> linksWithWebelements = new LinkedHashMap<>();    // sort order need to take into account
        for ( WebElementFacade linkElement : linksElements ) {
            String[] linkTexts = linkElement.getText().split( "\n" );
            LinkWithDateItem linkWithDateItem = new LinkWithDateItem( linkTexts[0], linkTexts[1] );
            linksWithWebelements.put( linkWithDateItem, linkElement );
        }
        return linksWithWebelements;
    }

    public void clickOnLink( LinkWithDateItem linkWithDateItem ) {
        LinkedHashMap<LinkWithDateItem, WebElementFacade> linksWithWebelements = getLinksWithWebelements();
        WebElementFacade requiredLink = linksWithWebelements.entrySet().stream()
                                                            .filter( entry -> entry.getKey().equals( linkWithDateItem ) )
                                                            .map( Map.Entry::getValue )
                                                            .findFirst()
                                                            .orElseThrow( () -> new RuntimeException( "The following link was was not found on 'Show on timeline' popup: " + linkWithDateItem ) );
        elementClicker( requiredLink );
    }

    public void clickOnCloseButton() {
        elementClicker( closeButton );
    }

    public String getNotificationText() {
        return modal.then( By.cssSelector( ".u-mt--" ) ).getTextContent();
    }

}
