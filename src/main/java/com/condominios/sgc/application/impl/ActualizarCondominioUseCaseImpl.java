package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.ActualizarCondominioRequest;
import com.condominios.sgc.application.usecase.ActualizarCondominioUseCase;
import com.condominios.sgc.domain.exception.CondominioException;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.port.CondominioPort;

public class ActualizarCondominioUseCaseImpl implements ActualizarCondominioUseCase {

    private final CondominioPort condominioPort;

    public ActualizarCondominioUseCaseImpl(CondominioPort condominioPort) {
        this.condominioPort = condominioPort;
    }

    @Override
    public CondominioModel ejecutar(Long id, ActualizarCondominioRequest request) {
        CondominioModel condominio = condominioPort.findById(id)
                .orElseThrow(CondominioException::noEncontrado);

        if (!condominio.getNombre().equals(request.nombre()) && condominioPort.existsByNombre(request.nombre())) {
            throw CondominioException.nombreEnUso();
        }

        condominio.actualizarDatos(
                request.nombre(),
                request.pais(),
                request.ciudad(),
                request.direccion()
        );

        return condominioPort.save(condominio);
    }
}
