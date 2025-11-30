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

import com.ge.onchron.verif.model.l3report.L3ModalHeader;

public class L3ModalHeaderConverter implements ParameterConverters.ParameterConverter {

    private static final String TITLE_COLUMN = "title";
    private static final String SUB_TITLE_COLUMN = "sub_title";

    @Override
    public boolean accept( Type type ) {
        return type instanceof Class && L3ModalHeader.class.isAssignableFrom( (Class) type );
    }

    @Override
    public Object convertValue( String s, Type type ) {
        ExamplesTable table = new ExamplesTable( s );
        assertThat( "Only one item can be added to the sidebar header table in this step definition",
                table.getRows().size(), is( equalTo( 1 ) ) );
        Map<String, String> parameters = table.getRow( 0 );
        if ( !parameters.containsKey( TITLE_COLUMN ) || !parameters.containsKey( SUB_TITLE_COLUMN ) ) {
            fail( String.format( "Missing mandatory column in the L3 modal header table in the feature file: %s", parameters ) );
        }
        return new L3ModalHeader( parameters.get( TITLE_COLUMN ), parameters.get( SUB_TITLE_COLUMN ) );
    }

}
