package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.auxiliar.SesionUsuario;

public interface AutenticacionPort {
    SesionUsuario login(String email, String password);
    void logout(String accessToken);
    String createUser(String email, String password, String rol);
    void changePassword(Long userId, String newPassword);
    void updateEmail(Long userId, String newEmail);
    SesionUsuario refreshToken(String refreshToken);
    void deleteUser(Long userId);
}
