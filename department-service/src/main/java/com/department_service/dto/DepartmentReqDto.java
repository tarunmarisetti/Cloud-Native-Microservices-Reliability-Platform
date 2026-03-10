package com.department_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentReqDto {
    @NotBlank
    private String departmentName;
    @NotBlank
    private String departmentDescription;
    @NotBlank
    @Size(min = 4,message = "code should have at least 4 characters")
    private String departmentCode;
}
