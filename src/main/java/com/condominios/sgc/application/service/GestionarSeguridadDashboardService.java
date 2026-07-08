package com.condominios.sgc.application.service;

import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.SecurityDashboardResult;
import com.condominios.sgc.application.dto.result.SecurityDashboardResult.SecurityRecentLogEntry;
import com.condominios.sgc.application.helper.CondominioIdResolver;
import com.condominios.sgc.application.port.in.GestionarSeguridadDashboardUseCase;
import com.condominios.sgc.application.port.out.EstacionamientoRepositoryPort;
import com.condominios.sgc.application.port.out.LogAccesoVehicularRepositoryPort;
import com.condominios.sgc.application.port.out.LogPrestamoCarritoRepositoryPort;

public class GestionarSeguridadDashboardService implements GestionarSeguridadDashboardUseCase {

    private final CondominioIdResolver condominioIdResolver;
    private final EstacionamientoRepositoryPort estacionamientoRepository;
    private final LogPrestamoCarritoRepositoryPort logPrestamoRepository;
    private final LogAccesoVehicularRepositoryPort logAccesoRepository;

    public GestionarSeguridadDashboardService(
            CondominioIdResolver condominioIdResolver,
            EstacionamientoRepositoryPort estacionamientoRepository,
            LogPrestamoCarritoRepositoryPort logPrestamoRepository,
            LogAccesoVehicularRepositoryPort logAccesoRepository) {
        this.condominioIdResolver = condominioIdResolver;
        this.estacionamientoRepository = estacionamientoRepository;
        this.logPrestamoRepository = logPrestamoRepository;
        this.logAccesoRepository = logAccesoRepository;
    }

    @Override
    public SecurityDashboardResult obtenerStatus(Long condominioIdOverride) {
        var condominioId = condominioIdResolver.resolver(condominioIdOverride);

        var estacionamientos = estacionamientoRepository.buscarPorCondominio(condominioId, new PaginaQuery(0, Integer.MAX_VALUE));
        int total = (int) estacionamientos.total();
        int ocupados = (int) estacionamientos.items().stream()
            .filter(e -> e.getCantidadActual() != null && e.getCantidadActual() > 0)
            .count();

        int prestamosActivos = logPrestamoRepository.buscarActivosPorCondominio(condominioId).size();

        var recientes = logAccesoRepository.buscarRecientesPorCondominio(condominioId, 10)
            .stream()
            .map(l -> new SecurityRecentLogEntry(
                l.getId(),
                "VEHICULAR",
                l.getPlaca().valor() + " - " + l.getMetodo().name(),
                l.getFechaEntrada()))
            .toList();

        return new SecurityDashboardResult(total, ocupados, prestamosActivos, recientes);
    }
}
