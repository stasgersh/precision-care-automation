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

import com.ge.onchron.verif.pages.SimplePage;
import net.serenitybdd.core.pages.WebElementFacade;

public abstract class AbstractTimeRangeButtons extends SimplePage {

    public enum TIME_RANGES {
        LAST_30_DAYS( 0 ),
        LAST_90_DAYS( 1 ),
        FULL_TIME_RANGE( 2 );

        private int range;

        TIME_RANGES( int range ) {
            this.range = range;
        }

        public int getRange() {
            return range;
        }
    }

    public abstract List<WebElementFacade> timeRangeButtons();

    public void setLast30days() {
        elementClicker( timeRangeButtons().get( TIME_RANGES.LAST_30_DAYS.getRange() ) );
    }

    public void setLast90days() {
        elementClicker( timeRangeButtons().get( TIME_RANGES.LAST_90_DAYS.getRange() ) );
    }

    public void setFullTimeRange() {
        elementClicker( timeRangeButtons().get( TIME_RANGES.FULL_TIME_RANGE.getRange() ) );
    }

}
