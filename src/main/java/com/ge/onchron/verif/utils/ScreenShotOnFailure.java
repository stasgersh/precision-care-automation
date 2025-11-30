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
package com.ge.onchron.verif.utils;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.ge.onchron.verif.TestSystemParameters;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.steps.StepEventBus;

import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;

public class ScreenShotOnFailure {

    public static void captureScreen() {
        try {
            String folderPath = "target" + File.separator + "TestResults" + File.separator + TestSystemParameters.FAILURE_SCREENSHOT_DIRECTORY + File.separator + new DateTime().toString( "yyyyMMdd" );
            new File( folderPath ).mkdirs();
            File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs( OutputType.FILE );

            StepEventBus stepEvent = StepEventBus.getEventBus();
            List<TestOutcome> testOutComesList = stepEvent.getBaseStepListener().getTestOutcomes();
            String stepId = testOutComesList.get( testOutComesList.size() - 1 ).getId().split( "/" )[2];
            stepId = stepId.replaceAll( ":", "" );  // Eliminate ":" characters from the filename

            FileUtils.copyFile( scrFile, new File( folderPath + File.separator + stepId + ".png" ) );
        } catch ( Exception ignored ) {
        }
    }

}
