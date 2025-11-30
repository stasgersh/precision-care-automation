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

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.RestRequest;
import com.ge.onchron.verif.model.Table;
import com.ge.onchron.verif.model.enums.PsSection;
import com.ge.onchron.verif.pages.sections.PatientHeader;
import com.ge.onchron.verif.pages.sections.ProgressiveSummarization;
import com.ge.onchron.verif.pages.tabs.Summary;
import com.ge.onchron.verif.utils.JsonUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.thucydides.core.annotations.Steps;

import static com.ge.onchron.verif.TestSystemParameters.PATIENT_SUMMARY_BASE_URL;
import static com.ge.onchron.verif.TestSystemParameters.PATIENT_SUMMARY_CONFIG_PATH;
import static com.ge.onchron.verif.TestSystemParameters.PATIENT_SUMMARY_MONITORING_PATH;
import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;
import static com.ge.onchron.verif.utils.ReplaceUtils.replaceStoredId;
import static com.ge.onchron.verif.utils.Utils.waitMillis;
import static net.serenitybdd.core.Serenity.sessionVariableCalled;

public class ProgressiveSummarizationSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger( ProgressiveSummarizationSteps.class );

    private ProgressiveSummarization ps;
    private Summary summary;
    private PatientHeader patientHeader;

    @Steps
    RestApiSteps restApiSteps;

    public void collapsePsOverview() {
        ps.collapsePsOverview();
        waitMillis( 500 );
    }

    public void checkPsOverviewVisibility( boolean isVisible ) {
        boolean observedVisibility = ps.isVisible();
        LOGGER.info( String.format( "Progressive Summarization visibility is: %s", observedVisibility ) );
        assertEquals( "Progressive Summarization visibility is not ok.", isVisible, observedVisibility );
    }

    public void checkPsOverviewIsCollapsed( boolean isCollapsed ) {
        ps.waitUntilLoadingSkeletonDisappears();
        boolean isCollapsedCurrently = ps.isCollapsed();
        LOGGER.info( String.format( "Progressive Summarization is collapsed: %s", isCollapsedCurrently ) );
        assertEquals( String.format( "Progressive Summarization should %s collapsed", isCollapsed ? "be" : "not be" ),
                isCollapsed, isCollapsedCurrently );
    }

    public void checkPsSectionVisibility( String sectionName, Boolean expectedVisibility ) {
        ps.waitUntilLoadingSkeletonDisappears();
        boolean observedVisibility = false;

        PsSection section = PsSection.fromString( sectionName );
        switch ( section ) {
            case BRIEF_HISTORY:
                observedVisibility = ps.isBriefHistoryVisible();
                break;
            case WHAT_HAS_CHANGED:
                observedVisibility = ps.isWhatHasChangedVisible();
                break;
            case SUMMARY_OF_RECENT_REPORTS:
                observedVisibility = ps.isSummaryOfRecentReportsVisible();
                break;
            default:
                fail( String.format( "Not known PS section: %s", sectionName ) );
        }
        LOGGER.info( String.format( "%s visibility: %s", sectionName, observedVisibility ) );
        assertEquals( String.format( "%s visibility is not ok.", sectionName ), expectedVisibility, observedVisibility );
    }

    public void checkPsSectionTitle( String sectionName, String expectedTitle ) {
        ps.waitUntilLoadingSkeletonDisappears();
        String observedTitle = null;

        PsSection section = PsSection.fromString( sectionName );
        switch ( section ) {
            case BRIEF_HISTORY:
                observedTitle = ps.getBriefHistoryTitle();
                break;
            case WHAT_HAS_CHANGED:
                observedTitle = ps.getWhatHasChangedTitle();
                break;
            case SUMMARY_OF_RECENT_REPORTS:
                observedTitle = ps.getSummaryOfRecentReportsTitle();
                break;
            default:
                fail( String.format( "Not known PS section: %s", sectionName ) );
        }
        LOGGER.info( String.format( "%s title: %s", sectionName, observedTitle ) );
        assertEquals( String.format( "%s title is not ok.", sectionName ), expectedTitle, observedTitle );
    }

    public void checkAiTurnedOffInfoVisibilityOnPsSection( String sectionName, Boolean expectedVisibility ) {
        ps.waitUntilLoadingSkeletonDisappears();
        boolean observedVisibility = false;

        PsSection section = PsSection.fromString( sectionName );
        switch ( section ) {
            case BRIEF_HISTORY:
                observedVisibility = ps.isAiTurnedOffInfoVisibleInBriefHistory();
                break;
            case WHAT_HAS_CHANGED:
                observedVisibility = ps.isAiTurnedOffInfoVisibleInWhatHasChangedSection();
                break;
            case SUMMARY_OF_RECENT_REPORTS:
                observedVisibility = ps.isAiTurnedOffInfoVisibleInSummaryOfRecentReports();
                break;
            default:
                fail( String.format( "Not known PS section: %s", sectionName ) );
        }
        LOGGER.info( String.format( "AI Turned Off Info visibility in %s: %s", sectionName, observedVisibility ) );
        assertEquals( String.format( "AI Turned Off Info visibility in %s is not ok.", sectionName ), expectedVisibility, observedVisibility );
    }

    public void checkAiBadgeTextOnPsSection( String sectionName, String expectedText ) {
        ps.waitUntilLoadingSkeletonDisappears();
        String observedText = null;

        PsSection section = PsSection.fromString( sectionName );
        switch ( section ) {
            case BRIEF_HISTORY:
                observedText = ps.getAiBadgeTextFromBriefHistory();
                break;
            case SUMMARY_OF_RECENT_REPORTS:
                observedText = ps.getAiBadgeTextFromSummaryOfRecentReports();
                break;
            default:
                fail( String.format( "Checking AI badge on PS section '%s' is not supported", sectionName ) );
        }
        LOGGER.info( String.format( "AI badge text on '%s': %s", sectionName, observedText ) );
        assertEquals( String.format( "AI badge text on '%s' is not ok.", sectionName ), expectedText, observedText );
    }

    public void hoverInfoSymbolOnPsSection( String sectionName ) {
        waitUntilAllLoadingSkeletonsDisappear();
        PsSection section = PsSection.fromString( sectionName );
        switch ( section ) {
            case BRIEF_HISTORY:
                ps.hoverInfoSymbolOnBriefHistory();
                break;
            case SUMMARY_OF_RECENT_REPORTS:
                ps.hoverInfoSymbolOnSummaryOfRecentReports();
                break;
            default:
                fail( String.format( "Hover Info symbol on PS section '%s' is not supported", sectionName ) );
        }
        waitMillis( 500 );
    }

    public void clickButtonOnPsSection( String sectionName, String buttonText ) {
        ps.waitUntilLoadingSkeletonDisappears();
        PsSection section = PsSection.fromString( sectionName );
        switch ( section ) {
            case BRIEF_HISTORY:
                ps.clickButtonOnBriefHistory( buttonText );
                break;
            case SUMMARY_OF_RECENT_REPORTS:
                ps.clickButtonOnSummaryOfRecentReports( buttonText );
                break;
            case WHAT_HAS_CHANGED:
                ps.clickButtonOnWhatHasChanged( buttonText );
                break;
            default:
                fail( String.format( "Click button on PS section '%s' is not supported", sectionName ) );
        }
        waitMillis( 1000 );
    }

    public void checkWhatHasChangedTableAvailability( boolean expectedVisibility ) {
        ps.waitUntilLoadingSkeletonDisappears();
        boolean observedVisibility = ps.isWhatHasChangedTableAvailable();
        LOGGER.info( String.format( "'What has changed' table visibility: %s", observedVisibility ) );
        assertEquals( "'What has changed' table visibility is not ok.", expectedVisibility, observedVisibility );
    }

    public void checkWhatHasChangedTableContent( Table expectedTable ) {
        ps.waitUntilLoadingSkeletonDisappears();
        Table observedTable = ps.getWhatHasChangedTable();
        assertEquals( "'What has changed' table is not ok.", expectedTable.getRows(), observedTable.getRows() );
    }

    public void checkWhatHasChangedSectionMoreOrLessButtonText( String sectionName, String expectedButtonText ) {
        ps.waitUntilLoadingSkeletonDisappears();
        PsSection section = PsSection.fromString( sectionName );
        String actualButtonText = null;
        switch ( section ) {
            case BRIEF_HISTORY:
                actualButtonText = ps.getBriefHistoryMoreOrLessButtonText();
                break;
            case SUMMARY_OF_RECENT_REPORTS:
                actualButtonText = ps.getSummaryOfRecentReportsMoreOrLessButtonText();
                break;
            case WHAT_HAS_CHANGED:
                actualButtonText = ps.getWhatHasChangedMoreOrLessButtonText();
                break;
            default:
                fail( String.format( "PS section '%s' is not found", sectionName ) );
        }
        assertNotNull( String.format( "'%s' button on PS section '%s' is not found", expectedButtonText, sectionName ), actualButtonText );
        LOGGER.info( String.format( "Actual button text of '%s' section was: \"%s\"", sectionName, actualButtonText ) );
        assertEquals( String.format( "'%s' 'Show more'/'Show less' button text is not expected", sectionName ), expectedButtonText, actualButtonText );
    }

    public void checkButtonStateInTable( String buttonName, boolean isEnabledExpected, int rowNb, String columnName ) {
        ps.waitUntilLoadingSkeletonDisappears();
        boolean isEnabledObserved = ps.isButtonEnabledInTableCell( buttonName, rowNb, columnName );
        LOGGER.info( String.format( "'Full report' button in 'What has changed' table (row: %s, column: %s) is enabled: %s", rowNb, columnName, isEnabledObserved ) );
        assertEquals(
                String.format( "State (enabled) of 'Full report' button in 'What has changed' table (row: %s, column: %s) is not ok.", rowNb, columnName, isEnabledObserved ),
                isEnabledExpected, isEnabledObserved );
    }

    public void clickFullReportButton( int rowNb, String columnName ) {
        ps.waitUntilLoadingSkeletonDisappears();
        ps.clickFullReportButton( rowNb, columnName );
    }

    public void hoverButtonInTable( String buttonName, int rowNb, String columnName ) {
        ps.waitUntilLoadingSkeletonDisappears();
        ps.hoverButtonInTable( buttonName, rowNb, columnName );
    }

    public void checkReportTextOnPsSection( String sectionName, List<String> expectedKeyWords, boolean shouldContain ) {
        ps.waitUntilLoadingSkeletonDisappears();
        PsSection section = PsSection.fromString( sectionName );
        String reportText = null;
        switch ( section ) {
            case BRIEF_HISTORY:
                reportText = ps.getBriefHistoryReportText();
                break;
            case SUMMARY_OF_RECENT_REPORTS:
                reportText = ps.getSummaryOfRecentReportsText();
                break;
            default:
                fail( String.format( "PS section '%s' is not known", sectionName ) );
        }
        List<String> notFoundKeyWords = new ArrayList<>();
        for ( String expectedKeyWord : expectedKeyWords ) {
            if ( !reportText.toLowerCase().contains( expectedKeyWord.toLowerCase() ) ) {
                notFoundKeyWords.add( expectedKeyWord );
            }
        }
        if ( shouldContain ) {
            assertTrue( String.format( "Following key words were not found in '%s' section: %s", sectionName, notFoundKeyWords ), notFoundKeyWords.isEmpty() );
        } else {
            expectedKeyWords.removeAll( notFoundKeyWords ); // remove the elements that were found but should not be found
            assertTrue(
                    String.format( "Following key words should not be found in '%s' section: %s", sectionName, expectedKeyWords ),
                    expectedKeyWords.isEmpty() );
        }
    }

    public void checkFullReportTextOnPsSection( String sectionName, String expectedText ) {
        ps.waitUntilLoadingSkeletonDisappears();
        PsSection section = PsSection.fromString( sectionName );
        String reportText = null;
        switch ( section ) {
            case BRIEF_HISTORY:
                reportText = ps.getBriefHistoryReportText();
                break;
            case SUMMARY_OF_RECENT_REPORTS:
                reportText = ps.getSummaryOfRecentReportsText();
                break;
            default:
                fail( String.format( "PS section '%s' is not known", sectionName ) );
        }
        assertEquals( String.format( "Text is not ok in PS section: %s", sectionName ), expectedText, reportText );
    }

    public void hoverSourceOnPsSection( String sectionName, int sourceIndex ) {
        waitUntilAllLoadingSkeletonsDisappear();
        PsSection section = PsSection.fromString( sectionName );
        switch ( section ) {
            case BRIEF_HISTORY:
                ps.hoverSourceInBriefHistory( sourceIndex );
                break;
            case SUMMARY_OF_RECENT_REPORTS:
                ps.hoverSourceInSummaryOfRecentReports( sourceIndex );
                break;
            default:
                fail( String.format( "PS section '%s' is not known", sectionName ) );
        }
    }

    public void clickSourceOnPsSection( String sectionName, int sourceIndex ) {
        waitUntilAllLoadingSkeletonsDisappear();
        PsSection section = PsSection.fromString( sectionName );
        switch ( section ) {
            case BRIEF_HISTORY:
                ps.clickSourceInBriefHistory( sourceIndex );
                break;
            case SUMMARY_OF_RECENT_REPORTS:
                ps.clickSourceInSummaryOfRecentReports( sourceIndex );
                break;
            default:
                fail( String.format( "PS section '%s' is not known", sectionName ) );
        }
    }

    public void clickLastSourceOnPsSection( String sectionName ) {
        waitUntilAllLoadingSkeletonsDisappear();
        PsSection section = PsSection.fromString( sectionName );
        switch ( section ) {
            case BRIEF_HISTORY:
                int nbOfSourcesInBriefHistory = ps.getNbOfVisibleSourcesInBriefHistory();
                clickSourceOnPsSection( sectionName, nbOfSourcesInBriefHistory - 1 );
                break;
            case SUMMARY_OF_RECENT_REPORTS:
                int nbOfSourcesInSummaryOfRecentReports = ps.getNbOfVisibleSourcesInSummaryOfRecentReports();
                clickSourceOnPsSection( sectionName, nbOfSourcesInSummaryOfRecentReports - 1 );
                break;
            default:
                fail( String.format( "PS section '%s' is not known", sectionName ) );
        }
    }

    private void waitUntilAllLoadingSkeletonsDisappear() {
        summary.waitUntilLoadingSkeletonDisappears();
        patientHeader.waitUntilPatientBannerLoadingSkeletonDisappears();
        ps.waitUntilLoadingSkeletonDisappears();
    }

    public void deletePatientFromMonitoringList( String patientId ) {
        List<String> patientIds = getPatientMonitoringList();
        if ( patientIds.contains( patientId ) ) {
            patientIds.removeIf( pid -> pid.equals( patientId ) );
            // Update PS monitoring list without the patientId that need to be removed
            JsonArray patientIdArray = new JsonArray();
            patientIds.forEach( patientIdArray::add );
            JsonObject payload = new JsonObject();
            payload.add( "patientIds", patientIdArray );
            updatePatientMonitoringList( payload );
        }
    }

    public void deletePatientsFromMonitoringList() {
        deletePatientMonitoringList();
    }

    public void configureTriggeringEventSchedules( String eventType, boolean isConfigured, int min ) {
        JsonObject configuration = getSummaryConfiguration();

        ZonedDateTime now = Instant.now().atZone( ZoneOffset.UTC );

        ZonedDateTime baseTime = isConfigured ? now.plusSeconds( 75 ) : now.minusMinutes( 5 );
        ZonedDateTime adjusted = baseTime.plusMinutes( min );
        int minuteConfig = adjusted.getMinute();
        String cronPattern = minuteConfig + " * * * ? *";

        if ( eventType.equalsIgnoreCase( "scheduled" ) ) {
            eventType = "scheduledTriggeringRule";
        } else if ( eventType.equalsIgnoreCase( "monitoring list" ) ) {
            eventType = "monitoredListUpdateRule";
        } else {
            fail( String.format( "Triggering event type '%s' is not supported", eventType ) );
        }
        String finalEventType = eventType;
        configuration.entrySet().forEach( entry -> {
            if ( entry.getKey().equals( finalEventType ) ) {
                entry.setValue( new JsonPrimitive( cronPattern ) );
            }
        } );

        updateSummaryConfiguration( configuration );
    }

    public void monitoringListContainsPatient( String patientId ) {
        patientId = replaceStoredId( patientId );

        final int MAX_WAIT_TIME_MS = 5 * 60 * 1_000; // 5 mins max; usually completes < 1 min
        final int MAX_RETRY_TIMES = 10;

        int retryCounter = 0;
        while ( !getPatientMonitoringList().contains( patientId ) && retryCounter <= MAX_RETRY_TIMES ) {
            waitMillis( MAX_WAIT_TIME_MS / MAX_RETRY_TIMES );
            retryCounter++;
        }

        assertTrue( getPatientMonitoringList().contains( patientId ) );
    }

    public void checkMonitoringListIsEmpty( boolean isEmpty ) {
        assertEquals( String.format( "The monitoring list is %s", isEmpty ? "not empty" : "empty" ),
                isEmpty, getPatientMonitoringList().isEmpty() );
    }

    private JsonObject getSummaryConfiguration() {
        RequestSpecBuilder rsb = new RequestSpecBuilder()
                .setBaseUri( getSystemParameter( PATIENT_SUMMARY_BASE_URL ) )
                .setBasePath( getSystemParameter( PATIENT_SUMMARY_CONFIG_PATH ) );
        Response response = restApiSteps.sendBackendAuthenticatedRequest( new RestRequest( Method.GET, rsb.build() ) );
        assertEquals( "The response status code of getting patient monitoring list is not ok.", HttpStatus.SC_OK, response.getStatusCode() );
        return new Gson().fromJson( response.asString(), JsonObject.class );
    }

    private void updateSummaryConfiguration( JsonObject payload ) {
        RequestSpecBuilder rsb = new RequestSpecBuilder()
                .setBaseUri( getSystemParameter( PATIENT_SUMMARY_BASE_URL ) )
                .setBasePath( getSystemParameter( PATIENT_SUMMARY_CONFIG_PATH ) )
                .setBody( payload.toString() )
                .setContentType( ContentType.JSON );
        Response response = restApiSteps.sendBackendAuthenticatedRequest( new RestRequest( Method.PUT, rsb.build() ) );
        assertEquals( "The response status code of updating patient monitoring list is not ok.", HttpStatus.SC_CREATED, response.getStatusCode() );
    }

    private List<String> getPatientMonitoringList() {
        RequestSpecBuilder rsb = new RequestSpecBuilder()
                .setBaseUri( getSystemParameter( PATIENT_SUMMARY_BASE_URL ) )
                .setBasePath( getSystemParameter( PATIENT_SUMMARY_MONITORING_PATH ) );
        Response response = restApiSteps.sendBackendAuthenticatedRequest( new RestRequest( Method.GET, rsb.build() ) );
        assertEquals( "The response status code of getting patient monitoring list is not ok.", HttpStatus.SC_OK, response.getStatusCode() );
        return response.jsonPath().getList( "patients.id" );
    }

    private void updatePatientMonitoringList( JsonObject payload ) {
        RequestSpecBuilder rsb = new RequestSpecBuilder()
                .setBaseUri( getSystemParameter( PATIENT_SUMMARY_BASE_URL ) )
                .setBasePath( getSystemParameter( PATIENT_SUMMARY_MONITORING_PATH ) )
                .setBody( payload.toString() )
                .setContentType( ContentType.JSON );
        Response response = restApiSteps.sendBackendAuthenticatedRequest( new RestRequest( Method.PUT, rsb.build() ) );
        assertEquals( "The response status code of updating patient monitoring list is not ok.", HttpStatus.SC_CREATED, response.getStatusCode() );
    }

    private void deletePatientMonitoringList() {
        RequestSpecBuilder rsb = new RequestSpecBuilder()
                .setBaseUri( getSystemParameter( PATIENT_SUMMARY_BASE_URL ) )
                .setBasePath( getSystemParameter( PATIENT_SUMMARY_MONITORING_PATH ) );
        Response response = restApiSteps.sendBackendAuthenticatedRequest( new RestRequest( Method.DELETE, rsb.build() ) );
        assertEquals( "The response status code of deleting patient monitoring list is not ok.", HttpStatus.SC_NO_CONTENT, response.getStatusCode() );
    }

    public void checkEmptyStateMessage( String sectionName, String expectedMessage ) {
        waitUntilAllLoadingSkeletonsDisappear();
        PsSection section = PsSection.fromString( sectionName );
        String observedEmptyStateMsg = null;
        switch ( section ) {
            case BRIEF_HISTORY:
                observedEmptyStateMsg = ps.getBriefHistoryEmptyStateMessage();
                break;
            case SUMMARY_OF_RECENT_REPORTS:
                observedEmptyStateMsg = ps.getSummaryOfRecentReportsEmptyStateMessage();
                break;
            case WHAT_HAS_CHANGED:
                observedEmptyStateMsg = ps.getWhatHasChangedEmptyStateMessage();
                break;
            default:
                fail( String.format( "PS section '%s' is not found", sectionName ) );
        }
        LOGGER.info( String.format( "Empty state message on '%s' section: %s", sectionName, observedEmptyStateMsg ) );
        assertEquals( String.format( "Empty state message is not ok in PS section: %s", sectionName ), expectedMessage, observedEmptyStateMsg );
    }

    public void checkLoadingSkeletonVisibility( boolean isVisible ) {
        boolean observedLoadingSkeletonVisible = ps.isLoadingSkeletonVisible();
        assertEquals( String.format( "Progressive summarization loading skeleton was %s when expected to be %s", isVisible ? "invisible" : "visible", isVisible ? "visible" : "invisible" ),
                isVisible,
                observedLoadingSkeletonVisible );
    }

    public void checkBulletPointsAvailabilityInSection( String sectionName ) {
        waitUntilAllLoadingSkeletonsDisappear();
        PsSection section = PsSection.fromString( sectionName );
        switch ( section ) {
            case BRIEF_HISTORY:
                ps.assertAllAiFindingHasBulletPointInBriefHistory();
                break;
            case SUMMARY_OF_RECENT_REPORTS:
                ps.assertAllAiFindingHasBulletPointInSummaryOfRecentReports();
                break;
            default:
                fail( String.format( "PS section '%s' is not found", sectionName ) );
        }
    }

    public void checkSourceLinkAvailabilityInSection( String sectionName ) {
        waitUntilAllLoadingSkeletonsDisappear();
        PsSection section = PsSection.fromString( sectionName );
        switch ( section ) {
            case BRIEF_HISTORY:
                ps.assertAllAiFindingHasSourceLinkInBriefHistory();
                break;
            case SUMMARY_OF_RECENT_REPORTS:
                ps.assertAllAiFindingHasSourceLinkInSummaryOfRecentReports();
                break;
            default:
                fail( String.format( "PS section '%s' is not found", sectionName ) );
        }
    }

    public void compareMonitoringProperties( String propertyName, String response2, String response1, String comparison ) {
        String jsonA = sessionVariableCalled( response1 );
        String jsonB = sessionVariableCalled( response2 );

        String stringValue1 = JsonUtils.getFieldValue( jsonA, propertyName );
        String stringValue2 = JsonUtils.getFieldValue( jsonB, propertyName );

        assertNotNull( stringValue1, STR."\{propertyName} not found in \{response1}" );
        assertNotNull( stringValue2, STR."\{propertyName} not found in \{response2}" );

        String comp = comparison == null ? "" : comparison.trim().toLowerCase();

        switch ( comp ) {
            case "greater":
                try {
                    long valueA = Long.parseLong( Objects.requireNonNull( stringValue1 ) );
                    long valueB = Long.parseLong( Objects.requireNonNull( stringValue2 ) );
                    assertTrue( String.format( "%s in %s (%d) is not greater than in %s (%d)",
                            propertyName, response2, valueB, response1, valueA ), valueB > valueA );
                    break;
                } catch ( Exception e ) {
                    fail( "Could not compare values from the saved responses as numbers: " + e.getMessage() );
                }

            case "later":
                OffsetDateTime date1 = JsonUtils.parseToOffsetDateTime( stringValue1 );
                OffsetDateTime date2 = JsonUtils.parseToOffsetDateTime( stringValue2 );
                assertTrue( String.format( "%s in %s (%s) is not later than in %s (%s)",
                        propertyName, response2, date2, response1, date1 ), date2.isAfter( date1 ) );
                return;

            case "equal":
                assertEquals( stringValue1, stringValue2, String.format( "%s in %s is not equal to in %s", propertyName, response1, response2 ) );
                return;

            default:
                throw new IllegalArgumentException( "Unsupported comparison: " + comparison + ". Supported: greater, later, equal" );
        }
    }

    public void checkMonitoringResponseDoesNotContainProperties( List<String> properties ) {
        String body = restApiSteps.getLastResponse().asString();

        List<String> foundProperties = new ArrayList<>();
        for ( String propertyName : properties ) {
            if ( body.contains( "\"" + propertyName + "\"" ) ) {
                foundProperties.add( propertyName );
            }
        }
        if ( !foundProperties.isEmpty() ) {
            fail( String.format( "The following properties were found in the response but should not be present: %s",
                    String.join( ", ", foundProperties ) ) );
        }
    }
}
