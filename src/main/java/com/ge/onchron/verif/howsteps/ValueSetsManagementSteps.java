package com.ge.onchron.verif.howsteps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ge.onchron.verif.model.RestRequest;
import com.ge.onchron.verif.model.valueSetsConfig.*;
import com.ge.onchron.verif.utils.FileUtils;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;
import net.thucydides.core.annotations.Steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;


import static com.ge.onchron.verif.TestSystemParameters.*;

import static com.ge.onchron.verif.utils.FileUtils.readFromFileByAbsolutePath;
import static org.junit.Assert.assertEquals;

public class ValueSetsManagementSteps {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataModelManagementSteps.class);
    @Steps
    private RestApiSteps restApiSteps;

    public void deleteValueSet(String valueSetsName) {
        RequestSpecBuilder rsb = new RequestSpecBuilder()
                .setBaseUri(getSystemParameter(VALUE_SETS_BASE_PATH))
                .setBasePath(STR."/\{valueSetsName}")
                .setContentType("application/json");
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(Method.DELETE, rsb.build()));
    }

    public void createNewValueSet(String valueSetsName) {
        RequestSpecification rs = getCreateUpdateRequest(valueSetsName);
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(Method.POST, rs));
    }
    public void updateValueSet(String fileName, String valueSetName) {
        Path filePath = Paths.get(getSystemParameter(VALUE_SETS_RESOURCES), STR."\{fileName}.json");
        String body = readFromFileByAbsolutePath(filePath.toString());
        RequestSpecBuilder rsb = new RequestSpecBuilder()
                .setBaseUri(getSystemParameter(VALUE_SETS_BASE_PATH))
                .setBasePath(STR."/\{valueSetName}")
                .setContentType("application/json")
                .setBody(body);
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(Method.PUT, rsb.build()));
    }

    private static RequestSpecification getCreateUpdateRequest(String modelName) {
        Path filePath = Paths.get(getSystemParameter(VALUE_SETS_RESOURCES), STR."\{modelName}.json");
        String body = readFromFileByAbsolutePath(filePath.toString());
        return new RequestSpecBuilder()
                .setBaseUri(getSystemParameter(VALUE_SETS_BASE_PATH))
                .setBody(body)
                .setContentType("application/json")
                .build();
    }

    public void getValueSetsList() {
        RequestSpecBuilder rsb = new RequestSpecBuilder()
                .setBaseUri(getSystemParameter(VALUE_SETS_BASE_PATH))
                .setContentType("application/json");
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(Method.GET, rsb.build()));
    }

    public void getValueSet(String valueSetName) {
        RequestSpecBuilder rsb = new RequestSpecBuilder()
                .setBaseUri(getSystemParameter(VALUE_SETS_BASE_PATH))
                .setBasePath(STR."/\{valueSetName}")
                .setContentType("application/json");
        restApiSteps.sendBackendAuthenticatedRequest(new RestRequest(Method.GET, rsb.build()));
    }

    @SneakyThrows
    public ValueSetsResponse getValueSetsConfig(final String fileName) {
        return new ObjectMapper().readValue(FileUtils.loadConfigFile(fileName, VALUE_SETS_RESOURCES), ValueSetsResponse.class);
    }

    @SneakyThrows
    public ValueSetsReq getValueSetsConfigReq(final String fileName) {
        return new ObjectMapper().readValue(FileUtils.loadConfigFile(fileName, VALUE_SETS_RESOURCES), ValueSetsReq.class);
    }
    public ValueSetsListItemResponse getLastResponse() {
        return restApiSteps.getLastResponse().as(ValueSetsListItemResponse.class);
    }

    public ValueSetsResponse getLastRootResponse() {
        return restApiSteps.getLastResponse().as(ValueSetsResponse.class);
    }

    public ValueSetsResponseItem getLastRootResponseItem() {
        return restApiSteps.getLastResponse().as(ValueSetsResponseItem.class);
    }

    public boolean checkValueSetsIfContains(String path) {
        ValueSetsResponse actualValueSetsResponse = getLastRootResponse();
        ValueSetsResponse valueSetsRes = getValueSetsConfig(path);
        for (ValueSetsListItemResponse value : actualValueSetsResponse.valuesets) {
            for (ValueSetsListItemResponse expectedValue : valueSetsRes.valuesets) {
                if (value.name.contains(expectedValue.name) && value.description.contains(expectedValue.description)
                        && value.version.contains(expectedValue.version)) {
                    return true;
                }
            }
        }
        return false;
    }
    public void checkValueSetItemEqual(String path){
        ValueSetsListItemResponse actualValueSetsResponse = getLastResponse();
        ValueSetsReq expectedValueSetsReq = getValueSetsConfigReq(path);

        assertEquals(expectedValueSetsReq.name,actualValueSetsResponse.name);
        assertEquals(expectedValueSetsReq.valueset.description,actualValueSetsResponse.description);
        assertEquals(expectedValueSetsReq.valueset.version,actualValueSetsResponse.version);
    }

    public void checkValueSetsIfEquals(String path) {
        ValueSetsResponseItem actualValueSetsReqResponse = getLastRootResponseItem();
        ValueSetsReq expectedValueSetsReq = getValueSetsConfigReq(path);

        assertEquals(expectedValueSetsReq.name,actualValueSetsReqResponse.name);
        assertEquals(expectedValueSetsReq.valueset.description,actualValueSetsReqResponse.valueset.description);
        assertEquals(expectedValueSetsReq.valueset.version,actualValueSetsReqResponse.valueset.version);
    }

    public String getValueSetsUrl() {
        return getSystemParameter( VALUE_SETS_BASE_PATH ).replace( getSystemParameter( FULFILLMENT_BASE_URL ), "fulfillmentBasePath/" );
    }
}
