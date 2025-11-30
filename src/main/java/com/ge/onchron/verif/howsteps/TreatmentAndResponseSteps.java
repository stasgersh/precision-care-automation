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
package com.ge.onchron.verif.howsteps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.EmptyStateMessage;
import com.ge.onchron.verif.model.treatmentAndResponse.StudyCard;
import com.ge.onchron.verif.model.treatmentAndResponse.TreatmentCard;
import com.ge.onchron.verif.pages.tabs.TreatmentAndResponse;
import java.util.ArrayList;
import java.util.List;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.findby.By;

public class TreatmentAndResponseSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger( SummarySteps.class );

    private TreatmentAndResponse treatmentAndResponse;

    public void waitUntilResponseViewLoaded() {
        LOGGER.info( "Wait until Response view is loaded" );
        treatmentAndResponse.waitUntilLoadingSkeletonDisappears();
    }

    public void openCompleteReportForTreatmentCard( String treatmentCardTitle ) {
        TreatmentCard expectedCard = treatmentAndResponse.getTreatmentCardByTitle( treatmentCardTitle );
        LOGGER.info( STR."Expected teatment card: \{expectedCard}" );
        treatmentAndResponse.clickOpenReportForTreatmentCard( expectedCard );
    }

    public void expandOrCollapseTreatmentCard( String btnName ) {
        LOGGER.info( STR."\{btnName} treatment card" );
        treatmentAndResponse.clickTreatmentModuleHeaderBtn( btnName );
    }

    public void checkAlertMessage( String msg ) {
        LOGGER.info( STR."Expected alert message \{msg}" );
        assertTrue( "The message doesnt exist", treatmentAndResponse.getAlertMsg().contains( msg ) );
    }

    public void iconVisibility( String iconName, String badgeText ) {
        String iconId = switch ( iconName ) {
            case "Open complete report" -> "Layer_1";
            case "Open images" -> "a";
            default -> "";
        };
        assertTrue( "Icon doesnt exist",
                treatmentAndResponse.getSelectedStudyDetailsByBadge( badgeText ).getCardElement()
                                    .findElements( By.id( iconId ) ).getFirst().isDisplayed() );
    }

    public void buttonTextVisibility( String buttonName, String badgeText ) {
        assertTrue( "Button text doesnt exist",
                treatmentAndResponse.getSelectedStudyDetailsByBadge( badgeText ).getButtonTextList().contains( buttonName ) );
    }

    public void clickButtonForStudy( String buttonText, String studyName, String badgeText ) {
        StudyCard observedStudyCard = treatmentAndResponse.getSelectedStudyDetailsByBadge( badgeText );
        LOGGER.info( STR."Study card for study\{badgeText}: \{observedStudyCard.toString()}" );
        treatmentAndResponse.clickButtonForStudy( observedStudyCard, buttonText );
    }

    public void checkCardDetails( String expectedCardTitle, TreatmentCard expectedCard ) {
        TreatmentCard observedCard = treatmentAndResponse.getTreatmentCardByTitle( expectedCardTitle );
        LOGGER.info( STR."Treatment observed Card: \{observedCard.toString()}" );
        assertEquals( "Expected treatment card was not displayed on the UI or had the wrong details\n", expectedCard, observedCard );
    }

    public void hoverOnAIGeneratedTextOfStudy( String studyTitle ) {
        treatmentAndResponse.hoverOnAIGeneratedTextOfStudy( studyTitle );
    }

    public void moveMouseOnLegend( String legendName ) {
        treatmentAndResponse.hoverOnLegendOnResponseView( legendName );
    }

    public void checkSelectedStudyDetailsFor( String badgeText, StudyCard expectedStudyCard ) {
        StudyCard observedStudyCard = treatmentAndResponse.getSelectedStudyDetailsByBadge( badgeText );
        LOGGER.info( STR."Study card for study \{badgeText}: \{observedStudyCard.toString()}" );
        assertEquals( STR."Study details of study \{badgeText} does not match expected study details", expectedStudyCard, observedStudyCard );
    }

    public void checkTreatmentCardState() {
        String state = treatmentAndResponse.getTreatmentCardState();
        LOGGER.info( STR."Current state: \{state}" );
        treatmentAndResponse.clickTreatmentModuleHeaderBtn( state );
        LOGGER.info( STR."new state: \{treatmentAndResponse.getTreatmentCardState()}" );
        assertNotEquals( STR."Treatment card state not changed", treatmentAndResponse.getTreatmentCardState(), state );
    }

    public void checkMeasurementTable( List<String> expectedTable ) {
        List<String> actualTable = getMeasureMentTableAsStringList();
        assertEquals( "Measurement table does not match expected", expectedTable, actualTable );
    }

    private List<String> getMeasureMentTableAsStringList() {
        WebElementFacade tableElement = treatmentAndResponse.getMeasurementTableElement();
        List<String> actualTable = new ArrayList<>();
        List<WebElementFacade> rows = tableElement.thenFindAll( By.tagName( "tr" ) );
        for ( int i = 1; i < rows.size(); i++ ) { // Skipping header row
            WebElementFacade row = rows.get( i );
            StringBuilder sb = new StringBuilder();
            List<WebElementFacade> cells = row.thenFindAll( By.tagName( "td" ) );
            for ( int j = 0; j < cells.size(); j++ ) {
                WebElementFacade cell = cells.get( j );
                String cellText = cell.getTextContent().trim();
                if ( !cellText.isEmpty() ) {
                    if ( j != 0 ) {
                        sb.append( "; " );
                    }
                    sb.append( cellText );
                }
            }
            actualTable.add( sb.toString() );
        }
        return actualTable;
    }

    public void checkStudyAEmptyState( String emptyStateText ) {
        StudyCard observedStudyCard = treatmentAndResponse.getSelectedStudyDetailsByBadge( "A" );
        LOGGER.info( STR."Study card for A is displayed as: \{observedStudyCard.toString()}" );
        assertEquals( "Empty state of study A card is not correct", emptyStateText, observedStudyCard.getEmptyText() );
    }

    public void checkAISummarizationIsTruncated( String badgeText, Boolean truncated ) {
        int studyIndex = badgeText.equalsIgnoreCase( "A" ) ? 0 : 1;
        WebElementFacade studyAIResponseElement = treatmentAndResponse.getAISummaryElementOfStudy( studyIndex );
        boolean isTruncated = studyAIResponseElement.getAttribute( "class" ).contains( "truncated" ) && studyAIResponseElement.getText().endsWith( "..." );
        assertEquals( STR."the text is \{!truncated ? "" : "not"} truncated", truncated, isTruncated );
    }

    public void checkEmptyTrendsChart( String emptyTrendsChartText ) {
        String observedTrendsChartMessage = treatmentAndResponse.getTrendsChartEmptyMessage();
        LOGGER.info( STR."Trends chart empty message is displayed as: \{observedTrendsChartMessage}" );
        assertEquals( "Empty state of Trends chart is not correct", emptyTrendsChartText, observedTrendsChartMessage );
    }

    public void checkLegendTooltipText( String toolTipText ) {
        String observedTooltipText = treatmentAndResponse.getLegendTooltipText();
        LOGGER.info( STR."Legend tooltip text is displayed as: \{observedTooltipText}" );
        assertEquals( "Legend tooltip does not match expectation", toolTipText, observedTooltipText );
    }

    public void checkEmptyResponseColumnText( EmptyStateMessage expectedEmptyStateDetails ) {
        EmptyStateMessage actualStateDetails = treatmentAndResponse.getEmptyStateDetails();
        LOGGER.info( STR."Actual empty Response state details: \{actualStateDetails.toString()}" );
        assertEquals( "Actual empty Response state details does not match expected", expectedEmptyStateDetails, actualStateDetails );
    }

    public void checkModuleIsNotVisible( String module ) {
        boolean moduleState = false;
        switch ( module.toLowerCase() ) {
            case "measures table":
                moduleState = treatmentAndResponse.isMeasuresTableVisible();
                break;
            case "multiline chart":
                moduleState = treatmentAndResponse.isMultilineChartVisible();
                break;
            default:
                fail( STR."No such module as \{module}" );
        }
        assertFalse( STR."\{module} was present when it shouldn't be", moduleState );
    }

}
