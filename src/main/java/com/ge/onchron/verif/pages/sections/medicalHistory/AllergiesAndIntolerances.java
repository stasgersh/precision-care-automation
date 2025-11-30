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
package com.ge.onchron.verif.pages.sections.medicalHistory;

import org.openqa.selenium.support.PageFactory;

import com.ge.onchron.verif.model.Table;
import com.ge.onchron.verif.model.detailedDataItems.TableItem;
import com.ge.onchron.verif.pages.elements.AllergiesAndIntolerancesElements;
import com.ge.onchron.verif.pages.sections.DetailedPatientData;
import com.ge.onchron.verif.pages.tabs.MedicalHistory;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.model.enums.MedicalHistorySection.ALLERGIES_AND_INTOLERANCES;
import static com.ge.onchron.verif.pages.utils.TableUtils.clickOnHeaderItemInTable;
import static com.ge.onchron.verif.pages.utils.TableUtils.createTableItem;

public class AllergiesAndIntolerances extends MedicalHistory {

    public DetailedPatientData getDetailedPatientData() {
        return PageFactory.initElements( getMedicalHistorySection( ALLERGIES_AND_INTOLERANCES ), DetailedPatientData.class );
    }

    public Table getAllergiesAndIntolerancesTable() {
        AllergiesAndIntolerancesElements allergiesAndIntoleranceElements = getAllergiesAndIntolerancesElements();
        WebElementFacade table = allergiesAndIntoleranceElements.getAllergiesAndIntolerancesTable();
        TableItem tableItem = createTableItem( null, table );
        return tableItem.getValue();
    }

    private AllergiesAndIntolerancesElements getAllergiesAndIntolerancesElements() {
        return new AllergiesAndIntolerancesElements( getMedicalHistorySection( ALLERGIES_AND_INTOLERANCES ) );
    }

    public void clickOnHeaderItemInAllergiesTable( String columnName ) {
        AllergiesAndIntolerancesElements allergiesAndIntoleranceElements = getAllergiesAndIntolerancesElements();
        WebElementFacade table = allergiesAndIntoleranceElements.getAllergiesAndIntolerancesTable();
        clickOnHeaderItemInTable( table, columnName );
    }
}
