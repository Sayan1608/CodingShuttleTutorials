package com.codingshuttle.mappings.controllers;

import com.codingshuttle.mappings.entities.EmployeeEntity;
import com.codingshuttle.mappings.services.EmployeeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(path = "/{employeeId}")
    public EmployeeEntity getEmployeeById(@PathVariable(name = "employeeId") Long id){
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public EmployeeEntity createNewEmployee(@RequestBody EmployeeEntity employeeEntity){
        return employeeService.createNewEmployee(employeeEntity);
    }

}
