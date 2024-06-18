package com.jaiylonbabb.uprightcleaningservices.service;

import com.jaiylonbabb.uprightcleaningservices.entity.Invoice;
import com.jaiylonbabb.uprightcleaningservices.entity.User;
import com.jaiylonbabb.uprightcleaningservices.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private AppointmentService appointmentService;

    public void save(Invoice invoice) {

    }

    public List<Invoice> getInvoicesForCurrentUser(User currentUser) {
        return invoiceRepository.findByUser(currentUser);
    }
}
