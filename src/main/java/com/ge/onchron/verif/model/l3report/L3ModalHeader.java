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
package com.ge.onchron.verif.model.l3report;

import java.util.Objects;

public class L3ModalHeader {

    private final String title;
    private final String subTitle;

    public L3ModalHeader( String title, String subTitle ) {
        this.title = title;
        this.subTitle = subTitle;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !(o instanceof L3ModalHeader that) ) return false;
        return Objects.equals( title, that.title ) && Objects.equals( subTitle, that.subTitle );
    }

    @Override
    public String toString() {
        return "L3ModalHeader{" +
                "title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                '}';
    }

}
