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
package com.ge.tracegen.service.dataWriters;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.ge.tracegen.models.Scenario;
import com.ge.tracegen.models.Trace;

import static com.ge.tracegen.Constants.CSV_SEPARATOR;
import static com.ge.tracegen.Constants.GENERATED_FILES_PATH;
import static com.ge.tracegen.utils.FileUtils.saveFile;

public class TestCaseWriter extends BaseTraceabilityProcessor {

    public TestCaseWriter( List<Trace> traceability ) {
        super( traceability );
    }

    public void generateTestCasesToCSV() {
        // Create test ID - test type (automated/manual) csv => mergedTraceability can contain a scenario more times, so observedTraceability is used here
        List<Scenario> observedScenarios = getScenariosFromTraceabilityList();
        StringBuilder scenarioCsv = new StringBuilder();
        scenarioCsv.append( "Test ID" + CSV_SEPARATOR + "Title" + CSV_SEPARATOR + "Test Type" + CSV_SEPARATOR + "Annotations" + "\n" );
        observedScenarios.forEach( scenario -> {
            String testType = scenario.isAutomated() ? "Automated" : "Manual";
            String annotations = collectAdditionalAnnotations( scenario );
            scenarioCsv.append( scenario.getId() + CSV_SEPARATOR + scenario.getTitle() + CSV_SEPARATOR + testType + CSV_SEPARATOR + annotations + "\n" );
        } );
        Path testFilePath = Paths.get( GENERATED_FILES_PATH, "testCases.csv" );
        saveFile( testFilePath, scenarioCsv.toString() );
        System.out.println( "Test cases saved into: " + testFilePath + "\n" );
    }

    public String collectAdditionalAnnotations( Scenario scenario ) {
        StringBuilder annotationsBuilder = new StringBuilder();
        scenario.getAdditionalAnnotations().forEach( additionalAnnotation -> {
            annotationsBuilder.append( additionalAnnotation + " " );
        } );
        return annotationsBuilder.toString();
    }

}
