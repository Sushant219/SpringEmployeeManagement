package com.sushanta.employeemanagement.service;


import com.sushanta.employeemanagement.dto.DepartmentAvarageSalaryDTO;
import com.sushanta.employeemanagement.dto.EmployeeRequestDTO;
import com.sushanta.employeemanagement.dto.EmployeeResponseDTO;
import com.sushanta.employeemanagement.entity.Department;
import com.sushanta.employeemanagement.entity.Employee;
import com.sushanta.employeemanagement.exception.DepartmentNotFoundException;
import com.sushanta.employeemanagement.exception.DuplicateEmailException;
import com.sushanta.employeemanagement.exception.EmployeeNotFoundException;
import com.sushanta.employeemanagement.exception.InvalidDepartmentException;
import com.sushanta.employeemanagement.repository.DepartmentRepository;
import com.sushanta.employeemanagement.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    // Dependency Creation
    EmployeeRepository repository;
    DepartmentRepository departmentRepository;
    private final EmployeeUpdateService employeeUpdateService;

    public EmployeeService(EmployeeRepository repository, DepartmentRepository departmentRepository, EmployeeUpdateService employeeUpdateService) {
        this.repository = repository;
        this.departmentRepository = departmentRepository;
        this.employeeUpdateService = employeeUpdateService;
    }

    // Add Employee
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO) {

        Department departmentId = departmentRepository.findById(employeeRequestDTO.getDepartmentId()).orElseThrow(()->new RuntimeException("Department Not Found!"));

        // DTO -> Entity
        Employee employee = new Employee(
                employeeRequestDTO.getName(),
                employeeRequestDTO.getEmail(),
                employeeRequestDTO.getAge(),
                departmentId,
                employeeRequestDTO.getSalary()
        );

        // Validating Email
        if (repository.existsByEmail(employee.getEmail())) {
            throw new DuplicateEmailException("Employee with " + employee.getEmail() + " already exists.");
        }


        // Save The Employee After Cheacking.
        repository.save(employee);


        // Entity -> DTO
        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO(
                employee.getName(),
                employee.getEmail(),
                employee.getAge(),
                employee.getDepartment().getName(),
                employee.getSalary()
        );
        return employeeResponseDTO;
    }

    // Get Employee
    public List<EmployeeResponseDTO> getEmployee() {

        // Getting All The List Of Employee Records.
        List<Employee> employees = repository.findAll();
        // Then Creating An Empty Array List.
        List<EmployeeResponseDTO> employeeResponseDTOS = new ArrayList<>();
        // Converting Each Employee Data To Each Response DTO.
        for (Employee employee : employees) {
            EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO(
                    employee.getName(),
                    employee.getEmail(),
                    employee.getAge(),
                    employee.getDepartment().getName(),
                    employee.getSalary()
            );
            // Adding Each DTO Inside Our ArrayList.
            employeeResponseDTOS.add(employeeResponseDTO);
        }
        // Returning Our Employee DTOS That Are Stored Inside Our ArrayList.
        return employeeResponseDTOS;
    }

    // Get Employee By Department
    public List<EmployeeResponseDTO> findByDepartment(Department department) {

        List<Employee> employeeList = repository.findByDepartment(department);

        List<EmployeeResponseDTO> employeeResponseDTOS = new ArrayList<>();

        for (Employee employee : employeeList) {
            EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO(
                    employee.getName(),
                    employee.getEmail(),
                    employee.getAge(),
                    employee.getDepartment().getName(),
                    employee.getSalary()
            );
            employeeResponseDTOS.add(employeeResponseDTO);
        }
        return employeeResponseDTOS;
    }

    public List<DepartmentAvarageSalaryDTO> getEmployeeDepartmentAvarageSalary() {
        return repository.findAvarageSalaryByDepartment();
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
                employee.getEmail(),
                employee.getAge(),
                employee.getDepartment().getName(),
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

        if (repository.existsByEmailAndIdNot(employee.getEmail(), id)) {
            throw new DuplicateEmailException("This Email: " + employee.getEmail() + " belongs to other employee.");
        }


        Department department = departmentRepository.findById(employeeRequestDTO.getDepartmentId())
                .orElseThrow(() -> new DepartmentNotFoundException(
                        "Department " + employeeRequestDTO.getDepartmentId() + " not found"
                ));

        employee.setName(employeeRequestDTO.getName());
        employee.setEmail(employeeRequestDTO.getEmail());
        employee.setAge(employeeRequestDTO.getAge());
        employee.setDepartment(department);
        employee.setSalary(employeeRequestDTO.getSalary());


        repository.save(employee);


        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO(
                employee.getName(),
                employee.getEmail(),
                employee.getAge(),
                employee.getDepartment().getName(),
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
                        employee.getEmail(),
                        employee.getAge(),
                        employee.getDepartment().getName(),
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
                        employee.getEmail(),
                        employee.getAge(),
                        employee.getDepartment().getName(),
                        employee.getSalary()
                );
                employeeResponseDTOS.add(employeeResponseDTO);
            }
        }
        return employeeResponseDTOS;
    }


    // Employee From DepartmentA Or DepartmentB
    public List<EmployeeResponseDTO> employeeFromDepartmentAOrDepartmentB(Department departmentA, Department departmentB) {
        List<Employee> employeeList = repository.findByDepartmentAOrDepartmentB(departmentA, departmentB);
        List<EmployeeResponseDTO> employeeResponseDTOS = new ArrayList<>();
        for (Employee employee : employeeList) {
            EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO(
                    employee.getName(),
                    employee.getEmail(),
                    employee.getAge(),
                    employee.getDepartment().getName(),
                    employee.getSalary()
            );
            employeeResponseDTOS.add(employeeResponseDTO);
        }
        return employeeResponseDTOS;
    }

    // Employee By Department And Age
    public List<EmployeeResponseDTO> getEmployeeByDepartmentAndAge(Department department, int age) {
        List<Employee> employees = repository.findByDepartmentAndAgeGreaterThanEqual(department, age);

        List<EmployeeResponseDTO> employeeResponseDTOS = new ArrayList<>();

        for (Employee employee : employees) {
            EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO(
                    employee.getName(),
                    employee.getEmail(),
                    employee.getAge(),
                    employee.getDepartment().getName(),
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
                    employee.getEmail(),
                    employee.getAge(),
                    employee.getDepartment().getName(),
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
                    employee.getEmail(),
                    employee.getAge(),
                    employee.getDepartment().getName(),
                    employee.getSalary()
            );
            employeeResponseDTOS.add(employeeResponseDTO);
        }
        return employeeResponseDTOS;
    }

    // Employee Age Is Between.
    public List<EmployeeResponseDTO> getEmployeeBySalaryBetween(float minSalary, float maxSalary) {
        List<Employee> employees = repository.findBySalaryBetween(minSalary, maxSalary);

        List<EmployeeResponseDTO> employeeResponseDTOS = new ArrayList<>();

        for (Employee employee : employees) {
            EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO(
                    employee.getName(),
                    employee.getEmail(),
                    employee.getAge(),
                    employee.getDepartment().getName(),
                    employee.getSalary()
            );
            employeeResponseDTOS.add(employeeResponseDTO);
        }
        return employeeResponseDTOS;
    }

    // Employee Department And Salary LessThan.
    public List<EmployeeResponseDTO> getEmployeeByDepartmentSortBySalaryDesc(Department department) {

        List<Employee> employees = repository.findByDepartmentOrderBySalaryDesc(department);

        List<EmployeeResponseDTO> employeeResponseDTOS = new ArrayList<>();

        for (Employee employee : employees) {
            EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO(
                    employee.getName(),
                    employee.getEmail(),
                    employee.getAge(),
                    employee.getDepartment().getName(),
                    employee.getSalary()
            );
            employeeResponseDTOS.add(employeeResponseDTO);
        }
        return employeeResponseDTOS;
    }

    // Paginated Employee
    public Page<EmployeeResponseDTO> getEmployeeByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employees = repository.findAll(pageable);

        Page<EmployeeResponseDTO> employeeResponseDTOS = employees.map(employee -> new EmployeeResponseDTO(
                employee.getName(),
                employee.getEmail(),
                employee.getAge(),
                employee.getDepartment().getName(),
                employee.getSalary()
        ));
        return employeeResponseDTOS;
    }

    // Paginated Employees + Sorting
    public Page<EmployeeResponseDTO> getEmployeesByPageAndSort(
            int page,
            int size,
            String sort,
            String direction) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(
                        Sort.Direction.valueOf(direction.toUpperCase()),
                        sort
                )
        );


        return repository.findAll(pageable)
                .map(employee -> new EmployeeResponseDTO(
                        employee.getName(),
                        employee.getEmail(),
                        employee.getAge(),
                        employee.getDepartment().getName(),
                        employee.getSalary()
                ));
    }

    // Age + Pagination + Sorting
    public Page<EmployeeResponseDTO> getEmployeesByAgeWithPagination(
            int age,
            int page,
            int size,
            String sort,
            String direction) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(
                        Sort.Direction.valueOf(direction.toUpperCase()),
                        sort
                )
        );

        return repository.findByAge(age, pageable)
                .map(employee -> new EmployeeResponseDTO(
                        employee.getName(),
                        employee.getEmail(),
                        employee.getAge(),
                        employee.getDepartment().getName(),
                        employee.getSalary()
                ));
    }

    // Department + Pagination + Sorting
    public Page<EmployeeResponseDTO> getEmployeesByDepartmentWithPagination(
            String department,
            int page,
            int size,
            String sort,
            String direction) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(
                        Sort.Direction.valueOf(direction.toUpperCase()),
                        sort
                )
        );

        return repository.findByDepartment(department, pageable)
                .map(employee -> new EmployeeResponseDTO(
                        employee.getName(),
                        employee.getEmail(),
                        employee.getAge(),
                        employee.getDepartment().getName(),
                        employee.getSalary()
                ));
    }
    // Get Employee Where Salary Is Greater Than Given Salary
    public Page<EmployeeResponseDTO> getEmployeesBySalaryGreaterThan(float minSalary, int page, int size) {

        Pageable pageable1 = PageRequest.of(page, size);
        return repository.findBySalaryGreaterThan(minSalary, pageable1)
                .map(employee -> new EmployeeResponseDTO(
                   employee.getName(),
                   employee.getEmail(),
                   employee.getAge(),
                   employee.getDepartment().getName(),
                   employee.getSalary()
                ));
    }

    // Get Employee By Department,salary,age,page and size
    public Page<EmployeeResponseDTO> getEmployeeByDepartmentSalaryAndAge(Department department,float minSalary, int age, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return repository.findByDepartmentSalaryAndAge(department,minSalary,age,pageable).map(employee -> new EmployeeResponseDTO(
                employee.getName(),
                employee.getEmail(),
                employee.getAge(),
                employee.getDepartment().getName(),
                employee.getSalary()
        ));
    }

    @Transactional
    public void testResult(int idOne, int idTwo) {

        Employee employeeOne = repository.findById(idOne)
                .orElseThrow(() -> new RuntimeException("Employee " + idOne + " Not Found"));

        Employee employeeTwo = repository.findById(idTwo)
                .orElseThrow(() -> new RuntimeException("Employee " + idTwo + " Not Found"));

        // First operation: change employee one's salary
        employeeOne.setSalary(58000.0f);
        repository.saveAndFlush(employeeOne);

       try { // Second operation: deliberately violate the UNIQUE email constraint
           employeeTwo.setEmail(employeeOne.getEmail());
           repository.saveAndFlush(employeeTwo);
       } catch (Exception e){
           System.out.println("Caught exception " + e.getMessage());
       }

       System.out.println("Test Completed");
    }


    //@Transactional
    public void firstEmployee(int idOne, int idTwo){
        Employee employeeFirst = repository.findById(idOne)
                .orElseThrow(() -> new RuntimeException("Employee " + idOne + " Not Found"));

        employeeFirst.setSalary(65000.0f);
        repository.save(employeeFirst);
        System.out.println("First employee updated");


        employeeUpdateService.secondEmployee(idTwo);

    }


}
