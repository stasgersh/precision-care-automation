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

import com.ge.onchron.verif.model.detailedDataItems.CommentItem;

public class CommentItemConverter extends TableListConverter implements ParameterConverters.ParameterConverter {

    private static final String AUTHOR_COLUMN = "author (firstname lastname)";
    private static final String DATE_TIME_COLUMN = "date_and_time";
    private static final String CONTENT_COLUMN = "content";
    private static final String BADGE_COLUMN = "badge";
    private static final String HAS_TIMELINE_LINK_COLUMN = "has_view_on_timeline_link";
    private static final String IS_COLLAPSED_COLUMN = "is_collapsed";
    private static final String EXPAND_BUTTON_TEXT = "expand_button_text";
    private static final String NA_PLACEHOLDER = "<N/A>";

    @Override
    public boolean accept( Type type ) {
        return type.getTypeName().equals( CommentItem.class.getTypeName() ) && CommentItem.class.isAssignableFrom( (Class) type );
    }

    @Override
    CommentItem convertSingleValue( String s ) {
        return (CommentItem) convertTableWithOneItem( s );
    }

    @Override
    CommentItem convertMultiValues( Map<String, String> map ) {
        if ( !map.containsKey( AUTHOR_COLUMN ) || !map.containsKey( DATE_TIME_COLUMN ) || !map.containsKey( CONTENT_COLUMN ) ) {
            fail( "Missing mandatory column in the timeline event table in the feature file: " + map );
        }
        CommentItem comment = new CommentItem( map.get( CONTENT_COLUMN ) );
        comment.setAuthor( map.get( AUTHOR_COLUMN ) );
        comment.setDateAndTime( map.get( DATE_TIME_COLUMN ) );

        // Optional columns
        if ( map.containsKey( BADGE_COLUMN ) && !map.get( BADGE_COLUMN ).equals( NA_PLACEHOLDER ) ) {
            comment.setFooterBadge( map.get( BADGE_COLUMN ) );
        }
        if ( map.containsKey( HAS_TIMELINE_LINK_COLUMN ) && !map.get( HAS_TIMELINE_LINK_COLUMN ).equals( NA_PLACEHOLDER ) ) {
            boolean hasViewOnTimelineLink = "yes".equals( map.get( HAS_TIMELINE_LINK_COLUMN ) );
            comment.hasFooterTimelineLink( hasViewOnTimelineLink );
        }
        if ( map.containsKey( IS_COLLAPSED_COLUMN ) && !map.get( IS_COLLAPSED_COLUMN ).equals( NA_PLACEHOLDER ) ) {
            comment.setCollapsed( "yes".equals( map.get( IS_COLLAPSED_COLUMN ) ) );
        }
        if ( map.containsKey( EXPAND_BUTTON_TEXT ) && !map.get( EXPAND_BUTTON_TEXT ).equals( NA_PLACEHOLDER ) ) {
            comment.setExpandToggleText( map.get( EXPAND_BUTTON_TEXT ) );
        }

        return comment;
    }

}
