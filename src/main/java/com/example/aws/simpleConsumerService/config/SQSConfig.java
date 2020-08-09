package com.example.aws.simpleConsumerService.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SQSConfig {

	@Value("${aws.sqs.host}")
	private String hostname;

	@Value("${aws.region}")
	private String region;

	@Bean
	public AmazonSQS amazonSQS(final AWSCredentialsProvider awsCredentialsProvider) {

		return AmazonSQSClientBuilder.standard().withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(hostname, region))
				.withCredentials(awsCredentialsProvider)
				.build();
	}
}
