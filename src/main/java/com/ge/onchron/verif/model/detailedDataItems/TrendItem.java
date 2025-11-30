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
package com.ge.onchron.verif.model.detailedDataItems;

import java.awt.image.BufferedImage;
import java.io.File;

import static org.junit.Assert.fail;

public class TrendItem extends DetailedDataItem {

    private final String SHUTTERBUG_SCREENSHOT_DIRECTORY = "target" + File.separator + "TestResults" + File.separator + "screenshots" + File.separator + "shutterbug";
    private BufferedImage trendBufferedImage;

    public TrendItem( String name, BufferedImage trendBufferedImage ) {
        setName( name );
        this.trendBufferedImage = trendBufferedImage;
    }

    public BufferedImage getTrendBufferedImage() {
        return trendBufferedImage;
    }

    @Override
    public BufferedImage getValue() {
        return trendBufferedImage;
    }

    @Override
    public boolean equals( Object o ) {
        fail( "Currently, it is not supported to compare trend charts in automated tests. VErif it manually." );
        return false;
    }

}
