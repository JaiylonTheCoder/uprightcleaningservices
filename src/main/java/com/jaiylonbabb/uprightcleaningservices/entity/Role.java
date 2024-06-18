package com.jaiylonbabb.uprightcleaningservices.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    public String name;
    @ManyToMany(mappedBy = "roles")
    private Set<User> user = new HashSet<>();
}
