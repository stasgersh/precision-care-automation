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

import org.openqa.selenium.By;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

public class AlertModal extends BaseModal {

    private final String ALERT_MODAL_CLASSNAME = "modal";

    @FindBy( className = ALERT_MODAL_CLASSNAME )
    private WebElementFacade modal;

    @FindBy( xpath = "//*[contains(@class,'Modal-module_modal-header')]" )
    private WebElementFacade header;

    @FindBy( xpath = "//*[contains(@class,'modal__content')]" )
    private WebElementFacade content;

    @Override
    protected By getLocator() {
        return By.className( ALERT_MODAL_CLASSNAME );
    }

    public String getHeaderText() {
        return header.getText();
    }

    public String getContent() {
        return content.getText();
    }

    public void clickOnButton( String buttonText ) {
        WebElementFacade button = modal.thenFindAll( By.tagName( "button" ) ).stream()
                .filter( btn -> btn.getText().equals( buttonText ) )
                .findFirst()
                .orElseThrow( () -> new RuntimeException( "The following button was not found on alert modal: " + buttonText ) );
        elementClicker( button );
    }

}
