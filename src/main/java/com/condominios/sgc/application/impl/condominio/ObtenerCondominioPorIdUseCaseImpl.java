package com.condominios.sgc.application.impl.condominio;

import com.condominios.sgc.application.dto.response.CondominioResponse;
import com.condominios.sgc.application.usecase.condominio.ObtenerCondominioPorIdUseCase;
import com.condominios.sgc.domain.exception.CondominioException;
import com.condominios.sgc.domain.port.CondominioPort;

public class ObtenerCondominioPorIdUseCaseImpl implements ObtenerCondominioPorIdUseCase {
    private final CondominioPort condominioPort;

    public ObtenerCondominioPorIdUseCaseImpl(CondominioPort condominioPort) {
        this.condominioPort = condominioPort;
    }

    @Override
    public CondominioResponse ejecutar(Long id) {
        return condominioPort.obtenerPorId(id)
            .map(CondominioResponse::desdeModelo)
            .orElseThrow(CondominioException::noEncontrado);
    }
}
