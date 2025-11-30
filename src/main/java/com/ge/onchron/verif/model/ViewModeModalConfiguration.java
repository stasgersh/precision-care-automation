/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2025, 2025, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE HealthCare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 */
package com.ge.onchron.verif.model;

import java.util.Objects;

public class ViewModeModalConfiguration {

    private String selectedViewMode;
    private String protocol;
    private String trialArm;
    private String protocolStartDate;

    public String getSelectedViewMode() {
        return selectedViewMode;
    }

    public void setSelectedViewMode( String selectedViewMode ) {
        this.selectedViewMode = selectedViewMode;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol( String protocol ) {
        this.protocol = protocol;
    }

    public String getTrialArm() {
        return trialArm;
    }

    public void setTrialArm( String trialArm ) {
        this.trialArm = trialArm;
    }

    public String getProtocolStartDate() {
        return protocolStartDate;
    }

    public void setProtocolStartDate( String protocolStartDate ) {
        this.protocolStartDate = protocolStartDate;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        ViewModeModalConfiguration that = (ViewModeModalConfiguration) o;
        return Objects.equals( selectedViewMode, that.selectedViewMode ) && Objects.equals( protocol, that.protocol ) && Objects.equals( trialArm, that.trialArm ) && Objects.equals( protocolStartDate, that.protocolStartDate );
    }

    @Override
    public String toString() {
        return "ViewModeModalConfiguration{" +
                "selectedViewMode='" + selectedViewMode + '\'' +
                ", protocol='" + protocol + '\'' +
                ", trialArm='" + trialArm + '\'' +
                ", protocolStartDate=" + protocolStartDate +
                '}';
    }
}
