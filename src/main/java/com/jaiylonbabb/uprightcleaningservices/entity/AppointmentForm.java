package com.jaiylonbabb.uprightcleaningservices.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Data
public class AppointmentForm {
    private String email; //Username
    private String name;
    private String companyName;
    private String phoneNumber;
    private String address;
    private Set<String> serviceTypes;
    private String appointmentDate;
    private String status;
    private String completionDate;
    private Set<String> additionalServices;
}
