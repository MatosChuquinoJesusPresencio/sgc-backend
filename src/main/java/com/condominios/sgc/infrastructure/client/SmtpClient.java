package com.condominios.sgc.infrastructure.client;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpClient {

    private final JavaMailSender mailSender;
    private final String from;

    public SmtpClient(JavaMailSender mailSender, String from) {
        this.mailSender = mailSender;
        this.from = from;
    }

    public void enviar(String destinatario, String asunto, String cuerpo) {
        var mensaje = new SimpleMailMessage();
        mensaje.setFrom(from);
        mensaje.setTo(destinatario);
        mensaje.setSubject(asunto);
        mensaje.setText(cuerpo);
        mailSender.send(mensaje);
    }
}
