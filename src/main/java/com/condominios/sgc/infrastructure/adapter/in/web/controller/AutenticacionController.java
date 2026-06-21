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
import com.condominios.sgc.infrastructure.adapter.out.util.CookieUtil;

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

    @PostMapping("/iniciar-sesion")
    public ResponseEntity<AutenticacionResponse> iniciarSesion(
            @Valid @RequestBody IniciarSesionRequest request) {
        var resultado = iniciarSesion.ejecutar(
            request.correo(), request.contrasena(), request.recuerdame());
        return conCookies(resultado);
    }

    @PostMapping("/refrescar")
    public ResponseEntity<AutenticacionResponse> refrescar(HttpServletRequest request) {
        var resultado = refrescarToken.ejecutar(cookieUtil.extraerRefreshToken(request));
        return conCookies(resultado);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/cerrar-sesion")
    public ResponseEntity<Void> cerrarSesion(HttpServletRequest request) {
        cerrarSesion.ejecutar(cookieUtil.extraerRefreshToken(request));
        var headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookieUtil.limpiarCookieAccessToken().toString());
        headers.add(HttpHeaders.SET_COOKIE, cookieUtil.limpiarCookieRefreshToken().toString());
        return ResponseEntity.ok().headers(headers).build();
    }

    @PostMapping("/olvide-contrasena")
    public ResponseEntity<Void> olvidasteContrasena(
            @Valid @RequestBody OlvidasteContrasenaRequest request) {
        olvidasteContrasena.ejecutar(request.correo());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/restablecer-contrasena")
    public ResponseEntity<Void> restablecerContrasena(
            @Valid @RequestBody RestablecerContrasenaRequest request) {
        restablecerContrasena.ejecutar(request.token(), request.nuevaContrasena());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/yo")
    public ResponseEntity<UsuarioResponse> yo() {
        return ResponseEntity.ok(mapper.toUsuarioResponse(obtenerUsuarioActual.ejecutar()));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/cambiar-contrasena")
    public ResponseEntity<Void> cambiarContrasena(
            @Valid @RequestBody CambiarContrasenaRequest request) {
        cambiarContrasena.ejecutar(request.contrasenaActual(), request.nuevaContrasena());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/correo")
    public ResponseEntity<Void> actualizarCorreo(
            @Valid @RequestBody ActualizarCorreoRequest request) {
        actualizarCorreo.ejecutar(request.nuevoCorreo(), request.contrasena());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verificar-email")
    public ResponseEntity<Void> verificarEmail(@RequestParam String token) {
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
