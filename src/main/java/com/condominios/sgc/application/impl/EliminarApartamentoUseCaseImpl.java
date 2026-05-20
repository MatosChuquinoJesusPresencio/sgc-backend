package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.EliminarApartamentoUseCase;
import com.condominios.sgc.application.usecase.ObtenerApartamentoUseCase;
import com.condominios.sgc.domain.port.ApartamentoPort;

public class EliminarApartamentoUseCaseImpl implements EliminarApartamentoUseCase {
    private final ApartamentoPort apartamentoPort;
    private final ObtenerApartamentoUseCase obtenerApartamentoUseCase;

    public EliminarApartamentoUseCaseImpl(ApartamentoPort apartamentoPort, ObtenerApartamentoUseCase obtenerApartamentoUseCase) {
        this.apartamentoPort = apartamentoPort;
        this.obtenerApartamentoUseCase = obtenerApartamentoUseCase;
    }

    @Override
    public void ejecutar(Long id) {
        obtenerApartamentoUseCase.ejecutar(id);
        apartamentoPort.deleteById(id);
    }
}