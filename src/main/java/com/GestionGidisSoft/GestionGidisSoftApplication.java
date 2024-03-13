package com.GestionGidisSoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class GestionGidisSoftApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(GestionGidisSoftApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(GestionGidisSoftApplication.class);
	}
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
