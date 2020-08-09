package com.example.aws.simpleConsumerService.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

	@Value("${aws.accessKey}")
	private String awsAccessKey;

	@Value("${aws.secretKey}")
	private String awsSecretKey;

	@Bean
	public AWSCredentialsProvider awsCredentialsProvider(){
		return new AWSCredentialsProviderChain(
				new AWSStaticCredentialsProvider(
						new BasicAWSCredentials(awsAccessKey, awsSecretKey)
				)
		);
	}
}
