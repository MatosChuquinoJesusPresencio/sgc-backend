package com.condominios.sgc.web.dto;

import com.condominios.sgc.domain.auxiliar.EstadoCarrito;
import com.condominios.sgc.domain.model.CarritoModel;

public record CarritoResponse(Long id, String codigo, EstadoCarrito estado, Long condominioId) {
    public static CarritoResponse fromModel(CarritoModel model) {
        return new CarritoResponse(model.getId(), model.getCodigo(), model.getEstado(), model.getCondominioId());
    }
}
