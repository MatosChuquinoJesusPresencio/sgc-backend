package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.auxiliar.LoginCompleta;
import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.domain.auxiliar.SesionUsuario;
import com.condominios.sgc.domain.auxiliar.UsuarioAutenticado;
import com.condominios.sgc.domain.exception.AutenticacionException;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.port.AutenticacionPort;
import com.condominios.sgc.infrastructure.persistence.mapper.UsuarioMapper;
import com.condominios.sgc.infrastructure.util.JwtUtil;
import com.condominios.sgc.infrastructure.persistence.repository.UsuarioRepository;

import io.jsonwebtoken.Claims;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AutenticacionAdapter implements AutenticacionPort {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AutenticacionAdapter(UsuarioRepository usuarioRepository,
                                JwtUtil jwtUtil,
                                PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginCompleta login(String email, String password, boolean rememberMe) {
        var usuario = usuarioRepository.findByCorreo(email)
            .orElseThrow(AutenticacionException::credencialesInvalidas);

        if (!passwordEncoder.matches(password, usuario.getPasswordHash())) {
            throw AutenticacionException.credencialesInvalidas();
        }

        var userId = String.valueOf(usuario.getId());
        var accessToken = jwtUtil.generateAccessToken(userId, usuario.getCorreo(), usuario.getRol().name());
        var refreshExpiration = rememberMe ? jwtUtil.getRememberMeRefreshExpiration() : jwtUtil.getRefreshTokenExpiration();
        var refreshToken = jwtUtil.generateRefreshToken(userId, refreshExpiration);

        long now = System.currentTimeMillis();
        var sesion = new SesionUsuario(
            accessToken,
            refreshToken,
            "Bearer",
            jwtUtil.getAccessTokenExpiration(),
            now + jwtUtil.getAccessTokenExpiration(),
            new UsuarioAutenticado(userId, usuario.getCorreo(), usuario.getRol())
        );
        return new LoginCompleta(sesion, UsuarioMapper.toModel(usuario));
    }

    @Override
    public void logout(String accessToken) {
    }

    @Override
    public String createUser(String email, String password, String rol) {
        var entity = new com.condominios.sgc.infrastructure.persistence.entity.UsuarioEntity();
        entity.setCorreo(email);
        entity.setPasswordHash(passwordEncoder.encode(password));
        entity.setRol(Rol.valueOf(rol));
        entity.setActivo(true);
        entity.setCorreoVerificado(false);
        var saved = usuarioRepository.save(entity);
        return String.valueOf(saved.getId());
    }

    @Override
    public void changePassword(Long userId, String newPassword) {
        var usuario = usuarioRepository.findById(userId)
            .orElseThrow(UsuarioException::noEncontrado);
        usuario.setPasswordHash(passwordEncoder.encode(newPassword));
        usuarioRepository.save(usuario);
    }

    @Override
    public void updateEmail(Long userId, String newEmail) {
        var usuario = usuarioRepository.findById(userId)
            .orElseThrow(UsuarioException::noEncontrado);
        usuario.setCorreoPendiente(newEmail);
        usuarioRepository.save(usuario);
    }

    @Override
    public LoginCompleta refreshToken(String refreshToken) {
        Claims claims = jwtUtil.validateToken(refreshToken);
        var userId = Long.valueOf(claims.getSubject());
        var usuario = usuarioRepository.findById(userId)
            .orElseThrow(UsuarioException::noEncontrado);

        var newAccessToken = jwtUtil.generateAccessToken(
            String.valueOf(usuario.getId()), usuario.getCorreo(), usuario.getRol().name());
        var newRefreshToken = jwtUtil.generateRefreshToken(String.valueOf(usuario.getId()));

        long now = System.currentTimeMillis();
        var sesion = new SesionUsuario(
            newAccessToken,
            newRefreshToken,
            "Bearer",
            jwtUtil.getAccessTokenExpiration(),
            now + jwtUtil.getAccessTokenExpiration(),
            new UsuarioAutenticado(String.valueOf(usuario.getId()), usuario.getCorreo(), usuario.getRol())
        );
        return new LoginCompleta(sesion, UsuarioMapper.toModel(usuario));
    }

    @Override
    public void deleteUser(Long userId) {
        var usuario = usuarioRepository.findById(userId)
            .orElseThrow(UsuarioException::noEncontrado);
        usuarioRepository.delete(usuario);
    }
}
