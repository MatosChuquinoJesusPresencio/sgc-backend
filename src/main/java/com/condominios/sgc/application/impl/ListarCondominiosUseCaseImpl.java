package com.condominios.sgc.application.impl;

import java.util.List;

import com.condominios.sgc.application.usecase.ListarCondominiosUseCase;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.port.CondominioPort;

public class ListarCondominiosUseCaseImpl implements ListarCondominiosUseCase {

    private final CondominioPort condominioPort;

    public ListarCondominiosUseCaseImpl(CondominioPort condominioPort) {
        this.condominioPort = condominioPort;
    }

    @Override
    public List<CondominioModel> ejecutar() {
        return condominioPort.findAll();
    }
}
