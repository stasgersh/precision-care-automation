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
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.howsteps;

import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.SECONDS;

import org.jbehave.core.model.ExamplesTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.ViewModeModalConfiguration;
import com.ge.onchron.verif.pages.sections.modal.ViewModeModal;
import com.ge.onchron.verif.pages.sections.toolbar.TimelineToolbar;

import static com.ge.onchron.verif.utils.ReplaceUtils.replaceDatePlaceholders;

public class ViewModeModalSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger( ViewModeModalSteps.class );

    private ViewModeModal viewModeModal;
    private TimelineToolbar timelineToolbar;


    public void openViewModeModal() {
        if ( !timelineToolbar.isViewModeModalOpen() ) {
            LOGGER.info( STR."Opening View mode modal on Timeline toolbar" );
            timelineToolbar.clickViewModeButton();
        }
    }

    public void checkViewModeModalIsOrIsNotDisplayed( boolean isDisplayed ) {
        boolean state = timelineToolbar.isViewModeModalOpen();
        LOGGER.info( STR."The 'View mode' modal's actual state is: \{state}" );
        assertEquals( "View mode modal was not in the expected state", isDisplayed, state );
    }

    public void setViewModeOnViewModeModal( String viewMode ) {
        if ( viewMode != null && !viewMode.equals( "N/A" ) && !viewMode.isEmpty() ) {
            String expectedModeValue = null;
            switch ( viewMode.toLowerCase() ) {
                case "default":
                    expectedModeValue = "default";
                    break;
                case "treatment adherence":
                    expectedModeValue = "treatmentAdherence";
                    break;
                default:
                    fail( STR."Could not found \{viewMode} option on View mode modal" );
                    break;
            }
            viewModeModal.clickOnRadio( expectedModeValue );
        }
    }

    public void setAdherenceProtocolSettings( ExamplesTable adherenceProtocolDetails ) {
        Map<String, String> protocolDetails = adherenceProtocolDetails.getRows().getFirst();
        String viewMode = protocolDetails.get( "view_mode" );
        String protocol = protocolDetails.get( "protocol" );
        String trialArm = protocolDetails.get( "trial_arm" );
        String date = protocolDetails.get( "start_date" );

        if ( date != null && date.contains( "DAYS" ) ) {
            date = replaceDatePlaceholders( date, "MMM dd, yyyy" );
        }

        setViewModeOnViewModeModal( viewMode );
        selectOptionInDropdown( "Protocol", protocol );
        selectOptionInDropdown( "Trial arm", trialArm );
        typeIntoProtocolStartDate( date );
    }

    private void typeIntoProtocolStartDate( String date ) {
        if ( date != null && !date.equals( "N/A" ) && !date.isEmpty() ) {
            viewModeModal.typeIntoProtocolStartDate( date );
        }
    }

    public void selectOptionInDropdown( String settings, String option ) {
        if ( option != null && !option.equals( "N/A" ) && !option.isEmpty() ) {
            viewModeModal.openDropdownOfFieldOnViewModeModal( settings );
            viewModeModal.selectOptionFromOpenDropdown( option );
        }
    }

    public void clickButtonOnViewMode( String button ) {
        viewModeModal.clickButtonOnViewMode( button );
    }

    public void waitForViewModeModalLoaderToFinish() {
        viewModeModal.waitForModalLoaderToFinish();
    }

    public void waitForViewModeModalToClose() {
        viewModeModal.waitForModalClosure();
    }

    public void openDropDownOnViewModeModal( String settings ) {
        viewModeModal.openDropdownOfFieldOnViewModeModal( settings );
    }

    public void closeOpenDropDownOnViewModeModal() {
        viewModeModal.closeOpenDropDown();
    }

    public void checkExpectedOptionsArePresentInTheOpenDropdown( ExamplesTable optionList ) {
        List<String> expectedOptions = optionList.getRows().stream().map( param -> param.get( "options" ) ).toList();
        List<String> actualOptions = viewModeModal.getOptionsFromOpenDropDownAsList();
        assertTrue( "Expected options were not available on the open dropdown", actualOptions.containsAll( expectedOptions ) );
    }

    public void checkErrorMessageOfFieldOnViewModeModal( String field, String expectedError ) {
        String actualError = viewModeModal.getErrorMessageOfViewModeModalField( field );
        LOGGER.info( STR."Actual error message of \{field} field was \{actualError}" );
        assertEquals( "Actual error message was not expected", expectedError, actualError );
    }

    public void checkNoErrorMessageIsDisplayedOfViewModeModalField( String field ) {
        viewModeModal.setImplicitTimeout( 0, SECONDS );
        String actualErrorMessage = viewModeModal.getErrorMessageOfViewModeModalField( field );
        assertTrue( "Error message should not be displayed\n", actualErrorMessage.contains( "No error message was found" ) );
        viewModeModal.resetImplicitTimeout();
    }

    public void checkViewModeModalConfiguration( ViewModeModalConfiguration expectedConfiguration ) {
        ViewModeModalConfiguration actualConfiguration = getCurrentConfiguration();
        assertEquals( "Expected view mode modal configuration does not match expected", expectedConfiguration, actualConfiguration );
    }

    private ViewModeModalConfiguration getCurrentConfiguration() {
        ViewModeModalConfiguration actualConfiguration = new ViewModeModalConfiguration();
        actualConfiguration.setSelectedViewMode( getSelectedValueOrPlaceholderByFieldTitle( "View mode" ) );
        actualConfiguration.setProtocol( getSelectedValueOrPlaceholderByFieldTitle( "Protocol" ) );
        actualConfiguration.setTrialArm( getSelectedValueOrPlaceholderByFieldTitle( "Trial arm" ) );
        actualConfiguration.setProtocolStartDate( getSelectedValueOrPlaceholderByFieldTitle( "Protocol start date" ) );
        return actualConfiguration;
    }

    private String getSelectedValueOrPlaceholderByFieldTitle( String elementTitle ) {
        if ( elementTitle.equalsIgnoreCase( "View mode" ) ) {
            return viewModeModal.getSelectedViewMode();
        } else if ( elementTitle.equalsIgnoreCase( "Protocol start date" ) ) {
            return viewModeModal.getSelectedDateOrPlaceholderFromDatePicker();
        } else {
            return viewModeModal.getSelectedConfigValueByTitle( elementTitle );
        }
    }
}
