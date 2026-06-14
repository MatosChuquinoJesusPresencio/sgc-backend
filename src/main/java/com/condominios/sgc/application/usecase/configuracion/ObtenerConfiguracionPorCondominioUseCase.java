package com.condominios.sgc.application.usecase.configuracion;

import com.condominios.sgc.application.dto.response.ConfiguracionResponse;

public interface ObtenerConfiguracionPorCondominioUseCase {
    ConfiguracionResponse ejecutar(Long idCondominio);
}
