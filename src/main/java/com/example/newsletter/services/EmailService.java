package com.example.newsletter.services;

import com.example.newsletter.entity.NewsletterEntity;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


@Service
public class EmailService{
    @Autowired
    private JavaMailSender emailsender;

    public void sendEmail(NewsletterEntity email) throws MessagingException, IOException {
       MimeMessage mimeMessage = emailsender.createMimeMessage();
       MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        String htmlContent = new String(Files.readAllBytes(Paths.get(new ClassPathResource("email-template.html").getURI())), StandardCharsets.UTF_8);

        // Substitui os placeholders
        htmlContent = htmlContent.replace("{{title}}", email.getTitle());
        htmlContent = htmlContent.replace("{{content}}", email.getContent());




        helper.setFrom("majorasbot@gmail.com");
        helper.setTo(email.getEmailUser());
        helper.setSubject(email.getTitle());
        helper.setText(htmlContent, true);
       emailsender.send(mimeMessage);
    }

}

