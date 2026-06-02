package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.CrearInquilinoRequest;
import com.condominios.sgc.application.usecase.CrearInquilinoUseCase;
import com.condominios.sgc.domain.exception.ConfiguracionException;
import com.condominios.sgc.domain.model.ConfiguracionModel;
import com.condominios.sgc.domain.model.InquilinoModel;
import com.condominios.sgc.domain.port.ConfiguracionPort;
import com.condominios.sgc.domain.port.InquilinoPort;

public class CrearInquilinoUseCaseImpl implements CrearInquilinoUseCase {

    private final InquilinoPort inquilinoPort;
    private final ConfiguracionPort configuracionPort;

    public CrearInquilinoUseCaseImpl(InquilinoPort inquilinoPort, ConfiguracionPort configuracionPort) {
        this.inquilinoPort = inquilinoPort;
        this.configuracionPort = configuracionPort;
    }

    @Override
    public InquilinoModel ejecutar(CrearInquilinoRequest request) {
        ConfiguracionModel config = configuracionPort.findByApartamentoId(request.apartamentoId())
                .orElseThrow(ConfiguracionException::noEncontrada);

        Integer inquilinosActuales = inquilinoPort.countByApartamentoId(request.apartamentoId());
        config.puedoAgregarInquilino(inquilinosActuales);

        InquilinoModel modelo = new InquilinoModel(
                request.nombres(),
                request.apellidos(),
                request.dni(),
                request.apartamentoId());

        return inquilinoPort.save(modelo);
    }
}
