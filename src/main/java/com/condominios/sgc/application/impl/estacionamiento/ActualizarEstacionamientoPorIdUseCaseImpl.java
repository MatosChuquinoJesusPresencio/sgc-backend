package com.condominios.sgc.application.impl.estacionamiento;

import com.condominios.sgc.application.dto.command.ActualizarEstacionamientoCommand;
import com.condominios.sgc.application.dto.response.EstacionamientoResponse;
import com.condominios.sgc.application.usecase.estacionamiento.ActualizarEstacionamientoPorIdUseCase;
import com.condominios.sgc.domain.exception.ConfiguracionException;
import com.condominios.sgc.domain.exception.EstacionamientoException;
import com.condominios.sgc.domain.model.ConfiguracionModel;
import com.condominios.sgc.domain.model.EstacionamientoModel;
import com.condominios.sgc.domain.port.ConfiguracionPort;
import com.condominios.sgc.domain.port.EstacionamientoPort;

public class ActualizarEstacionamientoPorIdUseCaseImpl implements ActualizarEstacionamientoPorIdUseCase {
    private final EstacionamientoPort estacionamientoPort;
    private final ConfiguracionPort configuracionPort;

    public ActualizarEstacionamientoPorIdUseCaseImpl(EstacionamientoPort estacionamientoPort, ConfiguracionPort configuracionPort) {
        this.estacionamientoPort = estacionamientoPort;
        this.configuracionPort = configuracionPort;
    }

    @Override
    public EstacionamientoResponse ejecutar(Long id, ActualizarEstacionamientoCommand command) {
        EstacionamientoModel estacionamiento = estacionamientoPort.obtenerPorId(id)
            .orElseThrow(EstacionamientoException::noEncontrado);

        estacionamiento.actualizarNumero(command.numero());

        if (command.tipoVehiculo() != null) {
            estacionamiento.configurar(command.tipoVehiculo(), command.capacidadMaxima());
        } else {
            estacionamiento.reiniciar();
        }

        if (command.desasignarApartamento()) {
            estacionamiento.desasignarApartamento();
        } else if (command.idApartamento() != null) {
            ConfiguracionModel config = configuracionPort.obtenerPorCondominio(estacionamiento.getIdCondominio())
                .orElseThrow(ConfiguracionException::noEncontrado);
            int count = estacionamientoPort.contarPorApartamento(command.idApartamento());
            if (!config.puedeAsignarEstacionamiento(count))
                throw EstacionamientoException.sinEspacio();
            estacionamiento.asignarApartamento(command.idApartamento());
        }

        estacionamiento = estacionamientoPort.guardar(estacionamiento);

        return EstacionamientoResponse.desdeModelo(estacionamiento);
    }
}
