/*
 *  -GE CONFIDENTIAL-
 *  Type: Source Code
 *
 *  Copyright (c)  2022,  2022, GE Healthcare
 *  All Rights Reserved
 *
 *  This unpublished material is proprietary to GE Healthcare. The methods and
 *  techniques described herein are considered trade secrets and/or
 *  confidential. Reproduction or distribution, in whole or in part, is
 *  forbidden except by express written permission of GE Healthcare.
 */

package com.ge.onchron.verif.converters;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.ParameterConverters.ParameterConverter;

import com.ge.onchron.verif.converters.types.ColorString;

public class ColorStringConverter implements ParameterConverter {

    public static final Map<String, String> COLOR_HEX_MAP = new HashMap<>() {{
        put( "low severity notification yellow", "#f5e7a9" );
        put( "yellow", "#f5cc0e" );
        put( "light blue-gray", "#b6becc" );
        put( "blue", "#506ec4" );
        put( "brown", "#5e1d2f" );
        put( "green", "#4e7c78" );
        put( "red", "#bb395e" );
        put( "grey", "#7f8185" );
        put( "purple", "#7252bc" );
        put( "ai-blue", "#d7f4fe" );
    }};

    @Override
    public boolean accept( Type type ) {
        return type instanceof Class && ColorString.class.isAssignableFrom( (Class) type );
    }

    @Override
    public Object convertValue( String string, Type type ) {
        try {
            return new ColorString( string, COLOR_HEX_MAP.get( string ) );
        } catch ( Exception e ) {
            throw new ParameterConverters.ParameterConvertionFailed( "Failed to convert " + string + " to color code" );
        }
    }

    public static ColorString convertString( String string ) {
        return new ColorString( string, COLOR_HEX_MAP.get( string ) );
    }
}
