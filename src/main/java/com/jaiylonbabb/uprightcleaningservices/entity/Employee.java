package com.jaiylonbabb.uprightcleaningservices.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private String fullName;
    private String email;
    private String password;
    private String address;
    private String phone;
//    @ManyToMany(mappedBy = "employees")
//    private Set<Appointment> appointments = new HashSet<>();
}
