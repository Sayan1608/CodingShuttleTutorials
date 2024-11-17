package com.codingshuttle.springbootweb.controllers;

import com.codingshuttle.springbootweb.dto.EmployeeDTO;
import com.codingshuttle.springbootweb.entities.EmployeeEntity;
import com.codingshuttle.springbootweb.repositories.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    final private EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

//    @GetMapping(path = "/getMySecretMessege")
//    public String getMySuperSecretMessege(){
//        return "Secret Message is : asdf#thhn$wer";
//    }

    @GetMapping("/{employeeId}")
    public EmployeeEntity getEmployeeById(@PathVariable(name = "employeeId") Long id) {
        return employeeRepository.findById(id).orElse(null);

    }

    @GetMapping()
    public List<EmployeeEntity> getAllEmployees(@RequestParam(required = false, name = "orderBy") String sortBy, @RequestParam(required = false) Integer age){
        return employeeRepository.findAll();
    }

    @PostMapping
    public EmployeeEntity createNewEmployee(@RequestBody EmployeeEntity inputEmployeeEntity){
        return employeeRepository.save(inputEmployeeEntity);
    }

    @PutMapping
    public String updateEmployee(Long id){
        return "Hello from PUT";
    }

}
