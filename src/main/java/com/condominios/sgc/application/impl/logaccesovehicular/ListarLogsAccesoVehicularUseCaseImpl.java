package com.condominios.sgc.application.impl.logaccesovehicular;

import java.util.List;

import com.condominios.sgc.application.dto.query.ListarLogsAccesoVehicularQuery;
import com.condominios.sgc.application.dto.response.LogAccesoVehicularResponse;
import com.condominios.sgc.application.usecase.logaccesovehicular.ListarLogsAccesoVehicularUseCase;
import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.LogAccesoVehicularFilter;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import com.condominios.sgc.domain.port.LogAccesoVehicularPort;

public class ListarLogsAccesoVehicularUseCaseImpl implements ListarLogsAccesoVehicularUseCase {
    private final LogAccesoVehicularPort logAccesoVehicularPort;

    public ListarLogsAccesoVehicularUseCaseImpl(LogAccesoVehicularPort logAccesoVehicularPort) {
        this.logAccesoVehicularPort = logAccesoVehicularPort;
    }

    @Override
    public PaginacionResponse<LogAccesoVehicularResponse> ejecutar(ListarLogsAccesoVehicularQuery query) {
        PaginacionRequest pagReq = new PaginacionRequest(query.pagina(), query.tamano());

        LogAccesoVehicularFilter filtro = new LogAccesoVehicularFilter(
            query.placa(), query.ocupante(), query.metodo(),
            query.idVehiculo(), query.idEstacionamiento(),
            query.fechaDesde(), query.fechaHasta(), query.sinSalida());

        PaginacionResponse<LogAccesoVehicularModel> result = logAccesoVehicularPort.obtenerTodos(pagReq, filtro);

        List<LogAccesoVehicularResponse> contenido = result.contenido().stream()
            .map(LogAccesoVehicularResponse::desdeModelo).toList();

        return new PaginacionResponse<>(contenido, result.pagina(), result.tamano(),
            result.totalElementos(), result.totalPaginas());
    }
}
