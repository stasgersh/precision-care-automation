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

import java.io.File;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.jbehave.core.steps.ParameterConverters;

import com.ge.onchron.verif.model.AuditLogItem;

import static com.ge.onchron.verif.TestSystemParameters.TEST_DATA_CLASSPATH;
import static org.junit.Assert.fail;

public class AuditLogItemConverter extends TableListConverter implements ParameterConverters.ParameterConverter {

    private static final String FILENAME_KEY = "expected_audit_log";
    private static final String IGNORED_ELEMENTS_KEY = "ignored_elements";

    private static final String AUDIT_LOG_FILE_PATH = TEST_DATA_CLASSPATH + "auditLogs";

    @Override
    public boolean accept( Type type ) {
        return type instanceof Class && AuditLogItem.class.isAssignableFrom( (Class) type );
    }

    @Override
    AuditLogItem convertSingleValue( String fileName ) {
        String filePath = AUDIT_LOG_FILE_PATH + File.separatorChar + fileName;
        return new AuditLogItem( filePath );
    }

    @Override
    AuditLogItem convertMultiValues( Map<String, String> map ) {
        if ( !map.containsKey( FILENAME_KEY ) ) {
            fail( "Missing mandatory column in the AuditLog table in the feature file: " + map );
        }

        String filePath = AUDIT_LOG_FILE_PATH + File.separatorChar + map.get( FILENAME_KEY );
        AuditLogItem auditLog = new AuditLogItem( filePath );

        // optional column
        if ( map.containsKey( IGNORED_ELEMENTS_KEY ) && !map.get( IGNORED_ELEMENTS_KEY ).equals( "<N/A>" ) ) {
            String ignoredElements = map.get( IGNORED_ELEMENTS_KEY );
            List<String> ignoredElementsList = Arrays.asList( ignoredElements.split( ", " ) );
            auditLog.setIgnoredElements( ignoredElementsList );
        }

        return auditLog;
    }

}
