/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2022, 2022, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.pages.elements;

import org.openqa.selenium.By;

import net.serenitybdd.core.pages.WebElementFacade;

public class Swimlane {

    private String name;
    private WebElementFacade header;
    private WebElementFacade eventPointsContainer;
    private WebElementFacade eventLabelsContainer;

    private static final String HEADER_TITLE_XPATH = ".//*[contains(@class,'SwimLanes-module_swimlane-header')]";

    public Swimlane( WebElementFacade header, WebElementFacade eventPointsContainer, WebElementFacade eventLabelsContainer ) {
        this.name = header.then( By.xpath( HEADER_TITLE_XPATH ) ).getText();
        this.header = header;
        this.eventPointsContainer = eventPointsContainer;
        this.eventLabelsContainer = eventLabelsContainer;
    }

    public String getName() {
        return name;
    }

    public WebElementFacade getHeader() {
        return header;
    }

    public WebElementFacade getEventPointsContainer() {
        return eventPointsContainer;
    }

    public WebElementFacade getEventLabelsContainer() {
        return eventLabelsContainer;
    }

}
