package com.condominios.sgc.application.impl.apartamento;

import com.condominios.sgc.application.dto.response.ApartamentoResponse;
import com.condominios.sgc.application.usecase.apartamento.ObtenerApartamentoPorIdUseCase;
import com.condominios.sgc.domain.exception.ApartamentoException;
import com.condominios.sgc.domain.port.ApartamentoPort;

public class ObtenerApartamentoPorIdUseCaseImpl implements ObtenerApartamentoPorIdUseCase {
    private final ApartamentoPort apartamentoPort;

    public ObtenerApartamentoPorIdUseCaseImpl(ApartamentoPort apartamentoPort) {
        this.apartamentoPort = apartamentoPort;
    }

    @Override
    public ApartamentoResponse ejecutar(Long id) {
        return apartamentoPort.obtenerPorId(id)
            .map(ApartamentoResponse::desdeModelo)
            .orElseThrow(ApartamentoException::noEncontrado);
    }
}
