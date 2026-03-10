package com.enterprise.message.functions;

import com.enterprise.message.dto.EmployeeMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunctions {
    private static final Logger LOGGER=LoggerFactory.getLogger(MessageFunctions.class);

    @Bean
    public Function<EmployeeMessageDto, EmployeeMessageDto> email(){
        return employeeMessageDto -> {
            LOGGER.info("sending email with the details: {}", employeeMessageDto.toString());
            return employeeMessageDto;
        };
    }

    @Bean
    public Function<EmployeeMessageDto, Long> sms(){
        return employeeMessageDto -> {
            LOGGER.info("sending sms with the details: {}", employeeMessageDto.toString());
            return employeeMessageDto.employeeId();
        };
    }
}
