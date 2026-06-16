package com.condominios.sgc.application.impl.apartamento;

import com.condominios.sgc.application.dto.response.ApartamentoResponse;
import com.condominios.sgc.application.usecase.apartamento.ObtenerApartamentoPorPropietarioUseCase;
import com.condominios.sgc.domain.exception.ApartamentoException;
import com.condominios.sgc.domain.port.ApartamentoPort;

public class ObtenerApartamentoPorPropietarioUseCaseImpl implements ObtenerApartamentoPorPropietarioUseCase {

    private final ApartamentoPort apartamentoPort;

    public ObtenerApartamentoPorPropietarioUseCaseImpl(ApartamentoPort apartamentoPort) {
        this.apartamentoPort = apartamentoPort;
    }

    @Override
    public ApartamentoResponse ejecutar(Long idPropietario) {
        return apartamentoPort.obtenerPorPropietario(idPropietario)
                .map(ApartamentoResponse::desdeModelo)
                .orElseThrow(ApartamentoException::noEncontrado);
    }
}
