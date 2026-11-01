package com.sushanta.employeemanagement.repository;

import com.sushanta.employeemanagement.dto.DepartmentAvarageSalaryDTO;
import com.sushanta.employeemanagement.entity.Department;
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

    List<Employee> findByDepartment(Department department);

    List<Employee> findByAge(int age);

    List<Employee> findBySalary(float salary);


    @Query("""
            SELECT new com.sushanta.employeemanagement.dto.DepartmentAvarageSalaryDTO(e.department.name,AVG(e.salary))
            FROM Employee e
            GROUP BY e.department.name
            """)
    List<DepartmentAvarageSalaryDTO> findAvarageSalaryByDepartment();


    @Query("""
            SELECT e
            FROM Employee e
            WHERE e.department = :departmentA 
            OR e.department = :departmentB
            """)
    List<Employee> findByDepartmentAOrDepartmentB(@Param("departmentA") Department departmentA, @Param("departmentB") Department departmentB);


    @Query("SELECT e FROM Employee e WHERE e.department =:department AND e.age >= :age")
    List<Employee> findByDepartmentAndAgeGreaterThanEqual(@Param("department") Department department, @Param("age") int age);


    @Query("SELECT e FROM Employee e WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Employee> findByNameContainingIgnoreCase(@Param("name") String name);


    @Query("SELECT e FROM Employee e WHERE e.salary > :salary ")
    List<Employee> findBySalaryGreaterThan(@Param("salary") float salary);


    @Query("SELECT e FROM Employee e WHERE e.salary >= :minSalary AND e.salary <= :maxSalary ")
    List<Employee> findBySalaryBetween(@Param("minSalary") float minSalary, @Param("maxSalary") float maxSalary);


    @Query("""
            SELECT e
            FROM Employee e
            WHERE e.department = :department
            ORDER BY e.salary DESC
            """)
    List<Employee> findByDepartmentOrderBySalaryDesc(
            @Param("department") Department department
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


    @Query("SELECT e FROM Employee e WHERE e.salary > :minSalary")
    Page<Employee> findBySalaryGreaterThan(@Param("minSalary") float minSalary, Pageable pageable);


    // Select Employee By Department, Salary, Age, Page And Size
    @Query("""
            SELECT e
            FROM Employee e
            WHERE e.department IS NULL OR e.department = :department
            AND e.salary IS NULL OR e.salary > :minSalary
            AND e.age IS NULL OR e.age = :age
            """)
    Page<Employee> findByDepartmentSalaryAndAge(@Param("department") Department department, @Param("minSalary") float minSalary, @Param("age") int age, Pageable pageable);

}
