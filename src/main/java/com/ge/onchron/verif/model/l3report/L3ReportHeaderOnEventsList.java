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
package com.ge.onchron.verif.model.l3report;

public class L3ReportHeaderOnEventsList extends L3ReportHeader {

    private boolean isSelected;

    public L3ReportHeaderOnEventsList() {
    }

    public L3ReportHeaderOnEventsList( L3ReportHeader header ) {
        this.setTitle( header.getTitle() );
        this.setDateText( header.getDateText() );
        this.setAuthor( header.getAuthor() );
        this.setStatus( header.getStatus() );
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected( boolean selected ) {
        isSelected = selected;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !(o instanceof L3ReportHeaderOnEventsList that) ) return false;
        if ( !super.equals( o ) ) return false;
        return isSelected() == that.isSelected();
    }

    @Override
    public String toString() {
        return "EventDataOnL3EventsList{" +
                "title='" + getTitle() + '\'' +
                ", dateText='" + getDateText() + '\'' +
                ", author='" + getAuthor() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", isSelected='" + isSelected() + '\'' +
                '}';
    }

}
