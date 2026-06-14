package com.condominios.sgc.application.dto.response;

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
    Long idCondominio
) {
    public static UsuarioResponse desdeModelo(UsuarioModel model) {
        return new UsuarioResponse(
            model.getId(), model.getNombres(), model.getApellidos(),
            model.getCorreo(), model.getTelefono(), model.getRol(),
            model.getActivo(), model.getIdCondominio());
    }
}
