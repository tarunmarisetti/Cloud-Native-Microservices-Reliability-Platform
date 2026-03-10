package com.employee_service.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "employee")
@Getter
@Setter
public class EmployeeContactInfoDto {
    private String message;
    private Map<String,String> contactDetails;
    private List<String> numbers;
}
