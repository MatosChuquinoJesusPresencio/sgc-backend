package com.condominios.sgc.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.condominios.sgc.application.dto.CrearUsuarioRequest;
import com.condominios.sgc.application.service.AutenticacionService;
import com.condominios.sgc.application.service.UsuarioService;
import com.condominios.sgc.infrastructure.util.SecurityUtils;
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

    private final AutenticacionService autenticacionService;
    private final UsuarioService usuarioService;

    public AuthController(AutenticacionService autenticacionService, UsuarioService usuarioService) {
        this.autenticacionService = autenticacionService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request,
            HttpServletResponse response) {
        var completa = autenticacionService.iniciarSesion(request.email(), request.password());
        response.addHeader("Set-Cookie",
            CookieUtils.crearCookieJwt(completa.sesion().accessToken(), completa.sesion().expiresIn()).toString());
        return ResponseEntity.ok(AuthResponse.fromSesion(completa.sesion(), UsuarioResponse.fromModel(completa.usuario())));
    }

    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> logout(
            @AuthenticationPrincipal Jwt jwt,
            HttpServletResponse response) {
        autenticacionService.cerrarSesion(jwt.getTokenValue());
        response.addHeader("Set-Cookie",
            CookieUtils.limpiarCookieJwt().toString());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(
            @RequestBody RefreshTokenRequest request,
            HttpServletResponse response) {
        var completa = autenticacionService.refrescarToken(request.refreshToken());
        response.addHeader("Set-Cookie",
            CookieUtils.crearCookieJwt(completa.sesion().accessToken(), completa.sesion().expiresIn()).toString());
        return ResponseEntity.ok(AuthResponse.fromSesion(completa.sesion(), UsuarioResponse.fromModel(completa.usuario())));
    }

    @PostMapping("/register")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<UsuarioResponse> register(@RequestBody CrearUsuarioRequest request) {
        return ResponseEntity.ok(
            UsuarioResponse.fromModel(usuarioService.crear(request, SecurityUtils.obtenerRolAutenticado())));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        usuarioService.enviarRecuperacionContrasena(request.email(), "");
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequest request) {
        usuarioService.restablecerContrasena(request.token(), request.password());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> changePassword(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody ChangePasswordRequest request) {
        autenticacionService.cambiarContrasena(
            SecurityUtils.obtenerIdUsuario(), request.currentPassword(), request.newPassword());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/email")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UsuarioResponse> updateEmail(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody UpdateEmailRequest request) {
        return ResponseEntity.ok(
            UsuarioResponse.fromModel(
                usuarioService.actualizarCorreo(
                    SecurityUtils.obtenerIdUsuario(), request.email(), jwt.getTokenValue())));
    }

    @GetMapping("/verificar-email")
    public ResponseEntity<UsuarioResponse> verificarEmail(
            @RequestParam String token) {
        return ResponseEntity.ok(
            UsuarioResponse.fromModel(
                usuarioService.verificarCorreo(token)));
    }
}
