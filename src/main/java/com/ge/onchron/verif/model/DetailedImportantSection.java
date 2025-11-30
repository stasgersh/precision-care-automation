/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2022, 2022, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.model;

import java.util.Objects;

public class DetailedImportantSection {

    private boolean isHeaderTitleHighlighted;
    private boolean isStarFilled;
    private String markAsImportantText;

    public DetailedImportantSection( boolean isHeaderTitleHighlighted, boolean isStarFilled, String markAsImportantText ) {
        this.isHeaderTitleHighlighted = isHeaderTitleHighlighted;
        this.isStarFilled = isStarFilled;
        this.markAsImportantText = markAsImportantText;
    }

    public boolean isStarFilled() {
        return isStarFilled;
    }

    public String getMarkAsImportantText() {
        return markAsImportantText;
    }

    @Override
    public int hashCode() {
        return Objects.hash( isHeaderTitleHighlighted, isStarFilled, markAsImportantText );
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        DetailedImportantSection that = (DetailedImportantSection) o;
        return isHeaderTitleHighlighted == that.isHeaderTitleHighlighted && isStarFilled == that.isStarFilled && markAsImportantText.equals( that.markAsImportantText );
    }

    @Override
    public String toString() {
        return "MarkAsImportantSection{" +
                "isHeaderTitleHighlighted=" + isHeaderTitleHighlighted +
                ", isStarFilled='" + isStarFilled + '\'' +
                ", markAsImportantText='" + markAsImportantText + '\'' + '}';
    }
}
