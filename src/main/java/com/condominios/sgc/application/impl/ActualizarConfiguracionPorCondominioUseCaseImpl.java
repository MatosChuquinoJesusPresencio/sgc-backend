package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.command.ActualizarConfiguracionCommand;
import com.condominios.sgc.application.dto.response.ConfiguracionResponse;
import com.condominios.sgc.application.usecase.ActualizarConfiguracionPorCondominioUseCase;
import com.condominios.sgc.domain.exception.ConfiguracionException;
import com.condominios.sgc.domain.model.ConfiguracionModel;
import com.condominios.sgc.domain.port.ConfiguracionPort;

public class ActualizarConfiguracionPorCondominioUseCaseImpl implements ActualizarConfiguracionPorCondominioUseCase {
    private final ConfiguracionPort configuracionPort;

    public ActualizarConfiguracionPorCondominioUseCaseImpl(ConfiguracionPort configuracionPort) {
        this.configuracionPort = configuracionPort;
    }

    @Override
    public ConfiguracionResponse ejecutar(Long idCondominio, ActualizarConfiguracionCommand command) {
        ConfiguracionModel configuracion = configuracionPort.obtenerPorCondominio(idCondominio)
            .orElseThrow(ConfiguracionException::noEncontrado);

        configuracion.actualizar(
            command.maxAutos(), command.maxMotos(), command.penalizacionPorMin(),
            command.maxTiempoPrestamoMin(), command.maxEstacionamientosPorApartamento(),
            command.maxCarritosPorApartamento(), command.maxVehiculosPorPropietario(),
            command.maxInquilinosPorApartamento());
        configuracion = configuracionPort.guardar(configuracion);

        return ConfiguracionResponse.desdeModelo(configuracion);
    }
}
