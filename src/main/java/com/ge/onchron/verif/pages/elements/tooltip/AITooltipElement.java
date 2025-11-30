/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2024, 2024, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE HealthCare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 */

package com.ge.onchron.verif.pages.elements.tooltip;

import org.openqa.selenium.By;

import static org.junit.Assert.assertEquals;

import com.ge.onchron.verif.model.AITooltipSection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.pages.utils.PageUtils.currentlyContainsWebElements;

public class AITooltipElement extends TooltipElement {

    private static final String TOOLTIP_SECTION_SELECTOR = "//*[contains(@class, 'ClpTooltipContent-module_attachment-item-report_') or contains(@class, 'ClpTooltipContent-module_source-item-report_')][not(contains(@class, 'meta'))]";
    private static final String TOOLTIP_SECTION_TITLE_SELECTOR = "[class*='LinkCta-module_container']";
    private static final String TOOLTIP_SECTION_TITLE_CLASS = "LinkCta-module_container";
    private static final String TOOLTIP_SECTION_ATTACHMENT_TITLE_SELECTOR = "[class*='AttachmentLink-module_container_']";
    private static final String TOOLTIP_SECTION_ATTACHMENT_TITLE_CLASS = "AttachmentLink-module_container_";
    private static final String TOOLTIP_METADATA_CLASS = "ClpTooltipContent-module_source-item-report-meta";
    private static final String TOOLTIP_SECTION_DATE_SELECTOR = "[class*='date']";
    private static final String TOOLTIP_SECTION_AUTHOR_SELECTOR = "[class*='author']";
    private static final String TOOLTIP_SECTION_STATUS_SELECTOR = "[class*='status']";
    private static final String TOOLTIP_AI_DISCLAIMER_SELECTOR = "[class*='ClpTooltipContent-module_disclaimer']";
    private static final String TOOLTIP_FULL_TEXT_SELECTOR = "[class*='ClpTooltipContent-module_gray']";

    public List<AITooltipSection> getAITooltipSections() {
        LinkedHashMap<AITooltipSection, WebElementFacade> aiTooltipSectionsWithWebElements = getAITooltipSectionsWithWebElements();
        return new ArrayList<>( aiTooltipSectionsWithWebElements.keySet() );
    }

    public void clickTitleOn( AITooltipSection expectedAITooltipSection ) {
        LinkedHashMap<AITooltipSection, WebElementFacade> aiTooltipSectionsWithWebElements = getAITooltipSectionsWithWebElements();
        WebElementFacade requiredAITooltipSection = aiTooltipSectionsWithWebElements.entrySet().stream()
                                                                                    .filter( aiTooltipSections -> aiTooltipSections.getKey().equals( expectedAITooltipSection ) )
                                                                                    .map( Map.Entry::getValue )
                                                                                    .findFirst()
                                                                                    .orElseThrow( () -> new RuntimeException(
                                                                                            String.format( "AI tooltip section was not found: %s", expectedAITooltipSection ) ) );
        WebElementFacade title = requiredAITooltipSection.then( By.cssSelector( TOOLTIP_SECTION_TITLE_SELECTOR ) );
        elementClicker( title );
    }

    private LinkedHashMap<AITooltipSection, WebElementFacade> getAITooltipSectionsWithWebElements() {
        LinkedHashMap<AITooltipSection, WebElementFacade> aiTooltipSections = new LinkedHashMap<>();
        List<WebElementFacade> tooltipSectionWebElements = getTooltipWebElement().thenFindAll( By.xpath( TOOLTIP_SECTION_SELECTOR ) );
        for ( WebElementFacade tooltipSectionWebElement : tooltipSectionWebElements ) {
            List<WebElementFacade> tooltipSectionParts = tooltipSectionWebElement.thenFindAll( By.xpath( "child::*" ) );
            AITooltipSection AITooltipSection = new AITooltipSection();
            for ( WebElementFacade tooltipSectionPart : tooltipSectionParts ) {
                String className = tooltipSectionPart.getAttribute( "class" );
                if ( currentlyContainsWebElements( tooltipSectionPart, By.cssSelector( TOOLTIP_SECTION_TITLE_SELECTOR ) ) ||
                        className.contains( TOOLTIP_SECTION_ATTACHMENT_TITLE_CLASS ) ) {
                    AITooltipSection.setTitle( tooltipSectionPart.getText() );
                } else if ( tooltipSectionPart.getAttribute( "class" ).contains( TOOLTIP_METADATA_CLASS ) ) {
                    List<WebElementFacade> tooltipSectionPartChildren = tooltipSectionPart.thenFindAll( By.cssSelector( "*" ) );
                    assertEquals( String.format( "3 child elements are required but found: %s", tooltipSectionPartChildren.size() ),
                            3, tooltipSectionPartChildren.size() );
                    AITooltipSection.setDate( tooltipSectionPartChildren.get( 0 ).getText().trim() );
                    AITooltipSection.setAuthor( tooltipSectionPartChildren.get( 1 ).getText().trim() );
                    AITooltipSection.setStatus( tooltipSectionPartChildren.get( 2 ).getText().trim() );
                }
            }
            aiTooltipSections.put( AITooltipSection, tooltipSectionWebElement );
        }
        return aiTooltipSections;
    }

    public String getAiInfoText() {
        return find( By.cssSelector( TOOLTIP_AI_DISCLAIMER_SELECTOR ) ).getText();
    }

    public String getFullText() {
        return find( By.cssSelector( TOOLTIP_FULL_TEXT_SELECTOR ) ).getText();
    }

}
