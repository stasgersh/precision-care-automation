/*
 * -GEHC CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2024, 2024, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE HealthCare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 */
package com.ge.onchron.verif.model.summaryCard.summaryCardItemData;

import com.ge.onchron.verif.utils.TextUtils;

public class SummaryCardItemDataCLP extends SummaryCardItemData {

    private String clpContent;

    @Override
    public Object getData() {
        return clpContent;
    }

    public String getClpContent() {
        return clpContent;
    }

    public void setClpContent( String clpContent ) {
        this.clpContent = clpContent;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !(o instanceof SummaryCardItemDataCLP) ) return false;
        SummaryCardItemDataCLP that = (SummaryCardItemDataCLP) o;
        return TextUtils.textCompareWithKeywords( clpContent, that.clpContent );
    }

    @Override
    public String toString() {
        return "SummaryCardItemDataCLP{" +
                ", clpContent='" + clpContent + '\'' +
                '}';
    }

}
