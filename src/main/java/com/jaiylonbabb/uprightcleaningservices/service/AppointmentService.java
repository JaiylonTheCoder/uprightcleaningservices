package com.jaiylonbabb.uprightcleaningservices.service;

import com.jaiylonbabb.uprightcleaningservices.entity.*;
import com.jaiylonbabb.uprightcleaningservices.repository.AppointmentRepository;
import com.jaiylonbabb.uprightcleaningservices.repository.ServiceTypeRepository;
import com.jaiylonbabb.uprightcleaningservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    public UserService userService;
    @Autowired
    private EmailService emailService;

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
    }


    public List<Appointment> getAppointmentsForCurrentUser(User currentUser) {
        // Get the current authenticated user
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User currentUser = (User) authentication.getPrincipal();

        // Retrieve appointments for the current user
        return appointmentRepository.findByUser(currentUser);
    }

    public List<Appointment> getAppointmentsByUserId(Long userId) {
        return appointmentRepository.findByUserId(userId);
    }

    public void save(AppointmentForm appointmentForm, Long userId) {
        Appointment appointment = new Appointment();

        User user = userRepository.getReferenceById(userId);
        appointment.setUser(user);

        // Set other form fields
        appointment.setEmail(appointmentForm.getEmail());
        appointment.setName(appointmentForm.getName());
        appointment.setCompanyName(appointmentForm.getCompanyName());
        appointment.setPhoneNumber(appointmentForm.getPhoneNumber());
        appointment.setAddress(appointmentForm.getAddress());

        Set<ServiceType> serviceTypes = appointmentForm.getServiceTypes().stream()
                .map(serviceTypeRepository::findByName)
                .collect(Collectors.toSet());
        appointment.setServiceTypes(serviceTypes);
        appointment.setAdditionalServices(appointmentForm.getAdditionalServices());
        appointment.setAppointmentDate(appointmentForm.getAppointmentDate());
        appointment.setStatus(appointmentForm.getStatus());
        appointment.setCompletionDate(appointmentForm.getCompletionDate());
        appointment.setAdditionalServices(appointmentForm.getAdditionalServices());

        // Send confirmation email
        String subject = "Appointment Confirmation";
        String body = "Dear " + user.getName() + ",\n\n" +
                "Your appointment has been successfully created.\n\n" +
                "Appointment Details:\n" +
                "Name: " + appointment.getName() + "\n" +
                "Date: " + appointment.getAppointmentDate() + "\n" +
                "Service Types: " + appointment.getServiceTypes().toString() + "\n\n" +
                "Thank you for choosing Upright Cleaning services.";
        emailService.sendAppointmentConfirmation(user.getEmail(), subject, body);

        // Send notification email to the application email (admin)
        String adminEmail = "jaiylonbabb21@gmail.com";
        String adminSubject = "New Appointment Created";
        String adminBody = "A new appointment has been created by " + user.getName() + ".\n\n" +
                "Appointment Details:\n" +
                "Name: " + appointment.getName() + "\n" +
                "Email: " + appointment.getEmail() + "\n" +
                "Company: " + appointment.getCompanyName() + "\n" +
                "Phone: " + appointment.getPhoneNumber() + "\n" +
                "Address: " + appointment.getAddress() + "\n" +
                "Date: " + appointment.getAppointmentDate() + "\n" +
                "Service Types: " + appointment.getServiceTypes().toString() + "\n\n" +
                "Please log in to the admin panel for more details.";
        emailService.sendAppointmentNotification(adminEmail, adminSubject, adminBody);
        appointment.setNotified(false);
        appointmentRepository.save(appointment);
    }


}
