package com.ge.onchron.verif.utils;

import lombok.Getter;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import static com.ge.onchron.verif.TestSystemParameters.STACK_NAME;
import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;

public class SNSProcessUtils {

    @Getter
    private final SnsClient snsClient;
    private static volatile SNSProcessUtils instance = null;

    public static SNSProcessUtils getInstance() {

        if (instance == null) {
            synchronized (SNSProcessUtils.class) {
                if (instance == null) {
                    instance = new SNSProcessUtils();
                }
            }

        }
        return instance;
    }

    private SNSProcessUtils() {
        this.snsClient = SnsClient
                .builder()
                .credentialsProvider(AwsUtils.getCredentialsProvider("SnsSession"))
                .region(Region.US_EAST_1)
                .build();
    }

    public PublishResponse publishCustomJsonPayload(String regionWithAccountId, String message, String correlationId) {

        PublishRequest request = PublishRequest.builder()
                .topicArn(String.format("arn:aws:sns:%s:%s-fulfill-sns.fifo",regionWithAccountId, getSystemParameter(STACK_NAME)))
                .subject("DataFabricDeleteEvent")
                .messageGroupId(correlationId)
                .messageDeduplicationId(correlationId)
                .message(message)
                .build();

        return snsClient.publish(request);
    }

    public void close() {
        snsClient.close();
    }

}
