package com.codingshuttle.caching.services;

import com.codingshuttle.caching.dto.EmployeeDto;
import jakarta.validation.Valid;

public interface EmployeeService {
    EmployeeDto getEmployeeById(Long id);

    EmployeeDto createNewEmployee(EmployeeDto employeeDto);

    EmployeeDto updateEmployee(@Valid EmployeeDto employeeDto, Long id);

    void deleteEmployee(Long id);
}
