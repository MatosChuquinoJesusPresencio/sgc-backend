package com.condominios.sgc.application.usecase.piso;

import com.condominios.sgc.application.dto.response.PisoResponse;

public interface ActualizarPisoPorIdUseCase {
    PisoResponse ejecutar(Long id, Integer numero);
}
