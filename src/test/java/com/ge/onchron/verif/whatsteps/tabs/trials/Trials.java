package com.ge.onchron.verif.whatsteps.tabs.trials;


import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.howsteps.trials.CommonTrialsViewSteps;
import com.ge.onchron.verif.howsteps.trials.TrialSteps;
import com.ge.onchron.verif.model.StringList;
import com.ge.onchron.verif.model.clinicalConfiguration.trial.ClinicalCriteriaItem;
import com.ge.onchron.verif.model.clinicalConfiguration.CriteriaStatus;
import com.ge.onchron.verif.model.clinicalConfiguration.CriteriaType;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Trials {

    @Steps
    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();
    @Steps
    private TrialSteps trialSteps;
    @Steps
    private CommonTrialsViewSteps trialsViewSteps;


    @Then("the notification message:\"$notificationMsg\" about the trials calculation process $isDisplayed displayed on \"$page\" page and above the trials list")
    public void checkForTheClinicalTrialProcessingNotificationMessage(Boolean isDisplayed, String page, String notificationMsg) {
        testDefinitionSteps.addStep(STR."Check for the notification message on top \{page} page and above of trial/s, \{isDisplayed ? "" : "not"} displayed");
        trialSteps.checkForTheTrialsProcessingNotificationMessage(notificationMsg);
        testDefinitionSteps.logEvidence(STR."the notification message on top \{page} page and above of trial/s, \{isDisplayed ? "" : "not"} displayed",
                "the notification message on top of the trials list is ok", true);
    }

    @Given("the Trials view $isDisplayed displayed")
    @Then("the Trials view $isDisplayed displayed")
    public void checkTrialsViewIsDisplayed(Boolean isDisplayed) {
        testDefinitionSteps.addStep(STR."Check Trials view is \{isDisplayed ? "" : "not"} displayed");
        trialsViewSteps.checkTrialsViewVisibility(isDisplayed);
        testDefinitionSteps.logEvidence(STR."the Trials view \{isDisplayed ? "" : "not"} displayed",
                "Visibility of the Trials section is ok", true);
    }

    @Given("the following patient was uploaded to PDS with $numOfTrials various trials and $numOfCriteriaPerTrial various criteria for one of the trials:$patientJson")
    public void uploadPatientToPDSWithDiffNumOfCriteriaAndTrial(int numOfTrials, int $numOfCriteriaPerTrial, String patientJson) {
        testDefinitionSteps.addStep(STR."Upload patient with \{numOfTrials} different trials");
        trialsViewSteps.performActionNotYetImplemented();
        testDefinitionSteps.logEvidence(
                STR."Patient - \{patientJson}, was uploaded successfully", STR."Patient - \{patientJson} was uploaded", true);
    }

    @When("the user clicks on the filter icon in the \'$columnName\' column")
    public void clickOnFilter(String columnName) {
        testDefinitionSteps.addStep(STR."Check the user clicks on the filter icon in the \'\{columnName}\' column");
        trialSteps.clickOnCriteriaFilterForColumn(columnName);
        testDefinitionSteps.logEvidence(
                STR."The user clicks on the filter icon in the \'\{columnName}\' column successfully",
                STR."\'\{columnName}\' column was clicked as expected",
                true);
    }

    @When("the user clicks on criteria grid view to hide the selection list")
    public void clickOnFilterToHideTheSelectionList() {
        testDefinitionSteps.addStep(STR."The user clicks on criteria grid view to hide the selection list");
        trialSteps.clickOnCriteriaGridToHideThePopupSelectionList();
        testDefinitionSteps.logEvidence(
                STR."The user clicks on criteria grid view to hide the selection list",
                STR."The user clicks on criteria grid view to hide the selection list",
                false);
    }

    @Then("the user click on \"Clear all\" button on \"Filter By\" frame")
    @When("the user click on \"Clear all\" button on \"Filter By\" frame")
    public void clickClearAllButton() {
        testDefinitionSteps.addStep("Check the user clicks \"Clear all\" button on \"Filter By\" frame");
        trialSteps.clickOnFilterClearAllButton();
        testDefinitionSteps.logEvidence(
                "\"Clear all\" button on \"Filter By\" frame was successfully clicked",
                "\"Clear all\" button on \"Filter By\" frame was successfully clicked", true);
    }

    @When("the user clicks the X button to close the Trials tab")
    public void clickXCloseTrialButton() {
        testDefinitionSteps.addStep("Check the Trial tab closed by X button");
        trialSteps.clickOnTrialsTabXCloseButton();
        testDefinitionSteps.logEvidence(
                "The Trials tab was successfully closed by clicking X button",
                "The Trials tab was successfully closed by clicking X button", true);
    }

    //SRS
    @When("the user clicks X on each <status> on \"Filter By\" frame")
    public void clickXToRemoveFilterBy(@Named("status") String status) {
        testDefinitionSteps.addStep("Check the user clicks X on each <status> on \"Filter By\" frame");
        trialsViewSteps.performActionNotYetImplemented();
        testDefinitionSteps.logEvidence(
                STR."The \{status} doesn't appears on \"Filter By\" frame",
                STR."The \{status} doesn't appears on \"Filter By\" frame", true);
    }

    @When("the user checks for trial/s display in trials list")
    @Then("the trial/s displayed in trials list")
    public void isAnyTrialDisplaysInTrialsListFocusOnListOfTrials() {
        testDefinitionSteps.addStep("Check for the list of trials");
        trialSteps.checkForAnyTrialDisplayInListOfTrials();
        testDefinitionSteps.logEvidence(
                "The list of trials is displayed as expected",
                "The list of trials is displayed", true);
    }

    @Given("the user focus on list of $sizeOfTrials trials")
    @When("the user focus on list of $sizeOfTrials trials")
    @Then("the user focus on list of $sizeOfTrials trials")
    public void focusOnListOfTrials(int sizeOfTrials) {
        testDefinitionSteps.addStep("Check for the list of trials");
        trialSteps.checkForListOfTrials(sizeOfTrials);
        testDefinitionSteps.logEvidence(
                "The list of trials is displayed as expected",
                "The list of trials is displayed", true);
    }

    @When("the user expands criteria name starts with \"$criteriaName\", to check the inner conditions")
    public void clickOnExpandButtonWithTitleOf(String criteriaName) {
        testDefinitionSteps.addStep(STR."Check the user expands criteria name starts with: \"\{criteriaName}\"");
        trialSteps.clickOnCriteriaExpandCollapseButton(criteriaName);
        testDefinitionSteps.logEvidence(
                STR."The criteria name starts with: \"\{criteriaName}\" was expanded successfully",
                STR."The criteria name starts with: \"\{criteriaName}\" was expanded", true);
    }

    @When("the user clicks collapse expended criteria name starts with:$criteriaName")
    public void clickOnExpandButtonToCollapseWithTitleOf(String criteriaName) {
        testDefinitionSteps.addStep("Check the criteria name starts with: \"$criteriaName\", is collapsed");
        trialSteps.clickCollapseArrowButtonOnCriteria(criteriaName);
        testDefinitionSteps.logEvidence(
                "The criteria name starts with: \"$criteriaName\" was collapsed successfully",
                "The criteria name starts with: \"$criteriaName\" was collapsed", true);
    }

    @When("the user selects the following trial id:$config_id from the list of trials")
    public void focusOnTrialByID(String configId) {
        testDefinitionSteps.addStep(STR."Check config ID :\{configId} is selected");
        trialSteps.clickOnTrialFromListByTrialID(configId);
        testDefinitionSteps.logEvidence(
                STR."The config ID:\{configId} is selected and its name is vievable on trial content view",
                STR."The config ID:\{configId} is selected and its name is vievable on trial content view", true);
    }

    @Then("the AI indication isDisplayed $isDisplayed, next to trial id:$configId")
    public void checkForAiIndicationOnTrialCardWithID(String configId, boolean isDisplayed) {
        testDefinitionSteps.addStep(STR."Check for AI indication on trial card with ID:\{configId}");
        trialSteps.checkAIIndicationInTrialsTabNextToMatchingStatusForTrialID(configId, isDisplayed);
        testDefinitionSteps.logEvidence(
                STR."The AI indication on trial card with ID:\{configId} was displayed correctly",
                STR."The AI indication on trial card with ID:\{configId} was displayed", true);
    }

    @When("the user hover the eligibility value(s) of \"$criteriaDescription\" eligibility criteria")
    public void hoverTheEligibilityValueByCriteriaDescription(String criteriaDescription) {
        testDefinitionSteps.addStep(STR."The user hover the eligibility value for criteria with title of: \{criteriaDescription}");
        trialSteps.hoverOverEligibilityValuesByCriteriaDescription(criteriaDescription);
        testDefinitionSteps.logEvidence(
                STR."The user hovered the eligibility value for criteria with title of: \{criteriaDescription} successfully",
                STR."The user hovered the eligibility value for criteria with title of: \{criteriaDescription} successfully", true);
    }

    @Then("the trial:$configID has the following expected AI data in Grid Details of main section:$expectedAiData")
    public void checkForAiIndicationOnGridMainSection(String configID, ExamplesTable expectedAiData) {
        String expectedData = expectedAiData.getRows()
                .stream()
                .map(m -> String.join(", ", m.values()))
                .collect(Collectors.joining("\n"));
        testDefinitionSteps.addStep(STR."Check for AI indicators on trial card with ID\{configID} as following:\{expectedData}");
        trialSteps.checkAIIndicationsInGridViewForTrialID(expectedAiData);
        testDefinitionSteps.logEvidence(
                STR."The AI indications in main grid section view for configID:\{configID} was displayed as following:\n\{expectedData}",
                STR."The observed AI indications in main grid section view for configID:\{configID} were observed as following:\n\{expectedData}", true);
    }

    //SRS
    @When("set screen's resolution to (1920 x 1200) & text size (100%)")
    public void checkAllCriteriaFitsThePage() {
        testDefinitionSteps.addStep("Check all criteria fits the page and displayed correctly");
        trialsViewSteps.performActionNotYetImplemented();
        testDefinitionSteps.logEvidence(
                STR."The all criteria fits the page and displayed correctly",
                STR."The all criteria fits the page and displayed correctly", true);
    }

    @When("scrolls down to the bottom of the list and last configID:$configID is displayed")
    public void scrollDownToTheBottomOfThePage(final String trialId) {
        testDefinitionSteps.addStep("Scroll down to the bottom of the list");
        trialSteps.scrollTrialsToButtonAndCheckLastTrialDisplay(trialId);
        testDefinitionSteps.logEvidence(
                "The bottom of the list was reached",
                "The bottom of the list reached", true);
    }

    //SRS
    @When("the user submits PUT request to update the name of existing trial with Trial ID:$configID to:$trialName")
    public void submitPutRequestForNameUpdate(String trialID, String trialName) {
        testDefinitionSteps.addStep(STR."Send update request for configId:\{trialID}, to change the trial name to:\{trialName}");
        trialsViewSteps.performActionNotYetImplemented();
        testDefinitionSteps.logEvidence(
                STR."The update request for configId:\{trialID}, to change the trial name to:\{trialName}",
                STR."The response status was successful for update requests(see logs above for more info)", true);
    }

    @When("the user selects each time only one <status> from the filter's list")
    public void selectFilterStatusAs(@Named("status") String status) {
        testDefinitionSteps.addStep(STR."Select \"\{status}\" from the filter's list");
        trialSteps.clickOnStatusFilterCheckbox(status);
        testDefinitionSteps.logEvidence(
                STR."\"\{status}\" status was slected from the filter's list successfully",
                STR."\"\{status}\" status was slected from the filter's list", true);
    }

    @When("the user selects $status status from the filter's list")
    public void selectFilterByStatus(String status) {
        testDefinitionSteps.addStep(STR."Select \"\{status}\" from the filter's list");
        trialSteps.clickOnStatusFilterCheckbox(status);
        testDefinitionSteps.logEvidence(
                STR."\"\{status}\" status was slected from the filter's list successfully",
                STR."\"\{status}\" status was slected from the filter's list", true);
    }

    @When("the user selects each time only one <type> from the filter's list")
    public void selectFilterTypeAs(@Named("type") String type) {
        testDefinitionSteps.addStep(STR."Select \"\{type}\" from the filter's list");
        trialSteps.clickOnStatusFilterCheckbox(type);
        testDefinitionSteps.logEvidence(
                STR."\"\{type}\" type was slected from the filter's list successfully",
                STR."\"\{type}\" type was slected from the filter's list", true);
    }

    //SRS
    @When("the user selects all filters at once the following filters:$filters")
    public void selectAllFilters(StringList filters) {
        testDefinitionSteps.addStep(STR."Select all avilable filters of the trial from the filter's list. The filters that needed to be selected, are:\n\{filters}");
        filters.getList().stream()
                .forEach(filter -> trialSteps.clickOnStatusFilterCheckbox(filter));
        testDefinitionSteps.logEvidence(
                "All filters were selected successfully",
                "All filters were selected successfully", true);
    }

    //SRS
    @Then("the trial name begins with $trialStringName and ends with three dots")
    public void trialHasInformationLabels(String trialStringName) {
        testDefinitionSteps.addStep(
                STR."Check the trial name begins with \{trialStringName} and ends with three dots");
        trialsViewSteps.performActionNotYetImplemented();
        testDefinitionSteps.logEvidence(
                STR."Check the trial name begins with \{trialStringName} and ends with three dots",
                STR."The trial name begins with \{trialStringName} and ends with three dots", true);
    }

    //TODO: Use specific object + its converter (e.g: List<TrialCard>)
    @Then("each trial has the following information:$information")
    public void trialHasInformationLabels(ExamplesTable trialsSectionFields) {
        String trialsSectionFieldsValue = trialsSectionFields.getRows()
                .stream().map(m -> String.join(", ", m.values()))
                .collect(Collectors.joining("\n"));
        testDefinitionSteps.addStep(STR."Check each trial has the following information:\{trialsSectionFieldsValue}");
        trialSteps.checkForInformationOfEachExistenceTrials();
        testDefinitionSteps.logEvidence(
                STR."Check each trial has the following information:\n\{trialsSectionFieldsValue}",
                STR."Each trial has the following information:\n\{trialsSectionFieldsValue}", true);
    }

    @Then("the user verify that \"Filter By\" frame contains only selected <status>")
    public void verifyForStatusOnFilterByFrame(@Named("status") String status) {
        testDefinitionSteps.addStep(STR."Check the \"Filter By\" frame contains only \{status} status");
        trialSteps.checkFilteredByLabelTextExists(status);
        testDefinitionSteps.logEvidence(
                STR."The \"Filter By\" frame contains only \{status} status",
                STR."The \"Filter By\" frame contains only \{status} status", true);
    }

    @Then("the user verify that \"Filter By\" frame contains only selected filters:$filters")
    public void verifyForStatusOnFilterByFrame(StringList filters) {
        testDefinitionSteps.addStep(STR."Check the \"Filter By\" frame contains only selected \{filters} status");
        filters.getList().stream()
                .forEach(status -> trialSteps.checkFilteredByLabelTextExists(status));
        testDefinitionSteps.logEvidence(
                STR."The \"Filter By\" frame contains only the expected filters\n\{filters}",
                STR."The \"Filter By\" frame contains only the expected filters\n\{filters}", true);
    }

    @Then("the user verify that \"Filter By\" frame contains only selected <type>")
    public void verifyForTypeOnFilterByFrame(String type) {
        testDefinitionSteps.addStep(STR."Check the \"Filter By\" frame contains only \{type} type");
        trialSteps.checkFilteredByLabelTextExists(type);
        testDefinitionSteps.logEvidence(
                STR."The \"Filter By\" frame contains only \{type} type",
                STR."The \"Filter By\" frame contains only \{type} type", true);

    }

    @Then("the \"Filter By\" frame contains only selected \"$type\"")
    public void verifyTypeOnFilterBy(String type) {
        testDefinitionSteps.addStep(STR."Check that the \"Filter By\" frame contains only \{type} type");
        trialSteps.checkFilteredByLabelTextExists(type);
        testDefinitionSteps.logEvidence(
                STR."The \"Filter By\" frame contains only \{type} type",
                STR."The \"Filter By\" frame contains only \{type} type", true);

    }

    @Then("the criteria view displays criteria with the following statuses:$status")
    public void verifyCriteriaViewDisplaysOnlyThisStatus(StringList statuses) {
        testDefinitionSteps.addStep(STR."Check the criteria view displays only \{statuses} status");
        statuses.getList()
                .forEach(status -> trialSteps.checkIfOnlyRelevantCriteriaStatusDisplayed(status));
        testDefinitionSteps.logEvidence(
                STR."The criteria view displays only \{statuses} status",
                STR."The criteria view displays only \{statuses} status", true);
    }

    @Then("the criteria view displays criteria with filtered type - <type> only")
    public void verifyCriteriaViewDisplaysOnlyThisType(@Named("type") String type) {
        testDefinitionSteps.addStep(STR."Check the criteria view displays only \{type} type");
        trialSteps.checkIfOnlyRelevantCriteriaTypeDisplayed(type);
        testDefinitionSteps.logEvidence(
                STR."The criteria view displays only \{type} type",
                STR."The criteria view displays only \{type} type", true);
    }

    @Then("the criteria view displays criteria with filtered status - <status> only")
    public void verifyCriteriaViewDisplaysOnlyThisStatus(@Named("status") String status) {
        testDefinitionSteps.addStep(STR."Check the criteria view displays only \{status} status");
        trialSteps.checkIfOnlyRelevantCriteriaStatusDisplayed(status);
        testDefinitionSteps.logEvidence(
                STR."The criteria view displays only \{status} status",
                STR."The criteria view displays only \{status} status", true);
    }

    //SRS
    @Then("the number of trials is $number")
    public void checkNumOfTrials(final int number) {
        testDefinitionSteps.addStep(
                STR."Check the number of trials is: \{number}");
        trialsViewSteps.performActionNotYetImplemented();
        testDefinitionSteps.logEvidence(
                STR."The number of trials is: \{number}",
                STR."The number of trials is: \{number}", true);
    }

    @Then("the criteria list is not empty and displayed on view for trial id:$trialId")
    public void checkAllCriteriaDisplayedForTrial(String trialId) {
        testDefinitionSteps.addStep(
                STR."Check all criteria list for trial id:\"\{trialId}\", displayed on view and not empty");
        trialSteps.checkCriteriaListNotEmptyForTrial();
        testDefinitionSteps.logEvidence(
                STR."Criteria for trial id:\"\{trialId}\", displayed on view and was not empty",
                STR."Criteria for trial id:\"\{trialId}\", displayed on view and was not empty", true);
    }

    @Then("the criteria list view contains the following columns:$columnsNames")
    public void checkForColumnsDisplay(final List<String> columnNames) {
        testDefinitionSteps.addStep(
                STR."Check for the dipslay of these columns on critera view:\n\{columnNames}");
        trialSteps.checkCriteriaColumnDisplayNames(columnNames);
        testDefinitionSteps.logEvidence(
                STR."The next columns on critera view:\n\{columnNames} are displayed",
                STR."The next columns on critera view:\n\{columnNames} are displayed", true);
    }

    @Then("the criteria list view contains the following values:$expectedCriteriaRowData")
    public void checkClinicalCriteriaItemsInGridView(ExamplesTable expectedCriteriaRowData) {

        testDefinitionSteps.addStep("Check the each criteria row in grid view displays all information and proper order");

        List<ClinicalCriteriaItem> expectedClinicalCriteriaItems = expectedCriteriaRowData.getRows()
                .stream()
                .map(criteriaItem -> ClinicalCriteriaItem.builder()
                        .criteriaDescription(criteriaItem.get("Eligibility_criteria"))
                        .criteriaStatus(CriteriaStatus.fromString(criteriaItem.get("Status")))
                        .criteriaType(CriteriaType.fromString(criteriaItem.get("Type")))
                        .eligibilityValue(criteriaItem.get("Eligibility_values"))
                        .build())
                .collect(toList());
        trialSteps.checkClinicalCriteriasInGridView(expectedClinicalCriteriaItems);
        testDefinitionSteps.logEvidence("Each criteria row in grid view displays all information and proper order",
                "Each criteria row in grid view displays all information and proper order", true);
    }

    @Then("the list of criteria for the \"$columnName\" column was sorted in {ascending|descending} order:$sortedList")
    public void checkSortingOrderOfTheValues(final String columnName, final ExamplesTable sortedValues) {
        testDefinitionSteps.addStep("the list of criteria for the \"$columnName\" column was sorted in {ascending|descending} order");
        List<String> columnOrderedValues = sortedValues.getRows()
                .stream()
                .map(item -> item.get("column_ordered_values")).toList();
        trialSteps.checkSortOrderInCriteriaTable(columnName, columnOrderedValues);
        testDefinitionSteps.logEvidence(
                STR."Criteria list for column '\{columnName}' is sorted in {ascending|descending} order as expected",
                STR."Criteria list for column '\{columnName}' is sorted in {ascending|descending} order as expected",
                true
        );
}

    //TODO: Use specific object + its converter (e.g: ClinicalTrialsPageSections)
    @Then("each trial has its related matching confidence score:$trialConfidenceScore")
    public void checkConfidenceScoreForTrialID(ExamplesTable trialListScore) {
        String trialListScoreValue = trialListScore.getRows()
                .stream().map(m -> String.join(", ", m.values())).collect(Collectors.joining("\n"));
        testDefinitionSteps.addStep(STR."Check each trial has its related matching confidence score as:\{trialListScoreValue}");
        trialSteps.checkConfidenceScoreForTrials(trialListScore);
        testDefinitionSteps.logEvidence(
                STR."Each trial has its related matching confidence score as:\n\{trialListScoreValue}",
                STR."Each trial has its related matching confidence score as:\n\{trialListScoreValue}", true);
    }

    //TODO: Use specific object + its converter
    @Then("the patient's criteria basic data is available for $configID and check the following fields and data:$data")
    public void checkPatientTrialDetails(String configID, ExamplesTable expectedData) {
        String dataDetails = expectedData.getRows()
                .stream()
                .map(m -> String.join(", ", m.values())).collect(Collectors.joining("\n"));
        testDefinitionSteps.addStep(
                STR."Check patient's configID:\{configID} was displayed with the following details:\n\{dataDetails}");
        trialSteps.checkBasicInformationInGridViewForTrialID(expectedData.getRows());
        testDefinitionSteps.logEvidence(
                STR."Patient's configID:\{configID} was displayed with the following details:\n\{expectedData.getRows()}",
                STR."Patient's configID:\{configID} was displayed with the following details:\n\{expectedData.getRows()}", true);
    }

    //SRS
    @Then("check all $numOfCriteria criteria displayed within the page and is scrollable Up/Down")
    public void checkConfidenceScoreForTrialID(int numOfCriteria) {
        testDefinitionSteps.addStep(
                STR."Check all \{numOfCriteria} criteria displayed within the page and is scrollable up/down");
        trialsViewSteps.performActionNotYetImplemented();
        testDefinitionSteps.logEvidence(
                STR."All \{numOfCriteria} criteria were displayed within the page and is scrollable up/down",
                STR."All \{numOfCriteria} criteria were displayed within the page and is scrollable up/down", true);
    }

    @Then("the \'$column\' column's pop-up list view contains the following values:$values")
    public void checkStatusValuesInPopUp(String column, StringList values) {
        testDefinitionSteps.addStep(
                STR."Check the '\{column}' column's pop-up list view contains the following values:\{values.getList()}");
        trialSteps.checkValuesForCriteriaByFilter(values.getList());
        testDefinitionSteps.logEvidence(
                STR."The '\{column}' column's pop-up list view contains the following values:\{values}",
                STR."The '\{column}' column's pop-up list view contains the following values:\{values}",true);
    }

    @Then("the user verify that \"Filter By\" frame doesn't appears on page")
    public void checkFilterByFrameNotOnPage() {
        testDefinitionSteps.addStep(
                "Check that \"Filter By\" frame doesn't appears on page");
        trialSteps.checkIfFilterByTextIsDisplayed();
        testDefinitionSteps.logEvidence(
                "\"Filter By\" frame was not appeared on page",
                "\"Filter By\" frame not appeared on page", true);
    }

      //SRS
    @Then("the user verify that \"Filter By\" frame contains all the selected filters")
    public void checkFilterByFrameHasAllSelectedFilters() {
        testDefinitionSteps.addStep(
                "Check that \"Filter By\" frame includes all the selected filters");
        trialsViewSteps.performActionNotYetImplemented();
        testDefinitionSteps.logEvidence(
                "All filters were displayed on \"Filter By\" frame",
                "All filters were displayed on \"Filter By\" frame", true);
    }

    //SRS
    @Then("the criteria view displays all filtered criteria")
    public void checkCriteriaViewDisplaysFilteredCriteria() {
        testDefinitionSteps.addStep(
                "Check the criteria view displays all filtered criteria");
        trialsViewSteps.performActionNotYetImplemented();
        testDefinitionSteps.logEvidence(
                "The criteria view displays all filtered criteria",
                "The criteria view displays all filtered criteria", true);
    }

    //SRS
    @Then("the criteria back into table view with removed statuses:$statuses")
    public void checkCriteriaBackIntoViewByRemovedStatus(StringList statuses) {
        testDefinitionSteps.addStep(
                STR."Check the criteria back into table view with removed \{statuses}");
        testDefinitionSteps.logEvidence(
                STR."The criteria back into table view with removed \{statuses}",
                STR."The criteria back into table view with removed \{statuses}", true);
    }

    @Then("the user checks the inner content has the following data: $table")
    public void checkInnerDataOfExpandedCriteria(ExamplesTable table) {
        testDefinitionSteps.addStep("Check the inner content of expanded rows");

        trialSteps.checkTypeOfExpandedCriteria(
                table.getRows().stream()
                        .map(expectedTableRow -> ClinicalCriteriaItem.builder()
                                .criteriaDescription(expectedTableRow.get("header"))
                                .criteriaStatus(CriteriaStatus.fromString(expectedTableRow.get("status")))
                                .criteriaType(
                                        expectedTableRow.containsKey("type") && expectedTableRow.get("type") != null
                                                ? CriteriaType.fromString(expectedTableRow.get("type"))
                                                : CriteriaType.EMPTY
                                )
                                .eligibilityValue(
                                        expectedTableRow.containsKey("eligibility_value")
                                                ? expectedTableRow.get("eligibility_value")
                                                : null
                                )
                                .build())
                        .collect(toList())
        );

        testDefinitionSteps.logEvidence(
                STR."The inner content has the following information: \{table.getRows().stream().map(Object::toString).collect(Collectors.joining("\n"))}",
                STR."The inner content has the following information: \{table.getRows().stream().map(Object::toString).collect(Collectors.joining("\n"))}", true);
    }

    @Then("the user checks that sub-criteria for the trial $configID, the AI indicator next to each status as following:$table")
    public void checkAiIconDisplayNextToStatusForExpandedView(String configID, ExamplesTable table) {
        testDefinitionSteps.addStep(STR."Check the AI icon for trial:\{configID}, displays for criteria with expanded rows as follows:\n\{table.getRows().stream().map(Object::toString).collect(Collectors.joining("\n"))}");
        trialSteps.checkAiIndicationOfExpandedCriteriaStatus(table.getRows().stream()
                                                                  .filter( row -> row.get("ai_icon_displayed").equals( "true" ) )
                                                                  .map(row -> row.get("criteria_status")).collect(toList()));
        testDefinitionSteps.logEvidence(
                STR."The inner content has the following information: \{table.getRows().stream().map(Object::toString).collect(Collectors.joining("\n"))}",
                STR."The inner content has the following information: \{table.getRows().stream().map(Object::toString).collect(Collectors.joining("\n"))}", true);
    }
    @Then("the user checks that sub-criteria for the trial $configID, the AI indicator next to each eligibility value as following:$table")
    public void checkAiIconDisplayNextToEligibilityForExpandedView(String configID, ExamplesTable table) {
        testDefinitionSteps.addStep(STR."Check the AI icon for trial:\{configID} to each eligibility value");
        trialSteps.checkAiIndicationOfExpandedCriteriaEligibilityValue(table.getRows().stream()
                .filter( row -> row.get("ai_icon_displayed").equals( "true" ) )
                .map(row -> row.get("eligibility_value")).collect(toList()));
        testDefinitionSteps.logEvidence(
                STR."The inner content has the following information: \{table.getRows().stream().map(Object::toString).collect(Collectors.joining("\n"))}",
                STR."The inner content has the following information: \{table.getRows().stream().map(Object::toString).collect(Collectors.joining("\n"))}", true);
    }
    @Then("the user checks the card menu, the AI indicator next to each card item as following:$table")
    public void checkAiIconDisplayNextToEligibilityForExpandedView(ExamplesTable table) {
        testDefinitionSteps.addStep(STR."Check the AI icon for card menu");
        trialSteps.checkAiIndicationOfExpandedCardMenuItems(table.getRows().stream()
                .filter( row -> row.get("ai_icon_displayed").equals( "true" ) )
                .map(row -> row.get("criteria_status")).collect(toList()));
        testDefinitionSteps.logEvidence(
                STR."The inner content has the following information: \{table.getRows().stream().map(Object::toString).collect(Collectors.joining("\n"))}",
                STR."The inner content has the following information: \{table.getRows().stream().map(Object::toString).collect(Collectors.joining("\n"))}", true);
    }
    @Then("the user checks that patient value highlighted per condition as indication of AI extraction:$table")
    public void checkThePatientValueHighlightedPerInnerCriteriaExpendedView(ExamplesTable table) {
        testDefinitionSteps.addStep(STR."Check the user checks that patient value highlighted per condition as indication of AI extraction:\n\{table.getRows().stream().map(Object::toString).collect(Collectors.joining("\n"))}");
        Map<String, String> expectedAiData = table.getRows()
                .stream()
                .collect(Collectors.toMap(
                        itemValue -> itemValue.get("patient_value"),
                        itemValue -> itemValue.get("isHighlighted")));
        trialSteps.checkPatientValueHighlightedAsIndicationOfAiExtraction(expectedAiData);
        testDefinitionSteps.logEvidence(
                STR."The the user checks that patient value highlighted per condition as indication of AI extraction: \{table.getRows().stream().map(Object::toString).collect(Collectors.joining("\n"))}",
                STR."Thethe user checks that patient value highlighted per condition as indication of AI extraction: \{table.getRows().stream().map(Object::toString).collect(Collectors.joining("\n"))}", true);
    }

    @Then("the sub-criteria $title followed by logical operator - $operator")
    public void checkTheConditionBetweenTwoTitles(String title, String operator) {
        testDefinitionSteps.addStep(STR."Check for the sub-criteria \{title} followed by \{operator} logical operator");
        trialSteps.checkSubCriteriaWithTitleAndLogicalOperator(title,operator);
        testDefinitionSteps.logEvidence(
                STR."The sub-criteria \{title} followed by \{operator} logical operator",
                STR."The sub-criteria \{title} followed by \{operator} logical operator", true);
    }

    @Then("the simple criteria name starts with:$criteriaName, is not expandable")
    public void checkForNoExpandableCriteria(String criteriaName) {
        testDefinitionSteps.addStep(
                STR."Check for non expandable simple criteria, which's name starts with:\{criteriaName}");
        trialSteps.isThereAnExpandOptionForCriteriaName(criteriaName, false);
        testDefinitionSteps.logEvidence(
                STR."No expand button found for simple criteria name starts with:\{criteriaName} as expected",
                STR."No expand button was observed for simple criteria name starts with: \{criteriaName}", true);
    }

    @Then("the criteria with title starts with \"$title\", was collapsed into initial view")
    public void checkForCriteriaCollapse(String title) {
        testDefinitionSteps.addStep(
                STR."Check for collapse of the criteria with title starts with:\{title}");
        trialSteps.checkTitleIsDisplayedForCollapsedComplexCriteria(title);
        testDefinitionSteps.logEvidence(
                STR."The criteria with title starts with:\{title}, was collapsed",
                STR."The criteria with title starts with:\{title}, was collapsed", true);
    }
    @Then("the trial card titled '$title' has a truncated title and its screenshot size matches the expected bytes in range of $expectedScreenshotSizeMin and $expectedScreenshotSizeMax")
    public void checkForTrialShortenedTitleDisplay(String title, int expectedScreenshotSizeMin, int expectedScreenshotSizeMax) {
        testDefinitionSteps.addStep(
                STR."Check the trial card titled \{title} has a truncated title and its screenshot size matches the expected bytes in range of \{expectedScreenshotSizeMin} and \{expectedScreenshotSizeMax}");
        trialSteps.checkTrialHasTruncatedTitle(title,expectedScreenshotSizeMin,expectedScreenshotSizeMax);
        testDefinitionSteps.logEvidence(
                STR."The trial card titled \{title} has a truncated title and its screenshot size matches the expected bytes in range of \{expectedScreenshotSizeMin} and \{expectedScreenshotSizeMax}",
                STR."The trial card titled \{title} has a truncated title and its screenshot size matches thethe expected bytes in range of \{expectedScreenshotSizeMin} and \{expectedScreenshotSizeMax}.Check screenshot for observed truncated title", true);
    }
    @Then("the trial card '$title' has a truncated description starting with '$description' and its screenshot size matches the expected bytes in range of $expectedScreenshotSizeMin and $expectedScreenshotSizeMax")
    public void checkForTrialCardShortenedDescription(String title, String description, int expectedScreenshotSizeMin, int expectedScreenshotSizeMax) {
        testDefinitionSteps.addStep(
                STR."Check the trial card titled \{title} has a truncated description that starts with \{description} and its screenshot size matches the expected bytes in range of \{expectedScreenshotSizeMin} and \{expectedScreenshotSizeMax}");
        trialSteps.checkTrialHasTruncatedDescription(description,expectedScreenshotSizeMin,expectedScreenshotSizeMax);
        testDefinitionSteps.logEvidence(
                STR."The trial card titled \{title} has a truncated description that starts with \{description} and its screenshot size matches the expected bytes in range of \{expectedScreenshotSizeMin} and \{expectedScreenshotSizeMax}",
                STR."The trial card titled \{title} has a truncated description that starts with \{description} and its screenshot size matches the expected bytes in range of \{expectedScreenshotSizeMin} and \{expectedScreenshotSizeMax}. Check screenshot for observed truncated description", true);
    }

    @Then("the complex is $logicalOperator between criteria, then the $calculatedStatus is the $matchLevel indication status of criteria with title starts with $title")
    public void checkComplexEvaluationStatus(String logicalOperator, String calculatedStatus, String matchLevel, String title) {
        testDefinitionSteps.addStep(
                STR."Check for a complex evaluation using \{logicalOperator}, where the calculated status is determined as the \{calculatedStatus} as \{matchLevel} indication status among the criteria");
        trialSteps.checkCollapsedCriteriaStatusByCriteriaTitle(title,calculatedStatus);
        testDefinitionSteps.logEvidence(
                STR."The calculated status using \{logicalOperator} was obsereved as the \{calculatedStatus} with \{matchLevel} indication status among the criteria",
                STR."The calculated status using \{logicalOperator} was obsereved as the \{calculatedStatus} with \{matchLevel} indication status among the criteria", true);
    }

    @Then("the calculated status is $calculatedStatus for the criteria titled $title")
    public void checkComplexEvaluationStatus(String calculatedStatus, String title) {
        testDefinitionSteps.addStep(
                STR."Check the calculated display status is \{calculatedStatus} for the criteria titled \{title}");
        trialSteps.checkCollapsedCriteriaStatusByCriteriaTitle(title,calculatedStatus);
        testDefinitionSteps.logEvidence(
                STR."The expected calculated display status is \{calculatedStatus} for the criteria titled \{title}",
                STR."The observed calculated display status was \{calculatedStatus} for the criteria titled \{title}",
                true);
    }

    @Then("the tooltip showed-up for criteria $criteriaTitle, with the following source description: '$tooltipContent'")
    public void checkTooltipContentDisplay(String criteriaTitle, String tooltipContent) {
        testDefinitionSteps.addStep(
                STR."Check the tooltip content displays: \{tooltipContent} for the criteria titled \{criteriaTitle}");
        trialSteps.checkTooltipDisplayTheContentPeForCriteriaWithTitle(criteriaTitle, tooltipContent);
        testDefinitionSteps.logEvidence(
                STR."The expected tooltip content display:\{tooltipContent} for the criteria titled \{criteriaTitle}",
                STR."The expected tooltip content display:\{tooltipContent} for the criteria titled \{criteriaTitle}",
                true);
    }

    @Then("check the 'Eligibility values' matches the regex according the file - $fileName")
    public void checkEligibilityValuesVsExpectedByRegex(String fileName) {
        testDefinitionSteps.addStep(
                STR."Check the 'Eligibility values' matches the regex according the file \{fileName}");
        trialSteps.checkObservedInGridViewVsExpectedEligibilityValuesInFile(fileName);
        testDefinitionSteps.logEvidence(
                STR."The expected matches the regex according the file \{fileName}",
                STR."The observed 'Eligibility values' matches the regex according the file - \{fileName}",
                true);
    }

    @When( "the user clicks on the sort arrow for the \"$columnName\" column" )
    public void setSortingForColumn( String columnName ) {
        testDefinitionSteps.addStep( STR."Clicking the sort arrow for \{columnName} column" );
        trialSteps.clickOnSortForColumn( columnName );
        testDefinitionSteps.logEvidence(
                STR."Successfully clicked on the sort arrow for the \{columnName} column",
                STR."Successfully clicked on the sort arrow for the \{columnName} column",
                true);
    }

    @Then( "the criteria table is sorted by \"$columnName\" column which has {a|an} \"$arrowDirection\" pointing arrow" )
    public void checkSortParamInDefaultMedicationsTable( String columnName, String arrowDirection ) {
        testDefinitionSteps.addStep("Check sort param in default medications table");
        trialSteps.checkSortParamInCriteriaTable( columnName, arrowDirection );
        testDefinitionSteps.logEvidence(STR."The criteria table is sorted by '\{columnName}' column which has '\{arrowDirection}' pointing arrow",
                STR."Criteria table is sorted by '\{columnName}' column which has '\{arrowDirection}' pointing arrow", true);
    }
}
