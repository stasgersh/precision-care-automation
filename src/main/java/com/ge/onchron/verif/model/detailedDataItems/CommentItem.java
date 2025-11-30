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
package com.ge.onchron.verif.model.detailedDataItems;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.ge.onchron.verif.TestSystemParameters.ANY_VALUE_PLACEHOLDER;
import static com.ge.onchron.verif.TestSystemParameters.COMMENT_DATE_TIME_FORMAT;

@Setter
@Getter
@ToString
public class CommentItem extends DetailedDataItem {

    public static final int MAX_TIME_GAP_IN_SEC = 240;

    private String author;
    private String dateAndTime;
    private String content;
    private String footerBadge;
    private boolean hasFooterTimelineLink;
    private boolean isCollapsed;
    private String expandToggleText;

    public CommentItem( String content ) {
        this.content = content;
    }
    
    public CommentItem(CommentItem commentItem) {
        this.author = commentItem.author;
        this.dateAndTime = commentItem.dateAndTime;
        this.content = commentItem.content;
        this.footerBadge = commentItem.footerBadge;
        this.hasFooterTimelineLink = commentItem.hasFooterTimelineLink;
        this.isCollapsed = commentItem.isCollapsed;
        this.expandToggleText = commentItem.expandToggleText;
    }

    public boolean hasFooterTimelineLink() {
        return hasFooterTimelineLink;
    }

    public void hasFooterTimelineLink( boolean hasFooterTimelineLink ) {
        this.hasFooterTimelineLink = hasFooterTimelineLink;
    }

    @Override
    public String getValue() {
        return content;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !(o instanceof CommentItem) ) return false;
        CommentItem that = (CommentItem) o;
        return Objects.equals( getAuthor(), that.getAuthor() ) &&
                Objects.equals( getContent(), that.getContent() ) &&
                Objects.equals( getFooterBadge(), that.getFooterBadge() ) &&
                Objects.equals( hasFooterTimelineLink(), that.hasFooterTimelineLink() ) &&
                Objects.equals( isCollapsed(), that.isCollapsed() ) &&
                Objects.equals( getExpandToggleText(), that.getExpandToggleText() ) &&
                this.dateTimeInAllowedRange( that );
    }

    private boolean dateTimeInAllowedRange( CommentItem that ) {
        if ( getDateAndTime().equals( ANY_VALUE_PLACEHOLDER ) || that.getDateAndTime().equals( ANY_VALUE_PLACEHOLDER ) ) {
            return true;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( COMMENT_DATE_TIME_FORMAT );
        LocalDateTime thisDateAndTime = LocalDateTime.parse( getDateAndTime(), formatter );
        LocalDateTime thatDateAndTime = LocalDateTime.parse( that.getDateAndTime(), formatter );
        Duration duration = Duration.between( thisDateAndTime, thatDateAndTime );
        return duration.abs().toSeconds() < MAX_TIME_GAP_IN_SEC;
    }
}
