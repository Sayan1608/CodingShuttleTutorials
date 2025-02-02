package com.codingshuttle.caching.controllers;

import com.codingshuttle.caching.advices.ApiResponse;
import com.codingshuttle.caching.dto.EmployeeDto;
import com.codingshuttle.caching.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/employees")
@Slf4j
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;


    @GetMapping(path = "/id/{empId}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable(name = "empId")Long id){
        log.info("Fetching Employee with id : {} from EmployeeController.getEmployeeById() : ",id);
        EmployeeDto employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createNewEmployee(@RequestBody @Valid EmployeeDto employeeDto){
        log.info("Creating a New Employee from EmployeeController.createNewEmployee()");
        return new ResponseEntity<>(employeeService.createNewEmployee(employeeDto),HttpStatus.CREATED);
    }

    @PutMapping(path = "/id/{empId}")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody @Valid EmployeeDto employeeDto,
                                                      @PathVariable(name = "empId") Long id){
        log.info("Updating Employee with id {} from EmployeeController.updateEmployee()",id);
        return ResponseEntity.ok(employeeService.updateEmployee(employeeDto,id));
    }

    @DeleteMapping(path = "/id/{empId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(name = "empId") Long id){
        log.info("Updating Employee with id {} from EmployeeController.createNewEmployee()",id);
        employeeService.deleteEmployee(id);
        ApiResponse<String> apiResponse =
                new ApiResponse<>("Employee with id: "+id+" deleted successfully.");
        return ResponseEntity.ok(apiResponse);
    }
}
