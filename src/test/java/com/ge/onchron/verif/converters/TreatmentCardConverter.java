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

import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.steps.ParameterConverters;

import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.treatmentAndResponse.TreatmentCard;

public class TreatmentCardConverter implements ParameterConverters.ParameterConverter {

    private static final String DATA_TYPE_COLUMN = "data_type";
    private static final String DATA_COLUMN = "data";

    @Override
    public boolean accept( Type type ) {
        return type instanceof Class && TreatmentCard.class.isAssignableFrom( (Class) type );
    }

    @Override
    public Object convertValue( String s, Type type ) {
        ExamplesTable table = new ExamplesTable( s );
        TreatmentCard treatmentCard = new TreatmentCard();

        for ( Map<String, String> map : table.getRows() ) {
            String dataType = map.get( DATA_TYPE_COLUMN );
            String data = map.get( DATA_COLUMN );
            switch ( dataType ) {
                case "CARD_TITLE":
                    treatmentCard.setCardTitle( data );
                    break;
                case "NORMAL_TEXT":
                    treatmentCard.setEventName( data );
                    break;
                case "DATE":
                    treatmentCard.setDate( data );
                    break;
                case "EMPTY_TEXT":
                    treatmentCard.setEmptyText( data );
                    break;
                default:
                    fail( STR."Not expected Treatment card table column: \{data}" );
            }
        }
        return treatmentCard;
    }
}
