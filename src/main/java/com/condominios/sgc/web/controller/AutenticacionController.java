package com.condominios.sgc.web.controller;

import com.condominios.sgc.application.dto.command.CambiarContrasenaCommand;
import com.condominios.sgc.application.dto.command.IniciarSesionCommand;
import com.condominios.sgc.application.dto.command.RefrescarTokenCommand;
import com.condominios.sgc.application.dto.command.RestablecerContrasenaCommand;
import com.condominios.sgc.application.usecase.autenticacion.CambiarContrasenaUseCase;
import com.condominios.sgc.application.usecase.autenticacion.CerrarSesionUseCase;
import com.condominios.sgc.application.usecase.autenticacion.ConfirmarCorreoUseCase;
import com.condominios.sgc.application.usecase.autenticacion.IniciarSesionUseCase;
import com.condominios.sgc.application.usecase.autenticacion.RefrescarTokenUseCase;
import com.condominios.sgc.application.usecase.autenticacion.RestablecerContrasenaUseCase;
import com.condominios.sgc.application.usecase.autenticacion.SolicitarReseteoContrasenaUseCase;
import com.condominios.sgc.infrastructure.util.CookieUtil;
import com.condominios.sgc.infrastructure.util.JwtUtil;
import com.condominios.sgc.web.dto.request.CambiarContrasenaRequest;
import com.condominios.sgc.web.dto.request.LoginRequest;
import com.condominios.sgc.web.dto.request.RefreshTokenRequest;
import com.condominios.sgc.web.dto.request.RestablecerContrasenaRequest;
import com.condominios.sgc.web.dto.request.SolicitarReseteoRequest;
import com.condominios.sgc.web.dto.response.LoginResponse;
import com.condominios.sgc.web.dto.response.MeResponse;
import com.condominios.sgc.web.dto.response.MensajeResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AutenticacionController {

    private final IniciarSesionUseCase iniciarSesion;
    private final CerrarSesionUseCase cerrarSesion;
    private final RefrescarTokenUseCase refrescarToken;
    private final CambiarContrasenaUseCase cambiarContrasena;
    private final ConfirmarCorreoUseCase confirmarCorreo;
    private final SolicitarReseteoContrasenaUseCase solicitarReseteo;
    private final RestablecerContrasenaUseCase restablecerContrasena;
    private final CookieUtil cookieUtil;
    private final JwtUtil jwtUtil;

    public AutenticacionController(
            IniciarSesionUseCase iniciarSesion,
            CerrarSesionUseCase cerrarSesion,
            RefrescarTokenUseCase refrescarToken,
            CambiarContrasenaUseCase cambiarContrasena,
            ConfirmarCorreoUseCase confirmarCorreo,
            SolicitarReseteoContrasenaUseCase solicitarReseteo,
            RestablecerContrasenaUseCase restablecerContrasena,
            CookieUtil cookieUtil,
            JwtUtil jwtUtil) {
        this.iniciarSesion = iniciarSesion;
        this.cerrarSesion = cerrarSesion;
        this.refrescarToken = refrescarToken;
        this.cambiarContrasena = cambiarContrasena;
        this.confirmarCorreo = confirmarCorreo;
        this.solicitarReseteo = solicitarReseteo;
        this.restablecerContrasena = restablecerContrasena;
        this.cookieUtil = cookieUtil;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody @Valid LoginRequest request,
            HttpServletResponse response) {
        var command = new IniciarSesionCommand(request.correo(), request.contrasena(), request.recuerdame());
        var result = iniciarSesion.ejecutar(command);
        response.addCookie(cookieUtil.crearAccessTokenCookie(result.token()));
        response.addCookie(cookieUtil.crearRefreshTokenCookie(result.refreshToken(), result.recuerdame()));
        return ResponseEntity.ok(new LoginResponse(result.idUsuario(), result.rol(), result.nombres()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(
            HttpServletRequest request,
            @RequestBody(required = false) RefreshTokenRequest body,
            HttpServletResponse response) {
        String token = cookieUtil.readCookie(request, cookieUtil.getRefreshTokenName())
                .orElseGet(() -> body != null ? body.token() : null);
        if (token == null) {
            return ResponseEntity.badRequest().build();
        }
        var command = new RefrescarTokenCommand(token);
        var result = refrescarToken.ejecutar(command);
        response.addCookie(cookieUtil.crearAccessTokenCookie(result.token()));
        response.addCookie(cookieUtil.crearRefreshTokenCookie(result.refreshToken(), result.recuerdame()));
        return ResponseEntity.ok(new LoginResponse(result.idUsuario(), result.rol(), result.nombres()));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            HttpServletRequest request,
            HttpServletResponse response) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            cerrarSesion.ejecutar(authHeader.substring(7));
        }
        response.addCookie(cookieUtil.eliminarAccessTokenCookie());
        response.addCookie(cookieUtil.eliminarRefreshTokenCookie());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/cambiar-contrasena")
    public ResponseEntity<Void> cambiarContrasena(
            @RequestBody @Valid CambiarContrasenaRequest request,
            Authentication auth) {
        Jwt jwt = (Jwt) auth.getPrincipal();
        Long idUsuario = Long.parseLong(jwt.getSubject());
        cambiarContrasena.ejecutar(new CambiarContrasenaCommand(idUsuario,
                request.contrasenaActual(), request.nuevaContrasena()));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/confirmar-correo")
    public ResponseEntity<MensajeResponse> confirmarCorreo(@RequestParam String token) {
        confirmarCorreo.ejecutar(token);
        return ResponseEntity.ok(new MensajeResponse("Correo confirmado exitosamente"));
    }

    @PostMapping("/solicitar-reseteo")
    public ResponseEntity<MensajeResponse> solicitarReseteo(@RequestBody @Valid SolicitarReseteoRequest request) {
        solicitarReseteo.ejecutar(request.correo());
        return ResponseEntity.ok(new MensajeResponse("Si el correo existe, recibirás un enlace"));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<MeResponse> me(Authentication auth) {
        var usuario = jwtUtil.extraerUsuario((Jwt) auth.getPrincipal());
        return ResponseEntity.ok(new MeResponse(usuario.idUsuario(), usuario.correo(),
                usuario.nombres(), usuario.apellidos(), usuario.rol(), usuario.idCondominio()));
    }

    @PostMapping("/restablecer-contrasena")
    public ResponseEntity<MensajeResponse> restablecerContrasena(
            @RequestBody @Valid RestablecerContrasenaRequest request) {
        var command = new RestablecerContrasenaCommand(request.token(), request.nuevaContrasena());
        restablecerContrasena.ejecutar(command);
        return ResponseEntity.ok(new MensajeResponse("Contraseña restablecida exitosamente"));
    }

}
