package com.sushanta.employeemanagement.repository;

import com.sushanta.employeemanagement.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    //-----------------
    // Validation
    //-----------------

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Integer id);


    // ===========================
// FILTERS
// ===========================

    List<Employee> findByDepartment(String department);

    List<Employee> findByAge(int age);

    List<Employee> findBySalary(float salary);


    @Query("SELECT e FROM Employee e WHERE e.department =:department AND e.age >= :age")
    List<Employee> findByDepartmentAndAgeGreaterThanEqual(@Param("department") String department, @Param("age") int age);

    List<Employee> findByNameContainingIgnoreCase(String name);


    @Query("SELECT e FROM Employee e WHERE e.salary > :salary ")
    List<Employee> findBySalaryGreaterThan(@Param("salary") float salary);

    List<Employee> findBySalaryBetween(
            float minSalary,
            float maxSalary
    );

    List<Employee> findByDepartmentOrderBySalaryDesc(
            String department
    );

// ===========================
// PAGINATION
// ===========================

    Page<Employee> findByAge(
            int age,
            Pageable pageable
    );

    Page<Employee> findByDepartment(
            String department,
            Pageable pageable
    );
}
