package com.ge.onchron.verif.howsteps.trials;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ge.onchron.verif.howsteps.CriteriaCalculatorSteps;
import com.ge.onchron.verif.howsteps.DataQuerySteps;
import com.ge.onchron.verif.howsteps.LoggingSteps;
import com.ge.onchron.verif.howsteps.RestApiSteps;
import com.ge.onchron.verif.model.CteAIConfig.*;

import com.ge.onchron.verif.model.RestRequest;
import com.ge.onchron.verif.model.clinicalConfiguration.CriteriaConfigList;
import com.ge.onchron.verif.model.criteriaCalculator.evaluationResponse.EvaluationResponse;
import com.ge.onchron.verif.utils.CloudWatchUtils;
import com.ge.onchron.verif.utils.DateUtil;
import com.ge.onchron.verif.utils.FileUtils;
import com.ge.onchron.verif.utils.SfnProcessUtils;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.model.ExamplesTable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.ge.onchron.verif.TestSystemParameters.*;
import static io.restassured.http.Method.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.fail;

public class CteAiSteps {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataQuerySteps.class);
    @Steps
    private RestApiSteps restApiSteps;
    @Steps
    private CriteriaCalculatorSteps criteriaCalculatorSteps;
    public String lastIngestion = "";
    long lastIngestionTime = 0;

    public RequestSpecBuilder requestSpecBuilder(String sysParam, String path) {
        return new RequestSpecBuilder()
                .setBaseUri(sysParam)
                .setBasePath(path)
                .setContentType("application/json");
    }

    public RequestSpecBuilder requestSpecBuilder(String sysParam) {
        return new RequestSpecBuilder()
                .setBaseUri(sysParam)
                .setContentType("application/json");
    }

    public void getAllDataConfig() {
        RequestSpecBuilder rsb = requestSpecBuilder(getSystemParameter(CTE_AI_BASE_CONFIG_URL));
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(GET, rsb.build()));
    }

    public void getDataConfigByID(String id) {
        RequestSpecBuilder rsb = requestSpecBuilder(getSystemParameter(INGEST_BASE_PATH),
                id);
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(GET, rsb.build()));
    }

    public void getLastIngestedIngestion() {
        String id = JsonPath.read(lastIngestion, "ingestionId");
        RequestSpecBuilder rsb = requestSpecBuilder(getSystemParameter(INGEST_BASE_PATH),
                id);
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(GET, rsb.build()));
    }

    public void configIngest(String body) {
        RequestSpecBuilder rsb = requestSpecBuilder(getSystemParameter(INGEST_BASE_PATH))
                .setBody(body);
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(POST, rsb.build()));
    }

    public void updateDataConfig(String body) {
        RequestSpecBuilder rsb = requestSpecBuilder(getSystemParameter(CTE_AI_BASE_CONFIG_URL))
                .setBody(body);
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(PUT, rsb.build()));
    }

    public void deleteCtePatient(String id) {
        RequestSpecBuilder rsb = requestSpecBuilder(getSystemParameter(PATIENT_CTE_AI_PATH), id);
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(DELETE, rsb.build()));
    }

    public void getPatientTreatmentByConfId(Object patientID, Object treatmentId) {
        RequestSpecBuilder rsb = requestSpecBuilder(getSystemParameter(PATIENT_CTE_AI_PATH),
                STR."\{patientID.toString()}/treatment/\{treatmentId.toString()}");
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(GET, rsb.build()));
    }

    public String createIngestPatientsFile(ExamplesTable table, String patient, String treatments) {
        try {
            CTEPatients patientsWrapper = new CTEPatients();
            ArrayList<CTEPatient> patientsList = new ArrayList<>();
            for (Map<String, String> patients : table.getRows()) {
                String patientID = patients.get(patient);
                List<CTETreatment> treatmentsIDs = Arrays.stream(patients.get(treatments).split(",")).map(String::trim).map(t -> {
                    CTETreatment ct = new CTETreatment();
                    ct.setTreatment(t);
                    return ct;
                }).toList();
                patientsList.add(CTEPatient.builder().id(patientID).treatments(treatmentsIDs).build());
            }
            patientsWrapper.setPatients(patientsList);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return objectMapper.writeValueAsString(patientsWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void configureTreatmentToPatient(ExamplesTable table, String patientID, String treatmentID) {
        for (Map<String, String> patient : table.getRows()) {
            getPatientTreatmentByConfId(patient.get(patientID), patient.get(treatmentID));
            if (restApiSteps.getLastResponse().getStatusCode() != 200) {
                configIngest(createIngestPatientsFile(table, patientID, treatmentID));
            }
        }
    }

    public void getTreatmentById(String id) {
        RequestSpecBuilder rsb = requestSpecBuilder(getSystemParameter(TREATMENT_CTE_AI_PATH),
                id);
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(GET, rsb.build()));
    }

    public void deleteTreatmentById(String id) {
        RequestSpecBuilder rsb = requestSpecBuilder(getSystemParameter(TREATMENT_CTE_AI_PATH),
                id);
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(DELETE, rsb.build()));
    }

    public void updateTreatments(String body) {
        RequestSpecBuilder rsb = requestSpecBuilder(getSystemParameter(TREATMENTS_CTE_AI_PATH))
                .setBody(body);
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(PUT, rsb.build()));
    }

    public void ingestTreatmentToPatient(ExamplesTable table, String patientID, String treatmentID) {
        configIngest(createIngestPatientsFile(table, patientID, treatmentID));
        lastIngestion = restApiSteps.getLastResponse().asString();
    }

    public void lastIngestionDateTime() {
        lastIngestionTime = DateUtil.convertLocalDateTimeOfResponseDateToMilli(restApiSteps.getLastResponse().getHeader("date"));
        LOGGER.info(STR."Last ingestion time: \{lastIngestionTime}");
    }

    public List<String> getLatencyMessageTimeStamps(String logGroupName, String message) {
        return CloudWatchUtils.getDefaultCloudWatchLogsByMsg(SfnProcessUtils.getShortStackName(), LoggingSteps.getCloudWatchLoggingGroup(logGroupName), message)
                .stream().map(CloudWatchUtils.LogMessage::getTimeStamp).toList();
    }

    public void checkLatencyOfIngestion(String logGroupName, String message, int latencyLimit) {
        long endTime = DateUtil.convertDateTimeStringToLong(getLatencyMessageTimeStamps(logGroupName, message).getFirst());
        int latency = (int) Math.ceil(TimeUnit.MILLISECONDS.toMinutes(endTime - lastIngestionTime));
        LOGGER.info(STR."ingestion latancy time \{latency}");
        assertThat("Latency is too high", latency, lessThan(latencyLimit));
    }

    public void checkEligibilityAssessment(String logGroupName, List<String> message, int latencyLimit) {
        List<Long> assessmentStart = getLatencyMessageTimeStamps(logGroupName, message.getFirst()).stream().map(DateUtil::convertDateTimeStringToLong).toList();
        List<Long> assessmentEnd = getLatencyMessageTimeStamps(logGroupName, message.getLast()).stream().map(DateUtil::convertDateTimeStringToLong).toList();
        int index = 0;
        if (assessmentStart.isEmpty() || assessmentEnd.isEmpty())
            fail("Log not found");
        else if (assessmentStart.size() > assessmentEnd.size() || assessmentStart.size() < assessmentEnd.size()) {
            fail(STR."Out of bounds'\n'Ingestion start time timeStamps: \{String.join("\n",
                    assessmentStart.stream().map(Object::toString).collect(Collectors.toList()))}'\n'timeStamps: \{String.join("\n",
                    assessmentEnd.stream().map(Object::toString).collect(Collectors.toList()))}");
        } else {
            for (long start : assessmentStart) {
                int latency = (int) Math.ceil(TimeUnit.MILLISECONDS.toSeconds(assessmentEnd.get(index) - start));
                LOGGER.info(STR."ingestion latancy time \{latency}");
                assertThat("Latency is too high", latency, lessThan(latencyLimit));
                index++;
            }
        }
    }

    @SneakyThrows
    public TreatmentConfigFile getTreatmentConfigFile(final String fileName) {
        return new ObjectMapper().readValue(FileUtils.loadConfigFile(fileName, CTE_AI_BASE_PATH), TreatmentConfigFile.class);
    }

    public CriteriaResList getLastCriteriaResponse() {
        return restApiSteps.getLastResponse().as(CriteriaResList.class);
    }

    @SneakyThrows
    public CriteriaConfigList getCriteraiConfigFile(final String fileName) {
        return new ObjectMapper().readValue(extractCriteriaSubJson(FileUtils.loadConfigFile(fileName, CLINICAL_TRIAL_RESOURCES_PATH), "criteria").toString(), CriteriaConfigList.class);
    }

    @SneakyThrows
    public EvaluationResponse getEvaluationResponse() {
        return restApiSteps.getLastResponse().as(EvaluationResponse.class);
    }


    public void checkTreatmentResponseAlignedWithConfigFile(String fileName, List<String> properties) {
        TreatmentConfigFile configFile = getTreatmentConfigFile(fileName);
        Response response = restApiSteps.getLastResponse();


        Object root = response.jsonPath().get("$");
        List<Map<String, Object>> fullList;
        List<Map<String, Object>> filteredList = new ArrayList<>();

        if (root instanceof List) {
            fullList = response.jsonPath().getList("$");
        } else if (root instanceof Map) {
            List<String> parent = List.of(properties.getFirst().split("\\."));
            fullList = response.jsonPath().getList(parent.getFirst());
            properties.set(0, parent.get(1));
            properties.set(1, List.of(properties.getLast().split("\\.")).get(1));
        } else {
            throw new IllegalStateException("Unexpected JSON structure in response.");
        }


        for (Map<String, Object> item : fullList) {
            Map<String, Object> filteredItem = new HashMap<>();
            for (String property : properties) {
                filteredItem.put(property, item.get(property));
            }
            filteredList.add(filteredItem);
        }
        assertMatchedCriteria(configFile, filteredList, properties);
    }

    public void assertMatchedCriteria(TreatmentConfigFile configFile, List<Map<String, Object>> filteredList, List<String> properties) {
        List<Map<String, Object>> matchedItems = filteredList.stream()
                .filter(res -> configFile.criteria.stream()
                        .anyMatch(criterion ->
                                criterion.statement.equals(res.get(properties.getFirst())) &&
                                        criterion.type.equalsIgnoreCase((String) res.get(properties.getLast()))))
                .collect(Collectors.toList());

        List<Criterion> unmatchedCriteria = configFile.criteria.stream()
                .filter(criterion -> matchedItems.stream()
                        .noneMatch(res ->
                                criterion.statement.equals(res.get(properties.getFirst())) &&
                                        criterion.type.equalsIgnoreCase((String) res.get(properties.getLast()))))
                .collect(Collectors.toList());

        matchedItems.forEach(item -> {
            LOGGER.info("Matched item: statement='{}', type='{}'",
                    item.get(properties.getFirst()), item.get(properties.getLast()));
        });

        unmatchedCriteria.forEach(criterion -> {
            LOGGER.error("No match found for criterion: statement='{}', type='{}'",
                    criterion.statement, criterion.type);
        });

        assertThat("Some criteria were not matched. See logs for details.",
                unmatchedCriteria.isEmpty(), is(true));
    }

    public void checkEligibilityCalculationWithCTEAi(String patientID, String treatmentID, String trailConfigFileName) {
        CriteriaConfigList trailConfig = getCriteraiConfigFile(trailConfigFileName);
        criteriaCalculatorSteps.getEvaluatedCriteriaConfigOfTypeByIdForPatient("trial", treatmentID, patientID);
        EvaluationResponse eligibilityResponse = getEvaluationResponse();
        getPatientTreatmentByConfId(patientID, treatmentID);
        CriteriaResList cteAiResponse = getLastCriteriaResponse();

        for (CriteriaRes criteriaRes : cteAiResponse.getCriteria()) {
            String id = getIdByCriteria(FileUtils.loadConfigFile(trailConfigFileName, CLINICAL_TRIAL_RESOURCES_PATH), criteriaRes.statement);
            LOGGER.info(STR."Expected criteria id: \{id}");
            String trialConfExpression = trailConfig.getCriteria().stream()
                    .filter(sub -> sub.subCriteria.getFirst().id.contains(id))
                    .map(sub -> sub.subCriteria.getFirst().expression)
                    .findFirst()
                    .orElse(null);
            if (trialConfExpression == null) {
                continue;
            }
            LOGGER.info(STR."Expected trial Configuration Expression: \{trialConfExpression}");
            Boolean allowMissing = trailConfig.getCriteria().stream()
                    .filter(sub -> sub.subCriteria != null &&
                            !sub.subCriteria.isEmpty() &&
                            sub.subCriteria.getFirst().id.contains(id))
                    .findFirst()
                    .map(sub -> sub.getAllowMissingVariables() != null && !sub.getAllowMissingVariables().isEmpty())
                    .orElse(false);
            LOGGER.info(STR."Allow missing argument exist: \{allowMissing}");
            String trialResponseExpression = eligibilityResponse.getAttributes().values().stream()
                    .filter(criterion -> criterion.getCriteria().getFirst().getId().contains(id)).map(val -> val.getValue()).findFirst().orElse(null);
            LOGGER.info(STR."Expected trial Response Expression: \{trialResponseExpression}");
            assertMatchingScore(allowMissing, criteriaRes, trialConfExpression, trialResponseExpression);
        }
    }

    private void assertMatchingScore(Boolean allowMissing, CriteriaRes criteria, String expression, String value) {
        String response = criteria.getResponse();

        if (expression.contains(response)) {
            LOGGER.info(STR."expected matching score: \{value}, expression = \{expression}, response = \{response}");
            assertThat("Wrong matching score", value.contains("MET"), is(equalTo(true)));
        } else if (allowMissing && "UNKNOWN".equals(response) && !expression.contains("UNKNOWN")) {
            LOGGER.info(STR."expected matching score: \{value}, expression = \{expression}, response = \{response}");
            assertThat("Wrong matching score", value.contains("MISSING"), is(equalTo(true)));
        } else {
            LOGGER.info(STR."expected matching score: \{value}, expression = \{expression}, response = \{response}");
            assertThat("Wrong matching score", value.contains("NOT_MET"), is(equalTo(true)));
        }
    }

    public static JSONObject extractCriteriaSubJson(String jsonString, String key) {
        JSONObject originalJson = new JSONObject(jsonString);
        JSONArray criteriaArray = originalJson.getJSONArray(key);

        JSONObject subJson = new JSONObject();
        subJson.put(key, criteriaArray);

        return subJson;
    }


    public static String getIdByCriteria(String json, String criteriaText) {
        ReadContext ctx = JsonPath.parse(json);
        List<Map<String, Object>> variables = ctx.read("$.variables[*]");

        for (Map<String, Object> variable : variables) {
            Map<String, Object> definition = (Map<String, Object>) variable.get("definition");
            if (definition != null && definition.containsKey("ai")) {
                Map<String, Object> ai = (Map<String, Object>) definition.get("ai");
                if (ai != null && ai.containsKey("criteria")) {
                    String criteria = ai.get("criteria").toString();
                    if (criteria.contains(criteriaText)) {
                        return variable.get("id").toString();
                    }
                }
            }
        }

        return null; // Not found
    }
}


