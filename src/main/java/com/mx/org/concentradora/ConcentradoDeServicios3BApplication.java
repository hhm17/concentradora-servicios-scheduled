package com.mx.org.concentradora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class ConcentradoDeServicios3BApplication {

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/concentradora");
		SpringApplication.run(ConcentradoDeServicios3BApplication.class, args);
	}

}
