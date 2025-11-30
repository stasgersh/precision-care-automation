package com.ge.onchron.verif.whatsteps.api;

import com.ge.onchron.verif.howsteps.CommonSteps;
import com.ge.onchron.verif.howsteps.RestApiSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.howsteps.radioligand.RadioligandTxSteps;
import com.ge.onchron.verif.howsteps.trials.TrialSteps;
import com.ge.onchron.verif.model.StringList;
import com.ge.onchron.verif.model.clinicalConfiguration.treatment.ClinicalTreatmentEligibilityConfig;
import com.ge.onchron.verif.utils.TrialConfigManagerUtils;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ge.onchron.verif.utils.TrialConfigManagerUtils.buildConfigWithOmittedCustomParams;
import static com.ge.onchron.verif.utils.TrialConfigManagerUtils.convertClinicalTreatmentEligibilityConfigToString;

public class Theranostics {

    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();
    @Steps
    private TrialSteps trialSteps;
    @Steps
    private RadioligandTxSteps radioligandTxSteps;
    @Steps
    private CommonSteps commonSteps;
    @Steps
    private RestApiSteps restApiSteps;

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

    @Given("the user submits PUT request to update/add treatment configuration type of $configType to an existing patient $patientID whether exists or not with omitted params: $omittedParams")
    @When("the user submits PUT request to update/add treatment configuration type of $configType to an existing patient $patientID whether exists or not with omitted params: $omittedParams")
    public void createOrUpdateDefaultTreatmentConfigWithOmittedParamsForExistingPatientByConfigType(StringList omittedParams, String patientID, String configType) {
        testDefinitionSteps.addStep(STR."Submit PUT request to add new clinical trials configuration with omitted params \{omittedParams.getList()}, to patient \{patientID}");
        ClinicalTreatmentEligibilityConfig config = TrialConfigManagerUtils.buildConfigWithOmittedCustomParams(omittedParams.getList(),configType,Optional.empty());
        String jsonConfig = TrialConfigManagerUtils.convertClinicalTreatmentEligibilityConfigToString(config);
        trialSteps.updateTrialConfigForPatientId(config.getType(), config.getId(),patientID,jsonConfig);
        testDefinitionSteps.logEvidence(
                STR."the following trial configuration:\n\{jsonConfig} was sent for update",
                STR."the following trial configuration:\n\{jsonConfig} was sent for update successfully", true  );
    }

    @Given("the user submits PUT request to update/add configuration type of $configType, whether exists or not with custom data:$trialsData")
    @When("the user submits PUT request to update/add configuration type of $configType, whether exists or not with custom data:$trialsData")
    public void createOrUpdateDefaultWithParamsConfig(@NotNull ExamplesTable trialsData, String configType) {
        Map<String, String> customData = trialsData.getRowsAsParameters()
                .stream()
                .collect(Collectors.toMap(
                        row -> row.valueAs("config_field_name", String.class),
                        row -> row.valueAs("config_field_value", String.class)));
        testDefinitionSteps.addStep(STR."Submit PUT request to add new clinical trials configuration with custom data \{customData}");
        ClinicalTreatmentEligibilityConfig config =
                TrialConfigManagerUtils.generateDefaultClinicalTreatmentEligibilityWithCustomData(customData,configType );
        String jsonConfig = convertClinicalTreatmentEligibilityConfigToString(config);
        trialSteps.updateTrialConfigWithParameters(config);
        testDefinitionSteps.logEvidence(
                STR."the following trial configuration:\n\{jsonConfig} was sent for update",
                STR."the following trial configuration:\n\{jsonConfig} was sent for update successfully", true);
    }

    @Given("the user sends POST to add $configType as global config, with omitted params:$omittedParams")
    @When("the user sends POST to add $configType as global config, with omitted params:$omittedParams")
    public void createOrUpdateDefaultTrialConfigWithOmittedParamsForExistingPatientByConfigType(String configType, StringList omittedParams) {
        testDefinitionSteps.addStep(STR."Submit POST request to add new global configuration with \{configType} type, omitted params: \{omittedParams.getList()}");
        ClinicalTreatmentEligibilityConfig config =
                buildConfigWithOmittedCustomParams(omittedParams.getList(), configType, Optional.of(Boolean.TRUE));
        String jsonConfig =
                convertClinicalTreatmentEligibilityConfigToString(config);
        radioligandTxSteps.createNewGeneratedTrialConfig(jsonConfig, configType);
        testDefinitionSteps.logEvidence(
                STR."The new generated global configuration was sent as POST request",
                STR."The new generated global configuration was sent as POST request successfully", true);
    }

}
