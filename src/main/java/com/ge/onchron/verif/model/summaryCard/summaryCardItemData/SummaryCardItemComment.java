/*
 * -GEHC CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2023, 2023, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE HealthCare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 */
package com.ge.onchron.verif.model.summaryCard.summaryCardItemData;

import java.util.List;
import java.util.Objects;

import com.ge.onchron.verif.model.detailedDataItems.CommentItem;
import com.ge.onchron.verif.utils.CommentUtils;

public class SummaryCardItemComment extends SummaryCardItemData {

    private final List<CommentItem> comments;

    public SummaryCardItemComment( List<CommentItem> comments ) {
        this.comments = comments;
    }

    @Override
    public Object getData() {
        return comments;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !(o instanceof SummaryCardItemComment) ) return false;
        SummaryCardItemComment that = (SummaryCardItemComment) o;
        comments.forEach( CommentUtils::changeParametersInComment );
        that.comments.forEach( CommentUtils::changeParametersInComment );
        return Objects.equals( comments, that.comments );
    }

    @Override
    public String toString() {
        return "SummaryCardItemDataTable{" +
                "comments=" + comments +
                '}';
    }

}
