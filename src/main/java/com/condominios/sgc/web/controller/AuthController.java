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

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.util.Map;
import java.util.UUID;

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
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response) {
        var completa = autenticacionService.iniciarSesion(request.email(), request.password());
        response.addHeader("Set-Cookie",
            CookieUtils.crearCookieAccessToken(completa.sesion().accessToken(), completa.sesion().expiresIn()).toString());
        response.addHeader("Set-Cookie",
            CookieUtils.crearCookieRefreshToken(completa.sesion().refreshToken(), 604800000L).toString());
        return ResponseEntity.ok(AuthResponse.fromSesion(completa.sesion(), UsuarioResponse.fromModel(completa.usuario())));
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UsuarioResponse> me() {
        Long id = SecurityUtils.obtenerIdUsuario();
        return ResponseEntity.ok(UsuarioResponse.fromModel(usuarioService.obtener(id)));
    }

    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> logout(
            @AuthenticationPrincipal Jwt jwt,
            HttpServletResponse response) {
        autenticacionService.cerrarSesion(jwt.getTokenValue());
        response.addHeader("Set-Cookie",
            CookieUtils.limpiarCookieAccessToken().toString());
        response.addHeader("Set-Cookie",
            CookieUtils.limpiarCookieRefreshToken().toString());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(
            @Valid @RequestBody(required = false) RefreshTokenRequest body,
            HttpServletRequest request,
            HttpServletResponse response) {
        String refreshToken = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refresh_token".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }
        if (refreshToken == null && body != null) {
            refreshToken = body.refreshToken();
        }
        var completa = autenticacionService.refrescarToken(refreshToken);
        response.addHeader("Set-Cookie",
            CookieUtils.crearCookieAccessToken(completa.sesion().accessToken(), completa.sesion().expiresIn()).toString());
        response.addHeader("Set-Cookie",
            CookieUtils.crearCookieRefreshToken(completa.sesion().refreshToken(), 604800000L).toString());
        return ResponseEntity.ok(AuthResponse.fromSesion(completa.sesion(), UsuarioResponse.fromModel(completa.usuario())));
    }

    @PostMapping("/register")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<UsuarioResponse> register(@Valid @RequestBody CrearUsuarioRequest request) {
        return ResponseEntity.ok(
            UsuarioResponse.fromModel(usuarioService.crear(request, SecurityUtils.obtenerRolAutenticado())));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        String token = UUID.randomUUID().toString();
        usuarioService.enviarRecuperacionContrasena(request.email(), token);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        usuarioService.restablecerContrasena(request.token(), request.password());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> changePassword(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody ChangePasswordRequest request) {
        autenticacionService.cambiarContrasena(
            SecurityUtils.obtenerIdUsuario(), request.currentPassword(), request.newPassword());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/email")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UsuarioResponse> updateEmail(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody UpdateEmailRequest request) {
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
