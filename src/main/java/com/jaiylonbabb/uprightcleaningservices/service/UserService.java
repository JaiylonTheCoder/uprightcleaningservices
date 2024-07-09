package com.jaiylonbabb.uprightcleaningservices.service;

import com.jaiylonbabb.uprightcleaningservices.entity.Role;
import com.jaiylonbabb.uprightcleaningservices.entity.User;
import com.jaiylonbabb.uprightcleaningservices.repository.RoleRepository;
import com.jaiylonbabb.uprightcleaningservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {
    @Autowired
    public UserRepository UserRepository;
    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    public void registerClient(User user) {
        saveClient(user);
    }

    //Create client
    private void saveClient(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setName(user.getName());
        user.setEmail(user.getEmail());
        user.setAddress(user.getAddress());
        user.setPhone(user.getPhone());
        UserRepository.save(user);
    }

    public void assignDefaultRole(User user) {
        Role userRole = roleRepository.findByName("USER");
        if (userRole != null) {
            user.getRoles().add(userRole);
        } else {
            throw new RuntimeException("Default role USER not found");
        }
    }
    public User findById(Long id) {
        return UserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(User user) {
        return UserRepository.save(user);
    }
}
