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
import java.util.List;
import java.util.Map;

import org.jbehave.core.model.ExamplesTable;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public abstract class TableListConverter {

    private static final String DATA_SEPARATOR = "|";

    public Object convertValue( String s, Type type ) {
        if ( s.substring( 0, 1 ).equals( DATA_SEPARATOR ) ) {
            ExamplesTable examplesTable = new ExamplesTable( s );
            Map<String, String> parameterMap = examplesTable.getRow( 0 );
            return convertMultiValues( parameterMap );
        } else {
            return convertSingleValue( s );
        }
    }

    public Object convertTableWithOneItem( String s ) {
        ExamplesTable examplesTable = new ExamplesTable( s );
        List<Map<String, String>> tableItems = examplesTable.getRows();
        assertThat( "Only one item can be added to this step definition", tableItems.size(), is( equalTo( 1 ) ) );
        return convertMultiValues( tableItems.get( 0 ) );
    }

    abstract Object convertSingleValue( String s );

    abstract Object convertMultiValues( Map<String, String> map );

}
