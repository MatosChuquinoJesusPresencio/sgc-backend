package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.command.*;
import com.condominios.sgc.application.dto.result.*;
import com.condominios.sgc.domain.auxiliar.TipoToken;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.AutenticacionPort;
import com.condominios.sgc.domain.port.CorreoPort;
import com.condominios.sgc.domain.port.TokenPort;
import com.condominios.sgc.domain.port.UsuarioPort;
import com.condominios.sgc.domain.exception.TokenException;
import com.condominios.sgc.domain.exception.UsuarioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AutenticacionService {

    private final AutenticacionPort autenticacionPort;
    private final TokenPort tokenPort;
    private final UsuarioPort usuarioPort;
    private final CorreoPort correoPort;

    public AutenticacionService(AutenticacionPort autenticacionPort,
                                TokenPort tokenPort,
                                UsuarioPort usuarioPort,
                                CorreoPort correoPort) {
        this.autenticacionPort = autenticacionPort;
        this.tokenPort = tokenPort;
        this.usuarioPort = usuarioPort;
        this.correoPort = correoPort;
    }

    public IniciarSesionResult iniciarSesion(IniciarSesionCommand comando) {
        UsuarioModel usuario = usuarioPort.obtenerPorCorreo(comando.correo())
                .orElseThrow(UsuarioException::noEncontrado);

        if (!autenticacionPort.verificarContrasena(comando.contrasena(), usuario.getContrasena()))
            throw UsuarioException.credencialesInvalidas();

        var accessToken = tokenPort.generarToken(TipoToken.ACCESS, usuario.getId(), comando.recuerdame());
        var refreshToken = tokenPort.generarToken(TipoToken.REFRESH, usuario.getId(), comando.recuerdame());

        return new IniciarSesionResult(
                accessToken.getToken(),
                refreshToken.getToken(),
                usuario.getId(),
                usuario.getCorreo(),
                usuario.getNombres(),
                usuario.getApellidos(),
                usuario.getRol().name());
    }

    public RefrescarTokenResult refrescarToken(RefrescarTokenCommand comando) {
        Long idUsuario = autenticacionPort.validarToken(comando.refreshToken())
                .orElseThrow(TokenException::tokenExpirado);

        boolean recuerdame = autenticacionPort.esRecuerdame(comando.refreshToken());
        autenticacionPort.invalidarToken(comando.refreshToken());

        var accessToken = tokenPort.generarToken(TipoToken.ACCESS, idUsuario, recuerdame);
        var refreshToken = tokenPort.generarToken(TipoToken.REFRESH, idUsuario, recuerdame);

        return new RefrescarTokenResult(accessToken.getToken(), refreshToken.getToken(), recuerdame);
    }

    public void cerrarSesion(CerrarSesionCommand comando) {
        autenticacionPort.invalidarToken(comando.refreshToken());
    }

    public void olvidasteContrasena(OlvidasteContrasenaCommand comando) {
        usuarioPort.obtenerPorCorreo(comando.correo()).ifPresent(usuario -> {
            var token = tokenPort.generarToken(TipoToken.REESTABLECIMIENTO, usuario.getId());
            correoPort.enviarReseteoContrasena(usuario.getCorreo(), usuario.getNombres(), token.getToken());
        });
    }

    public void reestablecerContrasena(ReestablecerContrasenaCommand comando) {
        Long idUsuario = autenticacionPort.validarToken(comando.token())
                .orElseThrow(TokenException::tokenExpirado);

        UsuarioModel usuario = usuarioPort.obtenerPorId(idUsuario)
                .orElseThrow(UsuarioException::noEncontrado);

        String hash = autenticacionPort.hashContrasena(comando.nuevaContrasena());
        usuario.cambiarContrasena(hash);
        usuarioPort.guardar(usuario);
        autenticacionPort.invalidarToken(comando.token());
    }

    public void cambiarContrasena(Long idUsuario, CambiarContrasenaCommand comando) {
        UsuarioModel usuario = usuarioPort.obtenerPorId(idUsuario)
                .orElseThrow(UsuarioException::noEncontrado);

        if (!autenticacionPort.verificarContrasena(comando.contrasenaActual(), usuario.getContrasena()))
            throw UsuarioException.credencialesInvalidas();

        String hash = autenticacionPort.hashContrasena(comando.nuevaContrasena());
        usuario.cambiarContrasena(hash);
        usuarioPort.guardar(usuario);
    }

    public void solicitarCambioCorreo(Long idUsuario, CambiarCorreoCommand comando) {
        UsuarioModel usuario = usuarioPort.obtenerPorId(idUsuario)
                .orElseThrow(UsuarioException::noEncontrado);

        usuario.cambiarCorreo(comando.nuevoCorreo());
        usuarioPort.guardar(usuario);

        var token = tokenPort.generarToken(TipoToken.VERIFICACION, usuario.getId());
        correoPort.enviarEmailVerificacion(comando.nuevoCorreo(), usuario.getNombres(), token.getToken());
    }

    public void confirmarCambioCorreo(String token) {
        Long idUsuario = autenticacionPort.validarToken(token)
                .orElseThrow(TokenException::tokenExpirado);

        UsuarioModel usuario = usuarioPort.obtenerPorId(idUsuario)
                .orElseThrow(UsuarioException::noEncontrado);

        usuario.confirmarCorreo();
        usuarioPort.guardar(usuario);
        autenticacionPort.invalidarToken(token);
    }

    public void cerrarSesionUsuario(Long idUsuario) {
        autenticacionPort.invalidarTokensPorUsuario(idUsuario);
    }

    public void reestablecerContrasenaUsuario(Long idUsuario, String nuevaContrasena) {
        UsuarioModel usuario = usuarioPort.obtenerPorId(idUsuario)
                .orElseThrow(UsuarioException::noEncontrado);
        String hash = autenticacionPort.hashContrasena(nuevaContrasena);
        usuario.cambiarContrasena(hash);
        usuarioPort.guardar(usuario);
    }
}
