package com.condominios.sgc.infrastructure.web.controller;

import com.condominios.sgc.application.dto.command.CerrarSesionCommand;
import com.condominios.sgc.application.dto.command.IniciarSesionCommand;
import com.condominios.sgc.application.dto.command.ObtenerUsuarioActualCommand;
import com.condominios.sgc.application.dto.command.OlvidasteContrasenaCommand;
import com.condominios.sgc.application.dto.command.RefrescarTokenCommand;
import com.condominios.sgc.application.dto.command.RestablecerContrasenaCommand;
import com.condominios.sgc.application.dto.result.UsuarioActualResult;
import com.condominios.sgc.application.port.in.CerrarSesionUseCase;
import com.condominios.sgc.application.port.in.IniciarSesionUseCase;
import com.condominios.sgc.application.port.in.ObtenerUsuarioActualUseCase;
import com.condominios.sgc.application.port.in.OlvidasteContrasenaUseCase;
import com.condominios.sgc.application.port.in.RefrescarTokenUseCase;
import com.condominios.sgc.application.port.in.RestablecerContrasenaUseCase;
import com.condominios.sgc.application.port.out.service.JwtServicePort;
import com.condominios.sgc.infrastructure.security.CookieUtil;
import com.condominios.sgc.infrastructure.security.UsuarioAuth;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IniciarSesionUseCase iniciarSesionUseCase;
    private final CerrarSesionUseCase cerrarSesionUseCase;
    private final RefrescarTokenUseCase refrescarTokenUseCase;
    private final OlvidasteContrasenaUseCase olvidasteContrasenaUseCase;
    private final RestablecerContrasenaUseCase restablecerContrasenaUseCase;
    private final ObtenerUsuarioActualUseCase obtenerUsuarioActualUseCase;
    private final JwtServicePort jwtService;
    private final CookieUtil cookieUtil;

    public AuthController(
            IniciarSesionUseCase iniciarSesionUseCase,
            CerrarSesionUseCase cerrarSesionUseCase,
            RefrescarTokenUseCase refrescarTokenUseCase,
            OlvidasteContrasenaUseCase olvidasteContrasenaUseCase,
            RestablecerContrasenaUseCase restablecerContrasenaUseCase,
            ObtenerUsuarioActualUseCase obtenerUsuarioActualUseCase,
            JwtServicePort jwtService,
            CookieUtil cookieUtil) {
        this.iniciarSesionUseCase = iniciarSesionUseCase;
        this.cerrarSesionUseCase = cerrarSesionUseCase;
        this.refrescarTokenUseCase = refrescarTokenUseCase;
        this.olvidasteContrasenaUseCase = olvidasteContrasenaUseCase;
        this.restablecerContrasenaUseCase = restablecerContrasenaUseCase;
        this.obtenerUsuarioActualUseCase = obtenerUsuarioActualUseCase;
        this.jwtService = jwtService;
        this.cookieUtil = cookieUtil;
    }

    @PostMapping("/iniciar-sesion")
    public ResponseEntity<UsuarioActualResult> iniciarSesion(@RequestBody IniciarSesionCommand command) {
        var resultado = iniciarSesionUseCase.ejecutar(command);

        var accessMaxAge = Duration.between(Instant.now(),
                jwtService.obtenerExpiracion(resultado.tokenAcceso())).toSeconds();
        var refreshMaxAge = Duration.between(Instant.now(),
                jwtService.obtenerExpiracion(resultado.tokenRefresco())).toSeconds();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookieUtil.crearCookieAccessToken(
                        resultado.tokenAcceso(), accessMaxAge))
                .header(HttpHeaders.SET_COOKIE, cookieUtil.crearCookieRefreshToken(
                        resultado.tokenRefresco(), refreshMaxAge))
                .body(resultado.usuarioActual());
    }

    @PostMapping("/cerrar-sesion")
    public ResponseEntity<Void> cerrarSesion(@CookieValue("refresh_token") String refreshToken) {
        cerrarSesionUseCase.ejecutar(new CerrarSesionCommand(refreshToken));
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookieUtil.eliminarCookieAccessToken())
                .header(HttpHeaders.SET_COOKIE, cookieUtil.eliminarCookieRefreshToken())
                .build();
    }

    @PostMapping("/refrescar-token")
    public ResponseEntity<UsuarioActualResult> refrescarToken(@CookieValue("refresh_token") String refreshToken) {
        var resultado = refrescarTokenUseCase.ejecutar(new RefrescarTokenCommand(refreshToken));

        var accessMaxAge = Duration.between(Instant.now(),
                jwtService.obtenerExpiracion(resultado.tokenAcceso())).toSeconds();
        var refreshMaxAge = Duration.between(Instant.now(),
                jwtService.obtenerExpiracion(resultado.tokenRefresco())).toSeconds();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookieUtil.crearCookieAccessToken(
                        resultado.tokenAcceso(), accessMaxAge))
                .header(HttpHeaders.SET_COOKIE, cookieUtil.crearCookieRefreshToken(
                        resultado.tokenRefresco(), refreshMaxAge))
                .body(resultado.usuarioActual());
    }

    @PostMapping("/olvidaste-contrasena")
    public ResponseEntity<Void> olvidasteContrasena(@RequestBody OlvidasteContrasenaCommand command) {
        olvidasteContrasenaUseCase.ejecutar(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/restablecer-contrasena")
    public ResponseEntity<Void> restablecerContrasena(@RequestBody RestablecerContrasenaCommand command) {
        restablecerContrasenaUseCase.ejecutar(command);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/usuario-actual")
    public UsuarioActualResult usuarioActual(@AuthenticationPrincipal UsuarioAuth usuarioAuth) {
        return obtenerUsuarioActualUseCase.ejecutar(
                new ObtenerUsuarioActualCommand(usuarioAuth.id()));
    }
}
