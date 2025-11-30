package com.ge.onchron.verif.howsteps;

import com.ge.onchron.verif.model.RequestResponse;
import com.ge.onchron.verif.utils.SfnProcessUtils;
import io.restassured.builder.ResponseBuilder;
import lombok.Setter;
import net.thucydides.core.annotations.Steps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.core.exception.AbortedException;
import software.amazon.awssdk.services.sfn.model.ExecutionListItem;
import software.amazon.awssdk.services.sfn.model.ExecutionStatus;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static com.ge.onchron.verif.TestSystemParameters.REQUEST_RESPONSE;
import static com.ge.onchron.verif.utils.SfnProcessUtils.*;
import static com.ge.onchron.verif.utils.Utils.waitMillis;
import static net.serenitybdd.core.Serenity.setSessionVariable;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class SfnCommonSteps {
    @Setter
    public String globalDate = null;
    @Steps
    private RestApiSteps restApiSteps;
    private static final Logger LOGGER = LoggerFactory.getLogger( SfnCommonSteps.class );
    public Map<String, String> latestParams = null;

    public void waitForSfnFinish(boolean isMandatory, int timeout, ExecutionStatus status, String sfnName) {
        waitForSfnFinish( isMandatory, timeout, status, null, sfnName);
    }

    public void waitForSfnFinish(boolean isMandatory, int timeout, ExecutionStatus status, Map<String, String> params, String sfnName)
    {
        waitForSfnFinish( isMandatory, timeout, DEFAULT_START_TIMEOUT, status, params, 1, sfnName);
    }

    public void waitForSfnFinish(boolean isMandatory, int timeout, ExecutionStatus status, Map<String, String> params, int count, String sfnName) {
        waitForSfnFinish( isMandatory, timeout, DEFAULT_START_TIMEOUT, status, params, count, sfnName);
    }

    public void waitForSfnFinish(boolean isMandatory, int timeout, ExecutionStatus status, Map<String, String> params, boolean explicit, String sfnName) {
        waitForSfnFinish( isMandatory, timeout, DEFAULT_START_TIMEOUT, status, params, 1 ,explicit, sfnName);
    }

    public void waitForSfnFinish(boolean isMandatory, int timeout, int startTimeout, ExecutionStatus status, Map<String, String> params, int count , String sfnName) {
        waitForSfnFinish( isMandatory, timeout, startTimeout, status, params, count, true , sfnName);
    }

    public void waitForSfnFinish(boolean isMandatory, int timeoutMillis, int startTimeout,
                                 ExecutionStatus status, Map<String, String> params,
                                 int count, boolean explicit, String sfnName) {

        String dateString = (globalDate == null)
                ? restApiSteps.getLastResponse().getHeader("date")
                : globalDate;

        LOGGER.info(STR."Start date and time of searching for execution: \{dateString}");

        SfnProcessUtils sfnProcessUtils = SfnProcessUtils.getInstance();
        long requestDateEpochSec = sfnProcessUtils.getRequestDate(dateString) - 10; // subtract 10 seconds
        LOGGER.info(STR."Adjusted requestDate: >>>>>> \{requestDateEpochSec}");

        String sfnArn = sfnProcessUtils.getStateMachineArn(sfnName);

        if (!waitForSfnStart(startTimeout, isMandatory, params, sfnName)) {
            globalDate = null;
            return;
        }

        long startTimeMillis = System.currentTimeMillis();

        while (!Thread.currentThread().isInterrupted()) {
            long elapsedMillis = System.currentTimeMillis() - startTimeMillis;

            if (elapsedMillis > timeoutMillis) {
                fail(STR."Step function did not finish after \{timeoutMillis / 1000} seconds!");
            }

            waitMillis(DEFAULT_WAIT_INTERVAL);

            List<ExecutionListItem> relevantExecsRun = sfnProcessUtils.getRelevantExecutions(
                    sfnArn, requestDateEpochSec, Integer.MAX_VALUE, params
            );

            boolean hasRunning = relevantExecsRun.stream()
                    .anyMatch(e -> e.status().equals(ExecutionStatus.RUNNING));

            if ((isMandatory && relevantExecsRun.size() < count) || hasRunning) {
                continue;
            }

            LOGGER.info( STR."All relevant executions finished after \{elapsedMillis / 1000} seconds" );
            relevantExecsRun.forEach(e -> LOGGER.info(STR."Name: \{e.name()}, Status: \{e.status()}"));
            relevantExecsRun.forEach(e -> assertEquals(status, e.status()));

            if (explicit && !relevantExecsRun.isEmpty()) {
                setSessionVariable(REQUEST_RESPONSE).to(new RequestResponse(
                        null,
                        new ResponseBuilder()
                                .setHeader("date", relevantExecsRun.getLast().stopDate()
                                        .atZone(ZoneId.systemDefault())
                                        .format(DateTimeFormatter.ofPattern(API_DATE_PATTERN)))
                                .setStatusCode(200)
                                .build()
                ));
            }
            break;
        }
        globalDate = null;
    }

    public boolean waitForSfnStart(boolean isMandatory, String sfnName) {
        return waitForSfnStart( DEFAULT_START_TIMEOUT, isMandatory, sfnName);
    }

    public boolean waitForSfnStart(int timeout, boolean isMandatory, String sfnName ) {
        return waitForSfnStart( timeout, isMandatory, null, sfnName );
    }

    public boolean waitForSfnStart(int timeout, boolean isMandatory, Map<String, String> params, String sfnName ) {
        return waitForSfnStart( timeout, isMandatory, params, 1, sfnName );
    }

    public void waitForSfnStart(boolean isMandatory, boolean isStarted, Map<String, String> params, String sfnName) {
        assertEquals( STR."Step function Process \{isStarted ? "not" : ""} started", waitForSfnStart( DEFAULT_START_TIMEOUT, isMandatory, params, 1 , sfnName), isStarted);
    }

    public boolean waitForSfnStart(int timeout, boolean isMandatory, Map<String, String> params, int count, String sfnName) {
        if(params != null)
            latestParams = params;
        if ( globalDate == null ) {
            globalDate = restApiSteps.getLastResponse().getHeader( "date" );
        }
        SfnProcessUtils sfnProcessUtils = SfnProcessUtils.getInstance();
        long requestDate = sfnProcessUtils.getRequestDate( globalDate ) - 10; // subtract 10 ;
        String sfnArn = sfnProcessUtils.getStateMachineArn(sfnName);

        long startTime = System.currentTimeMillis();

        while ( true ) {
            // Check for thread interruption before AWS call
            if (Thread.currentThread().isInterrupted()) {
                LOGGER.warn("Thread interrupted, stopping polling...");
                return false;
            }
            long elapsed = System.currentTimeMillis() - startTime;

            if (elapsed > timeout) {
                if ( isMandatory ) {
                    LOGGER.warn("No executions found within initial timeout. Retrying with wider time window...");
                    // Fallback: widen time window by subtracting 60 seconds
                    requestDate -= 60;
                    List<ExecutionListItem> fallbackExecs = sfnProcessUtils.getRelevantExecutions(sfnArn, requestDate, timeout, params);
                    LOGGER.info(STR."Fallback attempt found \{String.format(String.valueOf(fallbackExecs.size()))} executions");
                    if (fallbackExecs.size() >= count) {
                        LOGGER.info("Fallback succeeded: executions found after widening time window");
                        return true;
                    }
                    fail( STR."Step function did not start after \{timeout / 1000} seconds!" );
                }
                return false;
            }

            try {
                List<ExecutionListItem> relevantExecs = sfnProcessUtils.getRelevantExecutions(sfnArn, requestDate, timeout, params);
                LOGGER.info(STR."Polling executions... Found \{relevantExecs.size()} executions so far");

                if (relevantExecs.size() >= count) {
                    LOGGER.info(STR."Relevant executions started after \{elapsed / 1000} seconds");
                    relevantExecs.forEach(e -> LOGGER.info(STR."Name: \{e.name()}, Status: \{e.status()}"));
                    return true;
                }
            } catch (AbortedException e) {
                LOGGER.error("AWS call aborted due to thread interruption", e);
                return false;
            }

            waitMillis(DEFAULT_WAIT_INTERVAL);
        }
    }

    public void checkSfnJsonPropertyValueInResponse(String sfnName, Map<String, String> params,
                                                    String expectedPropertyValue, boolean contains ) {
        SfnProcessUtils sfnProcessUtils = SfnProcessUtils.getInstance();
        long requestDate = sfnProcessUtils.getRequestDate(restApiSteps.getLastResponse().getHeader("date"));
        String sfnArn = sfnProcessUtils.getStateMachineArn(sfnName);
        List<ExecutionListItem> relevantExecs = sfnProcessUtils.getRelevantExecutions(sfnArn, requestDate, Integer.MAX_VALUE, params);
        String response = getInstance().getLastSfnResponse(relevantExecs);
        LOGGER.info(STR."Observed Response from the step function \{sfnName}: \{response}");
        if (contains) {
            assertTrue("property value is not ok in the response.", response.contains(expectedPropertyValue));
        } else {
            assertThat("property value is not ok in the response.",
                    response, is(equalTo(expectedPropertyValue)));
        }
    }
}
