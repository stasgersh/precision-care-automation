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
package com.ge.onchron.verif.howsteps;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.Patient;
import com.ge.onchron.verif.model.detailedDataItems.DetailedDataItem;
import com.ge.onchron.verif.pages.sections.PatientHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ge.onchron.verif.TestSystemParameters.NUMBER_OF_PATIENTS;
import static net.serenitybdd.core.Serenity.hasASessionVariableCalled;
import static net.serenitybdd.core.Serenity.sessionVariableCalled;
import static net.serenitybdd.core.Serenity.setSessionVariable;

public class PatientSearchSteps {

    private              PatientHeader patientHeader;
    private static final Logger        LOGGER =
        LoggerFactory.getLogger(RestApiSteps.class);
    public void typeIntoPatientSearchField( String searchParam ) {
        patientHeader.typeIntoPatientSearchField( searchParam );
    }

    public void checkPatientListStrictly( List<Patient> expectedPatients ) {
        List<Patient> observedPatients = patientHeader.getPatients();
        LOGGER.info(STR."Observed patient list \{observedPatients}");
        assertEquals( "The patient lists do not match.", expectedPatients, observedPatients );
    }

    public void checkPatientList( Boolean isPresent, List<Patient> expectedPatients ) {
        List<Patient> observedPatients = patientHeader.getPatients();
        LOGGER.info(STR."Observed patient list \{observedPatients}");
        expectedPatients.forEach( expectedPatient ->
                assertThat( "Patient availability is not ok.", observedPatients.contains( expectedPatient ), is( equalTo( isPresent ) ) ) );
    }

    public void checkFirstPatientListItem( Patient patientListWithOneItem ) {
        List<Patient> observedPatients = patientHeader.getPatients();
        LOGGER.info(STR."Observed patient list \{observedPatients.stream().map(Patient::toString).collect(Collectors.joining("\n"))}");
        assertEquals( "The first patient in the list is not ok.", patientListWithOneItem, observedPatients.getFirst() );
    }

    public void verifyEmptyPatientList() {
        List<Patient> observedPatients = patientHeader.getPatients();
        LOGGER.info(STR."Observed patient list \{observedPatients.stream().map(Patient::toString).collect(Collectors.joining("\n"))}");
        assertTrue( "The patient list should be empty.", observedPatients.isEmpty() );
    }

    public void checkTextOfEmptyPatientList( String emptyListText ) {
        String textOfEmptyPatientList = patientHeader.getTextOfEmptyPatientList();
        LOGGER.info(STR."Patient empty list text: \{textOfEmptyPatientList}");
        assertEquals( "The empty patient list text is not ok.", emptyListText,  textOfEmptyPatientList);
    }

    public void saveNumOfPatients() {
        int observedPatientsNum = patientHeader.getNumberOfPatients();
        LOGGER.info(STR."Number of patient list: \{observedPatientsNum}");
        setSessionVariable( NUMBER_OF_PATIENTS ).to( observedPatientsNum );
    }

    public void verifyNumOfPatientsEqualsToSavedNum() {
        int observedPatientsNum = patientHeader.getNumberOfPatients();
        if ( !hasASessionVariableCalled( NUMBER_OF_PATIENTS ) ) {
            fail( "Number of patients was not saved in previous steps" );
        }
        int savedNumOfPatients = sessionVariableCalled( NUMBER_OF_PATIENTS );
        LOGGER.info(STR."Observed number of patients: \{observedPatientsNum}\n Saved number of patients: \{savedNumOfPatients}");
        assertThat( "Displayed patients number does not equal to the saved one", observedPatientsNum, is( equalTo( savedNumOfPatients ) ) );
    }

    public void verifyNumOfPatientsLessThanSavedNum() {
        List<Patient> observedPatients = patientHeader.getPatients();
        if ( !hasASessionVariableCalled( NUMBER_OF_PATIENTS ) ) {
            fail( "Number of patients was not saved in previous steps" );
        }
        int savedNumOfPatients = sessionVariableCalled( NUMBER_OF_PATIENTS );
        LOGGER.info(STR."Observed number of patients: \{observedPatients.size()}\n,  Saved number of patients: \{savedNumOfPatients}");
        assertThat( "Displayed patients number does not less than the saved one", observedPatients.size(), is( lessThan( savedNumOfPatients ) ) );
    }

    public void clickOnXButtonInSearch() {
        patientHeader.clickOnXButtonInSearch();
    }

    public void clearSearchParams() {
        patientHeader.clearSearchParams();
    }

}
