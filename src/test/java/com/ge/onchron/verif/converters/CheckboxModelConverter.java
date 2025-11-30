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
import java.util.Map;

import org.jbehave.core.steps.ParameterConverters;

import com.ge.onchron.verif.model.CheckboxModel;

import static org.junit.Assert.fail;

public class CheckboxModelConverter extends TableListConverter implements ParameterConverters.ParameterConverter {

    private static final String CHECKBOX_NAME_COLUMN = "checkbox_name";
    private static final String SELECTED_COLUMN = "selected";
    private static final String ENABLED_COLUMN = "enabled";

    @Override
    public boolean accept( Type type ) {
        return type instanceof Class && CheckboxModel.class.isAssignableFrom( (Class) type );
    }

    @Override
    CheckboxModel convertSingleValue( String s ) {
        fail( "Currently, it is not applicable to convert a single value to checkbox model" );
        return null;
    }

    @Override
    CheckboxModel convertMultiValues( Map<String, String> map ) {
        if ( !map.containsKey( CHECKBOX_NAME_COLUMN ) || !map.containsKey( SELECTED_COLUMN ) ) {
            fail( "Missing mandatory column in the checkbox table in the feature file: " + map );
        }
        CheckboxModel checkboxModel = new CheckboxModel(
                map.get( CHECKBOX_NAME_COLUMN ),
                Boolean.parseBoolean( map.get( SELECTED_COLUMN ) ) );

        // optional column
        if ( map.containsKey( ENABLED_COLUMN ) ) {
            checkboxModel.setEnabled( Boolean.parseBoolean( map.get( ENABLED_COLUMN ) ) );
        } else {
            // If checkbox state (enabled/disabled) is not provided in the feature file, then it is enabled by default
            checkboxModel.setEnabled( true );
        }
        return checkboxModel;
    }

}
