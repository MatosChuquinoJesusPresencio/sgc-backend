package com.condominios.sgc.application.service;

import com.condominios.sgc.application.dto.result.SecurityVehicleVerificationResult;
import com.condominios.sgc.application.port.in.GestionarSeguridadVehiculosUseCase;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.VehiculoRepositoryPort;
import com.condominios.sgc.domain.shared.exception.VehiculoException;

public class GestionarSeguridadVehiculosService implements GestionarSeguridadVehiculosUseCase {

    private final VehiculoRepositoryPort vehiculoRepository;
    private final UsuarioRepositoryPort usuarioRepository;

    public GestionarSeguridadVehiculosService(
            VehiculoRepositoryPort vehiculoRepository,
            UsuarioRepositoryPort usuarioRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public SecurityVehicleVerificationResult verificarPorPlaca(String placa) {
        var vehiculo = vehiculoRepository.buscarPorPlaca(placa)
            .orElseThrow(VehiculoException::noEncontrado);
        String nombrePropietario = null;
        if (vehiculo.getIdPropietario() != null) {
            nombrePropietario = usuarioRepository.buscarPorId(vehiculo.getIdPropietario())
                .map(u -> u.getNombreCompleto().nombres() + " " + u.getNombreCompleto().apellidos())
                .orElse(null);
        }
        return new SecurityVehicleVerificationResult(
            vehiculo.getId(), vehiculo.getPlaca().valor(),
            vehiculo.getMarca(), vehiculo.getColor(), vehiculo.getModelo(),
            vehiculo.getTipo(), vehiculo.getIdPropietario(), nombrePropietario,
            vehiculo.getIdEstacionamiento());
    }
}
