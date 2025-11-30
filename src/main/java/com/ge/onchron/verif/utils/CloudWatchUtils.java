package com.ge.onchron.verif.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;
import software.amazon.awssdk.services.cloudwatchlogs.model.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.logging.Level.INFO;


@UtilityClass
@Slf4j
public class CloudWatchUtils {

    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    public static final class LogMessage {
        private final String logLevel;
        private final String message;
        private final String timeStamp;
    }

    public static final String DEFAULT_QUERY_BY_MSG =
            "fields @timestamp, @message, @logStream, @log | filter @message like '%s' | sort @timestamp desc";
    public static final String BASIC_DEFAULT_QUERY_BY_MSG =
            "fields @timestamp, @message, @logStream, @log | filter @message like \"%s\" | sort @timestamp desc";
    public static final String BASE_QUERY =
            "fields @timestamp, @message, @logStream, @log | filter @requestId = '%s' | filter @message like \"%s\" | sort @timestamp desc";
    public static final String BASE_QUERY_FILTER_BY_REQUEST_ID_AND_MESSAGE =
            "fields @timestamp, @message, @logStream, @log | filter @requestId = '%s' | filter @message like %s | sort @timestamp desc";
    public static final String BASE_QUERY_FILTER_BY_MESSAGE =
            "fields @timestamp, @message, @logStream | filter @message like /%s/ | sort @timestamp desc | limit 1";
    public static final String LOG_GROUP_TEMPLATE =
            "/aws/lambda/%s-%s";
    public static final String ETL_LOG_GROUP_TEMPLATE =
            "/aws/vendedlogs/states/%s-%s";
    public static final String BASE_QUERY_FILTER_BY_TIMESTAMP_AND_MESSAGE =
            "fields @timestamp, @message, @logStream, @log | filter @timestamp >= %d | sort @timestamp desc";


    public static List<LogMessage> getCloudWatchLogsBasic(
            final String responseCorrelationId, final String stackName, final String logGroupName, String messge) {

        return fetchLogMessages(
                String.format( LOG_GROUP_TEMPLATE, stackName, logGroupName ),
                String.format( BASE_QUERY, responseCorrelationId, messge ) );
    }
    public static List<LogMessage> getCloudWatchLogsByMsg(
            final String stackName, final String logGroupName, String message ) {

        return fetchLogMessages(
                String.format( LOG_GROUP_TEMPLATE, stackName, logGroupName ),
                String.format(DEFAULT_QUERY_BY_MSG, message ) );
    }
    public static List<LogMessage> getDefaultCloudWatchLogsByMsg(
            final String stackName, final String logGroupName, String message ) {

        return receiveLogMessages(
                String.format( LOG_GROUP_TEMPLATE, stackName, logGroupName ),
                String.format(BASIC_DEFAULT_QUERY_BY_MSG, message ) );
    }

    public static List<LogMessage> getCloudWatchLogs(
            final String responseCorrelationId, final String stackName, final String logGroupName, String message ) {

        return fetchLogMessages(
                String.format( LOG_GROUP_TEMPLATE, stackName, logGroupName ),
                String.format( BASE_QUERY_FILTER_BY_REQUEST_ID_AND_MESSAGE, responseCorrelationId, message ) );
    }

    public static List<LogMessage> getCloudWatchLogs(
            final long timestamp, final String stackName, final String logGroupName ) {

        return receiveLogMessages(
                String.format( ETL_LOG_GROUP_TEMPLATE, stackName, logGroupName ),
                String.format( BASE_QUERY_FILTER_BY_TIMESTAMP_AND_MESSAGE, timestamp ) );
    }

    public static List<LogMessage> getCloudWatchLogsFilterByMessage(
            final String responseCorrelationId, final String stackName, final String logGroupName ) {

        String logGroup = String.format( LOG_GROUP_TEMPLATE, stackName, logGroupName );
        String query = String.format( BASE_QUERY_FILTER_BY_MESSAGE, responseCorrelationId );

        List<List<ResultField>> resultFields = executeQueryWithClient( logGroup, query );

        if ( resultFields.isEmpty() ) {
            log.warn( "No log streams found containing the correlation ID: {}", responseCorrelationId );
            return Collections.emptyList();
        }
        String latestLogStreamName = extractLogStreamNameFromResults( resultFields );
        return fetchLogsFromLogStream( logGroup, latestLogStreamName );
    }

    private static List<LogMessage> fetchLogMessages( String logGroup, String query ) {
        log.info( "Executing CloudWatch logs insights query: {}, for log group: {}", query, logGroup );
        List<List<ResultField>> resultFields = executeQueryWithClient( logGroup, query );
        return parseResults( resultFields );
    }

    private static List<LogMessage> receiveLogMessages( String logGroup, String query ) {
        log.info( "Executing CloudWatch logs insights query: {}, for log group: {}", query, logGroup );
        List<List<ResultField>> resultFields = executeQueryWithClient( logGroup, query );
        List<LogMessage> requestLogMessages = new ArrayList<>();
        for ( List<ResultField> resultFieldList : resultFields ) {
            LogMessage logMessage = new LogMessage( null, resultFieldList.get( 1 ).value(), resultFieldList.getFirst().value() );
            requestLogMessages.add( logMessage );
        }
        return requestLogMessages;
    }

    private static List<List<ResultField>> executeQueryWithClient( String logGroup, String query ) {
        try ( CloudWatchLogsClient logsClient = getCloudWatchClient() ) {
            return ensureCloudWatchQueryExecuted( logsClient, query, logGroup );
        } catch ( Exception e ) {
            log.error( "Error executing query: {}", query, e );
            return Collections.emptyList();
        }
    }

    private static List<LogMessage> fetchLogsFromLogStream( final String logGroup, final String logStreamName ) {
        try ( CloudWatchLogsClient logsClient = getCloudWatchClient() ) {
            GetLogEventsRequest request = GetLogEventsRequest.builder()
                                                             .logGroupName( logGroup )
                                                             .logStreamName( logStreamName )
                                                             .startFromHead( true )
                                                             .build();
            GetLogEventsResponse response = logsClient.getLogEvents( request );

            List<LogMessage> logMessages = new ArrayList<>();
            response.events().forEach( event ->
                    logMessages.add( new LogMessage( INFO.getName(), event.message(), event.timestamp().toString() ) )
            );
            return logMessages;
        } catch ( Exception e ) {
            log.error( "Error fetching log events from log stream: {}", logStreamName, e );
            return Collections.emptyList();
        }
    }

    // Extracts the log stream name from query results
    private static String extractLogStreamNameFromResults( final List<List<ResultField>> resultFields ) {
        for ( List<ResultField> fieldList : resultFields ) {
            for ( ResultField field : fieldList ) {
                if ( "@logStream".equals( field.field() ) ) { // Check if the field is the log stream name
                    return field.value();
                }
            }
        }
        throw new IllegalArgumentException( "Log stream name not found in query results" );
    }

    private static List<List<ResultField>> ensureCloudWatchQueryExecuted(
            final CloudWatchLogsClient logsClient, final String queryString, final String logGroup ) {
        return Failsafe
                .with( new RetryPolicy<List<List<ResultField>>>()
                        .withMaxRetries( -1 )
                        .withDelay( Duration.ofSeconds( 4 ) )
                        .withMaxDuration( Duration.ofSeconds( 240 ) )
                        .onRetriesExceeded(
                                e -> log.info( STR."retries exceeded for \{e.getFailure().getMessage()}" ) )
                        .handleResultIf( result -> 0 == result.size() )
                        .onSuccess( e -> log.info( "successfully found log message" ) ) )
                .get( () -> getLogResults( logsClient, queryString, logGroup ) );
    }

    private static List<List<ResultField>> getLogResults(
            final CloudWatchLogsClient logsClient, final String queryString, final String logGroup ) {

        return logsClient.getQueryResults( GetQueryResultsRequest.builder()
                                                                 .queryId( executeQuery( logsClient, queryString, logGroup ) )
                                                                 .build() ).results();
    }

    private static String executeQuery( final CloudWatchLogsClient logsClient, final String queryString, final String logGroup ) {
        // Execute CloudWatch LogsInsight query to fetch logs from the last hour
        log.info( STR."The CloudWatch query to fetch logs for group:\{logGroup}" );
        StartQueryResponse startQueryResponse =
                logsClient.startQuery( StartQueryRequest.builder()
                                                        .logGroupName( logGroup )  // Use the passed logGroup parameter
                                                        .startTime( System.currentTimeMillis() - 1000 * 60 * 60 )
                                                        .endTime( System.currentTimeMillis() )
                                                        .queryString( queryString )
                                                        .build() );

        String queryId = startQueryResponse.queryId();
        ensureQueryCompleted( logsClient, queryId );
        return queryId;
    }

    private static QueryStatus ensureQueryCompleted(
            final CloudWatchLogsClient logsClient, final String queryId ) {
        return Failsafe
                .with( new RetryPolicy<QueryStatus>()
                        .withMaxRetries( -1 )
                        .withDelay( Duration.ofSeconds( 2 ) )
                        .withMaxDuration( Duration.ofSeconds( 30 ) )
                        .onRetriesExceeded(
                                e -> log.info( STR."retries exceeded for \{e.getFailure().getMessage()}" ) )
                        .handleResultIf( queryStatus -> !QueryStatus.COMPLETE.equals( queryStatus ) )
                        .abortIf( queryStatus -> QueryStatus.COMPLETE.equals( queryStatus ) ) )
                .get( () -> getQueryStatus( logsClient, queryId ) );
    }

    private static QueryStatus getQueryStatus( final CloudWatchLogsClient logsClient, final String queryId ) {
        return logsClient.getQueryResults(
                GetQueryResultsRequest.builder().queryId( queryId ).build() ).status();
    }

    private static List<LogMessage> parseResults( final List<List<ResultField>> resultFields ) {
        List<LogMessage> requestLogMessages = new ArrayList<>();

        for ( List<ResultField> resultFieldList : resultFields ) {
            if ( resultFieldList.size() < 4 ) {
                log.warn( "Unexpected result format: {}", resultFieldList );
                continue;
            }
            String requestDate = resultFieldList.get( 0 ).value();
            String[] splitResults = resultFieldList.get( 1 ).value().split( " \\| " );
            if ( splitResults.length < 4 ) {
                log.warn( "Unexpected message format: {}", resultFieldList.get( 1 ).value() );
                continue;
            }
            LogMessage logMessage = new LogMessage( splitResults[1].trim(), splitResults[3], DateUtil.displayTime( requestDate ) );
            requestLogMessages.add( logMessage );
        }
        return requestLogMessages;
    }

    private static CloudWatchLogsClient getCloudWatchClient() {

        log.info( "Initialize CloudWatchLogsClient" );
        // Create CloudWatch client with automatic credential refresh
        return CloudWatchLogsClient
                .builder()
                .credentialsProvider( AwsUtils.getCredentialsProvider( "CloudWatchLogsSession" ) )
                .region( Region.US_EAST_1 ) // Set to your AWS region
                .build();
    }
}
