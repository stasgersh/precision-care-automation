/*
 * -GE CONFIDENTIAL- or -GE HIGHLY CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2025, 2025, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE HealthCare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 * GE is a trademark of General Electric Company used under trademark license.
 */

package com.ge.onchron.verif.converters.types;

import java.text.DecimalFormat;


public class Bitrate {

    public static final int DEFAULT = -1;

    private int kiloBitPerSecond;

    public Bitrate(int kiloBitPerSecond) {
        this.kiloBitPerSecond = kiloBitPerSecond;
    }

    public int getKiloBitPerSecond() {
        return kiloBitPerSecond;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.##");
        if ( kiloBitPerSecond == DEFAULT ) {
            return STR."'Default' kbit/s";
        } else if ( kiloBitPerSecond >= 1000 ) {
            return STR."\{df.format( kiloBitPerSecond / 1000 )} Mbit/s";
        } else {
            return STR."\{kiloBitPerSecond} kbit/s";
        }
    }
}
