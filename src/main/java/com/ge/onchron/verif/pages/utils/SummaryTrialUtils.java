package com.ge.onchron.verif.pages.utils;

import com.ge.onchron.verif.model.clinicalConfiguration.OverallMatchingScore;
import com.ge.onchron.verif.model.clinicalConfiguration.RowBadgeItem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SummaryTrialUtils {

    private static final String CLINICAL_TRIAL_MATCHING_SCORE_LOCATOR =
            "descendant::*[contains(@class, 'badge-wrapper') and not(contains(@class,'badge-ai'))]";

    private static final String CLINICAL_TRIAL_CARD_CONTAINER_LOCATOR =
            "//*[contains(@class,'text-bold-16') and starts-with(text(),'Clinical')]/parent::*";
    //Clinical trial options - child elements:
    private static final String CLINICAL_TRIAL_TITLE_SELECTOR =
            "descendant::*[contains(@class,'text-bold-16') and contains(text(),'Clinical trial options')]";
    private static final String CLINICAL_TRIAL_LINKABLE_DESCRIPTION_SELECTOR =
            "descendant::*[contains(@class,'LimitedRowsElement-module_container_')]";
    private static final String CLINICAL_TRIAL_BADGE_ROW_ELEMENT =
            "descendant::*[contains(@class,'badge-row-items_')]";
    private static final String CLINICAL_TRIAL_AI_BADGE_LOCATOR =
            ".//span[contains(@class, 'IndicatorElement-module_table-ai-indicator')]";
    private static final String SEE_ALL_CLINICAL_TRIAL_OPTIONS_LINK_LOCATOR =
            "descendant::*[contains(@class,'SummaryItem-module_stick')]";
    private static String TREATMENTS_BASE_LOCATOR = "//*[@id=\"treatments\"]";


    public static RowBadgeItem getBadgeRowItems(WebElement element) {
        return RowBadgeItem.builder()
                .trialOverallMatchingScore(getClinicalTrialOverallMatchingScore(element))
                .isAiIconBadgeDisplayed(isOverallMatchingScoreAiIconDisplayed(element))
                .build();
    }

    public static boolean isOverallMatchingScoreAiIconDisplayed( WebElement element ) {
        return element.findElements( By.xpath( CLINICAL_TRIAL_AI_BADGE_LOCATOR ) )
                      .stream()
                      .findFirst()
                      .isPresent();
    }

    public static OverallMatchingScore getClinicalTrialOverallMatchingScore(WebElement webElement) {
        return OverallMatchingScore.fromString(webElement.getText());
    }

    public static String getClinicalTrialLinkableDescription(WebElement webElement) {
        return webElement.getText();
    }

}
