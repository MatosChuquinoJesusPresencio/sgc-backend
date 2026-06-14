package com.condominios.sgc.application.usecase.apartamento;

import com.condominios.sgc.application.dto.command.ActualizarApartamentoCommand;
import com.condominios.sgc.application.dto.response.ApartamentoResponse;

public interface ActualizarApartamentoPorIdUseCase {
    ApartamentoResponse ejecutar(Long id, ActualizarApartamentoCommand command);
}
