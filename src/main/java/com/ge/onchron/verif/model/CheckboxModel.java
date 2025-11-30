/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2021, 2021, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.model;

import java.util.Objects;

public class CheckboxModel {

    private String label;
    private boolean isSelected;
    private boolean isEnabled;

    public CheckboxModel( String name, boolean isSelected ) {
        this.label = name;
        this.isSelected = isSelected;
    }

    public CheckboxModel( String name, boolean isSelected, boolean isEnabled ) {
        this.label = name;
        this.isSelected = isSelected;
        this.isEnabled = isEnabled;
    }

    public void setLabel( String label ) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setSelected( boolean selected ) {
        this.isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setEnabled( boolean isEnabled ) {
        this.isEnabled = isEnabled;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public String toString() {
        return "CheckboxModel{" +
                "label='" + label + '\'' +
                ", isSelected=" + isSelected +
                ", isEnabled=" + isEnabled +
                '}';
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        CheckboxModel that = (CheckboxModel) o;
        return isSelected == that.isSelected && isEnabled == that.isEnabled && Objects.equals( label, that.label );
    }

}
