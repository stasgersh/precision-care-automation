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
package com.ge.onchron.verif.pages.elements;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ge.onchron.verif.pages.SimplePage;

public class EmptyStateInfo extends SimplePage {

    private WebElement emptyStateInfo;

    public EmptyStateInfo( WebElement emptyStateInfo ) {
        this.emptyStateInfo = emptyStateInfo;
    }

    public String getHeader() {
        return getParams().get( 0 ).getText();
    }

    public String getBody() {
        return getParams().get( 1 ).getText();
    }

    private List<WebElement> getParams() {
        return emptyStateInfo.findElements( By.tagName( "p" ) );
    }

}
