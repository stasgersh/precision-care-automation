package com.ge.onchron.verif.utils;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.*;
import java.util.stream.Collectors;

@UtilityClass
public class DynamoDBUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamoDBUtils.class);
    private static final String CRITERIA_RESULTS_TABLE = "criteria-results";
    private static final String CRITERIA_CONFIGURATION_TABLE = "criteria-configurations";
    private static final String ELIGIBILITY_RESULTS_TABLE = "eligibility-results";
    private static DynamoDbClient dynamoDbClient;

    private QueryResponse queryResponse = null;

    private static synchronized DynamoDbClient getInstance() {
        if (dynamoDbClient == null) {
            createClient();
        }
        try {
            dynamoDbClient.listTables();
        } catch (IllegalStateException e) {
            System.out.println("DynamoDbClient is closed. Reinitializing...");
            createClient();
        }
        return dynamoDbClient;
    }

    private static void createClient() {
        System.out.println("Creating new DynamoDbClient instance...");
        dynamoDbClient = DynamoDbClient.builder()
                .region(Region.US_EAST_1)
                .build();
    }


    public static void deleteItemsFromTablePerStack(final String stack, final String tableName) {
        try {
            if (tableName.equals(CRITERIA_RESULTS_TABLE)) {
                deleteItemFromTableCriteriaResultsTable(stack);
            }
            if (tableName.equals(CRITERIA_CONFIGURATION_TABLE)) {
                deleteItemFromTableCriteriaConfigurationsTable(stack);
            }
            if (tableName.equals(ELIGIBILITY_RESULTS_TABLE)) {
                deleteItemFromTableEligibilityResultsTable(stack);
            }
        } catch (DynamoDbException e) {
            throw new RuntimeException("An exception was occurred while trying to delete entry form dynamo DB table", e.getCause());
        } finally {
            dynamoDbClient.close();
        }
    }


    public static void deleteItemsByQuery(final String tableName, final String partitionKeyName, final String partitionKeyValue,
                                          final String filterAttributeName, final String filterAttributeValue) {

        String keyConditionExpression = STR."\{partitionKeyName} = :partitionKeyVal";
        String filterExpression = STR."\{filterAttributeName} = :filterVal";

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":partitionKeyVal", AttributeValue.builder().s(partitionKeyValue).build());
        expressionAttributeValues.put(":filterVal", AttributeValue.builder().s(filterAttributeValue).build());

        QueryRequest queryRequest = QueryRequest.builder()
                .tableName(tableName)
                .keyConditionExpression(keyConditionExpression)
                .filterExpression(filterExpression)
                .expressionAttributeValues(expressionAttributeValues)
                .build();

        executeDeleteQuery(queryRequest);
    }
    /**
     * This method for querying DynamoDB table by filters
     *
     * @param tableName
     * @param filters
     *
     * @return QueryResponse
     */
    public static QueryResponse getItemsByQueryFilters(final String tableName,final Map<String, String> filters) {
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        StringBuilder filterExpression = new StringBuilder();

        for (Map.Entry<String, String> filter : filters.entrySet()) {
            if (!filterExpression.isEmpty()) {
                filterExpression.append(" AND ");
            }
            filterExpression.append(filter.getKey()).append(" = :").append(filter.getKey());
            expressionAttributeValues.put(STR.":\{filter.getKey()}", AttributeValue.builder().s(filter.getValue()).build());
        }

      QueryRequest queryRequest = QueryRequest.builder()
                .tableName(tableName)
                .keyConditionExpression(filterExpression.toString())
                .expressionAttributeValues(expressionAttributeValues)
                .build();

        return executeQuery(queryRequest);
    }

    /**
     * This method for scanning DynamoDB table w/o keys, but only by filters
     *
     * @param tableName
     * @param filterAttributeName
     * @param filterAttributeValue
     */
    public static void deleteItemsByScan(final String tableName, final String filterAttributeName, final String filterAttributeValue) {

        ScanRequest scanRequest = buildScanRequest(tableName, filterAttributeName, filterAttributeValue);

        executeDeleteScan(scanRequest);

    }

    public static int getAmountOfItemsExecutedQuery(final String tableName, final String partitionKeyName, final String partitionKeyValue) {
        return executeQuery(
                buildQueryRequest(tableName, partitionKeyName, partitionKeyValue)).
                items().size();
    }

    public static int getAmountOfItemsExecutedByScan(final String tableName, final String partitionKeyName, final String partitionKeyValue) {
        int size = executeScan(
                buildScanRequest(tableName, partitionKeyName, partitionKeyValue)).
                items().size();
        if(size>0){
            return size;
        }
        else return 0;
    }
    public List<String> getItemsExecutedByQuery(final String tableName, final String partitionKeyName, final String partitionKeyValue)
    {
        return executeQuery(buildQueryRequest(tableName, partitionKeyName, partitionKeyValue)).items().stream()
                .filter(item -> item.containsKey("configId/criterion"))
                .map(item -> item.get("configId/criterion").s()).map(value -> value.split("/")[0])
                .collect(Collectors.toList());
    }

    public static void deleteItemsByQuery(final String tableName, final String partitionKeyName, final String partitionKeyValue) {
        executeDeleteQuery(buildQueryRequest(tableName, partitionKeyName, partitionKeyValue));
    }

    public static void deleteItemFromTableCriteriaResultsTable(final String stack) {
        LOGGER.info("Delete items from criteria results table in stack {}", stack);
        String criteriaResultsTable = STR."\{stack}-\{CRITERIA_RESULTS_TABLE}";

        deleteFromTableWithKeys(criteriaResultsTable, Arrays.asList("patient", "configTypeId"));
        LOGGER.info(STR."All entries were deleted successfully from \{criteriaResultsTable} table !!!");
    }

    public static void deleteItemFromTableCriteriaConfigurationsTable(final String stack) {
        LOGGER.info("Delete items from criteria configurations table in stack {}", stack);
        String criteriaConfigurationsTable = STR."\{stack}-\{CRITERIA_CONFIGURATION_TABLE}";

        deleteFromTableWithKeys(criteriaConfigurationsTable, Arrays.asList("id"));
        LOGGER.info(STR."All entries were deleted successfully from \{criteriaConfigurationsTable} table !!!");
    }

    public static void deleteItemFromTableEligibilityResultsTable(final String stack) {
        LOGGER.info("Delete items from eligibility results table in stack {}", stack);
        String eligibilityResultsTableName = STR."\{stack}-\{ELIGIBILITY_RESULTS_TABLE}";

        deleteFromTableWithKeys(eligibilityResultsTableName, Arrays.asList("patientId", "configTypeId"));
        LOGGER.info(STR."All entries were deleted successfully from \{eligibilityResultsTableName} table !!!");
    }

    public static void dynamoDBPrimaryKeyCheck(String tableName) {
        DescribeTableResponse response = dynamoDbClient.describeTable(
                DescribeTableRequest.builder().tableName(tableName).build());
        LOGGER.info(STR."Primary Key Schema for Table:\{tableName}");

        for (KeySchemaElement keySchema : response.table().keySchema()) {
            LOGGER.info(STR."Attribute Name:\{keySchema.attributeName()}");
            LOGGER.info(STR."Key Type:\{keySchema.keyType()}");
        }
    }

    private static QueryRequest buildQueryRequest(String tableName, String partitionKeyName, String partitionKeyValue) {
        String keyConditionExpression = STR."\{partitionKeyName} = :partitionKeyVal";

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":partitionKeyVal", AttributeValue.builder().s(partitionKeyValue).build());

        QueryRequest queryRequest = QueryRequest.builder()
                .tableName(tableName)
                .keyConditionExpression(keyConditionExpression)
                .expressionAttributeValues(expressionAttributeValues)
                .build();
        return queryRequest;
    }

    private static ScanRequest buildScanRequest(String tableName, String filterAttributeName, String filterAttributeValue) {
        String filterExpression = "#filterAttribute = :filterVal";

        Map<String, String>  expressionAttributeNames = new HashMap<>();
        expressionAttributeNames.put("#filterAttribute", filterAttributeName);

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":filterVal", AttributeValue.builder().s(filterAttributeValue).build());

        return ScanRequest.builder()
                .tableName(tableName)
                .filterExpression(filterExpression)
                .expressionAttributeNames(expressionAttributeNames) // Add expressionAttributeNames here
                .expressionAttributeValues(expressionAttributeValues)
                .build();
    }

    private static void deleteFromTableWithKeys(String tableName, List<String> keys) {
        if (keys == null || keys.isEmpty()) {
            throw new IllegalArgumentException("Keys list cannot be null or empty.");
        }
        LOGGER.info("Starting deletion process for table: {}", tableName);

        while (true) {
            ScanResponse scanResponse = dynamoDbClient.scan(ScanRequest.builder().tableName(tableName).build());

            int itemCount = scanResponse.items().size();
            LOGGER.info(STR."Scan completed: Table '\{tableName}' contains \{itemCount} items to delete.");

            if (itemCount == 0) {
                LOGGER.info("Items have been successfully deleted from table {}.", tableName);
                break;
            }

            for (Map<String, AttributeValue> item : scanResponse.items()) {
                try {
                    Map<String, AttributeValue> keyMap = buildKeyMap(keys, item);
                    deleteItem(tableName, keyMap, item);
                } catch (IllegalArgumentException e) {
                    LOGGER.warn(STR."Skipping item \{item} due to missing key(s): \{e.getMessage()}");
                } catch (DynamoDbException e) {
                    LOGGER.error(STR."Error deleting item \{item} from table \{tableName}: \{e.getMessage()}");
                }
            }

            if (scanResponse.hasLastEvaluatedKey()) {
                LOGGER.info("Fetching next page of items for table: {}", tableName);
                continue;
            }
            LOGGER.info(STR."All scanned items have been processed for table: \{tableName}");
            break;
        }
    }

    private QueryResponse executeDeleteScan(ScanRequest scanRequest) {

        DynamoDbClient dynamoDbClient = getInstance();

        ScanResponse scanResponse = dynamoDbClient.scan(scanRequest);
        try {
            if (scanResponse.hasItems() && !scanResponse.items().isEmpty()) {
                LOGGER.info(STR."Found \{scanResponse.items().size()} items to delete.");

                for (Map<String, AttributeValue> item : scanResponse.items()) {
                    deleteItem(scanRequest.tableName(), item);
                }
            } else {
                LOGGER.info("No matching items found to delete.");
            }
            return queryResponse;

        } catch (DynamoDbException e) {
            throw new RuntimeException("Error querying and deleting items from DynamoDB table", e);
        } finally {
            dynamoDbClient.close();
        }
    }

    private QueryResponse executeScan(ScanRequest scanRequest) {

        DynamoDbClient dynamoDbClient = getInstance();

        try (dynamoDbClient) {
            ScanResponse scanResponse = dynamoDbClient.scan(scanRequest);
            if (!scanResponse.items().isEmpty()) {
                LOGGER.info(STR."Found \{scanResponse.items().size()} items via scan operation in table \{scanRequest.tableName()}.");
                return queryResponse;
            } else {
                LOGGER.info("No matching items found during scan operation.");
                return QueryResponse.builder().build();
            }
        } catch (DynamoDbException e) {
            throw new RuntimeException("Error scanning items in DynamoDB table", e);
        } finally {
            dynamoDbClient.close();
        }
    }

    private QueryResponse executeQuery(QueryRequest queryRequest) {

        DynamoDbClient dynamoDbClient = getInstance();

        queryResponse = dynamoDbClient.query(queryRequest);

        try {
            if (queryResponse.hasItems() && !queryResponse.items().isEmpty()) {
                LOGGER.info(STR."Found \{queryResponse.items().size()} items in \{queryRequest.tableName()}.");

                // Extract Partition Key Name
                String keyCondition = queryRequest.keyConditionExpression(); // e.g. "patientId = :partitionKeyVal"

                // Extract Partition Key Value
                String partitionKeyPlaceholder = keyCondition.split("=")[1].trim(); // ":partitionKeyVal"
                AttributeValue partitionKeyValue = queryRequest.expressionAttributeValues().get(partitionKeyPlaceholder);

                if (partitionKeyValue == null) {
                    throw new RuntimeException("Partition key value not found in ExpressionAttributeValues!");
                }
            } else {
                LOGGER.info(STR."No matching items found in \{queryRequest.tableName()} !!!");
            }
            return queryResponse;
        } catch (DynamoDbException e) {
            throw new RuntimeException("Error querying and deleting items from DynamoDB table", e);
        } finally {
            dynamoDbClient.close();
        }
    }


    private QueryResponse executeDeleteQuery(QueryRequest queryRequest) {
        DynamoDbClient dynamoDbClient = getInstance();

        queryResponse = dynamoDbClient.query(queryRequest);

        try {
            if (queryResponse.hasItems() && !queryResponse.items().isEmpty()) {
                LOGGER.info(STR."Found \{queryResponse.items().size()} items to delete.");

                // Extract Partition Key Name
                String keyCondition = queryRequest.keyConditionExpression(); // e.g. "patientId = :partitionKeyVal"

                // Extract Partition Key Value
                String partitionKeyPlaceholder = keyCondition.split("=")[1].trim(); // ":partitionKeyVal"
                AttributeValue partitionKeyValue = queryRequest.expressionAttributeValues().get(partitionKeyPlaceholder);

                if (partitionKeyValue == null) {
                    throw new RuntimeException("Partition key value not found in ExpressionAttributeValues!");
                }

                // Delete Each Item
                for (Map<String, AttributeValue> item : queryResponse.items()) {
                    deleteItem(queryRequest.tableName(), item);
                }
            } else {
                LOGGER.info(STR."No matching items found to delete !!!");
            }
            return dynamoDbClient.query(queryRequest);
        } catch (DynamoDbException e) {
            throw new RuntimeException("Error querying and deleting items from DynamoDB table", e);
        } finally {
            dynamoDbClient.close();
        }
    }

    private static void deleteItem(String tableName, Map<String, AttributeValue> item) {
        try {
            Map<String, AttributeValue> keyMap = new HashMap<>();
            DescribeTableResponse tableSchema = dynamoDbClient.describeTable(DescribeTableRequest.builder().tableName(tableName).build());

            for (KeySchemaElement keySchema : tableSchema.table().keySchema()) {
                String keyName = keySchema.attributeName();
                if (!item.containsKey(keyName)) {
                    throw new IllegalArgumentException(STR."Key '\{keyName}' not found in the item.");
                }
                keyMap.put(keyName, item.get(keyName));
            }

            DeleteItemRequest deleteItemRequest = DeleteItemRequest.builder()
                    .tableName(tableName)
                    .key(keyMap)
                    .build();

            DeleteItemResponse response = dynamoDbClient.deleteItem(deleteItemRequest);
            if(response.sdkHttpResponse().isSuccessful()){
                LOGGER.info(STR."Successfully deleted item from DynamoDB \'\{tableName}\' table");
            }

        } catch (DynamoDbException e) {
            throw new RuntimeException("Error deleting item from DynamoDB table", e);
        }
    }

    private static void deleteItem(String tableName, Map<String, AttributeValue> keys, Map<String, AttributeValue> item) {
        dynamoDbClient.deleteItem(
                DeleteItemRequest.builder()
                        .tableName(tableName)
                        .key(keys)
                        .build());
        LOGGER.info(STR."Item \{item} was deleted from DynamoDB table \{tableName}!");
    }

    @NotNull
    private static Map<String, AttributeValue> buildKeyMap(@NotNull List<String> keys, Map<String, AttributeValue> item) {
        Map<String, AttributeValue> keyMap = new HashMap<>();
        for (String key : keys) {
            if (!item.containsKey(key)) {
                throw new IllegalArgumentException(STR."Key '\{key}' not found in the item.");
            }
            keyMap.put(key, item.get(key));
        }
        return keyMap;
    }

    private static void scanTableForItsSizeAfterDelete(String tableNameWithStack) {
        ScanRequest scanAfterDeleteRequest = ScanRequest.builder()
                .tableName(tableNameWithStack)
                .build();
        ScanResponse scanResponseAfterDelete = dynamoDbClient.scan(scanAfterDeleteRequest);
        LOGGER.info(STR."After deletion action!\nTable \{tableNameWithStack} has \{scanResponseAfterDelete.items().size()} entries to be deleted");
    }
}