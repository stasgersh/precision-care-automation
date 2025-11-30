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

public class SummaryCardItemDataString extends SummaryCardItemData {

    private final String data;

    public SummaryCardItemDataString( String data ) {
        this.data = data;
    }

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !(o instanceof SummaryCardItemDataString) ) return false;
        SummaryCardItemDataString that = (SummaryCardItemDataString) o;
        return Objects.equals( getData(), that.getData() );
    }

    @Override
    public String toString() {
        return "SummaryCardItemDataString{" +
                "data='" + data + '\'' +
                '}';
    }

}
