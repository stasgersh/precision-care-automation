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
package com.ge.onchron.verif.converters;

import java.lang.reflect.Type;
import java.util.Map;

import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.steps.ParameterConverters;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.DetailedImportantSection;

public class MarkAsImportantSectionConverter implements ParameterConverters.ParameterConverter {

    private static final String HEADER_TITLE_HIGHLIGHTED = "header_title_highlighted";
    private static final String STAR_FILLED = "star_filled";
    private static final String DISPLAYED_TEXT = "displayed_text";

    @Override
    public boolean accept( Type type ) {
        return type instanceof Class && DetailedImportantSection.class.isAssignableFrom( (Class) type );
    }

    @Override
    public Object convertValue( String s, Type type ) {
        ExamplesTable table = new ExamplesTable( s );
        assertThat( "Only one item can be added to the expected table in this step definition",
                table.getRows().size(), is( equalTo( 1 ) ) );

        Map<String, String> parameters = table.getRow( 0 );
        if ( !parameters.containsKey( HEADER_TITLE_HIGHLIGHTED ) || !parameters.containsKey( STAR_FILLED ) || !parameters.containsKey( DISPLAYED_TEXT ) ) {
            fail( "Missing mandatory column in the expected table in the feature file: " + parameters );
        }

        return new DetailedImportantSection(
                parameters.get( HEADER_TITLE_HIGHLIGHTED ).equalsIgnoreCase( "Yes" ),
                parameters.get( STAR_FILLED ).equalsIgnoreCase( "Yes" ),
                parameters.get( DISPLAYED_TEXT ) );
    }

}
