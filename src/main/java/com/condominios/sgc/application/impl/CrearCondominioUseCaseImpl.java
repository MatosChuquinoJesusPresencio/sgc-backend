package com.condominios.sgc.application.impl;

import java.time.LocalDateTime;

import com.condominios.sgc.application.dto.CrearCondominioRequest;
import com.condominios.sgc.application.usecase.CrearCondominioUseCase;
import com.condominios.sgc.domain.exception.CondominioException;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.model.ConfiguracionModel;
import com.condominios.sgc.domain.port.CondominioPort;
import com.condominios.sgc.domain.port.ConfiguracionPort;

public class CrearCondominioUseCaseImpl implements CrearCondominioUseCase {

    private final CondominioPort condominioPort;
    private final ConfiguracionPort configuracionPort;

    public CrearCondominioUseCaseImpl(CondominioPort condominioPort, ConfiguracionPort configuracionPort) {
        this.condominioPort = condominioPort;
        this.configuracionPort = configuracionPort;
    }

    @Override
    public CondominioModel ejecutar(CrearCondominioRequest request) {
        if (condominioPort.existsByNombre(request.nombre())) {
            throw CondominioException.nombreEnUso();
        }

        ConfiguracionModel configuracionBase = ConfiguracionModel.obtenerConfiguracionBase();
        ConfiguracionModel configuracionGuardada = configuracionPort.save(configuracionBase);

        CondominioModel condominio = new CondominioModel(
                null,
                request.nombre(),
                request.pais(),
                request.ciudad(),
                request.direccion(),
                LocalDateTime.now(),
                configuracionGuardada.getId(),
                null,
                null,
                null
        );

        return condominioPort.save(condominio);
    }
}
