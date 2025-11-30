package com.ge.onchron.verif.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.core.exception.AbortedException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sfn.SfnClient;
import software.amazon.awssdk.services.sfn.model.DescribeExecutionRequest;
import software.amazon.awssdk.services.sfn.model.DescribeExecutionResponse;
import software.amazon.awssdk.services.sfn.model.ExecutionListItem;
import software.amazon.awssdk.services.sfn.model.ListExecutionsRequest;
import software.amazon.awssdk.services.sfn.model.ListExecutionsResponse;
import software.amazon.awssdk.services.sfn.model.ListStateMachinesRequest;
import software.amazon.awssdk.services.sfn.model.ListStateMachinesResponse;
import software.amazon.awssdk.services.sfn.model.StateMachineListItem;

import static com.ge.onchron.verif.TestSystemParameters.STACK_NAME;
import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;
import static org.junit.Assert.fail;

public class SfnProcessUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger( SfnProcessUtils.class );

    public static final int DEFAULT_WAIT_INTERVAL = 3000;
    public static final int DEFAULT_START_TIMEOUT = 90000;
    public static final String API_DATE_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";

    @Getter
    private final SfnClient sfnClient;
    private static SfnProcessUtils instance = null;

    public static SfnProcessUtils getInstance() {
        if ( instance == null ) {
            instance = new SfnProcessUtils();
        }
        return instance;
    }

    public SfnProcessUtils() {
        this.sfnClient = SfnClient.builder()
                .credentialsProvider(AwsUtils.getCredentialsProvider("SfnSession"))
                .region(Region.US_EAST_1)
                .overrideConfiguration(config -> config
                        .apiCallTimeout(Duration.ofSeconds(30))
                        .apiCallAttemptTimeout(Duration.ofSeconds(20)))
                .build();
    }

    public long getRequestDate( String dateStr ) {
        SimpleDateFormat dateFormat = new SimpleDateFormat( API_DATE_PATTERN );
        try {
            Date date = dateFormat.parse( dateStr );
            return date.getTime() / 1000;
        } catch ( ParseException e ) {
            throw new RuntimeException( "Was not able to parse last request's data: " + e );
        }
    }

    public String getStateMachineArn(String sfnName) {
        return getAllStateMachines().stream()
                                    .filter( m -> m.name().startsWith( STR."\{getShortStackName()}-\{sfnName}" ) )
                                    .findFirst()
                                    .orElseThrow( () -> new RuntimeException( "State machine not found" ) )
                                    .stateMachineArn();
    }

    public List<StateMachineListItem> getAllStateMachines() {
        List<StateMachineListItem> stateMachines = new ArrayList<>();
        String nextToken = null;
        do {
            ListStateMachinesRequest stateMachinesRequest = ListStateMachinesRequest.builder()
                                                                                    .nextToken( nextToken )
                                                                                    .build();
            ListStateMachinesResponse listSfnResponse = getSfnClient().listStateMachines( stateMachinesRequest );
            stateMachines.addAll( listSfnResponse.stateMachines() );
            nextToken = listSfnResponse.nextToken();
        } while ( nextToken != null );
        return stateMachines;
    }

    public List<ExecutionListItem> getRelevantExecutions( String sfnArn, long requestDate, long startTimeout ) {
        return getRelevantExecutions( sfnArn, requestDate, startTimeout, null );
    }

    public List<ExecutionListItem> getRelevantExecutions(String sfnArn, long requestDate, long startTimeout, Map<String, String> params) {
        // already in seconds
        long timeoutSec = startTimeout / 1000;
        long windowEndEpochSec = requestDate + timeoutSec;

        ListExecutionsRequest listExecutionsReq = ListExecutionsRequest.builder()
                .stateMachineArn(sfnArn)
                .build();
        try {
            ListExecutionsResponse listExecutions = getSfnClient().listExecutions(listExecutionsReq);
            return filterExecutions(listExecutions, requestDate, windowEndEpochSec, params);
        } catch (AbortedException e) {
            LOGGER.warn("AWS request was interrupted, retrying after short delay", e);
            return handleAbortedExecution(listExecutionsReq, requestDate, windowEndEpochSec, params);
        }
    }

    private List<ExecutionListItem> handleAbortedExecution(ListExecutionsRequest request,
                                                           long requestDate, long windowEndEpochSec, Map<String, String> params) {
        try {
            // Use exponential backoff
            for (int attempt = 1; attempt <= 3; attempt++) {
                try {
                    Thread.sleep(attempt * 1000L); // Increase delay with each attempt
                    ListExecutionsResponse response = getSfnClient().listExecutions(request);
                    return filterExecutions(response, requestDate, windowEndEpochSec, params);
                } catch (AbortedException ae) {
                    if (attempt == 3) throw ae;
                    LOGGER.warn(STR."Retry \{attempt} failed, trying again");
                }
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt(); // Restore interrupt status
            LOGGER.error(STR."Thread interrupted during retry delay \{ie}");
        }
        return List.of();
    }

    private List<ExecutionListItem> filterExecutions(ListExecutionsResponse response,
                                                     long requestDate, long windowEndEpochSec, Map<String, String> params) {
        return response.executions().stream()
                .filter(e -> {
                    long startEpoch = e.startDate().getEpochSecond();
                    boolean inWindow = startEpoch >= requestDate && startEpoch < windowEndEpochSec;

                    if (!inWindow) return false;
                    if (params == null) return true;

                    String input = getExecInput(e);
                    return params.entrySet().stream().allMatch(kv -> {
                        Object actual = JsonUtils.getFieldValue(input, kv.getKey());
                        LOGGER.info(STR."Checking param: key = \{kv.getKey()}, value = \{kv.getValue()}, actual = \{actual}");
                        return Objects.equals(actual, kv.getValue());
                    });
                })
                .toList();
    }

    public String getExecInput( ExecutionListItem execution ) {
        DescribeExecutionResponse details = getSfnClient().describeExecution(
                DescribeExecutionRequest.builder()
                                        .executionArn( execution.executionArn() )
                                        .build()
        );
        return details.input();
    }

    public static String getShortStackName() {
        String fullName = getSystemParameter( STACK_NAME );
        return fullName.chars().filter(ch -> ch == '-').count() >= 3 ? fullName.substring(0, fullName.lastIndexOf("-")) : fullName;
    }
    public String getLastSfnResponse(List<ExecutionListItem> relevantExec)
    {
        SfnClient client = getSfnClient();
        String exec =  relevantExec.getFirst().executionArn();
        if(Objects.equals(exec, "")) {
            fail("no execution matched filter");
        }
        DescribeExecutionRequest request = DescribeExecutionRequest.builder().executionArn(exec).build();
        DescribeExecutionResponse response = client.describeExecution(request);
        return response.output();
    }
}
