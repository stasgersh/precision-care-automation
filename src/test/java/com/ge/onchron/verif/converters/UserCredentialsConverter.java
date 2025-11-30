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

import org.jbehave.core.steps.ParameterConverters;

import com.ge.onchron.verif.model.UserCredentials;

import static com.ge.onchron.verif.utils.UserCredentialsUtils.getOrInitUserCredentials;

public class UserCredentialsConverter implements ParameterConverters.ParameterConverter {

    @Override
    public boolean accept( Type type ) {
        return type instanceof Class && UserCredentials.class.isAssignableFrom( (Class) type );
    }

    @Override
    public Object convertValue( String userAlias, Type type ) {
        return getOrInitUserCredentials( userAlias );
    }

}
