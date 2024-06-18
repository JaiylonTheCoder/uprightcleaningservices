package com.jaiylonbabb.uprightcleaningservices.entity;

import com.jaiylonbabb.uprightcleaningservices.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    @Autowired
    private RoleRepository roleRepository;

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @PostConstruct
    public void init() {
        createRoleIfNotFound("USER");
        createRoleIfNotFound("ADMIN");
    }

    private void createRoleIfNotFound(String roleName) {
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            logger.info("Creating role: {}", roleName);
            role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        } else {
            logger.info("Role {} already exists", roleName);
        }
    }
}
