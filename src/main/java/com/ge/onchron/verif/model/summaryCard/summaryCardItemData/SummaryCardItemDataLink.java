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
package com.ge.onchron.verif.model.summaryCard.summaryCardItemData;

import java.util.Objects;

public class SummaryCardItemDataLink extends SummaryCardItemData {

    private String linkString;
    private String dateString;

    @Override
    public Object getData() {
        return linkString + " " + dateString;
    }

    public String getLinkString() {
        return linkString;
    }

    public void setLinkString( String linkString ) {
        this.linkString = linkString;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString( String dateString ) {
        this.dateString = dateString;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !(o instanceof SummaryCardItemDataLink) ) return false;
        SummaryCardItemDataLink that = (SummaryCardItemDataLink) o;
        return Objects.equals( getLinkString(), that.getLinkString() ) && Objects.equals( getDateString(), that.getDateString() );
    }

    @Override
    public String toString() {
        return "SummaryCardItemDataLink{" +
                "linkString='" + linkString + '\'' +
                ", dateString='" + dateString + '\'' +
                '}';
    }

}
