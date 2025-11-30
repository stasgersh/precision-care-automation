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
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.ge.onchron.verif.howsteps.RestApiSteps;
import com.ge.onchron.verif.model.RestRequest;
import com.ge.onchron.verif.model.UserCredentials;
import com.ge.onchron.verif.model.detailedDataItems.CommentItem;
import com.ge.onchron.verif.pages.sections.Comments;
import com.ge.onchron.verif.utils.CommentUtils;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;

import static com.ge.onchron.verif.TestSystemParameters.API_BASE_URL_PROPERTY;
import static com.ge.onchron.verif.TestSystemParameters.TEST_DATA_CLASSPATH;
import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;
import static com.ge.onchron.verif.utils.CommentUtils.changeParametersInComment;
import static com.ge.onchron.verif.utils.FileUtils.readFromFile;

public abstract class BaseCommentSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger( BaseCommentSteps.class );

    public static final String PATIENT_ID_COLUMN = "patientID";
    public static final String ROOT_RESOURCE_ID_COLUMN = "rootResourceID";
    public static final String COMMENT_TEXT_COLUMN = "comment_text";

    public static final String PATIENT_COMMENT_PATH = "%s/patients/%s/comments";
    public static final String EVENT_COMMENT_PATH = "%s/patients/%s/events/%s/comments";

    private static final String COMMENT_REQUEST_BODY_SKELETON = "{ \"content\": \"%s\"}";

    @Steps
    private RestApiSteps restApiSteps;

    abstract Comments getCommentSection();

    public void checkComments( Boolean isAvailable, List<CommentItem> expectedComments ) {
        List<CommentItem> observedComments = getCommentSection().getComments();
        LOGGER.info( STR."Observed comments on Comments tab: \{observedComments}" );
        if ( isAvailable ) {
            assertEquals( "Number of comments do not match.", expectedComments.size(), observedComments.size() );
        }
        expectedComments.stream().map( CommentItem::new ).forEach( expectedComment -> {
            changeParametersInComment( expectedComment );
            boolean observedContainsExpectedComment = observedComments.contains( expectedComment );
            LOGGER.info( STR."The expected comment: \{expectedComment}, is available: \{observedContainsExpectedComment}" );
            assertThat( STR."Comment availability is not ok: \{expectedComment}", observedContainsExpectedComment, is( equalTo( isAvailable ) ) );
        } );
    }

    public void checkSortedComments( List<CommentItem> expCommentItems ) {
        List<CommentItem> observedComments = getCommentSection().getComments();
        List<CommentItem> expectedComments = expCommentItems.stream()
                                                            .map( CommentUtils::changeParametersInComment )
                                                            .collect( Collectors.toList() );
        LOGGER.info( STR."Observed comments: \{observedComments.stream().map( CommentItem::toString ).collect( Collectors.joining( "\n" ) )}" );
        assertEquals( "Expected and observed sorted comments do not match.", expectedComments, observedComments );
    }

    public void typeComment( String commentText ) {
        getCommentSection().typeComment( commentText );
    }

    public void typeCommentFromFile( String fileName ) {
        String comment = readFromFile( TEST_DATA_CLASSPATH + fileName );
        typeComment( comment );
        LOGGER.info( STR."Type comment: \{comment} from file: \{fileName}" );
    }

    public void continueTypeComment( String comment ) {
        getCommentSection().continueTypeComment( comment );
    }

    public void checkSendButtonState( Boolean isEnabled ) {
        final boolean sendButtonState = getCommentSection().getSendButtonState();
        LOGGER.info( STR."Comment input box is visible: \{sendButtonState}" );
        assertThat( "Comment input box visibility is not ok.", sendButtonState, is( equalTo( isEnabled ) ) );
    }

    public void clickSendComment() {
        int commentsBeforeSend = getCommentSection().getComments().size();
        getCommentSection().clickSendComment();
        getCommentSection().waitForSendingComment();
        getCommentSection().waitForNumberOfVisibleComments( commentsBeforeSend + 1 );
    }

    public void checkCommentInputBoxVisibility( boolean isVisible ) {
        boolean commentInputBoxVisibility = getCommentSection().getCommentInputBoxVisibility();
        LOGGER.info( STR."Comment input box visibility is: \{commentInputBoxVisibility}" );
        assertThat( "Comment input box visibility is not ok.", commentInputBoxVisibility, is( equalTo( isVisible ) ) );
    }

    public void checkCommentAvailability( boolean isAvailable ) {
        List<CommentItem> observedComments = getCommentSection().getComments();
        LOGGER.info( STR."Observed comments: \{observedComments}" );
        assertThat( STR."Comment availability is not ok at \{this.getClass().getSimpleName()}", !observedComments.isEmpty(), is( isAvailable ) );
    }

    public void clickDeleteComment( CommentItem comment ) {
        int commentsBeforeDelete = getCommentSection().getComments().size();
        getCommentSection().clickDeleteComment( changeParametersInComment( comment ) );
        getCommentSection().waitForDeletingComment();
        getCommentSection().waitForNumberOfVisibleComments( commentsBeforeDelete - 1 );
    }

    public boolean checkCommentCharCounterVisibility( Boolean isVisible ) {
        final boolean commentCharCounterVisibility = getCommentSection().getCommentCharCounterVisibility();
        assertThat( "Comment input box visibility is not ok.", commentCharCounterVisibility, is( equalTo( isVisible ) ) );
        return commentCharCounterVisibility;
    }

    public void checkCommentCharCounterMessage( String expCommentCharCounterMsg ) {
        String commentCharCounterMsg = getCommentSection().getCommentCharCounterMsg();
        LOGGER.info( STR."The actual comment char counter message is: \{commentCharCounterMsg}" );
        assertEquals( "Comment char counter message is not ok.", expCommentCharCounterMsg, commentCharCounterMsg );
    }

    public void checkCommentWarning( String expWarningMessage ) {
        assertTrue( "Comment warning is not visible.", getCommentSection().isCommentWarningVisible() );
        String warnMsg = getCommentSection().getCommentWarningMsg();
        LOGGER.info( STR."The actual comment warning message is: \{warnMsg}" );
        assertEquals( "Comment char counter message is not ok.", expWarningMessage, warnMsg );
    }

    /**
     * @param user         user credentials of the user who added the comment
     * @param commentsPath API path of event or patient comments
     */
    protected void deleteComments( UserCredentials user, String commentsPath ) {
        // 1. Get comments
        Response getCommentsResp = getComments( user, commentsPath );

        // 2. Get comment IDs
        List<String> commentIds = getCommentsResp.jsonPath().getList( "value.value.commentId" );
        LOGGER.info( STR."Comment IDs: \{commentIds}" );

        // 3. Delete comments and check response status code
        commentIds.forEach( commentId -> {
            RequestSpecBuilder deleteRequest = new RequestSpecBuilder();
            deleteRequest.setBaseUri( getSystemParameter( API_BASE_URL_PROPERTY ) );
            deleteRequest.setBasePath( STR."\{commentsPath}/\{commentId}" );
            restApiSteps.sendAuthenticatedRequest( user, new RestRequest( Method.DELETE, deleteRequest.build() ) );
            restApiSteps.checkResponseStatusCode( HttpStatus.SC_NO_CONTENT );
        } );
        getCommentsResp = getComments( user, commentsPath );
        commentIds = getCommentsResp.jsonPath().getList( "value.value.commentId" );
        LOGGER.info( STR."Comment IDs: \{commentIds}" );
        assertThat( "The comments list was not empty. Check why not all comments were deleted",
                commentIds.stream().findFirst(), is( Optional.empty() ) );

    }

    protected String getCommentId( UserCredentials user, String commentsPath, String commentText ) {
        Response getCommentsResp = getComments( user, commentsPath );

        List<String> contents = getCommentsResp.jsonPath().getList( "value.value.content" );
        if ( !contents.contains( commentText ) ) {
            fail( STR."Following comment was not found: \{commentText}" );
        }
        int index = contents.indexOf( commentText );

        List<String> commentIds = getCommentsResp.jsonPath().getList( "value.value.commentId" );
        return commentIds.get( index );
    }

    private Response getComments( UserCredentials user, String commentsPath ) {
        RequestSpecBuilder rsb = new RequestSpecBuilder();
        rsb.setBaseUri( getSystemParameter( API_BASE_URL_PROPERTY ) );
        rsb.setBasePath( commentsPath );
        Response getCommentsResp = restApiSteps.sendAuthenticatedRequest( user, new RestRequest( Method.GET, rsb.build() ) );
        LOGGER.info( STR."Comments from \{commentsPath}: \{getCommentsResp.asString()}" );
        return getCommentsResp;
    }

    protected void addComment( UserCredentials user, String commentPath, String comment ) {
        RequestSpecBuilder addCommentRequest = new RequestSpecBuilder();
        addCommentRequest.setBaseUri( getSystemParameter( API_BASE_URL_PROPERTY ) );
        addCommentRequest.setBasePath( commentPath );
        addCommentRequest.setBody( String.format( COMMENT_REQUEST_BODY_SKELETON, comment ) );
        addCommentRequest.setContentType( ContentType.JSON );
        Response addCommentResp = restApiSteps.sendAuthenticatedRequest( user, new RestRequest( Method.POST, addCommentRequest.build() ) );
        LOGGER.info( STR."Response of add comment: \{addCommentResp.asString()}" );
        restApiSteps.checkResponseStatusCode( HttpStatus.SC_CREATED );
    }

    public void checkCommentsInfoMsg( String expCommentsInfoText ) {
        String observedCommentsInfoText = getCommentSection().getCommentsInfoText();
        LOGGER.info( STR."Observed message: \{observedCommentsInfoText}" );
        assertEquals( "Comments info message is not ok.", expCommentsInfoText, observedCommentsInfoText );
    }

    public void checkEmptyCommentsMsg( String expectedText ) {
        List<CommentItem> observedComments = getCommentSection().getComments();
        LOGGER.info( STR."Available comments: \{observedComments}" );
        assertTrue( "Comment should not be available.", observedComments.isEmpty() );
        String emptyCommentsText = getCommentSection().getEmptyCommentsText();
        LOGGER.info( STR."The actual message for empty comments is: \{emptyCommentsText}" );
        assertEquals( "Empty comments info message is not ok.", expectedText, emptyCommentsText );
    }

    public void checkCommentTextInInputBox( String expectedText ) {
        String commentInputBoxText = getCommentSection().getCommentInputBoxText();
        LOGGER.info( STR."Found message:\{commentInputBoxText}" );
        assertThat( "Comment input box text is not ok.", commentInputBoxText, is( equalTo( expectedText ) ) );
    }

}
