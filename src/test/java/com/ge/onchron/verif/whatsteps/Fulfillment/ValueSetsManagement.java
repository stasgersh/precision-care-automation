package com.ge.onchron.verif.whatsteps.Fulfillment;

import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.howsteps.ValueSetsManagementSteps;
import com.ge.onchron.verif.model.StringList;
import com.ge.onchron.verif.utils.FileUtils;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.ge.onchron.verif.TestSystemParameters.*;
import static com.ge.onchron.verif.utils.FileUtils.readFromFileByAbsolutePath;

public class ValueSetsManagement {
    @Steps
    private ValueSetsManagementSteps valueSetsManagementSteps;
    private TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();


    @Given( "the following value sets are present in the system: $valueSetsItems" )
    @When( "the user creates the following value sets: $valueSetsItems" )
    public void createValueSetsIfAbsent(ExamplesTable valueSetsItems ) {
        testDefinitionSteps.addStep( STR."Create following Value Sets if absent \{valueSetsItems.getRows()}:\n"
                + STR."Send POST request to \{valueSetsManagementSteps.getValueSetsUrl()} with value set file as body." );
        for (int i = 0; i < valueSetsItems.getRows().size(); i++) {
            valueSetsManagementSteps.deleteValueSet(valueSetsItems.getRows().get(i).get("names"));
            valueSetsManagementSteps.createNewValueSet( valueSetsItems.getRows().get(i).get("items"));
        }
        testDefinitionSteps.logEvidence( "Create Value Set requests sent successfully",
                "Create Value Set request sent without errors (see previous logs)", true );
    }
    @When( "the user requests the list of all value sets" )
    public void getValueSets() {
        testDefinitionSteps.addStep( STR."Get list of Value Sets:\n"
                + STR."Send GET request to \{valueSetsManagementSteps.getValueSetsUrl()}" );
        valueSetsManagementSteps.getValueSetsList();
        testDefinitionSteps.logEvidence( "Get Value Sets request sent successfully",
                "Get Value Sets request sent without errors (see previous logs)", true );
    }
    @When( "the user requests for single value set: $valueSetName" )
    public void getValueSet(String valueSetName) {
        testDefinitionSteps.addStep( STR."Get particular Value Set: \{valueSetName}" );
        valueSetsManagementSteps.getValueSet(valueSetName);
        testDefinitionSteps.logEvidence( STR."Get Value Set \{valueSetName} request sent successfully",
                "Get Value Set request sent without errors (see previous logs)", true );
    }
    @Given("the user remove value set name $valueName when it exist in value set management")
    @When("the user remove value set name $valueName")
    public void checkValueSetExist( String valueName ) {
        testDefinitionSteps.addStep(STR."Remove value set name \{valueName} Value Sets Management");
        valueSetsManagementSteps.deleteValueSet( valueName );
        testDefinitionSteps.logEvidence( STR."The value sets name \{valueName} doesnt exist in value set management ",
                "The value set name doesnt exist", true );

    }
    @When("the user creates new value set $valueSetName")
    public void createNewValueSet(String valueSetName) {
        testDefinitionSteps.addStep("the user creates new value set");
        valueSetsManagementSteps.createNewValueSet(valueSetName);
        testDefinitionSteps.logEvidence(STR."the user creates value set \{valueSetName}",
                "the value set created", true);
    }
    @When("the user update value set $valueSetName with body: $fileName")
    public void updateValueSet(String valueSetName, String fileName) {
        Path filePath = Paths.get(getSystemParameter(VALUE_SETS_RESOURCES), STR."\{fileName}.json");
        String body = readFromFileByAbsolutePath(filePath.toString());
        testDefinitionSteps.addStep(STR."the user update value set \{valueSetName} with body \{body}");
        valueSetsManagementSteps.updateValueSet(fileName, valueSetName);
        testDefinitionSteps.logEvidence(STR."the user updates value set \{valueSetName}",
                "the value set updated", true);
    }
    @Then("the value sets response contains the following body content: $path")
    public void verifyValueSetsResponse(String path) {
        Path filePath = Paths.get(getSystemParameter(VALUE_SETS_RESOURCES), STR."\{path}.json");
        testDefinitionSteps.addStep(STR.
                "Check the response");
        boolean isContained = valueSetsManagementSteps.checkValueSetsIfContains(path);
        testDefinitionSteps.logEvidence(STR.
                        "the response contains the following body: \{FileUtils.readFromFileByAbsolutePath(filePath.toString())}",
                "Response content matches expected (see previous logs)",
                isContained);
    }
    @Then("The response content matches the content of jsonFile: $path")
    public void verifyValueSetsResponseJsonFile(String path) {
        testDefinitionSteps.addStep(STR.
                "Check the response");
        valueSetsManagementSteps.checkValueSetsIfEquals(path);
        testDefinitionSteps.logEvidence(STR.
                        "the response matches the content of jsonFile: \{path}",
                "Response content matches expected (see previous logs)",
                true);
    }
    @Then("the response content matches the content of jsonFile for single valueSet: $path")
    public void verifyValueSetsResponseJsonFileForSingleValueSet(String path) {
        testDefinitionSteps.addStep(STR.
                "Check the response");
        valueSetsManagementSteps.checkValueSetItemEqual(path);
        testDefinitionSteps.logEvidence(STR.
                        "the response matches the content of jsonFile: \{path}",
                "Response content matches expected (see previous logs)",
                true);
    }


}
