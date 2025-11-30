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
package com.ge.onchron.verif.pages.sections;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.detailedDataItems.CommentItem;
import com.ge.onchron.verif.pages.SimplePage;

import static com.ge.onchron.verif.pages.utils.CommentUtils.createCommentItem;
import static com.ge.onchron.verif.pages.utils.PageUtils.currentlyContainsWebElements;
import static com.ge.onchron.verif.pages.utils.PageUtils.executeWithZeroImplicitTimeout;
import static com.ge.onchron.verif.pages.utils.PageUtils.waitUntilElementDisappears;
import static com.ge.onchron.verif.utils.Utils.waitMillis;
import static com.ge.onchron.verif.utils.Utils.waitingTimeExpired;

public class Comments extends SimplePage {

    private static final Logger LOGGER = LoggerFactory.getLogger( Comments.class );

    private final String COMMENT_HEADER_SELECTOR = "[class^='Comments-module_comment-header']";
    private final String COMMENTS_CHAR_COUNTER_SELECTOR = "[class*='Comments-module_char-counter']";
    private final String COMMENT_WARNING_TEXT_SELECTOR = "[class*='Comments-module_warning-color'] > span";
    private final String COMMENT_FOOTER_SELECTOR = "[class*='Comments-module_comment-footer']";
    private final String VIEW_ON_TIMELINE_LINK_TEXT = "View on timeline";

    public final String COMMENT_INPUT_BOX_CLASS = "Comments-module_post-comment-container";

    public static final int MAX_WAITING_TIME_FOR_SENDING_DELETING_COMMENT_IN_MILLIS = 60000;
    public static final String SENDING_DELETING_COMMENT_INDICATOR_CSS_SELECTOR = "[class*='indicator']";

    @FindBy( xpath = "./.." )
    private WebElement commentSectionParent;

    @FindBy( css = "[class^='" + COMMENT_INPUT_BOX_CLASS + "']" )
    private WebElement commentInputBox;

    @FindBy( css = "[class*='Comments-module_comment-container']" )
    private List<WebElement> comments;

    @FindBy( css = "[class^='CommentsElement-module_comment-info']" )
    private WebElement commentsInfoBanner;

    @FindBy( css = "[class^='CommentsElement-module_empty']" )
    private WebElement emptyCommentsInfo;

    @FindBy( css = SENDING_DELETING_COMMENT_INDICATOR_CSS_SELECTOR )
    private List<WebElement> sendDeleteIndicators;

    private List<WebElement> getCommentsInfoBannerElements() {
        // 0. "Only you can see these comments..." info message
        // 1. Hide timeline comments switch (available only on Comments tab)
        return commentsInfoBanner.findElements( By.xpath( "child::*" ) );
    }

    private List<WebElement> getCommentsElements() {
        List<WebElement> commentsElements = new ArrayList<>();
        executeWithZeroImplicitTimeout( () -> commentsElements.addAll( comments ) );
        return commentsElements;
    }

    private WebElement getSendCommentButton() {
        return commentInputBox.findElement( By.className( "button" ) );
    }

    private List<WebElement> getSendDeleteIndicators() {
        List<WebElement> localSendDeleteIndicators = new ArrayList<>();
        executeWithZeroImplicitTimeout( () -> localSendDeleteIndicators.addAll( sendDeleteIndicators ) );
        return localSendDeleteIndicators;
    }

    public WebElement getTextArea() {
        return commentInputBox.findElement( By.tagName( "textarea" ) );
    }

    public void typeComment( String commentText ) {
        WebElement textArea = getTextArea();
        textArea.clear();
        textArea.sendKeys( commentText );
    }

    public void continueTypeComment( String commentText ) {
        getTextArea().sendKeys( commentText );
    }

    public String getCommentInputBoxText() {
        return getTextArea().getText();
    }

    public void clickSendComment() {
        getSendCommentButton().click();
    }

    public boolean getCommentInputBoxVisibility() {
        return commentInputBox.isDisplayed();
    }

    public void clickDeleteComment( CommentItem comment ) {
        WebElement commentToDelete = getCommentsElements().stream()
                                                          .filter( item -> createCommentItem( item ).equals( comment ) )
                                                          .findFirst()
                                                          .orElseThrow( () -> new RuntimeException( "The following comment was not found: \n" + comment ) );
        WebElement commentHeader = commentToDelete.findElement( By.cssSelector( COMMENT_HEADER_SELECTOR ) );
        LOGGER.info( STR."Found comment: \{comment}" ); // will throw before this line if not found
        commentHeader.findElement( By.cssSelector( "[id^='More']" ) ).click();
        commentHeader.findElement( By.cssSelector( "[class*='Comments-module_more-menu']" ) ).findElement( By.tagName( "button" ) ).click();
    }

    public List<CommentItem> getComments() {
        List<CommentItem> commentItems = new ArrayList<>();
        getCommentsElements().forEach( comment -> commentItems.add( createCommentItem( comment ) ) );
        return commentItems;
    }

    public boolean getCommentCharCounterVisibility() {
        return currentlyContainsWebElements( commentInputBox, By.cssSelector( COMMENTS_CHAR_COUNTER_SELECTOR ) );
    }

    public boolean getSendButtonState() {
        return commentInputBox.findElement( By.className( "button" ) ).isEnabled();
    }

    public String getCommentCharCounterMsg() {
        return commentInputBox.findElement( By.cssSelector( COMMENTS_CHAR_COUNTER_SELECTOR ) ).getText();
    }

    public boolean isCommentWarningVisible() {
        return currentlyContainsWebElements( commentInputBox, By.cssSelector( COMMENT_WARNING_TEXT_SELECTOR ) );
    }

    public String getCommentWarningMsg() {
        return commentInputBox.findElement( By.cssSelector( COMMENT_WARNING_TEXT_SELECTOR ) ).getText();
    }

    public String getCommentsInfoText() {
        return getCommentsInfoBannerElements().get( 0 ).getText();
    }

    public WebElement hideTimelineCommentsSwitch() {
        if ( !(getCommentsInfoBannerElements().size() >= 2) ) {
            fail( "'Hide Timeline comments' switch is not available." );
        }
        return getCommentsInfoBannerElements().get( 1 );
    }

    public String getEmptyCommentsText() {
        return emptyCommentsInfo.getText();
    }

    public void waitForSendingComment() {
        waitUntilElementDisappears( commentSectionParent,
                By.cssSelector( SENDING_DELETING_COMMENT_INDICATOR_CSS_SELECTOR ),
                MAX_WAITING_TIME_FOR_SENDING_DELETING_COMMENT_IN_MILLIS,
                String.format( "Sending comment indicator did not disappear in %s milliseconds", MAX_WAITING_TIME_FOR_SENDING_DELETING_COMMENT_IN_MILLIS ) );
        LOGGER.info( "The comment was sent successfully" );
    }

    public void waitForNumberOfVisibleComments( int expectedComments ) {
        Wait<WebDriver> wait = new FluentWait<>( getDriver() )
                .withTimeout( Duration.ofSeconds( 5 ) )
                .pollingEvery( Duration.ofMillis( 100 ) )
                .ignoring( NoSuchElementException.class );
        try {
            wait.until( ExpectedConditions.numberOfElementsToBe( By.cssSelector( "[class*='Comments-module_comment-container']" ), expectedComments ) );
        } catch ( TimeoutException e ) {
            fail( "Number of comments does not match expected after save/delete button was clicked" );
        }
    }

    public void waitForDeletingComment() {
        long startTime = System.currentTimeMillis();
        boolean waitingTimeExpired;
        List<WebElement> visibleSendDeleteIndicators;
        LOGGER.info( "Wait for deletion." );
        do {
            visibleSendDeleteIndicators = getSendDeleteIndicators();
            LOGGER.info( "Number of classes which contains delete 'indicator': " + visibleSendDeleteIndicators.size() );
            waitingTimeExpired = waitingTimeExpired( startTime, MAX_WAITING_TIME_FOR_SENDING_DELETING_COMMENT_IN_MILLIS );
        } while ( !visibleSendDeleteIndicators.isEmpty() && !waitingTimeExpired );

        if ( waitingTimeExpired ) {
            fail( String.format( "Deleting comment indicators did not disappear in %s milliseconds", MAX_WAITING_TIME_FOR_SENDING_DELETING_COMMENT_IN_MILLIS ) );
        }
        waitMillis( 400 ); // small additional wait is needed to finish rendering the page
    }

    public void clickViewOnTimelineButton( CommentItem comment ) {
        WebElement requiredComment = getCommentsElements().stream()
                                                          .filter( item -> createCommentItem( item ).equals( comment ) )
                                                          .findFirst()
                                                          .orElseThrow( () -> new RuntimeException( STR."The following comment was not found: \n\{comment}" ) );
        if ( !currentlyContainsWebElements( requiredComment, By.cssSelector( COMMENT_FOOTER_SELECTOR ) ) ) {
            fail( STR."Cannot navigate to timeline, footer not available for comment: \{comment}" );
        }
        WebElement commentFooter = requiredComment.findElement( By.cssSelector( COMMENT_FOOTER_SELECTOR ) );
        LOGGER.info( STR."Comment found: \{comment}" ); // will throw before this line if not found
        WebElement navigateToTimeline = commentFooter.findElement( By.linkText( VIEW_ON_TIMELINE_LINK_TEXT ) );
        navigateToTimeline.click();
    }
}
