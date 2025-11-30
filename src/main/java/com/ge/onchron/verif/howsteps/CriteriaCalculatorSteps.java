package com.ge.onchron.verif.howsteps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ge.onchron.verif.model.RestRequest;
import com.ge.onchron.verif.model.criteriaCalculator.CriteriaCalculatorResponse;
import com.ge.onchron.verif.model.criteriaCalculator.CriteriaCalculatorConfig;
import com.ge.onchron.verif.model.criteriaCalculator.evaluationResponse.EvaluationResponse;
import com.ge.onchron.verif.utils.RequestSpecificationUtils;
import com.google.common.collect.ImmutableMap;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;
import net.thucydides.core.annotations.Steps;
import org.apache.commons.text.StringSubstitutor;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.UUID;

import static com.ge.onchron.verif.TestSystemParameters.*;
import static com.ge.onchron.verif.utils.FileUtils.getAbsolutPathOfDir;
import static com.ge.onchron.verif.utils.FileUtils.readFromFileByAbsolutePath;
import static io.restassured.http.Method.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CriteriaCalculatorSteps {

    @Steps
    private RestApiSteps restApiSteps;

    @Steps
    private CommonSteps commonSteps;


    private static final Logger LOGGER = LoggerFactory.getLogger( CriteriaCalculatorSteps.class );

    private static final String CONTENT_BASE_PATH = STR."\{CONTENT_BASE_URL}/{config_type}";

    private static final String CONTENT_BASE_ID_PATH = STR."\{CONTENT_BASE_PATH}/{config_id}";

    private static final String CRITERIA_EVALUATE_PATH = STR."\{CRITERIA_BASE_URL}/{config_type}/evaluate";

    private static final String CRITERIA_EVALUATE_ID_PATH = STR."\{CRITERIA_EVALUATE_PATH}/{config_id}";

    private static final String CRITERIA_EVALUATE_SUMMARY_PATH = STR."\{CRITERIA_EVALUATE_PATH}/summary";

    public String randomUUID;

    public void getConfigByType( String configType ) {
        LOGGER.debug(STR."Submit GET request to list configs of type: \{configType}");
        restApiSteps.sendBackendAuthenticatedRequest( new RestRequest( GET,
                RequestSpecificationUtils.getCriteriaCalculatorRequestSpecification(CONTENT_BASE_PATH
                        .replaceAll("\\{config_type}", configType)) ) );
    }

    public void createNewConfig( String configType, String body ) {
        LOGGER.debug(STR."Create new configuration of \{configType} type with content: \{body}");
        RequestSpecification rs = RequestSpecificationUtils
                .getCriteriaCalculatorRequestSpecification(CONTENT_BASE_PATH
                        .replaceAll("\\{config_type}", configType))
                .body(body);
        restApiSteps.sendBackendAuthenticatedRequest( new RestRequest( POST, rs ) );
    }

    public void updateConfig( String configType, String configId, String body ) {
        LOGGER.debug(STR."Submit put request to update \{configType} configuration with id \{configId}");
        RequestSpecification rs = RequestSpecificationUtils
                .getCriteriaCalculatorRequestSpecification(CONTENT_BASE_ID_PATH
                        .replaceAll("\\{config_type}", configType)
                        .replaceAll("\\{config_id}", configId))
                .body(body);

        restApiSteps.sendBackendAuthenticatedRequest( new RestRequest( PUT, rs ) );
    }

    public void updateConfigForPatientId( String configType, String configId, String patientId, String body) {
        LOGGER.debug(STR."Submit put request to update \{configType} configuration with id \{configId} for patient ID \{patientId}");
        RequestSpecification rs = RequestSpecificationUtils
                .getCriteriaCalculatorRequestSpecification(CONTENT_BASE_ID_PATH
                        .replaceAll("\\{config_type}", configType)
                        .replaceAll("\\{config_id}", configId))
                .queryParam("patient", patientId)
                .body(body);

        restApiSteps.sendBackendAuthenticatedRequest( new RestRequest( PUT, rs ) );
    }

    public void createNewConfigForPatient( String configType, String patientId, String body) {
        RequestSpecification rs = RequestSpecificationUtils
                .getCriteriaCalculatorRequestSpecification(CONTENT_BASE_PATH
                        .replaceAll("\\{config_type}", configType))
                .queryParam("patient", patientId)
                .body(body);

        restApiSteps.sendBackendAuthenticatedRequest( new RestRequest( POST, rs ) );
    }

    public void getConfigByTypeAndId( String configType, String configId ) {
        restApiSteps.sendBackendAuthenticatedRequest( new RestRequest( GET,
                RequestSpecificationUtils.getCriteriaCalculatorRequestSpecification(CONTENT_BASE_ID_PATH
                        .replaceAll("\\{config_type}", configType)
                        .replaceAll("\\{config_id}", configId)) ) );
    }

    public void getConfigByTypeForPatient( String configType, String patientId ) {
        LOGGER.debug(STR."Submit GET request to retrieve configurations of \{configType} for patientId: \{patientId}");
        restApiSteps.sendBackendAuthenticatedRequest( new RestRequest( GET,
                RequestSpecificationUtils.getCriteriaCalculatorRequestSpecification(CONTENT_BASE_PATH
                        .replaceAll("\\{config_type}", configType))
                        .with().param("patient", patientId) ));
    }
    public void getConfigByTypeIdAndPatient(String configId, String configType, String patientId ) {
        LOGGER.debug(STR."Submit GET request to retrieve configurations of \{configType} for patientId: \{patientId}");
        restApiSteps.sendBackendAuthenticatedRequest( new RestRequest( GET,
                RequestSpecificationUtils.getCriteriaCalculatorRequestSpecification(CONTENT_BASE_ID_PATH
                            .replaceAll("\\{config_id}",configId)
                            .replaceAll("\\{config_type}", configType))
                        .with().param("patient", patientId) ));
    }

    public void deleteConfigByTypeAndId( String configType, String configId ) {
        LOGGER.debug(STR."Submit delete request to delete \{configType} configuration with id \{configId}");
        restApiSteps.sendBackendAuthenticatedRequest( new RestRequest( DELETE,
                RequestSpecificationUtils.getCriteriaCalculatorRequestSpecification(CONTENT_BASE_ID_PATH
                        .replaceAll("\\{config_type}", configType)
                        .replaceAll("\\{config_id}", configId)) ) );
    }

    public void getEvaluatedCriteriaConfigOfTypeByIdForPatient( String configType, String configId, String patientId ) {
        restApiSteps.sendBackendAuthenticatedRequest( new RestRequest( GET,
                RequestSpecificationUtils.getCriteriaCalculatorRequestSpecification(CRITERIA_EVALUATE_ID_PATH
                        .replaceAll("\\{config_type}", configType)
                        .replaceAll("\\{config_id}", configId))
                        .with().param("patient", patientId)) );
    }


    public void getEvaluatedCriteriaSummaryOfType( String configType, String patientId ) {
        LOGGER.debug(STR."Submit GET request for summary endpoint of type \{configType}\{patientId}");
        restApiSteps.sendBackendAuthenticatedRequest( new RestRequest( GET,
                RequestSpecificationUtils.getCriteriaCalculatorRequestSpecification(CRITERIA_EVALUATE_SUMMARY_PATH
                        .replaceAll("\\{config_type}", configType))
                        .with().param("patient", patientId)) );
    }

    public String loadConfigFileWithNewId(final String fileName) {
        return new StringSubstitutor(ImmutableMap
                .<String, Object> builder()
                .put("id", UUID.randomUUID().toString())
                .build())
                .replace( loadConfigFile(fileName) );
    }

    public String loadConfigFileWithNewIdAndType(final String fileName, final String configType) {
        randomUUID = UUID.randomUUID().toString();
        return new StringSubstitutor(ImmutableMap
                .<String, Object> builder()
                .put("id", randomUUID)
                .put("type", configType)
                .build())
                .replace( loadConfigFile(fileName) );
    }

    public String loadConfigFileWithNewIdTypeAndExtension(final String fileName,final String configType, final String extension) {
        return new StringSubstitutor(ImmutableMap
                .<String, Object> builder()
                .put("id", UUID.randomUUID().toString())
                .put("type", configType)
                .build())
                .replace( loadConfigFileWithExtension(fileName,extension) );
    }

    public String loadConfigFileWithId(final String fileName, final String configId) {
        return new StringSubstitutor(ImmutableMap
                .<String, Object> builder()
                .put("id", configId)
                .build())
                .replace( loadConfigFile(fileName) );
    }

    public String loadConfigFileWithIdAndType(final String fileName, final String configId, final String type) {
        return new StringSubstitutor(ImmutableMap
                .<String, Object> builder()
                .put("id", configId)
                .put("type",type)
                .build())
                .replace( loadConfigFile(fileName) );
    }

    public String loadConfigFile(final String fileName) {
        Path resourcesAbsolutPath = getAbsolutPathOfDir( getSystemParameter( CALCULATOR_RESOURCES_PATH ) );
        Path filePath = Paths.get( resourcesAbsolutPath.toString(), STR."\{fileName}.json" );
        return readFromFileByAbsolutePath( filePath.toString() );
    }

    public String loadConfigFileWithExtension(final String fileName, final String extension) {
        Path resourcesAbsolutPath = getAbsolutPathOfDir( getSystemParameter( CALCULATOR_RESOURCES_PATH ) );
        Path filePath = Paths.get( resourcesAbsolutPath.toString(), new StringBuilder().append(fileName).append(extension).toString());
        return readFromFileByAbsolutePath( filePath.toString() );
    }

    @SneakyThrows
    public CriteriaCalculatorConfig getCriteriaConfig(final String fileName) {
        return new ObjectMapper().readValue(loadConfigFile(fileName), CriteriaCalculatorConfig.class);
    }
    @SneakyThrows
    public CriteriaCalculatorConfig getCriteriaConfigWithIdAndType(final String fileName, final String id, final String configType) {
        return new ObjectMapper().readValue(loadConfigFileWithIdAndType(fileName,id, configType), CriteriaCalculatorConfig.class);
    }

    public CriteriaCalculatorConfig getLastCriteriaConfigResponse() {
        return restApiSteps.getLastResponse().as(CriteriaCalculatorConfig.class);
    }

    public CriteriaCalculatorResponse getLastClinicalCriteriaConfigResponse() {
        return restApiSteps.getLastResponse().as(CriteriaCalculatorResponse.class);
    }

    public EvaluationResponse getLastEvaluationResponse() {
        return restApiSteps.getLastResponse().as(EvaluationResponse.class);
    }

    public void getEvaluatedCriteriaConfigWithRetry(String type, String configId, String patientId) {
        LOGGER.info("Waiting before getting evaluated criteria config");
        commonSteps.waitInMillis(60000);
        // Retry in case 404 not found status code was returned
        Failsafe
                .with(new RetryPolicy<Integer>()
                        .withMaxRetries(15)
                        .withDelay(Duration.ofMillis(500))
                        .onRetriesExceeded(e ->
                                LOGGER.info("Retries exceeded for {}", e.getFailure().getMessage()))
                        .handleResultIf(result -> result != 200)
                        .onRetry(e ->
                                LOGGER.info("Retrying since no evaluation results were found: {} attempt",
                                e.getAttemptCount()))
                        .onSuccess(e -> LOGGER.info("Successfully found evaluation results"))
                        .abortIf(result -> result == 404))
                .get(() -> {
                    getEvaluatedCriteriaConfigOfTypeByIdForPatient(type, configId, patientId);
                    return restApiSteps.getResponseStatusCode();
                });
    }

    public void checkOutputCriteriaResponse(CriteriaCalculatorConfig expectedCriteriaConfig, EvaluationResponse evaluationResponse) {
        // Check all are found
        expectedCriteriaConfig.getOutputs().forEach(
                output ->
                        assertThat( "Output criteria is not found in response",
                                evaluationResponse.getAttributes().get(output.id), is( notNullValue() ) )
        );
    }

    public void checkCriteriaCalculatorConfig(CriteriaCalculatorConfig expectedCriteriaConfig, CriteriaCalculatorConfig actualCriteriaConfig) {
        assertThat(STR."The expected Criteria Config was not the same as observed! The expected was:\n\{expectedCriteriaConfig} while the observed was \{actualCriteriaConfig}",
                expectedCriteriaConfig, Matchers.is(equalTo(actualCriteriaConfig)));
    }

}
