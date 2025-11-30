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
package com.ge.onchron.verif.model;

import java.util.Objects;

public class NotificationPopup {

    private String header;
    private String contentMsg;
    private String contentEventDate;
    private String contentEventName;
    private boolean inStack = false;
    private int indexInStack = -1;

    public NotificationPopup( String header, String contentMsg ) {
        this.header = header;
        this.contentMsg = contentMsg;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader( String header ) {
        this.header = header;
    }

    public String getContentMsg() {
        return contentMsg;
    }

    public void setContentMsg( String contentMsg ) {
        this.contentMsg = contentMsg;
    }

    public String getContentEventDate() {
        return contentEventDate;
    }

    public void setContentEventDate( String contentEventDate ) {
        this.contentEventDate = contentEventDate;
    }

    public String getContentEventName() {
        return contentEventName;
    }

    public void setContentEventName( String contentEventName ) {
        this.contentEventName = contentEventName;
    }

    public boolean inStack() {
        return inStack;
    }

    public void setInStack( boolean inStack ) {
        this.inStack = inStack;
    }

    public int getIndexInStack() {
        return indexInStack;
    }

    public void setIndexInStack( int indexInStack ) {
        this.indexInStack = indexInStack;
    }

    public boolean isVisible() {
        return !inStack() || (inStack() && getIndexInStack() == 0);
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !(o instanceof NotificationPopup) ) return false;
        NotificationPopup that = (NotificationPopup) o;
        return Objects.equals( getHeader(), that.getHeader() ) &&
                Objects.equals( getContentMsg(), that.getContentMsg() ) &&
                Objects.equals( getContentEventDate(), that.getContentEventDate() ) &&
                Objects.equals( getContentEventName(), that.getContentEventName() ) &&
                Objects.equals( isVisible(), that.isVisible() );
    }

    @Override
    public String toString() {
        return "NotificationPopup{" +
                "header='" + header + '\'' +
                ", contentMsg='" + contentMsg + '\'' +
                ", contentEventDate='" + contentEventDate + '\'' +
                ", contentEventName='" + contentEventName + '\'' +
                ", inStack='" + inStack + '\'' +
                ", indexInStack='" + indexInStack + '\'' +
                '}';
    }

}
