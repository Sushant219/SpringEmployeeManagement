package com.sushanta.employeemanagement.controller;


import com.sushanta.employeemanagement.entity.Department;
import com.sushanta.employeemanagement.entity.Employee;
import com.sushanta.employeemanagement.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    // Dependency Injection
    private DepartmentService departmentService;
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    // Post Request
    @PostMapping("/create")
    public ResponseEntity<Department> createDepartment(@RequestBody Department request){
        return ResponseEntity.ok(departmentService.createDepartment(request));
    }

    // Get Request
    @GetMapping("/{id}")
    public ResponseEntity<List<Employee>> getDepartmentWithID(@PathVariable long id){
        return ResponseEntity.ok(departmentService.getDepartmentWithId(id));
    }

    // Get All Department
    @GetMapping("/all")
    public ResponseEntity<List<Department>> getAllDepartments(){
        return ResponseEntity.ok(departmentService.getAllDepartment());
    }

    // Put Request
    @PutMapping("/update/{id}")
    public ResponseEntity<Department> updateDepartment (@PathVariable long id, @RequestBody Department department){
        return ResponseEntity.ok(departmentService.updateDepartment(id, department));
    }

    // Delete Request
    @DeleteMapping("/delete/{id}")
    public String getDepartment(long id){
        return departmentService.deleteDepartment(id);
    }
}
