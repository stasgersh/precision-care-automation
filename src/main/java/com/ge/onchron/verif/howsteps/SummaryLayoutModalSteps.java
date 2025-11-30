/*
 * -GE CONFIDENTIAL-
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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import com.ge.onchron.verif.model.summaryCard.SummaryGroup;
import com.ge.onchron.verif.pages.sections.modal.SummaryLayoutModal;

public class SummaryLayoutModalSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger( SummaryLayoutModalSteps.class );

    private SummaryLayoutModal summaryLayoutModal;

    public void checkSummaryLayoutModalIsDisplayed( boolean isVisible ) {
        boolean summaryLayoutModalIsVisible = summaryLayoutModal.isCurrentlyVisible();
        LOGGER.info( "The Summary layout modal is visible: {}", summaryLayoutModalIsVisible );
        assertThat( "Summary layout modal visibility is not ok.", summaryLayoutModalIsVisible, is( equalTo( isVisible ) ) );
    }

    public void checkGroups( List<SummaryGroup> expectedGroups ) {
        LOGGER.info( "Expected group settings on Summary layout modal: {}", expectedGroups );
        List<SummaryGroup> observedGroups = summaryLayoutModal.getDraggableGroups();
        LOGGER.info( "Observed group settings on Summary layout modal'': {}", observedGroups );
        assertEquals( "Groups on Summary layout modal not ok", expectedGroups, observedGroups );
    }

    public void setGroupSettings( List<SummaryGroup> requiredGroupsSettings ) {
        LOGGER.info( "Required group settings on Summary layout modal: {}", requiredGroupsSettings );
        summaryLayoutModal.setGroups( requiredGroupsSettings );
        // Check groups to make sure that the order and visibility is set as expected
        LOGGER.info( "Group settings after setting them on Summary layout modal: {}", requiredGroupsSettings );
        checkGroups( requiredGroupsSettings );
    }

    public void clickOnButton( String buttonText ) {
        LOGGER.info( "Click on button on Summary layout modal: {}", buttonText );
        summaryLayoutModal.clickOnButton( buttonText );
    }

    public void waitForDisappearModal() {
        LOGGER.info( "Waiting for disappearing Summary layout modal" );
        summaryLayoutModal.waitForDisappearModal();
        LOGGER.info( "Summary layout modal disappeared" );
    }

    public void checkColumnNames( List<String> expectedColumnNames ) {
        LOGGER.info( "Expected column names on Summary layout modal: {}", expectedColumnNames );
        List<String> observedColumnNames = summaryLayoutModal.getColumnNames();
        LOGGER.info( "Observed column names on Summary layout modal: {}", observedColumnNames );
        assertEquals(
                String.format( "Header names not ok on Summary layout modal.\nExpected: %s\n Observed: %s\n",
                        expectedColumnNames, observedColumnNames ), expectedColumnNames, observedColumnNames );
    }

}
