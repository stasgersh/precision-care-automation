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
package com.ge.tracegen.service.dataReaders;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ge.tracegen.models.Scenario;
import com.ge.tracegen.models.Trace;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import static com.ge.tracegen.Constants.CSV_SEPARATOR_CHAR;

public class InitialTraceReader {

    String initialTraceabilityFilePath;

    public InitialTraceReader( String initialTraceabilityFilePath ) {
        this.initialTraceabilityFilePath = initialTraceabilityFilePath;
    }

    public List<Trace> getTraceability() {
        return readInitialTraceabilityFromCSV();
    }

    private List<Trace> readInitialTraceabilityFromCSV() {
        List<Trace> initialTraceability = new ArrayList<>();
        CSVParser csvParser = new CSVParserBuilder().withSeparator( CSV_SEPARATOR_CHAR ).build(); // custom separator
        try ( CSVReader reader = new CSVReaderBuilder(
                new FileReader( initialTraceabilityFilePath ) )
                .withCSVParser( csvParser )   // custom CSV parser
                .withSkipLines( 1 )           // skip the first line, header info
                .build() ) {
            List<String[]> initialTracesInString = reader.readAll();
            initialTracesInString.forEach( traceInString -> {
                Trace trace = new Trace();
                trace.setReqId( traceInString[0] );
                trace.setDescription( traceInString[1].replace( "\n", " " ) ); // replace new line char in excel cell
                trace.setSafety( traceInString[2].equalsIgnoreCase( "Yes" ) );
                String hasLink = traceInString[3];
                if ( hasLink.equalsIgnoreCase( "x" ) ) {
                    trace.setHasLink( true );
                    String linkedTestIds = traceInString[4];
                    String[] testIds = linkedTestIds.split( "," ); // test existence in feature files is checked in mergeTraceabilities() by using verifyInitialScenarios() method
                    Arrays.stream( testIds ).forEach( testId -> {
                        Scenario scenario = new Scenario();
                        scenario.setId( testId.replace( " ", "" ) ); // removing whitespaces from test id
                        trace.addScenario( scenario );
                    } );
                }
                initialTraceability.add( trace );
            } );
        } catch ( IOException | CsvException e ) {
            e.printStackTrace();
        }
        return initialTraceability;
    }

}
