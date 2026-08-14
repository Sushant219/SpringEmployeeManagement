package com.sushanta.employeemanagement.controller;


import com.sushanta.employeemanagement.dto.EmployeeRequestDTO;
import com.sushanta.employeemanagement.dto.EmployeeResponseDTO;
import com.sushanta.employeemanagement.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // ===========================
    // CRUD
    // ===========================

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(
            @Valid @RequestBody EmployeeRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employeeService.createEmployee(request));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getEmployee());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable int id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(
            @PathVariable int id,
            @Valid @RequestBody EmployeeRequestDTO request) {

        return ResponseEntity.ok(employeeService.updateEmployeeById(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        return ResponseEntity.ok(employeeService.deleteEmployeeById(id));
    }

    // ===========================
    // FILTERS
    // ===========================

    @GetMapping("/department/{department}")
    public ResponseEntity<List<EmployeeResponseDTO>> getByDepartment(
            @PathVariable String department) {

        return ResponseEntity.ok(employeeService.findByDepartment(department));
    }

    @GetMapping("/age/{age}")
    public ResponseEntity<List<EmployeeResponseDTO>> getByAge(
            @PathVariable int age) {

        return ResponseEntity.ok(employeeService.getEmployeeByAge(age));
    }

    @GetMapping("/salary/{salary}")
    public ResponseEntity<List<EmployeeResponseDTO>> getBySalary(
            @PathVariable float salary) {

        return ResponseEntity.ok(employeeService.getEmployeeBySalary(salary));
    }

    @GetMapping("/salary/greater-than/{salary}")
    public ResponseEntity<List<EmployeeResponseDTO>> getSalaryGreaterThan(
            @PathVariable float salary) {

        return ResponseEntity.ok(employeeService.getEmployeeBySalaryGreaterThan(salary));
    }

    @GetMapping("/salary/between/{minSalary}/{maxSalary}")
    public ResponseEntity<List<EmployeeResponseDTO>> getSalaryBetween(
            @PathVariable float minSalary,
            @PathVariable float maxSalary) {

        return ResponseEntity.ok(
                employeeService.getEmployeeBySalaryBetween(minSalary, maxSalary));
    }

    @GetMapping("/department/{department}/age/{age}")
    public ResponseEntity<List<EmployeeResponseDTO>> getDepartmentAndAge(
            @PathVariable String department,
            @PathVariable int age) {

        return ResponseEntity.ok(
                employeeService.getEmployeeByDepartmentAndAge(department, age));
    }

    @GetMapping("/search/name/{name}")
    public ResponseEntity<List<EmployeeResponseDTO>> searchByName(
            @PathVariable String name) {

        return ResponseEntity.ok(employeeService.getEmployeeByNameContains(name));
    }

    @GetMapping("/department/{department}/salary/desc")
    public ResponseEntity<List<EmployeeResponseDTO>> getDepartmentOrderBySalaryDesc(
            @PathVariable String department) {

        return ResponseEntity.ok(
                employeeService.getEmployeeByDepartmentSortBySalaryDesc(department));
    }

    // ===========================
    // PAGINATION
    // ===========================

    @GetMapping("/page")
    public ResponseEntity<Page<EmployeeResponseDTO>> getEmployeesByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return ResponseEntity.ok(
                employeeService.getEmployeeByPage(page, size));
    }

    // ===========================
    // PAGINATION + SORTING
    // ===========================

    @GetMapping("/page/sort")
    public ResponseEntity<Page<EmployeeResponseDTO>> getEmployeesByPageAndSort(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sort,

            @RequestParam(defaultValue = "ASC") String direction) {

        return ResponseEntity.ok(
                employeeService.getEmployeesByPageAndSort(page, size, sort, direction));
    }

    // ===========================
    // FILTER + PAGINATION + SORTING
    // ===========================

    @GetMapping("/department/{department}/page")
    public ResponseEntity<Page<EmployeeResponseDTO>> getEmployeesByDepartment(

            @PathVariable String department,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sort,

            @RequestParam(defaultValue = "ASC") String direction) {

        return ResponseEntity.ok(
                employeeService.getEmployeesByDepartmentWithPagination(
                        department,
                        page,
                        size,
                        sort,
                        direction
                )
        );
    }

    @GetMapping("/age/{age}/page")
    public ResponseEntity<Page<EmployeeResponseDTO>> getAgeEmployees(

            @PathVariable int age,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sort,

            @RequestParam(defaultValue = "ASC") String direction) {

        return ResponseEntity.ok(
                employeeService.getEmployeesByAgeWithPagination(
                        age,
                        page,
                        size,
                        sort,
                        direction));
    }
}