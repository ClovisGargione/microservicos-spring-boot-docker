package com.gk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class GkApplication {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	

	public static void main(String[] args) {
		SpringApplication.run(GkApplication.class, args);
	}
}
