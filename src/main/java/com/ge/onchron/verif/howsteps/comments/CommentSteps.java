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
package com.ge.onchron.verif.howsteps.comments;

import java.util.List;
import java.util.Map;

import org.jbehave.core.model.ExamplesTable;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.ge.onchron.verif.model.UserCredentials;
import com.ge.onchron.verif.model.detailedDataItems.CommentItem;
import com.ge.onchron.verif.pages.sections.Comments;
import com.ge.onchron.verif.pages.tabs.CommentsView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ge.onchron.verif.TestSystemParameters.CORE_API_BASE_PATH_PROPERTY;
import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;
import static com.ge.onchron.verif.utils.CommentUtils.changeParametersInComment;
import static com.ge.onchron.verif.utils.UserCredentialsUtils.getDefaultUser;
import static net.serenitybdd.core.Serenity.setSessionVariable;

public class CommentSteps extends BaseCommentSteps {
    private static final Logger LOGGER = LoggerFactory.getLogger( CommentSteps.class );
    CommentsView commentsView;

    @Override
    Comments getCommentSection() {
        commentsView.waitUntilPatientTabLoadingSkeletonDisappears();
        return commentsView.getCommentSection();
    }

    public void checkCommentsViewVisibility( Boolean isVisible ) {
        boolean commentsViewVisible = commentsView.isCommentsViewVisible();
        LOGGER.info(STR."Comment view visibility: \{commentsViewVisible}");
        assertThat( "The Comments view visibility is not ok.", commentsViewVisible, is( equalTo( isVisible ) ) );
    }

    /**
     * This method clears all event and patient comments for a patient
     *
     * @param user         who is logged in (i.e. has valid token)
     * @param patientsList clear all comments for these patients
     */
    public void clearCommentsForPatients( UserCredentials user, List<Map<String, String>> patientsList ) {
        patientsList.forEach( patient -> {
            String patientId = patient.get( PATIENT_ID_COLUMN );
            clearCommentsForSinglePatient( user, patientId );
        } );
    }

    public void clearAndAddPatientComments( UserCredentials user, ExamplesTable patientCommentDataList ) {
        // Clear comments for all patients
        patientCommentDataList.getRows().forEach( patientCommentData -> {
            String patientId = patientCommentData.get( PATIENT_ID_COLUMN );
            clearCommentsForSinglePatient( user, patientId );
        } );
        // Add new comments for events
        patientCommentDataList.getRows().forEach( patientCommentData -> {
            String patientId = patientCommentData.get( PATIENT_ID_COLUMN );
            String commentText = patientCommentData.get( COMMENT_TEXT_COLUMN );
            addPatientComment( user, patientId, commentText );
        } );
    }

    /**
     * Clear all event and patient comments for single patient
     *
     * @param user      who is logged in (i.e. has valid token)
     * @param patientId clear all comments for this patients
     */
    private void clearCommentsForSinglePatient( UserCredentials user, String patientId ) {
        String commentsPath = String.format( PATIENT_COMMENT_PATH, getSystemParameter( CORE_API_BASE_PATH_PROPERTY ), patientId );
        deleteComments( user, commentsPath );
    }

    private void addPatientComment( UserCredentials user, String patientId, String comment ) {
        String patientCommentPath = String.format( PATIENT_COMMENT_PATH, getSystemParameter( CORE_API_BASE_PATH_PROPERTY ), patientId );
        addComment( user, patientCommentPath, comment );
    }

    public void storePatientCommentId( String storedVariableName, Map<String, String> patientCommentData ) {
        String patientId = patientCommentData.get( PATIENT_ID_COLUMN );
        String commentText = patientCommentData.get( COMMENT_TEXT_COLUMN );

        String patientCommentsPath = String.format( PATIENT_COMMENT_PATH, getSystemParameter( CORE_API_BASE_PATH_PROPERTY ), patientId );
        String commentId = getCommentId( getDefaultUser(), patientCommentsPath, commentText );
        LOGGER.info(STR."Comment ID:\{commentId}");
        setSessionVariable( storedVariableName ).to( commentId );
    }

    public void clickViewOnTimelineButton( CommentItem comment ) {
        getCommentSection().clickViewOnTimelineButton( changeParametersInComment( comment ) );
    }

    public void clickHideTimelineComments() {
        commentsView.clickHideTimelineComments();
    }

}
