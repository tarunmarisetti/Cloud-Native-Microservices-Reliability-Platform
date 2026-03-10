package com.organization_service.exceptions;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SQLConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> sqlConstraintViolationExceptionHandler(SQLConstraintViolationException exception, WebRequest webRequest){
        ErrorResponse response=new ErrorResponse(LocalDateTime.now(),
                                                 exception.getMessage(),
                                                 webRequest.getDescription(false),
                                                 "BAD_REQUEST");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exception, WebRequest webRequest){
        ErrorResponse response=new ErrorResponse(LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "RESOURCE_NOT_FOUND");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        Map<String,String> errors=new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldError=((FieldError)error).getField();
            String message=error.getDefaultMessage();
            errors.put(fieldError,message);
        });
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);

    }


}
