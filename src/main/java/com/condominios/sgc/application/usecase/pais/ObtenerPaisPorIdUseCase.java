package com.condominios.sgc.application.usecase.pais;

import com.condominios.sgc.application.dto.response.PaisResponse;

public interface ObtenerPaisPorIdUseCase {
    PaisResponse ejecutar(Long id);
}
