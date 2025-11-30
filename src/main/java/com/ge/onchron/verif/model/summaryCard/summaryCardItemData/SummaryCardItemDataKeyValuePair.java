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
package com.ge.onchron.verif.model.summaryCard.summaryCardItemData;

import java.util.Objects;

public class SummaryCardItemDataKeyValuePair extends SummaryCardItemData {

    private String key;
    private String value;

    @Override
    public Object getData() {
        return key + " " + value;
    }

    public String getKey() {
        return key;
    }

    public void setKey( String key ) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue( String value ) {
        this.value = value;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !(o instanceof SummaryCardItemDataKeyValuePair) ) return false;
        SummaryCardItemDataKeyValuePair that = (SummaryCardItemDataKeyValuePair) o;
        return Objects.equals( getKey(), that.getKey() ) && Objects.equals( getValue(), that.getValue() );
    }

    @Override
    public String toString() {
        return "SummaryCardItemDataKeyValuePair{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

}
