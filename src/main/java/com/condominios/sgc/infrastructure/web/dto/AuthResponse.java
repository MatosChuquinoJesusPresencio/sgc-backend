package com.condominios.sgc.infrastructure.web.dto;

import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.domain.model.UsuarioModel;

public record AuthResponse(
    Long id,
    String correo,
    String nombres,
    String apellidos,
    Rol rol,
    Long condominioId
) {
    public static AuthResponse fromModel(UsuarioModel model) {
        return new AuthResponse(
            model.getId(),
            model.getCorreo(),
            model.getNombres(),
            model.getApellidos(),
            model.getRol(),
            model.getCondominioId()
        );
    }
}
