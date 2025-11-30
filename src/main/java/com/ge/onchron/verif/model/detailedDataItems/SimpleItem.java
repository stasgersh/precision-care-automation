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
package com.ge.onchron.verif.model.detailedDataItems;

import java.util.Objects;

public class SimpleItem extends DetailedDataItem {

    private String value;
    private String color;

    public SimpleItem( String name ) {
        setName( name );
    }

    public SimpleItem( String name, String value ) {
        setName( name );
        this.value = value;
    }

    public void setValue( String value ) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getColor() {
        return color;
    }

    public void setColor( String color ) {
        this.color = color;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        SimpleItem that = (SimpleItem) o;
        return Objects.equals( getName(), that.getName() )
                && Objects.equals( value, that.value )
                && (color == null || (that.color != null && color.equals( that.color )));
    }

    @Override
    public String toString() {
        return "SimpleItem{" +
                "name='" + getName() + '\'' +
                ", value='" + value + '\'' +
                (color == null ? '}' : ", color='" + getColor() + "'}");
    }

}
