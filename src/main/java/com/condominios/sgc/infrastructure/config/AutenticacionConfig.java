package com.condominios.sgc.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.condominios.sgc.application.impl.ActualizarCorreoAdminUseCaseImpl;
import com.condominios.sgc.application.impl.ActualizarCorreoUseCaseImpl;
import com.condominios.sgc.application.impl.CambiarContrasenaUseCaseImpl;
import com.condominios.sgc.application.impl.CerrarSesionUseCaseImpl;
import com.condominios.sgc.application.impl.CrearUsuarioUseCaseImpl;
import com.condominios.sgc.application.impl.EnviarRecuperacionContrasenaUseCaseImpl;
import com.condominios.sgc.application.impl.IniciarSesionUseCaseImpl;
import com.condominios.sgc.application.impl.RefrescarTokenUseCaseImpl;
import com.condominios.sgc.application.impl.RestablecerContrasenaAdminUseCaseImpl;
import com.condominios.sgc.application.impl.RestablecerContrasenaUseCaseImpl;
import com.condominios.sgc.application.impl.VerificarCorreoUseCaseImpl;
import com.condominios.sgc.application.usecase.ActualizarCorreoAdminUseCase;
import com.condominios.sgc.application.usecase.ActualizarCorreoUseCase;
import com.condominios.sgc.application.usecase.CambiarContrasenaUseCase;
import com.condominios.sgc.application.usecase.CerrarSesionUseCase;
import com.condominios.sgc.application.usecase.CrearUsuarioUseCase;
import com.condominios.sgc.application.usecase.EnviarRecuperacionContrasenaUseCase;
import com.condominios.sgc.application.usecase.IniciarSesionUseCase;
import com.condominios.sgc.application.usecase.RefrescarTokenUseCase;
import com.condominios.sgc.application.usecase.RestablecerContrasenaAdminUseCase;
import com.condominios.sgc.application.usecase.RestablecerContrasenaUseCase;
import com.condominios.sgc.application.usecase.VerificarCorreoUseCase;
import com.condominios.sgc.domain.port.AutenticacionPort;
import com.condominios.sgc.domain.port.CorreoPort;
import com.condominios.sgc.domain.port.RestablecimientoTokenPort;
import com.condominios.sgc.domain.port.UsuarioPort;
import com.condominios.sgc.domain.port.VerificacionTokenPort;
import com.condominios.sgc.infrastructure.adapter.AutenticacionLocalAdapter;
import com.condominios.sgc.infrastructure.adapter.RestablecimientoTokenAdapter;
import com.condominios.sgc.infrastructure.adapter.VerificacionTokenAdapter;
import com.condominios.sgc.infrastructure.persistence.repository.RestablecimientoTokenRepository;
import com.condominios.sgc.infrastructure.persistence.repository.UsuarioRepository;
import com.condominios.sgc.infrastructure.persistence.repository.VerificacionTokenRepository;
import com.condominios.sgc.infrastructure.security.JwtUtil;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AutenticacionConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtUtil jwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-expiration}") long accessTokenExpiration,
            @Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration) {
        return new JwtUtil(secret, accessTokenExpiration, refreshTokenExpiration);
    }

    @Bean
    public AutenticacionPort autenticacionPort(UsuarioPort usuarioPort, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        return new AutenticacionLocalAdapter(usuarioPort, jwtUtil, passwordEncoder);
    }

    @Bean
    public VerificacionTokenPort verificacionTokenPort(
            VerificacionTokenRepository repository,
            UsuarioRepository usuarioRepository) {
        return new VerificacionTokenAdapter(repository, usuarioRepository);
    }

    @Bean
    public RestablecimientoTokenPort restablecimientoTokenPort(
            RestablecimientoTokenRepository repository,
            UsuarioRepository usuarioRepository) {
        return new RestablecimientoTokenAdapter(repository, usuarioRepository);
    }

    @Bean
    public IniciarSesionUseCase iniciarSesionUseCase(
            AutenticacionPort autenticacionPort,
            UsuarioPort usuarioPort) {
        return new IniciarSesionUseCaseImpl(autenticacionPort, usuarioPort);
    }

    @Bean
    public CerrarSesionUseCase cerrarSesionUseCase(AutenticacionPort autenticacionPort) {
        return new CerrarSesionUseCaseImpl(autenticacionPort);
    }

    @Bean
    public RefrescarTokenUseCase refrescarTokenUseCase(
            AutenticacionPort autenticacionPort,
            UsuarioPort usuarioPort) {
        return new RefrescarTokenUseCaseImpl(autenticacionPort, usuarioPort);
    }

    @Bean
    public CrearUsuarioUseCase crearUsuarioUseCase(
            AutenticacionPort autenticacionPort,
            UsuarioPort usuarioPort,
            CorreoPort correoPort) {
        return new CrearUsuarioUseCaseImpl(autenticacionPort, usuarioPort, correoPort);
    }

    @Bean
    public EnviarRecuperacionContrasenaUseCase enviarRecuperacionContrasenaUseCase(
            UsuarioPort usuarioPort,
            RestablecimientoTokenPort restablecimientoTokenPort,
            CorreoPort correoPort) {
        return new EnviarRecuperacionContrasenaUseCaseImpl(usuarioPort, restablecimientoTokenPort, correoPort);
    }

    @Bean
    public RestablecerContrasenaUseCase restablecerContrasenaUseCase(
            AutenticacionPort autenticacionPort,
            RestablecimientoTokenPort restablecimientoTokenPort) {
        return new RestablecerContrasenaUseCaseImpl(autenticacionPort, restablecimientoTokenPort);
    }

    @Bean
    public CambiarContrasenaUseCase cambiarContrasenaUseCase(
            AutenticacionPort autenticacionPort) {
        return new CambiarContrasenaUseCaseImpl(autenticacionPort);
    }

    @Bean
    public ActualizarCorreoUseCase actualizarCorreoUseCase(
            UsuarioPort usuarioPort,
            VerificacionTokenPort verificacionTokenPort,
            CorreoPort correoPort) {
        return new ActualizarCorreoUseCaseImpl(usuarioPort, verificacionTokenPort, correoPort);
    }

    @Bean
    public RestablecerContrasenaAdminUseCase restablecerContrasenaAdminUseCase(
            AutenticacionPort autenticacionPort) {
        return new RestablecerContrasenaAdminUseCaseImpl(autenticacionPort);
    }

    @Bean
    public ActualizarCorreoAdminUseCase actualizarCorreoAdminUseCase(
            UsuarioPort usuarioPort,
            AutenticacionPort autenticacionPort) {
        return new ActualizarCorreoAdminUseCaseImpl(usuarioPort, autenticacionPort);
    }

    @Bean
    public VerificarCorreoUseCase verificarCorreoUseCase(
            UsuarioPort usuarioPort,
            AutenticacionPort autenticacionPort,
            VerificacionTokenPort verificacionTokenPort) {
        return new VerificarCorreoUseCaseImpl(usuarioPort, autenticacionPort, verificacionTokenPort);
    }
}
