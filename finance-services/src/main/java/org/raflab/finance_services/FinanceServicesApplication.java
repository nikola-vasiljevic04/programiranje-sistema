package org.raflab.finance_services;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(info = @Info(
		title = "Eco-Travel & Adventure Management System - Finance Service",
		version = "1.0",
		description = "API for managing invoices, transactions, and additional services."
), servers = @Server(url = "http://localhost:8080", description = "Gateway"))
public class FinanceServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceServicesApplication.class, args);
	}

}
