package com.condominios.sgc.application.impl.condominio;

import com.condominios.sgc.application.usecase.condominio.EliminarCondominioPorIdUseCase;
import com.condominios.sgc.domain.exception.CondominioException;
import com.condominios.sgc.domain.port.CondominioPort;

public class EliminarCondominioPorIdUseCaseImpl implements EliminarCondominioPorIdUseCase {
    private final CondominioPort condominioPort;

    public EliminarCondominioPorIdUseCaseImpl(CondominioPort condominioPort) {
        this.condominioPort = condominioPort;
    }

    @Override
    public void ejecutar(Long id) {
        condominioPort.obtenerPorId(id)
            .orElseThrow(CondominioException::noEncontrado);

        condominioPort.eliminarPorId(id);
    }
}
