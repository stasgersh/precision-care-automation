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
package com.ge.onchron.verif.pages.tabs;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import com.ge.onchron.verif.pages.PatientPage;
import com.ge.onchron.verif.pages.sections.Comments;
import net.serenitybdd.core.pages.WebElementFacade;

public class CommentsView extends PatientPage {

    private final String PATIENT_COMMENTS_VIEW_CSS_SELECTOR = "[class*='PatientComments-module_wrapper']";
    private final String PATIENT_COMMENTS_LOADING_SKELETON_CSS_SELECTOR = "[class*='SkeletonLoader-module_comment-container']";

    public boolean isCommentsViewVisible() {
        return getCommentsView().isCurrentlyVisible();
    }

    public Comments getCommentSection() {
        return PageFactory.initElements( getCommentsView().getWrappedElement(), Comments.class );
    }

    public void waitUntilPatientTabLoadingSkeletonDisappears() {
        waitUntilLoadingSkeletonDisappears( getCommentsView(), By.cssSelector( PATIENT_COMMENTS_LOADING_SKELETON_CSS_SELECTOR ) );
    }

    private WebElementFacade getCommentsView() {
        return getPatientDetails().find( By.cssSelector( PATIENT_COMMENTS_VIEW_CSS_SELECTOR ) );
    }

    public void clickHideTimelineComments() {
        getCommentSection().hideTimelineCommentsSwitch().click();
    }

}
