package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ListarLogsAccesoPorCondominioUseCase;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import com.condominios.sgc.domain.port.LogAccesoVehicularPort;

public class ListarLogsAccesoPorCondominioUseCaseImpl implements ListarLogsAccesoPorCondominioUseCase {

    private final LogAccesoVehicularPort logAccesoVehicularPort;

    public ListarLogsAccesoPorCondominioUseCaseImpl(LogAccesoVehicularPort logAccesoVehicularPort) {
        this.logAccesoVehicularPort = logAccesoVehicularPort;
    }

    @Override
    public PaginacionResponse<LogAccesoVehicularModel> ejecutar(Long condominioId, PaginacionRequest request) {
        return logAccesoVehicularPort.findByCondominioId(condominioId, request);
    }
}
