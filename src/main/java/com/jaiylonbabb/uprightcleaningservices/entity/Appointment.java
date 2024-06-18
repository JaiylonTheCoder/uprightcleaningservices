package com.jaiylonbabb.uprightcleaningservices.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT")
    private Long appointmentId;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    private String name;
    private String companyName;

//    @ManyToMany
//    @JoinTable(
//            name = "appointment_employee",
//            joinColumns = @JoinColumn(name = "appointment_id"),
//            inverseJoinColumns = @JoinColumn(name = "employee_id")
//    )
//    private Set<Employee> employees = new HashSet<>();

    private String email;
    private String phoneNumber;
    private String address;

//    @ElementCollection(targetClass = ServiceType.class)
//    @CollectionTable(name = "appointment_services", joinColumns = @JoinColumn(name = "appointment_id"))
//    @Enumerated(EnumType.STRING)
//    @Column(name = "service_type")
//    private Set<ServiceType> serviceTypes;

    @ManyToMany
    @JoinTable(
            name = "appointment_services",
            joinColumns = @JoinColumn(name = "appointment_id"),
            inverseJoinColumns = @JoinColumn(name = "service_type_id")
    )
    private Set<ServiceType> serviceTypes;

    @Column(name = "additional_service")
    @ElementCollection
    private Set<String> additionalServices;

    private boolean notified;

    @OneToOne(mappedBy = "appointment")
    private Invoice invoice;

    private String appointmentDate;
    private String completionDate;
    private String status;
}
