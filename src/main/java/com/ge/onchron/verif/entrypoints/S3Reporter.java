package com.ge.onchron.verif.entrypoints;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ge.onchron.verif.utils.InfraAwsUtils;
import com.ge.onchron.verif.utils.FileUtils;
import com.ge.onchron.verif.utils.JsonUtils;
import software.amazon.awssdk.services.s3.S3Client;


public class S3Reporter {

    public static void main( String[] args ) throws IOException {
        switch ( args[0] ) {
            case "report":
                reportResults();
                break;
            case "payload":
                extractPayload();
                break;
            default:
                System.out.println( "Unknown s3 action!" );
        }

    }

    private static void extractPayload() throws JsonProcessingException, IOException {
        String payload = new String(Files.readAllBytes(Paths.get("payload.json")));
        Map<String, String> result = new ObjectMapper().readValue( payload, HashMap.class );
        StringBuilder mvnArgs = new StringBuilder();
        for ( String key : result.keySet() ) {
            mvnArgs.append( STR."-D\{key}=\"\{JsonUtils.getFieldValue( payload, key.replace( ".", "\\." ) )                                                       
                                                       .replace( "\\", "\\\\" )
                                                       .replace( "$", "\\$" )
                                                       .replace( "\"", "\\\"" )
                                                       .replace( "\n", " " )}\" " );
        }
        System.out.print( mvnArgs );
    }

    private static void reportResults() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime());
        String reportName = System.getenv( "PAYLOAD__STACK__NAME" )+"__"+timeStamp+".zip";

        String logsPath = Paths.get( System.getProperty( "user.dir" ), "logs", "all_tests.log" ).toString();
        String reportPath = Paths.get( System.getProperty( "user.dir" ), "target", "site" ).toString();
        String targetZipPath = Paths.get( System.getProperty( "user.dir" ), "target", reportName ).toString();
        Files.copy( Paths.get( logsPath ), Paths.get( reportPath, "all_tests.log" ) );
        FileUtils.zipFile( reportPath, targetZipPath );

        S3Client client = S3Client.create();
        System.out.println( "Uploading logs from " + logsPath );
        InfraAwsUtils.putS3Object( client, System.getenv( "QA_E2E_RESULTS_S3" ),
                reportName,
                targetZipPath );
    }


}
