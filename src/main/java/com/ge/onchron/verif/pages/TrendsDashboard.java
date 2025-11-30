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
package com.ge.onchron.verif.pages;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

public class TrendsDashboard extends PatientPage {

    private final String TRENDS_TOOLBAR_XPATH = "//*[contains(@class,'TrendToolbar-module_content_')]";
    private final String TRENDS_DASHBOARD_XPATH = "//*[contains(@class,'TrendDashboard-module_')]";
    private final String TIMELINE_BUTTON_XPATH = ".//button[text()='Timeline']";
    private final String TREND_GRAPH_TITLE_CSS = "[class^='LineChart-module_title_']";
    private final String TREND_DASHBOARD_MODULE_CSS = "[class^='TrendDashboard-module_list']";

    @FindBy( xpath = TRENDS_TOOLBAR_XPATH )
    private WebElementFacade trendsToolbar;

    @FindBy( xpath = TRENDS_DASHBOARD_XPATH )
    private WebElementFacade trendDashBoard;


    public boolean isVisible() {
        return trendsToolbar.waitUntilVisible().isVisible() && trendDashBoard.waitUntilVisible().isVisible();
    }

    public void clickOnTimelineButton() {
        elementClicker( trendsToolbar.then( By.xpath( TIMELINE_BUTTON_XPATH ) ) );
    }

    public List<String> getTrendGraphOrder() {
        return getDriver().findElements( By.cssSelector( TREND_GRAPH_TITLE_CSS ) )
                          .stream()
                          .map( WebElement::getText ).collect( Collectors.toList() );
    }
}
