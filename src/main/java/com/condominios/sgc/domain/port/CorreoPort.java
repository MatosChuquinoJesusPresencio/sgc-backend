package com.condominios.sgc.domain.port;

public interface CorreoPort {
    void sendVerificationEmail(String recipient, String token);
    void sendPasswordResetEmail(String recipient, String token);
    void sendWelcomeEmail(String recipient, String names, String password);
}
