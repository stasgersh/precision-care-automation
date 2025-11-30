package com.ge.onchron.verif.utils;

import java.io.File;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.sfn.SfnClient;
import software.amazon.awssdk.services.sfn.model.SendTaskSuccessRequest;
import software.amazon.awssdk.services.sfn.model.SfnException;

public class InfraAwsUtils {

    public static void sendTaskSuccess( SfnClient sfnClient, String token, String json ) {
        try {
            SendTaskSuccessRequest successRequest = SendTaskSuccessRequest.builder()
                                                                          .taskToken( token )
                                                                          .output( json )
                                                                          .build();

            sfnClient.sendTaskSuccess( successRequest );

        } catch ( SfnException e ) {
            System.err.println( e.awsErrorDetails().errorMessage() );
            System.exit( 1 );
        }
    }

    public static void putS3Object( S3Client s3, String bucketName, String objectKey, String objectPath ) {
        PutObjectRequest putOb = PutObjectRequest.builder()
                                                 .bucket( bucketName )
                                                 .key( objectKey )
                                                 .build();

        s3.putObject( putOb, RequestBody.fromFile( new File( objectPath ) ) );
        System.out.println( "Successfully placed " + objectKey + " into bucket " + bucketName );
    }

    public static String getS3Object( S3Client s3, String bucketName, String objectKey) {
        GetObjectRequest putOb = GetObjectRequest.builder()
                                                 .bucket( bucketName )
                                                 .key( objectKey )
                                                 .build();

        ResponseBytes<GetObjectResponse> objectBytes = s3.getObjectAsBytes( putOb );
        return objectBytes.asUtf8String();
    }
}
