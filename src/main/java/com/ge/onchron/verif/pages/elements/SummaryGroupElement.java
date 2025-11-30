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

import com.ge.onchron.verif.model.summaryCard.SummaryGroup;
import com.ge.onchron.verif.pages.SimplePage;
import net.serenitybdd.core.pages.WebElementFacade;

public class SummaryGroupElement extends SimplePage {

    private SummaryGroup summaryGroup;
    private WebElementFacade groupElement;

    public SummaryGroupElement( SummaryGroup summaryGroup, WebElementFacade groupElement ) {
        this.summaryGroup = summaryGroup;
        this.groupElement = groupElement;
    }

    public WebElementFacade getGroupElement() {
        return groupElement;
    }

    public void setGroupElement( WebElementFacade groupElement ) {
        this.groupElement = groupElement;
    }

    public SummaryGroup getSummaryGroup() {
        return summaryGroup;
    }

    public void setSummaryGroup( SummaryGroup summaryGroup ) {
        this.summaryGroup = summaryGroup;
    }

}
