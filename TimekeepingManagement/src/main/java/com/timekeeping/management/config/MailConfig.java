package com.timekeeping.management.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;
@Configuration
public class MailConfig {
    @Value("${mail.server.host}")
    private String host;

    @Value("${mail.server.port}")
    private Integer port;

    @Value("${mail.server.username}")
    private String email;

    @Value("${mail.server.password}")
    private String password;

    @Value("${mail.server.isSSL}")
    private String isSSl;

    @Bean
    public JavaMailSender getJavaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(email);
        mailSender.setPassword(password);
        mailSender.setDefaultEncoding("UTF-8");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol","smtp");
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable","true");
//        props.put("mail.smtp.ssl.enable",isSSl);
        props.put("mail.debug","true");

        return mailSender;
    }
}
