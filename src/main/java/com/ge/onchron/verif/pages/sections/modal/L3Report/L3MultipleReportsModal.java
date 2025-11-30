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
package com.ge.onchron.verif.pages.sections.modal.L3Report;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;

import com.ge.onchron.verif.model.l3report.L3ReportHeader;
import com.ge.onchron.verif.model.l3report.L3ReportHeaderOnEventsList;
import com.ge.onchron.verif.pages.sections.modal.BaseModal;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

public class L3MultipleReportsModal extends BaseModal {

    private final String MODAL_BASE_XPATH = "//*[contains(@class,'ModalViewer')]";

    @FindBy( css = "[class*='modal-header']" )
    private WebElementFacade modalHeader;

    @FindBy( css = "[class*='SummaryModalViewer-module_event-list']:not([class*='container'])" )
    private WebElementFacade eventsList;

    @FindBy( css = "[class*='SummaryModalViewer-module_content']" )
    private WebElementFacade reportsSection;

    @Override
    protected By getLocator() {
        return By.xpath( MODAL_BASE_XPATH );
    }

    private WebElementFacade getSelectedReportSectionContainer() {
        return reportsSection.thenFindAll( By.xpath( "child::*" ) ).get( 2 );
    }

    public L3Report getSelectedReportSection() {
        return new L3Report( getSelectedReportSectionContainer() );
    }

    public L3ReportHeader getSelectedReportHeader() {
        return getSelectedReportSection().getHeader();

    }

    public String getSelectedReportBody() {
        return getSelectedReportSection().getBody();
    }

    public List<L3ReportHeaderOnEventsList> getEventsList() {
        LinkedHashMap<L3ReportHeaderOnEventsList, WebElementFacade> eventsListWithWebElements = getEventsListWithWebElements();
        return new ArrayList<>( eventsListWithWebElements.keySet() );
    }

    public void clickEventOnEventList( L3ReportHeaderOnEventsList expectedEvent ) {
        LinkedHashMap<L3ReportHeaderOnEventsList, WebElementFacade> eventsListWithWebElements = getEventsListWithWebElements();
        WebElementFacade foundEvent = eventsListWithWebElements.entrySet().stream()
                                                               .filter( event -> event.getKey().equals( expectedEvent ) )
                                                               .map( Map.Entry::getValue )
                                                               .findFirst()
                                                               .orElseThrow( () -> new RuntimeException(
                                                                       String.format( "Event was not found on event list: %s", expectedEvent ) ) );
        elementClicker( foundEvent );
    }

    private LinkedHashMap<L3ReportHeaderOnEventsList, WebElementFacade> getEventsListWithWebElements() {
        List<WebElementFacade> eventsWebElements = eventsList.thenFindAll( By.xpath( "child::*" ) );
        LinkedHashMap<L3ReportHeaderOnEventsList, WebElementFacade> eventsDataWithWebElements = new LinkedHashMap<>();
        for ( WebElementFacade eventWebElement : eventsWebElements ) {
            L3ReportHeaderOnEventsList eventData = new L3ReportHeaderOnEventsList();
            boolean isSelected = eventWebElement.getAttribute( "class" ).contains( "selected" );
            eventData.setSelected( isSelected );
            List<WebElementFacade> eventWebElementParts = eventWebElement.thenFindAll( By.xpath( "child::*" ) );
            eventData.setTitle( eventWebElementParts.getFirst().getText() );
            List<WebElementFacade> eventMetadata = eventWebElementParts.get( 1 ).thenFindAll( By.xpath( "child::*" ) );
            eventData.setDateText( eventMetadata.get( 0 ).getText() );
            eventData.setAuthor( eventMetadata.get( 1 ).getText() );
            eventData.setStatus( eventMetadata.get( 2 ).getText() );
            eventsDataWithWebElements.put( eventData, eventWebElement );
        }
        return eventsDataWithWebElements;
    }

}
