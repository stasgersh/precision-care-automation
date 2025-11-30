package com.ge.onchron.verif.whatsteps.api;

import com.ge.onchron.verif.howsteps.RestApiSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.howsteps.trials.CteAiSteps;
import com.ge.onchron.verif.model.StringList;
import com.ge.onchron.verif.utils.FileUtils;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

import java.nio.file.Paths;
import java.util.List;

import static com.ge.onchron.verif.TestSystemParameters.*;

public class CteAi {
    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();
    @Steps
    CteAiSteps  cteAiSteps;
    @Steps
    RestApiSteps restApi;

    @Given("the user submit GET request to get all data-configuration")
    @When("the user submits GET request to get all data-configuration")
    public void getAllDataConfiguration()
    {
        testDefinitionSteps.addStep("Get list of all data-configuration");
        cteAiSteps.getAllDataConfig();
        testDefinitionSteps.logEvidence("GET request submitted successfully",
                "GET request submitted successfully", true);
    }
    @Given("the user submit GET request for getting last ingested Ingestion process")
    @When("the user submits GET request to fetch latest Ingestion process status")
    public void getIngestionProcessById()
    {
        testDefinitionSteps.addStep("Get last Ingested ingestion");
        cteAiSteps.getLastIngestedIngestion();
        testDefinitionSteps.logEvidence("GET request submitted successfully",
                "GET request submitted successfully", true);
    }
    @Given("the user submit GET request for getting Ingestion process by ingestion_id $ingestionId")
    public void getIngestionProcessById(String ingestionId)
    {
        testDefinitionSteps.addStep(STR."Get Ingestion process by ingestion id: \{ingestionId}");
        cteAiSteps.getDataConfigByID(ingestionId);
        testDefinitionSteps.logEvidence("GET request submitted successfully",
                "GET request submitted successfully", true);
    }
    @Given("the user submit POST request to ingest CTE-AI configuration: $fileName")
    public void ingestCteAiConfig(String fileName)
    {
        String filePath = String.valueOf(Paths.get(getSystemParameter(CTE_AI_BASE_PATH),
                STR."\{fileName}.json"));
        testDefinitionSteps.addStep(STR."Ingest \{fileName} configuration");
        cteAiSteps.configIngest(FileUtils.readFromFileByAbsolutePath(filePath));
        testDefinitionSteps.logEvidence("Configuration POST request submitted successfully",
                "Configuration POST request submitted successfully", true);
    }
    @Given("the user submits PUT request to update data-configuration: $fileName")
    @When("the user submits PUT request to update data-configuration: $fileName")
    public void updateDataConfig(String fileName)
    {
        String filePath = String.valueOf(Paths.get(getSystemParameter(CTE_AI_BASE_PATH),
                STR."\{fileName}.json"));
        testDefinitionSteps.addStep(STR."Update \{fileName} configuration ");
        cteAiSteps.updateDataConfig(FileUtils.readFromFileByAbsolutePath(filePath));
        testDefinitionSteps.logEvidence("Configuration PUT request submitted successfully",
                "Configuration PUT request submitted successfully", true);

    }
    @Given("the user submits DELETE patient request from CTE by patientID: $patientID")
    public void cteDeletePatient(String patientID)
    {
        testDefinitionSteps.addStep(STR."Delete \{patientID} patient from CTE");
        cteAiSteps.deleteCtePatient(patientID);
        testDefinitionSteps.logEvidence("Patient DELETE request submitted successfully",
                "Patient DELETE request submitted successfully", true);
    }
    @When("the user submit GET request to get $patientID patient treatment by config ID: $configID")
    public void getTreatmentByConfigID(String patientID, String configID)
    {
        testDefinitionSteps.addStep(STR."Get patient treatment by config ID: \{configID}");
        cteAiSteps.getPatientTreatmentByConfId(patientID, configID);
        testDefinitionSteps.logEvidence("GET request submitted successfully",
                "GET request submitted successfully", true);
    }
    @When("the user submit GET request to get treatment by treatment ID: $treatmentID")
    public void getTreatmentByTreatmentID(String treatmentID)
    {
        testDefinitionSteps.addStep(STR."Get treatment by treatment ID: \{treatmentID}");
        cteAiSteps.getTreatmentById(treatmentID);
        testDefinitionSteps.logEvidence("GET request submitted successfully",
                "GET request submitted successfully", true);
    }
    @When("the user submit DELETE request to delete treatment by treatment ID: $treatmentID")
    public void deleteTreatmentByTreatmentID(String treatmentID)
    {
        testDefinitionSteps.addStep(STR."Delete \{treatmentID} treatment");
        cteAiSteps.deleteTreatmentById(treatmentID);
        testDefinitionSteps.logEvidence("Treatment DELETE request submitted successfully",
                "Treatment DELETE request submitted successfully", true);
    }
    @When("the user submits PUT request to update treatment configuration: $fileName")
    public void updateTreatment(String fileName)
    {
        String filePath = String.valueOf(Paths.get(getSystemParameter(CTE_AI_BASE_PATH),
                STR."\{fileName}.json"));
        testDefinitionSteps.addStep(STR."update treatments \{fileName}");
        cteAiSteps.updateTreatments(FileUtils.readFromFileByAbsolutePath(filePath));
        testDefinitionSteps.logEvidence("Treatments PUT request submitted successfully",
                "Treatments PUT request submitted successfully", true);
    }
    @When("the user submits PUT request to update treatments configuration: $fileName")
    public void updateTreatments(StringList fileNames)
    {
        testDefinitionSteps.addStep(STR."update treatments \{fileNames}");
        for (String fileName : fileNames.getList()) {
            String filePath = String.valueOf(Paths.get(getSystemParameter(CTE_AI_BASE_PATH),
                    STR."\{fileName}.json"));
            cteAiSteps.updateTreatments(FileUtils.readFromFileByAbsolutePath(filePath));
        }
        testDefinitionSteps.logEvidence("Treatments PUT request submitted successfully",
                "Treatments PUT request submitted successfully", true);
    }
    @Given("the Following treatment are configure to patient: $table")
    public void configuredTreatment(ExamplesTable table)
    {
        testDefinitionSteps.addStep(STR."Check if treatment is configured to patient");
        cteAiSteps.configureTreatmentToPatient(table, table.getHeaders().getFirst(), table.getHeaders().getLast());
        testDefinitionSteps.logEvidence("treatment configured successfully",
                "treatment configured successfully", true);
    }
    @Given("the Following treatments are configure to patients: $examplesTable")
    @When("the following treatments have been configured for the patients: $examplesTable")
    public void configuredTreatments(ExamplesTable examplesTable) {
        testDefinitionSteps.addStep(STR."Check if treatments is configured to patient \{examplesTable.getRows()}");
        cteAiSteps.ingestTreatmentToPatient(examplesTable, examplesTable.getHeaders().getFirst(), examplesTable.getHeaders().getLast());
        testDefinitionSteps.logEvidence("treatment configured successfully",
                "treatment configured successfully", true);
    }
    @Then("the following ingestion time for $patient")
    public void patientIngestionTime(String patient)
    {
        testDefinitionSteps.addStep(STR."Check \{patient} patient ingestion time start");
        cteAiSteps.lastIngestionDateTime();
        testDefinitionSteps.logEvidence("Ingestion time start presented",
                "Ingestion time start presented", true);

    }
    @Then("the following Latency ingestion time for patient is less then $time $timeMeasure")
    public void latencyIngestionTime(int time, String timeMeasure)
    {
        testDefinitionSteps.addStep("Check patient ingestion latency");
        cteAiSteps.checkLatencyOfIngestion("cte-ai-dataAssessment", "INIT_START Runtime Version", time);
        testDefinitionSteps.logEvidence(STR."Patient latancy is less than \{time} \{timeMeasure}",
                STR."Patient latency is less than \{time} \{timeMeasure}" ,true);
    }
    @Then("the following criterion eligibility assessment take less than $time $timeMeasure for $patient patient")
    public void eligibilityAssessmentTime(int time, String timeMeasure, String patient)
    {
        testDefinitionSteps.addStep("Check patient criterion eligibility assessment time");
        cteAiSteps.checkEligibilityAssessment("cte-ai-dataAssessment", List.of(STR."Starting assessment for patient \{patient} with criterion",
                STR."Assessment completed for patient \{patient} with criterion"), time);
        testDefinitionSteps.logEvidence(STR."Patient criterion eligibility assessment time less than \{time} \{timeMeasure}",
                STR."Patient criterion eligibility assessment time less than \{time} \{timeMeasure}" ,true);
    }
    @Then("the following score results are aligned for $patient patient with $trial trial and config file: $trialConfig")
    public void scoreResultsAlignedForPatientWithTrialConfig(String patient, String trial, String trialConfig)
    {
        testDefinitionSteps.addStep(STR."Check \{patient} patient matching Score with CTE-AI");
        cteAiSteps.checkEligibilityCalculationWithCTEAi(patient, trial, trialConfig);
        testDefinitionSteps.logEvidence("Correct matching score", "Correct matching score", true);
    }
    @Then("the treatment response contains the following body content $path in properties: $properties")
    public void treatmentResponseContain(String path, StringList properties) {
        testDefinitionSteps.addStep(STR.
                "Check the response");
        String filePath = STR."\{Paths.get(getSystemParameter(CTE_AI_BASE_PATH), path).toString()}.json";
        cteAiSteps.checkTreatmentResponseAlignedWithConfigFile(path, properties.getList());
        testDefinitionSteps.logEvidence(STR.
                        "the response contains the following body: \{FileUtils.readFromFileByAbsolutePath(filePath)}",
                "Response content matches expected (see previous logs)",
                true);
    }
}
