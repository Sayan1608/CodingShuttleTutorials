package com.codingshuttle.securityapp.advices;

import com.codingshuttle.securityapp.exceptions.ResourceNotFoundException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> MethodArgumentNotValidException(MethodArgumentNotValidException exception){
        Map<String, String> subErrors = new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(fieldError->{
                    subErrors.put(fieldError.getField(),fieldError.getDefaultMessage());
                });
        ApiError apiError = ApiError
                .builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Please note the following errors :")
                .subErrors(subErrors)
                .build();

        return buildApiResponse(apiError);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException exception){
        ApiError apiError = ApiError
                .builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .build();

        return buildApiResponse(apiError);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<?>> handleBadCredentialsException(BadCredentialsException exception){
        ApiError apiError = ApiError
                .builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(exception.getLocalizedMessage())
                .build();

        return buildApiResponse(apiError);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<?>> handleAuthenticationException(AuthenticationException exception){
        ApiError apiError = ApiError
                .builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(exception.getLocalizedMessage())
                .build();

        return buildApiResponse(apiError);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse<?>> handleJwtException(JwtException exception){
        ApiError apiError = ApiError
                .builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(exception.getLocalizedMessage())
                .build();

        return buildApiResponse(apiError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAccessDeniedException(AccessDeniedException exception){
        ApiError apiError = ApiError
                .builder()
                .status(HttpStatus.FORBIDDEN)
                .message(exception.getMessage())
                .build();

        return buildApiResponse(apiError);
    }


    private ResponseEntity<ApiResponse<?>> buildApiResponse(ApiError apiError){
        return new ResponseEntity<>(new ApiResponse<>(apiError), apiError.getStatus());
    }
}
