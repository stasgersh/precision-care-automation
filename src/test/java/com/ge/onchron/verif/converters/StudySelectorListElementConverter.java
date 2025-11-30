/*
 * -GEHC CONFIDENTIAL-
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

import com.ge.onchron.verif.model.treatmentAndResponse.StudySelectorListElement;

public class StudySelectorListElementConverter extends TableListConverter implements ParameterConverters.ParameterConverter {

    private static final String NAME_COLUMN = "name";
    private static final String DATE_COLUMN = "date";
    private static final String SELECTED_COLUMN = "selected";
    private static final String DISABLED_COLUMN = "disabled";

    @Override
    public boolean accept( Type type ) {
        return type instanceof Class && StudySelectorListElement.class.isAssignableFrom( (Class) type );
    }

    @Override
    Object convertSingleValue( String s ) {
        return (StudySelectorListElement) convertTableWithOneItem( s );
    }

    @Override
    Object convertMultiValues( Map<String, String> map ) {
        StudySelectorListElement studySelectorListElement = new StudySelectorListElement();
        studySelectorListElement.setName( map.get( NAME_COLUMN ) );
        studySelectorListElement.setDate( map.get( DATE_COLUMN ) );
        studySelectorListElement.setSelected( "yes".equals( map.get( SELECTED_COLUMN ) ) );
        studySelectorListElement.setDisabled( "yes".equals( map.get( DISABLED_COLUMN ) ) );
        return studySelectorListElement;
    }
}
