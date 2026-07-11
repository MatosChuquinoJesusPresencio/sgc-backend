package com.condominios.sgc.application.service;

import java.time.Instant;
import java.util.List;

import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.AdminLogEntryResult;
import com.condominios.sgc.application.dto.result.PaginaResult;
import com.condominios.sgc.application.helper.CondominioIdResolver;
import com.condominios.sgc.application.port.in.GestionarAdminLogsUseCase;
import com.condominios.sgc.application.port.out.LogAccesoVehicularRepositoryPort;
import com.condominios.sgc.application.port.out.LogPrestamoCarritoRepositoryPort;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class GestionarAdminLogsService implements GestionarAdminLogsUseCase {

    private final LogAccesoVehicularRepositoryPort logAccesoRepository;
    private final LogPrestamoCarritoRepositoryPort logCarritoRepository;
    private final CondominioIdResolver condominioIdResolver;

    public GestionarAdminLogsService(
            LogAccesoVehicularRepositoryPort logAccesoRepository,
            LogPrestamoCarritoRepositoryPort logCarritoRepository,
            CondominioIdResolver condominioIdResolver) {
        this.logAccesoRepository = logAccesoRepository;
        this.logCarritoRepository = logCarritoRepository;
        this.condominioIdResolver = condominioIdResolver;
    }

    @Override
    public PaginaResult<AdminLogEntryResult> listar(
            Long condominioIdOverride, String type, Long userId, Instant fechaInicio, Instant fechaFin, PaginaQuery pagina) {
        var condominioId = condominioIdResolver.resolver(condominioIdOverride);

        if ("CARRITO".equalsIgnoreCase(type)) {
            var result = logCarritoRepository.buscarPorCondominio(condominioId, userId, fechaInicio, fechaFin, pagina);
            var items = result.items().stream().map(this::toResult).toList();
            return new PaginaResult<>(items, result.total(), result.pagina(), result.tamano());
        }
        if ("VEHICULAR".equalsIgnoreCase(type)) {
            var result = logAccesoRepository.buscarPorCondominio(condominioId, userId, fechaInicio, fechaFin, pagina);
            var items = result.items().stream().map(this::toResult).toList();
            return new PaginaResult<>(items, result.total(), result.pagina(), result.tamano());
        }

        return new PaginaResult<>(List.of(), 0, pagina.pagina(), pagina.tamano());
    }

    private AdminLogEntryResult toResult(LogAccesoVehicularModel m) {
        return new AdminLogEntryResult(
            m.getId(), "VEHICULAR",
            m.getPlaca().valor(), m.getOcupante().name(), m.getDatosInquilino(),
            m.getMetodo().name(),
            m.getFechaEntrada(),
            m.getFechaSalida(),
            null, null, null, null, null, null,
            m.getIdCondominio(), m.getIdVehiculo(), m.getIdEstacionamiento());
    }

    private AdminLogEntryResult toResult(LogPrestamoCarritoModel m) {
        return new AdminLogEntryResult(
            m.getId(), "CARRITO",
            null, null, null, null, null, null,
            m.getSolicitante().name(), m.getNombreSolicitante(), m.getDniSolicitante(),
            m.getPenalizacion(),
            m.getFechaPrestamo(),
            m.getFechaDevolucion(),
            m.getIdCondominio(), null, null);
    }
}
