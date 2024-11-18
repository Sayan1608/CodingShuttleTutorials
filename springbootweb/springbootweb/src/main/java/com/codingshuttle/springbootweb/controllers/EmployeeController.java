package com.codingshuttle.springbootweb.controllers;

import com.codingshuttle.springbootweb.dto.EmployeeDTO;
import com.codingshuttle.springbootweb.entities.EmployeeEntity;
import com.codingshuttle.springbootweb.repositories.EmployeeRepository;
import com.codingshuttle.springbootweb.services.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

   private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{employeeId}")
    public EmployeeDTO getEmployeeById(@PathVariable(name = "employeeId") Long id) {
        return employeeService.getEmployeeById(id);

    }

    @GetMapping()
    public List<EmployeeDTO> getAllEmployees(@RequestParam(required = false, name = "orderBy") String sortBy, @RequestParam(required = false) Integer age){
        return employeeService.getAllEmployees();
    }

    @PostMapping
    public EmployeeDTO createNewEmployee(@RequestBody EmployeeDTO employeeDTO){
        return employeeService.createNewEmployee(employeeDTO);
    }

    @PutMapping
    public String updateEmployee(Long id){
        return "Hello from PUT";
    }

}
