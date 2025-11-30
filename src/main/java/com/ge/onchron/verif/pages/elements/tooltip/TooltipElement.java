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

import static org.junit.Assert.fail;

import com.ge.onchron.verif.pages.SimplePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

public class TooltipElement extends SimplePage {

    private final String TOOLTIP_SELECTOR = "[class^='Tooltip-module_container']";

    @FindBy( css = TOOLTIP_SELECTOR )
    private WebElementFacade tooltip;

    public WebElementFacade getTooltipWebElement() {
        return tooltip;
    }

    public String getTooltipFullText() {
        return tooltip.getText();
    }

    public String getDelayedTooltipFullText() {
        try {
            tooltip.waitUntilVisible();
            return tooltip.getText();
        } catch ( Exception e ) {
            fail( "Could not find tooltip after delay" );
        }
        return null;
    }

}
