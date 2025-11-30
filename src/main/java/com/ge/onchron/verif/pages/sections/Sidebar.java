/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2021, 2021, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.pages.sections;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.Badge;
import com.ge.onchron.verif.model.BannerNotification;
import com.ge.onchron.verif.model.DetailedImportantSection;
import com.ge.onchron.verif.model.DetailedSidebarHeader;
import com.ge.onchron.verif.model.detailedDataItems.DetailedDataItem;
import com.ge.onchron.verif.pages.SimplePage;
import com.ge.onchron.verif.utils.TextUtils;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.ListOfWebElementFacades;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.SystemTestConstants.BOLD_TEXT_FONT_WEIGHT;
import static com.ge.onchron.verif.SystemTestConstants.FONT_WEIGHT;
import static com.ge.onchron.verif.pages.utils.BadgeUtils.getEventBadgesFromWebElement;
import static com.ge.onchron.verif.pages.utils.PageElementUtils.hoverOnWebElement;
import static com.ge.onchron.verif.pages.utils.PageElementUtils.moveToElement;
import static com.ge.onchron.verif.pages.utils.PageUtils.currentlyContainsElements;
import static com.ge.onchron.verif.pages.utils.PageUtils.currentlyContainsWebElements;

public class Sidebar extends SimplePage {

    private final String SIDEBAR_SKELETON_LOADER_XPATH = ".//*[contains(@class,'SkeletonLoader-module_container')]";

    private final String MARK_AS_IMPORTANT_STAR_FILLED = "Star_-_Filled_-_16";
    private final String MARK_AS_IMPORTANT_STAR_CSS_SELECTOR = "[id^='Star']";   // the id starts with 'Star'
    private final String ID = "id";
    private final String HEADER_BUTTONS_SECTION_CLASS = "flex--justify";
    private final String SIDEBAR_HEADER_XPATH = ".//*[contains(@class,'EventSidebar-module_header')]";
    private final String MARK_AS_IMPORTANT_BUTTON_XPATH = SIDEBAR_HEADER_XPATH + "//*[contains(@class, 'button') and contains(normalize-space(text()), 'as important')]";
    private final String BUTTON_TAGNAME = "button";
    private final String LABEL_LIST_ITEM_SELECTOR = "[class*='label-list-item']";
    private final String SIDEBAR_NLP_TEXT = "[class*='Nlp-module_content']";

    private final String ADHERENCE_DATE_TABLE_SELECTOR = "[class*='SidebarInfoTable-module_container_']";
    private final String ADHERENCE_DATE_TABLE_ROW_SELECTOR = "[class*='SidebarInfoTable-module_row_']";
    private final String ADHERENCE_DATE_TABLE_KEY_SELECTOR = "[class*='SidebarInfoTable-module_key_']";
    private final String ADHERENCE_DATE_TABLE_VALUE_SELECTOR = "[class*='SidebarInfoTable-module_value_']";

    private final String SIDEBAR_BANNER_SELECTOR = "[class*='EventSidebar-module_sidebar-banner']";
    private final String SIDEBAR_BANNER_MESSAGE_SELECTOR = "[class*='BannerNotification-module_msg']";
    private final String BADGE_LIST_CONTAINER = "[class*='EventSidebar-module_label-list']";

    private final String NLP_MODULE_CONTAINER = "[class*='Nlp-module_container']";

    private static final Logger LOGGER = LoggerFactory.getLogger( Sidebar.class );
    @FindBy( xpath = "//*[contains(@class,'TimelineDashboard-module_sidebar')]" )
    private WebElementFacade sidebar;

    @FindBy( xpath = SIDEBAR_HEADER_XPATH )
    private WebElementFacade sidebarEventDetailsHeader;

    @FindBy( xpath = "//*[contains(@class,'Sidebar-module_content')]" )
    private WebElementFacade sidebarEventDetailsContent;

    @FindBy( xpath = MARK_AS_IMPORTANT_BUTTON_XPATH )
    private WebElementFacade markAsImportantButton;

    @FindBy( xpath = "//*[contains(@class,'EventSidebar-module_label-btn')]" )
    private WebElementFacade labelButton;

    @FindBy( css = LABEL_LIST_ITEM_SELECTOR )
    private List<WebElementFacade> labelList;

    @FindBy( css = SIDEBAR_NLP_TEXT )
    private List<WebElementFacade> sidebarNLPTextList;

    @FindBy( css = ADHERENCE_DATE_TABLE_SELECTOR )
    private WebElementFacade adherenceDateTable;

    @FindBy( css = NLP_MODULE_CONTAINER )
    private WebElementFacade nlpModuleContainer;

    public boolean isSidebarCurrentlyVisible() {
        return sidebar.isCurrentlyVisible();
    }

    public boolean isSidebarVisible() {
        return sidebar.isVisible();
    }

    public void waitUntilSidebarContentLoadingSkeletonDisappears() {
        waitUntilLoadingSkeletonDisappears( sidebarEventDetailsContent, By.xpath( SIDEBAR_SKELETON_LOADER_XPATH ) );
    }

    public DetailedSidebarHeader getHeader() {
        String title = getTitle( sidebarEventDetailsHeader );
        DetailedSidebarHeader header = new DetailedSidebarHeader( title );
        List<WebElementFacade> properties = sidebarEventDetailsHeader.thenFindAll( By.tagName( "p" ) );
        if ( properties.size() > 0 ) {
            header.setDateText( properties.get( 0 ).getText() );
        }
        if ( properties.size() > 1 ) {
            header.setReportType( properties.get( 1 ).getText() );
        }

        List<Badge> allBadges = new ArrayList<>();

        if ( currentlyContainsElements( sidebarEventDetailsContent, By.cssSelector( BADGE_LIST_CONTAINER ) ) ) {
            WebElementFacade badgeContainer = sidebarEventDetailsHeader.then( By.cssSelector( BADGE_LIST_CONTAINER ) );
            List<Badge> foundBadges = getEventBadgesFromWebElement( badgeContainer );
            allBadges.addAll( foundBadges );
        }
        header.setBadges( allBadges );
        return header;
    }

    private static String getTitle( WebElementFacade element ) {
        WebElementFacade titleElement = element.then( By.tagName( "h2" ) );
        if ( currentlyContainsElements( titleElement, By.tagName( "span" ) ) ) {
            return getTitleTextFrom( titleElement );
        }
        return titleElement.getText();
    }

    private static String getTitleTextFrom( WebElementFacade titleElement ) {
        ListOfWebElementFacades titleParts = titleElement.thenFindAll( By.tagName( "span" ) );
        StringBuilder title = new StringBuilder();
        titleParts.forEach( titlePart -> {
            if ( title.length() != 0 ) {
                title.append( SPACE );
            }
            title.append( titlePart.getText() );
        } );
        return title.toString();
    }

    public void clickOnCloseButton() {
        WebElementFacade closeButton = sidebarEventDetailsHeader.then( By.xpath( "//button[contains(text(),'Close')]" ) );
        elementClicker( closeButton );
    }

    public DetailedImportantSection getMarkAsImportantDetails() {
        boolean headerIsBold = sidebarEventDetailsHeader.then( By.tagName( "h2" ) ).getCssValue( FONT_WEIGHT ).equals( BOLD_TEXT_FONT_WEIGHT );
        WebElementFacade headerButtonsSection = sidebarEventDetailsHeader.then( By.className( HEADER_BUTTONS_SECTION_CLASS ) );
        boolean starIsFilled = headerButtonsSection.then( By.cssSelector( MARK_AS_IMPORTANT_STAR_CSS_SELECTOR ) ).getAttribute( ID ).equalsIgnoreCase( MARK_AS_IMPORTANT_STAR_FILLED );
        // If the event is marked as important the button disappears and the text can only be read from a different, newly created class
        String markAsImportantButtonText = headerButtonsSection.then( By.tagName( BUTTON_TAGNAME ) ).getText();
        return new DetailedImportantSection( headerIsBold, starIsFilled, markAsImportantButtonText );
    }

    public void clickOnMarkAsImportantButton( String markOrMarked ) {
        if ( getMarkAsImportantDetails().isStarFilled() && markOrMarked.equals( "Mark" ) ) {
            fail( "Event was already marked as important" );
        } else if ( !getMarkAsImportantDetails().isStarFilled() && markOrMarked.equals( "Marked" ) ) {
            fail( "Event was not marked as important" );
        } else {
            elementClicker( markAsImportantButton );
        }
    }

    public Comments getCommentSection() {
        return PageFactory.initElements( sidebarEventDetailsContent, Comments.class );
    }

    public DetailedPatientData getDetailedPatientData() {
        return PageFactory.initElements( sidebarEventDetailsContent, DetailedPatientData.class );
    }

    public String getCommentInfoFromHeader() {
        WebElementFacade headerButtonsSection = sidebarEventDetailsHeader.then( By.className( HEADER_BUTTONS_SECTION_CLASS ) );
        ListOfWebElementFacades headerButtons = headerButtonsSection.thenFindAll( By.tagName( BUTTON_TAGNAME ) );
        return headerButtons.get( headerButtons.size() - 1 ).getText(); // the last element is the comments info button
    }

    public void clickOnLabelButton() {
        elementClicker( labelButton );
    }

    public List<BannerNotification> getSidebarBanners() {
        Map<BannerNotification, WebElementFacade> sidebarBannerWebElements = getSidebarBannersWithWebElements();
        return new ArrayList<>( sidebarBannerWebElements.keySet() );
    }

    private LinkedHashMap<BannerNotification, WebElementFacade> getSidebarBannersWithWebElements() {
        List<WebElementFacade> sidebarBannerWebElements = sidebarEventDetailsHeader
                .then( By.cssSelector( SIDEBAR_BANNER_SELECTOR ) )
                .thenFindAll( By.cssSelector( SIDEBAR_BANNER_MESSAGE_SELECTOR ) );
        LinkedHashMap<BannerNotification, WebElementFacade> sidebarBannersWithWebelements = new LinkedHashMap<>();
        sidebarBannerWebElements.forEach( sidebarBannerWebelement -> {
            String bannerText = sidebarBannerWebelement.find( By.tagName( "span" ) ).getText();
            BannerNotification bannerNotification = new BannerNotification( bannerText );
            WebElementFacade buttonTextInBanner = getButtonFromSidebarBanner( sidebarBannerWebelement );
            if ( buttonTextInBanner != null ) {
                bannerNotification.setButtonText( buttonTextInBanner.getText() );
            }
            sidebarBannersWithWebelements.put( bannerNotification, sidebarBannerWebelement );
        } );
        return sidebarBannersWithWebelements;
    }

    private WebElementFacade getButtonFromSidebarBanner( WebElementFacade sidebarBanner ) {
        if ( currentlyContainsWebElements( sidebarBanner, By.tagName( "button" ) ) ) {
            return sidebarBanner.then( By.tagName( "button" ) );
        }
        return null;
    }

    public void clickButtonOnNotificationBanner( BannerNotification expectedBannerNotification ) {
        Map<BannerNotification, WebElementFacade> sidebarBannerWebElements = getSidebarBannersWithWebElements();
        Map.Entry<BannerNotification, WebElementFacade> sidebarBannerWebElement = sidebarBannerWebElements.entrySet().stream()
                                                                                                          .filter( entry -> entry.getKey().equals( expectedBannerNotification ) )
                                                                                                          .findFirst()
                                                                                                          .orElseThrow( () -> new RuntimeException( "The following sidebar banner was not found: \n" + expectedBannerNotification ) );
        WebElementFacade buttonInBanner = getButtonFromSidebarBanner( sidebarBannerWebElement.getValue() );
        if ( buttonInBanner != null ) {
            LOGGER.info( STR."Observed button text: \{buttonInBanner.getText()}" );
            assertEquals( "Required button was not found in sidepanel banner.", expectedBannerNotification.getButtonText(), buttonInBanner.getText() );
            elementClicker( buttonInBanner );
        } else {
            fail( STR."Required button was not found in sidepanel banner: \{expectedBannerNotification}" );
        }
    }

    public void hoverOnCLPLabelOnTimeline( String labelName ) {
        for ( int i = 0; i < labelList.size(); i++ ) {
            if ( labelList.get( i ).getText().equals( labelName ) ) {
                WebElementFacade CLPLabel = labelList.get( i );
                hoverOnWebElement( CLPLabel );
                break;
            }
        }
    }

    public void moveMouseOnTheSidebarData( DetailedDataItem sidebarItem ) {
        WebElementFacade webElementFacade = sidebarNLPTextList.stream()
                                                              .filter( c -> TextUtils.textCompareWithKeywords( c.getText(), sidebarItem.getValue().toString() ) )
                                                              .findFirst()
                                                              .orElseThrow( () -> new RuntimeException( "Failed to hover on sidebar data element" ) );
        moveToElement( webElementFacade );
    }

    public Map<String, String> getAdherenceDatesInMap() {
        List<WebElementFacade> rows = adherenceDateTable.thenFindAll( By.cssSelector( ADHERENCE_DATE_TABLE_ROW_SELECTOR ) );
        return rows.stream()
                   .collect( Collectors.toMap(
                           row -> row.then( By.cssSelector( ADHERENCE_DATE_TABLE_KEY_SELECTOR ) ).getText(),
                           row -> row.then( By.cssSelector( ADHERENCE_DATE_TABLE_VALUE_SELECTOR ) ).getText()
                   ) );
    }

    public List<String> getDisabledButtonsList() {
        return sidebarEventDetailsHeader.thenFindAll( By.cssSelector( "[disabled]" ) )
                                        .stream()
                                        .map( WebElementFacade::getText )
                                        .collect( Collectors.toList() );
    }

    public void clickOnAIShowMoreOrLess( String buttonMoreOrLess ) {
        try {
            WebElementFacade aIGeneratedShowMoreLessButton = nlpModuleContainer.then( By.xpath( STR."//button[span[text()='\{buttonMoreOrLess}']]" ) );
            elementClicker( aIGeneratedShowMoreLessButton );
        } catch ( NoSuchElementException e ) {
            fail( STR."Could not found button with \{buttonMoreOrLess} text under the AI generated text" );
        }
    }
}
