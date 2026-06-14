package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.command.ActualizarCondominioCommand;
import com.condominios.sgc.application.dto.response.CondominioResponse;
import com.condominios.sgc.application.usecase.ActualizarCondominioPorIdUseCase;
import com.condominios.sgc.domain.exception.CondominioException;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.port.CondominioPort;

public class ActualizarCondominioPorIdUseCaseImpl implements ActualizarCondominioPorIdUseCase {
    private final CondominioPort condominioPort;

    public ActualizarCondominioPorIdUseCaseImpl(CondominioPort condominioPort) {
        this.condominioPort = condominioPort;
    }

    @Override
    public CondominioResponse ejecutar(Long id, ActualizarCondominioCommand command) {
        CondominioModel condominio = condominioPort.obtenerPorId(id)
            .orElseThrow(CondominioException::noEncontrado);

        condominio.actualizar(command.nombre(), command.idPais(), command.idCiudad(), command.direccion());
        condominio = condominioPort.guardar(condominio);

        return CondominioResponse.desdeModelo(condominio);
    }
}
