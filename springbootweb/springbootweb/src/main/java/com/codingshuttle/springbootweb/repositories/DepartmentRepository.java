package com.codingshuttle.springbootweb.repositories;

import com.codingshuttle.springbootweb.entities.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity,Long> {
}
