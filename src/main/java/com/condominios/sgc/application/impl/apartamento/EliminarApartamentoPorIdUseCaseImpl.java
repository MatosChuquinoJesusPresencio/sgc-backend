package com.condominios.sgc.application.impl.apartamento;

import com.condominios.sgc.application.usecase.apartamento.EliminarApartamentoPorIdUseCase;
import com.condominios.sgc.domain.exception.ApartamentoException;
import com.condominios.sgc.domain.port.ApartamentoPort;

public class EliminarApartamentoPorIdUseCaseImpl implements EliminarApartamentoPorIdUseCase {
    private final ApartamentoPort apartamentoPort;

    public EliminarApartamentoPorIdUseCaseImpl(ApartamentoPort apartamentoPort) {
        this.apartamentoPort = apartamentoPort;
    }

    @Override
    public void ejecutar(Long id) {
        apartamentoPort.obtenerPorId(id)
            .orElseThrow(ApartamentoException::noEncontrado);

        apartamentoPort.eliminarPorId(id);
    }
}
