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
package com.ge.onchron.verif.config.webdriver;

import org.openqa.selenium.chromium.ChromiumOptions;

import net.serenitybdd.core.webdriver.enhancers.CustomChromiumOptions;
import net.thucydides.core.util.EnvironmentVariables;

import static com.ge.onchron.verif.config.webdriver.utils.WebdriverUtils.getDefaultExperimentalOptions;

public class ChromeBrowserOptions implements CustomChromiumOptions {

    @Override
    public void apply( EnvironmentVariables environmentVariables, ChromiumOptions<?> chromiumOptions ) {
        chromiumOptions.setExperimentalOption( "prefs", getDefaultExperimentalOptions() );
    }
}
