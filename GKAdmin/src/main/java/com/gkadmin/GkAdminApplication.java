package com.gkadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;


@RefreshScope
@EnableEurekaClient
@EnableCircuitBreaker
@SpringBootApplication
public class GkAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(GkAdminApplication.class, args);
	}
}
