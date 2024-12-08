package com.codingshuttle.prod_ready_features.advices;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@Builder
public class ApiError {
    private HttpStatus status;
    private String message;
    private Map<String,String> subErrors;

}
