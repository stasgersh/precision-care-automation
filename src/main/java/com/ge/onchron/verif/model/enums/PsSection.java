/*
 * -GE CONFIDENTIAL-
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
package com.ge.onchron.verif.model.enums;

public enum PsSection {
    BRIEF_HISTORY( "Brief history" ),
    WHAT_HAS_CHANGED( "What has changed" ),
    SUMMARY_OF_RECENT_REPORTS( "Summary of recent reports" );

    private final String sectionName;

    PsSection( String sectionName ) {
        this.sectionName = sectionName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public static PsSection fromString( String sectionName ) {
        for ( PsSection section : PsSection.values() ) {
            if ( section.sectionName.equalsIgnoreCase( sectionName ) ) {
                return section;
            }
        }
        throw new IllegalArgumentException( String.format( "Unknown PS section: %s", sectionName ) );
    }

}
