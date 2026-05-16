package com.condominios.sgc.web.dto;

import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.domain.model.UsuarioModel;

public record UsuarioResponse(
    String id,
    String nombres,
    String apellidos,
    String correo,
    String telefono,
    Rol rol,
    Boolean activo,
    Long condominioId
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
            model.getCondominioId()
        );
    }
}
