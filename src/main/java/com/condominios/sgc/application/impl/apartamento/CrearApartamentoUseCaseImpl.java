package com.condominios.sgc.application.impl.apartamento;

import com.condominios.sgc.application.dto.command.CrearApartamentoCommand;
import com.condominios.sgc.application.dto.response.ApartamentoResponse;
import com.condominios.sgc.application.usecase.apartamento.CrearApartamentoUseCase;
import com.condominios.sgc.domain.model.ApartamentoModel;
import com.condominios.sgc.domain.port.ApartamentoPort;

public class CrearApartamentoUseCaseImpl implements CrearApartamentoUseCase {
    private final ApartamentoPort apartamentoPort;

    public CrearApartamentoUseCaseImpl(ApartamentoPort apartamentoPort) {
        this.apartamentoPort = apartamentoPort;
    }

    @Override
    public ApartamentoResponse ejecutar(CrearApartamentoCommand command) {
        ApartamentoModel apartamento = new ApartamentoModel(
            command.numero(), command.metraje(), command.idPiso());

        apartamento = apartamentoPort.guardar(apartamento);
        
        return ApartamentoResponse.desdeModelo(apartamento);
    }
}
