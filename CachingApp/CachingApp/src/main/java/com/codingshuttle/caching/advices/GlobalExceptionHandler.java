package com.codingshuttle.caching.advices;


import com.codingshuttle.caching.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException exception){
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .messege(exception.getMessage())
                .build();
        return buildErrorResponseBody(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerErrors(Exception exception){
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .messege(exception.getMessage())
                .build();
        return buildErrorResponseBody(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerErrors(MethodArgumentNotValidException exception){
        Map<String,String> fieldErrorMap = new LinkedHashMap<>();
       exception.getBindingResult().getFieldErrors().forEach(fieldError ->{
           fieldErrorMap.put(fieldError.getField(),fieldError.getDefaultMessage());
       });
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .messege("Please note the errors in the following fields : ")
                .subErrors(fieldErrorMap)
                .build();
        return buildErrorResponseBody(apiError);
    }

    private ResponseEntity<ApiResponse<?>> buildErrorResponseBody(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError), apiError.getStatus());
    }
}
