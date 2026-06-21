package com.condominios.sgc.infrastructure.adapter.in.web.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.condominios.sgc.application.dto.result.SesionUsuarioResult;
import com.condominios.sgc.application.port.in.ActualizarCorreoUseCase;
import com.condominios.sgc.application.port.in.CambiarContrasenaUseCase;
import com.condominios.sgc.application.port.in.CerrarSesionUseCase;
import com.condominios.sgc.application.port.in.IniciarSesionUseCase;
import com.condominios.sgc.application.port.in.ObtenerUsuarioActualUseCase;
import com.condominios.sgc.application.port.in.OlvidasteContrasenaUseCase;
import com.condominios.sgc.application.port.in.RefrescarTokenUseCase;
import com.condominios.sgc.application.port.in.RestablecerContrasenaUseCase;
import com.condominios.sgc.application.port.in.VerificarEmailUseCase;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.ActualizarCorreoRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.CambiarContrasenaRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.IniciarSesionRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.OlvidasteContrasenaRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.RestablecerContrasenaRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.AutenticacionResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.UsuarioResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.mapper.AutenticacionMapper;
import com.condominios.sgc.infrastructure.util.CookieUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AutenticacionController {

    private final IniciarSesionUseCase iniciarSesion;
    private final RefrescarTokenUseCase refrescarToken;
    private final CerrarSesionUseCase cerrarSesion;
    private final OlvidasteContrasenaUseCase olvidasteContrasena;
    private final RestablecerContrasenaUseCase restablecerContrasena;
    private final ObtenerUsuarioActualUseCase obtenerUsuarioActual;
    private final CambiarContrasenaUseCase cambiarContrasena;
    private final ActualizarCorreoUseCase actualizarCorreo;
    private final VerificarEmailUseCase verificarEmail;
    private final AutenticacionMapper mapper;
    private final CookieUtil cookieUtil;

    public AutenticacionController(
            IniciarSesionUseCase iniciarSesion,
            RefrescarTokenUseCase refrescarToken,
            CerrarSesionUseCase cerrarSesion,
            OlvidasteContrasenaUseCase olvidasteContrasena,
            RestablecerContrasenaUseCase restablecerContrasena,
            ObtenerUsuarioActualUseCase obtenerUsuarioActual,
            CambiarContrasenaUseCase cambiarContrasena,
            ActualizarCorreoUseCase actualizarCorreo,
            VerificarEmailUseCase verificarEmail,
            AutenticacionMapper mapper,
            CookieUtil cookieUtil) {
        this.iniciarSesion = iniciarSesion;
        this.refrescarToken = refrescarToken;
        this.cerrarSesion = cerrarSesion;
        this.olvidasteContrasena = olvidasteContrasena;
        this.restablecerContrasena = restablecerContrasena;
        this.obtenerUsuarioActual = obtenerUsuarioActual;
        this.cambiarContrasena = cambiarContrasena;
        this.actualizarCorreo = actualizarCorreo;
        this.verificarEmail = verificarEmail;
        this.mapper = mapper;
        this.cookieUtil = cookieUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<AutenticacionResponse> login(
            @Valid @RequestBody IniciarSesionRequest request) {
        var resultado = iniciarSesion.ejecutar(
            request.correo(), request.contrasena(), request.recuerdame());
        return conCookies(resultado);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AutenticacionResponse> refresh(HttpServletRequest request) {
        var resultado = refrescarToken.ejecutar(cookieUtil.extraerRefreshToken(request));
        return conCookies(resultado);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        cerrarSesion.ejecutar(cookieUtil.extraerRefreshToken(request));
        var headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookieUtil.limpiarCookieAccessToken().toString());
        headers.add(HttpHeaders.SET_COOKIE, cookieUtil.limpiarCookieRefreshToken().toString());
        return ResponseEntity.ok().headers(headers).build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(
            @Valid @RequestBody OlvidasteContrasenaRequest request) {
        olvidasteContrasena.ejecutar(request.correo());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(
            @Valid @RequestBody RestablecerContrasenaRequest request) {
        restablecerContrasena.ejecutar(request.token(), request.nuevaContrasena());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> me() {
        return ResponseEntity.ok(mapper.toUsuarioResponse(obtenerUsuarioActual.ejecutar()));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/change-password")
    public ResponseEntity<Void> changePassword(
            @Valid @RequestBody CambiarContrasenaRequest request) {
        cambiarContrasena.ejecutar(request.contrasenaActual(), request.nuevaContrasena());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/email")
    public ResponseEntity<Void> email(
            @Valid @RequestBody ActualizarCorreoRequest request) {
        actualizarCorreo.ejecutar(request.nuevoCorreo(), request.contrasena());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify-email")
    public ResponseEntity<Void> verifyEmail(@RequestParam String token) {
        verificarEmail.ejecutar(token);
        return ResponseEntity.ok().build();
    }

    private ResponseEntity<AutenticacionResponse> conCookies(SesionUsuarioResult resultado) {
        var headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE,
            cookieUtil.crearCookieAccessToken(resultado.tokenAcceso(), resultado.expiracionAccesoMs()).toString());
        headers.add(HttpHeaders.SET_COOKIE,
            cookieUtil.crearCookieRefreshToken(resultado.tokenRefresco(), resultado.expiracionRefrescoMs()).toString());
        return ResponseEntity.ok()
            .headers(headers)
            .body(mapper.toAutenticacionResponse(resultado));
    }
}
