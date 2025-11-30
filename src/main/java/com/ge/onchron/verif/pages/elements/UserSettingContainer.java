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
package com.ge.onchron.verif.pages.elements;

import net.serenitybdd.core.pages.WebElementFacade;

public class UserSettingContainer {

    private WebElementFacade settingName;
    private WebElementFacade settingValue;

    public UserSettingContainer( WebElementFacade settingName, WebElementFacade settingValue ) {
        this.settingName = settingName;
        this.settingValue = settingValue;
    }

    public WebElementFacade getSettingName() {
        return settingName;
    }

    public WebElementFacade getSettingValue() {
        return settingValue;
    }

}
