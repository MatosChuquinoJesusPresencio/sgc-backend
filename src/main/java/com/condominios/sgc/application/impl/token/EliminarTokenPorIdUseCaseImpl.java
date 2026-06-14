package com.condominios.sgc.application.impl.token;

import com.condominios.sgc.application.usecase.token.EliminarTokenPorIdUseCase;
import com.condominios.sgc.domain.exception.TokenException;
import com.condominios.sgc.domain.port.TokenPort;

public class EliminarTokenPorIdUseCaseImpl implements EliminarTokenPorIdUseCase {
    private final TokenPort tokenPort;

    public EliminarTokenPorIdUseCaseImpl(TokenPort tokenPort) {
        this.tokenPort = tokenPort;
    }

    @Override
    public void ejecutar(Long id) {
        tokenPort.obtenerPorId(id)
            .orElseThrow(TokenException::noEncontrado);

        tokenPort.eliminarPorId(id);
    }
}
