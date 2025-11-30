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
package com.ge.onchron.verif.model.summaryCard;

import java.util.Objects;

public class SummaryGroup {

    private String name;
    private int columnIndex;
    private int groupIndexInColumn;
    private String badgeText;
    private boolean isHidden;

    public SummaryGroup( String name, int columnIndex, int groupIndexInColumn ) {
        this.name = name;
        this.columnIndex = columnIndex;
        this.groupIndexInColumn = groupIndexInColumn;
    }

    public SummaryGroup( String name, int columnIndex, int groupIndexInColumn, String badgeText ) {
        this.name = name;
        this.columnIndex = columnIndex;
        this.groupIndexInColumn = groupIndexInColumn;
        this.badgeText = badgeText;
    }

    public SummaryGroup( String name, int columnIndex, int groupIndexInColumn, String badgeText, boolean isHidden ) {
        this.name = name;
        this.columnIndex = columnIndex;
        this.groupIndexInColumn = groupIndexInColumn;
        this.badgeText = badgeText;
        this.isHidden = isHidden;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex( int columnIndex ) {
        this.columnIndex = columnIndex;
    }

    public int getGroupIndexInColumn() {
        return groupIndexInColumn;
    }

    public void setGroupIndexInColumn( int groupIndexInColumn ) {
        this.groupIndexInColumn = groupIndexInColumn;
    }

    public String getBadgeText() {
        return badgeText;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden( boolean hidden ) {
        isHidden = hidden;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !(o instanceof SummaryGroup) ) return false;
        SummaryGroup that = (SummaryGroup) o;
        return getColumnIndex() == that.getColumnIndex() && getGroupIndexInColumn() == that.getGroupIndexInColumn() && Objects.equals( getName(), that.getName() ) && isHidden() == that.isHidden();
    }

    @Override
    public String toString() {
        return "SummaryGroup{" +
                "name='" + name + '\'' +
                ", columnIndex=" + columnIndex +
                ", groupIndexInColumn=" + groupIndexInColumn +
                ", isHidden=" + isHidden +
                '}';
    }

}
