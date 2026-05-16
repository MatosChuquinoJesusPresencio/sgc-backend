package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ObtenerCondominioUseCase;
import com.condominios.sgc.domain.exception.CondominioException;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.port.CondominioPort;

public class ObtenerCondominioUseCaseImpl implements ObtenerCondominioUseCase {

    private final CondominioPort condominioPort;

    public ObtenerCondominioUseCaseImpl(CondominioPort condominioPort) {
        this.condominioPort = condominioPort;
    }

    @Override
    public CondominioModel ejecutar(Long id) {
        return condominioPort.findById(id)
                .orElseThrow(CondominioException::noEncontrado);
    }
}
