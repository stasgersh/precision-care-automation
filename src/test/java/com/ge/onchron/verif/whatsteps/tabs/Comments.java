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
package com.ge.onchron.verif.whatsteps.tabs;

import java.util.List;
import java.util.stream.Collectors;

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.howsteps.comments.CommentSteps;
import com.ge.onchron.verif.model.UserCredentials;
import com.ge.onchron.verif.model.detailedDataItems.CommentItem;
import net.thucydides.core.annotations.Steps;

import static com.ge.onchron.verif.howsteps.comments.BaseCommentSteps.COMMENT_TEXT_COLUMN;
import static com.ge.onchron.verif.howsteps.comments.BaseCommentSteps.PATIENT_ID_COLUMN;

public class Comments {

    @Steps
    private CommentSteps commentSteps;

    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();


    @Given( "[API] the [$userCredentials] user has no comments for the following {patient|patients}: $examplesTable" )
    @When( "[API] the [$userCredentials] user deletes all patient and event comments for the following {patient|patients}: $examplesTable" )
    @Alias( "[API] the [$userCredentials] user has neither patient nor event comments for the following {patient|patients}: $examplesTable" )
    public void clearCommentsForPatient( UserCredentials userCredentials, ExamplesTable event ) {
        String patients = event.getRows().stream()
                               .map( m -> String.join( ", ", m.values() ) )
                               .collect( Collectors.joining( "\n" ) );
        testDefinitionSteps.addStep( STR."As user \{userCredentials.getAlias()} clear comments for patient:\n\{patients}" );
        // This method clears all patient and event comments too
        commentSteps.clearCommentsForPatients( userCredentials, event.getRows() );
        testDefinitionSteps.logEvidence( STR."User \{userCredentials.getAlias()} has neither patient nor event comments for the listed patients",
                "User has no comments for the listed patients (see previous logs)", true );
    }

    @Given( "[API] the below patient comments were added previously by the user [$userCredentials]: $patientCommentData" )
    @When( "[API] the below patient comments are added by the user [$userCredentials]: $patientCommentData" )
    public void clearAndAddPatientComments( UserCredentials userCredentials, ExamplesTable patientCommentData ) {
        String comments = patientCommentData.getRows().stream()
                                            .map( p -> STR."Patient: \{p.get( PATIENT_ID_COLUMN )}, Comment: \{p.get( COMMENT_TEXT_COLUMN )}" )
                                            .collect( Collectors.joining( "\n" ) );
        testDefinitionSteps.addStep( STR."Add following patient comments as \{userCredentials.getAlias()} user:\n\{comments}" );
        commentSteps.clearAndAddPatientComments( userCredentials, patientCommentData );
        testDefinitionSteps.logEvidence( "All comments were added successfully",
                "All comments were added (see previous logs)", true );
    }

    @Given( "[API] the id of the below patient comment is stored as \"$storedVariableName\": $patientCommentData" )
    @When( "[API] the id of the below patient comment is stored as \"$storedVariableName\": $patientCommentData" )
    public void storePatientCommentId( String storedVariableName, ExamplesTable patientCommentData ) {
        String comments = patientCommentData.getRows().stream()
                                            .map( p -> STR."Patient: \{p.get( PATIENT_ID_COLUMN )}, Comment: \{p.get( COMMENT_TEXT_COLUMN )}" )
                                            .collect( Collectors.joining( "\n" ) );
        testDefinitionSteps.addStep( STR."Store patient comment ID for the followin comments:\n\{comments}" );
        assertThat( "Only one item can be added to the table in this step definition", patientCommentData.getRows().size(), is( equalTo( 1 ) ) );
        commentSteps.storePatientCommentId( storedVariableName, patientCommentData.getRow( 0 ) );
        testDefinitionSteps.logEvidence( "All patient comment IDs are stored successfully",
                "All patient comment IDs are stored successfully (see previous logs)", true );
    }

    @Then( "the Comments view $isDisplayed displayed" )
    public void checkCommentsViewVisibility( Boolean isDisplayed ) {
        testDefinitionSteps.addStep( "Check comments view visibility" );
        commentSteps.checkCommentsViewVisibility( isDisplayed );
        testDefinitionSteps.logEvidence( STR."Comments view is \{isDisplayed ? "" : "not"} visible",
                STR."Comments view was \{isDisplayed ? "" : "not"} visible (see previous logs)", true );
    }

    @Given( "the following comments $areAvailable available on the Comments view: $comments" )
    @Then( "the following comments $areAvailable available on the Comments view: $comments" )
    public void checkComments( Boolean areAvailable, List<CommentItem> comments ) {
        testDefinitionSteps.addStep( "Check comments availability on the Comments view" );
        commentSteps.checkComments( areAvailable, comments );
        testDefinitionSteps.logEvidence( STR."The comments : \{comments.toString()} are \{areAvailable ? "" : "not"} avilable",
                STR."Comments are \{areAvailable ? "" : "not"} available on the view (check previous logs)", true );
    }

    @Then( "the Comments view contains the comments in the following order: $comments" )
    public void checkSortedComments( List<CommentItem> comments ) {
        testDefinitionSteps.addStep( "Check comments order on the Comments view" );
        commentSteps.checkSortedComments( comments );
        testDefinitionSteps.logEvidence( STR."Comments are present in the following order:\{
                        comments.stream().map( CommentItem::toString ).collect( Collectors.joining( "\n" ) )}",
                "Comments were present in expected order (see previous logs)", true );
    }

    @Given( "the comment input box is available on the Comments view" )
    @Then( "the comment input box is available on the Comments view" )
    public void checkCommentInputBoxVisibility() {
        testDefinitionSteps.addStep( "Check comment input box visibility" );
        commentSteps.checkCommentInputBoxVisibility( true );
        testDefinitionSteps.logEvidence(
                "Comment input box is visible on the comments view}",
                "Comment input box was visible on the comments view (see previous logs)", true );
    }

    @Given( "comment $isAvailable available on the Comments view" )
    @Then( "comment $isAvailable available on the Comments view" )
    public void checkCommentInputBoxVisibility( Boolean isAvailable ) {
        testDefinitionSteps.addStep( "Check comment input box visibility" );
        commentSteps.checkCommentAvailability( isAvailable );
        testDefinitionSteps.logEvidence( STR."Comment input box is '\{isAvailable ? "" : "not"}' available on the Comments view",
                STR."Comment input box was '\{isAvailable ? "" : "not"}' on the Comments view (see previous logs)", true );
    }

    @When( "the user types the following text into the comment box on the Comments view: \"$commentText\"" )
    public void typeComment( String commentText ) {
        testDefinitionSteps.addStep( STR."Type comment: \{commentText} into the comment box" );
        commentSteps.typeComment( commentText );
        testDefinitionSteps.logEvidence( STR."The text was written to the comment box on Comments view: \{commentText}",
                "Comment is typed into the box", true );
    }

    @When( "the user types the text from $fileName file into the comment input box on the Comments view" )
    public void typeCommentFromFile( String fileName ) {
        testDefinitionSteps.addStep( STR."Type comment from file: \{fileName}" );
        commentSteps.typeCommentFromFile( fileName );
        testDefinitionSteps.logEvidence( STR."The text from \{fileName} was written to the comment input box successfully",
                "Comment written from file successfully (check previous logs)", true );
    }

    @When( "the user appends the following text to the comment in the comment input box on the Comments view: \"$commentText\"" )
    public void appendCommentText( String commentText ) {
        testDefinitionSteps.addStep( STR."Append text: \{commentText} to existing comment" );
        commentSteps.continueTypeComment( commentText );
        testDefinitionSteps.logEvidence( STR."Text \{commentText} appended successfully to existing comment",
                "Text appended to comment", true );
    }

    @When( "the user clicks on the send button next to the comment box on the Comments view" )
    public void clickSendPatientComment() {
        testDefinitionSteps.addStep( "Click on send button next to the comment box" );
        commentSteps.clickSendComment();
        testDefinitionSteps.logEvidence( "The send button was successfully clicked", "Button clicked and comment sent (check previous logs)", true );
    }

    @When( "the user clicks on the 'Delete' button in the menu of the following comment on the Comments view: $comment" )
    public void clickDeleteComment( CommentItem comment ) {
        testDefinitionSteps.addStep( STR."Click 'Delete' button in the menu of the following comment on the Comments view:\n \{comment}" );
        commentSteps.clickDeleteComment( comment );
        testDefinitionSteps.logEvidence( "Comment is deleted successfully", "Comment was deleted without errors", true );
    }

    @When( "the user clicks on the 'View on timeline' button in the footer of the following comment on the Comments view: $comment" )
    public void clickViewOnTimelineButton( CommentItem comment ) {
        testDefinitionSteps.addStep( "Clicks on the 'View on timeline' button in the footer of the following comment on the Comments " +
                STR."view: \{comment}" );
        commentSteps.clickViewOnTimelineButton( comment );
        testDefinitionSteps.logEvidence( "Clicked 'View on timeline' button successfully", "Button was clicked without errors", true );
    }

    @Then( "the comment character counter $isVisible visible on the Comments view" )
    public void checkCommentCharCounterVisibility( Boolean isVisible ) {
        boolean result = commentSteps.checkCommentCharCounterVisibility( isVisible );
        testDefinitionSteps.addStep( "Check comment char count visibility" );
        testDefinitionSteps.logEvidence( STR."The comment char count is visible: \{isVisible} as expected",
                STR."The comment char count is visible \{result}", result == isVisible );
    }

    @Then( "the comment character counter has te following text on the Comments view: \"$commentCharCounterMsg\"" )
    public void checkCommentCharCounterVisibility( String commentCharCounterMsg ) {
        testDefinitionSteps.addStep( STR."Check comment char counter has: \{commentCharCounterMsg}" );
        commentSteps.checkCommentCharCounterMessage( commentCharCounterMsg );
        testDefinitionSteps.logEvidence( STR."The \{commentCharCounterMsg} text is visible on the character counter in comments view",
                "The actual visible text is same as expected (check previous logs)", true );
    }

    @Given( "the send comment button $isEnabled enabled on the Comments view" )
    @Then( "the send comment button $isEnabled enabled on the Comments view" )
    public void checkPostButtonState( Boolean isEnabled ) {
        testDefinitionSteps.addStep( "Check post button state" );
        commentSteps.checkSendButtonState( isEnabled );
        testDefinitionSteps.logEvidence( STR."Post button state has the expected state: \{isEnabled}",
                "Button state set as expected (check previous logs)", true );
    }

    @When( "the user clicks on the 'Hide Timeline comments' on the Comments view" )
    @Alias( "the user clicks on the 'Hide Timeline comments' on the Comments view again" )
    public void clickHideTimelineComments() {
        testDefinitionSteps.addStep( "Click on the 'Hide Timeline comments' on the Comments view" );
        commentSteps.clickHideTimelineComments();
        testDefinitionSteps.logEvidence( "Clicked 'Hide Timeline comments' button successfully", "Button was clicked without errors", true );
    }

    @Then( "a warning is displayed for the comment with the following message on the Comments view: \"$warningMessage\"" )
    public void checkCommentWarning( String warningMessage ) {
        testDefinitionSteps.addStep( "Check comment warning message" );
        commentSteps.checkCommentWarning( warningMessage );
        testDefinitionSteps.logEvidence( STR."The comment has the following warning message: \{warningMessage}",
                "The comment actual warning message is same as expected (check previous logs)", true );
    }

    @Then( "the following information message is displayed on the Comments view: \"$message\"" )
    public void checkCommentsInfoMsg( String message ) {
        testDefinitionSteps.addStep( "Check info message in Comments view " );
        commentSteps.checkCommentsInfoMsg( message );
        testDefinitionSteps.logEvidence( STR."Message \{message} is displayed on the Comments view",
                STR."Message \{message} was displayed on the Comments view (see previous logs)", true );
    }

    @Then( "the following message is displayed on the empty Comments view: \"$message\"" )
    public void checkEmptyCommentsMsg( String message ) {
        testDefinitionSteps.addStep( "Check empty comments message" );
        commentSteps.checkEmptyCommentsMsg( message );
        testDefinitionSteps.logEvidence( STR."Check empty comments message is: \{message}",
                "Empty comments message has expected text (check previous logs)", true );
    }

    @Then( "the comment input box on the Comments view contains the following text: \"$commentText\"" )
    public void checkCommentTextInInputBox( String message ) {
        testDefinitionSteps.addStep( "Check the comment input box on the Comments view" );
        commentSteps.checkCommentTextInInputBox( message );
        testDefinitionSteps.logEvidence( STR."The comment input box on the Comments view contains the following text: \{message}",
                "Message text matches expected (see previous logs)", true );
    }

}
