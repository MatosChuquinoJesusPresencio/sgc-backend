package com.condominios.sgc.application.usecase.apartamento;

import com.condominios.sgc.application.dto.response.DetalleApartamentoResponse;

public interface ObtenerDetalleApartamentoUseCase {
    DetalleApartamentoResponse ejecutar(Long id);
}
