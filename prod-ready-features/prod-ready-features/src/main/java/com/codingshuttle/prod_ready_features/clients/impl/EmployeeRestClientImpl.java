package com.codingshuttle.prod_ready_features.clients.impl;

import com.codingshuttle.prod_ready_features.advices.ApiResponse;
import com.codingshuttle.prod_ready_features.clients.EmployeeRestClient;
import com.codingshuttle.prod_ready_features.dtos.EmployeeDTO;
import com.codingshuttle.prod_ready_features.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeRestClientImpl implements EmployeeRestClient {

    @Autowired
    private final RestClient restClient;

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        try {
             ApiResponse<List<EmployeeDTO>> apiResponse = restClient
                    .get()
                    .uri("employees")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
             return apiResponse.getData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public EmployeeDTO getEmployeeById(Long employeeId) {
       try {
           ApiResponse<EmployeeDTO> apiResponse = restClient
                   .get()
                   .uri("employees/{employeeId}",employeeId)
                   .retrieve()
                   .body(new ParameterizedTypeReference<>() {
                   });
           return apiResponse.getData();
       }catch (Exception e){
           throw  new RuntimeException(e);
       }
    }

    @Override
    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {
        try {
           ApiResponse<EmployeeDTO> apiResponse = restClient
                    .post()
                    .uri("employees")
                    .body(employeeDTO)
                    .retrieve()
                   .onStatus(HttpStatusCode::is4xxClientError, (req, res)->{
                       System.out.println(new String(res.getBody().readAllBytes()));
                       throw new ResourceNotFoundException("cannot create employee");
                   })
                    .body(new ParameterizedTypeReference<>() {
                    });
           return apiResponse.getData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
