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
package com.ge.onchron.verif.howsteps;

import static java.util.concurrent.TimeUnit.SECONDS;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import com.ge.onchron.verif.utils.FileUtils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.ge.onchron.verif.TestSystemParameters.DEFAULT_DOWNLOAD_DIRECTORY;
import static com.ge.onchron.verif.TestSystemParameters.TEST_DATA_CLASSPATH;
import static com.ge.onchron.verif.utils.FileUtils.listFilesInDir;
import static com.ge.onchron.verif.utils.FileUtils.readFileAsInputStream;
import static com.ge.onchron.verif.utils.FileUtils.readResourceFileAsInputStream;
import static junit.framework.TestCase.fail;

public class DownloadSteps {

    private static final int MAX_WAITING_TIME_IN_SEC_TO_DOWNLOAD_FILE = 20;

    private static final Logger LOGGER = LoggerFactory.getLogger( DownloadSteps.class );

    public void waitForDownloadingFile( String input ) {
        List<String> fileNames = tokenizeFileName( input );
        for ( String fileName : fileNames ) {
            Path downloadDir = FileUtils.getAbsolutPathOfDir( DEFAULT_DOWNLOAD_DIRECTORY );
            Path filePath = Paths.get( downloadDir.toString(), fileName );
            await().atMost( MAX_WAITING_TIME_IN_SEC_TO_DOWNLOAD_FILE, SECONDS )
                   .untilAsserted( () -> {
                       boolean successDownload = filePath.toFile().exists();
                       assertThat( STR."\nFile was not downloaded with name: \{fileName}", successDownload );
                   } );
            LOGGER.info( STR."The file: \{fileName} was downloaded after wait successfully" );
        }
    }

    private static List<String> tokenizeFileName( String input ) {
        if ( input.contains( "+" ) ) {
            return Arrays.asList( input.split( "\\+" ) );
        } else {
            return List.of( input );
        }
    }

    public void checkDownloadedFileContent( String expectedContentFileName ) {
        Path downloadDir = FileUtils.getAbsolutPathOfDir( DEFAULT_DOWNLOAD_DIRECTORY );
        Set<String> files = listFilesInDir( downloadDir );
        LOGGER.info( STR."Number of downloaded files to compare: \{files.size()}" );

        List<String> downloadedFileNames = new ArrayList<>( files );
        List<String> expectedFileNames = tokenizeFileName( expectedContentFileName );
        for ( String expectedBaseFile : expectedFileNames ) {
            // Getting the filename instead of folder structure
            String expectedFile = Paths.get( expectedBaseFile ).getFileName().toString();
            // Try to find a downloaded file that matches the expected file name - matches on multiple files with the same name (e.g.: Genomic report (1).pdf)
            Optional<String> matchedFile = downloadedFileNames.stream()
                                                              .filter( name -> name.equalsIgnoreCase( expectedFile ) )
                                                              .findFirst();

            if ( matchedFile.isEmpty() ) {
                fail( STR."Expected file '\{expectedFile}' not found in downloaded files." );
                continue;
            }
            Path downloadedFilePath = Paths.get( downloadDir.toString(), matchedFile.get() );
            try {
                InputStream expectedContent = readResourceFileAsInputStream( TEST_DATA_CLASSPATH + expectedBaseFile );
                InputStream observedContent = readFileAsInputStream( downloadedFilePath );
                boolean contentMatch = IOUtils.contentEquals( expectedContent, observedContent );
                LOGGER.info( String.format( "The content of expected file: '%s' and observed file: '%s' match: %s'", expectedFile, matchedFile.get(), contentMatch ) );
                assertTrue(
                        String.format( "The content of expected file: '%s' and downloaded file: '%s' does not match", expectedFile, matchedFile.get() ),
                        contentMatch
                );
            } catch ( FileNotFoundException e ) {
                fail( STR."Error while reading file '\{downloadedFilePath}': \{e.getMessage()}" );
            } catch ( IOException e ) {
                fail( STR."Error while comparing file contents: \{e.getMessage()}" );
            }
        }
    }
}