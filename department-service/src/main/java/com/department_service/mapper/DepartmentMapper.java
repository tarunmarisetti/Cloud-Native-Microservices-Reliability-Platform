package com.department_service.mapper;

import com.department_service.dto.DepartmentReqDto;
import com.department_service.entity.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {
    public DepartmentReqDto mapToDto(Department department){
        return new DepartmentReqDto(
                department.getDepartmentName(),
                department.getDepartmentDescription(),
                department.getDepartmentCode()
        );
    }
    public Department mapToEntity(DepartmentReqDto dto) {
        Department department = new Department();
        department.setDepartmentName(dto.getDepartmentName());
        department.setDepartmentDescription(dto.getDepartmentDescription());
        department.setDepartmentCode(dto.getDepartmentCode());
        return department;
    }
}
