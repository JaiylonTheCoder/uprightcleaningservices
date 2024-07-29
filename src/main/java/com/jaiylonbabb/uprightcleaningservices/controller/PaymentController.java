package com.jaiylonbabb.uprightcleaningservices.controller;

import com.jaiylonbabb.uprightcleaningservices.entity.Invoice;
import com.jaiylonbabb.uprightcleaningservices.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pay")
public class PaymentController {
    @Autowired
    private InvoiceService invoiceService;
    @GetMapping("/checkout/{invoiceId}")
    public String checkout(@PathVariable Long invoiceId, Model model) {
        Invoice invoice = invoiceService.findById(invoiceId); // Fetch the invoice details
        model.addAttribute("invoice", invoice);
        return "checkout";
    }

    @PostMapping("/checkout/{invoiceId}")
    public String processPayment(@PathVariable Long invoiceId, Model model) {
        Invoice invoice = invoiceService.findById(invoiceId);
        // Simulate payment processing
        invoice.setInvoiceStatus("paid"); // Update status to PAID
        invoiceService.save(invoice);
        model.addAttribute("message", "Payment was successful!");
        return "paymentSuccess"; // Return a success view
    }
}
