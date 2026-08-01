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

    // Employee By Department
    @GetMapping("/department/{department}")
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployeesByDepartment(@PathVariable String department) {
        return ResponseEntity.ok().body(employeeService.findByDepartment(department));
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

    // Employee By Salary
    @GetMapping("/salary/{salary}")
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployeesBySalary(@PathVariable float salary) {
        List<EmployeeResponseDTO> response = employeeService.getEmployeeBySalary(salary);
        return ResponseEntity.ok().body(response);
    }

    // Employee By Age
    @GetMapping("/age/{age}")
    public ResponseEntity<List<EmployeeResponseDTO>> getEmployeeByAge (@PathVariable int age){
        List<EmployeeResponseDTO> response = employeeService.getEmployeeByAge(age);
        return ResponseEntity.ok().body(response);
    }

    // Employee By Dept. And Age
    @GetMapping("/department/{department}/age/{age}")
    public ResponseEntity<List<EmployeeResponseDTO>> getEmployeeByDepartmentAndAge(@PathVariable String department, @PathVariable int age){
        List<EmployeeResponseDTO> response = employeeService.getEmployeeByDepartmentAndAge(department, age);
        return ResponseEntity.ok().body(response);
    }

    // Employee By Name Contains
    @GetMapping("/name/{name}")
        public ResponseEntity<List<EmployeeResponseDTO>> getEmployeeByNameContains(@PathVariable String name){
        List<EmployeeResponseDTO> response = employeeService.getEmployeeByNameContains(name);
        return ResponseEntity.ok().body(response);
        }

    // Employee By Salary Is Greater Than
    @GetMapping("/salary/greater-than/{salary}")
    public ResponseEntity<List<EmployeeResponseDTO>> getEmployeeBySalaryGreaterThan(@PathVariable float salary){
        List<EmployeeResponseDTO> response = employeeService.getEmployeeBySalaryGreaterThan(salary);
        return ResponseEntity.ok().body(response);
    }

    // Employee By Salary Between
    @GetMapping("/salary/between/{minSalary}/{maxSalary}")
    public ResponseEntity<List<EmployeeResponseDTO>> getEmployeeBySalaryBetween(@PathVariable float minSalary,@PathVariable float maxSalary){
        List<EmployeeResponseDTO> response = employeeService.getEmployeeBySalaryBetween(minSalary,maxSalary);
        return ResponseEntity.ok().body(response);
    }

    // Employee By Salary LessThan
    @GetMapping("/department/{department}/sort/salary/desc")
    public ResponseEntity<List<EmployeeResponseDTO>> getEmployeeByDepartmentAndSalary(@PathVariable String department){
        List<EmployeeResponseDTO> response = employeeService.getEmployeeByDepartmentSortBySalaryDesc(department);
        return ResponseEntity.ok().body(response);
    }

}
