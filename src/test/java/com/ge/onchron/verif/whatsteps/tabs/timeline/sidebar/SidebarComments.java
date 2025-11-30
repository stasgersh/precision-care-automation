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
package com.ge.onchron.verif.whatsteps.tabs.timeline.sidebar;

import com.ge.onchron.verif.howsteps.*;

import java.util.List;
import java.util.stream.Collectors;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.ge.onchron.verif.howsteps.comments.SidebarCommentSteps;
import com.ge.onchron.verif.model.UserCredentials;
import com.ge.onchron.verif.model.detailedDataItems.CommentItem;
import net.thucydides.core.annotations.Steps;

public class SidebarComments {

    @Steps
    private SidebarCommentSteps sidebarCommentSteps;

    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Given( "[API] the [$userCredentials] user has no comments for the following {event|events}: $examplesTable" )
    public void clearCommentsForEvent( UserCredentials userCredentials, ExamplesTable event ) {
        testDefinitionSteps.addStep( "Check the comments for the events, were cleared" );
        sidebarCommentSteps.clearCommentsForEvent( userCredentials, event.getRows() );
        testDefinitionSteps.logEvidence(
                STR."User \{userCredentials.getAlias()} successfully cleared comments for the following event/s: \n\{
                        event.getRows().stream().map( Object::toString ).collect( Collectors.joining( "\\n" ) )}",
                "The comments were cleared.(Check previous logs for more info)", true );
    }

    @Given( "[API] the below event comments were added previously by the user [$userCredentials]: $eventCommentData" )
    @When( "[API] the below event comments are added by the user [$userCredentials]: $eventCommentData" )
    public void clearAndAddEventComments( UserCredentials userCredentials, ExamplesTable eventCommentData ) {
        testDefinitionSteps.addStep( "Check the event comments added by the user" );
        sidebarCommentSteps.clearAndAddEventComments( userCredentials, eventCommentData );
        testDefinitionSteps.logEvidence(
                STR."User \{userCredentials.getAlias()} successfully added the following event comments: \n\{
                        eventCommentData.getRows().stream().map( Object::toString ).collect( Collectors.joining( "\\n" ) )}",
                "Event comments added by the user.(Check previous logs for more info)"
                , true );
    }

    @Given( "[API] the id of the below event comment is stored as \"$storedVariableName\": $eventCommentData" )
    @When( "[API] the id of the below event comment is stored as \"$storedVariableName\": $eventCommentData" )
    public void storePatientCommentId( String storedVariableName, ExamplesTable eventCommentData ) {
        testDefinitionSteps.addStep( "Store event comment" );
        final int eventDataSize = eventCommentData.getRows().size();
        assertThat( "Only one item can be added to the table in this step definition",
                eventDataSize, is( equalTo( 1 ) ) );
        sidebarCommentSteps.storeEventCommentId( storedVariableName, eventCommentData.getRow( 0 ) );
        testDefinitionSteps.logEvidence(
                STR."The id of the event comment was stored successfully for the following event data: \n\{
                        eventCommentData.getRows().stream().map( Object::toString ).collect( Collectors.joining( "\\n" ) )}",
                STR."The id of the event comment was stored successfully",
                true );

    }

    @Given( "the sidebar contains the following comments: $comments" )
    @Then( "the sidebar contains the following comments: $comments" )
    public void checkCommentsOnSidebar( List<CommentItem> comments ) {
        testDefinitionSteps.addStep( "Check comments information on sidebar." );
        sidebarCommentSteps.checkComments( true, comments );
        testDefinitionSteps.logEvidence(
                STR."The following comments appeared on sidebar: \n\{
                        comments.stream().map( CommentItem::toString ).collect( Collectors.joining( "\\n" ) )}",
                "The comments appears on sidebar.(Check previous logs for more info)"
                , true );
    }

    @Then( "the sidebar contains the comments in the following order: $comments" )
    public void checkSortedCommentsOnSidebar( List<CommentItem> comments ) {
        testDefinitionSteps.addStep( "Check sorted comments on sidebar" );
        String commentsStr = comments.stream().map( CommentItem::toString ).collect( Collectors.joining( "\\n" ) );
        sidebarCommentSteps.checkSortedComments( comments );
        testDefinitionSteps.logEvidence(
                STR."Comments on sidebar were sorted in following order: \n\{
                        commentsStr }",
                "Comments on sidebar were sorted.(Check previous logs for more info)"
                , true );
    }

    @Given( "the comment input box is available on the sidebar" )
    @Then( "the comment input box is available on the sidebar" )
    public void checkCommentInputBoxVisibilityOnSidebar() {
        testDefinitionSteps.addStep( "Check the comment input box is available on the sidebar" );
        sidebarCommentSteps.checkCommentInputBoxVisibility( true );
        testDefinitionSteps.logEvidence( "The comment input box is available on the sidebar",
                "The comment input box is available on the sidebar (see previous logs)", true );
    }

    @Given( "comment $isAvailable available on the sidebar" )
    @Then( "comment $isAvailable available on the sidebar" )
    public void checkCommentInputBoxVisibilityOnSidebar( Boolean isAvailable ) {
        testDefinitionSteps.addStep( "Check comment input box visibility on the sidebar" );
        sidebarCommentSteps.checkCommentAvailability( isAvailable );
        testDefinitionSteps.logEvidence( STR."Comment input box is '\{isAvailable ? "" : "not"}' available on the Sidebar",
                STR."Comment input box was '\{isAvailable ? "" : "not"}' on the Sidebar (see previous logs)", true );
    }

    @When( "the user types the following text into the comment box on the sidebar: \"$commentText\"" )
    public void typeComment( String commentText ) {
        testDefinitionSteps.addStep( "Check that the user can type text into the comment box on the sidebar" );
        sidebarCommentSteps.typeComment( commentText );
        testDefinitionSteps.logEvidence(
                STR."The user successfully typed the following comment:\{commentText} into the comment box on the sidebar",
                "The comment was typed into the comment box on the sidebar.(Check previous logs for more info)"
                , true );
    }

    @When( "the user types the text from $fileName file into the comment input box on the sidebar" )
    public void typeCommentFromFile( String fileName ) {
        testDefinitionSteps.addStep( "Type text into the comment input box on the sidebar" );
        sidebarCommentSteps.typeCommentFromFile( fileName );
        testDefinitionSteps.logEvidence(
                STR."Content of file: \{fileName} was successfully typed into the comment input box on the sidebar",
                STR."Content of file \{fileName} was typed into the comment input box on the sidebar.(Check previous logs for more info)"
                , true );
    }

    @When( "the user appends the following text to the comment in the comment input box on the sidebar: \"$commentText\"" )
    public void appendCommentText( String commentText ) {
        testDefinitionSteps.addStep( "Append text to the comment in the comment input box on the sidebar" );
        sidebarCommentSteps.continueTypeComment( commentText );
        testDefinitionSteps.logEvidence(
                STR."The text:\{commentText} was appended successfully in the comment input box on the sidebar",
                "The text appended in the comment input box on the sidebar.(Check previous logs for more info)"
                , true );
    }

    @When( "the user clicks on the send button next to the comment box on the sidebar" )
    public void clickSendComment() {
        testDefinitionSteps.addStep( "Click on the send button next to the comment box on the sidebar" );
        sidebarCommentSteps.clickSendComment();
        testDefinitionSteps.logEvidence(
                "Send button was successfully clicked",
                "Send button was clicked",
                true );

    }

    @When( "the user clicks on the 'Delete' button in the menu of the following comment on the sidebar: $comment" )
    public void clickDeleteComment( CommentItem comment ) {
        testDefinitionSteps.addStep( "Click on the 'Delete' button on the sidebar" );
        sidebarCommentSteps.clickDeleteComment( comment );
        testDefinitionSteps.logEvidence(
                "'Delete' button on the sidebar was successfully clicked",
                "'Delete' button was clicked",
                true );
    }

    @Then( "the comment character counter $isVisible visible on the sidebar" )
    public void checkCommentCharCounterVisibility( Boolean isVisible ) {
        boolean result = sidebarCommentSteps.checkCommentCharCounterVisibility( isVisible );
        testDefinitionSteps.addStep( "Check the comment character counter visibility" );
        testDefinitionSteps.logEvidence( STR."Comment character counter is available on the side bar: \{isVisible} as expected",
                STR."The comment char count is visible \{result}", result == isVisible );
    }

    @Then( "the comment character counter has te following text on the sidebar: \"$commentCharCounterMsg\"" )
    public void checkCommentCharCounterVisibility( String commentCharCounterMsg ) {
        testDefinitionSteps.addStep( "Check comment character counter visibility" );
        sidebarCommentSteps.checkCommentCharCounterMessage( commentCharCounterMsg );
        testDefinitionSteps.logEvidence(
                STR."Comment character counter is available on the side bar with expected text:\{commentCharCounterMsg}",
                "The comment char count is visible",
                true );
    }

    @Given( "the send comment button $isEnabled enabled on the sidebar" )
    @Then( "the send comment button $isEnabled enabled on the sidebar" )
    public void checkPostButtonState( Boolean isEnabled ) {
        testDefinitionSteps.addStep( "Check post button state" );
        sidebarCommentSteps.checkSendButtonState( isEnabled );
        testDefinitionSteps.logEvidence(
                STR."The expected post button state is: \{isEnabled} ",
                STR."The observed post button state visibility is: \{isEnabled ? "" : "not"} (check previous logs)",
                true );
    }

    @Then( "a warning is displayed for the comment with the following message on the sidebar: \"$warningMessage\"" )
    public void checkCommentWarning( String warningMessage ) {
        testDefinitionSteps.addStep( "Check the warning message is displayed on the sidebar" );
        sidebarCommentSteps.checkCommentWarning( warningMessage );
        testDefinitionSteps.logEvidence(
                STR."The expected warning message: \{warningMessage} was displayed on the sidebar",
                "The warning message was displayed on the sidebar.(Check previous logs for more info)", true );

    }

    @Then( "the following comment information message is displayed on the sidebar: \"$message\"" )
    public void checkCommentsInfoMsg( String message ) {
        testDefinitionSteps.addStep( "Check the comment information message is displayed on the sidebar" );
        sidebarCommentSteps.checkCommentsInfoMsg( message );
        testDefinitionSteps.logEvidence(
                STR."The expected comment information was displayed on the sidebar with message: \{message}",
                "The comment information was displayed on the sidebar. (Check previous logs for more info)", true );
    }

    @Then( "the following message about not available comments is displayed on the sidebar: \"$message\"" )
    public void checkEmptyCommentsMsg( String message ) {
        testDefinitionSteps.addStep( "Check for empty comments message on the sidebar" );
        sidebarCommentSteps.checkEmptyCommentsMsg( message );
        testDefinitionSteps.logEvidence(
                STR."The message about not available comments was displayed on the sidebar as following: \{message}",
                "The message about not available comments was displayed on the sidebar. (Check previous logs for more info)", true );

    }

    @Then( "the comment input box on the sidebar contains the following text: \"$commentText\"" )
    public void checkCommentTextInInputBox( String message ) {
        testDefinitionSteps.addStep( "Check the comment input box on the sidebar contains specific text" );
        sidebarCommentSteps.checkCommentTextInInputBox( message );
        testDefinitionSteps.logEvidence(
                STR."The comment input box on the sidebar contained the following text:\{message}",
                "The comment input box on the sidebar contained the message. (Check previous logs for more info)", true );
    }

    @Given( "the sidebar header contains the following comments information: \"$commentText\"" )
    @Then( "the sidebar header contains the following comments information: \"$commentText\"" )
    public void checkCommentInfoInHeader( String commentText ) {
        testDefinitionSteps.addStep( "Check the sidebar header contains specific comments information" );
        sidebarCommentSteps.checkCommentInfoInHeader( commentText );
        testDefinitionSteps.logEvidence(
                STR."The sidebar header contains the following comments information: \{commentText}",
                "The comment input box on the sidebar contained the message. (Check previous logs for more info)", true );
    }
}