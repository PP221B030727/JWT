package com.example.Mania.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Configuration
public class MessageSendlerConfig {
    @Value("${email.from}")
    private String from;
    @Value("${email.to}")
    private String to;
    @Value("${email.host}")
    private String host;
    @Value("${email.smtpPort}")
    private String smtpPort;
    @Value("${email.from.password}")
    private String password;

    @Bean
    public Properties getProperties(){
        Properties properties = new Properties();
        properties.put("mail.smtp.host",host);
        properties.put("mail.smtp.port",smtpPort);
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.auth","true");
        return properties;
    }

    @Bean
    public Session getSession(){
        Session session = Session.getInstance(getProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from , password);
            }
        });
        return session;
    }
}
