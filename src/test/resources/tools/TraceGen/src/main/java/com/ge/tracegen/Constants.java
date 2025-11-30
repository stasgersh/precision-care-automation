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
package com.ge.tracegen;

import java.io.File;

public final class Constants {

    public static final char CSV_SEPARATOR_CHAR = ';';
    public static final String CSV_SEPARATOR = String.valueOf( CSV_SEPARATOR_CHAR );

    public static final String INITIAL_TRACEABILITY_FILE_PATH = System.getProperty( "user.dir" ) + File.separator + "OncoCare_1.1_SRS_rev1_with_initial_traceability.csv";
    public static final String DEFAULT_FEATURE_FILES_DIR_PATH = System.getProperty( "user.dir" ) + File.separator +
            ".." + File.separator +
            ".." + File.separator +
            "src" + File.separator +
            "test" + File.separator +
            "resources" + File.separator +
            "stories";

    public static final String GENERATED_FILES_PATH = System.getProperty( "user.dir" ) + File.separator + "target" + File.separator + "traceabilityAndMetrics";

    private Constants() {
    }

}
