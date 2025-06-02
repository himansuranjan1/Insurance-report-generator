package com.demospring.utills;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class Mailsndr {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Sends an email with a file attachment.
     *
     * @param toEmail   Recipient's email address
     * @param fileType  Type of file ("pdf" or "excel")
     * @param fileData  File content in byte array format
     */
    public void sendFile(String toEmail, String fileType, byte[] fileData) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("Insurance Report");

            helper.setText(
                "Dear User,\n\n" +
                "Please find attached your insurance report in " + fileType.toUpperCase() + " format.\n\n" +
                "Regards,\nInsurance Report System"
            );

            ByteArrayResource attachment = new ByteArrayResource(fileData);

            String fileName = fileType.equalsIgnoreCase("excel") ? "insurance_report.xlsx" : "insurance_report.pdf";
            String mimeType = fileType.equalsIgnoreCase("pdf")
                    ? "application/pdf"
                    : "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

            helper.addAttachment(fileName, attachment, mimeType);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while sending email with attachment", e);
        }
    }
}
