package com.codingshuttle.mappings.services;

import com.codingshuttle.mappings.entities.DepartmentEntity;
import com.codingshuttle.mappings.entities.EmployeeEntity;
import com.codingshuttle.mappings.repositories.DepartmentRepository;
import com.codingshuttle.mappings.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    public DepartmentEntity getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public DepartmentEntity createNewDepartment(DepartmentEntity departmentEntity) {
        return departmentRepository.save(departmentEntity);
    }

    public DepartmentEntity assignManagerToDepartment(Long departmentId, Long employeeId) {
        Optional<DepartmentEntity> departmentEntity = departmentRepository.findById(departmentId);
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);

        return departmentEntity.flatMap(department ->employeeEntity.map(employee ->{
                                department.setManager(employee);
                                return  departmentRepository.save(department);
                            })).orElse(null);

    }

    public DepartmentEntity getAssignedDepartmentOfManager(Long id) {
//        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
//        return employeeEntity.map(EmployeeEntity::getManagedDepartment).orElse(null);

        EmployeeEntity employeeEntity = EmployeeEntity.builder().id(id).build();
        return departmentRepository.findByManager(employeeEntity);
    }
}
