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

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.ge.onchron.verif.howsteps.SelectLabelPanelSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.model.StringList;
import net.thucydides.core.annotations.Steps;

public class SelectLabelPanel {

    private TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Steps
    private SelectLabelPanelSteps labelPanelSteps;

    @When( "the user clicks on the 'Create new' button on the label panel" )
    public void clickOnCreateNew() {
        testDefinitionSteps.addStep("Click on the 'Create new' button on the label panel");
        labelPanelSteps.clickOnCreateNew();
        testDefinitionSteps.logEvidence(
                "The 'Create new' button was clicked successfully",
                "'Create new' button was clicked",
                true);
    }

    @When( "the user saves the color of the following {label|labels} on the label panel: $labelTexts" )
    public void saveColorOfLabels( StringList labelTexts ) {
        testDefinitionSteps.addStep(STR."Save the color of \{labelTexts} label");
        labelPanelSteps.saveColorOfLabels( labelTexts.getList() );
        testDefinitionSteps.logEvidence(
                STR."The color of \{labelTexts} was succeffully saved",
                STR."The color of \{labelTexts} was saved",
                true);
    }

    @When( "the user clicks on the following {label|labels} on the label panel: $labelTexts" )
    public void clickOnLabels( StringList labelTexts ) {
        testDefinitionSteps.addStep(STR."Click on the \{labelTexts} label panel");
        labelPanelSteps.clickOnLabels( labelTexts.getList() );
        testDefinitionSteps.logEvidence(
                STR."The: \{labelTexts} label was successfully clicked",
                STR."\{labelTexts} label was clicked",
                true);
    }

    @When( "the user types the following text into the search field on select label panel: \"$searchText\"" )
    public void searchLabel( String searchText ) {
        testDefinitionSteps.addStep(STR."Type the text - \{searchText} into the search field on select label pane");
        labelPanelSteps.searchLabel( searchText );
        testDefinitionSteps.logEvidence(
                STR."The \{searchText} was typed successfully into search field",
                STR."The \{searchText} typed into search field",
                true);
    }

    @When( "the label selection panel $isOpened {opened|displayed}" )
    @Then( "the label selection panel $isOpened {opened|displayed}" )
    public void checkLabelPanelVisibility( Boolean isOpened ) {
        testDefinitionSteps.addStep("Check the label selection panel visibility");
        labelPanelSteps.checkSelectLabelPanelVisibility( isOpened );
        testDefinitionSteps.logEvidence(
                STR."The label selection visibility is: \{isOpened}",
                STR."Label panel visibility is: \{isOpened? "" : "not"} (check previous logs)",
                true);
    }

    @Then( "the following labels $isAvailable available in the label list: $labelTexts" )
    public void checkLabelsInLabelList( Boolean isAvailable, StringList labelTexts ) {
        testDefinitionSteps.addStep("Check labels in label list");
        labelPanelSteps.checkLabelsInLabelList( isAvailable, labelTexts.getList() );
        testDefinitionSteps.logEvidence(
                STR."The labels availability in the label list \{labelTexts}, should be \{isAvailable}",
                STR."The labels availability in the label list \{labelTexts}, were \{isAvailable? "" : "not"}",
                true);
    }

    @Then( "the \"$labelName\" label is available with \"$storedColorName\" color in the label list" )
    public void checkLabelWithColorInLabelList( String labelName, String storedColorName ) {
        testDefinitionSteps.addStep("Check that the label is available with color name in labels list");
        labelPanelSteps.checkLabelWithColorInLabelList( labelName, storedColorName );
        testDefinitionSteps.logEvidence(
                STR."The \{labelName} label was available with \{storedColorName} color in the label list",
                STR."The \{labelName} label was available with \{storedColorName} color in the label list (See previous logs)",
                true);
    }

    @Then( "not found message is displayed on the label panel: \"$notFoundText\"" )
    public void checkNotFoundMessage( String notFoundText ) {
        testDefinitionSteps.addStep("Check not found message is displayed on the label panel");
        labelPanelSteps.checkNotFoundMessage( notFoundText );
        testDefinitionSteps.logEvidence(
                STR."The not-found message \{notFoundText} was displayed on the label panel",
                STR."\{notFoundText} was displayed on the label panel",
                true);
    }

}
