package com.ge.onchron.verif.pages.tabs.common;

import com.ge.onchron.verif.model.clinicalConfiguration.AIIndicator;
import com.ge.onchron.verif.model.clinicalConfiguration.CriteriaStatus;
import com.ge.onchron.verif.model.clinicalConfiguration.CriteriaTableHeaderItem;
import com.ge.onchron.verif.model.clinicalConfiguration.CriteriaType;
import com.ge.onchron.verif.model.clinicalConfiguration.trial.*;
import com.ge.onchron.verif.pages.SimplePage;
import com.ge.onchron.verif.pages.elements.tooltip.TooltipElement;
import com.ge.onchron.verif.pages.utils.PageUtils;
import com.ibm.icu.impl.Assert;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.webdriver.WebDriverFacade;
import org.apache.commons.compress.utils.Lists;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

import static com.ge.onchron.verif.pages.utils.PageElementUtils.*;
import static com.ge.onchron.verif.utils.Utils.waitMillis;
import static java.time.temporal.ChronoUnit.SECONDS;
import static junit.framework.TestCase.fail;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;

public class CommonTrialRadiology extends SimplePage {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonTrialRadiology.class);

    protected static final String BADGE_ROW_ITEMS_AI_ICON_LOCATOR = "//span[contains(@class, 'IndicatorElement-module_table-ai-indicator')]";

    protected static final String GRID_DETAILS_ID = "grid-details";
    protected static final String AI_ICON_ID = "ico-ai-16";
    //Trials list view locators
    protected static final String CONFIGURATION_CARD_ITEM = "//*[contains(@class,'CardsMenu-module_card-item')]";
    //new locator
    //protected static final String CONFIGURATION_CARD_ITEM = "//*[starts-with(@id,'card-item-')]";
    protected static final String METADATA_ROW_ITEM = "descendant::*[contains(@class,'RowItemsElement-module_row-item-wrapper_')]";
    protected static final String METADATA_OVERALL_MATCHING_SCORE_CLASS_LOCATOR = "descendant::*[starts-with(@class,'IndicatorElement-module_indicators-container_')][1]";
    //Criteria grid view per Trial
    protected static final String REPORT_METADATA_WRAPPER = "descendant::*[starts-with(@class,'ReportMeta-module_report-meta-wrapper_')]";
    protected static final String AI_HIGHLIGHTED_LOCATOR_IN_EXPENDED = "descendant::*[contains(@class,'module_nlp-highlight-container')]";
    protected static final String INITIAL_SELECTION_DESCENDANT = "section[@id='initialSelection']/descendant";
    protected static final String CRITERIA_EXPANDED_ROWS_VIEW = "//table[contains(@class, 'expanded-table')]";
    // Base reusable fragment
    protected static final String PARAGRAPH_WITH_BOLD_CLASS = "//tbody/tr[starts-with(@class,'GridRow-module_hide-border')]//*[contains(@class, 'BoldTextElem-module_paragraph') and contains(text(), '%s')]";
    // Targeting just the title node
    protected static final String SUB_CRITERIA_TITLE_TEMPLATE = STR."\{PARAGRAPH_WITH_BOLD_CLASS}";
    protected static final String SUB_CRITERIA_TYPE_BY_TITLE = STR."\{SUB_CRITERIA_TITLE_TEMPLATE}/ancestor::tr/td[3]/p[contains(@class,'TextElement-module_paragraph')]";
    protected static final String SUB_CRITERIA_ELIGIBILITY_VALUES_BY_TITLE = STR."\{SUB_CRITERIA_TITLE_TEMPLATE}/ancestor::tr/td[4]/descendant::*[contains(@class,'Nlp-module_print-only_')]";
    // Logical operator following the title
    protected static final String SUB_CRITERIA_TITLE_FOLLOWED_BY_LOGICAL_OPERATOR_PATH = "//following-sibling::tr[1]//section[contains(@class, 'ExpandRow-module_expanded-content-operator')]";
    protected static final String CRITERIA_COLUMN_XPATH = "descendant::*[contains(@class,'Grid-module_table-header_-qol1') and text()= '%s']";

    protected static final String TREATMENT_ELIGIBILITY_MODULE_CSS_WRAPPER = "[class^=GridView-module_grid-details-wrapper]";
    protected static final String EMPTY_STATE_CONTAINER_CSS = "[class*='EmptyState-module_container_']";
    protected static final String LOADING_SKELETON_GRID_VIEW_CSS_SELECTOR = "[class*='GridViewSkeleton-module_grid-view-skeleton_']";
    protected static final String LOADING_SKELETON_CARDS_CSS_MENU_SELECTOR = "[class*='TreatmentEligibility-module_treatment-eligibility-menu']";
    protected static final String LOADING_SKELETON_TRIALS_VIEW_XPATH_SELECTOR = "//*[text()='Loading...']";
    //Filter locators
    protected static final String  FILTER_STATUS_CHECKBOX_PATH = "//span[contains(@class,'checkbox__label')][text()='status_text']";
    protected static final String  CRITERIA_EXPAND_LOCATOR_BY_CRITERIA_NAME = "descendant::*[contains(section,'%s')]/ancestor::tr/td[1]/p[contains(@class,'TextElement-module_paragraph')]";
    protected static final String  EXPAND_COLLAPSE_ARROW_FOR_CRITERIA_TITLE_PATH = "//tbody/descendant::*[contains(section,'%s')]/ancestor::tr//*[@id='Layer_1']";
    protected static final String  ARROW_SORT_BUTTON_LOCATOR = "descendant::*[text()='%s']/child::*";
    protected static final String  CRITERIA_COLUMN_SORT = "//*[contains(@class,'Grid-module_table_')]//*[text()='%s']//*[contains(@id,'Arrow_')]";
    protected static final String  CRITERIA_TITLE_IN_GRID_VIEW = "descendant::*[starts-with(@id,'tr_')]/child::td[1]";
    protected static final String  HIGHLIGHT_CONTAINER_LOCATOR = "//*[contains(@class,'Nlp-module_container_')]";
    protected static final String  PRINT_ONLY_ELEMENT_WITH_TEXT = "//*[contains(@class,'Nlp-module_print-only') and contains(text(),'%s')]";
    protected static final String  MATCHING_STATUS_LOCATOR = "//span[contains(@class,' IndicatorElement-module_indicator') and not(contains(@class,'ai-indicator'))]";

    private TooltipElement tooltip;
    //---Trial Cards view--------------------------------------------------------------------//
    @FindBy(css = "[class*='Grid-module_table']")
    protected WebElementFacade configTable;

    @FindBy(xpath = "//*[contains(@class,'CardsMenu-module_menu-wrapper')]")
    protected WebElementFacade configurationListCardsItem;

    @FindBy(xpath = CONFIGURATION_CARD_ITEM +"/child::*")
    //@FindBy(xpath = CONFIGURATION_CARD_ITEM)
    protected List<WebElementFacade> configListCardsChildItems;

    @FindBy(xpath = "//*[contains(@class,'CardsMenu-module_card-item')]")
    protected WebElementFacade configModuleSingleCard;

    //---Criteria grid view Main Section------------------------------------------------------//
    @FindBy(xpath = "//*[starts-with(@class,'GridView-module_grid_')]")
    protected WebElementFacade criteriaGridListView;

    @FindBy(xpath = "//*[contains(@class,'GridView-module_grid-view-wrapper_')]")
    protected WebElementFacade configGridViewMainWrapper;

    @FindBy(id = GRID_DETAILS_ID)
    protected WebElementFacade configCriteriaGridTopSectionWrapper;

    @FindBy(xpath = "//*[contains(@class,'meta-wrapper')]")
    protected WebElementFacade criteriaGridTopSectionReportWrapper;

    @FindBy(xpath = "//*[starts-with(@class,'GridView-module_event-title_')]")
    protected WebElementFacade configTitle;

    @FindBy(xpath = "//*[contains(@class,'GridView-module_description_')]")
    protected WebElementFacade configDescription;

    @FindBy(xpath = "//*[contains(@class,'GridView-module_ai-disclaimer_')]")
    protected WebElementFacade aiDisclaimer;

    @FindBy(xpath = "//*[contains(@class,'GridView-module_ai-disclaimer-text_')]")
    protected WebElementFacade aiDisclaimerText;

    //---Criteria grid view Table Section---------------------------------------------------//
    @FindBy(xpath = "//*[contains(@class,'GridRow-module_hover-row_')]")
    protected List<WebElementFacade> criteriaRows;

    @FindBy(xpath = "//span[contains(@class,'filter-tag--label')]")
    protected List<WebElementFacade> filterByLabels;

    @FindBy(xpath = "//section[contains(text(),'Clear all')]")
    protected WebElementFacade filterClearAllButton;

    @FindBy(xpath = "//section[contains(text(),'Filtered by')]")
    protected WebElementFacade filterByText;

    @FindBy(xpath = "//*[contains(@class,'Grid-module_grid-wrapper_')]")
    protected WebElementFacade trialCriteriaGridModuleWrapper;

    @FindBy(xpath = "//*[contains(@class,'text-16 table Grid-module_table_')]/descendant::*/th")
    protected List<WebElementFacade> configCriteriaGridHeaders;

    @FindBy(xpath = "//table[contains(@class,'table Grid-module_table_')]//th")
    protected List<WebElement> configCriteriaHeaders;

    @FindBy(xpath = "//*[starts-with(@class,'Grid-module_filter-menu_')]/descendant::*[starts-with(@for,'filter_item_')]")
    protected List<WebElementFacade> filterMenuOptions;

    @FindBy(xpath = "//tbody"+MATCHING_STATUS_LOCATOR)
    protected List<WebElementFacade> rowsStatusResults;

    @FindBy(xpath = "//tbody/tr[contains(@id,'tr')]/td[4]")
    protected List<WebElementFacade> criteriaTypeResults;

    @FindBy(xpath = "//tbody/tr[contains(@id,'tr')]/td[5]")
    protected List<WebElementFacade> criteriaEligibilityValue;

    @FindBy(xpath = "//tbody/descendant::*[contains(@class,'module_text_limited')]")
    protected List<WebElementFacade> complexCriteriaTitles;

    //TODO: check against latest UI and remove one of these two expandedRowsElements
    @FindBy(xpath = CRITERIA_EXPANDED_ROWS_VIEW + "//tr")
    protected List<WebElementFacade> expandedRowsElements;

    @FindBy(xpath = CRITERIA_EXPANDED_ROWS_VIEW + "/descendant::*/section/p[1]")
    protected List<WebElementFacade> expandedRowsElementsNew;

    @FindBy(xpath = CRITERIA_EXPANDED_ROWS_VIEW + "/descendant::*/td[2]")
    protected List<WebElementFacade> expandedRows;

    @FindBy(xpath = "//section[contains(@class, 'ExpandRow-module_expanded-content-row_')]//tbody//section[contains(@class, 'sv-container-wrapper')]")
    protected List<WebElementFacade> expandedTitlesElements;

    @FindBy(xpath = "//table[contains(@class, 'expanded-table')]/descendant::td[4]")
    protected List<WebElementFacade> expandedEligibilityValues;

    public boolean isVisible() {
        waitForAngularRequestsToFinish();
        try {
            WebElement contentElement = getNonStaleElement( By.cssSelector( TREATMENT_ELIGIBILITY_MODULE_CSS_WRAPPER ) );
            return contentElement.isDisplayed();
        } catch ( NoSuchElementException e ) {
            try {
                WebElement emptyStateElement = getNonStaleElement( By.cssSelector( EMPTY_STATE_CONTAINER_CSS ) );
                return emptyStateElement.isDisplayed();
            } catch ( NoSuchElementException ex ) {
                return false;
            }
        }
    }

    public boolean isExpandOptionForCriteriaNameDisplay(final String criteriaTitle) {
        return trialCriteriaGridModuleWrapper
                .findElements(By.xpath(String.format(CRITERIA_EXPAND_LOCATOR_BY_CRITERIA_NAME, criteriaTitle))).isEmpty();
    }

    public boolean isSectionFilterByExistsInTrialGridView() {
        waitForAngularRequestsToFinish();
        return isElementVisible(find(By.xpath("//*[contains(text(),'Filtered by')]")));
    }

    public void waitUntilLoadingSkeletonDisappears() {
        setImplicitTimeout(0, SECONDS);
        Wait<WebDriver> wait = new FluentWait<>(getDriver())
                .withTimeout(Duration.ofSeconds(60))
                .pollingEvery(Duration.ofMillis(100))
                .ignoring(NoSuchElementException.class);
        try {
            wait.until(
                    ExpectedConditions.and(
                            invisibilityOfElementLocated(By.cssSelector(LOADING_SKELETON_GRID_VIEW_CSS_SELECTOR)),
                            invisibilityOfElementLocated(By.cssSelector(LOADING_SKELETON_CARDS_CSS_MENU_SELECTOR))));
        } catch (Exception e) {
            Assert.fail(
                    STR."Skeleton Grid View did not disappear: "
                            + LOADING_SKELETON_GRID_VIEW_CSS_SELECTOR + "\n"
                            + "Or Skeleton Cards View did not disappear: " + LOADING_SKELETON_CARDS_CSS_MENU_SELECTOR + "\n" + e);
        } finally {
            resetImplicitTimeout();
        }
    }
    public void waitUntilTrialLoadingSkeletonDisappears() {
        setImplicitTimeout(0, SECONDS);

        Wait<WebDriver> wait = new FluentWait<>(getDriver())
                .withTimeout(Duration.ofSeconds(60))
                .pollingEvery(Duration.ofMillis(100))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        try {
            wait.until(driver -> {
                List<WebElement> elements = driver.findElements(By.xpath(LOADING_SKELETON_TRIALS_VIEW_XPATH_SELECTOR));
                return elements.isEmpty();
            });
        } catch (Exception e) {
            Assert.fail(STR."Loading text did not disappear from DOM: \n\{e.getMessage()}");
        } finally {
            resetImplicitTimeout();         }
    }

    private ClinicalTrialItemMetadata createTrialItemMetadata(List<WebElement> elements) {
        return ClinicalTrialItemMetadata.builder()
                .configId(elements.getFirst().getText())
                .recruitmentStatus(getRecruitmentStatus(elements.get(1)))
                .phase(elements.getLast().getText())
                .build();
    }

    private ClinicalTrialItemMetadata.RecruitmentStatus getRecruitmentStatus(WebElement element) {
        return Arrays.stream(ClinicalTrialItemMetadata.RecruitmentStatus.values())
                .filter(meta -> meta.getStatus().equals(element.getText()))
                .findFirst().get();
    }

    public WebElement fetchElementByConfigurationID(String configID) {
        List<WebElement> webElements = configurationListCardsItem.thenFindAll(By.xpath(CONFIGURATION_CARD_ITEM))
                .stream()
                .map(element -> element.findElement(By.xpath(METADATA_ROW_ITEM)))
                .toList();
        return webElements
                .stream()
                .filter(element -> element.getText().contains(configID))
                .toList().getFirst();
    }


    public byte[] takeScreenShotOfTrialWithTitle(String title) {
        return getDriver().findElement(By.xpath(String.format("//*[contains(text(), '%s')]", title)))
                .getScreenshotAs(OutputType.BYTES);
    }

    public byte[] takeScreenShotOfTrialDescription(String description) {
        return getDriver().findElement(By.xpath(String.format("//*[starts-with(@class,'LimitedRowsElement-module_text_limited_rows') and contains(.,'%s')]", description)))
                .getScreenshotAs(OutputType.BYTES);
    }

    public void scrollTrialsInTabToTheButton() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        Long clientHeight = (Long) js.executeScript("return arguments[0].clientHeight;",
                configurationListCardsItem.getElement());
        if (clientHeight != null) {
            LOGGER.info(STR."The actual height in pixels, is :\n\{clientHeight}");
        } else {
            LOGGER.info("Scroll height is null or element not found.");
        }
        String javascriptCmd = String.format("arguments[0].scrollTo(%s,%s)", 0, clientHeight);
        WebDriver webdriver = Serenity.getWebdriverManager().getWebdriver();
        ((WebDriverFacade) webdriver).executeScript(javascriptCmd, configurationListCardsItem);
        waitMillis(600);
    }

    public void scrollTrialsInTabToTop() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollTo(0,0);", configurationListCardsItem.getElement());
        waitMillis(600);
    }

    public ClinicalTrialItemGridView getAiIndicatorsInGridView() {
        waitMillis(5000);
        return ClinicalTrialItemGridView.builder()
                .trialItem(
                        TrialItem.builder()
                                .aiIndicator(AIIndicator.builder()
                                        .isOverallMatchingScoreAiIconDisplayed(
                                                criteriaGridTopSectionReportWrapper.findElement(By.id(AI_ICON_ID)).isDisplayed())
                                        .isDisclaimerIconDisplayed(
                                                aiDisclaimer.findElement(By.id(AI_ICON_ID)).isDisplayed())
                                        .aiDisclaimerDescription(
                                                aiDisclaimerText.getText())
                                        .isAIHighlighted(
                                                !aiDisclaimerText.findElement(By.tagName("mark")).getText().isEmpty())
                                        .build())
                                .build())
                .build();
    }

    public List<CriteriaTableHeaderItem> getCriteriaColumnNames() {
        List<CriteriaTableHeaderItem> criteriaTableHeaderItems = Lists.newArrayList();
        configCriteriaGridHeaders
                .forEach(element -> {
                    criteriaTableHeaderItems.add(CriteriaTableHeaderItem.builder()
                            .columnName(
                                    CriteriaTableHeaderItem.CriteriaItemHeader.fromString(element.getText()))
                            .build());
                });
        return criteriaTableHeaderItems.stream()
                .filter(criteriaTableHeaderItem -> Objects.nonNull(criteriaTableHeaderItem.getColumnName()))
                .collect(Collectors.toList());
    }

    public List<ClinicalCriteriaItem> getCriteriaNameFromGridView() {
        List<ClinicalCriteriaItem> criteriaNamesFromGrid = Lists.newArrayList();
        trialCriteriaGridModuleWrapper.thenFindAll(By.xpath(CRITERIA_TITLE_IN_GRID_VIEW)).stream().forEach(element -> {
            criteriaNamesFromGrid.add(ClinicalCriteriaItem.builder().criteriaDescription(element.getText()).build());
        });
        return criteriaNamesFromGrid;
    }

    public void clickOnCriteriaGridAria() {
        configCriteriaGridTopSectionWrapper.click();
    }

    public void clickOnFilterButtonFoColumn(String columnName) {
        configCriteriaHeaders
                .stream()
                .forEach(specificColumn -> {
                    if (!specificColumn.findElements(By.xpath(String.format(CRITERIA_COLUMN_XPATH, columnName))).isEmpty()) {
                        specificColumn.findElements(By.id("Filter_-_16")).getFirst().click();
                    }
                });
    }

    public void clickOnSortForColumn(String columnName) {
        configCriteriaGridHeaders
                .stream()
                .filter(headerElement -> headerElement.getText().equals(columnName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(STR."The following column was not found: \n" + columnName))
                .then().thenFindAll(By.xpath(String.format(ARROW_SORT_BUTTON_LOCATOR, columnName))).getFirst().click();
    }

    public List<String> getFilterValues() {
        return getTextOfElementsList(filterMenuOptions);
    }

    public List<String> getFilteredBySelectedLabels() {
        return getTextOfElementsList(filterByLabels);
    }

    public void clickOnClearAllButton() {
        filterClearAllButton.click();
    }

    public boolean isFilterByTextDisplayed() {
        return getDriver()
                .findElements(By.xpath("//section[contains(text(),'Filtered by')]"))
                .stream()
                .findFirst().isEmpty();
    }

    public List<String> getStatusFromTable() {
        return getTextOfElementsList(rowsStatusResults);
    }

    public List<String> getTypeValuesFromCriteriaTable() {
        return getTextOfElementsList(criteriaTypeResults);
    }

    public List<String> getEligibilityValueFromCriteriaTable() {
        return getTextOfElementsList(criteriaEligibilityValue);
    }

    public void clickOnFilterStatusCheckbox(final String status) {
        getDriver().findElement(By.xpath(FILTER_STATUS_CHECKBOX_PATH.replaceAll("status_text", status)))
                .click();
    }
    //replace when new webapp
    /*public void clickOnFilterStatusCheckbox(final String status) {

    public void clickOnFilterStatusCheckbox(final String status) {
        By byStatusText = By.xpath(FILTER_STATUS_CHECKBOX_PATH.replace("status_text", status));
        LOGGER.info(STR."byStatusText:::\{byStatusText}");
        getDriver().findElement(byStatusText).click();

    }*/

    public List<String> getTitlesOfComplexCriteriaFromTable() {
        return getTextOfElementsList(complexCriteriaTitles);
    }

    public void clickExpandArrowButtonOnCriteria(final String criteriaTitle) {
        waitForAngularRequestsToFinish();
        criteriaGridListView.thenFindAll(By.xpath("//*[contains(@id,'tr_')]"))
                .stream()
                .filter(trialTableElement -> trialTableElement.getText().contains(criteriaTitle))
                .findFirst()
                .ifPresentOrElse(
                        trialTableElement -> trialTableElement.thenFindAll(By.id("Layer_1")).getFirst().click(),
                        () -> fail(STR."Unable to expand the criteria with title:\{criteriaTitle}"));

    }

    public void clickCollapseArrowButtonOnCriteria(final String criteriaTitle) {
        waitForAngularRequestsToFinish();
        String fullXpath = String.format(EXPAND_COLLAPSE_ARROW_FOR_CRITERIA_TITLE_PATH, criteriaTitle);
        dispatchMouseClickElementJS(getDriver().findElement(By.xpath(fullXpath)));
    }

    public List<String> getExpandedRowTitles() {
        return expandedRowsElementsNew
                .stream()
                .map(WebElementFacade::getText)
                .collect(Collectors.toList());
    }

    public List<String> getExpandedRowSubTitles() {
        return expandedRowsElements
                .stream()
                .map(expRow -> expRow.findBy(By.xpath("//td[1]//section//p[2]")).getText()).collect(Collectors.toList());
    }

    public List<String> getExpandedRowStatuses() {
        return expandedRows
                .stream()
                .map(expRow -> expRow.findElement(By.xpath("descendant::*/span[1]"))
                        .getText()).collect(Collectors.toList());
    }

    public List<WebElementFacade> getExpandedRows() {
        return expandedRowsElements
                .stream()
                .filter(findBy("//td[2]//section"))
                .collect(Collectors.toList());
    }

    public List<String> getExpandedRowSTypes() {
        return expandedRowsElements
                .stream()
                .map(expRow -> expRow.findBy(By.xpath("//td[3]//p")).getText()).collect(Collectors.toList());
    }

    public String getExpandedRowSTypesByTitle(final String subCriteriaTitle) {
        return getExpandedRowTextString(subCriteriaTitle, SUB_CRITERIA_TYPE_BY_TITLE);
    }

    public String getExpandedRowSEligibilityValuesByTitle(final String subCriteriaTitle) {
        return getExpandedRowTextString(subCriteriaTitle, SUB_CRITERIA_ELIGIBILITY_VALUES_BY_TITLE);
    }

    private String getExpandedRowTextString(String subCriteriaTitle, String subCriteriaTypeByTitle) {
       return expandedRowsElementsNew
                .stream()
                .map(expRow -> {
                    try {
                        String fullXpath = String.format(subCriteriaTypeByTitle, subCriteriaTitle);
                        LOGGER.debug(STR."Full xpath for expanded row sub-criteria : \{fullXpath}");
                        return expRow.findElement(By.xpath(fullXpath)).getAttribute("textContent");
                    } catch (NoSuchElementException e) {
                        LOGGER.debug(STR."No element found for title: \{subCriteriaTitle}");
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(STR."NO Status was found for sub-criteria with title: \{subCriteriaTitle}");
    }

    public List<String> getRowsEligibilityValues() {
        return expandedRowsElements
                .stream()
                .map(expRow -> expRow.findBy(By.xpath("//td[5]")).getText()).collect(Collectors.toList());
    }

    private List<String> getTextOfElementsList(List<WebElementFacade> elements) {
        return elements
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<WebElementFacade> getListOfExpandedStatusesWhichHasAiIconIndication() {
        return expandedRowsElements.stream()
                .filter(section -> !section.findElements(By.id("ico-ai-16")).isEmpty())
                .collect(Collectors.toList());
    }
    public List<WebElementFacade> getListOfExpandedEligibilityValuesWhichHasAiIconIndication() {
        return configTable.thenFindAll(By.xpath("//*[contains(@id,'tr_')]")).stream().filter(value ->
                        !value.thenFindAll(By.xpath("//*[contains(@class,'lpInfo-module_nlp-info-title')]")).isEmpty())
                .collect(Collectors.toList());
    }

    public List<WebElementFacade> getListOfExpandedCardsOnMenuWhichHasAiIconIndication() {
        return configurationListCardsItem.thenFindAll(By.xpath(CONFIGURATION_CARD_ITEM)).stream().filter(value ->
                        !value.thenFindAll(By.xpath("//*[contains(@class,'CardItem-module_card-item-wrapper')]")).isEmpty())
                .collect(Collectors.toList());
    }

    public List<WebElementFacade> getListOfExpandedPatientValuesWhichHighlightedAsAiIndication(String searchableValue) {
        return expandedRowsElements.stream()
                .filter(section -> !section.findElements(
                        By.xpath(HIGHLIGHT_CONTAINER_LOCATOR
                                + String.format(PRINT_ONLY_ELEMENT_WITH_TEXT, searchableValue))).isEmpty())
                .collect(Collectors.toList());
    }

    public boolean isPatientValueHighlightedForKey(String expectedKey) {
        return getListOfExpandedPatientValuesWhichHighlightedAsAiIndication(expectedKey)
                .stream()
                .anyMatch(section -> {
                    boolean containsText = section.containsText(expectedKey);
                    LOGGER.info(STR."The actual displayed expanded rows titles in table:\n\{section.getText()}");
                    return containsText;
                });

    }

    public boolean isExpandedTitleFollowedByLogicalOperator(String title, String operator) {

        Optional<WebElementFacade> matchingElement = expandedTitlesElements.stream()
                .filter(el -> el.getText().contains(title))
                .findFirst();

        if (matchingElement.isEmpty()) {
            LOGGER.info(STR."No expanded title element found containing '\{title}'");
            return false;
        }

        String observedOperator;
        try {
            WebElement operatorElement = matchingElement.get()
                    .findElement(By.xpath(PageUtils.formatXPath(SUB_CRITERIA_TITLE_FOLLOWED_BY_LOGICAL_OPERATOR_PATH, title)));
            observedOperator = operatorElement.getText();
        } catch (NoSuchElementException e) {
            LOGGER.error(STR."Operator element not found for title '\{title}'");
            return false;
        }
        LOGGER.info(STR."The '\{title}' was expanded and the operator was '\{observedOperator}'");
        return operator.equals(observedOperator);
    }

    public String getCollapsedCriteriaStatusByCriteriaTitle(String criteriaTitle) {
        return criteriaRows
                .stream()
                .findAny().get()
                .findElement(By.xpath(STR."/descendant::*[starts-with(text(),'\{criteriaTitle}')]/ancestor::tr/descendant::*" + MATCHING_STATUS_LOCATOR))
                .getText();
    }

    public List<ClinicalCriteriaItem> getCriteriaDataFromGridView() {
        final String expandableRowClass = "GridRow-module_expandable-row_";
        return criteriaRows.stream()
                .map(row -> {
                    boolean isExpandable = !row.findElements(By.xpath(STR."//*[contains(@class,'\{expandableRowClass}')]")).isEmpty();
                    return parseCriteriaRow(row, isExpandable);
                })
                .collect(Collectors.toList());

    }

    private ClinicalCriteriaItem parseCriteriaRow(WebElementFacade row, boolean isExpandable) {
        int offset = isExpandable ? 1 : 0;

        return ClinicalCriteriaItem.builder()
                .criteriaDescription(row.find(By.xpath(STR."td[\{1 + offset}]")).getText())
                .criteriaStatus(CriteriaStatus.fromString(row.find(By.xpath(STR."td[\{2 + offset}]")).getText()))
                .criteriaType(CriteriaType.fromString(row.find(By.xpath(STR."td[\{3 + offset}]")).getText()))
                .eligibilityValue(row.find(By.xpath(STR."td[\{4 + offset}]")).getText())
                .build();
    }
    // Step 1: criteriaRows - returns all the rows in the table
    // Step 2: Loop through each row and check td[2] for "Title" (e.g.:"Gender")
    public void hoverTheEligibilityValueByCriteriaDescription(String criteriaDescription) {
        for (WebElementFacade row : criteriaRows) {
            WebElementFacade eligibilityCriteriaTitle = row.then(By.xpath("./td[2]"));
            if (eligibilityCriteriaTitle.getText().contains(criteriaDescription)) {
                WebElementFacade td5 = row.then(By.xpath("./td[5]"));
                moveToElementJS( td5.then(By.cssSelector( "span[class*='Nlp-module_ellipsis_']" )));
                waitMillis( 500 );
            }
        }
    }

    public String getToolTipContentForCriteria(){
        return tooltip.getDelayedTooltipFullText();

    }

    public String getToolTipContent(){
        return tooltip.getTooltipFullText();

    }
}
