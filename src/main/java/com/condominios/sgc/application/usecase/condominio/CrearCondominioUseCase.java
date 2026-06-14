package com.condominios.sgc.application.usecase.condominio;

import com.condominios.sgc.application.dto.command.CrearCondominioCommand;
import com.condominios.sgc.application.dto.response.CondominioResponse;

public interface CrearCondominioUseCase {
    CondominioResponse ejecutar(CrearCondominioCommand command);
}
