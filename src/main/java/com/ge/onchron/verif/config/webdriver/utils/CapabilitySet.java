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
package com.ge.onchron.verif.config.webdriver.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.webdriver.CapabilityValue;

/**
 * Created based on Serenity class: 'net.thucydides.core.webdriver.CapabilitySet'
 * <p>
 * A set of user-defined capabilities to be used to configure the WebDriver driver.
 * Capabilities should be passed in as a semi-colon-separated list of key:value pairs, e.g.
 * "build:build-1234; max-duration:300; single-window:true; tags:[tag1,tag2,tag3]"
 */
public class CapabilitySet {
    private final EnvironmentVariables environmentVariables;
    private static final CharMatcher CAPABILITY_SEPARATOR = CharMatcher.anyOf( ";" );

    public CapabilitySet( EnvironmentVariables environmentVariables ) {
        this.environmentVariables = environmentVariables.copy();
    }

    public Map<String, Object> getCapabilitiesFromProperty( String systemProperty ) {
        Map<String, Object> capabilitiesMap = new HashMap<>();

        String specifiedCapabilities = environmentVariables.getProperty( systemProperty );
        if ( StringUtils.isNotEmpty( specifiedCapabilities ) ) {
            Iterable<String> capabilityValues = Splitter.on( CAPABILITY_SEPARATOR ).trimResults().split( specifiedCapabilities );
            capabilitiesMap = addCapabilityMapValues( capabilityValues );
        }
        return capabilitiesMap;
    }

    private Map<String, Object> addCapabilityMapValues( Iterable<String> capabilityValues ) {
        Map<String, Object> capabilitiesMap = new HashMap<>();
        for ( String capability : capabilityValues ) {
            CapabilityToken token = new CapabilityToken( capability );
            if ( token.isDefined() ) {
                capabilitiesMap.put( token.getName(), CapabilityValue.asObject( token.getValue() ) );
            }
        }
        return capabilitiesMap;
    }

    private static class CapabilityToken {
        private final String name;
        private final String value;

        private CapabilityToken( String capability ) {

            int colonIndex = capability.lastIndexOf( ":" );
            if ( colonIndex >= 0 ) {
                boolean colonIndexFound = false;
                int lastIndex = capability.length();
                while ( !colonIndexFound ) {
                    int lastColonIndex = capability.lastIndexOf( ":", lastIndex );
                    if ( lastColonIndex > 0 ) {
                        colonIndex = lastColonIndex;
                        // Check if colon is part of file path or JSON object
                        if ( ( capability.length() >= colonIndex + 1 )
                                && ( isFollowedByPathSeparator( capability, colonIndex ) || isWithinJsonObject( capability, colonIndex ) ) ) {

                            if ( lastIndex == colonIndex - 1 ) {
                                colonIndexFound = true;
                                //been here before, only single colon followed by a path separator found
                                break;
                            }
                            lastIndex = colonIndex - 1;
                        } else {
                            colonIndexFound = true;
                        }
                    } else {
                        colonIndexFound = true;
                    }
                }
                name = capability.substring( 0, colonIndex );
                value = capability.substring( colonIndex + 1 );
            } else {
                name = capability;
                value = null;
            }
        }

        private boolean isFollowedByPathSeparator( String capability, int colonIndex ) {
            return ( capability.charAt( colonIndex + 1 ) == '\\' ) || ( capability.charAt( colonIndex + 1 ) == '/' );
        }

        /**
         * Check if colon is between opening and closing curly brackets
         */
        private boolean isWithinJsonObject( String capability, int colonIndex ) {
            int openingBracketIndex = capability.indexOf( '{' );
            int closingBracketIndex = capability.indexOf( '}' );

            return ( openingBracketIndex > 0 ) && ( openingBracketIndex < colonIndex ) && ( colonIndex < closingBracketIndex );
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        public boolean isDefined() {
            return ( StringUtils.isNotEmpty( name ) && StringUtils.isNotEmpty( value ) );
        }
    }
}
