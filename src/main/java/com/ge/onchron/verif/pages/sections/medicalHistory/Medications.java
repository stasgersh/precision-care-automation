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
package com.ge.onchron.verif.pages.sections.medicalHistory;

import org.openqa.selenium.By;

import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.Table;
import com.ge.onchron.verif.model.detailedDataItems.TableItem;
import com.ge.onchron.verif.pages.elements.MedicationsElements;
import com.ge.onchron.verif.pages.tabs.MedicalHistory;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.model.enums.MedicalHistorySection.MEDICATIONS;
import static com.ge.onchron.verif.pages.utils.PageUtils.currentlyContainsElements;
import static com.ge.onchron.verif.pages.utils.TableUtils.clickOnHeaderItemInTable;
import static com.ge.onchron.verif.pages.utils.TableUtils.createTableItem;

public class Medications extends MedicalHistory {

    private final String COLLAPSE_COMPLETED_MEDICATIONS_CSS_SELECTOR = "[class*='MedicalHistory-module_collapse']";
    private final String EXPAND_TEXT = "Expand";
    private final String COLLAPSE_TEXT = "Collapse";

    public boolean isDefaultMedTableVisible() {
        MedicationsElements medicationsElements = getMedicationsElements();
        return medicationsElements.getDefaultMedTable().isCurrentlyVisible();
    }

    public boolean isCompletedMedHeaderVisible() {
        MedicationsElements medicationsElements = getMedicationsElements();
        return medicationsElements.getCompletedMedHeader().isCurrentlyVisible();
    }

    public boolean isCompletedMedTableVisible() {
        MedicationsElements medicationsElements = getMedicationsElements();
        WebElementFacade completedMedTable = medicationsElements.getCompletedMedTable();
        return completedMedTable != null && completedMedTable.isVisible();
    }

    private MedicationsElements getMedicationsElements() {
        return new MedicationsElements( getMedicalHistorySection( MEDICATIONS ) );
    }

    public boolean isExpandButtonVisible() {
        return isCompletedMedicationsButtonVisible( EXPAND_TEXT );
    }

    public boolean isCollapseButtonVisible() {
        return isCompletedMedicationsButtonVisible( COLLAPSE_TEXT );
    }

    public void expandCompletedMedications() {
        if ( getCompletedMedicationsCaretText().equals( EXPAND_TEXT ) ) {
            elementClicker( getCompletedMedicationsButton() );
        }
    }

    public void collapseCompletedMedications() {
        if ( getCompletedMedicationsCaretText().equals( COLLAPSE_TEXT ) ) {
            elementClicker( getCompletedMedicationsButton() );
        }
    }

    private boolean isCompletedMedicationsButtonVisible( String buttonText ) {
        if ( currentlyContainsElements( getMedicalHistorySection( MEDICATIONS ), By.cssSelector( COLLAPSE_COMPLETED_MEDICATIONS_CSS_SELECTOR ) ) ) {
            boolean buttonIsVisible = getMedicalHistorySection( MEDICATIONS ).then( By.cssSelector( COLLAPSE_COMPLETED_MEDICATIONS_CSS_SELECTOR ) ).isVisible();
            boolean buttonHasProperText = getCompletedMedicationsCaretText().equals( buttonText );
            return buttonIsVisible && buttonHasProperText;
        } else {
            return false;
        }
    }

    public String getCompletedMedicationsCaretText() {
        return getCompletedMedicationsButton().getText();
    }

    private WebElementFacade getCompletedMedicationsButton() {
        return getMedicalHistorySection( MEDICATIONS )
                .then( By.cssSelector( COLLAPSE_COMPLETED_MEDICATIONS_CSS_SELECTOR ) )
                .then( By.tagName( "button" ) );
    }

    public Table getCompletedMedTable() {
        MedicationsElements medicationsElements = getMedicationsElements();
        WebElementFacade table = medicationsElements.getCompletedMedTable();
        if ( table == null ) {
            fail( "Completed medications table is not available" );
        }
        TableItem tableItem = createTableItem( null, table );
        return tableItem.getValue();
    }

    public Table getDefaultMedTable() {
        MedicationsElements medicationsElements = getMedicationsElements();
        WebElementFacade table = medicationsElements.getDefaultMedTable();
        TableItem tableItem = createTableItem( null, table );
        return tableItem.getValue();
    }

    public String getCompletedMedBadgeText() {
        MedicationsElements medicationsElements = getMedicationsElements();
        WebElementFacade completedMedHeader = medicationsElements.getCompletedMedHeader();
        if ( !currentlyContainsElements( completedMedHeader, By.className( "badge" ) ) ) {
            fail( "Badge was not found on Completed medications header" );
        }
        return completedMedHeader.then( By.className( "badge" ) ).getText();
    }

    public void clickOnHeaderItemInDefaultMedTable( String columnName ) {
        MedicationsElements medicationsElements = getMedicationsElements();
        WebElementFacade table = medicationsElements.getDefaultMedTable();
        clickOnHeaderItemInTable( table, columnName );
    }

    public void clickOnHeaderItemInCompletedMedTable( String columnName ) {
        MedicationsElements medicationsElements = getMedicationsElements();
        WebElementFacade table = medicationsElements.getCompletedMedTable();
        clickOnHeaderItemInTable( table, columnName );
    }

}
