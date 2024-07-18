package com.jaiylonbabb.uprightcleaningservices.repository;

import com.jaiylonbabb.uprightcleaningservices.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByNameContaining(String name);
}
