package com.amalitech.securesail.amalisecuresail.global.services;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender emailSender;
    @Value("${spring.mail.username}")
    private String senderAddress;

    private final Configuration freeMarkerConfig;

    @Async
    public CompletableFuture<Void> sendEmail(String subject, String message, String recipientAddress) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(recipientAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setFrom(senderAddress);
        log.info("this is personalEmail message {}", simpleMailMessage);

        try {
            emailSender.send(simpleMailMessage);
            future.complete(null);
        } catch (Exception e) {
            log.error("Email failed to be sent", e);
            future.completeExceptionally(e);
        }
        return future;
    }

    public String buildEmailContent(String html, Map<String, Object> model) throws IOException, TemplateException {
        Template template = freeMarkerConfig.getTemplate(html);
        StringWriter writer = new StringWriter();
        template.process(model, writer);
        return writer.toString();
    }

    public CompletableFuture<Void> sendEmail(String template, String recipient, String subject, Map<String, Object> model) throws TemplateException, IOException, MessagingException {
        CompletableFuture<Void> future = new CompletableFuture<>();
        String emailContent = buildEmailContent(template, model);
        MimeMessage message = emailSender.createMimeMessage();
        message.setContent(emailContent, "text/html");
        message.setSubject(subject);
        message.setFrom(senderAddress);
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        try {
            emailSender.send(message);
            future.complete(null);
        } catch (MailException e) {
            future.completeExceptionally(e);
        }
        return future;
    }
}