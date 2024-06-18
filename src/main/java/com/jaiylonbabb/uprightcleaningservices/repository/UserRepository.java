package com.jaiylonbabb.uprightcleaningservices.repository;

import com.jaiylonbabb.uprightcleaningservices.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findByEmail(String email);

    List<User> findAll();
//    User findByUsername(String username);

//    Optional<User> findById(Long id);

//    User findById(Long id);

}
