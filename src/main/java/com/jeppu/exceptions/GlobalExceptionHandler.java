package com.jeppu.exceptions;

import com.jeppu.payloads.ApiResponse;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundException(ResourceNotFoundException ex) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSuccess(Boolean.FALSE);
        apiResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .collect(
                        Collectors.toMap(
                                error -> ((FieldError) error).getField(),
                                error -> ((FieldError) error).getDefaultMessage())
                );
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

}
