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
                employee.getDepartment()
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
                    employee.getDepartment()
            );
            // Adding Each DTO Inside Our ArrayList.
            employeeResponseDTOS.add(employeeResponseDTO);
        }
        // Returning Our Employee DTOS That Are Stored Inside Our ArrayList.
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
                employee.getDepartment()
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
                employee.getDepartment()
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
}
