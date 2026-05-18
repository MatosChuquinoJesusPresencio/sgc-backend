package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.CrearTorreRequest;
import com.condominios.sgc.domain.exception.CondominioException;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.model.TorreModel;
import com.condominios.sgc.domain.port.CondominioPort;
import com.condominios.sgc.domain.port.TorrePort;

public class CrearTorreUseCaseImpl {

    private final TorrePort torrePort;
    private final CondominioPort condominioPort;

    public CrearTorreUseCaseImpl(TorrePort torrePort, CondominioPort condominioPort) {
        this.torrePort = torrePort;
        this.condominioPort = condominioPort;
    }

    Override
    public TorreModel ejecutar(CrearTorreRequest request) {
        CondominioModel condominio = condominioPort.findById(request.condominioId());
        if (condominio == null) {
            throw new CondominioException("El condominio especificado no existe.");
        }

        TorreModel torre = new TorreModel();
        torre.setNombre(request.nombre());
        torre.setCondominioId(condominio.getId());

        // Simulación de condominio.agregarTorre() según regla de negocio
        if (condominio.getTorresIds() != null) {
            condominio.getTorresIds().add(torre.getId());
        }

        return torrePort.save(torre);
    }
}
