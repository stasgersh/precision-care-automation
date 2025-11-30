package com.ge.onchron.verif.whatsteps;

import com.ge.onchron.verif.howsteps.SNSCommonSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import net.thucydides.core.annotations.Steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class SNSCommon {

    @Steps
    private SNSCommonSteps snsCommonSteps;

    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @When("the DATA_FABRIC_DELETE SNS event from file $fileName was published with start time offset $timeOffSet sec to delete patient $patientId, triggering the ETL Step Function $sfnName")
    @Given("the DATA_FABRIC_DELETE SNS event from file $fileName was published with start time offset $timeOffSet sec to delete patient $patientId, triggering the ETL Step Function $sfnName")
    @Then("the DATA_FABRIC_DELETE SNS event from file $fileName was published with start time offset $timeOffSet sec to delete patient $patientId, triggering the ETL Step Function $sfnName")
    public void waitForPatientSfnProcess(String sfnName, String patientId, String fileName, long timeOffSet) {
        testDefinitionSteps.addStep(STR."The DATA_FABRIC_DELETE SNS event from file \{fileName} was published to delete patient \{patientId}, triggering the ETL Step Function \{sfnName}");
        snsCommonSteps.sendSnsAndSetGlobalDateForSfn(sfnName, patientId, fileName, timeOffSet);
        testDefinitionSteps.logEvidence(STR."The DATA_FABRIC_DELETE SNS event was published to delete patient \{patientId}, triggering the ETL Step Function \{sfnName}",
                STR."The DATA_FABRIC_DELETE SNS event was published to delete patient \{patientId}, triggering the ETL Step Function \{sfnName}", true);
    }
}
