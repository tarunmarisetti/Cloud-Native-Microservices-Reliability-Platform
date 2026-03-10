package com.employee_service.service.impl;

import com.employee_service.dto.*;
import com.employee_service.entity.Employee;
import com.employee_service.exceptions.InvalidCodeException;
import com.employee_service.exceptions.ResourceNotFoundException;
import com.employee_service.exceptions.SQLConstraintViolationException;
import com.employee_service.repository.EmployeeRepository;
import com.employee_service.service.client.OrganizationFeignClient;
import com.employee_service.service.client.DepartmentFeignClient;
import com.employee_service.service.EmployeeService;

import io.github.resilience4j.retry.annotation.Retry;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER= LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
//    private final RestTemplate restTemplate;
    private final WebClient webClient;
    private final DepartmentFeignClient departmentFeignClient;
    private final OrganizationFeignClient organizationFeignClient;
    private final StreamBridge streamBridge;

    @Value("${department.service.url}")
    private String departmentServiceUrl;

    @Value("${organization.service.url}")
    private String organizationServiceUrl;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper, WebClient webClient, DepartmentFeignClient departmentFeignClient, OrganizationFeignClient organizationFeignClient, StreamBridge streamBridge) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.webClient = webClient;
        this.departmentFeignClient = departmentFeignClient;
        this.organizationFeignClient = organizationFeignClient;
        this.streamBridge = streamBridge;
    }

    @Override
    public EmployeeResDto createEmployee(EmployeeReqDto employeeDto) throws SQLConstraintViolationException {
        if(employeeRepository.existsByEmail(employeeDto.getEmail())){
            throw new SQLConstraintViolationException("email");
        }
        if (Boolean.FALSE.equals(departmentFeignClient.validateDepartmentCode(employeeDto.getDepartmentCode()))) {
            throw new InvalidCodeException("Department");
        }
        if (Boolean.FALSE.equals(organizationFeignClient.validateOrganizationCode(employeeDto.getOrganizationCode()))) {
            throw new InvalidCodeException("Organization");
        }
        Employee employee=modelMapper.map(employeeDto,Employee.class);
        Employee savedEmployee=employeeRepository.save(employee);

        EmployeeResDto employeeResDto= modelMapper.map(savedEmployee, EmployeeResDto.class);
        sendCommunication(employeeResDto);
        return employeeResDto;
    }

    private void sendCommunication(EmployeeResDto employee) {
        String fullName= employee.getFirstName()+" "+employee.getLastName();
        var employeeDto = new EmployeeMessageDto(employee.getId(),fullName,employee.getEmail(),
                employee.getDepartmentCode(),employee.getOrganizationCode());
        LOGGER.info("Sending Communication request for the details: {}", employeeDto);
        var result = streamBridge.send("sendCommunication-out-0", employeeDto);
        LOGGER.info("Is the Communication request successfully triggered ? : {}", result);
    }

    @Override
    public EmployeeResDto getEmployeeById(Long id) {
        LOGGER.info("inside getEmployeeById method");
        Employee employee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("employee","id",id.toString()));
        return modelMapper.map(employee, EmployeeResDto.class);
    }

    @Retry(name = "getEmployeeDetails",fallbackMethod = "getDefaultEmployeeDetails")
    @Override
    public ApiResponseDto getEmployeeDetails(Long id, String correlationId) {
        LOGGER.info("inside getEmployeeDetails method");
        Employee employee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("employee","id",id.toString()));
//        ResponseEntity<DepartmentResDto> responseEntity=restTemplate.getForEntity("http://localhost:8080/departments/"+employee.getDepartmentCode(), DepartmentResDto.class);
//        DepartmentResDto departmentDto=responseEntity.getBody();
//        DepartmentResDto departmentDto=webClient.get()
//                .uri(departmentServiceUrl + "/departments/" + employee.getDepartmentCode())
//                .retrieve()
//                .bodyToMono(DepartmentResDto.class)
//                .block();
        DepartmentResDto departmentDto=departmentFeignClient.getDepartmentByCode(correlationId,employee.getDepartmentCode());
        OrganizationResDto organizationDto=organizationFeignClient.getOrganizationByCode(correlationId,employee.getOrganizationCode());
//        OrganizationDto organizationDto=webClient.get()
//                .uri(organizationServiceUrl + "/organizations/" + employee.getOrganizationCode())
//                .retrieve()
//                .bodyToMono(OrganizationDto.class)
//                .block();

        employee.setDepartmentCode(departmentDto.getDepartmentCode());
        employee.setOrganizationCode(organizationDto.getOrganizationCode());
        return new ApiResponseDto(modelMapper.map(employee, EmployeeResDto.class),departmentDto,organizationDto);
    }

    @Override
    public boolean updateCommunicationStatus(Long employeeId) {
        boolean isUpdated = false;
        if(employeeId !=null ){
            Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                    () -> new ResourceNotFoundException("Employee", "EmployeeId", employeeId.toString())
            );
            employee.setCommunicationSw(true);
            employeeRepository.save(employee);
            isUpdated = true;
        }
        return  isUpdated;
    }

    public ApiResponseDto getDefaultEmployeeDetails(Long id, String correlationId, Throwable throwable) {
        LOGGER.info("inside getDefaultEmployeeDetails method");
        Employee employee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("employee","id", id.toString()));
        DepartmentResDto departmentDto=new DepartmentResDto();
        departmentDto.setDepartmentName("default Department");
        departmentDto.setDepartmentCode("defaultCode");
        departmentDto.setDepartmentDescription("defaultDescription");

        OrganizationResDto organizationDto=new OrganizationResDto();
        organizationDto.setOrganizationName("defaultOrganization");
        organizationDto.setOrganizationDescription("defaultDescription");
        organizationDto.setOrganizationCode("defaultCode");
        organizationDto.setCreatedDate(LocalDateTime.now());

        employee.setDepartmentCode(departmentDto.getDepartmentCode());
        return new ApiResponseDto(modelMapper.map(employee, EmployeeResDto.class),departmentDto,organizationDto);

    }
}
