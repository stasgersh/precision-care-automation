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
package com.ge.onchron.verif.pages.sections.toolbar;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.fail;

import com.ge.onchron.verif.pages.elements.Checkbox;
import com.ge.onchron.verif.pages.elements.CheckboxState;
import com.ge.onchron.verif.pages.sections.toolbar.timeRangeButtons.TimeRangeButtons;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.ListOfWebElementFacades;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.pages.utils.PageUtils.currentlyContainsWebElements;

public class TimelineToolbar extends BaseTimelineToolbar {

    private static final Logger LOGGER = LoggerFactory.getLogger( TimelineToolbar.class );

    private final String PATIENT_TOOLBAR_XPATH = "//*[contains(@class,'TimelineToolbar-module_container')]";
    private final String TIMELINE_TOOLBAR_FILTER_COUNTER_ICON_SELECTOR = "[data-test='timeline_toolbar_filter'] [class*='TimelineToolbar-module_badge']";
    private final String TIMELINE_TOOLBAR_FILTER_LABEL_BADGES_SELECTOR = "[class*='Label-module_filtered-label-container']";
    private final String TIMELINE_TOOLBAR_MORE_FILTERS_BUTTON_SELECTOR = "[class*='TimelineToolbar-module_more-button-container']";
    private final String TIMELINE_TOOLBAR_MORE_FILTERS_MODAL_SELECTOR = "[class*='TimelineToolbar-module_dropdown-container']";
    private final String RESET_FILTER_BUTTON_ON_FILTERS_MODAL_SELECTOR = "[class*='TimelineToolbar-module_reset-button-container']";
    private final String MORE_CONTAINER_SELECTOR = "//*[contains(@class,'TimelineToolbar-module_more-container')]";
    private final String RESET_TO_DEFAULT_VIEW_SELECTOR = "//div[span[contains(text(),'Reset to default view')]]";
    private final String TIMELINE_TOOLBAR_VIEW_MODE_ICON_SELECTOR = "[data-test='timeline_toolbar_view_mode']";
    private final String VIEW_MODE_MODAL_SELECTOR = "//div[contains(@class, 'modal modal') and .//*[contains(text(), 'View mode')]]";
    private final String VIEW_MODE_BADGE_SELECTOR = "[class*='TimelineToolbar-module_badge_']";

    @FindBy( xpath = PATIENT_TOOLBAR_XPATH )
    private WebElementFacade toolbar;

    @FindBy( xpath = "//*[contains(@class,'TimelineToolbar-module_filter-btn') and span[text()='Filter']]" )
    private WebElementFacade timelineFilterBtn;

    @FindBy( xpath = "//*[contains(@class,'TimelineToolbar-module_info-badge')]" )
    private WebElementFacade swimLaneFilterBadge;

    @FindBy( css = "[class*='TimelineToolbar-module_item-group-filtered-elems']" )
    private WebElementFacade appliedFiltersList;

    @FindBy( css = "[class*='TimelineToolbar-module_hide-navigator-group']" )
    private WebElementFacade hideMacroNavigator;

    @FindBy( xpath = "//button[span[contains(text(),'More')]]" )
    private WebElementFacade moreButton;

    @FindBy( xpath = VIEW_MODE_MODAL_SELECTOR )
    private WebElementFacade viewModeModal;

    private ZoomButtons zoomButtons;
    private TimeRangeButtons timeRangeButtons;

    public boolean isVisible() {
        return toolbar.isVisible();
    }

    public void openFilterModal() {
        if ( !getTimelineFilterModal().isCurrentlyVisible() ) {
            elementClicker( timelineFilterBtn );
            getTimelineFilterModal().waitUntilVisible();
        }
    }

    public boolean isFilterDataBadgeVisible() {
        return swimLaneFilterBadge.isCurrentlyVisible();
    }

    public ZoomButtons getZoomButtons() {
        return zoomButtons;
    }

    public double getZoomSliderPosition() {
        String positionString = getZoomButtons().getZoomSlider().getValue();
        return Double.parseDouble( positionString );
    }

    public void moveZoomSliderByMouseToRightSide() {
        WebElementFacade zoomSlider = getZoomButtons().getZoomSlider();
        moveZoomSliderFromCenterByOffset( zoomSlider.getRect().getX() / 2 );
    }

    public void moveZoomSliderByMouseToLeftSide() {
        WebElementFacade zoomSlider = getZoomButtons().getZoomSlider();
        moveZoomSliderFromCenterByOffset( -1 * zoomSlider.getRect().getX() / 2 );
    }

    private void moveZoomSliderFromCenterByOffset( int offset ) {
        WebElementFacade zoomSlider = getZoomButtons().getZoomSlider();
        Actions move = new Actions( getDriver() );
        Action action = move.clickAndHold( zoomSlider ).moveByOffset( offset, 0 ).build();
        action.perform();
    }

    @Override
    public void setFullTimeRange() {
        timeRangeButtons.setFullTimeRange();
    }

    @Override
    public void setLast30daysTimeRange() {
        timeRangeButtons.setLast30days();
    }

    @Override
    public void setLast90daysTimeRange() {
        timeRangeButtons.setLast90days();
    }

    public String getNumFromFilterCounterIcon() {
        Wait<WebDriver> wait = new FluentWait<>( getDriver() )
                .withTimeout( Duration.ofSeconds( 5 ) )
                .pollingEvery( Duration.ofMillis( 100 ) )
                .ignoring( NoSuchElementException.class );
        try {
            wait.until( ExpectedConditions.visibilityOfElementLocated( By.cssSelector( TIMELINE_TOOLBAR_FILTER_COUNTER_ICON_SELECTOR ) ) );
            return toolbar.findElement( By.cssSelector( TIMELINE_TOOLBAR_FILTER_COUNTER_ICON_SELECTOR ) ).getText();
        } catch ( Exception e ) {
            return null;
        }
    }

    public List<String> getFilterNamesFromFilterList() {
        return getFiltersFromFilterList().textContents().stream().map( String::trim ).collect( Collectors.toList() );
    }

    public ListOfWebElementFacades getFiltersFromFilterList() {
        ListOfWebElementFacades appliedFilters = new ListOfWebElementFacades( new ArrayList<>() );
        if ( appliedFiltersList.isCurrentlyVisible() && currentlyContainsWebElements( appliedFiltersList, By.cssSelector( TIMELINE_TOOLBAR_FILTER_LABEL_BADGES_SELECTOR ) ) ) {
            // initial filters
            ListOfWebElementFacades initialFilters = appliedFiltersList.thenFindAll( By.cssSelector( TIMELINE_TOOLBAR_FILTER_LABEL_BADGES_SELECTOR ) );
            initialFilters.removeIf( filter -> !filter.isCurrentlyVisible() );
            appliedFilters.addAll( initialFilters );
            // filters from more filters modal
            ListOfWebElementFacades moreFilters = getFiltersFromMoreFiltersModal();
            moreFilters.removeIf( filter -> !filter.isCurrentlyVisible() );
            appliedFilters.addAll( moreFilters );
            return appliedFilters;
        } else {
            return appliedFilters;
        }
    }

    private ListOfWebElementFacades getFiltersFromMoreFiltersModal() {
        openMoreFiltersModal();
        if ( moreFiltersModalOpened() ) {
            WebElementFacade moreFiltersModal = toolbar.then( By.cssSelector( TIMELINE_TOOLBAR_MORE_FILTERS_MODAL_SELECTOR ) );
            return moreFiltersModal.thenFindAll( By.cssSelector( TIMELINE_TOOLBAR_FILTER_LABEL_BADGES_SELECTOR ) );
        } else {
            return new ListOfWebElementFacades( new ArrayList<>() );
        }
    }

    public void removeFilterFromAppliedFiltersList( List<String> filterNamesToRemove ) {
        filterNamesToRemove.forEach( filterNameToRemove -> {
            ListOfWebElementFacades appliedFilters = getFiltersFromFilterList();
            LOGGER.info( "Filter to remove (on timeline toolbar): " + filterNameToRemove );
            WebElementFacade filterToRemove = appliedFilters.stream()
                                                            .filter( appliedFilter -> appliedFilter.getText().equals( filterNameToRemove ) )
                                                            .findFirst()
                                                            .orElseThrow( () -> new RuntimeException( "Following filter was not found in applied filters list: " + filterNameToRemove ) );
            ListOfWebElementFacades filterToRemoveElements = filterToRemove
                    .then( By.cssSelector( "[class*='Label-module_filtered-label']" ) )
                    .thenFindAll( By.xpath( "child::*" ) );
            // The last element (svg) is the 'X' button (Note: filter can contain 2 or 3 child elements, depends on the badge icon availablility, e.g. "Important" filter badge)
            elementClicker( filterToRemoveElements.get( filterToRemoveElements.size() - 1 ) );
        } );
    }

    public boolean isMoreFiltersButtonVisible() {
        return getMoreFiltersButton().isCurrentlyVisible();
    }

    public void openMoreFiltersModal() {
        if ( isMoreFiltersButtonVisible() && !moreFiltersModalOpened() ) {
            elementClicker( getMoreFiltersButton() );
        }
    }

    private WebElementFacade getMoreFiltersButton() {
        return appliedFiltersList.then( By.cssSelector( TIMELINE_TOOLBAR_MORE_FILTERS_BUTTON_SELECTOR ) );
    }

    private boolean moreFiltersModalOpened() {
        return currentlyContainsWebElements( toolbar, By.cssSelector( TIMELINE_TOOLBAR_MORE_FILTERS_MODAL_SELECTOR ) );
    }

    public void resetFiltersOnMoreFiltersModal() {
        openMoreFiltersModal();
        if ( moreFiltersModalOpened() ) {
            WebElementFacade moreFiltersModal = toolbar.then( By.cssSelector( TIMELINE_TOOLBAR_MORE_FILTERS_MODAL_SELECTOR ) );
            WebElementFacade resetFiltersButton = moreFiltersModal
                    .then( By.cssSelector( RESET_FILTER_BUTTON_ON_FILTERS_MODAL_SELECTOR ) )
                    .then( By.tagName( "button" ) );
            elementClicker( resetFiltersButton );
        } else {
            fail( "Cannot click on 'Reset filters' button on applied filters list modal" );
        }
    }

    public void setHideNavigatorCheckboxState( CheckboxState checkboxState ) {
        WebElementFacade hideNavigatorCheckboxWebelement = hideMacroNavigator.then( By.tagName( "label" ) );
        Checkbox checkbox = new Checkbox( hideNavigatorCheckboxWebelement );
        checkbox.setCheckboxState( checkboxState );
    }

    public boolean getHideNavigatorCheckboxState() {
        return new Checkbox( hideMacroNavigator.then( By.tagName( "label" ) ) ).isSelected();
    }

    public void clickResetToDefaultViewButtonInMoreMenu() {
        WebElementFacade moreContainer = find( By.xpath( MORE_CONTAINER_SELECTOR ) );
        elementClicker( moreContainer.then( By.xpath( RESET_TO_DEFAULT_VIEW_SELECTOR ) ) );
    }

    public boolean isMoreMenuOpen() {
        return find( By.xpath( MORE_CONTAINER_SELECTOR ) ).isDisplayed();
    }

    public void openMoreMenu() {
        elementClicker( moreButton );
    }

    public boolean isViewModeModalOpen() {
        return viewModeModal.isVisible();
    }

    public void clickViewModeButton() {
        elementClicker( find( By.cssSelector( TIMELINE_TOOLBAR_VIEW_MODE_ICON_SELECTOR ) ) );
    }

    public boolean isBadgeDisplayedOnViewModeButton() {
        try {
            setImplicitTimeout( 0, ChronoUnit.SECONDS );
            return find( By.cssSelector( TIMELINE_TOOLBAR_VIEW_MODE_ICON_SELECTOR ) ).then( By.cssSelector( VIEW_MODE_BADGE_SELECTOR ) ).isCurrentlyVisible();
        } catch ( org.openqa.selenium.NoSuchElementException e ) {
            return false;
        } finally {
            resetImplicitTimeout();
        }
    }

    public String getViewModeBadgeText() {
        return find( By.cssSelector( TIMELINE_TOOLBAR_VIEW_MODE_ICON_SELECTOR ) ).then( By.cssSelector( VIEW_MODE_BADGE_SELECTOR ) ).getText();
    }

}
