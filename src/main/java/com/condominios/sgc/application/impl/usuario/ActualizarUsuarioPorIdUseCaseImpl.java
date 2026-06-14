package com.condominios.sgc.application.impl.usuario;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import com.condominios.sgc.application.dto.command.ActualizarUsuarioCommand;
import com.condominios.sgc.application.dto.response.UsuarioResponse;
import com.condominios.sgc.application.usecase.usuario.ActualizarUsuarioPorIdUseCase;
import com.condominios.sgc.domain.auxiliar.TipoToken;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.model.TokenModel;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.CorreoPort;
import com.condominios.sgc.domain.port.TokenPort;
import com.condominios.sgc.domain.port.UsuarioPort;

public class ActualizarUsuarioPorIdUseCaseImpl implements ActualizarUsuarioPorIdUseCase {
    private final UsuarioPort usuarioPort;
    private final TokenPort tokenPort;
    private final CorreoPort correoPort;

    public ActualizarUsuarioPorIdUseCaseImpl(UsuarioPort usuarioPort, TokenPort tokenPort, CorreoPort correoPort) {
        this.usuarioPort = usuarioPort;
        this.tokenPort = tokenPort;
        this.correoPort = correoPort;
    }

    @Override
    public UsuarioResponse ejecutar(Long id, ActualizarUsuarioCommand command) {
        UsuarioModel usuario = usuarioPort.obtenerPorId(id)
            .orElseThrow(UsuarioException::noEncontrado);

        usuario.actualizar(command.nombres(), command.apellidos(),
            command.telefono(), command.rol(), command.idCondominio());

        if (command.nuevoCorreo() != null) {
            usuario.cambiarCorreo(command.nuevoCorreo());

            String tokenStr = UUID.randomUUID().toString();
            TokenModel token = new TokenModel(TipoToken.VERIFICACION, tokenStr,
                Instant.now().plus(24, ChronoUnit.HOURS), usuario.getId());
            tokenPort.guardar(token);

            correoPort.enviarEmailVerificacion(command.nuevoCorreo(), usuario.getNombres(), tokenStr);
        }

        if (command.desasignarCondominio()) {
            usuario.desasignarCondominio();
        } else if (command.idCondominio() != null) {
            usuario.asignarCondominio(command.idCondominio());
        }

        usuario = usuarioPort.guardar(usuario);
        return UsuarioResponse.desdeModelo(usuario);
    }
}
