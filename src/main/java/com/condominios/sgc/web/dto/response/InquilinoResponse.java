package com.condominios.sgc.web.dto.response;

import com.condominios.sgc.domain.auxiliar.TipoDocumento;

public record InquilinoResponse(
    Long id,
    String nombres,
    String apellidos,
    TipoDocumento tipoDocumento,
    String numeroDocumento,
    Long idApartamento
) {
    public static InquilinoResponse desdeAplicacion(com.condominios.sgc.application.dto.response.InquilinoResponse app) {
        return new InquilinoResponse(app.id(), app.nombres(), app.apellidos(),
                app.tipoDocumento(), app.numeroDocumento(), app.idApartamento());
    }
}
