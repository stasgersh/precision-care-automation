package com.ge.onchron.verif.howsteps;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Map;


public class TestDefinitionSteps {
    private static final String extension = ".log";
    private final ArrayList<Step> steps = new ArrayList<>();
    private Step currentStep = null;
    private int index = 0;
    //todo split into different files
    private final Logger logger = LoggerFactory.getLogger(TestDefinitionSteps.class);
    @Setter
    @Getter
    private String testName;
    @Setter
    private String description;
    @Setter
    private Map<String, String> descriptorValues;

    private static TestDefinitionSteps instance = null;

    public static TestDefinitionSteps getInstance() {
        if (instance == null) {
            instance = new TestDefinitionSteps();
        }
        return instance;
    }

    private TestDefinitionSteps() {

    }

    public void initialize(String testName, String descriptor, Map<String, String> descriptorValues) {
        if (descriptorValues != null && !descriptorValues.isEmpty()) {
            for (String key : descriptorValues.keySet()) {
                descriptor = descriptor.replace(STR."@\{key}@", descriptorValues.get(key));
            }
        }
        logger.info("-----------------------------------Test Start-----------------------------------");
        logger.info(testName);
        logger.info(descriptor);
    }

    public void initialize() {
        initialize(testName, description, descriptorValues);
    }

    public void addStep(String name) {
        addStep( name, true );
    }

    public void addStep(String name, boolean encodeHtml) {
        if (testName == null) return;
        steps.add(new Step(STR."Step \{steps.size() + 1}: \{encodeHtml ? encodeHtmlChars( name ) : name }", logger));
        if (currentStep == null) {
            startStep();
        }
    }

    private void startStep() {
        currentStep = steps.size() > index ? steps.get(index) : null;
        if (currentStep != null) {
            currentStep.start();
        }
    }

    public void finishStep(boolean status) {
        if (currentStep != null) {
            currentStep.finish(status);
            index++;
        }
        startStep();
    }

    public void finishAll() {
        if (currentStep != null) {
            currentStep.finish(false);
            index++;
        }
        for (int i = index; i < steps.size(); i++) {
            Step step = steps.get(i);
            step.finish(false);
        }
        steps.clear();
        currentStep = null;
        testName = null;
        description = null;
        index = 0;
    }

    public void logEvidence(String expectationStr, Object actual, boolean assertResult) {
        logEvidence(expectationStr, actual, assertResult, true);
    }

    public String logEvidence(String expectationStr, Object actual, boolean assertResult, boolean finishStep) {
        return logEvidence( expectationStr, actual, assertResult, finishStep, true);
    }

    public String logEvidence(String expectationStr, Object actual, boolean assertResult, boolean finishStep, boolean encodeHtml) {
        if (currentStep == null || testName == null) return null;
        String msg = STR."Validating that \{encodeHtml ? encodeHtmlChars( expectationStr ) : expectationStr}. " +
                STR."Actual value is '\{encodeHtml ? encodeHtmlChars( actual.toString() ) : actual}'. \{assertResult ? "PASS" : "FAIL"}!";
        logger.info(msg.replace( "\r", "\\r" ).replace("\n", "\\n"));
        if (assertResult && finishStep) {
            // if assertResult is false, then the assert statement that follows this method call will log exception, and it will finish this step
            finishStep(true);
        }
        return msg;
    }
    
    public static String encodeHtmlChars( String text){
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\r", "\\r")
                   .replace("\n", "\\n");
    }
}

class Step {
    private final String name;
    private final Logger logger;

    public Step(String name, Logger logger) {
        this.name = name;
        this.logger = logger;
    }

    public void start() {
        logger.info(STR."\{name} started!");
    }

    public void finish() {
        finish(true);
    }

    public void finish(boolean status) {
        String logStatus = status ? "PASS" : "FAIL";
        logger.info(STR."\{name} finished with status \{logStatus}!");
        logStatus(logStatus);
    }

    public void skip() {
        logger.info(STR."\{name} finished with status SKIP!");
        logStatus("SKIP");
    }

    @SneakyThrows
    private void logStatus(String logStatus) {
        Map<String, String> data = Map.of("name", name, "status", logStatus);
        logger.info( STR."# End of step #\{new ObjectMapper().writeValueAsString( data )}" );
    }
}