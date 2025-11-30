/*
 * -GEHC CONFIDENTIAL-
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
package com.ge.onchron.verif.pages.elements;

import com.ge.onchron.verif.model.summaryCard.SummaryCardItem;
import com.ge.onchron.verif.pages.SimplePage;
import net.serenitybdd.core.pages.WebElementFacade;

public class SummaryCardItemElement extends SimplePage {

    private SummaryCardItem summaryCardItem;
    private WebElementFacade webElement;

    public SummaryCardItem getSummaryCardItem() {
        return summaryCardItem;
    }

    public void setSummaryCardItem( SummaryCardItem summaryCardItem ) {
        this.summaryCardItem = summaryCardItem;
    }

    public WebElementFacade getWebElement() {
        return webElement;
    }

    public void setWebElement( WebElementFacade webElement ) {
        this.webElement = webElement;
    }

}
