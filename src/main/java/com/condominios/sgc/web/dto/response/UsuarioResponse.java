package com.condominios.sgc.web.dto.response;

import com.condominios.sgc.domain.auxiliar.Rol;

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
    public static UsuarioResponse desdeAplicacion(com.condominios.sgc.application.dto.response.UsuarioResponse app) {
        return new UsuarioResponse(app.id(), app.nombres(), app.apellidos(),
                app.correo(), app.telefono(), app.rol(), app.activo(), app.idCondominio());
    }
}
