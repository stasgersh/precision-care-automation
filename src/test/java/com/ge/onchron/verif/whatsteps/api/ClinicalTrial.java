package com.ge.onchron.verif.whatsteps.api;

import com.ge.onchron.verif.howsteps.CommonSteps;
import com.ge.onchron.verif.howsteps.RestApiSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.howsteps.trials.TrialSteps;
import com.ge.onchron.verif.model.StringList;
import com.ge.onchron.verif.model.StringListStorage;
import com.ge.onchron.verif.model.clinicalConfiguration.treatment.ClinicalTreatmentEligibilityConfig;
import com.ge.onchron.verif.utils.TrialConfigManagerUtils;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClinicalTrial {

    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();
    @Steps
    private TrialSteps trialSteps;
    @Steps
    private CommonSteps commonSteps;
    @Steps
    private RestApiSteps restApiSteps;


    @When("the user submits PUT request to update config ID:$configID and configType:$configType, and with following data:$fileName")
    public void updateExistingConfig(String configID, String configType, String fileName) {
        testDefinitionSteps.addStep(STR."Submit PUT request to update the existing configuration with configId:\{configID} and configType:\{configType}");
        trialSteps.updateTrialConfig(configID, configType, trialSteps.loadConfigFile(fileName));
        testDefinitionSteps.logEvidence(STR."The PUT request successfully submitted to update configId:\{configID} and configType:\{configType}",
                STR."The PUT request successfully submitted to update configId:\{configID} and configType:\{configType}", true);
    }

    //TODO: improvement - add StepFunction process monitoring instead of fixed wait
    @Given("the user submits PUT request to update the existing patientID:$patientID with following details:$trialsData")
    @When("the user submits PUT request to update the existing patientID:$patientID with following details:$trialsData")
    public void updateExistingConfig(ExamplesTable trialsData, String patientID) {
        for (Map<String, String> trial : trialsData.getRows()) {
            String configType = trial.get("config_type");
            String configID = trial.get("config_id");
            String fileName = trial.get("trial_configuration_file");

            testDefinitionSteps.addStep(STR."Submit PUT request to update the exisitng patientID:\{patientID} with config ID:\{configID}, and with following data:\{fileName}");
            trialSteps.updateTrialConfigForPatientId(configType, configID, patientID, trialSteps.loadConfigFile(fileName));
            testDefinitionSteps.logEvidence(STR."The PUT request successfully submitted to update configId:\{configID}",
                    STR."The PUT request successfully submitted to update configId:\{configID}", true);
        }
        commonSteps.waitInMillis(90000);
    }

    @When("the user submits POST request to add the config type of $configType, as global configuration from file name: $fileName")
    public void createNewTrialConfig(String configType, String fileName) {
        testDefinitionSteps.addStep(STR."Submit POST request to add the trial \{configType} as global configuration from file name: \{fileName}");
        trialSteps.createNewTrialConfig(configType, trialSteps.loadConfigFile(fileName));
        testDefinitionSteps.logEvidence(
                STR."The POST request successfully submitted to create configType:\{configType}",
                STR."The POST request successfully submitted to create configType:\{configType}", true);
    }

    @When("the user submits POST request to add the $configType configuration for $patient patient from file name: $fileName")
    public void createNewTrialConfigForParticular(String fileName, String patient, String configType) {
        testDefinitionSteps.addStep(STR."Submit POST request to add the trial as global configuration from file name: \{fileName}");
        trialSteps.createNewTrialConfig(trialSteps.loadConfigFile(fileName), configType, patient);
        testDefinitionSteps.logEvidence(
                STR."The POST request successfully submitted",
                STR."The POST request successfully submitted", true);
    }

    @Given("the user submits PUT request to update/add trial/s configuration whether exists or not with custom data:$trialsData")
    @When("the user submits PUT request to update/add trial/s configuration whether exists or not with custom data:$trialsData")
    public void createOrUpdateDefaultWithParamsConfig(ExamplesTable trialsData) {
        Map<String, String> customData = trialsData.getRowsAsParameters()
                .stream()
                .collect(Collectors.toMap(
                        row -> row.valueAs("config_field_name", String.class),
                        row -> row.valueAs("config_field_value", String.class)));
        testDefinitionSteps.addStep(STR."Submit PUT request to add new clinical trials configuration with custom data \{customData}");
        ClinicalTreatmentEligibilityConfig config =
                TrialConfigManagerUtils.generateDefaultClinicalTreatmentEligibilityWithCustomData(customData);
        String jsonConfig = TrialConfigManagerUtils.convertClinicalTreatmentEligibilityConfigToString(config);
        trialSteps.updateTrialConfigWithParameters(config);
        testDefinitionSteps.logEvidence(
                STR."the following trial configuration:\n\{jsonConfig} was sent for update",
                STR."the following trial configuration:\n\{jsonConfig} was sent for update successfully", true);
    }
    @When("the user submits PUT request to {update|add} $numOfTrials tria{l|ls} configuration")
    public void createDefaultWithParamsConfig(int numOfTrials)
    {
        testDefinitionSteps.addStep(STR."Submit PUT request to add new clinical trials configuration");
         while (numOfTrials > 0) {
            ClinicalTreatmentEligibilityConfig config =
                    TrialConfigManagerUtils.generateDefaultClinicalTreatmentEligibilityWithCustomData();
            trialSteps.updateTrialConfigForPatientId(config.getType(), config.getId(), "testPatientManyTrialsId" ,
                    TrialConfigManagerUtils.convertClinicalTreatmentEligibilityConfigToString(config));
            StringListStorage.addString(config.getId());
            numOfTrials--;
        }
        testDefinitionSteps.logEvidence(
                STR."the \{numOfTrials} following trial configuration was sent for update",
                STR."the \{numOfTrials} following trial configuration was sent for update was sent for update successfully", true);
    }
    @When("the user submit GET request to get all trials for patient $patientID")
    public void getAllTrialsForPatientByID(String patientId) {
        testDefinitionSteps.addStep(STR."Get all trials for patient \{patientId}");
        trialSteps.getAllTrialsForPatient(patientId);
        testDefinitionSteps.logEvidence(STR."All patient \{patientId} were presnted",
                STR."All patient \{patientId} were presnted", true);
    }
    @Given("the user submit GET request to get all configurations by configType $configType")
    @When("the user submit GET request to get all configurations by configType $configType")
    public void getAllTrialsByConfigType(String configType) {
        testDefinitionSteps.addStep(STR."Get all trials with type of \{configType}");
        trialSteps.getAllEligibilityConfigByType(configType);
        testDefinitionSteps.logEvidence(STR."All trials with type of \{configType} were retrieved",
                STR."All trials with type of \{configType} were successfully retrieved", true);
    }
    @Given("the user submit GET request to get configuration by configType $configType and configId $configId")
    @When("the user submit GET request to get configuration by configType $configType and configId $configId")
    public void getConfigurationByTypeAndID(String configType, String configId) {
        testDefinitionSteps.addStep(STR."Get configfuration with type of \{configType} and config ID \{configId}");
        trialSteps.getConfigurationByTypeAndConfigId(configType, configId);
        testDefinitionSteps.logEvidence(STR."The configuration with type of \{configType} and configId \{configId} were retrieved",
                STR."The configuration with type of \{configType} and configId \{configId} were successfully retrieved", true );
    }
    @When("the user submit DELETE request to delete all fetched configurations type of $configType")
    public void deleteAllConfigurationsByConfigType(String configType) {
        testDefinitionSteps.addStep(STR."Delete all configurations with config type of \{configType}");
        trialSteps.deleteAllConfigurationsTypeOf(configType);
        testDefinitionSteps.logEvidence(STR."Delete all configurations with config type of \{configType}",
                STR."The configurations type of \{configType} were deleted", true  );
    }

    @When("the user submit DELETE request to delete all treatments by $configType configType and by patient $patientID")
    public void deleteAllTrialsForPatientByID(String configType, String patientId) {
        testDefinitionSteps.addStep(STR."Delete all trials for patient \{patientId}");
        trialSteps.deleteTrialConfigTypeAndPatientId(configType, patientId);
        testDefinitionSteps.logEvidence(STR."Delete all trials of the patient \{patientId}",
                STR."All trial of the patient \{patientId} were deleted", true);
    }

    @When("the user submit GET request to get Eligibility configuration by configType: $configType, configID: $configId, patientId: $patientId")
    public void getEligibilityConfigByTypeAncConfigIdAndPatientID(String configType, String configId, String patientId) {
        testDefinitionSteps.addStep(STR."GET eleigibility config by config paramters: configType - \{configType},configId - \{configId}, patientId - \{patientId}");
        trialSteps.getEligibilityConfigByTypeAndConfigIdAndPatientId(configType, configId, patientId );
        testDefinitionSteps.logEvidence(STR."All patient \{patientId} were presnted",
                STR."All patient \{patientId} were presnted", true);
    }

    @When("the user submit DELETE request to delete configuration by configType: $configType, configID: $configId, patientId: $patientId")
    public void deleteEligibilityConfigByTypeAncConfigIdAndPatientID(String configType, String configId, String patientId) {
        testDefinitionSteps.addStep(STR."Delete eleigibility config by config paramters: configType - \{configType},configId - \{configId}, patientId - \{patientId}");
        trialSteps.deleteTrialConfigByIdAndTypeAndPatientId(configType,configId,patientId);
        testDefinitionSteps.logEvidence(STR."All patient \{patientId} were presnted",
                STR."All patient \{patientId} were presnted", true);
    }

    @Then("the response contains $numOfTrials trials")
    public void verifyNumberOfTrials(int numOfTrials) {
        testDefinitionSteps.addStep("count num of trials");
        trialSteps.checkNumOfTrialsForPatient(StringListStorage.getStrings().size());
        testDefinitionSteps.logEvidence(STR."the response contains \{numOfTrials} trials",
                STR."the response contains \{numOfTrials} trials", true);
    }

    @Given("the user submits PUT request to update/add trial/s configuration whether exists or not with omitted params:$omittedParams")
    @When("the user submits PUT request to update/add trial/s configuration whether exists or not with omitted params:$omittedParams")
    public void createOrUpdateDefaultTrialConfigWithOmittedParams(StringList omittedParams) {
        testDefinitionSteps.addStep(STR."Submit PUT request to add new clinical trials configuration with omitted params \{omittedParams.getList()}");
        ClinicalTreatmentEligibilityConfig config = TrialConfigManagerUtils.buildConfigWithOmittedCustomParams(omittedParams.getList(), Optional.empty());
        String jsonConfig = TrialConfigManagerUtils.convertClinicalTreatmentEligibilityConfigToString(config);
        trialSteps.updateTrialConfig(config.getId(),config.getType(),jsonConfig);
        testDefinitionSteps.logEvidence(
                STR."the following trial configuration:\n\{jsonConfig} was sent for update",
                STR."the following trial configuration:\n\{jsonConfig} was sent for update successfully", true);
    }

    @Given("the user submits PUT request to update/add trial/s configuration to an existing patient $patientID whether exists or not with omitted params: $omittedParams")
    @When("the user submits PUT request to update/add trial/s configuration to an existing patient $patientID whether exists or not with omitted params: $omittedParams")
    public void createOrUpdateDefaultTrialConfigWithOmittedParamsForExistingPatient(StringList omittedParams, String patientID) {
        testDefinitionSteps.addStep(STR."Submit PUT request to add new clinical trials configuration with omitted params \{omittedParams.getList()}, to patient \{patientID}");
        ClinicalTreatmentEligibilityConfig config = TrialConfigManagerUtils.buildConfigWithOmittedCustomParams(omittedParams.getList(),Optional.empty());
        String jsonConfig = TrialConfigManagerUtils.convertClinicalTreatmentEligibilityConfigToString(config);
        trialSteps.updateTrialConfigForPatientId(config.getType(), config.getId(),patientID,jsonConfig);
        testDefinitionSteps.logEvidence(
                STR."the following trial configuration:\n\{jsonConfig} was sent for update",
                STR."the following trial configuration:\n\{jsonConfig} was sent for update successfully", true  );
    }

    //TODO: improvement - add StepFunction process monitoring instead of fixed wait
    @Given("the user submits PUT request to update/add trial/s configuration whether exists or not:$trialsData")
    @When("the user submits PUT request to update/add trial/s configuration whether exists or not:$trialsData")
    public void createNewOrUpdateTrialConfigOneByOne(ExamplesTable trialsData) {
        for (Map<String, String> trial : trialsData.getRows()) {
            String configId = trial.get("trial_id");
            String configType = trial.get("config_type");
            String trialConfigurationFileName = trial.get("trial_configuration_file");
            testDefinitionSteps.addStep(STR."Submit PUT request to add new clinical trials configuration file:\nfilename:\{trialConfigurationFileName},with configId:\{configId} and configType:\{configType}");
            trialSteps.updateTrialConfig(configId, configType, trialSteps.loadConfigFile(trialConfigurationFileName));
            testDefinitionSteps.logEvidence(
                    STR."the following trial configuration:\n\{trial.get("trial_configuration_file")} is created/updated",
                    STR."The following trial configuration:\n\{trial.get("trial_configuration_file")} is created/updated successfully", true);
        }
        commonSteps.waitInMillis(90000);
    }
}
