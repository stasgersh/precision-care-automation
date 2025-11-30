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
package com.ge.onchron.verif.model.summaryCard.summaryCardItemData.notificationGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.ge.onchron.verif.model.summaryCard.summaryCardItemData.SummaryCardItemData;

public class SummaryCardPatientIdentifier extends SummaryCardItemData {

    private final List<String> textRows = new ArrayList<>();

    public SummaryCardPatientIdentifier( List<String> patientDetails ) {
        textRows.addAll( patientDetails );
    }

    @Override
    public List<String> getData() {
        return textRows;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !(o instanceof SummaryCardPatientIdentifier) ) return false;
        SummaryCardPatientIdentifier that = (SummaryCardPatientIdentifier) o;
        return Objects.equals( textRows, that.textRows );
    }

    @Override
    public String toString() {
        return "PatientIdentifier{" +
                "textRows=" + textRows +
                '}';
    }

}
