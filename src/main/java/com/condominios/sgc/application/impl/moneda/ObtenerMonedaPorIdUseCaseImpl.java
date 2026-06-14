package com.condominios.sgc.application.impl.moneda;

import com.condominios.sgc.application.dto.response.MonedaResponse;
import com.condominios.sgc.application.usecase.moneda.ObtenerMonedaPorIdUseCase;
import com.condominios.sgc.domain.exception.MonedaException;
import com.condominios.sgc.domain.port.MonedaPort;

public class ObtenerMonedaPorIdUseCaseImpl implements ObtenerMonedaPorIdUseCase {
    private final MonedaPort monedaPort;

    public ObtenerMonedaPorIdUseCaseImpl(MonedaPort monedaPort) {
        this.monedaPort = monedaPort;
    }

    @Override
    public MonedaResponse ejecutar(Long id) {
        return monedaPort.obtenerPorId(id)
            .map(MonedaResponse::desdeModelo)
            .orElseThrow(MonedaException::noEncontrado);
    }
}
