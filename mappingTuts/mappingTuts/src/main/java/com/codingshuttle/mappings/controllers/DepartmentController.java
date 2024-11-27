package com.codingshuttle.mappings.controllers;

import com.codingshuttle.mappings.entities.DepartmentEntity;
import com.codingshuttle.mappings.services.DepartmentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping(path = "/{departmentId}")
    public DepartmentEntity getDepartmentById(@PathVariable(name = "departmentId") Long id){
        return departmentService.getDepartmentById(id);
    }

    @PostMapping
    public DepartmentEntity createNewDepartment(@RequestBody DepartmentEntity departmentEntity){
        return departmentService.createNewDepartment(departmentEntity);
    }

    @PutMapping(path = "/{departmentId}/manager/{employeeId}")
    public DepartmentEntity assignManagerToDepartment(
            @PathVariable(name = "departmentId") Long departmentId,
            @PathVariable(name = "employeeId") Long employeeId){
        return departmentService.assignManagerToDepartment(departmentId,employeeId);
    }

    @GetMapping(path = "/manager/{managerId}")
    public DepartmentEntity getAssignedDepartmentOfManager(@PathVariable(name = "managerId") Long id){
        return departmentService.getAssignedDepartmentOfManager(id);
    }
}
