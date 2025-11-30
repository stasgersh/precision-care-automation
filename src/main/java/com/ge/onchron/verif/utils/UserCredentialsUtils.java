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
package com.ge.onchron.verif.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ge.onchron.verif.model.UserCredentials;

import static com.ge.onchron.verif.TestSystemParameters.ONCO_DEFAULT_USER_ALIAS;
import static com.ge.onchron.verif.TestSystemParameters.STORED_USERS;
import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;
import static com.ge.onchron.verif.utils.FileUtils.readFromFile;
import static com.ge.onchron.verif.utils.Utils.storeMapSessionVariable;
import static net.serenitybdd.core.Serenity.hasASessionVariableCalled;
import static net.serenitybdd.core.Serenity.sessionVariableCalled;

public class UserCredentialsUtils {

    public static final String USER_DATA_CONFIG_FILE_PATH_ENV_VAR = "user.data.config.file.path";

    public static UserCredentials getDefaultUser() {
        return getOrInitUserCredentials( ONCO_DEFAULT_USER_ALIAS );
    }

    public static UserCredentials getOrInitUserCredentials( String userAlias ) {
        UserCredentials user = null;
        if ( hasASessionVariableCalled( STORED_USERS ) ) {
            Map<String, UserCredentials> storedUsers = sessionVariableCalled( STORED_USERS );
            if ( storedUsers.containsKey( userAlias ) ) {
                user = storedUsers.get( userAlias );
            }
        }
        if ( user == null ) {
            user = initUserCredentials( userAlias );
        }
        return user;
    }

    public static UserCredentials initUserCredentials( String userAlias ) {
        List<UserCredentials> users = initAllUserCredentials();
        return users.stream()
                    .filter( u -> u.getAlias().equals( userAlias ) )
                    .findFirst()
                    .orElseThrow( () -> new RuntimeException( "User was not found in the config file: " + userAlias ) );
    }

    public static List<UserCredentials> getTestUsers() {
        return initAllUserCredentials();
    }

    private static List<UserCredentials> initAllUserCredentials() {
        List<UserCredentials> baseUsers = getTestUsers(System.getenv("USER_CONFIG"));
        if (baseUsers == null) {
            baseUsers = getTestUsersFromFile();
        }
        List<UserCredentials> users = new ArrayList<>();
        baseUsers.forEach( user -> {
            // Check overwritten user parameters in environment variables
            String overwrittenUsername = getSystemParameter( "user." + user.getAlias() + ".name" );
            if ( overwrittenUsername != null ) {
                user.setUsername( overwrittenUsername );
            }
            String overwrittenPassword = getSystemParameter( "user." + user.getAlias() + ".psw" );
            if ( overwrittenUsername != null ) {
                user.setPassword( overwrittenPassword );
            }
            String overwrittenGivenName = getSystemParameter( "user." + user.getAlias() + ".givenName" );
            if ( overwrittenGivenName != null ) {
                user.setGivenName( overwrittenGivenName );
            }
            String overwrittenFamilyName = getSystemParameter( "user." + user.getAlias() + ".familyName" );
            if ( overwrittenFamilyName != null ) {
                user.setFamilyName( overwrittenFamilyName );
            }
            storeMapSessionVariable( STORED_USERS, user.getAlias(), user );
            users.add( user );
        } );
        return users;
    }

    private static List<UserCredentials> getTestUsersFromFile() {
        String usersJsonList = getTestUsersJson();
        ObjectMapper mapper = new ObjectMapper();
        List<UserCredentials> users = new ArrayList<>();
        try {
            users = mapper.readValue( usersJsonList, new TypeReference<List<UserCredentials>>() {
            } );

        } catch ( JsonProcessingException e ) {
            throw new RuntimeException( "Error parsing users config file: " + e );
        }
        return users;
    }
    private static List<UserCredentials> getTestUsers( String content ) {
        ObjectMapper mapper = new ObjectMapper();
        List<UserCredentials> users = new ArrayList<>();
        try {
            users = mapper.readValue( content, new TypeReference<List<UserCredentials>>() {
            } );

        } catch ( Exception e ) {
            return null;
        }
        if(users.size() < 3)
            return null;
        return users;
    }

    public static String getTestUsersJson() {
        return readFromFile( getSystemParameter( USER_DATA_CONFIG_FILE_PATH_ENV_VAR ) );
    }

}
