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

import com.ge.onchron.verif.model.summaryCard.SummaryCard;

public class SummaryCardConverter extends TableListConverter implements ParameterConverters.ParameterConverter {

    private static final String GROUP_NAME_COLUMN = "group_name";
    private static final String CARD_INDEX_IN_GROUP_COLUMN = "card_index_in_group";

    @Override
    public boolean accept( Type type ) {
        return type instanceof Class && SummaryCard.class.isAssignableFrom( (Class) type );
    }

    @Override
    SummaryCard convertSingleValue( String s ) {
        fail( "Currently, it is not applicable to convert a single value to a summary card" );
        return null;
    }

    @Override
    SummaryCard convertMultiValues( Map<String, String> map ) {
        if ( !map.containsKey( GROUP_NAME_COLUMN ) || !map.containsKey( CARD_INDEX_IN_GROUP_COLUMN ) ) {
            fail( "Missing mandatory column in the SummaryCardItem table in the feature file: " + map );
        }
        SummaryCard summaryCard = new SummaryCard();

        String groupName = map.get( GROUP_NAME_COLUMN );
        summaryCard.setGroup( groupName );

        String cardIndex = map.get( CARD_INDEX_IN_GROUP_COLUMN );
        summaryCard.setCardIndexInGroup( Integer.parseInt( cardIndex ) );

        return summaryCard;
    }

}
