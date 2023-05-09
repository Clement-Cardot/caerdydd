package com.caerdydd.taf;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TafApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(TafApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
  	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    	return builder.sources(TafApplication.class);
  }

}
