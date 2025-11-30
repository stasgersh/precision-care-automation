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
package com.ge.onchron.verif;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import net.thucydides.core.util.EnvironmentVariables;

import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;
import static net.thucydides.core.environment.SystemEnvironmentVariables.createEnvironmentVariables;

public class SystemTestConstants {

    public static final String FONT_WEIGHT = "font-weight";
    public static final String BOLD_TEXT_FONT_WEIGHT = "700";
    public static final String TEST_RESULT_DIRECTORY;

    static {
        EnvironmentVariables environmentvars = createEnvironmentVariables();
        String executionDate = new SimpleDateFormat( "yyyy.MM.dd_HH.mm.ss" ).format( new Timestamp( System.currentTimeMillis() ) );
        boolean useUniqueResultFolder = Boolean.parseBoolean( getSystemParameter( "use.unique.result.folder" ) );
        if ( useUniqueResultFolder ) {
            TEST_RESULT_DIRECTORY = "TestResults/"
                    + executionDate + "_"
                    + environmentvars.getProperty( "webdriver.driver" ) + "_"
                    + environmentvars.getProperty( "webdriver.base.url" ).replaceAll( "/|:", "_" );
        } else {
            TEST_RESULT_DIRECTORY = "JBehaveReport/";
        }
    }

}
