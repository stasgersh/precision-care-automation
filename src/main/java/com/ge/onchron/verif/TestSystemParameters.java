/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2021, 2021, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import net.thucydides.core.environment.SystemEnvironmentVariables;
import net.thucydides.core.util.EnvironmentVariables;

import static com.ge.onchron.verif.utils.Utils.insertSystemParameters;


/**
 * TestSystemParameters
 * <p/>
 * Containing parameters which are required for test execution
 */
public class TestSystemParameters {
    /**
     * parameters
     */

    public static final String INCLUDED_FEATURES = "includedFeatures";
    public static final String SAVE_RESULT = "save_result";
    public static final String FAILURE_SCREENSHOT_DIRECTORY = "failureScreenshots";
    public static final String UI_PERFORMANCE_TEST = "ui_performance_test";

    public static final String TEST_DATA_CLASSPATH = "testdata/";
    public static final String DEFAULT_DOWNLOAD_DIRECTORY = "target" + File.separator + "download";
    public static final String ARCHIVED_DOWNLOAD_DIRECTORY = "target" + File.separator + "ArchivedTestRunDownloads";

    public static final String ANY_VALUE_PLACEHOLDER = "<ANY_VALUE>";
    public static final String COMMENT_DATE_TIME_FORMAT = "MMM dd, yyyy | hh:mm a";
    public static final String TIMELINE_AXIS_TOOLTIP_DATE_FORMAT = "MMM dd, yyyy";

    // Serenity environment variables
    public static final String UI_BASE_URL_PROPERTY = "webdriver.base.url";
    public static final String UI_ADMIN_URL_PROPERTY = "adminWebDriver.base.url";
    public static final String API_BASE_URL_PROPERTY = "api.base.url";
    public static final String CORE_API_BASE_PATH_PROPERTY = "core.api.base.path";
    public static final String CONFIG_API_BASE_PATH_PROPERTY = "config.api.base.path";
    public static final String VALUESET_API_BASE_PATH_PROPERTY = "valueset.api.base.path";
    public static final String FHIR_SERVER_URL_PROPERTY = "fhir.server.url";
    public static final String DCC_URL_PROPERTY = "dcc.url";
    public static final String FHIR_CLIENT_ID = "fhir.cline.id";
    public static final String FHIR_CLIENT_SECRET = "fhir.client.secret";
    public static final String FHIR_SCOPE = "fhir.scope";
    public static final String LOG_REST_ASSURED_PROPERTY = "log.rest.assured.events";
    public static final String EAT_SERVICE_URL = "eat.service.url";
    public static final String AUDIT_LOG_MESSAGES_URL_PATH = "eat.service.path";
    public static final String AUDIT_LOG_VERSION = "eat.service.version";
    public static final String EAT_SERVICE_CLIENT_ID = "eat.service.client_id";
    public static final String EAT_SERVICE_TENANT_ID = "eat.service.tenant_id";
    public static final String EAT_SERVICE_PROXY_HOST = "eat.service.proxy.host";
    public static final String EAT_SERVICE_PROXY_PORT = "eat.service.proxy.port";
    public static final String AUDIT_LOG_TIME_OFFSET_IN_MINUTES = "audit.log.time.offset.in.minutes";
    public static final String AUDIT_LOG_WAITING_TIME_IN_MINUTES = "audit.log.waiting.time.in.minutes";
    public static final String EHL_PLATFORM_TYPE = "ehl.platform.type";
    public static final String EHL_BOX_ADDRESS = "ehl.box.address";
    public static final String USER_ORGANIZATION = "user.organization";
    public static final String PRODUCT_NAME = "product.name";

    // Serenity session variable params
    public static final String STORED_USERS = "STORED_USERS";
    public static final String MIXED_ACCESS_TOKEN = "MIXED_ACCESS_TOKEN";
    public static final String INVALIDATE_TOKEN = "INVALIDATE_TOKEN";
    public static final String REQUEST_RESPONSE = "REQUEST_RESPONSE";
    public static final String NUMBER_OF_PATIENTS = "NUMBER_OF_PATIENTS";
    public static final String ACCESS_TOKEN_FROM_COOKIE = "ACCESS_TOKEN_FROM_COOKIE";
    public static final String RESET_BROWSER_SIZE_REQUIRED = "RESET_BROWSER_SIZE_REQUIRED";
    public static final String FOUND_AUDIT_LOGS = "FOUND_AUDIT_LOGS";
    public static final String STORED_ONCO_SCHEMA = "STORED_ONCO_SCHEMA";
    public static final String USER_SETTINGS_CHANGED = "USER_SETTINGS_CHANGED";
    public static final String LABEL_COLORS_FROM_LABEL_PANEL = "LABEL_COLORS_FROM_LABEL_PANEL";
    public static final String DEFAULT_SUMMARY_LAYOUT = "DEFAULT_SUMMARY_LAYOUT";
    public static final String PS_CREATED_PATIENT_BUNDLE = "PS_CREATED_PATIENT_BUNDLE";
    public static final String ORIGINAL_CONFIGS = "ORIGINAL_CONFIGS";
    public static final String WINDOW_HANDLES_NUMBER = "WINDOW_HANDLES_NUMBER";

    // OncoCare URLs
    public static final String PATIENT_PAGE_URL = "/patient";
    public static final String ONCO_DEFAULT_USER_ALIAS = "DEFAULT-TEST-USER";

    // Fulfillment URLs
    public static final String FULFILLMENT_BASE_URL = "fulfillment.base.url";
    public static final String TEST_DATA_PATH = "testData.resource.path";
    public static final String BASE_RESOURCES_PATH = "base.resources.path";
    public static final String DATA_MODEL_BASE_PATH = "dataModel.base.path";
    public static final String DATA_MODEL_RESOURCES_PATH = "dataModel.resources.path";
    public static final String DATA_QUERY_RESOURCES = "dataQuery.resources.path";
    public static final String DATA_QUERY_BASE_PATH = "dataQuery.base.path";
    public static final String DATA_QUERY_PATIENT_PATH = "dataQuery.base.path.patient";
    public static final String DATA_QUERY_PATIENT_LIST_PATH = "dataQuery.base.path.patient.list";
    public static final String VALUE_SETS_RESOURCES = "valueSets.resources.path";
    public static final String VALUE_SETS_BASE_PATH = "valueSets.base.path";
    public static final String SUMMARY_DATA_PATH = "summary.resource.path";

    // CTE-AI URLs
    public static final String CTE_AI_BASE_PATH ="cteai.base.path";
    public static final String CTE_AI_BASE_CONFIG_URL = "cteai.base.config.url";
    public static final String INGEST_BASE_PATH = "ingest.base.path";
    public static final String PATIENT_CTE_AI_PATH = "patient.cteai.path";
    public static final String TREATMENT_CTE_AI_PATH = "treatment.cteai.path";
    public static final String TREATMENTS_CTE_AI_PATH = "treatments.cteai.path";


    // Representation URL
    public static final String REPRESENTATION_BASE_PATH = "representation.api.base.path";
    public static final String REPRESENTATION_QUERYSET_BASE_PATH = "representation.queryset.api.base.path";

    // App Config URL
    public static final String APP_CONFIG_PATH = "app.config.path";

    // Patient Summary URL
    public static final String PATIENT_SUMMARY_BASE_URL = "ps.base.url";
    public static final String PATIENT_SUMMARY_CONFIG_PATH = "ps.config.path";
    public static final String PATIENT_SUMMARY_MONITORING_PATH = "ps.monitoring.path";

    // Treatment eligibility URLs
    public static final String CLINICAL_TRIAL_SERVICE_BASE_URL = getSystemParameter( "clinical.trial.service.base.url" );
    public static final String CONFIG_BASE_PATH = getSystemParameter( "config.base.path" );
    public static final String ELIGIBILITY_BASE_PATH = getSystemParameter( "eligibility.base.path" );
    public static final String DYNAMO_TABLE_KEYS = "dynamoDB.table.keys";

    // Calculator URLs
    public static final String CLINICAL_CALCULATOR_SERVICE_BASE_URL = getSystemParameter( "clinical.calculator.service.base.url" );
    public static final String CONTENT_BASE_URL = getSystemParameter( "content.base.path" );
    public static final String CRITERIA_BASE_URL = getSystemParameter( "criteria.base.path" );

    // Calculator Path
    public static final String CALCULATOR_RESOURCES_PATH = "calculator.resources.path";

    //Clinical Treatment eligibility Paths
    public static final String CLINICAL_TRIAL_RESOURCES_PATH = "clinical.trials.resources.path";

    // Cloud Watch environment params
    public static final String STACK_NAME = "stack.name";
    public static final String FULFILL_VALUE_SET = "fulfillment.valueSet";
    public static final String FULFILL_QUERY = "fulfillment.query";
    public static final String FULFILL_ETL = "fulfillment.etl";
    public static final String FULFILL_PATIENTS_LIST = "fulfillment.listOfPatients";
    public static final String CRITERIA_EVALUATOR = "criteria.evaluator";
    public static final String CRITERIA_CONTENT_MANAGEMENT = "criteria.contentManagement";
    public static final String CRITERIA_QUERY = "criteria.query";
    public static final String EVENT_HANDLER = "event.handler";
    public static final String CTE_AI_DATA_ASSESSMENT = "cteAi.dataAssessment";

    // Fulfill auth credentials
    public static final String FULFILL_CLIENT_ID = "fulfill.client_id";
    public static final String FULFILL_CLIENT_SECRET = "fulfill.client_secret";
    public static final String FULFILL_SCOPE = "fulfill.scope";
    public static final String IDAM_URL = "idam.url";
    public static final String TOKEN_VALIDATION_MODE = "token.validation.mode";
    public static final String IDAM_JWKS_URL = "idam.jwks.url";
    public static final String ORIGINAL_TOKEN_VALIDATION_MODE = "original.token.validation.mode";

    // Adherence URLs
    public static final String ADHERENCE_SERVICE_URL = "adherence.base.url";
    public static final String ADHERENCE_TREATMENT_PATH = "adherence.treatment.path";

    // REST Assured
    public static final String REST_CONNECTION_TIMEOUT = "rest.assured.connect.timeout";
    public static final String REST_SOCKET_TIMEOUT = "rest.assured.socket.timeout";

    /**
     * default values for parameters
     */
    private static final Map<String, String> DEFAULTS = new HashMap();

    static {
        DEFAULTS.put( SAVE_RESULT, "Yes" );
        DEFAULTS.put( INCLUDED_FEATURES, "**/stories/**/*.feature" );
        DEFAULTS.put( UI_PERFORMANCE_TEST, "No" );
    }

    /**
     * Returning the given default or system parameter
     *
     * @return the value of the given parameter: get from environment variable if it is defined, else get from defaults
     */
    public static String getSystemParameter( String parameterName ) {
        EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
        String envValue = variables.getValue( parameterName );
        String parameterValue = (envValue != null) ? envValue : variables.getProperty( parameterName );
        if ( parameterValue == null || "".equals( parameterValue.trim() ) ) {
            parameterValue = DEFAULTS.get( parameterName );
        }
        parameterValue = insertSystemParameters( parameterValue );
        return parameterValue;
    }

}
