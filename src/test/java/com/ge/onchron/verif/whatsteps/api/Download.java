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
package com.ge.onchron.verif.whatsteps.api;

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Then;

import com.ge.onchron.verif.howsteps.DownloadSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import net.thucydides.core.annotations.Steps;

public class Download {

    @Steps
    private DownloadSteps downloadSteps;

    private TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Then( "{a} {file|files} {is|are} downloaded with name \"$fileName\"" )
    @Alias( "files are downloaded with names \"$fileName\"" )
    public void waitForDownloadingFile( String fileName ) {
        testDefinitionSteps.addStep( "Wait for file to be downloaded" );
        downloadSteps.waitForDownloadingFile( fileName );
        testDefinitionSteps.logEvidence( STR."The \{fileName} was successfully downloaded after wait",
                "The file was successfully downloaded (check previous logs)", true );
    }

    @Then( "the downloaded {file|files} content {is|are} equal to: $contentFileName" )
    public void checkDownloadedFile( String contentFileName ) {
        testDefinitionSteps.addStep( "Check the downloaded file content" );
        downloadSteps.checkDownloadedFileContent( contentFileName );
        testDefinitionSteps.logEvidence( STR."The \{contentFileName} content is valid",
                "The file content is valid (check previous logs)", true );
    }

}
