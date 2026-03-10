package com.organization_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationReqDto {
    @NotBlank
    private String organizationName;
    @NotBlank
    private String organizationDescription;
    @NotBlank
    @Size(min = 4,message = "code should have at least 4 characters")
    private String organizationCode;
}
