package com.codingshuttle.mappings.services;

import com.codingshuttle.mappings.entities.EmployeeEntity;
import com.codingshuttle.mappings.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeEntity getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public EmployeeEntity createNewEmployee(EmployeeEntity employeeEntity) {
        return employeeRepository.save(employeeEntity);
    }
}
