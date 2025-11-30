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
package com.ge.onchron.verif.pages.elements;

import java.util.List;
import java.util.stream.Collectors;

import com.ge.onchron.verif.model.summaryCard.SummaryCard;
import com.ge.onchron.verif.model.summaryCard.SummaryCardItem;
import com.ge.onchron.verif.pages.SimplePage;

public class SummaryCardElement extends SimplePage {

    private String group;
    private Integer cardIndexInGroup;
    private List<SummaryCardItemElement> summaryItemElements;

    public String getGroup() {
        return group;
    }

    public void setGroup( String group ) {
        this.group = group;
    }

    public Integer getCardIndexInGroup() {
        return cardIndexInGroup;
    }

    public void setCardIndexInGroup( Integer cardIndexInGroup ) {
        this.cardIndexInGroup = cardIndexInGroup;
    }

    public List<SummaryCardItemElement> getSummaryItemElements() {
        return summaryItemElements;
    }

    public void setSummaryItemElements( List<SummaryCardItemElement> summaryItemElements ) {
        this.summaryItemElements = summaryItemElements;
    }

    public SummaryCard convertToSummaryCardModel() {
        SummaryCard summaryCard = new SummaryCard();
        summaryCard.setGroup( this.getGroup() );
        summaryCard.setCardIndexInGroup( this.cardIndexInGroup );
        List<SummaryCardItem> summaryCardItems = this.getSummaryItemElements()
                                                     .stream()
                                                     .map( SummaryCardItemElement::getSummaryCardItem )
                                                     .collect( Collectors.toList() );
        summaryCard.setSummaryItems( summaryCardItems );
        return summaryCard;
    }

}
