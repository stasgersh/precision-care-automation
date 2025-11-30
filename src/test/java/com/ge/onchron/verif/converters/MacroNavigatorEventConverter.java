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

import com.ge.onchron.verif.model.MacroNavigatorEvent;

public class MacroNavigatorEventConverter extends TableListConverter implements ParameterConverters.ParameterConverter {

    private static final String INDEX_COLUMN = "index";
    private static final String IS_FILTERED_COLUMN = "is_filtered";
    private static final String SELECTED_COLUMN = "selected";


    @Override
    public boolean accept( Type type ) {
        return type.getTypeName().equals( MacroNavigatorEvent.class.getTypeName() ) && MacroNavigatorEvent.class.isAssignableFrom( (Class) type );
    }

    @Override
    MacroNavigatorEvent convertSingleValue( String s ) {
        return (MacroNavigatorEvent) convertTableWithOneItem( s );
    }

    @Override
    MacroNavigatorEvent convertMultiValues( Map<String, String> map ) {
        if ( !map.containsKey( INDEX_COLUMN ) || !map.containsKey( IS_FILTERED_COLUMN ) || !map.containsKey( SELECTED_COLUMN ) ) {
            fail( "Missing mandatory column in the macro-navigator event table in the feature file: " + map );
        }

        MacroNavigatorEvent macroNavigatorEvent = new MacroNavigatorEvent( Integer.parseInt( map.get( INDEX_COLUMN ) ) );
        macroNavigatorEvent.isFiltered( Boolean.parseBoolean( map.get( IS_FILTERED_COLUMN ) ) );
        macroNavigatorEvent.setSelected( Boolean.parseBoolean( map.get( SELECTED_COLUMN ) ) );

        return macroNavigatorEvent;
    }

}
