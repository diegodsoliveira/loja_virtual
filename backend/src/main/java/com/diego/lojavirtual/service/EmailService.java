package com.diego.lojavirtual.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Component
@Service
public class EmailService {

    @Value("${app.email.username}")
    private String login;

    @Value("${app.email.password}")
    private String senha;

    @Async
    public void enviarEmailHtml(String assunto, String mensagem, String emailDestino) throws MessagingException, UnsupportedEncodingException {
        Properties properties = new Properties();

        properties.put("mail.smtp.ssl.trust", "*");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls", "true");
        properties.put("mail.smtp.host", "smtp.emailemnuvem.com.br");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(login, senha);
            }
        });

        session.setDebug(true);

        Address[] toUser = InternetAddress.parse(emailDestino);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(login, "Diego Oliveira", "UTF-8"));
        message.setRecipients(Message.RecipientType.TO, toUser);
        message.setSubject(assunto);
        message.setContent(mensagem, "text/html;charset=utf-8");

        Transport.send(message);

    }
}
