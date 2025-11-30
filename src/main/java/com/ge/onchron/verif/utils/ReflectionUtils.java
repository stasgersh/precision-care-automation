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
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

public class ReflectionUtils {

    public ReflectionUtils() {
    }

    public static <U> List<Class<? extends U>> getSubTypes( String packageName, Class<U> clazz ) {
        Set<Class<? extends U>> setOfClasses = new Reflections( packageName ).getSubTypesOf( clazz );
        return new ArrayList<>( setOfClasses );
    }

    public static <U> List<U> createInstanceFromClassesWithDefaultConstructor( List<Class<? extends U>> classes ) {
        List<U> returnList = new ArrayList<>();
        for ( Class<? extends U> clazz : classes ) {
            try {
                returnList.add( clazz.getDeclaredConstructor().newInstance() );
            } catch ( Exception e ) {
                throw new RuntimeException( "An error occurred while instantiating class: " + clazz.getName() + " " + e.getMessage() );
            }
        }
        return returnList;
    }

}
