package com.condominios.sgc.infrastructure.adapter;

import java.util.Map;

import com.condominios.sgc.domain.auxiliar.SesionUsuario;
import com.condominios.sgc.domain.auxiliar.UsuarioAutenticado;
import com.condominios.sgc.domain.exception.AutenticacionException;
import com.condominios.sgc.domain.port.AutenticacionPort;
import com.condominios.sgc.domain.port.UsuarioPort;
import com.condominios.sgc.infrastructure.client.SupabaseClient;

import org.springframework.web.client.HttpClientErrorException;

public class AutenticacionAdapter implements AutenticacionPort {

    private final SupabaseClient supabaseClient;
    private final UsuarioPort usuarioPort;

    public AutenticacionAdapter(SupabaseClient supabaseClient, UsuarioPort usuarioPort) {
        this.supabaseClient = supabaseClient;
        this.usuarioPort = usuarioPort;
    }

    @Override
    public SesionUsuario iniciarSesion(String email, String password) {
        try {
            Map<String, Object> response = supabaseClient.iniciarSesion(email, password);
            return construirSesion(response);
        } catch (HttpClientErrorException e) {
            throw AutenticacionException.credencialesInvalidas();
        }
    }

    @Override
    public void cerrarSesion(String accessToken) {
        supabaseClient.cerrarSesion(accessToken);
    }

    @Override
    public String crearUsuario(String email, String password, String rol) {
        try {
            Map<String, Object> response = supabaseClient.crearUsuario(email, password, rol);
            return (String) response.get("id");
        } catch (HttpClientErrorException e) {
            throw AutenticacionException.errorCreacion(e.getResponseBodyAsString());
        }
    }

    @Override
    public void enviarRecuperacionContrasena(String email) {
        try {
            supabaseClient.enviarRecuperacionContrasena(email);
        } catch (HttpClientErrorException e) {
            throw AutenticacionException.errorAutenticacion(
                "Error al enviar recuperación: " + e.getResponseBodyAsString());
        }
    }

    @Override
    public void restablecerContrasena(String token, String nuevaContrasena) {
        try {
            supabaseClient.actualizarUsuario(token, Map.<String, Object>of("password", nuevaContrasena));
        } catch (HttpClientErrorException e) {
            throw AutenticacionException.errorAutenticacion(
                "Error al restablecer contraseña: " + e.getResponseBodyAsString());
        }
    }

    @Override
    public void cambiarContrasena(String accessToken, String nuevaContrasena) {
        try {
            supabaseClient.actualizarUsuario(accessToken, Map.<String, Object>of("password", nuevaContrasena));
        } catch (HttpClientErrorException e) {
            throw AutenticacionException.errorAutenticacion(
                "Error al cambiar contraseña: " + e.getResponseBodyAsString());
        }
    }

    @Override
    public void actualizarEmail(String token, String nuevoEmail) {
        try {
            supabaseClient.actualizarUsuario(token, Map.<String, Object>of("email", nuevoEmail));
        } catch (HttpClientErrorException e) {
            throw AutenticacionException.errorAutenticacion(
                "Error al actualizar email: " + e.getResponseBodyAsString());
        }
    }

    @Override
    public SesionUsuario refrescarToken(String refreshToken) {
        try {
            Map<String, Object> response = supabaseClient.refrescarToken(refreshToken);
            return construirSesion(response);
        } catch (HttpClientErrorException e) {
            throw AutenticacionException.errorAutenticacion(
                "Error al refrescar token: " + e.getResponseBodyAsString());
        }
    }

    @Override
    public void actualizarEmailAdmin(String usuarioId, String nuevoEmail) {
        try {
            supabaseClient.actualizarEmailAdmin(usuarioId, nuevoEmail);
        } catch (HttpClientErrorException e) {
            throw AutenticacionException.errorAutenticacion(
                "Error al actualizar email: " + e.getResponseBodyAsString());
        }
    }

    private SesionUsuario construirSesion(Map<String, Object> response) {
        String accessToken = (String) response.get("access_token");
        String tokenType = (String) response.get("token_type");
        long expiresIn = ((Number) response.get("expires_in")).longValue();
        long expiresAt = ((Number) response.get("expires_at")).longValue();
        String refreshToken = (String) response.get("refresh_token");
        Map<String, Object> userData = (Map<String, Object>) response.get("user");

        String userId = (String) userData.get("id");
        String email = (String) userData.get("email");

        var usuarioLocal = usuarioPort.findById(userId)
            .orElseThrow(() -> AutenticacionException.usuarioNoRegistrado());

        var usuarioAutenticado = new UsuarioAutenticado(userId, email, usuarioLocal.getRol());

        return new SesionUsuario(
            accessToken,
            tokenType,
            expiresIn,
            expiresAt,
            refreshToken,
            usuarioAutenticado
        );
    }
}
