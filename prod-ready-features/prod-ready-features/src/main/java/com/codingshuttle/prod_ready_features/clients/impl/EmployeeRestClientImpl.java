package com.codingshuttle.prod_ready_features.clients.impl;

import com.codingshuttle.prod_ready_features.advices.ApiResponse;
import com.codingshuttle.prod_ready_features.clients.EmployeeRestClient;
import com.codingshuttle.prod_ready_features.dtos.EmployeeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeRestClientImpl implements EmployeeRestClient {

    @Autowired
    private final RestClient restClient;

    @Override
    public ApiResponse<List<EmployeeDTO>> getEmployeeDetails() {
        try {
            return restClient
                    .get()
                    .uri("employees")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
