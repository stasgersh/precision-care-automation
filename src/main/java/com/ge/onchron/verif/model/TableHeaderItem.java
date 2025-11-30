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

import com.ge.onchron.verif.model.enums.ArrowDirection;

public class TableHeaderItem {

    private String name;
    private boolean usedForSorting;
    private ArrowDirection arrowDirection;

    public TableHeaderItem( String name, boolean usedForSorting ) {
        this.name = name;
        this.usedForSorting = usedForSorting;
    }

    public TableHeaderItem( String name, boolean usedForSorting, ArrowDirection sortOrder ) {
        this.name = name;
        this.usedForSorting = usedForSorting;
        this.arrowDirection = sortOrder;
    }

    public String getName() {
        return name;
    }

    public boolean usedForSorting() {
        return usedForSorting;
    }

    public ArrowDirection getArrowDirection() {
        return arrowDirection;
    }

    @Override
    public String toString() {
        return "TableHeaderItem{" +
                "name='" + name + '\'' +
                ", usedForSorting=" + usedForSorting +
                ", arrowDirection=" + arrowDirection +
                '}';
    }

}
