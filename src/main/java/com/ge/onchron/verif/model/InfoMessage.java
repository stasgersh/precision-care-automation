/*
 * -GE CONFIDENTIAL-
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
package com.ge.onchron.verif.model;

import java.util.Objects;

public class InfoMessage {

    private String title;
    private String content;

    public InfoMessage( String title, String content ) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent( String content ) {
        this.content = content;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !(o instanceof InfoMessage that) ) return false;
        return Objects.equals( getTitle(), that.getTitle() ) && Objects.equals( getContent(), that.getContent() );
    }

    @Override
    public String toString() {
        return "InfoMessage{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

}
