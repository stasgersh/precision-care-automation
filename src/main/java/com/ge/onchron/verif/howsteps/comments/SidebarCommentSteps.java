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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

import com.ge.onchron.verif.model.UserCredentials;
import com.ge.onchron.verif.pages.sections.Comments;
import com.ge.onchron.verif.pages.sections.Sidebar;

import static com.ge.onchron.verif.TestSystemParameters.CORE_API_BASE_PATH_PROPERTY;
import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;
import static com.ge.onchron.verif.utils.PatientUtils.getEventIdBasedOnRootResource;
import static com.ge.onchron.verif.utils.PatientUtils.getEventIdFromExampleTableRow;
import static com.ge.onchron.verif.utils.UserCredentialsUtils.getDefaultUser;
import static net.serenitybdd.core.Serenity.setSessionVariable;

public class SidebarCommentSteps extends BaseCommentSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger( SidebarCommentSteps.class );

    private Sidebar sidebar;

    @Override
    Comments getCommentSection() {
        sidebar.waitUntilSidebarContentLoadingSkeletonDisappears();
        return sidebar.getCommentSection();
    }

    public void clearCommentsForEvent( UserCredentials user, List<Map<String, String>> eventsList ) {
        eventsList.forEach( event -> {
            String patientId = event.get( PATIENT_ID_COLUMN );
            String eventId = getEventIdFromExampleTableRow( patientId, event );
            clearSingleEventComment( user, patientId, eventId );
        } );
    }

    public void clearAndAddEventComments( UserCredentials user, ExamplesTable eventCommentDataList ) {
        // Clear comments for all events
        eventCommentDataList.getRows().forEach( eventCommentData -> {
            String patientId = eventCommentData.get( PATIENT_ID_COLUMN );
            String eventId = getEventIdFromExampleTableRow( patientId, eventCommentData );
            clearSingleEventComment( user, patientId, eventId );
        } );
        // Add new comments for events
        eventCommentDataList.getRows().forEach( eventCommentData -> {
            String patientId = eventCommentData.get( PATIENT_ID_COLUMN );
            String eventId = getEventIdFromExampleTableRow( patientId, eventCommentData );
            String commentText = eventCommentData.get( COMMENT_TEXT_COLUMN );
            addEventComment( user, patientId, eventId, commentText );
        } );
    }

    private void clearSingleEventComment( UserCredentials user, String patientId, String eventIdWithType ) {
        String eventCommentsPath = String.format( EVENT_COMMENT_PATH, getSystemParameter( CORE_API_BASE_PATH_PROPERTY ), patientId, eventIdWithType );
        deleteComments( user, eventCommentsPath );
    }

    private void addEventComment( UserCredentials user, String patientId, String eventId, String comment ) {
        String eventCommentPath = String.format( EVENT_COMMENT_PATH, getSystemParameter( CORE_API_BASE_PATH_PROPERTY ), patientId, eventId );
        addComment( user, eventCommentPath, comment );
    }

    public void checkCommentInfoInHeader( String expectedCommentText ) {
        final String commentInfoFromHeader = sidebar.getCommentInfoFromHeader();
        LOGGER.info( STR."Observed comment info text in sidebar header was: \{commentInfoFromHeader}" );
        assertEquals( "Comment info text in sidebar header is not ok.", expectedCommentText, commentInfoFromHeader );
    }

    public void storeEventCommentId( String storedVariableName, Map<String, String> eventCommentData ) {
        String patientId = eventCommentData.get( PATIENT_ID_COLUMN );
        String rootResourceId = eventCommentData.get( ROOT_RESOURCE_ID_COLUMN );
        String eventId = getEventIdBasedOnRootResource( patientId, rootResourceId );
        String commentText = eventCommentData.get( COMMENT_TEXT_COLUMN );

        String eventCommentsPath = String.format( EVENT_COMMENT_PATH, getSystemParameter( CORE_API_BASE_PATH_PROPERTY ), patientId, eventId );

        String commentId = getCommentId( getDefaultUser(), eventCommentsPath, commentText );
        setSessionVariable( storedVariableName ).to( commentId );
    }

}
