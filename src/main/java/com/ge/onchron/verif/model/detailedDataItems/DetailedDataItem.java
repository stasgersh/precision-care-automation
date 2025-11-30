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

public abstract class DetailedDataItem {

    private String name;

    public void setName( String name ) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract Object getValue();

    public boolean hasSameName( DetailedDataItem other ) {
        if ( this == other ) return true;
        if ( other == null ) return false;
        return Objects.equals( name, other.name );
    }

}
