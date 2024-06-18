package com.jaiylonbabb.uprightcleaningservices.controller;

import com.jaiylonbabb.uprightcleaningservices.dto.AuthenticationRequest;
import com.jaiylonbabb.uprightcleaningservices.entity.User;
import com.jaiylonbabb.uprightcleaningservices.repository.UserRepository;
import com.jaiylonbabb.uprightcleaningservices.security.JwtUtil;
import com.jaiylonbabb.uprightcleaningservices.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService userService;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserRepository userRepository;
//    @Bean
//    public AuthenticationManager authenticationManager() throws Exception {
//        return authentication -> authenticationManager.authenticate(authentication);
//    }



//    @PostMapping("auth/login")
//    public String login(@RequestParam String username, @RequestParam String password) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
//
//        Authentication authentication = authenticationManager.authenticate(token);
//        System.out.println("In login controller");
//
//        if (authentication.isAuthenticated()) {
//            // Authentication successful, redirect to home page or any other secured page
//            System.out.println("User logged in: " + authentication.getName()); // Print the authenticated username
//            return "/register";
//        } else {
//            // Authentication failed, redirect back to login page with error message
//            return "redirect:/login?error";
//        }
//    }
//
//
//    @PostMapping("/loginV2")
//    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
//        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        final UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());
//        if (user != null) {
//            String token = jwtUtil.generateToken(user);
//            return ResponseEntity.ok(token);
//        }
//        return ResponseEntity.status(400).body("Bad request");
//    }

    @GetMapping("/profile")
    public String viewProfile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userService.UserRepository.findByEmail(username);
        Long userId = user.getId(); // Assuming getId() returns the user's ID
        model.addAttribute("userId", userId);
        return "profile";
    }


}
