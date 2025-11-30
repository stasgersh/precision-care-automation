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
package com.ge.onchron.verif.pages.sections.modal.L3Report;

import org.openqa.selenium.By;

import com.ge.onchron.verif.model.l3report.L3ReportHeader;
import com.ge.onchron.verif.pages.sections.modal.BaseModal;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.pages.utils.PageElementUtils.waitForLoadingIndicatorDisappears;
import static com.ge.onchron.verif.utils.Utils.waitMillis;

public class L3SingleReportModal extends BaseModal {

    private final String MODAL_BASE_XPATH = "//*[contains(@class,'ModalViewer')]";

    @FindBy( xpath = MODAL_BASE_XPATH )
    private WebElementFacade l3SingleReportModal;

    @Override
    protected By getLocator() {
        return By.xpath( MODAL_BASE_XPATH );
    }

    public boolean isCurrentlyVisible() {
        return l3SingleReportModal.isCurrentlyVisible();
    }

    protected WebElementFacade getModal() {
        return l3SingleReportModal;
    }

    private WebElementFacade getIframe() {
        return l3SingleReportModal.then( By.tagName( "iframe" ) );
    }

    public L3Report getL3Report() {
        return new L3Report( l3SingleReportModal.waitUntilVisible() );
    }

    public L3ReportHeader getHeader() {
        return getL3Report().getHeader();
    }

    public String getBody() {
        return getL3Report().getBody();
    }

    public void waitUntilContentLoadingIndicatorDisappears() {
        waitForLoadingIndicatorDisappears();
        waitMillis( 400 );
    }

}
