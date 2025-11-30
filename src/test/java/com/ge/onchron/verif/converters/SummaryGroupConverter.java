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

import com.ge.onchron.verif.model.summaryCard.SummaryGroup;

public class SummaryGroupConverter extends TableListConverter implements ParameterConverters.ParameterConverter {

    private static final String COLUMN_INDEX_COLUMN = "column_index";
    private static final String GROUP_INDEX_COLUMN = "group_index_in_column";
    private static final String GROUP_NAME_COLUMN = "group_name";
    private static final String BADGE_TEXT_COLUMN = "badge_text";
    private static final String HIDDEN_COLUMN = "hidden";

    @Override
    public boolean accept( Type type ) {
        return type instanceof Class && SummaryGroup.class.isAssignableFrom( (Class) type );
    }

    @Override
    SummaryGroup convertSingleValue( String s ) {
        fail( "Currently, it is not applicable to convert a single value to a summary group" );
        return null;
    }

    @Override
    SummaryGroup convertMultiValues( Map<String, String> map ) {
        if ( !map.containsKey( COLUMN_INDEX_COLUMN ) || !map.containsKey( GROUP_INDEX_COLUMN ) || !map.containsKey( GROUP_NAME_COLUMN ) ) {
            fail( "Missing mandatory column in the SummaryGroup table in the feature file: " + map );
        }
        int columnIndex = Integer.parseInt( map.get( COLUMN_INDEX_COLUMN ) );
        int groupIndex = Integer.parseInt( map.get( GROUP_INDEX_COLUMN ) );
        String groupName = map.get( GROUP_NAME_COLUMN );
        if ( groupName.equals( "<NO_TITLE>" ) ) {  // in case of patient and info cards
            groupName = "";
        }

        // Optional params
        String groupBadge = "";
        if ( map.containsKey( BADGE_TEXT_COLUMN ) && !map.get( BADGE_TEXT_COLUMN ).equals( "<N/A>" ) ) {
            groupBadge = map.get( BADGE_TEXT_COLUMN );
        }
        boolean isHidden = false;
        if ( map.containsKey( HIDDEN_COLUMN ) ) {
            isHidden = map.get( HIDDEN_COLUMN ).equals( "yes" );
        }

        return new SummaryGroup( groupName, columnIndex, groupIndex, groupBadge, isHidden );
    }

}
