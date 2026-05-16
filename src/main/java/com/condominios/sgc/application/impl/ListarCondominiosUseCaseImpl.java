package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ListarCondominiosUseCase;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.port.CondominioPort;

public class ListarCondominiosUseCaseImpl implements ListarCondominiosUseCase {

    private final CondominioPort condominioPort;

    public ListarCondominiosUseCaseImpl(CondominioPort condominioPort) {
        this.condominioPort = condominioPort;
    }

    @Override
    public PaginacionResponse<CondominioModel> ejecutar(PaginacionRequest request) {
        return condominioPort.buscarPaginado(request);
    }
}
