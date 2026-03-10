package com.department_service.controller;

import com.department_service.dto.DepartmentReqDto;
import com.department_service.dto.DepartmentContactInfoDto;
import com.department_service.dto.DepartmentResDto;
import com.department_service.exceptions.SQLConstraintViolationException;
import com.department_service.repository.DepartmentRepository;
import com.department_service.service.Impl.DepartmentServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentServiceImpl departmentService;

    private final DepartmentRepository departmentRepository;

    @Value("${spring.application.name}")
    private String appName;

    private final DepartmentContactInfoDto departmentContactInfo;
    private static final Logger LOGGER= LoggerFactory.getLogger(DepartmentController.class);

    public DepartmentController(DepartmentServiceImpl departmentService, DepartmentRepository departmentRepository, DepartmentContactInfoDto departmentContactInfo) {
        this.departmentService = departmentService;
        this.departmentRepository = departmentRepository;
        this.departmentContactInfo = departmentContactInfo;
    }

    @PostMapping
    public ResponseEntity<DepartmentResDto> createDepartment(@Valid @RequestBody DepartmentReqDto departmentDto) throws SQLConstraintViolationException {
        DepartmentResDto savedDepartment=departmentService.createDepartment(departmentDto);
        return new ResponseEntity<>(savedDepartment,HttpStatus.CREATED);
    }
    @GetMapping("{departmentCode}")
    public ResponseEntity<DepartmentResDto> getDepartmentByCode(@RequestHeader(name = "myapp-correlation-id")String correlationId,
                                                                @PathVariable("departmentCode") String code){
//        LOGGER.debug("myapp-correlation-id found :{}",correlationId);
        LOGGER.debug("getDepartmentDetails Method starts");
        DepartmentResDto fetchedDepartment=departmentService.getDepartmentByCode(code);
        LOGGER.debug("getDepartmentDetails Method ends");
        return new ResponseEntity<>(fetchedDepartment,HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<DepartmentResDto>> getAllDepartment(){
        List<DepartmentResDto> departmentDtos=departmentService.getAllDepartments();
        return new ResponseEntity<>(departmentDtos,HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<DepartmentResDto> updateDepartment(@PathVariable("id") Long id, @RequestBody DepartmentReqDto departmentDto) throws SQLConstraintViolationException {
        DepartmentResDto updateDepartment=departmentService.updateDepartment(id,departmentDto);
        return new ResponseEntity<>(updateDepartment,HttpStatus.OK);
    }

    @DeleteMapping("{departmentCode}")
    public ResponseEntity<String> deleteDepartmentByCode(@PathVariable("departmentCode") String code){
        departmentService.deleteDepartmentByCode(code);
        return new ResponseEntity<>("record deleted",HttpStatus.OK);
    }
    @GetMapping("/msg")
    public ResponseEntity<String> getApplicationName(){
        return new ResponseEntity<>(appName,HttpStatus.OK);
    }

    @GetMapping("/departmentContactInfo")
    public ResponseEntity<DepartmentContactInfoDto> getServiceDetails(){
        LOGGER.debug("invoked get Department Contact API");
        return new ResponseEntity<>(departmentContactInfo,HttpStatus.OK);
    }
    @GetMapping("/validate/{code}")
    public ResponseEntity<Boolean> validateDepartmentCode(@PathVariable String code) {
        boolean exists = departmentRepository.existsByDepartmentCode(code);
        return ResponseEntity.ok(exists);
    }



}
