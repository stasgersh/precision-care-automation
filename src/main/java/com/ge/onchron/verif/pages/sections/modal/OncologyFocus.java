package com.ge.onchron.verif.pages.sections.modal;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static org.junit.Assert.fail;

import com.ge.onchron.verif.pages.elements.tooltip.TooltipElement;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.utils.Utils.waitMillis;

public class OncologyFocus extends BaseModal {

    private TooltipElement tooltip;

    private final String ONCOLOGY_MODAL_CLASSNAME = "modal";

    @FindBy(xpath = "//*[contains(@class,'Tabs-module_onco-focus')]")
    private WebElement oncoFocusTab;

    @FindBy(xpath = "//*[contains(@class, 'Tabs-module_btns')]")
    private WebElementFacade oncoFocusPopupBtns;

    @FindBy(className = ONCOLOGY_MODAL_CLASSNAME)
    private WebElementFacade modal;

    @FindBy(xpath = "//*[contains(@class,'Modal-module_modal-header')]")
    private WebElementFacade header;

    @FindBy(xpath = "//*[contains(@class,'modal__content')]")
    private WebElementFacade content;

    @Override
    protected By getLocator() {
        return By.className(ONCOLOGY_MODAL_CLASSNAME);
    }

    public String getHeaderText() {
        return header.getText();
    }

    public void clickOnOncoFocusTab() {
        oncoFocusTab.findElement(By.cssSelector("[class*='toggle-button']")).click();
    }
    public List<String> getOncoFocusBtnsText() {
        return new ArrayList<>(List.of(oncoFocusPopupBtns.getText().split("\n")));
    }
    public List<String> getOncoFocusContentText() {
        return content.thenFindAll( By.xpath( "*" ) ).textContents();
    }
    public void clickOnoncoFocusPopupBtns(String btn) {
        elementClicker(oncoFocusPopupBtns.then(By.xpath("//*[text() = '" + btn + "']")));
    }
    public Boolean oncoFocusTabState() {
        return oncoFocusTab.findElement(By.cssSelector("[class*='toggle-button__input']")).isSelected();
    }
    public String getOncoFocusTooltipDis()
    {
        return tooltip.getTooltipFullText();
    }
    public void hoverToOncoFocusTooltip() {
        hoverToOncoFocusTooltip(oncoFocusTab.findElement(By.id("Help_-_16")));
    }
    public void hoverToOncoFocusTooltip(WebElement webElement) {
        Actions actions = new Actions( getDriver() );
        int tries = 0;
        while ( tries < 5 ) {
            try {
                actions.moveToElement( webElement ).perform();
                WebElement tooltipContainer = getDriver().findElement( By.xpath( "//*[contains(@class, 'Tooltip-module_container')]" ) );
                if ( tooltipContainer.isDisplayed() ) {
                    return;
                } else {
                    actions.moveToElement( webElement ).moveByOffset( 100, 100 ).perform();
                    waitMillis( 200 );
                    tries++;
                }
            } catch ( Exception e ) {
                tries++;
            }
        }
        if ( tries == 5 ) {
            fail( "Could not hover on onco focus text after 5 tries" );
        }
    }

}
