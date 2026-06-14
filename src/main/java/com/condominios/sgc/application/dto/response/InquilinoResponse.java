package com.condominios.sgc.application.dto.response;

import com.condominios.sgc.domain.auxiliar.TipoDocumento;
import com.condominios.sgc.domain.model.InquilinoModel;

public record InquilinoResponse(
    Long id,
    String nombres,
    String apellidos,
    TipoDocumento tipoDocumento,
    String numeroDocumento,
    Long idApartamento
) {
    public static InquilinoResponse desdeModelo(InquilinoModel model) {
        return new InquilinoResponse(
            model.getId(), model.getNombres(), model.getApellidos(),
            model.getTipoDocumento(), model.getNumeroDocumento(), model.getIdApartamento());
    }
}
