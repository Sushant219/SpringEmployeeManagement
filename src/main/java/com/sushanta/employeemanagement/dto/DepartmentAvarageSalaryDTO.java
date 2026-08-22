package com.sushanta.employeemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class DepartmentAvarageSalaryDTO {
    private String department;
    private double salary;
}
