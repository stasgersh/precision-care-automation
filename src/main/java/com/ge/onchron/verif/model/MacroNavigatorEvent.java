/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2024, 2024, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 */
package com.ge.onchron.verif.model;

public class MacroNavigatorEvent {

    private int index;
    private boolean isFiltered;
    private boolean isSelected;

    public MacroNavigatorEvent( int index ) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public boolean isFiltered() {
        return isFiltered;
    }

    public void isFiltered( boolean isFiltered ) {
        this.isFiltered = isFiltered;
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
        if ( !(o instanceof MacroNavigatorEvent) ) return false;
        MacroNavigatorEvent that = (MacroNavigatorEvent) o;
        return getIndex() == that.getIndex() && isFiltered() == that.isFiltered() && isSelected() == that.isSelected();
    }

    @Override
    public String toString() {
        return "MacroNavigatorEvent{" +
                "index=" + index +
                ", isFiltered=" + isFiltered +
                ", isSelected=" + isSelected +
                '}';
    }

}
