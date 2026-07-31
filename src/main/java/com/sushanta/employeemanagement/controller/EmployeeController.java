package com.sushanta.employeemanagement.controller;


import com.sushanta.employeemanagement.dto.EmployeeRequestDTO;
import com.sushanta.employeemanagement.dto.EmployeeResponseDTO;
import com.sushanta.employeemanagement.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/employee")
public class EmployeeController {

    // Dependency Creation
    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {

        this.employeeService = employeeService;
    }

    // Add Employee
    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(employeeRequestDTO));
    }

    // Get Employee
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        return ResponseEntity.ok().body(employeeService.getEmployee());
    }

    // Get Employee By ID
    @GetMapping("/{id}")
    public EmployeeResponseDTO getEmployee(@PathVariable int id) {
       return employeeService.getEmployeeById(id);
    }

    // Update Employee By ID
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@Valid @PathVariable int id, @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        EmployeeResponseDTO response = employeeService.updateEmployeeById(id, employeeRequestDTO);
        return ResponseEntity.ok().body(response);
    }

    // Delete Employee
    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable int id) {
        return employeeService.deleteEmployeeById(id);
    }
}
