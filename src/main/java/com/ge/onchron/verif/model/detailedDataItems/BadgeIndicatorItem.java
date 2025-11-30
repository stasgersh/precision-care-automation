/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2023, 2023, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.model.detailedDataItems;

import com.ge.onchron.verif.model.Badge;

public class BadgeIndicatorItem extends DetailedDataItem {

    private Badge badge;

    public BadgeIndicatorItem( Badge badge ) {
        this.badge = badge;
    }

    public Badge getValue() {
        return badge;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !(o instanceof BadgeIndicatorItem) ) return false;
        BadgeIndicatorItem that = (BadgeIndicatorItem) o;
        return badge.equals( that.badge );
    }

    @Override
    public String toString() {
        return "BadgeItem{" +
                "badge=" + badge +
                '}';
    }

}
