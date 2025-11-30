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
package com.ge.onchron.verif.howsteps;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.ge.onchron.verif.model.clinicalConfiguration.treatment.TreatmentItem;
import com.ge.onchron.verif.pages.tabs.Radioligand;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.ge.onchron.verif.model.InfoMessage;
import com.ge.onchron.verif.model.detailedDataItems.CommentItem;
import com.ge.onchron.verif.model.detailedDataItems.LinkWithDateItem;
import com.ge.onchron.verif.model.summaryCard.SummaryCard;
import com.ge.onchron.verif.model.summaryCard.SummaryCardItem;
import com.ge.onchron.verif.model.summaryCard.SummaryGroup;
import com.ge.onchron.verif.model.summaryCard.summaryCardItemData.SummaryCardItemDataString;
import com.ge.onchron.verif.model.summaryCard.summaryCardItemData.notificationGroup.SummaryCardPatientIdentifier;
import com.ge.onchron.verif.pages.sections.modal.NavigateToTimelineModal;
import com.ge.onchron.verif.pages.tabs.Summary;
import com.ge.onchron.verif.pages.tabs.Trials;

import static com.ge.onchron.verif.model.summaryCard.enums.SummaryCardItemType.CARD_TITLE;
import static com.ge.onchron.verif.model.summaryCard.enums.SummaryCardItemType.EMPTY_STATE;
import static com.ge.onchron.verif.model.summaryCard.enums.SummaryCardItemType.FOOTER;
import static com.ge.onchron.verif.model.summaryCard.enums.SummaryCardItemType.PATIENT_IDENTIFIER;
import static com.ge.onchron.verif.utils.Utils.waitMillis;



public class SummarySteps {

    private Trials trials;
    private Radioligand radioligand;
    private Summary summary;
    private NavigateToTimelineModal navigateToTimelineModal;

    private static final Logger LOGGER = LoggerFactory.getLogger( SummarySteps.class );

    public void waitUntilSummaryViewLoaded() {
        summary.waitUntilLoadingSkeletonDisappears();
    }

    public void summaryViewIsDisplayed() {
        boolean visible = summary.isVisible();
        LOGGER.info( STR."Summary view is displayed \{visible}" );
        assertTrue( "The Summary view is not displayed", visible );
    }

    public void checkGroupNames( List<SummaryGroup> expectedGroups ) {
        summary.waitUntilLoadingSkeletonDisappears();
        List<SummaryGroup> observedGroups = getCardGroups();
        assertEquals( "Summary groups are not ok on Summary view", expectedGroups, observedGroups );
    }

    public void checkNoVisibleCardGroups() {
        summary.waitUntilLoadingSkeletonDisappears();
        List<SummaryGroup> observedGroups = getCardGroups();
        assertTrue( "There should be no visible groups on Summary view", observedGroups.isEmpty() );
    }

    private List<SummaryGroup> getCardGroups() {
        List<SummaryGroup> observedGroups = summary.getGroups();
        LOGGER.info( STR."Observed summary view groups: \{observedGroups.stream().map( SummaryGroup::getName ).collect( Collectors.joining( "\n" ) )}" );
        return observedGroups;
    }

    public void checkNbOfCardsInGroup( String groupName, int expectedNbOfCards ) {
        summary.waitUntilLoadingSkeletonDisappears();
        int nbOfCards = summary.getNumberOfCardsInGroup( groupName );
        LOGGER.info( STR."Number of cards in group \{groupName} is \{nbOfCards}" );
        assertEquals( STR."Number of cards is not ok in group: \{groupName}", expectedNbOfCards, nbOfCards );
    }

    /**
     * @param cardIndex         card index in the group (starts from 0)
     * @param groupName         group name where the card can be found
     * @param expectedCardItems card items which need to be checked (card footer is not verified here!)
     */
    public void checkCardDetails(int cardIndex, String groupName, List<SummaryCardItem> expectedCardItems) {
        List<SummaryCardItem> observedSummaryCardItems = getSummaryCardItems(cardIndex, groupName);
        observedSummaryCardItems
                .forEach(observedSummaryCardItem -> {
                    SummaryCardItem matchingExpectedItem = findMatchingExpectedItemsInObserved(expectedCardItems, observedSummaryCardItem);
                    if (matchingExpectedItem != null) {
                        if (matchingExpectedItem.getColor() == null) {
                            observedSummaryCardItem.setColor(null);
                            LOGGER.info(String.format("Reset color for observed item: %s", observedSummaryCardItem));
                        }
                    } else {
                        LOGGER.warn(String.format("No matching expected item found for observed item: %s", observedSummaryCardItem));
                    }
                });
        assertEquals(STR."Summary card details are not ok, groupName: \{groupName}, cardIndex: \{cardIndex}", expectedCardItems,
                observedSummaryCardItems);
    }

    private SummaryCardItem findMatchingExpectedItemsInObserved(List<SummaryCardItem> expectedCardItems, SummaryCardItem observedSummaryCardItem) {
        return expectedCardItems
                .stream()
                .filter(expectedCardItem ->
                        Objects.equals(expectedCardItem.getType(), observedSummaryCardItem.getType()) &&
                                Objects.equals(expectedCardItem.getValue(), observedSummaryCardItem.getValue()))
                .findFirst()
                .orElse(null);
    }

    /**
     * @param cardIndex         card index in the group (starts from 0)
     * @param groupName         group name where the card can be found
     * @param expectedCardItems card items which need to be checked
     */
    public void checkCardDetailsContains( int cardIndex, String groupName, List<SummaryCardItem> expectedCardItems ) {
        List<SummaryCardItem> observedSummaryCardItems = getSummaryCardItems(cardIndex, groupName);
        LOGGER.info( STR."Observed summary card items are: \{
                observedSummaryCardItems.stream().map( SummaryCardItem::toString ).collect( Collectors.joining( "\n" ) )}" );
        assertTrue(STR."Summary card details are not ok, groupName: \{groupName}, cardIndex: \{cardIndex}", observedSummaryCardItems.containsAll(expectedCardItems)
        );
    }

    private @NotNull List<SummaryCardItem> getSummaryCardItems( int cardIndex, String groupName ) {
        SummaryCard summaryCard = getSummaryCard(groupName, cardIndex);
        List<SummaryCardItem> observedSummaryCardItems = summaryCard.getSummaryCardItems().stream()
                .filter( summaryCardItem -> summaryCardItem.getType() != FOOTER )
                .filter( summaryCardItem -> summaryCardItem.getType() != CARD_TITLE || !((SummaryCardItemDataString) summaryCardItem.getValue()).getData().toString().isBlank() )
                .collect( Collectors.toList() );
        LOGGER.info( STR."Observed summary card items are: \{observedSummaryCardItems.stream().map( SummaryCardItem::toString )
                .collect( Collectors.joining( "\n" ) )}" );
        return observedSummaryCardItems;
    }

    public void checkInfoText( String expectedInfoText ) {
        summary.waitUntilLoadingSkeletonDisappears();
        List<String> observedInfoTexts = summary.getInfoTexts();
        LOGGER.info( STR."Observed information text items: \{observedInfoTexts}" );
        assertThat( "AI info card details are not ok.", observedInfoTexts, hasItem( expectedInfoText ) );
    }

    public void checkPatientIdentifierCard( List<String> expectedTexts ) {
        SummaryCard summaryCard = summary.getPatientInfoCard();
        List<SummaryCardItem> summaryCardItems = summaryCard.getSummaryCardItems();
        int size = summaryCardItems.size();
        LOGGER.info( STR."Summary card num of items: \{size}" );
        assertEquals( "Patient info card items size should be 1", 1, size );
        LOGGER.info( STR."Summary card items type: \{summaryCardItems.getFirst().getType()}" );
        assertEquals( "Patient info card items type is not ok.", PATIENT_IDENTIFIER, summaryCardItems.getFirst().getType() );
        SummaryCardPatientIdentifier patientIdentifier = (SummaryCardPatientIdentifier) summaryCardItems.getFirst().getValue();
        List<String> observedTexts = patientIdentifier.getData();
        LOGGER.info( STR."Summary card observed identifier texts: \{observedTexts}" );
        assertEquals( "Patient identifier texts are not ok.", expectedTexts, observedTexts );
    }

    public void clickButtonAiNotificationCard( String buttonText ) {
        summary.waitUntilLoadingSkeletonDisappears();
        summary.clickButtonOnAiInfo( buttonText );
        waitMillis( 1000 );
    }

    public void checkStateOfNavigateToTimelineButtonInCardsFooter( boolean isEnabled, List<SummaryCard> cards ) {
        cards.forEach( card -> {
            boolean isNavigateToTimelineButtonEnabled = summary.isNavigateToTimelineButtonEnabledOnFooter( card );
            LOGGER.info( STR."State of navigateToTimelineButtonEnabled is: \{isNavigateToTimelineButtonEnabled}" );
            assertThat( "State of 'Navigate to timeline' button is not ok. (isEnabled)", isNavigateToTimelineButtonEnabled, is( equalTo( isEnabled ) ) );
        } );
    }

    public void clickOnNavigateToTimelineButtonInCardFooter( SummaryCard card ) {
        summary.clickNavigateToTimelineButtonOnFooter( card );
    }

    public void clickOnFullReportButtonInCardFooter( SummaryCard card ) {
        summary.clickFullReportButtonOnFooter( card );
    }
    public void hoverOnFullReportButtonInCardFooter( SummaryCard card ) {
        summary.hoverFullReportButtonOnFooter(card);
    }

    public void checkStateOfFullReportButtonInCardsFooter( Boolean isEnabled, List<SummaryCard> cards ) {
        cards.forEach( card -> {
            boolean isFullReportButtonEnabled = summary.isOpenFullReportButtonEnabledOnFooter( card );
            LOGGER.info( STR."'Full report' button \{isFullReportButtonEnabled} in \{card} card footer" );
            assertThat( "State of 'Full report' button is not ok. (isEnabled)", isFullReportButtonEnabled, is( equalTo( isEnabled ) ) );
        } );
    }

    public void checkCardFooterAvailability( Boolean isAvailable, String groupName, int cardIndex ) {
        SummaryCard summaryCard = getSummaryCard( groupName, cardIndex );
        List<SummaryCardItem> footerItems = summaryCard.getSummaryCardItems().stream()
                                                       .filter( summaryCardItem -> summaryCardItem.getType() == FOOTER )
                                                       .collect( Collectors.toList() );
        if ( isAvailable ) {
            LOGGER.info( STR."footer items size '\{footerItems.size()}' in summary card '\{summaryCard}'" );
            assertEquals( STR."Only one footer should be available on the card: \{summaryCard}", 1, footerItems.size() );
        } else {
            LOGGER.info( STR."footer items size '\{footerItems.size()}' in summary card '\{summaryCard}'" );
            assertEquals( STR."Footer should not be available on the card: \{summaryCard}", 0, footerItems.size() );
        }
    }

    private SummaryCard getSummaryCard( String groupName, int cardIndex ) {
        summary.waitUntilLoadingSkeletonDisappears();
        List<SummaryCard> cardsFromGroup = summary.getCardsFromGroup( groupName );
        return cardsFromGroup.stream()
                             .filter( card -> card.getCardIndexInGroup() == cardIndex )
                             .findFirst()
                             .orElseThrow( () -> new RuntimeException( String.format( "Card with index %s was not found in group: %s", cardIndex, groupName ) ) );
    }
    public void checkDatesChronology( String groupName, String cardName ) {
        summary.isCardsDateValuesSorted( groupName, cardName );
    }

    public void checkNavigateTimelineModalVisibility( boolean isVisible ) {
        LOGGER.info( STR."Navigate time line modal \{navigateToTimelineModal.isVisible() ? "" : "not"} visible" );
        assertThat( "The 'Show on timeline' modal visibility is not ok.", navigateToTimelineModal.isVisible(), is( equalTo( isVisible ) ) );
    }

    /**
     * Check timeline event links on navigate to timeline modal (event order does not take into account)
     *
     * @param expectedLinks on navigate to timeline modal
     */
    public void checkLinksOnNavigateTimelineModal( List<LinkWithDateItem> expectedLinks ) {
        List<LinkWithDateItem> observedLinks = navigateToTimelineModal.getLinks();
        LOGGER.info( STR."Observed links on navigate timeline modal is: \{observedLinks.stream().map( LinkWithDateItem::toString ).collect( Collectors.joining( "\n" ) )}" );
        assertThat( "Event links (or their order) on 'Show on timeline' popup are not ok.\n", observedLinks, containsInAnyOrder( expectedLinks.toArray() ) );
    }

    public void checkTextOnShowOnTimeline( String expectedText ) {
        String actualText = navigateToTimelineModal.getNotificationText();
        LOGGER.info( String.format( "Actual notification text on Show on timeline modal is: %s", actualText ) );
        assertEquals( "Notification text on 'Show on Timeline' modal was not correct", expectedText, actualText );
    }

    public void clickOnLinkOnNavigateTimelineModal( LinkWithDateItem linkWithDateItem ) {
        navigateToTimelineModal.clickOnLink( linkWithDateItem );
    }

    public void clickOnCloseOnNavigateTimelineModal() {
        navigateToTimelineModal.clickOnCloseButton();
    }

    public void clickOnDataItemOnCard( int cardIndex, String groupName, SummaryCardItem cardItem ) {
        summary.clickOnDataItemOnSummaryCard( cardIndex, groupName, cardItem );
    }

    public void clickButtonOnSummaryCardItem( int cardIndex, String groupName, SummaryCardItem cardItem, String buttonText ) {
        summary.clickButtonOnSummaryCardItem( cardIndex, groupName, cardItem, buttonText );
    }

    public void clickLinkOnSummaryCardItem( int cardIndex, String groupName, SummaryCardItem cardItem, String buttonText ) {
        summary.clickLinkOnSummaryCardItem( cardIndex, groupName, cardItem, buttonText );
    }

    public void clickViewOnTimelineButtonOnSummaryCard( int cardIndex, String groupName, CommentItem comment ) {
        summary.clickViewOnTimelineButtonOnCommentItem( cardIndex, groupName, comment );
    }

    public void clickStatusAndResponse() {
        summary.clickStatusAndResponseButton();
    }

    public void clickClinicalTrials() {
        summary.clickClinicalTrialsButton();
    }

    public void checkStatusAndResponseVisibility( boolean isVisible ) {
        LOGGER.info( "Check that the visibility of Status and response button is {}", isVisible );
        assertThat( "Visibility of Status and response button is not ok.", summary.isStatusAndResponseButtonVisible(), is( equalTo( isVisible ) ) );
    }

    public void hoverOnTextInCard( int cardIndex, String groupName, SummaryCardItem cardItem ) {
        summary.hoverOnHighlightedTextInCard( cardIndex, groupName, cardItem );
    }

    public void checkSummarySectionIsInTheViewport( String section ) {
        List<String> observedSectionsInViewport = summary.getSectionsInViewport();
        LOGGER.info(STR."Observed sections are in the viewport on the summary card \{observedSectionsInViewport}");
        assertThat(STR."\{section} section is not displayed in the viewport", observedSectionsInViewport, hasItem( section ) );
    }

    public void clinicalTrialsViewIsDisplayed() {
        boolean visible = summary.isVisible();
        LOGGER.info( STR."Clinical Trials View is displayed \{visible}" );
        assertTrue( "The Clinical Trials View is not displayed", visible );
    }

    public void clinicalRadioligandViewIsDisplayed() {
        boolean visible = summary.isRadioligandVisible();
        LOGGER.info( STR."Clinical Radioligand View is displayed \{visible}" );
        assertTrue( "The Clinical Radioligand View is not displayed", visible );
    }


    public void clickOnTheRadioligandDescriptionInSummaryPage( String description ) {
        summary.clickOnTrialDescriptionLink( description );
        LOGGER.info( STR."Clinical Radioligand with description:\{description} was clicked" );
        List<TreatmentItem> actualTreatmentItems = radioligand.getListOfTreatments();
        assertTrue( "The Clinical Radioligand were not displayed", !actualTreatmentItems.isEmpty());
    }

    public void clickOnTheFollowingTrialInSummaryPage( String description ) {
        summary.clickOnTrialDescriptionLink( description );
        LOGGER.info( STR."Clinical Trials with description:\{description} was clicked" );
        assertTrue( "The Clinical Trials were not displayed", !trials.getListOfTrials().isEmpty());
    }

    public void checkCardIsEmpty( int cardNumber, String groupName, Boolean isEmpty ) {
        LOGGER.info( "Check if the card is empty: {}", isEmpty );
        SummaryCard summaryCard = getSummaryCard( groupName, cardNumber );
        assertCardIsEmpty( summaryCard, isEmpty );
    }

    public void clickOnAnyTrialInSummaryPage() {
        String description = summary.clickOnAnyClinicalTrialOption();
        LOGGER.info( STR."Clinical Trials with description:\{description} was clicked" );
        assertTrue( "The Clinical Trials were not displayed", !trials.getListOfTrials().isEmpty());
    }

    public void clickOnAnyRadioligandOnSummaryPage() {
        String description = summary.clickOnAnyClinicalRadioligandOption();
        LOGGER.info( STR."Clinical Radioligand with description:\{description} was clicked" );
        assertTrue( "The Clinical Radioligand were not displayed", !radioligand.getListOfTreatments().isEmpty());
    }

    public void areVisibleCardsEmpty( boolean isEmpty ) {
        LOGGER.info( "Check if all cards are empty: {}", isEmpty );
        List<SummaryCard> allCards = summary.getAllCards();
        allCards.forEach( summaryCard -> {
            assertCardIsEmpty( summaryCard, isEmpty );
        } );
    }

    private void assertCardIsEmpty(SummaryCard summaryCard, boolean isEmpty) {
        List<SummaryCardItem> nonEmptyItems = summaryCard.getSummaryCardItems().stream()
                .filter(summaryCardItem ->
                        !summaryCardItem.getType().equals(EMPTY_STATE) && !summaryCardItem.getType().equals(FOOTER))
                .toList();
        LOGGER.info("Card '{}' is empty: {}", summaryCard, nonEmptyItems.isEmpty());
        assertEquals(String.format("Card in %s group should %s be empty.", summaryCard.getGroup(), isEmpty ? "" : "not"), isEmpty, nonEmptyItems.isEmpty());
    }

    public void checkInfoMessageAboutHiddenCards( InfoMessage expectedInfoMessage ) {
        LOGGER.info( "Check that the info message on Summary view is the following: {}", expectedInfoMessage );
        InfoMessage observedInfoMessage = summary.getHiddenInfoMessage();
        LOGGER.info( "Observed info message on Summary view: {}", observedInfoMessage );
        assertEquals( "Info message about all hidden groups is not ok.", expectedInfoMessage, observedInfoMessage );
    }

    public void clickOnEditSummaryVisibilityButton() {
        summary.clickOnEditSummaryVisibilityButton();
    }

    public void checkInCardTruncatedData(String cardName) {
        String atrrName = summary.getCardContentById(cardName).findElement(By.xpath("parent::node()")).getAttribute("class");
        String content = summary.getCardContentById(cardName).findElement(By.xpath("parent::node()")).getText();
        LOGGER.info(STR."\{cardName} data is truncated \{atrrName}");
        assertThat(atrrName.contains("truncated"), is(true));
        assertThat(content.contains("..."), is(true));
    }

    public void allDataDisplayed(String cardName, String button) {
        String atrrName = summary.getCardContentById(cardName).findElement(By.xpath("parent::node()")).getAttribute("class");
        String content = summary.getCardContentById(cardName).getText();
        LOGGER.info(STR."All \{cardName} card data is displayed: \{content}");
        assertThat(atrrName.contains("truncated"), is(false));
        assertThat(content.contains("..."), is(false));
        checkBtnAvailable(button);
    }
    public void checkBtnAvailable(String btnName){
        assertTrue(summary.checkButtonExist(btnName));
    }

    public void checkAndClickBtnSeeAllClinicalTrial( String buttonText ){
        summary.checkAndClickSeeAllClinicalTrialButton( buttonText );
    }

    public void checkAndClickBtnSeeAllRadioligandTherapy(){
        summary.checkAndClickSeeAllRadioligandTherapyOptions();
    }

    public void clickOnBtn(String btnName, String groupName){
        summary.clickOnButton( btnName, groupName );
    }

    public void checkContentHighlighted(String cardName){
        assertThat(!summary.getCardContentById(cardName).getText().isEmpty(), is(true));
    }

    public void checkSummaryLoadingSkeletonVisibility( boolean isVisible ) {
        boolean observedLoadingSkeletonVisible = summary.isLoadingSkeletonVisible();
        assertEquals( STR."Loading skeleton is\{isVisible ? " " : " not "}present on the summary when expected\{isVisible ? " not " : " "}to be present", isVisible, observedLoadingSkeletonVisible );
    }

    public void clickOnReportInKeyEventsOfDiagnosis( String reportTitle ) {
        summary.clickOnLinkInKeyEventsTable( reportTitle);
    }
}
