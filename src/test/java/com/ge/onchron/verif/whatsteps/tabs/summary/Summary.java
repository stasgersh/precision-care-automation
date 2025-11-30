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
package com.ge.onchron.verif.whatsteps.tabs.summary;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.ge.onchron.verif.howsteps.SummarySteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.howsteps.detailedPatientData.medicalHistory.LabHistorySteps;
import com.ge.onchron.verif.model.InfoMessage;
import com.ge.onchron.verif.model.StringList;
import com.ge.onchron.verif.model.Table;
import com.ge.onchron.verif.model.detailedDataItems.CommentItem;
import com.ge.onchron.verif.model.detailedDataItems.LinkWithDateItem;
import com.ge.onchron.verif.model.summaryCard.SummaryCard;
import com.ge.onchron.verif.model.summaryCard.SummaryCardItem;
import com.ge.onchron.verif.model.summaryCard.SummaryGroup;
import net.thucydides.core.annotations.Steps;


public class Summary {

    @Steps
    private SummarySteps summarySteps;
    @Steps
    private LabHistorySteps labHistorySteps;

    private static final Logger LOGGER = LoggerFactory.getLogger( Summary.class );
    private TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Given("the user clicks on 'Radioligand therapy options' description :$description")
    @When("the user clicks on 'Radioligand therapy options' description :$description")
    public void checkRadioligandTreatmentDescription(String description) {
        testDefinitionSteps.addStep(STR."the user clicked on the clinical eligibility description:\{description}");
        summarySteps.clickOnTheRadioligandDescriptionInSummaryPage(description);
        testDefinitionSteps.logEvidence(
                STR."The user clicked the clinical eligibility description:\n\{description}",
                STR."The user clicked the clinical eligibility description:\n\{description}", true);
    }

    @Given("the user clicks on the following description:$description")
    public void checkClinicalTrialsTreatmentDescription(String description) {
        testDefinitionSteps.addStep(STR."the user clicked on the clinical eligibility description:\{description}");
        summarySteps.clickOnTheFollowingTrialInSummaryPage(description);
        testDefinitionSteps.logEvidence(
                STR."The user clicked the clinical eligibility description:\n\{description}",
                STR."The user clicked the clinical eligibility description:\n\{description}", true);
    }

    @Given("the user clicks on the following treatment description:$description")
    public void checkClinicalTreatmentDescription(String description) {
        testDefinitionSteps.addStep(STR."the user clicked on the clinical eligibility description:\{description}");
        summarySteps.clickOnTheFollowingTrialInSummaryPage(description);
        testDefinitionSteps.logEvidence(
                STR."The user clicked the clinical eligibility description:\n\{description}",
                STR."The user clicked the clinical eligibility description:\n\{description}", true);
    }

    @Given("the user clicks on any available description")
    @When("the user clicks on any available description")
    public void clickOnAnyTrialDescription() {
        testDefinitionSteps.addStep("the user can click on any available clinical trials description");
        summarySteps.clickOnAnyTrialInSummaryPage();
        testDefinitionSteps.logEvidence(
                "The user can clicked the clinical trials description",
                "The user can clicked the clinical trials description", true);
    }

    @Given("the user clicks on any Radioligand's available description")
    @When("the user clicks on any Radioligand's available description")
    public void clickOnAnyRadioligandDescription() {
        testDefinitionSteps.addStep("the user can click on any available clinical Radioligand's description");
        summarySteps.clickOnAnyRadioligandOnSummaryPage();
        testDefinitionSteps.logEvidence(
                "The user can clicked the clinical Radioligand's description",
                "The user can clicked the clinical Radioligand's description", true);
    }

    @Given("there is a Treatments card with clinical trial options")
    @When("there is a Treatments card with clinical trial options")
    @Then("there is a Treatments card with clinical trial options")
    public void clinicalTreatmentCardIsDisplayed() {
        testDefinitionSteps.addStep("Treatments card has clinical trial options");
        summarySteps.clinicalTrialsViewIsDisplayed();
        testDefinitionSteps.logEvidence(
                "Treatments card is displayed with clinical trial options", "Treatments card was displayed with clinical trial options", true );
    }

    @Given("there is a Radioligand card with clinical therapy options")
    @When("there is a Radioligand card with clinical therapy options")
    @Then("there is a Radioligand card with clinical therapy options")
    public void clinicalRadioligandCardIsDisplayed() {
        testDefinitionSteps.addStep("Radioligand card with clinical therapy options is displayed");
        summarySteps.clinicalRadioligandViewIsDisplayed();
        testDefinitionSteps.logEvidence(
                "Radioligand card with clinical therapy options is displayed", "Radioligand card with clinical therapy options was displayed", true );
    }

    @Given( "the patient summary view is loaded" )
    @Then( "the patient summary view is loaded" )
    public void waitUntilPatientSummaryViewLoaded() {
        testDefinitionSteps.addStep( "Wait until patient summary view loaded" );
        summarySteps.waitUntilSummaryViewLoaded();
        testDefinitionSteps.logEvidence( "Patient summary is loaded successfully",
                "Patient summary view is loaded successfully (check previous logs)", true );
    }

    @Given( "the \"Status and response\" button was clicked" )
    @When( "the user clicks \"Status and response\" button" )
    public void clickStatusAndResponse() {
        testDefinitionSteps.addStep( "Click on \"Status and response\" button" );
        summarySteps.clickStatusAndResponse();
        testDefinitionSteps.logEvidence( "the \"Status and response\" button was clicked",
                "the \"Status and response\" button was clicked", true );
    }

    @Given( "the \"Clinical trials\" button was clicked" )
    @When( "the user clicks \"Clinical trials\" button" )
    public void clickClinicalTrials() {
        testDefinitionSteps.addStep( "Click on \"Clinical trials\" button" );
        summarySteps.clickClinicalTrials();
        testDefinitionSteps.logEvidence( "the \"Clinical trials\" button was clicked",
                "the \"Clinical trials\" button was clicked", true );
    }

    @Then( "the \"Status and response\" button $isVisible visible" )
    public void checkStatusAndResponseVisibility( Boolean isVisible ) {
        testDefinitionSteps.addStep( STR."Check that the \"Status and response\" button is \{isVisible ? "" : " not"} visible" );
        summarySteps.checkStatusAndResponseVisibility( isVisible );
        testDefinitionSteps.logEvidence(
                STR."the \"Status and response\" button is \{isVisible ? "" : " not"} visible",
                STR."the \"Status and response\" button is \{isVisible ? "" : " not"} visible", true );
    }

    @Given( "the patient summary view is displayed" )
    @Then( "the patient summary view is displayed" )
    public void summaryViewIsVisible() {
        testDefinitionSteps.addStep( "Check summary view visibility" );
        summarySteps.summaryViewIsDisplayed();
        testDefinitionSteps.logEvidence( "The patient summary view is displayed",
                "The Summary view is not displayed successfully (check previous logs)", true );
    }

    @Given( "the Summary view contains the following groups: $groups" )
    @Then( "the Summary view contains the following groups: $groups" )
    public void checkGroupNames( List<SummaryGroup> groups ) {
        String groupsStr = groups.stream().map( SummaryGroup::getName ).collect( Collectors.joining( "\n" ) );
        testDefinitionSteps.addStep( "Check summary view group names" );
        summarySteps.checkGroupNames( groups );
        testDefinitionSteps.logEvidence( STR."the Summary view contains the following groups: \{groupsStr}", "Summary groups are ok on Summary view", true );
    }

    @Then( "there are no visible card groups on the Summary view" )
    public void checkNoVisibleCardGroups() {
        testDefinitionSteps.addStep( "Check that there are no visible card groups on the Summary view" );
        summarySteps.checkNoVisibleCardGroups();
        testDefinitionSteps.logEvidence(
                "there are no visible card groups on the Summary view",
                "there are no visible card groups on the Summary view", true );
    }

    @When( "the button \"$buttonText\" is displayed and clicked for 'Clinical trial options'" )
    public void checkSeeAllClinicalTrialOptionsButtonDisplay( String buttonText ) {
        testDefinitionSteps.addStep( STR."Check the button \{buttonText} is displayed and clicked for 'Clinical trial options'" );
        summarySteps.checkAndClickBtnSeeAllClinicalTrial( buttonText );
        testDefinitionSteps.logEvidence(
                STR."The \{buttonText} button was displayed for 'Clinical trial options' treatment's card and link was clicked",
                STR."\{buttonText} button was observed and clicked (check previous logs)", true );
    }

    @When( "the button \"$buttonText\" is displayed and clicked for 'Radioligand therapy options'" )
    public void checkSeeAllRadioligandTherapyOptions() {
        testDefinitionSteps.addStep( STR."Check the button 'See all radioligand therapy options' is displayed and clicked for 'Radioligand therapy options'" );
        summarySteps.checkAndClickBtnSeeAllRadioligandTherapy();
        testDefinitionSteps.logEvidence(
                STR."The 'See all radioligand therapy options' button was displayed for Radioligand therapy options' treatment's card and link was clicked",
                STR."'See all radioligand therapy options' button was observed and clicked (check previous logs)", true );
    }

    @Given( "the \"$groupName\" group has $nbOfCards card{s|}" )
    @Then( "the \"$groupName\" group has $nbOfCards card{s|}" )
    public void checkNbOfCardsInGroup( String groupName, int nbOfCards ) {
        testDefinitionSteps.addStep( STR."check number of cards in group \{groupName}" );
        summarySteps.checkNbOfCardsInGroup( groupName, nbOfCards );
        testDefinitionSteps.logEvidence( STR."the \{groupName} group has \{nbOfCards} cards", "Number of cards is ok in group (check previous logs)", true );
    }

    @Given( "the $cardNumber{st|nd|rd|th} card in \"$groupName\" group $assertCondition{contains|equals} the following data: $cardItems" )
    @Then( "the $cardNumber{st|nd|rd|th} card in \"$groupName\" group $assertCondition{contains|equals} the following data: $cardItems" )
    public void checkCardDetails( int cardNumber, String groupName,String assertCondition, List<SummaryCardItem> cardItems ) {
        testDefinitionSteps.addStep( STR."check card details in group \{groupName}" );
        int cardIndex = cardNumber - 1;
        String cardItemsStr = cardItems.stream().map( SummaryCardItem::toString ).collect( Collectors.joining( "\n" ) );
        if(!assertCondition.equals("contains")) {
            summarySteps.checkCardDetails(cardIndex, groupName, cardItems);
        }
        summarySteps.checkCardDetailsContains( cardIndex, groupName, cardItems );
        testDefinitionSteps.logEvidence( STR."the \{cardNumber} card in \{groupName} group \{assertCondition} the following data: \n\{cardItemsStr}",
                "Summary card details are ok",
                true );
    }

    @Then( "the \"$subGroup\" are displayed in descending chronological order in \"$groupName\" group" )
    public void displayedInDescendingChronologicalOrder(String subGroup, String groupName)
    {
        testDefinitionSteps.addStep(STR."Check descending chronological order of \{subGroup}");
        summarySteps.checkDatesChronology( groupName, subGroup );
        testDefinitionSteps.logEvidence( STR."the \{subGroup} card in \{groupName} presented in descending chronological order",
                STR."the \{subGroup} card in \{groupName} presented in descending chronological order",
                true );
    }

    @Then( "the patient info card on the Summary View contains the following data: $texts" )
    public void checkPatientIdentifierCard( StringList texts ) {
        testDefinitionSteps.addStep( "check patient identifier card" );
        summarySteps.checkPatientIdentifierCard( texts.getList() );
        testDefinitionSteps.logEvidence(
                STR."the patient info card on the Summary View contains the following data: \{String.join( "\n", texts.getList() )}",
                "Patient identifier texts are ok",
                true );
    }

    @When( "the user clicks on the \"$buttonText\" button at the following data on the $cardNumber{st|nd|rd|th} card in \"$groupName\" group: $cardItem" )
    public void clickButtonOnSummaryCardItem( String buttonText, int cardNumber, String groupName, SummaryCardItem cardItem ) {
        testDefinitionSteps.addStep( STR."Click on '\{buttonText}' button at the following data on \{cardNumber} card in \{groupName} group:\n\{cardItem}" );
        int cardIndex = cardNumber - 1;
        summarySteps.clickButtonOnSummaryCardItem( cardIndex, groupName, cardItem, buttonText );
        testDefinitionSteps.logEvidence( "Button successfully clicked.", "Button clicked with no errors.", true );
    }

    @When( "the user clicks on the \"$linkText\" link at the following data on the $cardNumber{st|nd|rd|th} card in \"$groupName\" group: $cardItem" )
    public void clickLinkOnSummaryCardItem( String linkText, int cardNumber, String groupName, SummaryCardItem cardItem ) {
        testDefinitionSteps.addStep( STR."Click on '\{linkText}' link at the following data on \{cardNumber} card in \{groupName} group:\n\{cardItem}" );
        int cardIndex = cardNumber - 1;
        summarySteps.clickLinkOnSummaryCardItem( cardIndex, groupName, cardItem, linkText );
        testDefinitionSteps.logEvidence( "Link successfully clicked.", "Link clicked with no errors.", true );
    }

    @Then( "the $infoType info on the Summary View contains the following text: \"$infoText\"" )
    public void checkFilteredDataInfoText( String infoType, String infoText ) {
        testDefinitionSteps.addStep( "Check filtered data info card" );
        summarySteps.checkInfoText( infoText );
        testDefinitionSteps.logEvidence(
                STR."The \{infoType} info on the Summary View contains the following text: \{infoText}",
                STR."\{infoType} info on the Summary View contains the following text: \{infoText}", true );
    }

    @When( "the user clicks on the \"$buttonText\" button on the AI info" )
    public void clickButtonAiNotificationCard( String buttonText ) {
        testDefinitionSteps.addStep( STR."Click on the \{buttonText} button on the AI info" );
        summarySteps.clickButtonAiNotificationCard( buttonText );
        testDefinitionSteps.logEvidence( STR."Successfully clicked on the on the \{buttonText} button", "Successfully clicked on the button (without errors)", true );
    }

    @Then( "footer $isAvailable available on the $cardNumber{st|nd|rd|th} card in \"$groupName\" group" )
    public void checkCardFooterAvailability( Boolean isAvailable, int cardNumber, String groupName ) {
        testDefinitionSteps.addStep( "Check card footer availability" );
        int cardIndex = cardNumber - 1;
        summarySteps.checkCardFooterAvailability( isAvailable, groupName, cardIndex );
        testDefinitionSteps.logEvidence( STR."footer \{isAvailable ? "" : "not"} available on the \{cardNumber} card in \{groupName} group",
                STR."Footer \{isAvailable ? "" : "not"} available on the \{cardNumber} card in \{groupName} group as expected", true );
    }

    @Given( "the 'Show on timeline' button $isEnabled enabled in the footer of the below {card|cards}: $cards" )
    @Then( "the 'Show on timeline' button $isEnabled enabled in the footer of the below {card|cards}: $cards" )
    public void checkStateOfNavigateToTimelineButtonOnCardsFooter( Boolean isEnabled, List<SummaryCard> cards ) {
        testDefinitionSteps.addStep( "Check 'Navigate to timeline' button in the footer of the below cards" );
        summarySteps.checkStateOfNavigateToTimelineButtonInCardsFooter( isEnabled, cards );
        testDefinitionSteps.logEvidence(
                STR."The 'Navigate to timeline' button \{isEnabled ? "" : "not"} enabled in the footer of the below cards: \{cards.stream()
                                                                                                                                  .map( SummaryCard::toString ).collect( Collectors.joining( "\n" ) )}",
                "State of 'Navigate to timeline' button is ok (check previous logs)", true );
    }

    @When( "the user clicks on the 'Show on timeline' button in the footer of the below card: $cards" )
    public void clickOnNavigateToTimelineButtonOnCardFooter( List<SummaryCard> cards ) {
        testDefinitionSteps.addStep( "Click on the 'Navigate to timeline' button on card footer" );
        LOGGER.info( STR."Num of added items: \{cards.size()}" );
        assertThat( "Only one item can be added to the table in this step definition", cards.size(), is( equalTo( 1 ) ) );
        summarySteps.clickOnNavigateToTimelineButtonInCardFooter( cards.getFirst() );
        testDefinitionSteps.logEvidence( "Successfully clicked on the on the 'Navigate to timeline' button",
                "Successfully clicked on the button (without errors)", true );
    }

    @When( "the user clicks on the 'Full report' button in the footer of the below card: $cards" )
    public void clickOnFullReportButtonOnCardFooter( List<SummaryCard> cards ) {
        testDefinitionSteps.addStep( "Click on the 'Full report' button on card footer" );
        LOGGER.info( STR."Num of added items: \{cards.size()}" );
        assertThat( "Only one item can be added to the table in this step definition", cards.size(), is( equalTo( 1 ) ) );
        summarySteps.clickOnFullReportButtonInCardFooter( cards.getFirst() );
        testDefinitionSteps.logEvidence( "Successfully clicked on the on the 'Full report' button",
                "Successfully clicked on the button (without errors)", true );
    }
    @When( "the user hovers the 'Full report' button in the footer of the below card: $cards" )
    public void hoverOnFullReportButtonOnCardFooter( List<SummaryCard> cards ) {
        testDefinitionSteps.addStep( "Hover the 'Full report' button on card footer" );
        LOGGER.info( STR."Num of added items: \{cards.size()}" );
        assertThat( "Only one item can be added to the table in this step definition", cards.size(), is( equalTo( 1 ) ) );
        summarySteps.hoverOnFullReportButtonInCardFooter(cards.getFirst());
        testDefinitionSteps.logEvidence( "Successfully hovered on the on the 'Full report' button",
                "Successfully hovered on the disabled button (without errors)", true );
    }


    @Then( "the 'Full report' button $isEnabled enabled in the footer of the below cards: $cards" )
    public void checkStateOfFullReportButtonOnCardsFooter( Boolean isEnabled, List<SummaryCard> cards ) {
        testDefinitionSteps.addStep( "Check state of full report button on cards footer" );
        summarySteps.checkStateOfFullReportButtonInCardsFooter( isEnabled, cards );
        testDefinitionSteps.logEvidence(
                STR."The 'Full report' button \{isEnabled ? "" : "not"} enabled in the footer of the below cards: \{cards.stream()
                                                                                                                         .map( SummaryCard::toString ).collect( Collectors.joining( "\n" ) )}",
                "State of 'Full report' button is ok (check previous logs)", true );
    }

    @Then( "the 'Show on timeline' modal $isDisplayed displayed" )
    public void checkNavigateTimelineModalVisibility( Boolean isDisplayed ) {
        testDefinitionSteps.addStep( "Check navigation timeline modal visibility" );
        summarySteps.checkNavigateTimelineModalVisibility( isDisplayed );
        testDefinitionSteps.logEvidence( STR."The 'Show on timeline' modal \{isDisplayed ? "" : "not"} displayed",
                "State of 'Full report' button is ok (check previous logs)", true );
    }

    @Then( "the following links are available on the 'Show on timeline' modal: $links" )
    public void checkLinksOnNavigateTimelineModal( List<LinkWithDateItem> links ) {
        testDefinitionSteps.addStep( "Check Links on navigate timeline modal" );
        summarySteps.checkLinksOnNavigateTimelineModal( links );
        testDefinitionSteps.logEvidence(
                STR."the following links are available on the 'Show on timeline' modal: \{links.stream().map( LinkWithDateItem::toString )
                                                                                               .collect( Collectors.joining( "\n" ) )}",
                "Event links (or their order) on 'Show on timeline' popup are ok", true );
    }

    @Then( "the following text is displayed on the 'Show on timeline' modal: $expectedText" )
    public void checkTextOnShowOnTimelineModal( String expectedText ) {
        testDefinitionSteps.addStep( "Checking displayed text on show on timeline" );
        summarySteps.checkTextOnShowOnTimeline( expectedText );
        testDefinitionSteps.logEvidence( String.format( "The following text is displayed on the 'Show on timeline' modal: %s", expectedText ),
                String.format( "The following text was displayed on the 'Show on timeline' modal: %s", expectedText ), true );
    }

    @When( "the user clicks on the following link on the 'Show on timeline' modal: $links" )
    public void clickOnLinkOnNavigateTimelineModal( List<LinkWithDateItem> links ) {
        testDefinitionSteps.addStep( "Click on Link on navigate timeline modal" );
        LOGGER.info( STR."Num of added link items: \{links.size()}" );
        assertThat( "Only one item can be added to the table in this step definition", links.size(), is( equalTo( 1 ) ) );
        summarySteps.clickOnLinkOnNavigateTimelineModal( links.getFirst() );
        testDefinitionSteps.logEvidence( "Successfully clicked on the on the 'Show on timeline' link",
                "Successfully clicked on the link (without errors)", true );
    }

    @When( "the user clicks on the 'X' button on the 'Show on timeline' modal" )
    public void clickOnCloseOnNavigateTimelineModal() {
        testDefinitionSteps.addStep( "Click on close on navigate timeline modal" );
        summarySteps.clickOnCloseOnNavigateTimelineModal();
        testDefinitionSteps.logEvidence( "Successfully clicked on the on the 'X' button",
                "Successfully clicked on the button (without errors)", true );
    }

    @When( "the user clicks on the following data item on the $cardNumber{st|nd|rd|th} card in \"$groupName\" group: $cardItem" )
    public void clickOnDataItemOnCard( int cardNumber, String groupName, SummaryCardItem cardItem ) {
        testDefinitionSteps.addStep( STR."Click on data item on the \{cardNumber} card" );
        int cardIndex = cardNumber - 1;
        summarySteps.clickOnDataItemOnCard( cardIndex, groupName, cardItem );
        testDefinitionSteps.logEvidence( STR."the user clicks on the following data item on the \{cardNumber} card in \{groupName} group: \{cardItem}",
                "Successfully clicked on the data item (without errors)", true );
    }

    @When( "the user clicks on the 'View on timeline' button on the following comment on the $cardNumber{st|nd|rd|th} card in \"$groupName\" group: $comment" )
    public void clickViewOnTimelineButtonOnSummaryCard( int cardNumber, String groupName, CommentItem comment ) {
        testDefinitionSteps.addStep( STR."Click on 'View on timeline' button on \{cardNumber} SummaryCard" );
        CommentItem originalComment = new CommentItem( comment ); // due to placeholder change during execution
        int cardIndex = cardNumber - 1;
        summarySteps.clickViewOnTimelineButtonOnSummaryCard( cardIndex, groupName, comment );
        testDefinitionSteps.logEvidence( STR."the user clicks on the 'View on timeline' button on the following comment on the \{cardNumber} card in \{groupName} group: \{originalComment}",
                "Successfully clicked on the 'View on timeline' button (without errors)", true );
    }

    @When( "the user clicks on the \"$reportTitle\" report link in \"Key events\" in the \"Diagnosis\" group" )
    public void clickOnReportInKeyEventsTable( String reportTitle ) {
        testDefinitionSteps.addStep( STR."Click on the \{reportTitle} report link in Key events card in the Diagnosis group" );
        summarySteps.clickOnReportInKeyEventsOfDiagnosis( reportTitle );
        testDefinitionSteps.logEvidence( STR."the user clicks on the \{reportTitle} report link in Key events card in the Diagnosis group",
                STR."Successfully clicked on the \{reportTitle} report link in Key events card in the Diagnosis group", true );
    }

    @When( "the user moves the mouse on the data on the $cardNumber{st|nd|rd|th} card in \"$groupName\" group: $cardItems" )
    public void moveMouseOnTheDataOnSummaryCard( int cardNumber, String groupName, List<SummaryCardItem> cardItems ) {
        testDefinitionSteps.addStep( STR."Move the mouse on the data in the Summary card" );
        int cardIndex = cardNumber - 1;
        summarySteps.hoverOnTextInCard( cardIndex, groupName, cardItems.getFirst() );
        testDefinitionSteps.logEvidence( STR."The user moved the mouse on the CLP data in the Summary card",
                "The user successfully moved the mouse on the CLP data in the Summary card", true );
    }

    @Given( "the $cardNumber{st|nd|rd|th} card in \"$groupName\" group $isEmpty empty" )
    @Then( "the $cardNumber{st|nd|rd|th} card in \"$groupName\" group $isEmpty empty" )
    public void checkCardIsEmpty( int cardNumber, String groupName, Boolean isEmpty ) {
        testDefinitionSteps.addStep( STR."Check that the cardnumber \{cardNumber} card in group \{groupName} is \{isEmpty ? "" : " not "} empty" );
        int cardIndex = cardNumber - 1;
        summarySteps.checkCardIsEmpty( cardIndex, groupName, isEmpty );
        testDefinitionSteps.logEvidence(
                STR."the card \{cardNumber} in group \{groupName} is \{isEmpty ? "" : " not"} empty",
                STR."the card \{cardNumber} in group \{groupName} is \{isEmpty ? "" : " not"} empty", true );
    }

    @Then( "the visible cards $isEmpty empty on the Summary view" )
    public void areVisibleCardsEmpty( Boolean isEmpty ) {
        testDefinitionSteps.addStep( STR."Check that the visible cards on Summary view are \{isEmpty ? "" : " not"} empty" );
        summarySteps.areVisibleCardsEmpty( isEmpty );
        testDefinitionSteps.logEvidence(
                STR."the visible cards on Summary view are \{isEmpty ? "" : " not"} empty",
                STR."the visible cards on Summary view are \{isEmpty ? "" : " not"} empty", true );
    }

    @Then( "the below information message is displayed about the hidden cards: $infoMessage" )
    public void checkInfoMessageAboutHiddenCards( InfoMessage infoMessage ) {
        testDefinitionSteps.addStep( STR."Check that the info message about hidden cards contains the following data: \{infoMessage}" );
        summarySteps.checkInfoMessageAboutHiddenCards( infoMessage );
        testDefinitionSteps.logEvidence(
                STR."the info message about hidden cards contains the following data: \{infoMessage}",
                STR."the info message about hidden cards contains the following data: \{infoMessage}", true );
    }

    @When( "the user clicks on the \"Edit summary visibility\" button" )
    public void clickOnEditSummaryVisibilityButton() {
        testDefinitionSteps.addStep( "Click on the \"Edit summary visibility\" button" );
        summarySteps.clickOnEditSummaryVisibilityButton();
        testDefinitionSteps.logEvidence(
                "the user clicked on the \"Edit summary visibility\" button",
                "the user clicked on the \"Edit summary visibility\" button", true );
    }
    @Given("the \"$cardName\" card \"$groupName\" data truncated")
    @Then("the \"$cardName\" card \"$groupName\" data truncated")
    public void verifyCardIsTruncated( String cardName, String groupName ) {
        testDefinitionSteps.addStep(STR."Check \{cardName} card data is truncated");
        summarySteps.checkInCardTruncatedData(cardName);
        testDefinitionSteps.logEvidence(STR."The \"\{cardName}\" card data in group \"\{groupName}\" is truncated",
                "The data are truncated", true);
    }
    @Then("the \"$button\" button presented")
    public void verifyButtonPresented( String button ) {
        testDefinitionSteps.addStep(STR."Check \{button} button presented");
        summarySteps.checkBtnAvailable(button);
        testDefinitionSteps.logEvidence(STR."The \"\{button}\" button presented",
                STR."The \"\{button}\" button presented", true);
    }
    @When("the user click on \"$button\" button in \"$groupName\" group on Summary view")
    public void clickOnButtonInSummaryView( String button , String groupName ) {
        testDefinitionSteps.addStep(STR."Click on \"\{button}\" button in \"\{groupName}\" card");
        summarySteps.clickOnBtn(button, groupName);
        testDefinitionSteps.logEvidence(STR."The user click on \"\{button}\" button in \"\{groupName}\" group on Summary view",
                STR."\{button} button was clicked", true);
    }
    @Then("the all text displayed on \"$cardName\" card and \"$buttonName\" button")
    public void verifyAllTextDisplayed(String cardName, String buttonName ) {
        testDefinitionSteps.addStep("Verify all the text displayed");
        summarySteps.allDataDisplayed(cardName, buttonName);
        testDefinitionSteps.logEvidence("The all text displayed",
                "The text displayed", true);
    }
    @Then("the \"$cardName\" card content is highlighted")
    public void verifyCardContentIsHighlighted( String cardName ) {
        testDefinitionSteps.addStep("Verify the card content is highlighted");
        summarySteps.checkContentHighlighted(cardName);
        testDefinitionSteps.logEvidence(STR."The \"\{cardName}\" card content is highlighted",
                "Content is highlighted", true);
    }

    @Then( "the Summary Modal Viewer contains the following \"$tableTitle\" table: $table" )
    public void checkSidebarTable( String tableTitle, ExamplesTable table ) {
        testDefinitionSteps.addStep( STR."Check Summary Modal Viewer table \{tableTitle}" );
        Table expectedTable = new Table( tableTitle, table.getRows() );
        labHistorySteps.checkDataTable( expectedTable, false );
        testDefinitionSteps.logEvidence( STR."the Summary Modal Viewer contains the following table \{tableTitle} with following content: \{expectedTable}",
                "The table was found and content was matching (see previous logs)", true);
    }

    @Then( "the Patient card contains both the $patientName and $MRNValue together" )
    public void checkPatientCardContent( String patientName, String MRNValue ) {
        testDefinitionSteps.addStep( STR."Verify the patient card contains the \"\{patientName}\" name and \"\{MRNValue}\" MRN" );
        summarySteps.checkPatientIdentifierCard( Arrays.asList( patientName, STR."MRN: \{MRNValue}" ) );
        testDefinitionSteps.logEvidence( STR."the Patient card contains both the \{patientName} and \{MRNValue} together",
                STR."the Patient card successfully contains both the \{patientName} and \{MRNValue} together", true );
    }

    @Then( "the summary loading skeleton $isVisible visible" )
    public void checkTimelineLoadingSkeletonVisibility( Boolean isVisible ) {
        testDefinitionSteps.addStep( STR."Checking summary loading skeleton visibility" );
        summarySteps.checkSummaryLoadingSkeletonVisibility( isVisible );
        testDefinitionSteps.logEvidence( STR."Summary loading skeleton is\{isVisible ? " " : " not "}visible",
                STR."Summary loading skeleton was\{isVisible ? " " : " not "}visible", true );
    }
}
