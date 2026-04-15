package com.ecotravel;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@OpenAPIDefinition(
        servers = @Server(url = "http://localhost:8080", description = "Gateway")
)
@SpringBootApplication
@EnableFeignClients
public class VodiciServiceApplication {
   public static void main(String[] args) {
        SpringApplication.run(VodiciServiceApplication.class, args);
    }
}
