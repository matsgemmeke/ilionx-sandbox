package com.github.matsgemmeke.sandbox;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SandboxApplication {

	public static void main(String[] args) {
		SpringApplication.run(SandboxApplication.class, args);
	}

	@Bean
	public Queue myQueue() {
		return new Queue("sandbox", false);
	}

}
