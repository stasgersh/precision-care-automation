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

import com.ge.onchron.verif.model.summaryCard.enums.SummaryCardItemType;
import com.ge.onchron.verif.model.summaryCard.summaryCardItemData.SummaryCardItemData;
import com.ge.onchron.verif.model.summaryCard.summaryCardItemData.SummaryCardItemDataColor;

import java.util.Objects;

public class SummaryCardItem {

    private SummaryCardItemType type;
    private SummaryCardItemData value;
    private SummaryCardItemDataColor color;

    public SummaryCardItemType getType() {
        return type;
    }

    public void setType( SummaryCardItemType type ) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue( SummaryCardItemData value ) {
        this.value = value;
    }

    public SummaryCardItemDataColor getColor() {
        return color;
    }

    public void setColor(SummaryCardItemDataColor color) {
        this.color = color;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !(o instanceof SummaryCardItem) ) return false;
        SummaryCardItem that = (SummaryCardItem) o;

        boolean typeMatch = Objects.equals( this.type, that.type );
        boolean valueMatch = Objects.equals( this.value, that.value );

        //Optional parameter
        boolean colorMatch = (this.color == null || that.color == null ) || Objects.equals( this.color, that.color );

        return typeMatch && valueMatch && colorMatch;
    }

    @Override
    public String toString() {
        return "SummaryCardItem{" +
                "type=" + type +
                ", value=" + value +
                ", color=" + color +
                '}';
    }
}
