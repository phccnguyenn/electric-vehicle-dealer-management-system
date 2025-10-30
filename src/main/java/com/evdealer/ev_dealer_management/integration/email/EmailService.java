package com.evdealer.ev_dealer_management.integration.email;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
    void sendEmailWithAttachment(String to, String subject, String body, byte[] attachment, String filename);
}
