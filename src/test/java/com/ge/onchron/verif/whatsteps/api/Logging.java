package com.ge.onchron.verif.whatsteps.api;

import com.ge.onchron.verif.howsteps.LoggingSteps;
import com.ge.onchron.verif.howsteps.RestApiSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.model.StringList;
import com.ge.onchron.verif.utils.DateUtil;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Then;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public class Logging {
    @Steps
    private LoggingSteps loggingSteps;
    @Steps
    private RestApiSteps restApiSteps;
    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Then("the following $feature system log searched by recent correlationID, $isContains contains: $message")
    public void theSystemLogsByCorrelationIdContainsContainsMessage(String feature, Boolean isContains, String message) {
        testDefinitionSteps.addStep("Check the system logs in cloud watch");
        loggingSteps.theCloudWatchMessageByCorrelationIdContains(message, feature, isContains);
        testDefinitionSteps.logEvidence(STR."the system logs \{isContains ? "" : "do not"} contain the \{message} message",
                STR."the message \{isContains ? "" : "not"} exist in system logs", true);
    }

    @Then("the following $feature system log $isContains present: $message")
    public void theSystemLogsContainsMessage(String feature, Boolean isContains, String message) {
        testDefinitionSteps.addStep("Check the system logs in cloud watch");
        loggingSteps.theCloudWatchMessageContains(message, feature, isContains);
        testDefinitionSteps.logEvidence(STR."the system logs \{isContains ? "" : "do not"} contain the \{message} message",
                STR."the message \{isContains ? "" : "not"} exist in system logs", true);
    }
    @Then("the following $feature system log $isContains present with no correlation id: $message")
    public void theSystemLogsContainsMessageNoCorrelationId(String feature, Boolean isContains, String message) {
        testDefinitionSteps.addStep(STR."Check the \{feature} system logs in cloud watch");
        loggingSteps.theCloudWatchMessageWithoutCorrelationIdContains(message, feature, isContains);
        testDefinitionSteps.logEvidence(STR."the system logs \{isContains ? "" : "do not"} contain the \{message} message",
                STR."the message \{isContains ? "" : "not"} exist in system logs", true);
    }

    @Then("the following $feature system log $isContains present message: $messages")
    public void theSystemLogsContainsMessageByTimeStamp(String feature, Boolean isContains, StringList messages) {
        testDefinitionSteps.addStep("Check the system logs in cloud watch");
        loggingSteps.theCloudWatchMessageContains(messages, feature, isContains);
        testDefinitionSteps.logEvidence(STR."the system logs \{isContains ? "" : "do not"} contain the \{messages} message",
                STR."the message \{isContains ? "" : "not"} exist in system logs", true);
    }

    @Then("the following $group system log $message timestamp $isEqual equal to the response timestamp")
    public void theSystemLogsStartTimeStampEquals(String group,String message, Boolean isEquals) {
        testDefinitionSteps.addStep(STR."Compare the following log \{message} timestamp with the request");
        String timeStamp = String.valueOf(DateUtil.getLocalDateTimeOfResponseDate(String.valueOf(restApiSteps.getLastResponse()
                .getHeader("date"))));
        loggingSteps.theCloudWatchTimestampEquals(timeStamp, group, message, isEquals);
        testDefinitionSteps.logEvidence(STR."the system logs start timestamp \{isEquals ? "" : "not"} equal to request",
                STR."the timestamp \{isEquals ? "" : "not"} equal in system logs", true);
    }
}
