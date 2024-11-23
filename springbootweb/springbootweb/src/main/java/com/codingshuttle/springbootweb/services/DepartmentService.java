package com.codingshuttle.springbootweb.services;

import com.codingshuttle.springbootweb.dto.DepartmentDTO;
import com.codingshuttle.springbootweb.entities.DepartmentEntity;
import com.codingshuttle.springbootweb.exceptions.ResourceNotFoundException;
import com.codingshuttle.springbootweb.repositories.DepartmentRepository;
import jakarta.validation.Valid;
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
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    public DepartmentService(DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }

    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(dept->modelMapper.map(dept,DepartmentDTO.class))
                .collect(Collectors.toList());

    }

    public Optional<DepartmentDTO> getDepartmentById(Long id) {
        return departmentRepository
                .findById(id)
                .map(deptEty -> modelMapper.map(deptEty, DepartmentDTO.class));

    }

    public DepartmentDTO createNewDepartment(@Valid DepartmentDTO departmentDTO) {
        DepartmentEntity departmentEntity = modelMapper.map(departmentDTO, DepartmentEntity.class);
        return modelMapper.map(departmentRepository.save(departmentEntity), DepartmentDTO.class);
    }

    public DepartmentDTO updateDepartmentById(@Valid DepartmentDTO departmentDTO, Long id) {
        isExistsById(id);
        departmentDTO.setId(id);
        DepartmentEntity departmentEntity = departmentRepository.save(modelMapper.map(departmentDTO, DepartmentEntity.class));
        return modelMapper.map(departmentEntity, DepartmentDTO.class);
    }

    private void isExistsById(Long id) {
        boolean exists = departmentRepository.existsById(id);
        if(!exists) throw new ResourceNotFoundException("Department not found with id : " + id);
    }

    public DepartmentDTO updatePartiallyDepartmentById(Map<String, Object> updates, Long id) {
        isExistsById(id);
        DepartmentEntity departmentEntity = departmentRepository.findById(id).get();
        updates.forEach((key, value)->{
            Field field = ReflectionUtils.findRequiredField(DepartmentEntity.class, key);
            field.setAccessible(true);
            // Handle LocalDate explicitly
            if ("createdOn".equals(key) && value instanceof String) {
                value = LocalDate.parse((String) value); // Convert String to LocalDate
            }
            ReflectionUtils.setField(field, departmentEntity, value);
        });
        return modelMapper.map(departmentRepository.save(departmentEntity), DepartmentDTO.class);
    }

    public Boolean deleteDepartmentById(Long id) {
        isExistsById(id);
        departmentRepository.deleteById(id);
        return true;
    }
}
