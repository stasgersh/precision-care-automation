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
package com.ge.onchron.verif.converters;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.model.TableTransformers;
import org.jbehave.core.steps.ParameterControls;
import org.jbehave.core.steps.ParameterConverters;

public class OnchronParameterConverters extends ParameterConverters {

    private static final String EXAMPLE_TABLE_SEPARATOR = "\n";
    public static final String CUSTOM_COLLECTION_SEPARATOR = "<COLLECTION_SEPARATOR>";
    public static final String SIMPLE_LIST_SEPARATOR = ",";

    public OnchronParameterConverters() {
        super(
                DEFAULT_STEP_MONITOR,
                new LoadFromClasspath(),
                new ParameterControls(),
                new TableTransformers(),
                DEFAULT_NUMBER_FORMAT_LOCAL,
                CUSTOM_COLLECTION_SEPARATOR,
                true );
    }

    /**
     * Converts the string value (from the feature file) to the required object
     * If the value is a list or a table list, then the list separator is changed to 'CUSTOM_COLLECTION_SEPARATOR' before the convert
     *
     * @param value - value in string format
     * @param type  - type of the required variable
     * @return - return the value (from the feature file) in the required type
     */
    @Override
    public Object convert( String value, Type type ) {
        value = replacePlaceholders( value );
        if ( ( type instanceof ParameterizedType ) && List.class.isAssignableFrom( (Class<?>) ( (ParameterizedType) type ).getRawType() ) ) {
            // Processing Example table where each row is a list item
            if ( value.contains( EXAMPLE_TABLE_SEPARATOR ) ) {
                List<String> tableRows = new LinkedList<>( Arrays.asList( value.split( EXAMPLE_TABLE_SEPARATOR ) ) );
                String headerRow = tableRows.get( 0 );
                tableRows.remove( headerRow );
                StringBuilder table = new StringBuilder();
                for ( int i = 0; i < tableRows.size(); i++ ) {
                    table.append( headerRow ).append( "\n" ).append( tableRows.get( i ) );
                    if ( i < tableRows.size() - 1 ) {
                        table.append( CUSTOM_COLLECTION_SEPARATOR );
                    }
                }
                value = table.toString();

            } else {
                // Processing simple list from the feature file, e.g: "item1, item2, item3"
                value = value.replaceAll( SIMPLE_LIST_SEPARATOR, CUSTOM_COLLECTION_SEPARATOR );
            }
        }
        return super.convert( value, type );
    }

    private String replacePlaceholders( String value ) {
        return value.replaceAll( "<EMPTY>", "" );
    }

}
