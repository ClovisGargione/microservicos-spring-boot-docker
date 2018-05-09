package com.gk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@EnableHystrixDashboard
@EnableTurbine
@SpringBootApplication
@EnableAutoConfiguration
@EnableDiscoveryClient
public class HystrixMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(HystrixMonitorApplication.class, args);
	}
}
