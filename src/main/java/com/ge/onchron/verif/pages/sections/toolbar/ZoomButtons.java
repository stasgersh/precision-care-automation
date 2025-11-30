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
package com.ge.onchron.verif.pages.sections.toolbar;

import com.ge.onchron.verif.pages.SimplePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

public class ZoomButtons extends SimplePage {

    private final String ZOOM_OUT_ID = "zoom-out";
    private final String ZOOM_IN_ID = "zoom-in";
    private final String ZOOM_SLIDER_ID = "zoom-slider";
    private final String RESET_ZOOM_XPATH = ".//button[text()='Reset zoom']";

    @FindBy( id = ZOOM_OUT_ID )
    private WebElementFacade zoomOutBtn;

    @FindBy( id = ZOOM_IN_ID )
    private WebElementFacade zoomInBtn;

    @FindBy( xpath = RESET_ZOOM_XPATH )
    private WebElementFacade resetZoom;

    @FindBy( id = ZOOM_SLIDER_ID )
    private WebElementFacade zoomSlider;

    public boolean isZoomOutEnabled() {
        return zoomOutBtn.isCurrentlyEnabled();
    }

    public boolean isZoomInEnabled() {
        return zoomInBtn.isCurrentlyEnabled();
    }

    public void zoomOut() {
        elementClicker( zoomOutBtn );
    }

    public void zoomIn() {
        elementClicker( zoomInBtn );
    }

    public void resetZoom() {
        elementClicker( resetZoom );
    }

    public WebElementFacade getZoomSlider() {
        return zoomSlider;
    }

}
