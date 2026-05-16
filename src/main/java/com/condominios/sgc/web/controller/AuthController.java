package com.condominios.sgc.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.condominios.sgc.application.dto.CrearUsuarioRequest;
import com.condominios.sgc.domain.exception.AutenticacionException;
import com.condominios.sgc.domain.port.UsuarioPort;
import com.condominios.sgc.application.usecase.ActualizarCorreoAdminUseCase;
import com.condominios.sgc.application.usecase.ActualizarCorreoUseCase;
import com.condominios.sgc.application.usecase.CambiarContrasenaUseCase;
import com.condominios.sgc.application.usecase.CerrarSesionUseCase;
import com.condominios.sgc.application.usecase.CrearUsuarioUseCase;
import com.condominios.sgc.application.usecase.EnviarRecuperacionContrasenaUseCase;
import com.condominios.sgc.application.usecase.IniciarSesionUseCase;
import com.condominios.sgc.application.usecase.RefrescarTokenUseCase;
import com.condominios.sgc.application.usecase.RestablecerContrasenaUseCase;
import com.condominios.sgc.domain.auxiliar.SesionUsuario;
import com.condominios.sgc.infrastructure.util.CookieUtils;
import com.condominios.sgc.web.dto.AuthResponse;
import com.condominios.sgc.web.dto.ChangePasswordRequest;
import com.condominios.sgc.web.dto.ForgotPasswordRequest;
import com.condominios.sgc.web.dto.LoginRequest;
import com.condominios.sgc.web.dto.RefreshTokenRequest;
import com.condominios.sgc.web.dto.ResetPasswordRequest;
import com.condominios.sgc.web.dto.UpdateEmailRequest;
import com.condominios.sgc.web.dto.UsuarioResponse;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioPort usuarioPort;
    private final IniciarSesionUseCase iniciarSesionUseCase;
    private final CerrarSesionUseCase cerrarSesionUseCase;
    private final CrearUsuarioUseCase crearUsuarioUseCase;
    private final EnviarRecuperacionContrasenaUseCase enviarRecuperacionContrasenaUseCase;
    private final RestablecerContrasenaUseCase restablecerContrasenaUseCase;
    private final CambiarContrasenaUseCase cambiarContrasenaUseCase;
    private final ActualizarCorreoUseCase actualizarCorreoUseCase;
    private final ActualizarCorreoAdminUseCase actualizarCorreoAdminUseCase;
    private final RefrescarTokenUseCase refrescarTokenUseCase;

    public AuthController(
            UsuarioPort usuarioPort,
            IniciarSesionUseCase iniciarSesionUseCase,
            CerrarSesionUseCase cerrarSesionUseCase,
            CrearUsuarioUseCase crearUsuarioUseCase,
            EnviarRecuperacionContrasenaUseCase enviarRecuperacionContrasenaUseCase,
            RestablecerContrasenaUseCase restablecerContrasenaUseCase,
            CambiarContrasenaUseCase cambiarContrasenaUseCase,
            ActualizarCorreoUseCase actualizarCorreoUseCase,
            ActualizarCorreoAdminUseCase actualizarCorreoAdminUseCase,
            RefrescarTokenUseCase refrescarTokenUseCase) {
        this.usuarioPort = usuarioPort;
        this.iniciarSesionUseCase = iniciarSesionUseCase;
        this.cerrarSesionUseCase = cerrarSesionUseCase;
        this.crearUsuarioUseCase = crearUsuarioUseCase;
        this.enviarRecuperacionContrasenaUseCase = enviarRecuperacionContrasenaUseCase;
        this.restablecerContrasenaUseCase = restablecerContrasenaUseCase;
        this.cambiarContrasenaUseCase = cambiarContrasenaUseCase;
        this.actualizarCorreoUseCase = actualizarCorreoUseCase;
        this.actualizarCorreoAdminUseCase = actualizarCorreoAdminUseCase;
        this.refrescarTokenUseCase = refrescarTokenUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request,
            HttpServletResponse response) {
        SesionUsuario sesion = iniciarSesionUseCase.ejecutar(request.email(), request.password());
        var usuario = usuarioPort.findById(sesion.usuario().id())
            .orElseThrow(AutenticacionException::usuarioNoRegistrado);
        response.addHeader("Set-Cookie",
            CookieUtils.crearCookieJwt(sesion.accessToken(), sesion.expiresIn()).toString());
        return ResponseEntity.ok(AuthResponse.fromSesion(sesion, UsuarioResponse.fromModel(usuario)));
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('SUPER_ADMINISTRADOR')")
    public ResponseEntity<UsuarioResponse> register(@RequestBody CrearUsuarioRequest request) {
        return ResponseEntity.ok(
            UsuarioResponse.fromModel(crearUsuarioUseCase.ejecutar(request)));
    }

    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> logout(
            @AuthenticationPrincipal Jwt jwt,
            HttpServletResponse response) {
        cerrarSesionUseCase.ejecutar(jwt.getTokenValue());
        response.addHeader("Set-Cookie",
            CookieUtils.limpiarCookieJwt().toString());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(
            @RequestBody RefreshTokenRequest request,
            HttpServletResponse response) {
        SesionUsuario sesion = refrescarTokenUseCase.ejecutar(request.refreshToken());
        var usuario = usuarioPort.findById(sesion.usuario().id())
            .orElseThrow(AutenticacionException::usuarioNoRegistrado);
        response.addHeader("Set-Cookie",
            CookieUtils.crearCookieJwt(sesion.accessToken(), sesion.expiresIn()).toString());
        return ResponseEntity.ok(AuthResponse.fromSesion(sesion, UsuarioResponse.fromModel(usuario)));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        enviarRecuperacionContrasenaUseCase.ejecutar(request.email());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequest request) {
        restablecerContrasenaUseCase.ejecutar(
            request.token(), request.password());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> changePassword(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody ChangePasswordRequest request) {
        cambiarContrasenaUseCase.ejecutar(
            jwt.getTokenValue(), request.password());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/email")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UsuarioResponse> updateEmail(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody UpdateEmailRequest request) {
        return ResponseEntity.ok(
            UsuarioResponse.fromModel(
                actualizarCorreoUseCase.ejecutar(
                    jwt.getSubject(), jwt.getTokenValue(), request.email())));
    }

    @PatchMapping("/admin/{id}/email")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<UsuarioResponse> updateEmailAdmin(
            @PathVariable String id,
            @RequestBody UpdateEmailRequest request) {
        return ResponseEntity.ok(
            UsuarioResponse.fromModel(
                actualizarCorreoAdminUseCase.ejecutar(id, request.email())));
    }
}
