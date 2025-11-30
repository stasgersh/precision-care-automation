/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2024, 2024, GE HealthCare
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

import com.ge.onchron.verif.model.AITooltipSection;


public class PsTooltipConverter extends TableListConverter implements ParameterConverters.ParameterConverter {

    private static final String REPORT_TITLE_COLUMN = "report_title";
    private static final String DATE_COLUMN = "date";
    private static final String AUTHOR_COLUMN = "author";
    private static final String STATUS_COLUMN = "status";

    @Override
    public boolean accept( Type type ) {
        return type.getTypeName().equals( AITooltipSection.class.getTypeName() ) && AITooltipSection.class.isAssignableFrom( (Class) type );
    }

    @Override
    AITooltipSection convertSingleValue( String s ) {
        return (AITooltipSection) convertTableWithOneItem( s );
    }

    @Override
    AITooltipSection convertMultiValues( Map<String, String> map ) {
        if ( !map.containsKey( REPORT_TITLE_COLUMN ) || !map.containsKey( DATE_COLUMN ) ||
                !map.containsKey( AUTHOR_COLUMN ) || !map.containsKey( STATUS_COLUMN ) ) {
            fail( String.format( "Missing mandatory column in PS Tooltip table in the feature file: %s", map ) );
        }
        AITooltipSection psTooltip = new AITooltipSection();
        psTooltip.setTitle( map.get( REPORT_TITLE_COLUMN ) );
        psTooltip.setDate( map.get( DATE_COLUMN ) );
        psTooltip.setAuthor( map.get( AUTHOR_COLUMN ) );
        psTooltip.setStatus( map.get( STATUS_COLUMN ) );
        return psTooltip;
    }

}
