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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.Badge;
import com.ge.onchron.verif.model.enums.BadgeType;

import static com.ge.onchron.verif.converters.ColorStringConverter.COLOR_HEX_MAP;
import static com.ge.onchron.verif.model.Badge.SAVED_BADGE_COLOR;
import static com.ge.onchron.verif.utils.ReplaceUtils.replaceStoredPlaceholderText;

public class BadgeConverter extends TableListConverter implements ParameterConverters.ParameterConverter {

    private static final String BADGE_TYPE_COLUMN = "badge_type";
    private static final String BADGE_TEXT_COLUMN = "badge_text";
    private static final String BADGE_COLOR_COLUMN = "badge_color";

    @Override
    public boolean accept( Type type ) {
        return type instanceof Class && Badge.class.isAssignableFrom( (Class) type );
    }

    @Override
    Badge convertSingleValue( String s ) {
        fail( "Currently, it is not applicable to convert a single value to a badge" );
        return null;
    }

    @Override
    Badge convertMultiValues( Map<String, String> map ) {
        if ( !map.containsKey( BADGE_TYPE_COLUMN ) || !map.containsKey( BADGE_TEXT_COLUMN ) ) {
            fail( "Missing mandatory column in the timeline event table in the feature file: " + map );
        }
        Badge badge = new Badge();
        badge.setBadgeType( BadgeType.valueOf( map.get( BADGE_TYPE_COLUMN ) ) );
        badge.setText( map.get( BADGE_TEXT_COLUMN ) );

        // Optional column
        if ( map.containsKey( BADGE_COLOR_COLUMN ) ) {
            String badgeColor = map.get( BADGE_COLOR_COLUMN );
            switch ( badgeColor ) {
                case SAVED_BADGE_COLOR:
                    badge.setColor( SAVED_BADGE_COLOR );
                    break;
                case "<N/A>": // No need to set and check label color
                    break;
                default:
                    String colorName = COLOR_HEX_MAP.get( badgeColor );
                    if ( colorName != null ) {
                        badge.setColor( colorName );
                    }
                    break;
            }
        }

        return badge;
    }

}
