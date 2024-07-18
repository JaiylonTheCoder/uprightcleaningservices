package com.jaiylonbabb.uprightcleaningservices.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id", nullable = false)
    private Long id;

//    @Column(name = "name", unique = true, nullable = false)
//    private String name;
    @Enumerated(EnumType.STRING)
    private RoleName name;
}
