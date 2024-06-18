package com.jaiylonbabb.uprightcleaningservices.controller;

import com.jaiylonbabb.uprightcleaningservices.entity.Appointment;
import com.jaiylonbabb.uprightcleaningservices.entity.Invoice;
import com.jaiylonbabb.uprightcleaningservices.entity.ServiceType;
import com.jaiylonbabb.uprightcleaningservices.repository.AppointmentRepository;
import com.jaiylonbabb.uprightcleaningservices.repository.InvoiceRepository;
import com.jaiylonbabb.uprightcleaningservices.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class InvoiceController {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private EmailService emailService;
    public InvoiceController(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping("/invoice/form/{appointmentId}")
    public String showInvoiceForm(@PathVariable Long appointmentId, Model model) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentId);
        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
            model.addAttribute("appointment", appointment);
            model.addAttribute("userId", appointment.getUser().getId());
            return "invoiceForm";
        } else {
            // Handle the case where the appointment is not found
            return "redirect:/error"; // Or some error handling page
        }
    }

    @PostMapping("/invoice/save")
    public String saveInvoice(@RequestParam Long appointmentId,
                              @RequestParam(required = false) Long userId,
                              @RequestParam List<Double> prices,
                              Model model) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentId);
        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();

            // Update appointment status
            appointment.setStatus("complete");
            appointmentRepository.save(appointment);
            System.out.println("Appointment saved to database");
            // Calculate total amount
            double totalAmount = prices.stream().mapToDouble(Double::doubleValue).sum();

            // Create and save the invoice
            Invoice invoice = new Invoice();
            invoice.setAppointment(appointment);
            invoice.setTotalAmount(totalAmount);
            invoice.setUser(appointment.getUser());
            invoice.setDateCreated(LocalDateTime.now());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDate = invoice.getDateCreated().format(formatter);

            // Format the list of services provided
            String services = appointment.getServiceTypes().stream()
                    .map(ServiceType::getName)
                    .collect(Collectors.joining(", "));

            // Send confirmation email
            String subject = "Invoice Confirmation";
            String body = "Dear " + appointment.getUser().getName() + ",\n\n" +
                    "Your invoice has been successfully created.\n\n" +
                    "Invoice Details:\n" +
                    "Invoice ID: #" + invoice.getId() + "\n" +
                    "Creation Date: " + formattedDate + "\n" +
                    "Services Provided: " + services + "\n" +
                    "Total Amount: $" + invoice.getTotalAmount() + "\n\n" +
                    "Thank you for choosing Upright Cleaning services.";

            emailService.sendAppointmentConfirmation(appointment.getUser().getEmail(), subject, body);
            System.out.println("Email sent");
            invoiceRepository.save(invoice);
            System.out.println("Invoice saved");
            // Redirect or show success page
            return "redirect:/admin/dashboard";
        } else {
            // Handle the case where the appointment is not found
            return "redirect:/error";
        }
    }

    @GetMapping("/invoice/view/{id}")
    public String viewInvoice(@PathVariable Long id, Model model) {
        Optional<Invoice> invoiceOpt = invoiceRepository.findById(id);
        if (invoiceOpt.isPresent()) {
            Invoice invoice = invoiceOpt.get();
            model.addAttribute("invoice", invoice);
            return "invoiceCard";
        } else {
            return "redirect:/error";
        }
    }
}
