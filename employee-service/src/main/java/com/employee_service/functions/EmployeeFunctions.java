package com.employee_service.functions;

import com.employee_service.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class EmployeeFunctions {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeFunctions.class);
    @Bean
    public Consumer<Long> updateCommunication(EmployeeService employeeService){
        return employeeNumber->{
            LOGGER.info("updating communication status for the employee id: {}",employeeNumber.toString());
            employeeService.updateCommunicationStatus(employeeNumber);
        };

    }

}
