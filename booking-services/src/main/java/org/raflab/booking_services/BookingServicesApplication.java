package org.raflab.booking_services;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
	title = "Eco-Travel & Adventure Management System - Booking Service",
	version = "1.0",
	description = "API for managing accommodation bookings, guests, cancellation policies, and additional services."
))
public class BookingServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingServicesApplication.class, args);
	}

}
