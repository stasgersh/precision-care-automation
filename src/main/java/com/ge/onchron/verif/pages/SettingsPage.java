/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2022, 2022, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ge.onchron.verif.model.UserSetting;
import com.ge.onchron.verif.pages.elements.UserSettingContainer;
import com.ge.onchron.verif.pages.sections.UserSideNavMenu;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.ListOfWebElementFacades;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.pages.utils.DragAndDropUtils.dragAndDropToElement;
import static com.ge.onchron.verif.pages.utils.PageUtils.currentlyContainsElements;
import static com.ge.onchron.verif.pages.utils.PageUtils.waitUntilElementDisappears;
import static junit.framework.TestCase.fail;

public class SettingsPage extends SimplePage {

    private static final Logger LOGGER = LoggerFactory.getLogger( SettingsPage.class );

    private static final String SETTINGS_TITLE_CSS_SELECTOR = "[class*='Settings-module_title']:not([class*='icon'])";  // to find "Settings_title__" instead of "Settings_title-icon"
    private static final String SETTING_SUB_TITLE_CLASS = "Settings-module_sub-title-line";
    private static final String SETTING_NAME_CONTAINER_XPATH = ".//*[contains(@class,'Settings-module_sub-title')]";
    private static final String SETTINGS_BUTTONS_CONTAINER_XPATH = "//*[contains(@class,'Settings-module_btn')]";
    private static final String BUTTON_WITH_LABEL_XPATH = ".//button[text()='%s']";
    private static final String SETTINGS_BUTTONS_CONTAINER_CLASS = "Settings-module_btn-wrap";
    private static final String DRAG_AND_DROP_LIST_ITEM_CSS_SELECTOR = "[class^='Settings-module_drag-drop-list__item']";
    private static final String LOADING_CIRCLE_INDICATOR_CSS_SELECTOR = "[class*='indicator__circular']";
    private static final String SWIMLANE_ORDER_DRAG_DROP_LIST_ITEM = "div[class*='Settings-module_drag-drop-list__item_']";

    private static final String CANCEL_BUTTON_TEXT = "Cancel";

    private static final int MAX_WAITING_TIME_TO_SAVE_SETTINGS_IN_MILLIS = 3000;

    @FindBy( xpath = "//*[contains(@class,'Settings-module_container')]" )
    private WebElementFacade settingsPage;

    @FindBy( xpath = "//*[contains(@class,'GoBackBar-module_toolbar')]" )
    private WebElementFacade goBackToolbar;

    @FindBy( xpath = "//*[contains(@class,'Settings-module_content')]" )
    private WebElementFacade settingsContent;

    @FindBy( xpath = "//*[contains(@class,'Settings-module_setting-box')]" )
    private List<WebElementFacade> settingsBoxes;

    UserSideNavMenu userSideNavMenu;

    public boolean isVisible() {
        return settingsPage.isVisible();
    }

    public void waitUntilLoadingIndicatorDisappears() {
        waitUntilLoadingSkeletonDisappears( settingsContent, By.cssSelector( LOADING_CIRCLE_INDICATOR_CSS_SELECTOR ) );
    }

    public void clickOnToolbarButton( String btnLabel ) {
        String buttonXpath = String.format( BUTTON_WITH_LABEL_XPATH, btnLabel );
        if ( userSideNavMenu.isOpen() ) {  // if the toolbar is overlapped by the open left side menu
            new Actions( getDriver() ).moveToElement( goBackToolbar ).build().perform();
        }
        elementClicker( goBackToolbar.then( By.xpath( buttonXpath ) ) );
    }

    public void clickOnSettingsButton( String btnLabel ) {
        String buttonXpath = String.format( BUTTON_WITH_LABEL_XPATH, btnLabel );
        elementClicker( settingsContent.then( By.xpath( buttonXpath ) ) );
        if ( btnLabel.equals( "Save" ) || btnLabel.equals( "Cancel" ) ) {
            waitUntilSettingsButtonsDisappear();
        }
    }

    public void cancelEditSettings() {
        String buttonXpath = String.format( BUTTON_WITH_LABEL_XPATH, CANCEL_BUTTON_TEXT );
        if ( settingsPage.isCurrentlyVisible() && currentlyContainsElements( settingsContent, By.xpath( buttonXpath ) ) ) {
            clickOnSettingsButton( CANCEL_BUTTON_TEXT );
        }
    }

    private void waitUntilSettingsButtonsDisappear() {
        waitUntilElementDisappears( settingsContent.getWrappedElement(),
                By.xpath( SETTINGS_BUTTONS_CONTAINER_XPATH ),
                MAX_WAITING_TIME_TO_SAVE_SETTINGS_IN_MILLIS,
                "Cancel/Save buttons on Settings page did not disappear." );
    }

    public List<UserSetting> getUserSettings() {
        Map<WebElementFacade, List<UserSettingContainer>> settingsElements = getSettingsElements();
        List<UserSetting> userSettings = new ArrayList<>();
        settingsElements.forEach( ( title, settings ) ->
                settings.forEach( setting -> {
                    UserSetting userSetting = new UserSetting();
                    userSetting.setTitle( title.getText() );
                    userSetting.setSettingName( setting.getSettingName().find( By.xpath( SETTING_NAME_CONTAINER_XPATH ) ).getText() );  // Setting name without 'Edit' button
                    userSetting.setSettingValue( setting.getSettingValue().getText() );
                    userSettings.add( userSetting );
                } ) );
        return userSettings;
    }

    private Map<WebElementFacade, List<UserSettingContainer>> getSettingsElements() {
        Map<WebElementFacade, List<UserSettingContainer>> settingsElements = new HashMap<>();
        settingsBoxes.forEach( settingsBox -> {
            List<WebElementFacade> settingOptionElements = settingsBox.thenFindAll( By.xpath( "child::*" ) );
            ListIterator<WebElementFacade> iterator = settingOptionElements.listIterator();
            while ( iterator.hasNext() ) {
                Map<WebElementFacade, List<UserSettingContainer>> option = prepareSettingElement( iterator );
                settingsElements.putAll( option );
            }
        } );
        return settingsElements;
    }

    private Map<WebElementFacade, List<UserSettingContainer>> prepareSettingElement( ListIterator<WebElementFacade> iterator ) {
        WebElementFacade titleElement = null;
        WebElementFacade actualElement = iterator.next();

        if ( !currentlyContainsElements( actualElement, By.cssSelector( SETTINGS_TITLE_CSS_SELECTOR ) ) ) {
            fail( "First setting element shall contain a settings title element" );
        }

        titleElement = actualElement.then( By.cssSelector( SETTINGS_TITLE_CSS_SELECTOR ) );
        List<UserSettingContainer> userSettingElements = new ArrayList<>();

        while ( iterator.hasNext() ) {
            actualElement = iterator.next();
            UserSettingContainer userSettingElement = null;
            if ( !currentlyContainsElements( actualElement, By.cssSelector( SETTINGS_TITLE_CSS_SELECTOR ) ) ) {
                if ( actualElement.getTagName().equals( "hr" ) ) {
                    // ignore separator
                    LOGGER.info( "Settings separator found, skipping it." );
                } else if ( !actualElement.getAttribute( "class" ).contains( SETTING_SUB_TITLE_CLASS ) ) {
                    // Expected format: 'property name' and 'property value' pairs
                    fail( "Settings sub-title required after a settings title or after a settings sub-title and value pair" );
                } else {
                    userSettingElement = new UserSettingContainer( actualElement, iterator.next() );
                    userSettingElements.add( userSettingElement );
                }
            }
        }
        Map<WebElementFacade, List<UserSettingContainer>> returnItem = new HashMap<>();
        returnItem.put( titleElement, userSettingElements );
        return returnItem;
    }

    public void clickOnEdit( UserSetting requiredSettings ) {
        UserSettingContainer userSettingContainer = findUserSettingContainer( requiredSettings );
        userSettingContainer.getSettingName().find( By.tagName( "button" ) ).click();
    }

    public void selectRadioButton( String radioBtnLabel, UserSetting requiredSettings ) {
        UserSettingContainer userSettingContainer = findUserSettingContainer( requiredSettings );
        ListOfWebElementFacades radioButtons = userSettingContainer.getSettingValue().thenFindAll( By.className( "radio" ) );
        radioButtons.stream()
                    .filter( radioButton -> radioButton.getText().equals( radioBtnLabel ) )
                    .findFirst()
                    .ifPresentOrElse(
                            WebElementFacade::click,
                            () -> fail( "Unable to select radio button: " + radioBtnLabel ) );
    }

    private UserSettingContainer findUserSettingContainer( UserSetting requiredSettings ) {
        Map<WebElementFacade, List<UserSettingContainer>> observedSettings = getSettingsElements();
        List<UserSettingContainer> settingElements = observedSettings.entrySet().stream()
                                                                     .filter( prop -> prop.getKey().getText().equals( requiredSettings.getTitle() ) )
                                                                     .findFirst()
                                                                     .map( Map.Entry::getValue )
                                                                     .orElseThrow( () -> new RuntimeException( "User setting was not found: " + requiredSettings.getTitle() ) );

        return settingElements.stream()
                              .filter( settingElement -> settingElement.getSettingName().find( By.xpath( SETTING_NAME_CONTAINER_XPATH ) ).getText().equals( requiredSettings.getSettingName() ) )
                              .findFirst()
                              .orElseThrow( () -> new RuntimeException( "User sub-setting was not found: " + requiredSettings.getSettingName() ) );
    }

    public List<String> getListValueOfUserSetting( UserSetting userSetting, boolean isSwimlaneOrderEdited ) {
        UserSettingContainer userSettingContainer = findUserSettingContainer( userSetting );
        WebElementFacade settingValue = userSettingContainer.getSettingValue();
        String settingValuesTexts;
        if ( settingValue.containsElements( By.cssSelector( "[class^='" + SETTINGS_BUTTONS_CONTAINER_CLASS + "']" ) ) ) {
            ListOfWebElementFacades childElements = settingValue.thenFindAll( By.xpath( "child::*" ) );
            childElements.removeIf( childElement -> childElement.getAttribute( "class" ).contains( SETTINGS_BUTTONS_CONTAINER_CLASS ) );
            settingValuesTexts = childElements.stream()
                                              .map( WebElementFacade::getText )
                                              .collect( Collectors.joining() );
        }
        if ( isSwimlaneOrderEdited ) {
            settingValuesTexts = userSettingContainer.getSettingValue().find( By.cssSelector( ".flex" ) ).getText();
        } else {
            settingValuesTexts = userSettingContainer.getSettingValue().getText();
        }
        return Arrays.stream( settingValuesTexts.split( "\n" ) )
                     .collect( Collectors.toList() );
    }

    public void setDragAndDropUserSetting( UserSetting userSetting, List<String> expectedList ) {
        UserSettingContainer userSettingContainer = findUserSettingContainer( userSetting );
        List<WebElementFacade> observedList = userSettingContainer.getSettingValue().thenFindAll( By.cssSelector( DRAG_AND_DROP_LIST_ITEM_CSS_SELECTOR ) );
        for ( int i = 0; i < expectedList.size(); i++ ) {
            String requiredName = expectedList.get( i );
            if ( !observedList.get( i ).getText().equals( requiredName ) ) {
                WebElementFacade foundItem = observedList.stream()
                                                         .filter( observedItem -> observedItem.getText().equals( requiredName ) )
                                                         .findFirst()
                                                         .orElseThrow( () -> new RuntimeException( "Following user setting item was not found: " + requiredName ) );

                dragAndDropToElement( foundItem, observedList.get( i ) );
            }
            // Re-read the list items
            observedList = userSettingContainer.getSettingValue().thenFindAll( By.cssSelector( DRAG_AND_DROP_LIST_ITEM_CSS_SELECTOR ) );
        }
    }

    public boolean isSwimlaneOrderCurrentlyEdited() {
        // Class present only if the swimlane order is currently edited
        return !findAll( By.cssSelector( SWIMLANE_ORDER_DRAG_DROP_LIST_ITEM ) ).isEmpty();
    }
}
