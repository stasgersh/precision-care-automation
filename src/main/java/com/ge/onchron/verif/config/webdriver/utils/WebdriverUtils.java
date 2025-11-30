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
package com.ge.onchron.verif.config.webdriver.utils;

import com.ge.onchron.verif.utils.FileUtils;
import java.nio.file.Path;
import java.util.HashMap;

import static com.ge.onchron.verif.TestSystemParameters.DEFAULT_DOWNLOAD_DIRECTORY;

public class WebdriverUtils {

    public static HashMap<String, Object> getDefaultExperimentalOptions() {
        Path downloadDir = FileUtils.getAbsolutPathOfDir( DEFAULT_DOWNLOAD_DIRECTORY );
        HashMap<String, Object> prefs = new HashMap<>();
        prefs.put( "download.default_directory", downloadDir.toString() );
        prefs.put( "profile.default_content_settings.popups", 0 );
        prefs.put( "download.prompt_for_download", false );
        prefs.put( "download.directory_upgrade", true );
        prefs.put( "safebrowsing.enabled", true );
        prefs.put( "profile.content_settings.exceptions.automatic_downloads.*.setting", 1 );
        return prefs;
    }

}
