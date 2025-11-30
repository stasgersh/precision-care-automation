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
import java.util.Map;

import org.jbehave.core.steps.ParameterConverters;

import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.NotificationPopup;

public class NotificationPopupConverter extends TableListConverter implements ParameterConverters.ParameterConverter {

    private static final String HEADER_COLUMN = "popup_header";
    private static final String CONTENT_MSG_COLUMN = "popup_content_message";
    private static final String CONTENT_EVENT_DATE_COLUMN = "popup_content_event_date";
    private static final String CONTENT_EVENT_NAME_COLUMN = "popup_content_event_name";

    @Override
    public boolean accept( Type type ) {
        return type instanceof Class && NotificationPopup.class.isAssignableFrom( (Class) type );
    }

    @Override
    NotificationPopup convertSingleValue( String s ) {
        return (NotificationPopup) convertTableWithOneItem( s );
    }

    @Override
    NotificationPopup convertMultiValues( Map<String, String> map ) {
        if ( !map.containsKey( HEADER_COLUMN ) || !map.containsKey( CONTENT_MSG_COLUMN ) ) {
            fail( "Missing mandatory column in the notification popup table in the feature file: " + map );
        }
        NotificationPopup notifPopup = new NotificationPopup( map.get( HEADER_COLUMN ), map.get( CONTENT_MSG_COLUMN ) );

        // optional columns
        notifPopup.setContentEventDate( map.getOrDefault( CONTENT_EVENT_DATE_COLUMN, null ) );
        notifPopup.setContentEventName( map.getOrDefault( CONTENT_EVENT_NAME_COLUMN, null ) );

        return notifPopup;
    }

}
