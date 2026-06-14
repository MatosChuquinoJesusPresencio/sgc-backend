package com.condominios.sgc.application.impl.torre;

import com.condominios.sgc.application.dto.response.TorreResponse;
import com.condominios.sgc.application.usecase.torre.ActualizarTorrePorIdUseCase;
import com.condominios.sgc.domain.exception.TorreException;
import com.condominios.sgc.domain.model.TorreModel;
import com.condominios.sgc.domain.port.TorrePort;

public class ActualizarTorrePorIdUseCaseImpl implements ActualizarTorrePorIdUseCase {
    private final TorrePort torrePort;

    public ActualizarTorrePorIdUseCaseImpl(TorrePort torrePort) {
        this.torrePort = torrePort;
    }

    @Override
    public TorreResponse ejecutar(Long id, String nombre) {
        TorreModel torre = torrePort.obtenerPorId(id)
            .orElseThrow(TorreException::noEncontrado);
        torre.actualizarNombre(nombre);
        torre = torrePort.guardar(torre);
        return TorreResponse.desdeModelo(torre);
    }
}
