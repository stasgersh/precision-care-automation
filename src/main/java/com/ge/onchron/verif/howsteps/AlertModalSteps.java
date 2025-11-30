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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ge.onchron.verif.pages.sections.modal.AlertModal;

import static com.ge.onchron.verif.TestSystemParameters.TEST_DATA_CLASSPATH;
import static com.ge.onchron.verif.utils.FileUtils.readFromFile;
import static com.ge.onchron.verif.utils.Utils.normalizeLineEndings;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class AlertModalSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger( AlertModalSteps.class );

    private AlertModal alertModal;

    public void checkAlertModalVisibility( boolean isVisible ) {
        final boolean observedAlertModalVisibility = alertModal.isVisible();
        LOGGER.info( STR."Observed alert modal visibility was: \{observedAlertModalVisibility}" );
        assertThat( "The alert modal visibility is not ok.", observedAlertModalVisibility, is( equalTo( isVisible ) ) );
    }

    public void checkAlertModalTitle( String expectedTitle ) {
        final String observedAlertModalTitle = alertModal.getHeaderText();
        LOGGER.info( STR."Observed alert modal title was: \{observedAlertModalTitle }" );
        assertEquals( "Alert modal title is not ok.", expectedTitle, observedAlertModalTitle );
    }

    public void checkAlertModalMessage( String expectedMsgFileName ) {
        final String observedAlertModalMessage = normalizeLineEndings( alertModal.getContent() );
        LOGGER.info( STR."Observed alert modal title was: \{observedAlertModalMessage }" );
        assertEquals( "Alert modal content is not ok.",
                normalizeLineEndings(readFromFile( TEST_DATA_CLASSPATH + expectedMsgFileName ) ),
                observedAlertModalMessage );
    }

    public void clickButtonOnAlertModal( String buttonText ) {
        alertModal.clickOnButton( buttonText );
    }

    public void closeAlertModal() {
        alertModal.close();
    }
}
