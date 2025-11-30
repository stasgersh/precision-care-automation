package com.ge.onchron.verif.whatsteps.Fulfillment;

import java.io.File;
import java.util.Map;

import com.ge.onchron.verif.howsteps.SfnCommonSteps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.ge.onchron.verif.howsteps.DataModelManagementSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.model.StringList;
import net.thucydides.core.annotations.Steps;
import software.amazon.awssdk.services.sfn.model.ExecutionStatus;

import static com.ge.onchron.verif.utils.ReplaceUtils.replaceStoredId;


public class DataModelManagement {
    @Steps
    private DataModelManagementSteps dataModelSteps;
    @Steps
    private SfnCommonSteps sfnCommonSteps;
    private TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();
    final String sfnEtl = "fulfill-etl";

    @When( "the user creates new '$modelName' data model" )
    public void createNewDataModel( String modelName ) {
        testDefinitionSteps.addStep( STR."Create new data model:\n"
                + STR."Send POST request to \{dataModelSteps.getDataModelStepUrl()} with file \{new File( modelName ).getName()} as body." );
        dataModelSteps.createNewDataModel( modelName );
        testDefinitionSteps.logEvidence( "Create Data Model request sent successfully",
                "Create Data Model request sent without errors (see previous logs)", true );
    }

    @When( "the user updates '$modelName' data model" )
    public void updatesDataModel( String modelName ) {
        testDefinitionSteps.addStep( STR."Update data model:\n"
                + STR."Send PUT request to \{dataModelSteps.getDataModelStepUrl()} with file \{new File( modelName ).getName()} as body." );
        dataModelSteps.updateDataModel( modelName );
        testDefinitionSteps.logEvidence( "Update Data Model request sent successfully",
                "Update Data Model request sent without errors (see previous logs)", true );
    }

    @Given( "'$modelName' data model is not present in the system" )
    @When( "the user deletes '$modelName' data model" )
    public void removeDataModel( String modelName ) {
        testDefinitionSteps.addStep( STR."Remove data model '\{modelName}'\n"
                + STR."Send DELETE request to \{dataModelSteps.getDataModelStepUrl()}/\{modelName}" );
        dataModelSteps.deleteDataModel( modelName );
        testDefinitionSteps.logEvidence( "Delete Data Model request sent successfully",
                "Delete Data Model request sent without errors (see previous logs)", true );
    }

    @When( "the user requests the list of data models" )
    public void getDataModels() {
        testDefinitionSteps.addStep( STR."Get list of data models:\n"
                + STR."Send GET request to \{dataModelSteps.getDataModelStepUrl()}" );
        dataModelSteps.getDataModelsList( null );
        testDefinitionSteps.logEvidence( "Get Data Models request sent successfully",
                "Get Data Models request sent without errors (see previous logs)", true );
    }

    @When( "the user requests the list of data models with filter '$filter'" )
    public void getFilteredDataModels( String filter ) {
        testDefinitionSteps.addStep( STR."Get list of data models with filter '\{filter}'\n"
                + STR."Send GET request to \{dataModelSteps.getDataModelStepUrl()}?filter=\{filter}" );
        dataModelSteps.getDataModelsList( filter );
        testDefinitionSteps.logEvidence( "Get Data Models request sent successfully",
                "Get Data Models request sent without errors (see previous logs)", true );
    }

    @When( "the user requests the data model '$modelName' by name" )
    public void getDataModelByName( String modelName ) {
        testDefinitionSteps.addStep( STR."Get data model's '\{modelName}' definition \n"
                + STR."Send GET request to \{dataModelSteps.getDataModelStepUrl()}/\{modelName}" );
        dataModelSteps.getDataModelByName( modelName );
        testDefinitionSteps.logEvidence( "Get Data Model by name request sent successfully",
                "Get Data Model by name request sent without errors (see previous logs)", true );
    }

    @Given( "following data models are present in the system: $modelNames" )
    @When( "following data models are present in the system: $modelNames" )
    public void createDataModelsIfAbsent( StringList modelNames ) {
        testDefinitionSteps.addStep( STR."Create following data models if absent \{modelNames.getList()}:\n"
                + STR."Send POST request to \{dataModelSteps.getDataModelStepUrl()} with model file as body." );
        for ( String modelName : modelNames.getList() ) {
            dataModelSteps.createNewDataModel( modelName );
        }
        testDefinitionSteps.logEvidence( "Create Data Model requests sent successfully",
                "Create Data Model request sent without errors (see previous logs)", true );
    }

    @Given( "following data models are updated in the system: $modelNames" )
    public void updateDataModelsIfAbsent( StringList modelNames ) {
        testDefinitionSteps.addStep( STR."Update following data models \{modelNames.getList()}:\n"
                + STR."Send POST request to \{dataModelSteps.getDataModelStepUrl()} with model file as body." );
        for ( String modelName : modelNames.getList() ) {
            dataModelSteps.updateDataModel( modelName );
        }
        testDefinitionSteps.logEvidence( "Update Data Model requests sent successfully",
                "Update Data Model request sent without errors (see previous logs)", true );
    }

    @When( "the user creates new '$modelName' data model with $alteration" )
    public void createInvalidDataModel( String modelName, String alteration ) {
        testDefinitionSteps.addStep( STR."Create new invalid \{modelName} data model:\n"
                + STR."Send POST request to \{dataModelSteps.getDataModelStepUrl()} with \{alteration}" );
        dataModelSteps.createInvalidDataModel( modelName, alteration );
        testDefinitionSteps.logEvidence( "Create Data Model request sent successfully (regardless of response)",
                "Create Data Model request sent without errors (see previous logs)", true );
    }

    @When( "the user updates '$modelName' data model with $alteration" )
    public void updateInvalidDataModel( String modelName, String alteration ) {
        testDefinitionSteps.addStep( STR."Update \{modelName} data model with invalid data:\n"
                + STR."Send PUT request to \{dataModelSteps.getDataModelStepUrl()} with \{alteration}" );
        dataModelSteps.updateInvalidDataModel( modelName, alteration );
        testDefinitionSteps.logEvidence( "Update Data Model request sent successfully (regardless of response)",
                "Update Data Model request sent without errors (see previous logs)", true );
    }

    @Given( "the $isMandatory ETL process is finished within $timeoutSeconds seconds with status $status" )
    @Then( "the $isMandatory ETL process is finished within $timeoutSeconds seconds with status $status" )
    public void theETLProcess( Boolean isMandatory, int timeoutSeconds, String status ) {
        testDefinitionSteps.addStep( STR."Check\{isMandatory ? " " : " optional"} ETL process" );
        sfnCommonSteps.waitForSfnFinish( isMandatory, timeoutSeconds * 1000, ExecutionStatus.fromValue( status.toUpperCase() ), sfnEtl);
        testDefinitionSteps.logEvidence( STR."the ETL process \{isMandatory ? "" : "if started"} is finished within \{timeoutSeconds} seconds with status \{status}",
                "the ETL process is finished with expected status", true );
    }

    @Given( "$count $isMandatory ETL processes are finished within $timeoutSeconds seconds with status $status for patient $patient" )
    @Then( "$count $isMandatory ETL processes are finished within $timeoutSeconds seconds with status $status for patient $patient" )
    public void waitForETLProcess( int count, Boolean isMandatory, int timeoutSeconds, String status, String patient ) {
        patient = replaceStoredId(patient);
        testDefinitionSteps.addStep( STR."Check\{isMandatory ? " " : " optional"} ETL process for patient \{patient}" );
        sfnCommonSteps.waitForSfnFinish( isMandatory, timeoutSeconds * 1000, ExecutionStatus.fromValue( status.toUpperCase() ), Map.of( "parameters.patientId", patient ), count, sfnEtl);
        testDefinitionSteps.logEvidence( STR."the \{count} ETL processes \{isMandatory ? "" : "if started"} finished within \{timeoutSeconds} seconds with status \{status}",
                "the ETL process is finished with expected status", true );
    }

    @Given( "[API] {a|an} $isMandatory ETL process is started in $startTimeout seconds and finished within $timeoutSeconds seconds with status $status for patient $patientId" )
    @Then( "[API] {a|an} $isMandatory ETL process is started in $startTimeout seconds and finished within $timeoutSeconds seconds with status $status for patient $patientId" )
    public void waitForETLProcessPOC( Boolean isMandatory, int startTimeoutSeconds, int timeoutSeconds, String status, String patientId ) {
        patientId = replaceStoredId(patientId);
        testDefinitionSteps.addStep( String.format( "Check %s ETL process is started in %s seconds and finished within %s seconds with status %s for patient %s",
                isMandatory ? "mandatory" : "optional", startTimeoutSeconds, timeoutSeconds, status, patientId ) );
        sfnCommonSteps.waitForSfnFinish( isMandatory, timeoutSeconds * 1000, startTimeoutSeconds * 1000,
                ExecutionStatus.fromValue( status.toUpperCase() ), Map.of( "parameters.patientId", patientId ), 1, sfnEtl);
        testDefinitionSteps.logEvidence(
                String.format( "The %s ETL process is started in %s seconds and finished within %s seconds with status %s for patient %s",
                        isMandatory ? "mandatory" : "optional", startTimeoutSeconds, timeoutSeconds, status, patientId ),
                "the ETL process is finished with expected status",
                true );
    }

    @Given( "the $isMandatory ETL process is started" )
    @Then( "the $isMandatory ETL process is started" )
    @When( "the $isMandatory ETL process is started" )
    public void theETLProcessStarted( Boolean isMandatory ) {
        testDefinitionSteps.addStep( STR."Check\{isMandatory ? " " : " optional"} ETL process is started" );
        sfnCommonSteps.waitForSfnStart( isMandatory, sfnEtl);
        testDefinitionSteps.logEvidence( STR."the ETL process \{isMandatory ? "" : "is started"}",
                "the ETL process is started", true );
    }

    @Then( "the $isMandatory ETL process $isStarted started for $patient patient" )
    public void theETLProcessNotStartedForPatient( Boolean isMandatory, Boolean isStarted, String patient ) {
        testDefinitionSteps.addStep( STR."Check ETL process is \{isStarted ? "" : "not"} started for \{patient} patient" );
        sfnCommonSteps.waitForSfnStart( isMandatory, isStarted, Map.of( "parameters.patientId", patient ), sfnEtl);
        testDefinitionSteps.logEvidence( STR."the ETL process \{isStarted ? "" : "not"} started for \{patient} patient",
                STR."the ETL process \{isStarted ? "" : "not"} started for patient", true );
    }

    @Then( "the $isMandatory ETL process $isStarted started for $dataModel dataModel" )
    public void theETLProcessNotStartedForDataModel( Boolean isMandatory, Boolean isStarted, String dataModel ) {
        testDefinitionSteps.addStep( STR."Check ETL process is \{isStarted ? "" : "not"} started for \{dataModel} dataModel" );
        sfnCommonSteps.waitForSfnStart( isMandatory, isStarted, Map.of( "parameters.classId", dataModel ), sfnEtl);
        testDefinitionSteps.logEvidence( STR."the ETL process \{isStarted ? "" : "not"} started for \{dataModel} dataModel",
                STR."the ETL process \{isStarted ? "" : "not"} started for \{dataModel} dataModel", true );
    }

    @Given( "the $isMandatory ETL processes are finished within $timeoutSeconds seconds with status $status for dataModel $dataModel" )
    @Then( "the $isMandatory ETL processes are finished within $timeoutSeconds seconds with status $status for dataModel $dataModel" )
    public void theETLProcessNotFinishedForDataModel(Boolean isMandatory, int timeoutSeconds, String status, String dataModel ) {
        testDefinitionSteps.addStep( STR."Check\{isMandatory ? " " : " optional"} ETL process for dataModel \{dataModel}" );
        sfnCommonSteps.waitForSfnFinish( isMandatory, timeoutSeconds * 1000, ExecutionStatus.fromValue( status.toUpperCase() ), Map.of( "parameters.classId", dataModel ), sfnEtl);
        testDefinitionSteps.logEvidence( STR."the ETL processes \{isMandatory ? "" : "if started"} finished within \{timeoutSeconds} seconds with status \{status}",
                "the ETL process is finished with expected status", true );
    }

    @Then( "the $isMandatory ETL processes are finished within $timeoutSeconds seconds with status $status for valueSet $valueSet" )
    public void theETLProcessNotFinishedForvalueSet(Boolean isMandatory, int timeoutSeconds, String status, String valueSet ) {
        testDefinitionSteps.addStep( STR."Check\{isMandatory ? " " : " optional"} ETL process for valueSet \{valueSet}" );
        sfnCommonSteps.waitForSfnFinish( isMandatory, timeoutSeconds * 1000, ExecutionStatus.fromValue( status.toUpperCase() ), Map.of( "parameters.valueSetId", valueSet ), sfnEtl);
        testDefinitionSteps.logEvidence( STR."the ETL processes \{isMandatory ? "" : "if started"} finished within \{timeoutSeconds} seconds with status \{status}",
                "the ETL process is finished with expected status", true );
    }

    @Given( "the Delete $resourceName resource with $resourceId id process is finished in $stackName stack name" )
    @Then( "the Delete $resourceName resource with $resourceId id process is finished in $stackName stack name" )
    public void theDeleteProcess( String resourceName, String resourceId, String stackName) {
        testDefinitionSteps.addStep( STR."Check Delete \{resourceName} resource process" );
        dataModelSteps.deleteTrigger( resourceId, resourceName, stackName );
        testDefinitionSteps.logEvidence( STR."the Delete \{resourceName} resource with \{resourceId} id process is finished",
                "Delete resource process finished", true );
    }

    @Given( "[API] delete data ETL process is executed with patient id $resourceId in $stackName stack name" )
    @When("[API] delete data ETL process is executed with patient id $resourceId in $stackName stack name")
    public void deleteTriggerWithWait( String resourceName, String resourceId, String stackName ) {
        testDefinitionSteps.addStep( String.format( "Check Delete data ETL process is executed with patient id %s", resourceId ) );
        dataModelSteps.deleteTriggerWithWait( resourceId, "startExecutionReq", stackName);
        testDefinitionSteps.logEvidence(
                String.format( "Delete data ETL process is executed with patient id %s", resourceId ),
                String.format( "Delete data ETL process is executed with patient id %s", resourceId ),
                true );
    }

}
