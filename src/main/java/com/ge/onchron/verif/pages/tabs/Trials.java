package com.ge.onchron.verif.pages.tabs;


import com.ge.onchron.verif.model.clinicalConfiguration.AIIndicator;
import com.ge.onchron.verif.model.clinicalConfiguration.OverallMatchingScore;
import com.ge.onchron.verif.model.clinicalConfiguration.trial.*;
import com.ge.onchron.verif.pages.tabs.common.CommonTrialRadiology;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ge.onchron.verif.utils.DateUtil;

import java.util.*;
import java.util.stream.Collectors;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

public class Trials extends CommonTrialRadiology {

    protected static final Logger LOGGER = LoggerFactory.getLogger(Trials.class);

    protected static final String TRIALS_TAB_CLOSE_X_LOCATOR = "//button[contains(., 'Trials')]//*[starts-with(@id, 'Close')]";
    protected static final String TRIALS_NOTIFICATION_MESSAGE_XPATH = "//*[starts-with(@class,'NotificationDataElement-module_message_')]";

    //Overall Matching score is located independently
    @FindBy(xpath = "//*[contains(@class,'ReportMeta-module_two-column-generic-item_')]/child::*")
    protected List<WebElementFacade> trialMetadataItems;

    public void clickOnCloseTrialsTab() {
        getDriver().findElement(By.xpath(TRIALS_TAB_CLOSE_X_LOCATOR)).click();
    }

    public ClinicalTrialItemGridView getTrialGeneralInformationInGridViewBy() {
        return ClinicalTrialItemGridView.builder()
                .trialItem(
                        TrialItem.builder()
                                .trialName(configTitle.getText())
                                .description(configDescription.getText())
                                .overallMatchingScore(OverallMatchingScore.fromString(configCriteriaGridTopSectionWrapper.findElement(By.xpath(METADATA_OVERALL_MATCHING_SCORE_CLASS_LOCATOR)).getText()))
                                .clinicalTrialItemMetadata(getMetadata())
                                .build())
                .build();
    }

    private ClinicalTrialItemMetadata getMetadata() {
        ClinicalTrialItemMetadata clinicalTrialItemMetadata = new ClinicalTrialItemMetadata();
        clinicalTrialItemMetadata.setPhase(trialMetadataItems.get(1).getText());
        clinicalTrialItemMetadata.setRecruitmentStatus(ClinicalTrialItemMetadata.fromString(trialMetadataItems.get(3).getText()));
        clinicalTrialItemMetadata.setDate(DateUtil.getCriteriaDateTime(trialMetadataItems.get(5).getText()));
        clinicalTrialItemMetadata.setConfigId(trialMetadataItems.get(7).getText());
        return clinicalTrialItemMetadata;
    }
    @SneakyThrows
    public List<TrialItem> getListOfTrials() {
        waitUntilTrialLoadingSkeletonDisappears();
        return configListCardsChildItems.stream()
                .map(trialItem ->
                        createTrialsItem(trialItem.findElements(By.xpath("section/child::*"))))
                .collect(Collectors.toList());
    }

    private TrialItem createTrialsItem(List<WebElement> element) {

        List<String> metadataElements = List.of(element.get(2).getText().split("\n"));
        List<WebElement> aiIconsElements = element.get(1).findElements(By.xpath(BADGE_ROW_ITEMS_AI_ICON_LOCATOR));
        boolean displayed = false;
        if(!aiIconsElements.isEmpty()) {
            displayed = aiIconsElements.getFirst().isDisplayed();
        }
        return TrialItem.builder()
                .trialName(element.getFirst().getText())
                .description(element.get(3).getText())
                .overallMatchingScore(OverallMatchingScore.fromString(element.get(1).getText()))
                .clinicalTrialItemMetadata(ClinicalTrialItemMetadata.builder()
                        .configId(metadataElements.get(0))
                        .phase(String.format("%s, %s", metadataElements.get(1), metadataElements.size() > 2 ? metadataElements.get(2) : ""))
                        .recruitmentStatus(ClinicalTrialItemMetadata.fromString(metadataElements.size() > 3 ? metadataElements.get(3) : ""))
                        .build())
                .aiIndicator(AIIndicator.builder().isDisclaimerIconDisplayed(displayed).build())
                .build();
    }

    public String getTheTrialsProcessingNotificationMessage() {
            waitUntilTrialLoadingSkeletonDisappears();
            return getDriver().findElement(By.xpath(TRIALS_NOTIFICATION_MESSAGE_XPATH)).getText();
    }
}
