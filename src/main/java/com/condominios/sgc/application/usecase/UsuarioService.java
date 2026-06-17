package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.command.ActualizarPerfilCommand;
import com.condominios.sgc.application.dto.result.PerfilResult;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.UsuarioPort;
import com.condominios.sgc.domain.exception.UsuarioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioPort usuarioPort;

    public UsuarioService(UsuarioPort usuarioPort) {
        this.usuarioPort = usuarioPort;
    }

    @Transactional(readOnly = true)
    public PerfilResult obtenerPerfil(Long idUsuario) {
        UsuarioModel usuario = usuarioPort.obtenerPorId(idUsuario)
                .orElseThrow(UsuarioException::noEncontrado);
        return aPerfilResult(usuario);
    }

    public PerfilResult actualizarPerfil(Long idUsuario, ActualizarPerfilCommand comando) {
        UsuarioModel usuario = usuarioPort.obtenerPorId(idUsuario)
                .orElseThrow(UsuarioException::noEncontrado);

        usuario.actualizar(
                comando.nombres(),
                comando.apellidos(),
                comando.telefono(),
                usuario.getRol(),
                usuario.getIdCondominio()
        );

        usuario = usuarioPort.guardar(usuario);
        return aPerfilResult(usuario);
    }

    private PerfilResult aPerfilResult(UsuarioModel u) {
        return new PerfilResult(
                u.getId(),
                u.getNombres(),
                u.getApellidos(),
                u.getCorreo(),
                u.getTelefono(),
                u.getRol().name(),
                u.getActivo(),
                u.getCorreoVerificado() != null && u.getCorreoVerificado(),
                u.getIdCondominio(),
                u.getFechaCreacion()
        );
    }
}
