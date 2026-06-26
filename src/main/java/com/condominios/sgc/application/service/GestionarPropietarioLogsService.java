package com.condominios.sgc.application.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.AdminLogEntryResult;
import com.condominios.sgc.application.dto.result.PaginaResult;
import com.condominios.sgc.application.port.in.GestionarPropietarioLogsUseCase;
import com.condominios.sgc.application.port.out.LogAccesoVehicularRepositoryPort;
import com.condominios.sgc.application.port.out.LogPrestamoCarritoRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;
import com.condominios.sgc.domain.shared.exception.CondominioException;
import com.condominios.sgc.domain.shared.exception.UsuarioException;

public class GestionarPropietarioLogsService implements GestionarPropietarioLogsUseCase {

    private final SecurityServicePort securityService;
    private final UsuarioRepositoryPort usuarioRepository;
    private final LogAccesoVehicularRepositoryPort logAccesoRepository;
    private final LogPrestamoCarritoRepositoryPort logCarritoRepository;

    public GestionarPropietarioLogsService(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            LogAccesoVehicularRepositoryPort logAccesoRepository,
            LogPrestamoCarritoRepositoryPort logCarritoRepository) {
        this.securityService = securityService;
        this.usuarioRepository = usuarioRepository;
        this.logAccesoRepository = logAccesoRepository;
        this.logCarritoRepository = logCarritoRepository;
    }

    @Override
    public PaginaResult<AdminLogEntryResult> listar(Instant fechaInicio, Instant fechaFin, PaginaQuery pagina) {
        var usuario = usuarioRepository.buscarPorId(securityService.obtenerIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        var condominioId = usuario.getIdCondominio();
        if (condominioId == null) throw CondominioException.noEncontrado();
        var userId = usuario.getId();

        var vehicularPage = logAccesoRepository.buscarPorCondominio(
            condominioId, userId, fechaInicio, fechaFin, PageRequest.of(0, Integer.MAX_VALUE));
        var carritoPage = logCarritoRepository.buscarPorCondominio(
            condominioId, userId, fechaInicio, fechaFin, PageRequest.of(0, Integer.MAX_VALUE));

        var combined = new ArrayList<AdminLogEntryResult>();
        combined.addAll(vehicularPage.getContent().stream().map(this::toResult).toList());
        combined.addAll(carritoPage.getContent().stream().map(this::toResult).toList());

        combined.sort(Comparator.<AdminLogEntryResult, String>comparing(
            r -> r.fechaEntrada() != null ? r.fechaEntrada() : r.fechaPrestamo(),
            Comparator.nullsLast(Comparator.reverseOrder())));

        int total = combined.size();
        int from = pagina.pagina() * pagina.tamano();
        int to = Math.min(from + pagina.tamano(), total);
        var items = from < total ? combined.subList(from, to) : List.<AdminLogEntryResult>of();
        return new PaginaResult<>(items, total, pagina.pagina(), pagina.tamano());
    }

    private AdminLogEntryResult toResult(LogAccesoVehicularModel m) {
        return new AdminLogEntryResult(
            m.getId(), "VEHICULAR",
            m.getPlaca().valor(), m.getOcupante().name(), m.getDatosInquilino(),
            m.getMetodo().name(),
            m.getFechaEntrada(),
            m.getFechaSalida(),
            null, null, null, null, null, null,
            m.getIdCondominio());
    }

    private AdminLogEntryResult toResult(LogPrestamoCarritoModel m) {
        return new AdminLogEntryResult(
            m.getId(), "CARRITO",
            null, null, null, null, null, null,
            m.getSolicitante().name(), m.getNombreSolicitante(), m.getDniSolicitante(),
            m.getPenalizacion(),
            m.getFechaPrestamo(),
            m.getFechaDevolucion() != null ? m.getFechaDevolucion().toString() : null,
            m.getIdCondominio());
    }
}
