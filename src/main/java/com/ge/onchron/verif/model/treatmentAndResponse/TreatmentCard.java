/*
 * -GEHC CONFIDENTIAL-
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
package com.ge.onchron.verif.model.treatmentAndResponse;


import java.util.Objects;

import net.serenitybdd.core.pages.WebElementFacade;

public class TreatmentCard {

    private String cardTitle;
    private String eventName;
    private String date;
    private String emptyText;
    private boolean isEmpty;
    private WebElementFacade cardElement;


    public String getCardTitle() {
        return cardTitle;
    }

    public void setCardTitle( String cardTitle ) {
        this.cardTitle = cardTitle;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName( String eventName ) {
        this.eventName = eventName;
    }

    public String getDate() {
        return date;
    }

    public void setDate( String date ) {
        this.date = date;
    }

    public String getEmptyText() {
        return emptyText;
    }

    public void setEmptyText( String emptyText ) {
        this.emptyText = emptyText;
        isEmpty = true;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public WebElementFacade getCardElement() {
        return cardElement;
    }

    public void setCardElement( WebElementFacade cardElement ) {
        this.cardElement = cardElement;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !(o instanceof TreatmentCard) ) return false;
        TreatmentCard that = (TreatmentCard) o;
        return isEmpty == that.isEmpty &&
                Objects.equals( cardTitle, that.cardTitle ) &&
                Objects.equals( eventName, that.eventName ) &&
                Objects.equals( date, that.date ) &&
                Objects.equals( emptyText, that.emptyText );
    }

    @Override
    public String toString() {
        return "TreatmentCard{" +
                "cardTitle='" + cardTitle + '\'' +
                ", eventName='" + eventName + '\'' +
                ", date='" + date + '\'' +
                ", emptyText=" + emptyText +
                '}';
    }
}
