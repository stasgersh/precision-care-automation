/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2025, 2025, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE HealthCare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 */
package com.ge.onchron.verif.converters;

import java.lang.reflect.Type;
import java.util.Map;

import org.jbehave.core.steps.ParameterConverters;

import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.ViewModeModalConfiguration;

public class ViewModeModalConfigConverter extends TableListConverter implements ParameterConverters.ParameterConverter {

    private static final String VIEW_MODE = "view_mode";
    private static final String PROTOCOL = "protocol";
    private static final String TRIAL_ARM = "trial_arm";
    private static final String START_DATE = "start_date";

    @Override
    public boolean accept( Type type ) {
        return type instanceof Class && ViewModeModalConfiguration.class.isAssignableFrom( (Class) type );
    }

    @Override
    Object convertSingleValue( String s ) {
        fail( "Currently, it is not applicable to convert a single value" );
        return null;
    }

    @Override
    ViewModeModalConfiguration convertMultiValues( Map<String, String> map ) {
        if ( !map.containsKey( VIEW_MODE ) || !map.containsKey( PROTOCOL ) || !map.containsKey( START_DATE ) ) {
            fail( STR."Missing mandatory column in the View mode modal configuration table in the feature file: \{map}" );
        }
        ViewModeModalConfiguration viewModeModalConfiguration = new ViewModeModalConfiguration();

        viewModeModalConfiguration.setSelectedViewMode( map.get( VIEW_MODE ) );
        viewModeModalConfiguration.setProtocol( map.get( PROTOCOL ) );
        viewModeModalConfiguration.setTrialArm( map.get( TRIAL_ARM ) );
        viewModeModalConfiguration.setProtocolStartDate( map.get( START_DATE ) );

        return viewModeModalConfiguration;
    }

}
