package com.jaiylonbabb.uprightcleaningservices.service;

import com.jaiylonbabb.uprightcleaningservices.entity.CustomUserDetails;
import com.jaiylonbabb.uprightcleaningservices.entity.User;
import com.jaiylonbabb.uprightcleaningservices.entity.Role;
import com.jaiylonbabb.uprightcleaningservices.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
   private UserRepository userRepository;

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        logger.debug("Attempting to load user by email: {}", username);
//
//        User user = userRepository.findByEmail(username);
//        if (user == null) {
//            logger.error("User not found with email: {}", username);
//            throw new UsernameNotFoundException("User not found with email: " + username);
//        }
//
//        logger.debug("User found: {}", user);
//        return new CustomUserDetails(user);
//    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), user.getAuthorities());
//        return new CustomUserDetails(user);
    }

    public void printLoggedInUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                CustomUserDetails userDetails = (CustomUserDetails) principal;
                User user = userDetails.getUser();
                String email = user.getEmail();
                Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

                // Print user details to the console
                logger.info("Logged in user email: {}", email);
                logger.info("Logged in user roles: {}", authorities);
            } else {
                logger.info("No logged in user details available.");
            }
        } else {
            logger.info("No user is currently authenticated.");
        }
    }

    public UserPrincipal getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserPrincipal)authentication.getPrincipal();
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return ((CustomUserDetails) authentication.getPrincipal()).getUser();
    }
    private Collection<? extends GrantedAuthority> getAuthorities(List<Role> roles) {
        // Add "ROLE_" prefix to each role name
        Collection<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());

        logger.debug("Authorities: {}", authorities);

        return authorities;
    }
}
