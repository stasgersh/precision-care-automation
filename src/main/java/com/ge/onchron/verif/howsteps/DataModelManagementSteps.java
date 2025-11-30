package com.ge.onchron.verif.howsteps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.RequestResponse;
import com.ge.onchron.verif.model.RestRequest;
import com.ge.onchron.verif.utils.SfnProcessUtils;
import com.ge.onchron.verif.utils.JsonUtils;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import net.thucydides.core.annotations.Steps;
import software.amazon.awssdk.services.sfn.SfnClient;
import software.amazon.awssdk.services.sfn.model.DescribeExecutionRequest;
import software.amazon.awssdk.services.sfn.model.ExecutionStatus;
import software.amazon.awssdk.services.sfn.model.StartExecutionRequest;
import software.amazon.awssdk.services.sfn.model.StartExecutionResponse;

import static com.ge.onchron.verif.TestSystemParameters.DATA_MODEL_BASE_PATH;
import static com.ge.onchron.verif.TestSystemParameters.DATA_MODEL_RESOURCES_PATH;
import static com.ge.onchron.verif.TestSystemParameters.FULFILLMENT_BASE_URL;
import static com.ge.onchron.verif.TestSystemParameters.REQUEST_RESPONSE;
import static com.ge.onchron.verif.TestSystemParameters.TEST_DATA_PATH;
import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;
import static com.ge.onchron.verif.utils.SfnProcessUtils.API_DATE_PATTERN;
import static com.ge.onchron.verif.utils.FileUtils.getAbsolutPathOfDir;
import static com.ge.onchron.verif.utils.FileUtils.readFromFileByAbsolutePath;
import static com.ge.onchron.verif.utils.Utils.waitingTimeExpired;
import static net.serenitybdd.core.Serenity.setSessionVariable;

public class DataModelManagementSteps {
    private final String INVALID_FILTER = "[{" +
            "          \"type\": \"terminology\"," +
            "          \"conditions\": [" +
            "            {" +
            "              \"path\": \"$.code\"," +
            "              \"include\": \"invalid-filter-vs\"" +
            "            }" +
            "          ]" +
            "        }]";
    @Steps
    private RestApiSteps restApiSteps;

    private static final Logger LOGGER = LoggerFactory.getLogger( DataModelManagementSteps.class );
    private static final int TIMEOUT_OF_FINISHING_DELETE_EXECUTION_IN_SEC = 300;

    public void createNewDataModel( String modelName ) {
        RequestSpecification rs = getCreateUpdateRequest( modelName, false );
        restApiSteps.sendBackendAuthenticatedRequest( new RestRequest( Method.POST, rs ) );
    }

    public void updateDataModel( String modelName ) {
        RequestSpecification rs = getCreateUpdateRequest( modelName, true );
        restApiSteps.sendBackendAuthenticatedRequest( new RestRequest( Method.PUT, rs ) );
    }

    private static RequestSpecification getCreateUpdateRequest( String modelName, boolean isUpdate ) {
        Path resourcesAbsolutPath = getAbsolutPathOfDir( getSystemParameter( DATA_MODEL_RESOURCES_PATH ) );
        Path filePath = Paths.get( resourcesAbsolutPath.toString(), STR."\{modelName}.json" );
        String body = readFromFileByAbsolutePath( filePath.toString() );
        String uri = getSystemParameter( DATA_MODEL_BASE_PATH );
        if ( isUpdate ) {
            body = body.replace( "original", "updated" );
            uri += STR."/\{modelName}";
        }

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri( uri )
                .setBody( body )
                .setContentType( "application/json" );

        return requestSpecBuilder.build();
    }

    public void deleteDataModel( String modelName ) {
        RequestSpecification rs = new RequestSpecBuilder()
                .setBaseUri( STR."\{getSystemParameter( DATA_MODEL_BASE_PATH )}/\{modelName}" )
                .setContentType( "application/json" )
                .build();
        restApiSteps.sendBackendAuthenticatedRequest( new RestRequest( Method.DELETE, rs ) );
    }

    public void getDataModelsList( String filter ) {
        RequestSpecBuilder rsb = new RequestSpecBuilder()
                .setBaseUri( getSystemParameter( DATA_MODEL_BASE_PATH ) )
                .setContentType( "application/json" );
        if ( filter != null ) {
            rsb.addQueryParam( "filter", filter );
        }
        restApiSteps.sendBackendAuthenticatedRequest( new RestRequest( Method.GET, rsb.build() ) );
    }

    public void getDataModelByName( String modelName ) {
        RequestSpecification rs = new RequestSpecBuilder()
                .setBaseUri( STR."\{getSystemParameter( DATA_MODEL_BASE_PATH )}/\{modelName}" )
                .setContentType( "application/json" )
                .build();
        restApiSteps.sendBackendAuthenticatedRequest( new RestRequest( Method.GET, rs ) );
    }


    public void createInvalidDataModel( String modelName, String alteration ) {
        RequestSpecBuilder rsb = new RequestSpecBuilder()
                .setBaseUri( getSystemParameter( DATA_MODEL_BASE_PATH ) )
                .setContentType( "application/json" );

        rsb.setBody( getInvalidModel( modelName, alteration ) );

        restApiSteps.sendBackendAuthenticatedRequest( new RestRequest( Method.POST, rsb.build() ) );
    }

    public void updateInvalidDataModel( String modelName, String alteration ) {
        RequestSpecBuilder rsb = new RequestSpecBuilder()
                .setBaseUri( getSystemParameter( DATA_MODEL_BASE_PATH ) )
                .setBasePath( modelName )
                .setContentType( "application/json" );

        rsb.setBody( getInvalidModel( modelName, alteration ) );

        restApiSteps.sendBackendAuthenticatedRequest( new RestRequest( Method.PUT, rsb.build() ) );
    }

    private String getInvalidModel( String modelName, String alteration ) {
        Path resourcesAbsolutPath = getAbsolutPathOfDir( getSystemParameter( DATA_MODEL_RESOURCES_PATH ) );
        Path filePath = Paths.get( resourcesAbsolutPath.toString(), STR."\{modelName}.json" );
        String modelDefinition = readFromFileByAbsolutePath( filePath.toString() );

        if ( alteration.equals( "empty body" ) ) {
            return "";
        } else if ( alteration.equals( "empty json body" ) ) {
            return "{}";
        } else if ( alteration.equals( "invalid json" ) ) {
            return modelDefinition.replaceFirst( "}", "" );
        } else if ( alteration.startsWith( "missing field " ) ) {
            String fieldPath = alteration.replace( "missing field ", "" );
            return JsonUtils.setFieldValue( modelDefinition, fieldPath, null );
        } else if ( alteration.equals( "filters containing non-existent value set" ) ) {
            return JsonUtils.setFieldValue( modelDefinition, "definitions[0].filters", INVALID_FILTER );
        } else if ( alteration.startsWith( "wrong value in" ) ) {
            String fieldPath = alteration.replace( "wrong value in ", "" );
            return JsonUtils.setFieldValue( modelDefinition, fieldPath, "\"WrongValue\"" );
        }
        return modelDefinition;
    }

    public StartExecutionResponse deleteTrigger( String resourceId, String resourceName, String stackName ) {
        Path resourcesAbsolutPath = getAbsolutPathOfDir( getSystemParameter( TEST_DATA_PATH ) );
        Path filePath = Paths.get( resourcesAbsolutPath.toString(), STR."\{resourceName}.json" );
        String resource = readFromFileByAbsolutePath( filePath.toString() );
        resource = resource.replaceAll( "resourceId", resourceId );
        String sfnArn = SfnProcessUtils.getInstance().getAllStateMachines().stream().filter(m -> m.name().startsWith( STR."\{SfnProcessUtils.getShortStackName()}-\{stackName}" ) )
                                       .findFirst().get().stateMachineArn();
        StartExecutionRequest request = StartExecutionRequest.builder().stateMachineArn( sfnArn )
                                                             .input( resource )
                                                             .name( String.format( "DATA_FABRIC_DELETE-e2etest%s", System.currentTimeMillis() ) )
                                                             .build();
        SfnClient sfnClient = SfnProcessUtils.getInstance().getSfnClient();
        StartExecutionResponse response = sfnClient.startExecution( request );
        setSessionVariable( REQUEST_RESPONSE ).to( new RequestResponse( null,
                new ResponseBuilder()
                        .setHeader( "date", response.startDate()
                                                    .atZone( ZoneId.systemDefault() )
                                                    .format( DateTimeFormatter.ofPattern( API_DATE_PATTERN ) ) )
                        .setStatusCode( 200 )
                        .build() ) );
        LOGGER.info( STR."Execution ARN: \{response.executionArn()}" );
        return response;
    }

    public void deleteTriggerWithWait( String resourceId, String resourceName, String stackName ) {
        StartExecutionResponse response = deleteTrigger( resourceId, resourceName, stackName);
        DescribeExecutionRequest der = DescribeExecutionRequest.builder()
                                                               .executionArn( response.executionArn() )
                                                               .build();
        boolean waitingTimeExpired;
        long startTime = System.currentTimeMillis();
        LOGGER.info( "Waiting for delete execution to be finished..." );
        SfnClient sfnClient = SfnProcessUtils.getInstance().getSfnClient();
        ExecutionStatus status;
        do {
            status = sfnClient.describeExecution( der ).status();
            waitingTimeExpired = waitingTimeExpired( startTime, TIMEOUT_OF_FINISHING_DELETE_EXECUTION_IN_SEC * 1000 );
        } while ( !status.equals( ExecutionStatus.SUCCEEDED ) && !waitingTimeExpired );

        if ( waitingTimeExpired ) {
            fail( String.format( "Delete execution not finished within the predefined max waiting time: %s", TIMEOUT_OF_FINISHING_DELETE_EXECUTION_IN_SEC ) );
        }
        LOGGER.info( String.format( "Wait for delete execution took %s seconds.", (System.currentTimeMillis() - startTime) / 1000 ) );
    }

    public String getDataModelStepUrl() {
        return getSystemParameter( DATA_MODEL_BASE_PATH ).replace( getSystemParameter( FULFILLMENT_BASE_URL ), "fulfillmentBasePath/" );
    }
}