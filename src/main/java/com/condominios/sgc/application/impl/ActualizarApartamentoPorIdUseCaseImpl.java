package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.command.ActualizarApartamentoCommand;
import com.condominios.sgc.application.dto.response.ApartamentoResponse;
import com.condominios.sgc.application.usecase.ActualizarApartamentoPorIdUseCase;
import com.condominios.sgc.domain.exception.ApartamentoException;
import com.condominios.sgc.domain.model.ApartamentoModel;
import com.condominios.sgc.domain.port.ApartamentoPort;

public class ActualizarApartamentoPorIdUseCaseImpl implements ActualizarApartamentoPorIdUseCase {
    private final ApartamentoPort apartamentoPort;

    public ActualizarApartamentoPorIdUseCaseImpl(ApartamentoPort apartamentoPort) {
        this.apartamentoPort = apartamentoPort;
    }

    @Override
    public ApartamentoResponse ejecutar(Long id, ActualizarApartamentoCommand command) {
        ApartamentoModel apartamento = apartamentoPort.obtenerPorId(id)
            .orElseThrow(ApartamentoException::noEncontrado);

        apartamento.actualizar(command.numero(), command.metraje());

        if (command.desasignarPropietario()) {
            apartamento.desasignarPropietario();
        } else if (command.idPropietario() != null) {
            apartamento.asignarPropietario(command.idPropietario());
        }

        apartamento = apartamentoPort.guardar(apartamento);

        return ApartamentoResponse.desdeModelo(apartamento);
    }
}
