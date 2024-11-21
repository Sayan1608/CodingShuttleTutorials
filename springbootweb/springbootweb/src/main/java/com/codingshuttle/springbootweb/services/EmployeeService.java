package com.codingshuttle.springbootweb.services;

import com.codingshuttle.springbootweb.dto.EmployeeDTO;
import com.codingshuttle.springbootweb.entities.EmployeeEntity;
import com.codingshuttle.springbootweb.exceptions.ResourceNotFoundException;
import com.codingshuttle.springbootweb.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;


import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }


    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
        return employeeEntity.map(entity->modelMapper.map(entity, EmployeeDTO.class));

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

    public EmployeeDTO updateEmployeeById(EmployeeDTO employeeDTO, Long id) {
        isExistsEmployeeById(id);
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        employeeEntity.setId(id);
        return modelMapper.map(employeeRepository.save(employeeEntity),EmployeeDTO.class);
    }

    public void isExistsEmployeeById(Long id){
        boolean exists = employeeRepository.existsById(id);
        if(!exists) throw new ResourceNotFoundException("Employee Not Found with id : " + id);
    }

    public boolean deleteEmployeeById(Long id) {
        isExistsEmployeeById(id);
        employeeRepository.deleteById(id);
        return true;
    }

    public EmployeeDTO updatePartialEmployeeById(Map<String, Object> updates, Long id) {
        isExistsEmployeeById(id);
        EmployeeEntity employeeEntity = employeeRepository.findById(id).get();
        updates.forEach((key,value)->{
            Field field = ReflectionUtils.findRequiredField(EmployeeEntity.class,key);
            field.setAccessible(true);
            // Handle LocalDate explicitly
            if ("dateOfJoining".equals(key) && value instanceof String) {
                value = LocalDate.parse((String) value); // Convert String to LocalDate
            }
            ReflectionUtils.setField(field,employeeEntity,value);
        });
       return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);
    }
}
