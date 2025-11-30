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
package com.ge.tracegen;

import com.ge.tracegen.service.MetricsGenerator;
import com.ge.tracegen.service.TraceabilityGenerator;

import static com.ge.tracegen.Constants.DEFAULT_FEATURE_FILES_DIR_PATH;
import static com.ge.tracegen.Constants.INITIAL_TRACEABILITY_FILE_PATH;

/**
 * Traceability and Metrics Generator Tool
 */
public class TraceGenApp {

    public static void main( String[] args ) {
        System.out.println( "----- TraceGen started -----\n" );

        String initialTraceabilityFilePath = System.getProperty( "initialTraceabilityFilePath", INITIAL_TRACEABILITY_FILE_PATH );
        String featureFilesDirPath = System.getProperty( "featureFilesBaseDirPath", DEFAULT_FEATURE_FILES_DIR_PATH );

        TraceabilityGenerator traceGen = new TraceabilityGenerator( initialTraceabilityFilePath, featureFilesDirPath );
        traceGen.writeTestCasesToCSV();
        traceGen.writeTraceabilityToCSV();

        MetricsGenerator metricsGenerator = new MetricsGenerator( traceGen.getMergedTraceability() );
        metricsGenerator.writeMetricsToJson();

        System.out.println( "----- TraceGen ended -----" );
    }

}
