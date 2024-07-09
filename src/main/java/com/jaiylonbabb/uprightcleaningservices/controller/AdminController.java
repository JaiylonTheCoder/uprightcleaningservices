package com.jaiylonbabb.uprightcleaningservices.controller;

import com.jaiylonbabb.uprightcleaningservices.entity.Appointment;
import com.jaiylonbabb.uprightcleaningservices.entity.Invoice;
import com.jaiylonbabb.uprightcleaningservices.repository.AppointmentRepository;
import com.jaiylonbabb.uprightcleaningservices.repository.InvoiceRepository;
import com.jaiylonbabb.uprightcleaningservices.service.AppointmentService;
import com.jaiylonbabb.uprightcleaningservices.service.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private final AppointmentService appointmentService;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    public AdminController(AppointmentService appointmentService, AppointmentRepository appointmentRepository) {
        this.appointmentService = appointmentService;
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping("/dashboard")
    public String adminAppointments(Model model) {
        List<Appointment> appointments = appointmentRepository.findAll();
        List<Invoice> invoices = invoiceRepository.findAll();
        model.addAttribute("invoices", invoices);
        model.addAttribute("appointments", appointments);
        return "adminDashboard";
    }

//    @PostMapping("saveInvoice")
//    public String saveInvoice(@ModelAttribute("appointment") Appointment appointment) {
//
//    }
//    @GetMapping("/appointments")
//    public List<Appointment> getAllAppointments() {
//        return appointmentService.getAllAppointments();
//    }

//    @GetMapping("/appointments")
//    public String showAppointments(Model model) {
//        System.out.println("In showAppointments");
//        List<Appointment> appointments = appointmentService.getAllAppointments();
//        System.out.println("Appointments "+ appointments);
//        model.addAttribute("appointments", appointments);
//        return "adminDashboard";
//    }

//    @GetMapping("/appointments")
//    public String adminAppointments(Model model) {
//        List<Appointment> appointments = appointmentRepository.findAll();
//        model.addAttribute("appointments", appointments);
//        return "adminDashboard";
//    }
}
