package com.condominios.sgc.application.impl.piso;

import com.condominios.sgc.application.usecase.piso.EliminarPisoPorIdUseCase;
import com.condominios.sgc.domain.exception.PisoException;
import com.condominios.sgc.domain.port.PisoPort;

public class EliminarPisoPorIdUseCaseImpl implements EliminarPisoPorIdUseCase {
    private final PisoPort pisoPort;

    public EliminarPisoPorIdUseCaseImpl(PisoPort pisoPort) {
        this.pisoPort = pisoPort;
    }

    @Override
    public void ejecutar(Long id) {
        pisoPort.obtenerPorId(id)
            .orElseThrow(PisoException::noEncontrado);
        pisoPort.eliminarPorId(id);
    }
}
