package com.ge.onchron.verif.whatsteps.dynamoDB;

import com.ge.onchron.verif.howsteps.DynamoDBSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.model.ExamplesTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DynamoDB {

    @Steps
    public DynamoDBSteps dynamoDBSteps;

    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Given("delete all entries from [$dynamoTables] DynamoDB tables for $patientID")
    public void deleteAllEntriesFromDynamoDBTablesForSpecificPatient(String patientID, ArrayList<String> dynamoTables) {
        testDefinitionSteps.addStep( STR."Delete all entries for patient \{patientID}, from all \{dynamoTables} DynamoDB tables" );
        dynamoDBSteps.deleteAllEntriesFromDynamoDBTablesByPatient(patientID, dynamoTables);
        testDefinitionSteps.logEvidence( STR."the all entries from all \{dynamoTables} DynamoDB tables for patinet \{patientID}, were deleted",
                STR."the all entries from all \{dynamoTables} DynamoDB tables for patinet \{patientID}, were successfully deleted", true );
    }

    @Given("delete all entries from configuration [$dynamoTable] DynamoDB table, by partitionKeyName: $partitionKeyName and partitionKeyValue: $partitionKeyValue")
    public void deleteAllEntriesFromConfigDynamoDBTableByPartitionKeyNameAndValue(String dynamoDBTable, String partitionKeyName, String partitionKeyValue) {
        testDefinitionSteps.addStep( STR."Delete all entries from configuration \{dynamoDBTable} DynamoDB table by \{partitionKeyName} and \{partitionKeyValue}" );
        dynamoDBSteps.deleteFromConfigurationTableByKeyNameAndKeyValue(dynamoDBTable, partitionKeyName, partitionKeyValue);
        testDefinitionSteps.logEvidence( STR."The all entries from configuration \{dynamoDBTable} DynamoDB table by \{partitionKeyName} and \{partitionKeyValue}, were deleted",
                STR."The all entries from configuration \{dynamoDBTable} DynamoDB table by \{partitionKeyName} and \{partitionKeyValue}, were successfully deleted", true );
    }

    @Then("check $dynamoTable DynamoDB table contains items [$items] for particular patient $patientId")
    public void getTableItemsById(String dynamoTable, ArrayList<String> items, String patientId) {
            testDefinitionSteps.addStep(STR."Check table items \{items} for patient \{patientId}");
            dynamoDBSteps.checkItemsExistenceByPatient(dynamoTable, patientId, items);
            testDefinitionSteps.logEvidence(STR."the table contains relevant items", STR."the table contains relevant items", true);
    }

    @Then("check \"$dynamoTable\" DynamoDB table contains configurations with filter params: $filterParams")
        public void queryTableForConfigurationsByParams(String dynamoTable, ExamplesTable filterParams) {
        testDefinitionSteps.addStep(STR."Check for the configurations returned in query from table \{dynamoTable}");
        Map<String,String> queryParams = new HashMap<>();
        for ( Map<String, String> resourceRow : filterParams.getRows() ) {
            String filter = resourceRow.get("filter");
            String value = resourceRow.get("value");
            queryParams.put(filter, value);
        }
       dynamoDBSteps.checkItemsExistenceBySuppliedParameters(dynamoTable,queryParams);
       testDefinitionSteps.logEvidence(STR."the table contains relevant items", STR."the table contains relevant items", true);
    }

}
