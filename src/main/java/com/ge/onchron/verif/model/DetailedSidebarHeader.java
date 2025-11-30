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
package com.ge.onchron.verif.model;

import java.util.List;
import java.util.Objects;

public class DetailedSidebarHeader {

    private String title;
    private String dateText;
    private String reportType;
    private List<Badge> badges;

    public DetailedSidebarHeader() {
    }

    public DetailedSidebarHeader( String title ) {
        this.title = title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getDateText() {
        return dateText;
    }

    public void setDateText( String dateText ) {
        this.dateText = dateText;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType( String reportType ) {
        this.reportType = reportType;
    }

    public List<Badge> getBadges() {
        return badges;
    }

    public void setBadges( List<Badge> badges ) {
        this.badges = badges;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        DetailedSidebarHeader that = (DetailedSidebarHeader) o;
        return Objects.equals( title, that.title ) && Objects.equals( dateText, that.dateText ) && Objects.equals( reportType, that.reportType );
    }

    @Override
    public String toString() {
        return "DetailedSidebarHeader{" +
                "title='" + title + '\'' +
                ", dateText='" + dateText + '\'' +
                ", reportType='" + reportType + '\'' +
                ", badges=" + badges +
                '}';
    }

}
