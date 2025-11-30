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
package com.ge.onchron.verif.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClusterPillEvent {

    private String name;
    private String dateOnTooltip;
    private List<Badge> badges = new ArrayList<>();

    public ClusterPillEvent( String name ) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getDateOnTooltip() {
        return dateOnTooltip;
    }

    public void setDateOnTooltip( String dateOnTooltip ) {
        this.dateOnTooltip = dateOnTooltip;
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
        if ( !(o instanceof ClusterPillEvent) ) return false;
        ClusterPillEvent that = (ClusterPillEvent) o;
        return Objects.equals( getName(), that.getName() ) && Objects.equals( getDateOnTooltip(), that.getDateOnTooltip() );
    }

    @Override
    public String toString() {
        return "ClusterPillEvent{" +
                "name='" + name + '\'' +
                ", dateOnTooltip='" + dateOnTooltip + '\'' +
                ", badges=" + badges +
                '}';
    }

}
