package com.sushanta.employeemanagement.dto;


import com.sushanta.employeemanagement.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDTO {
    private String name;
    private String email;
    private int age;
    private String departmentId;
    private float salary;
}
