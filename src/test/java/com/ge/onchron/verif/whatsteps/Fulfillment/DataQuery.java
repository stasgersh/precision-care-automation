package com.ge.onchron.verif.whatsteps.Fulfillment;

import com.ge.onchron.verif.howsteps.DataQuerySteps;
import com.ge.onchron.verif.howsteps.RestApiSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.model.StringList;
import com.ge.onchron.verif.utils.FileUtils;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.*;

import java.nio.file.Paths;
import java.util.List;

import static com.ge.onchron.verif.TestSystemParameters.*;

public class DataQuery {
    private final TestDefinitionSteps testDefinitionSteps =
        TestDefinitionSteps.getInstance();
    private       RestApiSteps        restApiSteps;
    @Steps
    private       DataQuerySteps      dataQuerySteps;

    @When("the user executes [$data_queries] data quer{y|ies} for single patient [$patientId]")
    public void postingToDataQuery(List<String> data_query, String patientId) {
        testDefinitionSteps.addStep(STR."Execute data queries from file \{data_query} with post request for single patient \{patientId}");
        dataQuerySteps.executeDataQuery(data_query, patientId);
        testDefinitionSteps.logEvidence("Data queries request sent successfully",
                "Data queries request sent successfully (see previous logs)",
                true);
    }
    @When("the user executes data quer{y|ies} for list of patients")
    public void executeListOfPatientsDataQuery() {
        testDefinitionSteps.addStep(STR."Execute data query with post request for list of patients");
        dataQuerySteps.executeListOfPatientsDataQuery();
        testDefinitionSteps.logEvidence("Data query request sent successfully",
                "Data query request sent successfully (see previous logs)",
                true);
    }
    @When("the user executes [$data_query] data query for single patient [$patientId] with different attributes in \"show\" component: $attributes")
    public void executeDataQueryWithDifferentShowComponentValues(List<String> data_query, String patientId, StringList attributes) {
        testDefinitionSteps.addStep(STR."Execute data query from file \{data_query} with different attributes: \{attributes}\n, in \"show\" component");
        dataQuerySteps.executeDataQuery(dataQuerySteps.dataQueryUpdate(data_query, attributes.getList()), patientId);
        testDefinitionSteps.logEvidence("Data query request executed successfully",
            "Data query request executed successfully", true );
    }
    @When("the user executes [$data_query] data query for single patient [$patientId] with \"kuku\" component: $path")
    public void executeDataQueryWithDifferentInvalidComponentValues(List<String> data_query, String patientId, String path) {
        String filePath = String.valueOf(Paths.get(getSystemParameter(DATA_QUERY_RESOURCES),
                STR."\{path}.json"));
        testDefinitionSteps.addStep(STR."Execute data query from file \{data_query} with \"kuku\" components: ");
        dataQuerySteps.executeDataQuery(FileUtils.readFromFileByAbsolutePath(filePath), patientId);
        testDefinitionSteps.logEvidence("Data query request executed successfully",
                "Data query request executed successfully", true );
    }
    @When("the user executes data query for list of patients with \"kuku\" component: $path")
    public void executeDataQuerPatientListyWithDifferentInvalidComponentValues(String path) {
        String filePath = String.valueOf(Paths.get(getSystemParameter(DATA_QUERY_RESOURCES),
                STR."\{path}.json"));
        testDefinitionSteps.addStep(STR."Execute data query with \"kuku\" component");
        dataQuerySteps.executeListOfPatientsDataQuery(FileUtils.readFromFileByAbsolutePath(filePath));
        testDefinitionSteps.logEvidence("Data query request executed successfully",
                "Data query request executed successfully", true );
    }
    @When("the user executes [$data_query] data query for single patient [$patientId] with different invalid attributes in \"show\" component: $attributes")
    public void executeDataQueryWithInvalidDifferentShowComponentValues(List<String> data_query, String patientId, Integer attributes) {
        testDefinitionSteps.addStep(STR."Execute data query from file \{data_query} with different attributes: \{attributes}\n, in \"show\" component");
        dataQuerySteps.executeDataQuery(dataQuerySteps.dataQueryUpdate(data_query, attributes), patientId);
        testDefinitionSteps.logEvidence("Data query request executed successfully",
                "Data query request executed successfully", true );
    }
    @When("the user moving to next page")
    public void userMovingToNextPage() {
        testDefinitionSteps.addStep("Move next page" );
        dataQuerySteps.executeForNextPageDataQuery();
        testDefinitionSteps.logEvidence("Next page presented","Next page presented", true );
    }
    @When("the user executes data query for list of patients with different attributes in \"show\" components: $attributes")
    public void executeListDataQueryWithInvalidDifferentShowComponentValues(StringList attributes) {
        testDefinitionSteps.addStep(STR."Execute data query with different attributes: \{attributes}\n, in \"show\" component");
        dataQuerySteps.executeListOfPatientsDataQuery(dataQuerySteps.dataQueryUpdateForList(attributes.getList()));
        testDefinitionSteps.logEvidence("Data query request executed successfully",
                "Data query request executed successfully", true );
    }

    @When("the user executes data query for list of patients with different invalid attributes in \"show\" components: $attributes")
    public void executeQueryForPatientListWithDifferentShowComponentValues(Integer attributes) {
        testDefinitionSteps.addStep(STR."Execute data query from file with different attributes: \{attributes}\n, in \"show\" component");
        dataQuerySteps.executeListOfPatientsDataQuery(dataQuerySteps.dataQueryUpdateForList(attributes));
        testDefinitionSteps.logEvidence("Data query request executed successfully",
                "Data query request executed successfully", true );
    }

    @When("the user executes [$data_query] data query for single patient [$patientId] with $component components" )
    @Alias("the user executes [$data_query] data query for list of patients [$patientId] with $component components")
    public void differentComponentValues(List<String> data_query, String patientId, String components) {
        testDefinitionSteps.addStep(STR."Execute data query from file \{data_query} with \{components} component");
        String updatedJsonFile = dataQuerySteps.dataQueryUpdate(data_query, components);
        dataQuerySteps.executeDataQuery(updatedJsonFile, patientId);
        testDefinitionSteps.logEvidence("Data query request executed successfully",
                "Data query request executed successfully", true );
    }

    @When("the user executes [$data_query] data query for list of patients with \"show\" components: $attributes" )
    public void differentComponentValues(List<String> data_query, StringList attributes) {
        testDefinitionSteps.addStep(STR."Execute data query from file \{data_query} with different attributes: \{attributes}\n, in \"show\" component");
        String updatedJsonFile = dataQuerySteps.dataQueryUpdate(data_query, attributes.getList());
        dataQuerySteps.executeDataQuery(updatedJsonFile);
        testDefinitionSteps.logEvidence("Data query request executed successfully",
                "Data query request executed successfully", true );
    }

    @When("the user executes [$data_query] data query for single patient [$patientId] with $parameter parameter in \"from\" component")
    public void executeDataQueryWithDifferentParametersInFromComponent(List<String> data_query, String patientId, String parameter) {
        testDefinitionSteps.addStep(STR."Execute data query from file \{data_query} with different parameters \{parameter}, in \"from\" component");
        dataQuerySteps.executeDataQuery(data_query, patientId);
        testDefinitionSteps.logEvidence(STR."Data query request executed successfully",
                "Data query request executed successfully", true);
    }
    @When("the user executes data query [$data_query] for single patient [$patientId] with invalid $parameter parameter in \"from\" component")
    public void executeDataQueryWithDifferentInvalidParametersInFromComponent(List<String> data_query, String patientId, int parameter) {
        testDefinitionSteps.addStep(STR."Execute data query from file \{data_query} with different parameters \{parameter}, in \"from\" component");
        dataQuerySteps.executeDataQuery(dataQuerySteps.dataQueryUpdate(parameter, null), patientId);
        testDefinitionSteps.logEvidence(STR."Data query request executed successfully",
                "Data query request executed successfully", true);
    }
    @When("the user executes data query for list of patients with different invalid values in \"$component\" component: $file")
    public void executesDataQueryForListOfPatientsWithValuesInInvalidFilterComponents(String file, String component) {
        String filePath = String.valueOf(Paths.get(getSystemParameter(DATA_QUERY_RESOURCES),
                STR."\{file}.json"));
        testDefinitionSteps.addStep(STR."Execute data query for list of patients with different values, in \"\{component}\" component");
        dataQuerySteps.executeListOfPatientsDataQuery(FileUtils.readFromFileByAbsolutePath(filePath));
        testDefinitionSteps.logEvidence("Data query request executed successfully",
                "Data query request executed successfully", true );
    }
    @When("the user executes [$data_query] data query from $parameter for single patient [$patientId] with $file_name values in \"filter\" component")
    public void executesDataQueryWithValuesInFilterComponents(List<String> data_query, String patientId, String parameter, String file_name) {
        testDefinitionSteps.addStep(STR."Execute data query from file \{file_name} from \{parameter} with different values \{parameter}, in \"filter\" component");
        dataQuerySteps.executeDataQuery(dataQuerySteps.dataQueryUpdate(data_query, file_name), patientId);
        testDefinitionSteps.logEvidence("Data query request executed successfully",
            "Data query request executed successfully", true );
    }
    @When("the user executes data query for list of patients with different values in \"filter\" component: $file")
    public void executesDataQueryForListOfPatientsWithValuesInFilterComponents(String file) {
        String filePath = String.valueOf(Paths.get(getSystemParameter(DATA_QUERY_RESOURCES),
                STR."\{file}.json"));
        testDefinitionSteps.addStep(STR."Execute data query for list of patients with different values, in \"filter\" component");
        dataQuerySteps.executeListOfPatientsDataQuery(FileUtils.readFromFileByAbsolutePath(filePath));
        testDefinitionSteps.logEvidence("Data query request executed successfully",
                "Data query request executed successfully", true );
    }
    @When("the user executes dataQuery for list of patients with different attributes $attributes in \"sort\" component by $order order")
    public void executeDataQueryWithDifferentAttributesInSortComponent(StringList attributes, String order) {
        testDefinitionSteps.addStep(STR."Execute data query for list of patients with different attributes \{attributes}\n, in \"sort\" component");
        String updatedJsonFile = dataQuerySteps.dataQueryUpdateForList(attributes.getList(), order);
        dataQuerySteps.executeDataQuery(updatedJsonFile);
        testDefinitionSteps.logEvidence("Data query request executed successfully",
                "Data query request executed successfully", true );
    }
    @Then("the dataQuery response contains the following body content: $path")
    public void dataQueryResponseContains(String path) {
        String filePath = STR."\{Paths.get(getSystemParameter(DATA_QUERY_RESOURCES), path).toString()}.json";
        testDefinitionSteps.addStep(STR.
                "Check the response");
        dataQuerySteps.checkDataQueryIfEquals(path);
        testDefinitionSteps.logEvidence(STR.
                        "the response contains the following body: \{FileUtils.readFromFileByAbsolutePath(filePath)}",
                "Response content matches expected (see previous logs)",
                true);
    }
    @Then("the dataQuery response aligned to requested resource: $path")
    public void dataQueryResponseAlignedToRequestedResource(String path) {
        testDefinitionSteps.addStep("Compare between Fhir resource and DataQuery response");
        dataQuerySteps.checkDataQueryAlignedWithFhirResource(path);
        testDefinitionSteps.logEvidence("DataQuery response values match as source resouce values",
                "DataQuery response values match as source resouce values", true);
    }
    @Then("the version stored")
    public void versionStored() {
        testDefinitionSteps.addStep(STR.
                "Verify that the version stored");
        dataQuerySteps.checkPatientVersionStored();
        testDefinitionSteps.logEvidence(STR.
                        "the version stored",
                "the version stored",
                true);
    }
    @Then("the version updated")
    public void versionUpdated() {
        testDefinitionSteps.addStep(STR.
                "Verify that the version updated");
        dataQuerySteps.checkPatientVersionUpdated();
        testDefinitionSteps.logEvidence(STR.
                        "the version updated",
                "the version updated",
                true);
    }
    @Then("the dataQuery for patient-list response contains the following body content: $path")
    public void dataQueryPatientListResponseContains(String path) {
        String filePath = STR."\{Paths.get(getSystemParameter(DATA_QUERY_RESOURCES), path).toString()}.json";
        testDefinitionSteps.addStep(STR.
                "Check the response");
        dataQuerySteps.checkDataQueryListIfEquals(path);
        testDefinitionSteps.logEvidence(STR.
                        "the response contains the following body: \{FileUtils.readFromFileByAbsolutePath(filePath)}",
                "Response content matches expected (see previous logs)",
                true);
    }
    @Then("the dataQuery for patient-list with different attributes response contains the following body content: $path")
    public void dataQueryPatientListResponseWithDifferentAttributesContains(String path) {
        String filePath = STR."\{Paths.get(getSystemParameter(DATA_QUERY_RESOURCES), path).toString()}.json";
        testDefinitionSteps.addStep(STR.
                "Check the response");
        dataQuerySteps.checkDataQueryAttributesListIfEquals(path);
        testDefinitionSteps.logEvidence(STR.
                        "the response contains the following body: \{FileUtils.readFromFileByAbsolutePath(filePath)}",
                "Response content matches expected (see previous logs)",
                true);
    }
    @Then("the dataQuery for patient-list sorted by $order order with $attribute attribute")
    public void dataQueryPatientListOrder(String order, String attribute) {
        testDefinitionSteps.addStep(STR."Check response by \{order} order");
        dataQuerySteps.checkJsonOrder(order, attribute);
        testDefinitionSteps.logEvidence("the data query ordered successfully",
                "the data query ordered successfully", true);
    }
}
