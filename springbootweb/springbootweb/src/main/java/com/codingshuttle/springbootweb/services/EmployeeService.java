package com.codingshuttle.springbootweb.services;

import com.codingshuttle.springbootweb.dto.EmployeeDTO;
import com.codingshuttle.springbootweb.entities.EmployeeEntity;
import com.codingshuttle.springbootweb.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }


    public EmployeeDTO getEmployeeById(Long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
        return modelMapper.map(employeeEntity, EmployeeDTO.class);

    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return  employeeEntities
                .stream()
                .map(empEty->modelMapper.map(empEty,EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {
        EmployeeEntity savedEmployee = employeeRepository.save(modelMapper.map(employeeDTO, EmployeeEntity.class));
        return modelMapper.map(savedEmployee, EmployeeDTO.class);
    }
}
