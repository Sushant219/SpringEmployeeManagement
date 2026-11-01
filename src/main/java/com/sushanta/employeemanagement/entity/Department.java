package com.sushanta.employeemanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String location;

    @JsonIgnore
    @OneToMany(mappedBy = "department",
            cascade = CascadeType.REMOVE )
    private List<Employee> employees;

    public Department(String name, String location) {
        this.name = name;
        this.location = location;
    }
}
