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
        System.out.println("[Diagnóstico] CrearCondominioUseCase: nombre='" + request.nombre()
            + "', pais='" + request.pais() + "', ciudad='" + request.ciudad()
            + "', direccion='" + request.direccion() + "'");
        if (condominioPort.existsByNombre(request.nombre())) {
            System.out.println("[Diagnóstico] Nombre ya existe: " + request.nombre());
            throw CondominioException.nombreEnUso();
        }

        CondominioModel condominio = new CondominioModel(
                null,
                request.nombre(),
                request.pais(),
                request.ciudad(),
                request.direccion(),
                LocalDateTime.now()
        );

        CondominioModel condominioGuardado = condominioPort.save(condominio);

        ConfiguracionModel configuracionBase = new ConfiguracionModel(
                1, // maxAutos
                1, // maxMotos
                java.math.BigDecimal.ZERO, // penalizacionPorMin
                60, // maxTiempoPrestamoMin
                1, // maxEstacionamientosPorApartamento
                1, // maxCarritosPorApartamento
                2, // maxVehiculosPorPropietario
                5, // maxInquilinosPorApartamento
                condominioGuardado.getId()
        );
        configuracionPort.save(configuracionBase);

        return condominioGuardado;
    }
}
