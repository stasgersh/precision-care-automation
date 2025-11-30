package com.ge.onchron.verif.howsteps;

import com.ge.onchron.verif.model.StringList;
import com.ge.onchron.verif.utils.CloudWatchUtils;
import com.ge.onchron.verif.utils.DateUtil;
import com.ge.onchron.verif.utils.SfnProcessUtils;
import lombok.val;
import net.thucydides.core.annotations.Steps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static com.ge.onchron.verif.TestSystemParameters.*;
import static com.ge.onchron.verif.utils.Utils.insertSystemParameters;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LoggingSteps {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingSteps.class);

    @Steps
    RestApiSteps restApiSteps;


    public void theCloudWatchMessageContains(String message, final String groupName, final Boolean isContains) {
        if(message.contains("$")) {
            message = insertSystemParameters( message );
        }
        val cloudWatchMessageLog = CloudWatchUtils.getCloudWatchLogsBasic(
                restApiSteps.getCorrelationId(),
                SfnProcessUtils.getShortStackName(),
                getCloudWatchLoggingGroup(groupName),
                message);
        assertTheMessages(message, isContains, cloudWatchMessageLog);
    }

    public void theCloudWatchMessageWithoutCorrelationIdContains(final String message, final String groupName, final Boolean isContains) {
        val cloudWatchMessageLog = CloudWatchUtils.getCloudWatchLogsByMsg(
                SfnProcessUtils.getShortStackName(),
                getCloudWatchLoggingGroup(groupName),
                message);
        assertTheMessages(message, isContains, cloudWatchMessageLog);
    }


    public void theCloudWatchMessageContains(final StringList message, final String groupName, final Boolean isContains) {
        String timeStampStr = String.valueOf(DateUtil.getLocalDateTimeOfResponseDate(String.valueOf(restApiSteps.getLastResponse()
                .getHeader("date"))));
        long timeStamp = LocalDateTime.parse(timeStampStr).minusMinutes(1).truncatedTo(ChronoUnit.SECONDS).toInstant(ZoneOffset.UTC).toEpochMilli();
        val cloudWatchMessageLog = CloudWatchUtils.getCloudWatchLogs(
                timeStamp,
                SfnProcessUtils.getShortStackName(),
                getCloudWatchLoggingGroup(groupName));
        assertTheMessages(message, isContains, cloudWatchMessageLog);

    }

    public void theCloudWatchMessageByCorrelationIdContains(final String message, final String groupName, final Boolean isContains) {
        val cloudWatchMessageLog = CloudWatchUtils.getCloudWatchLogsFilterByMessage(
                restApiSteps.getCorrelationId(),
                SfnProcessUtils.getShortStackName(),
                getCloudWatchLoggingGroup(groupName));
        assertTheMessages(message, isContains, cloudWatchMessageLog);

    }
    private static void assertTheMessages(String message, Boolean isContains, List<CloudWatchUtils.LogMessage> cloudWatchMessageLog) {
        LOGGER.info(STR."Expected cloudwatchLogMessage: \{message}");
        LOGGER.info(STR."Actual cloudwatchLogMessage: \{cloudWatchMessageLog
                .stream()
                .map(CloudWatchUtils.LogMessage::getMessage)
                .collect(Collectors.toList())}");

        assertThat(cloudWatchMessageLog
                .stream()
                .map(CloudWatchUtils.LogMessage::getMessage)
                .anyMatch(msg -> msg.contains(message)), is(isContains));
    }

    private static void assertTheMessages(StringList messages, Boolean isContains, List<CloudWatchUtils.LogMessage> cloudWatchMessageLog) {
        LOGGER.info(STR."Expected cloudwatchLogMessage: \{messages}");
        LOGGER.info(STR."Actual cloudwatchLogMessage: \{cloudWatchMessageLog
                .stream()
                .map(CloudWatchUtils.LogMessage::getMessage)
                .collect(Collectors.toList())}");
        for(String message : messages.getList()) {
            assertThat(cloudWatchMessageLog
                    .stream()
                    .map(CloudWatchUtils.LogMessage::getMessage)
                    .anyMatch(msg -> msg.contains(message)), is(isContains));
        }
    }

    public void theCloudWatchTimestampEquals(final String timestamp, final String groupName, final String message, final Boolean isEquals) {
        val cloudWatchMessageLog = CloudWatchUtils.getCloudWatchLogs(restApiSteps.getCorrelationId(), SfnProcessUtils.getShortStackName(), getCloudWatchLoggingGroup(groupName), message);
        String actualTimeStamp = cloudWatchMessageLog.stream().map(m -> m.getTimeStamp()).findFirst().orElse("");
        boolean timestampsMatch = DateUtil.compareTimestampsIgnoringTimeZone(timestamp, actualTimeStamp);
        LOGGER.debug(STR."ActualTimeStamp :\{actualTimeStamp}");
        LOGGER.debug(STR."TimeStampMatchResult :\{timestampsMatch}");

        assertThat("Timestamps were not as expected!", timestampsMatch, is(isEquals));
    }

    public static String getCloudWatchLoggingGroup(String groupName) {
        LOGGER.info(STR."Logs are being fetched from group: \{groupName}");
        return switch (groupName) {
            case "value-set" -> getSystemParameter(FULFILL_VALUE_SET);
            case "query" -> getSystemParameter(FULFILL_QUERY);
            case "etl-workflow" -> getSystemParameter(FULFILL_ETL);
            case "patient-list" -> getSystemParameter(FULFILL_PATIENTS_LIST);
            case "criteria-evaluator" -> getSystemParameter(CRITERIA_EVALUATOR);
            case "criteria-ContentManagement" -> getSystemParameter(CRITERIA_CONTENT_MANAGEMENT);
            case "criteria-Query" -> getSystemParameter(CRITERIA_QUERY);
            case "event-handler" -> getSystemParameter(EVENT_HANDLER);
            case "cte-ai-dataAssessment" -> getSystemParameter(CTE_AI_DATA_ASSESSMENT);
            default -> throw new IllegalArgumentException("This group name not exist");
        };
    }
}
