/*
 * -GEHC CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2024, 2024, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE HealthCare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 */
package com.ge.onchron.verif.model.treatmentAndResponse;


import net.serenitybdd.core.pages.WebElementFacade;

public class ImagingStudySelector {

    private String badge;
    private boolean fadedBadge;
    private String selectedStudyName;
    private String selectedStudyDate;
    private boolean isEmpty;
    private boolean isOpen;
    private boolean isDisabled;
    private WebElementFacade moduleElement;


    public String getBadge() {
        return badge;
    }

    public void setBadge( String badge ) {
        this.badge = badge;
    }

    public boolean isBadgeFaded() {
        return fadedBadge;
    }

    public void setBadgeFaded( boolean fadedBadge ) {
        this.fadedBadge = fadedBadge;
    }

    public String getSelectedStudyName() {
        return selectedStudyName;
    }

    public void setSelectedStudyName( String selectedStudyName ) {
        this.selectedStudyName = selectedStudyName;
    }

    public String getSelectedStudyDate() {
        return selectedStudyDate;
    }

    public void setSelectedStudyDate( String selectedStudyDate ) {
        this.selectedStudyDate = selectedStudyDate;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty( boolean empty ) {
        isEmpty = empty;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen( boolean open ) {
        isOpen = open;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled( boolean disabled ) {
        isDisabled = disabled;
    }

    public WebElementFacade getModuleElement() {
        return moduleElement;
    }

    public void setModuleElement( WebElementFacade moduleElement ) {
        this.moduleElement = moduleElement;
    }
}
