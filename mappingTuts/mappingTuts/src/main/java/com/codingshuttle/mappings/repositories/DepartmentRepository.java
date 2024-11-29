package com.codingshuttle.mappings.repositories;

import com.codingshuttle.mappings.entities.DepartmentEntity;
import com.codingshuttle.mappings.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
    DepartmentEntity findByManager(EmployeeEntity employeeEntity);

    DepartmentEntity findByWorkers(EmployeeEntity build);
}
