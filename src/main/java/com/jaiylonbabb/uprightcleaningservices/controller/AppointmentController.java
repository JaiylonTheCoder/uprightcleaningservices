package com.jaiylonbabb.uprightcleaningservices.controller;


import com.jaiylonbabb.uprightcleaningservices.entity.Appointment;
import com.jaiylonbabb.uprightcleaningservices.entity.AppointmentForm;
import com.jaiylonbabb.uprightcleaningservices.entity.User;
import com.jaiylonbabb.uprightcleaningservices.repository.AppointmentRepository;
import com.jaiylonbabb.uprightcleaningservices.repository.UserRepository;
import com.jaiylonbabb.uprightcleaningservices.service.AppointmentService;
import com.jaiylonbabb.uprightcleaningservices.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class AppointmentController {
    private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    private UserService userService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping("/appointments")
    public String getUserAppointments(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userDetails;
        List<Appointment> appointments = appointmentService.getAppointmentsByUserId(user.getId());
        model.addAttribute("appointments", appointments);
        return "adminDashboard";
    }

    @PostMapping("/createAppointment")
    public String createAppointment(@ModelAttribute("appointmentForm") AppointmentForm appointmentForm, Model model) {
        // Get the currently logged-in user's email
        String userEmail = getCurrentUserEmail();
        List<Appointment> unnotifiedAppointments = appointmentRepository.findByNotifiedFalse();

        // Fetch the user from the database using their email
        User user = userRepository.findByEmail(userEmail);

        if (user != null) {
            Long userId = user.getId();
            appointmentService.save(appointmentForm, userId);

            return "redirect:/appointmentSuccess"; // Redirect to a success page
        }

        if (!unnotifiedAppointments.isEmpty()) {
            model.addAttribute("newAppointmentsAlert", true);
        }
        // Handle case where user is not found or not authenticated
        return "redirect:/login"; // Redirect to login page or handle as needed
    }
// Helper method to get the currently logged-in user's email
    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        return null; // Handle as needed if user is not found or not authenticated
    }

}
