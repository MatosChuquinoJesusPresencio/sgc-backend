package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.command.ActualizarConfiguracionCommand;
import com.condominios.sgc.application.dto.response.ConfiguracionResponse;

public interface ActualizarConfiguracionPorCondominioUseCase {
    ConfiguracionResponse ejecutar(Long idCondominio, ActualizarConfiguracionCommand command);
}
