/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2025, 2025, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE HealthCare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 */
package com.ge.onchron.verif.model;

import java.util.Objects;

public class EmptyStateMessage {

    private String title;
    private String message;
    private String buttonText;

    public EmptyStateMessage( String title, String message ) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText( String buttonText ) {
        this.buttonText = buttonText;
    }


    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !(o instanceof EmptyStateMessage) ) return false;
        EmptyStateMessage that = (EmptyStateMessage) o;
        return Objects.equals( getTitle(), that.getTitle() ) &&
                Objects.equals( getMessage(), that.getMessage() ) &&
                Objects.equals( getButtonText(), that.getButtonText() );
    }

    @Override
    public String toString() {
        return STR."EmptyStateMessage{title='\{title}\{'\''}, message='\{message}\{'\''}, buttonText='\{buttonText}\{'\''}\{'}'}";
    }
}
