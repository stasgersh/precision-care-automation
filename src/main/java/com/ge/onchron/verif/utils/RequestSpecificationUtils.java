package com.ge.onchron.verif.utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;

import static com.ge.onchron.verif.TestSystemParameters.CLINICAL_CALCULATOR_SERVICE_BASE_URL;
import static com.ge.onchron.verif.TestSystemParameters.CLINICAL_TRIAL_SERVICE_BASE_URL;

@UtilityClass
public class RequestSpecificationUtils {

    public RequestSpecification getCriteriaCalculatorRequestSpecification(String basePath) {
        return new RequestSpecBuilder()
                .setBaseUri(CLINICAL_CALCULATOR_SERVICE_BASE_URL)
                .setBasePath( basePath )
                .setContentType( ContentType.JSON )
                .build();
    }

    public RequestSpecification getClinicalTrialRequestSpecification(String basePath) {
        return new RequestSpecBuilder()
                .setBaseUri(CLINICAL_TRIAL_SERVICE_BASE_URL)
                .setBasePath( basePath )
                .setContentType( ContentType.JSON )
                .build();
    }

}
