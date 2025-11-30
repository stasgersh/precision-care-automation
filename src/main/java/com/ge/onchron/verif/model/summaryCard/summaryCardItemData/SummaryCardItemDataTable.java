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

import com.ge.onchron.verif.model.Table;

public class SummaryCardItemDataTable extends SummaryCardItemData {

    private final Table table;

    public SummaryCardItemDataTable( Table table ) {
        this.table = table;
    }

    @Override
    public Object getData() {
        return table;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !(o instanceof SummaryCardItemDataTable) ) return false;
        SummaryCardItemDataTable that = (SummaryCardItemDataTable) o;
        return Objects.equals( table, that.table );
    }

    @Override
    public String toString() {
        return "SummaryCardItemDataTable{" +
                "table=" + table +
                '}';
    }

}
