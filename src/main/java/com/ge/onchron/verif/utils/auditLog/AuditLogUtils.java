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
package com.ge.onchron.verif.utils.auditLog;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static com.ge.onchron.verif.TestSystemParameters.AUDIT_LOG_TIME_OFFSET_IN_MINUTES;
import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;

public class AuditLogUtils {

    public static boolean isDateAndTimeInRange( String observedDate, long startTimeInMillis, String format ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( format );
        LocalDateTime observedDateTime = LocalDateTime.parse( observedDate, formatter );
        LocalDateTime startDateTime = LocalDateTime.ofInstant( Instant.ofEpochMilli( startTimeInMillis ), ZoneId.of( "UTC" ) );
        long backwardIntervalInMins = Long.parseLong( (getSystemParameter( AUDIT_LOG_TIME_OFFSET_IN_MINUTES )) );
        if ( observedDateTime.isAfter( startDateTime.minusMinutes( backwardIntervalInMins ) ) ) {
            return true;
        }
        return false;
    }

}
