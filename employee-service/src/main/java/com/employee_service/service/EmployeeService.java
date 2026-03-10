package com.employee_service.service;

import com.employee_service.dto.ApiResponseDto;
import com.employee_service.dto.EmployeeReqDto;
import com.employee_service.dto.EmployeeResDto;
import com.employee_service.exceptions.SQLConstraintViolationException;

public interface EmployeeService {
    EmployeeResDto createEmployee(EmployeeReqDto employeeDto) throws SQLConstraintViolationException;
    EmployeeResDto getEmployeeById(Long id);
    ApiResponseDto getEmployeeDetails(Long id, String correlationId);
    boolean updateCommunicationStatus(Long employeeId);
}
