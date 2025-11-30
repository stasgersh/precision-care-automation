/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2023, 2023, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.model.detailedDataItems;

import java.util.Objects;

public class LinkWithDateItem {

    private String name;
    private String dateText;

    public LinkWithDateItem( String name, String dateText ) {
        this.name = name;
        this.dateText = dateText;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getDateText() {
        return dateText;
    }

    public void setDateText( String dateText ) {
        this.dateText = dateText;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !(o instanceof LinkWithDateItem) ) return false;
        LinkWithDateItem that = (LinkWithDateItem) o;
        return Objects.equals( getName(), that.getName() ) && Objects.equals( getDateText(), that.getDateText() );
    }

    @Override
    public String toString() {
        return "LinkWithDateItem{" +
                "name='" + name + '\'' +
                ", dateText='" + dateText + '\'' +
                '}';
    }

}
