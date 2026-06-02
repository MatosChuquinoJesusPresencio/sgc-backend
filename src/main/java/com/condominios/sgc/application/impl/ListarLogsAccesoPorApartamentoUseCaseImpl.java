package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ListarLogsAccesoPorApartamentoUseCase;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import com.condominios.sgc.domain.port.LogAccesoVehicularPort;

public class ListarLogsAccesoPorApartamentoUseCaseImpl implements ListarLogsAccesoPorApartamentoUseCase {

    private final LogAccesoVehicularPort logAccesoVehicularPort;

    public ListarLogsAccesoPorApartamentoUseCaseImpl(LogAccesoVehicularPort logAccesoVehicularPort) {
        this.logAccesoVehicularPort = logAccesoVehicularPort;
    }

    @Override
    public PaginacionResponse<LogAccesoVehicularModel> ejecutar(Long apartamentoId, PaginacionRequest request) {
        return logAccesoVehicularPort.findByApartamentoId(apartamentoId, request);
    }
}
