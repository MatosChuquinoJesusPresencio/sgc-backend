package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.CrearInquilinoRequest;
import com.condominios.sgc.application.dto.InquilinoResponse;
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
    public InquilinoResponse crear(CrearInquilinoRequest request) {
        ConfiguracionModel config = configuracionPort.findById(1L)
                .orElseThrow(ConfiguracionException::noEncontrada);

        Integer inquilinosActuales = inquilinoPort.countByApartamentoId(request.getApartamentoId());
        config.puedoAgregarInquilino(inquilinosActuales);

        InquilinoModel modelo = new InquilinoModel(
                null,
                request.getNombres(),
                request.getApellidos(),
                request.getDni(),
                request.getApartamentoId());

        InquilinoModel guardado = inquilinoPort.save(modelo);
        return new InquilinoResponse(
                guardado.getId(),
                guardado.getNombres(),
                guardado.getApellidos(),
                guardado.getDni(),
                guardado.getApartamentoId());
    }
}
