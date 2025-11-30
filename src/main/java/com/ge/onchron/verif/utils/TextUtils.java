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
package com.ge.onchron.verif.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger( TextUtils.class );

    public static String limitString( String input, int limit ) {
        if ( input == null || input.length() <= limit ) {
            return input;
        }
        int truncatedLength = input.length() - limit;
        String truncated = input.substring( 0, limit );
        return STR."\{truncated} ...(\{truncatedLength} charachters hidden)";
    }

    public static boolean textCompareWithKeywords( String text, String otherText ) {
        text = text.toLowerCase();
        otherText = otherText.toLowerCase();
        String prefix = "[keywords]:";
        List<String> keywords = null;
        String actual = null;
        // Can be false positive if both clpTexts start with the prefix, but it's not a normal scenario
        if ( text.startsWith( prefix ) ) {
            keywords = Arrays.stream( text.substring( prefix.length() ).split( "," ) ).map( String::strip ).toList();
            actual = otherText;
        } else if ( otherText.startsWith( prefix ) ) {
            keywords = Arrays.stream( otherText.substring( prefix.length() ).split( "," ) ).map( String::strip ).toList();
            actual = text;
        }

        if ( keywords == null ) {
            return Objects.equals( text, otherText );
        }

        List<String> missingKeywords = new ArrayList<>();
        for ( String keyword : keywords ) {
            if ( !actual.contains( keyword ) ) {
                missingKeywords.add( keyword );
            }
        }

        if ( !missingKeywords.isEmpty() ) {
            LOGGER.info( STR."Keywords not found in '\{actual}': \{String.join( ", ", missingKeywords )}" );
            return false;
        }
        
        return true;
    }

}
