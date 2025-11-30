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
package com.ge.onchron.verif.pages.utils;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ge.onchron.verif.model.detailedDataItems.CommentInputBoxItem;
import com.ge.onchron.verif.model.detailedDataItems.CommentItem;

import static com.ge.onchron.verif.pages.utils.PageUtils.currentlyContainsWebElements;

public class CommentUtils {

    private static final String COMMENT_HEADER_SELECTOR = "[class^='Comments-module_comment-header']";
    private static final String COMMENT_CONTENT_SELECTOR = "[class*='Comments-module_comment-content']";
    private static final String COMMENT_EXPAND_TOGGLE_SELECTOR = "[class*='Comments-module_comment-toggle']";
    private static final String COMMENT_COLLAPSED_SELECTOR = "[class*='Comments-module_line-clamp']";
    private static final String COMMENT_AUTHOR_SELECTOR = "[class*='Comments-module_comment-author']";
    private static final String COMMENT_TIME_SELECTOR = "[class*='Comments-module_comment-time']";
    private static final String COMMENT_FOOTER_SELECTOR = "[class*='Comments-module_comment-footer']";
    private static final String COMMENT_BADGE_SELECTOR = "[class*='Label-module_container']";
    private static final String VIEW_ON_TIMELINE_LINK_TEXT = "View on timeline";

    public static CommentItem createCommentItem( WebElement commentParentElement ) {
        WebElement commentHeader = commentParentElement.findElement( By.cssSelector( COMMENT_HEADER_SELECTOR ) );
        WebElement commentContent = commentParentElement.findElement( By.cssSelector( COMMENT_CONTENT_SELECTOR ) );
        boolean isCollapsed = !commentContent.findElements( By.cssSelector( COMMENT_COLLAPSED_SELECTOR )  ).isEmpty();
        List<WebElement> commentExpandButton = commentParentElement.findElements( By.cssSelector( COMMENT_EXPAND_TOGGLE_SELECTOR ) );

        String author = commentHeader.findElement( By.cssSelector( COMMENT_AUTHOR_SELECTOR ) ).getText();
        String dateAndTime = commentHeader.findElement( By.cssSelector( COMMENT_TIME_SELECTOR ) ).getText();
        String content = commentContent.getText();

        CommentItem comment = new CommentItem( content );
        comment.setAuthor( author );
        comment.setDateAndTime( dateAndTime );
        comment.setCollapsed( isCollapsed );
        comment.setExpandToggleText( commentExpandButton.isEmpty() ? null : commentExpandButton.getFirst().getText());

        if ( currentlyContainsWebElements( commentParentElement, By.cssSelector( COMMENT_FOOTER_SELECTOR ) ) ) {
            WebElement commentFooter = commentParentElement.findElement( By.cssSelector( COMMENT_FOOTER_SELECTOR ) );
            if ( currentlyContainsWebElements( commentFooter, By.cssSelector( COMMENT_BADGE_SELECTOR ) ) ) {
                WebElement badge = commentFooter.findElement( By.cssSelector( COMMENT_BADGE_SELECTOR ) );
                comment.setFooterBadge( badge.getText() );
            }
            boolean hasFooterTimelineLink = currentlyContainsWebElements( commentFooter, By.linkText( VIEW_ON_TIMELINE_LINK_TEXT ) );
            comment.hasFooterTimelineLink( hasFooterTimelineLink );
        }

        return comment;
    }

    public static CommentInputBoxItem createCommentInputBoxItem( WebElement commentInputBoxElement ) {
        WebElement textarea = commentInputBoxElement.findElement( By.tagName( "textarea" ) );
        String inputText = textarea.getText();
        CommentInputBoxItem inputBox = new CommentInputBoxItem( inputText );
        if ( inputText.isEmpty() ) {
            String placeholderText = textarea.getAttribute( "placeholder" );
            inputBox.setText( placeholderText );
            inputBox.setPlaceholderText( true );
        }
        return inputBox;
    }

}
