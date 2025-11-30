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

import com.ge.onchron.verif.model.EmptyStateMessage;

public class EmptyStateMessageConverter extends TableListConverter implements ParameterConverters.ParameterConverter {

    private static final String TITLE_COLUMN = "title";
    private static final String MESSAGE_COLUMN = "message";
    private static final String BUTTON_COLUMN = "button";

    @Override
    public boolean accept( Type type ) {
        return type instanceof Class && EmptyStateMessage.class.isAssignableFrom( (Class) type );
    }

    @Override
    EmptyStateMessage convertSingleValue( String s ) {
        return (EmptyStateMessage) convertTableWithOneItem( s );
    }

    @Override
    EmptyStateMessage convertMultiValues( Map<String, String> map ) {
        if ( !map.containsKey( TITLE_COLUMN ) || !map.containsKey( MESSAGE_COLUMN ) ) {
            fail( "Missing mandatory column in the notification popup table in the feature file: " + map );
        }
        EmptyStateMessage emptyStateMessage = new EmptyStateMessage( map.get( TITLE_COLUMN ), map.get( MESSAGE_COLUMN ) );
        // optional columns
        emptyStateMessage.setButtonText( map.getOrDefault( BUTTON_COLUMN, "" ) );

        return emptyStateMessage;
    }

}
