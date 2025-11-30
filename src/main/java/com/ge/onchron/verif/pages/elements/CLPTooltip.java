/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2024, 2024, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */

package com.ge.onchron.verif.pages.elements;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;

import com.ge.onchron.verif.pages.SimplePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

public class CLPTooltip extends SimplePage {

    private final String CLP_TOOLTIP_HYPERLINK = "[class^='ClpTooltipContent-module_link']";
    private final String CLP_TOOLTIP_AI_TEXT = "[class^='ClpTooltipContent-module_label']";
    private final String CLP_TOOLTIP_FULL_TEXT = "[class*='ClpTooltipContent-module_text']";
    private static final Pattern CONFIDENCE_SCORE = Pattern.compile( ".*?(\\d+(.\\d+)?)%$" );

    @FindBy( css = CLP_TOOLTIP_HYPERLINK )
    private List<WebElementFacade> showSourceLink;

    @FindBy( css = CLP_TOOLTIP_AI_TEXT )
    private List<WebElementFacade> tooltipAiText;

    // Only available when the CLP text is truncated
    @FindBy( css = CLP_TOOLTIP_FULL_TEXT )
    private List<WebElementFacade> tooltipFullText;

    public double getConfidenceScore() {
        String confidenceScoreText = tooltipAiText.get( 1 ).getText();
        Matcher matcher = CONFIDENCE_SCORE.matcher( confidenceScoreText );
        if ( matcher.find() ) {
            try {
                return Double.parseDouble( matcher.group( 1 ) );
            } catch ( NumberFormatException e ) {
                Assert.fail( STR."Could not parse confidence score from string: \{confidenceScoreText}" );
            }
        }
        return 0;
    }

    public String getAiInfoText() {
        return tooltipAiText.getLast().getText();
    }

    public String getFullText() {
        return tooltipFullText.getLast().getText();
    }

    public void clickOnHyperlink() {
        showSourceLink.getLast().click();
    }
}
