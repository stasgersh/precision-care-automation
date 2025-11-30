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
package com.ge.onchron.verif.howsteps;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.UserCredentials;
import com.ge.onchron.verif.model.detailedDataItems.DetailedDataItem;
import com.ge.onchron.verif.pages.sections.PatientHeader;
import com.ge.onchron.verif.utils.PatientUtils;
import net.thucydides.core.annotations.Steps;

import static com.ge.onchron.verif.TestSystemParameters.STORED_USERS;
import static com.ge.onchron.verif.pages.utils.CookieUtils.getLoggedInUsername;
import static com.ge.onchron.verif.utils.PatientUtils.getPatientId;
import static com.ge.onchron.verif.utils.PatientUtils.resetPatientSettings;
import static com.ge.onchron.verif.utils.ReplaceUtils.replaceStoredId;
import static net.serenitybdd.core.Serenity.sessionVariableCalled;

public class PatientHeaderSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger( PatientHeaderSteps.class );
    private PatientHeader patientHeader;

    @Steps
    PatientDataSyncBannerSteps patientDataSyncBannerSteps;

    public void checkPatientHeaderVisibility( boolean isVisible ) {
        LOGGER.info( STR."Patient header visibility is: \{patientHeader.isVisible()}" );
        assertThat( "Patient header visibility is not ok.", patientHeader.isVisible(), is( equalTo( isVisible ) ) );
    }

    public void checkPatientBannerVisibility( Boolean isVisible ) {
        LOGGER.info( STR."Patient banner visibility is: \{patientHeader.isPatientBannerVisible()}" );
        assertEquals( "Patient banner visibility is not ok.", patientHeader.isPatientBannerVisible(), isVisible );
    }

    public void selectPatientByName( String patientName, boolean initPatientSettings ) {
        LOGGER.info( STR."Select patient by name: \{patientName}" );
        if ( initPatientSettings ) {
            initPatientSettings( patientName );
        }
        patientHeader.clickOnPatientSelect();
        patientHeader.selectPatientByName( patientName );
        patientDataSyncBannerSteps.waitUntilPatientStatusIsLoaded();
    }

    public void selectPatientById( String patientName, boolean initPatientSettings ) {
        patientName = replaceStoredId( patientName );
        selectPatientByName( patientName, initPatientSettings );
    }

    private void initPatientSettings( String patientName ) {
        // 1. Get currently logged-in user
        String loggedInUsername = getLoggedInUsername();
        Map<String, UserCredentials> storedUsers = sessionVariableCalled( STORED_USERS );
        UserCredentials userCredentials = storedUsers.entrySet().stream()
                                                     .filter( user -> user.getValue().getUsername().equals( loggedInUsername ) )
                                                     .findFirst()
                                                     .map( Map.Entry::getValue )
                                                     .orElseThrow( () -> new RuntimeException( STR."User credentials of logged in user were not stored:\{loggedInUsername}" ) );
        // 2. Get Patient ID
        String patientId = getPatientId( patientName );
        // 3. Reset patient timeline settings
        resetPatientSettings( userCredentials, patientId, PatientUtils.PATIENT_SETTINGS.TIMELINE );
        // 4. Reset patient response settings
        resetPatientSettings( userCredentials, patientId, PatientUtils.PATIENT_SETTINGS.TREATMENT_RESPONSE );
        // 5. Reset summary overview settings
        resetPatientSettings( userCredentials, patientId, PatientUtils.PATIENT_SETTINGS.SUMMARY );
        // 6. Reset medical history settings
        resetPatientSettings( userCredentials, patientId, PatientUtils.PATIENT_SETTINGS.MEDICAL_HISTORY );
    }

    public void openPatientSelect() {
        patientHeader.openPatientSelectMenu();
    }

    public void selectPatientByNameFromOpenedList( String patientName ) {
        initPatientSettings( patientName );
        LOGGER.info( STR."Selecting patient '\{patientName}' from opened list" );
        patientHeader.selectPatientByName( patientName );
    }

    public void checkAllPatientBannerDetails( List<DetailedDataItem> expectedParams ) {
        LOGGER.info( "Expected Patient banner params: " + expectedParams.toString() );
        List<DetailedDataItem> observedParams = getAllPatientBannerDetails();
        LOGGER.info( STR."Observed Patient banner params: \{observedParams}" );

        List<DetailedDataItem> expectedButNotReceived = expectedParams.stream().filter( element -> observedParams.stream().noneMatch( element::hasSameName ) ).toList();
        List<DetailedDataItem> receivedButNotExpected = observedParams.stream().filter( element -> expectedParams.stream().noneMatch( element::hasSameName ) ).toList();

        if ( !expectedButNotReceived.isEmpty() || !receivedButNotExpected.isEmpty() ) {
            fail( String.format( "Patient banner params do not match:\nExpected but not received: %s\nReceived but not expected: %s\n", expectedButNotReceived, receivedButNotExpected ) );
        }
        assertEquals( "The order of items on patient banner is not ok.", expectedParams, observedParams );
    }

    public void checkSpecificPatientBannerDetail( String detailName, String detailValue ) {
        DetailedDataItem observedDetail = getAllPatientBannerDetails().stream()
                                                                      .filter( d -> d.getName().equals( detailName ) )
                                                                      .findFirst()
                                                                      .orElseThrow( () -> new RuntimeException( STR."Detail \"\{detailName}\" not found" ) );
        LOGGER.info( STR."Observed header banner param is: \{observedDetail.toString()}" );
        assertEquals( STR."Detail value does not match with the specified \"\{detailValue}\" value", detailValue, observedDetail.getValue() );
    }

    public void checkSpecificPatientBannerDetails( List<DetailedDataItem> expectedParams ) {
        List<DetailedDataItem> observedParams = getAllPatientBannerDetails();
        LOGGER.info( STR."Observed header banner params are: \{observedParams.stream().map( DetailedDataItem::toString ).collect( Collectors.joining( "\\n" ) )}" );
        List<DetailedDataItem> expectedButNotReceived = expectedParams.stream().filter( element -> !observedParams.contains( element ) ).toList();
        if ( !expectedButNotReceived.isEmpty() ) {
            fail( String.format( "Patient banner params do not match:\nExpected but not received: %s\nAll received: %s\n", expectedButNotReceived, observedParams ) );
        }
    }

    public void checkPatientBannerParamsVisibility( List<String> expectedParameterNames, boolean isVisible ) {
        patientHeader.waitUntilPatientBannerLoadingSkeletonDisappears();
        List<DetailedDataItem> visibleParams = patientHeader.getCurrentlyVisiblePatientBannerParams();
        List<String> visibleParamNames = visibleParams.stream().map( DetailedDataItem::getName ).toList();
        LOGGER.info( STR."Visible parameter(s) in patient banner: \{visibleParamNames}" );
        if ( isVisible ) {
            // Verify that the expected parameters are visible in the patient banner
            // At this point, the step will pass if additional params are also visible (the test will fail later if not visible parameters are verified)
            expectedParameterNames.removeIf( visibleParamNames::contains );
            assertTrue( STR."Following parameter(s) should be visible in patient banner: \{expectedParameterNames}", expectedParameterNames.isEmpty() );
        } else {
            expectedParameterNames.removeIf( expectedParameterName -> !visibleParamNames.contains( expectedParameterName ) );
            assertTrue( STR."Following parameter(s) should not be visible in patient banner: \{expectedParameterNames}", expectedParameterNames.isEmpty() );
        }
    }

    public void expandPatientBannerWithCaret() {
        LOGGER.info( "Expanding patient banner with caret" );
        patientHeader.waitUntilPatientBannerLoadingSkeletonDisappears();
        patientHeader.openPatientBannerCaret();
    }

    public void closePatientBannerWithCaret() {
        LOGGER.info( "Closing patient banner with caret" );
        patientHeader.closePatientBannerCaret();
    }

    private List<DetailedDataItem> getAllPatientBannerDetails() {
        patientHeader.openPatientBannerCaret();
        patientHeader.waitUntilPatientBannerLoadingSkeletonDisappears();
        List<DetailedDataItem> observedParams = patientHeader.getPatientBannerParams();
        LOGGER.info( STR."Observed Patient banner params: \{observedParams}" );
        return observedParams;
    }

    public void clickOnLinkInPatientBanner( String linkName ) {
        patientHeader.openPatientBannerCaret();
        patientHeader.waitUntilPatientBannerLoadingSkeletonDisappears();
        patientHeader.clickOnLink( linkName );
    }

    public void checkSelectedPatientName( String expectedPatientName ) {
        String selectedPatientName = patientHeader.getSelectedPatientName();
        assertEquals( "Selected patient mismatch.", expectedPatientName, selectedPatientName );
    }

    public void isPatientBannerExpanded( Boolean isExpanded ) {
        patientHeader.waitUntilPatientBannerLoadingSkeletonDisappears();
        LOGGER.info( STR."Patient banner status is: \{isExpanded ? "expanded" : "collapsed"}" );
        assertEquals( STR."Patient banner should be \{isExpanded ? "expanded" : "collapsed"}", isExpanded, !patientHeader.isPatientBannerClosed() );
    }

    public void checkPatientBannerButtonText( String expectedButtonText ) {
        String buttonText = patientHeader.getPatientBannerButtonText();
        LOGGER.info( STR."Patient banner button text is: \{buttonText}" );
        assertEquals( "Patient banner button text is not ok", expectedButtonText, buttonText );
    }

    public void hoverOnHeaderParameterValue( String parameter ) {
        patientHeader.waitUntilPatientBannerLoadingSkeletonDisappears();
        patientHeader.hoverOnHeaderParameterValue( parameter );
    }

    public void hoverOnHeaderParameterMoreLink( String parameter ) {
        patientHeader.waitUntilPatientBannerLoadingSkeletonDisappears();
        patientHeader.hoverOnHeaderParameterMoreLink( parameter );
    }

    public void moveMouseToPatientBannerItem( DetailedDataItem patientBannerItem ) {
        hoverOnHeaderParameterValue( patientBannerItem.getName() );
    }

    public void moveMouseToPatientBannerMoreLink( DetailedDataItem patientBannerItem ) {
        hoverOnHeaderParameterMoreLink( patientBannerItem.getName() );
    }

    public void checkHighlightedAttributeAsIndicationForAI( DetailedDataItem highlightedBannerItems ) {
        boolean isValueHighlighted = patientHeader.getHighlightedDataForSpecificParameter( highlightedBannerItems );
        if ( !isValueHighlighted ) {
            LOGGER.info( STR."The patient banner's \{highlightedBannerItems.getName()} was not highlighted" );
        }
        assertThat( STR."The patient banner's \{highlightedBannerItems.getName()} was not highlighted as expeted.", true, is( isValueHighlighted ) );
    }

    public void checkPatientNameInPatientHeader( String expectedPatientName ) {
        String displayedPatientName = patientHeader.getSelectedPatientName();
        LOGGER.info( STR."Dispalyed patient name in patient header: \{displayedPatientName}" );
        assertEquals( "The displayed patient name is not ok.", expectedPatientName, displayedPatientName );
    }

    public void checkBannerTooltip( String fullText ) {
        String observedTooltipText = patientHeader.getBannerTooltipText();
        LOGGER.info( STR."Patient banner tooltip text is displayed as: \{observedTooltipText}" );
        assertThat( "Patient banner tooltip does not match expectation", observedTooltipText.contains( fullText ) );
    }

    public void checkTooltipDisplaysFullValueOfTheBannerItem( DetailedDataItem patientBannerItem ) {
        String observedTooltipText = patientHeader.getBannerTooltipText();
        String bannerItemValue = patientHeader.getPatientBannerParams().stream()
                                              .filter( banner -> banner.getName().equals( patientBannerItem.getName() ) )
                                              .map( DetailedDataItem::getValue )
                                              .map( Object::toString )
                                              .findFirst()
                                              .orElse( "" );
        LOGGER.info( STR."Observed tooltip text: \{observedTooltipText}" );
        LOGGER.info( STR."Expected banner item value: \{bannerItemValue}" );
        assertThat( STR."Patient banner tooltip does not display full value of the banner item \{patientBannerItem.getName()}",
                observedTooltipText.contains( bannerItemValue )
        );
    }

    public void checkBannerLoadingSkeletonVisibility( boolean isVisible ) {
        boolean observedLoadingSkeletonVisible = patientHeader.isLoadingSkeletonVisible();
        assertEquals( String.format( "Patient banner loading skeleton was %s when expected to be %s", isVisible ? "invisible" : "visible", isVisible ? "visible" : "invisible" ),
                isVisible,
                observedLoadingSkeletonVisible );
    }
}
