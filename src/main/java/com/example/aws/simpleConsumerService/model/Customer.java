package com.example.aws.simpleConsumerService.model;

import lombok.Data;

@Data
public class Customer {
	private Long id;
	private String name;
	private int age;
	private String email;
}
