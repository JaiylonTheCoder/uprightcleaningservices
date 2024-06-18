package com.jaiylonbabb.uprightcleaningservices.controller;

import com.jaiylonbabb.uprightcleaningservices.entity.User;
import com.jaiylonbabb.uprightcleaningservices.service.CustomUserDetailsService;
import com.jaiylonbabb.uprightcleaningservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GeneralController {

    @Autowired
    public UserService userService;
    public CustomUserDetailsService userDetailsService;
    @GetMapping("/index")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

//    @GetMapping("/appointment")
//    public String appointmentForm(){
//        return "createAppointment";
//    }

    @GetMapping("/appointment")
    public String showAppointmentForm(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            String username = userDetails.getUsername();
            User user = userService.UserRepository.findByEmail(username);
            String name = user.getName();
            String phone = user.getPhone();

            model.addAttribute("phone", phone);
            model.addAttribute("name", name);
            model.addAttribute("username", username);
        }
        return "createAppointment";
    }
    @GetMapping("/register")
    public String registerPage(){
        return "register";
    }

}
