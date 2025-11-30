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
package com.ge.tracegen.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {

    public static String readContentFromFile( File file ) {
        String fileContent = null;
        try {
            fileContent = Files.readString( file.toPath() );
        } catch ( IOException e ) {
            System.out.println( "Error while reading data from file '" + file.toPath() + "': " + e.getMessage() );
        }
        return fileContent;
    }

    public static void saveFile( Path filePath, String fileContent ) {
        try {
            new File( filePath.getParent().toString() ).mkdirs();   // create directories
            Files.writeString( filePath, fileContent, StandardCharsets.UTF_8 );
        } catch ( IOException e ) {
            System.out.println( "Error while saving the file " + filePath + ": " + e );
        }
    }

}
