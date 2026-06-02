package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.EliminarCondominioUseCase;
import com.condominios.sgc.domain.exception.CondominioException;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.port.CondominioPort;

public class EliminarCondominioUseCaseImpl implements EliminarCondominioUseCase {

    private final CondominioPort condominioPort;

    public EliminarCondominioUseCaseImpl(CondominioPort condominioPort) {
        this.condominioPort = condominioPort;
    }

    @Override
    public void ejecutar(Long id) {
        CondominioModel condominio = condominioPort.findById(id)
                .orElseThrow(() -> CondominioException.noEncontrado(id));
        
        condominioPort.deleteById(condominio.getId());
    }
}
