/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2021, 2021, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.howsteps.trials;

import com.ge.onchron.verif.howsteps.CommonSteps;
import com.ge.onchron.verif.howsteps.RestApiSteps;
import com.ge.onchron.verif.model.RestRequest;
import com.ge.onchron.verif.model.clinicalConfiguration.*;
import com.ge.onchron.verif.model.clinicalConfiguration.treatment.ClinicalTreatmentEligibilityConfig;
import com.ge.onchron.verif.model.clinicalConfiguration.trial.*;
import com.ge.onchron.verif.pages.tabs.Trials;
import com.ge.onchron.verif.pages.tabs.common.CommonTrialRadiology;
import com.ge.onchron.verif.utils.AssertionUtils;
import com.ge.onchron.verif.utils.RequestSpecificationUtils;
import com.ge.onchron.verif.utils.TrialConfigManagerUtils;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Steps;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.jbehave.core.model.ExamplesTable;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import java.util.function.Supplier;


import static com.ge.onchron.verif.TestSystemParameters.*;
import static com.ge.onchron.verif.model.clinicalConfiguration.CriteriaType.fromString;
import static com.ge.onchron.verif.utils.FileUtils.getAbsolutPathOfDir;
import static com.ge.onchron.verif.utils.FileUtils.readFromFileByAbsolutePath;
import static com.ge.onchron.verif.utils.ImageUtils.bytesToBufferedImage;
import static com.ge.onchron.verif.utils.ImageUtils.saveScreenshot;
import static io.restassured.http.Method.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.StringContains.containsStringIgnoringCase;
import static org.junit.Assert.fail;

public class TrialSteps extends CommonTrialRadiology {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrialSteps.class);
    //Clinical trial
    private static final String CONFIG_TYPE_BASE_PATH = STR."\{CONFIG_BASE_PATH}/{configType}";
    private static final String CONFIG_ID_BASE_PATH = STR."\{CONFIG_TYPE_BASE_PATH}/{configId}";
    private static final String ELIGIBILITY_CONFIG_TYPE_BASE_PATH = STR."\{ELIGIBILITY_BASE_PATH}/{configType}";
    private static final String ELIGIBILITY_CONFIG_ID_BASE_PATH = STR."\{ELIGIBILITY_CONFIG_TYPE_BASE_PATH}/{configId}";

    private static final String STATUS_COLUMN = "status";
    private static final String TYPE_COLUMN = "type";
    private static final String ELIGIBILITY_VALUE_COLUMN = "eligibility value(s)";

    private Trials trialsSection;
    @Steps
    private RestApiSteps restApiSteps;
    @Steps
    private CommonSteps commonSteps;


    public void createNewTrialConfig(String configType, String trialData) {
        LOGGER.debug(STR."Submit POST request to create new Trial configurations with following data:\n\{trialData}");
        RequestSpecification rs = RequestSpecificationUtils
                .getClinicalTrialRequestSpecification(CONFIG_TYPE_BASE_PATH.replaceAll("\\{configType}", configType))
                .body(trialData);
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(POST, rs));
    }
    public void createNewTrialConfig(String trialData, String configType, String param) {
        LOGGER.debug(STR."Submit POST request to create new Trial configurations with following data:\n\{trialData}");
        RequestSpecification rs = RequestSpecificationUtils
                .getClinicalTrialRequestSpecification( CONFIG_TYPE_BASE_PATH.replaceAll("\\{configType}", configType))
                .body(trialData)
                .queryParam("patient", param);
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(POST, rs));
    }

    public void updateTrialConfig(String configId, String configType, String trialData) {
        LOGGER.debug(STR."Submit put request to update clinical trial configuration with id \{configId} and configType:\{configType} with the following data:\n\{trialData}");
        RequestSpecification rs = RequestSpecificationUtils
                .getClinicalTrialRequestSpecification(
                        CONFIG_ID_BASE_PATH.replaceAll("\\{configId}", configId).replaceAll("\\{configType}", configType))
                .body(trialData);
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(PUT, rs));
    }

    public void updateTrialConfigWithParameters(ClinicalTreatmentEligibilityConfig config) {
        String jsonBody = TrialConfigManagerUtils.convertClinicalTreatmentEligibilityConfigToString(config);
        LOGGER.debug(STR."Submit put request to update clinical trial configuration with id \{config.getId()} and the following data:\n\{jsonBody}");
        RequestSpecification rs = RequestSpecificationUtils
                .getClinicalTrialRequestSpecification(CONFIG_ID_BASE_PATH.replaceAll("\\{configId}", config.getId())
                        .replaceAll("\\{configType}", config.getType()))
                .body(jsonBody);
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(PUT, rs));
    }

    public void updateTrialConfigForPatientId(final String configType, final String configId, final String patientID, final String trialData) {
        LOGGER.debug(STR."Submit put request to update clinical trial configuration with id \{configId} and the following data:\{trialData}, and for patient with ID:\{patientID}");

        RequestSpecification rs = RequestSpecificationUtils
                .getClinicalTrialRequestSpecification(CONFIG_ID_BASE_PATH
                        .replaceAll("\\{configType}", configType)
                        .replaceAll("\\{configId}", configId))
                .queryParam("patient", patientID)
                .body(trialData);

        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(PUT, rs));
    }

    public String loadConfigFile(final String fileName) {
        Path resourcesAbsolutPath = getAbsolutPathOfDir(getSystemParameter(CLINICAL_TRIAL_RESOURCES_PATH));
        Path filePath = Paths.get(resourcesAbsolutPath.toString(), STR."\{fileName}.json");
        return readFromFileByAbsolutePath(filePath.toString());
    }

    public void checkForListOfTrials(final int numOfTrialsInTheList) {
        List<TrialItem> trialItemList = trialsSection.getListOfTrials();
        LOGGER.info(STR."Observed clinical trials were:\n\{trialItemList.stream().map(TrialItem::toString)
                .collect(Collectors.joining("\\n"))}");
        assertThat("Some clinical trials were missing", trialItemList.size(), is(equalTo(numOfTrialsInTheList)));
    }

    public void checkForTheTrialsProcessingNotificationMessage(final String notificationMsg) {
        String theTrialsProcessingNotificationMessage = trialsSection.getTheTrialsProcessingNotificationMessage();
        LOGGER.info(STR."Observed clinical trials processing notification message was :\{theTrialsProcessingNotificationMessage}");
        assertThat("The clinical trials processing notification message was missing", theTrialsProcessingNotificationMessage, is(equalTo(notificationMsg)));
    }

    public void checkForAnyTrialDisplayInListOfTrials() {
        List<TrialItem> trialItemList = trialsSection.getListOfTrials();
        LOGGER.info(STR."Observed clinical trials were:\n\{trialItemList.stream().map(TrialItem::toString)
                .collect(Collectors.joining("\\n"))}");
        assertThat("No clinical trials were found", trialItemList.size(), is(greaterThan(0)));
    }

    public void checkForInformationOfEachExistenceTrials() {
        List<TrialItem> trialItemList = trialsSection.getListOfTrials();
        LOGGER.info(STR."Observed clinical trials were:\n\{trialItemList.stream().map(TrialItem::toString)
                .collect(Collectors.joining("\\n"))}");

        trialItemList
                .forEach(trialItem -> {
                    assertThat("TrialName field were empty or missing", !trialItem.getTrialName().isEmpty());
                    assertThat("Description fields were empty or missing", !trialItem.getDescription().isEmpty());
                    assertThat("OverallMatchingScore fields were empty or missing", !trialItem.getOverallMatchingScore().name().isEmpty());
                    assertThat("ConfigId fields were empty or missing", !trialItem.getClinicalTrialItemMetadata().getConfigId().isEmpty());
                });
    }

    public void checkConfidenceScoreForTrials(ExamplesTable expectedTrialConfidenceScores) {
        List<TrialItem> expectedTrialItems = Lists.newArrayList();
        expectedTrialConfidenceScores.getRows()
                .forEach(linkedMap ->
                        expectedTrialItems.add(TrialItem.builder()
                                .clinicalTrialItemMetadata(ClinicalTrialItemMetadata.builder()
                                        .configId(linkedMap.get("trial_id")).build())
                                .overallMatchingScore(
                                        OverallMatchingScore.fromString(linkedMap.get("Matching_Confidence_Score")))
                                .build()));

        List<TrialItem> actualTrialItems = trialsSection.getListOfTrials();
        LOGGER.info(STR."Observed clinical trials were:\n\{actualTrialItems.stream().map(TrialItem::toString)
                .collect(Collectors.joining("\\n"))}");

        expectedTrialItems.forEach(expectedTrialItem -> {
            boolean matchFound = actualTrialItems.stream()
                    .anyMatch(actualTrialItem ->
                            actualTrialItem.getClinicalTrialItemMetadata().getConfigId().equals(expectedTrialItem.getClinicalTrialItemMetadata().getConfigId()) &&
                                    actualTrialItem.getOverallMatchingScore().getMatchingScore().equals(expectedTrialItem.getOverallMatchingScore().getMatchingScore())
                    );
            String errorMessage = STR."Matching trial item was not found for config ID: " +
                    expectedTrialItem.getClinicalTrialItemMetadata().getConfigId() +
                    " and Matching Score: " + expectedTrialItem.getOverallMatchingScore().getMatchingScore();
            assertThat(errorMessage, matchFound, is(true));
        });
    }

    public void clickOnTrialFromListByTrialID(final String trialId) {
        WebElement trialWebElement = fetchElementByConfigurationID(trialId);
        assertThat("TrialID field was not found", trialWebElement, is(notNullValue()));
        trialWebElement.click();
    }

    public void checkBasicInformationInGridViewForTrialID(final List<Map<String, String>> expectedRowsData) {
        ClinicalTrialItemGridView trialGeneralInformationInGridViewBy = trialsSection.getTrialGeneralInformationInGridViewBy();
        TrialItem actualTrialItem = trialGeneralInformationInGridViewBy.getTrialItem();

        Map<String, String> expectedData = expectedRowsData
                .stream()
                .collect(Collectors.toMap(
                        itemValue -> itemValue.get("Field"),
                        itemValue -> itemValue.get("Field content")));

        assertThat("Trial Name was not the same or missing",
                actualTrialItem.getTrialName(), is(expectedData.get("Trial name")));
        assertThat("Trial Description was not the same or missing",
                actualTrialItem.getDescription(), is(expectedData.get("Trial description")));
        assertThat("Trial Matching score was not the same or missing",
                actualTrialItem.getOverallMatchingScore().getMatchingScore(), is(expectedData.get("Matching score")));
        assertThat("Trial Phase was not the same or missing",
                actualTrialItem.getClinicalTrialItemMetadata().getPhase(), is(expectedData.get("Phase")));
        assertThat("Trial Status was not the same or missing",
                actualTrialItem.getClinicalTrialItemMetadata().getRecruitmentStatus().getStatus(), is(expectedData.get("Status")));
        assertThat("Trial ID was not the same or missing",
                actualTrialItem.getClinicalTrialItemMetadata().getConfigId(), equalToIgnoringCase(expectedData.get("Trial_ID")));
    }

    public void checkAIIndicationsInGridViewForTrialID(final ExamplesTable expectedData) {
        AIIndicator observedAiIndicator = trialsSection.getAiIndicatorsInGridView().getTrialItem().getAiIndicator();
        Map<String, String> expectedAiData = expectedData.getRows()
                .stream()
                .collect(Collectors.toMap(
                        itemValue -> itemValue.get("grid_detail_section"),
                        itemValue -> itemValue.get("expected_data")));
        assertThat("AI was not highlighted", observedAiIndicator.isAIHighlighted(), is(Boolean.parseBoolean(expectedAiData.get("isAIHighlighted"))));
        assertThat("Disclaimer icon was not displayed", observedAiIndicator.isDisclaimerIconDisplayed(), is(Boolean.parseBoolean(expectedAiData.get("isDisclaimerIconDisplayed"))));
        assertThat("Disclaimer description was not as expected", observedAiIndicator.getAiDisclaimerDescription(), is(expectedAiData.get("AiDisclaimerDescription")));
        assertThat("Oveirall Matching Score has no AI indcator", observedAiIndicator.isOverallMatchingScoreAiIconDisplayed(), is(Boolean.parseBoolean(expectedAiData.get("isOverallMatchingScoreAiIconDisplayed"))));
    }

    public void checkAIIndicationInTrialsTabNextToMatchingStatusForTrialID(final String configId, final boolean isDisplayed) {
        boolean isDisclaimerIconDisplayed =
                trialsSection.getListOfTrials().stream()
                .filter(trialItem -> trialItem.getClinicalTrialItemMetadata().getConfigId().equals(configId))
                        .toList().getFirst().getAiIndicator().isDisclaimerIconDisplayed;
        assertThat(STR."AI not displayed for specified \{configId}", isDisclaimerIconDisplayed, is(isDisplayed));
    }

    public void checkCriteriaColumnDisplayNames(final List<String> expectedColumnNames) {
        List<CriteriaTableHeaderItem> expectedColumns = expectedColumnNames
                .stream()
                .map(rowName -> CriteriaTableHeaderItem.builder().columnName(
                        CriteriaTableHeaderItem.CriteriaItemHeader
                                .fromString(rowName.replaceAll("(\s*\\|\s*)", ""))).build()).toList();

        List<CriteriaTableHeaderItem> criteriaColumnNames = trialsSection.getCriteriaColumnNames();
        criteriaColumnNames.forEach(actualColumnName ->
                assertThat("Column name was not found!",
                        expectedColumns
                                .stream()
                                .map(filter -> filter.getColumnName().getCriteriaItemHeader())
                                .toList()
                                .contains(actualColumnName.getColumnName().getCriteriaItemHeader()))
        );
    }

    public void clickOnCriteriaGridToHideThePopupSelectionList() {
        trialsSection.clickOnCriteriaGridAria();
    }

    public void clickOnCriteriaFilterForColumn(final String expectedColumnName) {
        trialsSection.clickOnFilterButtonFoColumn(expectedColumnName);
    }

    public void checkValuesForCriteriaByFilter(final List<String> expectedFilter) {
        trialsSection.waitForAngularRequestsToFinish();
        LOGGER.info("Waiting before reading the list of Status/Type filter values from pop-up");
        commonSteps.waitInMillis(60000);
        List<String> actualFilterValues = trialsSection.getFilterValues();
        assertThat(STR."Matching list was not found in the observed list!\nActual observed filterValues were:\n\{actualFilterValues}\nBut the expected filter values were:\n\{expectedFilter}",
                actualFilterValues.containsAll(expectedFilter));
    }

    public void isFilterBySectionDisplayedOnFrame() {
        assertThat(STR."'Filter by' frame was displayed!",
                !trialsSection.isSectionFilterByExistsInTrialGridView());
    }

    public void checkFilteredByLabelTextExists(final String status) {
        List<String> filterLabels = trialsSection.getFilteredBySelectedLabels();
        LOGGER.info(STR."The actual filter labels is: \{filterLabels}");
        assertThat(STR."Filter label \{status} does not exist", filterLabels, hasItem(containsStringIgnoringCase(status)));
    }

    public void clickOnFilterClearAllButton() {
        trialsSection.clickOnClearAllButton();
    }

    public void checkIfFilterByTextIsDisplayed() {
        boolean isDisplayed = trialsSection.isFilterByTextDisplayed();
        LOGGER.info(STR."The filter by text is displayed: \{isDisplayed}");
        assertThat(STR."Filter by text is not displayed", isDisplayed, is(true));
    }

    public void checkIfOnlyRelevantCriteriaStatusDisplayed(final String status) {
        List<String> statusList = trialsSection.getStatusFromTable();
        LOGGER.info(STR."The actual displayed statuses in table: \{statusList}");
        assertThat(STR."Not all statuses are set to \{status}", statusList, hasItem(status));
    }

    public void checkIfOnlyRelevantCriteriaTypeDisplayed(final String type) {
        List<String> typesList = trialsSection.getTypeValuesFromCriteriaTable();
        LOGGER.info(STR."The actual displayed statuses in table: \{typesList}");
        assertThat(STR."Not all types are set to \{type}", typesList, hasItem(type));
    }

    public void clickOnStatusFilterCheckbox(final String status) {
        trialsSection.clickOnFilterStatusCheckbox(status);
    }

    public void clickOnTrialsTabXCloseButton() {
        trialsSection.clickOnCloseTrialsTab();
    }

    public void checkCriteriaListNotEmptyForTrial() {
        List<ClinicalCriteriaItem> observedCriteriaNamesFromGridView = trialsSection.getCriteriaNameFromGridView();
        LOGGER.info(STR."The criteria view contains the following criterias' description:\{
                observedCriteriaNamesFromGridView
                        .stream()
                        .map(ClinicalCriteriaItem::getCriteriaDescription)
                        .collect(Collectors.joining("\\n"))}");
        assertThat(STR."The criteria list was empty", !observedCriteriaNamesFromGridView.isEmpty());
    }

    public void checkTitleIsDisplayedForCollapsedComplexCriteria(final String expectedTitle) {
        List<String> titlesList = trialsSection.getTitlesOfComplexCriteriaFromTable();
        LOGGER.debug(STR."The actual displayed collapsed titles in table: \{titlesList}");
        assertThat(STR."Not all statuses are set to \{expectedTitle}", titlesList, hasItem(startsWith(expectedTitle)));
    }

    public void checkTitlesOfExpandedCriteria(final List<String> expectedTitles) {
        List<String> titlesList = trialsSection.getExpandedRowTitles();
        LOGGER.debug(STR."The actual displayed expanded rows titles in table: \{titlesList}");
        assertThat(STR."Not all titles are similar \{expectedTitles}", titlesList, is(expectedTitles));
    }

    public void checkStatusOfExpandedCriteria(final List<String> expectedStatuses) {
        List<String> statusList = trialsSection.getExpandedRowStatuses();
        LOGGER.debug(STR."The actual displayed expanded rows statuses in table: \{statusList}");
        assertThat(STR."Not all statuses are similar \{expectedStatuses}", statusList, is(expectedStatuses));
    }

    public void clickOnCriteriaExpandCollapseButton(final String criteriaTitle) {
        trialsSection.clickExpandArrowButtonOnCriteria(criteriaTitle);
    }

    public void failDueToNonImplementedFeature(final Optional<String> optional) {
        optional.ifPresentOrElse(
                value -> fail(STR."Currently the test fails, because of:\n\{optional}!"),
                () -> fail("Currently the test fails, because of non-implemented feature!")
        );
    }

    public void isThereAnExpandOptionForCriteriaName(String criteriaName, boolean isExpandable) {
        boolean isDisplayed = trialsSection.isExpandOptionForCriteriaNameDisplay(criteriaName);
        LOGGER.info(STR."Expand arrow button was existed in dom: \{isDisplayed}");
        assertThat(STR."Expand arrow button was displayed for criteria\{criteriaName}", isDisplayed, is(isExpandable));
    }

    public void checkAiIndicationOfExpandedCriteriaStatus(final List<String> expectedStatuses) {
        checkAiIndication(
                expectedStatuses,
                () -> trialsSection.getListOfExpandedStatusesWhichHasAiIconIndication(),
                "AI icon not found for status: %s");
    }

    public void checkAiIndicationOfExpandedCriteriaEligibilityValue(final List<String> expectedStatuses) {
        checkAiIndication(
                expectedStatuses,
                () -> trialsSection.getListOfExpandedEligibilityValuesWhichHasAiIconIndication(),
                "AI icon not found for eligibility value: %s"
        );
    }

    public void checkAiIndicationOfExpandedCardMenuItems(final List<String> expectedStatuses) {
        checkAiIndication(
                expectedStatuses,
                () -> trialsSection.getListOfExpandedCardsOnMenuWhichHasAiIconIndication(),
                "AI icon not found for status: %s"
        );
    }

    public void checkAiIndication(
            final List<String> expectedStatuses,
            final Supplier<List<WebElementFacade>> sectionSupplier,
            final String messageTemplate) {

        expectedStatuses.forEach(expectedStatus -> {
            boolean statusFound = sectionSupplier.get()
                    .stream()
                    .anyMatch(section -> {
                        boolean containsText = section.containsText(expectedStatus);
                        LOGGER.debug("The actual displayed expanded rows titles in table: {}", section.getText());
                        return containsText;
                    });
            assertThat(String.format(messageTemplate, expectedStatus), statusFound, is(true));
        });
    }

    public void checkPatientValueHighlightedAsIndicationOfAiExtraction(Map<String, String> expectedPatientValuesHighlighted) {
        expectedPatientValuesHighlighted
                .forEach((expectedKey, expectedHighlightStatus) -> {
                    boolean isHighlighted = trialsSection.isPatientValueHighlightedForKey(expectedKey);
                    assertThat(
                            STR."Value was not highlighted for patient_value:\{expectedKey}", isHighlighted, is(Boolean.valueOf(expectedHighlightStatus)));
                });
    }

    public void checkTrialHasTruncatedTitle(String title, int expectedByteArraySizeMin, int expectedScreenshotSizeMax) {
        byte[] actualScreenshotBytesSize = trialsSection.takeScreenShotOfTrialWithTitle(title);
        compareTheScreenshots(expectedByteArraySizeMin, expectedScreenshotSizeMax, actualScreenshotBytesSize);
    }

    public void checkTrialHasTruncatedDescription(String description, int expectedByteArraySizeMin, int expectedByteArraySizeMax) {
        byte[] actualScreenshotBytesSize = trialsSection.takeScreenShotOfTrialDescription(description);
        compareTheScreenshots(expectedByteArraySizeMin, expectedByteArraySizeMax, actualScreenshotBytesSize);
    }

    private static void compareTheScreenshots(int expectedByteArraySizeMin, int expectedByteArraySizeMax, byte[] actualScreenshotBytesSize) {
        BufferedImage bufferedImage = bytesToBufferedImage(actualScreenshotBytesSize);
        saveScreenshot(bufferedImage);
        // Print the byte array or its length
        LOGGER.info(STR."Screenshot byte array length:\{actualScreenshotBytesSize.length}");
        LOGGER.info(STR."Element screenshot byte array:\{Arrays.toString(actualScreenshotBytesSize)}");
        assertThat(STR."Screenshot byte array length is not in expected range: between \{expectedByteArraySizeMin} and \{expectedByteArraySizeMax}",
                actualScreenshotBytesSize.length ,is(both(greaterThanOrEqualTo(expectedByteArraySizeMin)).and(lessThanOrEqualTo(expectedByteArraySizeMax))));
    }

    public void scrollTrialsToButtonAndCheckLastTrialDisplay(final String lastConfigId) {
        trialsSection.scrollTrialsInTabToTheButton();
        assertThat(STR."Scrollbar was scrolled down. Lats trial was displayed:",
                trialsSection.fetchElementByConfigurationID(lastConfigId).isDisplayed(), is(true));
    }

    public void checkSubCriteriaWithTitleAndLogicalOperator(String title, String operator) {
        boolean isFound = trialsSection.isExpandedTitleFollowedByLogicalOperator(title, operator);
        assertThat(STR."The sub-criteria found with title \{title} was followed by \{operator} logical operator",
                isFound, is(true));
    }

    public void checkCollapsedCriteriaStatusByCriteriaTitle(String title, String calculatedStatus) {
        String observedLevel = trialsSection.getCollapsedCriteriaStatusByCriteriaTitle(title);
        assertThat(STR."The observed \{calculatedStatus} matching status was not as expected", observedLevel, is(equalToIgnoringCase(calculatedStatus)));
    }

    public void clickCollapseArrowButtonOnCriteria(String title) {
        trialsSection.clickCollapseArrowButtonOnCriteria(title);
    }

    public void deleteTrialConfigByConfigTypeConfigId(String configType, String configId) {
        LOGGER.debug(STR."Submit delete request to delete trial configuration by \{configType} and with id \{configId}");
        RequestSpecification rs = RequestSpecificationUtils
                .getClinicalTrialRequestSpecification(CONFIG_ID_BASE_PATH
                        .replaceAll("\\{configId}", configId)
                        .replaceAll("\\{configType}", configType));
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(DELETE, rs));
    }

    public void deleteTrialConfigByIdAndPatientId(String configId, String patientId) {
        LOGGER.debug(STR."Submit delete request to delete trial configuration with id \{configId}");
        RequestSpecification rs = RequestSpecificationUtils
                .getClinicalTrialRequestSpecification(CONFIG_ID_BASE_PATH.replaceAll("\\{configId}", configId)
                        .replaceAll("\\{configType}", "trial"))
                .queryParam("patient", patientId);
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(DELETE, rs));
    }

    public void deleteTrialConfigByIdAndTypeAndPatientId(String configId, String configType, String patientId) {
        LOGGER.debug(STR."Submit delete request to delete trial configuration with id \{configId}");
        RequestSpecification rs = RequestSpecificationUtils
                .getClinicalTrialRequestSpecification(CONFIG_ID_BASE_PATH
                        .replaceAll("\\{configId}", configId)
                        .replaceAll("\\{configType}", configType))
                .queryParam("patient", patientId);
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(DELETE, rs));
    }

    public void deleteTrialConfigTypeAndPatientId(String configType, String patientId) {
        LOGGER.debug(STR."Submit delete request to delete trial configuration type of \{configType} and for patient \{patientId}");
        getLastResponseResultsIDs()
                .forEach(id -> deleteTrialConfigByIdAndPatientId(id, patientId));
        }

    public void deleteAllConfigurationsTypeOf(String configType) {
        LOGGER.debug(STR."Submit DELETE request to delete all configurations type of \{configType}");
        getLastResponseResultsIDs()
                .forEach(configId -> deleteTrialConfigByConfigTypeConfigId(configType, configId));
    }

    public List<String> getLastResponseResultsIDs(){
        List<LinkedHashMap<String, Object>> results = restApiSteps.getLastResponse().jsonPath().getList("results");
        if (results != null && !results.isEmpty()) {
            return results
                    .stream()
                    .map(entry -> (String) entry.get("id"))
                    .collect(Collectors.toList());
        }
     return Collections.emptyList();
    }

    public void getAllTrialsForPatient(String patientId) {
        LOGGER.debug(STR."Get all trials for patientId \{patientId}");
        RequestSpecification rs = RequestSpecificationUtils
                .getClinicalTrialRequestSpecification(CONFIG_TYPE_BASE_PATH
                        .replaceAll("\\{configType}", "trial"))
                .queryParam("patient", patientId);
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(GET, rs));
    }

    public void getAllEligibilityConfigByType(String configType) {
        LOGGER.debug(STR."Submit GET all trials by configType \{configType}");
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(GET,
                RequestSpecificationUtils.getClinicalTrialRequestSpecification(CONFIG_TYPE_BASE_PATH
                                .replaceAll("\\{configType}", configType))));
    }

    public void getConfigurationByTypeAndConfigId(String configType, String configId) {
        LOGGER.debug(STR."Submit GET request for eleigibility endpoint with config paramters: configType - \{configType},configId - \{configId}");
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(GET,
                RequestSpecificationUtils.getClinicalTrialRequestSpecification(CONFIG_ID_BASE_PATH
                        .replaceAll("\\{configType}", configType)
                        .replaceAll("\\{configId}", configId))));
    }

    public void getEligibilityConfigByTypeAndConfigIdAndPatientId( String configType, String configId, String patientId ) {
        LOGGER.debug(STR."Submit GET request for eleigibility endpoint with config paramters: configType - \{configType},configId - \{configId}, patientId - \{patientId}");
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(GET,
                RequestSpecificationUtils.getClinicalTrialRequestSpecification(ELIGIBILITY_CONFIG_ID_BASE_PATH
                        .replaceAll("\\{configType}", configType)
                        .replaceAll("\\{configId}", configId))
                        .with().queryParam("patient", patientId)));
    }

    public void checkNumOfTrialsForPatient(int expectedNumOfTrials) {
        int actualNumOfTrial = 0;
        if (restApiSteps.getLastResponse().asString().contains("id"))
            actualNumOfTrial = restApiSteps.getLastResponse().jsonPath().getList("results").size();
        LOGGER.debug(STR."Observed num of trials: \{actualNumOfTrial}");
        assertThat("Observed num of trials not equal", actualNumOfTrial, equalTo(expectedNumOfTrials));
    }

    public void checkTypeOfExpandedCriteria(final List<ClinicalCriteriaItem> expectedCriteriaItems) {
        List<String> actualTitlesList = trialsSection.getExpandedRowTitles();

        for (String actualTitle : actualTitlesList) {
            Optional<ClinicalCriteriaItem> optionalExpectedItem = expectedCriteriaItems.stream()
                    .filter(item -> item.getCriteriaDescription().equals(actualTitle))
                    .findFirst();

            if (optionalExpectedItem.isEmpty()) {
                LOGGER.warn(STR."No matching expected criteria found for title: \{actualTitle}" );
                throw new AssertionError(STR."Missing expected criteria for title: \{actualTitle}");
            }

            ClinicalCriteriaItem expectedItem = optionalExpectedItem.get();

            String actualEligibilityValue = Optional.ofNullable(
                            trialsSection.getExpandedRowSEligibilityValuesByTitle(actualTitle))
                    .orElse("");

            String actualTypeValue = Optional.ofNullable(
                            trialsSection.getExpandedRowSTypesByTitle(actualTitle))
                    .filter(s -> !s.isEmpty())
                    .orElse(CriteriaType.EMPTY.name());

            LOGGER.info(STR."Observed sub-criteria title: '\{actualTitle}', type: '\{actualTypeValue}'");

            if (expectedItem.getEligibilityValue() != null) {
                boolean eligibilityMatch = checkEligibilityMatch(
                        expectedItem.getEligibilityValue(),
                        actualEligibilityValue,
                        expectedItem.getCriteriaDescription());

                assertThat(STR."Eligibility value mismatch for title: \{actualTitle}", eligibilityMatch, is(true));
            }

            if (expectedItem.getCriteriaType() != null) {
                CriteriaType expectedType = fromString(expectedItem.getCriteriaType().name());
                assertThat(STR."Type mismatch for title: \{actualTitle}", actualTypeValue, is(expectedType.name()));
            }
        }
    }

    public void checkClinicalCriteriasInGridView(List<ClinicalCriteriaItem> expectedClinicalCriteriaItems) {
        Map<String, ClinicalCriteriaItem> actualMap = trialsSection.getCriteriaDataFromGridView().stream()
                .collect(Collectors.toMap(ClinicalCriteriaItem::getCriteriaDescription, Function.identity(),
                        (a, b) -> a));

        expectedClinicalCriteriaItems.forEach(expectedCriteria -> {
            ClinicalCriteriaItem actual = actualMap.get(expectedCriteria.getCriteriaDescription());

            assertThat(
                    STR."Criteria not found for description: \{expectedCriteria.getCriteriaDescription()} (status expected: \{expectedCriteria.getCriteriaStatus()})",
                    actual,
                    is(notNullValue()));

            LOGGER.debug(STR."Observed criteria description: \{actual.getCriteriaDescription()}");
            assertThat(STR."Criteria description mismatch for \{expectedCriteria.getCriteriaDescription()}",
                    actual.getCriteriaDescription(), is(expectedCriteria.getCriteriaDescription()));

            LOGGER.debug(STR."Observed criteria status: \{actual.getCriteriaStatus()}");
            assertThat(STR."Criteria status mismatch for \{expectedCriteria.getCriteriaStatus()}",
                    actual.getCriteriaStatus(), is(expectedCriteria.getCriteriaStatus()));

            LOGGER.debug(STR."Observed criteria type: \{actual.getCriteriaType()}");
            assertThat(STR."Criteria type mismatch for \{expectedCriteria.getCriteriaType()}",
                    actual.getCriteriaType(), is(expectedCriteria.getCriteriaType()));

            LOGGER.debug(STR."Observed eligibility value: \{actual.getEligibilityValue()}");
            assertThat("Criteria eligibility value mismatch",
                    areStringsSimilar(actual.getEligibilityValue(), expectedCriteria.getEligibilityValue()));
        });
    }

    public static boolean areStringsSimilar(String str1, String str2) {
        JaroWinklerSimilarity similarity = new JaroWinklerSimilarity();
        double score = similarity.apply(str1, str2);
        LOGGER.info(String.valueOf(score));
        if (score < 0.5) {
            String errorMessage = STR.
                    "ERROR: Strings are less than 60%% similar. Similarity score: \{score}%\nString 1: \{str1} \nString 2: \{str2}";
            LOGGER.info(errorMessage);
            return false;
        }
        return true;
    }

    public void checkObservedInGridViewVsExpectedEligibilityValuesInFile(String fileName) {
        Map<String, ClinicalCriteriaItem> actualMap = trialsSection.getCriteriaDataFromGridView()
                .stream()
                .collect(Collectors.toMap(ClinicalCriteriaItem::getCriteriaDescription, Function.identity(), (a, b) -> a));

        String file = String.valueOf(Paths.get(getSystemParameter(CTE_AI_BASE_PATH), STR."\{fileName}"));

        ExpectedCSVContainer.parseExpectedFile(file)
                .getExpectedCriteriaList()
                .forEach(expectedCriteria -> {
                    String expectedKey = expectedCriteria.getEligibilityKey();
                    ClinicalCriteriaItem actualItem = actualMap.get(expectedKey);

                    assertThat(STR."Criteria not found: \{expectedKey}", actualItem, notNullValue());

                    String actualEligibilityValue = actualItem.getEligibilityValue();

                    List<String> keywords = expectedCriteria.getKeywords();

                    boolean allKeywordsPresent = keywords.stream()
                            .allMatch(keyword -> actualEligibilityValue.toLowerCase().contains(keyword.toLowerCase()));

                    if (!allKeywordsPresent) {
                        LOGGER.error(STR."Mismatch for criteria '\{expectedKey}': actual value '\{actualEligibilityValue}'. Expected keywords: \{keywords}");
                    }
                 assertThat(STR."Eligibility value did not contain all expected values for: Expected keywords: \{keywords} Actual: \{actualEligibilityValue}",
                            allKeywordsPresent, is(true));
                });
    }

    private boolean checkEligibilityMatch(String expectedEligibilityValue, String actualEligibilityValue, String eligibilityKey) {

       if (expectedEligibilityValue == null || actualEligibilityValue == null) {
           LOGGER.warn(STR."Expected or actual eligibility value is null for key: \{eligibilityKey}");
           return false;
       }

        List<ExpectedCriteria> criteriaList = new ArrayList<>();
        if (expectedEligibilityValue.startsWith("regex:")) {
            String regexFileName = expectedEligibilityValue.substring("regex:".length());;
            String filePath = Paths.get(getSystemParameter(CTE_AI_BASE_PATH), regexFileName).toString();

            ExpectedCSVContainer container = ExpectedCSVContainer.parseExpectedFile(filePath);
            if (container.getExpectedCriteriaList() == null) {
                LOGGER.error(STR."Failed to parse expected criteria from file: \{filePath}");
                return false;
            }
            criteriaList = container.getExpectedCriteriaList();
        }
        return criteriaList.stream()
                        .filter(c -> c.getEligibilityKey().equals(eligibilityKey))
                        .allMatch(c -> {
                            List<String> cleanedKeywords = c.getKeywords().stream()
                                    .map(String::trim)
                                    .collect(Collectors.toList());

                            boolean allKeywordsPresent = cleanedKeywords.stream().allMatch(keyword -> {
                                Pattern keywordPattern = Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE);
                                return keywordPattern.matcher(actualEligibilityValue).find();
                            });

                            LOGGER.debug("Keywords match for '{eligibilityKey}': {allKeywordsPresent}");

                            if (!allKeywordsPresent) {
                                LOGGER.error("Mismatch for '{}': actual='{}', expected keywords={}", eligibilityKey, actualEligibilityValue, cleanedKeywords);
                                logTextDiff(cleanedKeywords, actualEligibilityValue);
                            }

                            return allKeywordsPresent;
                        });
    }

    private void logTextDiff(List<String> expectedKeywords, String actualText) {
        Set<String> missingInActual = expectedKeywords.stream()
                .filter(keyword -> {
                    Pattern pattern = Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE);
                    return !pattern.matcher(actualText).find();
                })
                .collect(Collectors.toSet());

        LOGGER.error("Words/phrases missing in actual: {}", missingInActual);
    }

    public void hoverOverEligibilityValuesByCriteriaDescription(String criteriaDescription) {
        LOGGER.debug(STR."The user hovers over the criteria : \{criteriaDescription}");
        trialsSection.hoverTheEligibilityValueByCriteriaDescription(criteriaDescription);
    }

    public void checkTooltipDisplayTheContentPeForCriteriaWithTitle(String eligibilityCriteriaTitle, String expectedToolTipContent) {
        String targetEligibilityValue = STR."//*[contains(@class,'GridRow-module_td_') and contains(.,'\{eligibilityCriteriaTitle}')]/ancestor::*[contains(@id,'tr_')]//span[contains(@class,'Nlp-module_ellipsis')]";
        WebElement targetElement = getDriver().findElements(By.xpath(targetEligibilityValue)).getFirst();
        String hoverScript = "var evObj = document.createEvent('MouseEvents');" +
                "evObj.initMouseEvent('mouseover', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
                "arguments[0].dispatchEvent(evObj);";
        ((JavascriptExecutor) getDriver()).executeScript(hoverScript, targetElement);

        //Wait for tooltip to be present in DOM
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        By tooltipLocator = By.cssSelector("[class^='Tooltip-module_container']");
        String actualToolTipContent = "";
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(tooltipLocator));
            WebElement tooltip = wait.until(ExpectedConditions.visibilityOfElementLocated(tooltipLocator));
            actualToolTipContent = tooltip.getText();
        } catch (TimeoutException e) {
            LOGGER.debug("Tooltip not visible. Trying to fetch via JS...");

            // Fallback to JS if tooltip is present but not visible
            String js = "var el = document.querySelector(\"[class^='Tooltip-module_container']\");" +
                    "return el ? el.innerText : null;";
            Object tooltipText = ((JavascriptExecutor) getDriver()).executeScript(js);
            LOGGER.debug(tooltipText != null ? tooltipText.toString() : "Tooltip not found");
        }
        LOGGER.debug(STR."Observed tooltip content was:\{actualToolTipContent}");
        assertThat(STR."The observed tooltip content was not as expected", actualToolTipContent, containsString(expectedToolTipContent));

    }

    public void checkSortParamInCriteriaTable(String columnName, String arrowDirection) {
        arrowDirection = arrowDirection.toLowerCase();
        String actualDirection = find( By.xpath( String.format( CRITERIA_COLUMN_SORT, columnName) ) ).getAttribute( "id" ).toLowerCase();
        assertThat( STR."The observed sort direction was not as expected", actualDirection.contains( arrowDirection ) );
    }

    public void checkSortOrderInCriteriaTable(final String columnName, final List<String> sortedValues) {
        final String normalizedColumnName = columnName.toLowerCase();

        switch (normalizedColumnName) {
            case STATUS_COLUMN -> {
                List<CriteriaStatus> expectedCriteriaStatusOrder = buildExpectedItemsForStatus(sortedValues);
                List<CriteriaStatus> actualCriteriaStatusOrder = getStatusFromTable()
                        .stream()
                        .map(CriteriaStatus::fromString)
                        .toList();
                AssertionUtils.assertExactOrder( STR."The observed sorted values in '\{columnName}' were not as expected. \nExpected order was :\{sortedValues}, \nBut ovserved was: \{actualCriteriaStatusOrder}", expectedCriteriaStatusOrder, actualCriteriaStatusOrder);
            }
            case TYPE_COLUMN -> {
                List<CriteriaType> expectedCriteriaTypeOrder = buildExpectedItemsForType(sortedValues);
                List<CriteriaType> actualCriteriaTypeOrder = getTypeValuesFromCriteriaTable().stream()
                        .map(CriteriaType::fromString)
                        .toList();
                AssertionUtils.assertExactOrder( STR."The observed sorted values in '\{columnName}' were not as expected. \nExpected order was :\{sortedValues}, \nBut ovserved was: \{actualCriteriaTypeOrder}", expectedCriteriaTypeOrder, actualCriteriaTypeOrder);
            }
            case ELIGIBILITY_VALUE_COLUMN -> {
                List<String> actualCriteriaEligibilityValueOrder = getEligibilityValueFromCriteriaTable();

                assertThat(
                        STR."The observed sorted values in '\{columnName}' were not as expected. \nExpected order was :\{sortedValues}, \nBut ovserved was: \{actualCriteriaEligibilityValueOrder}",
                        sortedValues.size(),
                        is(actualCriteriaEligibilityValueOrder.size())
                );

                for (int i = 0; i < sortedValues.size(); i++) {
                    String expected = sortedValues.get(i);
                    String actual   = actualCriteriaEligibilityValueOrder.get(i);

                    assertThat(
                            STR."At index \{i}, actual value '\{actual}' does not contain expected substring '\{expected}' (ignoring case)",
                            actual.toLowerCase(),
                            containsString(expected.toLowerCase())
                    );
                }
            }
            default -> throw new IllegalArgumentException(STR."Unsupported column name: \{columnName}");
        }
    }

    private List<CriteriaStatus> buildExpectedItemsForStatus(List<String> sortedValues) {
        return sortedValues
                .stream()
                .map(CriteriaStatus::fromString)
                .toList();
    }

    private List<CriteriaType> buildExpectedItemsForType(List<String> sortedValues) {
        return sortedValues
                .stream()
                .map(CriteriaType::fromString)
                .toList();
    }
}