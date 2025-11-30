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

package com.ge.onchron.verif.whatsteps.api;

import org.jbehave.core.annotations.When;

import com.ge.onchron.verif.converters.types.Bitrate;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.howsteps.WebDriverConfigSteps;
import net.thucydides.core.annotations.Steps;

public class WebDriverConfig {
    @Steps
    WebDriverConfigSteps webDriverConfigSteps;
    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @When( "the download bitrate is set to $bitrate" )
    public void setBitrate( Bitrate bitrate ) {
        testDefinitionSteps.addStep( STR."Setting download bitrate" );
        webDriverConfigSteps.setBitrate( bitrate.getKiloBitPerSecond() );
        testDefinitionSteps.logEvidence( STR."The download bitrate is set to \{bitrate}",
                STR."The download bitrate was set to \{bitrate}", true );
    }
}
