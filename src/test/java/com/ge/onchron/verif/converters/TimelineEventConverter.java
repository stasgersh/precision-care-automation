/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2022, 2022, GE Healthcare
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

import org.jbehave.core.steps.ParameterConverters;

import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.Badge;
import com.ge.onchron.verif.model.CheckboxModel;
import com.ge.onchron.verif.model.TimelineEvent;
import com.ge.onchron.verif.model.enums.EventType;

import static com.ge.onchron.verif.TestSystemParameters.TIMELINE_AXIS_TOOLTIP_DATE_FORMAT;
import static com.ge.onchron.verif.converters.converterUtils.BadgeConverterUtils.getBadgesFromTableCell;
import static com.ge.onchron.verif.utils.ReplaceUtils.replaceDatePlaceholders;

public class TimelineEventConverter extends TableListConverter implements ParameterConverters.ParameterConverter {

    private static final String EVENT_TYPE_COLUMN = "event_type";
    private static final String NAME_COLUMN = "name_on_label";
    private static final String TEXT_ON_EVENT_POINT = "text_on_event_point";
    private static final String DATE_ON_TIMELINE_AXIS = "date_on_timeline_axis";
    private static final String SWIMLANE_COLUMN = "swimlane";
    private static final String BADGES_COLUMN = "badges";
    private static final String EMPTY_STRING = "";

    @Override
    public boolean accept( Type type ) {
        return type instanceof Class && TimelineEvent.class.isAssignableFrom( (Class) type );
    }

    @Override
    CheckboxModel convertSingleValue( String s ) {
        fail( "Currently, it is not applicable to convert a single value to timeline event" );
        return null;
    }

    @Override
    TimelineEvent convertMultiValues( Map<String, String> map ) {
        if ( !map.containsKey( EVENT_TYPE_COLUMN ) || !map.containsKey( NAME_COLUMN ) ) {
            fail( "Missing mandatory column in the timeline event table in the feature file: " + map );
        }

        EventType eventType = EventType.valueOf( map.get( EVENT_TYPE_COLUMN ) );
        TimelineEvent event = new TimelineEvent( eventType );
        event.setNameLabel( map.get( NAME_COLUMN ) );

        // optional columns
        if ( map.containsKey( SWIMLANE_COLUMN ) && !map.get( SWIMLANE_COLUMN ).equals( "<N/A>" ) ) {
            event.setSwimlane( map.get( SWIMLANE_COLUMN ) );
        }
        if ( map.containsKey( TEXT_ON_EVENT_POINT ) && !map.get( TEXT_ON_EVENT_POINT ).equals( "<EMPTY>" ) ) {
            event.setTextOnEventPoint( map.get( TEXT_ON_EVENT_POINT ) );
        } else {   // set default value, because it is never null from the UI (it is a normal or an empty string)
            event.setTextOnEventPoint( EMPTY_STRING );
        }
        if ( map.containsKey( DATE_ON_TIMELINE_AXIS ) && !map.get( DATE_ON_TIMELINE_AXIS ).equals( "<N/A>" ) ) {
            String initialDateOnTimelineAxis = map.get( DATE_ON_TIMELINE_AXIS );
            if ( initialDateOnTimelineAxis.contains( "DAYS" ) ) {
                initialDateOnTimelineAxis = replaceDatePlaceholders( initialDateOnTimelineAxis, "MMM dd, yyyy" );
            }
            event.setDateOnTimelineAxis( initialDateOnTimelineAxis );
        }

        if ( map.containsKey( BADGES_COLUMN ) && !map.get( BADGES_COLUMN ).equals( "<N/A>" ) ) {
            String badgesData = map.get( BADGES_COLUMN );
            List<Badge> eventBadges = getBadgesFromTableCell( badgesData );
            event.setBadges( eventBadges );
        }
        return event;
    }

}
