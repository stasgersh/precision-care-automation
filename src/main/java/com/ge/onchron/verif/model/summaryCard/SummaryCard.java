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
package com.ge.onchron.verif.model.summaryCard;

import java.util.List;

public class SummaryCard {

    private String group;
    private int cardIndexInGroup;
    List<SummaryCardItem> summaryItems;

    public String getGroup() {
        return group;
    }

    public void setGroup( String group ) {
        this.group = group;
    }

    public int getCardIndexInGroup() {
        return cardIndexInGroup;
    }

    public void setCardIndexInGroup( Integer cardIndexInGroup ) {
        this.cardIndexInGroup = cardIndexInGroup;
    }

    public List<SummaryCardItem> getSummaryCardItems() {
        return summaryItems;
    }

    public void setSummaryItems( List<SummaryCardItem> summaryItems ) {
        this.summaryItems = summaryItems;
    }

    @Override
    public String toString() {
        return "SummaryCard{" +
                "group='" + group + '\'' +
                ", cardIndexInGroup=" + cardIndexInGroup +
                ", summaryItems=" + summaryItems +
                '}';
    }

}
