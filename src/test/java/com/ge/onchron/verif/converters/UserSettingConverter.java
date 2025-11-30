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

import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.UserSetting;

public class UserSettingConverter extends TableListConverter implements ParameterConverters.ParameterConverter {

    private static final String SETTINGS_TITLE_COLUMN = "settings_title";
    private static final String SETTING_NAME_COLUMN = "setting_name";
    private static final String SETTING_VALUE_COLUMN = "setting_value";


    @Override
    public boolean accept( Type type ) {
        return type instanceof Class && UserSetting.class.isAssignableFrom( (Class) type );
    }

    @Override
    UserSetting convertSingleValue( String s ) {
        return (UserSetting) convertTableWithOneItem( s );
    }

    @Override
    UserSetting convertMultiValues( Map<String, String> map ) {
        if ( !map.containsKey( SETTINGS_TITLE_COLUMN ) ) {
            fail( "Missing mandatory column in the settings table in the feature file: " + map );
        }
        UserSetting settings = new UserSetting();
        settings.setTitle( map.get( SETTINGS_TITLE_COLUMN ) );
        settings.setSettingName( map.getOrDefault( SETTING_NAME_COLUMN, "" ) );
        settings.setSettingValue( map.getOrDefault( SETTING_VALUE_COLUMN, null ) );

        return settings;
    }

}
