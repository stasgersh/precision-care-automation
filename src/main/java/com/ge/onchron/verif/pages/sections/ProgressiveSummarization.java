/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2024, 2024, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 */
package com.ge.onchron.verif.pages.sections;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.Table;
import com.ge.onchron.verif.pages.SimplePage;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.pages.utils.PageElementUtils.moveToElement;
import static com.ge.onchron.verif.pages.utils.PageUtils.currentlyContainsElements;
import static com.ge.onchron.verif.pages.utils.PageUtils.currentlyContainsWebElements;
import static com.ge.onchron.verif.pages.utils.ProgressiveSummaryUtils.getButtonWebelementFromTableCell;
import static com.ge.onchron.verif.pages.utils.ProgressiveSummaryUtils.getFullReportButton;
import static com.ge.onchron.verif.pages.utils.ProgressiveSummaryUtils.getWhatHasChangedTitleText;
import static com.ge.onchron.verif.pages.utils.ProgressiveSummaryUtils.hoverButtonInTableCell;
import static com.ge.onchron.verif.pages.utils.TableUtils.createTable;

public class ProgressiveSummarization extends SimplePage {

    private final String PS_OVERVIEW_SELECTOR = "[class*='ProgressiveSummarization-module_ps-container']";
    private final String PS_OVERVIEW_LOADING_SKELETON_SELECTOR = "[class*='SkeletonLoader-module_container']";
    private final String PS_OVERVIEW_HEADER_SELECTOR = "[class*='ProgressiveSummarization-module_ps-header']";
    private final String PS_OVERVIEW_TOGGLE_BUTTON_SELECTOR = "[class*='ProgressiveSummarization-module_caret-btn']";
    private final String PS_OVERVIEW_FIRST_COLUMN_SELECTOR = "[class*='ProgressiveSummarization-module_first']";
    private final String PS_OVERVIEW_SECOND_COLUMN_SELECTOR = "[class*='ProgressiveSummarization-module_second']";
    private final String EMPTY_STATE_CONTAINER_SELECTOR = "[class*='EmptyState-module_container']";
    private final String PS_SECTION_TITLE_SELECTOR = "[class*='HistorySummarization-module_history-title']";
    private final String AI_ICON_SELECTOR = "[class^='NlpInfo-module_nlp-info_']";
    private final String FULL_REPORT_BUTTON_SELECTOR = "[class*='SummaryItem-module_cta']";
    private final String SOURCE_LINK_SELECTOR = "[class*='HistorySummarization-module_source-link']";
    private final String PS_WHAT_HAS_CHANGED_SHOW_ALL_SELECTOR = "//div[contains(@class, 'ProgressiveSummarization-module_second-col_')]//span[contains(text(), 'events')]";
    private final String AI_SECTION_SHOW_MORE_SELECTOR = "button[class*='button']";
    private final String GENERALIZED_ELEMENT_TEXT_SELECTOR = "//*[text()='REPLACE']";
    private final String BRIEF_HISTORY_CONTENT_SELECTOR = "[class*='HistorySummarization-module_ss-description']";
    private final String AI_GENERATED_DATA_EMPTY_STATE_SELECTOR = "[class*='HistorySummarization-module_empty-content']";
    private final String WHAT_HAS_CHANGED_EMPTY_STATE_SELECTOR = "[class*='EventsSummarization-module_empty-events']";

    @FindBy( css = PS_OVERVIEW_SELECTOR )
    private WebElementFacade psOverviewContainer;

    @FindBy( css = PS_OVERVIEW_HEADER_SELECTOR )
    private WebElementFacade psOverviewHeader;

    @FindBy( css = PS_OVERVIEW_FIRST_COLUMN_SELECTOR )
    private WebElementFacade psOverviewFirstColumn;

    @FindBy( css = PS_OVERVIEW_SECOND_COLUMN_SELECTOR )
    private WebElementFacade psOverviewSecondColumn;

    @FindBy( xpath = PS_WHAT_HAS_CHANGED_SHOW_ALL_SELECTOR )
    private WebElementFacade psWhatHasChangedShowAllEventsButton;

    public void waitUntilLoadingSkeletonDisappears() {
        waitUntilLoadingSkeletonDisappears( psOverviewContainer, By.cssSelector( PS_OVERVIEW_LOADING_SKELETON_SELECTOR ) );
    }

    public boolean isVisible() {
        return psOverviewContainer.isCurrentlyVisible();
    }

    public boolean isCollapsed() {
        return psOverviewContainer.getAttribute( "class" ).contains( "collapsed" );
    }

    public void collapsePsOverview() {
        WebElementFacade expandCollapseButton = psOverviewHeader.then( By.cssSelector( PS_OVERVIEW_TOGGLE_BUTTON_SELECTOR ) );
        if ( expandCollapseButton.getText().equals( "Collapse" ) ) {
            expandCollapseButton.click();
        } else {
            fail( String.format( "Cannot collapse PS Overview, current text on toggle button: %s", expandCollapseButton.getText() ) );
        }
    }

    public boolean isBriefHistoryVisible() {
        Rectangle psContainerRect = psOverviewContainer.getRect();
        int psContainerBeginsAt = psContainerRect.getY();
        int psContainerEndsAt = psContainerBeginsAt + psContainerRect.getHeight();

        Rectangle briefHistoryRect = getBriefHistoryWebelement().getRect();
        int briefHistoryBeginsAt = briefHistoryRect.getY();
        int briefHistoryEndsAt = briefHistoryBeginsAt + briefHistoryRect.getHeight();

        return briefHistoryBeginsAt > psContainerBeginsAt && briefHistoryEndsAt < psContainerEndsAt;
    }

    public boolean isWhatHasChangedVisible() {
        Rectangle psContainerRect = psOverviewContainer.getRect();
        int psContainerBeginsAt = psContainerRect.getY();
        int psContainerEndsAt = psContainerBeginsAt + psContainerRect.getHeight();

        Rectangle whatHasChangedRect = getWhatHasChangedWebelement().getRect();
        int whatHasChangedBeginsAt = whatHasChangedRect.getY();
        int whatHasChangedEndsAt = whatHasChangedBeginsAt + whatHasChangedRect.getHeight();

        return whatHasChangedBeginsAt > psContainerBeginsAt && whatHasChangedEndsAt < psContainerEndsAt;
    }

    public boolean isSummaryOfRecentReportsVisible() {
        return getSummaryOfRecentReportsWebelement().isCurrentlyVisible();
    }

    public boolean isAiTurnedOffInfoVisibleInBriefHistory() {
        return currentlyContainsElements( getBriefHistoryWebelement(), By.cssSelector( EMPTY_STATE_CONTAINER_SELECTOR ) );
    }

    public boolean isAiTurnedOffInfoVisibleInWhatHasChangedSection() {
        return currentlyContainsElements( getWhatHasChangedWebelement(), By.cssSelector( EMPTY_STATE_CONTAINER_SELECTOR ) );
    }

    public boolean isAiTurnedOffInfoVisibleInSummaryOfRecentReports() {
        return currentlyContainsElements( getSummaryOfRecentReportsWebelement(), By.cssSelector( EMPTY_STATE_CONTAINER_SELECTOR ) );
    }

    public String getBriefHistoryTitle() {
        return getBriefHistoryWebelement().then( By.cssSelector( PS_SECTION_TITLE_SELECTOR ) ).getText();
    }

    public String getWhatHasChangedTitle() {
        WebElementFacade titleContainer = getWhatHasChangedWebelement().then( By.cssSelector( "[class*='title-wrapper']" ) );
        return getWhatHasChangedTitleText( titleContainer );
    }

    public String getSummaryOfRecentReportsTitle() {
        return getSummaryOfRecentReportsWebelement().then( By.cssSelector( PS_SECTION_TITLE_SELECTOR ) ).getText();
    }

    public void clickButtonOnBriefHistory( String buttonText ) {
        clickButtonOnWebelement( getBriefHistoryWebelement(), buttonText );
    }

    public void clickButtonOnSummaryOfRecentReports( String buttonText ) {
        clickButtonOnWebelement( getSummaryOfRecentReportsWebelement(), buttonText );
    }

    public void clickButtonOnWebelement( WebElementFacade webelement, String buttonText ) {
        List<WebElementFacade> buttons = webelement.thenFindAll( By.tagName( "button" ), By.cssSelector( "[class*='toggeling']" ) );
        if ( currentlyContainsWebElements( webelement, By.cssSelector( AI_SECTION_SHOW_MORE_SELECTOR ) ) ) {
            buttons.add( webelement.then( By.cssSelector( AI_SECTION_SHOW_MORE_SELECTOR ) ) );
        }
        WebElementFacade button = buttons.stream()
                                         .filter( btn -> btn.getText().equals( buttonText ) )
                                         .findFirst()
                                         .orElseThrow( () -> new RuntimeException( String.format( "Cannot find button on Brief History: %s", buttonText ) ) );
        elementClicker( button );
    }

    public String getAiBadgeTextFromBriefHistory() {
        return getAiBadge( getBriefHistoryWebelement() ).getText();
    }

    public String getAiBadgeTextFromSummaryOfRecentReports() {
        return getAiBadge( getSummaryOfRecentReportsWebelement() ).getText();
    }

    private WebElementFacade getAiBadge( WebElementFacade webelement ) {
        return webelement.then( By.cssSelector( AI_ICON_SELECTOR ) )
                         .then( By.cssSelector( "[class*='info-title']" ) );
    }

    public void hoverInfoSymbolOnBriefHistory() {
        hoverInfoSymbol( getBriefHistoryWebelement() );
    }

    public void hoverInfoSymbolOnSummaryOfRecentReports() {
        hoverInfoSymbol( getSummaryOfRecentReportsWebelement() );
    }

    private void hoverInfoSymbol( WebElementFacade webElement ) {
        WebElementFacade infoSymbol = webElement.then( By.cssSelector( AI_ICON_SELECTOR ) )
                                                .then( By.id( "Info_-_16" ) );
        moveToElement( infoSymbol );
    }

    public Table getWhatHasChangedTable() {
        return createTable( getWhatHasChangedWebelement().find( By.tagName( "table" ) ) );
    }

    public boolean isButtonEnabledInTableCell( String buttonName, int rowNb, String columnName ) {
        return !getButtonWebelementFromTableCell( getWhatHasChangedTableWebElement(), buttonName, rowNb, columnName ).getAttribute( "class" ).contains( "disabled" );
    }

    public void clickFullReportButton( int rowNb, String columnName ) {
        getFullReportButton( getWhatHasChangedTableWebElement(), rowNb, columnName ).click();
    }

    public void hoverButtonInTable( String buttonName, int rowNb, String columnName ) {
        hoverButtonInTableCell( getWhatHasChangedTableWebElement(), buttonName, rowNb, columnName );
    }

    private WebElement getWhatHasChangedTableWebElement() {
        return getWhatHasChangedWebelement().find( By.tagName( "table" ) );
    }

    public boolean isWhatHasChangedTableAvailable() {
        return currentlyContainsElements( getWhatHasChangedWebelement(), By.tagName( "table" ) );
    }

    private WebElementFacade getBriefHistoryWebelement() {
        return psOverviewFirstColumn.thenFindAll( By.xpath( "child::*" ) ).getFirst();
    }

    private WebElementFacade getWhatHasChangedWebelement() {
        return psOverviewSecondColumn.thenFindAll( By.xpath( "child::*" ) ).getFirst();
    }

    private WebElementFacade getSummaryOfRecentReportsWebelement() {
        return psOverviewSecondColumn.thenFindAll( By.xpath( "child::*" ) ).getLast();
    }

    public String getBriefHistoryReportText() {
        return getBriefHistoryReport().getText();
    }

    public WebElementFacade getBriefHistoryReport() {
        return getBriefHistoryWebelement().then( By.cssSelector( BRIEF_HISTORY_CONTENT_SELECTOR ) );
    }


    public String getSummaryOfRecentReportsText() {
        return getSummaryOfRecentReports().getText();
    }

    public WebElementFacade getSummaryOfRecentReports() {
        return getSummaryOfRecentReportsWebelement().then( By.cssSelector( BRIEF_HISTORY_CONTENT_SELECTOR ) );
    }

    public void hoverSourceInBriefHistory( int sourceIndex ) {
        List<WebElementFacade> sources = getBriefHistoryWebelement().thenFindAll( SOURCE_LINK_SELECTOR )
                                                                    .stream()
                                                                    .filter( WebElement::isDisplayed )
                                                                    .toList();
        ;
        moveToElement( sources.get( sourceIndex ) );
    }

    public void hoverSourceInSummaryOfRecentReports( int sourceIndex ) {
        List<WebElementFacade> sources = getSummaryOfRecentReportsWebelement().thenFindAll( SOURCE_LINK_SELECTOR )
                                                                              .stream()
                                                                              .filter( WebElement::isDisplayed )
                                                                              .toList();
        ;
        moveToElement( sources.get( sourceIndex ) );
    }

    public void clickSourceInBriefHistory( int sourceIndex ) {
        List<WebElementFacade> sources = getBriefHistoryWebelement().thenFindAll( SOURCE_LINK_SELECTOR )
                                                                    .stream()
                                                                    .filter( WebElement::isDisplayed )
                                                                    .toList();
        ;
        elementClicker( sources.get( sourceIndex ) );
    }

    public int getNbOfVisibleSourcesInBriefHistory() {
        return (int) getBriefHistoryWebelement().thenFindAll( SOURCE_LINK_SELECTOR ).stream().filter( WebElement::isDisplayed ).count();
    }

    public void clickSourceInSummaryOfRecentReports( int sourceIndex ) {
        List<WebElementFacade> sources = getSummaryOfRecentReportsWebelement().thenFindAll( SOURCE_LINK_SELECTOR )
                                                                              .stream()
                                                                              .filter( WebElement::isDisplayed )
                                                                              .toList();
        elementClicker( sources.get( sourceIndex ) );
    }

    public int getNbOfVisibleSourcesInSummaryOfRecentReports() {
        return (int) getSummaryOfRecentReportsWebelement().thenFindAll( SOURCE_LINK_SELECTOR ).stream().filter( WebElement::isDisplayed ).count();
    }

    public String getWhatHasChangedMoreOrLessButtonText() {
        return psWhatHasChangedShowAllEventsButton.getText();
    }

    public String getBriefHistoryMoreOrLessButtonText() {
        if ( currentlyContainsWebElements( getBriefHistoryWebelement(), By.cssSelector( AI_SECTION_SHOW_MORE_SELECTOR ) ) ) {
            return getBriefHistoryWebelement().then( By.cssSelector( AI_SECTION_SHOW_MORE_SELECTOR ) ).getText();
        }
        return null;
    }

    public String getSummaryOfRecentReportsMoreOrLessButtonText() {
        if ( currentlyContainsWebElements( getSummaryOfRecentReportsWebelement(), By.cssSelector( AI_SECTION_SHOW_MORE_SELECTOR ) ) ) {
            return getSummaryOfRecentReportsWebelement().then( By.cssSelector( AI_SECTION_SHOW_MORE_SELECTOR ) ).getText();
        }
        return null;
    }

    public void clickButtonOnWhatHasChanged( String buttonText ) {
        try {
            setImplicitTimeout( 0, ChronoUnit.SECONDS );
            WebElementFacade buttonElement = psOverviewSecondColumn.then( By.xpath( GENERALIZED_ELEMENT_TEXT_SELECTOR.replace( "REPLACE", buttonText ) ) );
            elementClicker( buttonElement );
        } catch ( NoSuchElementException e ) {
            fail( String.format( "Could not found button with the following text \"%s\"", buttonText ) );
        } finally {
            resetImplicitTimeout();
        }

    }

    public String getBriefHistoryEmptyStateMessage() {
        return getBriefHistoryWebelement()
                .then( By.cssSelector( AI_GENERATED_DATA_EMPTY_STATE_SELECTOR ) )
                .getText();
    }

    public String getWhatHasChangedEmptyStateMessage() {
        return getWhatHasChangedWebelement()
                .then( By.cssSelector( WHAT_HAS_CHANGED_EMPTY_STATE_SELECTOR ) )
                .getText();
    }

    public String getSummaryOfRecentReportsEmptyStateMessage() {
        return getSummaryOfRecentReportsWebelement()
                .then( By.cssSelector( AI_GENERATED_DATA_EMPTY_STATE_SELECTOR ) )
                .getText();
    }

    public boolean isLoadingSkeletonVisible() {
        Optional<WebElementFacade> loadingSkeleton = findFirst( By.cssSelector( PS_OVERVIEW_LOADING_SKELETON_SELECTOR ) );
        return loadingSkeleton.isPresent() && loadingSkeleton.get().isVisible();
    }

    public void assertAllAiFindingHasBulletPointInBriefHistory() {
        List<WebElementFacade> aiFindings = getBriefHistoryReport().thenFindAll( By.tagName( "li" ) );
        assertAllAiFindingHasBulletPoint( aiFindings );
    }

    public void assertAllAiFindingHasBulletPointInSummaryOfRecentReports() {
        List<WebElementFacade> aiFindings = getSummaryOfRecentReports().thenFindAll( By.tagName( "li" ) );
        assertAllAiFindingHasBulletPoint( aiFindings );
    }

    private void assertAllAiFindingHasBulletPoint( List<WebElementFacade> aiFindings ) {
        for ( WebElementFacade aiFinding : aiFindings ) {
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            String listStyleType = (String) js.executeScript(
                    "return window.getComputedStyle(arguments[0]).getPropertyValue('list-style-type');",
                    aiFinding
            );
            // If  element has list-style-type: disc, it has a visible marker, and that marker is a bullet (•)
            assertEquals( String.format( "Bullet point is not available in ai finding element: %s", aiFinding.getText() ),
                    "disc", listStyleType );
        }
    }

    public void assertAllAiFindingHasSourceLinkInBriefHistory() {
        List<WebElementFacade> aiFindings = getBriefHistoryReport().thenFindAll( By.tagName( "li" ) );
        assertAllAiFindingHasSourceLink( aiFindings );
    }

    public void assertAllAiFindingHasSourceLinkInSummaryOfRecentReports() {
        List<WebElementFacade> aiFindings = getSummaryOfRecentReports().thenFindAll( By.tagName( "li" ) );
        assertAllAiFindingHasSourceLink( aiFindings );
    }

    private void assertAllAiFindingHasSourceLink( List<WebElementFacade> aiFindings ) {
        for ( WebElementFacade aiFinding : aiFindings ) {
            List<WebElementFacade> aiFindingElements = aiFinding.thenFindAll( By.xpath( "child::*" ) );
            assertTrue( String.format( "The [source] is not available in ai finding element: %s", aiFinding.getText() ),
                    aiFindingElements.getLast().containsElements( By.cssSelector( SOURCE_LINK_SELECTOR ) ) );
        }
    }

}
