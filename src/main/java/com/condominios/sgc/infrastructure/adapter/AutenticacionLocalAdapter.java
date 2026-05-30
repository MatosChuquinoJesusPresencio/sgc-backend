package com.condominios.sgc.infrastructure.adapter;

import java.time.Instant;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.condominios.sgc.domain.auxiliar.SesionUsuario;
import com.condominios.sgc.domain.auxiliar.UsuarioAutenticado;
import com.condominios.sgc.domain.exception.AutenticacionException;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.port.AutenticacionPort;
import com.condominios.sgc.domain.port.UsuarioPort;
import com.condominios.sgc.infrastructure.security.JwtUtil;

public class AutenticacionLocalAdapter implements AutenticacionPort {

    private final UsuarioPort usuarioPort;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AutenticacionLocalAdapter(UsuarioPort usuarioPort, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.usuarioPort = usuarioPort;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SesionUsuario iniciarSesion(String email, String password) {
        var usuario = usuarioPort.findByCorreo(email)
            .orElseThrow(AutenticacionException::credencialesInvalidas);

        if (!passwordEncoder.matches(password, usuario.getPasswordHash())) {
            throw AutenticacionException.credencialesInvalidas();
        }

        if (!usuario.isActivo()) {
            throw AutenticacionException.errorAutenticacion("Usuario inactivo");
        }

        return generarSesion(usuario);
    }

    @Override
    public void cerrarSesion(String accessToken) {
    }

    @Override
    public String crearUsuario(String email, String password, String rol) {
        return UUID.randomUUID().toString();
    }

    @Override
    public void cambiarContrasena(String accessToken, String nuevaContrasena) {
        var claims = jwtUtil.validateToken(accessToken);
        String userId = claims.getSubject();
        var usuario = usuarioPort.findById(userId)
            .orElseThrow(() -> AutenticacionException.errorAutenticacion("Usuario no encontrado"));
        usuario.asignarPasswordHash(passwordEncoder.encode(nuevaContrasena));
        usuarioPort.save(usuario);
    }

    @Override
    public void actualizarCorreoAdmin(String usuarioId, String nuevoCorreo) {
    }

    @Override
    public SesionUsuario refrescarToken(String refreshToken) {
        var claims = jwtUtil.validateToken(refreshToken);
        String userId = claims.getSubject();
        var usuario = usuarioPort.findById(userId)
            .orElseThrow(() -> AutenticacionException.errorAutenticacion("Usuario no encontrado"));

        if (!usuario.isActivo()) {
            throw AutenticacionException.errorAutenticacion("Usuario inactivo");
        }

        return generarSesion(usuario);
    }

    @Override
    public void eliminarUsuario(String usuarioId) {
        usuarioPort.deleteById(usuarioId);
    }

    @Override
    public void actualizarPasswordAdmin(String usuarioId, String nuevaPassword) {
        var usuario = usuarioPort.findById(usuarioId)
            .orElseThrow(UsuarioException::noEncontrado);
        usuario.asignarPasswordHash(passwordEncoder.encode(nuevaPassword));
        usuarioPort.save(usuario);
    }

    private SesionUsuario generarSesion(com.condominios.sgc.domain.model.UsuarioModel usuario) {
        long now = Instant.now().getEpochSecond();
        long expiresIn = jwtUtil.getAccessTokenExpiration() / 1000;

        String accessToken = jwtUtil.generateAccessToken(
            usuario.getId(), usuario.getCorreo(), usuario.getRol().name());
        String refreshToken = jwtUtil.generateRefreshToken(usuario.getId());

        var usuarioAutenticado = new UsuarioAutenticado(
            usuario.getId(), usuario.getCorreo(), usuario.getRol());

        return new SesionUsuario(
            accessToken,
            refreshToken,
            "Bearer",
            expiresIn,
            now + expiresIn,
            usuarioAutenticado
        );
    }
}
