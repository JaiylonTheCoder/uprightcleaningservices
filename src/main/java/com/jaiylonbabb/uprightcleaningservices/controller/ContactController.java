package com.jaiylonbabb.uprightcleaningservices.controller;

import com.jaiylonbabb.uprightcleaningservices.entity.ContactForm;
import com.jaiylonbabb.uprightcleaningservices.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactController {
    @Autowired
    private EmailService emailService;
//    @PostMapping("/contact")
//    public String submitContactForm(ContactForm contactForm) {
//        sendEmail(contactForm.getName(), contactForm.getMessage());
//        return "redirect:/contact?success";
//    }

    @PostMapping("/contact")
    public String submitContactForm(ContactForm contactForm, RedirectAttributes redirectAttributes) {
        // Send email using EmailService
        String subject = "New Contact Form Submission";
        String body = "From: " + contactForm.getName() + " <" + contactForm.getEmail() + ">\n\n" + contactForm.getMessage();
        emailService.sendAppointmentConfirmation("jaiylonbabb21@gmail.com", subject, body);
        redirectAttributes.addFlashAttribute("successMessage", "Your message has been sent successfully!");
        return "redirect:/index";
    }
}
