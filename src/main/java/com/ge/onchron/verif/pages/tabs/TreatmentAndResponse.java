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
package com.ge.onchron.verif.pages.tabs;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.Color;

import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.EmptyStateMessage;
import com.ge.onchron.verif.model.Label;
import com.ge.onchron.verif.model.treatmentAndResponse.StudyCard;
import com.ge.onchron.verif.model.treatmentAndResponse.TreatmentCard;
import com.ge.onchron.verif.pages.SimplePage;
import com.ge.onchron.verif.pages.elements.tooltip.TooltipElement;
import com.ge.onchron.verif.utils.TextUtils;
import java.util.ArrayList;
import java.util.List;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.pages.utils.PageElementUtils.moveToElement;


public class TreatmentAndResponse extends SimplePage {

    private TooltipElement tooltip;

    private final String LOADING_SKELETON_SELECTOR = "[class*='TreatmentResponseSkeleton-module_container_']";
    private final String TREATMENT_RESPONSE_MODULE_SELECTOR = "[class*='TreatmentResponse-module_container_']";
    private final String TREATMENT_MODULE_SELECTOR = "[class*='TreatmentItem-module_container_']";
    private final String STUDYITEM_MODULE_SELECTOR = "[class*='StudyItem-module_container_']";
    private final String OPEN_COMPLETE_REPORT_BUTTON_SELECTOR = "//*[contains(@class, 'button') and contains(normalize-space(text()), 'Open complete report')]";
    private final String BUTTON_PLACEHOLDER = "BUTTON_PLACEHOLDER";
    private final String GENERAL_BUTTON_SELECTOR = ".//button[contains(@class, 'button') and contains(., 'BUTTON_PLACEHOLDER')]";
    private final String LABEL_LIST_ITEM_SELECTOR = "[class*='label-list-item']";
    private final String STUDY_CARD_NLP_TEXT = "[class*='Nlp-module_content']";
    private final String STUDY_CARD_NLP_MODULE = "div[class*='Nlp-module_no-print']";
    private final String MEASUREMENT_TABLE_SELECTOR = "[class*='ResponseColumn-module_measurements_']";
    private final String TRENDS_CHART_SELECTOR = "[class*='ResponseColumn-module_trends_']";
    private final String MULTILINE_CHART_LEGEND_SELECTOR = "[class*='MultiLineChart-module_legend-wrapper_']";
    private final String TREATMENT_MODULE_HEADER = "[class*='TreatmentAccording-module_header']";
    private final String STUDY_LIST_SELECTOR = "[class*='ImagingStudySelector-module_container']";
    private final String EMPTY_STATE_MODULE_SELECTOR = "[class*='EmptyStudies-module_content_']";
    private final String EMPTY_STATE_TITLE_SELECTOR = "[class*='EmptyStudies-module_title_']";
    private final String EMPTY_STATE_MESSAGE_SELECTOR = "[class*='EmptyStudies-module_description_']";
    private final String SITE_NAVIGATION_BUTTON_SELECTOR = "[class*='SiteNavigationButton-module_site-navigation-button']";
    private final String MULTILINE_CHART_SELECTOR = "[class*='MultiLineChart-module_container']";

    @FindBy( css = TREATMENT_RESPONSE_MODULE_SELECTOR )
    private WebElementFacade tRVContainer;

    @FindBy( css = TREATMENT_MODULE_SELECTOR )
    private List<WebElementFacade> treatmentModules;

    @FindBy( css = STUDYITEM_MODULE_SELECTOR )
    private List<WebElementFacade> studyItemModules;

    @FindBy( css = LABEL_LIST_ITEM_SELECTOR )
    private List<WebElementFacade> labelList;

    @FindBy( css = STUDY_CARD_NLP_TEXT )
    private List<WebElementFacade> studyCardNLPTextList;

    @FindBy( css = MEASUREMENT_TABLE_SELECTOR )
    private WebElementFacade measurementTable;

    @FindBy( css = STUDY_LIST_SELECTOR )
    private WebElementFacade studyListSelector;

    @FindBy( css = TRENDS_CHART_SELECTOR )
    private WebElementFacade trendsChart;

    @FindBy( css = MULTILINE_CHART_LEGEND_SELECTOR )
    private List<WebElementFacade> legendList;

    @FindBy( css = TREATMENT_MODULE_HEADER )
    private WebElementFacade treatmentModuleHeader;

    @FindBy( css = EMPTY_STATE_MODULE_SELECTOR )
    private WebElementFacade emptyStateModule;

    @FindBy( css = SITE_NAVIGATION_BUTTON_SELECTOR )
    private WebElementFacade siteNavigationButton;


    public void waitUntilLoadingSkeletonDisappears() {
        waitUntilLoadingSkeletonDisappears( tRVContainer, By.cssSelector( LOADING_SKELETON_SELECTOR ) );
    }

    public void clickTreatmentModuleHeaderBtn( String btnName ) {
        WebElementFacade btn = treatmentModuleHeader.then( By.cssSelector( "button" ) );
        if ( getTreatmentCardState().equals( btnName ) ) {
            try {
                elementClicker( btn );
            } catch ( NoSuchElementException e ) {
                fail( STR."Could not find the \{btnName} button" );
            }
        }
    }

    public boolean isVisible() {
        return tRVContainer.isCurrentlyVisible();
    }

    private List<TreatmentCard> getTreatmentCards() {
        List<TreatmentCard> treatmentCards = new ArrayList<>();
        treatmentModules.forEach( moduleItem -> {
            TreatmentCard card = new TreatmentCard();
            List<WebElementFacade> cardChildElements = moduleItem.thenFindAll( By.cssSelector( "*" ) );
            card.setCardElement( moduleItem );
            cardChildElements.forEach( childElement -> {
                String className = childElement.getAttribute( "class" );
                if ( className != null ) {
                    if ( className.contains( "EmptyMessage-module" ) ) {
                        card.setEmptyText( childElement.getText() );
                    } else if ( className.contains( "title" ) ) {
                        card.setCardTitle( childElement.getText() );
                    } else if ( className.contains( "text" ) ) {
                        card.setDate( childElement.getText() );
                    } else if ( className.contains( "paragraph" ) ) {
                        card.setEventName( childElement.getText() );
                    }
                }
            } );
            treatmentCards.add( card );
        } );
        return treatmentCards;
    }

    public TreatmentCard getTreatmentCardByTitle( String cardTitle ) {
        List<TreatmentCard> treatmentCards = getTreatmentCards();
        for ( TreatmentCard card : treatmentCards ) {
            if ( card.getCardTitle().equals( cardTitle ) ) {
                return card;
            }
        }
        return null;
    }

    public void clickOpenReportForTreatmentCard( TreatmentCard expectedCard ) {
        try {
            WebElementFacade openCompleteReportButton = expectedCard.getCardElement().find( By.xpath( OPEN_COMPLETE_REPORT_BUTTON_SELECTOR ) );
            elementClicker( openCompleteReportButton );
        } catch ( NoSuchElementException e ) {
            fail( "Could not find the \"Open complete report\" button for " + expectedCard.toString() );
        }
    }

    public void clickButtonForStudy( StudyCard studyCard, String buttonText ) {
        try {
            WebElementFacade button = studyCard.getCardElement().then( By.xpath( GENERAL_BUTTON_SELECTOR.replace( BUTTON_PLACEHOLDER, buttonText ) ) );
            elementClicker( button );
        } catch ( NoSuchElementException e ) {
            fail( "Could not find the \"" + buttonText + "\" button for " + studyCard.toString() );
        }
    }

    public String getAlertMsg() {
        return studyListSelector.then( By.xpath( ".//*[contains(@class, 'module_reversed-alert')]" ) ).getText();
    }

    public List<StudyCard> getImagingStudyCards() {
        List<StudyCard> studyCards = new ArrayList<>();
        studyItemModules.forEach( moduleItem -> {
            StudyCard card = new StudyCard();
            List<String> buttonTextList = new ArrayList<>();
            List<WebElementFacade> cardChildElements = moduleItem.thenFindAll( By.cssSelector( "*" ) );
            cardChildElements.forEach( childElement -> {
                String className = childElement.getAttribute( "class" );
                if ( className != null ) {
                    if ( className.contains( "empty-content" ) ) {
                        card.setEmptyText( childElement.getText() );
                    } else if ( className.contains( "module_badge" ) ) {
                        card.setBadge( childElement.getText() );
                    } else if ( className.contains( "module_label-list" ) ) {
                        card.setLabelList( getAllLabelsOfElement( childElement ) );
                    } else if ( className.contains( "module_date" ) ) {
                        card.setDate( childElement.getText() );
                    } else if ( className.contains( "module_title" ) ) {
                        card.setStudyName( childElement.getText() );
                    } else if ( className.contains( "button" ) ) {
                        buttonTextList.add( childElement.getText() );
                    }
                }
            } );
            card.setCardElement( moduleItem );
            card.setButtonTextList( buttonTextList );
            studyCards.add( card );
        } );
        return studyCards;
    }

    public StudyCard getSelectedStudyDetailsByBadge( String badgeText ) {
        return getImagingStudyCards().stream().filter( card -> card.getBadge().equals( badgeText ) ).findFirst().get();
    }

    public String getTreatmentCardState() {
        return treatmentModuleHeader.then( By.cssSelector( "button" ) ).getText();
    }

    private List<Label> getAllLabelsOfElement( WebElementFacade labelListElement ) {
        List<Label> labelList = new ArrayList<>();
        List<WebElementFacade> labelElementList = labelListElement.thenFindAll( By.cssSelector( LABEL_LIST_ITEM_SELECTOR ) );
        if ( labelElementList.isEmpty() ) {
            return null;
        }
        labelElementList.forEach( labelElement -> {
            Label label = new Label( labelElement.getText() );
            Color color = Color.fromString( labelElement.find( By.xpath( ".//span" ) ).getCssValue( "background-color" ) );
            label.setColor( color.asRgb() );
            labelList.add( label );
        } );

        return labelList;
    }

    public void hoverOnAIGeneratedTextOfStudy( String studyTitle ) {
        WebElementFacade studyCard = studyItemModules.stream()
                                                     .filter(card -> card.then(By.cssSelector(".StudyItem-module_title_CRojZ")).getText().equals(studyTitle))
                                                     .findFirst()
                                                     .orElseThrow(() -> new RuntimeException("Study card with title '" + studyTitle + "' not found"));

        WebElementFacade nlpContent = studyCard.then(By.cssSelector(".nlp-text.Nlp-module_content_ujjYO"));
        moveToElement(nlpContent);
    }

    public void hoverOnLegendOnResponseView( String legendName ) {
        WebElementFacade legend = legendList.stream()
                                            .filter( l -> l.findElement( By.cssSelector( "[class*='Label-module_text-wrap_']" ) ).getText().equals( legendName.replaceAll( "\\.+$", "" ) ) )
                                            .findFirst()
                                            .orElseThrow( () -> new RuntimeException( STR."Failed to hover on \{legendName} on the Response view" ) );
        moveToElement( legend );
    }

    public WebElementFacade getMeasurementTableElement() {
        return measurementTable;
    }

    public String getTrendsChartEmptyMessage() {
        try {
            return trendsChart.findElement( By.cssSelector( "[class*='EmptyMessage-module_']" ) ).getText();
        } catch ( Exception e ) {
            fail( "Could not find empty trends chart message" );
        }
        return null;
    }

    public String getLegendTooltipText() {
        return tooltip.getDelayedTooltipFullText();
    }

    public EmptyStateMessage getEmptyStateDetails() {
        try {
            String title = emptyStateModule.then( By.cssSelector( EMPTY_STATE_TITLE_SELECTOR ) ).getText();
            String message = emptyStateModule.then( By.cssSelector( EMPTY_STATE_MESSAGE_SELECTOR ) ).getText();
            String buttonText = emptyStateModule.then( By.cssSelector( SITE_NAVIGATION_BUTTON_SELECTOR ) ).getText();
            EmptyStateMessage emptyStateMessage = new EmptyStateMessage( title, message );
            emptyStateMessage.setButtonText( buttonText );
            return emptyStateMessage;
        } catch ( NoSuchElementException e ) {
            fail( "Could not find empty state message on Response column" );
        }
        return null;
    }

    public boolean isMeasuresTableVisible() {
        return !getDriver().findElements( By.cssSelector( MEASUREMENT_TABLE_SELECTOR ) ).isEmpty();
    }

    public boolean isMultilineChartVisible() {
        return !getDriver().findElements( By.cssSelector( MULTILINE_CHART_SELECTOR ) ).isEmpty();
    }

    public WebElementFacade getAISummaryElementOfStudy( int studyIndex ) {
        try {
            return studyItemModules.get( studyIndex ).then( By.cssSelector( STUDY_CARD_NLP_MODULE ) );
        } catch ( Exception e ) {
            fail( "Could not find AI summary element of study index: " + studyIndex );
        }
        return null;
    }
}
