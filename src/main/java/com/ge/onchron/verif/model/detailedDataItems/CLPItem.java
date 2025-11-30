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

import com.ge.onchron.verif.utils.TextUtils;

public class CLPItem extends DetailedDataItem {

    private String clpText;
    private final String backgroundColor;

    public CLPItem( String name, String clpText ) {
        setName( name );
        this.clpText = clpText;
        backgroundColor = null;
    }

    public CLPItem( String name, String clpText, String backgroundColor ) {
        setName( name );
        this.clpText = clpText;
        this.backgroundColor = backgroundColor;
    }

    public String getClpText() {
        return clpText;
    }

    public void setClpText( String clpText ) {
        this.clpText = clpText;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public Object getValue() {
        return clpText;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        CLPItem that = (CLPItem) o;
        return Objects.equals( getName(), that.getName() ) &&
                TextUtils.textCompareWithKeywords( clpText, that.clpText ) &&
                Objects.equals( getBackgroundColor(), that.getBackgroundColor() );
    }

    @Override
    public String toString() {
        return "CLPItem{" +
                "title='" + getName() + '\'' +
                "clpText='" + clpText + '\'' +
                "backgroundColor='" + backgroundColor + '\'' +
                '}';
    }

}
