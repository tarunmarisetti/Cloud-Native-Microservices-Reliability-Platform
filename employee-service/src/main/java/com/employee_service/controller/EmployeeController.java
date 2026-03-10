package com.employee_service.controller;

import com.employee_service.dto.ApiResponseDto;
import com.employee_service.dto.EmployeeContactInfoDto;
import com.employee_service.dto.EmployeeReqDto;
import com.employee_service.dto.EmployeeResDto;
import com.employee_service.exceptions.SQLConstraintViolationException;
import com.employee_service.service.impl.EmployeeServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeServiceImpl employeeService;
    private final EmployeeContactInfoDto employeeContactInfoDto;

    private static final Logger LOGGER= LoggerFactory.getLogger(EmployeeController.class);

    public EmployeeController(EmployeeServiceImpl employeeService, EmployeeContactInfoDto employeeContactInfoDto) {
        this.employeeService = employeeService;
        this.employeeContactInfoDto = employeeContactInfoDto;
    }

    @PostMapping
    public ResponseEntity<EmployeeResDto> createEmployee(@Valid @RequestBody EmployeeReqDto departmentDto) throws SQLConstraintViolationException {
        EmployeeResDto savedDepartment=employeeService.createEmployee(departmentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDepartment);
    }
    @GetMapping("{id}")
    public ResponseEntity<EmployeeResDto> getEmployeeById(@PathVariable("id") Long id){
        EmployeeResDto fetchedRecord=employeeService.getEmployeeById(id);
        return new ResponseEntity<>(fetchedRecord,HttpStatus.OK);
    }
    @GetMapping("{id}/details")
    public ResponseEntity<ApiResponseDto> getEmployeeDetails(
                            @RequestHeader(name = "myapp-correlation-id")String correlationId,
                            @PathVariable("id") Long id){
//        LOGGER.debug("myapp-correlation-id found :{}",correlationId);
        LOGGER.debug("getEmployeeDetails starts");
        ApiResponseDto fetchedRecord=employeeService.getEmployeeDetails(id,correlationId);
        LOGGER.debug("getEmployeeDetails ends");
        return new ResponseEntity<>(fetchedRecord,HttpStatus.OK);
    }

    @GetMapping("/employeeContactInfo")
    public ResponseEntity<EmployeeContactInfoDto> getServiceDetails(){
        return new ResponseEntity<>(employeeContactInfoDto,HttpStatus.OK);
    }
}
