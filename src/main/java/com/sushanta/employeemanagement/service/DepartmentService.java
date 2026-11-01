package com.sushanta.employeemanagement.service;


import com.sushanta.employeemanagement.entity.Department;
import com.sushanta.employeemanagement.entity.Employee;
import com.sushanta.employeemanagement.exception.DepartmentNotFoundException;
import com.sushanta.employeemanagement.exception.EmployeeNotFoundException;
import com.sushanta.employeemanagement.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private DepartmentRepository departmentRepository;
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    // Add Department
    public Department createDepartment(Department department){
        return departmentRepository.save(department);
    }

    // Get All Departments
    public List<Department> getAllDepartment(){
        List<Department> department = departmentRepository.findAll();
        return department;
    }
    // Get By ID
    public List<Employee> getDepartmentWithId(long id) {

        Department department = departmentRepository
                .findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException("Department Does Not Exist"));

        return department.getEmployees();
    }

    // Update Department
    public Department updateDepartment(long id, Department department){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);

        if(optionalDepartment.isEmpty()){
            throw new DepartmentNotFoundException("Department Does Not Exist");
        }

        Department updatedDepartment = optionalDepartment.get();

        updatedDepartment.setName(department.getName());
        updatedDepartment.setLocation(department.getLocation());

        departmentRepository.save(updatedDepartment);

        return updatedDepartment;
    }

    // Delete Department
    public String deleteDepartment(long id){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if(optionalDepartment.isEmpty()){
            throw new DepartmentNotFoundException("Department Does Not Exist");
        }
        departmentRepository.delete(optionalDepartment.get());
        return "Department" + optionalDepartment + "Deleted Successfully";
    }
}
