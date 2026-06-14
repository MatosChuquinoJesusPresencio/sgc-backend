package com.condominios.sgc.application.usecase.condominio;

import com.condominios.sgc.application.dto.command.ActualizarCondominioCommand;
import com.condominios.sgc.application.dto.response.CondominioResponse;

public interface ActualizarCondominioPorIdUseCase {
    CondominioResponse ejecutar(Long id, ActualizarCondominioCommand command);
}
