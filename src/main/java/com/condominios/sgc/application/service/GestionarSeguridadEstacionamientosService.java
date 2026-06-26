package com.condominios.sgc.application.service;

import java.util.List;

import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.SecurityParkingSlotResult;
import com.condominios.sgc.application.port.in.GestionarSeguridadEstacionamientosUseCase;
import com.condominios.sgc.application.port.out.EstacionamientoRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.domain.shared.exception.UsuarioException;

public class GestionarSeguridadEstacionamientosService implements GestionarSeguridadEstacionamientosUseCase {

    private final SecurityServicePort securityService;
    private final UsuarioRepositoryPort usuarioRepository;
    private final EstacionamientoRepositoryPort estacionamientoRepository;

    public GestionarSeguridadEstacionamientosService(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            EstacionamientoRepositoryPort estacionamientoRepository) {
        this.securityService = securityService;
        this.usuarioRepository = usuarioRepository;
        this.estacionamientoRepository = estacionamientoRepository;
    }

    @Override
    public List<SecurityParkingSlotResult> listarSlots() {
        var usuario = usuarioRepository.buscarPorId(securityService.obtenerIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        return estacionamientoRepository.buscarPorCondominio(usuario.getIdCondominio(), new PaginaQuery(0, Integer.MAX_VALUE))
            .items().stream()
            .map(e -> new SecurityParkingSlotResult(
                e.getId(), e.getNumero(), e.getTipoVehiculo(),
                e.getCapacidadMaxima(), e.getCantidadActual(), e.getDisponible()))
            .toList();
    }
}
