package com.sushanta.employeemanagement.service;


import com.sushanta.employeemanagement.dto.EmployeeRequestDTO;
import com.sushanta.employeemanagement.dto.EmployeeResponseDTO;
import com.sushanta.employeemanagement.entity.Employee;
import com.sushanta.employeemanagement.exception.EmployeeNotFoundException;
import com.sushanta.employeemanagement.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    // Dependency Creation
    EmployeeRepository repository;
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    // Add Employee
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO) {
        // DTO -> Entity
        Employee employee = new Employee(
                employeeRequestDTO.getName(),
                employeeRequestDTO.getEmail(),
                employeeRequestDTO.getAge(),
                employeeRequestDTO.getDepartment(),
                employeeRequestDTO.getSalary()
        );

        repository.save(employee);

        // Entity -> DTO
        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO(
                employee.getName(),
                employee.getAge(),
                employee.getDepartment(),
                employee.getSalary()
        );
        return employeeResponseDTO;
    }

    // Get Employee
    public List<EmployeeResponseDTO> getEmployee (){

        // Getting All The List Of Employee Records.
        List<Employee> employees = repository.findAll();
        // Then Creating An Empty Array List.
        List<EmployeeResponseDTO> employeeResponseDTOS = new ArrayList<>();
        // Converting Each Employee Data To Each Response DTO.
        for (Employee employee : employees) {
            EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO(
                    employee.getName(),
                    employee.getAge(),
                    employee.getDepartment(),
                    employee.getSalary()
            );
            // Adding Each DTO Inside Our ArrayList.
            employeeResponseDTOS.add(employeeResponseDTO);
        }
        // Returning Our Employee DTOS That Are Stored Inside Our ArrayList.
        return employeeResponseDTOS;
    }

    // Get Employee By Department
    public List<EmployeeResponseDTO> findByDepartment(String department) {

        List<Employee> employeeList = repository.findByDepartment(department);

        List<EmployeeResponseDTO> employeeResponseDTOS = new ArrayList<>();

        for (Employee employee : employeeList) {
            EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO(
                    employee.getName(),
                    employee.getAge(),
                    employee.getDepartment(),
                    employee.getSalary()
            );
            employeeResponseDTOS.add(employeeResponseDTO);
        }
        return employeeResponseDTOS;
    }

    // Get Employee By ID
    public EmployeeResponseDTO getEmployeeById(int id) {
        Optional<Employee> optionalEmployee = repository.findById(id);

        if (optionalEmployee.isEmpty()) {
            throw new EmployeeNotFoundException("Employee " + id + " Is Not Found");
        }

        Employee employee = optionalEmployee.get();

        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO(
                employee.getName(),
                employee.getAge(),
                employee.getDepartment(),
                employee.getSalary()
        );

        return employeeResponseDTO;
    }

    // Update Employee By ID
    public EmployeeResponseDTO updateEmployeeById(int id, EmployeeRequestDTO employeeRequestDTO) {

        Optional<Employee> optionalEmployee = repository.findById(id);

        if (optionalEmployee.isEmpty()) {
            throw new EmployeeNotFoundException("Employee " + id + " Is Not Found");
        }
        Employee employee = optionalEmployee.get();
        employee.setName(employeeRequestDTO.getName());
        employee.setAge(employeeRequestDTO.getAge());
        employee.setDepartment(employeeRequestDTO.getDepartment());
        employee.setSalary(employeeRequestDTO.getSalary());
        repository.save(employee);
        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO(
                employee.getName(),
                employee.getAge(),
                employee.getDepartment(),
                employee.getSalary()
        );
        return employeeResponseDTO;
    }

    // Delete Employee
    public String deleteEmployeeById(int id) {
        Optional<Employee> optionalEmployee = repository.findById(id);
        if (optionalEmployee.isEmpty()) {
            throw new EmployeeNotFoundException("Employee " + id + " Is Not Found");
        }
        Employee employee = optionalEmployee.get();
        repository.delete(employee);
        return "Employee ID: " + id + " Name: " + employee.getName() + " has been deleted";

    }

    // Employee By Age
    public List<EmployeeResponseDTO> getEmployeeByAge(int age) {
        List<Employee> employeeList = repository.findByAge(age);

        List<EmployeeResponseDTO> employeeResponseDTOS = new ArrayList<>();

        for (Employee employee : employeeList) {
            if (employee.getAge() >= age) {
                EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO(
                        employee.getName(),
                        employee.getAge(),
                        employee.getDepartment(),
                        employee.getSalary()
                );
                employeeResponseDTOS.add(employeeResponseDTO);
            }
        }
        return employeeResponseDTOS;
    }

    // Employee By Salary
    public List<EmployeeResponseDTO> getEmployeeBySalary(float salary) {

        List<Employee> employeeList = repository.findBySalary(salary);

        List<EmployeeResponseDTO> employeeResponseDTOS = new ArrayList<>();
        for (Employee employee : employeeList) {
            if (employee.getSalary() >= salary) {
                EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO(
                        employee.getName(),
                        employee.getAge(),
                        employee.getDepartment(),
                        employee.getSalary()
                );
                employeeResponseDTOS.add(employeeResponseDTO);
            }
        }
        return employeeResponseDTOS;
    }

    // Employee By Department And Age
    public List<EmployeeResponseDTO> getEmployeeByDepartmentAndAge (String department, int age) {
        List<Employee> employees = repository.findByDepartmentAndAgeGreaterThanEqual(department, age);

        List<EmployeeResponseDTO> employeeResponseDTOS = new ArrayList<>();

        for (Employee employee : employees) {
                EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO(
                        employee.getName(),
                        employee.getAge(),
                        employee.getDepartment(),
                        employee.getSalary()
                );
                employeeResponseDTOS.add(employeeResponseDTO);
        }
        return employeeResponseDTOS;
    }

    // Employee Name Contains.
    public List<EmployeeResponseDTO> getEmployeeByNameContains(String name) {
        List<Employee> employees = repository.findByNameContainingIgnoreCase(name);
        List<EmployeeResponseDTO> employeeResponseDTOS = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO(
                    employee.getName(),
                    employee.getAge(),
                    employee.getDepartment(),
                    employee.getSalary()
            );
            employeeResponseDTOS.add(employeeResponseDTO);
        }
        return employeeResponseDTOS;
    }

    // Employee Salary Is Greater Than.

    public List<EmployeeResponseDTO> getEmployeeBySalaryGreaterThan(float salary) {
        List<Employee> employees = repository.findBySalaryGreaterThan(salary);
        List<EmployeeResponseDTO> employeeResponseDTOS = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO(
                    employee.getName(),
                    employee.getAge(),
                    employee.getDepartment(),
                    employee.getSalary()
            );
            employeeResponseDTOS.add(employeeResponseDTO);
        }
        return employeeResponseDTOS;
    }

    // Employee Age Is Between.
    public List<EmployeeResponseDTO> getEmployeeBySalaryBetween(float minSalary,float maxSalary) {
        List<Employee> employees = repository.findBySalaryBetween(minSalary, maxSalary);

        List<EmployeeResponseDTO> employeeResponseDTOS = new ArrayList<>();

        for (Employee employee : employees) {
            EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO(
                    employee.getName(),
                    employee.getAge(),
                    employee.getDepartment(),
                    employee.getSalary()
            );
            employeeResponseDTOS.add(employeeResponseDTO);
        }
        return employeeResponseDTOS;
    }

    // Employee Department And Salary LessThan.
    public List<EmployeeResponseDTO> getEmployeeByDepartmentSortBySalaryDesc(String department) {

        List<Employee> employees = repository.findByDepartmentOrderBySalaryDesc(department);

        List<EmployeeResponseDTO> employeeResponseDTOS = new ArrayList<>();

        for (Employee employee : employees) {
            EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO(
                    employee.getName(),
                    employee.getAge(),
                    employee.getDepartment(),
                    employee.getSalary()
            );
            employeeResponseDTOS.add(employeeResponseDTO);
        }
        return employeeResponseDTOS;
    }
}
