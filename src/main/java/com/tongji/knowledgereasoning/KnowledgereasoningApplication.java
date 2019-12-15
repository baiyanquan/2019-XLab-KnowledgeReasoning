package com.tongji.knowledgereasoning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class KnowledgereasoningApplication {

	public static void main(String[] args) {
		SpringApplication.run(KnowledgereasoningApplication.class, args);
	}

}
