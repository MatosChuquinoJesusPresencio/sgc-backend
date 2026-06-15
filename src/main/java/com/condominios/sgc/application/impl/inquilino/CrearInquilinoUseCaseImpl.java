package com.condominios.sgc.application.impl.inquilino;

import com.condominios.sgc.application.dto.command.CrearInquilinoCommand;
import com.condominios.sgc.application.dto.response.InquilinoResponse;
import com.condominios.sgc.application.usecase.inquilino.CrearInquilinoUseCase;
import com.condominios.sgc.domain.exception.ConfiguracionException;
import com.condominios.sgc.domain.exception.InquilinoException;
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
    public InquilinoResponse ejecutar(CrearInquilinoCommand command) {
        ConfiguracionModel config = configuracionPort.obtenerPorCondominio(command.idCondominio())
            .orElseThrow(ConfiguracionException::noEncontrado);
        if (inquilinoPort.obtenerPorDocumento(command.tipoDocumento(), command.numeroDocumento()).isPresent()) {
            throw InquilinoException.documentoYaRegistrado();
        }

        int count = inquilinoPort.contarPorApartamento(command.idApartamento());
        if (!config.puedeAgregarInquilino(count))
            throw InquilinoException.limiteAlcanzado();

        InquilinoModel inquilino = new InquilinoModel(
            command.nombres(), command.apellidos(), command.tipoDocumento(),
            command.numeroDocumento(), command.idApartamento());

        inquilino = inquilinoPort.guardar(inquilino);

        return InquilinoResponse.desdeModelo(inquilino);
    }
}
