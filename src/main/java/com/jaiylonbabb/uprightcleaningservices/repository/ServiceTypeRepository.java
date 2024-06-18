package com.jaiylonbabb.uprightcleaningservices.repository;

import com.jaiylonbabb.uprightcleaningservices.entity.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {
    ServiceType findByName(String name);
}
