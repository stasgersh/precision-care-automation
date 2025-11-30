/*
 * -GEHC CONFIDENTIAL-
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
package com.ge.onchron.verif;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

import com.ge.onchron.verif.pages.elements.tooltip.TooltipElement;

public class TooltipSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger( TooltipSteps.class );
    private TooltipElement tooltip;

    public void checkTooltipText( String expectedTooltipText ) {
        String observedTooltipText = tooltip.getTooltipFullText();
        LOGGER.info( String.format( "Tooltip text: %s", observedTooltipText ) );
        assertEquals( "Tooltip text is not ok.", expectedTooltipText, observedTooltipText );
    }

}
