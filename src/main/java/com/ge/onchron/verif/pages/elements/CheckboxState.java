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


public enum CheckboxState {
    SELECTED( true ),
    UNSELECTED( false );

    private boolean isSelected;

    private CheckboxState( boolean isSelected ) {
        this.isSelected = isSelected;
    }

    public static CheckboxState valueOf( boolean isSelected ) {
        if ( isSelected ) {
            return CheckboxState.SELECTED;
        }
        return CheckboxState.UNSELECTED;
    }

    public boolean isSelected() {
        return isSelected;
    }

}
