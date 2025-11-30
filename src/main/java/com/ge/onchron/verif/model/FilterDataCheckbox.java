/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2023, 2023, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 */
package com.ge.onchron.verif.model;

public class FilterDataCheckbox {

    private String filterGroup;
    private CheckboxModel checkbox;

    public FilterDataCheckbox( String filterGroup, CheckboxModel checkbox ) {
        this.filterGroup = filterGroup;
        this.checkbox = checkbox;
    }

    public String getFilterGroup() {
        return filterGroup;
    }

    public CheckboxModel getCheckboxModel() {
        return checkbox;
    }

    @Override
    public String toString() {
        return "FilterDataCheckbox{" +
                "filterGroup='" + filterGroup + '\'' +
                ", checkbox=" + checkbox +
                '}';
    }

}
