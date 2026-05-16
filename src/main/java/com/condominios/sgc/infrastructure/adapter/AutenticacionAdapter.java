package com.condominios.sgc.infrastructure.adapter;

import java.util.Map;

import com.condominios.sgc.domain.auxiliar.SesionUsuario;
import com.condominios.sgc.domain.auxiliar.UsuarioAutenticado;
import com.condominios.sgc.domain.exception.AutenticacionException;
import com.condominios.sgc.domain.port.AutenticacionPort;
import com.condominios.sgc.domain.port.UsuarioPort;
import com.condominios.sgc.infrastructure.client.SupabaseClient;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

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
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED || e.getStatusCode() == HttpStatus.FORBIDDEN) {
                throw AutenticacionException.credencialesInvalidas();
            }
            throw AutenticacionException.errorAutenticacion(
                "Error de autenticación: " + e.getResponseBodyAsString());
        } catch (HttpServerErrorException e) {
            throw AutenticacionException.errorAutenticacion("Error del servidor de autenticación");
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
        } catch (HttpServerErrorException e) {
            throw AutenticacionException.errorAutenticacion("Error del servidor de autenticación");
        }
    }

    @Override
    public void enviarRecuperacionContrasena(String email) {
        try {
            supabaseClient.enviarRecuperacionContrasena(email);
        } catch (HttpClientErrorException e) {
            throw AutenticacionException.errorAutenticacion(
                "Error al enviar recuperación: " + e.getResponseBodyAsString());
        } catch (HttpServerErrorException e) {
            throw AutenticacionException.errorAutenticacion("Error del servidor de autenticación");
        }
    }

    @Override
    public void restablecerContrasena(String token, String nuevaContrasena) {
        try {
            supabaseClient.actualizarUsuario(token, Map.<String, Object>of("password", nuevaContrasena));
        } catch (HttpClientErrorException e) {
            throw AutenticacionException.errorAutenticacion(
                "Error al restablecer contraseña: " + e.getResponseBodyAsString());
        } catch (HttpServerErrorException e) {
            throw AutenticacionException.errorAutenticacion("Error del servidor de autenticación");
        }
    }

    @Override
    public void cambiarContrasena(String accessToken, String nuevaContrasena) {
        try {
            supabaseClient.actualizarUsuario(accessToken, Map.<String, Object>of("password", nuevaContrasena));
        } catch (HttpClientErrorException e) {
            throw AutenticacionException.errorAutenticacion(
                "Error al cambiar contraseña: " + e.getResponseBodyAsString());
        } catch (HttpServerErrorException e) {
            throw AutenticacionException.errorAutenticacion("Error del servidor de autenticación");
        }
    }

    @Override
    public void actualizarCorreo(String token, String nuevoCorreo) {
        try {
            supabaseClient.actualizarUsuario(token, Map.<String, Object>of("email", nuevoCorreo));
        } catch (HttpClientErrorException e) {
            throw AutenticacionException.errorAutenticacion(
                "Error al actualizar correo: " + e.getResponseBodyAsString());
        } catch (HttpServerErrorException e) {
            throw AutenticacionException.errorAutenticacion("Error del servidor de autenticación");
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
        } catch (HttpServerErrorException e) {
            throw AutenticacionException.errorAutenticacion("Error del servidor de autenticación");
        }
    }

    @Override
    public void actualizarCorreoAdmin(String usuarioId, String nuevoCorreo) {
        try {
            supabaseClient.actualizarEmailAdmin(usuarioId, nuevoCorreo);
        } catch (HttpClientErrorException e) {
            throw AutenticacionException.errorAutenticacion(
                "Error al actualizar correo: " + e.getResponseBodyAsString());
        } catch (HttpServerErrorException e) {
            throw AutenticacionException.errorAutenticacion("Error del servidor de autenticación");
        }
    }

    @Override
    public void eliminarUsuario(String usuarioId) {
        try {
            supabaseClient.eliminarUsuario(usuarioId);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() != 404) {
                throw AutenticacionException.errorAutenticacion(
                    "Error al eliminar usuario: " + e.getResponseBodyAsString());
            }
        } catch (HttpServerErrorException e) {
            throw AutenticacionException.errorAutenticacion("Error del servidor de autenticación");
        }
    }

    private SesionUsuario construirSesion(Map<String, Object> response) {
        String accessToken = (String) response.get("access_token");
        String tokenType = (String) response.get("token_type");
        Object expiresInObj = response.get("expires_in");
        Object expiresAtObj = response.get("expires_at");
        String refreshToken = (String) response.get("refresh_token");
        Map<String, Object> userData = (Map<String, Object>) response.get("user");

        if (accessToken == null || tokenType == null || expiresInObj == null
            || expiresAtObj == null || userData == null) {
            throw AutenticacionException.errorAutenticacion("Respuesta de autenticación inválida");
        }

        long expiresIn = ((Number) expiresInObj).longValue();
        long expiresAt = ((Number) expiresAtObj).longValue();

        String userId = (String) userData.get("id");
        String email = (String) userData.get("email");

        if (userId == null) {
            throw AutenticacionException.errorAutenticacion("ID de usuario no encontrado en respuesta");
        }

        var usuarioLocal = usuarioPort.findById(userId)
            .orElseThrow(AutenticacionException::usuarioNoRegistrado);

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
