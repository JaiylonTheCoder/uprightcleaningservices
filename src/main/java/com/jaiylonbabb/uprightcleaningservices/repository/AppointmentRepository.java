package com.jaiylonbabb.uprightcleaningservices.repository;

import com.jaiylonbabb.uprightcleaningservices.entity.Appointment;
import com.jaiylonbabb.uprightcleaningservices.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByUserId(Long userId);
    List<Appointment> findByUser(User user);
    List<Appointment> findByNotifiedFalse();
    List<Appointment> findByAppointmentDate(String appointmentDate);
}
