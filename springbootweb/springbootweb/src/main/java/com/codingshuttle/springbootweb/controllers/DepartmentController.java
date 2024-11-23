package com.codingshuttle.springbootweb.controllers;

import com.codingshuttle.springbootweb.dto.DepartmentDTO;
import com.codingshuttle.springbootweb.exceptions.ResourceNotFoundException;
import com.codingshuttle.springbootweb.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments(){
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @GetMapping(path = "/{departmentId}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable(name = "departmentId") Long id){
        Optional<DepartmentDTO> departmentById = departmentService.getDepartmentById(id);
        return departmentById
                .map(ResponseEntity::ok)
                .orElseThrow(()-> new ResourceNotFoundException("Department not found with id : " + id));

    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> createNewDepartment(@RequestBody @Valid DepartmentDTO departmentDTO){
        return new ResponseEntity<>(departmentService.createNewDepartment(departmentDTO), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{departmentId}")
    public ResponseEntity<DepartmentDTO> updateDepartmentById(@RequestBody @Valid DepartmentDTO departmentDTO, @PathVariable(name = "departmentId") Long id){
        return ResponseEntity.ok(departmentService.updateDepartmentById(departmentDTO, id));
    }

    @PatchMapping(path = "/{departmentId}")
    public ResponseEntity<DepartmentDTO> updatePartiallyDepartmentById(@RequestBody Map<String,Object> updates, @PathVariable(name = "departmentId") Long id){
        return ResponseEntity.ok(departmentService.updatePartiallyDepartmentById(updates, id));
    }

    @DeleteMapping(path = "/{departmentId}")
    public ResponseEntity<Boolean> deleteDepartmentById(@PathVariable(name = "departmentId") Long id){
        return ResponseEntity.ok(departmentService.deleteDepartmentById(id));
    }





}
