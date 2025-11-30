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
package com.ge.onchron.verif.model.detailedDataItems;

import java.util.Objects;

public class CommentInputBoxItem extends DetailedDataItem {

    private String text;
    private boolean isPlaceholderText = false;

    public CommentInputBoxItem( String text ) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText( String text ) {
        this.text = text;
    }

    public boolean isPlaceholderText() {
        return isPlaceholderText;
    }

    public void setPlaceholderText( boolean placeholderText ) {
        isPlaceholderText = placeholderText;
    }

    @Override
    public String getValue() {
        return text;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !(o instanceof CommentInputBoxItem) ) return false;
        CommentInputBoxItem that = (CommentInputBoxItem) o;
        return Objects.equals( getText(), that.getText() );
    }

    @Override
    public String toString() {
        return "CommentInputBoxItem{" +
                "text='" + text + '\'' +
                "isPlaceholderText='" + isPlaceholderText + '\'' +
                '}';
    }

}
