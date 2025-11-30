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
package com.ge.onchron.verif.pages.sections.modal;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;

import com.ge.onchron.verif.model.l3report.L3ModalHeader;
import com.ge.onchron.verif.pages.SimplePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.pages.utils.PageUtils.currentlyContainsWebElements;

public abstract class BaseModal extends SimplePage {

    private final int MAX_WAIT_TIME_TO_LOAD_MODAL_IN_SEC = 3;

    @FindBy( xpath = "//*[contains(@class,'Modal-module_close')]" )
    private WebElementFacade closeButton;

    public boolean isVisible() {
        List<WebElementFacade> modalElements = withTimeoutOf( Duration.ofSeconds( MAX_WAIT_TIME_TO_LOAD_MODAL_IN_SEC ) )
                .findAll( getLocator() );
        return !modalElements.isEmpty();
    }

    public void close() {
        elementClicker( closeButton );
    }

    abstract protected By getLocator();

    public L3ModalHeader getModalHeader() {
        if ( currentlyContainsWebElements( find( getLocator() ), By.tagName( "h2" ) ) && currentlyContainsWebElements( find( getLocator() ), By.tagName( "p" ) ) ) {
            String title = find( getLocator() ).then( By.tagName( "h2" ) ).getText();
            String subTitle = find( getLocator() ).then( By.tagName( "p" ) ).getText();
            return new L3ModalHeader( title, subTitle );
        }
        return new L3ModalHeader( "", "" );
    }

}
