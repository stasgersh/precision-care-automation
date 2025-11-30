/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2023, 2023, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.model.detailedDataItems;

import java.util.Objects;

public class EmptyInfoItem extends DetailedDataItem {

    private String value;
    private String color;

    public EmptyInfoItem( String value ) {
        setName( "N/A" );
        this.value = value;
    }

    public EmptyInfoItem( String value, String color ) {
        setName( "N/A" );
        this.value = value;
        this.color = color;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmptyInfoItem that = (EmptyInfoItem) o;
        return Objects.equals(value, that.value) && Objects.equals(color, that.color);
    }

    @Override
    public String toString() {
        return STR."EmptyInfoItem{value='\{value}\{'\''}, color='\{color}\{'\''}\{'}'}";
    }
}
