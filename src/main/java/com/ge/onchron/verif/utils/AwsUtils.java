package com.ge.onchron.verif.utils;

import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.auth.StsAssumeRoleCredentialsProvider;
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest;

public class AwsUtils {
    private final static String awsAccountId = System.getenv( "AWS_ACCOUNT_ID" );
    public final static String roleArn = STR."arn:aws:iam::\{awsAccountId}:role/gehc-devopsbot";

    public static StsAssumeRoleCredentialsProvider getCredentialsProvider( String sessionName ) {
        if ( awsAccountId == null || awsAccountId.isBlank() ) {
            return null;
        }
        return StsAssumeRoleCredentialsProvider
                .builder()
                .stsClient( StsClient.create() )
                .refreshRequest( AssumeRoleRequest
                        .builder()
                        .roleArn( roleArn )
                        .roleSessionName( sessionName )
                        .build() )
                .build();
    }
}
