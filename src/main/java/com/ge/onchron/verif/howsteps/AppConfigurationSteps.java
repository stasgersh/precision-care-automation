package com.ge.onchron.verif.howsteps;

import org.apache.http.HttpStatus;

import com.ge.onchron.verif.model.RestRequest;
import com.ge.onchron.verif.utils.JsonUtils;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import net.thucydides.core.annotations.Steps;

import static com.ge.onchron.verif.TestSystemParameters.API_BASE_URL_PROPERTY;
import static com.ge.onchron.verif.TestSystemParameters.CORE_API_BASE_PATH_PROPERTY;
import static com.ge.onchron.verif.TestSystemParameters.TEST_DATA_CLASSPATH;
import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;
import static com.ge.onchron.verif.utils.FileUtils.readFromFile;

public class AppConfigurationSteps {

    @Steps
    RestApiSteps restApiSteps;

    public void uploadConfig( String configFile ) {
        RequestSpecBuilder rsb = new RequestSpecBuilder()
                .setBaseUri( getSystemParameter( API_BASE_URL_PROPERTY ) )
                .setBasePath( STR."\{getSystemParameter( CORE_API_BASE_PATH_PROPERTY )}/configs/app" )
                .setContentType( ContentType.JSON )
                .setBody( readFromFile( TEST_DATA_CLASSPATH + configFile ) );
        
        restApiSteps.sendBackendAuthenticatedRequest( new RestRequest( Method.PUT, rsb.build() ) );
        restApiSteps.checkResponseStatusCode( HttpStatus.SC_NO_CONTENT );
    }

    private String getInvalidConfiguration( String alteration ) {        
        String config = readFromFile( STR."\{TEST_DATA_CLASSPATH}configuration/default_app_config.json" );

        if ( alteration.equals( "empty body" ) ) {
            return "";
        } else if ( alteration.equals( "empty json body" ) ) {
            return "{}";
        } else if ( alteration.equals( "invalid json" ) ) {
            return config.replaceFirst( "}", "" );
        } else if ( alteration.startsWith( "missing field " ) ) {
            String fieldPath = alteration.replace( "missing field ", "" );
            return JsonUtils.setFieldValue( config, fieldPath, null );
        } else if ( alteration.equals( "wrong DICOM url" ) ) {
            return JsonUtils.setFieldValue( config, "dicomViewerUrl", "12345" );
        } else if ( alteration.startsWith( "wrong value in" ) ) {
            String fieldPath = alteration.replace( "wrong value in ", "" );
            return JsonUtils.setFieldValue( config, fieldPath, "\"WrongValue\"" );
        }
        return config;
    }
}
