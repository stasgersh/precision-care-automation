package com.ge.onchron.verif.pages.sections.modal;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import com.ge.onchron.verif.pages.sections.DetailedPatientData;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

public class LabHistoryModal extends BaseModal {
    private final String MODAL_BASE_XPATH = "//*[contains(@class,'EmbeddedReportViewer-module_datalist-container')]";
    private final String SUMMARY_MODAL_SKELETON_LOADER_XPATH = ".//*[contains(@class,'SkeletonLoader-module_container')]";
    @FindBy( xpath = MODAL_BASE_XPATH )
    private WebElementFacade labEventDetailsContent;

    @Override
    protected By getLocator() {
        return By.xpath( MODAL_BASE_XPATH );
    }

    public DetailedPatientData getDetailedPatientData() {
        return PageFactory.initElements( labEventDetailsContent, DetailedPatientData.class );
    }

    public void waitUntilLoadingSkeletonDisappears() {        
        waitUntilLoadingSkeletonDisappears( labEventDetailsContent, By.xpath( SUMMARY_MODAL_SKELETON_LOADER_XPATH ) );
    }
    
}
