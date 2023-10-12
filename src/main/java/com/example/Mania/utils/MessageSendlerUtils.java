package com.example.Mania.utils;

import com.example.Mania.dtos.RegistrationUserDto;
import com.example.Mania.dtos.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class MessageSendlerUtils {
    @Value("${email.from}")
    private String from;
    private final Session session;

    public String generateCode() {
        String validCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {
            int randomIndex = random.nextInt(validCharacters.length());
            code.append(validCharacters.charAt(randomIndex));
        }

        return code.toString();
    }
    public void processMessage(String messageText , RegistrationUserDto userDto) {
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(userDto.getEmail())});
            message.setSubject("Them of message");
            message.setText(messageText);
            Transport.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
