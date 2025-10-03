package com.project.event_ticket_platform;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@OpenAPIDefinition(
		info = @Info(
				title = "EVA: Event Ticket Platform API",
				version = "1.0",
				description = "API documentation of the Event Ticket Platform application"
		)
)
@SpringBootApplication
@EnableJpaAuditing
public class EventTicketPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventTicketPlatformApplication.class, args);
	}

}
