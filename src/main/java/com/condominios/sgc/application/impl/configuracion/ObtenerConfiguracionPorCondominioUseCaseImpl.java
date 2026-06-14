package com.condominios.sgc.application.impl.configuracion;

import com.condominios.sgc.application.dto.response.ConfiguracionResponse;
import com.condominios.sgc.application.usecase.configuracion.ObtenerConfiguracionPorCondominioUseCase;
import com.condominios.sgc.domain.exception.ConfiguracionException;
import com.condominios.sgc.domain.port.ConfiguracionPort;

public class ObtenerConfiguracionPorCondominioUseCaseImpl implements ObtenerConfiguracionPorCondominioUseCase {
    private final ConfiguracionPort configuracionPort;

    public ObtenerConfiguracionPorCondominioUseCaseImpl(ConfiguracionPort configuracionPort) {
        this.configuracionPort = configuracionPort;
    }

    @Override
    public ConfiguracionResponse ejecutar(Long idCondominio) {
        return configuracionPort.obtenerPorCondominio(idCondominio)
            .map(ConfiguracionResponse::desdeModelo)
            .orElseThrow(ConfiguracionException::noEncontrado);
    }
}
