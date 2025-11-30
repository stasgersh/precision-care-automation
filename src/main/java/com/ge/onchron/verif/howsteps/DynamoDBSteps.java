package com.ge.onchron.verif.howsteps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.fail;
import static com.ge.onchron.verif.TestSystemParameters.*;
import static com.ge.onchron.verif.utils.DynamoDBUtils.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.stream.Collectors;


public class DynamoDBSteps {

    public static final String STACK = getSystemParameter(STACK_NAME);
    public static final String DYNAMO_DB_PRIMARY_KEYS = getSystemParameter(DYNAMO_TABLE_KEYS);
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamoDBSteps.class);


    private static void deleteByScan(final String tableName, final String partitionKeyName, final String partitionKeyValue) {
        LOGGER.info(STR."Delete partitionKeyValue \{partitionKeyValue} from \{tableName} configuration DynamoDB table for \{partitionKeyName}");
        deleteItemsByScan(STR."\{STACK}-\{tableName}", partitionKeyName, partitionKeyValue);
    }

    public void deleteAllEntriesFromDynamoDBTablesByPatient(String patientID, ArrayList<String> tables) {
        Map<String, String> resultMap = Arrays.stream(DYNAMO_DB_PRIMARY_KEYS.split(","))
                .map(s -> s.split(":", 2))
                .collect(Collectors.toMap(parts -> parts[0], parts -> parts[1]));

        for (String table: tables) {
                if (resultMap.containsKey(table)) {
                    deleteByPatientFromResultsTable(patientID, table, resultMap.get(table));
                }
                else
                    fail(STR."Table \{table} not found");
            }
    }

    public void deleteFromConfigurationTableByKeyNameAndKeyValue(String dynamoDBTable, String partitionKeyName, String partitionKeyValue) {
        String fullTableName = STR."\{STACK}-\{dynamoDBTable}";

        LOGGER.info(STR."Attempting to delete items from \{fullTableName} where \{partitionKeyName} = \{partitionKeyValue}");

        deleteByScan(dynamoDBTable, partitionKeyName, partitionKeyValue);

        int remainingItemsAfterDelete = getAmountOfItemsExecutedByScan(fullTableName, partitionKeyName, partitionKeyValue);

        if (remainingItemsAfterDelete > 0) {
            assertThat(STR."Not all results from \{fullTableName} DynamoDB table were deleted", remainingItemsAfterDelete, is(0));
        } else {
            LOGGER.info(STR."No items remaining in \{fullTableName} for \{partitionKeyName}: \{partitionKeyValue}. Deletion successful.");
        }
    }

    public void deleteByPatientFromResultsTable(String patientID, String dynamoDBTable, String partitionKeyName) {
        LOGGER.info(STR."Delete results from \{dynamoDBTable} results DynamoDB table for patient: \{patientID}");
        deleteItemsByQuery(STR."\{STACK}-\{dynamoDBTable}", partitionKeyName, patientID);
        int amountOfItemsExecutedQuery = getAmountOfItemsExecutedQuery(STR."\{STACK}-\{dynamoDBTable}", partitionKeyName, patientID);
        if (amountOfItemsExecutedQuery > 0) {
            assertThat(STR."Not all results from \{STR."\{STACK}-\{dynamoDBTable}"} DynamoDB table were deleted", amountOfItemsExecutedQuery, is(0));
        } else {
            LOGGER.info(STR."No items were found in DynamoDB \{dynamoDBTable} table for patientID: \{patientID}. So there is nothing to delete! ");
        }
    }

    public void checkItemsExistenceByPatient(String dynamoTable, String patientID, ArrayList<String> items) {
        List<String> dynamoItems = getItemsExecutedByQuery(STR."\{STACK}-\{dynamoTable}", "patientId", patientID);
        for (String item : items) {
            assertThat(dynamoItems.contains(item), is(true));
        }
    }

    public void checkItemsExistenceBySuppliedParameters(String dynamoTable, Map<String, String> filters) {
        QueryResponse itemsByQueryFilters = getItemsByQueryFilters(STR."\{STACK}-\{dynamoTable}", filters);

        boolean allItemsContainAllKeys =
                itemsByQueryFilters.items()
                        .stream()
                        .allMatch(item -> filters.keySet().stream()
                                .allMatch(item::containsKey));
        assertThat("One or more items are missing required keys from filters.", allItemsContainAllKeys, is(true));
    }
}