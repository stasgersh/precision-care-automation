/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2021, 2021, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.json.JSONObject;
import org.json.JSONTokener;

import static org.apache.commons.io.FileUtils.writeStringToFile;
import static org.junit.Assert.fail;

import junit.framework.TestCase;
import net.serenitybdd.core.Serenity;

import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;


public class FileUtils {

    public static String readFromFile( String path ) {
        String fileContent = null;
        try {
            Path referenceFilePath = Paths.get( ClassLoader.getSystemClassLoader().getResource( path ).toURI() );
            fileContent = Files.readString( referenceFilePath );
        } catch ( Exception e ) {
            fail( "Error while reading data from file '" + path + "': " + e.getMessage() );
        }
        return fileContent;
    }

    public static String loadConfigFile( final String fileName, String Path ) {
        Path resourcesAbsolutPath = getAbsolutPathOfDir( getSystemParameter( Path ) );
        Path filePath = Paths.get( resourcesAbsolutPath.toString(), STR."\{fileName}.json" );
        return readFromFileByAbsolutePath( filePath.toString() );
    }

    public static String readFromFileByAbsolutePath( String absolutePath ) {
        String fileContent = null;
        try {
            File file = new File( absolutePath );
            fileContent = Files.readString( file.toPath() );
        } catch ( IOException e ) {
            fail( "Error while reading data from file '" + absolutePath + "': " + e.getMessage() );
        }

        return fileContent;
    }

    public static JSONObject readFromJsonFileByAbsolutePath( String absolutePath ) {
        try {
            File file = new File( absolutePath );
            FileReader reader = new FileReader( file );
            JSONTokener tokener = new JSONTokener( reader );
            return new JSONObject( tokener );
        } catch ( IOException e ) {
            e.printStackTrace();
            return null;
        }
    }

    public static InputStream readResourceFileAsInputStream( String path ) {
        return FileUtils.class.getClassLoader().getResourceAsStream( path );
    }

    public static InputStream readFileAsInputStream( Path path ) throws FileNotFoundException {
        return new FileInputStream( new File( path.toUri() ) );
    }

    public static Set<String> listFilesInDir( Path path ) {
        Set<String> files = new HashSet<>();
        try {
            files = Files.list( path )
                         .map( Path::getFileName )
                         .map( Path::toString )
                         .collect( Collectors.toSet() );
        } catch ( IOException e ) {
            fail( "Error while listing files from directory: " + path );
        }
        return files;
    }

    public static boolean isEmptyDir( Path path ) {
        return Objects.requireNonNull( path.toFile().listFiles() ).length == 0;
    }

    public static void copyDir( String sourceDirPath, String destinationDirPath ) {
        File sourceDir = new File( sourceDirPath );
        if ( !sourceDir.exists() ) {
            fail( "Directory does not exist and cannot be copied: " + sourceDirPath );
        }
        File destinationDir = new File( destinationDirPath );
        try {
            org.apache.commons.io.FileUtils.copyDirectory( sourceDir, destinationDir );
        } catch ( IOException e ) {
            fail( String.format( "Failed to copy '%s' folder to '%s'", sourceDirPath, destinationDirPath ) );
        }
    }

    public static void createDirIfNotExists( String dirPath ) {
        File dir = new File( dirPath );
        if ( !dir.exists() ) {
            if ( !dir.mkdirs() ) {
                fail( "error while creating directory: " + dirPath );
            }
        }
    }

    public static Path getAbsolutPathOfDir( String dir ) {
        String userDir = Serenity.environmentVariables().getProperty( "user.dir" );
        return Paths.get( userDir, dir );
    }

    public static void saveFile( Path filePath, String fileContent ) {
        try {
            Files.writeString( filePath, fileContent, StandardCharsets.UTF_8 );
        } catch ( IOException e ) {
            fail( "Error while saving the file " + filePath + ": " + e );
        }
    }

    public static File createNewFile( String filePath ) {
        return new File( filePath );
    }

    public static void appendTextToFile( File fileName, String text ) {
        try {
            writeStringToFile( fileName, text, "UTF-8", true );
        } catch ( IOException e ) {
            TestCase.fail( "File write was not successful: " + fileName );
        }
    }

    public static void zipFile( String filePath, String targetZipPath ) throws IOException {
        File fileToZip = new File( filePath );
        FileOutputStream fos = new FileOutputStream( targetZipPath );
        ZipOutputStream zipOut = new ZipOutputStream( fos );
        zipFile( fileToZip, fileToZip.getName(), zipOut );
        zipOut.close();
        fos.close();
    }

    private static void zipFile( File fileToZip, String fileName, ZipOutputStream zipOut ) throws IOException {
        if ( fileToZip.isHidden() ) {
            return;
        }
        if ( fileToZip.isDirectory() ) {
            if ( fileName.endsWith( "/" ) ) {
                zipOut.putNextEntry( new ZipEntry( fileName ) );
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry( new ZipEntry( fileName + "/" ) );
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for ( File childFile : children ) {
                zipFile( childFile, fileName + "/" + childFile.getName(), zipOut );
            }
            return;
        }
        FileInputStream fis = new FileInputStream( fileToZip );
        ZipEntry zipEntry = new ZipEntry( fileName );
        zipOut.putNextEntry( zipEntry );
        byte[] bytes = new byte[1024];
        int length;
        while ( (length = fis.read( bytes )) >= 0 ) {
            zipOut.write( bytes, 0, length );
        }
        fis.close();
    }

    public static boolean isFilePresent( String path ) {
        try {
            URL fileUrl = ClassLoader.getSystemClassLoader().getResource( path );
            if ( fileUrl != null ) {
                File f = new File( fileUrl.toURI() );
                return f.exists() && !f.isDirectory();
            }
            return false;
        } catch ( URISyntaxException e ) {
            return false;
        }
    }

    public static List<File> listFilesInFolder(String folderPath) {
        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            throw new IllegalArgumentException(STR."Invalid folder path: \{folderPath}");
        }

        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("No files found in the folder.");
            return Collections.emptyList();
        }
        return Arrays.asList(files);
    }



}
