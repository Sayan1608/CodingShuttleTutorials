package com.codingshuttle.springbootweb.controllers;

import com.codingshuttle.springbootweb.dto.EmployeeDTO;
import com.codingshuttle.springbootweb.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

   private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(name = "employeeId") Long id) {
        Optional<EmployeeDTO> employeeById = employeeService.getEmployeeById(id);
        return employeeById
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping()
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false, name = "orderBy") String sortBy, @RequestParam(required = false) Integer age){
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody @Valid EmployeeDTO employeeDTO){
        return new ResponseEntity<>(employeeService.createNewEmployee(employeeDTO),HttpStatus.CREATED);
    }

    @PutMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody EmployeeDTO employeeDTO, @PathVariable(name = "employeeId")Long id){
        return ResponseEntity.ok(employeeService.updateEmployeeById(employeeDTO,id));
    }

    @DeleteMapping(path="/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable(name = "employeeId") Long id){
        return employeeService.deleteEmployeeById(id) ? ResponseEntity.ok(true) : ResponseEntity.notFound().build();
    }

    @PatchMapping(path ="{employeeId}" )
    public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@RequestBody Map<String,Object> updates, @PathVariable(name = "employeeId") Long id){
        EmployeeDTO employeeDTO = employeeService.updatePartialEmployeeById(updates, id);
        return (employeeDTO != null) ? ResponseEntity.ok(employeeDTO) : ResponseEntity.notFound().build();
    }

}
