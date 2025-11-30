/*
 * -GE CONFIDENTIAL- or -GE HIGHLY CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2025, 2025, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE HealthCare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 * GE is a trademark of General Electric Company used under trademark license.
 */

package com.ge.onchron.verif.converters;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jbehave.core.steps.ParameterConverters.ParameterConverter;

import static org.junit.Assert.fail;

import com.ge.onchron.verif.converters.types.Bitrate;

public class BitrateConverter implements ParameterConverter {

    public static final Map<String, Integer> BITRATE_UNIT_MAP = new HashMap() {{
        put( "Mbps", 1_000 );
        put( "Mbit/s", 1_000 );
        put( "kbps", 1 );
        put( "kbit/s", 1 );
    }};

    @Override
    public boolean accept( Type type ) {
        return type instanceof Class && Bitrate.class.isAssignableFrom( (Class) type );
    }

    @Override
    public Object convertValue( String s, Type type ) {
        switch ( s.toUpperCase() ) {
            case "DEFAULT":
                return new Bitrate( Bitrate.DEFAULT );
            default:
                Pattern pattern = Pattern.compile( "^(\\d+) ?(.*)$" );
                Matcher matcher = pattern.matcher( s );
                if ( matcher.matches() && BITRATE_UNIT_MAP.get( matcher.group( 2 ) ) != null ) {
                    int kiloBitPerSecond = Integer.parseInt( matcher.group( 1 ) ) * BITRATE_UNIT_MAP.get( matcher.group( 2 ) );
                    return new Bitrate( kiloBitPerSecond );
                } else if ( matcher.matches() ) {
                    fail( STR."Could not recognise '\{matcher.group( 2 )}' as a valid bitrate unit." );
                } else {
                    fail( STR."Could not recognise '\{s}' as a valid number and bitrate unit combination or as a predefined option." );
                }
                return null;
        }
    }
}
