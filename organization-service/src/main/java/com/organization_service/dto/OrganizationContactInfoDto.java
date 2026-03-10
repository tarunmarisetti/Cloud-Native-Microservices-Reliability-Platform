package com.organization_service.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "organization")
@Getter
@Setter
public class OrganizationContactInfoDto {
    private String message;
    private Map<String,String> contactDetails;
    private List<String> numbers;
}
