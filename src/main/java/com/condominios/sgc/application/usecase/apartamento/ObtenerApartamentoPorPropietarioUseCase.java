package com.condominios.sgc.application.usecase.apartamento;

import com.condominios.sgc.application.dto.response.ApartamentoResponse;

public interface ObtenerApartamentoPorPropietarioUseCase {
    ApartamentoResponse ejecutar(Long idPropietario);
}
