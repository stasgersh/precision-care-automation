/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2023, 2023, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE HealthCare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 */
package com.ge.onchron.verif.pages.sections.toolbar.timeRangeButtons;

import java.util.List;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

public class TimeRangeButtonsOnVerticalToolbar extends AbstractTimeRangeButtons {

    @FindBy( xpath = "//*[contains(@class,'VerticalToolbar-module_time-range-item')]" )
    private List<WebElementFacade> timeRangeButtons;

    @Override
    public List<WebElementFacade> timeRangeButtons() {
        return timeRangeButtons;
    }

}
