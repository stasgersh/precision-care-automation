/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2024, 2024, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 */
package com.ge.onchron.verif.converters;

import java.lang.reflect.Type;
import java.util.Map;

import org.jbehave.core.steps.ParameterConverters;

import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.MacroNavigatorSwimlane;

public class MacroNavigatorSwimlaneConverter extends TableListConverter implements ParameterConverters.ParameterConverter {

    private static final String SWIMLANE_NAME_COLUMN = "swimlane_name";
    private static final String IS_FILTERED_COLUMN = "is_filtered";

    @Override
    public boolean accept( Type type ) {
        return type.getTypeName().equals( MacroNavigatorSwimlane.class.getTypeName() ) && MacroNavigatorSwimlane.class.isAssignableFrom( (Class) type );
    }

    @Override
    MacroNavigatorSwimlane convertSingleValue( String s ) {
        return (MacroNavigatorSwimlane) convertTableWithOneItem( s );
    }

    @Override
    MacroNavigatorSwimlane convertMultiValues( Map<String, String> map ) {
        if ( !map.containsKey( SWIMLANE_NAME_COLUMN ) || !map.containsKey( IS_FILTERED_COLUMN ) ) {
            fail( "Missing mandatory column in the macro-navigator swimlane table in the feature file: " + map );
        }
        return new MacroNavigatorSwimlane( map.get( SWIMLANE_NAME_COLUMN ), Boolean.parseBoolean( map.get( IS_FILTERED_COLUMN ) ) );
    }

}
