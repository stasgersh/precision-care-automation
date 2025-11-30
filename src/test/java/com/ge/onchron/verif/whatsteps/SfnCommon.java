package com.ge.onchron.verif.whatsteps;

import com.ge.onchron.verif.howsteps.SfnCommonSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import software.amazon.awssdk.services.sfn.model.ExecutionStatus;

import java.util.Map;

public class SfnCommon {
    @Steps
    private SfnCommonSteps sfnCommonSteps;
    private TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Given( "the $isMandatory $sfnName Sfn processes are finished within $timeoutSeconds seconds with status $status for patient $patient" )
    @Then( "the $isMandatory $sfnName Sfn processes are finished within $timeoutSeconds seconds with status $status for patient $patient" )
    public void waitForPatientSfnProcess(Boolean isMandatory, int timeoutSeconds, String status, String patient, String sfnName) {
        testDefinitionSteps.addStep( STR."Check\{isMandatory ? " " : " optional"} \{sfnName} sfn process for patient \{patient}" );
        sfnCommonSteps.waitForSfnFinish( isMandatory, timeoutSeconds * 1000, ExecutionStatus.fromValue( status.toUpperCase() ), Map.of( "patientId", patient ), sfnName);
        testDefinitionSteps.logEvidence( STR."the \{sfnName} sfn processes \{isMandatory ? "" : "if started"} finished within \{timeoutSeconds} seconds with status \{status}",
                STR."the \{sfnName} sfn process is finished with expected status", true );
    }
    @Given( "the $isMandatory $sfnName Sfn processes are finished within $timeoutSeconds seconds with status $status for property $propertyName and propertyValue $propertyValue" )
    @Then( "the $isMandatory $sfnName Sfn processes are finished within $timeoutSeconds seconds with status $status for property $propertyName and propertyValue $propertyValue" )
    public void waitForPropertySfnProcess(Boolean isMandatory, int timeoutSeconds, String status, String patient, String sfnName, String propertyName, String propertyValue) {
        testDefinitionSteps.addStep( STR."Check\{isMandatory ? " " : " optional"} \{sfnName} sfn process for patient \{patient}" );
        sfnCommonSteps.waitForSfnFinish( isMandatory, timeoutSeconds * 1000, ExecutionStatus.fromValue( status.toUpperCase() ), Map.of( propertyName, propertyValue ), sfnName);
        testDefinitionSteps.logEvidence( STR."the \{sfnName} sfn processes \{isMandatory ? "" : "if started"} finished within \{timeoutSeconds} seconds with status \{status}",
                STR."the \{sfnName} sfn process is finished with expected status", true );
    }

    @Given( "the $isMandatory $sfnName Sfn processes are finished $isExplicit within $timeoutSeconds seconds with status $status for patient $patient" )
    @Then( "the $isMandatory $sfnName Sfn processes are finished $isExplicit within $timeoutSeconds seconds with status $status for patient $patient" )
    public void waitForPatientSfnProcess(Boolean isMandatory, Boolean isExplicit, int timeoutSeconds, String status, String patient, String sfnName) {
        testDefinitionSteps.addStep( STR."Check\{isMandatory ? " " : " optional"} \{sfnName} sfn process for patient \{patient}" );
        sfnCommonSteps.waitForSfnFinish( isMandatory ,timeoutSeconds * 1000, ExecutionStatus.fromValue( status.toUpperCase() ), Map.of( "patients[0].id", patient ), isExplicit ,sfnName);
        testDefinitionSteps.logEvidence( STR."the \{sfnName} sfn processes \{isMandatory ? "" : "if started"} finished within \{timeoutSeconds} seconds with status \{status}",
                STR."the \{sfnName} sfn process is finished with expected status", true );
    }
    @Then("the $sfnName Sfn processes response $expression the value \"$propertyValue\" for patient $patient" )
    public void checkSfnJsonPropertyValueInResponse(String sfnName, String expression, String propertyValue, String patient ) {
        testDefinitionSteps.addStep( STR."Check \{sfnName} step function response" );
        sfnCommonSteps.checkSfnJsonPropertyValueInResponse(sfnName, Map.of("patients[0].id", patient),propertyValue, expression.equals( "contains" ) );
        testDefinitionSteps.logEvidence( STR.
                        "the response \{expression} the value: '\{propertyValue}'",
                "Response contains expected value (See previous logs)",
                true );
    }
    @Given( "the $isMandatory $sfnName sfn process is started" )
    @Then( "the $isMandatory $sfnName sfn process is started" )
    @When( "the $isMandatory $sfnName sfn process is started" )
    public void theSFNProcessStarted( Boolean isMandatory, String sfnName) {
        testDefinitionSteps.addStep( STR."Check\{isMandatory ? " " : " optional"} ETL process is started" );
        sfnCommonSteps.waitForSfnStart( isMandatory, sfnName);
        testDefinitionSteps.logEvidence( STR."the ETL process \{isMandatory ? "" : "is started"}",
                "the ETL process is started", true );
    }
}
