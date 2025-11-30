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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.steps.ParameterConverters;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.ge.onchron.verif.model.StringList;

public class StringListConverter implements ParameterConverters.ParameterConverter {

    @Override
    public boolean accept( Type type ) {
        return type instanceof Class && StringList.class.isAssignableFrom( (Class) type );
    }

    @Override
    public Object convertValue( String s, Type type ) {
        ExamplesTable table = new ExamplesTable( s );
        if ( table.getRowCount() != 0 ) {
            assertThat( "Only one example table column is allowed.", table.getHeaders().size(), is( equalTo( 1 ) ) );

            String header = table.getHeaders().get( 0 );
            List<String> list = getItemsFromTableColumn( table, header );
            return new StringList( list );
        } else {
            String[] array = s.split( ", |," );
            return new StringList( Arrays.asList( array ) );
        }
    }

    private List<String> getItemsFromTableColumn( ExamplesTable table, String header ) {
        List<String> itemsFromColumn = new ArrayList<>();
        table.getRowsAsParameters( true ).forEach( row -> itemsFromColumn.add( row.values().get( header ) ) );
        return itemsFromColumn;
    }

}
