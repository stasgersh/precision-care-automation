package com.ge.onchron.verif.howsteps;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ge.onchron.verif.model.query.DataQuery;
import com.ge.onchron.verif.model.query.PatientListQuery;
import com.ge.onchron.verif.model.query.Queries;
import com.ge.onchron.verif.model.query.Query;
import com.ge.onchron.verif.model.RestRequest;
import com.ge.onchron.verif.model.dataQueryConfig.*;
import com.ge.onchron.verif.utils.FileUtils;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Method;
import lombok.SneakyThrows;
import net.thucydides.core.annotations.Steps;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static com.ge.onchron.verif.TestSystemParameters.*;
import static com.ge.onchron.verif.utils.FileUtils.readFromJsonFileByAbsolutePath;
import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;


public class DataQuerySteps {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataQuerySteps.class);
    @Steps
    private RestApiSteps restApiSteps;
    static long last_version = 0;

    public void executeDataQuery(List<String> dataQuery, String patientId) {
        String fileContent = dataQueryUpdate(dataQuery);
        LOGGER.info(STR."Json file request content \{fileContent}");
        executeDataQuery(fileContent, patientId);
    }

    public void executeDataQuery(String fileContent, String patientId) {
        RequestSpecBuilder rsb = new RequestSpecBuilder();
        rsb.setBaseUri(getSystemParameter(DATA_QUERY_PATIENT_PATH));
        rsb.setBasePath(STR."/\{patientId}");
        rsb.setBody(fileContent);
        rsb.setContentType("application/json");
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(Method.POST, rsb.build()));
    }
    public void executeDataQuery(String fileContent) {
        RequestSpecBuilder rsb = new RequestSpecBuilder();
        rsb.setBaseUri(getSystemParameter(DATA_QUERY_PATIENT_LIST_PATH));
        rsb.setBody(fileContent);
        rsb.setContentType("application/json");
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(Method.POST, rsb.build()));
    }


    public void executeListOfPatientsDataQuery() {
        RequestSpecBuilder rsb = new RequestSpecBuilder();
        rsb.setBaseUri(getSystemParameter(DATA_QUERY_BASE_PATH));
        rsb.setBasePath("/patient-list");
        rsb.setBody( "{ \"size\": 30}" );
        rsb.setContentType("application/json");
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(Method.POST, rsb.build()));
    }

    public void executeListOfPatientsDataQuery(String fileContent) {
        RequestSpecBuilder rsb = new RequestSpecBuilder();
        rsb.setBaseUri(getSystemParameter(DATA_QUERY_BASE_PATH));
        rsb.setBasePath("/patient-list");
        rsb.setBody(fileContent);
        rsb.setContentType("application/json");
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(Method.POST, rsb.build()));
    }

    public void executeForNextPageDataQuery() {
        RequestSpecBuilder rsb = new RequestSpecBuilder();
        rsb.setBaseUri(getSystemParameter(DATA_QUERY_BASE_PATH));
        rsb.setBasePath("/patient-list");
        rsb.setBody(STR."{\"nextPageToken\":\"\{restApiSteps.getNextPage()}\"}");
        rsb.setContentType("application/json");
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(Method.POST, rsb.build()));
    }

    public String dataQueryUpdate(List<String> data_queries) {
        try {
            Queries queries = new Queries();
            ArrayList<Query> queryList = new ArrayList<>();
            for (String data_query : data_queries) {
                DataQuery dataQueryData = new DataQuery();
                Query query = new Query();
                dataQueryData.setFrom(data_query);
                dataQueryData.setDedup(true);
                query.setAlias(data_query);
                query.setQuery(dataQueryData);
                queryList.add(query);
            }
            queries.setQueries(queryList);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return objectMapper.writeValueAsString(queries);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String dataQueryUpdateForList(Object attributes) {
        PatientListQuery patientListQuery = new PatientListQuery();
        patientListQuery.setShow(attributes);
        patientListQuery.setSize(100);
        patientListQuery.setSort(new ArrayList<>());
        return dataQueryUpdateStep(patientListQuery);
    }
    public String dataQueryUpdateForList(List<String> attributes, String order) {
        PatientListQuery patientListQuery = new PatientListQuery();
        patientListQuery.setShow(attributes);
        patientListQuery.setSize(100);
        Object sortList = attributes.stream()
                .map(attribute -> Map.of(attribute, order))
                .collect(Collectors.toList());
        patientListQuery.setSort(sortList);
        return dataQueryUpdateStep(patientListQuery);
    }

    public String dataQueryUpdateStep(PatientListQuery patientListQuery) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(patientListQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Query> setDataQuery(Object dataQuery, Object attributes, ArrayList<Query> queryList) {
        DataQuery dataQueryData = new DataQuery();
        dataQueryData.setFrom(dataQuery);
        if (attributes instanceof String) {
            String jsonPath = String.valueOf(Paths.get(getSystemParameter(DATA_QUERY_RESOURCES),
                    STR."\{attributes}.json"));
            // Read JSON file into a JSONObject
            JSONObject jsonObject = readFromJsonFileByAbsolutePath(jsonPath);
            // Convert JSONObject to Map
            Map<String, Object> filterData = jsonObject.toMap();
            // Set the filter as a Map
            dataQueryData.setFilter(filterData);
        } else {
            dataQueryData.setShow(attributes);
        }
        Query query = new Query();
        dataQueryData.setDedup(true);
        query.setAlias(""); // Set a proper alias if needed
        query.setQuery(dataQueryData);
        queryList.add(query);
        return queryList;
    }
    public String dataQueryUpdate(Object data_queries, Object attributes) {
        try {
            Queries queries = new Queries();
            ArrayList<Query> queryList = new ArrayList<>();
            if(data_queries instanceof List<?>) {
                for (Object data_query : (List<?>) data_queries) {
                    setDataQuery(data_query, attributes, queryList);
                }
            }
            else {
                setDataQuery(data_queries, attributes, queryList);
            }
            queries.setQueries(queryList);

            ObjectMapper objectMapper = new ObjectMapper();
            // Serialize the queries object
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            String jsonString = objectMapper.writeValueAsString(queries);

            return jsonString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public DataQueryPatientResponse getLastPatientResponse() {
        return restApiSteps.getLastResponse().as(DataQueryPatientResponse.class);
    }

    public DataQueryListResponse getPatientListLastRootResponse() {
        return restApiSteps.getLastResponse().as(DataQueryListResponse.class);
    }

    @SneakyThrows
    public DataQueryPatientResponse getDataQueryPatientConfig(final String fileName) {
        return new ObjectMapper().readValue(FileUtils.loadConfigFile(fileName, DATA_QUERY_RESOURCES), DataQueryPatientResponse.class);
    }

    @SneakyThrows
    public DataQueryListResponse getDataQueryPatientListConfig(final String fileName) {
        return new ObjectMapper().readValue(FileUtils.loadConfigFile(fileName, DATA_QUERY_RESOURCES), DataQueryListResponse.class);
    }

    public void checkDataQueryIfEquals(String path) {
        DataQueryPatientResponse actualDataQueryPatientResponse = getLastPatientResponse();
        DataQueryPatientResponse expectefDataQueryPatientResponse = getDataQueryPatientConfig(path);
        assertEquals(actualDataQueryPatientResponse.patient, (expectefDataQueryPatientResponse.patient));
        boolean eq = false;

            for (int i = 0; i < expectefDataQueryPatientResponse.results.size(); i++) {
                DataQueryResult expectedResult = expectefDataQueryPatientResponse.results.get(i);
                assertTrue("No results", actualDataQueryPatientResponse.results.size() > i);
                DataQueryResult result = actualDataQueryPatientResponse.results.get(i);
                try {
                    if (expectedResult.entries.isEmpty() && result.entries.isEmpty()) {
                        eq = true;
                        break;
                    }
                    for (int j = 0; j < expectedResult.entries.size(); j++) {
                        if (expectedResult.entries.get(j).attributes.equals(result.entries.get(j).attributes)) {
                            eq = true;
                            break;
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
        }
        assertTrue("DataQueries are not equals",eq);
    }

    public void checkDataQueryListIfEquals(String path) {
        DataQueryListResponse actualDataQueryResponse = getPatientListLastRootResponse();
        DataQueryListResponse expectedDataQueryResponse = getDataQueryPatientListConfig(path);
        for (DataQueryEntry entry : expectedDataQueryResponse.entries) {
            for (DataQueryEntry expectedEntry : actualDataQueryResponse.entries) {
                if (entry.patient_id.equals(expectedEntry.patient_id)) {
                    assertEquals(entry.id, expectedEntry.id);
                    assertEquals(entry.type, expectedEntry.type);
                    for (Iterator<String> it = entry.attributes.fieldNames(); it.hasNext(); ) {
                        String attr = it.next();
                        assertEquals(entry.attributes.get(attr), expectedEntry.attributes.get(attr));
                    }
                }
            }
        }
    }

    public void checkDataQueryAttributesListIfEquals(String path) {
        DataQueryListResponse actualDataQueryResponse = getPatientListLastRootResponse();
        DataQueryListResponse expectedDataQueryResponse = getDataQueryPatientListConfig(path);
        for (DataQueryEntry entry : expectedDataQueryResponse.entries) {
            for (DataQueryEntry expectedEntry : actualDataQueryResponse.entries) {
                for (Iterator<String> it = entry.attributes.fieldNames(); it.hasNext(); ) {
                    String attr = it.next();
                    LOGGER.info(STR."Observed attributes: \{actualDataQueryResponse.entries.stream().map(dataQueryEntry -> dataQueryEntry.attributes).toList()}");
                    assertThat("Mismatch in data query attributes",
                            actualDataQueryResponse.entries.stream().anyMatch(x -> x.attributes.get(attr).toString()
                                    .contains(expectedEntry.attributes.get(attr).toString())));
                }
            }
        }
    }
    public void checkDataQueryAlignedWithFhirResource(String path) {
        String jsonPath = String.valueOf(Paths.get(getSystemParameter(SUMMARY_DATA_PATH),
                STR."\{path}.json"));
        JSONObject jsonObject = readFromJsonFileByAbsolutePath(jsonPath);
        DocumentContext existingDoc = JsonPath.parse(jsonObject.toString());
        DocumentContext incomingDoc = JsonPath.parse(restApiSteps.getLastResponse().getBody().asString());
        LOGGER.info(STR."DataQuery response: \{incomingDoc} \n Requested Fhir resource: \{existingDoc}");
        assertTrue("The patient id not equals", JsonPath.read(incomingDoc.jsonString(),
                "$.results[*].entries[*].patient_id").equals(JsonPath.read(existingDoc.jsonString(), "$.entry[*].resource[?(@.resourceType == 'Patient')].id")));
        assertTrue("The dataQuery attributes are not equals with the fhir resource", JsonPath.read(existingDoc.jsonString(), "$.entry[*].resource[?(@.id == 'BMiResource')].valueQuantity").toString()
                .contains(JsonPath.read(incomingDoc.jsonString(), "$.results[*].entries[*].attributes.valueQuantity").toString()));
    }

    public void checkPatientVersionStored() {
        DataQueryPatientResponse actualDataQueryResponse = getLastPatientResponse();
        last_version = actualDataQueryResponse.version;
        LOGGER.info(STR."Data query version: \{last_version}");
        assertThat("Data model not created", last_version > 0);
    }

    public void checkPatientVersionUpdated()
    {
        DataQueryPatientResponse actualDataQueryResponse = getLastPatientResponse();
        LOGGER.info(STR."version before update: \{last_version} vs updated version: \{actualDataQueryResponse.version}");
        assertThat("Version was not updated", last_version != actualDataQueryResponse.version && actualDataQueryResponse.version > 0);
    }

    public void checkJsonOrder(String order, String attribute) {
        // Extract JSON keys in their current order
        List<String> listOrder = restApiSteps.getLastResponse().jsonPath().get(STR."entries.\{attribute}");
        // Extract JSON keys in their current order
        List<String> sortedKeys = new ArrayList<>(listOrder);
        sortedKeys.stream().sorted(); // Ascending order
        List<String> reverseSortedKeys = new ArrayList<>(listOrder);
        reverseSortedKeys.sort(Collections.reverseOrder()); // Descending order
        // Run assertions inside this method
        if (order.equalsIgnoreCase("asc")) {
            assertEquals("JSON keys are NOT in Ascending order.", sortedKeys, listOrder);
        } else if (order.equalsIgnoreCase("desc")) {
            assertEquals("JSON keys are NOT in Descending order.", reverseSortedKeys, listOrder);
        }
    }
}

