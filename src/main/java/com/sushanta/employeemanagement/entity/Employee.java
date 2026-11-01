package com.sushanta.employeemanagement.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String name;

    @Email
    private String email;

    @Min(value = 18,message = "Age Must Be Greater Than 18")
    private int age;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Positive
    private float salary;

    public Employee(String name, String email, int age, Department department, float salary) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.department = department;
        this.salary = salary;
    }
}
