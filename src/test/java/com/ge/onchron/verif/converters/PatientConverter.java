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

import com.ge.onchron.verif.model.Patient;

import static org.junit.Assert.fail;

public class PatientConverter extends TableListConverter implements ParameterConverters.ParameterConverter {

    private static final String NAME_COLUMN = "name";
    private static final String MRN_COLUMN = "mrn";

    @Override
    public boolean accept( Type type ) {
        return type instanceof Class && Patient.class.isAssignableFrom( (Class) type );
    }

    @Override
    Patient convertSingleValue( String s ) {
        return new Patient( s );
    }

    @Override
    Patient convertMultiValues( Map<String, String> map ) {
        if ( !map.containsKey( NAME_COLUMN ) ) {
            fail( "Missing mandatory column in the patient table in the feature file: " + map );
        }
        Patient patient = new Patient( map.get( NAME_COLUMN ) );

        // optional column
        if ( map.containsKey( MRN_COLUMN ) ) {
            patient.setPID( map.get( MRN_COLUMN ) );
        }
        return patient;
    }

}
