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
package com.ge.onchron.verif.pages.tabs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.ge.onchron.verif.howsteps.SummarySteps;
import com.ge.onchron.verif.model.InfoMessage;
import com.ge.onchron.verif.model.Table;
import com.ge.onchron.verif.model.clinicalConfiguration.RowBadgeItem;
import com.ge.onchron.verif.model.detailedDataItems.CommentItem;
import com.ge.onchron.verif.model.detailedDataItems.TableItem;
import com.ge.onchron.verif.model.summaryCard.SummaryCard;
import com.ge.onchron.verif.model.summaryCard.SummaryCardItem;
import com.ge.onchron.verif.model.summaryCard.SummaryGroup;
import com.ge.onchron.verif.model.summaryCard.enums.SummaryCardItemType;
import com.ge.onchron.verif.model.summaryCard.summaryCardItemData.SummaryCardItemComment;
import com.ge.onchron.verif.model.summaryCard.summaryCardItemData.SummaryCardItemDataCLP;
import com.ge.onchron.verif.model.summaryCard.summaryCardItemData.SummaryCardItemDataColor;
import com.ge.onchron.verif.model.summaryCard.summaryCardItemData.SummaryCardItemDataKeyValuePair;
import com.ge.onchron.verif.model.summaryCard.summaryCardItemData.SummaryCardItemDataLink;
import com.ge.onchron.verif.model.summaryCard.summaryCardItemData.SummaryCardItemDataString;
import com.ge.onchron.verif.model.summaryCard.summaryCardItemData.SummaryCardItemDataTable;
import com.ge.onchron.verif.model.summaryCard.summaryCardItemData.SummaryCardItemDataTrial;
import com.ge.onchron.verif.model.summaryCard.summaryCardItemData.notificationGroup.SummaryCardPatientIdentifier;
import com.ge.onchron.verif.pages.SimplePage;
import com.ge.onchron.verif.pages.elements.SummaryCardElement;
import com.ge.onchron.verif.pages.elements.SummaryCardItemElement;
import com.ge.onchron.verif.pages.elements.SummaryGroupElement;
import com.ge.onchron.verif.pages.sections.Comments;
import com.ge.onchron.verif.pages.utils.PageElementUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.ListOfWebElementFacades;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.model.summaryCard.enums.SummaryCardItemType.COMMENTS;
import static com.ge.onchron.verif.model.summaryCard.enums.SummaryCardItemType.FOOTER;
import static com.ge.onchron.verif.model.summaryCard.enums.SummaryCardItemType.PATIENT_IDENTIFIER;
import static com.ge.onchron.verif.pages.tabs.Summary.FOOTER_BUTTONS.FULL_REPORT;
import static com.ge.onchron.verif.pages.tabs.Summary.FOOTER_BUTTONS.NAVIGATE_TO_TIMELINE;
import static com.ge.onchron.verif.pages.utils.ClpUtils.CLP_HIGHLIGHT_CLASS;
import static com.ge.onchron.verif.pages.utils.ClpUtils.getClpItemText;
import static com.ge.onchron.verif.pages.utils.PageElementUtils.hoverOnWebElement;
import static com.ge.onchron.verif.pages.utils.PageUtils.currentlyContainsElements;
import static com.ge.onchron.verif.pages.utils.SummaryTrialUtils.getBadgeRowItems;
import static com.ge.onchron.verif.pages.utils.SummaryTrialUtils.getClinicalTrialLinkableDescription;
import static com.ge.onchron.verif.pages.utils.TableUtils.createTableItem;
import static com.ge.onchron.verif.utils.CommentUtils.changeParametersInComment;

public class Summary extends SimplePage {

    private static final Logger LOGGER = LoggerFactory.getLogger( SummarySteps.class );

    private final String LOADING_SKELETON_SELECTOR = "[class*='SummaryItemSkeleton']";
    private final String SUMMARY_COLUMN_SELECTOR = "[class*='SummaryColumn-module_summary-column']";
    private final String SUMMARY_GROUP_NAME_SELECTOR = "[class*='SummaryGroup-module_name']";
    private final String SUMMARY_GROUP_BADGE_SELECTOR = "[class*='badge']";
    private final String SUMMARY_CARD_SELECTOR = "[class*='summary-card-container']";
    private final String SUMMARY_CARD_CONTENT_CLASS = "summary-item-container";
    private final String SUMMARY_CARD_FOOTER_SELECTOR = "[class*='SummaryItem-module_footer']";
    private final String SUMMARY_CARD_CLP_MODULE = "[class*='Nlp-module_container']";
    private final String DISABLE_CLASS_IN_CARD_FOOTER = "SummaryItem-module_disable";
    private final String STATUS_AND_RESPONSE_BUTTON_SELECTOR = "//*[contains(@class, 'button') and contains(normalize-space(text()), 'Patient status')]";
    private final String AI_INFO_BUTTON_SELECTOR = "[class*='AiInfo-module_ai-button']";
    private final String CLINICAL_TRIAL_OPTIONS_CARD_LINK_LOCATOR = "//section[contains(text(),'%')]";
    // Summary item identifiers
    private final String CARD_TITLE_ITEM_CLASS = "text-bold";
    private final String SUMMARY_ITEM_CLASS = "SummaryItem";
    private final String TABLE_ITEM_CLASS = "table";
    private final String PARAGRAPH_TEXT_ITEM_CLASS = "SummaryItem-module_gray-text";
    private final String BADGE_ITEM_CLASS = "badge";
    private final String KEY_VALUE_ITEM_CLASS = "TwoColumnElement-module_two-column-text";
    private final String EMPTY_STATE_ITEM_CLASS = "EmptyMessage-module_faded";
    private final String FOOTER_ITEM_CLASS = "SummaryItem-module_footer";
    private final String TWO_COLUMN_LINK_ITEM_CLASS = "TwoColumnElement-module_two-column-link";
    private final String REPORT_LINK_ITEM_SELECTOR = "[class*='LinkCta-module_container']";
    private final String COMMENTS_INFO_CLASS = "Comments-module_comment-info";
    private final String COMMENTS_CLASS = "comment-list";
    private final String PATIENT_IDENTIFIER_DETAILS_SELECTOR = "[class*='SummaryItem-module_details']";
    private final String BUTTON_IN_DATA_ITEM_SELECTOR = "[class*='button--tertiary']";
    private final String SUMMARY_LINK_SELECTOR = "[class*='LinkCta-module_cta'],a";
    private final String SUMMARY_CARDS_LIST = "[class*='CardElement-module_container']";
    private final String MOST_RECENT_CONTENT = "//*[starts-with(@class,'nlp-text Nlp-module_content')]";
    private final String INFO_SECTION_SELECTOR = "[class*='SummaryInfoSection-module_summary-info-section_']";
    private final String AI_INFO_SECTION_SELECTOR = "[class*='AiInfo-module_ai-info']";
    private final String SUMMARY_DATE_CLASS = "TwoColumnElement-module_date";
    private final String SEE_ALL_CLINICAL_TRIALS_BUTTON_LOCATOR = "//*[@id='treatments']/descendant::section/div";

    private final String ALL_CARDS_HIDDEN_CONTAINER_SELECTOR = "[class*='Summary-module_summary-groups-hidden__container']:not([class*='icon'])";
    private final String CLINICAL_TRIAL_DESCRIPTION_CLASS = "LimitedRowsElement-module_container_";
    private final String CLINICAL_TRIALS_BUTTON_SELECTOR = "//button/descendant::*[contains(text(),'Clinical trials')]";
    private final String SEE_ALL_RADIOLIGAND_THERAPY_OPTIONS_CSS = "[class*='SummaryItem-module_stick-to-the-left_']";

    public enum FOOTER_BUTTONS {
        NAVIGATE_TO_TIMELINE( 0 ),
        FULL_REPORT( 1 );

        private int num;

        FOOTER_BUTTONS( int num ) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }

    }

    @FindBy( css = "[class^='Summary-module_container']" )
    private WebElementFacade summaryContainer;

    @FindBy( xpath = STATUS_AND_RESPONSE_BUTTON_SELECTOR )
    private WebElementFacade statusAndResponseButton;

    @FindBy( xpath = CLINICAL_TRIALS_BUTTON_SELECTOR )
    private WebElementFacade clinicalTrialsButton;

    @FindBy( css = "[class^='SummaryItem-module_patient']" )
    private WebElementFacade patientInfoCardElement;

    @FindBy( css = INFO_SECTION_SELECTOR )
    private WebElementFacade infoSection;

    //uncomment this locator and replace with enabled if need to run against test-dev version
   //@FindBy( css = "[id*='clinical-trial-options']" )
    @FindBy( xpath = "//div[p[text()='Clinical trial options']]" )
    private WebElementFacade clinicalTrialOptions;

    @FindBy( xpath = "//div[p[text()='Radioligand therapy options']]" )
    private WebElementFacade clinicalRadioligandOptions;

    @FindBy( css = ALL_CARDS_HIDDEN_CONTAINER_SELECTOR )
    private WebElementFacade allCardsHiddenInfoContainer;

    @FindBy( css = "[id='key-events-card']" )
    private WebElementFacade keyEvents;

    public void waitUntilLoadingSkeletonDisappears() {
        waitUntilLoadingSkeletonDisappears( summaryContainer, By.cssSelector( LOADING_SKELETON_SELECTOR ) );
    }

    public boolean isVisible() {
        return summaryContainer.isCurrentlyVisible();
    }

    public List<SummaryGroup> getGroups() {
        List<SummaryGroupElement> groupsElements = getAllGroupElements();
        return groupsElements.stream().map( SummaryGroupElement::getSummaryGroup ).collect( Collectors.toList() );
    }

    public boolean isRadioligandVisible() {
        return clinicalRadioligandOptions.isCurrentlyVisible();
    }

    public List<WebElementFacade> getClinicalTrials() {
        return clinicalTrialOptions.thenFindAll( By.cssSelector( "[class*='LimitedRowsElement-module_container'" ) );
    }

    public List<WebElementFacade> getClinicalRadioligandTherapyOptions() {
        return clinicalRadioligandOptions.thenFindAll( By.cssSelector( "[class*='LimitedRowsElement-module_container'" ) );
    }
    public int getNumberOfCardsInGroup( String requiredGroupName ) {
        SummaryGroupElement groupElement = getGroupElement( requiredGroupName );
        List<WebElementFacade> cardElements = groupElement.getGroupElement().thenFindAll( By.cssSelector( SUMMARY_CARD_SELECTOR ) );
        return cardElements.size();
    }

    public List<SummaryCard> getCardsFromGroup( String requiredGroupName ) {
        SummaryGroupElement groupElement = getGroupElement( requiredGroupName );
        List<WebElementFacade> cardElements = groupElement.getGroupElement().thenFindAll( By.cssSelector( SUMMARY_CARD_SELECTOR ) );

        List<SummaryCard> summaryCardsFromGroup = new ArrayList<>();
        for ( int cardIndex = 0; cardIndex < cardElements.size(); cardIndex++ ) {
            SummaryCardElement summaryCardElement = new SummaryCardElement();
            summaryCardElement.setGroup( requiredGroupName );
            summaryCardElement.setCardIndexInGroup( cardIndex );

            WebElementFacade cardWebElement = cardElements.get( cardIndex );
            List<SummaryCardItemElement> summaryCardItemElements = getCardItemElements( cardWebElement );
            summaryCardElement.setSummaryItemElements( summaryCardItemElements );
            summaryCardsFromGroup.add( summaryCardElement.convertToSummaryCardModel() );
        }
        return summaryCardsFromGroup;
    }

    public List<SummaryCard> getAllCards() {
        List<SummaryGroup> groups = getGroups();
        List<SummaryCard> allCards = new ArrayList<>();
        groups.forEach( group -> {
            List<SummaryCard> cardsFromGroup = getCardsFromGroup( group.getName() );
            allCards.addAll( cardsFromGroup );
        } );
        return allCards;
    }

    public void isCardsDateValuesSorted( String requiredGroupName, String cardName ) {
        List<SummaryCard> summaryCards = getCardsFromGroup( requiredGroupName );
        List<String> cardsDateValue = new ArrayList<>();
        List<List<SummaryCardItem>> cards = summaryCards.stream().map( SummaryCard::getSummaryCardItems ).toList();
        for ( List<SummaryCardItem> cardItems : cards ) {
            if ( ((SummaryCardItemDataString) cardItems.stream().findAny().map( SummaryCardItem::getValue ).get()).getData().equals( cardName ) ) {
                for ( SummaryCardItem summaryCardItem : cardItems ) {
                    if ( summaryCardItem.getValue() instanceof SummaryCardItemDataLink ) {
                        String date = ((SummaryCardItemDataLink) summaryCardItem.getValue()).getDateString();
                        if ( date != null && !"No date".equals( date ) ) {
                            cardsDateValue.add( date );
                        }
                    } else if ( summaryCardItem.getValue() instanceof SummaryCardItemDataTable ) {
                        List<Map<String, String>> tableRows = ((Table) ((SummaryCardItemDataTable) summaryCardItem.getValue()).getData()).getRows();
                        for ( Map<String, String> row : tableRows ) {
                            String date = row.get( "Date" );
                            if ( date != null && !"No date".equals( date ) ) {
                                cardsDateValue.add( date );
                            }
                        }
                        LOGGER.info( STR."Cards Date Value:\{cardsDateValue} for \{tableRows.getFirst().keySet().stream().findFirst().get()} in card \{cardName}" );
                        assertTrue( isSortedByDateDescending( cardsDateValue ) );
                        cardsDateValue.clear();
                    }
                }
                if ( !cardsDateValue.isEmpty() ) {
                    LOGGER.info( STR."Cards Date Value:\{cardsDateValue} for \{cardName} card" );
                    assertTrue( isSortedByDateDescending( cardsDateValue ) );
                }
            }
        }
    }

    public boolean isSortedByDateDescending( List<String> dateList ) {
        SimpleDateFormat dateFormat = new SimpleDateFormat( "MMM dd, yyyy" );
        try {
            for ( String date : dateList ) {
                if ( dateList.indexOf( date ) == dateList.size() - 1 )
                    return true;
                Date currentDate = dateFormat.parse( dateList.get( dateList.indexOf( date ) ) );
                Date nextDate = dateFormat.parse( dateList.get( dateList.indexOf( date ) + 1 ) );
                if ( nextDate.after( currentDate ) ) {
                    return false;
                }
            }
        } catch ( ParseException e ) {
            e.printStackTrace();
            return false; // Error occurred, so return false
        }
        return true; // All dates are in descending order
    }

    public SummaryCard getPatientInfoCard() {
        WebElementFacade patientDetailElement = patientInfoCardElement.then( By.cssSelector( PATIENT_IDENTIFIER_DETAILS_SELECTOR ) );

        ListOfWebElementFacades patientDetailItems = patientDetailElement.thenFindAll( By.tagName( "p" ) );
        List<String> patientDetails = patientDetailItems.stream()
                                                        .map( WebElementFacade::getText )
                                                        .collect( Collectors.toList() );
        List<SummaryCardItem> summaryCardItems = new ArrayList<>();
        SummaryCardItem summaryCardItem = new SummaryCardItem();
        summaryCardItem.setType( PATIENT_IDENTIFIER );
        summaryCardItem.setValue( new SummaryCardPatientIdentifier( patientDetails ) );
        summaryCardItems.add( summaryCardItem );

        SummaryCard patientInfoCard = new SummaryCard();
        patientInfoCard.setSummaryItems( summaryCardItems );

        return patientInfoCard;
    }

    public List<String> getInfoTexts() {
        List<WebElementFacade> infoItems = infoSection
                .thenFindAll( By.xpath( "child::*" ) );
        return infoItems.stream().map( item -> item.getText().replace( "\n", " " ) ).toList();
    }

    public void clickButtonOnAiInfo( String buttonText ) {
        WebElementFacade aiInfoWebelement = infoSection.then( By.cssSelector( AI_INFO_SECTION_SELECTOR ) );
        WebElementFacade button = aiInfoWebelement.then( By.cssSelector( AI_INFO_BUTTON_SELECTOR ) );
        if ( button.getText().equals( buttonText ) ) {
            elementClicker( button );
        } else {
            throw new RuntimeException( "Button was not found on AI notification card: " + buttonText );
        }
    }

    public boolean isNavigateToTimelineButtonEnabledOnFooter( SummaryCard card ) {
        WebElementFacade navToTimelineButton = getFooterButton( card, NAVIGATE_TO_TIMELINE );
        return !navToTimelineButton.getAttribute( "class" ).contains( DISABLE_CLASS_IN_CARD_FOOTER );
    }

    public void clickNavigateToTimelineButtonOnFooter( SummaryCard card ) {
        WebElementFacade navToTimelineButton = getFooterButton( card, NAVIGATE_TO_TIMELINE );
        elementClicker( navToTimelineButton );
    }

    public void clickFullReportButtonOnFooter( SummaryCard card ) {
        WebElementFacade navToTimelineButton = getFooterButton( card, FULL_REPORT );
        elementClicker( navToTimelineButton );
    }

    public void hoverFullReportButtonOnFooter( SummaryCard card ) {
        WebElementFacade navToReportButton = getFooterButton( card, FULL_REPORT );
        hoverOnWebElement( navToReportButton );
    }

    public boolean isOpenFullReportButtonEnabledOnFooter( SummaryCard card ) {
        WebElementFacade fullReportButton = getFooterButton( card, FULL_REPORT );
        return !fullReportButton.getAttribute( "class" ).contains( DISABLE_CLASS_IN_CARD_FOOTER );
    }

    private WebElementFacade getFooterButton( SummaryCard card, FOOTER_BUTTONS footerButton ) {
        List<WebElementFacade> footerButtons = getButtonsFromCardFooter( card );
        return footerButtons.get( footerButton.getNum() );
    }

    public List<WebElementFacade> getButtonsFromCardFooter( SummaryCard card ) {
        WebElementFacade requiredCard = getCardElement( card.getGroup(), card.getCardIndexInGroup() );
        WebElementFacade footerElement = getFooterFromCard( requiredCard );
        List<WebElementFacade> footerButtons = footerElement.thenFindAll( By.xpath( "child::*" ) );
        assertThat( "2 buttons are expected on the card footer: " + card, footerButtons.size(), is( equalTo( 2 ) ) );
        return footerButtons;
    }

    private WebElementFacade getCardElement( String group, int cardIndexInGroup ) {
        SummaryGroupElement groupElement = getGroupElement( group );
        List<WebElementFacade> cardElements = getCardElements( groupElement );
        if ( cardElements.size() < cardIndexInGroup + 1 ) {
            fail( String.format( "Required card index is %s but there are only %s cards in the group: %s",
                    cardIndexInGroup, cardElements.size(), group ) );
        }
        return cardElements.get( cardIndexInGroup );
    }

    public void clickOnDataItemOnSummaryCard( int cardIndex, String groupName, SummaryCardItem expectedCardItem ) {
        SummaryCardItemElement cardItemElement = getSummaryCardItem( cardIndex, groupName, expectedCardItem );
        elementClicker( getClickableElement( cardItemElement ) );
    }

    public void clickButtonOnSummaryCardItem( int cardIndex, String groupName, SummaryCardItem expectedCardItem, String buttonText ) {
        SummaryCardItemElement cardItemElement = getSummaryCardItem( cardIndex, groupName, expectedCardItem );
        WebElement requiredButton = cardItemElement.getWebElement().findElement( By.cssSelector( BUTTON_IN_DATA_ITEM_SELECTOR ) );
        assertEquals( "Button text is not ok at the summary element.", buttonText, requiredButton.getText() );
        requiredButton.click();
    }

    public void clickLinkOnSummaryCardItem( int cardIndex, String groupName, SummaryCardItem expectedCardItem, String linkText ) {
        SummaryCardItemElement cardItemElement = getSummaryCardItem( cardIndex, groupName, expectedCardItem );
        WebElement requiredLink =
                cardItemElement
                        .getWebElement()
                        .findElements( By.cssSelector( SUMMARY_LINK_SELECTOR ) )
                        .stream().filter( e -> e.getText().equals( linkText ) )
                        .findFirst().orElse( null );
        assertNotNull( STR."Link with text \{linkText} was not found", requiredLink );
        assertEquals( "Link text is not ok at the summary element.", linkText, requiredLink.getText() );
        requiredLink.click();
    }

    private SummaryCardItemElement getSummaryCardItem( int cardIndex, String groupName, SummaryCardItem expectedCardItem ) {
        List<SummaryCardItemElement> cardDataItems = getCardElements( groupName, cardIndex );
        LOGGER.info( STR."Observed card items: \{cardDataItems.stream().map( SummaryCardItemElement::getSummaryCardItem ).toList()}" );
        return cardDataItems.stream()
                            .filter( cardDataItem -> cardDataItem.getSummaryCardItem().equals( expectedCardItem ) )
                            .findFirst()
                            .orElseThrow( () -> new RuntimeException(
                                    String.format( "Following card item was not found in group %s, cardIndex: %s: %s", groupName, cardIndex, expectedCardItem ) ) );
    }

    private WebElementFacade getClickableElement( SummaryCardItemElement cardItemElement ) {
        WebElementFacade clickableElement;
        if ( currentlyContainsElements( cardItemElement.getWebElement(), By.tagName( "a" ) ) ) {
            clickableElement = cardItemElement.getWebElement().then( By.tagName( "a" ) );
        } else if ( currentlyContainsElements( cardItemElement.getWebElement(), By.cssSelector( REPORT_LINK_ITEM_SELECTOR ) ) ) {
            clickableElement = cardItemElement.getWebElement().then( By.cssSelector( REPORT_LINK_ITEM_SELECTOR ) );
        } else {
            clickableElement = cardItemElement.getWebElement();
        }
        return clickableElement;
    }

    public void clickViewOnTimelineButtonOnCommentItem( int cardIndex, String groupName, CommentItem expectedComment ) {
        List<SummaryCardItemElement> cardDataItems = getCardElements( groupName, cardIndex );
        SummaryCardItemElement commentsElement = cardDataItems.stream()
                                                              .filter( cardDataItem -> cardDataItem.getSummaryCardItem().getType().equals( COMMENTS ) )
                                                              .findFirst()
                                                              .orElseThrow( () -> new RuntimeException(
                                                                      String.format( "Comments were not found in group %s, cardIndex: %s: %s", groupName, cardIndex ) ) );
        WebElementFacade commentsParent = commentsElement.getWebElement().then( By.xpath( "./.." ) );
        Comments comments = PageFactory.initElements( commentsParent.getWrappedElement(), Comments.class );
        comments.clickViewOnTimelineButton( changeParametersInComment( expectedComment ) );
    }


    private List<SummaryCardItemElement> getCardElements( String requiredGroupName, int cardIndex ) {
        SummaryGroupElement groupElement = getGroupElement( requiredGroupName );
        List<WebElementFacade> cardElements = groupElement.getGroupElement().thenFindAll( By.cssSelector( SUMMARY_CARD_SELECTOR ) );
        if ( cardElements.size() < cardIndex + 1 ) {
            fail( String.format( "Card index %s is needed but only %s cards are available", cardIndex, cardElements.size() ) );
        }
        WebElementFacade requiredCard = cardElements.get( cardIndex );
        return getCardItemElements( requiredCard );
    }

    private WebElementFacade getFooterFromCard( WebElementFacade card ) {
        return card.then( By.cssSelector( SUMMARY_CARD_FOOTER_SELECTOR ) );
    }

    private SummaryGroupElement getGroupElement( String groupName ) {
        List<SummaryGroupElement> groups = getAllGroupElements();
        return groups.stream()
                     .filter( group -> group.getSummaryGroup().getName().equals( groupName ) )
                     .findFirst()
                     .orElseThrow( () -> new RuntimeException( "Following summary group was not found: " + groupName ) );
    }

    private List<SummaryGroupElement> getAllGroupElements() {
        List<SummaryGroupElement> groupElements = new ArrayList<>();
        List<WebElementFacade> columns = summaryContainer.thenFindAll( By.cssSelector( SUMMARY_COLUMN_SELECTOR ) );
        for ( int columnIndex = 0; columnIndex < columns.size(); columnIndex++ ) {
            WebElementFacade colum = columns.get( columnIndex );
            List<WebElementFacade> groupsInColumn = colum.thenFindAll( By.xpath( "child::*" ) );
            for ( int groupIndex = 0; groupIndex < groupsInColumn.size(); groupIndex++ ) {
                WebElementFacade groupWebElement = groupsInColumn.get( groupIndex );
                String groupName = "";  // in case of the group of patient and info cards
                String groupBadge = "";
                if ( currentlyContainsElements( groupWebElement, By.cssSelector( SUMMARY_GROUP_NAME_SELECTOR ) ) ) {
                    groupName = groupWebElement.then( By.cssSelector( SUMMARY_GROUP_NAME_SELECTOR ) ).getText();
                }
                if ( currentlyContainsElements( groupWebElement, By.cssSelector( SUMMARY_GROUP_BADGE_SELECTOR ) ) ) {
                    groupBadge = groupWebElement.then( By.cssSelector( SUMMARY_GROUP_BADGE_SELECTOR ) ).getText();
                }
                SummaryGroup summaryGroup = new SummaryGroup( groupName, columnIndex, groupIndex, groupBadge );
                SummaryGroupElement summaryGroupElement = new SummaryGroupElement( summaryGroup, groupWebElement );
                groupElements.add( summaryGroupElement );
            }
        }
        return groupElements;
    }

    private List<WebElementFacade> getCardElements( SummaryGroupElement groupElement ) {
        return groupElement.getGroupElement().thenFindAll( By.cssSelector( SUMMARY_CARD_SELECTOR ) );
    }

    private List<SummaryCardItemElement> getCardItemElements( WebElementFacade cardElement ) {
        List<WebElementFacade> summaryCardItemWebElements = cardElement.thenFindAll( By.xpath( "child::*" ) );

        // This list will contain all the items from a card (e.g. empty, state, comment, title, paragraph title, paragraph text, table, graph, etc.)
        List<SummaryCardItemElement> summaryCardItemElements = new ArrayList<>();

        for ( int i = 0; i < summaryCardItemWebElements.size(); i++ ) {
            WebElementFacade actualWebElement = summaryCardItemWebElements.get( i );
            String actualClass = actualWebElement.getAttribute( "class" );
            SummaryCardItem summaryCardItem = new SummaryCardItem();
            // Identify and get the item from Summary card (e.g. title, paragraph title, paragraph text, table, graph, etc.)
            if ( actualClass.contains( FOOTER_ITEM_CLASS ) ) {
                summaryCardItem.setType( FOOTER );
                summaryCardItemElements.add( prepSummaryCardItemElement( summaryCardItem, actualWebElement ) );

            } else if ( actualClass.contains( EMPTY_STATE_ITEM_CLASS ) ) {   // usually, this is observed on cards: empty state without footer                
                summaryCardItem.setType( SummaryCardItemType.EMPTY_STATE );
                summaryCardItem.setValue( new SummaryCardItemDataString( actualWebElement.getText() ) );
                summaryCardItem.setColor( new SummaryCardItemDataColor( Color.fromString( actualWebElement.getCssValue( "color" ) ) ) );
                summaryCardItemElements.add( prepSummaryCardItemElement( summaryCardItem, actualWebElement ) );

            } else if ( actualClass.contains( "TextElement-module_paragraph" ) ) {
                summaryCardItem.setType( SummaryCardItemType.NORMAL_TEXT );
                summaryCardItem.setValue( new SummaryCardItemDataString( actualWebElement.getText() ) );
                summaryCardItemElements.add( prepSummaryCardItemElement( summaryCardItem, actualWebElement ) );

            } else if ( actualClass.contains( KEY_VALUE_ITEM_CLASS ) || actualClass.equals( "u-mb" ) ) {
                setKeyValueDataToCard( summaryCardItem, actualWebElement );
                summaryCardItemElements.add( prepSummaryCardItemElement( summaryCardItem, actualWebElement ) );

            } else if ( actualClass.contains( CLP_HIGHLIGHT_CLASS )
                    || currentlyContainsElements( actualWebElement, By.cssSelector( String.format( "[class*=%s]", CLP_HIGHLIGHT_CLASS ) ) ) ) {
                summaryCardItem.setType( SummaryCardItemType.CLP_DATA );
                String clpContent = getClpItemText( actualWebElement );
                SummaryCardItemDataCLP summaryCardItemDataCLP = new SummaryCardItemDataCLP();
                summaryCardItemDataCLP.setClpContent( clpContent );
                summaryCardItem.setValue( summaryCardItemDataCLP );
                summaryCardItemElements.add( prepSummaryCardItemElement( summaryCardItem, actualWebElement ) );
            } else if ( actualClass.contains( SUMMARY_CARD_CONTENT_CLASS )
                    || actualClass.contains( "u-pb--" )
                    || actualClass.contains( "u-mv--" )
                    || actualClass.contains( TWO_COLUMN_LINK_ITEM_CLASS ) ) {

                List<SummaryCardItemElement> cardItems = processDetailedCardContent( actualWebElement );
                summaryCardItemElements.addAll( cardItems );
                //todo refactor and merge this method with processDetailedCardContent due to duplicate checks
            } else if ( actualClass.contains( CARD_TITLE_ITEM_CLASS ) ) {
                summaryCardItem.setType( SummaryCardItemType.CARD_TITLE );
                summaryCardItem.setValue( new SummaryCardItemDataString( actualWebElement.getText() ) );
                summaryCardItemElements.add( prepSummaryCardItemElement( summaryCardItem, actualWebElement ) );
            } else if ( actualClass.contains( TABLE_ITEM_CLASS ) ) {
                summaryCardItem.setType( SummaryCardItemType.TABLE );
                TableItem tableItem = createTableItem( "", actualWebElement );
                summaryCardItem.setValue( new SummaryCardItemDataTable( tableItem.getValue() ) );
                summaryCardItemElements.add( prepSummaryCardItemElement( summaryCardItem, actualWebElement ) );
            } else if ( actualClass.contains( CLINICAL_TRIAL_DESCRIPTION_CLASS ) ) {
                summaryCardItem.setType( SummaryCardItemType.CLINICAL_TRIAL_DESCRIPTION );
                summaryCardItem.setValue( SummaryCardItemDataTrial.builder()
                                                                  .trialDescription( getClinicalTrialLinkableDescription( actualWebElement ) )
                                                                  .build() );
                summaryCardItemElements.add( prepSummaryCardItemElement( summaryCardItem, actualWebElement ) );

            } else if ( actualClass.contains( "module_indicators-container" ) ) {
                summaryCardItem.setType( SummaryCardItemType.CLINICAL_TRIAL_ROW_BADGE_DATA );
                RowBadgeItem rowBadgeItem = getBadgeRowItems( actualWebElement );
                summaryCardItem.setValue(
                        SummaryCardItemDataTrial.builder()
                                                .rowBadgeItem( rowBadgeItem )
                                                .build() );

                summaryCardItemElements.add( prepSummaryCardItemElement( summaryCardItem, actualWebElement ) );

            } else if ( actualClass.contains( "text-16 u-mb" ) ) {      // e.g. "From Mar 07, 2017 through Jun 07, 2017"
                summaryCardItem.setType( SummaryCardItemType.NORMAL_TEXT );
                summaryCardItem.setValue( new SummaryCardItemDataString( actualWebElement.getText() ) );
                summaryCardItemElements.add( prepSummaryCardItemElement( summaryCardItem, actualWebElement ) );
            } else if ( actualClass.isEmpty() ) {
                if ( !actualWebElement.thenFindAll( By.cssSelector( ".button" ) ).isEmpty() ) {
                    summaryCardItem.setType( SummaryCardItemType.BUTTON );
                    summaryCardItem.setValue( new SummaryCardItemDataString( actualWebElement.getText() ) );
                    summaryCardItemElements.add( prepSummaryCardItemElement( summaryCardItem, actualWebElement ) );
                }
            } else {
                summaryCardItem.setType( SummaryCardItemType.UNKNOWN_ITEM );
                summaryCardItem.setValue( new SummaryCardItemDataString( actualWebElement.getText() ) );
                summaryCardItemElements.add( prepSummaryCardItemElement( summaryCardItem, actualWebElement ) );
            }
        }

        return summaryCardItemElements;
    }

    private static void setKeyValueDataToCard( SummaryCardItem summaryCardItem, WebElementFacade actualWebElement ) {
        summaryCardItem.setType( SummaryCardItemType.KEY_VALUE );
        SummaryCardItemDataKeyValuePair keyValueData = new SummaryCardItemDataKeyValuePair();
        List<WebElementFacade> keyValueElements = actualWebElement.thenFindAll( By.xpath( "child::*" ) );
        assertThat( "Key-value elements size is not ok on summary card. ", keyValueElements.size(), is( 2 ) );
        keyValueData.setKey( keyValueElements.get( 0 ).getText() );
        keyValueData.setValue( keyValueElements.get( 1 ).getText() );
        summaryCardItem.setValue( keyValueData );
    }

    private SummaryCardItemElement prepSummaryCardItemElement( SummaryCardItem summaryCardItem, WebElementFacade actualWebElement ) {
        SummaryCardItemElement summaryCardItemElement = new SummaryCardItemElement();
        summaryCardItemElement.setSummaryCardItem( summaryCardItem );
        summaryCardItemElement.setWebElement( actualWebElement );
        return summaryCardItemElement;
    }

    private List<SummaryCardItemElement> processDetailedCardContent( WebElementFacade cardElement ) {
        List<WebElementFacade> summaryCardItemWebElements = cardElement.thenFindAll( By.xpath( "child::*" ) );

        // This list will contain all the items from a card (e.g. title, paragraph title, paragraph text, table, graph, etc.)
        List<SummaryCardItemElement> summaryCardItemElements = new ArrayList<>();

        for ( int i = 0; i < summaryCardItemWebElements.size(); i++ ) {
            WebElementFacade actualWebElement = summaryCardItemWebElements.get( i );
            String actualClass = actualWebElement.getAttribute( "class" );

            String nextClass = null;
            if ( i < summaryCardItemWebElements.size() - 1 ) {
                WebElementFacade nextElement = summaryCardItemWebElements.get( i + 1 );
                nextClass = nextElement.getAttribute( "class" );
            }
            SummaryCardItemElement summaryCardItemElement = new SummaryCardItemElement();
            summaryCardItemElement.setWebElement( actualWebElement );

            // Identify and get the item from Summary card (e.g. title, paragraph title, paragraph text, table, graph, etc.)
            SummaryCardItem summaryCardItem = new SummaryCardItem();
            boolean isSeparatorElement = false; // used to skip vertical line on the summary card
            if ( i == 0 && actualClass.contains( CARD_TITLE_ITEM_CLASS ) ) {
                summaryCardItem.setType( SummaryCardItemType.CARD_TITLE );
                summaryCardItem.setValue( new SummaryCardItemDataString( actualWebElement.getText() ) );

            } else if ( actualClass.contains( EMPTY_STATE_ITEM_CLASS ) ) {  // usually, this is observed on cards: empty state with footer
                summaryCardItem.setType( SummaryCardItemType.EMPTY_STATE );
                summaryCardItem.setValue( new SummaryCardItemDataString( actualWebElement.getText() ) );

            } else if ( isNormalText( actualWebElement, actualClass, nextClass ) ) {
                String text = actualWebElement.getText();
                if ( text.isEmpty() ) {
                    // This is only a separator element, no need to process
                    isSeparatorElement = true;
                } else {
                    summaryCardItem.setType( SummaryCardItemType.NORMAL_TEXT );
                    summaryCardItem.setValue( new SummaryCardItemDataString( actualWebElement.getText() ) );
                }

            } else if ( !actualClass.contains( SUMMARY_ITEM_CLASS ) && !actualClass.contains( TABLE_ITEM_CLASS ) && nextClass != null && nextClass.contains( PARAGRAPH_TEXT_ITEM_CLASS ) ) {
                // Skeleton only, implement later when card details will be verified
                summaryCardItem.setType( SummaryCardItemType.PARAGRAPH_TITLE );

            } else if ( actualClass.contains( PARAGRAPH_TEXT_ITEM_CLASS ) ) {
                // Skeleton only, implement later when card details will be verified
                summaryCardItem.setType( SummaryCardItemType.PARAGRAPH_TEXT );

            } else if ( actualClass.contains( BADGE_ITEM_CLASS ) ) {
                // Skeleton only, implement later when card details will be verified
                summaryCardItem.setType( SummaryCardItemType.BADGE );

            } else if ( actualClass.contains( TABLE_ITEM_CLASS ) ) {
                summaryCardItem.setType( SummaryCardItemType.TABLE );
                TableItem tableItem = createTableItem( "", actualWebElement );
                summaryCardItem.setValue( new SummaryCardItemDataTable( tableItem.getValue() ) );

            } else if ( actualClass.contains( KEY_VALUE_ITEM_CLASS ) ) {
                setKeyValueDataToCard( summaryCardItem, actualWebElement );

            } else if ( actualClass.contains( COMMENTS_INFO_CLASS ) ) {
                continue;
            } else if ( actualClass.contains( COMMENTS_CLASS ) ) {
                summaryCardItem.setType( COMMENTS );
                WebElementFacade commentsParent = actualWebElement.then( By.xpath( "./.." ) );
                Comments comments = PageFactory.initElements( commentsParent.getWrappedElement(), Comments.class );
                SummaryCardItemComment commentsData = new SummaryCardItemComment( comments.getComments() );
                summaryCardItem.setValue( commentsData );

            } else if ( !actualClass.contains( CLP_HIGHLIGHT_CLASS ) &&
                    (actualClass.contains( TWO_COLUMN_LINK_ITEM_CLASS ) ||
                            currentlyContainsElements( actualWebElement, By.cssSelector( REPORT_LINK_ITEM_SELECTOR ) ) ||
                            currentlyContainsElements( actualWebElement, By.tagName( "a" ) )) ||
                    actualWebElement.getTagName().equals( "a" ) ) {
                // Two columns link (e.g. link with date) or report link (e.g. Open complete report) or "Open all comments" link

                summaryCardItem.setType( SummaryCardItemType.LINK );
                SummaryCardItemDataLink linkData = new SummaryCardItemDataLink();
                List<WebElementFacade> linkTextElements = actualWebElement.thenFindAll( By.xpath( "child::*" ) );
                linkData.setLinkString( linkTextElements.getFirst().getText() );
                if ( linkTextElements.size() > 1 ) { // link with date
                    linkData.setDateString( linkTextElements.get( 1 ).getText() );
                } else {
                    List<WebElementFacade> linkDateElements = actualWebElement.thenFindAll( By.xpath( "following-sibling::*" ) );
                    if ( !linkDateElements.isEmpty() && linkDateElements.getFirst().getAttribute( "class" ).contains( SUMMARY_DATE_CLASS ) ) { // link with date
                        linkData.setDateString( linkDateElements.getFirst().getText() );
                        i++; //skip over found date element
                    }
                }
                summaryCardItem.setValue( linkData );

            } else if ( actualClass.contains( CLP_HIGHLIGHT_CLASS ) ) {
                summaryCardItem.setType( SummaryCardItemType.CLP_DATA );
                String clpContent = getClpItemText( actualWebElement );
                SummaryCardItemDataCLP summaryCardItemDataCLP = new SummaryCardItemDataCLP();
                summaryCardItemDataCLP.setClpContent( clpContent );
                summaryCardItem.setValue( summaryCardItemDataCLP );

            } else {
                summaryCardItem.setType( SummaryCardItemType.UNKNOWN_ITEM );
                summaryCardItem.setValue( new SummaryCardItemDataString( actualWebElement.getText() ) );
            }
            summaryCardItemElement.setSummaryCardItem( summaryCardItem );

            if ( !isSeparatorElement ) {
                summaryCardItemElements.add( summaryCardItemElement );
            }
        }
        return summaryCardItemElements;
    }

    //TODO: improve the logic of nextClass!=null. Current implementation cause false alarm failures in Trials tab
    private boolean isNormalText( WebElementFacade actualWebElement, String actualClass, String nextClass ) {
        if ( currentlyContainsElements( actualWebElement, By.tagName( "a" ) ) || actualWebElement.getTagName().equals( "a" ) ) { // this is a link item, e.g: "Open all comments" / links on Medical history card
            return false;
        } else {
            return !actualClass.isEmpty() &&
                    !actualClass.contains( SUMMARY_ITEM_CLASS ) &&
                    !actualClass.contains( TABLE_ITEM_CLASS ) &&
                    !actualClass.contains( TWO_COLUMN_LINK_ITEM_CLASS ) &&
                   /* nextClass != null &&
                    !nextClass.contains( PARAGRAPH_TEXT_ITEM_CLASS ) &&*/
                    !actualClass.contains( COMMENTS_INFO_CLASS ) &&
                    !actualClass.contains( COMMENTS_CLASS ) &&
                    !actualClass.contains( CLP_HIGHLIGHT_CLASS );
        }
    }

    public void clickStatusAndResponseButton() {
        elementClicker( statusAndResponseButton );
    }

    public void clickClinicalTrialsButton() {
        elementClicker( clinicalTrialsButton );
    }

    public boolean isStatusAndResponseButtonVisible() {
        return statusAndResponseButton.isCurrentlyVisible();
    }

    public void hoverOnHighlightedTextInCard( int cardIndex, String groupName, SummaryCardItem cardItem ) {
        List<SummaryCardItemElement> cardDataItems = getCardElements( groupName, cardIndex );
        SummaryCardItemElement summaryCardItemElement = cardDataItems.stream()
                                                                     .filter( c -> c.getSummaryCardItem().getType().equals( cardItem.getType() ) && c.getSummaryCardItem().getValue().equals( cardItem.getValue() ) )
                                                                     .findFirst()
                                                                     .orElseThrow( () -> new RuntimeException( "Failed to hover on Summary card element" ) );
        WebElementFacade clpText = summaryCardItemElement.getWebElement().find( By.cssSelector( SUMMARY_CARD_CLP_MODULE ) );
        hoverOnWebElement( clpText );
    }

    public List<String> getSectionsInViewport() {
        return getDriver().findElements( By.cssSelector( "[class*='SummaryGroup-module_name_']" ) )
                          .stream()
                          .filter( PageElementUtils::isVisibleInViewport )
                          .map( WebElement::getText )
                          .toList();
    }

    public void clickOnTrialDescriptionLink( String description ) {
        elementClicker( find( By.xpath( String.format( "//section[starts-with(text(),'%s')]", description ) ) ) );

    }

    public void clickOnLinkInKeyEventsTable( String reportTitle ) {
        WebElementFacade reportElement = keyEvents.then( By.xpath( STR.".//*[normalize-space(text())='\{reportTitle}']" ) );
        elementClicker( reportElement );
    }

    public String clickOnAnyClinicalTrialOption() {
        waitUntilLoadingSkeletonDisappears();
        LOGGER.info( STR."The number of trials displayed on page:\{getClinicalTrials().size()}" );
        if(!getClinicalTrials().isEmpty()){
            WebElementFacade option = getClinicalTrials().get( new Random().nextInt( 0, getClinicalTrials().size() ) );
            String text = option.getText();
            elementClicker( option );
            return text;
        }
        return null;
    }

    public String clickOnAnyClinicalRadioligandOption() {
        waitUntilLoadingSkeletonDisappears();
        LOGGER.info( STR."The number of Radioligands displayed on page:\{getClinicalRadioligandTherapyOptions().size()}" );
        WebElementFacade option = getClinicalRadioligandTherapyOptions().get( new Random().nextInt( 0, getClinicalRadioligandTherapyOptions().size() ) );
        String text = option.getText();
        elementClicker( option );
        return text;
    }

    public InfoMessage getHiddenInfoMessage() {
        List<WebElementFacade> messageElements = allCardsHiddenInfoContainer.thenFindAll( By.tagName( "p" ) );
        return new InfoMessage( messageElements.get( 0 ).getText(), messageElements.get( 1 ).getText() );
    }

    public void clickOnEditSummaryVisibilityButton() {
        WebElementFacade button = allCardsHiddenInfoContainer.then( By.tagName( "button" ) );
        elementClicker( button );
    }

    public WebElement getCardContentById( String cardName ) {
        List<WebElement> cardElements = getDriver().findElements( By.cssSelector( SUMMARY_CARDS_LIST ) );
        for ( WebElement cardElement : cardElements ) {
            if ( cardElement.getText().contains( cardName ) || cardElement.getText().contains( cardName ) ) {
                return cardElement.findElement( By.xpath( MOST_RECENT_CONTENT ) );
            }
        }
        return null;
    }

    public boolean checkButtonExist( String buttonName ) {
        return getDriver().findElements( By.cssSelector( BUTTON_IN_DATA_ITEM_SELECTOR ) ).stream()
                          .anyMatch( button -> button.getText().equals( buttonName ) );
    }

    public void checkAndClickSeeAllClinicalTrialButton( String buttonText ) {
        getDriver().findElements( By.xpath( SEE_ALL_CLINICAL_TRIALS_BUTTON_LOCATOR ) )
                   .stream()
                   .filter( cardElement -> cardElement.getText().equals( buttonText ) )
                   .toList()
                   .getFirst()
                   .click();
    }

    public void checkAndClickSeeAllRadioligandTherapyOptions() {
        getDriver().findElements( By.cssSelector( SEE_ALL_RADIOLIGAND_THERAPY_OPTIONS_CSS) )
                .getFirst()
                .click();
    }

    public void clickOnButton( String buttonName, String groupName ) {
        getDriver().findElements( By.cssSelector( BUTTON_IN_DATA_ITEM_SELECTOR ) ).stream()
                   .filter( cardElement -> cardElement.getText().equals( buttonName ) ).toList().getFirst().click();
    }

    public boolean isLoadingSkeletonVisible() {
        Optional<WebElementFacade> loadingSkeleton = findFirst( By.cssSelector( LOADING_SKELETON_SELECTOR ) );
        return loadingSkeleton.isPresent() && loadingSkeleton.get().isVisible();
    }
}
