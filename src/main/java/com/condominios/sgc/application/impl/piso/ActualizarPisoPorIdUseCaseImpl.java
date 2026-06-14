package com.condominios.sgc.application.impl.piso;

import com.condominios.sgc.application.dto.response.PisoResponse;
import com.condominios.sgc.application.usecase.piso.ActualizarPisoPorIdUseCase;
import com.condominios.sgc.domain.exception.PisoException;
import com.condominios.sgc.domain.model.PisoModel;
import com.condominios.sgc.domain.port.PisoPort;

public class ActualizarPisoPorIdUseCaseImpl implements ActualizarPisoPorIdUseCase {
    private final PisoPort pisoPort;

    public ActualizarPisoPorIdUseCaseImpl(PisoPort pisoPort) {
        this.pisoPort = pisoPort;
    }

    @Override
    public PisoResponse ejecutar(Long id, Integer numero) {
        PisoModel piso = pisoPort.obtenerPorId(id)
            .orElseThrow(PisoException::noEncontrado);
        piso.actualizarNumero(numero);
        piso = pisoPort.guardar(piso);
        return PisoResponse.desdeModelo(piso);
    }
}
