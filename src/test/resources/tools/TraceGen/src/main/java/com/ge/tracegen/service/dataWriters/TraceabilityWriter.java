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

import com.ge.tracegen.models.Trace;

import static com.ge.tracegen.Constants.CSV_SEPARATOR;
import static com.ge.tracegen.Constants.GENERATED_FILES_PATH;
import static com.ge.tracegen.utils.FileUtils.saveFile;

public class TraceabilityWriter {

    List<Trace> traceability;

    public TraceabilityWriter( List<Trace> traceability ) {
        this.traceability = traceability;
    }

    public void generateTraceabilityToCSV() {
        StringBuilder generatedCSVTraceability = new StringBuilder();
        // add headers
        generatedCSVTraceability.append( "SRS ID" ).append( CSV_SEPARATOR )
                                .append( "Requirement" ).append( CSV_SEPARATOR )
                                .append( "Safety" ).append( CSV_SEPARATOR )
                                .append( "Link" ).append( CSV_SEPARATOR )
                                .append( "TEST ID" ).append( "\n" );

        traceability.forEach( trace -> {
            String safetyFlag = trace.isSafety() ? "Yes" : "No";
            String firstTraceLine = trace.getReqId() + CSV_SEPARATOR + trace.getDescription() + CSV_SEPARATOR + safetyFlag + CSV_SEPARATOR;

            if ( trace.hasLink() ) {                                                                        // covered by at least one link
                generatedCSVTraceability.append( firstTraceLine );
                String firstScenarioId = trace.getScenarios().get( 0 ).getId();
                generatedCSVTraceability.append( "x" ).append( CSV_SEPARATOR );                             // set 'Link' column
                generatedCSVTraceability.append( firstScenarioId ).append( "\n" );
                for ( int i = 1; i < trace.getScenarios().size(); i++ ) {
                    String additionalTraceLine = CSV_SEPARATOR + CSV_SEPARATOR + CSV_SEPARATOR + "x" + CSV_SEPARATOR;
                    generatedCSVTraceability.append( additionalTraceLine ).append( trace.getScenarios().get( i ).getId() ).append( "\n" );
                }
            } else if ( trace.getScenarios().isEmpty() ) {                                                  // not covered
                generatedCSVTraceability.append( firstTraceLine );
                generatedCSVTraceability.append( CSV_SEPARATOR )    // set empty 'Link' column
                                        .append( "Not Covered" ).append( "\n" );
            } else {                                                                                        // covered by direct tests
                generatedCSVTraceability.append( firstTraceLine );
                String firstScenarioId = trace.getScenarios().get( 0 ).getId();
                generatedCSVTraceability.append( CSV_SEPARATOR );    // set empty 'Link' column
                generatedCSVTraceability.append( firstScenarioId ).append( "\n" );
                for ( int i = 1; i < trace.getScenarios().size(); i++ ) {
                    String additionalTraceLine = CSV_SEPARATOR + CSV_SEPARATOR + CSV_SEPARATOR + CSV_SEPARATOR;
                    generatedCSVTraceability.append( additionalTraceLine ).append( trace.getScenarios().get( i ).getId() ).append( "\n" );
                }
            }
        } );
        Path traceFilePath = Paths.get( GENERATED_FILES_PATH, "generatedTraceability.csv" );
        saveFile( traceFilePath, generatedCSVTraceability.toString() );
        System.out.println( "Traceability saved into: " + traceFilePath + "\n" );
    }

}
