package com.jaiylonbabb.uprightcleaningservices.controller;

import com.jaiylonbabb.uprightcleaningservices.entity.*;
import com.jaiylonbabb.uprightcleaningservices.repository.RoleRepository;
import com.jaiylonbabb.uprightcleaningservices.repository.ServiceTypeRepository;
import com.jaiylonbabb.uprightcleaningservices.service.AppointmentService;
import com.jaiylonbabb.uprightcleaningservices.service.InvoiceService;
import com.jaiylonbabb.uprightcleaningservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    private final RoleRepository roleRepository;
    private final AppointmentService appointmentService;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private ServiceTypeRepository serviceTypeRepository;


    @Autowired
    public UserController(UserService userService, RoleRepository roleRepository,
                          AppointmentService appointmentService) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.appointmentService = appointmentService;
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes, @RequestParam("confirmPassword") String confirmPassword) {
        if (!user.getPassword().equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Passwords do not match");
            return "redirect:/register";
        }

        // Assign the default role "USER"
        Role defaultRole = roleRepository.findByName("ROLE_USER");
        System.out.println("Default Role " + defaultRole);//made change here
        user.setRoles(Collections.singletonList(defaultRole));

        userService.registerClient(user);
        redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please log in with your credentials.");
        return "redirect:/login";
    }


    @GetMapping("/profile")
    public String showProfile(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        User currentUser = userService.UserRepository.findByEmail(email);

        // Fetch appointments for the current user
        List<Appointment> appointments = appointmentService.getAppointmentsForCurrentUser(currentUser);
        Long userId = userService.UserRepository.findByEmail(email).getId();
        String phoneNumber = userService.UserRepository.findByEmail(email).getPhone();
        String address = userService.UserRepository.findByEmail(email).getAddress();
        String companyName = userService.UserRepository.findByEmail(email).getCompanyName();
        List<ServiceType> serviceTypes = serviceTypeRepository.findAll();

        //Fetch the invoices for the current user
        List<Invoice> invoices = invoiceService.getInvoicesForCurrentUser(currentUser);
        model.addAttribute("invoices", invoices);
        model.addAttribute("serviceTypes", serviceTypes);
        model.addAttribute("appointments", appointments);
        model.addAttribute("email", email);
        model.addAttribute("userId", userId);
        model.addAttribute("phoneNumber", phoneNumber);
        model.addAttribute("address", address);
        model.addAttribute("companyName", companyName);

        return "userProfile";
    }

    @GetMapping("/fetchAppointments")
    @ResponseBody
    public List<Appointment> fetchAppointmentsForCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return appointmentService.getAppointmentsForCurrentUser(currentUser);
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@ModelAttribute("user") User user, Model model) {
        User updatedUser = userService.updateUser(user);
        model.addAttribute("user", updatedUser);
        return "profile"; // Return the view name for the profile page
    }




}
