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
package com.ge.onchron.verif.howsteps;

import com.ge.onchron.verif.pages.SplashPage;
import net.thucydides.core.annotations.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

public class SplashScreenSteps {

    private              SplashPage splashPage;
    private static final Logger     LOGGER = LoggerFactory.getLogger( PatientHeaderSteps.class );

    public void loadSplashPage() {
        splashPage.open();
        splashPage.ignoreCertIssue();
    }

    @Step
    public void clickSignIn() {
        splashPage.clickSignIn();
    }

    public void verifyUserIsOnTheSplashScreen() {
        boolean pageIsVisible = splashPage.splashPageIsVisible();
        LOGGER.info(STR."Splash screen visible: \{pageIsVisible}");
        assertTrue( "Splash screen is not displayed.", pageIsVisible);
    }

    public void waitForLoginModuleToLoad() {
        splashPage.waitForLoginModule();
    }
}
