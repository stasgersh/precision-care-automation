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


import java.util.Objects;

import net.serenitybdd.core.pages.WebElementFacade;

public class StudySelectorListElement {

    private String name;
    private String date;
    private boolean isSelected;
    private boolean isDisabled;
    private WebElementFacade webElement;

    public StudySelectorListElement( String name, String date ) {
        this.name = name;
        this.date = date;
    }

    public StudySelectorListElement() {
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate( String date ) {
        this.date = date;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected( boolean selected ) {
        isSelected = selected;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled( boolean disabled ) {
        isDisabled = disabled;
    }

    public WebElementFacade getWebElement() {
        return webElement;
    }

    public void setWebElement( WebElementFacade webElement ) {
        this.webElement = webElement;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        StudySelectorListElement that = (StudySelectorListElement) o;
        return Objects.equals( getName(), that.getName() )
                && Objects.equals( getDate(), that.getDate() )
                && Objects.equals( isSelected(), that.isSelected() )
                && Objects.equals( isDisabled(), that.isDisabled() );
    }

    @Override
    public String toString() {
        return STR."StudySelectorListElement{name=\{name}, date='\{date}\{'\''}, isSelected='\{isSelected}\{'\''}, isDisabled='\{isDisabled}\{'\''}\{'}'}";
    }
}
