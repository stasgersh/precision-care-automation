package com.ge.onchron.verif.pages.tabs;

import com.ge.onchron.verif.model.clinicalConfiguration.treatment.ClinicalTreatmentItemGridView;
import com.ge.onchron.verif.model.clinicalConfiguration.treatment.TreatmentItem;
import com.ge.onchron.verif.model.clinicalConfiguration.AIIndicator;
import com.ge.onchron.verif.model.clinicalConfiguration.OverallMatchingScore;
import com.ge.onchron.verif.pages.tabs.common.CommonTrialRadiology;
import lombok.SneakyThrows;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class Radioligand extends CommonTrialRadiology {

    private static final Logger LOGGER = LoggerFactory.getLogger(Radioligand.class);

    @FindBy(xpath = "//*[contains(@class,'GridView-module_description_')]")
    private WebElementFacade trialDescription;

    @FindBy(xpath = "//*[contains(@class,'CardsMenu-module_card-item')]")
    protected List<WebElementFacade> treatmentModules;

    public static final String LIMITED_ROWS_ELEMENT_MODULE_CONTAINER_CLASS = "[class*='LimitedRowsElement-module_container'";

    public ClinicalTreatmentItemGridView getTreatmentGeneralInformationInGridViewBy() {
        return ClinicalTreatmentItemGridView.builder()
                .treatmentItem(
                        TreatmentItem.builder()
                                .treatmentName(configTitle.getText())
                                .description(configDescription.getText())
                                .overallMatchingScore(OverallMatchingScore.fromString(configCriteriaGridTopSectionWrapper.findElement(By.xpath(METADATA_OVERALL_MATCHING_SCORE_CLASS_LOCATOR)).getText()))
                                .build())
                .build();
    }

    @SneakyThrows
    public List<TreatmentItem> getListOfTreatments() {
        waitUntilTrialLoadingSkeletonDisappears();
        return treatmentModules.stream()
                .map(this::createTreatmentItem)
                .collect(Collectors.toList());
    }

    private TreatmentItem createTreatmentItem(WebElement element) {

        List<WebElement> aiIconsElements = element.findElements(By.xpath(BADGE_ROW_ITEMS_AI_ICON_LOCATOR));
        boolean displayed = false;
        if(!aiIconsElements.isEmpty()) {
            displayed = aiIconsElements.getFirst().isDisplayed();
        }
       return TreatmentItem.builder()
                .treatmentID(element.getAttribute("id"))
                .treatmentName(element.findElements(By.cssSelector(LIMITED_ROWS_ELEMENT_MODULE_CONTAINER_CLASS)).getFirst().getText())
                .treatmentMetadata(element.findElement(By.cssSelector( "[class*='RowItemsElement-module_row-item'")).getText())
                .description(element.findElements(By.cssSelector( LIMITED_ROWS_ELEMENT_MODULE_CONTAINER_CLASS)).getLast().getText())
                .overallMatchingScore(OverallMatchingScore.fromString(element.findElement(By.cssSelector( "[class*='IndicatorElement-module_indicators-container'")).getText()))
                .aiIndicator(AIIndicator.builder().isDisclaimerIconDisplayed(displayed).build())
                .build();
    }
}
