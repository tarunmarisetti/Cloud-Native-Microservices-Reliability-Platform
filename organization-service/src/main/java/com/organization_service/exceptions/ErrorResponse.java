package com.organization_service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private LocalDateTime localDateTime;
    private String errorMessage;
    private String path;
    private String errorCode;
}
