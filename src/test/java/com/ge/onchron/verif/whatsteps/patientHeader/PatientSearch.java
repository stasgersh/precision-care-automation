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
package com.ge.onchron.verif.whatsteps.patientHeader;

import java.util.List;
import java.util.stream.Collectors;

import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.model.detailedDataItems.DetailedDataItem;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.ge.onchron.verif.howsteps.PatientSearchSteps;
import com.ge.onchron.verif.model.Patient;
import net.thucydides.core.annotations.Steps;

public class PatientSearch {

    @Steps
    private PatientSearchSteps patientSearchSteps;

    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Given("the user typed into the patient search field: $searchParam")
    @When("the user types into the patient search field: $searchParam")
    public void typeIntoPatientSearchField(String searchParam) {
        testDefinitionSteps.addStep(STR."Type into the patient search field \{searchParam}");
        patientSearchSteps.typeIntoPatientSearchField(searchParam);
        testDefinitionSteps.logEvidence("typed into the patient search field successfully", "type into the patient search field successfully", true);
    }

    @Then("only the following user is displayed in the patient list: $patientList")
    public void checkPatientListContentStrictly(List<Patient> patientList) {
        String patientListStr = patientList.stream().map(Object::toString).collect(Collectors.joining("\n"));
        testDefinitionSteps.addStep(STR."Check patient list");
        patientSearchSteps.checkPatientListStrictly(patientList);
        testDefinitionSteps.logEvidence(STR."only the following user is displayed in the patient list \{patientListStr}",
            "The patient lists match successfully", true);
    }

    @Then("the following user is the first item in the patient list: $patientListWithOneItem")
    public void checkFirstPatientListItem(List<Patient> patientListWithOneItem) {
        testDefinitionSteps.addStep(STR."Check patient list");
        patientSearchSteps.checkFirstPatientListItem(patientListWithOneItem.getFirst());
        testDefinitionSteps.logEvidence(
            STR."only the following user is displayed as a first item \{patientListWithOneItem.getFirst()} in patient list",
            "The first patient in the list is match successfully", true);
    }

    @Then( "the following user $isPresent present in the patient list: $patientList" )
    @Alias( "the following users $isPresent present in the patient list: $patientList" )
    public void checkPatientListContent( Boolean isPresent, List<Patient> patientList ) {
        String patientListStr = patientList.stream().map(Object::toString).collect(Collectors.joining("\n"));
        testDefinitionSteps.addStep("Check patient list content");
        patientSearchSteps.checkPatientList( isPresent, patientList );
        testDefinitionSteps.logEvidence(
            STR."the following user \{isPresent? "" : "not"} presented in the patient list: \{patientList}",
            "Patient availability is match successfully", true);
    }

    @Then( "patient list is empty" )
    public void checkEmptyPatientList() {
        testDefinitionSteps.addStep(STR."Check patient list");
        patientSearchSteps.verifyEmptyPatientList();
        testDefinitionSteps.logEvidence(
            "patient list is empty",
            "patient list is empty", true);
    }

    @Then( "the following text is displayed in the empty patient list: \"$emptyListText\"" )
    public void checkTextOfEmptyPatientList( String emptyListText ) {
        testDefinitionSteps.addStep(STR."Check text that displayed in the empty patient list");
        patientSearchSteps.checkTextOfEmptyPatientList( emptyListText );
        testDefinitionSteps.logEvidence(
            STR."the following text is displayed in the empty patient list: \{emptyListText}",
            STR."The empty patient list text \{emptyListText} is ok", true);
    }

    @Given( "the number of the patients in the patient list is saved" )
    public void saveNumOfPatients() {
        testDefinitionSteps.addStep("Save number of patients in the patient list");
        patientSearchSteps.saveNumOfPatients();
        testDefinitionSteps.logEvidence(
            "the number of the patients in the patient list is saved",
            "the number of the patients in the patient list is saved", true);
    }

    @Then( "the number of patients is equal to the saved number" )
    public void verifyNumOfPatientsEqualsToSavedNum() {
        testDefinitionSteps.addStep("Check number of patients in the patient list");
        patientSearchSteps.verifyNumOfPatientsEqualsToSavedNum();
        testDefinitionSteps.logEvidence(
            "The number of patients is equal to the saved number",
            "Displayed patients number equal to the saved one", true);
    }

    @Given( "the number of patients was less than the saved number" )
    @Then( "the number of patients is less than the saved number" )
    public void verifyNumOfPatientsLessThanSavedNum() {
        testDefinitionSteps.addStep("Check number of patients in the patient list");
        patientSearchSteps.verifyNumOfPatientsLessThanSavedNum();
        testDefinitionSteps.logEvidence(
        "The number of patients is less then the saved number",
            "Displayed patients number less then the saved one", true);
    }

    @When( "the user clicks on the 'X' button in the searching window" )
    public void clickOnXButtonInSearch() {
        testDefinitionSteps.addStep("Click on 'X' button in the searching window");
        patientSearchSteps.clickOnXButtonInSearch();
        testDefinitionSteps.logEvidence(
        "Searching criteria is cleared successfully",
                "Searching criteria is cleared", true);
    }

    @Given( "the search field is empty" )
    public void clearSearchParams() {
        testDefinitionSteps.addStep("Clear search params");
        patientSearchSteps.clearSearchParams();
        testDefinitionSteps.logEvidence(
        "the search field is empty",
            "the search field is empty", true);
    }

}
