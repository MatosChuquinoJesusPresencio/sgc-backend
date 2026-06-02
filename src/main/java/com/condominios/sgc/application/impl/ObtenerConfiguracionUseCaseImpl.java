package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ObtenerConfiguracionUseCase;
import com.condominios.sgc.domain.exception.ConfiguracionException;
import com.condominios.sgc.domain.model.ConfiguracionModel;
import com.condominios.sgc.domain.port.ConfiguracionPort;

public class ObtenerConfiguracionUseCaseImpl implements ObtenerConfiguracionUseCase {
    
    private final ConfiguracionPort configuracionPort;

    public ObtenerConfiguracionUseCaseImpl(ConfiguracionPort configuracionPort) {
        this.configuracionPort = configuracionPort;
    }

    @Override
    public ConfiguracionModel ejecutar(Long id) {
        return configuracionPort.findById(id)
            .orElseThrow(ConfiguracionException::noEncontrada);
    }
}
