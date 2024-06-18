package com.jaiylonbabb.uprightcleaningservices.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
    private LocalDateTime dateCreated;

//    @CollectionTable(name = "appointment_services", joinColumns = @JoinColumn(name = "appointment_id"))
//    @ElementCollection
//    @Column(name = "service_type")
//    private Set<ServiceType> servicesProvided;

    private Double totalAmount;
}
