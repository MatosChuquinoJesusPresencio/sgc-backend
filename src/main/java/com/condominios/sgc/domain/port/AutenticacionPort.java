package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.auxiliar.LoginCompleta;

public interface AutenticacionPort {
    LoginCompleta login(String email, String password);
    void logout(String accessToken);
    String createUser(String email, String password, String rol);
    void changePassword(Long userId, String newPassword);
    void updateEmail(Long userId, String newEmail);
    LoginCompleta refreshToken(String refreshToken);
    void deleteUser(Long userId);
}
