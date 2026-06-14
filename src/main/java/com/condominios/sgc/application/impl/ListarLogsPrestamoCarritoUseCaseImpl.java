package com.condominios.sgc.application.impl;

import java.util.List;

import com.condominios.sgc.application.dto.query.ListarLogsPrestamoCarritoQuery;
import com.condominios.sgc.application.dto.response.LogPrestamoCarritoResponse;
import com.condominios.sgc.application.usecase.ListarLogsPrestamoCarritoUseCase;
import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.LogPrestamoCarritoFilter;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;
import com.condominios.sgc.domain.port.LogPrestamoCarritoPort;

public class ListarLogsPrestamoCarritoUseCaseImpl implements ListarLogsPrestamoCarritoUseCase {
    private final LogPrestamoCarritoPort logPrestamoCarritoPort;

    public ListarLogsPrestamoCarritoUseCaseImpl(LogPrestamoCarritoPort logPrestamoCarritoPort) {
        this.logPrestamoCarritoPort = logPrestamoCarritoPort;
    }

    @Override
    public PaginacionResponse<LogPrestamoCarritoResponse> ejecutar(ListarLogsPrestamoCarritoQuery query) {
        PaginacionRequest pagReq = new PaginacionRequest(query.pagina(), query.tamano());

        LogPrestamoCarritoFilter filtro = new LogPrestamoCarritoFilter(
            query.nombreSolicitante(), query.dniSolicitante(), query.solicitante(),
            query.idApartamento(), query.idCarrito(),
            query.fechaDesde(), query.fechaHasta(), query.sinDevolucion());

        PaginacionResponse<LogPrestamoCarritoModel> result = logPrestamoCarritoPort.obtenerTodos(pagReq, filtro);

        List<LogPrestamoCarritoResponse> contenido = result.contenido().stream()
            .map(LogPrestamoCarritoResponse::desdeModelo).toList();

        return new PaginacionResponse<>(contenido, result.pagina(), result.tamano(),
            result.totalElementos(), result.totalPaginas());
    }
}
