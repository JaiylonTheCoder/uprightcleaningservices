package com.jaiylonbabb.uprightcleaningservices.service;

import com.jaiylonbabb.uprightcleaningservices.entity.User;
import com.jaiylonbabb.uprightcleaningservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    public UserRepository UserRepository;
    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;

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

    public User findById(Long id) {
        return UserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(User user) {
        return UserRepository.save(user);
    }
}
