package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.response.ConfiguracionResponse;

public interface ObtenerConfiguracionPorCondominioUseCase {
    ConfiguracionResponse ejecutar(Long idCondominio);
}
