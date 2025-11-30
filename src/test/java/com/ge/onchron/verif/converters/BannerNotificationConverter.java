/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2023, 2023, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE HealthCare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.converters;

import org.jbehave.core.steps.ParameterConverters;
import org.openqa.selenium.support.Color;

import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.BannerNotification;
import java.lang.reflect.Type;
import java.util.Map;

public class BannerNotificationConverter extends TableListConverter implements ParameterConverters.ParameterConverter {

    private static final String BANNER_MESSAGE_REGEX_COLUMN = "banner_message_regex";
    private static final String BANNER_MESSAGE_TEXT_COLUMN = "banner_message_text";
    private static final String BANNER_BUTTON_TEXT_COLUMN = "button_in_banner";
    private static final String BANNER_BACKGROUND_COLOR = "semantic_color";
    private static final String BANNER_BAR_SIZE = "bar_size";
    private static final String BANNER_BAR_PIXEL_SIZE = "bar_size(px)";

    @Override
    public boolean accept( Type type ) {
        return type instanceof Class && BannerNotification.class.isAssignableFrom( (Class) type );
    }

    @Override
    BannerNotification convertSingleValue( String s ) {
        return new BannerNotification( s );
    }

    @Override
    BannerNotification convertMultiValues( Map<String, String> map ) {
        if ( !map.containsKey( BANNER_MESSAGE_REGEX_COLUMN ) && !map.containsKey( BANNER_MESSAGE_TEXT_COLUMN ) ) {
            fail(STR."Missing mandatory column in the banner notification table in the feature file: \{map}");
        }
        BannerNotification bannerNotification;
        if ( map.containsKey( BANNER_MESSAGE_REGEX_COLUMN ) ) {
            bannerNotification = new BannerNotification( map.get( BANNER_MESSAGE_REGEX_COLUMN ) );
        } else {
            bannerNotification = new BannerNotification( map.get( BANNER_MESSAGE_TEXT_COLUMN ) );
        }

        // Optional columns
        if ( map.containsKey( BANNER_BUTTON_TEXT_COLUMN ) && !map.get( BANNER_BUTTON_TEXT_COLUMN ).equals( "N/A" ) ) {
            bannerNotification.setButtonText( map.get( BANNER_BUTTON_TEXT_COLUMN ) );
        }
        if ( map.containsKey( BANNER_BACKGROUND_COLOR) && !map.get( BANNER_BACKGROUND_COLOR).equals( "N/A" ) ) {
            bannerNotification.setBackgroundColor( Color.fromString( ColorStringConverter.convertString( map.get( BANNER_BACKGROUND_COLOR ) ).getHex() ) );
        }
        if ( map.containsKey( BANNER_BAR_SIZE ) && !map.get( BANNER_BAR_SIZE ).equals( "N/A" ) ) {
            bannerNotification.setBarSize( Integer.valueOf(map.get( BANNER_BAR_SIZE )));
        }
        if ( map.containsKey( BANNER_BAR_PIXEL_SIZE ) && !map.get( BANNER_BAR_PIXEL_SIZE ).equals( "N/A" ) ) {
            bannerNotification.setBarSize( Integer.valueOf(map.get( BANNER_BAR_PIXEL_SIZE )));
        }
        return bannerNotification;
    }

}
