package com.example.aws.simpleConsumerService.consumerService;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Slf4j
public class ConsumerService {

	@Value("${sqs.url}")
	private String sqsUrl;

	@Value("${aws.accessKey}")
	private String awsAccessKey;

	@Value("${aws.secretKey}")
	private String awsSecretKey;

	@Value("${aws.region}")
	private String awsRegion;

	private AmazonSQS amazonSQS;

	@PostConstruct
	private void postConstructor() {

		log.info("SQS URL: " + sqsUrl);

		AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(
				new BasicAWSCredentials(awsAccessKey, awsSecretKey)
		);

		this.amazonSQS = AmazonSQSClientBuilder.standard().withCredentials(awsCredentialsProvider).build();
	}

	public void customerListener() {
		final ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(sqsUrl)
				.withMaxNumberOfMessages(1)
				.withWaitTimeSeconds(3);

		while (true) {

			final List<Message> messages = amazonSQS.receiveMessage(receiveMessageRequest).getMessages();

			for (Message messageObject : messages) {
				String message = messageObject.getBody();

				log.info("Received message: " + message);

				deleteMessage(messageObject);
			}
		}
	}

	private void deleteMessage(Message messageObject) {

		final String messageReceiptHandle = messageObject.getReceiptHandle();
		amazonSQS.deleteMessage(new DeleteMessageRequest(sqsUrl, messageReceiptHandle));
	}
}
