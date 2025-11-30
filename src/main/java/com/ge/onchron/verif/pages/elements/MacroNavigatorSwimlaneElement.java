/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2024, 2024, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 */
package com.ge.onchron.verif.pages.elements;

import com.ge.onchron.verif.model.MacroNavigatorSwimlane;
import net.serenitybdd.core.pages.WebElementFacade;

public class MacroNavigatorSwimlaneElement {

    private MacroNavigatorSwimlane swimlane;
    private WebElementFacade lane;

    public MacroNavigatorSwimlaneElement( MacroNavigatorSwimlane swimlane, WebElementFacade lane ) {
        this.swimlane = swimlane;
        this.lane = lane;
    }

    public MacroNavigatorSwimlane getSwimlane() {
        return swimlane;
    }

    public void setSwimlane( MacroNavigatorSwimlane swimlane ) {
        this.swimlane = swimlane;
    }

    public WebElementFacade getLane() {
        return lane;
    }

    public void setLane( WebElementFacade lane ) {
        this.lane = lane;
    }

}
