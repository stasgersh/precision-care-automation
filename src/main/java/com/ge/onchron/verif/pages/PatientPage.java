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
package com.ge.onchron.verif.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;

import static com.ge.onchron.verif.TestSystemParameters.PATIENT_PAGE_URL;

@DefaultUrl( PATIENT_PAGE_URL )
public class PatientPage extends SimplePage {

    @FindBy( xpath = "//*[contains(@class,'PatientDetails-module_container')]" )
    private WebElementFacade patientDetailsMain;

    public boolean isVisible() {
        return patientDetailsMain.waitUntilVisible().isVisible();
    }

    public boolean isCurrentlyVisible() {
        return patientDetailsMain.isCurrentlyVisible();
    }

    public WebElementFacade getPatientDetails() {
        return patientDetailsMain;
    }

}
