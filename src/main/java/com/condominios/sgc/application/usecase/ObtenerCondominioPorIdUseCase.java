package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.response.CondominioResponse;

public interface ObtenerCondominioPorIdUseCase {
    CondominioResponse ejecutar(Long id);
}
