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
package com.ge.onchron.verif.howsteps.radioligand;

import com.ge.onchron.verif.howsteps.CommonSteps;
import com.ge.onchron.verif.howsteps.RestApiSteps;
import com.ge.onchron.verif.model.RestRequest;
import com.ge.onchron.verif.model.clinicalConfiguration.treatment.ClinicalTreatmentItemGridView;
import com.ge.onchron.verif.model.clinicalConfiguration.OverallMatchingScore;
import com.ge.onchron.verif.model.clinicalConfiguration.treatment.TreatmentItem;
import com.ge.onchron.verif.pages.tabs.Radioligand;
import com.ge.onchron.verif.pages.tabs.common.CommonTrialRadiology;
import com.ge.onchron.verif.utils.RequestSpecificationUtils;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Steps;
import org.apache.commons.compress.utils.Lists;
import org.hamcrest.Matchers;
import org.jbehave.core.model.ExamplesTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ge.onchron.verif.TestSystemParameters.*;
import static io.restassured.http.Method.POST;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RadioligandTxSteps extends CommonTrialRadiology {

    private static final Logger LOGGER = LoggerFactory.getLogger(RadioligandTxSteps.class);

    public static final String RADIOLIGAND_TAB_CLOSE_X_LOCATOR = "//button[contains(., 'Radioligand')]//*[starts-with(@id, 'Close')]";
    private static final String CONFIG_TYPE_BASE_PATH = STR."\{CONFIG_BASE_PATH}/{configType}";

    private Radioligand radioligandSection;
    @Steps
    private RestApiSteps restApiSteps;
    @Steps
    private CommonSteps commonSteps;

    public void checkRadioligandViewVisibility( boolean isDisplayed ) {
        boolean radioligandTxTabVisible = radioligandSection.isVisible();
        LOGGER.info(STR."Radioligand Tx view visibility is: \{radioligandTxTabVisible }");
        assertThat( "Visibility of the Radioligand Tx is not ok.", radioligandTxTabVisible, is( equalTo( isDisplayed ) ) );
    }

    public void clickOnXToCloseRadioligandTab() {
        getDriver().findElement(By.xpath(RADIOLIGAND_TAB_CLOSE_X_LOCATOR)).click();
    }
    public void clickOnTreatmentByID(final String treatmentId) {
        WebElement treatmentWebElement = fetchTreatmentByID(treatmentId.trim());
        assertThat("TreatmentID was not found", treatmentWebElement, is(notNullValue()));
        treatmentWebElement.click();
    }

    public WebElement fetchTreatmentByID(String treatmentId) {
        return configurationListCardsItem.findElement(By.id(STR."card-item-\{treatmentId}"));
    }

    public void checkForListOfTreatments(int sizeOfTreatments) {
        List<TreatmentItem> treatmentItemList = radioligandSection.getListOfTreatments();
        LOGGER.info(STR."Observed clinical treatments were:\n\{treatmentItemList.stream().map(TreatmentItem::toString)
                .collect(Collectors.joining("\\n"))}");
        assertThat("Some treatments were missing", treatmentItemList.size(), is(equalTo(sizeOfTreatments)));
    }

    public void checkConfidenceScoreForTreatments(ExamplesTable expectedTreatmentConfidenceScores) {
        List<TreatmentItem> expectedTreatmentItems = Lists.newArrayList();
        expectedTreatmentConfidenceScores.getRows()
                .forEach(linkedMap ->
                        expectedTreatmentItems.add(TreatmentItem.builder()
                                .treatmentID(STR."card-item-\{linkedMap.get("treatment_id")}")
                                .overallMatchingScore(
                                        OverallMatchingScore.fromString(linkedMap.get("Matching_Confidence_Score")))
                                .build()));

        List<TreatmentItem> actualTreatmentItems = radioligandSection.getListOfTreatments();
        LOGGER.info(STR."Observed clinical treatments were:\n\{actualTreatmentItems.stream().map(TreatmentItem::toString)
                .collect(Collectors.joining("\\n"))}");

        expectedTreatmentItems.forEach(expectedTreatment -> {
            boolean matchFound = actualTreatmentItems.stream()
                    .anyMatch(actualTreatmentItem ->
                            actualTreatmentItem.getTreatmentID().equals(expectedTreatment.getTreatmentID()) &&
                                    actualTreatmentItem.getOverallMatchingScore().getMatchingScore().equals(expectedTreatment.getOverallMatchingScore().getMatchingScore())
                    );
            String errorMessage = STR."Matching treatment item was not found for config ID: " +
                    expectedTreatment.getTreatmentID() +
                    " and Matching Score: " + expectedTreatment.getOverallMatchingScore().getMatchingScore();
            assertThat(errorMessage, matchFound, is(true));
        });
    }

    public void createNewGeneratedTrialConfig(String treatmentData, String configType) {
        LOGGER.debug("Submit POST request to create new configuration");
        RequestSpecification rs = RequestSpecificationUtils
                .getClinicalTrialRequestSpecification( CONFIG_TYPE_BASE_PATH.replaceAll("\\{configType}", configType))
                .body(treatmentData);
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(POST, rs));
    }

    public void checkBasicInformationInGridViewForTreatmentID(final List<Map<String, String>> expectedRowsData) {
        ClinicalTreatmentItemGridView clinicalTreatmentItemGridView = radioligandSection.getTreatmentGeneralInformationInGridViewBy();
        TreatmentItem actualTreatmentItem = clinicalTreatmentItemGridView.getTreatmentItem();

        Map<String, String> expectedData = expectedRowsData
                .stream()
                .collect(Collectors.toMap(
                        itemValue -> itemValue.get("Field"),
                        itemValue -> itemValue.get("Field content")));

        assertThat("Treatment Name was not the same or missing",
                actualTreatmentItem.getTreatmentName(), Matchers.is(expectedData.get("Treatment name")));
        assertThat("Treatment Description was not the same or missing",
                actualTreatmentItem.getDescription(), Matchers.is(expectedData.get("Treatment description")));
        assertThat("Treatment Matching score was not the same or missing",
                actualTreatmentItem.getOverallMatchingScore().getMatchingScore(), Matchers.is(expectedData.get("Matching score")));
    }
}