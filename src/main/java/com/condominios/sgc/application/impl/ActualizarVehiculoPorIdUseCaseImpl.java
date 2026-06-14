package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.command.ActualizarVehiculoCommand;
import com.condominios.sgc.application.dto.response.VehiculoResponse;
import com.condominios.sgc.application.usecase.ActualizarVehiculoPorIdUseCase;
import com.condominios.sgc.domain.exception.VehiculoException;
import com.condominios.sgc.domain.model.VehiculoModel;
import com.condominios.sgc.domain.port.VehiculoPort;

public class ActualizarVehiculoPorIdUseCaseImpl implements ActualizarVehiculoPorIdUseCase {
    private final VehiculoPort vehiculoPort;

    public ActualizarVehiculoPorIdUseCaseImpl(VehiculoPort vehiculoPort) {
        this.vehiculoPort = vehiculoPort;
    }

    @Override
    public VehiculoResponse ejecutar(Long id, ActualizarVehiculoCommand command) {
        VehiculoModel vehiculo = vehiculoPort.obtenerPorId(id)
            .orElseThrow(VehiculoException::noEncontrado);

        vehiculo.actualizar(command.marca(), command.color(), command.modelo(),
            command.placa(), command.tipo());

        if (command.desasignarPropietario()) {
            vehiculo.desasignarPropietario();
        } else if (command.idPropietario() != null) {
            vehiculo.asignarPropietario(command.idPropietario());
        }

        if (command.desasignarInquilino()) {
            vehiculo.desasignarInquilino();
        } else if (command.idInquilino() != null) {
            vehiculo.asignarInquilino(command.idInquilino());
        }

        if (command.desasignarEstacionamiento()) {
            vehiculo.desasignarEstacionamiento();
        } else if (command.idEstacionamiento() != null) {
            vehiculo.asignarEstacionamiento(command.idEstacionamiento());
        }

        vehiculo = vehiculoPort.guardar(vehiculo);
        return VehiculoResponse.desdeModelo(vehiculo);
    }
}
