package com.marcohnp.cursomc.services;

import com.marcohnp.cursomc.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(Pedido pedido);

    void sendEmail(SimpleMailMessage msg);

    void sendOrderConfirmationHtmlEmail(Pedido pedido);

    void sendHtmlEmail(MimeMessage msg);
}
