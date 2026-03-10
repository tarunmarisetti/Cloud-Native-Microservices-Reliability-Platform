package com.employee_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String departmentCode;
    private String organizationCode;
    private Boolean communicationSw;
}
