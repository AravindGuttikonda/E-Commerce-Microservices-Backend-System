package com.ecommerce.notification_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Service
public class EmailGenerator {
    @KafkaListener(topics = "email-topic", groupId = "notificationGroup")
    public void getMessage(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        EmailContent emailContent = objectMapper.readValue(message, EmailContent.class);

        final String fromEmail = emailContent.getFrom(); //requires valid gmail id
        final String password = "xqmyoxhvhqoyxjsz"; // correct password for gmail id
        final String toEmail = emailContent.getTo(); // can be any email id

        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);

        EmailUtils.sendEmail(session, fromEmail, toEmail,emailContent.getSubject(), emailContent.getBody());

    }
}
