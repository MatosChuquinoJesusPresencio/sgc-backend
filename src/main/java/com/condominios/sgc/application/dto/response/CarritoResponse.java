package com.condominios.sgc.application.dto.response;

import com.condominios.sgc.domain.auxiliar.EstadoCarrito;
import com.condominios.sgc.domain.model.CarritoModel;

public record CarritoResponse(
    Long id,
    String codigo,
    EstadoCarrito estado,
    Long idCondominio
) {
    public static CarritoResponse desdeModelo(CarritoModel model) {
        return new CarritoResponse(
            model.getId(), model.getCodigo(), model.getEstado(), model.getIdCondominio());
    }
}
