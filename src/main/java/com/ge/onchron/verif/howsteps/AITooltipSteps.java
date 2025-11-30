/*

-GE CONFIDENTIAL-
Type: Source Code


Copyright (c) 2024, 2024, GE Healthcare
All Rights Reserved


This unpublished material is proprietary to GE Healthcare. The methods and
techniques described herein are considered trade secrets and/or
confidential. Reproduction or distribution, in whole or in part, is
forbidden except by express written permission of GE Healthcare.
*/
package com.ge.onchron.verif.howsteps;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.ge.onchron.verif.model.AITooltipSection;
import com.ge.onchron.verif.pages.elements.tooltip.AITooltipElement;


public class AITooltipSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger( AITooltipSteps.class );

    private AITooltipElement aiTooltip;

    public void checkDisplayedAIDisclaimer( String AiInfoText ) {
        String actualAiText = aiTooltip.getAiInfoText();
        assertEquals( "AI Info text is incorrect", AiInfoText, actualAiText );
    }

    public void checkTruncatedBannerTooltipText( String fullText ) {
        String observedTooltipText = aiTooltip.getFullText();
        assertEquals( "Text on AI tooltip does not match expectation", fullText, observedTooltipText );
    }

    public void clickOnTitleOnAITooltipSection( AITooltipSection aiTooltipSection ) {
        aiTooltip.clickTitleOn( aiTooltipSection );
    }

    public void checkAITooltipSections( List<AITooltipSection> expectedTooltipSections ) {
        List<AITooltipSection> observedAITooltipSections = aiTooltip.getAITooltipSections();
        LOGGER.info( String.format( "Observed AI Tooltip sections: %s", observedAITooltipSections ) );
        assertEquals( "AI Tooltip sections are not ok.", expectedTooltipSections, observedAITooltipSections );
    }

}
