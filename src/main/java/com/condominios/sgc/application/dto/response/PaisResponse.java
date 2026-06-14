package com.condominios.sgc.application.dto.response;

import com.condominios.sgc.domain.model.PaisModel;

public record PaisResponse(
    Long id,
    String nombre,
    String codigoIso,
    Long idMoneda
) {
    public static PaisResponse desdeModelo(PaisModel model) {
        return new PaisResponse(model.getId(), model.getNombre(), model.getCodigoIso(), model.getIdMoneda());
    }
}
