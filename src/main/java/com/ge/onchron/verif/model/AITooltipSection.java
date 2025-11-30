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
package com.ge.onchron.verif.model;

import java.util.Objects;

public class AITooltipSection {

    private String title;
    private String date;
    private String author;
    private String status;

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate( String date ) {
        this.date = date;
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
        if ( !(o instanceof AITooltipSection aiTooltip) ) return false;
        return Objects.equals( getTitle(), aiTooltip.getTitle() ) && Objects.equals( getDate(), aiTooltip.getDate() ) &&
                Objects.equals( getAuthor(), aiTooltip.getAuthor() ) && Objects.equals( getStatus(), aiTooltip.getStatus() );
    }

    @Override
    public String toString() {
        return "AITooltipSection{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", author='" + author + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

}
