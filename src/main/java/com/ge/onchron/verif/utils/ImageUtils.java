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
package com.ge.onchron.verif.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import static org.junit.Assert.fail;

public class ImageUtils {

    public static BufferedImage readFileToBufferedImage( String path ) {
        BufferedImage bufferedImage = null;
        try {
            File file = new File( FileUtils.class.getClassLoader().getResource( path ).toURI() );
            bufferedImage = ImageIO.read( file );
        } catch ( Exception e ) {
            fail( "Cannot read file into BufferedImage: " + path + "\n" + e.getMessage() );
        }
        return bufferedImage;
    }

    public static BufferedImage bytesToBufferedImage( byte[] bytes ) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read( new ByteArrayInputStream( bytes ) );
        } catch ( IOException e ) {
            fail( "Error during bytes to buffered image conversion: " + e.getMessage() );
        }
        return bufferedImage;
    }

    public static void saveScreenshot( BufferedImage image ) {
        File outputFile = new File( "target" + File.separator + "TestResults" + File.separator + "screenshots" + File.separator + "screenshot-" + System.currentTimeMillis() + ".png" );
        try {
            File parentDir = outputFile.getParentFile();
            if ( parentDir != null && !parentDir.exists() ) {
                if ( !parentDir.mkdirs() ) {
                    fail( "error while creating directories" );
                }
            }
            ImageIO.write( image, "png", outputFile );
        } catch ( IOException e ) {
            fail( "Error while saving screenshot: " + e.getMessage() );
        }
    }

}
