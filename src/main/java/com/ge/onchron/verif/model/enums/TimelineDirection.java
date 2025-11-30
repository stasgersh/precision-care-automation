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
package com.ge.onchron.verif.model.enums;

public enum TimelineDirection {
    NEWEST_ON_RIGHT( 0 ),
    NEWEST_ON_LEFT( 1 );

    int timelineDirection;

    TimelineDirection( int timelineDirection ) {
        this.timelineDirection = timelineDirection;
    }

    public int getValue() {
        return timelineDirection;
    }

}
