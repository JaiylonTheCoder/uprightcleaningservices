package com.jaiylonbabb.uprightcleaningservices.repository;

import com.jaiylonbabb.uprightcleaningservices.entity.Role;
import com.jaiylonbabb.uprightcleaningservices.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
//    Role findByName(String name);
    Role findByName(RoleName name);
}
