package com.codingshuttle.prod_ready_features.clients;

import com.codingshuttle.prod_ready_features.advices.ApiResponse;
import com.codingshuttle.prod_ready_features.dtos.EmployeeDTO;

import java.util.List;

public interface EmployeeRestClient {
    public List<EmployeeDTO> getAllEmployees();

    public EmployeeDTO getEmployeeById(Long employeeId);

    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO);


}
