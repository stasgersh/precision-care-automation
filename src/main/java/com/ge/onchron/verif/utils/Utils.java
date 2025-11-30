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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jbehave.core.model.ExamplesTable;

import static org.junit.Assert.fail;

import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;
import static com.ge.onchron.verif.utils.FileUtils.appendTextToFile;
import static com.ge.onchron.verif.utils.FileUtils.createNewFile;
import static net.serenitybdd.core.Serenity.hasASessionVariableCalled;
import static net.serenitybdd.core.Serenity.sessionVariableCalled;
import static net.serenitybdd.core.Serenity.setSessionVariable;

public class Utils {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger( Utils.class );

    public static void waitMillis(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // restore interrupted status
            LOGGER.warn("Thread interrupted during wait, exiting early.");
            return; // exit instead of throwing AssertionError
        }
    }

    public static boolean waitingTimeExpired( long startTime, int maxWaitingTimeInMillis ) {
        return System.currentTimeMillis() - startTime > maxWaitingTimeInMillis;
    }

    public static void storeMapSessionVariable( String storedMapName, String key, Object value ) {
        Map<String, Object> storedMap = new HashMap<>();
        if ( hasASessionVariableCalled( storedMapName ) ) {
            storedMap = sessionVariableCalled( storedMapName );
        }
        storedMap.put( key, value );
        setSessionVariable( storedMapName ).to( storedMap );
    }

    public static LinkedHashMap<String, String> convertTableToMap( ExamplesTable table ) {
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        for ( Map<String, String> row : table.getRows() ) {
            Map<String, String> param = Collections.singletonMap( row.get( "key" ), row.get( "value" ) );
            parameters.putAll( param );
        }
        return parameters;
    }

    public static String insertSystemParameters( String value ) {
        String replaceableValue = StringUtils.substringBetween( value, "${", "}" );
        while ( replaceableValue != null ) {
            String replacementValue = getSystemParameter( replaceableValue );
            if ( replacementValue != null ) {
                value = value.replace( "${" + replaceableValue + "}", replacementValue );
            } else {
                fail( "Following system parameter is not found: " + replaceableValue );
            }
            replaceableValue = StringUtils.substringBetween( value, "${", "}" );
        }
        return value;
    }

    public static String normalizeLineEndings( String value ) {
        return value.indent( 0 );
    }

    public static void savePerformance( int performance ) {
        String callerMethod = Thread.currentThread().getStackTrace()[4].getMethodName();
        if ( PERFORMANCE_DATA.containsKey( callerMethod ) ) {
            List<Integer> performanceList = PERFORMANCE_DATA.get( callerMethod );
            if ( performanceList.get( 0 ) < performance ) {
                performanceList.add( 0, performance );
            } else {
                performanceList.add( performance );
            }
            PERFORMANCE_DATA.replace( callerMethod, performanceList );
        } else {
            PERFORMANCE_DATA.put( callerMethod, new ArrayList<>( Arrays.asList( performance ) ) );
        }
    }

    public static Map<String, List<Integer>> PERFORMANCE_DATA = new HashMap<>();

    public static void generatePerformanceReport() {
        File performanceFile = createNewFile( "target" + File.separator + "TestResults" + File.separator + "ui_performance_data.txt" );
        String timeStamp = new SimpleDateFormat( "yyyy.MM.dd HH:mm:ss" ).format( Calendar.getInstance().getTime() );
        appendTextToFile( performanceFile, "UI Performance Statistics - Generated at: " + timeStamp );
        appendTextToFile( performanceFile, String.format( "\n\n%-50s %40s %15s\n", "Method name", "Average time (ms)", "Max time (ms)" ) );

        for ( String method : PERFORMANCE_DATA.keySet() ) {
            int average = (int) PERFORMANCE_DATA.get( method ).stream().mapToInt( Integer::intValue ).average().orElse( 0.0 );
            int max = PERFORMANCE_DATA.get( method ).stream().mapToInt( Integer::intValue ).max().getAsInt();
            String formattedText = String.format( "%-50s %40s %15s\n", method, average, max );
            appendTextToFile( performanceFile, formattedText );
        }

        appendTextToFile( performanceFile, "\nAll measured performance data:\n\n" );
        appendTextToFile( performanceFile, String.format( "%-20s %80s\r\n", "Method name", "All measured times (ms)" ) );
        for ( String method : PERFORMANCE_DATA.keySet() ) {
            appendTextToFile( performanceFile, String.format( "%-80s", method + ": " ) + PERFORMANCE_DATA.get( method ) + "\n" );
        }
    }

}
