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

import java.util.Objects;

public class MacroNavigatorSwimlane {

    private String name;
    private boolean isFiltered;

    public MacroNavigatorSwimlane( String name, boolean isFiltered ) {
        this.name = name;
        this.isFiltered = isFiltered;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public boolean isFiltered() {
        return isFiltered;
    }

    public void setFiltered( boolean filtered ) {
        isFiltered = filtered;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !(o instanceof MacroNavigatorSwimlane) ) return false;
        MacroNavigatorSwimlane that = (MacroNavigatorSwimlane) o;
        return isFiltered() == that.isFiltered() && Objects.equals( getName(), that.getName() );
    }

    @Override
    public String toString() {
        return "MacroNavigatorSwimlane{" +
                "name='" + name + '\'' +
                ", isFiltered=" + isFiltered +
                '}';
    }

}
