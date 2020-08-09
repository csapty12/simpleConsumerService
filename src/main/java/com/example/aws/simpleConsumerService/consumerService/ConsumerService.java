package com.example.aws.simpleConsumerService.consumerService;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.example.aws.simpleConsumerService.model.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Slf4j
public class ConsumerService {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private AmazonSQS amazonSQS;


	public void customerListener() {
		String queueUrl = amazonSQS.getQueueUrl("my-first-queue").getQueueUrl();
		System.out.println(queueUrl);


		final ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl)
				.withMaxNumberOfMessages(1)
				.withWaitTimeSeconds(3);
//
		while (true) {

			final List<Message> messages = amazonSQS.receiveMessage(receiveMessageRequest).getMessages();
			Customer receivedCustomer = null;
			for (Message messageObject : messages) {
				try {
					receivedCustomer = objectMapper.readValue(messageObject.getBody(), Customer.class);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}

				log.info("Received message: " + receivedCustomer);

				deleteMessage(queueUrl, messageObject);
			}
		}
	}

	private void deleteMessage(String queueUrl, Message messageObject) {

		final String messageReceiptHandle = messageObject.getReceiptHandle();
		amazonSQS.deleteMessage(new DeleteMessageRequest(queueUrl, messageReceiptHandle));
	}
}
