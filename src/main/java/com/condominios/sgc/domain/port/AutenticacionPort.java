package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.auxiliar.SesionUsuario;

public interface AutenticacionPort {

    SesionUsuario iniciarSesion(String email, String password);

    void cerrarSesion(String accessToken);

    String crearUsuario(String email, String password, String rol);

    void enviarRecuperacionContrasena(String email);

    void restablecerContrasena(String token, String nuevaContrasena);

    void cambiarContrasena(String accessToken, String nuevaContrasena);

    void actualizarCorreo(String token, String nuevoCorreo);

    void actualizarCorreoAdmin(String usuarioId, String nuevoCorreo);

    SesionUsuario refrescarToken(String refreshToken);

    void eliminarUsuario(String usuarioId);
}
