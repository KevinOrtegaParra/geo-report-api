package com.geo_report_api.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.geo_report_api.services.ifaces.IEmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailServiceImpl implements IEmailService{

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine engine;

    @Override
    public boolean sendMail(String email, String name) {
        boolean sent = false;
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("email", email);

        String htmlContent = engine.process("registration.html", context);
        sendHtmlEmail(email, "Bienvenido a GEO-REPORT", htmlContent);
        sent = true;
        return sent;
    }

    private void sendHtmlEmail(String email, String string, String htmlContent) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("kevin.ortegaap@est.iudigital.edu.co");
            helper.setTo(email);
            helper.setText(htmlContent, true);
            helper.setSubject(string);
            mailSender.send(message);
            log.info("Mensaje enviado exitosamente!");

        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo", e);
        }
    }
    
}
