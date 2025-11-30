/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2024, 2024, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.pages.sections.modal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ge.onchron.verif.model.Table;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.pages.utils.PageElementUtils.waitForLoadingIndicatorDisappears;
import static com.ge.onchron.verif.pages.utils.ProgressiveSummaryUtils.getFullReportButton;
import static com.ge.onchron.verif.pages.utils.ProgressiveSummaryUtils.getWhatHasChangedTitleText;
import static com.ge.onchron.verif.pages.utils.ProgressiveSummaryUtils.hoverButtonInTableCell;
import static com.ge.onchron.verif.pages.utils.TableUtils.createTable;
import static com.ge.onchron.verif.utils.Utils.waitMillis;

public class WhatHasChangedModal extends BaseModal {

    private final String MODAL_BASE_SELECTOR = "[class*='ModalViewer']";

    @FindBy( css = MODAL_BASE_SELECTOR )
    private WebElementFacade whatHasChangedModal;

    @Override
    protected By getLocator() {
        return By.cssSelector( MODAL_BASE_SELECTOR );
    }

    public boolean isCurrentlyVisible() {
        return whatHasChangedModal.isCurrentlyVisible();
    }

    protected WebElementFacade getModal() {
        return whatHasChangedModal;
    }

    public String getHeader() {
        waitUntilContentLoadingIndicatorDisappears();
        WebElementFacade titleContainer = getModal().then( By.cssSelector( "[class*='ProgressiveSummarization-module_ets-title_']" ) );
        return getWhatHasChangedTitleText( titleContainer );
    }

    public void waitUntilContentLoadingIndicatorDisappears() {
        waitForLoadingIndicatorDisappears();
        waitMillis( 400 );
    }

    public Table getWhatHasChangedTable() {
        return createTable( getWhatHasChangedTableWebElement() );
    }

    private WebElement getWhatHasChangedTableWebElement() {
        return getModal().then( By.cssSelector( "[class*='ProgressiveSummarization-module_evnets-table-wrapper']" ) ).then( By.tagName( "table" ) );
    }

    public void clickFullReportButton( int rowNb, String columnName ) {
        getFullReportButton( getWhatHasChangedTableWebElement(), rowNb, columnName ).click();
    }

    public void hoverButton( String buttonName, int rowNb, String columnName ) {
        hoverButtonInTableCell( getWhatHasChangedTableWebElement(), buttonName, rowNb, columnName );
    }

    public void clickBackToEventList() {
        WebElementFacade backToEventListButtonElement = getModal()
                .then( By.cssSelector( "[class*='back-button-wrapper']" ) )
                .then( By.tagName( "button" ) );
        elementClicker( backToEventListButtonElement );
    }
}
