package com.example.aws.simpleConsumerService;

import com.example.aws.simpleConsumerService.consumerService.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimpleConsumerServiceApplication  implements CommandLineRunner {

	@Autowired
	private ConsumerService consumerService;

	public static void main(String[] args) {
		SpringApplication.run(SimpleConsumerServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		consumerService.customerListener();
	}
}
