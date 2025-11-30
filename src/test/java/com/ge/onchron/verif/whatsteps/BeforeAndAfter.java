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
package com.ge.onchron.verif.whatsteps;

import com.ge.onchron.verif.howsteps.trials.TrialSteps;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterScenario.Outcome;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.ScenarioType;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementClickInterceptedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.commons.io.FileUtils.deleteDirectory;
import static org.junit.Assert.fail;

import com.ge.onchron.verif.howsteps.CriteriaCalculatorSteps;
import com.ge.onchron.verif.howsteps.PatientDataSteps;
import com.ge.onchron.verif.howsteps.PatientScreenSteps;
import com.ge.onchron.verif.howsteps.RestApiSteps;
import com.ge.onchron.verif.howsteps.SNSCommonSteps;
import com.ge.onchron.verif.howsteps.SfnCommonSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.howsteps.UserSettingsSteps;
import com.ge.onchron.verif.model.RestRequest;
import com.ge.onchron.verif.model.UserCredentials;
import com.ge.onchron.verif.model.criteriaCalculator.CriteriaCalculatorResponse;
import com.ge.onchron.verif.utils.ConfigUtils;
import com.ge.onchron.verif.utils.FileUtils;
import com.ge.onchron.verif.utils.JsonUtils;
import com.ge.onchron.verif.utils.ScreenShotOnFailure;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.steps.StepEventBus;
import software.amazon.awssdk.services.sfn.model.ExecutionStatus;

import static com.ge.onchron.verif.TestSystemParameters.ARCHIVED_DOWNLOAD_DIRECTORY;
import static com.ge.onchron.verif.TestSystemParameters.DEFAULT_DOWNLOAD_DIRECTORY;
import static com.ge.onchron.verif.TestSystemParameters.INVALIDATE_TOKEN;
import static com.ge.onchron.verif.TestSystemParameters.LOG_REST_ASSURED_PROPERTY;
import static com.ge.onchron.verif.TestSystemParameters.ORIGINAL_TOKEN_VALIDATION_MODE;
import static com.ge.onchron.verif.TestSystemParameters.PS_CREATED_PATIENT_BUNDLE;
import static com.ge.onchron.verif.TestSystemParameters.RESET_BROWSER_SIZE_REQUIRED;
import static com.ge.onchron.verif.TestSystemParameters.STORED_USERS;
import static com.ge.onchron.verif.TestSystemParameters.UI_BASE_URL_PROPERTY;
import static com.ge.onchron.verif.TestSystemParameters.UI_PERFORMANCE_TEST;
import static com.ge.onchron.verif.TestSystemParameters.USER_SETTINGS_CHANGED;
import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;
import static com.ge.onchron.verif.utils.FileUtils.copyDir;
import static com.ge.onchron.verif.utils.FileUtils.isEmptyDir;
import static com.ge.onchron.verif.utils.FileUtils.listFilesInFolder;
import static com.ge.onchron.verif.utils.FileUtils.readFromFileByAbsolutePath;
import static com.ge.onchron.verif.utils.FileUtils.saveFile;
import static com.ge.onchron.verif.utils.OrcanosReporterUtils.prepareDescription;
import static com.ge.onchron.verif.utils.ReportUtils.setCustomFieldsForSerenityReport;
import static com.ge.onchron.verif.utils.RestUtils.sendRequest;
import static com.ge.onchron.verif.utils.UserCredentialsUtils.getTestUsers;
import static com.ge.onchron.verif.utils.Utils.generatePerformanceReport;
import static com.ge.onchron.verif.whatsteps.api.CriteriaCalculator.CUSTOM;
import static com.ge.onchron.verif.whatsteps.api.CriteriaCalculator.TRIAL;
import static net.serenitybdd.core.Serenity.environmentVariables;
import static net.serenitybdd.core.Serenity.hasASessionVariableCalled;
import static net.serenitybdd.core.Serenity.sessionVariableCalled;
import static net.serenitybdd.core.Serenity.setSessionVariable;
import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;

public class BeforeAndAfter {

    private static final Logger LOGGER = LoggerFactory.getLogger( BeforeAndAfter.class );

    private static final String CONFIG_JSON = "config.json";
    private static final String SAVED_VERSION_FILE_NAME = "OncoVersion.json";

    private static final String UI_CONFIG = "config/ui";
    private static final String SAVED_UI_CONFIG_FILE_NAME = "OncoUiConfig.json";

    private static final String DEFAULT_TRIAL_BREAST_CONFIGURATIONS_PATH = "src/test/resources/testdata/configuration/repo/default/treatment-eligibility/trials/breast";
    private static final String DEFAULT_TRIAL_PROSTATE_CONFIGURATION_PATH = "src/test/resources/testdata/configuration/repo/default/treatment-eligibility/trials/prostate";

    @Steps
    private UserSettingsSteps userSettingsSteps;
    @Steps
    private PatientScreenSteps patientScreenSteps;
    @Steps
    private RestApiSteps restApiSteps;
    @Steps
    private CriteriaCalculatorSteps criteriaCalculatorSteps;
    @Steps
    private TrialSteps trialSteps;
    @Steps
    private SfnCommonSteps sfnCommonSteps;
    @Steps
    private PatientDataSteps patientDataSteps;
    @Steps
    private SNSCommonSteps snsCommonSteps;


    private TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @BeforeStories
    public void saveOncoConfigurations() {
        String configProperties = saveOncoConfigurationDetails( CONFIG_JSON, SAVED_VERSION_FILE_NAME );
        setCustomFieldsForSerenityReport( configProperties );
        saveOncoConfigurationDetails( UI_CONFIG, SAVED_UI_CONFIG_FILE_NAME );
    }

    private String saveOncoConfigurationDetails( String path, String filename ) {
        RequestSpecification rs = new RequestSpecBuilder()
                .setBaseUri( getSystemParameter( UI_BASE_URL_PROPERTY ) )
                .setBasePath( path )
                .build();
        Response response = sendRequest( new RestRequest( Method.GET, rs ) );
        String userDir = Serenity.environmentVariables().getProperty( "user.dir" );
        Path filePath = Paths.get( userDir + File.separator + "target" + File.separator + filename );
        saveFile( filePath, response.asString() );
        return response.asString();
    }

    @BeforeStories
    public void logRestAssuredEvents() {
        boolean logNeeded = Boolean.parseBoolean( getSystemParameter( LOG_REST_ASSURED_PROPERTY ) );
        if ( logNeeded ) {
            RestAssured.filters( new RequestLoggingFilter(), new ResponseLoggingFilter() );
        }
    }

    @BeforeStories
    public void deleteArchivedDownloadDir() {
        Path archivedDownloadDir = FileUtils.getAbsolutPathOfDir( ARCHIVED_DOWNLOAD_DIRECTORY );
        try {
            deleteDirectory( archivedDownloadDir.toFile() );
        } catch ( IOException e ) {
            fail( "Error while deleting archived download directory: " + e.getMessage() );
        }
    }

    @BeforeStories
    public void setDefaultUserSettingsForDefaultUser() {
        List<UserCredentials> testUsers = getTestUsers();
        for ( UserCredentials testUser : testUsers ) {
            if ( testUser.getAlias().contains( "SERVICE" ) ) {
                continue;
            }
           /* if ( userSettingsSteps.userHasAccessToOncoCare( testUser ) ) {
                userSettingsSteps.setDefaultUserSettings( testUser );
            }*/
        }
    }

    @BeforeStories
    public void setOsTypeEnvVar() {
        String osName = environmentVariables().getProperty( "os.name" ).toLowerCase();
        String osType;
        if ( osName.contains( "windows" ) ) {
            osType = "windows";
        } else if ( osName.contains( "linux" ) ) {
            osType = "linux";
        } else {
            osType = "not known";
        }
        System.setProperty( "os.type", osType );
    }

    @BeforeStory
    public void setSessionVariables() {
        setSessionVariable( RESET_BROWSER_SIZE_REQUIRED ).to( false );
    }

    @BeforeScenario( uponType = ScenarioType.ANY )
    public void deleteDownloadDir() {
        Path downloadDir = FileUtils.getAbsolutPathOfDir( DEFAULT_DOWNLOAD_DIRECTORY );
        try {
            deleteDirectory( downloadDir.toFile() );
        } catch ( IOException e ) {
            LOGGER.warn( "Error while deleting the download directory" );
//            fail( "Error while deleting download directories: " + e.getMessage() );
        }
    }

    @BeforeScenario( uponType = ScenarioType.ANY )
    public void resetBrowserSize() {
        boolean resetBrowserSize = sessionVariableCalled( RESET_BROWSER_SIZE_REQUIRED );
        if ( resetBrowserSize ) {
            int browserWidth = Integer.parseInt( getSystemParameter( "serenity.browser.width" ) );
            int browserHeight = Integer.parseInt( getSystemParameter( "serenity.browser.height" ) );
            Dimension dimension = new Dimension( browserWidth, browserHeight );
            getDriver().manage().window().setSize( dimension );
            setSessionVariable( RESET_BROWSER_SIZE_REQUIRED ).to( false );
        }
    }

    @BeforeScenario( uponType = ScenarioType.ANY )
    public void prepareScenarioDataForOrcanos() throws JsonProcessingException {
        String scenarioName = StepEventBus.getEventBus().getBaseStepListener().getCurrentTestOutcome().getName();
        if ( testDefinitionSteps.getTestName() != null &&
                testDefinitionSteps.getTestName().equals( scenarioName ) ) {
            return;
        }

        Map<String, String> metaData = Serenity.getCurrentSession().getMetaData();
        String description = prepareDescription( scenarioName, metaData.keySet() );

        testDefinitionSteps.finishAll();
        testDefinitionSteps.setTestName( scenarioName );
        testDefinitionSteps.setDescription( description );
        testDefinitionSteps.initialize();
    }

    /**
     * All "AfterScenario" actions in case of passed scenario.
     */
    @AfterScenario( uponType = ScenarioType.ANY, uponOutcome = Outcome.SUCCESS )
    public void actionsAfterPassedScenario() {
        actionsAfterAllScenario();
    }

    /**
     * All "AfterScenario" actions in case of failed scenario.
     * The execution order of the methods with @AfterScenario tag is not guaranteed,
     * but screenshots about the failed tests shall be always the fist action
     */
    @AfterScenario( uponType = ScenarioType.ANY, uponOutcome = Outcome.FAILURE )
    public void actionsAfterFailedScenario() {
        screenshotTaker();  // always the first action after the scenario
        actionsAfterAllScenario();
    }

    @AfterScenario( uponType = ScenarioType.ANY, uponOutcome = Outcome.FAILURE )
    public void waitForEtlfinishedETL() {
        if ( sfnCommonSteps.globalDate == null )
            return;
        sfnCommonSteps.waitForSfnFinish( true, 3000000, ExecutionStatus.SUCCEEDED, sfnCommonSteps.latestParams, "fulfill-etl" );
    }

    @AfterStories()
    @AfterScenario( uponType = ScenarioType.NORMAL )
    public void finishSteps() {
        testDefinitionSteps.finishAll();
    }

    @AfterScenario( uponType = ScenarioType.ANY )
    public void resetTokenValidity() {
        // make token valid again for following tests if it was invalidated
        if ( sessionVariableCalled( INVALIDATE_TOKEN ) != null && (boolean) sessionVariableCalled( INVALIDATE_TOKEN ) ) {
            restApiSteps.resetAccessTokenValidity();
        }
    }

    @AfterScenario( uponType = ScenarioType.EXAMPLE )
    public void finishLastExampleStep() {
        testDefinitionSteps.finishStep( false );
    }

    private void actionsAfterAllScenario() {
        setDefaultUserSettings();
        postUIActions();
        archiveDownloadDir();
    }

    private void postUIActions() {
        try {
            userSettingsSteps.cancelEditSettings();
        } catch ( ElementClickInterceptedException e ) {
            LOGGER.info( "ElementClickInterceptedException in postUIActions(): the browser was closed after the last scenario while executing these steps" );
        }
        if ( patientScreenSteps.patientPageIsCurrentlyVisible() ) {
            patientScreenSteps.navigateToInitialPatientPage();
            patientScreenSteps.verifyPatientPageIsLoaded();
        }
    }

    private void screenshotTaker() {
        ScreenShotOnFailure.captureScreen();
    }

    private void archiveDownloadDir() {
        Path downloadDir = FileUtils.getAbsolutPathOfDir( DEFAULT_DOWNLOAD_DIRECTORY );
        Path archivedDownloadDir = FileUtils.getAbsolutPathOfDir( ARCHIVED_DOWNLOAD_DIRECTORY );
        if ( downloadDir.toFile().exists() && !isEmptyDir( downloadDir ) ) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd_HH.mm.ss" );
            String timestamp = LocalDateTime.now().format( formatter );
            String archiveDirPath = archivedDownloadDir + File.separator + "download-" + timestamp;
            copyDir( downloadDir.toString(), archiveDirPath );
            LOGGER.info( "Download directory is archived into " + archiveDirPath );
        }
    }

    private void setDefaultUserSettings() {
        if ( hasASessionVariableCalled( USER_SETTINGS_CHANGED ) && (boolean) sessionVariableCalled( USER_SETTINGS_CHANGED ) ) {
            Map<String, UserCredentials> storedUsers = sessionVariableCalled( STORED_USERS );
           /* storedUsers.forEach( ( userAlias, userCredentials ) -> {
                if ( !userAlias.contains( "SERVICE" ) && userSettingsSteps.userHasAccessToOncoCare( userCredentials ) ) {
                    userSettingsSteps.setDefaultUserSettings( userCredentials );
                }
            } );*/
            setSessionVariable( USER_SETTINGS_CHANGED ).to( false );
        }
    }

    @AfterStories()
    @AfterScenario( uponType = ScenarioType.ANY, uponOutcome = Outcome.ANY )
    public void configRestore() {
        Map<String, String> metaData = Serenity.getCurrentSession().getMetaData();
        if ( metaData.containsKey( "configChange" ) ) {
            ConfigUtils.restoreChangedConfiguration();
        }
        if ( metaData.containsKey( "trialBreastConfigChange" ) ) {
            restoreTrialConfigurations(DEFAULT_TRIAL_BREAST_CONFIGURATIONS_PATH);
        }
        if ( metaData.containsKey( "trialProstateConfigChange" ) ) {
            restoreTrialConfigurations(DEFAULT_TRIAL_PROSTATE_CONFIGURATION_PATH);
        }
        if ( metaData.containsKey( "token_validation" ) ) {
            String originalMode = Serenity.sessionVariableCalled( ORIGINAL_TOKEN_VALIDATION_MODE );
            if ( originalMode != null ) {
                System.setProperty( "token.validation.mode", originalMode );
                LOGGER.info( "Restored token validation mode to: {}", originalMode );
            }
        }

    }

    @AfterStory
    public void tearDown() {
        Map<String, String> metaData = Serenity.getCurrentSession().getMetaData();
        if ( metaData.containsKey( "cleanup" ) ) {
            String type = criteriaCalculatorSteps.getLastCriteriaConfigResponse().type;
            String configId = restApiSteps.getCreatedResponseId();
            deleteConfigByTypeAndConfigId( type, configId );
        }
        if ( metaData.containsKey( "cleanupAllPatientCustomTypeConfigurations" ) ) {
            criteriaCalculatorSteps.getConfigByType( CUSTOM );
            CriteriaCalculatorResponse lastCriteriaConfigResponse = criteriaCalculatorSteps.getLastClinicalCriteriaConfigResponse();
            lastCriteriaConfigResponse.getResults()
                                      .stream()
                                      .forEach( config -> {
                                          deleteConfigByTypeAndConfigId( CUSTOM, config.getId() );
                                      } );
        }
        if ( metaData.containsKey( "cleanupAllPatientTrialTypeConfigurations" ) ) {
            CriteriaCalculatorResponse lastCriteriaConfigResponse = criteriaCalculatorSteps.getLastClinicalCriteriaConfigResponse();
            lastCriteriaConfigResponse.getResults()
                                      .stream()
                                      .forEach( config -> {
                                          deleteConfigByTypeAndConfigId( TRIAL, config.getId() );
                                      } );
        }
        if ( metaData.containsKey( "deletePatientAfterTest" ) ) {
            String resourceBundle = sessionVariableCalled( PS_CREATED_PATIENT_BUNDLE );
            resourceBundle = resourceBundle.replaceAll( "PUT", "DELETE" );
            patientDataSteps.uploadBundle( resourceBundle );
            String patientId = JsonUtils.getFieldValue( resourceBundle, "entry[0].resource.id" );
            snsCommonSteps.sendSnsAndSetGlobalDateForSfn( "fulfill-etl-workflow", patientId, "deleteDataFabricMessage", 90 );
            patientDataSteps.waitForPostProcess( resourceBundle.length(), patientId );

        } else {
            LOGGER.info( "No cleanup was required! Check if that in purpose" );
        }
    }

    private void deleteConfigByTypeAndConfigId( String type, String configId ) {
        LOGGER.info( STR."Cleanup is required. Performing cleaning calculator configuration after scenario for type of: \{type} and configurationID: \{configId}..." );
        criteriaCalculatorSteps.deleteConfigByTypeAndId( type, configId );
        LOGGER.info( STR."Response status: \{restApiSteps.getLastResponse().statusCode()}" );
        LOGGER.info( STR."Response message: \{restApiSteps.getLastResponse().body().asString()}" );
    }

    private void restoreTrialConfigurations(String folderPath) {
        LOGGER.info( STR."Re-ingesting default configurations for \{folderPath}");
        List<File> files = listFilesInFolder(folderPath);
        files.forEach(file -> {
            trialSteps.updateTrialConfig(file.getName().replace(".json",""),
                    TRIAL,
                    readFromFileByAbsolutePath(file.toString()));
            LOGGER.info( STR."Response status: \{restApiSteps.getLastResponse().statusCode()}" );
        });
    }

    @AfterStories
    public void afterRun() {
        if ( getSystemParameter( UI_PERFORMANCE_TEST ).equals( "Yes" ) ) {
            generatePerformanceReport();
        }
    }
}
