package com.employee_service.exceptions;

public class InvalidCodeException extends RuntimeException {
    public InvalidCodeException(String service) {
        super(String.format("ERROR, there is no '%s' code exists", service));
    }
}
