package com.sushanta.employeemanagement.repository;

import com.sushanta.employeemanagement.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {


    // Spring Data JPA Custom Method For Department
    List<Employee> findByDepartment(String department);

    // Spring Data JPA Custom Method For Age.
    List<Employee> findByAge(int age);

    List<Employee> findBySalary(float salary);

    List<Employee> findByDepartmentAndAgeGreaterThanEqual(String department, int age);

    // Employee Name Contains
    List<Employee> findByNameContainingIgnoreCase(String name);

    // Employee Salary Is Greater Than
    List<Employee> findBySalaryGreaterThan(float salary);

    // Employee Age Is Between
    List<Employee> findBySalaryBetween(float minSalary, float maxSalary);

    // Employee Department And Salary LessThan
    List<Employee> findByDepartmentOrderBySalaryDesc(String department);

    // Paginated Employee By Age
    Page<Employee> findByAge(int age, Pageable pageable);

}
