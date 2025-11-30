/*
 * -GEHC CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2024, 2024, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE HealthCare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 */
package com.ge.onchron.verif.model.treatmentAndResponse;


import java.util.List;
import java.util.Objects;

import com.ge.onchron.verif.model.Label;
import net.serenitybdd.core.pages.WebElementFacade;

public class StudyCard {

    private String studyName;
    private String badge;
    private String date;
    private String emptyText;
    private boolean isEmpty;
    private List<Label> labelList;
    private List<String> buttonTextList;
    private List<String> clpTextList;
    private WebElementFacade cardElement;

    public String getEmptyText() {
        return emptyText;
    }

    public void setEmptyText( String emptyText ) {
        this.emptyText = emptyText;
        isEmpty = true;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public String getStudyName() {
        return studyName;
    }

    public void setStudyName( String studyName ) {
        this.studyName = studyName;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge( String badge ) {
        this.badge = badge;
    }

    public String getDate() {
        return date;
    }

    public void setDate( String date ) {
        this.date = date;
    }

    public List<Label> getLabelList() {
        return labelList;
    }

    public void setLabelList( List<Label> labelList ) {
        this.labelList = labelList;
    }

    public List<String> getButtonTextList() {
        return buttonTextList;
    }

    public void setButtonTextList( List<String> buttonTextList ) {
        this.buttonTextList = buttonTextList;
    }

    public List<String> getClpTextList() {
        return clpTextList;
    }

    public void setClpTextList( List<String> clpTextList ) {
        this.clpTextList = clpTextList;
    }

    public WebElementFacade getCardElement() {
        return cardElement;
    }

    public void setCardElement( WebElementFacade cardElement ) {
        this.cardElement = cardElement;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !(o instanceof StudyCard) ) return false;
        StudyCard that = (StudyCard) o;
        return isEmpty == that.isEmpty &&
                Objects.equals( studyName, that.studyName ) &&
                Objects.equals( date, that.date ) &&
                Objects.equals( emptyText, that.emptyText ) &&
                Objects.equals( labelList, that.labelList ) &&
                Objects.equals( clpTextList, that.clpTextList );
    }

    @Override
    public String toString() {
        return STR."StudyCard{name=\{studyName}, date='\{date}\{'\''}, emptyText='\{emptyText}\{'\''}, labelList='\{labelList}\{'\''}, clpTextList='\{clpTextList}\{'\''}\{'}'}";
    }
}
