package com.ge.onchron.verif.entrypoints;

import software.amazon.awssdk.services.sfn.SfnClient;

import static com.ge.onchron.verif.utils.InfraAwsUtils.sendTaskSuccess;

public class SfnCallback {
    public static void main( String[] args ) {
        String taskToken = System.getenv( "TASK_TOKEN" );
        System.out.println("Sending callback to " + taskToken);
        SfnClient client = SfnClient.create();
        sendTaskSuccess(client, taskToken, "{\"message\": \"Dry run success!\"}");
    }
}
