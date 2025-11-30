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

import java.util.Objects;

public class L3ReportHeader {

    private String title;
    private String dateText;
    private String author;
    private String status;

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public String getDateText() {
        return dateText;
    }

    public void setDateText( String dateText ) {
        this.dateText = dateText;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor( String author ) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus( String status ) {
        this.status = status;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !(o instanceof L3ReportHeader l3Header) ) return false;
        return Objects.equals( getTitle(), l3Header.getTitle() )
                && Objects.equals( getDateText(), l3Header.getDateText() )
                && Objects.equals( getAuthor(), l3Header.getAuthor() )
                && Objects.equals( getStatus(), l3Header.getStatus() );
    }

    @Override
    public String toString() {
        return "L3SingleReportHeader{" +
                "title='" + title + '\'' +
                ", dateText='" + dateText + '\'' +
                ", author='" + author + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

}
