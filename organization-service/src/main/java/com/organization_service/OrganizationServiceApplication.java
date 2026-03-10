package com.organization_service;

import com.organization_service.dto.OrganizationContactInfoDto;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(OrganizationContactInfoDto.class)
public class OrganizationServiceApplication {
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(OrganizationServiceApplication.class, args);
	}

}
