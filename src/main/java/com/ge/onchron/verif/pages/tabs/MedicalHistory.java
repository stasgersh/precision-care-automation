/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2023, 2023, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.pages.tabs;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.SECONDS;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.ge.onchron.verif.model.enums.MedicalHistorySection;
import com.ge.onchron.verif.pages.SimplePage;
import com.ibm.icu.impl.Assert;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.model.enums.MedicalHistorySection.ALLERGIES_AND_INTOLERANCES;
import static com.ge.onchron.verif.model.enums.MedicalHistorySection.COMORBIDITIES;
import static com.ge.onchron.verif.model.enums.MedicalHistorySection.MEDICATIONS;
import static com.ge.onchron.verif.model.enums.MedicalHistorySection.PATIENT_HISTORY;
import static com.ge.onchron.verif.pages.utils.PageElementUtils.getNonStaleElement;
import static com.ge.onchron.verif.pages.utils.PageElementUtils.isVisibleInViewport;

public class MedicalHistory extends SimplePage {

    private static final Map<MedicalHistorySection, String> MEDICAL_HISTORY_SECTION_ID_MAP = new HashMap();

    static {
        MEDICAL_HISTORY_SECTION_ID_MAP.put( COMORBIDITIES, "comorbidities" );
        MEDICAL_HISTORY_SECTION_ID_MAP.put( MEDICATIONS, "medications" );
        MEDICAL_HISTORY_SECTION_ID_MAP.put( ALLERGIES_AND_INTOLERANCES, "allergies" );
        MEDICAL_HISTORY_SECTION_ID_MAP.put( PATIENT_HISTORY, "patientHistory" );
    }

    private final String LOADING_SKELETON_CSS_SELECTOR = "[class*='SkeletonLoader-module_container']";
    private final String ANCHOR_CSS_SELECTOR = "[class^='MedicalHistory-module_anchor']";
    private final String MEDICAL_HISTORY_CONTAINER_CSS = "[class^='MedicalHistory-module_container']";
    private final String MEDICAL_HISTORY_SECTION_TITLE_CSS = "[class^='MedicalHistory-module_section-title_']";

    @FindBy( css = "[class^='MedicalHistory-module_section-container']" )
    private List<WebElementFacade> medicalHistorySections;

    @FindBy( css = "[class^='MedicalHistory-module_sidenavlink']" )
    private List<WebElementFacade> medicalHistorySidenavButtons;

    public boolean isVisible() {
        waitForAngularRequestsToFinish();
        WebElement medicalHistoryContainer = getNonStaleElement( By.cssSelector( MEDICAL_HISTORY_CONTAINER_CSS ) );
        if ( medicalHistoryContainer != null ) {
            return medicalHistoryContainer.isDisplayed();
        }
        return false;
    }

    public void waitUntilLoadingSkeletonDisappears() {
        setImplicitTimeout( 0, SECONDS );
        Wait<WebDriver> wait = new FluentWait<>( getDriver() )
                .withTimeout( Duration.ofSeconds( 60 ) )
                .pollingEvery( Duration.ofMillis( 100 ) )
                .ignoring( NoSuchElementException.class );
        try {
            wait.until( ExpectedConditions.invisibilityOfElementLocated( By.cssSelector( LOADING_SKELETON_CSS_SELECTOR ) ) );
        } catch ( Exception e ) {
            Assert.fail( "Skeleton did not disappear: " + LOADING_SKELETON_CSS_SELECTOR + "\n" + e );
        } finally {
            resetImplicitTimeout();
        }
    }

    public boolean isSectionVisible( MedicalHistorySection medicalHistorySection ) {
        return getMedicalHistorySection( medicalHistorySection ).isCurrentlyVisible();
    }

    public WebElementFacade getMedicalHistorySection( MedicalHistorySection medicalHistorySection ) {
        return medicalHistorySections.stream()
                                     .filter( mhSection -> {
                                         WebElementFacade anchor = mhSection.then( By.cssSelector( ANCHOR_CSS_SELECTOR ) );
                                         return anchor.getAttribute( "id" ).equals( MEDICAL_HISTORY_SECTION_ID_MAP.get( medicalHistorySection ) );
                                     } )
                                     .findFirst()
                                     .orElseThrow( () -> new RuntimeException( "Following Medical history section was not found: " + medicalHistorySection ) );
    }

    public List<String> getMedicalHistorySectionTitles() {
        return medicalHistorySections.stream()
                                     .map( section -> section.then( By.cssSelector( MEDICAL_HISTORY_SECTION_TITLE_CSS ) ).getText() )
                                     .collect( Collectors.toList() );
    }

    public boolean isSectionVisibleInViewport( MedicalHistorySection medicalHistorySection ) {
        WebElement medicalSection = getMedicalHistorySection( medicalHistorySection ).getWrappedElement();
        return isVisibleInViewport( medicalSection );
    }

    public boolean isNavigationButtonHighlighted( String navigationButtonName ) {
        WebElementFacade requiredSidenavButton = medicalHistorySidenavButtons.stream()
                                                                             .filter( mhSidenavButton -> mhSidenavButton.getText().equals( navigationButtonName ) )
                                                                             .findFirst()
                                                                             .orElseThrow( () ->
                                                                                     new RuntimeException(
                                                                                             "Following sidenav button on Medical History view was not found: " + navigationButtonName ) );
        return requiredSidenavButton.getAttribute( "class" ).contains( "active" );
    }

}