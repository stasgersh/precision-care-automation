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
package com.ge.onchron.verif.whatsteps.popups.labels;

import com.ge.onchron.verif.howsteps.CreateLabelPanelSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import net.thucydides.core.annotations.Steps;

public class CreateLabelPanel {

    private TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Steps
    private CreateLabelPanelSteps createLabelPanelSteps;

    @When( "the user types the following text into the 'Label name' field: \"$labelName\"" )
    public void typeTextIntoLabelNameField( String labelName ) {
        testDefinitionSteps.addStep(STR."Type the following text into the 'Label name' field: \{labelName}");
        createLabelPanelSteps.typeTextIntoLabelNameField( labelName );
        testDefinitionSteps.logEvidence(
            "The text was typed successfully into the 'Label name' field",
            STR."\{labelName} typed into the 'Label name' field",
            true);
    }

    @When( "the user removes the last character of the label name in the 'Label name' field" )
    public void removeLastCharOfLabelName() {
      testDefinitionSteps.addStep("Remove the last character of the label name in the 'Label name' field");
      createLabelPanelSteps.removeLastCharOfLabelName();
      testDefinitionSteps.logEvidence(
          "The last character of the label name in the 'Label name' field was successfully removed",
          "The last character of the label name in the 'Label name' field was successfully removed",
          true);
    }

    @When( "the user adds the following text to the label name in the 'Label name' field: \"$text\"" )
    public void continueLabelNameTyping( String text ) {
      testDefinitionSteps.addStep(STR."Add the following text to the label name in the 'Label name' field: \{text}");
      createLabelPanelSteps.continueLabelNameTyping( text );
      testDefinitionSteps.logEvidence(
          STR."The \{text} was successfully added into the 'Label name' field",
          STR."\{text} added to the label name in the 'Label name' field",
          true);
    }

    @When( "the user clicks on the $ordinalNum{st|nd|rd|th} color on the 'Color' palette" )
    public void selectColorFromPalette( int ordinalNum ) {
      testDefinitionSteps.addStep(STR."Click on the \{ordinalNum} color on the 'Color' palette");
      createLabelPanelSteps.selectColorFromPalette( ordinalNum );
      testDefinitionSteps.logEvidence(
          "The color on the 'Color' palette was successfully clicked",
          STR."\{ordinalNum} color was clicked on the 'Color' palette",
          true);
    }

    @Then( "the $ordinalNum{st|nd|rd|th} color is selected on the 'Color' palette" )
    public void checkSelectedColorOnPalette( int ordinalNum ) {
      testDefinitionSteps.addStep(STR."Check for selected color \{ordinalNum} on the 'Color' palette");
      createLabelPanelSteps.checkSelectedColorNumOnPalette( ordinalNum );
      testDefinitionSteps.logEvidence(
          STR."The selected color number on the 'Color' palette is\{ordinalNum}",
          STR."\{ordinalNum} color was selected on the 'Color' palette",
          true);
    }

    @When( "the user saves the color of the $ordinalNum{st|nd|rd|th} item on the 'Color' palette as \"$storeName\"" )
    public void saveColorFromPaletteAs( int ordinalNum, String storeName ) {
      testDefinitionSteps.addStep(STR."Save \{ordinalNum} color on the 'Color' palette as \{storeName}");
      createLabelPanelSteps.saveColorFromPaletteAs( ordinalNum, storeName );
      testDefinitionSteps.logEvidence(
          "The color of the item was stored successfully on the 'Color' palette",
          STR."Color of \{ordinalNum} was stored on the 'Color' palette",
          true);
    }

    @When( "the user clicks on the 'Create' button on the create new label panel" )
    public void clickOnCreateButton() {
      testDefinitionSteps.addStep("Click on the 'Create' button on the create new label panel");
      createLabelPanelSteps.clickOnCreateButton();
      testDefinitionSteps.logEvidence(
          "The 'Create' button was successfully clicked",
          "'Create' button was clicked",
          true);
    }

    @Then( "the create new label panel $isOpened opened" )
    @Alias( "the create new label panel $isOpened displayed" )
    public void checkCreateNewLabelPanelVisibility( Boolean isOpened ) {
      testDefinitionSteps.addStep(STR."Check the create new label panel is \{isOpened? "" : "not"} displayed");
      createLabelPanelSteps.checkCreateNewLabelPanelVisibility( isOpened );
      testDefinitionSteps.logEvidence(
          STR."The new label panel is \{isOpened? "" : "not"} displayed as expected",
          STR."Label panel was \{isOpened? "" : "not"} displayed",
          true);
    }

    @Then( "the following error is displayed on the create label panel: \"$errorMessage\"" )
    public void checkCreateLabelErrorMessage( String errorMessage ) {
      testDefinitionSteps.addStep(STR."Check the following error is displayed on the create label panel: \"\{errorMessage}\"");
      createLabelPanelSteps.checkCreateLabelErrorMessage( errorMessage );
      testDefinitionSteps.logEvidence(
          STR."The expected error was displayed on the create label panel:\"\{errorMessage}\"",
          STR."\"\{errorMessage}\" is displayed on the create label panel",
          true);
    }

    @Then( "the 'Create' button $isEnabled enabled on the create new label panel" )
    public void checkCreateButtonState( Boolean isEnabled ) {
      testDefinitionSteps.addStep(STR."Check that 'Create' button is \{isEnabled} on the create new label panel");
      createLabelPanelSteps.checkCreateButtonState( isEnabled );
      testDefinitionSteps.logEvidence(
          STR."The button is \{isEnabled} as expected on the create new label panel",
          STR."The button is \{isEnabled? "" : "not enabled"}",
          true);
    }

}
