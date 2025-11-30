/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2024, 2024, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE HealthCare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 */
package com.ge.onchron.verif.converters.L3Modal;

import java.lang.reflect.Type;
import java.util.Map;

import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.steps.ParameterConverters;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.l3report.L3ReportHeader;
import com.ge.onchron.verif.model.l3report.L3ReportHeaderOnEventsList;

public class L3ReportHeaderConverter implements ParameterConverters.ParameterConverter {

    private static final String TITLE_COLUMN = "title";
    private static final String CREATION_DATE_COLUMN = "creation_date";
    private static final String AUTHOR_COLUMN = "author";
    private static final String STATUS_COLUMN = "status";
    private static final String SELECTED_COLUMN = "selected";

    @Override
    public boolean accept( Type type ) {
        return type instanceof Class && L3ReportHeader.class.isAssignableFrom( (Class) type );
    }

    private boolean isL3ReportHeaderOnEventsList( Type type ) {
        return type instanceof Class && L3ReportHeaderOnEventsList.class.isAssignableFrom( (Class) type );
    }

    @Override
    public Object convertValue( String s, Type type ) {
        ExamplesTable table = new ExamplesTable( s );
        assertThat( "Only one item can be added to the sidebar header table in this step definition",
                table.getRows().size(), is( equalTo( 1 ) ) );

        Map<String, String> parameters = table.getRow( 0 );
        if ( !parameters.containsKey( TITLE_COLUMN ) || !parameters.containsKey( CREATION_DATE_COLUMN ) ||
                !parameters.containsKey( AUTHOR_COLUMN ) || !parameters.containsKey( STATUS_COLUMN ) ) {
            fail( String.format( "Missing mandatory column in the L3 report header table in the feature file: %s", parameters ) );
        }

        L3ReportHeader header = new L3ReportHeader();
        header.setTitle( parameters.get( TITLE_COLUMN ) );
        header.setDateText( parameters.get( CREATION_DATE_COLUMN ) );
        header.setAuthor( parameters.get( AUTHOR_COLUMN ) );
        header.setStatus( parameters.get( STATUS_COLUMN ) );

        if ( isL3ReportHeaderOnEventsList( type ) ) {
            if ( !parameters.containsKey( SELECTED_COLUMN ) ) {
                fail( String.format( "Missing mandatory column in the 'Event Data On L3 Event List' table in the feature file: %s", parameters ) );
            }
            L3ReportHeaderOnEventsList headerOnEventsList = new L3ReportHeaderOnEventsList( header );
            headerOnEventsList.setSelected( Boolean.parseBoolean( parameters.get( SELECTED_COLUMN ) ) );
            return headerOnEventsList;
        }
        return header;
    }

}
