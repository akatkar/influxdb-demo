package com.akatkar.samples.influxdb.ms.dummy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableDiscoveryClient
@Configuration
public class MicroserviceDummyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceDummyApplication.class, args);
	}

}
