/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2023, 2023, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

import com.ge.onchron.verif.model.UserCredentials;
import com.ge.onchron.verif.model.detailedDataItems.CommentItem;

import static com.ge.onchron.verif.TestSystemParameters.COMMENT_DATE_TIME_FORMAT;
import static com.ge.onchron.verif.utils.UserCredentialsUtils.getOrInitUserCredentials;

public class CommentUtils {

    private static final String CURRENT_DATE_AND_TIME_PLACEHOLDER = "<CURRENT_DATE_AND_TIME>";

    public static CommentItem changeParametersInComment( CommentItem comment ) {
        // 1. Change user parameter when needed: if it is between "[" and "]" (e.g. [DEFAULT-TEST-USER])
        String author = comment.getAuthor();
        if ( author.startsWith( "[" ) && author.endsWith( "]" ) ) {
            author = StringUtils.substringBetween( author, "[", "]" );
            UserCredentials userCredentials = getOrInitUserCredentials( author );
            comment.setAuthor( String.format( "%s %s", userCredentials.getGivenName(), userCredentials.getFamilyName() ) );
        }
        // 2. Change expected date and time if needed
        String expDateAndTime = comment.getDateAndTime();
        if ( expDateAndTime.equals( CURRENT_DATE_AND_TIME_PLACEHOLDER ) ) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern( COMMENT_DATE_TIME_FORMAT );
            String replacedExpDateAndTime = LocalDateTime.now().format( formatter );
            comment.setDateAndTime( replacedExpDateAndTime );
        }
        return comment;
    }

}
