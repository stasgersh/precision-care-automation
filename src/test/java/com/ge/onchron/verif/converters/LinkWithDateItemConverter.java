/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2023, 2023, GE Healthcare
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

import com.ge.onchron.verif.model.detailedDataItems.LinkWithDateItem;

public class LinkWithDateItemConverter extends TableListConverter implements ParameterConverters.ParameterConverter {

    private static final String LINK_TEXT_COLUMN = "link_text";
    private static final String DATE_COLUMN = "date";

    @Override
    public boolean accept( Type type ) {
        return type.getTypeName().equals( LinkWithDateItem.class.getTypeName() ) && LinkWithDateItem.class.isAssignableFrom( (Class) type );
    }

    @Override
    LinkWithDateItem convertSingleValue( String s ) {
        return (LinkWithDateItem) convertTableWithOneItem( s );
    }

    @Override
    LinkWithDateItem convertMultiValues( Map<String, String> map ) {
        if ( !map.containsKey( LINK_TEXT_COLUMN ) || !map.containsKey( DATE_COLUMN ) ) {
            fail( "Missing mandatory column in detailed data table in the feature file: " + map );
        }
        String linkName = map.get( LINK_TEXT_COLUMN );
        String date = map.get( DATE_COLUMN );
        return new LinkWithDateItem( linkName, date );
    }

}
