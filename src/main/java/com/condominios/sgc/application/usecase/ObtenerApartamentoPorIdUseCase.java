package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.response.ApartamentoResponse;

public interface ObtenerApartamentoPorIdUseCase {
    ApartamentoResponse ejecutar(Long id);
}
