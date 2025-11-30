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

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.ge.onchron.verif.model.l3report.L3ReportHeader;
import com.ge.onchron.verif.pages.SimplePage;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.pages.utils.PageElementUtils.waitForLoadingIndicatorDisappears;
import static com.ge.onchron.verif.utils.Utils.waitMillis;

public class L3Report extends SimplePage {

    private final String L3_REPORT_SELECTOR = "[class*='EmbeddedReportViewer-module_content']";
    private final String HEADER_TITLE_CLASS = "EmbeddedReportViewer-module_event-title";
    private final String HEADER_XPATH_SELECTOR = String.format( "//*[contains(@class, '%s')]/..", HEADER_TITLE_CLASS );
    private final String HEADER_TITLE_SELECTOR = String.format( "[class*='%s']", HEADER_TITLE_CLASS );
    private final String HEADER_SUBTITLE_SELECTOR = "[class*='subtitle-list']";

    private WebElement l3Report;

    public L3Report( WebElementFacade section ) {
        this.setDriver( getDriver() );  // waitForCondition() is not working without this, because this.driver is null
        waitForCondition().until( ExpectedConditions.presenceOfNestedElementLocatedBy( section, By.cssSelector( L3_REPORT_SELECTOR ) ) );
        l3Report = section.then( By.cssSelector( L3_REPORT_SELECTOR ) );
    }

    public boolean isCurrentlyVisible() {
        return l3Report.isDisplayed();
    }

    protected WebElement getModal() {
        return l3Report;
    }

    private WebElement getIframe() {
        return l3Report.findElement( By.tagName( "iframe" ) );
    }

    public L3ReportHeader getHeader() {
        waitUntilContentLoadingIndicatorDisappears();
        WebElement modalHeader = getModal().findElement( By.xpath( HEADER_XPATH_SELECTOR ) );
        L3ReportHeader header = new L3ReportHeader();
        String title = modalHeader.findElement( By.cssSelector( HEADER_TITLE_SELECTOR ) )
                                  .getText();
        header.setTitle( title );
        WebElement subtitleList = modalHeader.findElement( By.cssSelector( HEADER_SUBTITLE_SELECTOR ) );
        List<WebElement> subtitleElements = subtitleList.findElements( By.xpath( "child::*" ) );
        header.setDateText( subtitleElements.get( 0 ).getText() );
        header.setAuthor( subtitleElements.get( 1 ).getText() );
        header.setStatus( subtitleElements.get( 2 ).getText() );
        return header;
    }

    public String getBody() {
        getDriver().switchTo().frame( getIframe() );
        String body = getDriver().findElement( By.tagName( "html" ) ).findElement( By.tagName( "body" ) ).getAttribute( "innerHTML" );
        getDriver().switchTo().defaultContent();
        return body.trim();
    }

    public void waitUntilContentLoadingIndicatorDisappears() {
        waitForLoadingIndicatorDisappears();
        waitMillis( 400 );
    }

}
