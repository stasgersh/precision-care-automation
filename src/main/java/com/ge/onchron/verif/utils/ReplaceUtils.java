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

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.serenitybdd.core.Serenity.hasASessionVariableCalled;
import static net.serenitybdd.core.Serenity.sessionVariableCalled;
import static net.serenitybdd.core.Serenity.setSessionVariable;

public class ReplaceUtils {

    private static final String DOB_VALUE_PATTERN = "MMM d, yyyy";
    private static final String ACTUAL_AGE_PLACEHOLDER = "<<ACTUAL_AGE>>";
    private static final String ACTUAL_AGE_FORMAT = " (" + ACTUAL_AGE_PLACEHOLDER + "y)";

    private static final String CREATE_LABEL_TIMESTAMP_PLACEHOLDER = "<<create_label_timestamp>>";
    private static final String PREVIOUSLY_CREATED_LABEL_TIMESTAMP_PLACEHOLDER = "<<previously_created_label_timestamp>>";

    public static String replaceCustomParameters( String text ) {
        if ( text.contains( CREATE_LABEL_TIMESTAMP_PLACEHOLDER ) ) {
            String timestamp = String.valueOf( System.currentTimeMillis() );
            setSessionVariable( CREATE_LABEL_TIMESTAMP_PLACEHOLDER ).to( timestamp );
            text = text.replace( CREATE_LABEL_TIMESTAMP_PLACEHOLDER, timestamp );
        }
        if ( text.contains( PREVIOUSLY_CREATED_LABEL_TIMESTAMP_PLACEHOLDER ) && hasASessionVariableCalled( CREATE_LABEL_TIMESTAMP_PLACEHOLDER ) ) {
            text = text.replace( PREVIOUSLY_CREATED_LABEL_TIMESTAMP_PLACEHOLDER, sessionVariableCalled( CREATE_LABEL_TIMESTAMP_PLACEHOLDER ) );
        }
        if ( text.contains( ACTUAL_AGE_FORMAT ) ) {
            String dob = StringUtils.substringBefore( text, ACTUAL_AGE_FORMAT );
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern( DOB_VALUE_PATTERN );
            LocalDate birthDate = LocalDate.parse( dob, formatter );
            int age = Period.between( birthDate, LocalDate.now() ).getYears();
            text = text.replace( ACTUAL_AGE_PLACEHOLDER, String.valueOf( age ) );
        }
        if ( text.contains( "<<" ) && text.contains( ">>" ) ) {
            Pattern pattern = Pattern.compile( "<<(.*?)>>" );
            Matcher matcher = pattern.matcher( text );
            while ( matcher.find() ) {
                String placeholder = matcher.group( 1 );
                String fullPlaceholder = "<<" + placeholder + ">>";
                String requiredText = replaceStoredPlaceholderText( placeholder );
                text = text.replace( fullPlaceholder, requiredText );
            }
        }
        return text;
    }

    public static String replaceStoredPlaceholderText( String text ) {
        if ( hasASessionVariableCalled( text ) ) {
            return sessionVariableCalled( text );
        }
        return text;
    }

    public static String replaceDatePlaceholders( String resourceString, String datePattern ) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern( datePattern );
        resourceString = resourceString.replace( "<TODAY>", LocalDate.now().format( dateFormatter ) );

        // Replace <X_DAYS_AGO>
        String regex = "<(\\d+)_DAYS_AGO>";
        Pattern pattern = Pattern.compile( regex );
        Matcher matcher = pattern.matcher( resourceString );
        while ( matcher.find() ) {
            String stringToReplace = matcher.group( 0 );
            int daysAgo = Integer.parseInt( matcher.group( 1 ) );
            String dateDaysAgo = LocalDate.now().minus( Period.ofDays( daysAgo ) ).format( dateFormatter );
            resourceString = resourceString.replace( stringToReplace, dateDaysAgo );
        }

        // Replace <X_DAYS_FROM_NOW>
        Pattern fromNowPattern = Pattern.compile( "<(\\d+)_DAYS_FROM_NOW>" );
        Matcher fromNowMatcher = fromNowPattern.matcher( resourceString );
        while ( fromNowMatcher.find() ) {
            String stringToReplace = fromNowMatcher.group( 0 );
            int daysFromNow = Integer.parseInt( fromNowMatcher.group( 1 ) );
            String dateDaysFromNow = LocalDate.now().plusDays( daysFromNow ).format( dateFormatter );
            resourceString = resourceString.replace( stringToReplace, dateDaysFromNow );
        }

        return resourceString;
    }

    public static String replaceIdPlaceholders( String resourceString ) {
        Pattern RESOURCE_ID_PATTERN = Pattern.compile( "--(.*?)--" );

        return RESOURCE_ID_PATTERN.matcher( resourceString ).replaceAll(
                id -> replaceIdPlaceholderWithRandomId( id.group( 1 ) )
        );
    }

    public static String replaceIdPlaceholderWithRandomId( String placeholder ) {
        if ( hasASessionVariableCalled( placeholder ) ) {
            return sessionVariableCalled( placeholder );
        }

        String randomId = UUID.randomUUID().toString();
        setSessionVariable( placeholder ).to( randomId );

        return randomId;
    }

    public static String replaceStoredId( String id ) {
        return id.startsWith( "--" ) ? replaceStoredPlaceholderText( id.split( "--" )[1] ) : id;
    }
}
