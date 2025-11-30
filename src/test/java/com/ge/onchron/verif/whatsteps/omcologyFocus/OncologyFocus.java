package com.ge.onchron.verif.whatsteps.omcologyFocus;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.ge.onchron.verif.howsteps.OncologyFocusSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.model.StringList;
import net.thucydides.core.annotations.Steps;

public class OncologyFocus {
    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();
    @Steps
    private OncologyFocusSteps oncologyFocusSteps;
    @When("the user turns Oncology focus trigger $state")
    public void oncologyFocusTrigger(String state) {
        testDefinitionSteps.addStep(STR."Turns \{state} Oncology focus");
        oncologyFocusSteps.turnOnOrOffOncoToggle();
        testDefinitionSteps.logEvidence(STR."the user turns Oncology focus trigger \{state}",
                STR."Oncology focus turns \{state}", true);
    }

    @Then("the following popup is displayed with title '$title' and options: $options")
    public void oncologyFocusPopUpDisplayed(String title, StringList options) {
        testDefinitionSteps.addStep("Check oncology popup visibility");
        oncologyFocusSteps.checkOncoPopUpVisibility(true);
        oncologyFocusSteps.checkOncoPopUpTitle(title);
        oncologyFocusSteps.checkOncologyFocusPopupOptions(options);
        testDefinitionSteps.logEvidence(STR."The popup is displayed with title \{title} and options \{options}",
                "Pop is displayed (see previous logs)", true);
    }


    @Then("the following popup content is displayed: $options")
    public void oncologyFocusPopUpContentDisplayed(StringList content) {
        testDefinitionSteps.addStep("Check oncology popup content visibility");
        oncologyFocusSteps.checkOncoPopUpVisibility(true);
        oncologyFocusSteps.checkOncologyFocusPopupContent(content);
        testDefinitionSteps.logEvidence(STR."The popup content is displayed with content \{content}",
                "Popup content is displayed (see previous logs)", true);
    }

    @When("the user selects \"$checkbox\" checkbox")
    public void oncologyFocusCheckbox(String checkbox) {
        testDefinitionSteps.addStep(STR."Select oncology \{checkbox} checkbox");
        oncologyFocusSteps.clickOnBtnInPopup(checkbox);
        testDefinitionSteps.logEvidence(STR."The user selects \{checkbox} checkbox",
                STR."Select \{checkbox} checkbox", true);
    }
    @When("the user presses \"$button\" button")
    public void oncologyFocusButton(String button) {
        testDefinitionSteps.addStep(STR."Select oncology \{button} button");
        oncologyFocusSteps.clickOnBtnInPopup(button);
        testDefinitionSteps.logEvidence(STR."The user selects \{button} button",
                STR."Select \{button} button", true);
    }
    @Then("the popup is closed")
    public void oncologyFocusClose() {
        testDefinitionSteps.addStep("Check oncology popup visibility");
        oncologyFocusSteps.checkOncoPopUpVisibility(false);
        testDefinitionSteps.logEvidence("The popup is closed",
                "Popup is closed", true);
    }
    @Then("the Oncology focus trigger is turned $state")
    public void oncologyFocusTriggerIsTurned(String state) {
        testDefinitionSteps.addStep("Check Oncology focus trigger state");
        oncologyFocusSteps.oncoFocusBtnState(state.equals("on"));
        testDefinitionSteps.logEvidence(STR."The Oncology focus trigger is turned \{state}",
                STR."Oncology focus trigger is turned \{state}", true );
    }
    @When("the user hovers over the Oncology focus info icon")
    public void oncologyFocusInfoIcon() {
        testDefinitionSteps.addStep("Hover over the Oncology focus info icon");
        oncologyFocusSteps.moveToToolTip();
        testDefinitionSteps.logEvidence("The user hovers over the Oncology focus info icon",
                "Oncology focus info presented", true);
    }
    @Then("the tooltip is displayed above the icon with the text: $text")
    public void oncologyFocusTooltip(String text) {
        testDefinitionSteps.addStep("Check presented tooltip text");
        oncologyFocusSteps.oncoFocusTooltipTxt(text);
        testDefinitionSteps.logEvidence(STR."The tooltip is displayed above the icon with the text: \{text}",
                STR."Displayed text above the tooltip is: \{text}", true);
    }

}
