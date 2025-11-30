/*
 * -GE CONFIDENTIAL- or -GE HIGHLY CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2025, 2025, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE HealthCare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 * GE is a trademark of General Electric Company used under trademark license.
 */

package com.ge.onchron.verif.config.webdriver;

import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.chromium.ChromiumNetworkConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.fail;

import java.time.Duration;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.webdriver.WebDriverFacade;

public class WebDriverNetworkConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger( WebDriverNetworkConfig.class );

    public void setDownloadThroughput( int downloadThroughput ) {
        if ( Serenity.getDriver() instanceof WebDriverFacade webDriverFacade && webDriverFacade.getProxiedDriver() instanceof ChromiumDriver driver ) {
            ChromiumNetworkConditions conditions = new ChromiumNetworkConditions();
            conditions.setDownloadThroughput( downloadThroughput );
            conditions.setLatency( Duration.ofMillis( 3000 ));
            driver.setNetworkConditions( conditions );
            LOGGER.info( STR."Download throughput is set to \{downloadThroughput} kbit/s" );
        } else {
            fail( "WebDriver could not be identified as Chromium driver" );
        }
    }
}
