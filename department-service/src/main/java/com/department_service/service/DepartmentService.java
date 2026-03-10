package com.department_service.service;

import com.department_service.dto.DepartmentReqDto;
import com.department_service.dto.DepartmentResDto;
import com.department_service.exceptions.SQLConstraintViolationException;

import java.util.List;

public interface DepartmentService {
    DepartmentResDto createDepartment(DepartmentReqDto departmentDto) throws SQLConstraintViolationException;
    DepartmentResDto getDepartmentByCode(String code);
    List<DepartmentResDto> getAllDepartments();
    DepartmentResDto updateDepartment(Long id, DepartmentReqDto departmentDto) throws SQLConstraintViolationException;
    void deleteDepartmentByCode(String code);
}
