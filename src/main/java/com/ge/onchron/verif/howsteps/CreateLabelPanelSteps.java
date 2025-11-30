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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import com.ge.onchron.verif.pages.sections.labelPanel.CreateLabelPanel;

import static com.ge.onchron.verif.utils.ReplaceUtils.replaceCustomParameters;
import static net.serenitybdd.core.Serenity.setSessionVariable;

public class CreateLabelPanelSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger( CreateLabelPanelSteps.class );

    private CreateLabelPanel createLabelPanel;


    public void checkCreateNewLabelPanelVisibility( boolean visible ) {
        final boolean observedVisibilityState = createLabelPanel.isVisible();
        LOGGER.info( STR."Observed create new label pannel visibility was: \{observedVisibilityState}" );
        assertThat( "The create new label panel visibility is not ok.", observedVisibilityState, is( equalTo( visible ) ) );
    }

    public void typeTextIntoLabelNameField( String labelName ) {
        String updatedLabelName = replaceCustomParameters( labelName );
        setSessionVariable( labelName ).to( updatedLabelName ); // in order to be able to use it in another step
        createLabelPanel.typeTextIntoLabelNameField( updatedLabelName );
    }

    public void removeLastCharOfLabelName() {
        createLabelPanel.removeLastCharOfLabelName();
    }

    public void continueLabelNameTyping( String text ) {
        createLabelPanel.continueLabelNameTyping( text );
    }

    public void selectColorFromPalette( int ordinalNum ) {
        createLabelPanel.selectColor( ordinalNum );
    }

    public void saveColorFromPaletteAs( int ordinalNum, String storeName ) {
        String color = createLabelPanel.getRgbColorFromPalette( ordinalNum );
        LOGGER.info( STR."Stored color (rgb): \{color}" );
        setSessionVariable( storeName ).to( color );
    }

    public void clickOnCreateButton() {
        createLabelPanel.clickOnCreateButton();
    }

    public void checkCreateLabelErrorMessage( String expectedErrorMessage ) {
        final String observedLabelPanelError = createLabelPanel.getLabelPanelError();
        LOGGER.info( STR."Observed error message was: \{observedLabelPanelError}" );
        assertEquals( "Error message on create label panel is not ok.", expectedErrorMessage, observedLabelPanelError );
    }

    public void checkSelectedColorNumOnPalette( int ordinalNum ) {
        List<Integer> ordinalNumsOfSelectedColors = createLabelPanel.getOrdinalNumsOfSelectedColorsFromPalette();
        LOGGER.info( STR."Observed colors are: \{ordinalNumsOfSelectedColors}" );
        assertEquals( STR."One color should be in selected state but these are selected: \{ordinalNumsOfSelectedColors}", 1, ordinalNumsOfSelectedColors.size() );
        assertEquals( "Selected color is not ok.", ordinalNum, ordinalNumsOfSelectedColors.get( 0 ).intValue() );
    }

    public void checkCreateButtonState( boolean isEnabled ) {
        final boolean observedCreateButtonState = createLabelPanel.isCreateButtonEnabled();
        LOGGER.info( STR."Observed create button state was:\{observedCreateButtonState }" );
        assertThat( "The 'Create' button state on create new label panel is not ok.", observedCreateButtonState, is( equalTo( isEnabled ) ) );
    }

}
