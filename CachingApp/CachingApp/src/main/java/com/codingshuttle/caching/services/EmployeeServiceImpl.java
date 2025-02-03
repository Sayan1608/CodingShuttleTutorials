package com.codingshuttle.caching.services;

import com.codingshuttle.caching.dto.EmployeeDto;
import com.codingshuttle.caching.entities.Employee;
import com.codingshuttle.caching.exceptions.ResourceNotFoundException;
import com.codingshuttle.caching.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{
    public static final String EMPLOYEE = "employee";
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;


    @Override
    @Cacheable(cacheNames = EMPLOYEE, key = "#id")
    public EmployeeDto getEmployeeById(Long id) {
        log.info("Fetching Employee with id {} from EmployeeServiceImpl.getEmployeeById() ",id);
        isEmployeeExistsById(id);
        return modelMapper.map(employeeRepository.findById(id).get(),EmployeeDto.class);
    }

    @Override
    @CachePut(cacheNames = EMPLOYEE, key = "#result.id")
    public EmployeeDto createNewEmployee(EmployeeDto employeeDto) {
        log.info("Creating a New Employee from EmployeeServiceImpl.createNewEmployee()");
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        return modelMapper.map(employeeRepository.save(employee),EmployeeDto.class);
    }

    @Override
    @CachePut(cacheNames = EMPLOYEE, key = "#id")
    public EmployeeDto updateEmployee(EmployeeDto employeeDto, Long id) {
        log.info("Updating Employee with id {} from EmployeeServiceImpl.updateEmployee()",id);
        isEmployeeExistsById(id);
        employeeDto.setId(id);
        return modelMapper
                .map(employeeRepository
                        .save(modelMapper.map(employeeDto,Employee.class)),EmployeeDto.class);
    }

    @Override
    @CacheEvict(cacheNames = EMPLOYEE, key = "#id")
    public void deleteEmployee(Long id) {
        log.info("Deleting Employee with id {} from EmployeeServiceImpl.deleteEmployee()",id);
        isEmployeeExistsById(id);
        employeeRepository.deleteById(id);
    }

    private void isEmployeeExistsById(Long id) {
        boolean exists = employeeRepository.existsById(id);
        if(!exists) throw new ResourceNotFoundException("Employee not found with id: "+ id);
    }
}
