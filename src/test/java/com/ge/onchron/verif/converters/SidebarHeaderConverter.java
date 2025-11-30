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

import com.ge.onchron.verif.model.DetailedSidebarHeader;

public class SidebarHeaderConverter implements ParameterConverters.ParameterConverter {

    private static final String TITLE_COLUMN = "title";
    private static final String CREATION_DATE_COLUMN = "creation_date";
    private static final String REPORT_TYPE_COLUMN = "report_type";

    @Override
    public boolean accept( Type type ) {
        return type instanceof Class && DetailedSidebarHeader.class.isAssignableFrom( (Class) type );
    }

    @Override
    public Object convertValue( String s, Type type ) {
        ExamplesTable table = new ExamplesTable( s );
        assertThat( "Only one item can be added to the sidebar header table in this step definition",
                table.getRows().size(), is( equalTo( 1 ) ) );

        Map<String, String> parameters = table.getRow( 0 );
        if ( !parameters.containsKey( TITLE_COLUMN ) || !parameters.containsKey( CREATION_DATE_COLUMN ) ) {
            fail( "Missing mandatory column in the sidebar header table in the feature file: " + parameters );
        }

        DetailedSidebarHeader header = new DetailedSidebarHeader( parameters.get( TITLE_COLUMN ) );

        // optional columns
        if ( parameters.containsKey( CREATION_DATE_COLUMN ) ) {
            String creationDate = parameters.get( CREATION_DATE_COLUMN );
            if ( creationDate.equals( "<N/A>" ) ) {
                header.setDateText( "" );
            } else {
                header.setDateText( creationDate );
            }
        }
        header.setReportType( parameters.getOrDefault( REPORT_TYPE_COLUMN, null ) );

        return header;
    }

}
