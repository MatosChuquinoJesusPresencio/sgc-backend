package com.condominios.sgc.web.dto;

import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.domain.model.UsuarioModel;

public record UsuarioResponse(
    Long id,
    String nombres,
    String apellidos,
    String correo,
    String telefono,
    Rol rol,
    Boolean activo,
    Long condominioId,
    String correoPendiente,
    Boolean correoVerificado
) {
    public static UsuarioResponse fromModel(UsuarioModel model) {
        return new UsuarioResponse(
            model.getId(),
            model.getNombres(),
            model.getApellidos(),
            model.getCorreo(),
            model.getTelefono(),
            model.getRol(),
            model.isActivo(),
            model.getCondominioId(),
            model.getCorreoPendiente(),
            model.isCorreoVerificado()
        );
    }
}
