package com.example.newsletter.controllers;

import com.example.newsletter.entity.NewsletterEntity;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import com.example.newsletter.services.EmailService;

import java.io.IOException;

@RestController
@RequestMapping("/newsletter")
public class NewsletterController {

    @Autowired
    private EmailService service;

    @PostMapping("/send")
    private ResponseEntity<String> sendEmail(@RequestBody NewsletterEntity email){
        try {
            service.sendEmail(email);
            return ResponseEntity.ok("Email enviado para: " + email.getNameUser());
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao enviar o e-mail.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Endpoint está funcionando!");
    }
}
