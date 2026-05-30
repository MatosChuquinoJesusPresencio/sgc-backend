package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.ActualizarApartamentoRequest;
import com.condominios.sgc.application.usecase.ActualizarApartamentoUseCase;
import com.condominios.sgc.application.usecase.ObtenerApartamentoUseCase;
import com.condominios.sgc.domain.model.ApartamentoModel;
import com.condominios.sgc.domain.port.ApartamentoPort;

public class ActualizarApartamentoUseCaseImpl implements ActualizarApartamentoUseCase {
    private final ApartamentoPort apartamentoPort;
    private final ObtenerApartamentoUseCase obtenerApartamentoUseCase;

    public ActualizarApartamentoUseCaseImpl(ApartamentoPort apartamentoPort,
                                            ObtenerApartamentoUseCase obtenerApartamentoUseCase) {
        this.apartamentoPort = apartamentoPort;
        this.obtenerApartamentoUseCase = obtenerApartamentoUseCase;
    }

    @Override
    public ApartamentoModel ejecutar(Long id, ActualizarApartamentoRequest request) {
        ApartamentoModel apartamento = obtenerApartamentoUseCase.ejecutar(id);

        apartamento.actualizarDatos(request.numero(),
                request.metraje(),
                request.derechoEstacionamiento());

        return apartamentoPort.save(apartamento);
    }
}