package com.condominios.sgc.application.usecase.apartamento;

import com.condominios.sgc.application.dto.command.CrearApartamentoCommand;
import com.condominios.sgc.application.dto.response.ApartamentoResponse;

public interface CrearApartamentoUseCase {
    ApartamentoResponse ejecutar(CrearApartamentoCommand command);
}
