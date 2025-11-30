/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2021, 2021, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.converters;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.jbehave.core.steps.ParameterConverters;

public class BooleanConverter implements ParameterConverters.ParameterConverter {

    private static final Map<String, Boolean> BOOLEAN_MAP = new HashMap<>() {{
        put( "is", true );
        put( "is not", false );
        put( "are", true );
        put( "are not", false );
        put( "disabled", false );
        put( "enabled", true );
        put( "have", true );
        put( "do not have", false );
        put( "has", true );
        put( "does not have", false );
        put( "was", true );
        put( "was not", false );
        put( "were", true );
        put( "were not", false );
        put( "to", true );
        put( "not to", false );
        put( "opens", true );
        put( "closes", false );
        put( "opened", true );
        put( "closed", false );
        put( "selects", true );
        put( "unselects", false );
        put( "optional", false );
        put( "mandatory", true );
        put( "explicit", true );
        put( "implicit", false);
    }};

    @Override
    public boolean accept( Type type ) {
        return type instanceof Class && Boolean.class.isAssignableFrom( (Class) type );
    }

    @Override
    public Object convertValue( String string, Type type ) {
        if ( BOOLEAN_MAP.get( string ) != null ) {
            return BOOLEAN_MAP.get( string );
        } else {
            throw new ParameterConverters.ParameterConvertionFailed( "Failed to convert " + string + " to boolean" );
        }
    }

}
