package com.jaiylonbabb.uprightcleaningservices.repository;

import com.jaiylonbabb.uprightcleaningservices.entity.Appointment;
import com.jaiylonbabb.uprightcleaningservices.entity.Invoice;
import com.jaiylonbabb.uprightcleaningservices.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByUser(User user);
}
