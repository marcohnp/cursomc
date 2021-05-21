package com.marcohnp.cursomc.config;

import com.marcohnp.cursomc.services.EmailService;
import com.marcohnp.cursomc.services.SmtpEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class ProdConfig {

    @Bean
    public EmailService emailService() {
        return new SmtpEmailService();
    }
}