package com.department_service.service.Impl;

import com.department_service.dto.DepartmentReqDto;
import com.department_service.dto.DepartmentResDto;
import com.department_service.entity.Department;
import com.department_service.exceptions.ResourceNotFoundException;
import com.department_service.exceptions.SQLConstraintViolationException;
import com.department_service.repository.DepartmentRepository;
import com.department_service.service.DepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DepartmentResDto createDepartment(DepartmentReqDto departmentDto) throws SQLConstraintViolationException {
        if(departmentRepository.existsByDepartmentCode(departmentDto.getDepartmentCode())){
            throw new SQLConstraintViolationException(departmentDto.getDepartmentCode());
        }
        Department department= new Department();
        department.setDepartmentCode(departmentDto.getDepartmentCode());
        department.setDepartmentName(departmentDto.getDepartmentName());
        department.setDepartmentDescription(departmentDto.getDepartmentDescription());
        Department savedDepartment=departmentRepository.save(department);
        return modelMapper.map(savedDepartment, DepartmentResDto.class);
    }

    @Override
    public DepartmentResDto getDepartmentByCode(String code) {
        Department fetchedDepartment=departmentRepository.findByDepartmentCode(code)
                .orElseThrow(()-> new ResourceNotFoundException("department","department-code",code));
        return modelMapper.map(fetchedDepartment, DepartmentResDto.class);
    }

    @Override
    public List<DepartmentResDto> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(department -> modelMapper.map(department, DepartmentResDto.class)).toList();
    }

    @Override
    public DepartmentResDto updateDepartment(Long id, DepartmentReqDto departmentDto) throws SQLConstraintViolationException {
        Department department=departmentRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("department","id",id.toString()));
        if(Objects.equals(department.getDepartmentCode(), departmentDto.getDepartmentCode())){
            throw new SQLConstraintViolationException(departmentDto.getDepartmentCode());
        }
        if(departmentDto.getDepartmentCode()!=null){
            department.setDepartmentCode(departmentDto.getDepartmentCode());
        }
        if(departmentDto.getDepartmentName()!=null){
            department.setDepartmentName(departmentDto.getDepartmentName());
        }
        if(departmentDto.getDepartmentDescription()!=null){
            department.setDepartmentDescription(departmentDto.getDepartmentDescription());
        }
        Department updatedDepartment=departmentRepository.save(department);
        return modelMapper.map(updatedDepartment, DepartmentResDto.class);
    }

    @Override
    public void deleteDepartmentByCode(String code) {
        Department department=departmentRepository.findByDepartmentCode(code)
                .orElseThrow(()->new ResourceNotFoundException("department","department-code",code));
        departmentRepository.delete(department);

    }
}
