package com.ge.onchron.verif.whatsteps.tabs.radioligand;

import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.howsteps.radioligand.RadioligandTxSteps;
import com.ge.onchron.verif.howsteps.trials.CommonTrialsViewSteps;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

public class Radioligand {
    private static final Logger LOGGER = LoggerFactory.getLogger(Radioligand.class);
    @Steps
    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();
    @Steps
    private RadioligandTxSteps radioligandTxSteps;
    @Steps
    private CommonTrialsViewSteps commonTrialsViewSteps;

    @Then("the patient's treatment basic data is available for $treatmentID and check the following fields and data:$data")
    public void checkPatientTreatmentDetails(String treatmentID, ExamplesTable expectedData) {
        String dataDetails = expectedData.getRows()
                .stream()
                .map(m -> String.join(", ", m.values())).collect(Collectors.joining("\n"));
        testDefinitionSteps.addStep(
                STR."Check patient's treatmentID:\{treatmentID} was displayed with the following details:\n\{dataDetails}");
        radioligandTxSteps.checkBasicInformationInGridViewForTreatmentID(expectedData.getRows());
        testDefinitionSteps.logEvidence(
                STR."Patient's treatmentID:\{treatmentID} was displayed with the following details:\n\{expectedData.getRows()}",
                STR."Patient's treatmentID:\{treatmentID} was displayed with the following details:\n\{expectedData.getRows()}", true);
    }


    @Then("the Radioligand view $isDisplayed displayed")
    public void checkRadioligandViewIsDisplayed(Boolean isDisplayed) {
        testDefinitionSteps.addStep(STR."Check Radioligand view is \{isDisplayed ? "" : "not"} displayed");
        radioligandTxSteps.checkRadioligandViewVisibility(isDisplayed);
        testDefinitionSteps.logEvidence(STR."the Radioligand view \{isDisplayed ? "" : "not"} displayed",
                "Visibility of the Radioligand section is ok", true);
    }

    @When("the user clicks the 'X' button to close the 'Radioligand' tab")
    public void clickXCloseRadioligandTab() {
        testDefinitionSteps.addStep("Check the Radioligand tab closed by X button");
        radioligandTxSteps.clickOnXToCloseRadioligandTab();
        testDefinitionSteps.logEvidence(
                "The Radioligand tab was successfully closed by clicking X button",
                "The Radioligand tab was successfully closed by clicking X button", true);
    }

    @When("the user selects the following configuration id: $config_id from the list of treatments")
    public void focusOnTreatmentByID(String configId) {
        testDefinitionSteps.addStep(STR."Check config ID :\{configId} is selected");
        radioligandTxSteps.clickOnTreatmentByID(configId);
        testDefinitionSteps.logEvidence(
                STR."The config ID:\{configId} is selected and its name is vievable on treatment content view",
                STR."The config ID:\{configId} is selected and its name is vievable on treatment content view", true);
    }

    @Given("the user focus on list of $sizeOfTreatments treatments")
    @When("the user focus on list of $sizeOfTreatments treatments")
    @Then("the user focus on list of $sizeOfTreatments treatments")
    public void focusOnListOfTreatments(int sizeOfTreatments) {
        testDefinitionSteps.addStep("Check for the list of treatments");
        radioligandTxSteps.checkForListOfTreatments(sizeOfTreatments);
        testDefinitionSteps.logEvidence(
                "The list of treatments is displayed as expected",
                "The list of treatments is displayed", true);
    }

    @Then("each treatment has its related matching confidence score:$treatmentConfidenceScore")
    public void checkConfidenceScoreForTreatmentID(ExamplesTable treatmentsScore) {
        String treatmentScoreValue = treatmentsScore.getRows()
                .stream().map(m -> String.join(", ", m.values())).collect(Collectors.joining("\n"));
        testDefinitionSteps.addStep(STR."Check each treatment has its related matching confidence score as:\{treatmentScoreValue}");
        radioligandTxSteps.checkConfidenceScoreForTreatments(treatmentsScore);
        testDefinitionSteps.logEvidence(
                STR."Each treatment has its related matching confidence score as:\n\{treatmentScoreValue}",
                STR."Each treatment has its related matching confidence score as:\n\{treatmentScoreValue}", true);
    }
}
