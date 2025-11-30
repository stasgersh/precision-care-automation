package com.ge.onchron.verif.whatsteps.api;

import com.ge.onchron.verif.howsteps.CriteriaCalculatorSteps;
import com.ge.onchron.verif.howsteps.RestApiSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.model.criteriaCalculator.CriteriaCalculatorConfig;
import com.ge.onchron.verif.model.criteriaCalculator.Output;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.fail;


public class CriteriaCalculator {

    private static final Logger LOGGER = LoggerFactory.getLogger(CriteriaCalculatorSteps.class);
    // Calculator config files
    private static final String VALID_CUSTOM_CONFIG                             = "validCustomConfig";
    private static final String VALID_COMPLEX_CUSTOM_CONFIG                     = "validComplexCriteriaCustomConfig";
    private static final String VALID_NON_COMPLEX_CUSTOM_CONFIG                 = "nonComplexCriteriaCustomConfig";
    private static final String INVALID_CUSTOM_CONFIG_MISSING_MAIN_FIELDS       = "invalidCustomConfigMainFields";
    private static final String INVALID_CUSTOM_CONFIG_MISSING_STATES_FIELD      = "invalidCustomConfigMissingStatesField";
    private static final String INVALID_CUSTOM_CONFIG_MISSING_EXPRESSION_FIELDS = "invalidCustomConfigFields";
    private static final String INVALID_CUSTOM_CONFIG_FULFILLMENT_QUERY         = "invalidCustomConfigFulfillmentQuery";
    private static final String INVALID_CUSTOM_CONFIG_MISSING_ITEMS_FIELD       = "invalidCustomConfigMissingItemsField";
    // Calculator types
    public static final String CUSTOM = "custom";
    public static final String TRIAL = "trial";
    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    private String randomUUID;

    @Steps
    private CriteriaCalculatorSteps criteriaCalculatorSteps;
    @Steps
    private RestApiSteps restApiSteps;

    @Given("the $type configuration files are uploaded")
    public void configurationsOfTypeUploaded(String type) {
        testDefinitionSteps.addStep(STR."Upload \{type} type configuration files");
        criteriaCalculatorSteps.createNewConfig(type, criteriaCalculatorSteps.loadConfigFileWithNewIdAndType(VALID_CUSTOM_CONFIG, type));
        testDefinitionSteps.logEvidence("The custom configuration files were successfully uploaded"
                , "The custom configuration files were successfully uploaded (check previous logs)", true);
    }
   //FIXME: Check if used/needed. Delete in case it's not
   @Given("the new configuration uploaded with type of $type and configured for the patient $patientId")
   @When("the new configuration uploaded with type of $type and configured for the patient $patientId")
    public void uploadCalculatorConfigurationFiles(String type, String patientId) {
        testDefinitionSteps.addStep(STR."Upload \{type} configuration files for the patient: \{patientId}");
        criteriaCalculatorSteps.
                createNewConfigForPatient(type, patientId, criteriaCalculatorSteps.loadConfigFileWithNewIdAndType(VALID_CUSTOM_CONFIG,type));
        testDefinitionSteps.logEvidence("The custom configuration files for the patient were successfully uploaded"
                , "The custom configuration files for the patient were successfully uploaded", true);
    }

    @Given("the $amount $type configuration files are uploaded")
    @When("uploading another $amount $type configurations files")
    public void uploadAmountOfConfigs(int amount, String type) {
        testDefinitionSteps.addStep(STR."Upload \{amount} \{type} configurations");
        for (int i = 0; i < amount; i++)
        criteriaCalculatorSteps.createNewConfig(type, criteriaCalculatorSteps.loadConfigFileWithNewId(VALID_CUSTOM_CONFIG));
        testDefinitionSteps.logEvidence(STR."The \{amount} \{type} configurations were successfully uploaded",
                STR."The \{amount} \{type} configurations were successfully uploaded", true);
    }

    @Given("the $type configuration files are uploaded with non complex criteria")
    public void uploadConfigsWithoutComplexCriteria(String type) {
        testDefinitionSteps.addStep(STR."Upload \{type} type configurations with non complex criteria");
        criteriaCalculatorSteps.createNewConfig(type, criteriaCalculatorSteps.loadConfigFileWithNewId(VALID_NON_COMPLEX_CUSTOM_CONFIG));
        testDefinitionSteps.logEvidence("The configuration files were successfully uploaded",
                "The configuration files were successfully uploaded", true);
    }

    @Given("the $type configuration files are uploaded with non complex criteria for patient: $patientId")
    public void uploadConfigsWithoutComplexCriteria(String type, String patientId) {
        testDefinitionSteps.addStep(STR."Upload \{type} type configurations with non complex criteria");
        criteriaCalculatorSteps.createNewConfigForPatient(type, patientId, criteriaCalculatorSteps.loadConfigFileWithNewId(VALID_NON_COMPLEX_CUSTOM_CONFIG));
        testDefinitionSteps.logEvidence("The configuration files were successfully uploaded",
                "The configuration files were successfully uploaded", true);
    }

    @Given("the $type configuration files are uploaded with complex criteria")
    public void uploadConfigWithComplexCriteria(String type) {
        testDefinitionSteps.addStep(STR."Upload \{type} type configurations with complex criteria");
        criteriaCalculatorSteps.createNewConfig(type, criteriaCalculatorSteps.loadConfigFileWithNewId(VALID_COMPLEX_CUSTOM_CONFIG));
        testDefinitionSteps.logEvidence("The configuration files were successfully uploaded",
                "The configuration files were successfully uploaded", true);
    }

    @Given("the $type configuration files are uploaded with complex criteria for patient: $patientId")
    public void uploadConfigWithComplexCriteria(String type, String patientId) {
        testDefinitionSteps.addStep(STR."Upload \{type} type configurations with complex criteria");
        criteriaCalculatorSteps.createNewConfigForPatient(type, patientId, criteriaCalculatorSteps.loadConfigFileWithNewId(VALID_COMPLEX_CUSTOM_CONFIG));
        testDefinitionSteps.logEvidence("The configuration files were successfully uploaded",
                "The configuration files were successfully uploaded", true);
    }

    @When("the user submits GET request to retrieve the list of configurations for patient $patientId")
    public void getConfigForPatient(String patientId) {
        testDefinitionSteps.addStep("Submit GET request to retrieve the list of configurations for patient");
        criteriaCalculatorSteps.getConfigByTypeForPatient(CUSTOM, patientId);
        testDefinitionSteps.logEvidence("The GET request submitted to list configurations for patient successfully",
                "The GET request submitted to list configurations for patient successfully (check previous logs)", true);
    }

    @When("the user submits GET request to retrieve the list of all criteria configurations for patient $patientId and config type of $configType")
    public void getAllCriteriaConfigurationsForPatientAndType(String patientId, String configType) {
        testDefinitionSteps.addStep("Submit GET request to retrieve the list of configurations for patient");
        criteriaCalculatorSteps.getConfigByTypeForPatient(configType, patientId);
        testDefinitionSteps.logEvidence("The GET request submitted to list configurations for patient successfully",
                "The GET request submitted to list configurations for patient successfully (check previous logs)", true);
    }

    @When("the user submits GET request to retrieve the list of configurations")
    public void getConfigList() {
        testDefinitionSteps.addStep("Submit GET request to retrieve the list of custom configurations");
        criteriaCalculatorSteps.getConfigByType(CUSTOM);
        testDefinitionSteps.logEvidence("The GET request submitted to list configurations of type custom successfully",
                "The GET request submitted to list configurations of type custom successfully (check previous logs)", true);
    }

    @When("the user submits GET request to the next page")
    public void goToNextPage() {
        testDefinitionSteps.addStep("Submit GET request to the next page");
        testDefinitionSteps.logEvidence("The GET request successfully submitted to the next page",
                "The GET request successfully submitted to the next page", true);
    }

    @When("the user submits POST request to create new $type configuration")
    public void createNewConfig(String type) {
        testDefinitionSteps.addStep(STR."Submit POST request to create new \{type} type configuration");
        criteriaCalculatorSteps.createNewConfig(type, criteriaCalculatorSteps.loadConfigFileWithNewIdAndType(VALID_CUSTOM_CONFIG, type));
        testDefinitionSteps.logEvidence(STR."The request to create new configuration of \{type} type was submitted successfully",
                STR."The request to create new configuration of \{type} type was submitted successfully (check previous logs)", true);
    }
    @When("the generated ID is stored as parameter from new custom configuration creation")
    public void storeGeneratedID() {
        randomUUID = criteriaCalculatorSteps.randomUUID;
        testDefinitionSteps.addStep(STR."Store the generated ID from new custom configuration creation");
        testDefinitionSteps.logEvidence(
                STR."The generated ID is stored",
                STR."The generated ID is stored", true);
    }

    @When("the user submits POST request to create new configuration with existing id")
    public void createNewConfigExistingId() {
        testDefinitionSteps.addStep("Submit POST request to create new configuration with existing id");
        criteriaCalculatorSteps.createNewConfig(CUSTOM, criteriaCalculatorSteps.loadConfigFileWithIdAndType(VALID_CUSTOM_CONFIG, restApiSteps.getCreatedResponseId(),CUSTOM));
        testDefinitionSteps.logEvidence("The request to create new configuration with existing id was submitted successfully",
                "The request to create new configuration of with existing id was submitted successfully (check previous logs)", true);
    }

    @When("the user submits POST request to create new configuration having {complex criteria|expression} $fileName")
    public void createNewConfigFromFile(String fileName) {
        testDefinitionSteps.addStep(STR."Submit POST request to create new configuration from file: \{fileName}");
        criteriaCalculatorSteps.createNewConfig(CUSTOM, criteriaCalculatorSteps.loadConfigFileWithNewId(fileName));
        testDefinitionSteps.logEvidence(STR."The request to create new configuration was submitted successfully",
                STR."The request to create new configuration was submitted successfully (check previous logs)", true);
    }

    @When("the user submits POST request to create configuration file $fileName")
    public void createConfigFromFile(String fileName) {
        testDefinitionSteps.addStep(STR."Submit POST request to create new configuration from file: \{fileName}");
        criteriaCalculatorSteps.createNewConfig(CUSTOM, criteriaCalculatorSteps.loadConfigFileWithNewId(fileName));
        testDefinitionSteps.logEvidence(STR."The request to create new configuration was submitted successfully",
                STR."The request to create new configuration was submitted successfully (check previous logs)", true);
    }

    @When("the user submits POST request to create configuration with $fileName and extension of $extension")
    public void createConfigFromFileWithExtension(String fileName, String extension) {
        testDefinitionSteps.addStep(STR."Submit POST request to create new configuration from file: \{fileName}, with extension of \{extension}");
        criteriaCalculatorSteps.createNewConfig(CUSTOM, criteriaCalculatorSteps.loadConfigFileWithNewIdTypeAndExtension(fileName, CUSTOM, extension));
        testDefinitionSteps.logEvidence(STR."The request to create new configuration was submitted successfully",
                STR."The request to create new configuration was submitted successfully (check previous logs)", true);
    }

    @When("the user submits POST request to create new $type configuration for patient id $patientId")
    public void createNewConfigForPatient(String type, String patientId) {
        testDefinitionSteps.addStep(STR."Submit POST request to create new \{type} type configuration for patient \{patientId}");
        criteriaCalculatorSteps.createNewConfigForPatient(type, patientId, criteriaCalculatorSteps.loadConfigFileWithNewIdAndType(VALID_CUSTOM_CONFIG, type));
        testDefinitionSteps.logEvidence(STR."The request to create new configuration of \{type} type for patient \{patientId} was submitted successfully",
                STR."The request to create new configuration of \{type} type for patient \{patientId} was submitted successfully (check previous logs)", true);
    }

    @When("the user submits POST request to create new complex criteria configuration type of $type for patient $patientId and from file name: $fileName")
    public void createNewConfigForPatient(String type, String patientId, String fileName) {
        testDefinitionSteps.addStep(STR."Submit POST request to create new \{type} type configuration for patient \{patientId}");
        criteriaCalculatorSteps.createNewConfigForPatient(type, patientId, criteriaCalculatorSteps.loadConfigFileWithNewIdAndType(fileName, type));
        testDefinitionSteps.logEvidence(STR."The request to create new configuration of \{type} type for patient \{patientId} was submitted successfully",
                STR."The request to create new configuration of \{type} type for patient \{patientId} was submitted successfully (check previous logs)", true);
    }

    @Given("the user sends a PUT request to create/update a $configType configuration with config ID $configId and file $fileName for patient ID $patientId")
    public void updateConfigForPatientId(String configType, String configId, String fileName, String patientId) {
        testDefinitionSteps.addStep(STR."Submit a PUT request to create/update a \{configType} configuration with config ID \{configId} and file \{fileName} for patient ID \{patientId}");
        criteriaCalculatorSteps.updateConfigForPatientId(configType, configId,patientId,criteriaCalculatorSteps.loadConfigFileWithIdAndType(fileName, configId, configType));
        testDefinitionSteps.logEvidence(STR."The request to request to create/update a \{configType} type configuration with config ID \{configId} and file \{fileName} for patient ID \{patientId} was submitted successfully",
                STR."The request to request to create/update a \{configType} type configuration with config ID \{configId} and file \{fileName} for patient ID \{patientId} was submitted successfully (check previous logs)", true);
    }

    @When("the user sends a PUT request to update calculator configuration using stored generated configID,type $configType, patient $patientId from file $fileName")
    public void updateExistingConfigForPatientId(String configType, String fileName, String patientId) {
        String generatedConfigId = restApiSteps.getCreatedResponseId();
        testDefinitionSteps.addStep(STR."Submit a PUT request to update a \{configType} configuration with generated config ID and file \{fileName} for patient ID \{patientId}");
        criteriaCalculatorSteps.updateConfigForPatientId(configType, generatedConfigId, patientId,criteriaCalculatorSteps.loadConfigFileWithIdAndType(fileName, generatedConfigId, configType));
        testDefinitionSteps.logEvidence(STR."The request to update a \{configType} type configuration with generated config ID and file \{fileName} for patient ID \{patientId} was submitted successfully",
                STR."The request to update a \{configType} type configuration with generated config ID and file \{fileName} for patient ID \{patientId} was submitted successfully (check previous logs)", true);
    }

    @When("the user submits GET request to retrieve calculator configuration by $configType type, existing configID and patient $patientID")
    public void getConfigByTypeIdAndPatient(String configType, String patientID) {
        String configID = restApiSteps.getCreatedResponseId();
        testDefinitionSteps.addStep(STR."Submit GET request to retrieve calculator configuration by \{configType} type, existing configID and patient \{patientID}");
        criteriaCalculatorSteps.getConfigByTypeIdAndPatient(configID, configType, patientID);
        testDefinitionSteps.logEvidence(STR."The GET request was successfully submitted to retrieve calculator configuration by \{configType} type, existing configID and patient \{patientID}",
                STR."The GET request was successfully submitted to retrieve calculator configuration by \{configType} type, existing configID and patient \{patientID} (check previous logs)", true);
    }

    @When("the user submits GET request to retrieve the created $type type configuration")
    @Alias("the user submits GET request to retrieve the updated $type type configuration")
    public void getCreatedConfigById(String type) {
        String id = restApiSteps.getCreatedResponseId();
        testDefinitionSteps.addStep(STR."Submit GET request to retrieve configuration by last created id and type \{type}");
        criteriaCalculatorSteps.getConfigByTypeAndId(type, id);
        testDefinitionSteps.logEvidence(STR."The GET request was successfully submitted to retrieve configurationby last created id and type \{type}",
                STR."The GET request was successfully submitted to retrieve configuration by last created id and type \{type}", true);
    }

    @When("the user submits GET request to retrieve configuration with non-existing-id")
    public void getNonExistingConfigId() {
        testDefinitionSteps.addStep("Submit GET request to fetch non-existing-id configuration");
        criteriaCalculatorSteps.getConfigByTypeAndId(CUSTOM, "non-existing-id");
        testDefinitionSteps.logEvidence(
                STR."The GET request was executed trying to fetch configuration with non-existing-id",
                STR."The GET request was executed trying to fetch configuration with non-existing-id",
                true);
    }

    @When("the user submits PUT request to update existing configuration")
    public void updateExistingConfig() {
        String id = restApiSteps.getCreatedResponseId();
        // TODO: Make change at least in one of the fields of the new config for updating
        testDefinitionSteps.addStep("Submit PUT request to update existing configuration");
        criteriaCalculatorSteps.updateConfig(CUSTOM, id, criteriaCalculatorSteps.loadConfigFileWithIdAndType(VALID_CUSTOM_CONFIG, id,CUSTOM));
        testDefinitionSteps.logEvidence("The PUT request successfully submitted",
                "The PUT request successfully submitted", true);
    }

    @When("the user submits PUT request to update existing configuration fpr patient : $patientId")
    public void updateExistingConfigForPatient(String patientId) {
        String id = restApiSteps.getCreatedResponseId();
        // TODO: Make change at least in one of the fields of the new config for updating
        testDefinitionSteps.addStep("Submit PUT request to update existing configuration");
        criteriaCalculatorSteps.updateConfigForPatientId(CUSTOM, id,patientId, criteriaCalculatorSteps.loadConfigFileWithIdAndType(VALID_CUSTOM_CONFIG, id,CUSTOM));
        testDefinitionSteps.logEvidence("The PUT request successfully submitted",
                "The PUT request successfully submitted", true);
    }

    @When("the user submits PUT request to update existing configuration with complex criteria")
    public void updateExistingConfigWithComplex() {
        String id = restApiSteps.getCreatedResponseId();
        testDefinitionSteps.addStep("Submit PUT request to update existing configuration with complex criteria");
        criteriaCalculatorSteps.updateConfig(CUSTOM, id, criteriaCalculatorSteps.loadConfigFileWithId(VALID_COMPLEX_CUSTOM_CONFIG, id));
        testDefinitionSteps.logEvidence("The PUT request successfully submitted",
                "The PUT request successfully submitted", true);
    }

    @When("user submits PUT request to update existing configuration with complex criteria, for patient: $patientId")
    public void updateExistingConfigWithComplexForPatient(String patientId) {
        String id = restApiSteps.getCreatedResponseId();
        // TODO: Make change at least in one of the fields of the new config for updating
        testDefinitionSteps.addStep("Submit PUT request to update existing configuration");
        criteriaCalculatorSteps.updateConfigForPatientId(CUSTOM, id,patientId, criteriaCalculatorSteps.loadConfigFileWithIdAndType(VALID_CUSTOM_CONFIG, id,CUSTOM));
        testDefinitionSteps.logEvidence("The PUT request successfully submitted",
                "The PUT request successfully submitted", true);
    }

    @When("the user submits PUT request to update non existing configuration")
    public void updateNonExistingConfig() {
        String generatedId = String.valueOf(UUID.randomUUID().toString());
        testDefinitionSteps.addStep("Submit PUT request to update non-existing configuration");
        criteriaCalculatorSteps.updateConfig(CUSTOM, UUID.randomUUID().toString(), criteriaCalculatorSteps.loadConfigFileWithId(VALID_CUSTOM_CONFIG, generatedId));
        testDefinitionSteps.logEvidence("The PUT request successfully submitted",
                "The PUT request successfully submitted", true);
    }
    @When("the user submits DELETE request to delete existing configuration type of $configType")
    public void deleteExistingConfigurationByType(String configType) {
        testDefinitionSteps.addStep(STR."Submit DELETE request to an existing configuration type of \{configType}");
        criteriaCalculatorSteps.deleteConfigByTypeAndId(configType, restApiSteps.getCreatedResponseId());
        testDefinitionSteps.logEvidence(STR."The DELETE request successfully submitted to an existing configuration type of \{configType}",
                "The DELETE request successfully submitted (check previous logs)", true);
    }

    @When("the user submits DELETE request to delete existing configuration")
    public void deleteExistingConfiguration() {
        testDefinitionSteps.addStep("Submit DELETE request to existing configuration");
        criteriaCalculatorSteps.deleteConfigByTypeAndId(CUSTOM, restApiSteps.getCreatedResponseId());
        testDefinitionSteps.logEvidence("The DELETE request successfully submitted",
                "The DELETE request successfully submitted (check previous logs)", true);
    }
    @When("the user submits DELETE request to delete configuration by its ID - $configurationId and type $type")
    public void deleteConfigurationBy(String configurationId, String type) {
        testDefinitionSteps.addStep(STR."Submit DELETE request to delete configuration by its ID - \{configurationId} and type \{type}");
        criteriaCalculatorSteps.deleteConfigByTypeAndId(CUSTOM, configurationId);
        testDefinitionSteps.logEvidence("The DELETE request successfully submitted",
                "The DELETE request successfully submitted", true);
    }
    @When("the user submits DELETE request to delete non existing configuration")
    public void deleteNonExistingConfiguration() {
        testDefinitionSteps.addStep("Submit DELETE request to deleted non existing configuration");
        criteriaCalculatorSteps.deleteConfigByTypeAndId(CUSTOM, "non-existing-id");
        testDefinitionSteps.logEvidence("The DELETE request successfully submitted",
                "The DELETE request successfully submitted", true);
    }

    @When("the user submits POST request to create new configuration missing or invalid required $fields {field|fields}")
    public void createInvalidConfigs(String fields) {
        testDefinitionSteps.addStep(STR."Submit POST request to create new configuration missing \{fields} fields");
        switch (fields) {
            case "expression":
                criteriaCalculatorSteps.createNewConfig(CUSTOM,
                        criteriaCalculatorSteps.loadConfigFileWithNewIdAndType(INVALID_CUSTOM_CONFIG_MISSING_EXPRESSION_FIELDS, CUSTOM));
                break;
            case "fulfillment":
                criteriaCalculatorSteps.createNewConfig(CUSTOM,
                        criteriaCalculatorSteps.loadConfigFileWithNewIdAndType(INVALID_CUSTOM_CONFIG_FULFILLMENT_QUERY, CUSTOM));
                break;

            case "main":
                criteriaCalculatorSteps.createNewConfig(CUSTOM,
                        criteriaCalculatorSteps.loadConfigFileWithNewIdAndType(INVALID_CUSTOM_CONFIG_MISSING_MAIN_FIELDS, CUSTOM));
                break;

            case "states":
                criteriaCalculatorSteps.createNewConfig(CUSTOM,
                        criteriaCalculatorSteps.loadConfigFileWithNewIdAndType(INVALID_CUSTOM_CONFIG_MISSING_STATES_FIELD, CUSTOM));
                break;

            case "items":
                criteriaCalculatorSteps.createNewConfig(CUSTOM,
                        criteriaCalculatorSteps.loadConfigFileWithNewIdAndType(INVALID_CUSTOM_CONFIG_MISSING_ITEMS_FIELD, CUSTOM));
                break;
            default:
                fail(STR."Illegal argument: \{fields}, add to the list");
        }
        testDefinitionSteps.logEvidence(STR."The create request was submitted with missing \{fields} fields",
                STR."The create request was submitted with missing \{fields} fields", true);
    }

    @When("the user submits POST request to create new configuration with failed fulfillment query")
    public void createConfigWithInvalidFulfillmentQuery() {
        testDefinitionSteps.addStep("Submit POST request to create new configuration with failed fulfillment query");
        testDefinitionSteps.logEvidence("The create request was submitted with failed fulfillment query",
                "The create request was submitted with failed fulfillment query", true);
    }

    @When("the user submits POST request to create empty configuration file")
    public void createEmptyConfig() {
        testDefinitionSteps.addStep("Submit request to create empty configuration file");
        criteriaCalculatorSteps.createNewConfig(CUSTOM, "{}");
        testDefinitionSteps.logEvidence("The request to create empty configuration file was submitted successfully",
                "The request to create empty configuration file was submitted successfully", true);
    }

    @Given("the patient $patientId has criteria results in the system")
    @When("the user submits GET request to retrieve the summary of evaluated criteria for patient id $patientId")
    public void getSummaryOfEvaluatedCriteria(String patientId) {
        testDefinitionSteps.addStep("Submit GET request to retrieve the summary of evaluated criteria");
        criteriaCalculatorSteps.getEvaluatedCriteriaSummaryOfType(CUSTOM, patientId);
        testDefinitionSteps.logEvidence("The GET request successfully submitted to retrieve the summary",
                "The GET request successfully submitted to retrieve the summary (check previous logs)", true);
    }

    @When("the user submits GET request to retrieve the summary of evaluated criteria for patientId $patientId and configuration type $type")
    public void getSummaryOfEvaluatedCriteriaByTypeAndPatientId(String patientId, String type) {
        testDefinitionSteps.addStep(STR."Submit GET request to retrieve the summary of evaluated criteria for patient id \{patientId} and configuration type \{type}");
        criteriaCalculatorSteps.getEvaluatedCriteriaSummaryOfType(type, patientId);
        testDefinitionSteps.logEvidence(STR."The GET request successfully submitted to retrieve the summary of evaluated criteria for patient id \{patientId} and configuration type \{type}",
                STR."The GET request successfully submitted to retrieve the summary of evaluated criteria for patient id \{patientId} and configuration type \{type}. (check previous logs)", true);
    }

    @When("the user submits GET request to retrieve the output of created configuration for patientId $patientId and configId $configId")
    public void getOutputOfConfig(String patientId, String configId) {
        testDefinitionSteps.addStep(STR."Submit GET request to retrieve output of the created config for patient \{patientId}");
        criteriaCalculatorSteps.getEvaluatedCriteriaConfigWithRetry(CUSTOM, configId, patientId);
        testDefinitionSteps.logEvidence(STR."The GET request to retrieve output for the created config for patient \{patientId} was sent successfully",
                STR."The GET request to retrieve output for the created config for patient \{patientId} was sent successfully", true);
    }

    @When("the user submits GET request to retrieve config evaluation for patient $patientId by type of $type and configID $configId")
    public void getEvaluationConfigBy(String type, String configId, String patientId) {
        testDefinitionSteps.addStep(STR."Submit GET request to retrieve config evaluation patient \{patientId} by type of \{type} and configID \{configId}");
        criteriaCalculatorSteps.getEvaluatedCriteriaConfigWithRetry(type, configId, patientId);
        testDefinitionSteps.logEvidence(STR."The GET request to retrieve config evaluation patient \{patientId} by type of \{type} and configID \{configId} was sent successfully",
                STR."The GET request to retrieve config evaluation patient \{patientId} by type of \{type} and configID \{configId} was sent successfully", true);
    }
    @When("the user submits GET request to retrieve config evaluation for patient $patientId by type of $type and dynamically created configId and fetched from recent transaction")
    public void getEvaluationConfigBy(String type, String patientId) {
        String configId = criteriaCalculatorSteps.getLastEvaluationResponse().id;
        LOGGER.info(STR."Generated ID:\{configId}");
        testDefinitionSteps.addStep(STR."Submit GET request to retrieve config evaluation patient \{patientId} by type of \{type} and recently cerated configID");
        criteriaCalculatorSteps.getEvaluatedCriteriaConfigWithRetry(type, configId, patientId);
        testDefinitionSteps.logEvidence(
                STR."The GET request to retrieve config evaluation patient \{patientId} by type of \{type} and recently cerated configID was sent successfully",
                STR."The GET request to retrieve config evaluation patient \{patientId} by type of \{type} and recently cerated configID was sent successfully", true);
    }

    @When("the user submits GET request to retrieve the summary of unsupported evaluated criteria type for $patientId patient")
    public void getSummaryOfUnsupportedCriteria(String patientId) {
        testDefinitionSteps.addStep(STR."Submit GET request to retrieve summary of unsupported criteria for patient \{patientId}");
        criteriaCalculatorSteps.getEvaluatedCriteriaSummaryOfType("unsupported", patientId);
        testDefinitionSteps.logEvidence(STR."The GET request was successfully submitted for unsupported criteria type, for patient \{patientId}",
                STR."The GET request was successfully submitted for unsupported criteria type, for patient \{patientId}", true);
    }

    @Then("status field has '$status' value")
    public void checkCreatedValue(String status) {
        testDefinitionSteps.addStep(STR."Check the response status field value has \{status} status");
        restApiSteps.checkJsonPropertyValueInResponse("status", status, false);
        testDefinitionSteps.logEvidence(STR."The response status field has the \{status} status as expected",
                STR."The response status field has the \{status} status as expected", true);
    }

    @Then("the response contains the relevant configuration $type")
    @Alias("the response contains the newly created $type configuration")
    public void checkConfigurationsFound(String type) {
        String id = restApiSteps.getCreatedResponseId();
        testDefinitionSteps.addStep(STR."Check if the response has the relevant \{type} type configuration");
        CriteriaCalculatorConfig actualCriteriaConfigResponse = criteriaCalculatorSteps.getLastCriteriaConfigResponse();
        CriteriaCalculatorConfig expectedCriteriaConfig = criteriaCalculatorSteps.getCriteriaConfigWithIdAndType(VALID_CUSTOM_CONFIG, id, CUSTOM);
        criteriaCalculatorSteps.checkCriteriaCalculatorConfig(expectedCriteriaConfig,actualCriteriaConfigResponse);
        testDefinitionSteps.logEvidence(STR."The response contains the newly created configuration",
                STR."The response contained the newly created configuration", true);
    }
    @Then("the response contains recently created configuration with $type, generated configurationID and the configuration from $fileName")
    public void checkConfigurationsFoundByTypeRecentConfigIdAndContentFromFile(String type, String fileName) {
        String id = restApiSteps.getCreatedResponseId();
        testDefinitionSteps.addStep(STR."Check if the response has the relevant \{type} type configuration");
        CriteriaCalculatorConfig actualCriteriaConfigResponse = criteriaCalculatorSteps.getLastCriteriaConfigResponse();
        CriteriaCalculatorConfig expectedCriteriaConfig = criteriaCalculatorSteps.getCriteriaConfigWithIdAndType(fileName, id, CUSTOM);
        criteriaCalculatorSteps.checkCriteriaCalculatorConfig(expectedCriteriaConfig,actualCriteriaConfigResponse);
        testDefinitionSteps.logEvidence(STR."The response contains the newly created configuration",
                STR."The response contained the newly created configuration", true);
    }

    @Then("the response has the id of the $status configuration")
    public void checkResponseHasDeletedConfigId(String status) {
        testDefinitionSteps.addStep(STR."Check response has the \{status} and generated configuration id");
        restApiSteps.checkJsonPropertyAvailabilityInResponse("id");
        testDefinitionSteps.logEvidence(STR."The response has the \{status} and generated configuration id",
                STR."The response has the \{status} configuration id", true);
    }

    @Then("the response contains the expected output config criteria")
    public void checkOutputConfigResponse() {
        testDefinitionSteps.addStep("Check the output config response");
        CriteriaCalculatorConfig expectedCriteriaConfig = criteriaCalculatorSteps.getCriteriaConfig(VALID_CUSTOM_CONFIG);
        criteriaCalculatorSteps.checkOutputCriteriaResponse(expectedCriteriaConfig, criteriaCalculatorSteps.getLastEvaluationResponse());
        testDefinitionSteps.logEvidence(STR."The response contains the expected output configs: \{
                        expectedCriteriaConfig.getOutputs().stream().map(Output::toString).collect(Collectors.joining("\\n"))}",
                "The response contains the expected output config response", true);
    }

}
