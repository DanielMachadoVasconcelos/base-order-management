package br.ead.base.order;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DynamoDBConfiguration {

    @Value("${aws.dynamodb.accessKey}")
    private String accessKey;

    @Value("${aws.dynamodb.secretKey}")
    private String secretKey;

    @Value("${aws.dynamodb.region}")
    private String region;

    @Value("${aws.dynamodb.endpoint}")
    private String endpoint;

    @Bean
    public DynamoDbClient getDynamoDbClient() {
        AwsCredentialsProvider credentialsProvider = DefaultCredentialsProvider.builder()
                        .profileName("localstack")
                        .build();

        return DynamoDbClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(credentialsProvider)
                .build();
    }

    @Bean
    public DynamoDbEnhancedClient getDynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }
}