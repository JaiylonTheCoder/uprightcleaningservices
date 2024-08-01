package com.jaiylonbabb.uprightcleaningservices.controller;


import com.jaiylonbabb.uprightcleaningservices.entity.Appointment;
import com.jaiylonbabb.uprightcleaningservices.entity.AppointmentForm;
import com.jaiylonbabb.uprightcleaningservices.entity.User;
import com.jaiylonbabb.uprightcleaningservices.repository.AppointmentRepository;
import com.jaiylonbabb.uprightcleaningservices.repository.UserRepository;
import com.jaiylonbabb.uprightcleaningservices.service.AppointmentService;
import com.jaiylonbabb.uprightcleaningservices.service.EmailService;
import com.jaiylonbabb.uprightcleaningservices.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
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
    private EmailService emailService;
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

            return "redirect:/appointmentSuccess";
        }

        if (!unnotifiedAppointments.isEmpty()) {
            model.addAttribute("newAppointmentsAlert", true);
        }
        // Handle case where user is not found or not authenticated
        return "redirect:/login"; // Redirect to login page or handle as needed
    }

    @GetMapping("/appointmentSuccess")
    public String appointmentSuccess() {
        return "appointmentSuccess";
    }

    @PostMapping("/rescheduleAppointment")
    public String rescheduleAppointment(
            @RequestParam Long appointmentId,
            @RequestParam String newDate,
            RedirectAttributes redirectAttributes) {

        try {
            appointmentService.rescheduleAppointment(appointmentId, newDate);
            redirectAttributes.addFlashAttribute("successMessage", "Appointment rescheduled successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "The selected date is not available or appointment not found.");
        }
        return "redirect:/user/profile";
    }


    @GetMapping("/checkDateAvailability")
    @ResponseBody
    public boolean checkDateAvailability(@RequestParam String newDate) {
        return appointmentService.isDateAvailable(newDate);
    }
    @PostMapping("/cancelAppointment")
    public String cancelAppointment(@RequestParam Long appointmentId, @RequestParam Long userId, RedirectAttributes redirectAttributes) {
        // Cancel the appointment
        appointmentService.cancelAppointment(appointmentId);
        redirectAttributes.addFlashAttribute("successMessage", "Appointment canceled successfully.");
        return "redirect:/user/profile";
    }


// Helper method to get the currently logged-in user's email
    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        return null;
    }



    @GetMapping("/checkDateTimeAvailability")
    public ResponseEntity<Boolean> checkDateTimeAvailability(@RequestParam String dateTime) {
        List<Appointment> appointments = appointmentRepository.findByAppointmentDate(dateTime);
        for (Appointment appointment : appointments) {
            if (appointment.getStatus() == null) {
                return ResponseEntity.ok(false); // Date and time are not available
            }
        }
        return ResponseEntity.ok(true); // Date and time are available
    }

}
