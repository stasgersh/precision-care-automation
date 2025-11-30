/*
 * -GEHC CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2023, 2023, GE HealthCare
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

import com.ge.onchron.verif.model.ClusterPillEvent;

import static com.ge.onchron.verif.TestSystemParameters.TIMELINE_AXIS_TOOLTIP_DATE_FORMAT;
import static com.ge.onchron.verif.utils.ReplaceUtils.replaceDatePlaceholders;

public class ClusterPillEventConverter extends TableListConverter implements ParameterConverters.ParameterConverter {

    private static final String EVENT_NAME_COLUMN = "event_name";
    private static final String DATE_ON_TOOLTIP_COLUMN = "date_on_tooltip";
    private static final String NA = "<N/A>";

    @Override
    public boolean accept( Type type ) {
        return type instanceof Class && ClusterPillEvent.class.isAssignableFrom( (Class) type );
    }

    @Override
    ClusterPillEvent convertSingleValue( String name ) {
        return new ClusterPillEvent( name );
    }

    @Override
    ClusterPillEvent convertMultiValues( Map<String, String> map ) {
        if ( !map.containsKey( EVENT_NAME_COLUMN ) ) {
            fail( "Missing mandatory column in the cluster pill event table in the feature file: " + map );
        }

        String eventName = map.get( EVENT_NAME_COLUMN );
        ClusterPillEvent event = new ClusterPillEvent( eventName );

        // optional column
        if ( map.containsKey( DATE_ON_TOOLTIP_COLUMN ) && !map.get( DATE_ON_TOOLTIP_COLUMN ).equals( NA ) ) {
            String initialDateOnTooltip = map.get( DATE_ON_TOOLTIP_COLUMN );
            String dateOnTooltip = replaceDatePlaceholders( initialDateOnTooltip, TIMELINE_AXIS_TOOLTIP_DATE_FORMAT );
            event.setDateOnTooltip( dateOnTooltip );
        }
        return event;
    }

}
