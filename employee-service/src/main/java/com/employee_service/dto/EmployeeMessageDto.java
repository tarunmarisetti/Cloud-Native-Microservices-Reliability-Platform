package com.employee_service.dto;

/**
 * \
 * @param employeeId
 * @param name
 * @param email
 * @param departmentCode
 * @param organizationCode
 */
public record EmployeeMessageDto(
        Long employeeId, String name, String email, String departmentCode, String organizationCode  ) {
}
