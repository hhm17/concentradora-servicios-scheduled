package com.mx.org.concentradora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@EnableScheduling
public class ConcentradoraServiciosScheduledApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConcentradoraServiciosScheduledApplication.class, args);
	}

}
