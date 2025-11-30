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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.ge.onchron.verif.model.Label;
import com.ge.onchron.verif.pages.sections.labelPanel.SelectLabelPanel;

import static com.ge.onchron.verif.TestSystemParameters.LABEL_COLORS_FROM_LABEL_PANEL;
import static com.ge.onchron.verif.utils.ReplaceUtils.replaceStoredPlaceholderText;
import static net.serenitybdd.core.Serenity.hasASessionVariableCalled;
import static net.serenitybdd.core.Serenity.sessionVariableCalled;
import static net.serenitybdd.core.Serenity.setSessionVariable;

public class SelectLabelPanelSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger( SelectLabelPanelSteps.class );

    private SelectLabelPanel selectLabelPanel;


    public void checkSelectLabelPanelVisibility( boolean visible ) {
        final boolean selectLabelPanelVisibility = selectLabelPanel.isVisible();
        LOGGER.info( STR."Actual label pannel visibility was: \{selectLabelPanelVisibility}");
        assertThat( "The default label panel visibility is not ok.",
                selectLabelPanelVisibility, is( equalTo( visible ) ) );
    }

    public void saveColorOfLabels( List<String> labelTexts ) {
        labelTexts.forEach( this::saveColorOfLabel );
    }

    private void saveColorOfLabel( String labelText ) {
        labelText = replaceStoredPlaceholderText( labelText );
        String labelColor = selectLabelPanel.getLabelColor( labelText );
        LOGGER.info(STR."The label color is: \{labelColor}");
        Map<String, String> labelColorsFromLabelPanel = new HashMap<>();
        if ( hasASessionVariableCalled( LABEL_COLORS_FROM_LABEL_PANEL ) ) {
            Map<String, String> storedLabelColorsFromLabelPanel = sessionVariableCalled( LABEL_COLORS_FROM_LABEL_PANEL );
            labelColorsFromLabelPanel.putAll( storedLabelColorsFromLabelPanel );
        }
        labelColorsFromLabelPanel.put( labelText, labelColor );

        setSessionVariable( LABEL_COLORS_FROM_LABEL_PANEL ).to( labelColorsFromLabelPanel );
    }

    public void clickOnLabels( List<String> labelTexts ) {
        labelTexts.forEach( labelText -> {
            labelText = replaceStoredPlaceholderText( labelText );
            LOGGER.info( STR."Clicking on label: \{labelText}" );
            selectLabelPanel.clickOnLabel( labelText );
        } );
    }

    public void clickOnCreateNew() {
        selectLabelPanel.clickOnCreateNew();
    }

    public void checkLabelWithColorInLabelList( String labelName, String storedColorName ) {
        labelName = replaceStoredPlaceholderText( labelName );
        Label foundLabel = selectLabelPanel.getLabel( labelName );
        String storedColor = sessionVariableCalled( storedColorName );
        LOGGER.info( STR."Observed label was: \{foundLabel}");
        assertEquals( STR."Label color is not ok: \{labelName}", storedColor, foundLabel.getColor() );
    }

    public void checkLabelsInLabelList( Boolean isAvailable, List<String> expectedLabelNames ) {
        List<String> allAvailableLabelNames = selectLabelPanel.getAllLabelNames();
        LOGGER.info( STR."Observed labels in labels list, were: \{allAvailableLabelNames}");
        expectedLabelNames.forEach( expectedLabelName -> {
            expectedLabelName = replaceStoredPlaceholderText( expectedLabelName );
            assertThat( String.format( "Label '%s' availability on select label panel is not ok.", expectedLabelName ),
                    allAvailableLabelNames.contains( expectedLabelName ), is( isAvailable ) );
        } );
    }

    public void searchLabel( String searchText ) {
        selectLabelPanel.typeInSearchField( searchText );
    }

    public void checkNotFoundMessage( String expectedNotFoundText ) {
        String observedText = selectLabelPanel.getNotFoundText();
        LOGGER.info( STR."Observed text message was: \{observedText}");
        assertNotNull( "Not found message was not available on label panel.", observedText );
        assertEquals( "Not found message is not ok on label panel.", expectedNotFoundText, observedText );
    }

}
