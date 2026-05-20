package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.CrearTorreRequest;
import com.condominios.sgc.application.usecase.CrearTorreUseCase;
import com.condominios.sgc.domain.exception.CondominioException;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.model.TorreModel;
import com.condominios.sgc.domain.port.CondominioPort;
import com.condominios.sgc.domain.port.TorrePort;

public class CrearTorreUseCaseImpl implements CrearTorreUseCase{

    private final TorrePort torrePort;
    private final CondominioPort condominioPort;

    public CrearTorreUseCaseImpl(TorrePort torrePort, CondominioPort condominioPort) {
        this.torrePort = torrePort;
        this.condominioPort = condominioPort;
    }

    @Override
    public TorreModel ejecutar(CrearTorreRequest request) {
        CondominioModel condominio = condominioPort.findById(request.condominioId()).orElse(null);
        if (condominio == null) {
            throw CondominioException.noEncontrado();
        }

        TorreModel torre = new TorreModel(request.nombre(), condominio.getId());
        return torrePort.save(torre);
    }
}
