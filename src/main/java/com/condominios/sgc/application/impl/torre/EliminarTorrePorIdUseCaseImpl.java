package com.condominios.sgc.application.impl.torre;

import com.condominios.sgc.application.usecase.torre.EliminarTorrePorIdUseCase;
import com.condominios.sgc.domain.exception.TorreException;
import com.condominios.sgc.domain.port.TorrePort;

public class EliminarTorrePorIdUseCaseImpl implements EliminarTorrePorIdUseCase {
    private final TorrePort torrePort;

    public EliminarTorrePorIdUseCaseImpl(TorrePort torrePort) {
        this.torrePort = torrePort;
    }

    @Override
    public void ejecutar(Long id) {
        torrePort.obtenerPorId(id)
            .orElseThrow(TorreException::noEncontrado);
        torrePort.eliminarPorId(id);
    }
}
