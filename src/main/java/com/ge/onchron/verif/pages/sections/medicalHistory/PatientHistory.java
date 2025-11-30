/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2022, 2022, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.pages.sections.medicalHistory;

import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.Table;
import com.ge.onchron.verif.model.detailedDataItems.TableItem;
import com.ge.onchron.verif.pages.elements.PatientHistoryElements;
import com.ge.onchron.verif.pages.sections.DetailedPatientData;
import com.ge.onchron.verif.pages.tabs.MedicalHistory;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.model.enums.MedicalHistorySection.PATIENT_HISTORY;
import static com.ge.onchron.verif.pages.utils.TableUtils.clickOnHeaderItemInTable;
import static com.ge.onchron.verif.pages.utils.TableUtils.createTableItem;

public class PatientHistory extends MedicalHistory {

    public DetailedPatientData getDetailedPatientData() {
        return PageFactory.initElements( getMedicalHistorySection( PATIENT_HISTORY ), DetailedPatientData.class );
    }

    public Table getSurgicalHistoryTable() {
        PatientHistoryElements patientHistoryElements = getPatientHistoryElements();
        WebElementFacade table = patientHistoryElements.getSurgicalHistoryTable();
        if ( table == null ) {
            fail( "Completed medications table is not available" );
        }
        TableItem tableItem = createTableItem( null, table );
        return tableItem.getValue();
    }

    private PatientHistoryElements getPatientHistoryElements() {
        return new PatientHistoryElements( getMedicalHistorySection( PATIENT_HISTORY ) );
    }

    public void clickOnHeaderItemInSurgicalHistoryTable( String columnName ) {
        PatientHistoryElements patientHistoryElements = getPatientHistoryElements();
        WebElementFacade table = patientHistoryElements.getSurgicalHistoryTable();
        clickOnHeaderItemInTable( table, columnName );
    }

}
