package com.employee_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLIntegrityConstraintViolationException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SQLConstraintViolationException extends SQLIntegrityConstraintViolationException {
    public SQLConstraintViolationException(String field) {
        super(String.format("a record with the field '%s' already exists",field));
    }
}
