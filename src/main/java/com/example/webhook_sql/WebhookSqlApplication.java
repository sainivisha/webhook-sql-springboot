package com.example.webhook_sql;

import com.example.webhook_sql.service.WebhookSqlService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WebhookSqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebhookSqlApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(WebhookSqlService webhookSqlService) {
		return args -> {
			webhookSqlService.process();
		};
	}

	@Bean
	public RestTemplate restTemplate() {
		return  new RestTemplate();
	}

}
