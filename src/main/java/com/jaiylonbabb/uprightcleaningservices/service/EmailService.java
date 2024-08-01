package com.jaiylonbabb.uprightcleaningservices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;
    public void sendAppointmentConfirmation(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom(fromEmail);

        mailSender.send(message);
    }

    // Method to send appointment notification to admin
    public void sendAppointmentNotification(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom(fromEmail);

        mailSender.send(message);
    }

    public void sendBlogPostNotification(List<String> toEmails, String subject, String body) {
        for (String toEmail : toEmails) {
            sendAppointmentConfirmation(toEmail, subject, body);
        }
    }

    public void sendRescheduleConfirmation(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom(fromEmail);

        mailSender.send(message);
    }

    public void sendRescheduleNotificationToAdmin(String adminEmail, String userName, Long appointmentId, String newDate) {
        String subject = "Appointment Rescheduled";
        String body = String.format("The appointment with ID %d has been rescheduled by %s to %s.", appointmentId, userName, newDate);

        sendRescheduleConfirmation(adminEmail, subject, body);
    }
}
