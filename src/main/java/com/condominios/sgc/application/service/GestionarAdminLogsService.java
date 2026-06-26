package com.condominios.sgc.application.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.AdminLogEntryResult;
import com.condominios.sgc.application.dto.result.PaginaResult;
import com.condominios.sgc.application.port.in.GestionarAdminLogsUseCase;
import com.condominios.sgc.application.port.out.LogAccesoVehicularRepositoryPort;
import com.condominios.sgc.application.port.out.LogPrestamoCarritoRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;
import com.condominios.sgc.domain.shared.exception.CondominioException;
import com.condominios.sgc.domain.shared.exception.UsuarioException;

public class GestionarAdminLogsService implements GestionarAdminLogsUseCase {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    private final SecurityServicePort securityService;
    private final UsuarioRepositoryPort usuarioRepository;
    private final LogAccesoVehicularRepositoryPort logAccesoRepository;
    private final LogPrestamoCarritoRepositoryPort logCarritoRepository;

    public GestionarAdminLogsService(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            LogAccesoVehicularRepositoryPort logAccesoRepository,
            LogPrestamoCarritoRepositoryPort logCarritoRepository) {
        this.securityService = securityService;
        this.usuarioRepository = usuarioRepository;
        this.logAccesoRepository = logAccesoRepository;
        this.logCarritoRepository = logCarritoRepository;
    }

    private Long obtenerCondominioId() {
        var usuario = usuarioRepository.buscarPorId(securityService.obtenerIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        var condominioId = usuario.getIdCondominio();
        if (condominioId == null) {
            throw CondominioException.noEncontrado();
        }
        return condominioId;
    }

    @Override
    public PaginaResult<AdminLogEntryResult> listar(String filtro, Long condominioId, Instant fechaInicio, Instant fechaFin, PaginaQuery paginaQuery) {


        Page<AdminLogEntryResult> paginaLogs = logAccesoRepository.buscarHistorialCombinado(
                condominioId,
                paginaQuery.pagina(),
                paginaQuery.tamano()
        );


        return new PaginaResult<>(
                paginaLogs.getContent(),
                paginaLogs.getTotalElements(),
                paginaQuery.pagina(),
                paginaQuery.tamano()
        );
    }

    private <T> PaginaResult<AdminLogEntryResult> toPaginaResult(
            Page<T> page, java.util.function.Function<T, AdminLogEntryResult> mapper) {
        var items = page.getContent().stream().map(mapper).toList();
        return new PaginaResult<>(
            items, (int) page.getTotalElements(), page.getNumber(), page.getSize());
    }

    private AdminLogEntryResult toResult(LogAccesoVehicularModel m) {
        return new AdminLogEntryResult(
            m.getId(), "VEHICULAR",
            m.getPlaca().valor(), m.getOcupante().name(), m.getDatosInquilino(),
            m.getMetodo().name(),
            toString(m.getFechaEntrada()),
            toString(m.getFechaSalida()),
            null, null, null, null, null, null,
            m.getIdCondominio());
    }

    private AdminLogEntryResult toResult(LogPrestamoCarritoModel m) {
        return new AdminLogEntryResult(
            m.getId(), "CARRITO",
            null, null, null, null, null, null,
            m.getSolicitante().name(), m.getNombreSolicitante(), m.getDniSolicitante(),
            m.getPenalizacion(),
            toString(m.getFechaPrestamo()),
            toString(m.getFechaDevolucion()),
            m.getIdCondominio());
    }

    private static String toString(Instant instant) {
        return instant != null
            ? ZonedDateTime.ofInstant(instant, ZoneId.systemDefault()).format(FMT)
            : null;
    }
}
