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

package com.ge.onchron.verif.howsteps;

import com.ge.onchron.verif.config.webdriver.WebDriverNetworkConfig;
import net.thucydides.core.annotations.Steps;

public class WebDriverConfigSteps {

    @Steps
    private WebDriverNetworkConfig networkConfig;

    public void setBitrate( int kiloBitPerSecond ) {
        networkConfig.setDownloadThroughput( kiloBitPerSecond );
    }
}
