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

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.ge.onchron.verif.pages.SimplePage;
import net.serenitybdd.core.pages.WebElementFacade;

public class Checkbox extends SimplePage {

    private WebElementFacade checkbox;

    public Checkbox( WebElementFacade webElement ) {
        this.checkbox = webElement;
    }

    public String getText() {
        return checkbox.getText();
    }

    public boolean isSelected() {
        return checkboxInput().isSelected();
    }

    public boolean isEnabled() {
        return checkboxInput().isEnabled();
    }

    public void setCheckboxState( CheckboxState state ) {
        if ( state.isSelected() != checkboxInput().isSelected() ) {
            elementClicker( checkbox );
        }
        assertThat( "Failed to change checkbox state", checkboxInput().isSelected(), is( equalTo( state.isSelected() ) ) );
    }

    private WebElement checkboxInput() {
        return checkbox.findElement( By.className( "checkbox__input" ) );
    }

    public WebElementFacade getWebElement() {
        return checkbox;
    }

}
