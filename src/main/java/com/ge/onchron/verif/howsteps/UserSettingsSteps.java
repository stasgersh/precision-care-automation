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
package com.ge.onchron.verif.howsteps;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.ge.onchron.verif.model.RestRequest;
import com.ge.onchron.verif.model.StringList;
import com.ge.onchron.verif.model.UserCredentials;
import com.ge.onchron.verif.model.UserSetting;
import com.ge.onchron.verif.model.enums.OnOff;
import com.ge.onchron.verif.model.enums.TimelineDirection;
import com.ge.onchron.verif.pages.SettingsPage;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;

import static com.ge.onchron.verif.TestSystemParameters.API_BASE_URL_PROPERTY;
import static com.ge.onchron.verif.TestSystemParameters.CORE_API_BASE_PATH_PROPERTY;
import static com.ge.onchron.verif.TestSystemParameters.DEFAULT_SUMMARY_LAYOUT;
import static com.ge.onchron.verif.TestSystemParameters.TEST_DATA_CLASSPATH;
import static com.ge.onchron.verif.TestSystemParameters.USER_SETTINGS_CHANGED;
import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;
import static com.ge.onchron.verif.model.enums.OnOff.ON;
import static com.ge.onchron.verif.utils.FileUtils.readFromFile;
import static net.serenitybdd.core.Serenity.hasASessionVariableCalled;
import static net.serenitybdd.core.Serenity.sessionVariableCalled;
import static net.serenitybdd.core.Serenity.setSessionVariable;

public class UserSettingsSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger( UserSettingsSteps.class );
    public static final String USER_SETTINGS_ENDPOINT_PATH = "/settings";
    public static final String SWIMLANES_ENDPOINT_PATH = "/patients/swimlanes";
    public static final String SUMMARY_LAYOUT_ENDPOINT_PATH = "/patients/summary-layout";

    private SettingsPage settingsPage;

    @Steps
    private RestApiSteps restApiSteps;

    /**
     * The method is used in BeforeStory section to change back the modified user settings to the default one
     */
    public void setDefaultUserSettings( UserCredentials user ) {
        String actualUserSettings = getUserSettings( user );

        // Set back modified user parameters to the default value
        JsonObject userSettings = new Gson().fromJson( actualUserSettings, JsonObject.class );
        userSettings.addProperty( "showNlpResults", false );
        userSettings.addProperty( "timelineDirection", 0 );
        userSettings.remove( "swimlaneOrder" );
        setUserSettings( user, userSettings );
    }

    private String getUserSettings( UserCredentials user ) {
        RequestSpecBuilder rsb = new RequestSpecBuilder();
        rsb.setBaseUri( getSystemParameter( API_BASE_URL_PROPERTY ) );
        rsb.setBasePath( getSystemParameter( CORE_API_BASE_PATH_PROPERTY ) + USER_SETTINGS_ENDPOINT_PATH );
        Response response = restApiSteps.sendAuthenticatedRequest( user, new RestRequest( Method.GET, rsb.build() ) );
        restApiSteps.checkResponseStatusCode( HttpStatus.SC_OK );
        return response.asString();
    }

    private String getDefaultSummaryLayout( UserCredentials user ) {
        if ( hasASessionVariableCalled( DEFAULT_SUMMARY_LAYOUT ) ) {
            return sessionVariableCalled( DEFAULT_SUMMARY_LAYOUT );
        }
        RequestSpecBuilder rsb = new RequestSpecBuilder();
        rsb.setBaseUri( getSystemParameter( API_BASE_URL_PROPERTY ) );
        rsb.setBasePath( getSystemParameter( CORE_API_BASE_PATH_PROPERTY ) + SUMMARY_LAYOUT_ENDPOINT_PATH );
        Response response = restApiSteps.sendAuthenticatedRequest( user, new RestRequest( Method.GET, rsb.build() ) );
        restApiSteps.checkResponseStatusCode( HttpStatus.SC_OK );
        setSessionVariable( DEFAULT_SUMMARY_LAYOUT ).to( response.asString() );
        return response.asString();
    }

    private void setUserSettings( UserCredentials user, JsonObject userSettingsJson ) {
        RequestSpecBuilder rsb = new RequestSpecBuilder();
        rsb.setBaseUri( getSystemParameter( API_BASE_URL_PROPERTY ) );
        rsb.setBasePath( getSystemParameter( CORE_API_BASE_PATH_PROPERTY ) + USER_SETTINGS_ENDPOINT_PATH );
        rsb.setContentType( ContentType.JSON );
        rsb.setBody( userSettingsJson.toString() );
        restApiSteps.sendAuthenticatedRequest( user, new RestRequest( Method.PUT, rsb.build() ) );
        restApiSteps.checkResponseStatusCode( HttpStatus.SC_NO_CONTENT );
        setSessionVariable( USER_SETTINGS_CHANGED ).to( true );
    }

    private String getSwimlanes( UserCredentials user ) {
        RequestSpecBuilder rsb = new RequestSpecBuilder();
        rsb.setBaseUri( getSystemParameter( API_BASE_URL_PROPERTY ) );
        rsb.setBasePath( getSystemParameter( CORE_API_BASE_PATH_PROPERTY ) + SWIMLANES_ENDPOINT_PATH );
        Response response = restApiSteps.sendAuthenticatedRequest( user, new RestRequest( Method.GET, rsb.build() ) );
        restApiSteps.checkResponseStatusCode( HttpStatus.SC_OK );
        return response.asString();
    }

    /**
     * Switch on/off CLP visualization via API call (default user setting)
     */
    public void switchClpVisualization( OnOff clpState, UserCredentials user ) {
        String actualUserSettings = getUserSettings( user );
        JsonObject userSettings = new Gson().fromJson( actualUserSettings, JsonObject.class );
        boolean state = clpState.equals( ON );
        LOGGER.info( STR."Set user settings property showClpResults to: \{state}" );
        userSettings.addProperty( "showNlpResults", state );
        setUserSettings( user, userSettings );
    }

    public void setTimelineDirection( UserCredentials user, TimelineDirection timelineDirection ) {
        String actualUserSettings = getUserSettings( user );
        JsonObject userSettings = new Gson().fromJson( actualUserSettings, JsonObject.class );
        userSettings.addProperty( "timelineDirection", timelineDirection.getValue() );
        LOGGER.info( "Setting timelineDirection to {}", timelineDirection );
        setUserSettings( user, userSettings );
    }

    public void setSwimlaneOrder( List<String> expectedSwimlaneOrder, UserCredentials user ) {
        // Get user settings
        String actualUserSettings = getUserSettings( user );
        JsonObject userSettings = new Gson().fromJson( actualUserSettings, JsonObject.class );

        // Get swimlanes
        String swimlanesResp = getSwimlanes( user );
        JsonArray swimlanes = new Gson().fromJson( swimlanesResp, JsonArray.class );

        List<String> orderedSwimlaneIds = new ArrayList<>();
        for ( int i = 0; i < expectedSwimlaneOrder.size(); i++ ) {
            String requiredSwimlaneName = expectedSwimlaneOrder.get( i );
            String receivedSwimlaneId = getSwimlaneId( requiredSwimlaneName, swimlanes );
            assertNotNull( "Following swimlane and its ID was not found: " + requiredSwimlaneName, receivedSwimlaneId );
            orderedSwimlaneIds.add( receivedSwimlaneId );
        }

        JsonArray orderedSwimlaneIdList = new Gson().toJsonTree( orderedSwimlaneIds ).getAsJsonArray();
        LOGGER.info( STR."The swimlaneOrder is set in the following order: \{orderedSwimlaneIdList}" );
        userSettings.add( "swimlaneOrder", orderedSwimlaneIdList );
        setUserSettings( user, userSettings );
    }

    public void setDefaultSwimlaneOrder( UserCredentials user ) {
        String actualUserSettings = getUserSettings( user );
        JsonObject userSettings = new Gson().fromJson( actualUserSettings, JsonObject.class );
        userSettings.remove( "swimlaneOrder" );   // empty array means default order
        LOGGER.info( "The swimlaneOrder is set to default" );
        setUserSettings( user, userSettings );
    }

    private String getSwimlaneId( String requiredSwimlaneName, JsonArray swimlanes ) {
        for ( int receivedSwimlaneIndex = 0; receivedSwimlaneIndex < swimlanes.size(); receivedSwimlaneIndex++ ) {
            String receivedSwimlaneName = swimlanes.get( receivedSwimlaneIndex ).getAsJsonObject().get( "label" ).getAsString();
            if ( receivedSwimlaneName.equals( requiredSwimlaneName ) ) {
                return swimlanes.get( receivedSwimlaneIndex ).getAsJsonObject().get( "id" ).getAsString();
            }
        }
        return null;
    }

    public void checkSettingsPageVisibility( Boolean isDisplayed ) {
        boolean isVisible = settingsPage.isVisible();
        LOGGER.info( STR."The settings page is visible: \{isVisible}" );
        assertThat( "User settings page visibility is not ok.", isVisible, is( equalTo( isDisplayed ) ) );
    }

    public void clickEditOnSetting( UserSetting usersetting ) {
        settingsPage.waitUntilLoadingIndicatorDisappears();
        settingsPage.clickOnEdit( usersetting );
    }

    public void selectRadioButton( String radioBtnLabel, UserSetting userSetting ) {
        settingsPage.selectRadioButton( radioBtnLabel, userSetting );
    }

    public void checkAvailableUserSettings( List<UserSetting> expectedUserSettings ) {
        settingsPage.waitUntilLoadingIndicatorDisappears();
        List<UserSetting> observedUserSettings = settingsPage.getUserSettings();
        LOGGER.info( STR."Observed settings: \{observedUserSettings}" );
        expectedUserSettings.forEach( expectedSettings ->
                assertTrue( STR."Following settings option was not found: \{expectedUserSettings}", observedUserSettings.contains( expectedSettings ) ) );
    }

    public void clickButtonInSettingsToolbar( String btnLabel ) {
        settingsPage.clickOnToolbarButton( btnLabel );
    }

    public void clickButtonInSettingsContent( String btnLabel ) {
        settingsPage.clickOnSettingsButton( btnLabel );
        setSessionVariable( USER_SETTINGS_CHANGED ).to( true );
    }

    public void cancelEditSettings() {
        settingsPage.cancelEditSettings();
    }

    public void checkListValueOfUserSetting( UserSetting userSetting, StringList expectedList ) {
        settingsPage.waitUntilLoadingIndicatorDisappears();
        // DOM is different if swimlane order Editing was clicked before
        boolean isSwimlaneOrderEdited = userSetting.getSettingName().contains( "Swimlane" )
                ? settingsPage.isSwimlaneOrderCurrentlyEdited()
                : false;

        List<String> observedList = settingsPage.getListValueOfUserSetting( userSetting, isSwimlaneOrderEdited );
        LOGGER.info( STR."Observed user settings: \{observedList}" );
        assertEquals(
                String.format( "User setting values do not match of '%s' user setting in '%s' section", userSetting.getSettingName(), userSetting.getTitle() ),
                expectedList.getList(), observedList );
    }

    public void setDragAndDropUserSetting( UserSetting userSetting, StringList expectedList ) {
        settingsPage.setDragAndDropUserSetting( userSetting, expectedList.getList() );
    }

    public boolean userHasAccessToOncoCare( UserCredentials user ) {
        RequestSpecBuilder rsb = new RequestSpecBuilder();
        rsb.setBaseUri( getSystemParameter( API_BASE_URL_PROPERTY ) );
        rsb.setBasePath( getSystemParameter( CORE_API_BASE_PATH_PROPERTY ) + USER_SETTINGS_ENDPOINT_PATH );
        Response response = restApiSteps.sendAuthenticatedRequest( user, new RestRequest( Method.GET, rsb.build() ) );
        return response.getStatusCode() == HttpStatus.SC_OK;
    }

    public void switchOncoFocus( OnOff oncoFocusState, UserCredentials user ) {
        LOGGER.info( "Set Oncology Focus: {}", oncoFocusState );
        String actualUserSettings = getUserSettings( user );
        JsonObject userSettings = new Gson().fromJson( actualUserSettings, JsonObject.class );
        boolean state = oncoFocusState.equals( ON );
        setUserSettings( user, userSettings );
    }

}
