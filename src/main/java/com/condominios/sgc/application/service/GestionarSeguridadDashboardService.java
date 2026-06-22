package com.condominios.sgc.application.service;

import com.condominios.sgc.application.dto.result.SecurityDashboardResult;
import com.condominios.sgc.application.dto.result.SecurityDashboardResult.SecurityRecentLogEntry;
import com.condominios.sgc.application.port.in.GestionarSeguridadDashboardUseCase;
import com.condominios.sgc.application.port.out.EstacionamientoRepositoryPort;
import com.condominios.sgc.application.port.out.LogAccesoVehicularRepositoryPort;
import com.condominios.sgc.application.port.out.LogPrestamoCarritoRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.domain.shared.exception.UsuarioException;

public class GestionarSeguridadDashboardService implements GestionarSeguridadDashboardUseCase {

    private final SecurityServicePort securityService;
    private final UsuarioRepositoryPort usuarioRepository;
    private final EstacionamientoRepositoryPort estacionamientoRepository;
    private final LogPrestamoCarritoRepositoryPort logPrestamoRepository;
    private final LogAccesoVehicularRepositoryPort logAccesoRepository;

    public GestionarSeguridadDashboardService(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            EstacionamientoRepositoryPort estacionamientoRepository,
            LogPrestamoCarritoRepositoryPort logPrestamoRepository,
            LogAccesoVehicularRepositoryPort logAccesoRepository) {
        this.securityService = securityService;
        this.usuarioRepository = usuarioRepository;
        this.estacionamientoRepository = estacionamientoRepository;
        this.logPrestamoRepository = logPrestamoRepository;
        this.logAccesoRepository = logAccesoRepository;
    }

    @Override
    public SecurityDashboardResult obtenerStatus() {
        var usuario = usuarioRepository.buscarPorId(securityService.obtenerIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        var condominioId = usuario.getIdCondominio();

        var estacionamientos = estacionamientoRepository.buscarPorCondominio(condominioId);
        int total = estacionamientos.size();
        int ocupados = (int) estacionamientos.stream()
            .filter(e -> e.getCantidadActual() != null && e.getCantidadActual() > 0)
            .count();

        int prestamosActivos = logPrestamoRepository.buscarActivosPorCondominio(condominioId).size();

        var recientes = logAccesoRepository.buscarRecientesPorCondominio(condominioId, 10)
            .stream()
            .map(l -> new SecurityRecentLogEntry(
                l.getId(),
                "VEHICULAR",
                l.getPlaca().valor() + " - " + l.getMetodo().name(),
                l.getFechaEntrada() != null ? l.getFechaEntrada().toString() : null))
            .toList();

        return new SecurityDashboardResult(total, ocupados, prestamosActivos, recientes);
    }
}
