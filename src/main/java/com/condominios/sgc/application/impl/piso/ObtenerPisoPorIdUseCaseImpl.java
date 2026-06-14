package com.condominios.sgc.application.impl.piso;

import com.condominios.sgc.application.dto.response.PisoResponse;
import com.condominios.sgc.application.usecase.piso.ObtenerPisoPorIdUseCase;
import com.condominios.sgc.domain.exception.PisoException;
import com.condominios.sgc.domain.port.PisoPort;

public class ObtenerPisoPorIdUseCaseImpl implements ObtenerPisoPorIdUseCase {
    private final PisoPort pisoPort;

    public ObtenerPisoPorIdUseCaseImpl(PisoPort pisoPort) {
        this.pisoPort = pisoPort;
    }

    @Override
    public PisoResponse ejecutar(Long id) {
        return pisoPort.obtenerPorId(id)
            .map(PisoResponse::desdeModelo)
            .orElseThrow(PisoException::noEncontrado);
    }
}
