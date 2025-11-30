/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2023, 2023, GE HealthCare
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

import com.ge.onchron.verif.model.CheckboxModel;
import com.ge.onchron.verif.model.FilterDataCheckbox;

import static com.ge.onchron.verif.utils.ReplaceUtils.replaceStoredPlaceholderText;

public class FilterDataCheckboxConverter extends TableListConverter implements ParameterConverters.ParameterConverter {

    private static final String FILTER_GROUP_NAME_COLUMN = "filter_group";
    private static final String CHECKBOX_NAME_COLUMN = "checkbox_name";
    private static final String SELECTED_COLUMN = "selected";
    private static final String ENABLED_COLUMN = "enabled";

    @Override
    public boolean accept( Type type ) {
        return type instanceof Class && FilterDataCheckbox.class.isAssignableFrom( (Class) type );
    }

    @Override
    CheckboxModel convertSingleValue( String s ) {
        fail( "Currently, it is not applicable to convert a single value to checkbox model" );
        return null;
    }

    @Override
    FilterDataCheckbox convertMultiValues( Map<String, String> map ) {
        if ( !map.containsKey( FILTER_GROUP_NAME_COLUMN ) || !map.containsKey( CHECKBOX_NAME_COLUMN ) || !map.containsKey( SELECTED_COLUMN ) ) {
            fail( "Missing mandatory column in the checkbox table in the feature file: " + map );
        }

        CheckboxModel checkboxModel = new CheckboxModel(
                replaceStoredPlaceholderText( map.get( CHECKBOX_NAME_COLUMN ) ),
                Boolean.parseBoolean( map.get( SELECTED_COLUMN ) ) );

        FilterDataCheckbox filterDataCheckbox = new FilterDataCheckbox(
                map.get( FILTER_GROUP_NAME_COLUMN ),
                checkboxModel );

        // optional column
        if ( map.containsKey( ENABLED_COLUMN ) ) {
            filterDataCheckbox.getCheckboxModel().setEnabled( Boolean.parseBoolean( map.get( ENABLED_COLUMN ) ) );
        } else {
            // If checkbox state (enabled/disabled) is not provided in the feature file, then it is enabled by default
            filterDataCheckbox.getCheckboxModel().setEnabled( true );
        }
        return filterDataCheckbox;
    }

}
