/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2023, 2023, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.pages;

import org.openqa.selenium.By;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

public class ErrorPage extends SimplePage {

    private final String ERROR_SCREEN_CLASS = "Error-module_with-masthead";
    private final String ERROR_ADMIN_SCREEN_CLASS = "cdk-overlay-0";
    private final String ERROR_LOGIN_PAGE = "Authentication error!";

    @FindBy( css = "[class*='" + ERROR_SCREEN_CLASS + "']" )
    private WebElementFacade errorScreen;

    @FindBy(id = ERROR_ADMIN_SCREEN_CLASS )
    private WebElementFacade errorInfoScreen;

    @FindBy(css = "[heading-text*='" + ERROR_LOGIN_PAGE + "']")
    private WebElementFacade errorLoginScreen;

    public String getErrorMessage() {
        return errorScreen.waitUntilVisible().then( By.tagName( "p" ) ).getText();
    }

    public String getAdminConsoleErrorMessage() {
        return errorInfoScreen.waitUntilVisible().then().getText();

    }
    public void closePopUP()
    {
        errorInfoScreen.waitUntilVisible().then(By.xpath("//*[contains(@class,'tertiary-button')]")).click();
    }

}
